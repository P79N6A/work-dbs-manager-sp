package com.dcdzsoft.servlet;

import java.io.File;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.Velocity;

import com.dcdzsoft.client.web.QYWebClientAdapter;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.config.BusinessConfig;
import com.dcdzsoft.constant.ControlParam;
import com.dcdzsoft.email.EmailSenderManager;
import com.dcdzsoft.ppcapi.SendItemManager;
import com.dcdzsoft.sms.SMSManager;
import com.dcdzsoft.util.DateUtils;
import com.dcdzsoft.business.GServer;

//@WebServlet(urlPatterns={"/InitServlet"},loadOnStartup=1)

public class InitServlet extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	//Initialize global variables
    public void init() throws ServletException
    {
    	ApplicationConfig apConfig = ApplicationConfig.getInstance();
        BusinessConfig busiCfg = BusinessConfig.getInstance();

        ServletContext context = getServletContext();
        String realPath = context.getRealPath("/");
        if(!realPath.endsWith("/"))
        	realPath = realPath + "/";
        
        String fileName = realPath + "WEB-INF/appconfig.xml";
        String busifileName = realPath + "WEB-INF/busiconfig.xml";
        String vmPath = realPath + "WEB-INF/vm/";

        //设置真实的物理路径
        apConfig.setPhysicalPath(realPath);
        
        apConfig.setAppDataLocalPath(realPath+"../../share/data/");
        File f0 = new File(apConfig.getAppDataLocalPath());
        if (!f0.isDirectory())
            f0.mkdirs();
        
        f0 = new File(apConfig.getPhysicalDataPath());
        if (!f0.isDirectory())
            f0.mkdirs();
        
        f0 = new File(apConfig.getFullImagesFilePath());
        if (!f0.isDirectory())
            f0.mkdirs();
        
        f0 = new File(apConfig.getFullLockerPicFilePath());
        if (!f0.isDirectory())
            f0.mkdirs();
        
        f0 = new File(apConfig.getFullCompanyLogosPath());
        if (!f0.isDirectory())
            f0.mkdirs();
        
        f0 = new File(apConfig.getFullE1FilePath());
        if (!f0.isDirectory())
            f0.mkdirs();
        
        //设置上传日志的路径
        File f = new File(apConfig.getFullTerminalLogPath());
        if (!f.isDirectory())
            f.mkdirs();

        File f1 = new File(apConfig.getFullTerminalLogTmpPath());
        if (!f1.isDirectory())
            f1.mkdirs();
        
        //设置log属性值
        String logPath = realPath + "log";
        System.setProperty("log.home", logPath);

        try
        {
            org.apache.log4j.PropertyConfigurator.configure(logPath + "/log4j.properties");
            apConfig.load(fileName);

            busiCfg.load(busifileName);

            GServer.getInstance().initMemoryData();
            
            Properties p = new Properties();
            p.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, vmPath);
            p.setProperty(Velocity.INPUT_ENCODING, "utf-8");
            p.setProperty(Velocity.OUTPUT_ENCODING, "utf-8");
            Velocity.init(p);
            
            
            SMSManager.getInstance();//启动短信处理线程
            EmailSenderManager.getInstance();//发送邮件处理线程
            SendItemManager.getInstance();//
            
            System.out.println("realPath="+apConfig.getPhysicalPath()+",realDataLocalPath="+apConfig.getAppDataLocalPath());
            System.out.println("realDataPath:"+apConfig.getPhysicalDataPath());
            System.out.println(DateUtils.nowDate() + " " + DateUtils.nowTime() +" Read dcdzcomm config file(myadmin) successfully....");
            System.out.println("ServerID="+apConfig.getServerID()+",MasterServerID="+ControlParam.getInstance().getMasterServerID());
            System.out.println("Software Version:"+GServer.getSoftVersion());
            
            
            try{
                int result = QYWebClientAdapter.doBusiness("exec func");
                System.out.println("exec func = "+result);
            }catch(Exception e){
                System.err.println("[exec func error] = " + e.getMessage());
            }
        }
        catch (Exception e)
        {
            System.err.println(DateUtils.nowDate() + " " + DateUtils.nowTime() + " Read dcdzcomm config file(myadmin) failure!!!!!!" +
                               e.getMessage());
        }
        
    }

    //Clean up resources
    public void destroy()
    {

    }
}
