
package com.xgsdk.client.core.http;

import com.xgsdk.client.core.utils.XGLog;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpUtils {
    /**
     * http超时时间
     */
    public final static int DEFAULT_HTTP_CONNECT_TIMEOUT = 3000;
    public final static int DEFAULT_THREAD_WAIT_TIMEOUT = 30000;

    private static ExecutorService httpService = Executors
            .newFixedThreadPool(4);

    /**
     * 非阻塞是执行http post请求
     * 
     * @param urlStr url地址
     * @param body body内容
     * @param callback 回调，执行完http请求之后回调给执行者
     */
    public static void executeHttpGet(final String urlStr,
            final IHttpExecuteCallback callback) {
        httpService.execute(new Runnable() {

            @Override
            public void run() {
                String result = null;
                URL url = null;
                HttpURLConnection connection = null;
                InputStreamReader in = null;
                try {
                    url = new URL(urlStr);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(DEFAULT_HTTP_CONNECT_TIMEOUT);
                    connection.setReadTimeout(DEFAULT_HTTP_CONNECT_TIMEOUT);
                    connection.setRequestProperty("Connection", "close");
                    connection.setRequestProperty("Content-Type",
                            "application/json");
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
                    XGLog.i("url=" + url, "result=" + result);
                    callback(200, callback, strBuffer.toString());
                } catch (Exception e) {
                    XGLog.e("execute http get error,url:" + urlStr, e);
                    callback(-1, callback, "execute http get error,url:"
                            + urlStr + ",msg:" + e.getMessage());
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
            }
        });
    }

    private static void callback(final int code,
            final IHttpExecuteCallback callback, final String ret) {
        callback.callback(code, ret);
    }

    /**
     * 非阻塞是执行http post请求
     * 
     * @param url url地址
     * @param body body内容
     * @param callback 回调，执行完http请求之后回调给执行者
     */
    public static void executeHttpPost(final String url, final String body,
            final IHttpExecuteCallback callback) {
        httpService.execute(new Runnable() {
            @Override
            public void run() {
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
                    connection.setRequestProperty("Content-Type",
                            "application/json");
                    // connection.setRequestProperty("Accept", "text/plain");
                    connection.setRequestProperty("Content-Encoding", "gzip");
                    connection.connect();

                    out = new DataOutputStream(connection.getOutputStream());
                    out.write(body.getBytes());
                    out.flush();
                    in = new InputStreamReader(connection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(in);
                    StringBuffer strBuffer = new StringBuffer();
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        strBuffer.append(line);
                    }
                    callback(200, callback, strBuffer.toString());
                } catch (Exception e) {
                    XGLog.e("execute http post error,url:" + url, e);
                    callback(-1, callback, "execute http post error,url:" + url
                            + ",msg:" + e.getMessage());
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

            }
        });
    }
}
