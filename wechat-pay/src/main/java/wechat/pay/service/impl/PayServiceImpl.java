package wechat.pay.service.impl;

import wechat.pay.service.IPayConfigStorage;
import wechat.pay.service.IPayService;

/**
 * @author 武海升
 * @date 2018/7/13 12:34
 */
public class PayServiceImpl implements IPayService {

    private IPayConfigStorage payConfigStorage;
    private String appId;
    private String mchKey;
    private String callbackUrl;
    private String redirectUrl;
    private String mchId;

    @Override
    public IPayConfigStorage getPayConfigStorage() {
        return payConfigStorage;
    }

    @Override
    public void setPayConfigStorage(IPayConfigStorage payConfigStorage) {
        this.appId = payConfigStorage.getAppId();
        this.mchKey = payConfigStorage.getMchKey();
        this.callbackUrl = payConfigStorage.getCallbackUrl();
        this.redirectUrl = payConfigStorage.getRedirectUrl();
        this.mchId = payConfigStorage.getMchId();
    }

    @Override
    public String getAppId() {
        return appId;
    }

    @Override
    public String getMchKey() {
        return mchKey;
    }

    @Override
    public String getCallbackUrl() {
        return callbackUrl;
    }

    @Override
    public String getRedirectUrl() {
        return redirectUrl;
    }

    @Override
    public String getMchId() {
        return mchId;
    }

}
