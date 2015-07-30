
package com.xgsdk.client.callback;

public abstract class PayCallBack {

    public abstract void onSuccess(String msg);

    public abstract void onFail(int code, String msg);

    public abstract void onCancel(String msg);

    public void onProgress(String msg) {
    };

    public void onOthers(int code, String msg) {
    }

}
