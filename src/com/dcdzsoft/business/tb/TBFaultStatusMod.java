package com.dcdzsoft.business.tb;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sms.SMSInfo;
import com.dcdzsoft.sms.SMSManager;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;
import com.dcdzsoft.businessproxy.MonitorProxy;
import com.dcdzsoft.businessproxy.PushBusinessProxy;

/**
 * <p>Title: 智能自助包裹柜系统</p>
 * <p>Description: 箱体故障状态维护 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author 王中立
 * @version 1.0
 */

public class TBFaultStatusMod extends ActionBean {

    public String doBusiness(InParamTBFaultStatusMod in) throws EduException {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        String result = "";

        //1.	验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(in.TerminalNo)
            ||StringUtils.isEmpty(in.BoxNo)
            || StringUtils.isEmpty(in.FaultStatus))
            throw new EduException(ErrorCode.ERR_PARMERR);

        if (StringUtils.isEmpty(in.RemoteFlag))
            in.RemoteFlag = SysDict.OPER_FLAG_REMOTE;

        if (in.RemoteFlag.equalsIgnoreCase(SysDict.OPER_FLAG_REMOTE)) {
            //2.	调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
            OPOperOnline operOnline = commonDAO.isOnline(in.OperID);
        }

        TBServerBoxDAO boxDAO = daoFactory.getTBServerBoxDAO();
        TBServerBox box = new TBServerBox();
        box.BoxNo = in.BoxNo;
        box.TerminalNo = in.TerminalNo;

        box = boxDAO.find(box);

        int boxStatus = NumberUtils.parseInt(box.BoxStatus);
        if (SysDict.BOX_FAULTSTATUS_NO.equalsIgnoreCase(in.FaultStatus)) //正常
            boxStatus = boxStatus & 1;
        else //设为故障
            boxStatus = boxStatus | 2;

        //状态未发生变化
        if(box.BoxStatus.equalsIgnoreCase(String.valueOf(boxStatus)))
        	return result;
        
        //修改状态
        JDBCFieldArray setCols1 = new JDBCFieldArray();
        JDBCFieldArray whereCols1 = new JDBCFieldArray();

        setCols1.add("BoxStatus", String.valueOf(boxStatus));
        whereCols1.add("BoxNo", in.BoxNo);
        whereCols1.add("TerminalNo", in.TerminalNo);

        boxDAO.update(setCols1, whereCols1);

        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = in.OperID;
        log.FunctionID = in.getFunctionID();
        log.OccurTime = null;
        log.StationAddr = "";
        log.TerminalNo  = in.TerminalNo;
        log.Remark = in.RemoteFlag + "," + in.TerminalNo + "," + in.BoxNo+","+in.FaultStatus;

        commonDAO.addOperatorLog(log);

        
        if (in.RemoteFlag.equalsIgnoreCase(SysDict.OPER_FLAG_REMOTE)) {
            ///推送到设备端
        	try
        	{
        		PushBusinessProxy.doBusiness(in);
        	}catch(EduException e)
        	{}
        }
        
        result = String.valueOf(boxStatus);
        //广播消息
        if(SysDict.BOX_FAULTSTATUS_YES.equals(in.FaultStatus))
        {
        	//插入异常消息
        	commonDAO.insertAlert(in.TerminalNo, SysDict.ALERT_TYPE_BOXFAULT, SysDict.ALERT_LEVEL_COMMON, in.BoxNo, "");
        	//广播消息
        	MonitorProxy.broadcastAlert(in.TerminalNo, SysDict.ALERT_TYPE_BOXFAULT, SysDict.ALERT_LEVEL_COMMON, in.BoxNo, "");
        }
        //result = sentReminderSMS2(in);//取消，不再发送短信通知，在订单状态恢复时发送催领短信20160420
        return result;
    }
    private String sentReminderSMS2(InParamTBFaultStatusMod in) throws EduException {
        String result = "";
        //终端故障修复，发送通知短信，提醒用户取走遗留在箱中的物品
        if (!in.RemoteFlag.equalsIgnoreCase(SysDict.OPER_FLAG_REMOTE)
            &&SysDict.BOX_FAULTSTATUS_NO.equals(in.FaultStatus)
            &&StringUtils.isNotEmpty(ApplicationConfig.getInstance().getSendShortMsg())) {

            java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
            
            //原箱中已经有订单（原物品已被取走），不发送
            PTInBoxPackageDAO inboxPackDAO = daoFactory.getPTInBoxPackageDAO();
            JDBCFieldArray whereCols = new JDBCFieldArray();
            whereCols.add("BoxNo", in.BoxNo);
            whereCols.add("TerminalNo", in.TerminalNo);
            if(inboxPackDAO.isExist(whereCols)>0){
                return result;
            }

            //查看24小时之内的求助记录，超过24小时的求助记录，不发送
            RMAppealLogDAO appealLogDAO = daoFactory.getRMAppealLogDAO();
            JDBCFieldArray whereCols01 = new JDBCFieldArray();
            whereCols01.add("TerminalNo", in.TerminalNo);
            whereCols01.add("BoxNo", in.BoxNo);
            whereCols01.add("Remark", "UnSend");
            whereCols01.add("AppealTime", ">", new java.sql.Timestamp((sysDateTime.getTime()-1000*60*60*24)));
            //取求助记录中的订单号
            String packageID = "";
            java.util.List<String> orderByField = new java.util.LinkedList<String>();
            orderByField.add("AppealTime DESC");
            RowSet rset = appealLogDAO.select(whereCols01, orderByField);
            if(RowSetUtils.rowsetNext(rset)){//取最后一个求助记录
                packageID = RowSetUtils.getStringValue(rset, "PackageID");
            }

            if(StringUtils.isEmpty(packageID)){
                return result;
            }
            
            //历史记录表中查询  订单状态为“Takeout”才进行发送
            PTDeliverHistoryDAO historyPackDAO = daoFactory.getPTDeliverHistoryDAO();
            JDBCFieldArray whereCols02 = new JDBCFieldArray();
            whereCols02.add("TerminalNo", in.TerminalNo);
            whereCols02.add("BoxNo", in.BoxNo);
            whereCols02.add("PackageID", packageID);
            whereCols02.add("ItemStatus", SysDict.ITEM_STATUS_DROP_TAKEOUT);
            RowSet rset2 = historyPackDAO.select(whereCols02);
            if(RowSetUtils.rowsetNext(rset2)){
                
                //////////获取取件密码 //////////////////////////////////////////////////////////////////////
                MBPwdShortMsgDAO shortMsgDAO = daoFactory.getMBPwdShortMsgDAO();
                MBPwdShortMsg shortMsg = new MBPwdShortMsg();
                
                JDBCFieldArray whereCols0 = new JDBCFieldArray();
                whereCols0.add("PackageID", packageID);
                whereCols0.add("TerminalNo", in.TerminalNo);
                whereCols0.add("StoredTime", RowSetUtils.getTimestampValue(rset2, "StoredTime"));//add by zxy :MBPwdShortMsg表中相同PackageID、TerminalNo的可能有多条记录，故增加StoredTime条件

                RowSet rset3 = shortMsgDAO.select(whereCols0);
                if(RowSetUtils.rowsetNext(rset3))
                {
                    shortMsg.WaterID = RowSetUtils.getLongValue(rset3, "WaterID");
                }
                
                if(shortMsg.WaterID <= 0){
                    return result;
                }
                shortMsg = shortMsgDAO.find(shortMsg);
                
                shortMsg.UMID = StringUtils.getUUID();
                
                /////发送通知短信，提醒用户取走遗留在箱中的物品//////////////////////////////////////////////
                TBTerminalDAO terminalDAO = daoFactory.getTBTerminalDAO();
                TBTerminal terminal = new TBTerminal();
                terminal.TerminalNo = in.TerminalNo;
                
                terminal = terminalDAO.find(terminal);
                SMSInfo smsInfo = new SMSInfo();
                smsInfo.PackageID = packageID;
                smsInfo.TerminalNo = in.TerminalNo;
                smsInfo.StoredTime = shortMsg.StoredTime;
                smsInfo.CustomerMobile = shortMsg.CustomerMobile;
                smsInfo.OpenBoxKey = shortMsg.OpenBoxKey;
                smsInfo.TerminalName = terminal.TerminalName;
                smsInfo.Location = terminal.Location;
                smsInfo.TerminalType = terminal.TerminalType;
                smsInfo.MsgType = SMSInfo.MSG_TYPE_REMINDER2;
                smsInfo.UMID  = shortMsg.UMID;
                smsInfo.ScheduledDateTime = sysDateTime;
                smsInfo.Latitude = terminal.Latitude;
                smsInfo.Longitude = terminal.Longitude;
                SMSManager.getInstance().sentReminderSMS2(smsInfo);
            }
        }
        return result;
    }
}
