
package com.xgsdk.client.api.statistics;

import com.xgsdk.client.api.callback.ExitCallBack;
import com.xgsdk.client.api.callback.PayCallBack;
import com.xgsdk.client.api.entity.GameServerInfo;
import com.xgsdk.client.api.entity.PayInfo;
import com.xgsdk.client.api.entity.RoleInfo;
import com.xgsdk.client.api.entity.XGUser;
import com.xgsdk.client.core.data.XGDataMonitor;
import com.xgsdk.client.core.utils.XGLog;

import android.app.Activity;

import java.util.Map;

public class Statistics {

    private static final String DATA_CLASS = "com.xgsdk.client.data.XGDataAgent";
    private static XGDataMonitor sDataAgent = null;

    public static void onCreate(Activity activity) {
        try {
            sDataAgent = (XGDataMonitor) Class.forName(DATA_CLASS)
                    .newInstance();
            sDataAgent.setActivity(activity, null);
            sDataAgent.onDeviceConnect(null);
        } catch (Exception e) {
            XGLog.e("instance " + DATA_CLASS + " error.");
        }
    }

    public static void onResume(Activity activity) {
        if (sDataAgent == null) {
            return;
        }
        sDataAgent.onResume(null);
    }

    public static void onPause(Activity activity) {
        if (sDataAgent == null) {
            return;
        }
        sDataAgent.onPause(null);
    }

    public static void login(Activity activity, String customParams) {

    }

    public static void logout(Activity activity, String customParams) {
    }

    public static void pay(Activity activity, PayInfo payInfo,
            PayCallBack payCallBack) {
    }

    public static void switchAccount(Activity activity, String customParams) {
    }

    public static void exit(Activity activity, ExitCallBack exitCallBack,
            String customParams) {
    }

    public static void openUserCenter(Activity activity) {
    }

    public static void onCreateRole(Activity activity, RoleInfo role) {
    }

    public static void onEnterGame(Activity activity, XGUser user,
            RoleInfo role, GameServerInfo server) {
        if (sDataAgent == null) {
            return;
        }
        String serverId = server.getServerId();
        String serverName = server.getServerName();
        String accountId = user.getUid();
        String accountName = user.getUserName();
        String roleId = role.getRoleId();
        String roleName = role.getRoleName();
        int roleLevel = role.getLevel();
        sDataAgent.onAccountLogin(accountId, accountName, null);
        sDataAgent.onRoleLogin(accountId, accountName, roleId, roleName,
                serverId, serverName, roleLevel, "login", null);
    }

    public static void onRoleLevelup(Activity activity, XGUser user,
            RoleInfo role, GameServerInfo server) {
        if (sDataAgent == null) {
            return;
        }
        String serverId = server.getServerId();
        String accountId = user.getUid();
        String accountName = user.getUserName();
        String roleId = role.getRoleId();
        String roleName = role.getRoleName();
        int level = role.getLevel();
        sDataAgent.onRoleLevelUp(serverId, accountId, accountName, roleId,
                roleName, level, null);
    }

    public static void onEvent(Activity activity, XGUser user, RoleInfo role,
            GameServerInfo server, String eventId, String eventDesc,
            int eventVal, Map<String, Object> eventBody, String ext) {
        if (sDataAgent == null) {
            return;
        }
        String serverId = server.getServerId();
        String accountId = user.getUid();
        String accountName = user.getUserName();
        String roleId = role.getRoleId();
        String roleName = role.getRoleName();
        sDataAgent.onEvent(eventId, eventDesc, eventVal, accountId,
                accountName, eventBody, ext);
    }

    public static void onMissionBegin(Activity activity, XGUser user,
            RoleInfo roleInfo, GameServerInfo serverInfo, String missionName,
            String ext) {
        onRoleMission(activity, user, roleInfo, serverInfo, missionName,
                "enter", ext);
    }

    public static void onMissionSuccess(Activity activity, XGUser user,
            RoleInfo roleInfo, GameServerInfo serverInfo, String missionName,
            String ext) {
        onRoleMission(activity, user, roleInfo, serverInfo, missionName,
                "success", ext);
    }

    public static void onMissionFail(Activity activity, XGUser user,
            RoleInfo roleInfo, GameServerInfo serverInfo, String missionName,
            String ext) {
        onRoleMission(activity, user, roleInfo, serverInfo, missionName,
                "failed", ext);
    }

    private static void onRoleMission(Activity activity, XGUser user,
            RoleInfo role, GameServerInfo server, String missionName,
            String missionFlag, String ext) {
        if (sDataAgent == null) {
            return;
        }
        String serverId = server.getServerId();
        String accountId = user.getUid();
        String accountName = user.getUserName();
        String roleId = role.getRoleId();
        String roleName = role.getRoleName();
        int roleLevel = role.getLevel();
        sDataAgent.onRoleMission(serverId, accountId, accountName, roleId,
                roleName, roleLevel, missionName, missionFlag, ext);

    }

}
