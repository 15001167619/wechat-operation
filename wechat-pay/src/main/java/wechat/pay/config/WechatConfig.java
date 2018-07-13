package wechat.pay.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wechat.pay.config.properties.WechatProperties;
import wechat.pay.service.IPayConfigStorage;
import wechat.pay.service.IPayService;
import wechat.pay.service.impl.PayConfigStorageImpl;
import wechat.pay.service.impl.PayServiceImpl;

/**
 * @author 武海升
 * @date 2018/7/13 10:02
 */
@Configuration
public class WechatConfig {

    @Autowired
    private WechatProperties wechatProperties;


    /**
     * 配置IPayService信息
     * @return
     */
    @Bean
    public IPayService payService(){
        IPayService payService = new PayServiceImpl();
        // 设置配置信息的存储位置
        payService.setPayConfigStorage(payConfigStorage());
        return payService;
    }


    /**
     * 配置微信支付参数
     * @return
     */
    @Bean
    public IPayConfigStorage payConfigStorage(){
        IPayConfigStorage payConfigStorage = new PayConfigStorageImpl();
        payConfigStorage.setAppId(wechatProperties.getAppId());
        payConfigStorage.setMchKey(wechatProperties.getMchKey());
        payConfigStorage.setCallbackUrl(wechatProperties.getCallbackUrl());
        payConfigStorage.setRedirectUrl(wechatProperties.getRedirectUrl());
        payConfigStorage.setMchId(wechatProperties.getMchId());
        return payConfigStorage;
    }


}
