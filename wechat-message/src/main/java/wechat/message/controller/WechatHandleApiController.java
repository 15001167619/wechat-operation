package wechat.message.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wechat.message.constants.WxConstants;
import wechat.message.entity.RcvTextMessage;
import wechat.message.entity.RplTextMessage;
import wechat.message.service.RcvMsgService;
import wechat.message.service.RplMsgService;
import wechat.message.utils.Charge;
import wechat.message.utils.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @author 武海升
 * @date 2018/8/23 9:06
 */
@Controller
@RequestMapping(value = "api/wechat")
@Slf4j
public class WechatHandleApiController {

    @Autowired
    private RcvMsgService rsvService;

    @Autowired
    private RplMsgService rplMsgService;

    @RequestMapping("chargeToken")
    @ResponseBody
    public void chargeToken(HttpServletRequest req, HttpServletResponse resp) {

        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        resp.setCharacterEncoding("UTF-8");

        System.out.println("开始请求---");

        String signature = req.getParameter("signature");

        String echostr = req.getParameter("echostr");

        String timestamp = req.getParameter("timestamp");

        String nonce = req.getParameter("nonce");

        if (null == signature || null == timestamp || null == nonce) {
            System.out.println(new Date() + "-非法访问！服务器名：" + req.getRemoteHost() + "，访问地址：" + req.getRequestURL());
            return;
        }

        String[] str = { WxConstants.WX_TOKEN, timestamp, nonce };
        Arrays.sort(str);
        String bigStr = str[0] + str[1] + str[2];

        // 进行校验
        String digest = new Charge().getDigestOfString(bigStr.getBytes()).toLowerCase();

        if (digest.equals(signature))
            try {
                if (echostr != null && echostr.length() > 1) {
                    resp.getWriter().print(echostr);
                } else {
                    try {
                        resp.getWriter().print(this.wxOperate(req, resp));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


    /**
     * 读取从微信服务端post入内容，并生成回复信息
     * @throws IOException
     */
    private String wxOperate(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        /** 读取接收到的xml消息 */
        StringBuffer sb = new StringBuffer();
        InputStream is = req.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        RcvTextMessage rsvMsg;
        RplTextMessage rplMsg;
        String s;
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        String xmlStr = sb.toString();
        Map<String, String> requestMap;
        try {
            requestMap = MessageUtil.parseXml(xmlStr);
            rsvMsg = rsvService.genRcvMsgObj(requestMap);
            rplMsg = rplMsgService.genRplMsgObj(rsvMsg, requestMap);
            br.close();
            isr.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if(br!=null){
                br.close();
            }else if (isr!=null){
                isr.close();
            }else if(is!=null){
                is.close();
            }
        }

        if("text".equals(rplMsg.getMsgType())){
            return rplMsg.toXML();
        }else if("news".equals(rplMsg.getMsgType())){
            return rplMsg.getContent();
        }
        return rplMsg.toXML();

    }

}
