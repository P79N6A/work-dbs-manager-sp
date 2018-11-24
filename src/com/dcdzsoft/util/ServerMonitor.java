package com.dcdzsoft.util;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;

import com.dcdzsoft.EduException;
import com.dcdzsoft.business.GServer;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.ControlParam;
import com.dcdzsoft.constant.ErrorCode;
import com.dcdzsoft.email.EmailInfo;
import com.dcdzsoft.email.EmailSenderManager;
import com.sun.management.OperatingSystemMXBean;

/**
 * 
 * <p>Title: 智能自助包裹柜系统</p>
 *
 * <p>Description: 服务器监控</p>
 *
 * <p>Copyright: Copyright (c) 2016</p>
 *
 * <p>Company: 杭州东城电子有限公司</p>
 *
 * @author zhengxy
 * @version 1.0
 */
public class ServerMonitor {
    private final static int MAX_BUFFER_SIZE = 1000;
    private final static double MB = (1024.0 * 1024);
    private final static SimpleDateFormat dateFromat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private static double g_PhysicalFreeMemoryPercentMin = 100.0;//最小物理空闲内存比例
    private static double g_RuntimeFreeMemoryPercentMin = 100.0;//最小运行空闲内存比例
    private static int g_TotalThreadMax = 0;
    private static int g_ActiveThreadMax = 0;
    
    private static java.util.Date serverStartTime = new java.util.Date();//系统启动时间
    /**
     * 获取应用服务器时间
     * @return
     */
    public static String getServerTime(){
        String nowDatetimeStr = "["+dateFromat.format(new java.util.Date())+"] ";
        return nowDatetimeStr;
    }
    public static void init(){
        g_PhysicalFreeMemoryPercentMin = 100.0;//最小物理空闲内存比例
        g_RuntimeFreeMemoryPercentMin = 100.0;//最小运行空闲内存比例
        g_TotalThreadMax = 0;
        g_ActiveThreadMax = 0;
    }
    /**
     * 获取应用服务器信息
     * @return
     */
    public static String getServerInfo(){
        StringBuffer info = new StringBuffer();
        info.append("Server: ["+ApplicationConfig.getInstance().getServerID());
        info.append(","+ApplicationConfig.getInstance().getServerIP());
        info.append(","+ApplicationConfig.getInstance().getServerPort()+"]\n");
        info.append("Master Server: "+ControlParam.getInstance().getMasterServerID()+"\n");
        info.append("Server Soft Version: "+GServer.getSoftVersion()+"\n");
        info.append("Server Start: "+dateFromat.format(serverStartTime)+"\n");
        info.append("Monitor Email: "+ApplicationConfig.getInstance().getSystemServiceEmail()+"\n");
        return info.toString();
    }
    /**
     * 获取应用服务器系统信息
     * @return
     */
    public static String getServerOSInfo(){
        StringBuffer info = new StringBuffer();
        // 操作系统  
        String osName = System.getProperty("os.name");  
        
        info.append("------------------------ Server System Info ------------------------\n");
        info.append(getServerInfo());    
        info.append("OS : " + osName + "\n");
        info.append("JDK: " + System.getProperty("java.version")+ "\n");
        //info.append("Tomcat: " + application.getServerInfo()+ "\n");
        info.append("Tomcat_HOME: " + System.getProperty("catalina.home")+ "\n");
        info.append("User_HOME: "+System.getProperty("user.home")+ "\n");
        return info.toString();
    }
    /**
     * 获取应用服务器内存信息
     * @return
     */
    public static String getServerMemoryInfo(){
        String nowDatetimeStr = getServerTime();
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        
        StringBuffer info = new StringBuffer();
        info.append("------------------------ Memory  ------------------------\n");
        // 总的物理内存  
        double totalMemorySize = osmxb.getTotalPhysicalMemorySize() / MB;  
        // 剩余的物理内存  
        double freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / MB;  
        // 已使用的物理内存  
        double usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize())/ MB;  
        info.append(nowDatetimeStr+"Physical.totalMemory = " + totalMemorySize + " MB\n");
        info.append(nowDatetimeStr+"Physical.freeMemory  = " + freePhysicalMemorySize + " MB\n");
        info.append(nowDatetimeStr+"Physical.usedMemory  = " + usedMemory + " MB\n");
        info.append("\n");
        double totalMemory = (Runtime.getRuntime().totalMemory()) /MB ;
        double maxMemory = (Runtime.getRuntime().maxMemory()) / MB;
        double freeMemory = (Runtime.getRuntime().freeMemory()) / MB;
        
        if(g_PhysicalFreeMemoryPercentMin<(freePhysicalMemorySize/totalMemorySize)){
            g_PhysicalFreeMemoryPercentMin = (freePhysicalMemorySize/totalMemorySize);
        }
        if(g_RuntimeFreeMemoryPercentMin<(freeMemory/totalMemory)){
            g_RuntimeFreeMemoryPercentMin = (freeMemory/totalMemory);
        }
        
        info.append(nowDatetimeStr+"JVM.maxMemory   = " + maxMemory + " MB\n");
        info.append(nowDatetimeStr+"JVM.totalMemory = " + totalMemory + " MB\n");
        info.append(nowDatetimeStr+"JVM.freeMemory  = " + freeMemory + " MB\n");
        info.append(nowDatetimeStr+"JVM.usedMemory  = " + (totalMemory - freeMemory) + " MB\n");
        info.append(nowDatetimeStr+"JVM.avaiMemory  = " + (maxMemory - totalMemory + freeMemory) + "MB\n");// (maxMemory-totalMemory+freeMemory)
        return info.toString();
    }
    /**
     * 获取应用服务器线程信息
     * @return
     */
    public static String getServerThreadInfo(){
        String nowDatetimeStr = getServerTime();
        StringBuffer info = new StringBuffer();
        // 获得线程总数 
        info.append("------------------------ Thread  ------------------------\n");
        ThreadGroup parentThread;  
        for (parentThread = Thread.currentThread().getThreadGroup(); parentThread.getParent() != null; parentThread = parentThread.getParent())  
            ;  
        int totalThread = parentThread.activeCount();  
        int activeThreads = Thread.activeCount();
        info.append(nowDatetimeStr+"App.ActiveThreads      =" + activeThreads + "\n");
        info.append(nowDatetimeStr+"JVM.TotalActiveThreads = " + totalThread + "\n");
        
        if(g_TotalThreadMax> totalThread){
            g_TotalThreadMax = totalThread;
        }
        if(g_ActiveThreadMax> activeThreads){
            g_ActiveThreadMax = activeThreads;
        }
        return info.toString();
    }
    public static String getRunMonitorInfo(){
        String nowDatetimeStr = getServerTime();
        StringBuffer info = new StringBuffer();
        // 
        runMonitor();
        info.append("------------------------ Run Monitor Info  ------------------------\n");
        info.append(nowDatetimeStr+"PhysicalFreeMemoryMin     = " + g_PhysicalFreeMemoryPercentMin + "%\n");
        info.append(nowDatetimeStr+"RuntimeFreeMemoryMin      = " + g_RuntimeFreeMemoryPercentMin + "%\n");
        info.append(nowDatetimeStr+"JVM.TotalActiveThreadsMax = " + g_TotalThreadMax + "\n");
        info.append(nowDatetimeStr+"App.ActiveThreadsMax      = " + g_ActiveThreadMax + "\n");
        return info.toString();
    }
    public static void runMonitor(){
        System.gc();
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        // 总的物理内存  
        double totalMemorySize = osmxb.getTotalPhysicalMemorySize() / MB;  
        // 剩余的物理内存  
        double freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / MB;  
        
        // 已使用的物理内存  
        //double usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize())/ MB;  
        double totalMemory = (Runtime.getRuntime().totalMemory()) /MB ;
        //double maxMemory = (Runtime.getRuntime().maxMemory()) / MB;
        double freeMemory = (Runtime.getRuntime().freeMemory()) / MB;
        
        if(g_PhysicalFreeMemoryPercentMin > ((freePhysicalMemorySize/totalMemorySize)*100)){
            g_PhysicalFreeMemoryPercentMin = ((freePhysicalMemorySize/totalMemorySize)*100);
        }
        if(g_RuntimeFreeMemoryPercentMin > ((freeMemory/totalMemory)*100)){
            g_RuntimeFreeMemoryPercentMin = ((freeMemory/totalMemory)*100);
        }
        
        ThreadGroup parentThread;  
        for (parentThread = Thread.currentThread().getThreadGroup(); parentThread.getParent() != null; parentThread = parentThread.getParent())  
            ;  
        int totalThread = parentThread.activeCount();  
        int activeThreads = Thread.activeCount();
        if(totalThread>g_TotalThreadMax ){
            g_TotalThreadMax = totalThread;
        }
        if(activeThreads>g_ActiveThreadMax){
            g_ActiveThreadMax = activeThreads;
        }
    }
    public static String getServerReport(){
        StringBuffer reportInfo = new StringBuffer();
        reportInfo.append(getServerOSInfo());
        reportInfo.append(getServerMemoryInfo());
        reportInfo.append(getServerThreadInfo());
        reportInfo.append(getRunMonitorInfo());
        return reportInfo.toString();
    }
    public static String sendMonitorEmail(){
        String toEmailAddress = ApplicationConfig.getInstance().getSystemServiceEmail();
        String content  = sendMonitorEmail(toEmailAddress);
        return content;
    }
    public static String sendMonitorEmail(String toEmailAddress){
        String subject = "Elocker Server Monitor Report";
        String content  = getServerReport();
        if(StringUtils.isEmpty(toEmailAddress)){
            return content;
        }
        content = content.replaceAll("\n", "<br/>");
        //#start 设置邮箱账户
        EmailSenderManager sender = EmailSenderManager.getInstance();
        EmailInfo mailInfo = new EmailInfo();
        mailInfo.setToAddress(toEmailAddress); //目的地址
        //#end
        
        //#start 设置邮件内容
        mailInfo.setSubject(subject); 
        StringBuffer contentBuff = new StringBuffer(content);
        mailInfo.setContent(contentBuff.toString());
        //#end
        
        try {
            sender.sendHtmlMail(mailInfo);
        } catch (Exception e) {
            // TODO Auto-generated catch block
        }
        init();
        return content;
    }
}
