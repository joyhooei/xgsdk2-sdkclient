
package com.xgsdk.client.api.cocos2dx;

public class Cocos2dxPayCallBack {

    public native static void onSuccess(String msg);

    public native static void onFail(int code, String msg);

    public native static void onCancel(String msg);

    public native static void onOthers(int code, String msg);

}
