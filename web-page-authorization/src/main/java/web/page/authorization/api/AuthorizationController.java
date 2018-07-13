package web.page.authorization.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.page.authorization.constants.WxConstants;
import web.page.authorization.enums.ResultEnum;
import web.page.authorization.exception.AuthException;
import web.page.authorization.result.WxOAuth2AccessToken;
import web.page.authorization.result.WxUserInfo;
import web.page.authorization.service.IAuthorizationService;

import java.net.URLEncoder;


/**
 * @author 武海升
 * @version 2.0
 * @description
 * @date 2018-07-07 21:06
 */
@Controller
@RequestMapping(value = "wechat")
@Slf4j
public class AuthorizationController {

    @Autowired
    private IAuthorizationService authorizationService;

    /**
     * 获取code参数
     * 网页授权snsapi_base为scope发起的网页授权，是用来获取进入页面的用户的openid的，静默授权并自动跳转到回调页
     * @param returnUrl 需要跳转的url
     * @return
     */
    @GetMapping(value = "authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {
        String url = authorizationService.getRedirectUrl()+"/wechat/getOpenId";
        // 获取微信返回的重定向url
        String redirectUrl = authorizationService.oauth2buildAuthorizationUrl(url, WxConstants.OAuth2Scope.SNSAPI_BASE, URLEncoder.encode(returnUrl));
        log.info("【微信网页授权】获取code，redirectUrl = {}", redirectUrl);
        return "redirect:" + redirectUrl;
    }
    /**
     * 获取code参数
     *snsapi_userinfo为scope发起的网页授权，是用来获取用户的基本信息的。但这种授权需要用户手动同意，并且由于用户同意过，所以无须关注，就可在授权后获取该用户的基本信息。
     * @param returnUrl 需要跳转的url
     * @return
     */
    @GetMapping(value = "authorizeUserInfo")
    public String authorizeUserInfo(@RequestParam("returnUrl") String returnUrl) {
        String url = authorizationService.getRedirectUrl()+"/wechat/getUserInfo";
        // 获取微信返回的重定向url
        String redirectUrl = authorizationService.oauth2buildAuthorizationUrl(url, WxConstants.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        log.info("【微信网页授权】获取code，redirectUrl = {}", redirectUrl);
        return "redirect:" + redirectUrl;
    }

    /**
     * 使用code参数换取access_token信息，并获取到openid
     *
     * @param code
     * @param returnUrl
     * @return
     */
    @GetMapping(value = "getOpenId")
    public String getOpenId(@RequestParam("code") String code, @RequestParam("state") String returnUrl){
        WxOAuth2AccessToken wxOAuth2AccessToken;
        try {
            // 使用code换取access_token信息
            wxOAuth2AccessToken = authorizationService.oauth2getAccessToken(code);
            //验证access token
            boolean valid = authorizationService.oauth2validateAccessToken(wxOAuth2AccessToken);
            log.info("【微信网页授权】验证access token，accessToken = {}", valid);
        } catch (Exception e) {
            log.error("【微信网页授权】异常，{}", e);
            throw new AuthException(ResultEnum.WECHAT_ERROR.getCode(), e.getMessage());
        }
        // 从access_token信息中获取到用户的openid
        String openId = wxOAuth2AccessToken.getOpenId();
        log.info("【微信网页授权】获取openId，openId = {}", openId);
        // 重定向跳转的页面
        return "redirect:" + returnUrl + "?openid=" + openId;
    }
    /**
     * 使用code参数换取access_token信息，getUserInfo
     *
     * @param code
     * @param returnUrl
     * @return
     */
    @GetMapping(value = "getUserInfo")
    public String getUserInfo(@RequestParam("code") String code, @RequestParam("state") String returnUrl){
        WxOAuth2AccessToken wxOAuth2AccessToken;
        try {
            // 使用code换取access_token信息
            wxOAuth2AccessToken = authorizationService.oauth2getAccessToken(code);
        } catch (Exception e) {
            log.error("【微信网页授权】异常，{}", e);
            throw new AuthException(ResultEnum.WECHAT_ERROR.getCode(), e.getMessage());
        }
        WxUserInfo wxUserInfo = authorizationService.oauth2getUserInfo(wxOAuth2AccessToken,null);
        System.out.println(wxUserInfo);
        // 重定向跳转的页面
        return "redirect:" + returnUrl + "?openid=" + wxUserInfo.getOpenId();
    }

}
