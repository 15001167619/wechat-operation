package web.page.authorization.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 武海升
 * @version 2.0
 * @description
 * @date 2018-07-08 12:25
 */
@Slf4j
public class HttpClientUtil {

    /**
     * URL请求返回正确的statusCode
     */
    public static final int STATE_CODE_RIGHT = 200;

    /**
     * 后台GET 请求
     * @param url
     * @return 响应结果
     */
    public static String get(String url) {
        String result;
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        try {
            client.executeMethod(method);
            if(method.getStatusCode() != STATE_CODE_RIGHT){
                return null;
            }
            result = method.getResponseBodyAsString();
        } catch (HttpException e) {
            log.error(e.getMessage(),e);
            return null;
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            return null;
        }
        method.releaseConnection();
        return result;
    }

    /**
     * 后台GET 请求
     * @param url
     * @param paramMap
     * @return 响应结果
     */
    public static String get(String url, Map<String, String> paramMap) {

        //参数判定
        if(StringUtils.isBlank(url)){
            return null;
        }
        return get(linkURL(url, paramMap));
    }

    /**
     * 后台POST 请求 ,
     * @param url
     * @param params 一定要有参数,不能为空
     * @return 请求返回结果
     */
    public static String post(String url, Map<String, String> params) {

        if(StringUtils.isBlank(url)){
            return null;
        }

        if(params == null || params.size() == 0){
            return null;
        }

        String result = null;
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        for(Map.Entry<String, String> entry : params.entrySet()){
            if(StringUtils.isNotBlank(entry.getValue())){
                method.addParameter(entry.getKey(),entry.getValue());
            }
        }

        try {
            client.executeMethod(method);
            result = method.getResponseBodyAsString();
        } catch (HttpException e) {
            log.error(e.getMessage(),e);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }

        if(method.getStatusCode() != STATE_CODE_RIGHT){
            return null;
        }
        method.releaseConnection();
        return result;
    }

    /**
     * 拼接成一个完整的URL
     * @param baseURL
     * @param paramMap
     * @return
     */
    public static String linkURL(String baseURL, Map<String, String> paramMap){

        String params = null;
        if(paramMap != null && paramMap.size() > 0){
            StringBuilder paramBuilder = new StringBuilder();
            for(Map.Entry<String, String> entry : paramMap.entrySet()){
                paramBuilder.append('&');
                if(StringUtils.isNotBlank(entry.getValue())){
                    paramBuilder.append(entry.getKey());
                    paramBuilder.append('=');
                    paramBuilder.append(entry.getValue());
                }
            }
            params = paramBuilder.deleteCharAt(0).toString();
        }

        if(StringUtils.isBlank(params)){
            return baseURL;
        }

        if(StringUtils.isBlank(baseURL)){
            return params;
        }
        return baseURL + '?' + params;
    }

    /**
     * 将URL中转换成map,如果URL中无参数，则返回null
     *
     * @param url
     * @return
     */
    public static Map<String, String> getURLParamMap(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        String[] tempUrl = url.split("\\?");
        String tmpStr;
        Map<String, String> paramMap = new HashMap<String, String>(6);

        tmpStr = tempUrl[tempUrl.length - 1];
        for (String param : tmpStr.split("&")) {
            String[] tempParam = param.split("=");
            if(tempParam.length == 2){
                paramMap.put(tempParam[0], tempParam[1]);
            }else{
                paramMap.put(tempParam[0], null);
            }
        }

        return paramMap;
    }

    public static String encodeURIComponent(String input) {
        if(StringUtils.isEmpty(input)) {
            return input;
        } else {
            int l = input.length();
            StringBuilder o = new StringBuilder(l * 3);

            try {
                for(int e = 0; e < l; ++e) {
                    String e1 = input.substring(e, e + 1);
                    if("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.!~*\'()".indexOf(e1) == -1) {
                        byte[] b = e1.getBytes("utf-8");
                        o.append(getHex(b));
                    } else {
                        o.append(e1);
                    }
                }

                return o.toString();
            } catch (UnsupportedEncodingException var6) {
                var6.printStackTrace();
                return input;
            }
        }
    }

    private static String getHex(byte[] buf) {
        StringBuilder o = new StringBuilder(buf.length * 3);

        for(int i = 0; i < buf.length; ++i) {
            int n = buf[i] & 255;
            o.append("%");
            if(n < 16) {
                o.append("0");
            }

            o.append(Long.toString((long)n, 16).toUpperCase());
        }

        return o.toString();
    }

    public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
        StringBuffer buffer = new StringBuffer();
        try {

            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();


            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
