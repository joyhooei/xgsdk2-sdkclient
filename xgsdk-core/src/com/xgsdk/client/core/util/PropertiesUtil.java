
package com.xgsdk.client.core.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static final String LOG_TAG = "PropertiesUtil";

    public static Properties loadProperties(InputStream is) {
        Properties p = new Properties();

        if (is == null) {
            return p;
        }

        try {
            p.load(is);
        } catch (IOException e) {
            Log.e(LOG_TAG, "getProperties error", e);
        }
        return p;
    }

    public static Properties loadProperties(File file) {
        Properties p = new Properties();
        if (file == null) {
            return p;
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            p = loadProperties(fis);
        } catch (FileNotFoundException e) {
            Log.e(LOG_TAG, "getProperties error." + file, e);
        }

        return p;

    }

    public static Properties loadPropertiesFromAsset(Context context,
            String filename) {
        InputStream is = null;
        Properties prop = new Properties();

        try {
            AssetManager am = context.getAssets();
            is = am.open(filename);
            prop = loadProperties(is);
        } catch (Exception e) {
            Log.e(LOG_TAG, "load properties error." + filename, e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "close is error.", e);
                }
            }
        }

        return prop;
    }

    public static Properties loadPropertiesFromSdcard(String filename) {

        Properties prop = new Properties();
        if (!SDCardUtil.isSDCardReady()) {
            Log.e(LOG_TAG, "sdcard is not ready.");
            return prop;
        }

        File sdcard = SDCardUtil.getSDCard();
        File config = new File(sdcard, filename);
        if (!config.exists() || !config.isFile()) {
            Log.e(LOG_TAG, filename + " not exist.");
            return prop;
        }

        prop = loadProperties(config);
        return prop;
    }

}
