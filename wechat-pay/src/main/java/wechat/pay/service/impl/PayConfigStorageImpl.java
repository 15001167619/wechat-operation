package wechat.pay.service.impl;

import wechat.pay.service.IPayConfigStorage;

/**
 * @author 武海升
 * @date 2018/7/13 12:25
 */
public class PayConfigStorageImpl implements IPayConfigStorage {

    private volatile String appId;
    private volatile String mchKey;
    private volatile String callbackUrl;
    private volatile String redirectUrl;
    private volatile String mchId;

    @Override
    public void setAppId(String appId) {
        this.appId = appId;
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
    public void setMchKey(String mchKey) {
        this.mchKey = mchKey;
    }

    @Override
    public String getCallbackUrl() {
        return callbackUrl;
    }

    @Override
    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    @Override
    public String getMchId() {
        return mchId;
    }

    @Override
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    @Override
    public String getRedirectUrl() {
        return redirectUrl;
    }

    @Override
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
