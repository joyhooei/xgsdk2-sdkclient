package com.xgsdk.client.testchannel;

import android.app.Activity;

import com.xgsdk.client.api.data.AccountLoginMessage;
import com.xgsdk.client.api.data.DeviceConnectMessage;
import com.xgsdk.client.api.data.GameEventMessage;
import com.xgsdk.client.api.data.RoleLevelMessage;
import com.xgsdk.client.api.data.RoleLoginMessage;
import com.xgsdk.client.api.data.RoleMissionMessage;
import com.xgsdk.client.api.data.XGDataMonitor;

public class XGDataTester implements XGDataMonitor {

    @Override
    public void setActivity(Activity currentActivity) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDeviceConnect(DeviceConnectMessage message) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAccountLogin(AccountLoginMessage message) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onRoleLogin(RoleLoginMessage message) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onRoleLevelChange(RoleLevelMessage message) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onRoleMission(RoleMissionMessage message) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onEvent(GameEventMessage message) {
        // TODO Auto-generated method stub

    }

}
