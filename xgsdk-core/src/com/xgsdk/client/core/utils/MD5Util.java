
package com.xgsdk.client.core.utils;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static byte[] md5(byte[] byteData, int count) {
        byte[] md5 = null;

        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.update(byteData, 0, count);
            md5 = algorithm.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return md5;
    }

    public static String md5(String string) {
        byte[] data = string.getBytes();

        byte[] md5 = md5(data, data.length);
        if (null != md5) {
            return byte2hex(md5);
        }
        return null;
    }

    public static String md5(File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            return null;
        }

        InputStream fis = null;
        byte[] buffer = new byte[1024];
        int numRead = 0;
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            while ((numRead = fis.read(buffer)) > 0) {
                algorithm.update(buffer, 0, numRead);
            }
            return byte2hex(algorithm.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
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
        return null;
    }

    protected static char[] Digit = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
            'D', 'E', 'F'
    };

    public static String byte2hex(byte[] bytearray) {

        int nSize = bytearray.length;
        char[] charHex = new char[nSize * 2];

        for (int i = 0; i < nSize; i++) {
            byte hm = bytearray[i];
            charHex[i * 2] = Digit[(hm >>> 4) & 0X0F];
            charHex[i * 2 + 1] = Digit[hm & 0X0F];
        }

        return new String(charHex);
    }

    public static String subMd5(String str) {
        String md5 = md5(str);
        if (TextUtils.isEmpty(md5) || md5.length() < 32) {
            return md5;
        }

        return md5.substring(8, 24);
    }
}
