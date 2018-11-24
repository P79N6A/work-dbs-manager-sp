package com.dcdzsoft.dao.factory;

// Oracle concrete DAO Factory implementation
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.impl.*;

import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.dao.common.impl.*;
import com.dcdzsoft.dao.common.impl.mssql.*;

public class MsSQLDAOFactory extends DAOFactory
{
	public CommonDAO getCommonDAO()
	{
		return new CommonDAOImpl();
	}

	public UtilDAO getUtilDAO()
	{
		return new MsSQLUtilDAO();
	}

	public APUserKeyMapDAO getAPUserKeyMapDAO()
	{
		return new APUserKeyMapDAOImpl();
	}

	public DMCollectionParcelDAO getDMCollectionParcelDAO()
	{
		return new DMCollectionParcelDAOImpl();
	}

	public DMHistoryItemDAO getDMHistoryItemDAO()
	{
		return new DMHistoryItemDAOImpl();
	}

	public DMItemLifeCycleDAO getDMItemLifeCycleDAO()
	{
		return new DMItemLifeCycleDAOImpl();
	}

	public IMBPartnerServiceRightDAO getIMBPartnerServiceRightDAO()
	{
		return new IMBPartnerServiceRightDAOImpl();
	}

	public IMBusinessPartnerDAO getIMBusinessPartnerDAO()
	{
		return new IMBusinessPartnerDAOImpl();
	}

	public IMCollectZoneDAO getIMCollectZoneDAO()
	{
		return new IMCollectZoneDAOImpl();
	}

	public IMCompanyDAO getIMCompanyDAO()
	{
		return new IMCompanyDAOImpl();
	}

	public IMCompanyBoxRightDAO getIMCompanyBoxRightDAO()
	{
		return new IMCompanyBoxRightDAOImpl();
	}

	public IMCustomerDAO getIMCustomerDAO()
	{
		return new IMCustomerDAOImpl();
	}

	public IMEcommercePartnerDAO getIMEcommercePartnerDAO()
	{
		return new IMEcommercePartnerDAOImpl();
	}

	public IMGatewayDAO getIMGatewayDAO()
	{
		return new IMGatewayDAOImpl();
	}

	public IMPostalProcessingCenterDAO getIMPostalProcessingCenterDAO()
	{
		return new IMPostalProcessingCenterDAOImpl();
	}

	public IMServiceCounterDAO getIMServiceCounterDAO()
	{
		return new IMServiceCounterDAOImpl();
	}

	public IMServiceRateDAO getIMServiceRateDAO()
	{
		return new IMServiceRateDAOImpl();
	}

	public IMZoneDAO getIMZoneDAO()
	{
		return new IMZoneDAOImpl();
	}

	public IMZoneCounterDAO getIMZoneCounterDAO()
	{
		return new IMZoneCounterDAOImpl();
	}

	public IMZoneLockerRightDAO getIMZoneLockerRightDAO()
	{
		return new IMZoneLockerRightDAOImpl();
	}

	public MBBindMobileWaterDAO getMBBindMobileWaterDAO()
	{
		return new MBBindMobileWaterDAOImpl();
	}

	public MBBoxStatusLogDAO getMBBoxStatusLogDAO()
	{
		return new MBBoxStatusLogDAOImpl();
	}

	public MBLockUserTimeDAO getMBLockUserTimeDAO()
	{
		return new MBLockUserTimeDAOImpl();
	}

	public MBMobileBlackListDAO getMBMobileBlackListDAO()
	{
		return new MBMobileBlackListDAOImpl();
	}

	public MBMonitorPictureInfoDAO getMBMonitorPictureInfoDAO()
	{
		return new MBMonitorPictureInfoDAOImpl();
	}

	public MBPwdShortMsgDAO getMBPwdShortMsgDAO()
	{
		return new MBPwdShortMsgDAOImpl();
	}

	public MBReminderWaterDAO getMBReminderWaterDAO()
	{
		return new MBReminderWaterDAOImpl();
	}

	public MBSignInfoDAO getMBSignInfoDAO()
	{
		return new MBSignInfoDAOImpl();
	}

	public MBSmsStatDAO getMBSmsStatDAO()
	{
		return new MBSmsStatDAOImpl();
	}

	public MBTerminalAlertLogDAO getMBTerminalAlertLogDAO()
	{
		return new MBTerminalAlertLogDAOImpl();
	}

	public MBTerminalStatusLogDAO getMBTerminalStatusLogDAO()
	{
		return new MBTerminalStatusLogDAOImpl();
	}

	public MBWrongPwdWaterDAO getMBWrongPwdWaterDAO()
	{
		return new MBWrongPwdWaterDAOImpl();
	}

	public OPFunctionDAO getOPFunctionDAO()
	{
		return new OPFunctionDAOImpl();
	}

	public OPMenuDAO getOPMenuDAO()
	{
		return new OPMenuDAOImpl();
	}

	public OPOperAllLimitDAO getOPOperAllLimitDAO()
	{
		return new OPOperAllLimitDAOImpl();
	}

	public OPOperOnlineDAO getOPOperOnlineDAO()
	{
		return new OPOperOnlineDAOImpl();
	}

	public OPOperRoleDAO getOPOperRoleDAO()
	{
		return new OPOperRoleDAOImpl();
	}

	public OPOperSpeRightDAO getOPOperSpeRightDAO()
	{
		return new OPOperSpeRightDAOImpl();
	}

	public OPOperToMenuDAO getOPOperToMenuDAO()
	{
		return new OPOperToMenuDAOImpl();
	}

	public OPOperatorDAO getOPOperatorDAO()
	{
		return new OPOperatorDAOImpl();
	}

	public OPOperatorLogDAO getOPOperatorLogDAO()
	{
		return new OPOperatorLogDAOImpl();
	}

	public OPRoleDAO getOPRoleDAO()
	{
		return new OPRoleDAOImpl();
	}

	public OPRoleAllLimitDAO getOPRoleAllLimitDAO()
	{
		return new OPRoleAllLimitDAOImpl();
	}

	public OPRoleSpeRightDAO getOPRoleSpeRightDAO()
	{
		return new OPRoleSpeRightDAOImpl();
	}

	public OPRoleToMenuDAO getOPRoleToMenuDAO()
	{
		return new OPRoleToMenuDAOImpl();
	}

	public OPSpecialPrivDAO getOPSpecialPrivDAO()
	{
		return new OPSpecialPrivDAOImpl();
	}

	public PAControlParamDAO getPAControlParamDAO()
	{
		return new PAControlParamDAOImpl();
	}

	public PADictionaryDAO getPADictionaryDAO()
	{
		return new PADictionaryDAOImpl();
	}

	public PASequenceDAO getPASequenceDAO()
	{
		return new PASequenceDAOImpl();
	}

	public PATerminalCtrlParamDAO getPATerminalCtrlParamDAO()
	{
		return new PATerminalCtrlParamDAOImpl();
	}

	public PMPostmanDAO getPMPostmanDAO()
	{
		return new PMPostmanDAOImpl();
	}

	public PMPostmanLockerRightDAO getPMPostmanLockerRightDAO()
	{
		return new PMPostmanLockerRightDAOImpl();
	}

	public PTDeliverHistoryDAO getPTDeliverHistoryDAO()
	{
		return new PTDeliverHistoryDAOImpl();
	}

	public PTDeliverItemTransferDAO getPTDeliverItemTransferDAO()
	{
		return new PTDeliverItemTransferDAOImpl();
	}

	public PTInBoxPackageDAO getPTInBoxPackageDAO()
	{
		return new PTInBoxPackageDAOImpl();
	}

	public PTItemLifeCycleDAO getPTItemLifeCycleDAO()
	{
		return new PTItemLifeCycleDAOImpl();
	}

	public PTReadyPackageDAO getPTReadyPackageDAO()
	{
		return new PTReadyPackageDAOImpl();
	}

	public PTTransferItemWaterDAO getPTTransferItemWaterDAO()
	{
		return new PTTransferItemWaterDAOImpl();
	}

	public PYServiceBillWaterDAO getPYServiceBillWaterDAO()
	{
		return new PYServiceBillWaterDAOImpl();
	}

	public PYTopUpReqDAO getPYTopUpReqDAO()
	{
		return new PYTopUpReqDAOImpl();
	}

	public RMAppealLogDAO getRMAppealLogDAO()
	{
		return new RMAppealLogDAOImpl();
	}

	public RMOpenBoxLogDAO getRMOpenBoxLogDAO()
	{
		return new RMOpenBoxLogDAOImpl();
	}

	public SCFtpLogDAO getSCFtpLogDAO()
	{
		return new SCFtpLogDAOImpl();
	}

	public SCPushDataQueueDAO getSCPushDataQueueDAO()
	{
		return new SCPushDataQueueDAOImpl();
	}

	public SCSyncFailWaterDAO getSCSyncFailWaterDAO()
	{
		return new SCSyncFailWaterDAOImpl();
	}

	public SCTimestampDAO getSCTimestampDAO()
	{
		return new SCTimestampDAOImpl();
	}

	public SCUploadDataQueueDAO getSCUploadDataQueueDAO()
	{
		return new SCUploadDataQueueDAOImpl();
	}

	public SMAdVideoDAO getSMAdVideoDAO()
	{
		return new SMAdVideoDAOImpl();
	}

	public SMSystemInfoDAO getSMSystemInfoDAO()
	{
		return new SMSystemInfoDAOImpl();
	}

	public TBBoxTypeDAO getTBBoxTypeDAO()
	{
		return new TBBoxTypeDAOImpl();
	}

	public TBServerBoxDAO getTBServerBoxDAO()
	{
		return new TBServerBoxDAOImpl();
	}

	public TBServerDeskDAO getTBServerDeskDAO()
	{
		return new TBServerDeskDAOImpl();
	}

	public TBTerminalDAO getTBTerminalDAO()
	{
		return new TBTerminalDAOImpl();
	}

	public TBTerminalCounterDAO getTBTerminalCounterDAO()
	{
		return new TBTerminalCounterDAOImpl();
	}

	public MBSendReportEmailDAO getMBSendReportEmailDAO() 
	{
		return new MBSendReportEmailDAOImpl();
	}

	public MBTimeLimitDAO getMBTimeLimitDAO()
	{
		return new MBTimeLimitDAOImpl();
	}

	public PMTransporterDAO getPMTransporterDAO()
	{
		return new PMTransporterDAOImpl();
	}

}