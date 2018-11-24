package com.dcdzsoft.client.web;

import com.dcdzsoft.aop.BusiBeanAOPBaseClass;
import com.dcdzsoft.dto.business.InParamIMCustomerModStatusAuto;
import com.dcdzsoft.dto.business.OutParamIMCustomerInfoQry;

public class IMWebClientAdapter {
	public IMWebClientAdapter(){}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMCompanyAdd p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMCompanyAdd bean = ( com.dcdzsoft.business.im.IMCompanyAdd)aop.bind(com.dcdzsoft.business.im.IMCompanyAdd.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMCompanyMod p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMCompanyMod bean = ( com.dcdzsoft.business.im.IMCompanyMod)aop.bind(com.dcdzsoft.business.im.IMCompanyMod.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMCompanyDel p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMCompanyDel bean = ( com.dcdzsoft.business.im.IMCompanyDel)aop.bind(com.dcdzsoft.business.im.IMCompanyDel.class);
		return bean.doBusiness(p1);
	}
	
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamIMCompanyQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMCompanyQry bean = ( com.dcdzsoft.business.im.IMCompanyQry)aop.bind(com.dcdzsoft.business.im.IMCompanyQry.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMCompanyQryCount p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMCompanyQryCount bean = ( com.dcdzsoft.business.im.IMCompanyQryCount)aop.bind(com.dcdzsoft.business.im.IMCompanyQryCount.class);
		return bean.doBusiness(p1);
	}
	
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamIMCompanyListQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMCompanyListQry bean = ( com.dcdzsoft.business.im.IMCompanyListQry)aop.bind(com.dcdzsoft.business.im.IMCompanyListQry.class);
		return bean.doBusiness(p1);
	}
	
	//Zone
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMZoneAdd p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMZoneAdd bean = ( com.dcdzsoft.business.im.IMZoneAdd)aop.bind(com.dcdzsoft.business.im.IMZoneAdd.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMZoneMod p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMZoneMod bean = ( com.dcdzsoft.business.im.IMZoneMod)aop.bind(com.dcdzsoft.business.im.IMZoneMod.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMZoneDel p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMZoneDel bean = ( com.dcdzsoft.business.im.IMZoneDel)aop.bind(com.dcdzsoft.business.im.IMZoneDel.class);
		return bean.doBusiness(p1);
	}
	
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamIMZoneQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMZoneQry bean = ( com.dcdzsoft.business.im.IMZoneQry)aop.bind(com.dcdzsoft.business.im.IMZoneQry.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMZoneQryCount p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMZoneQryCount bean = ( com.dcdzsoft.business.im.IMZoneQryCount)aop.bind(com.dcdzsoft.business.im.IMZoneQryCount.class);
		return bean.doBusiness(p1);
	}
	
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamIMZoneListQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMZoneListQry bean = ( com.dcdzsoft.business.im.IMZoneListQry)aop.bind(com.dcdzsoft.business.im.IMZoneListQry.class);
		return bean.doBusiness(p1);
	}
	//PPC
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMPPCAdd p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMPPCAdd bean = ( com.dcdzsoft.business.im.IMPPCAdd)aop.bind(com.dcdzsoft.business.im.IMPPCAdd.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMPPCMod p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMPPCMod bean = ( com.dcdzsoft.business.im.IMPPCMod)aop.bind(com.dcdzsoft.business.im.IMPPCMod.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMPPCDel p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMPPCDel bean = ( com.dcdzsoft.business.im.IMPPCDel)aop.bind(com.dcdzsoft.business.im.IMPPCDel.class);
		return bean.doBusiness(p1);
	}
	
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamIMPPCQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMPPCQry bean = ( com.dcdzsoft.business.im.IMPPCQry)aop.bind(com.dcdzsoft.business.im.IMPPCQry.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMPPCQryCount p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMPPCQryCount bean = ( com.dcdzsoft.business.im.IMPPCQryCount)aop.bind(com.dcdzsoft.business.im.IMPPCQryCount.class);
		return bean.doBusiness(p1);
	}
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamIMPPCListQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMPPCListQry bean = ( com.dcdzsoft.business.im.IMPPCListQry)aop.bind(com.dcdzsoft.business.im.IMPPCListQry.class);
		return bean.doBusiness(p1);
	}
	//BusiPartner
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMBusiPartnerAdd p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMBusiPartnerAdd bean = ( com.dcdzsoft.business.im.IMBusiPartnerAdd)aop.bind(com.dcdzsoft.business.im.IMBusiPartnerAdd.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMBusiPartnerMod p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMBusiPartnerMod bean = ( com.dcdzsoft.business.im.IMBusiPartnerMod)aop.bind(com.dcdzsoft.business.im.IMBusiPartnerMod.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMBusiPartnerDel p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMBusiPartnerDel bean = ( com.dcdzsoft.business.im.IMBusiPartnerDel)aop.bind(com.dcdzsoft.business.im.IMBusiPartnerDel.class);
		return bean.doBusiness(p1);
	}
	
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamIMBusiPartnerQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMBusiPartnerQry bean = ( com.dcdzsoft.business.im.IMBusiPartnerQry)aop.bind(com.dcdzsoft.business.im.IMBusiPartnerQry.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMBusiPartnerQryCount p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMBusiPartnerQryCount bean = ( com.dcdzsoft.business.im.IMBusiPartnerQryCount)aop.bind(com.dcdzsoft.business.im.IMBusiPartnerQryCount.class);
		return bean.doBusiness(p1);
	}
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamIMBusiPartnerListQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMBusiPartnerListQry bean = ( com.dcdzsoft.business.im.IMBusiPartnerListQry)aop.bind(com.dcdzsoft.business.im.IMBusiPartnerListQry.class);
		return bean.doBusiness(p1);
	}
	//IMEcomPartner
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMEcomPartnerAdd p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMEcomPartnerAdd bean = ( com.dcdzsoft.business.im.IMEcomPartnerAdd)aop.bind(com.dcdzsoft.business.im.IMEcomPartnerAdd.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMEcomPartnerMod p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMEcomPartnerMod bean = ( com.dcdzsoft.business.im.IMEcomPartnerMod)aop.bind(com.dcdzsoft.business.im.IMEcomPartnerMod.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMEcomPartnerDel p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMEcomPartnerDel bean = ( com.dcdzsoft.business.im.IMEcomPartnerDel)aop.bind(com.dcdzsoft.business.im.IMEcomPartnerDel.class);
		return bean.doBusiness(p1);
	}
	
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamIMEcomPartnerQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMEcomPartnerQry bean = ( com.dcdzsoft.business.im.IMEcomPartnerQry)aop.bind(com.dcdzsoft.business.im.IMEcomPartnerQry.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMEcomPartnerQryCount p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMEcomPartnerQryCount bean = ( com.dcdzsoft.business.im.IMEcomPartnerQryCount)aop.bind(com.dcdzsoft.business.im.IMEcomPartnerQryCount.class);
		return bean.doBusiness(p1);
	}
	//IMCustomer
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMCustomerAdd p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMCustomerAdd bean = ( com.dcdzsoft.business.im.IMCustomerAdd)aop.bind(com.dcdzsoft.business.im.IMCustomerAdd.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMCustomerMod p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMCustomerMod bean = ( com.dcdzsoft.business.im.IMCustomerMod)aop.bind(com.dcdzsoft.business.im.IMCustomerMod.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMCustomerDel p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMCustomerDel bean = ( com.dcdzsoft.business.im.IMCustomerDel)aop.bind(com.dcdzsoft.business.im.IMCustomerDel.class);
		return bean.doBusiness(p1);
	}
	
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamIMCustomerQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMCustomerQry bean = ( com.dcdzsoft.business.im.IMCustomerQry)aop.bind(com.dcdzsoft.business.im.IMCustomerQry.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMCustomerQryCount p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMCustomerQryCount bean = ( com.dcdzsoft.business.im.IMCustomerQryCount)aop.bind(com.dcdzsoft.business.im.IMCustomerQryCount.class);
		return bean.doBusiness(p1);
	}
	public static String doBusiness(com.dcdzsoft.dto.business.InParamIMCustomerInfoQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMCustomerInfoQry bean = ( com.dcdzsoft.business.im.IMCustomerInfoQry)aop.bind(com.dcdzsoft.business.im.IMCustomerInfoQry.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMCustomerModStatusAuto p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMCustomerModStatusAuto bean = ( com.dcdzsoft.business.im.IMCustomerModStatusAuto)aop.bind(com.dcdzsoft.business.im.IMCustomerModStatusAuto.class);
		return bean.doBusiness(p1);
	}
	//IMGateway
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMGatewayAdd p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMGatewayAdd bean = ( com.dcdzsoft.business.im.IMGatewayAdd)aop.bind(com.dcdzsoft.business.im.IMGatewayAdd.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMGatewayMod p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMGatewayMod bean = ( com.dcdzsoft.business.im.IMGatewayMod)aop.bind(com.dcdzsoft.business.im.IMGatewayMod.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMGatewayDel p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMGatewayDel bean = ( com.dcdzsoft.business.im.IMGatewayDel)aop.bind(com.dcdzsoft.business.im.IMGatewayDel.class);
		return bean.doBusiness(p1);
	}
	
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamIMGatewayQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMGatewayQry bean = ( com.dcdzsoft.business.im.IMGatewayQry)aop.bind(com.dcdzsoft.business.im.IMGatewayQry.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMGatewayQryCount p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMGatewayQryCount bean = ( com.dcdzsoft.business.im.IMGatewayQryCount)aop.bind(com.dcdzsoft.business.im.IMGatewayQryCount.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMEmailAccountAdd p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMEmailAccountAdd bean = ( com.dcdzsoft.business.im.IMEmailAccountAdd)aop.bind(com.dcdzsoft.business.im.IMEmailAccountAdd.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMEmailAccountMod p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMEmailAccountMod bean = ( com.dcdzsoft.business.im.IMEmailAccountMod)aop.bind(com.dcdzsoft.business.im.IMEmailAccountMod.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMEmailAccountDel p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMEmailAccountDel bean = ( com.dcdzsoft.business.im.IMEmailAccountDel)aop.bind(com.dcdzsoft.business.im.IMEmailAccountDel.class);
		return bean.doBusiness(p1);
	}
	
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamIMEmailAccountQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMEmailAccountQry bean = ( com.dcdzsoft.business.im.IMEmailAccountQry)aop.bind(com.dcdzsoft.business.im.IMEmailAccountQry.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMEmailAccountQryCount p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMEmailAccountQryCount bean = ( com.dcdzsoft.business.im.IMEmailAccountQryCount)aop.bind(com.dcdzsoft.business.im.IMEmailAccountQryCount.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMMsgTemplateAdd p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMMsgTemplateAdd bean = ( com.dcdzsoft.business.im.IMMsgTemplateAdd)aop.bind(com.dcdzsoft.business.im.IMMsgTemplateAdd.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMMsgTemplateMod p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMMsgTemplateMod bean = ( com.dcdzsoft.business.im.IMMsgTemplateMod)aop.bind(com.dcdzsoft.business.im.IMMsgTemplateMod.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMMsgTemplateDel p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMMsgTemplateDel bean = ( com.dcdzsoft.business.im.IMMsgTemplateDel)aop.bind(com.dcdzsoft.business.im.IMMsgTemplateDel.class);
		return bean.doBusiness(p1);
	}
	
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamIMMsgTemplateQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMMsgTemplateQry bean = ( com.dcdzsoft.business.im.IMMsgTemplateQry)aop.bind(com.dcdzsoft.business.im.IMMsgTemplateQry.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMMsgTemplateQryCount p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMMsgTemplateQryCount bean = ( com.dcdzsoft.business.im.IMMsgTemplateQryCount)aop.bind(com.dcdzsoft.business.im.IMMsgTemplateQryCount.class);
		return bean.doBusiness(p1);
	}
	
	//IMServiceRate
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMServiceRateAdd p1)                                                               
			  throws com.dcdzsoft.EduException                                                                                                       
	{                                                                                                                                            
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();                                                                             
		com.dcdzsoft.business.im.IMServiceRateAdd bean = ( com.dcdzsoft.business.im.IMServiceRateAdd)aop.bind(com.dcdzsoft.business.im.IMServiceRateAdd.class);
		return bean.doBusiness(p1);                                                                                                                
	}                                                                                                                                            
                                                                                                                                             
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMServiceRateMod p1)                                                               
			  throws com.dcdzsoft.EduException                                                                                                       
	{                                                                                                                                            
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();                                                                             
		com.dcdzsoft.business.im.IMServiceRateMod bean = ( com.dcdzsoft.business.im.IMServiceRateMod)aop.bind(com.dcdzsoft.business.im.IMServiceRateMod.class);
		return bean.doBusiness(p1);                                                                                                                
	}                                                                                                                                            
	                                                                                                                                             
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMServiceRateDel p1)                                                               
			  throws com.dcdzsoft.EduException                                                                                                       
	{                                                                                                                                            
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();                                                                             
		com.dcdzsoft.business.im.IMServiceRateDel bean = ( com.dcdzsoft.business.im.IMServiceRateDel)aop.bind(com.dcdzsoft.business.im.IMServiceRateDel.class);
		return bean.doBusiness(p1);                                                                                                                
	}                                                                                                                                            
	                                                                                                                                             
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamIMServiceRateQry p1)                                                  
			  throws com.dcdzsoft.EduException                                                                                                       
	{                                                                                                                                            
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();                                                                             
		com.dcdzsoft.business.im.IMServiceRateQry bean = ( com.dcdzsoft.business.im.IMServiceRateQry)aop.bind(com.dcdzsoft.business.im.IMServiceRateQry.class);
		return bean.doBusiness(p1);                                                                                                                
	}                 
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMServiceRateQryCount p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMServiceRateQryCount bean = ( com.dcdzsoft.business.im.IMServiceRateQryCount)aop.bind(com.dcdzsoft.business.im.IMServiceRateQryCount.class);
		return bean.doBusiness(p1);
	}
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamIMServiceRateListQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMServiceRateListQry bean = ( com.dcdzsoft.business.im.IMServiceRateListQry)aop.bind(com.dcdzsoft.business.im.IMServiceRateListQry.class);
		return bean.doBusiness(p1);
	}
	//IMCollectZone
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMCollectZoneAdd p1)                                                               
			  throws com.dcdzsoft.EduException                                                                                                       
	{                                                                                                                                            
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();                                                                             
		com.dcdzsoft.business.im.IMCollectZoneAdd bean = ( com.dcdzsoft.business.im.IMCollectZoneAdd)aop.bind(com.dcdzsoft.business.im.IMCollectZoneAdd.class);
		return bean.doBusiness(p1);                                                                                                                
	}                                                                                                                                            
                                                                                                                                           
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMCollectZoneMod p1)                                                               
			  throws com.dcdzsoft.EduException                                                                                                       
	{                                                                                                                                            
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();                                                                             
		com.dcdzsoft.business.im.IMCollectZoneMod bean = ( com.dcdzsoft.business.im.IMCollectZoneMod)aop.bind(com.dcdzsoft.business.im.IMCollectZoneMod.class);
		return bean.doBusiness(p1);                                                                                                                
	}                                                                                                                                            
	                                                                                                                                             
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMCollectZoneDel p1)                                                               
			  throws com.dcdzsoft.EduException                                                                                                       
	{                                                                                                                                            
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();                                                                             
		com.dcdzsoft.business.im.IMCollectZoneDel bean = ( com.dcdzsoft.business.im.IMCollectZoneDel)aop.bind(com.dcdzsoft.business.im.IMCollectZoneDel.class);
		return bean.doBusiness(p1);                                                                                                                
	}                                                                                                                                            
	                                                                                                                                             
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamIMCollectZoneQry p1)                                                  
			  throws com.dcdzsoft.EduException                                                                                                       
	{                                                                                                                                            
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();                                                                             
		com.dcdzsoft.business.im.IMCollectZoneQry bean = ( com.dcdzsoft.business.im.IMCollectZoneQry)aop.bind(com.dcdzsoft.business.im.IMCollectZoneQry.class);
		return bean.doBusiness(p1);                                                                                                                
	}                 
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMCollectZoneQryCount p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMCollectZoneQryCount bean = ( com.dcdzsoft.business.im.IMCollectZoneQryCount)aop.bind(com.dcdzsoft.business.im.IMCollectZoneQryCount.class);
		return bean.doBusiness(p1);
	}
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamIMCollectZoneListQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMCollectZoneListQry bean = ( com.dcdzsoft.business.im.IMCollectZoneListQry)aop.bind(com.dcdzsoft.business.im.IMCollectZoneListQry.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMCompBoxRightDel p1)                                                               
			  throws com.dcdzsoft.EduException                                                                                                       
	{                                                                                                                                            
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();                                                                             
		com.dcdzsoft.business.im.IMCompBoxRightDel bean = ( com.dcdzsoft.business.im.IMCompBoxRightDel)aop.bind(com.dcdzsoft.business.im.IMCompBoxRightDel.class);
		return bean.doBusiness(p1);                                                                                                                
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMCompBoxRightAdd p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMCompBoxRightAdd bean = ( com.dcdzsoft.business.im.IMCompBoxRightAdd)aop.bind(com.dcdzsoft.business.im.IMCompBoxRightAdd.class);
		return bean.doBusiness(p1);
	}
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamIMCompBoxRightQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMCompBoxRightQry bean = ( com.dcdzsoft.business.im.IMCompBoxRightQry)aop.bind(com.dcdzsoft.business.im.IMCompBoxRightQry.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMCompBoxRightQryCount p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMCompBoxRightQryCount bean = ( com.dcdzsoft.business.im.IMCompBoxRightQryCount)aop.bind(com.dcdzsoft.business.im.IMCompBoxRightQryCount.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMZoneLockerRightAdd p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMZoneLockerRightAdd bean = ( com.dcdzsoft.business.im.IMZoneLockerRightAdd)aop.bind(com.dcdzsoft.business.im.IMZoneLockerRightAdd.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMZoneLockerRightDel p1)                                                               
			  throws com.dcdzsoft.EduException                                                                                                       
	{                                                                                                                                            
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();                                                                             
		com.dcdzsoft.business.im.IMZoneLockerRightDel bean = ( com.dcdzsoft.business.im.IMZoneLockerRightDel)aop.bind(com.dcdzsoft.business.im.IMZoneLockerRightDel.class);
		return bean.doBusiness(p1);                                                                                                                
	}
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamIMZoneLockerRightQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMZoneLockerRightQry bean = ( com.dcdzsoft.business.im.IMZoneLockerRightQry)aop.bind(com.dcdzsoft.business.im.IMZoneLockerRightQry.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMZoneLockerRightQryCount p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMZoneLockerRightQryCount bean = ( com.dcdzsoft.business.im.IMZoneLockerRightQryCount)aop.bind(com.dcdzsoft.business.im.IMZoneLockerRightQryCount.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMBPartnerServiceRightAdd p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMBPartnerServiceRightAdd bean = ( com.dcdzsoft.business.im.IMBPartnerServiceRightAdd)aop.bind(com.dcdzsoft.business.im.IMBPartnerServiceRightAdd.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMBPartnerServiceRightDel p1)                                                               
			  throws com.dcdzsoft.EduException                                                                                                       
	{                                                                                                                                            
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();                                                                             
		com.dcdzsoft.business.im.IMBPartnerServiceRightDel bean = ( com.dcdzsoft.business.im.IMBPartnerServiceRightDel)aop.bind(com.dcdzsoft.business.im.IMBPartnerServiceRightDel.class);
		return bean.doBusiness(p1);                                                                                                                
	}
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamIMBPartnerServiceRightQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMBPartnerServiceRightQry bean = ( com.dcdzsoft.business.im.IMBPartnerServiceRightQry)aop.bind(com.dcdzsoft.business.im.IMBPartnerServiceRightQry.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamIMBPartnerServiceRightQryCount p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.im.IMBPartnerServiceRightQryCount bean = ( com.dcdzsoft.business.im.IMBPartnerServiceRightQryCount)aop.bind(com.dcdzsoft.business.im.IMBPartnerServiceRightQryCount.class);
		return bean.doBusiness(p1);
	}
}
