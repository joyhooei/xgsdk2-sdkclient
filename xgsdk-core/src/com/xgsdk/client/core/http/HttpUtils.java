
package com.xgsdk.client.core.http;

import com.xgsdk.client.core.utils.XGLog;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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

    public static String executeHttpPost(String url, String content) {
        String result = null;
        URL postUrl = null;
        InputStreamReader in = null;
        HttpURLConnection connection = null;
        DataOutputStream out = null;
        try {
            postUrl = new URL(url);
            connection = (HttpURLConnection) postUrl.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setConnectTimeout(DEFAULT_HTTP_CONNECT_TIMEOUT);
            connection.setReadTimeout(DEFAULT_HTTP_CONNECT_TIMEOUT);
            connection.setRequestProperty("Connection", "close");
            connection.setRequestProperty("Content-Type", "application/json");
            // connection.setRequestProperty("Accept", "text/plain");
            connection.setRequestProperty("Content-Encoding", "gzip");
            connection.connect();
            out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(content);
            out.flush();
            in = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);
            StringBuffer strBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();
        } catch (Exception e) {
            XGLog.e("execute http post error", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    XGLog.e("close input stream error.", e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    XGLog.e("close output stream error.", e);
                }
            }
        }

        return result;
    }

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
            // connection.setRequestProperty("Accept", "text/plain");
            connection.setRequestProperty("Content-Encoding", "gzip");
            in = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);
            StringBuffer strBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();
            XGLog.d("url=" + urlsrc, "result=" + result);
        } catch (Exception e) {
            XGLog.e("execute http get error", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    XGLog.e("close input stream error.", e);
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

    public static String doPostInThread(final String urlSrc,
            final String content) throws Exception {
        Callable<String> callable = new Callable<String>() {
            public String call() throws Exception {
                return executeHttpPost(urlSrc, content);
            }
        };
        FutureTask<String> future = new FutureTask<String>(callable);
        Thread thread = new Thread(future);
        thread.start();
        thread.join(30000);
        return future.get();
    }
}
