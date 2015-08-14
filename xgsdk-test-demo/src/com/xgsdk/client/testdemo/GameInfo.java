
package com.xgsdk.client.testdemo;

public class GameInfo {

    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private static GameInfo sInstance;

    private GameInfo() {
    }

    public static GameInfo getInstance() {
        if (sInstance == null) {
            synchronized (GameInfo.class) {
                if (sInstance == null) {
                    sInstance = new GameInfo();
                }
            }
        }

        return sInstance;
    }

}
