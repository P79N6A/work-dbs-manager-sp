package com.dcdzsoft.businessproxy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import com.dcdzsoft.EduException;
import com.dcdzsoft.websocket.WebSocketManager;
import com.dcdzsoft.util.StringUtils;

/**
 *
 * <p>
 * Title: 智能自助包裹柜系统
 * </p>
 *
 * <p>
 * Description: 向设备端推送的所有业务接口
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
public class PushBusinessProxy {
	private static WebSocketManager socketManager = WebSocketManager
			.getInstance();
	private static Object syncObject = new Object();

	protected PushBusinessProxy() {
	}

	/***************************** 投递相关 ********************************************************/
	/***
	 * 修改订单状态
	 *
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamPTModPackageStatus p)
			throws EduException {
		socketManager.sendRequestMsg(p, p.TerminalNo);

		return "";
	}

	/***
	 * 删除订单
	 *
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamPTDelPackage p)
			throws EduException {
		socketManager.sendRequestMsg(p, p.TerminalNo);

		return "";
	}

	/***
	 * 重置提货码
	 *
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamPTResetOpenBoxKey p)
			throws EduException {
		socketManager.sendRequestMsg(p, p.TerminalNo);

		return "";
	}

	/***
	 * 修改订单超时时间
	 *
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamPTModExpiredTime p)
			throws EduException {
		socketManager.sendRequestMsg(p, p.TerminalNo);

		return "";
	}

	/***************************** 箱体相关 ********************************************************/
	/***
	 * 修改柜体状态
	 *
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamTBTerminalModStatus p)
			throws EduException {
		socketManager.sendRequestMsg(p, p.TerminalNo);

		return "";
	}

	/***
	 * 修改柜体名称
	 *
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamTBTerminalModName p)
			throws EduException {
		socketManager.sendRequestMsg(p, p.TerminalNo);

		return "";
	}

	/***
	 * 修改箱体状态
	 *
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamTBBoxStatusMod p)
			throws EduException {
		socketManager.sendRequestMsg(p, p.TerminalNo);

		return "";
	}

	/***
	 * 箱体锁定状态维护
	 *
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamTBLockStatusMod p)
			throws EduException {
		socketManager.sendRequestMsg(p, p.TerminalNo);

		return "";
	}

	/***
	 * 箱体故障状态维护
	 *
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamTBFaultStatusMod p)
			throws EduException {
		socketManager.sendRequestMsg(p, p.TerminalNo);
		// System.out.println("OK");

		return "";
	}
	
	/***
	 * 远程开大箱
	 *
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamRMRemoteOpenBox p)
			throws EduException {
		socketManager.sendRequestMsg(p, p.TerminalNo);
		// System.out.println("OK");

		return "";
	}
	
	/**
	 * 远程开箱
	 * @param p
	 * @return
	 * @throws EduException
	 */
	public static String doBusiness(com.dcdzsoft.dto.business.InParamTBOpenBox4Manager p)
			throws EduException {
		socketManager.sendRequestMsg(p, p.TerminalNo);
		// System.out.println("OK");

		return "";
	}
	
	/***
	 * 用户APP开箱
	 *
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamAPCustomerAppOpenBox p)
			throws EduException {
		socketManager.sendRequestMsg(p, p.TerminalNo);

		return "";
	}
	
	/***
	 * 投递员APP取回
	 *
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(com.dcdzsoft.dto.business.InParamAPPostmanAppTakeout p)
			throws EduException {
		socketManager.sendRequestMsg(p, p.TerminalNo);

		return "";
	}

	/***************************** 投递员相关 ********************************************************/
	/***
	 * 推送投递员增加
	 *
	 * @param p
	 * @param TerminalNoStr
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamPMPushPostmanAdd p,
			String TerminalNoStr) throws EduException {

		if(StringUtils.isEmpty(p.InputMobileFlag))
			p.InputMobileFlag = "0"; //寄件
		
		if(StringUtils.isEmpty(p.PostmanType))
			p.PostmanType = "1"; //投递员
		
		///////////////////////////////////////////////
		ArrayList<String> terminalNoList = getTerminalNoList(TerminalNoStr);
		if (terminalNoList.size() > 0) {
			p.TerminalNoStr = "";
			synchronized (syncObject) {
				for (int j = 0; j < terminalNoList.size(); j++) {
					try {
						socketManager.sendRequestMsg(p, terminalNoList.get(j));
					} catch (Exception e) {
					}
				}
			}
		}
		
		return "";
	}

	/***
	 * 推送投递员修改
	 *
	 * @param p
	 * @param TerminalNoStr
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamPMPushPostmanMod p,
			String TerminalNoStr) throws EduException {
		
		if(StringUtils.isEmpty(p.PostmanType))
			p.PostmanType = "1"; //投递员
		
		///////////////////////////////////////////////
		ArrayList<String> terminalNoList = getTerminalNoList(TerminalNoStr);
		if (terminalNoList.size() > 0) {
			p.TerminalNoStr = "";
			synchronized (syncObject) {
				for (int j = 0; j < terminalNoList.size(); j++) {
					try {
						socketManager.sendRequestMsg(p, terminalNoList.get(j));
					} catch (Exception e) {
					}
				}
			}
		}
		
		return "";
	}

	/***
	 * 推送投递员删除
	 *
	 * @param p
	 * @param TerminalNoStr
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamPMPushPostmanDel p,
			String TerminalNoStr) throws EduException {
		
		// /////////////////////////////////////////////
		ArrayList<String> terminalNoList = getTerminalNoList(TerminalNoStr);
		if (terminalNoList.size() > 0) {
			p.TerminalNoStr = "";
			synchronized (syncObject) {
				for (int j = 0; j < terminalNoList.size(); j++) {
					try {
						socketManager.sendRequestMsg(p, terminalNoList.get(j));
					} catch (Exception e) {
					}
				}
			}
		}
		
		return "";
	}

	/***************************** 管理员相关 ********************************************************/
	/***
	 * 推送现场管理员
	 *
	 * @param p
	 * @param TerminalNoStr
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamOPPushSpotOperator p,
			String TerminalNoStr) throws EduException {
		ArrayList<String> terminalNoList = getTerminalNoList(TerminalNoStr);
		if (terminalNoList.size() > 0) {
			p.TerminalNoStr = "";
			synchronized (syncObject) {
				for (int j = 0; j < terminalNoList.size(); j++) {
					try {
						socketManager.sendRequestMsg(p, terminalNoList.get(j));
					} catch (Exception e) {
						if(terminalNoList.size() == 1)
							throw new EduException(e.getMessage());
					}
				}
			}
		}

		return "";

	}

	/***
	 * 修改现场管理员密码
	 *
	 * @param p
	 * @param TerminalNoStr
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamOPModSpotOperPwd p,
			String TerminalNoStr) throws EduException {
		ArrayList<String> terminalNoList = getTerminalNoList(TerminalNoStr);
		if (terminalNoList.size() > 0) {
			p.TerminalNoStr = "";
			synchronized (syncObject) {
				for (int j = 0; j < terminalNoList.size(); j++) {
					try {
						socketManager.sendRequestMsg(p, terminalNoList.get(j));
					} catch (Exception e) {
						if(terminalNoList.size() == 1)
							throw new EduException(e.getMessage());
					}
				}
			}
		}

		return "";

	}

	/***
	 * 删除现场管理员
	 *
	 * @param p
	 * @param TerminalNoStr
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamOPDelSpotOperator p,
			String TerminalNoStr) throws EduException {
		ArrayList<String> terminalNoList = getTerminalNoList(TerminalNoStr);
		if (terminalNoList.size() > 0) {
			p.TerminalNoStr = "";
			synchronized (syncObject) {
				for (int j = 0; j < terminalNoList.size(); j++) {
					try {
						socketManager.sendRequestMsg(p, terminalNoList.get(j));
					} catch (Exception e) {
						if(terminalNoList.size() == 1)
							throw new EduException(e.getMessage());
					}
				}
			}
		}

		return "";

	}

	/***************************** 系统相关 ********************************************************/
	/***
	 * 修改终端设备控制参数
	 *
	 * @param p
	 * @param TerminalNoStr
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamPATerminalCtrlParamMod p,
			String TerminalNoStr) throws EduException {

		ArrayList<String> terminalNoList = getTerminalNoList(TerminalNoStr);
		if (terminalNoList.size() > 0) {
			p.TerminalNoStr = "";
			synchronized (syncObject) {
				for (int j = 0; j < terminalNoList.size(); j++) {
					try {
						socketManager.sendRequestMsg(p, terminalNoList.get(j));
					} catch (Exception e) {
					}
				}
			}
		}

		return "";
	}

	/***
	 * 获取箱体一致性比对信息
	 *
	 * @param p
	 * @param TerminalNo
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamMBGetBoxDectionResult p)
			throws EduException {
		socketManager.sendRequestMsg(p, p.TerminalNo);

		return "";
	}

	/***
	 * 获取设备系统日志
	 *
	 * @param p
	 * @param TerminalNo
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamMBGetDeviceSysLog p)
			throws EduException {
		socketManager.sendRequestMsg(p, p.TerminalNo);

		return "";
	}

	/***
	 * 获取设备历史投递记录信息
	 *
	 * @param p
	 * @param TerminalNo
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamMBGetHistoryPackage p)
			throws EduException {
		socketManager.sendRequestMsg(p, p.TerminalNo);

		return "";
	}

	/***
	 * 获取设备在箱包裹信息
	 *
	 * @param p
	 * @param TerminalNo
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamMBGetInboxPackage p)
			throws EduException {
		socketManager.sendRequestMsg(p, p.TerminalNo);

		return "";
	}

	/***
	 * 获取管理员日志
	 *
	 * @param p
	 * @param TerminalNo
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamMBGetManagerLog p)
			throws EduException {
		socketManager.sendRequestMsg(p, p.TerminalNo);

		return "";
	}

	/***
	 * 获取设备端控制参数
	 *
	 * @param p
	 * @param TerminalNo
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamMBGetControlParam p)
			throws EduException {
		socketManager.sendRequestMsg(p, p.TerminalNo);

		return "";
	}

	/***
	 * 获取上传失败数据
	 *
	 * @param p
	 * @param TerminalNo
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamMBGetUpFailureData p)
			throws EduException {
		socketManager.sendRequestMsg(p, p.TerminalNo);

		return "";
	}

	/***
	 * 发送设备升级通知
	 *
	 * @param p
	 * @param TerminalNoStr
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamMBSendUpgradeInform p,
			String TerminalNoStr) throws EduException {
		ArrayList<String> terminalNoList = getTerminalNoList(TerminalNoStr);
		if (terminalNoList.size() > 0) {
			p.TerminalNoStr = "";
			synchronized (syncObject) {
				for (int j = 0; j < terminalNoList.size(); j++) {
					try {
						socketManager.sendRequestMsg(p, terminalNoList.get(j));
					} catch (Exception e) {
					}
				}
			}
		}

		return "";

	}

        /***
         * 发送重新上传对账文件通知
         *
         * @param p
         * @param TerminalNoStr
         * @return
         * @throws Exception
         */
        public static String doBusiness(
                        com.dcdzsoft.dto.business.InParamMBUploadInboxPack p,
                        String TerminalNoStr) throws EduException {
                ArrayList<String> terminalNoList = getTerminalNoList(TerminalNoStr);
                if (terminalNoList.size() > 0) {
                        p.TerminalNoStr = "";
                        synchronized (syncObject) {
                                for (int j = 0; j < terminalNoList.size(); j++) {
                                        try {
                                                socketManager.sendRequestMsg(p, terminalNoList.get(j));
                                        } catch (Exception e) {
                                        }
                                }
                        }
                }

                return "";

        }


	/***
	 * 修改签到密码
	 *
	 * @param p
	 * @param TerminalNoStr
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamSMModSignPwd p,
			String TerminalNoStr) throws EduException {
		ArrayList<String> terminalNoList = getTerminalNoList(TerminalNoStr);
		if (terminalNoList.size() > 0) {
			synchronized (syncObject) {
				for (int j = 0; j < terminalNoList.size(); j++) {
					try {
						socketManager.sendRequestMsg(p, terminalNoList.get(j));
					} catch (Exception e) {
					}
				}
			}
		}

		return "";
	}

        /***
         * 修改欢迎词
         *
         * @param p
         * @param TerminalNoStr
         * @return
         * @throws Exception
         */
        public static String doBusiness(
                        com.dcdzsoft.dto.business.InParamSMModWelcomeInfo p,
                        String TerminalNoStr) throws EduException {
                ArrayList<String> terminalNoList = getTerminalNoList(TerminalNoStr);
                if (terminalNoList.size() > 0) {
                        synchronized (syncObject) {
                                for (int j = 0; j < terminalNoList.size(); j++) {
                                        try {
                                                socketManager.sendRequestMsg(p, terminalNoList.get(j));
                                        } catch (Exception e) {
                                        }
                                }
                        }
                }

                return "";
        }

        /***
         * 推送LED消息
         *
         * @param p
         * @param TerminalNoStr
         * @return
         * @throws Exception
         */
        public static String doBusiness(
                        com.dcdzsoft.dto.business.InParamMBPushTerminalLedMsg p,
                        String TerminalNoStr) throws EduException {
                ArrayList<String> terminalNoList = getTerminalNoList(TerminalNoStr);
                if (terminalNoList.size() > 0) {
                        synchronized (syncObject) {
                                for (int j = 0; j < terminalNoList.size(); j++) {
                                        try {
                                                socketManager.sendRequestMsg(p, terminalNoList.get(j));
                                        } catch (Exception e) {
                                        }
                                }
                        }
                }

                return "";
        }

	/***
	 * 修改设备连接到终端的服务器设置
	 *
	 * @param p
	 * @param TerminalNoStr
	 * @return
	 * @throws Exception
	 */
	public static String doBusiness(
			com.dcdzsoft.dto.business.InParamSMModServerCfg p,
			String TerminalNoStr) throws EduException {
		ArrayList<String> terminalNoList = getTerminalNoList(TerminalNoStr);
		if (terminalNoList.size() > 0) {
			synchronized (syncObject) {
				for (int j = 0; j < terminalNoList.size(); j++) {
					try {
						socketManager.sendRequestMsg(p, terminalNoList.get(j));
					} catch (Exception e) {
					}
				}
			}
		}

		return "";
	}

	private static ArrayList<String> getTerminalNoList(String TerminalNoStr) {
		ArrayList<String> terminalNoList = new ArrayList<String>();
		if (StringUtils.isNotEmpty(TerminalNoStr)) {
			String[] strs = TerminalNoStr.split(",");
			for (int i = 0; i < strs.length; i++) {
				terminalNoList.add(strs[i]);
			}
		} else {
			Set<String> set = WebSocketManager.getInstance()
					.getOnlineTerminalNoList();
			for (Iterator<String> it = set.iterator(); it.hasNext();) {
				terminalNoList.add(it.next());
			}
		}

		return terminalNoList;
	}

}
