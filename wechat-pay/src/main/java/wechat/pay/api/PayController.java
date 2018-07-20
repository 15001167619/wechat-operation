package wechat.pay.api;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wechat.pay.constants.WxConstants;
import wechat.pay.service.IPayService;
import wechat.pay.utils.MD5Util;
import wechat.pay.utils.TenpayUtil;
import wechat.pay.utils.WXUtil;
import wechat.pay.utils.XMLUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @author 武海升
 * @date 2018/7/13 10:02
 */
@Controller
@RequestMapping(value = "wechat")
@Slf4j
public class PayController {

    private static final String openId = "XXXXXXXX";

    @Autowired
    private IPayService payService;

    @RequestMapping(value = "wxPay",method = RequestMethod.POST)
    public @ResponseBody
    JSONObject wxPay(HttpServletRequest request,
                     String commodityName, double totalPrice){
        /**
         * 总金额(分为单位)
         */
        int total = (int) (totalPrice * 100);

        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        /**
         * 公众号APPID
         */
        parameters.put("appid", payService.getAppId());
        /**
         * 商户号
         */
        parameters.put("mch_id", payService.getMchId());
        /**
         * 随机字符串
         */
        parameters.put("nonce_str", WXUtil.getNonceStr());
        /**
         * 商品名称
         */
        parameters.put("body", commodityName);

        /**
         * 当前时间 yyyyMMddHHmmss
         */
        String currTime = TenpayUtil.getCurrTime();
        /**
         * 8位日期
         */
        String strTime = currTime.substring(8, currTime.length());
        /**
         * 四位随机数
         */
        String strRandom = TenpayUtil.buildRandom(4) + "";
        /**
         * 订单号
         */
        parameters.put("out_trade_no", strTime + strRandom);

        /** 订单金额以分为单位 整数
         */
        parameters.put("total_fee", total);
        /**
         * 客户端本地ip
         */
        parameters.put("spbill_create_ip", request.getRemoteAddr());
        /**
         * 支付回调地址
         */
        parameters.put("notify_url", payService.getRedirectUrl() + payService.getCallbackUrl());
        /**
         * 支付方式：JSAPI支付  trade_type为JSAPI
         */
        parameters.put("trade_type", "JSAPI");
        /**
         用户微信的openid
         */
        parameters.put("openid", openId);

        /** MD5进行签名 */

        String mchKey = payService.getMchKey();

        String sign = createSign("UTF-8", parameters,mchKey);
        parameters.put("sign", sign);

        /** 生成xml结构的数据，用于统一下单接口的请求 */
        String requestXML = getRequestXml(parameters);
        log.info("requestXML：" + requestXML);
        /** 开始请求统一下单接口，获取预支付prepay_id */
        HttpClient client = new HttpClient();
        PostMethod myPost = new PostMethod(WxConstants.WX_UNIURL);
        client.getParams().setSoTimeout(300 * 1000);
        String result = null;
        try {
            myPost.setRequestEntity(new StringRequestEntity(requestXML, "text/xml", "utf-8"));
            int statusCode = client.executeMethod(myPost);
            if (statusCode == HttpStatus.SC_OK) {
                BufferedInputStream bis = new BufferedInputStream(myPost.getResponseBodyAsStream());
                byte[] bytes = new byte[1024];
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int count = 0;
                while ((count = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, count);
                }
                byte[] strByte = bos.toByteArray();
                result = new String(strByte, 0, strByte.length, "utf-8");
                bos.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /** 需要释放掉、关闭连接 */
        myPost.releaseConnection();
        client.getHttpConnectionManager().closeIdleConnections(0);

        log.info("请求统一支付接口的返回结果:");
        log.info(result);
        try {
            /** 解析微信返回的信息 */
            Map<String, String> map = XMLUtil.doXMLParse(result);

            JSONObject payObj = new JSONObject();

            SortedMap<Object, Object> params = new TreeMap<Object, Object>();
            String nonceStr = WXUtil.getNonceStr();
            params.put("appId", payService.getAppId());
            params.put("timeStamp", "\"" + new Date().getTime() + "\"");
            params.put("nonceStr", nonceStr);


            payObj.put("appId", payService.getAppId());
            payObj.put("timeStamp",params.get("timeStamp"));
            payObj.put("nonceStr", nonceStr);
            /**
             * 获取预支付单号prepay_id
             * 微信支付最新接口中，要求package的值的固定格式为prepay_id=...
             */
            params.put("package", "prepay_id=" + map.get("prepay_id"));

            payObj.put("package", "prepay_id=" + map.get("prepay_id"));

            /** MD5加密 */
            params.put("signType", "MD5");

            payObj.put("signType", "MD5");
            /**
             * 获取预支付prepay_id之后，需要再次进行签名，参与签名的参数有：appId、timeStamp、nonceStr、package、signType.
             */

            log.info("【生成签名】appId，appId = {}", payService.getAppId());
            log.info("【生成签名】nonceStr，nonce_str = {}", nonceStr);
            log.info("【生成签名】package，package = {}", params.get("package"));
            log.info("【生成签名】signType，signType = {}", params.get("signType"));
            log.info("【生成签名】timeStamp，timeStamp = {}", params.get("timeStamp"));

            String paySign = createSign("UTF-8", params,mchKey);
            params.put("paySign", paySign);
            payObj.put("paySign", paySign);

            /** 预支付单号，前端ajax回调获取。由于js中package为关键字，所以，这里使用packageValue作为key。 */
            payObj.put("packageValue", "prepay_id=" + map.get("prepay_id"));

            /** 付款成功后，微信会同步请求我们自定义的成功通知页面，通知用户支付成功 */

            payObj.put("sendUrl", payService.getRedirectUrl() + "/wechat/pay/paySuccess.html");
            /** 获取用户的微信客户端版本号 */
            String userAgent = request.getHeader("user-agent");
            char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger") + 15);
            payObj.put("agent", new String(new char[] { agent }));

            log.info("【返回微信】payObj，payObj = {}", payObj.toJSONString());

            return payObj;

        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * sign签名
     * @param characterEncoding
     * @param parameters
     * @return
     */
    public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters,String apiKey) {
        StringBuffer sb = new StringBuffer();
        Set<Map.Entry<Object, Object>> es = parameters.entrySet();
        Iterator<Map.Entry<Object, Object>> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            /** 如果参数为key或者sign，则不参与加密签名 */
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        /** 支付密钥必须参与加密，放在字符串最后面 */
        sb.append("key=" + apiKey);
        log.info("【生成签名连接】 url，url = {}", sb.toString());
        /** 记得最后一定要转换为大写 */
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }


    /**
     * @param parameters
     * @return
     */
    public static String getRequestXml(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set<Map.Entry<Object, Object>> es = parameters.entrySet();
        Iterator<Map.Entry<Object, Object>> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) it.next();
            String k = (String) entry.getKey();
            String v = entry.getValue() + "";
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /***
     * 付款成功回调处理
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "pay")
    public @ResponseBody void notify_success(HttpServletRequest request,
                                             HttpServletResponse response) throws IOException, JDOMException {
        log.info("微信支付成功调用回调URL");
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        log.info("~~~~~~~~~~~~~~~~付款成功~~~~~~~~~");
        outSteam.close();
        inStream.close();

        /** 支付成功后，微信回调返回的信息 */
        String result = new String(outSteam.toByteArray(), "utf-8");
        log.info("微信返回的订单支付信息:" + result);
        Map<Object, Object> map = XMLUtil.doXMLParse(result);

        // 用于验签
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        for (Object keyValue : map.keySet()) {
            /** 输出返回的订单支付信息 */
            log.info(keyValue + "=" + map.get(keyValue));
            if (!"sign".equals(keyValue)) {
                parameters.put(keyValue, map.get(keyValue));
            }
        }
        if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
            // 先进行校验，是否是微信服务器返回的信息
            String mchKey = payService.getMchKey();
            String checkSign = createSign("UTF-8", parameters,mchKey);
            log.info("对服务器返回的结果进行签名：" + checkSign);
            log.info("服务器返回的结果签名：" + map.get("sign"));
            if (checkSign.equals(map.get("sign"))) {// 如果签名和服务器返回的签名一致，说明数据没有被篡改过
                log.info("签名校验成功，信息合法，未被篡改过");
                //告诉微信服务器，我收到信息了，不要再调用回调方法了
                /**如果不返回SUCCESS的信息给微信服务器，则微信服务器会在一定时间内，多次调用该回调方法，如果最终还未收到回馈，微信默认该订单支付失败*/
                /** 微信默认会调用8次该回调地址 */
                OutputStream outputStream = null;
                try {
                    outputStream = response.getOutputStream();
                    outputStream.flush();
                    outputStream.write(setXML("SUCCESS", "").getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try {
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                log.info("-------------" + setXML("SUCCESS", ""));
            }
        }
    }

    /**
     * 发送xml格式数据到微信服务器 告知微信服务器回调信息已经收到。
     */
    public static String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code
                + "]]></return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";
    }


}
