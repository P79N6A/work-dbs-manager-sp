package com.dcdzsoft.client.web;

import com.dcdzsoft.aop.BusiBeanAOPBaseClass;

public class PTWebClientAdapter {
	protected PTWebClientAdapter(){}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTAutoLockOrder p1)
			  throws com.dcdzsoft.EduException
    {
        BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTAutoLockOrder bean = ( com.dcdzsoft.business.pt.PTAutoLockOrder)aop.bind(com.dcdzsoft.business.pt.PTAutoLockOrder.class);
		return bean.doBusiness(p1);
    }
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTAutoDADCancel p1)
			  throws com.dcdzsoft.EduException
    {
        BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
        com.dcdzsoft.business.pt.PTAutoDADCancel bean = ( com.dcdzsoft.business.pt.PTAutoDADCancel)aop.bind(com.dcdzsoft.business.pt.PTAutoDADCancel.class);
        return bean.doBusiness(p1);
    }
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTDeliveryItemAdd p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTDeliveryItemAdd bean = ( com.dcdzsoft.business.pt.PTDeliveryItemAdd)aop.bind(com.dcdzsoft.business.pt.PTDeliveryItemAdd.class);
		return bean.doBusiness(p1);
	}
	
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamPTDeliveryItemQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTDeliveryItemQry bean = ( com.dcdzsoft.business.pt.PTDeliveryItemQry)aop.bind(com.dcdzsoft.business.pt.PTDeliveryItemQry.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTDeliveryItemQryCount p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTDeliveryItemQryCount bean = ( com.dcdzsoft.business.pt.PTDeliveryItemQryCount)aop.bind(com.dcdzsoft.business.pt.PTDeliveryItemQryCount.class);
		return bean.doBusiness(p1);
	}
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamPTItemLifeCycleQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTItemLifeCycleQry bean = ( com.dcdzsoft.business.pt.PTItemLifeCycleQry)aop.bind(com.dcdzsoft.business.pt.PTItemLifeCycleQry.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTParcelsAssign p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTParcelsAssign bean = ( com.dcdzsoft.business.pt.PTParcelsAssign)aop.bind(com.dcdzsoft.business.pt.PTParcelsAssign.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTParcelsUnAssign p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTParcelsUnAssign bean = ( com.dcdzsoft.business.pt.PTParcelsUnAssign)aop.bind(com.dcdzsoft.business.pt.PTParcelsUnAssign.class);
		return bean.doBusiness(p1);
	}
	
	public static String doBusiness(com.dcdzsoft.dto.business.InParamPTParcelsReceive p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTParcelsReceive bean = ( com.dcdzsoft.business.pt.PTParcelsReceive)aop.bind(com.dcdzsoft.business.pt.PTParcelsReceive.class);
		return bean.doBusiness(p1);
	}
	
	public static String doBusiness(com.dcdzsoft.dto.business.InParamPTParcelsTransfer p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTParcelsTransfer bean = ( com.dcdzsoft.business.pt.PTParcelsTransfer)aop.bind(com.dcdzsoft.business.pt.PTParcelsTransfer.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTDropOrderItemsAdd p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTDropOrderItemsAdd bean = ( com.dcdzsoft.business.pt.PTDropOrderItemsAdd)aop.bind(com.dcdzsoft.business.pt.PTDropOrderItemsAdd.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTDropOrderItemsDel p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTDropOrderItemsDel bean = ( com.dcdzsoft.business.pt.PTDropOrderItemsDel)aop.bind(com.dcdzsoft.business.pt.PTDropOrderItemsDel.class);
		return bean.doBusiness(p1);
	}
	
	public static String doBusiness(com.dcdzsoft.dto.business.InParamPTDropOrderItemsExe p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTDropOrderItemsExe bean = ( com.dcdzsoft.business.pt.PTDropOrderItemsExe)aop.bind(com.dcdzsoft.business.pt.PTDropOrderItemsExe.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTParcelsManExpire p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTParcelsManExpire bean = ( com.dcdzsoft.business.pt.PTParcelsManExpire)aop.bind(com.dcdzsoft.business.pt.PTParcelsManExpire.class);
		return bean.doBusiness(p1);
	}
	
	public static String doBusiness(com.dcdzsoft.dto.business.InParamPTExpParcelsMissing p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTExpParcelsMissing bean = ( com.dcdzsoft.business.pt.PTExpParcelsMissing)aop.bind(com.dcdzsoft.business.pt.PTExpParcelsMissing.class);
		return bean.doBusiness(p1);
	}
	public static String doBusiness(com.dcdzsoft.dto.business.InParamPTConfirmParcelsMissing p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTConfirmParcelsMissing bean = ( com.dcdzsoft.business.pt.PTConfirmParcelsMissing)aop.bind(com.dcdzsoft.business.pt.PTConfirmParcelsMissing.class);
		return bean.doBusiness(p1);
	}
	
	public static String doBusiness(com.dcdzsoft.dto.business.InParamPTExpParcelsRetrieve p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTExpParcelsRetrieve bean = ( com.dcdzsoft.business.pt.PTExpParcelsRetrieve)aop.bind(com.dcdzsoft.business.pt.PTExpParcelsRetrieve.class);
		return bean.doBusiness(p1);
	}
	
	public static String doBusiness(com.dcdzsoft.dto.business.InParamPTExpParcelsReturn p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTExpParcelsReturn bean = ( com.dcdzsoft.business.pt.PTExpParcelsReturn)aop.bind(com.dcdzsoft.business.pt.PTExpParcelsReturn.class);
		return bean.doBusiness(p1);
	}
	
	public static String doBusiness(com.dcdzsoft.dto.business.InParamPTExpParcesRedistribute p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTExpParcesRedistribute bean = ( com.dcdzsoft.business.pt.PTExpParcesRedistribute)aop.bind(com.dcdzsoft.business.pt.PTExpParcesRedistribute.class);
		return bean.doBusiness(p1);
	}
	
	public static String doBusiness(com.dcdzsoft.dto.business.InParamPTNextReportOrderID p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTNextReportOrderID bean = ( com.dcdzsoft.business.pt.PTNextReportOrderID)aop.bind(com.dcdzsoft.business.pt.PTNextReportOrderID.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTDeliverPackage2DB p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTDeliverPackage2DB bean = ( com.dcdzsoft.business.pt.PTDeliverPackage2DB)aop.bind(com.dcdzsoft.business.pt.PTDeliverPackage2DB.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTInboxPackage2DB p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTInboxPackage2DB bean = ( com.dcdzsoft.business.pt.PTInboxPackage2DB)aop.bind(com.dcdzsoft.business.pt.PTInboxPackage2DB.class);
		return bean.doBusiness(p1);
	}
	
	public static com.dcdzsoft.dto.business.OutParamPTPostmanLogin doBusiness(com.dcdzsoft.dto.business.InParamPTPostmanLogin p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTPostmanLogin bean = ( com.dcdzsoft.business.pt.PTPostmanLogin)aop.bind(com.dcdzsoft.business.pt.PTPostmanLogin.class);
		return bean.doBusiness(p1);
	}
	
	public static java.util.List<com.dcdzsoft.dto.business.OutParamPTReadPackageQry> doBusiness(com.dcdzsoft.dto.business.InParamPTReadPackageQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTReadPackageQry bean = ( com.dcdzsoft.business.pt.PTReadPackageQry)aop.bind(com.dcdzsoft.business.pt.PTReadPackageQry.class);
		return bean.doBusiness(p1);
	}
	
	public static com.dcdzsoft.dto.business.OutParamPTDeliveryPackage doBusiness(com.dcdzsoft.dto.business.InParamPTDeliveryPackage p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTDeliveryPackage bean = ( com.dcdzsoft.business.pt.PTDeliveryPackage)aop.bind(com.dcdzsoft.business.pt.PTDeliveryPackage.class);
		return bean.doBusiness(p1);
	}
	
	public static java.util.List<com.dcdzsoft.dto.business.OutParamPTExpiredPackQry> doBusiness(com.dcdzsoft.dto.business.InParamPTExpiredPackQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTExpiredPackQry bean = ( com.dcdzsoft.business.pt.PTExpiredPackQry)aop.bind(com.dcdzsoft.business.pt.PTExpiredPackQry.class);
		return bean.doBusiness(p1);
	}
	
	public static com.dcdzsoft.dto.business.OutParamPTWithdrawExpiredPack doBusiness(com.dcdzsoft.dto.business.InParamPTWithdrawExpiredPack p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTWithdrawExpiredPack bean = ( com.dcdzsoft.business.pt.PTWithdrawExpiredPack)aop.bind(com.dcdzsoft.business.pt.PTWithdrawExpiredPack.class);
		return bean.doBusiness(p1);
	}
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamPTCompFreeBoxCountQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTCompFreeBoxCountQry bean = ( com.dcdzsoft.business.pt.PTCompFreeBoxCountQry)aop.bind(com.dcdzsoft.business.pt.PTCompFreeBoxCountQry.class);
		return bean.doBusiness(p1);
	}
	
	public static com.dcdzsoft.dto.business.OutParamPTCompFreeBoxListQry doBusiness(com.dcdzsoft.dto.business.InParamPTCompFreeBoxListQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTCompFreeBoxListQry bean = ( com.dcdzsoft.business.pt.PTCompFreeBoxListQry)aop.bind(com.dcdzsoft.business.pt.PTCompFreeBoxListQry.class);
		return bean.doBusiness(p1);
	}
	
	public static com.dcdzsoft.dto.business.OutParamPTAllocateeBox doBusiness(com.dcdzsoft.dto.business.InParamPTAllocateeBox p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTAllocateeBox bean = ( com.dcdzsoft.business.pt.PTAllocateeBox)aop.bind(com.dcdzsoft.business.pt.PTAllocateeBox.class);
		return bean.doBusiness(p1);
	}
	public static com.dcdzsoft.dto.business.OutParamPTPackageDetail doBusiness(com.dcdzsoft.dto.business.InParamPTPackageDetail p1)
            throws com.dcdzsoft.EduException
  {
      BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
      com.dcdzsoft.business.pt.PTPackageDetail bean = 
              ( com.dcdzsoft.business.pt.PTPackageDetail)aop.bind(com.dcdzsoft.business.pt.PTPackageDetail.class);
      return bean.doBusiness(p1);
  }
	public static com.dcdzsoft.dto.business.OutParamPrintData doBusiness(com.dcdzsoft.dto.business.InParamPTPrintConfirmation p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTPrintConfirmation bean = ( com.dcdzsoft.business.pt.PTPrintConfirmation)aop.bind(com.dcdzsoft.business.pt.PTPrintConfirmation.class);
		return bean.doBusiness(p1);
	}
	public static com.dcdzsoft.dto.business.OutParamPrintData doBusiness(com.dcdzsoft.dto.business.InParamPTPrintTransfer p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTPrintTransfer bean = ( com.dcdzsoft.business.pt.PTPrintTransfer)aop.bind(com.dcdzsoft.business.pt.PTPrintTransfer.class);
		return bean.doBusiness(p1);
	}
	public static com.dcdzsoft.dto.business.OutParamPrintData doBusiness(com.dcdzsoft.dto.business.InParamPTPrintDropOrder p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTPrintDropOrder bean = ( com.dcdzsoft.business.pt.PTPrintDropOrder)aop.bind(com.dcdzsoft.business.pt.PTPrintDropOrder.class);
		return bean.doBusiness(p1);
	}
	public static com.dcdzsoft.dto.business.OutParamPrintData doBusiness(com.dcdzsoft.dto.business.InParamPTPrintReturn p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTPrintReturn bean = ( com.dcdzsoft.business.pt.PTPrintReturn)aop.bind(com.dcdzsoft.business.pt.PTPrintReturn.class);
		return bean.doBusiness(p1);
	}
	public static com.dcdzsoft.dto.business.OutParamPrintData doBusiness(com.dcdzsoft.dto.business.InParamPTPrintRedistribute p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTPrintRedistribute bean = ( com.dcdzsoft.business.pt.PTPrintRedistribute)aop.bind(com.dcdzsoft.business.pt.PTPrintRedistribute.class);
		return bean.doBusiness(p1);
	}
	
	public static com.dcdzsoft.dto.business.OutParamPrintData doBusiness(com.dcdzsoft.dto.business.InParamPTPrintMissing p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTPrintMissing bean = ( com.dcdzsoft.business.pt.PTPrintMissing)aop.bind(com.dcdzsoft.business.pt.PTPrintMissing.class);
		return bean.doBusiness(p1);
	}
	public static com.dcdzsoft.dto.business.OutParamPrintData doBusiness(com.dcdzsoft.dto.business.InParamPTPrintConfirmLost p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTPrintConfirmLost bean = ( com.dcdzsoft.business.pt.PTPrintConfirmLost)aop.bind(com.dcdzsoft.business.pt.PTPrintConfirmLost.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTModExpiredTime p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTModExpiredTime bean = ( com.dcdzsoft.business.pt.PTModExpiredTime)aop.bind(com.dcdzsoft.business.pt.PTModExpiredTime.class);
		return bean.doBusiness(p1);
	}
    public static int doBusiness(com.dcdzsoft.dto.business.InParamPTModPackageStatus p1)
	           throws com.dcdzsoft.EduException
	{
        BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
        com.dcdzsoft.business.pt.PTModPackageStatus bean = ( com.dcdzsoft.business.pt.PTModPackageStatus)aop.bind(com.dcdzsoft.business.pt.PTModPackageStatus.class);
        return bean.doBusiness(p1);
    }
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTTransferItemMod p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTTransferItemMod bean = ( com.dcdzsoft.business.pt.PTTransferItemMod)aop.bind(com.dcdzsoft.business.pt.PTTransferItemMod.class);
		return bean.doBusiness(p1);
	}
	/**
	 * 修改发送失败的状态，以便重新发送
	 * @return
	 * @throws com.dcdzsoft.EduException
	 */
	public static int doBusiness(com.dcdzsoft.business.pt.PTTransferItemModFailReSend p1) throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTTransferItemModFailReSend bean = 
				( com.dcdzsoft.business.pt.PTTransferItemModFailReSend)aop.bind(com.dcdzsoft.business.pt.PTTransferItemModFailReSend.class);
		return bean.doBusiness(p1);
	}
	/**
     * 删除已发送订单更新状态的数据
     * @return
     * @throws EduException
     */
    public static int doBusiness(com.dcdzsoft.business.pt.PTTransferUpdateItemDel p1) throws com.dcdzsoft.EduException{
    	BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTTransferUpdateItemDel bean = 
				( com.dcdzsoft.business.pt.PTTransferUpdateItemDel)aop.bind(com.dcdzsoft.business.pt.PTTransferUpdateItemDel.class);
		return bean.doBusiness(p1);
    }
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamPTTransferItemQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTTransferItemQry bean = ( com.dcdzsoft.business.pt.PTTransferItemQry)aop.bind(com.dcdzsoft.business.pt.PTTransferItemQry.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTTransferItemQryCount p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTTransferItemQryCount bean = ( com.dcdzsoft.business.pt.PTTransferItemQryCount)aop.bind(com.dcdzsoft.business.pt.PTTransferItemQryCount.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTTransferItemSend p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTTransferItemSend bean = ( com.dcdzsoft.business.pt.PTTransferItemSend)aop.bind(com.dcdzsoft.business.pt.PTTransferItemSend.class);
		return bean.doBusiness(p1);
	}
	
	public static java.util.List<com.dcdzsoft.dto.business.OutParamPTDeliveryItemDetail> doBusiness(com.dcdzsoft.dto.business.InParamPTDeliveryItemDetail p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTDeliveryItemDetail bean = ( com.dcdzsoft.business.pt.PTDeliveryItemDetail)aop.bind(com.dcdzsoft.business.pt.PTDeliveryItemDetail.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTAbnormalReturn p1)
            throws com.dcdzsoft.EduException
    {
        BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
        com.dcdzsoft.business.pt.PTAbnormalReturn bean = 
                ( com.dcdzsoft.business.pt.PTAbnormalReturn)aop.bind(com.dcdzsoft.business.pt.PTAbnormalReturn.class);
        return bean.doBusiness(p1);
    }
	//转运
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTTransportPackAdd p1)
            throws com.dcdzsoft.EduException
    {
        BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
        com.dcdzsoft.business.pt.PTTransportPackAdd bean = 
                ( com.dcdzsoft.business.pt.PTTransportPackAdd)aop.bind(com.dcdzsoft.business.pt.PTTransportPackAdd.class);
        return bean.doBusiness(p1);
    }
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTTransportPackDel p1)
            throws com.dcdzsoft.EduException
    {
        BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
        com.dcdzsoft.business.pt.PTTransportPackDel bean = 
                ( com.dcdzsoft.business.pt.PTTransportPackDel)aop.bind(com.dcdzsoft.business.pt.PTTransportPackDel.class);
        return bean.doBusiness(p1);
    }
	public static String doBusiness(com.dcdzsoft.dto.business.InParamPTTransportPackExe p1)
            throws com.dcdzsoft.EduException
    {
        BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
        com.dcdzsoft.business.pt.PTTransportPackExe bean = 
                ( com.dcdzsoft.business.pt.PTTransportPackExe)aop.bind(com.dcdzsoft.business.pt.PTTransportPackExe.class);
        return bean.doBusiness(p1);
    }
	//投递柜台
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTCounterPickup p1)
            throws com.dcdzsoft.EduException
    {
        BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
        com.dcdzsoft.business.pt.PTCounterPickup bean = 
                ( com.dcdzsoft.business.pt.PTCounterPickup)aop.bind(com.dcdzsoft.business.pt.PTCounterPickup.class);
        return bean.doBusiness(p1);
    }
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTCounterReceive p1)
            throws com.dcdzsoft.EduException
    {
        BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
        com.dcdzsoft.business.pt.PTCounterReceive bean = 
                ( com.dcdzsoft.business.pt.PTCounterReceive)aop.bind(com.dcdzsoft.business.pt.PTCounterReceive.class);
        return bean.doBusiness(p1);
    }
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTCounterReturn p1)
            throws com.dcdzsoft.EduException
    {
        BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
        com.dcdzsoft.business.pt.PTCounterReturn bean = 
                ( com.dcdzsoft.business.pt.PTCounterReturn)aop.bind(com.dcdzsoft.business.pt.PTCounterReturn.class);
        return bean.doBusiness(p1);
    }
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPTErrPackDel p1)
            throws com.dcdzsoft.EduException
    {
        BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
        com.dcdzsoft.business.pt.PTErrPackDel bean = 
                ( com.dcdzsoft.business.pt.PTErrPackDel)aop.bind(com.dcdzsoft.business.pt.PTErrPackDel.class);
        return bean.doBusiness(p1);
    }
	
	public static String doBusiness(com.dcdzsoft.dto.business.InParamPTFJG2DB p1)
            throws com.dcdzsoft.EduException
    {
        BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
        com.dcdzsoft.business.pt.PTFJG2DB bean = 
                ( com.dcdzsoft.business.pt.PTFJG2DB)aop.bind(com.dcdzsoft.business.pt.PTFJG2DB.class);
        return bean.doBusiness(p1);
    }
	
}
