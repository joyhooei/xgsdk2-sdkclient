
package com.xgsdk.client.data;

public enum PaymentType {
    ALIPAY(1), TENPAY(2), UNION_PAY(3), KINGSOFT_PAY(4), CHINA_MOBILE(5), CHINA_UNICOM(
            6), CHINA_TELECOM(7), APPSTORE(8), PAYPAL(9), OTHER(0);

    private int id;

    private PaymentType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static PaymentType valueOf(int i) {
        switch (i) {
            case 0:
                return OTHER;
            case 1:
                return ALIPAY;
            case 2:
                return TENPAY;
            case 3:
                return UNION_PAY;
            case 4:
                return KINGSOFT_PAY;
            case 5:
                return CHINA_MOBILE;
            case 6:
                return CHINA_UNICOM;
            case 7:
                return CHINA_TELECOM;
            case 8:
                return APPSTORE;
            case 9:
                return PAYPAL;
            default:
                return null;
        }
    }
}
