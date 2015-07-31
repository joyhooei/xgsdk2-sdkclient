
package com.xgsdk.client.data.process;

import com.xgsdk.client.core.utils.XGLog;
import com.xgsdk.client.data.Constants;
import com.xgsdk.client.data.handler.ProcessThread;
import com.xgsdk.client.data.handler.SendMessageRunnable;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import java.util.Calendar;

public class DataPackager {

    public static void prepareError(Context context, String errLog) {
        JSONObject json = packageAction(Constants.ACTION_ERROR,
                Common.getErrorData(errLog, false), null);
        ProcessThread.sHandler.post(new SendMessageRunnable(context, json));
    }

    public static void prepareTerminateJson(Context context, String sessionId) {
        if (TextUtils.isEmpty(sessionId)) {
            XGLog.w("Missing session_id, ignore message");
            return;
        }
        JSONObject json = new JSONObject();
        try {
            json.put(Constants.KEY_DATA_TYPE, Constants.DATA_TYPE_TERMINATE);
            json.put(Constants.KEY_SESSION, sessionId);
            // mJSONObject.put(Constants.KEY_TS, getTimeStamp());
        } catch (JSONException e) {
            XGLog.e("prepareTerminateJson error", e);
            return;
        }
        ProcessThread.sHandler.post(new SendMessageRunnable(context, json));
    }

    public static void prepareLaunch(Context context, String sessionId) {
        if (TextUtils.isEmpty(sessionId)) {
            XGLog.e("Missing session_id, ignore message");
            return;
        }
        JSONObject json = new JSONObject();
        try {
            json.put(Constants.KEY_DATA_TYPE, Constants.DATA_TYPE_LAUNCH);
            json.put(Constants.KEY_SESSION, sessionId);
        } catch (JSONException e) {
            XGLog.e("prepareLaunch error", e);
            return;
        }
        ProcessThread.sHandler.post(new SendMessageRunnable(context, json));
    }

    public static void prepareOnlineDetection(Context context, String action,
            JSONObject data) {
        JSONObject json = new JSONObject();
        try {
            json.put(Constants.KEY_DATA_TYPE,
                    Constants.DATA_TYPE_ONLINE_DETECTION);
            json.put(Constants.KEY_ACTION, action);
            json.put(Constants.KEY_DATA, data);
        } catch (JSONException e) {
            XGLog.e("prepareOnlineDetection error", e);
            return;
        }
        ProcessThread.sHandler.post(new SendMessageRunnable(context, json));
    }

    public static void prepareGameAction(Context context, String action,
            JSONObject data, JSONObject other) {
        JSONObject infoJSONObject = packageAction(action, data, other);
        ProcessThread.sHandler.post(new SendMessageRunnable(context,
                infoJSONObject));
    }

    public static JSONObject packageAction(String action, JSONObject data,
            JSONObject other) {
        JSONObject json = new JSONObject();
        try {
            json.put(Constants.KEY_ACTION, action);
            if (data != null) {
                json.put(Constants.KEY_DATA, data);
            }
            if (other != null) {
                json.put(Constants.KEY_OTHER, other);
            }
            json.put(Constants.KEY_ACTION_TIME, getTimeStamp());
            if (json.length() > Constants.DATA_MAX_LENGTH) {
                throw new RuntimeException(
                        "The data size is too large! More than "
                                + Constants.DATA_MAX_LENGTH);
            }
        } catch (JSONException e) {
            XGLog.e("packageAction error : " + action, e);
        }
        return json;
    }

    public static String getTimeStamp() {
        Calendar cal = Calendar.getInstance();
        String time = Constants.SDF.format(cal.getTime());
        return time;
    }

}
