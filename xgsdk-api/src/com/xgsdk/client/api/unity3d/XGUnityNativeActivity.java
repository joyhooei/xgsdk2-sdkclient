
package com.xgsdk.client.api.unity3d;

import com.unity3d.player.UnityPlayerNativeActivity;
import com.xgsdk.client.api.XGSDK;

import android.content.Intent;
import android.os.Bundle;

public class XGUnityNativeActivity extends UnityPlayerNativeActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XGSDK.getInstance().onCreate(this);
        XGSDK.getInstance().init(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        XGSDK.getInstance().onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        XGSDK.getInstance().onPause(this);
    }

    protected void onStart() {
        super.onStart();
        XGSDK.getInstance().onStart(this);
    }

    protected void onRestart() {
        super.onRestart();
        XGSDK.getInstance().onRestart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        XGSDK.getInstance().onStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        XGSDK.getInstance().onDestroy(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        XGSDK.getInstance().onNewIntent(this, intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        XGSDK.getInstance().onActivityResult(this, requestCode, resultCode,
                data);
    }
}
