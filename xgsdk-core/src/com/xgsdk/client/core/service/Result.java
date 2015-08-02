
package com.xgsdk.client.core.service;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

public class Result {
    
    public static final String CODE_SUCCESS = "0";

    private String code;
    private String msg;
    private String data;

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
            result = new Result();
            result.code = json.optString("code");
            result.msg = json.optString("msg");
            result.data = json.optString("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

}
