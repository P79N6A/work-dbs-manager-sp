package com.dcdzsoft.ppcapi.impl;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;

import com.dcdzsoft.EduException;
import com.dcdzsoft.client.web.DMWebClientAdapter;
import com.dcdzsoft.client.web.TBWebClientAdapter;
import com.dcdzsoft.constant.ControlParam;
import com.dcdzsoft.constant.SysDict;
import com.dcdzsoft.dto.business.InParamDMDeliveryE1InfoQry;
import com.dcdzsoft.dto.business.OutParamDMDeliveryE1InfoQry;
import com.dcdzsoft.dto.function.TBTerminal;
import com.dcdzsoft.email.ApiFailCount;
import com.dcdzsoft.ppcapi.PPCApiProxy;
import com.dcdzsoft.ppcapi.SendInfo;
import com.dcdzsoft.ppcapi.impl.SubmitItemServiceStub.ParcelStationResult;
import com.dcdzsoft.ppcapi.impl.SubmitItemServiceStub.SubmitItemEvent;
import com.dcdzsoft.ppcapi.impl.SubmitItemServiceStub.SubmitItemEventByItemCode;
import com.dcdzsoft.ppcapi.impl.SubmitItemServiceStub.SubmitItemEventByItemCodeParcelMTRequest;
import com.dcdzsoft.ppcapi.impl.SubmitItemServiceStub.SubmitItemEventByItemCodeResponse;
import com.dcdzsoft.ppcapi.impl.SubmitItemServiceStub.SubmitItemEventByItemCode_Parcel;
import com.dcdzsoft.ppcapi.impl.SubmitItemServiceStub.SubmitItemEventByItemCode_ParcelResponse;
import com.dcdzsoft.ppcapi.impl.SubmitItemServiceStub.SubmitItemEventResponse;
import com.dcdzsoft.ppcapi.impl.SubmitItemsServiceStub.ArrayOfItemTransaction;
import com.dcdzsoft.ppcapi.impl.SubmitItemsServiceStub.ArrayOfItemTransactionParcelStation;
import com.dcdzsoft.ppcapi.impl.SubmitItemsServiceStub.ItemTransaction;
import com.dcdzsoft.ppcapi.impl.SubmitItemsServiceStub.ItemTransactionParcelStation;
import com.dcdzsoft.ppcapi.impl.SubmitItemsServiceStub.SubmitItems;
import com.dcdzsoft.ppcapi.impl.SubmitItemsServiceStub.SubmitItemsResponse;
import com.dcdzsoft.ppcapi.impl.SubmitItemsServiceStub.SubmitItemsResult;
import com.dcdzsoft.ppcapi.impl.SubmitItemsServiceStub.SubmitParcelStationItems;
import com.dcdzsoft.util.NumberUtils;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

public class PPCOfSaudiPost implements PPCApiProxy{
    private static Log log = org.apache.commons.logging.LogFactory.getLog(PPCOfSaudiPost.class);
    private static String apiString = "";
    private static ApiFailCount counter = new ApiFailCount("SP PPC server");
    @Override
    public String sendItemInfo(SendInfo itemInfo) throws EduException {
        // TODO Auto-generated method stub
        
        String res = "";
        if(StringUtils.isEmpty(itemInfo.apiString)
            ||StringUtils.isEmpty(itemInfo.userName)
            ||StringUtils.isEmpty(itemInfo.userKey)){
            res = "Error: PPC API error.(uri, username, or userkey empty)";
            return res;
        }
        
        if(SysDict.TRANSFER_TYPE_SUBMIT_ITEM.equals(itemInfo.ItemType)){
        	InParamDMDeliveryE1InfoQry e1qry = new InParamDMDeliveryE1InfoQry();
			e1qry.PackageID = itemInfo.ItemCode;
			e1qry.CreateTime = itemInfo.CreateTime;
			e1qry.QryFlag    = "History";
			OutParamDMDeliveryE1InfoQry out = DMWebClientAdapter.doBusiness(e1qry);
			itemInfo.SenderName = out.BPartnerName;
			itemInfo.SenderMobile = out.Mobile;
			itemInfo.CustomerAddress = out.CustomerAddress;
			itemInfo.CustomerName    = out.CustomerName;
			itemInfo.CustomerMobile  = out.CustomerMobile;
			itemInfo.OfBureau        = out.OfBureau;
			
        	//res = submitItems(itemInfo);
        	res = submitParcelSationItems(itemInfo);
        }else{
        	TBTerminal terminal = new TBTerminal();
        	terminal.TerminalNo = itemInfo.TerminalNo;
        	try{
        		terminal = TBWebClientAdapter.doBusiness(terminal);
        	}catch(EduException e){
        	}
        	
        	itemInfo.OfBureau = terminal.OfBureau;
        	res = submitItemEvent(itemInfo);
        }
        
        return res;
    }
    private String submitItems(SendInfo itemInfo) throws EduException {
    	String res = "";
    	StringBuffer msgBuff = new StringBuffer();
    	//https://185.12.166.63/ItemsService/SubmitItemsService.svc?wsdl
    	//https://servicesstg.sp.com.sa/ItemsService/SubmitItemsService.svc
    	final String apiPath = "ItemsService/SubmitItemsService.svc";
    	StringBuffer uriBuff = new StringBuffer("https://servicesstg.sp.com.sa/");
    	uriBuff.append(apiPath);
    	apiString = uriBuff.toString();
    	
    	itemInfo.apiString = uriBuff.toString();
    	System.out.println("apiString="+itemInfo.apiString);
    	SubmitItemsServiceStub stub = null;
    	try {
    		msgBuff.append("SubmitItems,").append(itemInfo.apiString).append(",");
    		stub = new SubmitItemsServiceStub(itemInfo.apiString);
    		SubmitItems submitItems2 = new SubmitItems();
    		//submitItems2.setUserName("intrpos");//itemInfo.userName
    		//submitItems2.setPassword("123456");//itemInfo.userKey
    		submitItems2.setUserName(itemInfo.userName);
    		submitItems2.setPassword(itemInfo.userKey);
    		ArrayOfItemTransaction param = new ArrayOfItemTransaction();
    		ItemTransaction param2 = new ItemTransaction();
    		
    		//Optional init
    		/*param2.setCityId(1234);
    		param2.setCustomerAddressLine1("");
    		param2.setCustomerAddressLine2("");
    		param2.setItemCode("");
    		param2.setItemWeight(5);
    		param2.setParcelStationID(44);
    		param2.setPostServiceID(33);
    		param2.setSendToCellPhone1("0123456789");
    		param2.setSendToId(0);
    		param2.setSendToName("zz");
    		param2.setSenderCellPhone1("0123456789");
    		param2.setSenderId(0);
    		param2.setSenderName("cc");
    		param2.setSourceDirctorateID(777);
    		param2.setAdditionalNo(44);
    		param2.setAddressTypeID(55);
    		param2.setBuildingNo(66);
    		param2.setCreatedBy("aa");
    		param2.setCustomerCatID(77);
    		param2.setDestinationCountryID(88);
    		param2.setDestinationDirectorateID(0);
    		param2.setDistrictID(0);
    		param2.setDistrictName("44");
    		param2.setItemType(2);
    		param2.setNewPassportNo("rrr");
    		param2.setOfficeID(0);
    		param2.setOldPassportNo("qq");
    		param2.setOriginalCode("ww");
    		param2.setPoBox(220709);
    		param2.setProcedureID(5666);
    		param2.setSendToSocialNo("444");
    		param2.setSenderSocialNo("777");
    		param2.setSourceCountryID(0);//
    		param2.setStreetName("888");
    		param2.setUnitNo(1);
    		param2.setZipCode(12346);*/
    		
    		//Optional Setting
    		param2.setPostServiceID(5);
    		param2.setItemCode(itemInfo.ItemCode);
    		msgBuff.append(itemInfo.ItemCode).append(",");
    		param2.setItemType(2);
    		param2.setSourceDirctorateID(NumberUtils.parseInt(itemInfo.OfBureau));//PPC-AZC
    		param2.setDestinationCountryID(NumberUtils.parseInt(itemInfo.TerminalNo));//
    		msgBuff.append(itemInfo.OfBureau).append(",").append(itemInfo.TerminalNo).append(",");
    		param2.setSenderName(itemInfo.SenderName);
    		param2.setSenderCellPhone1(itemInfo.SenderMobile);
    		param2.setSendToName(itemInfo.CustomerName);
    		param2.setSendToCellPhone1(itemInfo.CustomerMobile);
    		msgBuff.append(itemInfo.CustomerName).append(",").append(itemInfo.CustomerMobile).append(",");
    		param2.setChannelTypeID(3);
    		param2.setAddressTypeID(6);//
    		param2.setParcelStationID(NumberUtils.parseInt(itemInfo.TerminalNo));//Locker.No
    		System.out.println(itemInfo.CustomerAddress);
    		String[] addressLines = itemInfo.CustomerAddress.split("\n");
    		msgBuff.append(itemInfo.CustomerAddress).append(",");
    		param2.setCustomerAddressLine1("");
    		param2.setCustomerAddressLine2("");
    		System.out.println(msgBuff.toString()+","+addressLines.length);
    		if(addressLines.length>=2){
    			param2.setCustomerAddressLine1(addressLines[0]);
    			param2.setCustomerAddressLine2(addressLines[1]);
    			System.out.println(addressLines[0]+","+addressLines[1]);
    		}else if(addressLines.length>=1){
    			param2.setCustomerAddressLine1(addressLines[0]);
    			System.out.println(addressLines[0]);
    		}else{
    			log.error("CustomerAddress="+itemInfo.CustomerAddress);
    			System.out.println("CustomerAddress="+itemInfo.CustomerAddress);
    		}
    		System.out.println(JSONObject.fromObject(param2).toString());
			param.addItemTransaction(param2 );
			submitItems2.setItemTransactions(param );
			SubmitItemsResponse resp = stub.submitItems(submitItems2 );
			SubmitItemsResult  result = resp.getSubmitItemsResult();
			if(result !=null){
				int code = result.getReturnCode();
                if(0 == code){
                    res = "success:"+msgBuff.toString();
                    sendPPCServerReport(true);
                }else{
                    res = "fail:["+result.getReturnMsg()+"], "+msgBuff.toString();
                    sendPPCServerReport(true);
                    log.debug(itemInfo.apiString+":"+res);
                }
            }else{
            	msgBuff.append(itemInfo.apiString);
                String errorMsg = "Transfer "+msgBuff.toString()+" fail. PPC Server NOT Responding";
                counter.addFailMsg(errorMsg);
                sendPPCServerReport(false);
                log.error(errorMsg);
                throw new EduException(errorMsg);
            }
    	} catch (AxisFault e) {
            // TODO Auto-generated catch block
            String errorMsg = "";
            if(StringUtils.isNotEmpty(msgBuff.toString())){
                errorMsg = "Transfer  fail :"+e.getMessage()+","+msgBuff.toString();
            }else{
                errorMsg = e.getMessage();
            }
            counter.addFailMsg(errorMsg);
            sendPPCServerReport(false);
            log.error("PPCApiProxySaudiPost-AxisFault:"+errorMsg);
            throw new EduException("PPCApiProxySaudiPost-AxisFault:"+errorMsg);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            String errorMsg = "";
            if(StringUtils.isNotEmpty(msgBuff.toString())){
                errorMsg = "Transfer "+msgBuff.toString()+" fail\n"+e.getMessage();
            }else{
                errorMsg = e.getMessage();
            }
            counter.addFailMsg(errorMsg);
            sendPPCServerReport(false);
            log.error("PPCApiProxySaudiPost-RemoteException:"+errorMsg);
            throw new EduException("PPCApiProxySaudiPost-RemoteException:"+errorMsg);
        }finally{
            if(stub!=null){
                try {
                	stub._getServiceClient().cleanupTransport();
                    stub._getServiceClient().cleanup();
                    stub.cleanup();
               } catch (AxisFault e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
               }
                stub = null;
            }
        }
    	return res;
    }
    private String submitParcelSationItems(SendInfo itemInfo) throws EduException {
    	String res = "";
    	StringBuffer msgBuff = new StringBuffer();
    	//https://185.12.166.63/ItemsService/SubmitItemsService.svc?wsdl
    	//https://servicesstg.sp.com.sa/ItemsService/SubmitItemsService.svc
    	final String apiPath = "ItemsService/SubmitItemsService.svc";
    	StringBuffer uriBuff = new StringBuffer("https://servicesstg.sp.com.sa/");
    	uriBuff.append(apiPath);
    	apiString = uriBuff.toString();
    	
    	itemInfo.apiString = uriBuff.toString();
    	System.out.println("apiString="+itemInfo.apiString);
    	SubmitItemsServiceStub stub = null;
    	try {
    		msgBuff.append("SubmitParcelSationItems,").append(itemInfo.apiString).append(",");
    		stub = new SubmitItemsServiceStub(itemInfo.apiString);
    		
    		SubmitParcelStationItems submitParcelStationItems0 = new SubmitParcelStationItems();
    		submitParcelStationItems0.setUserName(itemInfo.userName);
    		submitParcelStationItems0.setPassword(itemInfo.userKey);
    		ArrayOfItemTransactionParcelStation param = new ArrayOfItemTransactionParcelStation();
			submitParcelStationItems0.setItemTransactionParcelStation(param );
			ItemTransactionParcelStation[] param3 = new ItemTransactionParcelStation[1];
			ItemTransactionParcelStation param2 = new ItemTransactionParcelStation();
			
    		//Optional init
    		/*param2.setCityId(1234);
    		param2.setCustomerAddressLine1("");
    		param2.setCustomerAddressLine2("");
    		param2.setItemCode("");
    		param2.setItemWeight(5);
    		param2.setParcelStationID(44);
    		param2.setPostServiceID(33);
    		param2.setSendToCellPhone1("0123456789");
    		param2.setSendToId(0);
    		param2.setSendToName("zz");
    		param2.setSenderCellPhone1("0123456789");
    		param2.setSenderId(0);
    		param2.setSenderName("cc");
    		param2.setSourceDirctorateID(777);
    		param2.setAdditionalNo(44);
    		param2.setAddressTypeID(55);
    		param2.setBuildingNo(66);
    		param2.setCreatedBy("aa");
    		param2.setCustomerCatID(77);
    		param2.setDestinationCountryID(88);
    		param2.setDestinationDirectorateID(0);
    		param2.setDistrictID(0);
    		param2.setDistrictName("44");
    		param2.setItemType(2);
    		param2.setNewPassportNo("rrr");
    		param2.setOfficeID(0);
    		param2.setOldPassportNo("qq");
    		param2.setOriginalCode("ww");
    		param2.setPoBox(220709);
    		param2.setProcedureID(5666);
    		param2.setSendToSocialNo("444");
    		param2.setSenderSocialNo("777");
    		param2.setSourceCountryID(0);//
    		param2.setStreetName("888");
    		param2.setUnitNo(1);
    		param2.setZipCode(12346);*/
    		
    		//Optional Setting
			param2.setCityId(0);
			System.out.println(itemInfo.CustomerAddress);
    		String[] addressLines = itemInfo.CustomerAddress.split("\n");
    		msgBuff.append(itemInfo.CustomerAddress).append(",");
    		param2.setCustomerAddressLine1("");
    		param2.setCustomerAddressLine2("");
    		System.out.println(msgBuff.toString()+","+addressLines.length);
    		if(addressLines.length>=2){
    			param2.setCustomerAddressLine1(addressLines[0]);
    			param2.setCustomerAddressLine2(addressLines[1]);
    			System.out.println(addressLines[0]+","+addressLines[1]);
    		}else if(addressLines.length>=1){
    			param2.setCustomerAddressLine1(addressLines[0]);
    			System.out.println(addressLines[0]);
    		}else{
    			log.error("CustomerAddress="+itemInfo.CustomerAddress);
    			System.out.println("CustomerAddress="+itemInfo.CustomerAddress);
    		}
    		
    		param2.setItemCode(itemInfo.ItemCode);
    		msgBuff.append(itemInfo.ItemCode).append(",");
    		param2.setItemWeight(1);
    		param2.setParcelStationID(NumberUtils.parseInt(itemInfo.TerminalNo));//Locker.No
    		param2.setPostServiceID(5);
    		param2.setSendToId(-1);
    		param2.setSendToName(itemInfo.CustomerName);
    		param2.setSendToCellPhone1(itemInfo.CustomerMobile);
    		
    		param2.setSenderCellPhone1(itemInfo.SenderMobile);
    		param2.setSenderId(0);
    		param2.setSenderName(itemInfo.SenderName);
    		
    		//param2.setItemType(2);
    		param2.setSourceDirctorateID(NumberUtils.parseInt(itemInfo.OfBureau));//PPC-AZC
    		//param2.setDestinationCountryID(NumberUtils.parseInt(itemInfo.TerminalNo));//
    		msgBuff.append(itemInfo.OfBureau).append(",").append(itemInfo.TerminalNo).append(",");
    		msgBuff.append(itemInfo.CustomerName).append(",").append(itemInfo.CustomerMobile).append(",");
    		//param2.setChannelTypeID(3);
    		//param2.setAddressTypeID(6);//
    		
    		System.out.println(JSONObject.fromObject(param2).toString());
    		param3[0] = param2;
			param.setItemTransactionParcelStation(param3);
			com.dcdzsoft.ppcapi.impl.SubmitItemsServiceStub.SubmitParcelStationItemsResponse resp = stub.submitParcelStationItems(submitParcelStationItems0);
			
			SubmitItemsResult  result = resp.getSubmitParcelStationItemsResult();
			if(result !=null){
				int code = result.getReturnCode();
                if(0 == code){
                    res = "success:"+msgBuff.toString();
                    sendPPCServerReport(true);
                }else{
                    res = "fail:["+result.getReturnMsg()+"], "+msgBuff.toString();
                    sendPPCServerReport(true);
                    log.debug(itemInfo.apiString+":"+res);
                }
            }else{
            	msgBuff.append(itemInfo.apiString);
                String errorMsg = "Transfer "+msgBuff.toString()+" fail. PPC Server NOT Responding";
                counter.addFailMsg(errorMsg);
                sendPPCServerReport(false);
                log.error(errorMsg);
                throw new EduException(errorMsg);
            }
    	} catch (AxisFault e) {
            // TODO Auto-generated catch block
            String errorMsg = "";
            if(StringUtils.isNotEmpty(msgBuff.toString())){
                errorMsg = "Transfer  fail :"+e.getMessage()+","+msgBuff.toString();
            }else{
                errorMsg = e.getMessage();
            }
            counter.addFailMsg(errorMsg);
            sendPPCServerReport(false);
            log.error("PPCApiProxySaudiPost-AxisFault:"+errorMsg);
            throw new EduException("PPCApiProxySaudiPost-AxisFault:"+errorMsg);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            String errorMsg = "";
            if(StringUtils.isNotEmpty(msgBuff.toString())){
                errorMsg = "Transfer "+msgBuff.toString()+" fail\n"+e.getMessage();
            }else{
                errorMsg = e.getMessage();
            }
            counter.addFailMsg(errorMsg);
            sendPPCServerReport(false);
            log.error("PPCApiProxySaudiPost-RemoteException:"+errorMsg);
            throw new EduException("PPCApiProxySaudiPost-RemoteException:"+errorMsg);
        }finally{
            if(stub!=null){
                try {
                	stub._getServiceClient().cleanupTransport();
                    stub._getServiceClient().cleanup();
                    stub.cleanup();
               } catch (AxisFault e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
               }
                stub = null;
            }
        }
    	return res;
    }
    private String submitItemEvent(SendInfo itemInfo) throws EduException {
    	String res = "";
    	StringBuffer msgBuff = new StringBuffer();
    	SubmitItemServiceStub stub = null;
    	
    	//https://185.12.166.63/ParcelStation/ParcelStationService.svc?wsdl
    	//https://servicesprd.sp.com.sa/ParcelStation/ParcelStationService.svc
    	final String apiPath = "ParcelStation/ParcelStationService.svc";
    	StringBuffer uriBuff = new StringBuffer();
    	if(itemInfo.apiString.endsWith("ParcelStation/ParcelStationService.svc")){
    		uriBuff.append(itemInfo.apiString);
    	}else if(itemInfo.apiString.endsWith("/")){
    		uriBuff.append(itemInfo.apiString).append(apiPath);
    	}else{
    		uriBuff.append(itemInfo.apiString).append("/").append(apiPath);
    	}
    	apiString = uriBuff.toString();
    	itemInfo.apiString = uriBuff.toString();
    	try {
            String transferType = itemInfo.ItemType;
            int ProcedureId = 0;
            int Deliveryid  = 0;
            switch(transferType){
            case SysDict.TRANSFER_TYPE_ANNOUNCING:/*分拣投递中,上传更新订单状态*/
                ProcedureId = 20;
                Deliveryid  = 3;
                break;
            case SysDict.TRANSFER_TYPE_TAKEOUT_BY_CUSTOMER://用户正常取件
            case SysDict.TRANSFER_TYPE_TAKEOUT_BY_DAD:
            case SysDict.TRANSFER_TYPE_TAKEOUT_BY_MANAGER:
                ProcedureId = 18;
                Deliveryid  = -1;
                break;
            case SysDict.TRANSFER_TYPE_PARCEL_NOT_RECEIVED://未收到包裹
                ProcedureId = 203;
                Deliveryid  = -1;//Pane1 + Status = "Listed"  + Transfer 
                break;
            case SysDict.TRANSFER_TYPE_ERROR_INFO://异常信息退单
                ProcedureId = 20;
                Deliveryid  = 71;//Pane1 + Status = "Received"  + Transfer 
                break;
            case SysDict.TRANSFER_TYPE_EXPIRED://"4";/*逾期未取退单*/
                ProcedureId = 20;
                Deliveryid  = 70;
                break;
            case SysDict.TRANSFER_TYPE_M_EXPIRED:///*逾期未取退单（人工）  Remark 包含  M_expire*/
                ProcedureId = 20;
                Deliveryid  = 90;
                break;
            case SysDict.TRANSFER_TYPE_MISSING://"6";/*包裹丢失退单*/
                ProcedureId = 20;
                Deliveryid  = 7;
                break;
            case SysDict.TRANSFER_TYPE_SEND_MAIL://"8";/*寄件订单*/
            case SysDict.TRANSFER_TYPE_MAIL_RETURNED://"9";/*寄件退单*/
                ProcedureId = 20;
                Deliveryid  = 3;
                break;
            case SysDict.TRANSFER_TYPE_DROPPED:
            case SysDict.TRANSFER_TYPE_D_DROPPED:  
                ProcedureId = 202;
                Deliveryid  = -1;
                break;
            case SysDict.TRANSFER_TYPE_ADD_ITEM:
                ProcedureId = 201;
                Deliveryid  = -1;
                break;
            }
            
            //
            stub = new SubmitItemServiceStub(itemInfo.apiString);
            stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(1000*60);
            
            ParcelStationResult result = null;
            msgBuff.append("Barcode:").append(itemInfo.ItemCode).append(",");
            msgBuff.append("Pid:").append(ProcedureId).append(",");
            msgBuff.append("Did:").append(Deliveryid).append(",");
            msgBuff.append("ItemID:").append(itemInfo.ItemID).append(",");
            boolean isNewApi = true;//20170522
            if(isNewApi){
            	msgBuff.append("SubmitItemEventByItemCode_Parcel").append(",");
            	SubmitItemEventByItemCode_Parcel submitItemEventByItemCode_Parcel2 = new SubmitItemEventByItemCode_Parcel();
            	submitItemEventByItemCode_Parcel2.setUserName(itemInfo.userName);
            	submitItemEventByItemCode_Parcel2.setPassword(itemInfo.userKey);//"123456"
            	SubmitItemEventByItemCodeParcelMTRequest param = new SubmitItemEventByItemCodeParcelMTRequest();
            	param.setBarcode(itemInfo.ItemCode);
            	param.setProcedureId(ProcedureId);
            	param.setDeliveryReasonId(Deliveryid);
            	param.setTransFrom(NumberUtils.parseInt(itemInfo.TerminalNo));
            	param.setTransTo(NumberUtils.parseInt(itemInfo.OfBureau));
				submitItemEventByItemCode_Parcel2.setItemCodeMTRequest(param );
            	
            	//SubmitItemEventByItemCode_Parcel 
				SubmitItemEventByItemCode_ParcelResponse resp = stub.submitItemEventByItemCode_Parcel(submitItemEventByItemCode_Parcel2 );
				result = resp.getSubmitItemEventByItemCode_ParcelResult();
            }else{
            	if(itemInfo.ItemID>=0){//由PPC server产生的itemID在系统中保存为负值
            		msgBuff.append("submitItemEventByItemCode").append(",");
                    //DAD 或AddItem时elocker 生成ItemID
                    SubmitItemEventByItemCode submitItemEventByItemCode0 = new SubmitItemEventByItemCode();
                    submitItemEventByItemCode0.setUserName(itemInfo.userName);
                    submitItemEventByItemCode0.setPassword(itemInfo.userKey);//"123456"

                    submitItemEventByItemCode0.setBarcode(itemInfo.ItemCode);
                    submitItemEventByItemCode0.setProcedureId(ProcedureId);
                    submitItemEventByItemCode0.setDeliveryReasonId(Deliveryid);
                    
                    SubmitItemEventByItemCodeResponse resp = stub.submitItemEventByItemCode(submitItemEventByItemCode0 );
                    result = resp.getSubmitItemEventByItemCodeResult();
                }else{
                	msgBuff.append("submitItemEvent").append(",");
                    int itemID = (int)Math.abs(itemInfo.ItemID);//绝对值   由PPC server产生的itemID在系统中保存为负值
                    
                    //"https://185.12.166.63/ParcelStation/ParcelStationService.svc"
                    SubmitItemEvent submitItem = new SubmitItemEvent();
                    submitItem.setUserName(itemInfo.userName);
                    submitItem.setPassword(itemInfo.userKey);//"123456"
                                
                    submitItem.setItemID(itemID);
                    submitItem.setBarcode(itemInfo.ItemCode);
                    submitItem.setProcedureId(ProcedureId);
                    submitItem.setDeliveryReasonId(Deliveryid);
                    
                    SubmitItemEventResponse resp = stub.submitItemEvent(submitItem );
                    result = resp.getSubmitItemEventResult();
                    
                }
            }
            
            
            if(result !=null){
                boolean status = result.getStatus();
                if(status){
                    res = "success:"+msgBuff.toString();
                    sendPPCServerReport(true);
                }else{
                    res = "fail:["+msgBuff.toString()+"], "+result.getMessage();
                    sendPPCServerReport(true);
                    log.debug(itemInfo.apiString+":"+res);
                }
            }else{
            	msgBuff.append(itemInfo.apiString);
                String errorMsg = "Transfer "+msgBuff.toString()+" fail. PPC Server NOT Responding";
                counter.addFailMsg(errorMsg);
                sendPPCServerReport(false);
                log.error(errorMsg);
                throw new EduException(errorMsg);
            }
            
         } catch (AxisFault e) {
             // TODO Auto-generated catch block
             String errorMsg = "";
             if(StringUtils.isNotEmpty(msgBuff.toString())){
                 errorMsg = "Transfer "+msgBuff.toString()+" fail\n"+e.getMessage();
             }else{
                 errorMsg = e.getMessage();
             }
             counter.addFailMsg(errorMsg);
             sendPPCServerReport(false);
             log.error("PPCApiProxySaudiPost-AxisFault:"+errorMsg);
             throw new EduException("PPCApiProxySaudiPost-AxisFault:"+errorMsg);
         } catch (RemoteException e) {
             // TODO Auto-generated catch block
             String errorMsg = "";
             if(StringUtils.isNotEmpty(msgBuff.toString())){
                 errorMsg = "Transfer "+msgBuff.toString()+" fail\n"+e.getMessage();
             }else{
                 errorMsg = e.getMessage();
             }
             counter.addFailMsg(errorMsg);
             sendPPCServerReport(false);
             log.error("PPCApiProxySaudiPost-RemoteException:"+errorMsg);
             throw new EduException("PPCApiProxySaudiPost-RemoteException:"+errorMsg);
         }finally{
             if(stub!=null){
                 try {
                    stub._getServiceClient().cleanupTransport();
                    stub._getServiceClient().cleanup();
                    stub.cleanup();
                } catch (AxisFault e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                 stub = null;
             }
         }
    	return res;
    }
    public static boolean sendFailReport(){
        long thresholdCount = NumberUtils.parseLong(ControlParam.getInstance().getSendPPCFailReportCnt());
        counter.setThresholdCount(thresholdCount);
        counter.setApiString(apiString);
        boolean isOk = counter.sendFailReport();
        return isOk;
    }
    private boolean sendPPCServerReport(boolean isRecovery){
        long thresholdCount = NumberUtils.parseLong(ControlParam.getInstance().getSendPPCFailReportCnt());
        if(thresholdCount < 3){
            thresholdCount = 3;
        }
        counter.setThresholdCount(thresholdCount);
        counter.setApiString(apiString);
        boolean isOk = counter.sendPPCServerReport(isRecovery);
        return isOk;
    }
}
