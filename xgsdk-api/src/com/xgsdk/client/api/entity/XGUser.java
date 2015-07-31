
package com.xgsdk.client.api.entity;

public class XGUser {

    private String uid;
    private String userName;
    private String authInfo;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(String authInfo) {
        this.authInfo = authInfo;
    }

    @Override
    public String toString() {
        return "XGUser [uid=" + uid + ", userName=" + userName + ", authInfo="
                + authInfo + " ]";
    }

}
