package com.dcdzsoft.email;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Synchronization;

import org.apache.commons.lang.StringUtils;

import com.dcdzsoft.EduException;

/**
 * 
 * <p>Title: 智能自助包裹柜系统</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: 杭州东城电子有限公司</p>
 *
 * @author zhengxy
 * @version 1.0
 */
public class ApiFailCount {
    private  String name = "";
    private  long lastFailCount  = 0;//最近一次统计时发送失败次数
    private  long currFailCount  = 0;//当前发送失败总次数
    private  long thresholdCount = 0;//发送通知邮件的门限值
    private  long ppcServerTimeoutCount   = 0;//ppc server 超时没有应答次数
    private  boolean isSendFailReport = true;//是否发送报告
    private  Date lastReportDateTime = new Date();
    private  Date currReportDateTime = new Date();
    private  String apiString = "";
    private  StringBuffer failMsgBuff = new StringBuffer(1024);
    private  String lastFailMsg = "";//记录最近的错误信息
    private  static final int MAX_MSG_BUFF_LENGTH = 1000;
    public static final SimpleDateFormat dateFromatSa = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    public ApiFailCount(String name){
        this.name = name;
    }
    /**
     * 发送系统错误信息给系统维护人员
     * @return
     */
    public boolean sendFailReport(){
        //System.out.println(thresholdCount+"\n");
        boolean isOk = false;
        if(currFailCount<=0
           ||thresholdCount<0//不发送
           ||(currFailCount-lastFailCount)<thresholdCount){
            return false;
        }
        String subject = this.name+" fail Report";
        StringBuffer contentBuff = new StringBuffer();
        currReportDateTime = new Date();
        contentBuff.append("Uri: "+apiString+"\n");
        contentBuff.append("Last Report DateTime: "+dateFromatSa.format(lastReportDateTime)+"\n");
        contentBuff.append("Last Fail Count: "+lastFailCount+"\n");
        contentBuff.append("Currect DateTime: "+dateFromatSa.format(currReportDateTime)+"\n");
        contentBuff.append("Total Fail Count: "+currFailCount+"\n");
        if(failMsgBuff.length()>0){
            contentBuff.append(failMsgBuff);
        }
        lastFailCount      = currFailCount;
        lastReportDateTime = currReportDateTime;
        
        EmailSenderManager sender = EmailSenderManager.getInstance();
        try {
            isOk = sender.sendSystemErrorLog(subject, contentBuff.toString());
            failMsgBuff = new StringBuffer();
        } catch (EduException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println(subject+": "+contentBuff.toString().replaceAll("\n", ";"));
        return isOk;
    }
    /**
     * 
     * @param isRecovery false 发送错误通知，   true 接口恢复正常，发送恢复正常通知
     * @return
     */
    public  boolean sendPPCServerReport(boolean isRecovery){
        boolean isOk = false;
        String subject = "";
        StringBuffer contentBuff = new StringBuffer();
        synchronized(this){
            if(isRecovery){
                if(!isSendFailReport){
                    ppcServerTimeoutCount = 0;//统计清零
                    isSendFailReport = true;//接口恢复正常，允许发送失败通知
                }else{
                    ppcServerTimeoutCount = 0;//统计清零
                    return false;
                }
                subject = "PPC Server Back to Normal";
                contentBuff.append("PPC Server Back to Normal\n");
            }else{
                if(ppcServerTimeoutCount<thresholdCount){
                    return false;
                }
                if(!isSendFailReport){
                    return false;
                }
                isSendFailReport = false;//恢复正常后，才开始发送失败通知
                subject = "PPC Server NOT Responding";
                contentBuff.append("PPC Server NOT Responding – Read TimeOut Count: "+ppcServerTimeoutCount+"\n");
            }
        }
        
        contentBuff.append("Uri: "+apiString+"\n");
        contentBuff.append("DateTime: "+dateFromatSa.format(new Date())+"\n");
        
        EmailSenderManager sender = EmailSenderManager.getInstance();
        try {
            isOk = sender.sendApiMonitorReport(subject, contentBuff.toString());
        } catch (EduException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //System.out.println(subject+": "+contentBuff.toString().replaceAll("\n", ";"));
        return isOk;
    }
    public synchronized void addFailMsg(String msg){
        //统计计数
        currFailCount++;
        ppcServerTimeoutCount++;
        
        if(StringUtils.isNotEmpty(msg)){
            Date nowDate = new Date();
            lastFailMsg = "==== ApiFail:( "+name+")"+dateFromatSa.format(nowDate)+" Count="+currFailCount+" ====\n"+msg;
            String temp = lastFailMsg+"\n"+failMsgBuff.toString();
            int endIndex = temp.length();
            if(endIndex>MAX_MSG_BUFF_LENGTH){
                endIndex = MAX_MSG_BUFF_LENGTH;
            }
            failMsgBuff = new StringBuffer(temp.subSequence(0, endIndex));
        }
    }
    public long getLastFailCount() {
        return lastFailCount;
    }

    public void setLastFailCount(long lastFailCount) {
        this.lastFailCount = lastFailCount;
    }

    public long getCurrFailCount() {
        return currFailCount;
    }

    public void setCurrFailCount(long currFailCount) {
        this.currFailCount = currFailCount;
    }

    public long getThresholdCount() {
        return thresholdCount;
    }

    public void setThresholdCount(long thresholdCount) {
        this.thresholdCount = thresholdCount;
    }

    public Date getLastReportDateTime() {
        return lastReportDateTime;
    }

    public void setLastReportDateTime(Date lastReportDateTime) {
        this.lastReportDateTime = lastReportDateTime;
    }

    public Date getCurrReportDateTime() {
        return currReportDateTime;
    }

    public void setCurrReportDateTime(Date currReportDateTime) {
        this.currReportDateTime = currReportDateTime;
    }

    public String getApiString() {
        return apiString;
    }

    public void setApiString(String apiString) {
        this.apiString = apiString;
    }
}
