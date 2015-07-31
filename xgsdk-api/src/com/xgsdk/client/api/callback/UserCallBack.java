
package com.xgsdk.client.api.callback;

public interface UserCallBack {

    public void onLoginSuccess(String authInfo);

    public void onLoginFail(int code, String msg);

    public void onLoginCancel(String msg);

    public void onLogoutSuccess(String msg);

    public void onLogoutFail(int code, String msg);

    public void onInitFail(int code, String msg);

}
