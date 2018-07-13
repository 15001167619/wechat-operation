package wechat.pay.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 武海升
 * @date 2018/7/13 10:03
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "wechat")
public class WechatProperties {

    /**
     * 设置微信公众号的appid
     */
    private String appId;

    /**
     * 支付密钥，商户平台 > API安全 > 密钥管理 中进行设置
     */
    private String mchKey;

    /**
     * 支付的回调方法
     */
    private String callbackUrl;
    /**
     * 微信服务器回调URl
     */
    private String redirectUrl;

    /**
     * 微信公众号绑定的商户号
     */
    private String mchId;

    /**
     * 微信号的openId  具体实际项目可依据网页授权 获取用户openId
     */
    private static final String openId = "of4ll0uPly4bbij3_3PjQap7oi58";

}
