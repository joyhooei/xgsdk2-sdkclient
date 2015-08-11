
package com.xgsdk.client.testchannel;

import com.xgsdk.client.core.data.XGDataMonitor;

import android.app.Activity;

import java.util.Map;

public class XGDataTester implements XGDataMonitor {

    public XGDataTester() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void setActivity(Activity currentActivity, Object ext) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onResume(Object ext) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPause(Object ext) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDeviceConnect(Object ext) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAccountLogin(String accountId, String accountName, Object ext) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onRoleLogin(String accountId, String accountName,
            String roleId, String roleName, String server, String serverName,
            int roleLevel, String loginFlag, Object ext) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onRoleLevelUp(String server, String accountId,
            String accountName, String roleId, String roleName, int roleLevel,
            Object ext) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onRoleMission(String server, String accountId,
            String accountName, String roleId, String roleName, int roleLevel,
            String missionName, String missionFlag, Object ext) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onEvent(String eventId, String eventDesc, int eventVal,
            String accountId, String accountName,
            Map<String, Object> eventBody, Object ext) {
        // TODO Auto-generated method stub

    }

}
