package com.dcdzsoft.client.web;

import com.dcdzsoft.aop.BusiBeanAOPBaseClass;

public class  TBWebClientAdapter
{
	protected TBWebClientAdapter() {}

	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamTBBoxQry p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.tb.TBBoxQry bean = ( com.dcdzsoft.business.tb.TBBoxQry)aop.bind(com.dcdzsoft.business.tb.TBBoxQry.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamTBBoxQryCount p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.tb.TBBoxQryCount bean = ( com.dcdzsoft.business.tb.TBBoxQryCount)aop.bind(com.dcdzsoft.business.tb.TBBoxQryCount.class);
		return bean.doBusiness(p1);
	}
	
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamTBBoxStatusInfoQry p1)
			throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.tb.TBBoxStatusInfoQry bean = ( com.dcdzsoft.business.tb.TBBoxStatusInfoQry)aop.bind(com.dcdzsoft.business.tb.TBBoxStatusInfoQry.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamTBBoxStatusInfoQryCount p1)
			 throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.tb.TBBoxStatusInfoQryCount bean = ( com.dcdzsoft.business.tb.TBBoxStatusInfoQryCount)aop.bind(com.dcdzsoft.business.tb.TBBoxStatusInfoQryCount.class);
		return bean.doBusiness(p1);
	}
	
	public static java.lang.String doBusiness(com.dcdzsoft.dto.business.InParamTBBoxStatusMod p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.tb.TBBoxStatusMod bean = ( com.dcdzsoft.business.tb.TBBoxStatusMod)aop.bind(com.dcdzsoft.business.tb.TBBoxStatusMod.class);
		return bean.doBusiness(p1);
	}

	public static java.lang.String doBusiness(com.dcdzsoft.dto.business.InParamTBBoxTypeMod p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.tb.TBBoxTypeMod bean = ( com.dcdzsoft.business.tb.TBBoxTypeMod)aop.bind(com.dcdzsoft.business.tb.TBBoxTypeMod.class);
		return bean.doBusiness(p1);
	}

	public static java.lang.String doBusiness(com.dcdzsoft.dto.business.InParamTBFaultStatusMod p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.tb.TBFaultStatusMod bean = ( com.dcdzsoft.business.tb.TBFaultStatusMod)aop.bind(com.dcdzsoft.business.tb.TBFaultStatusMod.class);
		return bean.doBusiness(p1);
	}

	public static java.lang.String doBusiness(com.dcdzsoft.dto.business.InParamTBLockStatusMod p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.tb.TBLockStatusMod bean = ( com.dcdzsoft.business.tb.TBLockStatusMod)aop.bind(com.dcdzsoft.business.tb.TBLockStatusMod.class);
		return bean.doBusiness(p1);
	}
	
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamTBTerminalListQry p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.tb.TBTerminalListQry bean = ( com.dcdzsoft.business.tb.TBTerminalListQry)aop.bind(com.dcdzsoft.business.tb.TBTerminalListQry.class);
		return bean.doBusiness(p1);
	}

	/*public static int doBusiness(com.dcdzsoft.dto.business.InParamTBTerminalModDepartID p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.tb.TBTerminalModDepartID bean = ( com.dcdzsoft.business.tb.TBTerminalModDepartID)aop.bind(com.dcdzsoft.business.tb.TBTerminalModDepartID.class);
		return bean.doBusiness(p1);
	}*/

	public static int doBusiness(com.dcdzsoft.dto.business.InParamTBTerminalModName p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.tb.TBTerminalModName bean = ( com.dcdzsoft.business.tb.TBTerminalModName)aop.bind(com.dcdzsoft.business.tb.TBTerminalModName.class);
		return bean.doBusiness(p1);
	}

	public static java.lang.String doBusiness(com.dcdzsoft.dto.business.InParamTBTerminalModStatus p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.tb.TBTerminalModStatus bean = ( com.dcdzsoft.business.tb.TBTerminalModStatus)aop.bind(com.dcdzsoft.business.tb.TBTerminalModStatus.class);
		return bean.doBusiness(p1);
	}

	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamTBTerminalQry p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.tb.TBTerminalQry bean = ( com.dcdzsoft.business.tb.TBTerminalQry)aop.bind(com.dcdzsoft.business.tb.TBTerminalQry.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamTBTerminalQryCount p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.tb.TBTerminalQryCount bean = ( com.dcdzsoft.business.tb.TBTerminalQryCount)aop.bind(com.dcdzsoft.business.tb.TBTerminalQryCount.class);
		return bean.doBusiness(p1);
	}
	public static int doBusiness(com.dcdzsoft.dto.business.InParamTBTerminalPowerRecovery p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.tb.TBTerminalPowerRecovery bean = 
				( com.dcdzsoft.business.tb.TBTerminalPowerRecovery)aop.bind(com.dcdzsoft.business.tb.TBTerminalPowerRecovery.class);
		return bean.doBusiness(p1);
	}
	public static com.dcdzsoft.dto.function.TBTerminal doBusiness(com.dcdzsoft.dto.function.TBTerminal p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.tb.TBTerminalQryByNo bean = 
				( com.dcdzsoft.business.tb.TBTerminalQryByNo)aop.bind(com.dcdzsoft.business.tb.TBTerminalQryByNo.class);
		return bean.doBusiness(p1);
	}
}
