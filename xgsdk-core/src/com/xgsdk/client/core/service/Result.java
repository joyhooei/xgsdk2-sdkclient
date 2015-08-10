
package com.xgsdk.client.core.service;

import com.xgsdk.client.core.utils.XGLog;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

public class Result {

    public static final String CODE_SUCCESS = "0";

    private String code;
    private String msg;
    private String data;
    private String orderId;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getData() {
        return data;
    }

    public static Result parse(String ret) {
        Result result = null;
        if (TextUtils.isEmpty(ret)) {
            return result;
        }
        try {
            JSONObject json = new JSONObject(ret);
            result = create();
            result.code = json.optString("code");
            result.msg = json.optString("msg");
            result.data = json.optString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        XGLog.d("response json :" + ret + "\n");
        XGLog.d("result:" + result);
        return result;
    }

    public static Result create() {
        return new Result();
    }

    @Override
    public String toString() {
        return "Result [code=" + code + ", msg=" + msg + ", data=" + data + "]";
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
