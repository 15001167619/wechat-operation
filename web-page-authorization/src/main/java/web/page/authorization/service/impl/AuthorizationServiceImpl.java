package web.page.authorization.service.impl;

import org.apache.commons.lang3.StringUtils;
import web.page.authorization.constants.WxConstants;
import web.page.authorization.result.WxOAuth2AccessToken;
import web.page.authorization.result.WxUserInfo;
import web.page.authorization.service.IAuthorizationService;
import web.page.authorization.utils.HttpClientUtil;


/**
 * @author 武海升
 * @version 2.0
 * @description
 * @date 2018-07-08 11:58
 */
public class AuthorizationServiceImpl implements IAuthorizationService {

    protected volatile String appId;
    protected volatile String secret;
    protected String redirectUrl;

    @Override
    public String getAppId() {
        return this.appId;
    }

    @Override
    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String getSecret() {
        return this.secret;
    }

    @Override
    public String getRedirectUrl() {
        return this.redirectUrl;
    }

    @Override
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public WxOAuth2AccessToken oauth2getAccessToken(String code) {
        String url = String.format(WxConstants.OAUTH2_ACCESS_TOKEN_PATH, new Object[]{this.getAppId(), this.getSecret(), code});
        return this.getOAuth2AccessToken(url);
    }

    @Override
    public boolean oauth2validateAccessToken(WxOAuth2AccessToken token) {
        String url = String.format(WxConstants.OAUTH2_VALIDATE_ACCESS_TOKEN, new Object[]{token.getAccessToken(), token.getOpenId()});
        try {
            HttpClientUtil.get(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public WxOAuth2AccessToken oauth2refreshAccessToken(String refreshToken) {
        String url = String.format(WxConstants.OAUTH2_REFRESH_ACCESS_TOKEN, new Object[]{this.getAppId(), refreshToken});
        return this.getOAuth2AccessToken(url);
    }

    @Override
    public String oauth2buildAuthorizationUrl(String redirectURI, String scope, String state) {
        return String.format(WxConstants.OAUTH2_BUILD_AUTHORIZATION_URL, new Object[]{this.getAppId(), HttpClientUtil.encodeURIComponent(redirectURI), scope, StringUtils.trimToEmpty(state)});
    }

    @Override
    public WxUserInfo oauth2getUserInfo(WxOAuth2AccessToken token, String lang) {
        if(lang == null) {
            lang = "zh_CN";
        }
        String url = String.format(WxConstants.OAUTH2_USER_INFO_PATH, new Object[]{token.getAccessToken(), token.getOpenId(), lang});
        try {
            String responseText = HttpClientUtil.get(url);
            return WxUserInfo.fromJson(responseText);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private WxOAuth2AccessToken getOAuth2AccessToken(String url){
        try {
            String responseText = HttpClientUtil.get(url);
            return WxOAuth2AccessToken.fromJson(responseText);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}
