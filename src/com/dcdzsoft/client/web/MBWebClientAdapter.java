package com.dcdzsoft.client.web;

import javax.sql.RowSet;

import com.dcdzsoft.aop.BusiBeanAOPBaseClass;

public class MBWebClientAdapter {
	protected MBWebClientAdapter(){}

	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamMBDeviceAlertQry p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBDeviceAlertQry bean = ( com.dcdzsoft.business.mb.MBDeviceAlertQry)aop.bind(com.dcdzsoft.business.mb.MBDeviceAlertQry.class);
			return bean.doBusiness(p1);
		}

		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBDeviceAlertQryCount p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBDeviceAlertQryCount bean = ( com.dcdzsoft.business.mb.MBDeviceAlertQryCount)aop.bind(com.dcdzsoft.business.mb.MBDeviceAlertQryCount.class);
			return bean.doBusiness(p1);
		}

		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBDeviceOffline p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBDeviceOffline bean = ( com.dcdzsoft.business.mb.MBDeviceOffline)aop.bind(com.dcdzsoft.business.mb.MBDeviceOffline.class);
			return bean.doBusiness(p1);
		}

		public static com.dcdzsoft.dto.business.OutParamMBDeviceSign doBusiness(com.dcdzsoft.dto.business.InParamMBDeviceSign p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBDeviceSign bean = ( com.dcdzsoft.business.mb.MBDeviceSign)aop.bind(com.dcdzsoft.business.mb.MBDeviceSign.class);
			return bean.doBusiness(p1);
		}

		public static com.dcdzsoft.dto.business.OutParamMBDeviceSign0 doBusiness(com.dcdzsoft.dto.business.InParamMBDeviceSign0 p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBDeviceSign0 bean = ( com.dcdzsoft.business.mb.MBDeviceSign0)aop.bind(com.dcdzsoft.business.mb.MBDeviceSign0.class);
			return bean.doBusiness(p1);
		}

		public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamMBDeviceSignQry p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBDeviceSignQry bean = ( com.dcdzsoft.business.mb.MBDeviceSignQry)aop.bind(com.dcdzsoft.business.mb.MBDeviceSignQry.class);
			return bean.doBusiness(p1);
		}

		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBDeviceSignQryCount p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBDeviceSignQryCount bean = ( com.dcdzsoft.business.mb.MBDeviceSignQryCount)aop.bind(com.dcdzsoft.business.mb.MBDeviceSignQryCount.class);
			return bean.doBusiness(p1);
		}

		public static com.dcdzsoft.dto.business.OutParamMBGetAdvertisePic doBusiness(com.dcdzsoft.dto.business.InParamMBGetAdvertisePic p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBGetAdvertisePic bean = ( com.dcdzsoft.business.mb.MBGetAdvertisePic)aop.bind(com.dcdzsoft.business.mb.MBGetAdvertisePic.class);
			return bean.doBusiness(p1);
		}

		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBGetBoxDectionResult p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBGetBoxDectionResult bean = ( com.dcdzsoft.business.mb.MBGetBoxDectionResult)aop.bind(com.dcdzsoft.business.mb.MBGetBoxDectionResult.class);
			return bean.doBusiness(p1);
		}

		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBGetControlParam p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBGetControlParam bean = ( com.dcdzsoft.business.mb.MBGetControlParam)aop.bind(com.dcdzsoft.business.mb.MBGetControlParam.class);
			return bean.doBusiness(p1);
		}

		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBGetDeviceSysLog p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBGetDeviceSysLog bean = ( com.dcdzsoft.business.mb.MBGetDeviceSysLog)aop.bind(com.dcdzsoft.business.mb.MBGetDeviceSysLog.class);
			return bean.doBusiness(p1);
		}

		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBGetHistoryPackage p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBGetHistoryPackage bean = ( com.dcdzsoft.business.mb.MBGetHistoryPackage)aop.bind(com.dcdzsoft.business.mb.MBGetHistoryPackage.class);
			return bean.doBusiness(p1);
		}

		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBGetInboxPackage p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBGetInboxPackage bean = ( com.dcdzsoft.business.mb.MBGetInboxPackage)aop.bind(com.dcdzsoft.business.mb.MBGetInboxPackage.class);
			return bean.doBusiness(p1);
		}

		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBGetManagerLog p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBGetManagerLog bean = ( com.dcdzsoft.business.mb.MBGetManagerLog)aop.bind(com.dcdzsoft.business.mb.MBGetManagerLog.class);
			return bean.doBusiness(p1);
		}

		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBGetUpFailureData p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBGetUpFailureData bean = ( com.dcdzsoft.business.mb.MBGetUpFailureData)aop.bind(com.dcdzsoft.business.mb.MBGetUpFailureData.class);
			return bean.doBusiness(p1);
		}

		public static com.dcdzsoft.dto.business.OutParamMBHeartBeat doBusiness(com.dcdzsoft.dto.business.InParamMBHeartBeat p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBHeartBeat bean = ( com.dcdzsoft.business.mb.MBHeartBeat)aop.bind(com.dcdzsoft.business.mb.MBHeartBeat.class);
			return bean.doBusiness(p1);
		}

		public static com.dcdzsoft.dto.business.OutParamMBInitialization doBusiness(com.dcdzsoft.dto.business.InParamMBInitialization p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBInitialization bean = ( com.dcdzsoft.business.mb.MBInitialization)aop.bind(com.dcdzsoft.business.mb.MBInitialization.class);
			return bean.doBusiness(p1);
		}

		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBMobileBlackListAdd p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBMobileBlackListAdd bean = ( com.dcdzsoft.business.mb.MBMobileBlackListAdd)aop.bind(com.dcdzsoft.business.mb.MBMobileBlackListAdd.class);
			return bean.doBusiness(p1);
		}

		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBMobileBlackListDel p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBMobileBlackListDel bean = ( com.dcdzsoft.business.mb.MBMobileBlackListDel)aop.bind(com.dcdzsoft.business.mb.MBMobileBlackListDel.class);
			return bean.doBusiness(p1);
		}

		public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamMBMobileBlackListQry p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBMobileBlackListQry bean = ( com.dcdzsoft.business.mb.MBMobileBlackListQry)aop.bind(com.dcdzsoft.business.mb.MBMobileBlackListQry.class);
			return bean.doBusiness(p1);
		}

		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBMobileBlackListQryCount p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBMobileBlackListQryCount bean = ( com.dcdzsoft.business.mb.MBMobileBlackListQryCount)aop.bind(com.dcdzsoft.business.mb.MBMobileBlackListQryCount.class);
			return bean.doBusiness(p1);
		}

		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBModSMSSentStatus p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBModSMSSentStatus bean = ( com.dcdzsoft.business.mb.MBModSMSSentStatus)aop.bind(com.dcdzsoft.business.mb.MBModSMSSentStatus.class);
			return bean.doBusiness(p1);
		}

		public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamMBPwdShortMsgQry p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBPwdShortMsgQry bean = ( com.dcdzsoft.business.mb.MBPwdShortMsgQry)aop.bind(com.dcdzsoft.business.mb.MBPwdShortMsgQry.class);
			return bean.doBusiness(p1);
		}

		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBPwdShortMsgQryCount p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBPwdShortMsgQryCount bean = ( com.dcdzsoft.business.mb.MBPwdShortMsgQryCount)aop.bind(com.dcdzsoft.business.mb.MBPwdShortMsgQryCount.class);
			return bean.doBusiness(p1);
		}

		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBPwdShortMsgReSend p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBPwdShortMsgReSend bean = ( com.dcdzsoft.business.mb.MBPwdShortMsgReSend)aop.bind(com.dcdzsoft.business.mb.MBPwdShortMsgReSend.class);
			return bean.doBusiness(p1);
		}

		public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamMBReminderMsgQry p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBReminderMsgQry bean = ( com.dcdzsoft.business.mb.MBReminderMsgQry)aop.bind(com.dcdzsoft.business.mb.MBReminderMsgQry.class);
			return bean.doBusiness(p1);
		}

		public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamMBReminderWaterQry p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBReminderWaterQry bean = ( com.dcdzsoft.business.mb.MBReminderWaterQry)aop.bind(com.dcdzsoft.business.mb.MBReminderWaterQry.class);
			return bean.doBusiness(p1);
		}

		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBReminderWaterQryCount p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBReminderWaterQryCount bean = ( com.dcdzsoft.business.mb.MBReminderWaterQryCount)aop.bind(com.dcdzsoft.business.mb.MBReminderWaterQryCount.class);
			return bean.doBusiness(p1);
		}

		public static com.dcdzsoft.dto.business.OutParamMBReportBoxStatus doBusiness(com.dcdzsoft.dto.business.InParamMBReportBoxStatus p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBReportBoxStatus bean = ( com.dcdzsoft.business.mb.MBReportBoxStatus)aop.bind(com.dcdzsoft.business.mb.MBReportBoxStatus.class);
			return bean.doBusiness(p1);
		}

		public static com.dcdzsoft.dto.business.OutParamMBReportPeripheralStatus doBusiness(com.dcdzsoft.dto.business.InParamMBReportPeripheralStatus p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBReportPeripheralStatus bean = ( com.dcdzsoft.business.mb.MBReportPeripheralStatus)aop.bind(com.dcdzsoft.business.mb.MBReportPeripheralStatus.class);
			return bean.doBusiness(p1);
		}

		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBSendReminderMsg p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBSendReminderMsg bean = ( com.dcdzsoft.business.mb.MBSendReminderMsg)aop.bind(com.dcdzsoft.business.mb.MBSendReminderMsg.class);
			return bean.doBusiness(p1);
		}

		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBSendUpgradeInform p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBSendUpgradeInform bean = ( com.dcdzsoft.business.mb.MBSendUpgradeInform)aop.bind(com.dcdzsoft.business.mb.MBSendUpgradeInform.class);
			return bean.doBusiness(p1);
		}

		public static com.dcdzsoft.dto.business.OutParamMBSpotAdminLogin doBusiness(com.dcdzsoft.dto.business.InParamMBSpotAdminLogin p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBSpotAdminLogin bean = ( com.dcdzsoft.business.mb.MBSpotAdminLogin)aop.bind(com.dcdzsoft.business.mb.MBSpotAdminLogin.class);
			return bean.doBusiness(p1);
		}

		public static java.lang.String doBusiness(com.dcdzsoft.dto.business.InParamMBUploadDeviceAlert p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBUploadDeviceAlert bean = ( com.dcdzsoft.business.mb.MBUploadDeviceAlert)aop.bind(com.dcdzsoft.business.mb.MBUploadDeviceAlert.class);
			return bean.doBusiness(p1);
		}

		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBUploadInboxPack p1)
			  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBUploadInboxPack bean = ( com.dcdzsoft.business.mb.MBUploadInboxPack)aop.bind(com.dcdzsoft.business.mb.MBUploadInboxPack.class);
			return bean.doBusiness(p1);
		}
		
		public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamMBTerminalLedMsgQry p1)
				  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBTerminalLedMsgQry bean = ( com.dcdzsoft.business.mb.MBTerminalLedMsgQry)aop.bind(com.dcdzsoft.business.mb.MBTerminalLedMsgQry.class);
			return bean.doBusiness(p1);
		}
		
		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBTerminalLedMsgQryCount p1)
				  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBTerminalLedMsgQryCount bean = ( com.dcdzsoft.business.mb.MBTerminalLedMsgQryCount)aop.bind(com.dcdzsoft.business.mb.MBTerminalLedMsgQryCount.class);
			return bean.doBusiness(p1);
		}
		
		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBPushTerminalLedMsg p1)
				  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBPushTerminalLedMsg bean = ( com.dcdzsoft.business.mb.MBPushTerminalLedMsg)aop.bind(com.dcdzsoft.business.mb.MBPushTerminalLedMsg.class);
			return bean.doBusiness(p1);
		}
		
		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBSendAlarmInfo p1)
				  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBSendAlarmInfo bean = 
					( com.dcdzsoft.business.mb.MBSendAlarmInfo)aop.bind(com.dcdzsoft.business.mb.MBSendAlarmInfo.class);
			return bean.doBusiness(p1);
		}	
		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBSendOfflineInfo p1)
				  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBSendOfflineInfo bean = 
					( com.dcdzsoft.business.mb.MBSendOfflineInfo)aop.bind(com.dcdzsoft.business.mb.MBSendOfflineInfo.class);
			return bean.doBusiness(p1);
		}	
	    public static int doBusiness(com.dcdzsoft.dto.business.InParamMBSendScheduleMsg p1)
	                throws com.dcdzsoft.EduException
	    {
	        BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
	        com.dcdzsoft.business.mb.MBSendScheduleMsg bean = 
	                ( com.dcdzsoft.business.mb.MBSendScheduleMsg)aop.bind(com.dcdzsoft.business.mb.MBSendScheduleMsg.class);
	        return bean.doBusiness(p1);
	    }
	          
	    public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamMBScheduleMsgQry p1)
	                throws com.dcdzsoft.EduException
	    {
	        BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
	        com.dcdzsoft.business.mb.MBScheduleMsgQry bean = 
	                ( com.dcdzsoft.business.mb.MBScheduleMsgQry)aop.bind(com.dcdzsoft.business.mb.MBScheduleMsgQry.class);
	        return bean.doBusiness(p1);
	    }
	    //订单时间限制
	    public static int doBusiness(com.dcdzsoft.dto.business.InParamMBTimeLimitAdd p1) throws com.dcdzsoft.EduException
	    {
	    	BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
        	com.dcdzsoft.business.mb.MBTimeLimitAdd bean = 
                ( com.dcdzsoft.business.mb.MBTimeLimitAdd)aop.bind(com.dcdzsoft.business.mb.MBTimeLimitAdd.class);
        	return bean.doBusiness(p1);
	    }
	    public static int doBusiness(com.dcdzsoft.dto.business.InParamMBTimeLimitDel p1) throws com.dcdzsoft.EduException
	    {
	    	BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
        	com.dcdzsoft.business.mb.MBTimeLimitDel bean = 
                ( com.dcdzsoft.business.mb.MBTimeLimitDel)aop.bind(com.dcdzsoft.business.mb.MBTimeLimitDel.class);
        	return bean.doBusiness(p1);
	    }
	    public static int doBusiness(com.dcdzsoft.dto.business.InParamMBTimeLimitMod p1) throws com.dcdzsoft.EduException
	    {
	    	BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
        	com.dcdzsoft.business.mb.MBTimeLimitMod bean = 
                ( com.dcdzsoft.business.mb.MBTimeLimitMod)aop.bind(com.dcdzsoft.business.mb.MBTimeLimitMod.class);
        	return bean.doBusiness(p1);
	    }
	    public static RowSet doBusiness(com.dcdzsoft.dto.business.InParamMBTimeLimitQry p1) throws com.dcdzsoft.EduException
	    {
	    	BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
        	com.dcdzsoft.business.mb.MBTimeLimitQry bean = 
                ( com.dcdzsoft.business.mb.MBTimeLimitQry)aop.bind(com.dcdzsoft.business.mb.MBTimeLimitQry.class);
        	return bean.doBusiness(p1);
	    }
	    public static int doBusiness(com.dcdzsoft.dto.business.InParamMBTimeLimitQryCount p1) throws com.dcdzsoft.EduException
	    {
	    	BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
        	com.dcdzsoft.business.mb.MBTimeLimitQryCount bean = 
                ( com.dcdzsoft.business.mb.MBTimeLimitQryCount)aop.bind(com.dcdzsoft.business.mb.MBTimeLimitQryCount.class);
        	return bean.doBusiness(p1);
	    }
	    //发送邮件
	    public static int doBusiness(com.dcdzsoft.dto.business.InParamMBReportEmailRecrAdd p1) throws com.dcdzsoft.EduException
	    {
	    	BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
        	com.dcdzsoft.business.mb.MBReportEmailRecrAdd bean = 
                ( com.dcdzsoft.business.mb.MBReportEmailRecrAdd)aop.bind(com.dcdzsoft.business.mb.MBReportEmailRecrAdd.class);
        	return bean.doBusiness(p1);
	    }
	    public static int doBusiness(com.dcdzsoft.dto.business.InParamMBReportEmailRecrDel p1) throws com.dcdzsoft.EduException
	    {
	    	BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
        	com.dcdzsoft.business.mb.MBReportEmailRecrDel bean = 
                ( com.dcdzsoft.business.mb.MBReportEmailRecrDel)aop.bind(com.dcdzsoft.business.mb.MBReportEmailRecrDel.class);
        	return bean.doBusiness(p1);
	    }
	    public static int doBusiness(com.dcdzsoft.dto.business.InParamMBReportEmailRecrMod p1) throws com.dcdzsoft.EduException
	    {
	    	BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
        	com.dcdzsoft.business.mb.MBReportEmailRecrMod bean = 
                ( com.dcdzsoft.business.mb.MBReportEmailRecrMod)aop.bind(com.dcdzsoft.business.mb.MBReportEmailRecrMod.class);
        	return bean.doBusiness(p1);
	    }
	    public static RowSet doBusiness(com.dcdzsoft.dto.business.InParamMBReportEmailRecrQry p1) throws com.dcdzsoft.EduException
	    {
	    	BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
        	com.dcdzsoft.business.mb.MBReportEmailRecrQry bean = 
                ( com.dcdzsoft.business.mb.MBReportEmailRecrQry)aop.bind(com.dcdzsoft.business.mb.MBReportEmailRecrQry.class);
        	return bean.doBusiness(p1);
	    }
	    public static int doBusiness(com.dcdzsoft.dto.business.InParamMBReportEmailRecrQryCount p1) throws com.dcdzsoft.EduException
	    {
	    	BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
        	com.dcdzsoft.business.mb.MBReportEmailRecrQryCount bean = 
                ( com.dcdzsoft.business.mb.MBReportEmailRecrQryCount)aop.bind(com.dcdzsoft.business.mb.MBReportEmailRecrQryCount.class);
        	return bean.doBusiness(p1);
	    }
		public static int doBusiness(com.dcdzsoft.dto.business.InParamMBSendBoxUsedEmail p1)
				  throws com.dcdzsoft.EduException
		{
			BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
			com.dcdzsoft.business.mb.MBSendBoxUsedEmail bean = (com.dcdzsoft.business.mb.MBSendBoxUsedEmail)aop.bind(com.dcdzsoft.business.mb.MBSendBoxUsedEmail.class);
			return bean.doBusiness(p1);
		}
}
