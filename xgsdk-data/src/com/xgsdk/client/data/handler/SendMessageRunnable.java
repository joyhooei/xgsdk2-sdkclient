
package com.xgsdk.client.data.handler;

import com.xgsdk.client.data.XGDataAgent;
import com.xgsdk.client.data.process.Common;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class SendMessageRunnable implements Runnable {
    private static final Object mObject = new Object();
    private Context mContext;
    private JSONObject mJSONObject;

    public SendMessageRunnable(Context paramContext, JSONObject paramJSONObject) {
        this.mContext = paramContext;
        this.mJSONObject = paramJSONObject;
    }

    public void run() {
        try {
            synchronized (mObject) {
                Common.postToServer(mContext, mJSONObject);
            }
        } catch (Exception ex) {
            Log.e(XGDataAgent.TAG, "Exception occurred when sending message.");
            ex.printStackTrace();
        }
    }

}
