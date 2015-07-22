
package com.xgsdk.client.data;

import com.xgsdk.client.core.util.ConstInfo;
import com.xgsdk.client.core.util.XGLogger;
import com.xgsdk.client.data.DataInfo.AccountType;
import com.xgsdk.client.data.DataInfo.Gender;
import com.xgsdk.client.data.handler.CrashHandler;
import com.xgsdk.client.data.handler.DetectionHandler;
import com.xgsdk.client.data.handler.ProcessThread;
import com.xgsdk.client.data.process.Common;

import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class XGDataAgent {

    public static final String TAG = "XSJData";

    public static void setAppId(String appId) {
        ConstInfo.setAppId(appId);
    }

    public static void setAppKey(String appKey) {
        ConstInfo.setAppKey(appKey);
    }

    public static void setAdId(String adId) {
        ConstInfo.setAdId(adId);
    }

    public static void setChannelId(String channelId) {
        ConstInfo.setChannelId(channelId);
    }

    /**
     * each level log will be print with debug = true , otherwise print e/w
     * level.
     * 
     * @param debug
     */
//    public static void setDebugMode(boolean debug) {
//        XGLogger.DEBUG = debug;
//    }

    /**
     * set the game's server id.
     * 
     * @param serverId
     */
    public static void setServerId(String serverId) {
        DataInfo.getInstance().setServerId(serverId);
    }

    /**
     * set the game's role id.
     * 
     * @param roleId
     */
    public static void setRoleId(String roleId) {
        DataInfo.getInstance().setRoleId(roleId);
    }

    /**
     * set the account id.
     * 
     * @param accountId
     */
    public static void setAccountId(String accountId) {
        DataInfo.getInstance().setAccountId(accountId);
    }

    /**
     * set the ReportPolicy. REALTIME:report data immediately while event is
     * happening. BATCH_AT_LAUNCH:store data into cache and report it while next
     * launch. SMART:REALTIME with wifi and BATCH_AT_LAUNCH with 2G/3G/4G.
     * Default policy is SMART. open/close/login/logout will be report realtime.
     * 
     * @param policy REALTIME/BATCH_AT_LAUNCH/SMART
     */
    public static void setReportPolicy(ReportPolicy policy) {
        DataInfo.getInstance().setReportPolicy(policy);
    }

    public static void setGender(Gender gender) {
        DataInfo.getInstance().setGender(gender);
    }

    public static void setLevel(String level) {
        DataInfo.getInstance().setLevel(level);
    }

    public static void setAge(int age) {
        DataInfo.getInstance().setAge(age);
    }

    public static void setAccountType(AccountType type) {
        DataInfo.getInstance().setAccountType(type);
    }

    public static void setAccountName(String accountName) {
        DataInfo.getInstance().setAccountName(accountName);
    }

    /**
     * UncaughtException will be store and report next launch if enable
     * 
     * @param context
     * @param enable
     */
    public static void setUncaughtExceptionEnable(Context context,
            boolean enable) {
        CrashHandler.getInstance(context).setEnable(enable);
    }

    /**
     * Call in main Activity.onCreate method. Required.
     * 
     * @param context
     */
    public static void onCreate(Context context) {
    }

    /**
     * Call in main Activity.onDestory method. Required.
     * 
     * @param context
     */
    public static void onDestory(Context context) {
    }

    /**
     * Call in main Activity.onResume method. Required.
     * 
     * @param context
     */
    public static void onResume(Context context) {
        try {
            if (context == null) {
                XGLogger.e("unexpected null context");
                return;
            }
            open(context);
            new ProcessThread(context, Constants.FLAG_RESUME).start();
            DetectionHandler.getInstance(context).startDetection();
        } catch (Exception ex) {
            XGLogger.e("Exception occurred in .onResume(). ");
        }
    }

    /**
     * Call in main Activity.onPause method.Required.
     * 
     * @param context
     */
    public static void onPause(Context context) {
        try {
            if (context == null) {
                XGLogger.e("unexpected null context");
                return;
            }
            Common.sendCountEvent(context);
            close(context);
            new ProcessThread(context, Constants.FLAG_PAUSE).start();
            DetectionHandler.getInstance(context).stopDetection();
        } catch (Exception ex) {
            XGLogger.e("Exception occurred in .onRause(). ");
        }
    }

    /**
     * Report while catch exception/error
     * 
     * @param context
     * @param ex
     */
    public static void onError(Context context, Throwable ex) {
        try {
            if (context == null) {
                XGLogger.e("unexpected null context");
                return;
            }
            new ProcessThread(context, Constants.ACTION_ERROR,
                    Common.getErrorData(context, ex, false), null,
                    Constants.FLAG_ERROR).start();
        } catch (Exception e) {
            XGLogger.e("Exception occurred in .onError(). ", e);
        }
    }

    public static void onError(Context context, String error) {
        try {
            if (context == null) {
                XGLogger.e("unexpected null context");
                return;
            }
            new ProcessThread(context, Constants.ACTION_ERROR,
                    Common.getErrorData(context, error, false), null,
                    Constants.FLAG_ERROR).start();
        } catch (Exception e) {
            XGLogger.e("Exception occurred in .onError(). ", e);
        }
    }

    /**
     * Report once each event happening.eventId:1
     * 
     * @param context
     * @param eventId
     */
    public static void onEvent(Context context, String eventId) {
        onEvent(context, eventId, null);
    }

    /**
     * Report once each event happening.eventId:1
     * 
     * @param context
     * @param eventId
     * @param otherMap expand data
     */
    public static void onEvent(Context context, String eventId,
            HashMap<String, String> otherMap) {
        try {
            new ProcessThread(context, Constants.ACTION_BEHAVE,
                    Common.getEventData(eventId, 1),
                    Common.getOtherData(otherMap), Constants.FLAG_EVENT)
                    .start();
        } catch (Exception ex) {
            XGLogger.e("Exception occurred in .onEvent(). ", ex);
        }
    }

    /**
     * Report every 30 times. eventId:30
     * 
     * @param context
     * @param eventId
     */
    public static void onEventCount(Context context, String eventId) {
        onEventCount(context, eventId, null);
    }

    /**
     * Report every 30 times. eventId:30
     * 
     * @param context
     * @param eventId
     * @param otherMap expand data
     */
    private static void onEventCount(Context context, String eventId,
            HashMap<String, String> otherMap) {
        try {
            int count = Common.getEventCount(context, eventId);
            if (count >= Constants.COUNT_REPORT_THRESHOLD) {
                JSONObject data = Common.getEventCountData();
                new ProcessThread(context, Constants.ACTION_BEHAVE, data,
                        Common.getOtherData(otherMap),
                        Constants.FLAG_EVENT_COUNT).start();
                Common.cleanEventCount();
            } else {
                Common.eventCount(context, eventId);
            }
        } catch (Exception ex) {
            XGLogger.e("Exception occurred in .onEventCount(). ", ex);
        }
    }

    /**
     * Report only once during the whole life of the game.
     * 
     * @param context
     * @param eventId
     */
    public static void onEventOnce(Context context, String eventId) {
        onEventOnce(context, eventId, null);
    }

    /**
     * Report only once during the whole life of the game.
     * 
     * @param context
     * @param eventId
     * @param otherMap expand data
     */
    public static void onEventOnce(Context context, String eventId,
            HashMap<String, String> otherMap) {
        try {
            if (getEventCount(context, eventId) >= 1) {
                Log.w(TAG, eventId + " has been report once. ");
            } else {
                new ProcessThread(context, Constants.ACTION_BEHAVE,
                        Common.getEventData(eventId, 1),
                        Common.getOtherData(otherMap), Constants.FLAG_EVENT)
                        .start();
                setEventCount(context, eventId, 1);
            }

        } catch (Exception ex) {
            XGLogger.e("Exception occurred in .onEventOnce(). ", ex);
        }
    }

    /**
     * Report after a period of interval.
     * 
     * @param context
     * @param eventId
     * @param interval HOUR/DAY/WEEK/MONTH
     */
    public static void onEventInterval(Context context, String eventId,
            Interval interval) {
        onEventInterval(context, eventId, interval, null);
    }

    /**
     * Report after a period of interval.
     * 
     * @param context
     * @param eventId
     * @param interval HOUR/DAY/WEEK/MONTH
     * @param otherMap expand data
     */
    public static void onEventInterval(Context context, String eventId,
            Interval interval, HashMap<String, String> otherMap) {
        try {
            Calendar cal = Calendar.getInstance();
            long last = getLastEventTime(context, eventId);
            long now = System.currentTimeMillis();
            cal.setTimeInMillis(last);
            long next = 0;
            switch (interval) {
                case HOUR:
                    cal.add(Calendar.HOUR, 1);
                    break;
                case WEEK:
                    cal.add(Calendar.WEEK_OF_YEAR, 1);
                    break;
                case MONTH:
                    cal.add(Calendar.MONTH, 1);
                    break;
                case DAY:
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    break;
            }
            next = cal.getTimeInMillis();
            if (Math.abs(next - now) <= 5 * 60 * 1000 || last < 0) {
                new ProcessThread(context, Constants.ACTION_BEHAVE,
                        Common.getEventData(eventId, 1),
                        Common.getOtherData(otherMap), Constants.FLAG_EVENT)
                        .start();
                setLastEventTime(context, eventId);
            } else {
                XGLogger.i("The interval time hasn't arrived.Next report time is "
                        + Constants.SDF.format(new Date(next)));
            }
        } catch (Exception ex) {
            XGLogger.e("Exception occurred in .onEventInterval(). ", ex);
        }
    }

    private static void open(Context context) {
        open(context, null);
    }

    private static void open(Context context, HashMap<String, String> other) {
        try {
            new ProcessThread(context, Constants.ACTION_GAME_OPEN,
                    Common.getDeviceData(context), Common.getOtherData(other),
                    Constants.FLAG_GAME_ACTION).start();
        } catch (Exception ex) {
            XGLogger.e("Exception occurred in .open(). ", ex);
        }
    }

    private static void close(Context context) {
        close(context, null);
    }

    private static void close(Context context, HashMap<String, String> other) {
        try {
            new ProcessThread(context, Constants.ACTION_GAME_CLOSE, null,
                    Common.getOtherData(other), Constants.FLAG_GAME_ACTION)
                    .start();
        } catch (Exception ex) {
            XGLogger.e("Exception occurred in .close(). ", ex);
        }
    }

    /**
     * Call while game login.
     * 
     * @param context
     * @param roleName name of login role
     * @param balance the number of remaining ingot.
     */
    public static void login(Context context, String roleName, int balance) {
        login(context, roleName, balance, null);
    }

    /**
     * Call while game login.
     * 
     * @param context
     * @param roleName name of login role
     * @param balance the number of remaining ingot.
     * @param other expand data
     */
    public static void login(Context context, String roleName, int balance,
            HashMap<String, String> other) {
        try {
            new ProcessThread(context, Constants.ACTION_GAME_LOGIN,
                    Common.getLoginData(context, roleName, balance),
                    Common.getOtherData(other), Constants.FLAG_GAME_ACTION)
                    .start();

        } catch (Exception ex) {
            XGLogger.e("Exception occurred in .login(). ", ex);
        }
    }

    /**
     * Call while game logout.
     * 
     * @param context
     */
    @Deprecated
    public static void logout(Context context) {
        logout(context, null);
    }

    /**
     * Call while game logout.
     * 
     * @param context
     * @param other expand data
     */
    @Deprecated
    public static void logout(Context context, HashMap<String, String> other) {
        try {
            new ProcessThread(context, Constants.ACTION_GAME_LOGOUT, null,
                    Common.getOtherData(other), Constants.FLAG_GAME_ACTION)
                    .start();
        } catch (Exception ex) {
            XGLogger.e("Exception occurred in .logout(). ", ex);
        }
    }

    /**
     * Call while consume.
     * 
     * @param context
     * @param consumeId id of consume
     * @param goodsNum amount of goods
     * @param ingot unit price of goods
     * @param category category of goods
     */
    public static void consume(Context context, String consumeId, int goodsNum,
            int unitPrice, String category) {
        try {
            consume(context, consumeId, goodsNum, unitPrice, category, null);
        } catch (Exception ex) {
            XGLogger.e("Exception occurred in .consume(). ", ex);
        }
    }

    /**
     * Call while consume.
     * 
     * @param context
     * @param consumeId id of consume
     * @param goodsNum goodsNum of goods
     * @param unitPrice unit price of goods
     * @param category category of goods
     * @param other expand data
     */
    public static void consume(Context context, String consumeId, int goodsNum,
            int unitPrice, String category, HashMap<String, String> other) {
        try {
            new ProcessThread(context, Constants.ACTION_GAME_CONSUME,
                    Common.getConsumeData(context, consumeId, goodsNum,
                            unitPrice, category), Common.getOtherData(other),
                    Constants.FLAG_GAME_ACTION).start();
        } catch (Exception ex) {
            XGLogger.e("Exception occurred in .consume(). ", ex);
        }
    }

    public static void roleLevelup(Context context, String roleLevel) {
        levelup(context, roleLevel, null, null);
    }

    public static void vipLevelup(Context context, String vipLevel) {
        levelup(context, null, null, vipLevel);
    }

    public static void newbieGuide(Context context, String newbieStep) {
        levelup(context, null, newbieStep, null);
    }

    /**
     * Call while game level up.
     * 
     * @param context
     * @param roleLevel
     * @param newbieStep
     * @param vipLevel expand data
     */
    private static void levelup(Context context, String roleLevel,
            String newbieStep, String vipLevel) {
        levelup(context, roleLevel, newbieStep, vipLevel, null);
    }

    /**
     * Call while game level up.
     * 
     * @param context
     * @param roleLevel
     * @param newbieStep
     * @param vipLevel
     * @param other
     */
    public static void levelup(Context context, String roleLevel,
            String newbieStep, String vipLevel, HashMap<String, String> other) {
        try {
            new ProcessThread(context, Constants.ACTION_GAME_LEVELUP,
                    Common.getUplevelData(context, roleLevel, newbieStep,
                            vipLevel), Common.getOtherData(other),
                    Constants.FLAG_GAME_ACTION).start();
        } catch (Exception ex) {
            XGLogger.e("Exception occurred in .levelup(). ", ex);
        }
    }

    private static void order(Context context, String orderId, String billingId,
            float money, int type, String payWay, String payTime, String status) {
        order(context, orderId, billingId, money, type, payWay, payTime,
                status, null);
    }

    private static void order(Context context, String orderId, String billingId,
            float money, int type, String payWay, String payTime,
            String status, HashMap<String, String> other) {
        try {
            new ProcessThread(context, Constants.ACTION_GAME_ORDER,
                    Common.getOrderData(context, orderId, billingId, money,
                            type, payWay, payTime, status),
                    Common.getOtherData(other), Constants.FLAG_GAME_ACTION)
                    .start();
        } catch (Exception ex) {
            XGLogger.e("Exception occurred in .order(). ", ex);
        }
    }

    public static void reward(Context context, String rewardId, int ingot,
            int type) {
        pay(context, rewardId, 0, ingot, type, PaymentType.OTHER,
                null);
    }

    public static void pay(Context context, String orderId, float money,
            int ingot, PaymentType payment) {
        pay(context, orderId, money, ingot, 0, payment, null);
    }

    private static void pay(Context context, String orderId, float money,
            int ingot, int type, PaymentType payment,
            HashMap<String, String> other) {
        try {
            if (TextUtils.isEmpty(orderId)) {
                XGLogger.e("orderId can not be null!");
                return;
            }
            new ProcessThread(context, Constants.ACTION_GAME_PAY,
                    Common.getPayData(context, orderId, money, ingot,
                            type, payment), Common.getOtherData(other),
                    Constants.FLAG_GAME_ACTION).start();

        } catch (Exception ex) {
            XGLogger.e("Exception occurred in .pay(). ", ex);
        }
    }

    public static void onExitOrKillProgress(Context context) {
        try {
            XGLogger.w("onExitOrKillProgress");
            Common.storeCountEvent(context);
            Thread.sleep(100);
            close(context);
        } catch (Exception ex) {
            XGLogger.e("Exception occurred in .onExitOrKillProgress(). ", ex);
        }
    }

    private static int getEventCount(Context context, String eventId) {
        SharedPreferences pref = Common.getSharedPreferences(context);
        return pref.getInt(eventId + Constants.SUFFIX_COUNT, 0);
    }

    private static void setEventCount(Context context, String eventId, int count) {
        SharedPreferences pref = Common.getSharedPreferences(context);
        pref.edit().putInt(eventId + Constants.SUFFIX_COUNT, count).commit();
    }

    private static long getLastEventTime(Context context, String eventId) {
        SharedPreferences pref = Common.getSharedPreferences(context);
        return pref.getLong(eventId + Constants.SUFFIX_TIME, -1);
    }

    private static void setLastEventTime(Context context, String eventId) {
        SharedPreferences pref = Common.getSharedPreferences(context);
        pref.edit()
                .putLong(eventId + Constants.SUFFIX_TIME,
                        System.currentTimeMillis()).commit();
    }

}
