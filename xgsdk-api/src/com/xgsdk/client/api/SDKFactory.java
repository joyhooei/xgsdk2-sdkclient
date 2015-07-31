
package com.xgsdk.client.api;

import com.xgsdk.client.core.utils.XGLog;
import com.xgsdk.client.inner.XGChannel;

class SDKFactory {

    private static final String IMPL_SDK_CLASS = "com.xgsdk.client.impl.XGChannelImpl";
    private static final String IMPL_SDK_TEST_CLASS = "com.xgsdk.client.testchannel.TestChannel";

    private static XGChannel sSDK = null;

    static XGChannel getSDK() {
        if (sSDK == null) {
            synchronized (SDKFactory.class) {
                try {
                    if (sSDK == null) {
                        sSDK = (XGChannel) Class.forName(IMPL_SDK_CLASS)
                                .newInstance();
                        XGLog.w("Create a v2 xgsdk instance. " + IMPL_SDK_CLASS);
                    }
                } catch (Exception e) {
                    XGLog.i(IMPL_SDK_CLASS + " not exist.");
                }

                try {
                    if (sSDK == null) {
                        sSDK = (XGChannel) Class.forName(IMPL_SDK_TEST_CLASS)
                                .newInstance();
                        XGLog.w("Create a simulate xgsdk instance. ");
                    }
                } catch (Exception e) {
                    XGLog.i("create simulate sdk instance error.", e);
                }
            }
        }

        return sSDK;
    }

}
