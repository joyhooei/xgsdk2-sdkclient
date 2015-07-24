
package com.xgsdk.client.cocos2dx;

public class Cocos2dxUserCallBack {

    public native static void onLogoutSuccess(String msg);

    public native static void onLogoutFail(String msg);

    public native static void onLoginSuccess(String authInfo);

    public native static void onLoginFail(String msg);
    
    public native static void onLoginCancel(String msg);

    public native static void onInitFail(String msg);
}
