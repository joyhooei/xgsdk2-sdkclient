
package com.xgsdk.client.data.network;

import com.xgsdk.client.data.Constants;

import android.content.Context;

public class TransmitterFactory {

    public static IDataTransmitter getTransmitter(Context context,
            String transType) {
        if (Constants.TRANS_TYPE_TCP.equalsIgnoreCase(transType)) {
            return new TcpTransmitter(context, Constants.HOST, Constants.PORT);
        } else {
            return null;
        }
    }

}
