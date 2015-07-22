
package com.xgsdk.client.core.util;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {
    private static final String LOG_TAG = "ConfigUtil";

    private static String ENV_KEY_XSJ_DEBUG = "XG_DEBUG";
    private static String CONFIG_FILE = "xg.cfg";

    private static Properties sProperties;

    private static Properties loadProperties() {
        Properties prop = new Properties();
        if (!SDCardUtil.isSDCardReady()) {
            Log.d(LOG_TAG, "sdcard not ready ");
            return prop;
        }
        File sdcard = SDCardUtil.getSDCard();
        File config = new File(sdcard, CONFIG_FILE);
        if (!config.exists() || !config.isFile()) {
            Log.d(LOG_TAG, CONFIG_FILE + " not exist ");
            return prop;
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(config);
            prop.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return prop;

    }

    private ConfigUtil() {
    }

    public static boolean isEnvDebug() {

        if (sProperties == null) {
            sProperties = loadProperties();
        }

        boolean debug = Boolean.parseBoolean(sProperties.getProperty(
                ENV_KEY_XSJ_DEBUG, Boolean.FALSE.toString()));

        return debug;
    }
}
