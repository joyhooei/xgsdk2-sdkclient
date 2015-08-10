
package com.xgsdk.client.data.message;

import com.xgsdk.client.data.Config;
import com.xgsdk.client.data.DateUtil;

public abstract class AbsBucket implements Bucket {

    protected long startTs;

    public AbsBucket() {
        this.startTs = DateUtil.nowTs();
    }

    @Override
    public boolean isReadyToSend() {
        // 时间
        if (isTimeOut()) {
            return true;
        }
        return false;
    }

    protected boolean isTimeOut() {
        return (DateUtil.nowTs() - startTs) > (Config.timeOut * DateUtil.SECOND_MS);
    }

}
