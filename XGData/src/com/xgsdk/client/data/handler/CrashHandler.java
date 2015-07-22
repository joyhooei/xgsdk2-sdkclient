
package com.xgsdk.client.data.handler;

import com.xgsdk.client.core.util.XGLogger;
import com.xgsdk.client.data.Constants;
import com.xgsdk.client.data.process.Common;
import com.xgsdk.client.data.process.DataPackager;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import java.lang.Thread.UncaughtExceptionHandler;

public class CrashHandler implements UncaughtExceptionHandler {
    private boolean mEnable = false;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;
    private static CrashHandler sInstance;

    private CrashHandler(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static CrashHandler getInstance(Context context) {
        if (sInstance == null) {
            synchronized (CrashHandler.class) {
                if (sInstance == null) {
                    sInstance = new CrashHandler(context);
                }
            }
        }
        return sInstance;
    }

    public void setEnable(boolean enable) {
        mEnable = enable;
    }

    public boolean getEnable() {
        return mEnable;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (mEnable) {
            try {
                JSONObject errorJson = DataPackager.packageAction(
                        Constants.ACTION_ERROR,
                        Common.getErrorData(mContext, ex, true), null);
                Common.storeDataToCache(mContext, errorJson);
            } catch (JSONException e) {
                XGLogger.e(
                        "JSONException in uncaughtException,"
                                + thread.getName() + "," + ex.getMessage(), e);
            }
        }

        if (mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }

}
