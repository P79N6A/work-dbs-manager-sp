package com.dcdzsoft.business.mb;

import java.util.Iterator;
import java.util.List;

import javax.sql.RowSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dcdzsoft.EduException;
import com.dcdzsoft.packet.PacketUtils;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 设备格口状态报告 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class MBReportBoxStatus extends ActionBean
{

    public OutParamMBReportBoxStatus doBusiness(InParamMBReportBoxStatus in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        OutParamMBReportBoxStatus out = new OutParamMBReportBoxStatus();

        TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        whereCols.add("TerminalNo", in.TerminalNo);
        if(terminalDAO.isExist(whereCols) <= 0){
            out.Remark = "0";
            return out;
        }
        TBServerBoxDAO boxDAO = daoFactory.getTBServerBoxDAO();
        
        PTInBoxPackageDAO inboxPackDAO = daoFactory.getPTInBoxPackageDAO();
        try {
            JSONArray jsonArray = JSONArray.fromObject(in.BoxInfo);
            List list = JsonUtils.jsonObjectToListDTO(jsonArray, InParamMBInboxStatus.class);
            Iterator<InParamMBInboxStatus> iter = list.iterator();  
            while(iter.hasNext())  
            {  
                boolean isOccupy = false;//是否在用
                String newUsedStatus = SysDict.BOX_USED_STATUS_FREE;//新的使用状态 0-空闲
                String newBoxStatus  = SysDict.BOX_STATUS_NORMAL;
                InParamMBInboxStatus inboxStatus = iter.next();  
                
                //终端箱有订单占用，检查服务端箱是否为同一订单占用
                if(StringUtils.isNotEmpty(inboxStatus.PackageID)){
                    //检查箱中订单是否同步
                    JDBCFieldArray whereCols1 = new JDBCFieldArray();
                    whereCols1.add("TerminalNo", in.TerminalNo);
                    whereCols1.add("BoxNo", inboxStatus.BoxNo);
                    whereCols1.add("PackageID", inboxStatus.PackageID);
                    
                    if (inboxPackDAO.isExist(whereCols1) <=0){
                        newUsedStatus = SysDict.BOX_USED_STATUS_UNNORMAL_OCCUPY;//不同步 异常占用
                    }else{
                        isOccupy = true;
                    }
                }
                
                if("1".equals(inboxStatus.ArticleStatus)  && !isOccupy ){//物品状态(0无物，1有物,-1未知默认)
                    newUsedStatus = SysDict.BOX_USED_STATUS_UNNORMAL_OCCUPY;//有物占用
                }
                //异常占用，设置为故障箱
                int boxStatus = NumberUtils.parseInt(inboxStatus.BoxStatus);//上传的BoxStatus： 1-表示箱子有订单占用  0-表示空闲  2-表示故障。
                if(SysDict.BOX_USED_STATUS_UNNORMAL_OCCUPY.equals(newUsedStatus)){
                    newBoxStatus = SysDict.BOX_STATUS_FAULT;//
                }else if(boxStatus == 2){
                    newBoxStatus = SysDict.BOX_STATUS_FAULT;//
                }
                if(!SysDict.BOX_STATUS_NORMAL.equals(newBoxStatus)){//软件自动修改正常状态为故障，不自动恢复箱的故障状态
                    JDBCFieldArray whereCols2 = new JDBCFieldArray();
                    JDBCFieldArray setCols2 = new JDBCFieldArray();
                    setCols2.add("BoxStatus", newBoxStatus);// 1-锁定，2-故障，3-故障锁定
                    setCols2.add("BoxUsedStatus", newUsedStatus);
                    whereCols2.add("TerminalNo", in.TerminalNo);
                    whereCols2.add("BoxNo", inboxStatus.BoxNo);
                    boxDAO.update(setCols2, whereCols2);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return out;
    }
    
}
