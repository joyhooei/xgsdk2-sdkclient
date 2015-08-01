
package com.xgsdk.client.core.service;

import com.xgsdk.client.core.XGInfo;
import com.xgsdk.client.core.http.HttpUtils;
import com.xgsdk.client.core.utils.XGLog;
//import com.xgsdk.client.util.ProductConfig;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.text.TextUtils;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class PayService extends BaseService {

    // 单独线程运行方式
    public static String createOrderInThread(final Activity activity,
            final String uId, final String productId, final String productName,
            final String productDec, final String amount,
            final String totalPrice, final String serverId,
            final String zoneId, final String roleId, final String roleName,
            final String currencyName, final String payExt,
            final String gameOrderId, final String notifyUrl) throws Exception {

        Callable<String> callable = new Callable<String>() {
            public String call() throws Exception {
                return PayService.createOrder(activity, uId, productId,
                        productName, productDec, amount, totalPrice, serverId,
                        zoneId, roleId, roleName, currencyName, payExt,
                        gameOrderId, notifyUrl);
            }
        };
        FutureTask<String> future = new FutureTask<String>(callable);
        Thread thread = new Thread(future);
        thread.start();
        thread.join(THREAD_JOIN_TIME_OUT);
        return future.get();
    }

    // 单独线程运行方式
    public static String createOrderInThreadForOriginal(
            final Activity activity, final String uId, final String productId,
            final String productName, final String productDec,
            final String amount, final String totalPrice,
            final String serverId, final String zoneId, final String roleId,
            final String roleName, final String currencyName,
            final String payExt, final String gameOrderId,
            final String notifyUrl) throws Exception {
        Callable<String> callable = new Callable<String>() {
            public String call() throws Exception {
                return PayService.createOrderForOriginal(activity, uId,
                        productId, productName, productDec, amount, totalPrice,
                        serverId, zoneId, roleId, roleName, currencyName,
                        payExt, gameOrderId, notifyUrl);
            }
        };
        FutureTask<String> future = new FutureTask<String>(callable);
        Thread thread = new Thread(future);
        thread.start();
        thread.join(THREAD_JOIN_TIME_OUT);
        orderId = future.get();
        return orderId;
    }

    public static String orderId = "";

    // 单独线程运行方式
    public static void updateOrderInThread(final Activity activity,
            final String orderId, final String uId, final String productId,
            final String productName, final String productDec,
            final String amount, final String totalPrice,
            final String serverId, final String zoneId, final String roleId,
            final String roleName, final String currencyName,
            final String payExt, final String gameOrderId,
            final String notifyUrl) throws Exception {
        PayService.orderId = orderId;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PayService.updateOrder(activity, orderId, uId, productId,
                            productName, productDec, amount, totalPrice,
                            serverId, zoneId, roleId, roleName, currencyName,
                            payExt, gameOrderId, notifyUrl);
                } catch (Exception ex) {
                    XGLog.e(ex.getMessage(), ex);
                }
            }
        });
        thread.start();
        thread.join(THREAD_JOIN_TIME_OUT);
    }

    // 单独线程运行方式
    public static void cancelOrderInThread(final Activity activity,
            final String orderId) throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PayService.cancelOrder(activity, orderId);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    XGLog.e(ex.getMessage(), ex);
                }
            }
        });
        thread.start();
        thread.join(THREAD_JOIN_TIME_OUT);
    }

    public static String createOrder(final Activity activity, final String uId,
            final String productId, final String productName,
            final String productDec, final String amount,
            final String totalPrice, final String serverId,
            final String zoneId, final String roleId, final String roleName,
            final String currencyName, final String payExt,
            final String gameOrderId, final String notifyUrl) throws Exception {
        // 发送请求
        String result = createOrderForOriginal(activity, uId, productId,
                productName, productDec, amount, totalPrice, serverId, zoneId,
                roleId, roleName, currencyName, payExt, gameOrderId, notifyUrl);
        JSONObject jsonResult = new JSONObject(result);
        if (!"1".equals(jsonResult.getString("code"))) {
            throw new Exception("response exception:"
                    + jsonResult.getString("msg"));
        }
        JSONObject jsonData = jsonResult.getJSONObject("data");
        if (null == jsonData) {
            throw new Exception("response exception:"
                    + jsonResult.getString("msg"));
        }
        PayService.orderId = jsonData.getString("orderId");
        return jsonData.getString("orderId");
    }

    public static String createOrderForOriginal(final Activity activity,
            final String uId, final String productId, final String productName,
            final String productDec, final String amount,
            final String totalPrice, final String serverId,
            final String zoneId, final String roleId, final String roleName,
            final String currencyName, final String payExt,
            final String gameOrderId, final String notifyUrl) throws Exception {
        // 排序签名
        StringBuilder getUrl = generateRequestUrl(activity, PAY_NEW_ORDER_URI,
                INTERFACE_TYPE_CREATE_ORDER, null, uId, productId, productName,
                productDec, amount, totalPrice, serverId, zoneId, roleId,
                roleName, currencyName, payExt, gameOrderId, notifyUrl);
        // 发送请求
        String result = HttpUtils.executeHttpGet(getUrl.toString());
        // 返回结果为空
        if (TextUtils.isEmpty(result)) {
            // 生成订单失败
            throw new Exception("request:" + getUrl.toString()
                    + ",response is null.");
        }
        return result;
    }

    private static void updateOrder(final Activity activity,
            final String orderId, final String uId, final String productId,
            final String productName, final String productDec,
            final String amount, final String totalPrice,
            final String serverId, final String zoneId, final String roleId,
            final String roleName, final String currencyName,
            final String payExt, final String gameOrderId,
            final String notifyUrl) throws Exception {
        PayService.orderId = orderId;
        StringBuilder getUrl = generateRequestUrl(activity,
                PAY_UPDATE_ORDER_URI, INTERFACE_TYPE_UPDATE_ORDER, orderId,
                uId, productId, productName, productDec, amount, totalPrice,
                serverId, zoneId, roleId, roleName, currencyName, payExt,
                gameOrderId, notifyUrl);
        // 发送请求
        String result = HttpUtils.executeHttpGet(getUrl.toString());
        // 返回结果为空
        if (TextUtils.isEmpty(result)) {
            // 生成订单失败
            throw new Exception("request:" + getUrl.toString()
                    + ",response is null.");
        }
        JSONObject jsonResult = new JSONObject(result);
        if ("1".equals(jsonResult.getString("code"))) {
            return;
        } else {
            throw new Exception("response exception:"
                    + jsonResult.getString("msg"));
        }
    }

    private static StringBuilder generateRequestUrl(final Activity activity,
            final String uri, final String interfacetype, final String orderId,
            final String uId, final String productId, final String productName,
            final String productDec, final String amount,
            final String totalPrice, final String serverId,
            final String zoneId, final String roleId, final String roleName,
            final String currencyName, final String payExt,
            final String gameOrderId, final String notifyUrl) throws Exception {
        List<NameValuePair> requestParams = generateBasicRequestParams(
                activity, interfacetype);
        if (!TextUtils.isEmpty(orderId)) {
            requestParams.add(new BasicNameValuePair("orderId", orderId));
        }
        String appId = XGInfo.getXGAppId(activity);
        if (!TextUtils.isEmpty(uId)) {
            requestParams.add(new BasicNameValuePair("sdkUid", uId));
        }
        if (!TextUtils.isEmpty(totalPrice)) {
            requestParams.add(new BasicNameValuePair("totalPrice", totalPrice));
            requestParams.add(new BasicNameValuePair("originalPrice",
                    totalPrice));
        }
        if (!TextUtils.isEmpty(amount)) {
            requestParams.add(new BasicNameValuePair("appGoodsAmount", amount));
        }
        if (!TextUtils.isEmpty(productId)) {
            requestParams.add(new BasicNameValuePair("appGoodsId", productId));
        }
        if (!TextUtils.isEmpty(productName)) {
            requestParams.add(new BasicNameValuePair("appGoodsName",
                    productName));
        }
        if (!TextUtils.isEmpty(productDec)) {
            requestParams
                    .add(new BasicNameValuePair("appGoodsDesc", productDec));
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
        if (!TextUtils.isEmpty(payExt)) {
            requestParams.add(new BasicNameValuePair("custom", payExt));
        }
        if (!TextUtils.isEmpty(gameOrderId)) {
            requestParams
                    .add(new BasicNameValuePair("gameTradeNo", gameOrderId));
        }
        if (!TextUtils.isEmpty(notifyUrl)) {
            requestParams.add(new BasicNameValuePair("gameCallbackUrl",
                    notifyUrl));
        }
        String requestContent = generateSignRequestContent(activity,
                requestParams);
        // 生成请求
        StringBuilder getUrl = new StringBuilder();
        getUrl.append(XGInfo.getXGRechargeUrl(activity)).append(uri)
                .append("/").append(XGInfo.getChannelId()).append("/")
                .append(appId).append("?");
        getUrl.append(requestContent);
        return getUrl;

    }

    /**
     * 通知服务端取消订单
     * 
     * @param orderId
     * @return
     */
    public static void cancelOrder(Activity activity, final String orderId)
            throws Exception {
        StringBuilder getUrl = generateRequestUrl(activity,
                PAY_CANCEL_ORDER_URI, INTERFACE_TYPE_CANCEL_ORDER, orderId,
                null, null, null, null, null, null, null, null, null, null,
                null, null, null, null);
        // 发送请求
        String result = HttpUtils.executeHttpGet(getUrl.toString());
        // 返回结果为空
        if (TextUtils.isEmpty(result)) {
            // 生成订单失败
            throw new Exception("request:" + getUrl.toString()
                    + ",response is null.");
        }
        JSONObject jsonResult = new JSONObject(result);
        if ("1".equals(jsonResult.getString("code"))) {
            return;
        } else {
            throw new Exception(jsonResult.getString("data"));
        }
    }

    public static void testChannelNotify(Activity activity, String orderId)
            throws Exception {
        List<NameValuePair> requestParams = generateBasicRequestParams(
                activity, INTERFACE_TYPE_TESTCHANNEL_NOTIFY);
        requestParams.add(new BasicNameValuePair("orderId", orderId));
        String requestContent = generateSignRequestContent(activity,
                requestParams);
        // 生成请求
        StringBuilder getUrl = new StringBuilder();
        getUrl.append(XGInfo.getXGRechargeUrl(activity))
                .append(PAY_TESTCHANNEL_NOTIFY).append("/")
                .append(XGInfo.getXGAppId(activity)).append("?");
        getUrl.append(requestContent);
        // 发送请求
        String result = HttpUtils.executeHttpGet(getUrl.toString());
        // 返回结果为空
        if (TextUtils.isEmpty(result)) {
            // 失败
            throw new Exception("request:" + getUrl.toString()
                    + ",response is null.");
        }
        if (!("0".equals(result))) {
            throw new Exception("testChannelNotify failed,request:"
                    + getUrl.toString());
        }
    }

    public static String queryOrderStatus(Activity activity, String orderId)
            throws Exception {
        List<NameValuePair> requestParams = generateBasicRequestParams(
                activity, INTERFACE_TYPE_QUERY_ORDER_STATUS);
        requestParams.add(new BasicNameValuePair("orderId", orderId));
        String requestContent = generateSignRequestContent(activity,
                requestParams);
        // 生成请求
        StringBuilder getUrl = new StringBuilder();
        getUrl.append(XGInfo.getXGRechargeUrl(activity))
                .append(PAY_TESTCHANNEL_NOTIFY).append("/")
                .append(XGInfo.getXGAppId(activity)).append("?");
        getUrl.append(requestContent);
        // 发送请求
        String result = HttpUtils.executeHttpGet(getUrl.toString());
        // 返回结果为空
        if (TextUtils.isEmpty(result)) {
            // 失败
            throw new Exception("request:" + getUrl.toString()
                    + ",response is null.");
        }
        if (!("0".equals(result))) {
            throw new Exception("testChannelNotify failed,request:"
                    + getUrl.toString());
        }
        return result;
    }

    // 单独线程运行方式
    public static String getResponseInThread(final String url) throws Exception {

        Callable<String> callable = new Callable<String>() {
            public String call() throws Exception {
                return PayService.getResponse(url);
            }
        };
        FutureTask<String> future = new FutureTask<String>(callable);
        Thread thread = new Thread(future);
        thread.start();
        thread.join(THREAD_JOIN_TIME_OUT);
        return future.get();
    }

    public static String getResponse(String url) throws Exception {

        // 发送请求
        String result = HttpUtils.executeHttpGet(url);
        // 返回结果为空
        if (TextUtils.isEmpty(result)) {
            // 生成订单失败
            throw new Exception("request:" + url + ",response is null.");
        }

        return result;
    }

}
