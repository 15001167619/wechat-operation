package wechat.pay.service;

/**
 * @author 武海升
 * @date 2018/7/13 10:11
 */
public interface IPayService {

    IPayConfigStorage getPayConfigStorage();

    void setPayConfigStorage(IPayConfigStorage payConfigStorage);

    String getAppId();

    String getMchKey();

    String getCallbackUrl();

    String getRedirectUrl();

    String getMchId();


}
