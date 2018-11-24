package com.dcdzsoft.business.pt;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;
import com.dcdzsoft.sda.security.SecurityUtils;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 用户取件身份认证 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTVerfiyUser extends ActionBean
{

    public OutParamPTVerfiyUser doBusiness(InParamPTVerfiyUser in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamPTVerfiyUser out = new OutParamPTVerfiyUser();

        //1.	验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(in.CustomerID)
            || StringUtils.isEmpty(in.OpenBoxKey))
            throw new EduException(ErrorCode.ERR_PARMERR);

        ControlParam ctrlParam = ControlParam.getInstance();

        PTInBoxPackageDAO inboxPackDAO = daoFactory.getPTInBoxPackageDAO();
        JDBCFieldArray whereCols = new JDBCFieldArray();

        in.OpenBoxKey = SecurityUtils.md5(in.OpenBoxKey).toLowerCase();

        if (SysDict.TAKEOUTCHECK_TYPE_PACKAGEID.equals(ctrlParam.getTakeOutCheckType())) {
            whereCols.addSQL(" AND TerminalNo = " + StringUtils.addQuote(in.TerminalNo) + "");
            whereCols.addSQL(" AND PackageID = " + StringUtils.addQuote(in.CustomerID) + "");
            whereCols.addSQL(" AND LOWER(OpenBoxKey) = " + StringUtils.addQuote(in.OpenBoxKey) + "");
        } else if (SysDict.TAKEOUTCHECK_TYPE_MOBILE.equals(ctrlParam.getTakeOutCheckType())) {
             whereCols.addSQL(" AND TerminalNo = " + StringUtils.addQuote(in.TerminalNo) + "");
            whereCols.addSQL(" AND CustomerMobile = " + StringUtils.addQuote(in.CustomerID) + "");
            whereCols.addSQL(" AND LOWER(OpenBoxKey) = " + StringUtils.addQuote(in.OpenBoxKey) + "");
        } else if (SysDict.TAKEOUTCHECK_TYPE_MOBILEAFTER4.equals(ctrlParam.getTakeOutCheckType())) {
            whereCols.addSQL(" AND TerminalNo = " + StringUtils.addQuote(in.TerminalNo) + "");
            whereCols.addSQL(" AND SUBSTR(CustomerMobile,LENGTH(CustomerMobile)-3,4) = " + StringUtils.addQuote(in.CustomerID) + "");
            whereCols.addSQL(" AND LOWER(OpenBoxKey) = " + StringUtils.addQuote(in.OpenBoxKey) + "");
        } else if (SysDict.TAKEOUTCHECK_TYPE_PACKORMOBILE.equals(ctrlParam.getTakeOutCheckType())) {
            whereCols.addSQL(" AND TerminalNo = " + StringUtils.addQuote(in.TerminalNo) + "");
            whereCols.addSQL(" AND (PackageID = " + StringUtils.addQuote(in.CustomerID));
            whereCols.addSQL("      OR CustomerMobile = " + StringUtils.addQuote(in.CustomerID) + ")");
            whereCols.addSQL(" AND LOWER(OpenBoxKey) = " + StringUtils.addQuote(in.OpenBoxKey) + "");
        } else if (SysDict.TAKEOUTCHECK_TYPE_CARDID.equals(ctrlParam.getTakeOutCheckType())) {
            whereCols.addSQL(" AND TerminalNo = " + StringUtils.addQuote(in.TerminalNo) + "");
            whereCols.addSQL(" AND CustomerID = " + StringUtils.addQuote(in.CustomerID) + "");
            whereCols.addSQL(" AND LOWER(OpenBoxKey) = " + StringUtils.addQuote(in.OpenBoxKey) + "");
        } else if (SysDict.TAKEOUTCHECK_TYPE_4PACKID.equals(ctrlParam.getTakeOutCheckType())) {
            whereCols.addSQL(" AND TerminalNo = " + StringUtils.addQuote(in.TerminalNo) + "");
            whereCols.addSQL(" AND SUBSTR(PackageID,LENGTH(PackageID)-3,4) = " + StringUtils.addQuote(in.CustomerID) + "");
            whereCols.addSQL(" AND LOWER(OpenBoxKey) = " + StringUtils.addQuote(in.OpenBoxKey) + "");
        } else if (SysDict.TAKEOUTCHECK_TYPE_4PACKORMOBILE.equals(ctrlParam.getTakeOutCheckType())) {
            whereCols.addSQL(" AND TerminalNo = " + StringUtils.addQuote(in.TerminalNo) + "");
            whereCols.addSQL(" AND (SUBSTR(PackageID,LENGTH(PackageID)-3,4) = " + StringUtils.addQuote(in.CustomerID));
            whereCols.addSQL("      OR CustomerMobile = " + StringUtils.addQuote(in.CustomerID) + ")");
            whereCols.addSQL(" AND LOWER(OpenBoxKey) = " + StringUtils.addQuote(in.OpenBoxKey) + "");
        }

        RowSet rset = inboxPackDAO.select(whereCols);
        if (RowSetUtils.rowsetNext(rset)) { //可能出现多个记录如何处理？？   同一柜体的订单开箱密码不允许相同
            //判断包裹状态不????(判断锁定，不判断超时)
            if (RowSetUtils.getStringValue(rset,"ParcelStatus").equalsIgnoreCase(SysDict.PACKAGE_STATUS_LOCKED))
            {
                throw new EduException(ErrorCode.ERR_PACKAGEHAVELOCKED);
            }
            String ItemStatus = RowSetUtils.getStringValue(rset,"ItemStatus");
            String limit = ControlParam.getInstance().getTakeOutExpiredItemLimit();
            if("1".equalsIgnoreCase(limit) || "3".equalsIgnoreCase(limit)){
                if(SysDict.ITEM_STATUS_DROP_M_EXPIRED.equals(ItemStatus)){
                    throw new EduException(ErrorCode.ERR_PACKAGEHAVELOCKED);
                }
            }
            if("2".equalsIgnoreCase(limit) || "3".equalsIgnoreCase(limit)){
                if(SysDict.ITEM_STATUS_DROP_EXPIRED.equals(ItemStatus)){
                    throw new EduException(ErrorCode.ERR_PACKAGEHAVELOCKED);
                }
            }

            out.BoxNo = RowSetUtils.getStringValue(rset, "BoxNo");
            out.PackageID = RowSetUtils.getStringValue(rset, "PackageID");
            out.PosPayFlag = RowSetUtils.getStringValue(rset, "PosPayFlag");
            out.ExpiredAmt = 0D; //???
        } else {
            if (SysDict.TAKEOUTCHECK_TYPE_PACKAGEID.equals(ctrlParam.getTakeOutCheckType())) {
                throw new EduException(ErrorCode.ERR_BUSINESS_WRONGPACKAGID);
            } else if (SysDict.TAKEOUTCHECK_TYPE_MOBILE.equals(ctrlParam.getTakeOutCheckType())) {
                throw new EduException(ErrorCode.ERR_BUSINESS_WRONGMOBILE);
            } else if (SysDict.TAKEOUTCHECK_TYPE_MOBILEAFTER4.equals(ctrlParam.getTakeOutCheckType())) {
                throw new EduException(ErrorCode.ERR_BUSINESS_WRONGMOBILE);
            } else if (SysDict.TAKEOUTCHECK_TYPE_PACKORMOBILE.equals(ctrlParam.getTakeOutCheckType())) {
                throw new EduException(ErrorCode.ERR_BUSINESS_WRONGPACKMOBILE);
            } else if (SysDict.TAKEOUTCHECK_TYPE_CARDID.equals(ctrlParam.getTakeOutCheckType())) {
                throw new EduException(ErrorCode.ERR_BUSINESS_WRONGCUSTOMERID);
            } else if (SysDict.TAKEOUTCHECK_TYPE_4PACKID.equals(ctrlParam.getTakeOutCheckType())) {
                throw new EduException(ErrorCode.ERR_BUSINESS_WRONGPACKAGID);
            } else if (SysDict.TAKEOUTCHECK_TYPE_4PACKORMOBILE.equals(ctrlParam.getTakeOutCheckType())) {
                throw new EduException(ErrorCode.ERR_BUSINESS_WRONGPACKMOBILE);
            }
        }

        return out;
    }
}
