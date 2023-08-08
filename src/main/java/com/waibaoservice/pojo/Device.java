package com.waibaoservice.pojo;

/**
 * @author DJS
 * Date create 19:00 2023/3/14
 * Modified By DJS
 **/
public class Device {
    private String device_id;
    private String openid;

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Override
    public String toString() {
        return "Device{" +
                "device_id=" + device_id +
                ", openid='" + openid + '\'' +
                '}';
    }
}
