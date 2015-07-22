
package com.xgsdk.client.agent;

import com.xgsdk.client.core.util.XGLogger;
import com.xgsdk.client.simulator.SimulateAgent;

public class SDKFactory {

    private static final String IMPL_SDK_CLASS = "com.xgsdk.client.XGAgentImpl";
    private static final String IMPL_SDK_V1_CLASS = "com.seasun.xgsdk.XGSDKImpl";

    private static XGAgent sSDK = null;

    public static XGAgent getSDK() {
        if (sSDK == null) {
            synchronized (SDKFactory.class) {
                try {
                    if (sSDK == null) {
                        sSDK = (XGAgent) Class.forName(IMPL_SDK_CLASS)
                                .newInstance();
                        XGLogger.w("Create a v2 xgsdk instance. "
                                + IMPL_SDK_CLASS);
                    }

                } catch (Exception e) {
                    XGLogger.d("create " + IMPL_SDK_CLASS + " instance error",
                            e);
                }

                try {
                    if (sSDK == null) {
                        Class.forName(IMPL_SDK_V1_CLASS);
                        sSDK = new XGV1AdapterAgent();
                        XGLogger.w("Create a v1 xgsdk instance. "
                                + IMPL_SDK_CLASS);
                    }
                } catch (Exception e) {
                    XGLogger.d("create " + IMPL_SDK_V1_CLASS
                            + " instance error", e);
                }

                try {
                    if (sSDK == null) {
                        sSDK = new SimulateAgent();
                        XGLogger.w("Create a simulate xgsdk instance. ");
                    }

                } catch (Exception e) {
                    XGLogger.d("create simulate sdk instance error", e);
                }
            }
        }

        return sSDK;
    }

}
