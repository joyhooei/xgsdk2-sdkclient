
package com.xgsdk.client.testdemo.orders;

import com.xgsdk.client.api.entity.PayInfo;
import com.xgsdk.client.core.service.PayStatus;
import com.xgsdk.client.core.utils.XGLog;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class OrderUtils {

    private static SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public static final String KEY_ORDER_ID = "orderid";
    public static final String KEY_ORDER_DETAILS = "details";
    public static final String KEY_ORDER_TIME = "time";
    public static final String KEY_ORDER_STATUS = "status";

    private static final String PREFIX_ORDERS = "orders_";

    public static void storeOrder(Context context, String uid, String orderId,
            PayInfo pay) {
        SharedPreferences sp = getOrdersSP(context, uid);
        Editor editor = sp.edit();
        JSONObject json;
        try {
            json = toJson(pay);

            if (json != null) {
                json.put("status", PayStatus.UNKNOWN);
                json.put("time",
                        sdf.format(new Date(System.currentTimeMillis())));
                editor.putString(orderId, json.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;
        editor.commit();
    }

    public static HashMap<String, JSONObject> getOrders(Context context,
            String uid) {
        SharedPreferences sp = getOrdersSP(context, uid);
        Map<String, ?> map = sp.getAll();
        if (map == null || map.isEmpty()) {
            return null;
        }

        HashMap<String, JSONObject> orders = new HashMap<String, JSONObject>();
        Set<String> set = map.keySet();

        for (String str : set) {
            String info = (String) map.get(str);
            if (info != null) {
                try {
                    orders.put(str, new JSONObject(info));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return orders;
    }

    public static JSONObject getOrder(Context context, String uid,
            String orderId) {

        SharedPreferences sp = getOrdersSP(context, uid);
        String info = sp.getString(orderId, null);
        if (!TextUtils.isEmpty(info)) {
            try {
                JSONObject json = new JSONObject(info);
                return json;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static void updateOrder(Context context, String uid,
            String orderId, JSONObject json) {
        if (json == null) {
            return;
        }
        SharedPreferences sp = getOrdersSP(context, uid);
        Editor editor = sp.edit();
        editor.putString(orderId, json.toString());
        editor.commit();
    }

    public static void updateOrderStatus(Context context, String uid,
            String orderId, String status) {
        JSONObject json = getOrder(context, uid, orderId);
        if (json != null) {
            try {
                json.put("status", status);
                updateOrder(context, uid, orderId, json);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    public static void cleanOrders(Context context, String uid) {
        SharedPreferences sp = getOrdersSP(context, uid);
        Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    private static SharedPreferences getOrdersSP(Context context, String uid) {
        return context.getSharedPreferences(PREFIX_ORDERS + uid,
                Context.MODE_PRIVATE);

    }

    private static JSONObject toJson(Object obj) throws JSONException {
        if (obj == null) {
            return null;
        }

        JSONObject json = new JSONObject();

        HashMap<String, Object> params = getVariablesFromObj(obj);
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
        json.put(KEY_ORDER_DETAILS, pjson);
        return json;

    }

    private static HashMap<String, Object> getVariablesFromObj(Object obj) {
        if (obj == null) {
            return null;
        }
        HashMap<String, Object> map = new HashMap<String, Object>();

        Method[] methods = obj.getClass().getDeclaredMethods();
        if (methods != null) {
            for (int i = 0; i < methods.length; i++) {

                Method method = methods[i];
                String name = method.getName();
                int parametersCount = method.getParameterTypes().length;
                if (name.startsWith("get") && parametersCount == 0) {
                    try {
                        Object ret = method.invoke(obj, null);
                        String n = name.substring(3);
                        map.put(n, ret);
                    } catch (Exception e) {
                        XGLog.d("", e);

                    }
                }
            }
        }
        return map;
    }

}
