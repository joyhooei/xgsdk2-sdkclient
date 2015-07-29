
package com.xgsdk.client;

import com.seasun.powerking.sdkclient.Util;
import com.seasun.powerking.sdkclient.callback.IConstant;
import com.seasun.xgsdk.XGSDKAndroidImpl;
import com.xgsdk.client.agent.XGAgent;
import com.xgsdk.client.core.util.XGLogger;
import com.xgsdk.client.callback.ExitCallBack;
import com.xgsdk.client.callback.PayCallBack;
import com.xgsdk.client.entity.GameServerInfo;
import com.xgsdk.client.entity.PayInfo;
import com.xgsdk.client.entity.RoleInfo;
import com.xgsdk.client.entity.XGUser;
import com.xgsdk.plugin.listenses.Listener;
import com.xgsdk.client.entity.XGErrorCode;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Base64;

class XGV1AdapterAgent extends XGAgent {

    private static final String CODE_HAS_CHANNEL_EXIT = "1";
    private static final String CODE_NO_CHANNEL_EXIT = "0";

    private XGSDKAndroidImpl mGameSDK;

    XGV1AdapterAgent() {
    }

    @Override
    public String getChannelId() {
        return XGSDKAndroidImpl.getChannelID();
    }

    @Override
    public void onCreate(Activity activity) {
        mGameSDK.onCreate(activity);
    }

    @Override
    public void onDestory(Activity activity) {
        mGameSDK.onDestroy(activity);
    }

    @Override
    public void onPause(Activity activity) {
        mGameSDK.onPause(activity);
    }

    @Override
    public void onResume(Activity activity) {
        mGameSDK.onResume(activity);
    }

    @Override
    public void init(final Activity activity) {

        mGameSDK = new XGSDKAndroidImpl(new Listener() {

            @Override
            public void onListener(String json) {
                JSONObject parseObject;
                try {
                    parseObject = new JSONObject(json);

                    XGLogger.d(json);
                    String callbackType = parseObject.getString("callbackType");
                    String flag = parseObject
                            .getString(IConstant.KEY_successFlag);
                    // 登录类型的回调
                    if (TextUtils.equals(IConstant.XGSDK_CALLBACK_LOGIN_FUNC,
                            callbackType)) {

                        // 登录成功标示
                        if (TextUtils.equals(IConstant.F_YES, flag)) {
                            String data = parseObject
                                    .getString(IConstant.KEY_data);
                            // 渠道用户信息
                            JSONObject dataInfo = new JSONObject(data);
                            String authInfo = dataInfo
                                    .getString(IConstant.KEY_retMsg);
                            byte[] decode = Base64.decode(authInfo,
                                    Base64.NO_WRAP);
                            String msg = new String(decode);
                            XGLogger.d(msg);

                            mGameSDK.showToolBar(activity, "1");

                            if (mUserCallBack != null) {
                                mUserCallBack.onLoginSuccess(authInfo);
                            }
                        } else if (TextUtils.equals(IConstant.F_NO, flag)) {
                            if (mUserCallBack != null) {
                                mUserCallBack.onLoginFail(XGErrorCode.OTHER_ERROR, "login fail");
                            }
                        }
                    } else if (TextUtils.equals(
                            IConstant.XGSDK_CALLBACK_EXIT_FUNC, callbackType)) {
                        if (mExitCallBack != null) {
                            if (TextUtils.equals(CODE_HAS_CHANNEL_EXIT,
                                    mGameSDK.hasChannelExit())) {
                                mExitCallBack.onExit();
                            } else if (TextUtils.equals(CODE_NO_CHANNEL_EXIT,
                                    mGameSDK.hasChannelExit())) {
                                mExitCallBack.onNoChannelExiter();
                            } else {
                                mExitCallBack.onCancel();
                            }
                        }

                    } else if (TextUtils.equals(
                            IConstant.XGSDK_CALLBACK_INIT_FUNC, callbackType)) {
                        if (TextUtils.equals(IConstant.F_NO, flag)
                                && mUserCallBack != null) {
                            mUserCallBack.onInitFail(XGErrorCode.OTHER_ERROR, "init fail");
                        }

                    } else if (TextUtils.equals(
                            IConstant.XGSDK_CALLBACK_LOGOUT_FUNC, callbackType)
                            || TextUtils.equals(
                                    IConstant.XGSDK_CALLBACK_SWITCH_USER_FUNC,
                                    callbackType)) {

                        if (mUserCallBack != null) {
                            if (TextUtils.equals(IConstant.F_YES, flag)) {
                                mUserCallBack
                                        .onLogoutSuccess("logout success.");
                            } else {
                                mUserCallBack.onLogoutFail(XGErrorCode.OTHER_ERROR, "logout fail.");
                            }
                        }

                    } else if (TextUtils.equals(
                            IConstant.XGSDK_CALLBACK_RECHARGE_FUNC,
                            callbackType)) {
                        if (mPayCallBack != null) {
                            if (TextUtils.equals(IConstant.F_YES, flag)) {
                                mPayCallBack.onSuccess("pay success.");
                            } else {
                                mPayCallBack.onFail(XGErrorCode.OTHER_ERROR,"pay fail");
                            }
                        }
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }, activity);
        mGameSDK.init(activity, null);
        Util.setActivity(activity);
        Util.setContext(activity);
    }

    @Override
    public void login(Activity activity, String customParams) {
        mGameSDK.login(activity, "");
    }

    @Override
    public void logout(Activity activity, String customParams) {
        mGameSDK.logout(activity, "");
    }

    @Override
    public void exit(Activity activity, ExitCallBack exitCallBack,
            String customParams) {
        mGameSDK.onExitGame(activity, null);
    }

    @Override
    public void pay(Activity activity, PayInfo payment, PayCallBack payCallBack) {

        String payInfo = getPayInfo(payment.getUid(), payment.getProductId(),
                payment.getProductName(), payment.getProductDesc(),
                String.valueOf(payment.getProductTotalPrice()),
                String.valueOf(payment.getProductCount()),
                payment.getCurrencyName(), payment.getServerId(),
                payment.getServerName(), payment.getRoleId(),
                payment.getRoleName(), payment.getBalance(), payment.getExt());
        mGameSDK.pay(activity, payInfo);
    }

    @Override
    public void switchAccount(Activity activity, String customParams) {
        mGameSDK.switchUser(activity, "");
    }

    @Override
    public void onNewIntent(Activity activity, Intent intent) {
        mGameSDK.onNewIntent(activity, intent);
    }

    @Override
    public void onStart(Activity activity) {
        mGameSDK.onStart(activity, XGSDKAndroidImpl.getChannelID(), null);
    }

    @Override
    public void onRestart(Activity activity) {
        mGameSDK.onRestart(activity, null);
    }

    @Override
    public void onStop(Activity activity) {
        mGameSDK.onStop(activity);
    }

    @Override
    public void openUserCenter(Activity activity, String customParams) {
        mGameSDK.openUserCenter(activity);
    }

    @Override
    public void onEnterGame(Activity activity, XGUser user, RoleInfo roleInfo,
            GameServerInfo serverInfo) {
        mGameSDK.onEnterGame(activity, null);
        // mGameSDK.enterGame(roleInfo.getRoleId(), roleInfo.getRoleName(),
        // roleInfo.getLevel(), serverInfo.getServerId(),
        // serverInfo.getServerName(), roleInfo.getVipLevel(),
        // roleInfo.getBalance(), null);
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode,
            int resultCode, Intent data) {
        mGameSDK.onActivityResult(requestCode, resultCode, data);

    }

    private String getPayInfo(String accountId, String productId,
            String productName, String productDec, String price, String amount,
            String currencyName, String serverId, String serverName,
            String roleId, String roleName, String balance, String ext) {

        JSONObject json = new JSONObject();
        try {
            json.put("accountID", accountId);
            json.put("productID", productId);
            json.put("productName", productName);
            json.put("productDec", productDec);
            json.put("price", price);
            json.put("amount", amount);
            json.put("currencyName", currencyName);
            json.put("serverId", serverId);
            json.put("serverName", serverName);
            json.put("roleId", roleId);
            json.put("roleName", roleName);
            json.put("balance", balance);
            json.put("ext", ext);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json.toString();
    }

    public String channelTag() {
        // TODO Auto-generated method stub
        return XGSDKAndroidImpl.getChannelID();
    }

}
