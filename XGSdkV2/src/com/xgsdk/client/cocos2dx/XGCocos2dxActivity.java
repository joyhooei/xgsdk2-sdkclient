
package com.xgsdk.client.cocos2dx;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxGLSurfaceView;
import org.cocos2dx.plugin.PluginWrapper;
import org.cocos2dx.plugin.XGSDKWrapper;

import android.content.Intent;
import android.os.Bundle;

/**
 * XGSDK main activity 重载了生命周期方法.
 * 
 * @author yinlong
 */
public abstract class XGCocos2dxActivity extends Cocos2dxActivity {
    private static boolean hasChannel = true;
    static {
        try {
            Class.forName("com.seasun.xgsdk.XGSDKImpl");
        } catch (ClassNotFoundException e) {
            hasChannel = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (hasChannel) {
            // 初始化XGSDK
            PluginWrapper.init(this);
            PluginWrapper.setGLSurfaceView(Cocos2dxGLSurfaceView.getInstance());

            // 接入生命周期
            XGSDKWrapper.getImpl().onCreate(savedInstanceState);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (hasChannel)
            XGSDKWrapper.getImpl().onStart("", "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hasChannel)
            XGSDKWrapper.getImpl().onResume("");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (hasChannel)
            XGSDKWrapper.getImpl().onPause("");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (hasChannel)
            XGSDKWrapper.getImpl().onStop("");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (hasChannel)
            XGSDKWrapper.getImpl().onRestart("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (hasChannel)
            XGSDKWrapper.getImpl().onDestroy("");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (hasChannel)
            XGSDKWrapper.getImpl().onNewIntent(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (hasChannel)
            XGSDKWrapper.getImpl().onActivityResult(requestCode, resultCode,
                    data);
    }

}
