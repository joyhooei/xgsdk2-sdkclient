
package com.xgsdk.client.core.service;

public class PayStatus {

    public static final int STATUS_INITIAL = -1;
    public static final int STATUS_WAITTING_CHANNEL_NOTIFY = 0;
    public static final int STATUS_CANCELED = 99;
    public static final int STATUS_PAY_FAIL_WAITTING_NOTIFY_GAME = 6;
    public static final int STATUS_PAY_SUCCESS_WAITING_NOTIFY_GAME = 1;
    public static final int STATUS_PAY_FAIL_NOTIFIED_GAME = 3;
    public static final int STATUS_PAY_SUCCESS_NOTIFIED_GAME = 2;
    public static final int STATUS_NOTIFY_GAME_FAIL = 5;
}
