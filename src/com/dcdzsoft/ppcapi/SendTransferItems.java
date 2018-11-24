package com.dcdzsoft.ppcapi;

import java.util.concurrent.Callable;



import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;


import com.dcdzsoft.EduException;
import com.dcdzsoft.client.web.PTWebClientAdapter;
import com.dcdzsoft.constant.ControlParam;
import com.dcdzsoft.dto.business.InParamPTTransferItemMod;
import com.dcdzsoft.ppcapi.impl.PPCOfSaudiPost;

public class SendTransferItems  implements Callable<String> {
	private static Log log = org.apache.commons.logging.LogFactory.getLog(SendTransferItems.class);
	
	private SendInfo sendInfo = null;
	private PPCApiProxy ppcApi = null;
	public SendTransferItems(SendInfo sendInfo){
		this.sendInfo = sendInfo;
	}
	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		if(this.sendInfo==null){
			System.out.println("doSend error!!!sendInfo is null");
			return "";
		}
		if(!"1".equals(ControlParam.getInstance().getTransferToPPCFlag())){
            return "";
        }
		
		String result = "";
		InParamPTTransferItemMod inParamMod = new InParamPTTransferItemMod();
        inParamMod.WaterID = sendInfo.ItemID;
        inParamMod.TransferWaterID = sendInfo.TransferWaterID;
		if("PPCOfSaudiPost".equalsIgnoreCase(sendInfo.PPCInterface)){
            ppcApi = new PPCOfSaudiPost();
        }else{
            //接口未定义(未实现)
            result = "Error:PPCInterfaceAPI undefined.";
            inParamMod.SendStatus = "4";// 0:未发送 1:发送进行中 2:发送成功 4:发送失败
            inParamMod.Remark     = result;
            
            //更新发送状态
            PTWebClientAdapter.doBusiness(inParamMod);
            return result;
        }
		try {
			//
            result = ppcApi.sendItemInfo(sendInfo);
            if(result.startsWith("success")){
                inParamMod.SendStatus = "2";// 0:未发送 1:发送进行中 2:发送成功 4:发送失败
                inParamMod.Remark     = result;
            }else{
                inParamMod.SendStatus = "4";// 0:未发送 1:发送进行中 2:发送成功 4:发送失败
                inParamMod.Remark     = result;
            }
        } catch (EduException e) {
            // TODO Auto-generated catch block
            result = "System error:"+e.getMessage();
            inParamMod.SendStatus = "4";// 0:未发送 1:发送进行中 2:发送成功 4:发送失败
            inParamMod.Remark     = result;
            e.printStackTrace();
        }
		PTWebClientAdapter.doBusiness(inParamMod);
		
		log.debug("doTransferSend is ok? "+result);
		return "";
	}
}
