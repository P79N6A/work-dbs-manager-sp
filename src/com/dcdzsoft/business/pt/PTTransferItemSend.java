package com.dcdzsoft.business.pt;

import javax.sql.RowSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dcdzsoft.EduException;
import com.dcdzsoft.ppcapi.SendInfo;
import com.dcdzsoft.ppcapi.SendItemManager;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.sequence.SequenceGenerator;
import com.dcdzsoft.util.*;
import com.dcdzsoft.dto.function.*;
import com.dcdzsoft.dto.business.*;
import com.dcdzsoft.dao.*;
import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.client.web.PTWebClientAdapter;
import com.dcdzsoft.constant.*;
import com.dcdzsoft.business.ActionBean;

/**
 * <p>Title: 自提柜后台运营系统</p>
 * <p>Description: 发送订单数据到PPC服务器 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: dcdzsoft</p>
 * @author zhengxy
 * @version 1.0
 */

public class PTTransferItemSend extends ActionBean
{

    public int doBusiness(InParamPTTransferItemSend in) throws EduException
    {
        utilDAO = this.getUtilDAO();
        commonDAO = this.getCommonDAO();
        dbSession = this.getCurrentDBSession();
        int result = 0;

		//1.	验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(in.PackageID)
			||StringUtils.isEmpty(in.PPCID))
			throw new EduException(ErrorCode.ERR_PARMERR);

		//2.	调用CommonDAO.isOnline(管理员编号)判断管理员是否在线。
		boolean isManual = false;
		OPOperOnline operOnline = null;
		if(StringUtils.isNotEmpty(in.OperID)){
		    operOnline = commonDAO.isOnline(in.OperID);
		    isManual = true;
		}

		//3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		java.sql.Timestamp sysDateTime = utilDAO.getCurrentDateTime();
		java.sql.Date sysDate = DateUtils.toSQLDate(sysDateTime);
		
		int sendNumMax = NumberUtils.parseInt(ControlParam.getInstance().getTransferResendTimes());
        if(sendNumMax<Constant.TRANSFER_TO_PPC_SEND_TIMES_MAX){
            sendNumMax = Constant.TRANSFER_TO_PPC_SEND_TIMES_MAX;//默认值；
        }
        
		//PPC
        IMPostalProcessingCenterDAO ppcDAO = daoFactory.getIMPostalProcessingCenterDAO();
        IMPostalProcessingCenter  ppc = new IMPostalProcessingCenter();
        ppc.PPCID = in.PPCID;
        ppc  = ppcDAO.find(ppc);
        
		int itemcount = 0;
		String PPCInterface = getRemarkValue(ppc.Remark,"PPCInterface");
		String UploadFlag   = getRemarkValue(ppc.Remark,"UploadFlag");
		String[] itemIds = in.PackageID.split(",");//PackageID中保存的是流水号
        for(String itemid : itemIds){
            //创建发送信息对象
            SendInfo sendInfo     = new SendInfo();
            sendInfo.ppcId        = in.PPCID;
            sendInfo.apiString    = ppc.PPCServerURL;
            sendInfo.userKey      = ppc.PPCServerPassword;
            sendInfo.userName     = ppc.PPCServerUsername;
            sendInfo.PPCInterface = PPCInterface;
            sendInfo.uploadFlag   = UploadFlag;
            sendInfo.ItemID = NumberUtils.parseLong(itemid);
            itemcount += sendItem(sendInfo, sysDateTime, isManual, sendNumMax);
        }
        
        if(operOnline!=null){
            // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
            OPOperatorLog log = new OPOperatorLog();
            log.OperID = in.OperID;
            log.FunctionID = in.getFunctionID();
            log.OccurTime = sysDateTime;
            log.StationAddr = operOnline.LoginIPAddr;
            log.Remark = "count"+itemcount;

            commonDAO.addOperatorLog(log);
        }
	
        return result;
    }
    /**
     * 
     * @param sendInfo
     * @param sysDateTime
     * @param isManual   true-人工操作，重发次数重新计数
     * @param sendNumMax 失败自动重发的最大次数
     * @return
     * @throws EduException
     */
    private int sendItem(SendInfo sendInfo, java.sql.Timestamp sysDateTime, boolean isManual, int sendNumMax) throws EduException{
        int result = 0;
        PTDeliverItemTransferDAO itemTransferDAO = daoFactory.getPTDeliverItemTransferDAO();
        PTDeliverItemTransfer itemTransfer = new PTDeliverItemTransfer();
        itemTransfer.WaterID = sendInfo.ItemID;
        itemTransfer = itemTransferDAO.find(itemTransfer);
        
        if(!sendInfo.ppcId.equals(itemTransfer.PPCID)){//只发送同一PPC的订单
            throw new EduException(ErrorCode.ERR_OPERAT_FORBIDSUBMITMULTIPPC);
        }
        if(SysDict.TRANSFER_TYPE_ANNOUNCING.equals(itemTransfer.TransferType)){//上传订单更新状态
            sendInfo.TransferFlag = Constant.TRANSFER_FLAG_UPDATE;
        }else{
            sendInfo.TransferFlag = Constant.TRANSFER_FLAG_UPLOAD;
        }
        sendInfo.ItemID       = itemTransfer.WaterID;
        sendInfo.ItemCode     = itemTransfer.PackageID;
        sendInfo.CreateTime   = itemTransfer.CreateTime;
        sendInfo.ItemType     = itemTransfer.TransferType;//NumberUtils.parseInt(itemTransfer.TransferType);
        sendInfo.ZoneID       = itemTransfer.ZoneID;
        sendInfo.ItemStatus   = itemTransfer.ItemStatus;
        sendInfo.TerminalNo   = itemTransfer.TerminalNo;
        
        //更新发送状态
        JDBCFieldArray setCols = new JDBCFieldArray();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        setCols.add("TransferStatus", "1");//0:未发送 1:发送进行中 2:发送成功 4:发送失败
        
        if(isManual){
            setCols.add("ReSendNum", 1);//重发次数重新计数
        }else{
            setCols.add("ReSendNum", (itemTransfer.ReSendNum+1));
        }
        
        setCols.add("LastModifyTime", sysDateTime);
        
        whereCols.add("WaterID", itemTransfer.WaterID);
        
        itemTransferDAO.update(setCols, whereCols);
        
        //插入发送流水
        PTTransferItemWaterDAO trasnferWaterDAO = daoFactory.getPTTransferItemWaterDAO();
        PTTransferItemWater trasnferWater = new PTTransferItemWater();
        SequenceGenerator seqGen = SequenceGenerator.getInstance();
        trasnferWater.WaterID = seqGen.getNextKey(SequenceGenerator.SEQ_TRANSFERWATERID);
        trasnferWater.CreateTime = sysDateTime;
        trasnferWater.LastModifyTime = sysDateTime;
        trasnferWater.ItemStatus     = itemTransfer.ItemStatus;
        trasnferWater.PackageID    = itemTransfer.PackageID;
        trasnferWater.PPCID        = itemTransfer.PPCID;
        trasnferWater.ZoneID       = itemTransfer.ZoneID;
        trasnferWater.TransferID   = String.valueOf((int)Math.abs(itemTransfer.WaterID));
        trasnferWater.ResendNum    = 1;
        trasnferWater.TransferType = itemTransfer.TransferType;
        trasnferWater.TransferStatus = itemTransfer.TransferStatus;
        trasnferWaterDAO.insert(trasnferWater);
        
        sendInfo.TransferWaterID = trasnferWater.WaterID;
        
        SendItemManager.getInstance().sendItemsToPPC(sendInfo);
        result = 1;
        //System.out.println("PTTransferItemSend:"+itemTransfer.WaterID+","+itemTransfer.PackageID+","+sendInfo.ItemType+","+(itemTransfer.ReSendNum+1));
        return result;
    }
    private String getRemarkValue(String remark, String key){
        if(StringUtils.isEmpty(remark) || StringUtils.isEmpty(key)){
            return "";
        }
        String value = "";
        String temp = remark;
        key = key.trim();
        if(!key.endsWith("=")){
            key = key+"=";
        }
        int index= temp.indexOf(key);
        if(index < 0){
            return value;
        }
        temp = temp.substring(index+key.length());
        if(temp.indexOf(";")>=0){
            value = temp.substring(0, temp.indexOf(";"));
        }else{
            value = temp;
        }
        value = value.trim();
        return value;
    }
}
