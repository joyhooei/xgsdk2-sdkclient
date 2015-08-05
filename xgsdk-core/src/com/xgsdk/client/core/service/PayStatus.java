
package com.xgsdk.client.core.service;

public enum PayStatus {

    UNKNOWN(-99), INITIAL(-1), WAITTING_CHANNEL_NOTIFY(0), CANCELED(99), PAY_FAIL_WAITTING_NOTIFY_GAME(
            6), PAY_SUCCESS_WAITING_NOTIFY_GAME(1), PAY_FAIL_NOTIFIED_GAME(3), PAY_SUCCESS_NOTIFIED_GAME(
            2), NOTIFY_GAME_FAIL(5);

    private int value;

    private PayStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PayStatus valueOf(int value) {
        PayStatus status = null;
        switch (value) {
            case -1:
                status = INITIAL;
                break;
            case 0:
                status = WAITTING_CHANNEL_NOTIFY;
                break;
            case 99:
                status = CANCELED;
            case 1:
                status = PAY_SUCCESS_WAITING_NOTIFY_GAME;
                break;
            case 2:
                status = PAY_SUCCESS_NOTIFIED_GAME;
                break;
            case 3:
                status = PAY_FAIL_NOTIFIED_GAME;
                break;
            case 5:
                status = NOTIFY_GAME_FAIL;
                break;
            case 6:
                status = PAY_FAIL_WAITTING_NOTIFY_GAME;
                break;
            case -99:
            default:
                status = UNKNOWN;
        }
        return status;
    }
}
