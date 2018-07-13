package web.page.authorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import web.page.authorization.config.properties.WechatProperties;
import web.page.authorization.service.IAuthorizationService;
import web.page.authorization.service.impl.AuthorizationServiceImpl;

/**
 * @author 武海升
 * @version 2.0
 * @description
 * @date 2018-07-07 21:07
 */
@Configuration
public class WechatConfig {

    @Autowired
    private WechatProperties wechatProperties;

    @Bean
    public IAuthorizationService authorizationService(){
        IAuthorizationService authorizationService = new AuthorizationServiceImpl();
        authorizationService.setAppId(wechatProperties.getAppId());
        authorizationService.setSecret(wechatProperties.getSecret());
        authorizationService.setRedirectUrl(wechatProperties.getRedirectUrl());
        return authorizationService;
    }
}
