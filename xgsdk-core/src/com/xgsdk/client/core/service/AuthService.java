
package com.xgsdk.client.core.service;

import com.xgsdk.client.core.XGInfo;
import com.xgsdk.client.core.http.HttpUtils;
import com.xgsdk.client.core.utils.XGLog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class AuthService extends BaseService {

    public static String genAuthInfo(Context context, String token, String uId,
            String name) throws Exception {
        String appId = XGInfo.getXGAppId(context);
        String channelId = XGInfo.getChannelId();
        String appKey = XGInfo.getXGAppKey(context);
        List<NameValuePair> requestParams = new ArrayList<NameValuePair>();
        requestParams.add(new BasicNameValuePair("appId", appId));
        requestParams.add(new BasicNameValuePair("channelId", channelId));
        requestParams.add(new BasicNameValuePair("deviceId", XGInfo
                .getXGDeviceId(context)));
        requestParams.add(new BasicNameValuePair("ts", ts()));
        if (!TextUtils.isEmpty(token)) {
            requestParams.add(new BasicNameValuePair("authToken", token));
        }
        if (!TextUtils.isEmpty(uId)) {
            requestParams.add(new BasicNameValuePair("uId", uId));
        }
        if (!TextUtils.isEmpty(name)) {
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
        String sign = sign(strSign.toString() + appId + appKey, appKey);
        XGLog.d("before sign:" + strSign.toString());
        XGLog.d("after sign:" + sign);
        JSONObject jsonAuth = new JSONObject();
        for (NameValuePair pair : requestParams) {
            jsonAuth.put(pair.getName(), pair.getValue());
        }
        jsonAuth.put("sign", sign);
        return new String(Base64.encode(jsonAuth.toString().getBytes(),
                Base64.NO_WRAP));
    }

    public static Result sessionAuthInThread(final Activity activity,
            final String authInfo) throws Exception {
        Callable<Result> callable = new Callable<Result>() {
            public Result call() throws Exception {
                return AuthService.sessionAuth(activity, authInfo);
            }
        };
        FutureTask<Result> future = new FutureTask<Result>(callable);
        Thread thread = new Thread(future);
        thread.start();
        thread.join(THREAD_JOIN_TIME_OUT);
        return future.get();
    }

    public static Result sessionAuth(Activity activity, final String authInfo)
            throws Exception {
        List<NameValuePair> requestParams = generateBasicRequestParams(
                activity, INTERFACE_TYPE_VERIFY_SESSION);
        requestParams.add(new BasicNameValuePair("authInfo", authInfo));
        String requestContent = generateSignRequestContent(activity,
                requestParams);
        String ret = HttpUtils.doGetInThread(XGInfo.getXGAuthUrl(activity)
                + ACCOUNT_VERIFY_SESSION_URI + XGInfo.getXGAppId(activity)
                + "?" + requestContent);
        return Result.parse(ret);
    }

}
