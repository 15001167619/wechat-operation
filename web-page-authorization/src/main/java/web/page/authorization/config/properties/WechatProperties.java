package web.page.authorization.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author 武海升
 * @version 2.0
 * @description
 * @date 2018-07-07 21:08
 */
@Data
@Configuration
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatProperties {

    /**
     * 设置微信公众号的appid
     */
    private String appId;

    /**
     * 设置微信公众号的app secret
     */
    private String secret;

    /**
     * 设置微信公众号的token
     */
    private String token;

    /**
     * 设置微信公众号的EncodingAESKey
     */
    private String aesKey;
    /**
     * 设置微信公众号 回调路径
     */
    private String redirectUrl;

}
