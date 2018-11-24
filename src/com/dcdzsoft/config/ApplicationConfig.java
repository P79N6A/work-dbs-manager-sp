package com.dcdzsoft.config;

import java.io.File;

import com.dcdzsoft.memcached.MemCachedParam;
import com.dcdzsoft.sda.db.DataSourceUtils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;

/**
 * <p>Title: 智能自助包裹柜系统</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company:  杭州东城电子有限公司</p>
 *
 * @author wangzl
 * @version 1.0
 */
public class ApplicationConfig {
    /**
     * database config
     */
    private DataSourceUtils dbCfg = DataSourceUtils.getInstance();

    /**
     * MemCachedParam
     */
    MemCachedParam memCachedParam = MemCachedParam.getInstance();
    //错误消息提示语言
    private String locale = "";

    private String  countryCode = "";//服务国家代码
    
    //系统运行模式
    private int sysRunModel = 2; //1:转发 2:运营 3:转发运营

    //接口包名
    private String interfacePackage = ""; //
    
    //应用服务器编号
    private String serverID = "elocker_server";
    private String serverIP = "";
    private String serverPort = "8080";
    

    //业务处理线程的数量
    private int workerCount = 100; //业务处理线程的数量

    //记录自己平台的消息日志
    private boolean logRawMsg = false;

    //记录运营商消息的日志
    private boolean logMbMsg = false;
    
    //测试需要发送短信否
    private String sendShortMsg = ""; //MsgProxyDcdzsoft,MsgProxyFangzhengkuandai,MsgProxyShanghaiyz
    private String gatewayUser = "";
    private String gatewayPwd = "";
    private String smsServerIp = "";
    private String smsServerPort = "";
    
    private String gatewayField1 = "";
    private String gatewayField2 = "";
    private String gatewayField3 = "";
    private String gatewayField4 = "";
    //运营商服务器IP
    private String mbHost = "";
    //运营商服务器Port
    private String mbPort = "";
    //运营商服务器URI
    private String mbUri = "";
    //图片服务根地址
    private String imgServerUri = "";
    
    private String ftpHost = "";
    private String ftpPort = "";
    private String ftpUser = "";
    private String ftpPasswd = "";
    
    /**
     * webserver 
     */
    private String allowClientIPs = "";
    private String appKeyElockerWebProtal = "";
    
    /**
    *emailconfig
    */
    private String emailServerHost = "";
    private String emailServerPort = "";
    private String emailUser = "";
    private String emailPwd  = "";
    private String emailAddress = "";
    
    private String maintenanceEmail = "";//维修中心邮箱
    private String legalDepartmentEmail     = "";//法务部邮箱，处理丢失包裹的订单历史信息
    private String systemServiceEmail  = "";//系统服务器维护邮箱
    /**
     * memcache config
     */
    private boolean memCache = false;
    private String servers = "";
    private String weights = "";
    private int initConn = 0;
    private int minConn  = 0;
    private int maxConn  = 0;
    private int maxIdle  = 0;
    
    private int lockerSessionIdle = 0;
    
    /**
     * webapps的真实路径
     */
    private String physicalPath = "";
    
    private String physicalDataPath = "";//站点程序与数据分离，数据存储的物理路径
    private String appDataLocalPath = "";//数据存储的本地物理路径
    
    private String terminalLogPath = "terminalLog";
    private String fullTerminalLogPath;
    private String fullTerminalLogTmpPath;
    
    private String imagesFilePath = "images";
    private String fullImagesFilePath = "";
    
    private String lockerPicFilePath = "lockerpic";
    private String fullLockerPicFilePath = "";
    
    private String companyLogosPath = "logos";//公司logo
    private String fullCompanyLogosPath = "";
    
    private String e1FilePath = "e1file";//e1面单文件缓存路径
    private String fullE1FilePath = "";
    
    /**
     * 私有默认构造函数
     */
    private ApplicationConfig() {

    }

    private static class SingletonHolder {
        private static final ApplicationConfig instance = new ApplicationConfig();
    }


    /**
     * 静态工厂方法，返还此类的惟一实例
     * @return a ApplicationConfig instance
     */
    public static ApplicationConfig getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 读取配置文件
     * @param fileName String
     * @throws ConfigurationException
     * @throws IOException
     */
    public void load(String fileName) throws ConfigurationException, java.io.IOException {
        String globalPrefix = "global.";
        String mbPrefix = "mbconfig.";
        String smsPrefix = "smsconfig.";
        String dbPrefix = "dbconfig.";
        String wsPrefix = "wsconfig.";
        String emailPrefix = "emailconfig.";
        String mcPrefix = "mcconfig.";

        XMLConfiguration config = new XMLConfiguration(fileName);
        config.setEncoding("utf-8"); //设置编码
        config.setDefaultListDelimiter('~');

        //global
        this.setLocale(config.getString(globalPrefix + "locale"));
        this.setCountryCode(config.getString(globalPrefix + "countryCode"));
        this.setSysRunModel(config.getInt(globalPrefix + "sysRunModel"));
        this.setInterfacePackage(config.getString(globalPrefix + "interfacePackage"));
        this.setWorkerCount(config.getInt(globalPrefix + "workerCount"));
        this.setLogRawMsg(config.getBoolean(globalPrefix + "logRawMsg"));
        this.setLogMbMsg(config.getBoolean(globalPrefix + "logMbMsg"));
        this.setPhysicalDataPath(config.getString(globalPrefix + "appDataPath"));
        this.setServerID(config.getString(globalPrefix + "serverID"));
        this.setServerIP(config.getString(globalPrefix + "serverIP"));
        this.setServerPort(config.getString(globalPrefix + "serverPort"));
        
        this.setSendShortMsg(config.getString(smsPrefix + "sendShortMsg"));
        this.setSmsServerIp(config.getString(smsPrefix + "smsServerIp"));
        this.setSmsServerPort(config.getString(smsPrefix + "smsServerPort"));
        this.setGatewayUser(config.getString(smsPrefix + "gatewayUser"));
        this.setGatewayPwd(config.getString(smsPrefix + "gatewayPwd"));
        this.setGatewayField1(config.getString(smsPrefix + "gatewayField1"));
        this.setGatewayField2(config.getString(smsPrefix + "gatewayField2"));
        this.setGatewayField3(config.getString(smsPrefix + "gatewayField3"));
        this.setGatewayField4(config.getString(smsPrefix + "gatewayField4"));
        
        //mbconfig
        this.setMbHost(config.getString(mbPrefix + "mbHost"));
        this.setMbPort(config.getString(mbPrefix + "mbPort"));
        this.setMbUri(config.getString(mbPrefix + "mbUri"));
        
        this.setImgServerUri(config.getString(mbPrefix + "imgServerUri"));
        
        this.setFtpHost(config.getString(mbPrefix + "ftpHost"));
        this.setFtpPort(config.getString(mbPrefix + "ftpPort"));
        this.setFtpUser(config.getString(mbPrefix + "ftpUser"));
        this.setFtpPasswd(config.getString(mbPrefix + "ftpPasswd"));

        //db config
        dbCfg.setDatabaseType(config.getInt(dbPrefix + "databasetype"));
        dbCfg.setDriverClassName(config.getString(dbPrefix + "driverClassName"));
        dbCfg.setUrl(config.getString(dbPrefix + "url"));
        dbCfg.setUsername(config.getString(dbPrefix + "username"));
        dbCfg.setPassword(config.getString(dbPrefix + "password"));
        dbCfg.setMaxActive(config.getInt(dbPrefix + "maxActive"));
        dbCfg.setMaxIdle(config.getInt(dbPrefix + "maxIdle"));
        dbCfg.setMinIdle(config.getInt(dbPrefix + "minIdle"));
        dbCfg.setMaxWait(config.getInt(dbPrefix + "maxWait"));
        dbCfg.setTimeBetweenEvictionRunsMillis(config.getInt(dbPrefix + "timeBetweenEvictionRunsMillis"));
        dbCfg.setValidationQuery(config.getString(dbPrefix + "validationQuery"));
        dbCfg.setJetLag(config.getInt(dbPrefix + "jetLag"));
        
        //wsconfig
        this.setAllowClientIPs(config.getString(wsPrefix + "allowClientIPs"));
        this.setAppKeyElockerWebProtal(config.getString(wsPrefix + "appKeyElockerWebProtal"));
        
        //mcconfig
        this.setMemCache(config.getBoolean(mcPrefix+"memCache"));
        this.setServers(config.getString(mcPrefix+"servers"));
        this.setWeights(config.getString(mcPrefix+"weights"));
        this.setInitConn(config.getInt(mcPrefix+"initConn"));
        this.setMinConn(config.getInt(mcPrefix+"minConn"));
        this.setMaxConn(config.getInt(mcPrefix+"maxConn"));
        this.setMaxIdle(config.getInt(mcPrefix+"maxIdle"));
        this.setLockerSessionIdle(config.getInt(mcPrefix+"lockerSessionIdle"));
        
        //emailconfig
        this.setEmailServerHost(config.getString(emailPrefix + "emailServerHost"));
        this.setEmailServerPort(config.getString(emailPrefix + "emailServerPort"));
        this.setEmailUser(config.getString(emailPrefix + "emailUser"));
        this.setEmailPwd(config.getString(emailPrefix + "emailPwd"));
        this.setEmailAddress(config.getString(emailPrefix + "emailAddress"));
        this.setMaintenanceEmail(config.getString(emailPrefix + "maintenanceEmail"));
        this.setLegalDepartmentEmail(config.getString(emailPrefix + "legalDepartmentEmail"));
        this.setSystemServiceEmail(config.getString(emailPrefix + "systemServiceEmail"));
        
        //加载错误信息提示文件
        String errorfileName = physicalPath + "WEB-INF/locale/errorMsg_" + this.getLocale();
        ErrorMsgConfig.load(errorfileName);
    }

    public String getPhysicalPath() {
        return physicalPath;
    }

    /**
	 * @return the physicalDataPath
	 */
	public String getPhysicalDataPath() {
		if(StringUtils.isEmpty(physicalDataPath)){
			//未设置网络共享路径
			return appDataLocalPath;
		}else{
			//网络共享路径断开，使用本地路径
			if(!new File(physicalDataPath).exists()){
				return this.appDataLocalPath;
			}
		}
		return physicalDataPath;
	}

	/**
	 * @param physicalDataPath the physicalDataPath to set
	 */
	public void setPhysicalDataPath(String physicalDataPath) {
		this.physicalDataPath = physicalDataPath;
	}

	/**
	 * @return the imagesFilePath
	 */
	public String getImagesFilePath() {
		return imagesFilePath;
	}

	/**
	 * @param imagesFilePath the imagesFilePath to set
	 */
	public void setImagesFilePath(String imagesFilePath) {
		this.imagesFilePath = imagesFilePath;
	}

	public String getFullImagesFilePath() {
		this.fullImagesFilePath = this.getPhysicalDataPath()+this.imagesFilePath;
		return this.fullImagesFilePath;
	}

	public void setFullImagesFilePath(String fullImagesFilePath) {
		this.fullImagesFilePath = fullImagesFilePath;
	}

	public String getLockerPicFilePath() {
		return lockerPicFilePath;
	}

	public void setLockerPicFilePath(String lockerPicFilePath) {
		this.lockerPicFilePath = lockerPicFilePath;
	}
	
	public String getFullLockerPicFilePath() {
		fullLockerPicFilePath = this.getPhysicalDataPath()+this.lockerPicFilePath;
		return fullLockerPicFilePath;
	}
	
	public void setFullLockerPicFilePath(String fullLockerPicFilePath) {
		this.fullLockerPicFilePath = fullLockerPicFilePath;
	}

	public String getLocale() {
        return locale;
    }

    public int getSysRunModel(){
        return sysRunModel;
    }

    public void setSysRunModel(int sysRunModel)
    {
        this.sysRunModel = sysRunModel;
    }

    public int getWorkerCount(){
    	return workerCount;
    }

    public void setWorkerCount(int workerCount)
    {
    	this.workerCount = workerCount;
    }

    public String getFullTerminalLogPath() {
        return this.getPhysicalDataPath() + this.terminalLogPath;
    }

    public String getFullTerminalLogTmpPath() {
        return getFullTerminalLogPath() + "/temp";
    }

    public void setPhysicalPath(String physicalPath) {
        this.physicalPath = physicalPath;
    }

    public void setTerminalLogPath(String terminalLogPath) {
        this.terminalLogPath = terminalLogPath;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setInterfacePackage(String value)
    {
    	this.interfacePackage = value;
    }

    public String getInterfacePackage()
    {
    	return interfacePackage;
    }

    public void setMbHost(String value)
    {
        this.mbHost = value;
    }

    public String getMbHost()
    {
        return mbHost;
    }

    public void setMbPort(String value)
    {
        this.mbPort = value;
    }

    public String getMbPort()
    {
        return mbPort;
    }
    public void setCountryCode(String value)
    {
        this.countryCode = value;
    }

    public String getCountryCode()
    {
        return countryCode;
    }
    public void setMbUri(String value)
    {
        this.mbUri = value;
    }

    public void setLogRawMsg(boolean logRawMsg) {
        this.logRawMsg = logRawMsg;
    }

    public void setLogMbMsg(boolean logMbMsg) {
        this.logMbMsg = logMbMsg;
    }

    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }

    public void setFtpPasswd(String ftpPasswd) {
        this.ftpPasswd = ftpPasswd;
    }

    public void setFtpPort(String ftpPort) {
        this.ftpPort = ftpPort;
    }

    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }

    public void setFullTerminalLogPath(String fullTerminalLogPath) {
        this.fullTerminalLogPath = fullTerminalLogPath;
    }

    public void setFullTerminalLogTmpPath(String fullTerminalLogTmpPath) {
        this.fullTerminalLogTmpPath = fullTerminalLogTmpPath;
    }

    public String getMbUri()
    {
    	if(!mbUri.endsWith("/")){
    		mbUri += "/";
    	}
        return mbUri;
    }

    public boolean isLogRawMsg() {
        return logRawMsg;
    }

    public boolean isLogMbMsg() {
        return logMbMsg;
    }

    public String getFtpUser() {
        return ftpUser;
    }

    public String getFtpPort() {
        return ftpPort;
    }

    public String getFtpPasswd() {
        return ftpPasswd;
    }

    public String getFtpHost() {
        return ftpHost;
    }

    public String getTerminalLogPath() {
        return terminalLogPath;
    }
    
    /**
	 * @return the e1FilePath
	 */
	public String getE1FilePath() {
		return e1FilePath;
	}

	/**
	 * @param e1FilePath the e1FilePath to set
	 */
	public void setE1FilePath(String e1FilePath) {
		this.e1FilePath = e1FilePath;
	}

	/**
	 * @return the fullE1FilePath
	 */
	public String getFullE1FilePath() {
		fullE1FilePath = this.getPhysicalDataPath() + e1FilePath;
		return fullE1FilePath;
	}

	/**
	 * @param fullE1FilePath the fullE1FilePath to set
	 */
	public void setFullE1FilePath(String fullE1FilePath) {
		this.fullE1FilePath = fullE1FilePath;
	}

	/**
	 * @return the companyLogosPath
	 */
	public String getCompanyLogosPath() {
		return companyLogosPath;
	}

	/**
	 * @param companyLogosPath the companyLogosPath to set
	 */
	public void setCompanyLogosPath(String companyLogosPath) {
		this.companyLogosPath = companyLogosPath;
	}

	/**
	 * @return the fullCompanyLogosPath
	 */
	public String getFullCompanyLogosPath() {
		//fullCompanyLogosPath = this.getPhysicalPath() + "myadmin/"+ this.getCompanyLogosPath();
		fullCompanyLogosPath = this.getFullImagesFilePath()+"/"+this.companyLogosPath;
		return fullCompanyLogosPath;
	}

	/**
	 * @param fullCompanyLogosPath the fullCompanyLogosPath to set
	 */
	public void setFullCompanyLogosPath(String fullCompanyLogosPath) {
		this.fullCompanyLogosPath = fullCompanyLogosPath;
	}

	public String getSendShortMsg() {
        return sendShortMsg;
    }
    
    public void setSendShortMsg(String sendShortMsg ) {
        this.sendShortMsg = sendShortMsg;
    }

	public String getSmsServerIp() {
        return smsServerIp;
    }
    
    public void setSmsServerIp(String smsServerIp ) {
        this.smsServerIp = smsServerIp;
    }
    
    public String getSmsServerPort() {
        return smsServerPort;
    }
    
    public void setSmsServerPort(String smsServerPort ) {
        this.smsServerPort = smsServerPort;
    }
    
    public String getGatewayUser() {
        return gatewayUser;
    }
    
    public void setGatewayUser(String gatewayUser ) {
        this.gatewayUser = gatewayUser;
    }
    
    public String getGatewayPwd() {
        return gatewayPwd;
    }
    
    public void setGatewayPwd(String gatewayPwd ) {
        this.gatewayPwd = gatewayPwd;
    }

	public String getGatewayField1() {
		return gatewayField1;
	}

	public void setGatewayField1(String gatewayField1) {
		this.gatewayField1 = gatewayField1;
	}

	public String getGatewayField2() {
		return gatewayField2;
	}

	public void setGatewayField2(String gatewayField2) {
		this.gatewayField2 = gatewayField2;
	}

	public String getGatewayField3() {
		return gatewayField3;
	}

	public void setGatewayField3(String gatewayField3) {
		this.gatewayField3 = gatewayField3;
	}

	public String getGatewayField4() {
		return gatewayField4;
	}

	public void setGatewayField4(String gatewayField4) {
		this.gatewayField4 = gatewayField4;
	}

	public String getAllowClientIPs() {
		return allowClientIPs;
	}

	public void setAllowClientIPs(String allowClientIPs) {
		this.allowClientIPs = allowClientIPs;
	}

	public String getAppKeyElockerWebProtal() {
		return appKeyElockerWebProtal;
	}

	public void setAppKeyElockerWebProtal(String appKeyElockerWebProtal) {
		this.appKeyElockerWebProtal = appKeyElockerWebProtal;
	}
	public String getEmailServerHost() {
		return emailServerHost;
	}
	public void setEmailServerHost(String emailServerHost) {
		this.emailServerHost = emailServerHost;
	}
	public String getEmailServerPort() {
		return emailServerPort;
	}
	public void setEmailServerPort(String emailServerPort) {
		this.emailServerPort = emailServerPort;
	}
	public String getEmailUser() {
		return emailUser;
	}
	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}
	public String getEmailPwd() {
		return emailPwd;
	}
	public void setEmailPwd(String emailPwd) {
		this.emailPwd = emailPwd;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getMaintenanceEmail() {
		return maintenanceEmail;
	}
	public void setMaintenanceEmail(String maintenanceEmail) {
		this.maintenanceEmail = maintenanceEmail;
	}
	public String getLegalDepartmentEmail() {
		return legalDepartmentEmail;
	}
	public void setLegalDepartmentEmail(String legalDepartmentEmail) {
		this.legalDepartmentEmail = legalDepartmentEmail;
	}

	public String getSystemServiceEmail() {
        return systemServiceEmail;
    }

    public void setSystemServiceEmail(String systemServiceEmail) {
        this.systemServiceEmail = systemServiceEmail;
    }

    /**
	 * @return the imgServerUri
	 */
	public String getImgServerUri() {
		return imgServerUri;
	}

	/**
	 * @param imgServerUri the imgServerUri to set
	 */
	public void setImgServerUri(String imgServerUri) {
		this.imgServerUri = imgServerUri;
	}

	public String getAppDataLocalPath() {
		return appDataLocalPath;
	}

	public void setAppDataLocalPath(String appDataLocalPath) {
		this.appDataLocalPath = appDataLocalPath;
	}

	public String getServerID() {
		return serverID;
	}

	public void setServerID(String serverID) {
		this.serverID = serverID;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public boolean isMemCache() {
		return memCache;
	}

	public void setMemCache(boolean memCache) {
		this.memCache = memCache;
	}

	public String getServers() {
		return servers;
	}

	public void setServers(String servers) {
		memCachedParam.setServers(servers);
		this.servers = servers;
	}

	public String getWeights() {
		return weights;
	}

	public void setWeights(String weights) {
		memCachedParam.setWeights(weights);
		this.weights = weights;
	}

	public int getInitConn() {
		return initConn;
	}

	public void setInitConn(int initConn) {
		memCachedParam.setInitConn(initConn);
		this.initConn = initConn;
	}

	public int getMinConn() {
		return minConn;
	}

	public void setMinConn(int minConn) {
		memCachedParam.setMinConn(minConn);
		this.minConn = minConn;
	}

	public int getMaxConn() {
		return maxConn;
	}

	public void setMaxConn(int maxConn) {
		memCachedParam.setMaxConn(maxConn);
		this.maxConn = maxConn;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		memCachedParam.setMaxIdle(maxIdle);
		this.maxIdle = maxIdle;
	}

	public int getLockerSessionIdle() {
		return lockerSessionIdle;
	}

	public void setLockerSessionIdle(int lockerSessionIdle) {
		this.lockerSessionIdle = lockerSessionIdle;
	}
}
