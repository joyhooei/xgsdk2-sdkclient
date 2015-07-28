
package com.xgsdk.client;

import com.xgsdk.client.callback.ExitCallBack;
import com.xgsdk.client.callback.PayCallBack;
import com.xgsdk.client.callback.UserCallBack;
import com.xgsdk.client.core.util.XGLogger;
import com.xgsdk.client.entity.GameServerInfo;
import com.xgsdk.client.entity.PayInfo;
import com.xgsdk.client.entity.RoleInfo;
import com.xgsdk.client.entity.XGUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.lang.reflect.Method;
import java.util.HashMap;

class Statistics {

    private static final String METHOD_ON_CREATE = "onCreate";
    private static final String METHOD_ON_RESUME = "onResume";
    private static final String METHOD_ON_START = "onStart";
    private static final String METHOD_ON_RESTART = "onRestart";
    private static final String METHOD_ON_PAUSE = "onPause";
    private static final String METHOD_ON_STOP = "onStop";
    private static final String METHOD_ON_DESTORY = "onDestory";
    private static final String METHOD_ON_ACTIVITY_RESULT = "onActivityResult";
    private static final String METHOD_ON_NEW_INTENT = "onNewIntent";
    private static final String METHOD_INIT = "init";
    private static final String METHOD_PAY = "pay";
    private static final String METHOD_LOGIN = "login";
    private static final String METHOD_LOGOUT = "logout";
    private static final String METHOD_EXIT = "exit";
    private static final String METHOD_SWITCH_ACCOUNT = "switchAccount";
    private static final String METHOD_SET_USERCALLBACK = "setUserCallBack";
    private static final String METHOD_OPEN_USERCENTER = "openUserCenter";
    private static final String METHOD_ON_CREATE_ROLE = "onCreateRole";
    private static final String METHOD_ON_ENTER_GAME = "onEnterGame";
    private static final String METHOD_ON_ROLE_LEVELUP = "onRoleLevelup";
    private static final String METHOD_ON_VIP_LEVELUP = "onVipLevelup";
    private static final String METHOD_ON_APPLICATION_CREATE = "onApplicationCreate";
    private static final String METHOD_ON_APPLICATION_ATTACHBASECONTEXT = "onApplicationAttachBaseContext";

    private static final String PARAM_NAME_ACTIVITY = "Activity";
    private static final String PARAM_NAME_CONTEXT = "Context";
    private static final String PARAM_NAME_REQUEST_CODE = "RequestCode";
    private static final String PARAM_NAME_RESULT_CODE = "ResultCode";
    private static final String PARAM_NAME_DATA = "Data";
    private static final String PARAM_NAME_INTENT = "Intent";
    private static final String PARAM_NAME_PAYINFO = "PayInfo";
    private static final String PARAM_NAME_PAYCALLBACK = "PayCallBack";
    private static final String PARAM_NAME_USERCALLBACK = "UserCallBack";
    private static final String PARAM_NAME_ROLEINFO = "RoleInfo";
    private static final String PARAM_NAME_XGUSER = "XGUser";
    private static final String PARAM_NAME_GAMESERVERINFO = "GameServerInfo";
    private static final String PARAM_NAME_LEVEL = "Level";
    private static final String PARAM_NAME_VIPLEVEL = "VipLevel";
    private static final String PARAM_NAME_CUSTOM_PARAMS = "CustomParams";

    private static void statistics(String name, HashMap<String, Object> params) {
        XGLogger.i(name + " " + params);
        check(name, params);
    }

    private static void check(String name, HashMap<String, Object> params) {

    }

    private static HashMap<String, Object> getVariablesFromObj(Object obj) {
        if (obj == null) {
            return null;
        }
        HashMap<String, Object> map = new HashMap<String, Object>();

        Method[] methods = obj.getClass().getDeclaredMethods();
        if (methods != null) {
            for (int i = 0; i < methods.length; i++) {

                Method method = methods[i];
                String name = method.getName();
                if (name.startsWith("get")) {
                    try {
                        Object ret = method.invoke(obj, null);
                        String n = name.substring(3);
                        map.put(n, ret);
                    } catch (Exception e) {
                        XGLogger.d("", e);

                    }
                }
            }
        }
        return map;
    }

    private static void lifecycle(String name, Activity activity) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_ACTIVITY, activity);
        statistics(name, paramsMap);
    }

    static void onCreate(Activity activity) {
        lifecycle(METHOD_ON_CREATE, activity);
    }

    static void onResume(Activity activity) {
        lifecycle(METHOD_ON_RESUME, activity);
    }

    static void onStart(Activity activity) {
        lifecycle(METHOD_ON_START, activity);
    }

    static void onRestart(Activity activity) {
        lifecycle(METHOD_ON_RESTART, activity);
    }

    static void onPause(Activity activity) {
        lifecycle(METHOD_ON_PAUSE, activity);
    }

    static void onStop(Activity activity) {
        lifecycle(METHOD_ON_STOP, activity);
    }

    static void onDestory(Activity activity) {
        lifecycle(METHOD_ON_DESTORY, activity);
    }

    static void onActivityResult(Activity activity, int requestCode,
            int resultCode, Intent data) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_REQUEST_CODE, requestCode);
        paramsMap.put(PARAM_NAME_RESULT_CODE, resultCode);
        paramsMap.put(PARAM_NAME_DATA, data);
        statistics(METHOD_ON_ACTIVITY_RESULT, paramsMap);
    }

    static void onNewIntent(Activity activity, Intent intent) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_INTENT, intent);
        statistics(METHOD_ON_NEW_INTENT, paramsMap);
    }

    static void init(Activity activity) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_ACTIVITY, activity);
        statistics(METHOD_INIT, paramsMap);
    }

    static void login(Activity activity, String customParams) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_ACTIVITY, activity);
        paramsMap.put(PARAM_NAME_CUSTOM_PARAMS, customParams);
        statistics(METHOD_LOGIN, paramsMap);
    }

    static void logout(Activity activity, String customParams) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_ACTIVITY, activity);
        paramsMap.put(PARAM_NAME_CUSTOM_PARAMS, customParams);
        statistics(METHOD_LOGOUT, paramsMap);
    }

    static void pay(Activity activity, PayInfo payInfo, PayCallBack payCallBack) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_ACTIVITY, activity);
        paramsMap.put(PARAM_NAME_PAYINFO, getVariablesFromObj(payInfo));
        paramsMap.put(PARAM_NAME_PAYCALLBACK, payCallBack);
        statistics(METHOD_PAY, paramsMap);
    }

    static void switchAccount(Activity activity, String customParams) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_ACTIVITY, activity);
        paramsMap.put(PARAM_NAME_CUSTOM_PARAMS, customParams);
        statistics(METHOD_SWITCH_ACCOUNT, paramsMap);
    }

    static void setUserCallBack(UserCallBack userCallBack) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_USERCALLBACK, userCallBack);
        statistics(METHOD_SET_USERCALLBACK, paramsMap);
    }

    static void exit(Activity activity, ExitCallBack exitCallBack,
            String customParams) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_USERCALLBACK, exitCallBack);
        paramsMap.put(PARAM_NAME_CUSTOM_PARAMS, customParams);
        statistics(METHOD_EXIT, paramsMap);
    }

    static void openUserCenter(Activity activity) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_ACTIVITY, activity);
        statistics(METHOD_OPEN_USERCENTER, paramsMap);
    }

    static void onCreateRole(Activity activity, RoleInfo role) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_ROLEINFO, getVariablesFromObj(role));
        statistics(METHOD_ON_CREATE_ROLE, paramsMap);
    }

    static void onEnterGame(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_ACTIVITY, activity);
        paramsMap.put(PARAM_NAME_XGUSER, getVariablesFromObj(user));
        paramsMap.put(PARAM_NAME_ROLEINFO, getVariablesFromObj(role));
        paramsMap.put(PARAM_NAME_GAMESERVERINFO, getVariablesFromObj(server));
        statistics(METHOD_ON_ENTER_GAME, paramsMap);
    }

    static void onRoleLevelup(Activity activity, String level) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_LEVEL, level);
        statistics(METHOD_ON_ROLE_LEVELUP, paramsMap);
    }

    static void onVipLevelup(Activity activity, String vipLevel) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_VIPLEVEL, vipLevel);
        statistics(METHOD_ON_VIP_LEVELUP, paramsMap);
    }

    static void onApplicationCreate(Context context) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_CONTEXT, context);
        statistics(METHOD_ON_APPLICATION_CREATE, paramsMap);
    }

    static void onApplicationAttachBaseContext(Context context) {
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(PARAM_NAME_CONTEXT, context);
        statistics(METHOD_ON_APPLICATION_ATTACHBASECONTEXT, paramsMap);
    }

}
