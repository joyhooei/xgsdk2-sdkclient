
package com.xgsdk.client.api.cocos2dx;

import com.xgsdk.client.api.XGSDK;

import org.cocos2dx.lib.Cocos2dxActivity;

import android.content.Intent;
import android.os.Bundle;

public class XGCocos2dxActivity extends Cocos2dxActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XGSDK.getInstance().onCreate(this);
        XGSDKCocos2dxWrapper.getInstance().init(this);// 必须使用XGSDKCocos2dxWrapper的init，将Activity赋值入接口
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
        XGSDK.getInstance().onDestory(this);
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
