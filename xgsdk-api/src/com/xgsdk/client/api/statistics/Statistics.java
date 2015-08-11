
package com.xgsdk.client.api.statistics;

import com.xgsdk.client.core.data.XGDataMonitor;
import com.xgsdk.client.core.utils.XGLog;

import android.app.Activity;

import java.util.Map;

public class Statistics {

    private static final String DATA_CLASS = "com.xgsdk.client.data.XGDataAgent";
    private static XGDataMonitor sDataAgent = null;

    // ******************** 生命周期 ********************//

    public static void onCreate(Activity activity) {
        try {
            sDataAgent = (XGDataMonitor) Class.forName(DATA_CLASS)
                    .newInstance();
            sDataAgent.setActivity(activity, null);
            sDataAgent.onDeviceConnect(null);
        } catch (Exception e) {
            XGLog.e(DATA_CLASS + " class is empty.");
        }
    }

    public static void onResume(Activity activity, Object ext) {
        if (sDataAgent == null) {
            return;
        }
        sDataAgent.onResume(ext);
    }

    public static void onPause(Activity activity, Object ext) {
        if (sDataAgent == null) {
            return;
        }
        sDataAgent.onPause(ext);
    }

    // ******************** 帐号 ********************//

    /**
     * 创建帐号
     * 
     * @param activity
     * @param accountId
     * @param accountName
     * @param ext
     */
    public static void onAccountCreate(Activity activity, String accountId,
            String accountName, Object ext) {
        if (sDataAgent == null) {
            return;
        }
    }

    /**
     * 帐号登录
     * 
     * @param activity
     * @param accountId
     * @param accountName
     * @param ext
     */
    public static void onAccountLogin(Activity activity, String accountId,
            String accountName, Object ext) {
        if (sDataAgent == null) {
            return;
        }
    }

    /**
     * 帐号退出
     * 
     * @param activity
     * @param accountId
     * @param accountName
     * @param ext
     */
    public static void onAccountLogout(Activity activity, String accountId,
            String accountName, Object ext) {
        if (sDataAgent == null) {
            return;
        }
    }

    // ******************** 角色 ********************//

    /**
     * 角色创建
     * 
     * @param activity
     * @param serverId
     * @param serverName
     * @param accountId
     * @param accountName
     * @param roleId
     * @param roleName
     * @param roleLevel
     * @param ext
     */
    public static void onRoleCreate(Activity activity, String serverId,
            String serverName, String accountId, String accountName,
            String roleId, String roleName, int roleLevel, Object ext) {
    }

    /**
     * 角色登录
     * 
     * @param activity
     * @param serverId
     * @param serverName
     * @param accountId
     * @param accountName
     * @param roleId
     * @param roleName
     * @param roleLevel
     * @param ext
     */
    public static void onRoleLogin(Activity activity, String serverId,
            String serverName, String accountId, String accountName,
            String roleId, String roleName, int roleLevel, Object ext) {

    }

    /**
     * 角色登出
     * 
     * @param activity
     * @param serverId
     * @param serverName
     * @param accountId
     * @param accountName
     * @param roleId
     * @param roleName
     * @param roleLevel
     * @param ext
     */
    public static void onRoleLogout(Activity activity, String serverId,
            String serverName, String accountId, String accountName,
            String roleId, String roleName, int roleLevel, Object ext) {

    }

    /**
     * 角色进入游戏
     * 
     * @param activity
     * @param serverId
     * @param serverName
     * @param accountId
     * @param accountName
     * @param roleId
     * @param roleName
     * @param roleLevel
     * @param ext
     */
    public static void onRoleEnterGame(Activity activity, String serverId,
            String serverName, String accountId, String accountName,
            String roleId, String roleName, int roleLevel, Object ext) {
        if (sDataAgent == null) {
            return;
        }

    }

    public static void onRoleLevelUp(Activity activity, String serverId,
            String serverName, String accountId, String accountName,
            String roleId, String roleName, int roleLevel, Object ext) {
        if (sDataAgent == null) {
            return;
        }

    }

    // ********************* 任务 ************************//

    /**
     * 任务开始
     * 
     * @param activity
     * @param missionId 不可为空,不可为空串
     * @param missionName
     * @param serverId
     * @param serverName
     * @param accountId
     * @param accountName
     * @param roleId
     * @param roleName
     * @param roleLevel
     * @param ext
     */
    public static void onMissionBegin(Activity activity, String missionId,
            String missionName, String serverId, String serverName,
            String accountId, String accountName, String roleId,
            String roleName, int roleLevel, Object ext) {

    }

    /**
     * 任务完成
     * 
     * @param activity
     * @param missionId 不可为空,不可为空串
     * @param missionName
     * @param serverId
     * @param serverName
     * @param accountId
     * @param accountName
     * @param roleId
     * @param roleName
     * @param roleLevel
     * @param ext
     */
    public static void onMissionSuccess(Activity activity, String missionId,
            String missionName, String serverId, String serverName,
            String accountId, String accountName, String roleId,
            String roleName, int roleLevel, Object ext) {
    }

    /**
     * 任务失败
     * 
     * @param activity
     * @param missionId 不可为空,不可为空串
     * @param missionName
     * @param serverId
     * @param serverName
     * @param accountId
     * @param accountName
     * @param roleId
     * @param roleName
     * @param roleLevel
     * @param ext
     */
    public static void onMissionFail(Activity activity, String missionId,
            String missionName, String serverId, String serverName,
            String accountId, String accountName, String roleId,
            String roleName, int roleLevel, Object ext) {
    }

    // ********************* 关卡 ************************//

    /**
     * 关卡开始
     * 
     * @param activity
     * @param levelsId 不可为空,不可为空串
     * @param serverId
     * @param serverName
     * @param accountId
     * @param accountName
     * @param roleId
     * @param roleName
     * @param roleLevel
     * @param ext
     */
    public static void onLevelsBegin(Activity activity, String levelsId,
            String serverId, String serverName, String accountId,
            String accountName, String roleId, String roleName, int roleLevel,
            Object ext) {

    }

    /**
     * 关卡完成
     * 
     * @param activity
     * @param levelsId 不可为空,不可为空串
     * @param serverId
     * @param serverName
     * @param accountId
     * @param accountName
     * @param roleId
     * @param roleName
     * @param roleLevel
     * @param ext
     */
    public static void onLevelsSuccess(Activity activity, String levelsId,
            String serverId, String serverName, String accountId,
            String accountName, String roleId, String roleName, int roleLevel,
            Object ext) {

    }

    /**
     * 关卡失败
     * 
     * @param activity
     * @param levelsId 不可为空,不可为空串
     * @param reason
     * @param serverId
     * @param serverName
     * @param accountId
     * @param accountName
     * @param roleId
     * @param roleName
     * @param roleLevel
     * @param ext
     */
    public static void onLevelsFail(Activity activity, String levelsId,
            String reason, String serverId, String serverName,
            String accountId, String accountName, String roleId,
            String roleName, int roleLevel, Object ext) {

    }

    // ********************* 道具 ************************//

    /**
     * 购买道具
     * 
     * @param activity
     * @param itemId 不可为空,不可为空串
     * @param itemName
     * @param itemCount
     * @param listPrice
     * @param transPrice
     * @param payGold
     * @param payBindingGold
     * @param curGold
     * @param curBindingGold
     * @param totalGold
     * @param totalBindingGold
     * @param serverId
     * @param serverName
     * @param accountId
     * @param accountName
     * @param roleId
     * @param roleName
     * @param roleLevel
     * @param ext
     */
    public static void onItemBuy(Activity activity, String itemId,
            String itemName, int itemCount, int listPrice, int transPrice,
            int payGold, int payBindingGold, int curGold, int curBindingGold,
            int totalGold, int totalBindingGold, String serverId,
            String serverName, String accountId, String accountName,
            String roleId, String roleName, int roleLevel, Object ext) {

    }

    /**
     * 获取道具
     * 
     * @param activity
     * @param itemId 不可为空,不可为空串
     * @param itemName
     * @param itemCount
     * @param listPrice
     * @param transPrice
     * @param payGold
     * @param payBindingGold
     * @param curGold
     * @param curBindingGold
     * @param totalGold
     * @param totalBindingGold
     * @param serverId
     * @param serverName
     * @param accountId
     * @param accountName
     * @param roleId
     * @param roleName
     * @param roleLevel
     * @param ext
     */
    public static void onItemGet(Activity activity, String itemId,
            String itemName, int itemCount, int listPrice, int transPrice,
            int payGold, int payBindingGold, int curGold, int curBindingGold,
            int totalGold, int totalBindingGold, String serverId,
            String serverName, String accountId, String accountName,
            String roleId, String roleName, int roleLevel, Object ext) {

    }

    /**
     * 消耗道具
     * 
     * @param activity
     * @param itemId 不可为空,不可为空串
     * @param itemName
     * @param itemCount
     * @param listPrice
     * @param transPrice
     * @param payGold
     * @param payBindingGold
     * @param curGold
     * @param curBindingGold
     * @param totalGold
     * @param totalBindingGold
     * @param serverId
     * @param serverName
     * @param accountId
     * @param accountName
     * @param roleId
     * @param roleName
     * @param roleLevel
     * @param ext
     */
    // TODO: 参数待定
    public static void onItemConsume(Activity activity, String itemId,
            String itemName, int itemCount, int listPrice, int transPrice,
            int payGold, int payBindingGold, int curGold, int curBindingGold,
            int totalGold, int totalBindingGold, String serverId,
            String serverName, String accountId, String accountName,
            String roleId, String roleName, int roleLevel, Object ext) {

    }

    // ********************* 金币 ************************//

    /**
     * 获取金币
     * 
     * @param activity
     * @param gainChannel
     * @param gold
     * @param bindingGold
     * @param curGold
     * @param curBindingGold
     * @param totalGold
     * @param totalBindingGold
     * @param serverId
     * @param serverName
     * @param accountId
     * @param accountName
     * @param roleId
     * @param roleName
     * @param roleLevel
     * @param ext
     */
    // TODO: 参数待定
    public static void onGoldGain(Activity activity, String gainChannel,
            int gold, int bindingGold, int curGold, int curBindingGold,
            int totalGold, int totalBindingGold, String serverId,
            String serverName, String accountId, String accountName,
            String roleId, String roleName, int roleLevel, Object ext) {

    }

    // ********************* 自定义事件 ************************//

    public static void onEvent(Activity activity, String serverId,
            String serverName, String accountId, String accountName,
            String roleId, String roleName, int roleLevel, String eventId,
            String eventDesc, int eventVal, Map<String, Object> eventBody,
            Object ext) {
        if (sDataAgent == null) {
            return;
        }
    }
}
