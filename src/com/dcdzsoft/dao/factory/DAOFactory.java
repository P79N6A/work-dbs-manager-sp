package com.dcdzsoft.dao.factory;

import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;

import com.dcdzsoft.sda.db.DataSourceUtils;

// Abstract class DAO Factory
public abstract class DAOFactory
{
	// List of DAO types supported by the factory
	public static DAOFactory getDAOFactory(int whichFactory)
	{
		switch (whichFactory)
		{
			case DataSourceUtils.DB_TYPE_ORACLE:
				return new OracleDAOFactory();
			case DataSourceUtils.DB_TYPE_MYSQL:
				return new MysqlDAOFactory();
			case DataSourceUtils.DB_TYPE_SQLSERVER:
				return new MsSQLDAOFactory();
			default:
				return null;
		}
	}

	// There will be a method for each DAO that can be
	// created. The concrete factories will have to
	// implement these methods.
	public abstract CommonDAO getCommonDAO();

	public abstract UtilDAO getUtilDAO();

	public abstract APUserKeyMapDAO getAPUserKeyMapDAO();

	public abstract DMCollectionParcelDAO getDMCollectionParcelDAO();

	public abstract DMHistoryItemDAO getDMHistoryItemDAO();

	public abstract DMItemLifeCycleDAO getDMItemLifeCycleDAO();

	public abstract IMBPartnerServiceRightDAO getIMBPartnerServiceRightDAO();

	public abstract IMBusinessPartnerDAO getIMBusinessPartnerDAO();

	public abstract IMCollectZoneDAO getIMCollectZoneDAO();

	public abstract IMCompanyDAO getIMCompanyDAO();

	public abstract IMCompanyBoxRightDAO getIMCompanyBoxRightDAO();

	public abstract IMCustomerDAO getIMCustomerDAO();

	public abstract IMEcommercePartnerDAO getIMEcommercePartnerDAO();

	public abstract IMGatewayDAO getIMGatewayDAO();

	public abstract IMPostalProcessingCenterDAO getIMPostalProcessingCenterDAO();

	public abstract IMServiceCounterDAO getIMServiceCounterDAO();

	public abstract IMServiceRateDAO getIMServiceRateDAO();

	public abstract IMZoneDAO getIMZoneDAO();

	public abstract IMZoneCounterDAO getIMZoneCounterDAO();

	public abstract IMZoneLockerRightDAO getIMZoneLockerRightDAO();

	public abstract MBBindMobileWaterDAO getMBBindMobileWaterDAO();

	public abstract MBBoxStatusLogDAO getMBBoxStatusLogDAO();

	public abstract MBLockUserTimeDAO getMBLockUserTimeDAO();

	public abstract MBMobileBlackListDAO getMBMobileBlackListDAO();

	public abstract MBMonitorPictureInfoDAO getMBMonitorPictureInfoDAO();

	public abstract MBPwdShortMsgDAO getMBPwdShortMsgDAO();

	public abstract MBReminderWaterDAO getMBReminderWaterDAO();

	public abstract MBSendReportEmailDAO getMBSendReportEmailDAO();

	public abstract MBSignInfoDAO getMBSignInfoDAO();

	public abstract MBSmsStatDAO getMBSmsStatDAO();

	public abstract MBTerminalAlertLogDAO getMBTerminalAlertLogDAO();

	public abstract MBTerminalStatusLogDAO getMBTerminalStatusLogDAO();

	public abstract MBTimeLimitDAO getMBTimeLimitDAO();

	public abstract MBWrongPwdWaterDAO getMBWrongPwdWaterDAO();

	public abstract OPFunctionDAO getOPFunctionDAO();

	public abstract OPMenuDAO getOPMenuDAO();

	public abstract OPOperAllLimitDAO getOPOperAllLimitDAO();

	public abstract OPOperOnlineDAO getOPOperOnlineDAO();

	public abstract OPOperRoleDAO getOPOperRoleDAO();

	public abstract OPOperSpeRightDAO getOPOperSpeRightDAO();

	public abstract OPOperToMenuDAO getOPOperToMenuDAO();

	public abstract OPOperatorDAO getOPOperatorDAO();

	public abstract OPOperatorLogDAO getOPOperatorLogDAO();

	public abstract OPRoleDAO getOPRoleDAO();

	public abstract OPRoleAllLimitDAO getOPRoleAllLimitDAO();

	public abstract OPRoleSpeRightDAO getOPRoleSpeRightDAO();

	public abstract OPRoleToMenuDAO getOPRoleToMenuDAO();

	public abstract OPSpecialPrivDAO getOPSpecialPrivDAO();

	public abstract PAControlParamDAO getPAControlParamDAO();

	public abstract PADictionaryDAO getPADictionaryDAO();

	public abstract PASequenceDAO getPASequenceDAO();

	public abstract PATerminalCtrlParamDAO getPATerminalCtrlParamDAO();

	public abstract PMPostmanDAO getPMPostmanDAO();

	public abstract PMPostmanLockerRightDAO getPMPostmanLockerRightDAO();

	public abstract PMTransporterDAO getPMTransporterDAO();

	public abstract PTDeliverHistoryDAO getPTDeliverHistoryDAO();

	public abstract PTDeliverItemTransferDAO getPTDeliverItemTransferDAO();

	public abstract PTInBoxPackageDAO getPTInBoxPackageDAO();

	public abstract PTItemLifeCycleDAO getPTItemLifeCycleDAO();

	public abstract PTReadyPackageDAO getPTReadyPackageDAO();

	public abstract PTTransferItemWaterDAO getPTTransferItemWaterDAO();

	public abstract PYServiceBillWaterDAO getPYServiceBillWaterDAO();

	public abstract PYTopUpReqDAO getPYTopUpReqDAO();

	public abstract RMAppealLogDAO getRMAppealLogDAO();

	public abstract RMOpenBoxLogDAO getRMOpenBoxLogDAO();

	public abstract SCFtpLogDAO getSCFtpLogDAO();

	public abstract SCPushDataQueueDAO getSCPushDataQueueDAO();

	public abstract SCSyncFailWaterDAO getSCSyncFailWaterDAO();

	public abstract SCTimestampDAO getSCTimestampDAO();

	public abstract SCUploadDataQueueDAO getSCUploadDataQueueDAO();

	public abstract SMAdVideoDAO getSMAdVideoDAO();

	public abstract SMSystemInfoDAO getSMSystemInfoDAO();

	public abstract TBBoxTypeDAO getTBBoxTypeDAO();

	public abstract TBServerBoxDAO getTBServerBoxDAO();

	public abstract TBServerDeskDAO getTBServerDeskDAO();

	public abstract TBTerminalDAO getTBTerminalDAO();

	public abstract TBTerminalCounterDAO getTBTerminalCounterDAO();

}