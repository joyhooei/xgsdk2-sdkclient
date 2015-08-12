
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

    public void onDestroy(Activity activity) {
        try {
            mXGChannel.onDestroy(activity);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onDestroy " + " error ", e);
        }
    }

    public void onResume(Activity activity) {
        try {
            mXGChannel.onResume(activity);
            Statistics.onResume(activity, null);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onResume " + " error ", e);
        }
    }

    public void onPause(Activity activity) {
        try {
            mXGChannel.onPause(activity);
            Statistics.onPause(activity, null);
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
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " login " + " error ", e);
        }
    }

    public void logout(Activity activity, String customParams) {
        try {
            mXGChannel.logout(activity, customParams);
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
                                    + payInfo.getTotalPrice() + ",ext:"
                                    + payInfo.getCustom());
                            payCallBack.onFail(
                                    XGErrorCode.PAY_FAILED_CREATE_ORDER_FAILED,
                                    "create order fail in xg service ,uid:"
                                            + payInfo.getUid() + ",price:"
                                            + payInfo.getTotalPrice() + ",ext:"
                                            + payInfo.getCustom());
                            return;
                        }
                        payInfo.setAdditionalParam(PayInfo.KEY_XG_ORDER_ID,
                                orderId);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mXGChannel.pay(activity, payInfo, payCallBack);
                            }
                        });
                    }
                });
            } else {
                mXGChannel.pay(activity, payInfo, payCallBack);
            }
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " pay " + " error ", e);
        }

    }

    public void exit(Activity activity, ExitCallBack exitCallBack,
            String customParams) {
        try {
            mXGChannel.exit(activity, exitCallBack, customParams);
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
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " switchAccount " + " error ", e);
        }
    }

    public void onCreateRole(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server) {
        try {
            mXGChannel.onCreateRole(activity, role);
            Statistics
                    .onRoleCreate(activity, server.getServerId(),
                            server.getServerName(), user.getUid(),
                            user.getUserName(), role.getRoleId(),
                            role.getRoleName(), role.getLevel(), null);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onCreateRole " + role
                    + " error ", e);
        }
    }

    public void onEnterGame(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server) {
        try {
            mXGChannel.onEnterGame(activity, user, role, server);
            String serverId = server.getServerId();
            String serverName = server.getServerName();
            String accountId = user.getUid();
            String accountName = user.getUserName();
            String roleId = role.getRoleId();
            String roleName = role.getRoleName();
            int roleLevel = role.getLevel();
            Statistics.onAccountLogin(activity, user.getUid(),
                    user.getUserName(), null);
            Statistics.onRoleLogin(activity, serverId, serverName, accountId,
                    accountName, roleId, roleName, roleLevel, null);
            Statistics.onRoleEnterGame(activity, serverId, serverName,
                    accountId, accountName, roleId, roleName, roleLevel, null);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onEnterGame error ", e);
        }
    }

    public void onRoleLevelup(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server) {
        try {
            mXGChannel.onRoleLevelup(activity, role);
            String serverId = server.getServerId();
            String serverName = server.getServerName();
            String accountId = user.getUid();
            String accountName = user.getUserName();
            String roleId = role.getRoleId();
            String roleName = role.getRoleName();
            int roleLevel = role.getLevel();
            Statistics.onRoleLevelUp(activity, serverId, serverName, accountId,
                    accountName, roleId, roleName, roleLevel, null);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onRoleLevelup " + role
                    + " error ", e);
        }
    }

    public void onRoleLogout(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server, String customParams) {
        try {
            String serverId = server.getServerId();
            String serverName = server.getServerName();
            String accountId = user.getUid();
            String accountName = user.getUserName();
            String roleId = role.getRoleId();
            String roleName = role.getRoleName();
            int roleLevel = role.getLevel();
            Statistics.onRoleLogout(activity, serverId, serverName, accountId,
                    accountName, roleId, roleName, roleLevel, customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onRoleLogout " + role
                    + " error ", e);
        }
    }

    public void onAccountCreate(Activity activity, XGUser user,
            String customParams) {
        try {
            String accountId = user.getUid();
            String accountName = user.getUserName();
            Statistics.onAccountCreate(activity, accountId, accountName,
                    customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onAccountCreate " + user
                    + " error ", e);
        }
    }

    public void onAccountLogout(Activity activity, XGUser user,
            String customParams) {
        try {
            String accountId = user.getUid();
            String accountName = user.getUserName();
            Statistics.onAccountLogout(activity, accountId, accountName,
                    customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onAccountLogout " + user
                    + " error ", e);
        }
    }

    public void onEvent(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server, String eventId, String eventDesc,
            int eventVal, Map<String, Object> eventBody, String customParams) {
        try {
            String serverId = server.getServerId();
            String serverName = server.getServerName();
            String accountId = user.getUid();
            String accountName = user.getUserName();
            String roleId = role.getRoleId();
            String roleName = role.getRoleName();
            int roleLevel = role.getLevel();
            Statistics.onEvent(activity, serverId, serverName, accountId,
                    accountName, roleId, roleName, roleLevel, eventId,
                    eventDesc, eventVal, eventBody, customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onEvent error ", e);
        }
    }

    public void onMissionBegin(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server, String missionId, String missionName,
            String customParams) {
        try {
            String serverId = server.getServerId();
            String serverName = server.getServerName();
            String accountId = user.getUid();
            String accountName = user.getUserName();
            String roleId = role.getRoleId();
            String roleName = role.getRoleName();
            int roleLevel = role.getLevel();
            Statistics.onMissionBegin(activity, missionId, missionName,
                    serverId, serverName, accountId, accountName, roleId,
                    roleName, roleLevel, customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onMissionBegin error ", e);
        }
    }

    public void onMissionSuccess(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server, String missionId, String missionName,
            String customParams) {
        try {
            String serverId = server.getServerId();
            String serverName = server.getServerName();
            String accountId = user.getUid();
            String accountName = user.getUserName();
            String roleId = role.getRoleId();
            String roleName = role.getRoleName();
            int roleLevel = role.getLevel();
            Statistics.onMissionSuccess(activity, missionId, missionName,
                    serverId, serverName, accountId, accountName, roleId,
                    roleName, roleLevel, customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onMissionSuccess error ", e);
        }
    }

    public void onMissionFail(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server, String missionId, String missionName,
            String customParams) {
        try {
            String serverId = server.getServerId();
            String serverName = server.getServerName();
            String accountId = user.getUid();
            String accountName = user.getUserName();
            String roleId = role.getRoleId();
            String roleName = role.getRoleName();
            int roleLevel = role.getLevel();
            Statistics.onMissionFail(activity, missionId, missionName,
                    serverId, serverName, accountId, accountName, roleId,
                    roleName, roleLevel, customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onMissionFail error ", e);
        }
    }

    public void onLevelsBegin(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server, String levelsId, String customParams) {
        try {
            String serverId = server.getServerId();
            String serverName = server.getServerName();
            String accountId = user.getUid();
            String accountName = user.getUserName();
            String roleId = role.getRoleId();
            String roleName = role.getRoleName();
            int roleLevel = role.getLevel();
            Statistics.onLevelsBegin(activity, levelsId, serverId, serverName,
                    accountId, accountName, roleId, roleName, roleLevel,
                    customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onLevelsBegin error ", e);
        }
    }

    public void onLevelsSuccess(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server, String levelsId, String customParams) {
        try {
            String serverId = server.getServerId();
            String serverName = server.getServerName();
            String accountId = user.getUid();
            String accountName = user.getUserName();
            String roleId = role.getRoleId();
            String roleName = role.getRoleName();
            int roleLevel = role.getLevel();
            Statistics.onLevelsSuccess(activity, levelsId, serverId,
                    serverName, accountId, accountName, roleId, roleName,
                    roleLevel, customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onLevelsSuccess error ", e);
        }
    }

    public void onLevelsFail(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server, String levelsId, String reason,
            String customParams) {
        try {
            String serverId = server.getServerId();
            String serverName = server.getServerName();
            String accountId = user.getUid();
            String accountName = user.getUserName();
            String roleId = role.getRoleId();
            String roleName = role.getRoleName();
            int roleLevel = role.getLevel();
            Statistics.onLevelsFail(activity, levelsId, reason, serverId,
                    serverName, accountId, accountName, roleId, roleName,
                    roleLevel, customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onLevelsFail error ", e);
        }
    }

    public void onItemBuy(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server, String itemId, String itemName,
            int itemCount, int listPrice, int transPrice, int payGold,
            int payBindingGold, int curGold, int curBindingGold, int totalGold,
            int totalBindingGold, String customParams) {
        try {
            String serverId = server.getServerId();
            String serverName = server.getServerName();
            String accountId = user.getUid();
            String accountName = user.getUserName();
            String roleId = role.getRoleId();
            String roleName = role.getRoleName();
            int roleLevel = role.getLevel();
            Statistics.onItemBuy(activity, itemId, itemName, itemCount,
                    listPrice, transPrice, payGold, payBindingGold, curGold,
                    curBindingGold, totalGold, totalBindingGold, serverId,
                    serverName, accountId, accountName, roleId, roleName,
                    roleLevel, customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onItemBuy error ", e);
        }

    }

    public void onItemGet(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server, String itemId, String itemName,
            int itemCount, int listPrice, int transPrice, int payGold,
            int payBindingGold, int curGold, int curBindingGold, int totalGold,
            int totalBindingGold, String customParams) {
        try {
            String serverId = server.getServerId();
            String serverName = server.getServerName();
            String accountId = user.getUid();
            String accountName = user.getUserName();
            String roleId = role.getRoleId();
            String roleName = role.getRoleName();
            int roleLevel = role.getLevel();
            Statistics.onItemGet(activity, itemId, itemName, itemCount,
                    listPrice, transPrice, payGold, payBindingGold, curGold,
                    curBindingGold, totalGold, totalBindingGold, serverId,
                    serverName, accountId, accountName, roleId, roleName,
                    roleLevel, customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onItemBuy error ", e);
        }

    }

    public void onItemConsume(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server, String itemId, String itemName,
            int itemCount, int listPrice, int transPrice, int payGold,
            int payBindingGold, int curGold, int curBindingGold, int totalGold,
            int totalBindingGold, String customParams) {
        try {
            String serverId = server.getServerId();
            String serverName = server.getServerName();
            String accountId = user.getUid();
            String accountName = user.getUserName();
            String roleId = role.getRoleId();
            String roleName = role.getRoleName();
            int roleLevel = role.getLevel();
            Statistics.onItemConsume(activity, itemId, itemName, itemCount,
                    listPrice, transPrice, payGold, payBindingGold, curGold,
                    curBindingGold, totalGold, totalBindingGold, serverId,
                    serverName, accountId, accountName, roleId, roleName,
                    roleLevel, customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onItemConsume error ", e);
        }

    }

    public void onGoldGain(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server, String gainChannel, int gold,
            int bindingGold, int curGold, int curBindingGold, int totalGold,
            int totalBindingGold, String customParams) {
        try {
            String serverId = server.getServerId();
            String serverName = server.getServerName();
            String accountId = user.getUid();
            String accountName = user.getUserName();
            String roleId = role.getRoleId();
            String roleName = role.getRoleName();
            int roleLevel = role.getLevel();
            Statistics.onGoldGain(activity, gainChannel, gold, bindingGold,
                    curGold, curBindingGold, totalGold, totalBindingGold,
                    serverId, serverName, accountId, accountName, roleId,
                    roleName, roleLevel, customParams);
        } catch (Exception e) {
            XGLog.e(LOG_TAG, getChannelId() + " onGoldGain error ", e);
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
