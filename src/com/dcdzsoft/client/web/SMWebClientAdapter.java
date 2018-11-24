package com.dcdzsoft.client.web;

import com.dcdzsoft.aop.BusiBeanAOPBaseClass;

public class  SMWebClientAdapter
{
	protected SMWebClientAdapter() {}

	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamSMDbCfgQry p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.sm.SMDbCfgQry bean = ( com.dcdzsoft.business.sm.SMDbCfgQry)aop.bind(com.dcdzsoft.business.sm.SMDbCfgQry.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamSMDbCfgSet p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.sm.SMDbCfgSet bean = ( com.dcdzsoft.business.sm.SMDbCfgSet)aop.bind(com.dcdzsoft.business.sm.SMDbCfgSet.class);
		return bean.doBusiness(p1);
	}

	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamSMGlobalCfgQry p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.sm.SMGlobalCfgQry bean = ( com.dcdzsoft.business.sm.SMGlobalCfgQry)aop.bind(com.dcdzsoft.business.sm.SMGlobalCfgQry.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamSMGlobalCfgSet p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.sm.SMGlobalCfgSet bean = ( com.dcdzsoft.business.sm.SMGlobalCfgSet)aop.bind(com.dcdzsoft.business.sm.SMGlobalCfgSet.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamSMLoadMailVm p1)
		  throws com.dcdzsoft.EduException
	{
		/*BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.sm.SMLoadMailVm bean = ( com.dcdzsoft.business.sm.SMLoadMailVm)aop.bind(com.dcdzsoft.business.sm.SMLoadMailVm.class);
		return bean.doBusiness(p1);*/
		return -1;
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamSMModServerCfg p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.sm.SMModServerCfg bean = ( com.dcdzsoft.business.sm.SMModServerCfg)aop.bind(com.dcdzsoft.business.sm.SMModServerCfg.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamSMModSignPwd p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.sm.SMModSignPwd bean = ( com.dcdzsoft.business.sm.SMModSignPwd)aop.bind(com.dcdzsoft.business.sm.SMModSignPwd.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamSMModSysVersion p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.sm.SMModSysVersion bean = ( com.dcdzsoft.business.sm.SMModSysVersion)aop.bind(com.dcdzsoft.business.sm.SMModSysVersion.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamSMModWelcomeInfo p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.sm.SMModWelcomeInfo bean = ( com.dcdzsoft.business.sm.SMModWelcomeInfo)aop.bind(com.dcdzsoft.business.sm.SMModWelcomeInfo.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamSMReloadCtrlParam p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.sm.SMReloadCtrlParam bean = ( com.dcdzsoft.business.sm.SMReloadCtrlParam)aop.bind(com.dcdzsoft.business.sm.SMReloadCtrlParam.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamSMReloadFuncData p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.sm.SMReloadFuncData bean = ( com.dcdzsoft.business.sm.SMReloadFuncData)aop.bind(com.dcdzsoft.business.sm.SMReloadFuncData.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamSMReloadLogCfg p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.sm.SMReloadLogCfg bean = ( com.dcdzsoft.business.sm.SMReloadLogCfg)aop.bind(com.dcdzsoft.business.sm.SMReloadLogCfg.class);
		return bean.doBusiness(p1);
	}

	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamSMSysInfoQry p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.sm.SMSysInfoQry bean = ( com.dcdzsoft.business.sm.SMSysInfoQry)aop.bind(com.dcdzsoft.business.sm.SMSysInfoQry.class);
		return bean.doBusiness(p1);
	}

	public static long doBusiness(com.dcdzsoft.dto.business.InParamSMSysTimeSync p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.sm.SMSysTimeSync bean = ( com.dcdzsoft.business.sm.SMSysTimeSync)aop.bind(com.dcdzsoft.business.sm.SMSysTimeSync.class);
		return bean.doBusiness(p1);
	}

	
}
