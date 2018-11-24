package com.dcdzsoft.businessproxy;

import com.dcdzsoft.aop.BusiBeanAOPBaseClass;
import com.dcdzsoft.dto.business.InParamAPCustomerAppOpenBox;
import com.dcdzsoft.dto.business.InParamAPCustomerGetKeyMsg;
import com.dcdzsoft.dto.business.InParamAPCustomerInfoQry;
import com.dcdzsoft.dto.business.InParamAPCustomerPackageCount;
import com.dcdzsoft.dto.business.InParamAPCustomerPackageDetail;
import com.dcdzsoft.dto.business.InParamAPCustomerPackageList;
import com.dcdzsoft.dto.business.InParamAPCustomerPackageStat;
import com.dcdzsoft.dto.business.InParamAPCustomerRegister;
import com.dcdzsoft.dto.business.InParamAPCustomerUpdate;
import com.dcdzsoft.dto.business.InParamAPDepartListQry;
import com.dcdzsoft.dto.business.InParamAPLockerPoBoxQry;
import com.dcdzsoft.dto.business.InParamAPPostmanAppTakeout;
import com.dcdzsoft.dto.business.InParamAPPostmanCheck;
import com.dcdzsoft.dto.business.InParamAPPostmanInboxPackage;
import com.dcdzsoft.dto.business.InParamAPPostmanPackageDetail;
import com.dcdzsoft.dto.business.InParamAPPostmanRegister;
import com.dcdzsoft.dto.business.InParamAPPostmanModPwd;
import com.dcdzsoft.dto.business.OutParamAPCustomerGetKeyMsg;
import com.dcdzsoft.dto.business.OutParamAPCustomerInfoQry;
import com.dcdzsoft.dto.business.OutParamAPCustomerPackageDetail;
import com.dcdzsoft.dto.business.OutParamAPCustomerPackageList;
import com.dcdzsoft.dto.business.OutParamAPCustomerPackageStat;
import com.dcdzsoft.dto.business.OutParamAPCustomerRegister;
import com.dcdzsoft.dto.business.OutParamAPCustomerUpdate;
import com.dcdzsoft.dto.business.OutParamAPDepartListQry;
import com.dcdzsoft.dto.business.OutParamAPLockerPoBoxQry;
import com.dcdzsoft.dto.business.OutParamAPPostmanCheck;
import com.dcdzsoft.dto.business.InParamAPPostmanPackageList;
import com.dcdzsoft.dto.business.OutParamAPPostmanInboxPackage;
import com.dcdzsoft.dto.business.OutParamAPPostmanPackageDetail;
import com.dcdzsoft.dto.business.OutParamAPPostmanPackageList;
import com.dcdzsoft.dto.business.InParamAPPostmanPackageCount;
import com.dcdzsoft.dto.business.InParamAPPostmanPackageStat;
import com.dcdzsoft.dto.business.OutParamAPPostmanPackageStat;
import com.dcdzsoft.dto.business.InParamAPCustomerInboxPackage;
import com.dcdzsoft.dto.business.OutParamAPCustomerInboxPackage;


/**
 * 
 * <p>
 * Title: 智能自助包裹柜系统
 * </p>
 * 
 * <p>
 * Description: 暴露给移动端所有业务接口
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * 
 * <p>
 * Company: 杭州东城电子有限公司
 * </p>
 * 
 * @author wangzl
 * @version 1.0
 */
public class MobileProxy {
	/**
	 * 投递员身份验证
	 * 
	 * @param p1
	 * @param TerminalNo
	 * @return
	 * @throws com.dcdzsoft.EduException
	 */
	public static OutParamAPPostmanCheck doBusiness(InParamAPPostmanCheck p1) throws com.dcdzsoft.EduException {
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APPostmanCheck bean = (com.dcdzsoft.business.ap.APPostmanCheck) aop
				.bind(com.dcdzsoft.business.ap.APPostmanCheck.class);
		return bean.doBusiness(p1);
	}
	
	/**
	 * 投递员未取包裹列表
	 * 
	 * @param p1
	 * @param TerminalNo
	 * @return
	 * @throws com.dcdzsoft.EduException
	 */
	public static java.util.List<OutParamAPPostmanInboxPackage> doBusiness(InParamAPPostmanInboxPackage p1) throws com.dcdzsoft.EduException {
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APPostmanInboxPackage bean = (com.dcdzsoft.business.ap.APPostmanInboxPackage) aop
				.bind(com.dcdzsoft.business.ap.APPostmanInboxPackage.class);
		return bean.doBusiness(p1);
	}
	
	/**
	 * 投递员包裹列表
	 * 
	 * @param p1
	 * @param TerminalNo
	 * @return
	 * @throws com.dcdzsoft.EduException
	 */
	public static java.util.List<OutParamAPPostmanPackageList> doBusiness(InParamAPPostmanPackageList p1) throws com.dcdzsoft.EduException {
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APPostmanPackageList bean = (com.dcdzsoft.business.ap.APPostmanPackageList) aop
				.bind(com.dcdzsoft.business.ap.APPostmanPackageList.class);
		return bean.doBusiness(p1);
	}
	
	/**
	 * 投递员包裹列表总记录数
	 * 
	 * @param p1
	 * @param TerminalNo
	 * @return
	 * @throws com.dcdzsoft.EduException
	 */
	public static int doBusiness(InParamAPPostmanPackageCount p1) throws com.dcdzsoft.EduException {
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APPostmanPackageCount bean = (com.dcdzsoft.business.ap.APPostmanPackageCount) aop
				.bind(com.dcdzsoft.business.ap.APPostmanPackageCount.class);
		return bean.doBusiness(p1);
	}
	
	/**
	 * 投递员单个包裹查询
	 * 
	 * @param p1
	 * @param TerminalNo
	 * @return
	 * @throws com.dcdzsoft.EduException
	 */
	public static OutParamAPPostmanPackageDetail doBusiness(InParamAPPostmanPackageDetail p1) throws com.dcdzsoft.EduException {
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APPostmanPackageDetail bean = (com.dcdzsoft.business.ap.APPostmanPackageDetail) aop
				.bind(com.dcdzsoft.business.ap.APPostmanPackageDetail.class);
		return bean.doBusiness(p1);
	}
	
	/**
	 * 投递员包裹统计
	 * 
	 * @param p1
	 * @param TerminalNo
	 * @return
	 * @throws com.dcdzsoft.EduException
	 */
	public static java.util.List<OutParamAPPostmanPackageStat> doBusiness(InParamAPPostmanPackageStat p1) throws com.dcdzsoft.EduException {
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APPostmanPackageStat bean = (com.dcdzsoft.business.ap.APPostmanPackageStat) aop
				.bind(com.dcdzsoft.business.ap.APPostmanPackageStat.class);
		return bean.doBusiness(p1);
	}
	
	/**
	 * 收件人未取包裹列表
	 * 
	 * @param p1
	 * @param TerminalNo
	 * @return
	 * @throws com.dcdzsoft.EduException
	 */
	public static java.util.List<OutParamAPCustomerInboxPackage> doBusiness(InParamAPCustomerInboxPackage p1) throws com.dcdzsoft.EduException {
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APCustomerInboxPackage bean = (com.dcdzsoft.business.ap.APCustomerInboxPackage) aop
				.bind(com.dcdzsoft.business.ap.APCustomerInboxPackage.class);
		return bean.doBusiness(p1);
	}
	
	/**
	 * 收件人包裹列表
	 * 
	 * @param p1
	 * @param TerminalNo
	 * @return
	 * @throws com.dcdzsoft.EduException
	 */
	public static java.util.List<OutParamAPCustomerPackageList> doBusiness(InParamAPCustomerPackageList p1) throws com.dcdzsoft.EduException {
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APCustomerPackageList bean = (com.dcdzsoft.business.ap.APCustomerPackageList) aop
				.bind(com.dcdzsoft.business.ap.APCustomerPackageList.class);
		return bean.doBusiness(p1);
	}
	
	/**
	 * 收件人包裹列表总记录数
	 * 
	 * @param p1
	 * @param TerminalNo
	 * @return
	 * @throws com.dcdzsoft.EduException
	 */
	public static int doBusiness(InParamAPCustomerPackageCount p1) throws com.dcdzsoft.EduException {
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APCustomerPackageCount bean = (com.dcdzsoft.business.ap.APCustomerPackageCount) aop
				.bind(com.dcdzsoft.business.ap.APCustomerPackageCount.class);
		return bean.doBusiness(p1);
	}
	
	/**
	 * 收件人单个包裹查询
	 * 
	 * @param p1
	 * @param TerminalNo
	 * @return
	 * @throws com.dcdzsoft.EduException
	 */
	public static OutParamAPCustomerPackageDetail doBusiness(InParamAPCustomerPackageDetail p1) throws com.dcdzsoft.EduException {
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APCustomerPackageDetail bean = (com.dcdzsoft.business.ap.APCustomerPackageDetail) aop
				.bind(com.dcdzsoft.business.ap.APCustomerPackageDetail.class);
		return bean.doBusiness(p1);
	}
	
	/**
	 * 收件人包裹统计
	 * 
	 * @param p1
	 * @param TerminalNo
	 * @return
	 * @throws com.dcdzsoft.EduException
	 */
	public static java.util.List<OutParamAPCustomerPackageStat> doBusiness(InParamAPCustomerPackageStat p1) throws com.dcdzsoft.EduException {
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APCustomerPackageStat bean = (com.dcdzsoft.business.ap.APCustomerPackageStat) aop
				.bind(com.dcdzsoft.business.ap.APCustomerPackageStat.class);
		return bean.doBusiness(p1);
	}
	
	/**
	 * 用户APP开箱
	 * 
	 * @param p1
	 * @param TerminalNo
	 * @return
	 * @throws com.dcdzsoft.EduException
	 */
	public static String doBusiness(InParamAPCustomerAppOpenBox p1) throws com.dcdzsoft.EduException {
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APCustomerAppOpenBox bean = (com.dcdzsoft.business.ap.APCustomerAppOpenBox) aop
				.bind(com.dcdzsoft.business.ap.APCustomerAppOpenBox.class);
		return bean.doBusiness(p1);
	}
	
	/**
	 * 投递员APP取回
	 * 
	 * @param p1
	 * @param TerminalNo
	 * @return
	 * @throws com.dcdzsoft.EduException
	 */
	public static String doBusiness(InParamAPPostmanAppTakeout p1) throws com.dcdzsoft.EduException {
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APPostmanAppTakeout bean = (com.dcdzsoft.business.ap.APPostmanAppTakeout) aop
				.bind(com.dcdzsoft.business.ap.APPostmanAppTakeout.class);
		return bean.doBusiness(p1);
	}
	
	/**
	 * 投递员注册
	 * 
	 * @param p1
	 * @param TerminalNo
	 * @return
	 * @throws com.dcdzsoft.EduException
	 */
	public static String doBusiness(InParamAPPostmanRegister p1) throws com.dcdzsoft.EduException {
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APPostmanRegister bean = (com.dcdzsoft.business.ap.APPostmanRegister) aop
				.bind(com.dcdzsoft.business.ap.APPostmanRegister.class);
		return bean.doBusiness(p1);
	}
	
	/**
	 * 投递员修改密码
	 * 
	 * @param p1
	 * @param TerminalNo
	 * @return
	 * @throws com.dcdzsoft.EduException
	 */
	public static String doBusiness(InParamAPPostmanModPwd p1) throws com.dcdzsoft.EduException {
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APPostmanModPwd  bean = (com.dcdzsoft.business.ap.APPostmanModPwd ) aop
				.bind(com.dcdzsoft.business.ap.APPostmanModPwd .class);
		return bean.doBusiness(p1);
	}
	
	/**
	 * 柜体PoxBox服务状态查询
	 * @param p1
	 * @param LockerID
	 * @return
	 *  RegisterFlag 注册标志（0-不提供注册；1-可用于注册）
	 *  LockerID    当前柜号
	 *  LockerName  柜体名称
	 *  Location    柜体位置
	 *  RegisterLimit 激活期限（天数）,即未激活或已失效用户信息保存的时间
	 *  Message 返回消息：RegisterFlag=0时，消息内容为不支持注册提醒；
	 *                 RegisterFlag=1，消息内容为Application  Guide
	 * @throws com.dcdzsoft.EduException
	 */
	public static OutParamAPLockerPoBoxQry doBusiness(InParamAPLockerPoBoxQry p1) throws com.dcdzsoft.EduException {
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APLockerPoBoxQry  bean = (com.dcdzsoft.business.ap.APLockerPoBoxQry ) aop
				.bind(com.dcdzsoft.business.ap.APLockerPoBoxQry.class);
		return bean.doBusiness(p1);
	}

	/**
	 * 用户注册
	 * @param p1 
	 *  CustomerMobile 个人客户手机     (必填)
	 *  LockerID       当前柜号         (必填，自动获取)
	 *  CustomerName   个人客户姓名     (必填)
	 *  Months         注册使用时间     (必填)
	 *  CustomerEmail  个人客户电子邮箱  (必填)
	 *  CustomerIDCard 个人客户身份证ID  (可选)
	 * @return
	 *   Status    0-注册失败；1-手机号注册成功，
	 *   Message   Status=1：注册成功，提醒激活消息；
	 * @throws com.dcdzsoft.EduException
	 */
	public static OutParamAPCustomerRegister doBusiness(InParamAPCustomerRegister p1) throws com.dcdzsoft.EduException {
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APCustomerRegister  bean = (com.dcdzsoft.business.ap.APCustomerRegister ) aop
				.bind(com.dcdzsoft.business.ap.APCustomerRegister.class);
		return bean.doBusiness(p1);
	}
	/**
	 * 用户更新虚拟POBox地址
	 * @param p1
	 *  CustomerMobile 个人客户手机  (必选，不可更改)
	 *  Keyword      验证码     (必选)
	 *  Months       使用时间
	 *  LockerID     柜号（保留）
	 *  CustomerName  姓名（保留）
	 *  CustomerEmail 电子邮箱（保留）
	 *  
	 * @return
	 *  Status       0-失败；1-成功
	 *  Address	     个人客户虚拟POBox地址
	 *  Validity	 有效期（yyyy-MM-dd）
	 * @throws com.dcdzsoft.EduException
	 */
	public static OutParamAPCustomerUpdate doBusiness(InParamAPCustomerUpdate p1) throws com.dcdzsoft.EduException {
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APCustomerUpdate  bean = (com.dcdzsoft.business.ap.APCustomerUpdate ) aop
				.bind(com.dcdzsoft.business.ap.APCustomerUpdate.class);
		return bean.doBusiness(p1);
	}
	/**
	 * 用户信息查询
	 * @param p1
	 * @param CustomerID 个人客户编号（或手机号）
	 * @return 
	 *  Status    客户激活状态（0-未激活；1-正常；2-失效）
	 *  Message   Status=1时客户虚拟POBox地址（POBoxAddress）；其他状态为提醒消息
	 *  CustomerID   客户编号
	 *  CustomerName 客户姓名
	 *  CustomerMobile 客户手机
	 *  CustomerEmail 
	 *  Validity  有效期（yyyy-MM-dd）
	 * @throws com.dcdzsoft.EduException
	 */
	public static OutParamAPCustomerInfoQry doBusiness(InParamAPCustomerInfoQry p1) throws com.dcdzsoft.EduException {
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APCustomerInfoQry  bean = (com.dcdzsoft.business.ap.APCustomerInfoQry ) aop
				.bind(com.dcdzsoft.business.ap.APCustomerInfoQry.class);
		return bean.doBusiness(p1);
	}
	/**
	 * 用户获取验证码
	 * @param p1
	 * @param CustomerMobile 客户手机号
	 * @return 0-失败；1-成功
	 * @throws com.dcdzsoft.EduException
	 */
	public static OutParamAPCustomerGetKeyMsg doBusiness(InParamAPCustomerGetKeyMsg p1) throws com.dcdzsoft.EduException {
		BusiBeanAOPBaseClass aop = BusiBeanAOPBaseClass.getInstance();
		com.dcdzsoft.business.ap.APCustomerGetKeyMsg  bean = (com.dcdzsoft.business.ap.APCustomerGetKeyMsg ) aop
				.bind(com.dcdzsoft.business.ap.APCustomerGetKeyMsg .class);
		return bean.doBusiness(p1);
	}
}
