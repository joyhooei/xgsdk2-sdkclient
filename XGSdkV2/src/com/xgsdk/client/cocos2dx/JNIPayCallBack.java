
package com.xgsdk.client.cocos2dx;


public class JNIPayCallBack {

    public native static void onSuccess(String msg);

    public native static void onFail(String msg);

    public native static void onCancel(String msg);

}
