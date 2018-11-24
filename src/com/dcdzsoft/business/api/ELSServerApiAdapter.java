package com.dcdzsoft.business.api;



import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.dcdzsoft.EduException;
import com.dcdzsoft.aop.BusiBeanAOPBaseClass;
import com.dcdzsoft.business.api.Comm.ErrorCode;
import com.dcdzsoft.client.web.PTWebClientAdapter;
import com.dcdzsoft.constant.Constant;
import com.dcdzsoft.constant.SysDict;
import com.dcdzsoft.dto.business.InParamAPCollectedByDepartment;
import com.dcdzsoft.dto.business.InParamAPIUserKeyAndDataVerity;
import com.dcdzsoft.dto.business.InParamLockerStationAddressQry;
import com.dcdzsoft.dto.business.InParamPTDeliveryItemDetail;
import com.dcdzsoft.dto.business.InParamPTDeliveryItemQry;
import com.dcdzsoft.dto.business.InParamPTItemLifeCycleQry;
import com.dcdzsoft.dto.business.OutParamAPCollectedByDepartment;
import com.dcdzsoft.dto.business.OutParamAPIUserKeyAndDataVerity;
import com.dcdzsoft.dto.business.OutParamDMBusinessPartnerTopUP;
import com.dcdzsoft.dto.business.OutParamLockerStationAddressQry;
import com.dcdzsoft.dto.business.OutParamPTDeliveryItemDetail;
import com.dcdzsoft.dto.business.OutParamPTDeliveryItemQry;
import com.dcdzsoft.sda.db.PreparedWhereExpression;
import com.dcdzsoft.sda.db.RowSetUtils;
import com.dcdzsoft.util.JsonUtils;

public class ELSServerApiAdapter {
	public ELSServerApiAdapter(){}
	
	/**
	 * API请求校验
	 * @param mode int : 1-数据进行加密，需要校验datamd5；其他-不需要校验datamd5
	 * @param keymd5
	 * @param datamd5
	 * @param action
	 * @param requsetData
	 * @return OutParamAPIUserKeyAndDataVerity
	 */
	public static OutParamAPIUserKeyAndDataVerity checkApiRequest(int mode, String keymd5, String datamd5, String action, String requsetData){
		
		String errorMsg = null;
		//#start 检查参数(keymd5, datamd5)是否为空
        if(StringUtils.isEmpty(keymd5) 
        	||StringUtils.isEmpty(action) 
            ||(mode ==1 && StringUtils.isEmpty(datamd5)	)){
        	errorMsg = Comm.getErrorMessage(ErrorCode.ERROR_PARAM_EMPTY);
        	System.out.println("keymd5="+keymd5+",datamd5="+datamd5+",action="+action);
    		//return errorMsg;
        }
        //#end
        
        //#start检查数据是否为空,JSON
        if(StringUtils.isEmpty(requsetData) 
        		||!requsetData.startsWith("{")
        		||!requsetData.endsWith("}")){
        	errorMsg = Comm.getErrorMessage(ErrorCode.ERROR_DATA_EMPTY);  
        	System.out.println("requsetData="+requsetData);
    		//return errorMsg;
        }
        //#end
        
        OutParamAPIUserKeyAndDataVerity userKeyOut = new OutParamAPIUserKeyAndDataVerity();
        if(StringUtils.isNotEmpty(errorMsg)){
        	userKeyOut.ErrorMsg = errorMsg;
        	return userKeyOut;
        }
        
        //#start 数据源身份及数据完整性验证
    	InParamAPIUserKeyAndDataVerity userKeyIn = new InParamAPIUserKeyAndDataVerity();
    	userKeyIn.Datamd5 = datamd5;
    	userKeyIn.Keymd5  = keymd5;
    	userKeyIn.RequestData = requsetData;
		try {
			userKeyOut = ELSServerApiAdapter.doBusiness(userKeyIn);
			
		} catch (EduException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		if(userKeyOut == null){
			userKeyOut = new OutParamAPIUserKeyAndDataVerity();
			userKeyOut.ErrorMsg = null;
		}
		
    	//keymd5 error
    	if(StringUtils.isEmpty(userKeyOut.UserID) ){
    		errorMsg = Comm.getErrorMessage(ErrorCode.ERROR_PARAM_KEYMD5);
    		userKeyOut.ErrorMsg = errorMsg;
    		return userKeyOut;
    	}
    	//datamd5 error
    	if(mode ==1 && userKeyOut.DataFalg != 1){
    		errorMsg = Comm.getErrorMessage(ErrorCode.ERROR_PARAM_DATAMD5);    
    		userKeyOut.ErrorMsg = errorMsg;
    		return userKeyOut;
    	}
    	//#end
    	
		return userKeyOut;
	}
	public static com.dcdzsoft.dto.business.OutParamAPIUserKeyAndDataVerity doBusiness(com.dcdzsoft.dto.business.InParamAPIUserKeyAndDataVerity p1)
			 throws EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APIUserKeyAndDataVerity bean = 
				( com.dcdzsoft.business.ap.APIUserKeyAndDataVerity)aop.bind(com.dcdzsoft.business.ap.APIUserKeyAndDataVerity.class);
		return bean.doBusiness(p1);
	}
	/**
	 * PPC订单数据导入
	 * @param p1
	 * @return
	 * @throws EduException
	 */
	public static com.dcdzsoft.business.api.Comm.ResultCode doBusiness(com.dcdzsoft.dto.business.InParamPTAddItemsFromPPC p1)
	 throws EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.pt.PTAddItemsFromPPC bean = 
				( com.dcdzsoft.business.pt.PTAddItemsFromPPC)aop.bind(com.dcdzsoft.business.pt.PTAddItemsFromPPC.class);
		return bean.doBusiness(p1);
	}
	
	/**
	 * 商用合作伙伴账户充值请求
	 * @param p1
	 * @return
	 * @throws EduException
	 */
	public static com.dcdzsoft.dto.business.OutParamDMBusinessPartnerTopUpReq doBusiness(com.dcdzsoft.dto.business.InParamDMBusinessPartnerTopUpReq p1) throws EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMBusinessPartnerTopUpReq bean = 
				( com.dcdzsoft.business.dm.DMBusinessPartnerTopUpReq)aop.bind(com.dcdzsoft.business.dm.DMBusinessPartnerTopUpReq.class);
		
		return bean.doBusiness(p1);
	}
	/**
	 * 商用合作伙伴账户充值
	 * @param p1
	 * @return
	 * @throws EduException
	 */
	public static com.dcdzsoft.dto.business.OutParamDMBusinessPartnerTopUP doBusiness(com.dcdzsoft.dto.business.InParamDMBusinessPartnerTopUP p1) throws EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMBusinessPartnerTopUP bean = ( com.dcdzsoft.business.dm.DMBusinessPartnerTopUP)aop.bind(com.dcdzsoft.business.dm.DMBusinessPartnerTopUP.class);
		
		return bean.doBusiness(p1);
	}
	
	/**
	 * 查询柜体位置信息
	 * @param p1
	 * @return
	 * @throws EduException
	 */
	public static com.dcdzsoft.dto.business.OutParamTBLockerAddressQry doBusiness(com.dcdzsoft.dto.business.InParamTBLockerAddressQry p1) throws EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.tb.TBLockerAddressQry bean = 
				( com.dcdzsoft.business.tb.TBLockerAddressQry)aop.bind(com.dcdzsoft.business.tb.TBLockerAddressQry.class);
		
		return bean.doBusiness(p1);
	}
	/**
	 * 通过范围查询柜体位置信息
	 * @param p1
	 * @return
	 * @throws EduException
	 */
	public static java.util.List<OutParamLockerStationAddressQry> doBusiness(com.dcdzsoft.dto.business.InParamLockerStationAddressQry p1) throws EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.tb.LockerStationAddressQry bean = 
				( com.dcdzsoft.business.tb.LockerStationAddressQry)aop.bind(com.dcdzsoft.business.tb.LockerStationAddressQry.class);
		
		return bean.doBusiness(p1);
	}
	
	/**
	 * 投递员身份验证
	 * @param p1
	 * @return
	 * @throws EduException
	 */
	public static com.dcdzsoft.dto.business.OutParamAPPostmanCheck doBusiness(com.dcdzsoft.dto.business.InParamAPPostmanCheck p1) throws EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APPostmanCheck bean = 
				( com.dcdzsoft.business.ap.APPostmanCheck)aop.bind(com.dcdzsoft.business.ap.APPostmanCheck.class);
		
		return bean.doBusiness(p1);
	}
	/**
	 * 揽件员查询待揽件订单的地址信息
	 * @param p1
	 * @return
	 * @throws EduException
	 */
	public static java.util.List<com.dcdzsoft.dto.business.OutParamAPCollectAgentAddressQry> doBusiness(com.dcdzsoft.dto.business.InParamAPCollectAgentAddressQry p1) throws EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APCollectAgentAddressQry bean = 
				( com.dcdzsoft.business.ap.APCollectAgentAddressQry)aop.bind(com.dcdzsoft.business.ap.APCollectAgentAddressQry.class);
		
		return bean.doBusiness(p1);
	}
	/**
	 * 寄件订单信息查询
	 * @param p1
	 * @return
	 * @throws EduException
	 */
	public static com.dcdzsoft.dto.business.OutParamAPCollectAgentItemQry doBusiness(com.dcdzsoft.dto.business.InParamAPCollectAgentItemQry p1) throws EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APCollectAgentItemQry bean = 
				( com.dcdzsoft.business.ap.APCollectAgentItemQry)aop.bind(com.dcdzsoft.business.ap.APCollectAgentItemQry.class);
		
		return bean.doBusiness(p1);
	}
	/**
	 * 揽件员确认揽件
	 * @param p1
	 * @return
	 * @throws EduException
	 */
	public static int doBusiness(com.dcdzsoft.dto.business.InParamAPCollectAgentCollected p1) throws EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APCollectAgentCollected bean = 
				( com.dcdzsoft.business.ap.APCollectAgentCollected)aop.bind(com.dcdzsoft.business.ap.APCollectAgentCollected.class);
		
		return bean.doBusiness(p1);
	}
	/**
	 * 揽件部门揽件
	 * @param p1
	 * @return
	 * @throws EduException
	 */
	public static int doBusiness(com.dcdzsoft.dto.business.InParamAPCollectedByDepartment p1) throws EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APCollectedByDepartment bean = 
				( com.dcdzsoft.business.ap.APCollectedByDepartment)aop.bind(com.dcdzsoft.business.ap.APCollectedByDepartment.class);
		
		return bean.doBusiness(p1);
	}
	/**
	 * 创建寄件订单，包括Send/Mail item ,return item
	 * @param p1
	 * @return
	 * @throws com.dcdzsoft.EduException
	 */
	public static com.dcdzsoft.dto.business.OutParamDMDeliveryCreate doBusiness(com.dcdzsoft.dto.business.InParamDMDeliveryCreate p1)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMDeliveryCreate bean = ( com.dcdzsoft.business.dm.DMDeliveryCreate)aop.bind(com.dcdzsoft.business.dm.DMDeliveryCreate.class);
		return bean.doBusiness(p1);
	}
	/**
	 * Check BP Password
	 * @param BPartnerID
	 * @param Password
	 * @return
	 * @throws com.dcdzsoft.EduException
	 */
	public static int doBusiness(String BPartnerID, String Password)
			  throws com.dcdzsoft.EduException
	{
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.dm.DMBPartnerCheck bean = ( com.dcdzsoft.business.dm.DMBPartnerCheck)aop.bind(com.dcdzsoft.business.dm.DMBPartnerCheck.class);
		return bean.doBusiness(BPartnerID, Password);
	}
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
}
