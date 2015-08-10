
package com.xgsdk.client.api;

import com.xgsdk.client.api.callback.ExitCallBack;
import com.xgsdk.client.api.callback.PayCallBack;
import com.xgsdk.client.api.callback.UserCallBack;
import com.xgsdk.client.api.entity.GameServerInfo;
import com.xgsdk.client.api.entity.PayInfo;
import com.xgsdk.client.api.entity.RoleInfo;
import com.xgsdk.client.api.entity.XGUser;
import com.xgsdk.client.api.statistics.Statistics;
import com.xgsdk.client.core.XGInfo;
import com.xgsdk.client.core.service.ICallback;
import com.xgsdk.client.core.service.Result;
import com.xgsdk.client.core.utils.XGLog;
import com.xgsdk.client.inner.XGChannel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.util.Map;

public class XGSDK {
    public static final String LOG_TAG = "XGSDK";

    public static final String VERSION = "2.0";

    private static XGSDK sInstance = null;

    private XGChannel mXGChannel = null;

    private XGSDK() {
        initchannel();
    }

    public String getChannelId() {
        return mXGChannel == null ? null : mXGChannel.getChannelId();
    }

    public static XGSDK getInstance() {
        if (null == sInstance) {
            synchronized (XGSDK.class) {
                if (null == sInstance) {
                    sInstance = new XGSDK();
                }
            }
        }

        return sInstance;
    }

    private void initchannel() {
        try {
            mXGChannel = SDKFactory.getSDK();
            if (mXGChannel == null) {
                throw new RuntimeException("Create xgsdk channel error.");
            }
            XGInfo.init(VERSION, mXGChannel.getChannelId());
            XGLog.i(LOG_TAG, getChannelId() + " instance " + mXGChannel);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " instance " + " channel error ",
                    e);
        }
    }

    public void onCreate(Activity activity) {
        try {
            mXGChannel.onCreate(activity);
            Statistics.onCreate(activity);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onCreate " + " error ", e);
        }
    }

    public void onDestory(Activity activity) {
        try {
            mXGChannel.onDestory(activity);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onDestory " + " error ", e);
        }
    }

    public void onResume(Activity activity) {
        try {
            mXGChannel.onResume(activity);
            Statistics.onResume(activity);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onResume " + " error ", e);
        }
    }

    public void onPause(Activity activity) {
        try {
            mXGChannel.onPause(activity);
            Statistics.onPause(activity);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onPause " + " error ", e);
        }
    }

    public void onStart(Activity activity) {
        try {
            mXGChannel.onStart(activity);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onStart " + " error ", e);
        }
    }

    public void onRestart(Activity activity) {
        try {
            mXGChannel.onRestart(activity);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onRestart " + " error ", e);
        }
    }

    public void onStop(Activity activity) {
        try {
            mXGChannel.onStop(activity);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onStop " + " error ", e);
        }
    }

    public void onNewIntent(Activity activity, Intent intent) {
        try {
            mXGChannel.onNewIntent(activity, intent);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onNewIntent " + " error ", e);
        }
    }

    public void onActivityResult(Activity activity, int requestCode,
            int resultCode, Intent data) {
        try {
            mXGChannel
                    .onActivityResult(activity, requestCode, resultCode, data);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onActivityResult " + " error ",
                    e);
        }
    }

    public void setUserCallBack(UserCallBack userCallBack) {
        try {
            mXGChannel.setUserCallBack(userCallBack);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " setUserCallBack " + " error ",
                    e);
        }
    }

    public void init(Activity activity) {
        try {
            mXGChannel.init(activity);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " init " + " error ", e);
        }
    }

    public void login(Activity activity, String customParams) {
        try {
            mXGChannel.login(activity, customParams);
            Statistics.login(activity, customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " login " + " error ", e);
        }
    }

    public void logout(Activity activity, String customParams) {
        try {
            mXGChannel.logout(activity, customParams);
            Statistics.logout(activity, customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " logout " + " error ", e);
        }
    }

    public void pay(final Activity activity, final PayInfo payInfo,
            final PayCallBack payCallBack) {
        try {
            if (!mXGChannel.isCreateXGOrderIdBySelf()) {// 若渠道实现不自己创建XG订单，主动帮它创建
                mXGChannel.createOrder(activity, payInfo, new ICallback() {

                    @Override
                    public void callback(Result result, String retStr) {
                        String orderId = result.getOrderId();

                        if (TextUtils.isEmpty(orderId)) {
                            XGLog.e("create order fail in xg service ,uid:"
                                    + payInfo.getUid() + ",price:"
                                    + payInfo.getProductTotalPrice() + ",ext:"
                                    + payInfo.getExt());
                            payCallBack.onFail(
                                    XGErrorCode.PAY_FAILED_CREATE_ORDER_FAILED,
                                    "create order fail in xg service ,uid:"
                                            + payInfo.getUid() + ",price:"
                                            + payInfo.getProductTotalPrice()
                                            + ",ext:" + payInfo.getExt());
                            return;
                        }
                        payInfo.setAdditionalParam(PayInfo.KEY_XG_ORDER_ID,
                                orderId);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mXGChannel.pay(activity, payInfo, payCallBack);
                                Statistics.pay(activity, payInfo, payCallBack);
                            }
                        });
                    }
                });
            } else {
                mXGChannel.pay(activity, payInfo, payCallBack);
                Statistics.pay(activity, payInfo, payCallBack);
            }
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " pay " + " error ", e);
        }

    }

    public void exit(Activity activity, ExitCallBack exitCallBack,
            String customParams) {
        try {
            mXGChannel.exit(activity, exitCallBack, customParams);
            Statistics.exit(activity, exitCallBack, customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " exit " + " error ", e);
        }
    }

    public void openUserCenter(Activity activity, String customParams) {
        try {
            mXGChannel.openUserCenter(activity, customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " openUserCenter " + " error ", e);
        }
    }

    public void switchAccount(Activity activity, String customParams) {
        try {
            mXGChannel.switchAccount(activity, customParams);
            Statistics.switchAccount(activity, customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " switchAccount " + " error ", e);
        }
    }

    public void onCreateRole(Activity activity, RoleInfo info) {
        try {
            mXGChannel.onCreateRole(activity, info);
            Statistics.onCreateRole(activity, info);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onCreateRole " + info
                    + " error ", e);
        }
    }

    public void onEnterGame(Activity activity, XGUser user, RoleInfo roleInfo,
            GameServerInfo serverInfo) {
        try {
            mXGChannel.onEnterGame(activity, user, roleInfo, serverInfo);
            Statistics.onEnterGame(activity, user, roleInfo, serverInfo);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onEnterGame error ", e);
        }
    }

    public void onRoleLevelup(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server) {
        try {
            mXGChannel.onRoleLevelup(activity, role);
            Statistics.onRoleLevelup(activity, user, role, server);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onRoleLevelup " + role
                    + " error ", e);
        }
    }

    public void onEvent(Activity activity, XGUser user, RoleInfo roleInfo,
            GameServerInfo serverInfo, String eventId, String eventDesc,
            int eventVal, Map<String, Object> eventBody, String customParams) {
        try {
            Statistics.onEvent(activity, user, roleInfo, serverInfo, eventId,
                    eventDesc, eventVal, eventBody, customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onEvent error ", e);
        }
    }

    public void onMissionBegin(Activity activity, XGUser user,
            RoleInfo roleInfo, GameServerInfo serverInfo, String missionName,
            String customParams) {
        try {
            Statistics.onMissionBegin(activity, user, roleInfo, serverInfo,
                    missionName, customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onMissionBegin error ", e);
        }
    }

    public void onMissionSuccess(Activity activity, XGUser user,
            RoleInfo roleInfo, GameServerInfo serverInfo, String missionName,
            String customParams) {
        try {
            Statistics.onMissionSuccess(activity, user, roleInfo, serverInfo,
                    missionName, customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onMissionSuccess error ", e);
        }
    }

    public void onMissionFail(Activity activity, XGUser user,
            RoleInfo roleInfo, GameServerInfo serverInfo, String missionName,
            String customParams) {
        try {
            Statistics.onMissionFail(activity, user, roleInfo, serverInfo,
                    missionName, customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onMissionFail error ", e);
        }
    }

    void onApplicationCreate(final Context context) {
        try {
            mXGChannel.onApplicationCreate(context);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onApplicationCreate "
                    + " error ", e);
        }
    }

    void onApplicationAttachBaseContext(final Context context) {
        try {
            mXGChannel.onApplicationAttachBaseContext(context);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId()
                    + " onApplicationAttachBaseContext " + " error ", e);
        }
    }

    public boolean isMethodSupport(String methodName) {
        boolean isSupport = false;
        try {
            isSupport = XGChannel.isMethodSupported(mXGChannel, methodName);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " isMethodSupport " + " error ",
                    e);
        }
        return isSupport;
    }

}
