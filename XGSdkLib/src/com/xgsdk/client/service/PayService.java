
package com.xgsdk.client.service;

import com.xgsdk.client.core.http.HttpUtils;
import com.xgsdk.client.core.util.MD5Util;
import com.xgsdk.client.core.util.XGLogger;
import com.xgsdk.client.util.ProductConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;

@SuppressLint("NewApi")
public class PayService {

    private static final int THREAD_JOIN_TIME_OUT = 30000;

    // 单独线程运行方式
    public static String createOrderInThread(final Activity activity,
            final String appId, final String appKey, final String channelId,
            final String uId, final String productId, final String productName,
            final String productDec, final String amount,
            final String totalPrice, final String serverId,
            final String roleId, final String roleName,
            final String currencyName, final String payExt) throws Exception {

        Callable<String> callable = new Callable<String>() {
            public String call() throws Exception {
                return PayService.createOrder(activity, appId, appKey,
                        channelId, uId, productId, productName, productDec,
                        amount, totalPrice, serverId, roleId, roleName,
                        currencyName, payExt);
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
            final Activity activity, final String appId, final String appKey,
            final String channelId, final String uId, final String productId,
            final String productName, final String productDec,
            final String amount, final String totalPrice,
            final String serverId, final String roleId, final String roleName,
            final String currencyName, final String payExt) throws Exception {
        Callable<String> callable = new Callable<String>() {
            public String call() throws Exception {
                return PayService.createOrderForOriginal(activity, appId,
                        appKey, channelId, uId, productId, productName,
                        productDec, amount, totalPrice, serverId, roleId,
                        roleName, currencyName, payExt);
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
            final String appId, final String appKey, final String channelId,
            final String orderId, final String uId, final String productId,
            final String productName, final String productDec,
            final String amount, final String totalPrice,
            final String serverId, final String roleId, final String roleName,
            final String currencyName, final String payExt) throws Exception {
        PayService.orderId = orderId;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PayService.updateOrder(activity, appId, appKey, channelId,
                            orderId, uId, productId, productName, productDec,
                            amount, totalPrice, serverId, roleId, roleName,
                            currencyName, payExt);
                } catch (Exception ex) {
                    XGLogger.e(ex.getMessage(), ex);
                }
            }
        });
        thread.start();
        thread.join(THREAD_JOIN_TIME_OUT);
    }

    // 单独线程运行方式
    public static void cancelOrderInThread(final Activity activity,
            final String appId, final String appKey, final String orderId)
            throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PayService.cancelOrder(activity, appId, appKey, orderId);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    XGLogger.e(ex.getMessage(), ex);
                }
            }
        });
        thread.start();
        thread.join(THREAD_JOIN_TIME_OUT);
    }

    /**
     * 通知服务端生成订单
     * 
     * @param uId
     * @param productId
     * @param productName
     * @param productDec
     * @param price
     * @param amount
     * @param totalPrice
     * @param serverId
     * @param roleId
     * @param roleName
     * @param currencyName
     * @param payExt
     * @return 订单号
     * @throws Exception
     */
    public static String createOrder(final Activity activity,
            final String appId, final String appKey, final String channelId,
            final String uId, final String productId, final String productName,
            final String productDec, final String amount,
            final String totalPrice, final String serverId,
            final String roleId, final String roleName,
            final String currencyName, final String payExt) throws Exception {
        // 发送请求
        String result = createOrderForOriginal(activity, appId, appKey,
                channelId, uId, productId, productName, productDec, amount,
                totalPrice, serverId, roleId, roleName, currencyName, payExt);
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

    /**
     * 通知服务端生成订单
     * 
     * @param uId
     * @param productId
     * @param productName
     * @param productDec
     * @param price
     * @param amount
     * @param totalPrice
     * @param serverId
     * @param roleId
     * @param roleName
     * @param currencyName
     * @param payExt
     * @return 订单号
     * @throws Exception
     */
    public static String createOrderForOriginal(final Activity activity,
            final String appId, final String appKey, final String channelId,
            final String uId, final String productId, final String productName,
            final String productDec, final String amount,
            final String totalPrice, final String serverId,
            final String roleId, final String roleName,
            final String currencyName, final String payExt) throws Exception {
        // 排序签名
        List<NameValuePair> requestParams = new ArrayList<NameValuePair>();
        requestParams.add(new BasicNameValuePair("sdkAppid", appId));
        requestParams.add(new BasicNameValuePair("channelId", channelId));
        if (null != uId && !uId.isEmpty()) {
            requestParams.add(new BasicNameValuePair("sdkUid", uId));
        }
        requestParams.add(new BasicNameValuePair("totalPrice", totalPrice));
        requestParams.add(new BasicNameValuePair("originalPrice", totalPrice));
        if (null != amount && !amount.isEmpty()) {
            requestParams.add(new BasicNameValuePair("appGoodsAmount", amount));
        }
        if (null != productId && !productId.isEmpty()) {
            requestParams.add(new BasicNameValuePair("appGoodsId", productId));
        }
        if (null != productName && !productName.isEmpty()) {
            requestParams.add(new BasicNameValuePair("appGoodsName",
                    productName));
        }
        if (null != productDec && !productDec.isEmpty()) {
            requestParams
                    .add(new BasicNameValuePair("appGoodsDesc", productDec));
        }
        if (null != serverId && !serverId.isEmpty()) {
            requestParams.add(new BasicNameValuePair("serverId", serverId));
        }
        if (null != roleId && !roleId.isEmpty()) {
            requestParams.add(new BasicNameValuePair("roleId", roleId));
        }
        if (null != roleName && !roleName.isEmpty()) {
            requestParams.add(new BasicNameValuePair("roleName", roleName));
        }
        if (null != currencyName && !currencyName.isEmpty()) {
            requestParams.add(new BasicNameValuePair("currencyName",
                    currencyName));
        }
        if (null != payExt && !payExt.isEmpty()) {
            requestParams.add(new BasicNameValuePair("custom", payExt));
        }
        Collections.sort(requestParams, new Comparator<NameValuePair>() {
            @Override
            public int compare(NameValuePair lhs, NameValuePair rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        // 生成MD5签名
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
        String sign = MD5Util.md5(strSign.toString() + appId + appKey);
        XGLogger.i("before sign:" + strSign.toString() + appId + appKey);
        XGLogger.i("after sign:" + sign);
        // 生成请求
        StringBuilder getUrl = new StringBuilder();
        getUrl.append(ProductConfig.getRechargeUrl(activity))
                .append(ProductConfig.PAY_NEW_ORDER_URI).append("/")
                .append(channelId).append("/").append(appId).append("?");
        getUrl.append(requestContent);
        getUrl.append("&sign=").append(sign);
        // 发送请求
        String result = HttpUtils.executeHttpGet(getUrl.toString());
        // 返回结果为空
        if (null == result || result.isEmpty()) {
            // 生成订单失败
            throw new Exception("request:" + getUrl.toString()
                    + ",response is null.");
        }
        return result;
    }

    /**
     * 通知服务端更新订单
     * 
     * @param uId
     * @param productId
     * @param productName
     * @param productDec
     * @param price
     * @param amount
     * @param totalPrice
     * @param serverId
     * @param roleId
     * @param roleName
     * @param currencyName
     * @param payExt
     * @return
     * @throws Exception
     */
    public static void updateOrder(final Activity activity, final String appId,
            final String appKey, final String channelId, final String orderId,
            final String uId, final String productId, final String productName,
            final String productDec, final String amount,
            final String totalPrice, final String serverId,
            final String roleId, final String roleName,
            final String currencyName, final String payExt) throws Exception {
        PayService.orderId = orderId;
        List<NameValuePair> requestParams = new ArrayList<NameValuePair>();
        requestParams.add(new BasicNameValuePair("orderId", orderId));
        requestParams.add(new BasicNameValuePair("sdkAppid", appId));
        requestParams.add(new BasicNameValuePair("channelId", channelId));
        if (null != uId && !uId.isEmpty()) {
            requestParams.add(new BasicNameValuePair("sdkUid", uId));
        }
        if (null != totalPrice && !totalPrice.isEmpty()) {
            requestParams.add(new BasicNameValuePair("totalPrice", totalPrice));
            requestParams.add(new BasicNameValuePair("originalPrice",
                    totalPrice));
        }
        if (null != amount && !amount.isEmpty()) {
            requestParams.add(new BasicNameValuePair("appGoodsAmount", amount));
        }
        if (null != productId && !productId.isEmpty()) {
            requestParams.add(new BasicNameValuePair("appGoodsId", productId));
        }
        if (null != productName && !productName.isEmpty()) {
            requestParams.add(new BasicNameValuePair("appGoodsName",
                    productName));
        }
        if (null != productDec && !productDec.isEmpty()) {
            requestParams
                    .add(new BasicNameValuePair("appGoodsDesc", productDec));
        }
        if (null != serverId && !serverId.isEmpty()) {
            requestParams.add(new BasicNameValuePair("serverId", serverId));
        }
        if (null != roleId && !roleId.isEmpty()) {
            requestParams.add(new BasicNameValuePair("roleId", roleId));
        }
        if (null != roleName && !roleName.isEmpty()) {
            requestParams.add(new BasicNameValuePair("roleName", roleName));
        }
        if (null != currencyName && !currencyName.isEmpty()) {
            requestParams.add(new BasicNameValuePair("currencyName",
                    currencyName));
        }
        if (null != payExt && !payExt.isEmpty()) {
            requestParams.add(new BasicNameValuePair("custom", payExt));
        }
        Collections.sort(requestParams, new Comparator<NameValuePair>() {
            @Override
            public int compare(NameValuePair lhs, NameValuePair rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        // 生成MD5签名
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
        String sign = MD5Util.md5(strSign.toString() + appId + appKey);
        XGLogger.d("before sign:" + strSign.toString());
        XGLogger.d("after sign:" + sign);
        // 生成请求
        StringBuilder getUrl = new StringBuilder();
        getUrl.append(ProductConfig.getRechargeUrl(activity))
                .append(ProductConfig.PAY_UPDATE_ORDER_URI).append("/")
                .append(channelId).append("/").append(appId).append("?");
        getUrl.append(requestContent);
        getUrl.append("&sign=").append(sign);
        // 发送请求
        String result = HttpUtils.executeHttpGet(getUrl.toString());
        // 返回结果为空
        if (null == result || result.isEmpty()) {
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

    /**
     * 通知服务端取消订单
     * 
     * @param orderId
     * @return
     */
    public static void cancelOrder(Activity activity, final String appId,
            final String appKey, final String orderId) throws Exception {
        String strSign = "orderId=" + orderId;
        String sign = MD5Util.md5(strSign + appId + appKey);
        XGLogger.d("before sign:" + strSign);
        XGLogger.d("after sign:" + sign);
        // 生成请求
        StringBuilder getUrl = new StringBuilder();
        getUrl.append(ProductConfig.getRechargeUrl(activity))
                .append(ProductConfig.PAY_CANCEL_ORDER_URI).append("/")
                .append(ProductConfig.getChannelId(activity)).append("/")
                .append(appId).append("?");
        getUrl.append(strSign);
        getUrl.append("&sign=").append(sign);
        // 发送请求
        String result = HttpUtils.executeHttpGet(getUrl.toString());
        // 返回结果为空
        if (null == result || result.isEmpty()) {
            // 生成订单失败
            throw new Exception("request:" + getUrl.toString()
                    + ",response is null.");
        }
        JSONObject jsonResult = new JSONObject(result);
        if ("1".equals(jsonResult.getString("code"))) {
            return;
        } else {
            throw new Exception(jsonResult.getString("msg"));
        }
    }

    public static void refreshBalanceInThread(final Activity activity,
            final String appId, final String appKey, final String openid,
            final String openkey, final String pay_token, final String appid,
            final String pf, final String pfkey, final String serverId,
            final String sdkAppid, final String channelId) throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PayService.refreshBalance(activity, appId, appKey, openid,
                            openkey, pay_token, appid, pf, pfkey, serverId,
                            sdkAppid, channelId);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    XGLogger.e(ex.getMessage(), ex);
                }
            }
        });
        thread.start();
        thread.join(THREAD_JOIN_TIME_OUT);
    }

    public static void refreshBalance(final Activity activity,
            final String appId, final String appKey, String openid,
            String openkey, String pay_token, String appid, String pf,
            String pfkey, String serverId, String sdkAppid, String channelId)
            throws Exception {
        // 排序签名
        List<NameValuePair> requestParams = new ArrayList<NameValuePair>();
        requestParams.add(new BasicNameValuePair("openid", openid));
        requestParams.add(new BasicNameValuePair("openkey", openkey));
        if (pay_token != null && !pay_token.isEmpty()) {
            requestParams.add(new BasicNameValuePair("pay_token", pay_token));
        }
        requestParams.add(new BasicNameValuePair("appid", appid));
        requestParams.add(new BasicNameValuePair("pf", pf));
        requestParams.add(new BasicNameValuePair("pfkey", pfkey));
        if (serverId != null && !serverId.isEmpty()) {
            requestParams.add(new BasicNameValuePair("zoneid", serverId));
        }
        requestParams.add(new BasicNameValuePair("sdkAppid", sdkAppid));
        requestParams.add(new BasicNameValuePair("channelId", channelId));
        Collections.sort(requestParams, new Comparator<NameValuePair>() {
            @Override
            public int compare(NameValuePair lhs, NameValuePair rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        // 生成MD5签名
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
        String sign = MD5Util.md5(strSign.toString() + appId + appKey);
        XGLogger.d("before sign:" + strSign.toString());
        XGLogger.d("after sign:" + sign);
        // 生成请求
        StringBuilder getUrl = new StringBuilder();
        getUrl.append(ProductConfig.getRechargeUrl(activity))
                .append(ProductConfig.PAY_REFRESHBALANCE_URI).append("/")
                .append(channelId).append("/").append(appId).append("?");
        getUrl.append(requestContent);
        getUrl.append("&sign=").append(sign);
        // 发送请求
        String result = HttpUtils.executeHttpGet(getUrl.toString());
        // 返回结果为空
        if (null == result || result.isEmpty()) {
            // 失败
            throw new Exception("request:" + getUrl.toString()
                    + ",response is null.");
        }
        if (!("0".equals(result))) {
            throw new Exception("refresh balance failed,request:"
                    + getUrl.toString());
        }
    }

    public static String verifyPay(Activity activity, String orderId) {

        if (TextUtils.isEmpty(orderId)) {
            orderId = PayService.orderId;
        }
        StringBuilder getUrl = new StringBuilder();
        // http://onsite.recharge.xgsdk.com:8180/xgsdk/apiXgsdkPay/verifyOrder/{channelId}/{sdkAppid}
        getUrl.append(ProductConfig.getRechargeUrl(activity))
                .append("/xgsdk/apiXgsdkPay/verifyOrder").append("/")
                .append(ProductConfig.getChannelId(activity)).append("/")
                .append(ProductConfig.getXgAppId(activity)).append("?orderId=")
                .append(orderId).append("&sign=");
        String ret = "";
        try {
            ret = HttpUtils.doGetInThread(getUrl.toString());
            XGLogger.i(ret);
        } catch (Exception e) {
            XGLogger.e("verify pay error:", e);
        }
        return ret;
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
        if (null == result || result.isEmpty()) {
            // 生成订单失败
            throw new Exception("request:" + url + ",response is null.");
        }

        return result;
    }
}
