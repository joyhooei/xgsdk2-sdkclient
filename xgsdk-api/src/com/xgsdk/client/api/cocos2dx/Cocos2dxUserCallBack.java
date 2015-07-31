
package com.xgsdk.client.api.cocos2dx;

public class Cocos2dxUserCallBack {

    public native static void onLogoutSuccess(String msg);

    public native static void onLogoutFail(int code, String msg);

    public native static void onLoginSuccess(String authInfo);

    public native static void onLoginFail(int code, String msg);

    public native static void onLoginCancel(String msg);

    public native static void onInitFail(int code, String msg);
}
