package com.dcdzsoft.ppcapi;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;

import com.dcdzsoft.EduException;
import com.dcdzsoft.constant.ErrorCode;
import com.dcdzsoft.ppcapi.impl.EmallPickUpServiceStub;
import com.dcdzsoft.ppcapi.impl.EmallPickUpServiceStub.ArrayOfPreAdviceshipmentData;
import com.dcdzsoft.ppcapi.impl.EmallPickUpServiceStub.GetPreAdviceshipmentByItemCode;
import com.dcdzsoft.ppcapi.impl.EmallPickUpServiceStub.GetPreAdviceshipmentByItemCodeResponse;
import com.dcdzsoft.ppcapi.impl.EmallPickUpServiceStub.PreAdviceshipmentData;
import com.dcdzsoft.ppcapi.impl.EmallPickUpServiceStub.PreAdviceshipmentResult;

public class GetInfoManager {
    private static Log log = org.apache.commons.logging.LogFactory.getLog(GetInfoManager.class);
    private GetInfoManager(){
        
    }
    private static class SingletonHolder{
        private static GetInfoManager instance = new GetInfoManager();
    }
    
    /**
     * 静态工厂方法，返还此类的惟一实例
     * @return
     */
    public static GetInfoManager getInstance(){
        return SingletonHolder.instance;
    }
    
    /**
     * 根据订单号，获取收件人手机号
     * @param itemCode
     * @return
     * @throws EduException
     */
    public String getCutomerMobileByItemCode(final String itemCode) throws EduException {
        String mobile = "";
        final String apiPath = "EmallPickUp/EmallPickUpService.svc";
        StringBuffer uriBuff = new StringBuffer();
        uriBuff.append("https://servicesstg.sp.com.sa").append("/").append(apiPath);
        EmallPickUpServiceStub stub = null;
        try {
            log.info("getCutomerMobileByItemCode:"+itemCode+","+uriBuff.toString());
            stub = new EmallPickUpServiceStub(uriBuff.toString());
            stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(1000*60);
            GetPreAdviceshipmentByItemCode getPreAdviceshipmentByItemCode0 = new GetPreAdviceshipmentByItemCode();
            getPreAdviceshipmentByItemCode0.setItemCode(itemCode);
            getPreAdviceshipmentByItemCode0.setWsUsername("extrparcelstation");
            getPreAdviceshipmentByItemCode0.setWsPassword("123456");
            GetPreAdviceshipmentByItemCodeResponse response = stub.getPreAdviceshipmentByItemCode(getPreAdviceshipmentByItemCode0);
            PreAdviceshipmentResult result = response.getGetPreAdviceshipmentByItemCodeResult();
            
            if(result !=null){
                int status = result.getStatus();
                if(status == 0){
                    ArrayOfPreAdviceshipmentData data =result.getPreAdviceshipmentData();
                    PreAdviceshipmentData[] datas= data.getPreAdviceshipmentData();
                    mobile = datas[0].getCustomerCellPhone();
                    log.info("getCutomerMobileByItemCode:"+itemCode+","+mobile);
                }else{
                    log.info("getCutomerMobileByItemCode:"+itemCode+",status="+status);
                    throw new EduException(ErrorCode.ERR_PACKAGENOTEXISTS);
                }
            }else{
                log.info("getCutomerMobileByItemCode:"+itemCode+",result is null");
                throw new EduException(ErrorCode.ERR_PACKAGENOTEXISTS);
            }
        } catch (AxisFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error("getCutomerMobileByItemCode:"+itemCode+","+e.getMessage());
            throw new EduException(ErrorCode.ERR_PACKAGENOTEXISTS);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error("getCutomerMobileByItemCode:"+itemCode+","+e.getMessage());
            throw new EduException(ErrorCode.ERR_PACKAGENOTEXISTS);
        } finally{
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
        
        return mobile;
    }
    
    public static void main(String[] arg){
        String itemCode = "EMALL2225874698";
        try {
            String mobile = GetInfoManager.getInstance().getCutomerMobileByItemCode(itemCode);
            
            Thread.sleep(60000);
            System.out.println("itemCode="+itemCode+",mobile="+mobile);
        } catch (EduException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
