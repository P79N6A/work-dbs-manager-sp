package com.dcdzsoft.ppcapi.impl;

import com.dcdzsoft.EduException;
import com.dcdzsoft.constant.SysDict;
import com.dcdzsoft.ppcapi.PPCApiProxy;
import com.dcdzsoft.ppcapi.SendInfo;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PPCApiProxy  apiProxy = new PPCOfSaudiPost();
		SendInfo itemInfo  = new SendInfo();
		itemInfo.apiString = "https://servicesstg.sp.com.sa/ItemsService/SubmitItemsService.svc";
		itemInfo.userName  = "extrparcelstation";//"intrpos";//
		itemInfo.userKey   = "123456";
		
		itemInfo.CustomerAddress = "220797\nRiyadh, ZIP 12345\nSaudi Arabia";
		itemInfo.ItemType = SysDict.TRANSFER_TYPE_SUBMIT_ITEM;
		itemInfo.CustomerMobile = "0123456789";
		itemInfo.CustomerName   = "xx";
		itemInfo.TerminalNo  = "220709";
		itemInfo.OfBureau    = "220709";
		itemInfo.ItemCode    = "CS000000204SA";
		itemInfo.SenderMobile = "012356788";
		itemInfo.SenderName   = "zz";
		try {
			String result = apiProxy.sendItemInfo(itemInfo);
			System.out.println("result:"+result);
		} catch (EduException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(20000);
			System.out.println("end");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
