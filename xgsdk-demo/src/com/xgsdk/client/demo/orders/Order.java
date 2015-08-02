
package com.xgsdk.client.demo.orders;

import com.xgsdk.client.api.entity.PayInfo;

public class Order {

    private PayInfo pay;

    private String status;

    public PayInfo getPay() {
        return pay;
    }

    public void setPay(PayInfo pay) {
        this.pay = pay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}
