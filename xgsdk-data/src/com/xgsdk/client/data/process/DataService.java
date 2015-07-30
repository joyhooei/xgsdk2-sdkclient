
package com.xgsdk.client.data.process;

import com.xgsdk.client.core.util.XGLogger;
import com.xgsdk.client.data.Constants;

import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

public class DataService {
    public static final DataService INSTANCE = new DataService();

    private DataService() {

    }

    public void onResumeService(Context context) {
        String sessionId;
        if (Common.isNewSession(context)) {
            sessionId = Common.getSessionStartInfo(context);
            XGLogger.i("Start new session: " + sessionId);
        } else {
            sessionId = Common.getSessionExtendInfo(context);
            XGLogger.i("Extend current session: " + sessionId);
        }
    }

    public void onPauseService(Context context) {
        Long tmpEndTime = Long.valueOf(System.currentTimeMillis());
        SharedPreferences pref = Common.getSharedPreferences(context);
        SharedPreferences.Editor mEditor = pref.edit();
        mEditor.putLong("end_millis", tmpEndTime);
        mEditor.commit();
    }

    public void onEventService(Context context, JSONObject data,
            JSONObject other) {
        DataPackager.prepareGameAction(context, Constants.ACTION_BEHAVE, data,
                other);
    }

    public void onEventCountService(Context context, JSONObject data,
            JSONObject other) {
        DataPackager.prepareGameAction(context, Constants.ACTION_BEHAVE, data,
                other);
    }

    public void onGameActionService(Context context, String action,
            JSONObject data, JSONObject other) {
        DataPackager.prepareGameAction(context, action, data, other);
    }

    public void onOnlineDetectionService(Context context, String action,
            JSONObject data) {
        DataPackager.prepareOnlineDetection(context, action, data);
    }

}
