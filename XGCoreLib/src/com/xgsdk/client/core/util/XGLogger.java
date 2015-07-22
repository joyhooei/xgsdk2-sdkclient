
package com.xgsdk.client.core.util;

import android.util.Log;

public class XGLogger {
    private static final String SDK_LOG = "XG_LOG";
    public static boolean LOG_ENABLE = false;

    public static boolean ENV_DEBUG = ConfigUtil.isEnvDebug();

    public static void d(String msg, boolean isNeedConnect) {
        if (isNeedConnect) {
            Log.d(SDK_LOG, msg, null);
        } else {
            d(SDK_LOG, msg, null);
        }
    }

    public static void i(String msg, boolean isNeedConnect) {
        if (isNeedConnect) {
            Log.i(SDK_LOG, msg, null);
        } else {
            i(SDK_LOG, msg, null);
        }
    }

    public static void d(String msg) {
        d(SDK_LOG, msg, null);
    }

    public static void d(String tag, String msg) {
        d(tag, msg, null);
    }

    public static void d(String msg, Throwable tr) {
        d(SDK_LOG, msg, tr);
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (LOG_ENABLE || ENV_DEBUG) {
            Log.d(tag, msg, tr);
        }
    }

    public static void i(String msg) {
        i(SDK_LOG, msg, null);
    }

    public static void i(String tag, String msg) {
        i(tag, msg, null);
    }

    public static void i(String msg, Throwable tr) {
        i(SDK_LOG, msg, tr);
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (LOG_ENABLE || ENV_DEBUG) {
            Log.i(tag, msg, tr);
        }
    }

    public static void w(String msg) {
        w(SDK_LOG, msg, null);
    }

    public static void w(String tag, String msg) {
        w(tag, msg, null);
    }

    public static void w(String msg, Throwable tr) {
        w(SDK_LOG, msg, tr);
    }

    public static void w(String tag, String msg, Throwable tr) {
        Log.w(tag, msg, tr);
    }

    public static void e(String msg) {
        e(SDK_LOG, msg, null);
    }

    public static void e(String tag, String msg) {
        e(tag, msg, null);
    }

    public static void e(String msg, Throwable tr) {
        e(SDK_LOG, msg, tr);
    }

    public static void e(String tag, String msg, Throwable tr) {
        Log.e(tag, msg, tr);
    }

    public static void v(String msg) {
        v(SDK_LOG, msg, null);
    }

    public static void v(String tag, String msg) {
        v(tag, msg, null);
    }

    public static void v(String msg, Throwable tr) {
        v(SDK_LOG, msg, tr);
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (LOG_ENABLE || ENV_DEBUG) {
            Log.v(tag, msg, tr);
        }
    }
}
