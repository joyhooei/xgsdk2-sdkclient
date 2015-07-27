
package com.xgsdk.client.core.http;

import com.xgsdk.client.core.util.XGLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class HttpUtils {
    /**
     * http超时时间
     */
    public final static int DEFAULT_HTTP_CONNECT_TIMEOUT = 3000;
    public final static int DEFAULT_THREAD_WAIT_TIMEOUT = 30000;

    /**
     * http get请求实现
     * 
     * @param urlsrc url地址
     * @return
     */
    public static String executeHttpGet(String urlsrc) {
        return executeHttpGet(urlsrc, true);
    }

    /**
     * http get请求实现
     * 
     * @param urlsrc url地址
     * @return
     */
    public static String executeHttpGet(String urlsrc, boolean printLog) {
        String result = null;
        URL url = null;
        HttpURLConnection connection = null;
        InputStreamReader in = null;
        try {
            url = new URL(urlsrc);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(DEFAULT_HTTP_CONNECT_TIMEOUT);
            connection.setReadTimeout(DEFAULT_HTTP_CONNECT_TIMEOUT);
            connection.setRequestProperty("Connection", "close");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "text/plain");
            connection.setRequestProperty("Content-Encoding", "gzip");
            in = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);
            StringBuffer strBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();
            XGLogger.d("url=" + urlsrc, "result=" + result);
        } catch (Exception e) {
            XGLogger.e("execute http error", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    XGLogger.e("close input stream error.", e);
                }
            }
        }
        return result;
    }

    public static String doGetInThread(final String urlSrc) throws Exception {
        Callable<String> callable = new Callable<String>() {
            public String call() throws Exception {
                return executeHttpGet(urlSrc);
            }
        };
        FutureTask<String> future = new FutureTask<String>(callable);
        Thread thread = new Thread(future);
        thread.start();
        thread.join(30000);
        return future.get();
    }
}
