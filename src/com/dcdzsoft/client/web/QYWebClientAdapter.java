package com.dcdzsoft.client.web;

import java.util.Map;

import com.dcdzsoft.aop.BusiBeanAOPBaseClass;
import com.dcdzsoft.dto.business.InParamQYOperationReportPackNum;

public class  QYWebClientAdapter
{
	protected QYWebClientAdapter() {}

	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamQYBoxUsedStat p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.qy.QYBoxUsedStat bean = ( com.dcdzsoft.business.qy.QYBoxUsedStat)aop.bind(com.dcdzsoft.business.qy.QYBoxUsedStat.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamQYBoxUsedStatCount p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.qy.QYBoxUsedStatCount bean = ( com.dcdzsoft.business.qy.QYBoxUsedStatCount)aop.bind(com.dcdzsoft.business.qy.QYBoxUsedStatCount.class);
		return bean.doBusiness(p1);
	}
	
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamQYBoxUsageStat p1)
			throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.qy.QYBoxUsageStat bean = ( com.dcdzsoft.business.qy.QYBoxUsageStat)aop.bind(com.dcdzsoft.business.qy.QYBoxUsageStat.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamQYBoxUsageStatCount p1)
			throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.qy.QYBoxUsageStatCount bean = ( com.dcdzsoft.business.qy.QYBoxUsageStatCount)aop.bind(com.dcdzsoft.business.qy.QYBoxUsageStatCount.class);
		return bean.doBusiness(p1);
	}
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamQYStat4Company p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.qy.QYStat4Company bean = ( com.dcdzsoft.business.qy.QYStat4Company)aop.bind(com.dcdzsoft.business.qy.QYStat4Company.class);
		return bean.doBusiness(p1);
	}

	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamQYStat4Terminal p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.qy.QYStat4Terminal bean = ( com.dcdzsoft.business.qy.QYStat4Terminal)aop.bind(com.dcdzsoft.business.qy.QYStat4Terminal.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamQYStat4TerminalCount p1)
		  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.qy.QYStat4TerminalCount bean = ( com.dcdzsoft.business.qy.QYStat4TerminalCount)aop.bind(com.dcdzsoft.business.qy.QYStat4TerminalCount.class);
		return bean.doBusiness(p1);
	}
	
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamQYStat4SMS p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.qy.QYStat4SMS bean = ( com.dcdzsoft.business.qy.QYStat4SMS)aop.bind(com.dcdzsoft.business.qy.QYStat4SMS.class);
		return bean.doBusiness(p1);
	}
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamQYCompanyBoxUsage p1)
			throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.qy.QYCompanyBoxUsage bean = 
				( com.dcdzsoft.business.qy.QYCompanyBoxUsage)aop.bind(com.dcdzsoft.business.qy.QYCompanyBoxUsage.class);
		return bean.doBusiness(p1);
	}

	public static int doBusiness(com.dcdzsoft.dto.business.InParamQYCompanyBoxUsageCount p1)
			throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.qy.QYCompanyBoxUsageCount bean = 
				( com.dcdzsoft.business.qy.QYCompanyBoxUsageCount)aop.bind(com.dcdzsoft.business.qy.QYCompanyBoxUsageCount.class);
		return bean.doBusiness(p1);
	}
	public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamQYStatDeliverOrder p1)
           throws com.dcdzsoft.EduException
     {
         BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
         com.dcdzsoft.business.qy.QYStatDeliverOrder bean = 
                 ( com.dcdzsoft.business.qy.QYStatDeliverOrder)aop.bind(com.dcdzsoft.business.qy.QYStatDeliverOrder.class);
         return bean.doBusiness(p1);
     }

     public static int doBusiness(com.dcdzsoft.dto.business.InParamQYStatDeliverOrderCount p1)
           throws com.dcdzsoft.EduException
     {
         BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
         com.dcdzsoft.business.qy.QYStatDeliverOrderCount bean = 
                 ( com.dcdzsoft.business.qy.QYStatDeliverOrderCount)aop.bind(com.dcdzsoft.business.qy.QYStatDeliverOrderCount.class);
         return bean.doBusiness(p1);
     }
     public static int doBusiness(String func)
             throws com.dcdzsoft.EduException
     {
         BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
         com.dcdzsoft.business.qy.QYExecProcedure bean = ( com.dcdzsoft.business.qy.QYExecProcedure)aop.bind(com.dcdzsoft.business.qy.QYExecProcedure.class);
         return bean.doBusiness(func);
     }
     public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamQYOperationReport p1)
             throws com.dcdzsoft.EduException
     {
         BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
         com.dcdzsoft.business.qy.QYOperationReport bean = 
                 ( com.dcdzsoft.business.qy.QYOperationReport)aop.bind(com.dcdzsoft.business.qy.QYOperationReport.class);
         return bean.doBusiness(p1);
     }
     public static int doBusiness(com.dcdzsoft.dto.business.InParamQYOperationReportCount p1)
             throws com.dcdzsoft.EduException
       {
           BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
           com.dcdzsoft.business.qy.QYOperationReportCount bean = 
                   ( com.dcdzsoft.business.qy.QYOperationReportCount)aop.bind(com.dcdzsoft.business.qy.QYOperationReportCount.class);
           return bean.doBusiness(p1);
       }
     public static javax.sql.RowSet doBusiness(com.dcdzsoft.dto.business.InParamQYOperationReportByPack p1)
             throws com.dcdzsoft.EduException
     {
         BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
         com.dcdzsoft.business.qy.QYOperationReportByPack bean = 
                 ( com.dcdzsoft.business.qy.QYOperationReportByPack)aop.bind(com.dcdzsoft.business.qy.QYOperationReportByPack.class);
         return bean.doBusiness(p1);
     }
     public static int doBusiness(com.dcdzsoft.dto.business.InParamQYOperationReportByPackCount p1)
             throws com.dcdzsoft.EduException
       {
           BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
           com.dcdzsoft.business.qy.QYOperationReportByPackCount bean = 
                   ( com.dcdzsoft.business.qy.QYOperationReportByPackCount)aop.bind(com.dcdzsoft.business.qy.QYOperationReportByPackCount.class);
           return bean.doBusiness(p1);
       }
     public static Map<String,String> doBusiness(com.dcdzsoft.dto.business.InParamQYOperationReportPackNum p1)
             throws com.dcdzsoft.EduException
       {
           BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
           com.dcdzsoft.business.qy.QYOperationReportPackNum bean = 
                   ( com.dcdzsoft.business.qy.QYOperationReportPackNum)aop.bind(com.dcdzsoft.business.qy.QYOperationReportPackNum.class);
           return bean.doBusiness(p1);
       }
     
     
     
}
