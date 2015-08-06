
package com.xgsdk.client.api.statistics;

import com.xgsdk.client.api.callback.ExitCallBack;
import com.xgsdk.client.api.callback.PayCallBack;
import com.xgsdk.client.api.entity.GameServerInfo;
import com.xgsdk.client.api.entity.PayInfo;
import com.xgsdk.client.api.entity.RoleInfo;
import com.xgsdk.client.api.entity.XGUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.Map;

public class Statistics {

    private static final String DATA_CLASS = "com.xgsdk.data.XGDATA";

    public static void onCreate(Activity activity) {
    }

    public static void onResume(Activity activity) {
    }

    public static void onStart(Activity activity) {
    }

    public static void onRestart(Activity activity) {
    }

    public static void onPause(Activity activity) {
    }

    public static void onStop(Activity activity) {
    }

    public static void onDestory(Activity activity) {
    }

    public static void onActivityResult(Activity activity, int requestCode,
            int resultCode, Intent data) {
    }

    public static void onNewIntent(Activity activity, Intent intent) {
    }

    public static void init(Activity activity) {
    }

    public static void login(Activity activity, String customParams) {
    }

    public static void logout(Activity activity, String customParams) {
    }

    public static void pay(Activity activity, PayInfo payInfo,
            PayCallBack payCallBack) {
    }

    public static void switchAccount(Activity activity, String customParams) {
    }

    public static void exit(Activity activity, ExitCallBack exitCallBack,
            String customParams) {
    }

    public static void openUserCenter(Activity activity) {
    }

    public static void onCreateRole(Activity activity, RoleInfo role) {
    }

    public static void onEnterGame(Activity activity, XGUser user,
            RoleInfo role, GameServerInfo server) {
    }

    public static void onRoleLevelup(Activity activity, XGUser user,
            RoleInfo role, GameServerInfo server) {
    }

    public static void onApplicationCreate(Context context) {
    }

    public static void onApplicationAttachBaseContext(Context context) {
    }

    public static void onEvent(Activity activity, XGUser user,
            RoleInfo roleInfo, GameServerInfo serverInfo, String eventId,
            String eventDesc, int eventVal, Map<String, Object> eventBody,
            Map<String, Object> customParams) {
    }

    public static void onMissionBegin(Activity activity, XGUser user,
            RoleInfo roleInfo, GameServerInfo serverInfo, String missionName,
            Map<String, Object> customParams) {
        onRoleMission(activity, user, roleInfo, serverInfo, missionName,
                "enter", customParams);
    }

    public static void onMissionSuccess(Activity activity, XGUser user,
            RoleInfo roleInfo, GameServerInfo serverInfo, String missionName,
            Map<String, Object> customParams) {
        onRoleMission(activity, user, roleInfo, serverInfo, missionName,
                "success", customParams);
    }

    public static void onMissionFail(Activity activity, XGUser user,
            RoleInfo roleInfo, GameServerInfo serverInfo, String missionName,
            Map<String, Object> customParams) {
        onRoleMission(activity, user, roleInfo, serverInfo, missionName,
                "failed", customParams);
    }

    private static void onRoleMission(Activity activity, XGUser user,
            RoleInfo roleInfo, GameServerInfo serverInfo, String missionName,
            String missionFlag, Map<String, Object> customParams) {

    }

}
