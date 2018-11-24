package com.dcdzsoft.business.pt;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.ppcapi.GetInfoManager;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 单个包裹查询 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTPackageDetail extends ActionBean
{

    public OutParamPTPackageDetail doBusiness(InParamPTPackageDetail in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamPTPackageDetail out = new OutParamPTPackageDetail();

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.TerminalNo)
			||StringUtils.isEmpty(in.PackageID)
			)//||StringUtils.isEmpty(in.LeftFlag)
			throw new EduException(ErrorCode.ERR_PARMERR);
		
		if(!SysDict.PACKAGE_LEFT_FLAG_YES.equalsIgnoreCase(in.LeftFlag)){//投递包裹订单下载
			//验证投递员是否存在
	        PMPostmanDAO postmanDAO = daoFactory.getPMPostmanDAO();
	        PMPostman postman = new PMPostman();
	        postman.PostmanID = in.PostmanID;
	        try {
	            postman = postmanDAO.find(postman);
	        } catch (Exception e) {
	            throw new EduException(ErrorCode.ERR_POSTMANNOTEXISTS);
	        }

	        //验证柜体权限
	        //commonDAO.checkManTerminalRight(postman, in.TerminalNo);
	        switch(postman.PostmanType){
	        case SysDict.POSTMAN_TYPE_DAD_BUSINESSPARTNER:
	        	out = _doBusiness4PT_DAD_B(in);
	        	break;
	        case SysDict.POSTMAN_TYPE_DAD_SP:
                out = _doBusiness4PT_DAD_SP(in);
                break;
	        case SysDict.POSTMAN_TYPE_DAD:
	            try{
	                out = _doBusiness4PT(in);
	            } catch(EduException e){
	                out.PackageStatus = "";
	            }
	            if(StringUtils.isNotEmpty(out.PackageStatus)){
	                if(!SysDict.ITEM_STATUS_DROP_D_CREATE.equals(out.PackageStatus)){
                        throw new EduException(ErrorCode.ERR_PACKHAVEDELIVERYD);
                    }
	            }else{
	                out.PackageID = in.PackageID;
	                out.OrderTime = in.OccurTime;
	                out.CompanyID = in.CompanyID;
	                out.BoxNo = "";
	                out.BoxType = "";
	                out.CustomerMobile = "";
	            }
	            break;
	        default:
	            out = _doBusiness4PT(in);
	        	break;
	        }
		}else{//用户寄件
		    out = _doBusiness4DM(in);
		}
        
        return out;
    }
    
    //网络获取手机号
    private OutParamPTPackageDetail _doBusiness4PT_DAD_SP(InParamPTPackageDetail in) throws EduException{
        OutParamPTPackageDetail out = new OutParamPTPackageDetail();
        out.PackageID = in.PackageID;
        out.OrderTime = in.OccurTime;
        out.CompanyID = in.CompanyID;
        out.BoxNo = "";
        out.BoxType = "";
        out.CustomerMobile = GetInfoManager.getInstance().getCutomerMobileByItemCode(in.PackageID);
        return out;
    }
    //投递员投递
    private OutParamPTPackageDetail _doBusiness4PT(InParamPTPackageDetail in) throws EduException{
        OutParamPTPackageDetail out = new OutParamPTPackageDetail();
        PTReadyPackageDAO readyPackDAO = daoFactory.getPTReadyPackageDAO();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        whereCols.add("TerminalNo", in.TerminalNo);
        whereCols.add("PackageID", in.PackageID);
        whereCols.add("DropAgentID", in.PostmanID);

        RowSet rset = readyPackDAO.select(whereCols);

        if (RowSetUtils.rowsetNext(rset)) {
            out.PackageID = RowSetUtils.getStringValue(rset, "PackageID");
            out.OrderTime = RowSetUtils.getTimestampValue(rset, "CreateTime");//OrderTime
            out.ExpiredTime = RowSetUtils.getTimestampValue(rset, "ExpiredTime");
            out.CompanyID = RowSetUtils.getStringValue(rset, "CompanyID");
            out.BoxNo = RowSetUtils.getStringValue(rset, "BoxNo");
            out.BoxType = RowSetUtils.getStringValue(rset, "BoxType");
            out.CustomerID = RowSetUtils.getStringValue(rset, "CustomerID");
            out.CustomerName = RowSetUtils.getStringValue(rset, "CustomerName");
            out.CustomerAddress = RowSetUtils.getStringValue(rset, "CustomerAddress");
            out.CustomerMobile = RowSetUtils.getStringValue(rset, "CustomerMobile");
            out.PosPayFlag = "";//RowSetUtils.getStringValue(rset, "PosPayFlag");
            out.PackageStatus = RowSetUtils.getStringValue(rset, "ItemStatus");
            out.Remark = RowSetUtils.getStringValue(rset, "Remark");
            /*String  postmanID = RowSetUtils.getStringValue(rset, "DropAgentID");
            String  terminalNo = RowSetUtils.getStringValue(rset, "TerminalNo");
           
            if(SysDict.POSTMAN_TYPE_DAD.equals(postman.PostmanType)){
                //DAD
               out.BoxNo = "";
               out.BoxType = "";
               out.CustomerMobile = "";
                if(!SysDict.ITEM_STATUS_DROP_D_CREATE.equals(out.PackageStatus)){
                    throw new EduException(ErrorCode.ERR_PACKHAVEDELIVERYD);
                }
            }
            if(!in.TerminalNo.equals(terminalNo)|| 
                    !in.PostmanID.equals(postmanID)){
               throw new EduException(ErrorCode.ERR_PACKHAVEDELIVERYD);
            }*/
        }else{
            /*if(SysDict.POSTMAN_TYPE_DAD.equals(postman.PostmanType)){
                out.PackageID = in.PackageID;
                out.OrderTime = in.OccurTime;
                out.CompanyID = in.CompanyID;
                out.BoxNo = "";
                out.BoxType = "";
            }else{
               throw new EduException(ErrorCode.ERR_PACKAGENOTEXISTS);
            }*/
            throw new EduException(ErrorCode.ERR_PACKAGENOTEXISTS);
        }
        return out;
    }
    //合作伙伴直接投递
    private OutParamPTPackageDetail _doBusiness4PT_DAD_B(InParamPTPackageDetail in) throws EduException{
        OutParamPTPackageDetail out = new OutParamPTPackageDetail();
        DMCollectionParcelDAO collectionParcelDAO = daoFactory.getDMCollectionParcelDAO();
        JDBCFieldArray whereCols2 = new JDBCFieldArray();
        whereCols2.add("PackageID", in.PackageID);
        whereCols2.add("TerminalNo", in.TerminalNo);//只能在订单创建时选择的客户收件柜体进行投递
        whereCols2.add("ReturnFlag","<>", "1");
        //whereCols.add("ItemStatus", SysDict.ITEM_STATUS_COLLECT_CREATED);
        String ItemStatus = SysDict.ITEM_STATUS_COLLECT_CREATED;//+","//50
        whereCols2.addSQL(utilDAO.getFlagInSQL("ItemStatus", ItemStatus));
        
        RowSet rset = collectionParcelDAO.select(whereCols2);

        if (RowSetUtils.rowsetNext(rset)) {
            out.PackageID = RowSetUtils.getStringValue(rset, "PackageID");
            out.OrderTime = RowSetUtils.getTimestampValue(rset, "CreateTime");
            out.CompanyID = RowSetUtils.getStringValue(rset, "CompanyID");
            out.ExpiredTime = commonDAO.getExpiredTime(out.CompanyID);
            out.BoxNo = "0";
            out.BoxType = RowSetUtils.getStringValue(rset, "ParcelSize");
            out.CustomerID = RowSetUtils.getStringValue(rset, "CustomerID");
            out.CustomerName = RowSetUtils.getStringValue(rset, "CustomerName");
            out.CustomerAddress = RowSetUtils.getStringValue(rset, "CustomerAddress");
            out.CustomerMobile = RowSetUtils.getStringValue(rset, "CustomerMobile");
            out.PosPayFlag = "1";//RowSetUtils.getStringValue(rset, "PosPayFlag");
            out.PackageStatus = RowSetUtils.getStringValue(rset, "ItemStatus");
            out.Remark = RowSetUtils.getStringValue(rset, "Remark");
        }else{
            throw new EduException(ErrorCode.ERR_COLLECT_ITEM_NOT_EXISTS);
        }
        return out;
    }
    //用户寄件
    private OutParamPTPackageDetail _doBusiness4DM(InParamPTPackageDetail in) throws EduException{
        OutParamPTPackageDetail out = new OutParamPTPackageDetail();
        DMCollectionParcelDAO collectionParcelDAO = daoFactory.getDMCollectionParcelDAO();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        whereCols.add("TerminalNo", in.TerminalNo);
        whereCols.add("PackageID", in.PackageID);

        RowSet rset = collectionParcelDAO.select(whereCols);

        if (RowSetUtils.rowsetNext(rset)) {
            out.PackageID = RowSetUtils.getStringValue(rset, "PackageID");
            out.OrderTime = RowSetUtils.getTimestampValue(rset, "CreateTime");
            out.ExpiredTime = null;//RowSetUtils.getTimestampValue(rset, "ExpiredTime");
            out.CompanyID = RowSetUtils.getStringValue(rset, "CompanyID");
            out.BoxNo = RowSetUtils.getStringValue(rset, "BoxNo");
            out.BoxType = "";//RowSetUtils.getStringValue(rset, "BoxType");
            out.CustomerID = RowSetUtils.getStringValue(rset, "CustomerID");
            out.CustomerName = RowSetUtils.getStringValue(rset, "CustomerName");
            out.CustomerAddress = RowSetUtils.getStringValue(rset, "CustomerAddress");
            out.CustomerMobile = RowSetUtils.getStringValue(rset, "CustomerMobile");
            out.PosPayFlag = "";//RowSetUtils.getStringValue(rset, "PosPayFlag");
            out.PackageStatus = RowSetUtils.getStringValue(rset, "ItemStatus");
            out.Remark = RowSetUtils.getStringValue(rset, "Remark");
        }else{
            throw new EduException(ErrorCode.ERR_PACKAGENOTEXISTS);
        }
        return out;
    }
}
