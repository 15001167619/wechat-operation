package wechat.pay.service;

/**
 * @author 武海升
 * @date 2018/7/13 10:19
 */
public interface IPayConfigStorage {

    void setAppId(String appId);

    String getAppId();

    String getMchKey();

    void setMchKey(String mchKey);

    String getCallbackUrl();

    void setCallbackUrl(String callbackUrl);

    String getMchId();

    void setMchId(String mchId);

    String getRedirectUrl();

    void setRedirectUrl(String redirectUrl);

}
