
package com.xgsdk.client.data.network;

import com.xgsdk.client.core.util.XGLogger;
import com.xgsdk.client.data.Constants;
import com.xgsdk.client.data.XGDataAgent;
import com.xgsdk.client.data.process.DataPackager;
import com.xgsdk.client.data.util.Base64Util;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class TcpTransmitter implements IDataTransmitter {
    private static final String LOG_TAG = XGDataAgent.TAG;

    private Context mContext;
    private String mHost;
    private int mPort;

    public TcpTransmitter(Context context, String host, int port) {
        mContext = context;
        mHost = host;
        mPort = port;
    }

    @Override
    public String[] send(JSONObject... jsons) {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader br = null;
        InputStream is = null;
        String[] result = null;

        if (jsons == null || jsons.length == 0) {
            Log.w(LOG_TAG, "send jsons cannot be null");
            return null;
        }

        try {
            socket = new Socket();
            SocketAddress address = new InetSocketAddress(mHost, mPort);
            socket.connect(address, 5000);

            out = new PrintWriter(socket.getOutputStream());
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            result = new String[jsons.length];
            for (int i = 0; i < jsons.length; i++) {
                if (!socket.isOutputShutdown()) {
                    JSONObject json = jsons[i];
                    json.put(Constants.KEY_SEND_TIME,
                            DataPackager.getTimeStamp());
                    XGLogger.d("send \n" + json + "\n");

                    // out.print(Base64.encodeToString(jsons[i].toString()
                    // .getBytes("UTF-8"), Base64.DEFAULT));
                    out.print(new String(Base64Util.encode(json.toString()
                            .getBytes("UTF-8")), "UTF-8"));// unchunks the
                                                           // encoded output
                                                           // into 76 character
                                                           // blocks
                    out.print("\n");
                    out.flush();
                }

                if (!socket.isInputShutdown()) {
                    result[i] = br.readLine();
                }
            }

        } catch (Exception e) {
            XGLogger.e("send datas error", e);
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
                if (out != null) {
                    out.close();
                }
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                XGLogger.e("close resource error", e);
            }
        }
        return result;
    }
}
