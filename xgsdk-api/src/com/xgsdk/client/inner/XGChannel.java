
package com.xgsdk.client.inner;

import com.xgsdk.client.api.callback.ExitCallBack;
import com.xgsdk.client.api.callback.PayCallBack;
import com.xgsdk.client.api.callback.UserCallBack;
import com.xgsdk.client.api.entity.GameServerInfo;
import com.xgsdk.client.api.entity.PayInfo;
import com.xgsdk.client.api.entity.RoleInfo;
import com.xgsdk.client.api.entity.XGUser;
import com.xgsdk.client.core.service.PayService;
import com.xgsdk.client.core.utils.CommonUtils;
import com.xgsdk.client.core.utils.XGLog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.HashMap;

public abstract class XGChannel {

    protected UserCallBack mUserCallBack;

    public abstract String getChannelId();

    public abstract String getChannelAppId(Context context);

    public abstract void init(final Activity activity); // 初始化

    public final void setUserCallBack(final UserCallBack userCallBack) {
        mUserCallBack = userCallBack;
    }

    public abstract void login(final Activity activity,
            final String customParams);

    public void logout(final Activity activity, final String customParams) {
        if (mUserCallBack != null) {
            mUserCallBack.onLogoutSuccess("");
        }
    }

    public void exit(final Activity activity, final ExitCallBack exitCallBack,
            final String customParams) {
        if (exitCallBack != null) {
            exitCallBack.onNoChannelExiter();
        }
    }

    public abstract void pay(final Activity activity, final PayInfo payInfo,
            final PayCallBack payCallBack);

    public void switchAccount(final Activity activity, final String customParams) {
        if (mUserCallBack != null) {
            mUserCallBack.onLogoutSuccess("");
        }
    }

    public void onCreate(final Activity activity) {
    }// 游戏主Actitivy在onCreate()调用

    public void onDestory(final Activity activity) {
    }// 游戏主Actitivy在onDestory()调用

    public void onPause(final Activity activity) {
    }// 游戏主Actitivy在onPause()调用

    public void onResume(final Activity activity) {
    }// 游戏主Actitivy在onResume()调用

    public void onNewIntent(final Activity activity, final Intent intent) {
    }

    public void onStart(final Activity activity) {
    }

    public void onRestart(final Activity activity) {
    }

    public void onStop(final Activity activity) {
    }

    public void onApplicationCreate(final Context context) {
    }

    public void onApplicationAttachBaseContext(final Context context) {
    }

    public void openUserCenter(final Activity activity,
            final String customParams) {
    }

    public void onCreateRole(final Activity activity, final RoleInfo info) {
    }

    public void onRoleLevelup(final Activity activity, final RoleInfo roleInfo) {
    }

    public void onEnterGame(final Activity activity, XGUser user,
            RoleInfo roleInfo, GameServerInfo serverInfo) {
    }

    public void onActivityResult(Activity activity, int requestCode,
            int resultCode, Intent data) {
    }

    public boolean isCreateXGOrderIdBySelf() {
        return false;
    }

    public final void updateOrder(Activity activity, PayInfo payInfo) {
        try {
            PayService.updateOrderInThread(activity, payInfo.getXgOrderId(),
                    payInfo.getUid(), payInfo.getProductId(),
                    payInfo.getProductName(), payInfo.getProductDesc(),
                    String.valueOf(payInfo.getProductCount()),
                    String.valueOf(payInfo.getProductTotalPrice()),
                    payInfo.getServerId(), payInfo.getServerName(),
                    payInfo.getRoleId(), payInfo.getRoleName(),
                    payInfo.getCurrencyName(), payInfo.getExt(),
                    payInfo.getGameOrderId(), payInfo.getNotifyURL());
        } catch (Exception e) {
            XGLog.e("update order error", e);
        }
    }

    public final void cancelOrder(Activity activity, String XgOrderId) {
        try {
            PayService.cancelOrderInThread(activity, XgOrderId);
        } catch (Exception e) {
            XGLog.e("cancel order error", e);
        }
    }

    public final String createOrder(Activity activity, PayInfo payInfo) {
        String orderId = null;
        try {
            orderId = PayService.createOrderInThread(activity,
                    payInfo.getUid(), payInfo.getProductId(),
                    payInfo.getProductName(), payInfo.getProductDesc(),
                    String.valueOf(payInfo.getProductCount()),
                    String.valueOf(payInfo.getProductTotalPrice()),
                    payInfo.getServerId(), payInfo.getZoneId(),
                    payInfo.getRoleId(), payInfo.getRoleName(),
                    payInfo.getCurrencyName(), payInfo.getExt(),
                    payInfo.getGameOrderId(), payInfo.getNotifyURL(),
                    getChannelAppId(activity));
        } catch (Exception e) {
            XGLog.e("create order error", e);
        }
        return orderId;
    }

    public final static boolean isMethodSupported(XGChannel agent,
            String methodName) {
        Class<?>[] parameterTypes = METHODS_MAP.get(methodName);
        return CommonUtils.supportMethodInSubClass(agent, methodName,
                parameterTypes);
    }

    public static final HashMap<String, Class<?>[]> METHODS_MAP = new HashMap<String, Class<?>[]>();
    static {
        METHODS_MAP.put("login", new Class[] {
                Activity.class, String.class
        });
        METHODS_MAP.put("logout", new Class[] {
                Activity.class, String.class
        });
        METHODS_MAP.put("pay", new Class[] {
                Activity.class, PayInfo.class, PayCallBack.class
        });
        METHODS_MAP.put("switchAccount", new Class[] {
                Activity.class, String.class
        });
        METHODS_MAP.put("openUserCenter", new Class[] {
                Activity.class, String.class
        });
        METHODS_MAP.put("exit", new Class[] {
                Activity.class, ExitCallBack.class, String.class
        });
    }
}
