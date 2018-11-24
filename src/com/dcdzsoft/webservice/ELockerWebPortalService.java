package com.dcdzsoft.webservice;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.HTTPConstants;

import com.dcdzsoft.EduException;
import com.dcdzsoft.client.web.DMWebClientAdapter;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.dto.business.InParamDMBusinessPartnerLogin;
import com.dcdzsoft.dto.business.InParamDMBusinessPartnerModPwd;
import com.dcdzsoft.dto.business.InParamDMDeliveryCancel;
import com.dcdzsoft.dto.business.InParamDMDeliveryCreate;
import com.dcdzsoft.dto.business.InParamDMDeliveryE1InfoQry;
import com.dcdzsoft.dto.business.InParamDMDeliveryItemDetailQry;
import com.dcdzsoft.dto.business.InParamDMDeliveryItemsQry;
import com.dcdzsoft.dto.business.InParamDMDeliveryItemsQryCount;
import com.dcdzsoft.dto.business.InParamDMPartnerBalanceQry;
import com.dcdzsoft.dto.business.InParamDMPartnerServicesQry;
import com.dcdzsoft.dto.business.InParamLockerStationAddressQry;
import com.dcdzsoft.dto.business.OutParamDMBusinessPartnerLogin;
import com.dcdzsoft.dto.business.OutParamDMDeliveryCreate;
import com.dcdzsoft.dto.business.OutParamDMDeliveryE1InfoQry;
import com.dcdzsoft.dto.business.OutParamDMDeliveryItemDetailQry;
import com.dcdzsoft.dto.business.OutParamDMDeliveryItemsQry;
import com.dcdzsoft.dto.business.OutParamDMPartnerBalanceQry;
import com.dcdzsoft.dto.business.OutParamDMPartnerServicesQry;
import com.dcdzsoft.dto.business.OutParamLockerStationAddressQry;
import com.dcdzsoft.sda.security.SecurityUtils;

/**
 * 
 * <p>
 * Title: 智能自助包裹柜系统
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2015
 * </p>
 *
 * <p>
 * Company: 杭州东城电子有限公司
 * </p>
 *
 * @author zhengxy
 * @version 1.0
 */
public class ELockerWebPortalService {

	/**
	 * 获取客户端IP地址，如调用方的IP，以便检查权限。 适用于axis发布的webservice
	 * 
	 * @return
	 */
	private String getClientIpAxis() {
		String clientIp = "";
		MessageContext mc = null;
		HttpServletRequest request = null;
		try {
			mc = MessageContext.getCurrentMessageContext();
			if (mc == null)
				throw new Exception("无法获取到MessageContext");
			request = (HttpServletRequest) mc
					.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
			System.out.println("remote  ip:  " + request.getRemoteAddr());
			clientIp = request.getRemoteAddr();
		} catch (Exception e) {
			// System.out.println(e.getMessage());
			// e.printStackTrace();
		}
		return clientIp;
	}

	/**
	 * 验证
	 * 
	 * @param user
	 * @param datakey
	 * @return
	 */
	private boolean verification(String user, String datakey) {
		ApplicationConfig apCfg = ApplicationConfig.getInstance();
		String clientKey = apCfg.getAppKeyElockerWebProtal();// "dcdzSoft"
		String allowClientIPs = apCfg.getAllowClientIPs();// 绑定网站IP
		// IP检查
		String ip = getClientIpAxis();
		if (allowClientIPs.indexOf(ip) < 0) {
			System.out.println("Allow IPs:" + allowClientIPs);
			return false;
		}

		// DataKey检查
		String key = SecurityUtils.md5(user + clientKey);
		if (!key.equals(datakey)) {
			System.out.println("ClientKey:" + clientKey + ",datakey:" + datakey
					+ ",user:" + user);
			return false;
		}
		return true;
	}

	// http://192.168.8.116:8080/services/ELockerWebPortalService?wsdl
	public OutParamDMBusinessPartnerLogin doBusinessPartnerLogin(
			InParamDMBusinessPartnerLogin p1, String datakey) {
		System.out.println("doBusinessPartnerLogin");
		OutParamDMBusinessPartnerLogin out = null;
		if (!verification(p1.Username, datakey)) {
			return null;
		}
		try {
			out = DMWebClientAdapter.doBusiness(p1);
		} catch (EduException e) {
			System.out.println(e.getMessage());
		}
		return out;
	}

	public int doDMBusinessPartnerModPwd(InParamDMBusinessPartnerModPwd p1,
			String datakey) {
		System.out.println("doBusinessPartnerLogin");
		if (!verification(p1.BPartnerID, datakey)) {
			return -1;
		}
		int result = 0;
		try {
			result = DMWebClientAdapter.doBusiness(p1);
		} catch (EduException e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}

	public OutParamDMPartnerBalanceQry doBusinessPartnerBalanceQry(
			InParamDMPartnerBalanceQry p1, String datakey) {
		if (!verification(p1.BPartnerID, datakey)) {
			return null;
		}
		OutParamDMPartnerBalanceQry out = null;
		try {
			out = DMWebClientAdapter.doBusiness(p1);
		} catch (EduException e) {
			System.out.println(e.getMessage());
		}
		return out;
	}

	public java.util.List<OutParamDMPartnerServicesQry> doBusinessPartnerServicesQry(
			InParamDMPartnerServicesQry p1, String datakey) {
		// System.out.println("doBusinessPartnerServicesQry");
		if (!verification(p1.BPartnerID, datakey)) {
			return null;
		}
		java.util.List<OutParamDMPartnerServicesQry> outList = null;
		try {
			outList = DMWebClientAdapter.doBusiness(p1);
		} catch (EduException e) {
			System.out.println(e.getMessage());
		}
		return outList;
	}

	public OutParamDMDeliveryCreate doDMDeliveryCreate(
			InParamDMDeliveryCreate p1, String datakey) {
		// System.out.println("doBusinessPartnerServicesQry");
		if (!verification(p1.BPartnerID, datakey)) {
			return null;
		}
		com.dcdzsoft.dto.business.OutParamDMDeliveryCreate out = null;
		try {
			out = DMWebClientAdapter.doBusiness(p1);
		} catch (EduException e) {
			System.out.println(e.getMessage());
		}
		return out;
	}

	public OutParamDMDeliveryE1InfoQry doDMDeliveryE1InfoQry(
			InParamDMDeliveryE1InfoQry p1, String datakey) {
		// System.out.println("doDMDeliveryE1InfoQry");
		if (!verification(p1.BPartnerID, datakey)) {
			return null;
		}
		com.dcdzsoft.dto.business.OutParamDMDeliveryE1InfoQry out = null;
		try {
			out = DMWebClientAdapter.doBusiness(p1);
		} catch (EduException e) {
			System.out.println(e.getMessage());
		}
		return out;
	}

	public int doDMDeliveryCancel(InParamDMDeliveryCancel p1, String datakey) {
		if (!verification(p1.BPartnerID, datakey)) {
			return -1;
		}
		int result = 0;
		try {
			result = DMWebClientAdapter.doBusiness(p1);
			return result;
		} catch (EduException e) {
			System.out.println(e.getMessage());
			return -1;
		}

	}

	public java.util.List<OutParamDMDeliveryItemsQry> doDMDeliveryItemsQry(
			InParamDMDeliveryItemsQry p1, String datakey) {
		// System.out.println("doDMDeliveryItemsQry");
		if (!verification(p1.BPartnerID, datakey)) {
			return null;
		}

		java.util.List<com.dcdzsoft.dto.business.OutParamDMDeliveryItemsQry> outList = null;
		try {
			outList = DMWebClientAdapter.doBusiness(p1);
		} catch (EduException e) {
			System.out.println(e.getMessage());
		}
		return outList;
	}

	public int doDMDeliveryItemsQryCount(InParamDMDeliveryItemsQryCount p1,
			String datakey) {
		if (!verification(p1.BPartnerID, datakey)) {
			return -1;
		}

		int result = 0;
		try {
			result = DMWebClientAdapter.doBusiness(p1);
		} catch (EduException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

	public OutParamDMDeliveryItemDetailQry doDMDeliveryItemDetailQry(
			InParamDMDeliveryItemDetailQry p1, String datakey) {
		// System.out.println("doDMDeliveryItemDetailQry");
		if (!verification(p1.OperID, datakey)) {
			return null;
		}

		com.dcdzsoft.dto.business.OutParamDMDeliveryItemDetailQry out = null;
		try {
			out = DMWebClientAdapter.doBusiness(p1);
		} catch (EduException e) {
			System.out.println(e.getMessage());
		}
		return out;
	}

	public java.util.List<OutParamLockerStationAddressQry> doLockerStationAddressQry(
			InParamLockerStationAddressQry p1, String datakey) {
		// System.out.println("doLockerStationAddressQry");
		java.util.List<OutParamLockerStationAddressQry> outList = null;
		try {
			outList = DMWebClientAdapter.doBusiness(p1);
		} catch (EduException e) {
			System.out.println(e.getMessage());
		}
		return outList;
	}
}
