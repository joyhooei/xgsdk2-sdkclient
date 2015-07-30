
package com.xgsdk.client.data.network;

import org.json.JSONObject;

public interface IDataTransmitter {

    public String[] send(JSONObject... json);
}
