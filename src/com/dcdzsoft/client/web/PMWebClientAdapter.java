package com.dcdzsoft.client.web;

import com.dcdzsoft.aop.BusiBeanAOPBaseClass;

public class PMWebClientAdapter {
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPMPostmanAdd p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pm.PMPostmanAdd bean = ( com.dcdzsoft.business.pm.PMPostmanAdd)aop.bind(com.dcdzsoft.business.pm.PMPostmanAdd.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamPMPostmanMod p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pm.PMPostmanMod bean = ( com.dcdzsoft.business.pm.PMPostmanMod)aop.bind(com.dcdzsoft.business.pm.PMPostmanMod.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPMPostmanModPwd p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pm.PMPostmanModPwd bean = ( com.dcdzsoft.business.pm.PMPostmanModPwd)aop.bind(com.dcdzsoft.business.pm.PMPostmanModPwd.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPMPostmanModZone p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pm.PMPostmanModZone bean = ( com.dcdzsoft.business.pm.PMPostmanModZone)aop.bind(com.dcdzsoft.business.pm.PMPostmanModZone.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamPMPostmanModRight p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pm.PMPostmanModRight bean = ( com.dcdzsoft.business.pm.PMPostmanModRight)aop.bind(com.dcdzsoft.business.pm.PMPostmanModRight.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPMPostmanDel p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pm.PMPostmanDel bean = ( com.dcdzsoft.business.pm.PMPostmanDel)aop.bind(com.dcdzsoft.business.pm.PMPostmanDel.class);
		return bean.doBusiness(p1);
	}
	
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamPMPostmanQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pm.PMPostmanQry bean = ( com.dcdzsoft.business.pm.PMPostmanQry)aop.bind(com.dcdzsoft.business.pm.PMPostmanQry.class);
		return bean.doBusiness(p1);
	}
	
	public static int doBusiness(com.dcdzsoft.dto.business.InParamPMPostmanQryCount p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pm.PMPostmanQryCount bean = ( com.dcdzsoft.business.pm.PMPostmanQryCount)aop.bind(com.dcdzsoft.business.pm.PMPostmanQryCount.class);
		return bean.doBusiness(p1);
	}
	
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamPMPostmanListQry p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pm.PMPostmanListQry bean = ( com.dcdzsoft.business.pm.PMPostmanListQry)aop.bind(com.dcdzsoft.business.pm.PMPostmanListQry.class);
		return bean.doBusiness(p1);
	}
}
