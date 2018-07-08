package web.page.authorization.constants;

/**
 * @author 武海升
 * @version 2.0
 * @description 公用常量类
 * @date 2018-07-08 12:33
 */
public class WxConstants {

    public static String OAUTH2_ACCESS_TOKEN_PATH = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    public static String OAUTH2_USER_INFO_PATH = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=%s";
    public static String OAUTH2_BUILD_AUTHORIZATION_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s&connect_redirect=1#wechat_redirect";
    public static String OAUTH2_VALIDATE_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/auth?access_token=%s&openid=%s";
    public static String OAUTH2_REFRESH_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";

    public static class OAuth2Scope {
        public static final String SNSAPI_BASE = "snsapi_base";
        public static final String SNSAPI_USERINFO = "snsapi_userinfo";

        public OAuth2Scope() {
        }
    }

}
