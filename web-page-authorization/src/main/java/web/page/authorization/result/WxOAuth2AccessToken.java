package web.page.authorization.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.Data;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * @author 武海升
 * @version 2.0
 * @description
 * @date 2018-07-08 12:03
 */
@Data
public class WxOAuth2AccessToken implements Serializable {
    private static final long serialVersionUID = -7114551558419892254L;
    private String accessToken;
    private int expiresIn = -1;
    private String refreshToken;
    private String openId;
    private String scope;
    private String unionId;

    public static WxOAuth2AccessToken fromJson(String COMPLEX_JSON_STR) {
        String JSON_STR = null;
        try {
            JSON_STR = new String(COMPLEX_JSON_STR.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return JSON.parseObject(JSON_STR, new TypeReference<WxOAuth2AccessToken>() {});
    }

}
