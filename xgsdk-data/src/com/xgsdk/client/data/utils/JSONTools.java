
package com.xgsdk.client.data.utils;

import com.xgsdk.client.core.utils.XGLog;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Base64;

public class JSONTools {

    public static String fetchString(JSONObject json, String key) {
        if (json.has(key)) {
            try {
                return json.getString(key);
            } catch (JSONException e) {
                XGLog.e("fetchString error : " + key, e);
            }
        }

        return null;
    }

    public static int fetchInt(JSONObject json, String key) {
        if (json.has(key)) {
            try {
                return json.getInt(key);
            } catch (JSONException e) {
                XGLog.e("fetchInt error : " + key, e);
            }
        }
        return -1;
    }

    public static boolean fetchBoolean(JSONObject json, String key) {
        if (json.has(key)) {
            try {
                return json.getBoolean(key);
            } catch (JSONException e) {
                XGLog.e("fetchBoolean error : " + key, e);
            }
        }
        return false;
    }

    public static String base64decode(String src) {
        try {
            if (TextUtils.isEmpty(src)) {
                return "";
            }
            String decode = new String(Base64.decode(src, Base64.DEFAULT));
            return decode;
        } catch (Exception e) {
            XGLog.e("base64decode error ", e);
        }
        return "";
    }

    public static String base64encode(String src) {
        try {
            if (TextUtils.isEmpty(src)) {
                return "";
            }
            return Base64.encodeToString(src.getBytes(), Base64.DEFAULT);
        } catch (Exception e) {
            XGLog.e("base64encode error ", e);
        }
        return "";
    }

}
