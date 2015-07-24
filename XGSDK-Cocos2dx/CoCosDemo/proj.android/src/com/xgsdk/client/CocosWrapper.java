package com.xgsdk.client;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class CocosWrapper {
    private static CocosWrapper sInstance = null;
    private Activity activity;
    
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    static native void onResult(String msg);
    
    public static CocosWrapper getInstance(){
        if (null == sInstance) {
            synchronized (CocosWrapper.class) {
                if (null == sInstance) {
                    sInstance = new CocosWrapper();
                }
            }
        }
        return sInstance;
    }
    
    public void login(String msg){
        final String message = msg;
        activity.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run(){
                        new AlertDialog.Builder(activity)
                        .setTitle("Warning")
                        .setMessage(message)
                        .setPositiveButton("ok", 
                                new DialogInterface.OnClickListener() {
                                    
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                        
                                    }
                                }).create().show();
                    }
                });

    }
}
