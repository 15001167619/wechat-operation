package web.page.authorization.service;

import web.page.authorization.result.WxOAuth2AccessToken;
import web.page.authorization.result.WxUserInfo;

/**
 * @author 武海升
 * @version 2.0
 * @description
 * @date 2018-07-08 11:57
 */
public interface IAuthorizationService {

    String getAppId();

    String getSecret();

    String getRedirectUrl();

    void setAppId(String appId);

    void setSecret(String secret);

    void setRedirectUrl(String redirectUrl);

    public WxOAuth2AccessToken oauth2getAccessToken(String code);

    boolean oauth2validateAccessToken(WxOAuth2AccessToken wxOAuth2AccessToken);

    WxOAuth2AccessToken oauth2refreshAccessToken(String refreshToken);

    public String oauth2buildAuthorizationUrl(String redirectURI, String scope, String state);

    public WxUserInfo oauth2getUserInfo(WxOAuth2AccessToken wxOAuth2AccessToken,String lang);
}
