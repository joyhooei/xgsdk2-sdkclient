
package com.xgsdk.client.core.util;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

public class SDCardUtil {

    public static boolean isSDCardReady() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    public static File getSDCard() {
        return Environment.getExternalStorageDirectory();
    }

    public static String getSdDirectory() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static long getSDFreeSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        long blockSize = sf.getBlockSize();
        long freeBlocks = sf.getAvailableBlocks();
        return freeBlocks * blockSize;
    }

    public static long getSDAllSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        long blockSize = sf.getBlockSize();
        long allBlocks = sf.getBlockCount();
        return allBlocks * blockSize;
    }

}
