package com.waibaoservice.pojo;

/**
 * @author DJS
 * Date create 22:23 2023/2/19
 * Modified By DJS
 **/
public class User {
    private String code;
    private String Session_key;
    private String openid;
    private String unionid;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSession_key() {
        return Session_key;
    }

    public void setSession_key(String session_key) {
        this.Session_key = session_key;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    @Override
    public String toString() {
        return "User{" +
                "code='" + code + '\'' +
                ", Session_key='" + Session_key + '\'' +
                ", openid='" + openid + '\'' +
                ", unionid='" + unionid + '\'' +
                '}';
    }
}
