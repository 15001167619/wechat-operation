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

//    /**
//     * 配置WxMpService所需信息
//     * @return
//     */
//    @Bean
//    public WxMpService wxMpService(){
//        WxMpService wxMpService = new WxMpServiceImpl();
//        // 设置配置信息的存储位置
//        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
//
//        return wxMpService;
//    }

    @Bean
    public IAuthorizationService authorizationService(){
        IAuthorizationService authorizationService = new AuthorizationServiceImpl();
        authorizationService.setAppId(wechatProperties.getAppId());
        authorizationService.setSecret(wechatProperties.getSecret());
        return authorizationService;
    }

//    /**
//     * 配置appID和appsecret
//     * @return
//     */
//    @Bean
//    public WxMpConfigStorage wxMpConfigStorage(){
//        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
//        wxMpInMemoryConfigStorage.setAppId(wechatProperties.getAppId());
//        wxMpInMemoryConfigStorage.setSecret(wechatProperties.getSecret());
//        return wxMpInMemoryConfigStorage;
//    }

}
