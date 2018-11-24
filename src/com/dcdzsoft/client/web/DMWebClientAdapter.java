package com.dcdzsoft.client.web;

import com.dcdzsoft.aop.BusiBeanAOPBaseClass;

public class DMWebClientAdapter {
	protected DMWebClientAdapter(){}


	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamDMDeliveryQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMDeliveryQry bean = ( com.dcdzsoft.business.dm.DMDeliveryQry)aop.bind(com.dcdzsoft.business.dm.DMDeliveryQry.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamDMDeliveryQryCount p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMDeliveryQryCount bean = ( com.dcdzsoft.business.dm.DMDeliveryQryCount)aop.bind(com.dcdzsoft.business.dm.DMDeliveryQryCount.class);
		return bean.doBusiness(p1);
	}
    public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamDMBPToCancelItemQry p1)
            throws com.dcdzsoft.EduException
    {
      BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
      com.dcdzsoft.business.dm.DMBPToCancelItemQry bean = 
              ( com.dcdzsoft.business.dm.DMBPToCancelItemQry)aop.bind(com.dcdzsoft.business.dm.DMBPToCancelItemQry.class);
      return bean.doBusiness(p1);
    }
	public static com.dcdzsoft.dto.business.OutParamDMCollectAgentItemQry doBusiness(com.dcdzsoft.dto.business.InParamDMCollectAgentItemQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMCollectAgentItemQry bean = 
				( com.dcdzsoft.business.dm.DMCollectAgentItemQry)aop.bind(com.dcdzsoft.business.dm.DMCollectAgentItemQry.class);
		return bean.doBusiness(p1);
	}
	public static com.dcdzsoft.dto.business.OutParamDMDeliveryCollected doBusiness(com.dcdzsoft.dto.business.InParamDMDeliveryCollected p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMDeliveryCollected bean = ( com.dcdzsoft.business.dm.DMDeliveryCollected)aop.bind(com.dcdzsoft.business.dm.DMDeliveryCollected.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamDMDeliveryAssign p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMDeliveryAssign bean = ( com.dcdzsoft.business.dm.DMDeliveryAssign)aop.bind(com.dcdzsoft.business.dm.DMDeliveryAssign.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamDMDeliveryUnAssign p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMDeliveryUnAssign bean = ( com.dcdzsoft.business.dm.DMDeliveryUnAssign)aop.bind(com.dcdzsoft.business.dm.DMDeliveryUnAssign.class);
		return bean.doBusiness(p1);
	}
	public static String doBusiness(com.dcdzsoft.dto.business.InParamDMDeliveryReceived p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMDeliveryReceived bean = ( com.dcdzsoft.business.dm.DMDeliveryReceived)aop.bind(com.dcdzsoft.business.dm.DMDeliveryReceived.class);
		return bean.doBusiness(p1);
	}
	public static String doBusiness(com.dcdzsoft.dto.business.InParamDMDeliveryTobeDropped p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMDeliveryTobeDropped bean = ( com.dcdzsoft.business.dm.DMDeliveryTobeDropped)aop.bind(com.dcdzsoft.business.dm.DMDeliveryTobeDropped.class);
		return bean.doBusiness(p1);
	}
	public static com.dcdzsoft.dto.business.OutParamPrintData doBusiness(com.dcdzsoft.dto.business.InParamDMPrintDeliveryTobeDropped p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMPrintDeliveryTobeDropped bean = ( com.dcdzsoft.business.dm.DMPrintDeliveryTobeDropped)aop.bind(com.dcdzsoft.business.dm.DMPrintDeliveryTobeDropped.class);
		return bean.doBusiness(p1);
	}
	public static com.dcdzsoft.dto.business.OutParamPrintData doBusiness(com.dcdzsoft.dto.business.InParamPTPrintInwardReceive p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTPrintInwardReceive bean = ( com.dcdzsoft.business.pt.PTPrintInwardReceive)aop.bind(com.dcdzsoft.business.pt.PTPrintInwardReceive.class);
		return bean.doBusiness(p1);
	}
	public static com.dcdzsoft.dto.business.OutParamPrintData doBusiness(com.dcdzsoft.dto.business.InParamDMDeliveryTransfer p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMDeliveryTransfer bean = ( com.dcdzsoft.business.dm.DMDeliveryTransfer)aop.bind(com.dcdzsoft.business.dm.DMDeliveryTransfer.class);
		return bean.doBusiness(p1);
	}
	public static com.dcdzsoft.dto.business.OutParamDMBusinessPartnerLogin doBusiness(com.dcdzsoft.dto.business.InParamDMBusinessPartnerLogin p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMBusinessPartnerLogin bean = ( com.dcdzsoft.business.dm.DMBusinessPartnerLogin)aop.bind(com.dcdzsoft.business.dm.DMBusinessPartnerLogin.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamDMBusinessPartnerModPwd p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMBusinessPartnerModPwd bean = ( com.dcdzsoft.business.dm.DMBusinessPartnerModPwd)aop.bind(com.dcdzsoft.business.dm.DMBusinessPartnerModPwd.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamDMBusinessPartnerForgetPwd p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMBusinessPartnerForgetPwd bean = ( com.dcdzsoft.business.dm.DMBusinessPartnerForgetPwd)aop.bind(com.dcdzsoft.business.dm.DMBusinessPartnerForgetPwd.class);
		return bean.doBusiness(p1);
	}
	public static com.dcdzsoft.dto.business.OutParamDMBusinessPartnerTopUP doBusiness(com.dcdzsoft.dto.business.InParamDMBusinessPartnerTopUP p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMBusinessPartnerTopUP bean = ( com.dcdzsoft.business.dm.DMBusinessPartnerTopUP)aop.bind(com.dcdzsoft.business.dm.DMBusinessPartnerTopUP.class);
		return bean.doBusiness(p1);
	}
	public static com.dcdzsoft.dto.business.OutParamDMBPBalanceUpdate doBusiness(com.dcdzsoft.dto.business.InParamDMBPBalanceUpdate p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMBPBalanceUpdate bean = ( com.dcdzsoft.business.dm.DMBPBalanceUpdate)aop.bind(com.dcdzsoft.business.dm.DMBPBalanceUpdate.class);
		return bean.doBusiness(p1);
	}
	public static com.dcdzsoft.dto.business.OutParamDMPartnerBalanceQry doBusiness(com.dcdzsoft.dto.business.InParamDMPartnerBalanceQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMPartnerBalanceQry bean = ( com.dcdzsoft.business.dm.DMPartnerBalanceQry)aop.bind(com.dcdzsoft.business.dm.DMPartnerBalanceQry.class);
		return bean.doBusiness(p1);
	}
	public static java.util.List<com.dcdzsoft.dto.business.OutParamDMPartnerServicesQry> doBusiness(com.dcdzsoft.dto.business.InParamDMPartnerServicesQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMPartnerServicesQry bean = ( com.dcdzsoft.business.dm.DMPartnerServicesQry)aop.bind(com.dcdzsoft.business.dm.DMPartnerServicesQry.class);
		return bean.doBusiness(p1);
	}
	
	public static com.dcdzsoft.dto.business.OutParamDMDeliveryCreate doBusiness(com.dcdzsoft.dto.business.InParamDMDeliveryCreate p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMDeliveryCreate bean = ( com.dcdzsoft.business.dm.DMDeliveryCreate)aop.bind(com.dcdzsoft.business.dm.DMDeliveryCreate.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamDMDeliveryCancel p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMDeliveryCancel bean = ( com.dcdzsoft.business.dm.DMDeliveryCancel)aop.bind(com.dcdzsoft.business.dm.DMDeliveryCancel.class);
		return bean.doBusiness(p1);
	}
	
	public static java.util.List<com.dcdzsoft.dto.business.OutParamDMDeliveryItemsQry> doBusiness(com.dcdzsoft.dto.business.InParamDMDeliveryItemsQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMDeliveryItemsQry bean = ( com.dcdzsoft.business.dm.DMDeliveryItemsQry)aop.bind(com.dcdzsoft.business.dm.DMDeliveryItemsQry.class);
		return bean.doBusiness(p1);
	}
	public static com.dcdzsoft.dto.business.OutParamDMDeliveryE1InfoQry doBusiness(com.dcdzsoft.dto.business.InParamDMDeliveryE1InfoQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMDeliveryE1InfoQry bean = ( com.dcdzsoft.business.dm.DMDeliveryE1InfoQry)aop.bind(com.dcdzsoft.business.dm.DMDeliveryE1InfoQry.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamDMDeliveryItemsQryCount p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMDeliveryItemsQryCount bean = ( com.dcdzsoft.business.dm.DMDeliveryItemsQryCount)aop.bind(com.dcdzsoft.business.dm.DMDeliveryItemsQryCount.class);
		return bean.doBusiness(p1);
	}
	public static com.dcdzsoft.dto.business.OutParamDMDeliveryItemDetailQry doBusiness(com.dcdzsoft.dto.business.InParamDMDeliveryItemDetailQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMDeliveryItemDetailQry bean = ( com.dcdzsoft.business.dm.DMDeliveryItemDetailQry)aop.bind(com.dcdzsoft.business.dm.DMDeliveryItemDetailQry.class);
		return bean.doBusiness(p1);
	}
	public static java.util.List<com.dcdzsoft.dto.business.OutParamLockerStationAddressQry> doBusiness(com.dcdzsoft.dto.business.InParamLockerStationAddressQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.tb.LockerStationAddressQry bean = ( com.dcdzsoft.business.tb.LockerStationAddressQry)aop.bind(com.dcdzsoft.business.tb.LockerStationAddressQry.class);
		return bean.doBusiness(p1);
	}
	
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamDMExportTransactions p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMExportTransactions bean = 
				( com.dcdzsoft.business.dm.DMExportTransactions)aop.bind(com.dcdzsoft.business.dm.DMExportTransactions.class);
		return bean.doBusiness(p1);
	}
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamDMBPBillsQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMBPBillsQry bean = ( com.dcdzsoft.business.dm.DMBPBillsQry)aop.bind(com.dcdzsoft.business.dm.DMBPBillsQry.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamDMBPBillsQryCount p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMBPBillsQryCount bean = ( com.dcdzsoft.business.dm.DMBPBillsQryCount)aop.bind(com.dcdzsoft.business.dm.DMBPBillsQryCount.class);
		return bean.doBusiness(p1);
	}
}
