
package com.xgsdk.client.core.util;

import android.content.Context;

public class RUtil {

    public static int getResIdentifier(Context context, String name, String type) {
        return context.getResources().getIdentifier(name, type,
                context.getPackageName());
    }

    public static int getId(Context context, String name) {
        return getResIdentifier(context, name, "id");
    }

    public static int getDrawable(Context context, String name) {
        return getResIdentifier(context, name, "drawable");
    }

    public static int getString(Context context, String name) {
        return getResIdentifier(context, name, "string");
    }
    public static int getLayout(Context context, String name) {
        return getResIdentifier(context, name, "layout");
    }

}
