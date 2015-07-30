
package com.xgsdk.client.core.util;

import java.util.Properties;

public class ConfigUtil {
    private static String ENV_KEY_XSJ_DEBUG = "XG_DEBUG";
    private static String CONFIG_FILE = "xg.cfg";

    private static Properties sProperties;

    private ConfigUtil() {
    }

    public static boolean isEnvDebug() {

        if (sProperties == null) {
            sProperties = PropertiesUtil.loadPropertiesFromSdcard(CONFIG_FILE);
        }

        boolean debug = Boolean.parseBoolean(sProperties.getProperty(
                ENV_KEY_XSJ_DEBUG, Boolean.FALSE.toString()));

        return debug;
    }
}
