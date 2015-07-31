
package com.xgsdk.client.data.process;

import com.xgsdk.client.core.utils.XGLog;
import com.xgsdk.client.data.Constants;
import com.xgsdk.client.data.handler.ProcessThread;

import org.json.JSONObject;

import android.content.Context;

class SdkDataTools {

    static void onSdkError(Context context, JSONObject errorInfo) {
        try {
            JSONObject data = Common.getSDKErrorData(context, errorInfo);
            new ProcessThread(context, Constants.ACTION_SDK_ERROR, data, null,
                    Constants.FLAG_GAME_ACTION).start();
        } catch (Exception ex) {
            XGLog.e("Exception occurred in onSdkError. ", ex);
        }
    }

}
