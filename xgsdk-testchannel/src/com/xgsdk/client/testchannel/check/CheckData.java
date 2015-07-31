
package com.xgsdk.client.testchannel.check;

import com.xgsdk.client.api.XGSDK;
import com.xgsdk.client.core.ProductInfo;
import com.xgsdk.client.core.utils.XGLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

public class CheckData {

    private static final SimpleDateFormat SDF = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'ZZZ", Locale.getDefault());
    static {
        SDF.setTimeZone(TimeZone.getDefault());
    }

    private static final String KEY_OS = "os";
    private static final String KEY_XG_APPID = "xgappid";
    private static final String KEY_XG_SDK_VERSION = "xgsdkv";
    private static final String KEY_TS = "ts";
    private static final String KEY_NAME = "name";
    private static final String KEY_PARAMS = "params";
    private static final String KEY_CHECK = "check";
    private static final String KEY_META = "meta";

    private static JSONObject getMetaJson(Context context) throws JSONException {
        JSONObject json = new JSONObject();
        json.put(KEY_OS, "ANDROID");
        json.put(KEY_XG_SDK_VERSION, XGSDK.VERSION);
        json.put(KEY_XG_APPID, ProductInfo.getXGAppId(context));
        json.put(KEY_TS, getTimeStamp());
        return json;
    }

    private static JSONObject getMethodJson(Context context, String methodName,
            HashMap<String, Object> params) throws JSONException {
        JSONObject json = new JSONObject();
        json.put(KEY_NAME, methodName);
        json.put(KEY_TS, getTimeStamp());
        JSONObject pjson = new JSONObject();
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                Object value = params.get(key);
                if (!(value instanceof HashMap<?, ?>)) {
                    pjson.put(key, value);
                } else {
                    pjson.put(key, new JSONObject((HashMap<?, ?>) value));
                }
            }
        }
        json.put(KEY_PARAMS, pjson);

        return json;
    }

    public static JSONObject getCheckTotalJson(Context context,
            String methodName, HashMap<String, Object> params) {
        JSONObject json = new JSONObject();
        try {
            json.put(KEY_META, getMetaJson(context));
            JSONArray array = new JSONArray();
            JSONObject j = getMethodJson(context, methodName, params);
            array.put(j);
            json.put(KEY_CHECK, array);
        } catch (JSONException e) {
            XGLog.e("getInfo error", e);
        }
        return json;
    }

    private static String getTimeStamp() {
        Calendar cal = Calendar.getInstance();
        String time = SDF.format(cal.getTime());
        return time;
    }

}
