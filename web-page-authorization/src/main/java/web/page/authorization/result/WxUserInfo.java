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
 * @date 2018-07-08 12:05
 */
@Data
public class WxUserInfo implements Serializable {
    private static final long serialVersionUID = 8565955083185313626L;

    private Boolean subscribe;
    private String openId;
    private String nickname;
    private String sexDesc;
    private Integer sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headImgUrl;
    private Long subscribeTime;
    private String unionId;
    private String remark;
    private Integer groupId;
    private Long[] tagIds;
    private String[] privileges;
    private String subscribeScene;
    private String qrScene;
    private String qrSceneStr;

    public static WxUserInfo fromJson(String COMPLEX_JSON_STR) {
        String JSON_STR = null;
        try {
            JSON_STR = new String(COMPLEX_JSON_STR.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return JSON.parseObject(JSON_STR, new TypeReference<WxUserInfo>() {});
    }

}
