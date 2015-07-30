
package com.xgsdk.client.data.handler;

import com.xgsdk.client.core.util.XGLogger;
import com.xgsdk.client.data.Constants;
import com.xgsdk.client.data.process.DataService;

import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

public final class ProcessThread extends Thread {

    private static final String TAG = "ProcessThread";

    private static final Object obj = new Object();

    public static HandlerThread sLooperThread;

    public static Handler sHandler;

    private Context mContext;

    private int eventFlag;

    private JSONObject mOther;

    private JSONObject data;

    private String action;

    static {
        sLooperThread = new HandlerThread(TAG);
        sLooperThread.start();
        sHandler = new Handler(sLooperThread.getLooper());
    }

    public ProcessThread(Context paramContext, int eventFlag) {
        super(TAG);
        this.mContext = paramContext;
        this.eventFlag = eventFlag;
    }

    public ProcessThread(Context paramContext, String action, JSONObject data,
            JSONObject other, int eventFlag) {
        super(TAG);
        this.mContext = paramContext;
        this.action = action;
        this.data = data;
        this.mOther = other;
        this.eventFlag = eventFlag;
    }

    public void run() {
        try {
            synchronized (obj) {
                switch (this.eventFlag) {
                    case Constants.FLAG_PAUSE:
                        DataService.INSTANCE.onPauseService(this.mContext);
                        break;
                    case Constants.FLAG_RESUME:
                        DataService.INSTANCE.onResumeService(this.mContext);
                        break;
                    case Constants.FLAG_EVENT:
                        DataService.INSTANCE.onEventService(this.mContext,
                                this.data, this.mOther);
                        break;
                    case Constants.FLAG_EVENT_COUNT:
                        DataService.INSTANCE.onEventCountService(this.mContext,
                                this.data, this.mOther);
                        break;
                    case Constants.FLAG_ONLINE_DETECTION:
                        DataService.INSTANCE.onOnlineDetectionService(
                                this.mContext, this.action, this.data);
                        break;
                    case Constants.FLAG_ERROR:
                    case Constants.FLAG_SDK_ERROR:
                    case Constants.FLAG_GAME_ACTION:
                    case Constants.FLAG_CRASH:
                        DataService.INSTANCE.onGameActionService(this.mContext,
                                this.action, this.data, this.mOther);
                        break;

                }
            }
        } catch (Exception e) {
            XGLogger.e("Exception occurred when recording usage.", e);
        }
    }

}
