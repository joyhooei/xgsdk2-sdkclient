
package com.xgsdk.client.callback;

public interface UserCallBack {

    public void onLoginSuccess(String authInfo);

    public void onLoginFail(String msg);

    public void onLogoutSuccess(String msg);

    public void onLogoutFail(String msg);

    public void onInitFail(String msg);

}
