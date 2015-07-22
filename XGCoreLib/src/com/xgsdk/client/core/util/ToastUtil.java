
package com.xgsdk.client.core.util;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtil {
    private static Toast sToastInstance;

    public static void showToast(Context context, String message) {
        makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message, int duration) {
        makeText(context, message, duration).show();
    }

    public static void showToast(Context context, int resId, int duration) {
        showToast(context, context.getString(resId), duration);
    }

    public static void showToast(Context context, int resId) {
        showToast(context, context.getString(resId));
    }

    private static Toast makeText(Context context, String message, int duration) {
        if (sToastInstance == null) {
            sToastInstance = new Toast(context);
            TextView tv = getTextView(context);
            sToastInstance.setView(tv);
        }
        ((TextView) sToastInstance.getView()).setText(message);

        sToastInstance.setDuration(duration);
        return sToastInstance;
    }

    private static TextView getTextView(Context context) {
        TextView tv = new TextView(context);
        tv.setTextColor(Color.WHITE);
        tv.setBackgroundColor(Color.BLACK);
        tv.setPadding(32, 24, 32, 24);
        return tv;
    }

    public static void showNormalToast(Context context, int resId) {
        showNormalToast(context, context.getString(resId), Toast.LENGTH_SHORT);
    }

    public static void showNormalToast(Context context, int resId, int duration) {
        showNormalToast(context, context.getString(resId), duration);
    }

    public static void showNormalToast(Context context, String message) {
        showNormalToast(context, message, Toast.LENGTH_SHORT);
    }

    public static void showNormalToast(Context context, String message,
            int duration) {
        Toast t = Toast.makeText(context, message, duration);
        TextView tv = getTextView(context);
        tv.setText(message);
        t.setView(tv);
        t.show();
    }

}
