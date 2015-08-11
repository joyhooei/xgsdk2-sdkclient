
package com.xgsdk.client.core.service;

import com.xgsdk.client.core.XGInfo;
import com.xgsdk.client.core.http.HttpUtils;
import com.xgsdk.client.core.http.IHttpExecuteCallback;
import com.xgsdk.client.core.utils.XGLog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class PayService extends BaseService {

    public static String orderId = "";

    public static void createOrder(final Activity activity, final String uId,
            final String productId, final String productName,
            final String productDesc, final String productCount,
            final String appGoodsUnit, final String productTotalPrice,
            final String originalPrice, final String serverId,
            final String zoneId, final String roleId, final String roleName,
            final String currencyName, final String ext,
            final String gameOrderId, final String notifyUrl,
            final String channelAppId, final ICallback callback)
            throws Exception {
        // 排序签名
        final StringBuilder getUrl = generateRequestUrl(activity,
                PAY_NEW_ORDER_URI, INTERFACE_TYPE_CREATE_ORDER, null, uId,
                productId, productName, productDesc, productCount,
                appGoodsUnit, productTotalPrice, originalPrice, serverId,
                zoneId, roleId, roleName, currencyName, ext, gameOrderId,
                notifyUrl, channelAppId);
        HttpUtils.executeHttpGet(getUrl.toString(), new IHttpExecuteCallback() {

            @Override
            public void callback(int code, String data) {
                // String resp = HttpUtils.executeHttpGet(getUrl.toString());
                // 返回结果为空
                if (TextUtils.isEmpty(data)) {
                    throw new RuntimeException("request:" + getUrl.toString()
                            + ",response is null.");

                }
                // 发送请求
                Result result = Result.parse(data);
                if (result == null) {
                    // 生成订单失败
                    // throw new RuntimeException("request:" + getUrl.toString()
                    // + ",result is null.");
                    callback.callback(Result.create(), data);
                } else if (callback != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result.getData());
                        String orderId = jsonObject.getString("orderId");
                        result.setOrderId(orderId);
                        callback.callback(result, data);
                    } catch (JSONException e) {
                        callback.callback(result, data);
                    }

                }
            }
        });

    }

    public static void updateOrder(final Activity activity,
            final String orderId, final String uId, final String productId,
            final String productName, final String productDesc,
            final String productCount, final String appGoodsUnit,
            final String productTotalPrice, final String originalPrice,
            final String serverId, final String zoneId, final String roleId,
            final String roleName, final String currencyName, final String ext,
            final String gameOrderId, final String notifyUrl,
            final ICallback callback) throws Exception {
        PayService.orderId = orderId;
        final String getUrl = generateRequestUrl(activity,
                PAY_UPDATE_ORDER_URI, INTERFACE_TYPE_UPDATE_ORDER, orderId,
                uId, productId, productName, productDesc, productCount,
                appGoodsUnit, productTotalPrice, originalPrice, serverId,
                zoneId, roleId, roleName, currencyName, ext, gameOrderId,
                notifyUrl, null).toString();
        // String resp = HttpUtils.executeHttpGet(getUrl.toString());
        HttpUtils.executeHttpGet(getUrl, new IHttpExecuteCallback() {

            @Override
            public void callback(int code, String data) {
                // 返回结果为空
                if (TextUtils.isEmpty(data)) {
                    // 更新订单失败
                    throw new RuntimeException("request:" + getUrl.toString()
                            + ",response is null.");
                }
                // 发送请求
                Result result = Result.parse(data);
                // 返回结果为空
                if (result == null) {
                    // 生成订单失败
                    throw new RuntimeException("request:" + getUrl.toString()
                            + ",response is null.");
                }
                if (!TextUtils.equals(Result.CODE_SUCCESS, result.getCode())) {
                    throw new RuntimeException("response exception:"
                            + result.getMsg());
                }
                if (callback != null) {
                    callback.callback(result, data);
                }
            }
        });

    }

    private static StringBuilder generateRequestUrl(final Activity activity,
            final String uri, final String interfacetype, final String orderId,
            final String sdkUid, final String appGoodsId,
            final String appGoodsName, final String appGoodsDesc,
            final String appGoodsAmount, final String appGoodsUnit,
            final String totalPrice, final String originalPrice,
            final String serverId, final String zoneId, final String roleId,
            final String roleName, final String currencyName,
            final String custom, final String gameTradeNo,
            final String gameCallbackUrl, final String channelAppId)
            throws Exception {
        List<NameValuePair> requestParams = generateBasicRequestParams(
                activity, interfacetype);
        if (!TextUtils.isEmpty(channelAppId)) {
            requestParams.add(new BasicNameValuePair("channelAppId",
                    channelAppId));
        }
        if (!TextUtils.isEmpty(orderId)) {
            requestParams.add(new BasicNameValuePair("orderId", orderId));
        }
        String xgAppId = XGInfo.getXGAppId(activity);
        if (!TextUtils.isEmpty(sdkUid)) {
            requestParams.add(new BasicNameValuePair("sdkUid", sdkUid));
        }
        if (!TextUtils.isEmpty(totalPrice)) {
            requestParams.add(new BasicNameValuePair("totalPrice", totalPrice));

        }
        if (!TextUtils.isEmpty(originalPrice)) {
            requestParams.add(new BasicNameValuePair("originalPrice",
                    originalPrice));
        }
        if (!TextUtils.isEmpty(appGoodsAmount)) {
            requestParams.add(new BasicNameValuePair("appGoodsAmount",
                    appGoodsAmount));
        }
        // if (!TextUtils.isEmpty(appGoodsUnit)) {
        // requestParams.add(new BasicNameValuePair("appGoodsUnit",
        // appGoodsUnit));
        // }
        if (!TextUtils.isEmpty(appGoodsId)) {
            requestParams.add(new BasicNameValuePair("appGoodsId", appGoodsId));
        }
        if (!TextUtils.isEmpty(appGoodsName)) {
            requestParams.add(new BasicNameValuePair("appGoodsName",
                    appGoodsName));
        }
        if (!TextUtils.isEmpty(appGoodsDesc)) {
            requestParams.add(new BasicNameValuePair("appGoodsDesc",
                    appGoodsDesc));
        }
        if (!TextUtils.isEmpty(serverId)) {
            requestParams.add(new BasicNameValuePair("serverId", serverId));
        }
        if (!TextUtils.isEmpty(zoneId)) {
            requestParams.add(new BasicNameValuePair("zoneId", zoneId));
        }
        if (!TextUtils.isEmpty(roleId)) {
            requestParams.add(new BasicNameValuePair("roleId", roleId));
        }

        if (!TextUtils.isEmpty(roleName)) {
            requestParams.add(new BasicNameValuePair("roleName", roleName));
        }
        if (!TextUtils.isEmpty(currencyName)) {
            requestParams.add(new BasicNameValuePair("currencyName",
                    currencyName));
        }
        if (!TextUtils.isEmpty(custom)) {
            requestParams.add(new BasicNameValuePair("custom", custom));
        }
        if (!TextUtils.isEmpty(gameTradeNo)) {
            requestParams
                    .add(new BasicNameValuePair("gameTradeNo", gameTradeNo));
        }
        if (!TextUtils.isEmpty(gameCallbackUrl)) {
            requestParams.add(new BasicNameValuePair("gameCallbackUrl",
                    gameCallbackUrl));
        }
        String requestContent = generateSignRequestContent(activity,
                requestParams);
        // 生成请求
        StringBuilder getUrl = new StringBuilder();
        getUrl.append(XGInfo.getXGRechargeUrl(activity)).append(uri)
                .append("/").append(XGInfo.getChannelId()).append("/")
                .append(xgAppId).append("?");
        getUrl.append(requestContent);
        return getUrl;

    }

    /**
     * 通知服务端取消订单
     * 
     * @param orderId
     * @return
     */
    public static void cancelOrder(Activity activity, final String orderId,
            final ICallback callback) throws Exception {
        final StringBuilder getUrl = generateRequestUrl(activity,
                PAY_CANCEL_ORDER_URI, INTERFACE_TYPE_CANCEL_ORDER, orderId,
                null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null);
        // 发送请求
        // String resp = HttpUtils.executeHttpGet(getUrl.toString());
        HttpUtils.executeHttpGet(getUrl.toString(), new IHttpExecuteCallback() {

            @Override
            public void callback(int code, String data) {
                // 返回结果为空
                if (TextUtils.isEmpty(data)) {
                    // 取消订单失败
                    throw new RuntimeException("request:" + getUrl.toString()
                            + ",response is null.");
                }
                Result result = Result.parse(data);
                if (result == null) {
                    throw new RuntimeException(
                            "cancel order .parse response error:" + data);
                }
                if (!TextUtils.equals(Result.CODE_SUCCESS, result.getCode())) {
                    throw new RuntimeException("cancel order error: "
                            + result.getMsg());
                }
                if (callback != null) {
                    callback.callback(result, data);
                }
            }
        });

    }

    public static void testChannelNotify(Activity activity, String orderId,
            final ICallback callback) throws Exception {
        List<NameValuePair> requestParams = generateBasicRequestParams(
                activity, INTERFACE_TYPE_TESTCHANNEL_NOTIFY);
        requestParams.add(new BasicNameValuePair("orderId", orderId));
        String requestContent = generateSignRequestContent(activity,
                requestParams);
        // 生成请求
        final StringBuilder getUrl = new StringBuilder();
        getUrl.append(XGInfo.getXGRechargeUrl(activity))
                .append(PAY_TESTCHANNEL_NOTIFY).append("/")
                .append(XGInfo.getXGAppId(activity)).append("?");
        getUrl.append(requestContent);
        // 发送请求
        // String resp = HttpUtils.executeHttpGet(getUrl.toString());
        HttpUtils.executeHttpGet(getUrl.toString(), new IHttpExecuteCallback() {

            @Override
            public void callback(int code, String data) {// 返回结果为空
                if (TextUtils.isEmpty(data)) {
                    // 失败
                    throw new RuntimeException("request:" + getUrl.toString()
                            + ",response is null.");
                }
                Result result = Result.parse(data);
                if (result == null) {
                    throw new RuntimeException(
                            "testChannelNotify order .parse response error:"
                                    + data);
                }
                if (!TextUtils.equals(Result.CODE_SUCCESS, result.getCode())) {

                    throw new RuntimeException("testChannelNotify failed,resp:"
                            + data);
                }
                XGLog.d("testChannelNotify order ," + result);
                if (callback != null) {
                    callback.callback(result, data);
                }
            }
        });

    }

    public static void queryOrderStatus(Activity activity, String orderId,
            final ICallback callback) throws Exception {
        List<NameValuePair> requestParams = new ArrayList<NameValuePair>();
        String appId = XGInfo.getXGAppId(activity);
        if (!TextUtils.isEmpty(appId)) {
            requestParams.add(new BasicNameValuePair("xgAppId", appId));
        }
        String channelId = XGInfo.getChannelId();
        if (!TextUtils.isEmpty(channelId)) {
            requestParams.add(new BasicNameValuePair("channelId", channelId));
        }
        requestParams.add(new BasicNameValuePair("ts", ts()));
        String planId = XGInfo.getXGPlanId(activity);
        if (!TextUtils.isEmpty(planId)) {
            requestParams.add(new BasicNameValuePair("planId", planId));
        }
        String deviceId = XGInfo.getXGDeviceId(activity);
        if (!TextUtils.isEmpty(deviceId)) {
            requestParams.add(new BasicNameValuePair("deviceId", deviceId));
        }
        requestParams.add(new BasicNameValuePair("orderId", orderId));
        requestParams.add(new BasicNameValuePair("type",
                INTERFACE_TYPE_QUERY_ORDER_STATUS));
        String requestContent = generateSignRequestContent(activity,
                requestParams);
        // 生成请求
        final String url = new StringBuilder()
                .append(XGInfo.getXGRechargeUrl(activity))
                .append(PAY_QUERY_ORDER_STATUS).append("/")
                .append(XGInfo.getXGAppId(activity)).append("?")
                .append(requestContent).toString();
        // 发送请求
        // Result result = Result
        // .parse(HttpUtils.executeHttpGet(getUrl.toString()));
        HttpUtils.executeHttpGet(url.toString(), new IHttpExecuteCallback() {

            @Override
            public void callback(int code, String data) {
                // 返回结果为空
                if (data == null) {
                    // 失败
                    throw new RuntimeException("request:" + url
                            + ",response is null.");
                }
                Result result = Result.parse(data);
                if (!TextUtils.equals(Result.CODE_SUCCESS, result.getCode())) {
                    throw new RuntimeException(
                            "queryOrderStatus failed,request:" + url.toString());
                }
                if (callback != null) {
                    callback.callback(result, data);
                }
            }
        });

    }

}
