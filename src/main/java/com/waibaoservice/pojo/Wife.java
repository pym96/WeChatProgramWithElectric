package com.waibaoservice.pojo;

/**
 * @author DJS
 * Date create 1:10 2023/3/16
 * Modified By DJS
 **/

public class Wife {
    private String deviceId;
    private String account;
    private String psd;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    @Override
    public String toString() {
        return "Wife{" +
                "deviceId='" + deviceId + '\'' +
                ", account='" + account + '\'' +
                ", psd='" + psd + '\'' +
                '}';
    }
}
