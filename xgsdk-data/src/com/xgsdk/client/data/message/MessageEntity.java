
package com.xgsdk.client.data.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class MessageEntity {

    private Head head;

    private JSONArray content;

    public MessageEntity(Head head) {
        this.head = head;
        this.content = new JSONArray();
    }

    public String getSendContent() {
        return JSON.toJSONString(this);
    }

    /**
     * @return the head
     */
    public Head getHead() {
        return head;
    }

    /**
     * @param head the head to set
     */
    public void setHead(Head head) {
        this.head = head;
    }

    /**
     * @return the content
     */
    public JSONArray getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(JSONArray content) {
        this.content = content;
    }

}
