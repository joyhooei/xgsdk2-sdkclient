
package com.xgsdk.client.core.service;

import com.xgsdk.client.core.SystemInfo;
import com.xgsdk.client.core.XGInfo;
import com.xgsdk.client.core.utils.SHA1Util;
import com.xgsdk.client.core.utils.XGLog;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BaseService {

    protected static final String INTERFACE_TYPE_CREATE_ORDER = "1";
    protected static final String INTERFACE_TYPE_UPDATE_ORDER = "2";
    protected static final String INTERFACE_TYPE_CANCEL_ORDER = "3";
    protected static final String INTERFACE_TYPE_TESTCHANNEL_NOTIFY = "4";
    protected static final String INTERFACE_TYPE_QUERY_ORDER_STATUS = "7";
    protected static final String INTERFACE_TYPE_VERIFY_SESSION = "9";

    protected static final int THREAD_JOIN_TIME_OUT = 30000;

    // 创建订单URI
    protected static String PAY_NEW_ORDER_URI = "/pay/create-order";
    // 更新订单URI
    protected static String PAY_UPDATE_ORDER_URI = "/pay/update-order";
    // 取消订单URI
    protected static String PAY_CANCEL_ORDER_URI = "/pay/cancel-order";
    
    protected static String PAY_TESTCHANNEL_NOTIFY = "/pay/testchannel_notify";
    
    protected static String PAY_QUERY_ORDER_STATUS = "/pay/query_order_status";
    
    protected static String ACCOUNT_VERIFY_SESSION_URI = "/account/verify-session/";

    private static final String TIME_PATTERN = "yyyyMMddHHmmss";
    private static final SimpleDateFormat sTsFormat = new SimpleDateFormat(
            TIME_PATTERN, Locale.getDefault());

    protected static String ts() {
        return sTsFormat.format(new Date(System.currentTimeMillis()));
    }

    protected static String sign(String content, String encryptKey)
            throws Exception {
        return SHA1Util.HmacSHA1EncryptByte(content, encryptKey);
    }

    protected static List<NameValuePair> generateBasicRequestParams(
            Activity activity, String interfaceType) {

        List<NameValuePair> requestParams = new ArrayList<NameValuePair>();
        String appId = XGInfo.getXGAppId(activity);
        if (!TextUtils.isEmpty(appId)) {
            requestParams.add(new BasicNameValuePair("sdkAppid", appId));
        }
        String channelId = XGInfo.getChannelId();
        if (!TextUtils.isEmpty(channelId)) {
            requestParams.add(new BasicNameValuePair("channelId", channelId));
        }
        if (!TextUtils.isEmpty(XGInfo.getXGSdkVersion())) {
            requestParams.add(new BasicNameValuePair("sdkVersion", XGInfo
                    .getXGSdkVersion()));
        }
        requestParams.add(new BasicNameValuePair("ts", ts()));
        String planId = XGInfo.getXGPlanId(activity);
        if (!TextUtils.isEmpty(planId)) {
            requestParams.add(new BasicNameValuePair("planId", planId));
        }
        String buildNumber = XGInfo.getXGBuildNumber(activity);
        if (!TextUtils.isEmpty(buildNumber)) {
            requestParams
                    .add(new BasicNameValuePair("buildNumber", buildNumber));
        }
        String deviceId = XGInfo.getXGDeviceId(activity);
        if (!TextUtils.isEmpty(deviceId)) {
            requestParams.add(new BasicNameValuePair("deviceId", deviceId));
        }
        String deviceBrand = SystemInfo.getDeviceBrand();
        if (!TextUtils.isEmpty(deviceBrand)) {
            requestParams
                    .add(new BasicNameValuePair("deviceBrand", deviceBrand));
        }
        String deviceModel = SystemInfo.getDeviceModel();
        if (!TextUtils.isEmpty(deviceModel)) {
            requestParams
                    .add(new BasicNameValuePair("deviceModel", deviceModel));
        }
        requestParams.add(new BasicNameValuePair("type", interfaceType));

        return requestParams;
    }

    protected static String generateSignRequestContent(Context context,
            List<NameValuePair> requestParams) throws Exception {
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
        String requestContent = URLEncodedUtils.format(requestParams,
                HTTP.UTF_8);
        String sign = sign(strSign.toString(), XGInfo.getXGAppKey(context));

        XGLog.d("before sign:" + strSign.toString());
        XGLog.d("after sign:" + sign);
        return requestContent + "&sign=" + sign;

    }

}
