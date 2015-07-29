
package com.xgsdk.client;

import android.app.Application;
import android.content.Context;

public class XGApplication extends Application {
    
    @Override
    public void onCreate() {
        XGSDK.getInstance().onApplicationCreate(getApplicationContext());
        super.onCreate();
    }
    
    @Override
    protected void attachBaseContext(Context base) {
        XGSDK.getInstance().onApplicationAttachBaseContext(base);
        super.attachBaseContext(base);
    }

}
