package com.dcdzsoft.constant;

import java.io.*;

import org.apache.commons.lang.StringUtils;

/**
 * <p>Title: 智能自助包裹柜系统</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: 杭州东城电子有限公司</p>
 *
 * @author wangzl
 * @version 1.0
 */
public final class ControlParam implements Serializable {
    /**
     * 私有默认构造函数
     */
    private ControlParam() {

    }

    private static class SingletonHolder {
        private static final ControlParam instance = new ControlParam();
    }


    /**
     * 静态工厂方法，返还此类的惟一实例
     * @return a ControlParam instance
     */
    public static ControlParam getInstance() {
        return SingletonHolder.instance;
    }

    private long dbServerAppServerTimeOffset = 0L;
    
    //11001 comapny
    private String companyName = "";
    private String companyTel  = "";
    private String companyAddress = "";
    private String companyWWW     = "";
    private String companyEmail     = "";
    //private String serviceTel = "";
    //private String msgTel = ""; //短信咨询电话
    
    //11003 Master
    private String elockerPortal = "";//elocker web portal www
    private String elockerServiceTel = "";//elocker system 服务电话
    
    private String customerAddressChange = "yes";//Customer Address Change:yes or no
    private String poBoxMonthlyRate = "0";//POBox Monthly Rate
    private String poBoxApplicationGuide = "";//POBox Application Guide
    
    private String e1ValidityPeriodDays = "";//e1ValidityPeriodDays
    //11005
    //private String buinessModel = ""; //业务处理模式(STD 标准版 XIANGYOU 湘邮 JINGDONG 京东
    //11006
    //private String networkModel = ""; //网络通讯模式(0 长连接 SJJK4HzyouzhengProxy 
    //11011
    //private String serverIP = ""; //服务器IP地址
    //private String serverPort = ""; //服务器端口
    //private String serverSSL = ""; //通讯是否采用SSL加密 false:不采用 true:采用
    //private String serverConnectTimeout = ""; //超时时间(秒)
    //11013
    private String masterServerID="";//主应用服务器ID
    
    //11031
    //public String screenProtectTime = ""; //屏保启用时间（单位：分钟）
    //public String screensoundFlag = ""; //语音提示（0关闭；1开启）
    
    //11041 原Application Configuration，移到DB的ControlParam表中
    //错误消息提示语言
    /*private String locale = "";
    private String  countryCode = "";//服务国家代码    
    //系统运行模式
    private int sysRunModel = 2; //1:转发 2:运营 3:转发运营
    //接口包名
    private String interfacePackage = ""; //
    //业务处理线程的数量
    private int workerCount = 100; //业务处理线程的数量
    //记录自己平台的消息日志
    private boolean logRawMsg = false;
    //记录运营商消息的日志
    private boolean logMbMsg = false;    
    //测试需要发送短信否
    private String sendShortMsg = ""; 
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
    */
    /**
     * webserver 
     */
    //private String allowClientIPs = "";
    //private String appKeyElockerWebProtal = "";    
    
    //功能开通控制  11081
    private String mobileBlackList = "";//手机黑名单 0:未开通 1:开通
    private String mobileFormatCheck = "";//手机格式检查： 0:未开通 1:开通
    private String sendReminderSMS = ""; //
    private String sendReSendSMS = ""; //重发短信开关：0:未开通 1:开通
    private String sendReturnSMS = "";//发送回收通知短信开关：0:未开通 1:开通
    private String messageTempletLoadMode = "";//消息模板加载方式 0:vm文件加载 1:数据库加载
    private String sendBPPwdSMS = "";//发送BP密码短信：0:未开通 1:开通（默认发送邮件通知）
    private String sendOperatorPwdSMS = "";//发送操作员密码短信：0:未开通 1:开通
    private String sendTopupMsgSMS = "";//发送充值账单短信：0:未开通 1:开通
    private String sendBalanceSMS  = "";//发送余额提醒短信：0:未开通 1:开通
    private String sendLockerAlarmSMS  = "";//发送Locker告警短信：0:未开通 1:开通
    private String modifyMobileFromLocker  = "";//在终端修改取件手机：0:未开通 1:开通
    private String renewBoxAndLockedOldBox  = "";//重新分箱时锁定原先分配的箱子：0:未开通 1:开通
    private String sendSMSTimeGapInMs = "";//短信发送时间间隔(ms)：
    private String printfSMSContent = "";//打印短信内容：0:未开通 1:开通
    //private String sendToPartner = ""; //
    
    //定期同步控制  11082
    private String loadIntervalInMs = "";//定期加载时间间隔(ms)
    private String loadFunctionToMemory = "";//定期加载日志标志到内存：0:未开通 1:开通
    private String loadCtrlParamToMemory = "";//定期加载控制参数到内存：0:未开通 1:开通
    private String loadApplicationConfigToMemory = "";//定期加载应用配置到内存：0:未开通 1:开通
    
    //接口认证 11091
    //public String appclientId  = ""; //客户端Id
    //public String appKey = ""; //应用的app_key
    
    //推送业务控制  11095
    //public String pushserviceCtrl = ""; //推送业务控制
    
    public String currentVersion = ""; //当前系统版本号
    
    
    //12001
    //private String deviceBoardPort = ""; //驱动板端口
    //private String deviceBoardModel = ""; //控制模式(1:事件通知 2:轮询)'
    
    //12002
    //private String cardReaderPort = ""; //读卡器端口
    //private String cardReaderModel = ""; //控制模式(1:事件通知 2:轮询)
    //private String cardReaderNeedFlag = ""; //是否需要读卡器(0:不需要 1:需要)

    //12003
    //private String printPort = ""; //打印端口
    //private String printModel = ""; //控制模式(1:事件通知 2:轮询 3:按需同步)
    //private String printNeedFlag = ""; //是否需要打印机(0:不需要 1:需要)

    //12004
    //private String barCodePort = ""; //条码端口
    //private String barCodeModel = ""; //控制模式(1:事件通知 2:轮询)
    //private String barNeedFlag = ""; //是否需要条码扫描(0:不需要 1:需要)

    //12005
    //private String shortMsgPort = ""; //短信端口
    //private String shortMsgModel = ""; //控制模式(1:事件通知 2:轮询 3:按需同步)
    //private String shortMsgNeedFlag = ""; //是否需要短信猫(0:不需要 1:需要)

    //12006
    //private String ledScreenPort = ""; //LED条屏端口
    //private String ledSreenModel = ""; //控制模式(1:事件通知 2:轮询 3:按需同步)
    //private String ledSreenNeedFlag = ""; //是否需要LED条屏(0:不需要 1:需要)

    //12007
    //private String posPort = ""; //POS机端口
    //private String posModel = ""; //控制模式(1:事件通知 2:轮询 4:按需异步)
    //private String posNeedFlag = ""; //是否需要POS机(0:不需要 1:需要)

    //12008
    //private String coinPort = ""; //投币机端口
    //private String coinModel = ""; //控制模式(1:事件通知 2:轮询)
    //private String coinNeedFlag = ""; //是否需要投币机(0:不需要 1:需要)

    //12009
    //private String bankCardPort = ""; //银行卡端口
    //private String bankCardModel = ""; //控制模式(1:事件通知 2:轮询)
    //private String bankCardNeedFlag = ""; //是否需要银行卡(0:不需要 1:需要)

    //12021
    //public String cameraPort = ""; //摄像端口
    //public String cameraModel = ""; //控制模式(1:事件通知 2:轮询 4:按需异步)
    //public String cameraNeedFlag = ""; //是否需要摄像头(0:不需要 1:需要)

    //12135 - 个人客户管理
    private String customerIDMode              = "0";//客户号模式，0-固定，1-动态更新（随注册的柜号变化而变化）
    private String customerIDFormat            = "1";//客户号格式:1-Locker.LockerID+"-"+Locker.Counter；2-Locker.LockerName+"-"+Locker.Counter. defualt=1
    private String customerPOBoxBandBode       = "1";//POBox地址绑定模式:1-一个手机一个地址（默认）；2-一个身份证号一个POBox地址
    private String customerExtendLimitDays     = "30";//设置延期期限（天），customer需要在期限内完成延期
    private String customerExpiredKeepDays     = "15";//设置系统保留已失效的customer账户的天数
    private String customerPOBoxRateFree       = "1";//Customer使用POBox是否免费：1-free,2-Not free
    private String poBoxAddressFirstLineFormat = "1";//1-AZC.Code+","+Customer.Code；2-AZC.Code+","+Customer.Code. defualt=1
    
    
    /*=======================管理账户管理(13001-13999);=========================*/
    //13001
    //private String systemHTPWD = ""; //前台转后台密码
    //private String spotAdminID = ""; //现场管理员编号
    //private String spotAdminPWD = ""; //现场管理员密码

    /*=======================运营信息管理(15001-15999)=========================*/
    //15001  
    //public String headerDepartmentID = ""; //运营网点根节点
    //15101  
    private String monitorEmail = ""; //API接口维护人员邮箱
    private String sendSMSFailReportCnt = ""; //发送失败次数阀值，超出后发送提醒邮件
    private String sendPPCFailReportCnt = ""; //发送失败次数阀值，超出后发送提醒邮件
    
    //private String stdSendMessage = ""; //标准的派件信息内容
    //private String stdRetrieveMessage = ""; //标准的回收信息内容
    
    //21001
    //private String articleInspectFlag = ""; // 物品检测标志(0:关闭 1:打开)
    //private String doorInspectFlag = ""; // 箱门检测标志(0:关闭 1:打开)
    //private String powerInspectFlag = ""; // 电源检测标志(0:关闭 1:打开)
    //private String shockInspectFlag = ""; // 震动防撬检测标志(0:关闭 1:打开)
    //private String boxWarnTime = ""; // 箱门异常报警时间参数(0:关闭 30秒)
    
    //21002
    //private String deskDefaultHeight = ""; //副柜默认高度
    
    //21011
    //private String existSuperLargeBox = ""; //存在超大箱体
    //private String existSameTypeBox = ""; //存在生鲜配送箱体
    
    //21021
    private String lockerStationMapQry = "";//柜体位置查询：0-提供所有，1-提供已注册柜体，2-提供正常在线柜体
    private String lockerListQry = "";//柜体列表查询：0-提供所有，1-提供已注册柜体，2-提供正常在线柜体
    /*=======================投递员管理(31001-31999)=========================*/
    //31001
    //private String postmanCheckSource = ""; //投递员身份验证地点      0:本地 1:网络
    //private String postmanCheckModel = ""; //投递员身份认证方式       0:获取动态码 1:身份认证
    //31005
    private String manTerminalRightCheck = ""; //投递员柜体权限验证模式  0:不需要 1:验证公司 2:验证本人 3:验证AZC
    private String manDADTerminalRightCheck = ""; //直投投递员柜体权限验证开关  0:不需要 1:使用manTerminalRightCheck验证
    //31006
    private String manBoxRightCheck     = ""; //投递员格口权限验证模式  0:不需要 1:验证公司 2:验证本人

    //31011
    //private String postmanMD5Flag = ""; //密码存储使用MD5加密 投递员(0:不使用 1:使用)
    //private String managerMD5Flag = ""; //密码存储使用MD5加密 管理员(0:不使用 1:使用)
    //private String passwdCheckModel = ""; //密码验证方式(0:普通MD5 1:京东MD5 2:邮政)
    
    //31021
    //private String registerModel   = ""; //投递员注册模式(0:输入验证码 1:短信获取验证码)
    private String regSentPwdSms = ""; //注册发送短信密码(0:不发送 1:发送)
    
    //31031
    //private String companyQryModel = ""; //投递公司查询模式(1:同级别或以下 2:同级别或以下或上级)
    
    /*=======================投递信息管理(33001-33999)=========================*/
    //33001
    //private String orderDeliverySource = ""; //投递订单来源 0:无 1:网络 2:本地
    //private String orderCheckFlag = ""; //订单验证标志 0:不验证 1:验证
    //private String ordersNotInList = ""; //列表外订单处理方式 0:不允许投递 1:允许投递
    //private String orderNeedNetCheck = ""; //订单需要网络验证 0:不需要 1:需要
    //private String orderFormatHandleWay = ""; //订单号格式处理方式 0:无 1:京东
    private String dadDropE1Item = "";//DAD投递E1订单 0:不允许 1:允许投递
    
    //33003
    private String assignLockerMode = "";//分配柜体的模式：0-不允许修改已分配的柜体，1-允许修改已分配的柜体
    private String assignBoxSizeMode = "";//分配箱大小的模式：0-不允许修改已分配的箱大小；1-允许修改已分配的箱大小
    
    //33011
    private String recoveryTimeout = ""; //回收超时时间（单位：天数）
    private String reminderDays = ""; //催领天数
    private String recoverySource = ""; //回收来源 1从服务器下载；2从本地库验证(终端开关)
    private String recoveryScope = ""; //回收范围 1投递员；2 AZC （后台开关）
    private String recoveryPower = ""; //回收力度 1强制回收(需要先回收后才能投递)，2非强制回收，沙特版本未提供该功能(终端开关)
    private String recoveryInputPwd = "";//回收输入密码 0 不输入，1 输入(终端开关)
    private String recoveryMode = "";    //回收模式 0 可以回收柜子中的所有包裹 1只能回收同类型的包裹(普通包裹或DAD包裹)(后台开关)
    
    //33021
    //private String takeOutPWDSource = ""; //取件密码来源 0:本地不生成 1:本地生成
    private String takeOutPwdLen = ""; //取件密码长度
    private String takeOutPwdFormat = ""; //取件密码组成格式 0:数字 1:字母 2:字母数字组合

    //33031
    private String autoLockOrder = "";
    private String takeOutCheckType = ""; //取件验证方式 1单号+密码，2手机号+密码，3单号或手机号+密码，4提货码+会员卡
    //private String takeOutCheckModel = ""; //取件验证模式 1本地验证，2网络验证
    //private String takeOutPwdMD5Type = ""; //取件密码MD5加密类型 0:标准 1:京东
    //private String refuseCloseDoor = ""; //拒关箱门（单位：次数）
    private String takeOutExpiredItemLimit = "";//客户取逾期包裹的限制， 1-不允许取人工逾期的包裹，2-不允许取自动逾期的包裹，3-不允许取逾期包裹，其他-无限制
    
    //33041
    //private String firstWrongPwdCount = ""; //初次取件密码出错次数上限'
    //private String secondWrongPwdCount = ""; //再次取件密码出错次数上限
    //private String temporaryLockTime = ""; //暂时锁定时间限制(分钟)'

    //33051 transfer
    private String transferToPPCFlag = "0";//transfer item from azc to ppc 开关
    private String transferResendTimes = "3";//transfer item from azc to ppc 自动发送次数
    private String transferItemRetainDays = "7";//发送队列中订单保留时间
    
    //51001 异常信息处理
    //private String inBoxInfoCompare = ""; //异常在箱信息比对，1需要比对，0不需要
    //private String boxInfoCompare = ""; //异常箱体信息比对，1需要比对，0不需要
    
    //55001
    private String contactTel = ""; //求助联系人电话
    private String appealTimeout = "";//求助超期时间（分钟）
    
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    /**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the companyTel
	 */
	public String getCompanyTel() {
		return companyTel;
	}

	/**
	 * @param companyTel the companyTel to set
	 */
	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	/**
	 * @return the companyAddress
	 */
	public String getCompanyAddress() {
		return companyAddress;
	}

	/**
	 * @param companyAddress the companyAddress to set
	 */
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	/**
	 * @return the companyWWW
	 */
	public String getCompanyWWW() {
		return companyWWW;
	}

	/**
	 * @param companyWWW the companyWWW to set
	 */
	public void setCompanyWWW(String companyWWW) {
		this.companyWWW = companyWWW;
	}
	
    /**
	 * @return the companyEmail
	 */
	public String getCompanyEmail() {
		return companyEmail;
	}

	/**
	 * @param companyEmail the companyEmail to set
	 */
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }
    
    /**
	 * @return the mobileBlackList
	 */
	public String getMobileBlackList() {
		return mobileBlackList;
	}

	/**
	 * @param mobileBlackList the mobileBlackList to set
	 */
	public void setMobileBlackList(String mobileBlackList) {
		this.mobileBlackList = mobileBlackList;
	}

	/**
	 * @return the mobileFormatCheck
	 */
	public String getMobileFormatCheck() {
		return mobileFormatCheck;
	}

	/**
	 * @param mobileFormatCheck the mobileFormatCheck to set
	 */
	public void setMobileFormatCheck(String mobileFormatCheck) {
		this.mobileFormatCheck = mobileFormatCheck;
	}

	public String getSendReminderSMS() {
        return sendReminderSMS;
    }
    
    public void setSendReminderSMS(String sendReminderSMS) {
        this.sendReminderSMS = sendReminderSMS;
    }

	/**
	 * @return the messageTempletLoadMode
	 */
	public String getMessageTempletLoadMode() {
		return messageTempletLoadMode;
	}

	/**
	 * @param messageTempletLoadMode the messageTempletLoadMode to set
	 */
	public void setMessageTempletLoadMode(String messageTempletLoadMode) {
		this.messageTempletLoadMode = messageTempletLoadMode;
	}

	/**
	 * @return the sendBPPwdSMS
	 */
	public String getSendBPPwdSMS() {
		return sendBPPwdSMS;
	}

	/**
	 * @param sendBPPwdSMS the sendBPPwdSMS to set
	 */
	public void setSendBPPwdSMS(String sendBPPwdSMS) {
		this.sendBPPwdSMS = sendBPPwdSMS;
	}

	/**
	 * @return the sendOperatorPwdSMS
	 */
	public String getSendOperatorPwdSMS() {
		return sendOperatorPwdSMS;
	}

	/**
	 * @param sendOperatorPwdSMS the sendOperatorPwdSMS to set
	 */
	public void setSendOperatorPwdSMS(String sendOperatorPwdSMS) {
		this.sendOperatorPwdSMS = sendOperatorPwdSMS;
	}

	public String getSendTopupMsgSMS() {
		return sendTopupMsgSMS;
	}

	public void setSendTopupMsgSMS(String sendTopupMsgSMS) {
		this.sendTopupMsgSMS = sendTopupMsgSMS;
	}

	public String getSendBalanceSMS() {
		return sendBalanceSMS;
	}

	public void setSendBalanceSMS(String sendBalanceSMS) {
		this.sendBalanceSMS = sendBalanceSMS;
	}

	/**
	 * @return the lockerStationMapQry
	 */
	public String getLockerStationMapQry() {
		return lockerStationMapQry;
	}

	/**
	 * @param lockerStationMapQry the lockerStationMapQry to set
	 */
	public void setLockerStationMapQry(String lockerStationMapQry) {
		this.lockerStationMapQry = lockerStationMapQry;
	}

	/**
	 * @return the lockerListQry
	 */
	public String getLockerListQry() {
		return lockerListQry;
	}

	/**
	 * @param lockerListQry the lockerListQry to set
	 */
	public void setLockerListQry(String lockerListQry) {
		this.lockerListQry = lockerListQry;
	}

	/**
	 * @return the manTerminalRightCheck
	 */
	public String getManTerminalRightCheck() {
		return manTerminalRightCheck;
	}

	/**
	 * @param manTerminalRightCheck the manTerminalRightCheck to set
	 */
	public void setManTerminalRightCheck(String manTerminalRightCheck) {
		this.manTerminalRightCheck = manTerminalRightCheck;
	}

	/**
	 * @return the manBoxRightCheck
	 */
	public String getManBoxRightCheck() {
		return manBoxRightCheck;
	}

	/**
	 * @param manBoxRightCheck the manBoxRightCheck to set
	 */
	public void setManBoxRightCheck(String manBoxRightCheck) {
		this.manBoxRightCheck = manBoxRightCheck;
	}

	/**
	 * @return the regSentPwdSms
	 */
	public String getRegSentPwdSms() {
		return regSentPwdSms;
	}

	/**
	 * @param regSentPwdSms the regSentPwdSms to set
	 */
	public void setRegSentPwdSms(String regSentPwdSms) {
		this.regSentPwdSms = regSentPwdSms;
	}

	/**
	 * @return the assignLockerMode
	 */
	public String getAssignLockerMode() {
		return assignLockerMode;
	}

	/**
	 * @param assignLockerMode the assignLockerMode to set
	 */
	public void setAssignLockerMode(String assignLockerMode) {
		this.assignLockerMode = assignLockerMode;
	}

	/**
	 * @return the assignBoxSizeMode
	 */
	public String getAssignBoxSizeMode() {
		return assignBoxSizeMode;
	}

	/**
	 * @param assignBoxSizeMode the assignBoxSizeMode to set
	 */
	public void setAssignBoxSizeMode(String assignBoxSizeMode) {
		this.assignBoxSizeMode = assignBoxSizeMode;
	}

	/**
	 * @return the recoveryTimeout
	 */
	public String getRecoveryTimeout() {
		return recoveryTimeout;
	}

	/**
	 * @param recoveryTimeout the recoveryTimeout to set
	 */
	public void setRecoveryTimeout(String recoveryTimeout) {
		this.recoveryTimeout = recoveryTimeout;
	}

	/**
	 * @return the reminderDays
	 */
	public String getReminderDays() {
		return reminderDays;
	}

	/**
	 * @param reminderDays the reminderDays to set
	 */
	public void setReminderDays(String reminderDays) {
		this.reminderDays = reminderDays;
	}

	public String getRecoverySource() {
		return recoverySource;
	}

	public void setRecoverySource(String recoverySource) {
		this.recoverySource = recoverySource;
	}

	public String getRecoveryScope() {
		return recoveryScope;
	}

	public void setRecoveryScope(String recoveryScope) {
		this.recoveryScope = recoveryScope;
	}

	public String getRecoveryPower() {
		return recoveryPower;
	}

	public void setRecoveryPower(String recoveryPower) {
		this.recoveryPower = recoveryPower;
	}

	public String getRecoveryInputPwd() {
		return recoveryInputPwd;
	}

	public void setRecoveryInputPwd(String recoveryInputPwd) {
		this.recoveryInputPwd = recoveryInputPwd;
	}

	public String getRecoveryMode() {
		return recoveryMode;
	}

	public void setRecoveryMode(String recoveryMode) {
		this.recoveryMode = recoveryMode;
	}

	/**
	 * @return the takeOutPwdLen
	 */
	public String getTakeOutPwdLen() {
		return takeOutPwdLen;
	}

	/**
	 * @param takeOutPwdLen the takeOutPwdLen to set
	 */
	public void setTakeOutPwdLen(String takeOutPwdLen) {
		this.takeOutPwdLen = takeOutPwdLen;
	}

	/**
	 * @return the takeOutPwdFormat
	 */
	public String getTakeOutPwdFormat() {
		return takeOutPwdFormat;
	}

	/**
	 * @param takeOutPwdFormat the takeOutPwdFormat to set
	 */
	public void setTakeOutPwdFormat(String takeOutPwdFormat) {
		this.takeOutPwdFormat = takeOutPwdFormat;
	}

	/**
	 * @return the autoLockOrder
	 */
	public String getAutoLockOrder() {
		return autoLockOrder;
	}

	/**
	 * @param autoLockOrder the autoLockOrder to set
	 */
	public void setAutoLockOrder(String autoLockOrder) {
		this.autoLockOrder = autoLockOrder;
	}

	/**
	 * @return the takeOutCheckType
	 */
	public String getTakeOutCheckType() {
		return takeOutCheckType;
	}

	/**
	 * @param takeOutCheckType the takeOutCheckType to set
	 */
	public void setTakeOutCheckType(String takeOutCheckType) {
		this.takeOutCheckType = takeOutCheckType;
	}
	public String getTakeOutExpiredItemLimit() {
        return takeOutExpiredItemLimit;
    }

    public void setTakeOutExpiredItemLimit(String takeOutExpiredItemLimit) {
        this.takeOutExpiredItemLimit = takeOutExpiredItemLimit;
    }

    public String getTransferToPPCFlag() {
		return transferToPPCFlag;
	}

	public void setTransferToPPCFlag(String transferToPPCFlag) {
		this.transferToPPCFlag = transferToPPCFlag;
	}

	public String getTransferResendTimes() {
		return transferResendTimes;
	}

	public void setTransferResendTimes(String transferResendTimes) {
		this.transferResendTimes = transferResendTimes;
	}

	public String getTransferItemRetainDays() {
		return transferItemRetainDays;
	}

	public void setTransferItemRetainDays(String transferItemRetainDays) {
		this.transferItemRetainDays = transferItemRetainDays;
	}

	/**
	 * @return the contactTel
	 */
	public String getContactTel() {
		return contactTel;
	}

	/**
	 * @param contactTel the contactTel to set
	 */
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getAppealTimeout() {
        return appealTimeout;
    }

    public void setAppealTimeout(String appealTimeout) {
        this.appealTimeout = appealTimeout;
    }

    public String getCustomerIDMode() {
		return customerIDMode;
	}

	public void setCustomerIDMode(String customerIDMode) {
		this.customerIDMode = customerIDMode;
	}

	/**
	 * @return the customerIDFormat
	 */
	public String getCustomerIDFormat() {
		return customerIDFormat;
	}

	/**
	 * @param customerIDFormat the customerIDFormat to set
	 */
	public void setCustomerIDFormat(String customerIDFormat) {
		this.customerIDFormat = customerIDFormat;
	}

	/**
	 * @return the customerExtendLimitDays
	 */
	public int getCustomerExtendLimitDays() {
		int days = 30;
		try{
			days = Integer.parseInt(customerExtendLimitDays);
		}catch(Exception e){
			days = 30;
		}
		return days;
	}

	/**
	 * @param customerExtendLimitDays the customerExtendLimitDays to set
	 */
	public void setCustomerExtendLimitDays(String customerExtendLimitDays) {
		this.customerExtendLimitDays = customerExtendLimitDays;
	}

	/**
	 * @return the customerExpiredKeepDays
	 */
	public int getCustomerExpiredKeepDays() {
		int days = 30;
		try{
			days = Integer.parseInt(customerExpiredKeepDays);
		}catch(Exception e){
			days = 30;
		}
		return days;
	}

	/**
	 * @param customerExpiredKeepDays the customerExpiredKeepDays to set
	 */
	public void setCustomerExpiredKeepDays(String customerExpiredKeepDays) {
		this.customerExpiredKeepDays = customerExpiredKeepDays;
	}

	/**
	 * @return the customerPOBoxRateFree
	 */
	public String getCustomerPOBoxRateFree() {
		return customerPOBoxRateFree;
	}

	/**
	 * @param customerPOBoxRateFree the customerPOBoxRateFree to set
	 */
	public void setCustomerPOBoxRateFree(String customerPOBoxRateFree) {
		this.customerPOBoxRateFree = customerPOBoxRateFree;
	}

	public String getCustomerPOBoxBandBode() {
		return customerPOBoxBandBode;
	}

	public void setCustomerPOBoxBandBode(String customerPOBoxBandBode) {
		this.customerPOBoxBandBode = customerPOBoxBandBode;
	}

	/**
	 * @return the poBoxAddressFirstLineFormat
	 */
	public String getPoBoxAddressFirstLineFormat() {
		return poBoxAddressFirstLineFormat;
	}

	/**
	 * @param poBoxAddressFirstLineFormat the poBoxAddressFirstLineFormat to set
	 */
	public void setPoBoxAddressFirstLineFormat(
			String poBoxAddressFirstLineFormat) {
		this.poBoxAddressFirstLineFormat = poBoxAddressFirstLineFormat;
	}

	public String getElockerPortal() {
		return elockerPortal;
	}

	public void setElockerPortal(String elockerPortal) {
		this.elockerPortal = elockerPortal;
	}

	public String getElockerServiceTel() {
		return elockerServiceTel;
	}

	public void setElockerServiceTel(String elockerServiceTel) {
		this.elockerServiceTel = elockerServiceTel;
	}

	/**
	 * @return the customerAddressChange
	 */
	public String getCustomerAddressChange() {
		return customerAddressChange;
	}

	/**
	 * @param customerAddressChange the customerAddressChange to set
	 */
	public void setCustomerAddressChange(String customerAddressChange) {
		this.customerAddressChange = customerAddressChange;
	}

	/**
	 * @return the poBoxMonthlyRate
	 */
	public int getPoBoxMonthlyRate() {
		int value =0;
		try{
			value = Integer.parseInt(poBoxMonthlyRate);
		}catch(Exception e){
			value =0;
		}
		
		return value;
	}

	/**
	 * @param poBoxMonthlyRate the poBoxMonthlyRate to set
	 */
	public void setPoBoxMonthlyRate(String poBoxMonthlyRate) {
		this.poBoxMonthlyRate = poBoxMonthlyRate;
	}

	/**
	 * @return the poBoxApplicationGuide
	 */
	public String getPoBoxApplicationGuide() {
		return poBoxApplicationGuide;
	}

	/**
	 * @param poBoxApplicationGuide the poBoxApplicationGuide to set
	 */
	public void setPoBoxApplicationGuide(String poBoxApplicationGuide) {
		this.poBoxApplicationGuide = poBoxApplicationGuide;
	}

	public String getE1ValidityPeriodDays() {
        return e1ValidityPeriodDays;
    }

    public void setE1ValidityPeriodDays(String e1ValidityPeriodDays) {
        this.e1ValidityPeriodDays = e1ValidityPeriodDays;
    }

    public String getSendReSendSMS() {
		return sendReSendSMS;
	}

	public void setSendReSendSMS(String sendReSendSMS) {
		this.sendReSendSMS = sendReSendSMS;
	}

	public String getModifyMobileFromLocker() {
		return modifyMobileFromLocker;
	}

	public void setModifyMobileFromLocker(String modifyMobileFromLocker) {
		this.modifyMobileFromLocker = modifyMobileFromLocker;
	}

	public String getSendLockerAlarmSMS() {
		return sendLockerAlarmSMS;
	}

	public void setSendLockerAlarmSMS(String sendLockerAlarmSMS) {
		this.sendLockerAlarmSMS = sendLockerAlarmSMS;
	}

	public String getRenewBoxAndLockedOldBox() {
		return renewBoxAndLockedOldBox;
	}

	public void setRenewBoxAndLockedOldBox(String renewBoxAndLockedOldBox) {
		this.renewBoxAndLockedOldBox = renewBoxAndLockedOldBox;
	}

	public String getLoadIntervalInMs() {
		return loadIntervalInMs;
	}

	public void setLoadIntervalInMs(String loadIntervalInMs) {
		this.loadIntervalInMs = loadIntervalInMs;
	}

	public String getLoadFunctionToMemory() {
		return loadFunctionToMemory;
	}

	public void setLoadFunctionToMemory(String loadFunctionToMemory) {
		this.loadFunctionToMemory = loadFunctionToMemory;
	}

	public String getLoadCtrlParamToMemory() {
		return loadCtrlParamToMemory;
	}

	public void setLoadCtrlParamToMemory(String loadCtrlParamToMemory) {
		this.loadCtrlParamToMemory = loadCtrlParamToMemory;
	}

	public String getLoadApplicationConfigToMemory() {
		return loadApplicationConfigToMemory;
	}

	public void setLoadApplicationConfigToMemory(
			String loadApplicationConfigToMemory) {
		this.loadApplicationConfigToMemory = loadApplicationConfigToMemory;
	}

	public String getManDADTerminalRightCheck() {
		return manDADTerminalRightCheck;
	}

	public void setManDADTerminalRightCheck(String manDADTerminalRightCheck) {
		this.manDADTerminalRightCheck = manDADTerminalRightCheck;
	}

	public String getSendSMSTimeGapInMs() {
		return sendSMSTimeGapInMs;
	}

	public void setSendSMSTimeGapInMs(String sendSMSTimeGapInMs) {
		this.sendSMSTimeGapInMs = sendSMSTimeGapInMs;
	}

	public long getDbServerAppServerTimeOffset() {
		return dbServerAppServerTimeOffset;
	}

	public void setDbServerAppServerTimeOffset(long dbServerAppServerTimeOffset) {
		this.dbServerAppServerTimeOffset = dbServerAppServerTimeOffset;
	}

	public String getSendReturnSMS() {
		return sendReturnSMS;
	}

	public void setSendReturnSMS(String sendReturnSMS) {
		this.sendReturnSMS = sendReturnSMS;
	}

	public String getPrintfSMSContent() {
		return printfSMSContent;
	}

	public void setPrintfSMSContent(String printfSMSContent) {
		this.printfSMSContent = printfSMSContent;
	}

	public String getMasterServerID() {
		return masterServerID;
	}

	public void setMasterServerID(String masterServerID) {
		this.masterServerID = masterServerID;
	}

	public String getDadDropE1Item() {
		return dadDropE1Item;
	}

	public void setDadDropE1Item(String dadDropE1Item) {
		this.dadDropE1Item = dadDropE1Item;
	}

    public String getMonitorEmail() {
        return monitorEmail;
    }

    public void setMonitorEmail(String monitorEmail) {
        this.monitorEmail = monitorEmail;
    }

    public String getSendSMSFailReportCnt() {
        return sendSMSFailReportCnt;
    }

    public void setSendSMSFailReportCnt(String sendSMSFailReportCnt) {
        this.sendSMSFailReportCnt = sendSMSFailReportCnt;
    }

    public String getSendPPCFailReportCnt() {
        return sendPPCFailReportCnt;
    }

    public void setSendPPCFailReportCnt(String sendPPCFailReportCnt) {
        this.sendPPCFailReportCnt = sendPPCFailReportCnt;
    }

}
