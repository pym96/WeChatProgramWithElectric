package com.waibaoservice.pojo;

/**
 * @author DJS
 * Date create 3:01 2023/3/16
 * Modified By DJS
 **/
public class KitchenHood {
    private String positive_time;
    private String device_id;
    private String openId;
    private String end_time;
    private int status;

    public String getPositive_time() {
        return positive_time;
    }

    public void setPositive_time(String positive_time) {
        this.positive_time = positive_time;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "KitchenHood{" +
                "positive_time='" + positive_time + '\'' +
                ", device_id='" + device_id + '\'' +
                ", openId='" + openId + '\'' +
                ", end_time='" + end_time + '\'' +
                ", status=" + status +
                '}';
    }
}
