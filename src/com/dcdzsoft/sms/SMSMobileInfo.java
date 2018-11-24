package com.dcdzsoft.sms;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SMSMobileInfo implements java.io.Serializable{
    private String mobile = "";
    private long   lastSentTimestamp = 0L;//最近一次发送短信的时间
    private long   count = 0;//等待发送的短信数量
    
    public String toString () {
        return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getLastSentTimestamp() {
        return lastSentTimestamp;
    }

    public void setLastSentTimestamp(long lastSentTimestamp) {
        this.lastSentTimestamp = lastSentTimestamp;
    }
    
    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
    public void addCount() {
        count ++;
    }
    public void subCount() {
        count --;
        if(count<0){
            count=0;
        }
    }

}
