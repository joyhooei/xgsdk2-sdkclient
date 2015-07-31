
package com.xgsdk.client.data.handler;

import com.xgsdk.client.core.utils.XGLog;
import com.xgsdk.client.data.Constants;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

public class DetectionHandler {

    private static final long INTERVAL_MS = 5 * 60 * 1000;
    private static final int MSG_ONLINE_DETECTION = 1000;

    private static final String TAG = "DetectionHandler";

    private static HandlerThread sHandlerThread;

    private static DetectionHandler sInstance;

    static {
        sHandlerThread = new HandlerThread(TAG);
        sHandlerThread.start();
    }

    private Handler sHandler = new Handler(sHandlerThread.getLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_ONLINE_DETECTION: {
                    if (!mEnable) {
                        return;
                    }

                    XGLog.i("send online detection data");

                    new ProcessThread(mContext, Constants.ACTION_BEHAVE, null,
                            null, Constants.FLAG_ONLINE_DETECTION).start();
                    sendEmptyMessageDelayed(MSG_ONLINE_DETECTION, INTERVAL_MS);
                }
                    break;
            }
        }
    };

    private Context mContext;

    private boolean mEnable = true;

    private DetectionHandler(Context context) {
        mContext = context;
    }

    public static DetectionHandler getInstance(Context context) {
        synchronized (DetectionHandler.class) {
            if (sInstance == null) {
                synchronized (DetectionHandler.class) {
                    if (sInstance == null) {
                        sInstance = new DetectionHandler(context);
                    }
                }
            }
        }
        return sInstance;
    }

    public void startDetection() {
        XGLog.i("start online detection");
        mEnable = true;
        sHandler.removeMessages(MSG_ONLINE_DETECTION);
        sHandler.sendEmptyMessageDelayed(MSG_ONLINE_DETECTION, INTERVAL_MS);
    }

    public void stopDetection() {
        XGLog.i("stop online detection");
        mEnable = false;
        sHandler.removeMessages(MSG_ONLINE_DETECTION);
    }

}
