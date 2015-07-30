
package com.xgsdk.client.demo;

import com.xgsdk.client.ProductInfo;
import com.xgsdk.client.core.util.ToastUtil;
import com.xgsdk.client.core.util.XGLogger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;

public class TestUtil {

    public static void auth(Activity activity, String version, String authInfo) {

        sendRequestWithHttpClient(activity, ProductInfo.getXGAuthUrl(activity)
                + "/xgsdk/apiXgsdkAccount/verifySession?version=" + version
                + "&authInfo=" + authInfo);
    }

    private static void sendRequestWithHttpClient(final Activity activity,
            final String url) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                HttpClient httpCient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                XGLogger.d(url);

                try {
                    HttpResponse httpResponse = httpCient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = httpResponse.getEntity();
                        final String response = EntityUtils.toString(entity,
                                "utf-8");

                        XGLogger.w("auth = " + response);
                        activity.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                ToastUtil.showToast(activity, response);

                            }
                        });
                    }

                } catch (Exception e) {
                    XGLogger.e("auth error : ", e);
                }

            }
        }).start();

    }
}
