
package com.xgsdk.client.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.unity3d.player.UnityPlayer;
import com.xgsdk.client.core.http.HttpUtils;
import com.xgsdk.client.core.util.MD5Util;
import com.xgsdk.client.core.util.XGLogger;
import com.xgsdk.client.util.ProductConfig;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Base64;

@SuppressLint("NewApi")
public class AuthService {

    private static final int THREAD_JOIN_TIME_OUT = 30000;

    /**
     * 生成鉴权信息
     * 
     * @param appId xgsdk分配给游戏appid
     * @param appKey xgsdk分配给游戏的appkey
     * @param channleId xgsdk分配给游戏的channleid
     * @param token 登录返回的token
     * @param uId 登录返回的uid,没有填""
     * @param name 登录返回的name,没有填""
     * @return
     * @throws Exception
     */
    public static String genAuthInfo(String appId, String appKey,
            String channleId, String token, String uId, String name)
            throws Exception {
        List<NameValuePair> requestParams = new ArrayList<NameValuePair>();
        requestParams.add(new BasicNameValuePair("appId", appId));
        requestParams.add(new BasicNameValuePair("channelId", channleId));
        if (null != token && !token.isEmpty()) {
            requestParams.add(new BasicNameValuePair("authToken", token));
        }
        if (null != uId && !uId.isEmpty()) {
            requestParams.add(new BasicNameValuePair("uId", uId));
        }
        if (null != name && !name.isEmpty()) {
            requestParams.add(new BasicNameValuePair("name", name));
        }
        Collections.sort(requestParams, new Comparator<NameValuePair>() {
            @Override
            public int compare(NameValuePair lhs, NameValuePair rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        StringBuilder strSign = new StringBuilder();
        for (int i = 0; i < requestParams.size(); i++) {
            NameValuePair nvPair = requestParams.get(i);
            strSign.append(nvPair.getName()).append("=")
                    .append(nvPair.getValue());
            if (i < requestParams.size() - 1) {
                strSign.append("&");
            }
        }
        String sign = MD5Util.md5(strSign.toString() + appId + appKey);
        XGLogger.d("before sign:" + strSign.toString());
        XGLogger.d("after sign:" + sign);
        JSONObject jsonAuth = new JSONObject();
        for (NameValuePair pair : requestParams) {
            jsonAuth.put(pair.getName(), pair.getValue());
        }
        jsonAuth.put("sign", sign);
        return new String(Base64.encode(jsonAuth.toString().getBytes(),
                Base64.NO_WRAP));
    }

    public static String sessionAuthInThread(final Activity activity,
            final String authInfo) throws Exception {
        Callable<String> callable = new Callable<String>() {
            public String call() throws Exception {
                return AuthService.sessionAuth(activity, authInfo);
            }
        };
        FutureTask<String> future = new FutureTask<String>(callable);
        Thread thread = new Thread(future);
        thread.start();
        thread.join(THREAD_JOIN_TIME_OUT);
        return future.get();
    }

    public static String sessionAuth(Activity activity, final String authInfo)
            throws Exception {
        return HttpUtils.doGetInThread(ProductConfig.getAuthUrl(activity)
                + ProductConfig.ACCOUNT_VERIFY_SESSION_URI + "?authInfo="
                + authInfo);
    }

    public static void showDialog(final Activity activity, final String title,
            final String msg) {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                dialog.setTitle(title);
                dialog.setMessage(msg);
                dialog.setPositiveButton("确认", null);
                dialog.show();
            }
        });

    }

    /**
     * @param authInfo
     */
    public static String verifySession(Activity activity, String authInfo) {
        String sessionAuth = "";
        try {
            sessionAuth = sessionAuth(activity, authInfo);
            showDialog(activity, "登录结果", "登录成功,会话校验结果是" + sessionAuth);
            XGLogger.i("verify session success:{0}", sessionAuth);
        } catch (Exception e) {
            XGLogger.e("verify session error:", e);
        }
        return sessionAuth;
    }

    /**
     * @param authInfo
     */
    public static void verifyUnity3dSession(String authInfo) {
        verifySession(UnityPlayer.currentActivity, authInfo);
    }
}
