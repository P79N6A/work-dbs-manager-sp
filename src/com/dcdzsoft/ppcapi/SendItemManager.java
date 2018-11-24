package com.dcdzsoft.ppcapi;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.sql.RowSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;

import com.dcdzsoft.EduException;
import com.dcdzsoft.business.GServer;
import com.dcdzsoft.business.pt.PTTransferItemModFailReSend;
import com.dcdzsoft.business.pt.PTTransferUpdateItemDel;
import com.dcdzsoft.client.web.IMWebClientAdapter;
import com.dcdzsoft.client.web.PTWebClientAdapter;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.constant.Constant;
import com.dcdzsoft.constant.ControlParam;
import com.dcdzsoft.constant.SysDict;
import com.dcdzsoft.dto.business.InParamIMPPCQry;
import com.dcdzsoft.dto.business.InParamPTTransferItemMod;
import com.dcdzsoft.dto.business.InParamPTTransferItemQry;
import com.dcdzsoft.dto.business.InParamPTTransferItemQryCount;
import com.dcdzsoft.dto.business.InParamPTTransferItemSend;
import com.dcdzsoft.sda.db.RowSetUtils;
import com.dcdzsoft.util.DateUtils;

public class SendItemManager {
	private static Log log = org.apache.commons.logging.LogFactory
			.getLog(SendItemManager.class);

	private final static int MAX_SEND_ITEMS_NUM_DEFAULT = 10;
	/**
	 * 工作的线程数
	 */
	private int workerCount = 100;

	private ThreadPoolExecutor executor;

	private TransferToPPCThread transferToPPCThread = null;

	private SendItemManager() {
		workerCount = ApplicationConfig.getInstance().getWorkerCount();
		executor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(workerCount);
		transferToPPCThread = new TransferToPPCThread();
		transferToPPCThread.start();
	}

	private static class SingletonHolder {
		private static final SendItemManager instance = new SendItemManager();
	}

	/**
	 * 静态工厂方法，返还此类的惟一实例
	 * 
	 * @return a SendManager instance
	 */
	public static SendItemManager getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * 发送电子订单数据到相应PPC
	 * 
	 * @param sendInfo
	 * @throws EduException
	 */
	public int transferItemsToPPC(SendInfo sendInfo) throws EduException {

		String itemType = "";
		if (Constant.TRANSFER_FLAG_UPLOAD == sendInfo.TransferFlag) {// UPLOAD1-发送最终订单信息;2-发送订单状态更新信息
			// 过滤订单类型
			itemType = "1";// TRANSFER_TYPE_TAKEOUT_BY_CUSTOMER
			itemType += ",2";// TRANSFER_TYPE_PARCEL_NOT_RECEIVED
			itemType += ",3,4,5,6,7,8,9";//
		} else {
			// 过滤订单类型
			itemType = SysDict.TRANSFER_TYPE_ANNOUNCING;
		}

		int count = 0;
		while (true) {

			String wateridLists = "";
			// #start 查询同一PPC待发送订单数据
			InParamPTTransferItemQry inParam2 = new InParamPTTransferItemQry();
			inParam2.recordBegin = 0;
			inParam2.recordNum = MAX_SEND_ITEMS_NUM_DEFAULT;
			inParam2.PPCID = sendInfo.ppcId;
			inParam2.SendStatus = "0";// 0:未发送 1:发送进行中 2:发送成功 4:发送失败
			inParam2.ItemType = "";// itemType; 不做限制
			inParam2.ItemStatus = "";

			RowSet rset = PTWebClientAdapter.doBusiness(inParam2);
			while (RowSetUtils.rowsetNext(rset)) {
				long waterid = RowSetUtils.getLongValue(rset, "WaterID");
				if (StringUtils.isEmpty(wateridLists)) {
					wateridLists += "" + waterid;
				} else {
					wateridLists += "," + waterid;
				}
				count++;
			}

			if (StringUtils.isEmpty(wateridLists)) {
				break;
			}
			try {
				InParamPTTransferItemSend in = new InParamPTTransferItemSend();
				in.PackageID = wateridLists;
				in.PPCID = sendInfo.ppcId;
				PTWebClientAdapter.doBusiness(in);

			} catch (EduException e) {
				System.err.println("[transferItemsToPPC business error] = "
						+ e.getMessage());
				break;
			}
			// #end
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
		if (count > 0) {
			System.out.println("transferItemsToPPC(No.:" + sendInfo.ppcId
					+ "):" + count);
		}
		return count;
	}

	/**
	 * 发送电子订单数据到相应PPC
	 * 
	 * @param sendInfo
	 * @throws EduException
	 */
	public void sendItemsToPPC(SendInfo sendInfo) throws EduException {
		executor.submit(new SendTransferItems(sendInfo));
	}

	/**
	 * 订单数据到PPC服务器线程
	 * <p>
	 * Title: 自提柜系统
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
	private class TransferToPPCThread extends Thread {
		private String getRemarkValue(String remark, String key) {
			if (StringUtils.isEmpty(remark) || StringUtils.isEmpty(key)) {
				return "";
			}
			String value = "";
			String temp = remark;
			key = key.trim();
			if (!key.endsWith("=")) {
				key = key + "=";
			}
			int index = temp.indexOf(key);
			if (index < 0) {
				return value;
			}
			temp = temp.substring(index + key.length());
			if (temp.indexOf(";") >= 0) {
				value = temp.substring(0, temp.indexOf(";"));
			} else {
				value = temp;
			}
			value = value.trim();
			return value;
		}

		public void run() {
			try {
				Thread.sleep(1000 * 10);
				System.out
						.println("TransferToPPCThread Thread Begin --------------");

				// PPC列表
				JSONArray ppcInfoArray = new JSONArray();
				Date loadInfoDate = new Date();
				Date nextReSentDateTime = new Date();
				while (true) {
					// 只有主服务器才执行定期任务
					if (!GServer.getInstance().isMasterServer()
							|| !"1".equals(ControlParam.getInstance()
									.getTransferToPPCFlag())) {
						try {
							Thread.sleep(1000 * 60 * 10); // 休眠10分钟
						} catch (InterruptedException ex) {
						}
						continue;
					}
					Date nowDate = new Date();
					if ((nowDate.getTime() - nextReSentDateTime.getTime()) > (1000 * 60 * 10)) {
						// 修改发送失败状态，以便重新发送
						PTTransferItemModFailReSend modSendStatus = new PTTransferItemModFailReSend();
						PTWebClientAdapter.doBusiness(modSendStatus);
						nextReSentDateTime = new Date();
					}
					// #start 定期查询PPC服务器信息到内存，减少数据库查询
					if (DateUtils.getHour(nowDate) == 2
							&& (nowDate.getTime() - loadInfoDate.getTime()) > (1000 * 60 * 60 * 2)) {
						ppcInfoArray.clear();
						loadInfoDate = new Date();

						// 删除已发送订单更新状态的数据
						PTTransferUpdateItemDel updateItemDel = new PTTransferUpdateItemDel();
						int count = PTWebClientAdapter
								.doBusiness(updateItemDel);
						System.out
								.println("TransferToPPCThread Thread Del count="
										+ count);
					}
					if (ppcInfoArray.isEmpty()) {
						InParamIMPPCQry qryIn = new InParamIMPPCQry();
						try {
							RowSet rset = IMWebClientAdapter.doBusiness(qryIn);
							while (RowSetUtils.rowsetNext(rset)) {
								JSONObject ppcInfoJosn = new JSONObject();
								String PPCID = RowSetUtils.getStringValue(rset,
										"PPCID");
								String apiString = RowSetUtils.getStringValue(
										rset, "PPCServerURL");
								String userkey = RowSetUtils.getStringValue(
										rset, "PPCServerPassword");
								String username = RowSetUtils.getStringValue(
										rset, "PPCServerUsername");
								String remark = RowSetUtils.getStringValue(
										rset, "Remark");

								if (StringUtils.isEmpty(apiString)
										|| StringUtils.isEmpty(userkey)
										|| StringUtils.isEmpty(remark)) {
									continue;
								}
								// 提取PPCInterface
								String PPCInterface = getRemarkValue(remark,
										"PPCInterface");
								String UploadFlag = getRemarkValue(remark,
										"UploadFlag");// uploadFlag 2 or 3
								ppcInfoJosn.put("ppcid", PPCID);
								ppcInfoJosn.put("apiuri", apiString);
								ppcInfoJosn.put("userkey", userkey);
								ppcInfoJosn.put("username", username);
								ppcInfoJosn.put("interface", PPCInterface);
								ppcInfoJosn.put("uploadFlag", UploadFlag);

								ppcInfoArray.add(ppcInfoJosn);
								Thread.sleep(10);
							}
						} catch (Exception e) {
							System.err
									.println("[TransferToPPCThread business error(load ppc)] = "
											+ e.getMessage());
						}
					}
					// #end

					// #start 发送订单数据到PPC
					for (int i = 0; i < ppcInfoArray.size(); i++) {
						JSONObject ppcInfoJosn = ppcInfoArray.getJSONObject(i);

						// PPC server API 信息
						SendInfo sendInfo = new SendInfo();
						sendInfo.ppcId = ppcInfoJosn.getString("ppcid");
						sendInfo.apiString = ppcInfoJosn.getString("apiuri");
						sendInfo.userKey = ppcInfoJosn.getString("userkey");
						sendInfo.userName = ppcInfoJosn.getString("username");
						sendInfo.PPCInterface = ppcInfoJosn
								.getString("interface");
						sendInfo.uploadFlag = ppcInfoJosn
								.getString("uploadFlag");
						try {
							// 发送Transfer 订单
							sendInfo.TransferFlag = Constant.TRANSFER_FLAG_UPLOAD;
							transferItemsToPPC(sendInfo);

							Thread.sleep(50);

							// 发送订单状态更新信息到PPC
							if ("2".equals(sendInfo.uploadFlag)
									|| "3".equals(sendInfo.uploadFlag)) {// uploadFlag
																			// 2
																			// or
																			// 3
								sendInfo.TransferFlag = Constant.TRANSFER_FLAG_UPDATE;
								transferItemsToPPC(sendInfo);
							}
						} catch (Exception e) {
							System.err
									.println("[TransferToPPCThread business error] = "
											+ e.getMessage());
						}
					}
					// #end

					try {
						Thread.sleep(1000 * 60 * 5); // 5分钟启动一次
					} catch (InterruptedException ex) {

					}
				}
			} catch (Exception e) {
				System.err.println("[TransferToPPCThread error] = "
						+ e.getMessage());
			}
			System.out.println("TransferToPPCThread Thread end --------------");
		}
	}

	@Deprecated
	public int sendTransferItems(String ppcid, String apiString,
			String userkey, String username) throws EduException {
		int count = 0;
		// 过滤订单类型
		String itemType = "1";// TRANSFER_TYPE_TAKEOUT_BY_CUSTOMER
		itemType += ",2";// TRANSFER_TYPE_PARCEL_NOT_RECEIVED
		itemType += ",3,4,5,6,7,8,9";//

		// #start 修改发送失败状态，以便重新发送
		PTTransferItemModFailReSend modSendStatus = new PTTransferItemModFailReSend();
		PTWebClientAdapter.doBusiness(modSendStatus);
		// #end

		// #start 发送订单数据到PPC
		while (true) {
			// #查询同一PPC的待发送订单数量
			InParamPTTransferItemQryCount inParam1 = new InParamPTTransferItemQryCount();
			inParam1.PPCID = ppcid;
			inParam1.ItemType = itemType;
			inParam1.SendStatus = "0";// 0:未发送 1:发送进行中 2:发送成功 4:发送失败
			int itemsTotal = PTWebClientAdapter.doBusiness(inParam1);
			if (itemsTotal <= 0) {
				return count;
			}
			// #end

			// #start 查询同一PPC待发送订单数据
			InParamPTTransferItemQry inParam2 = new InParamPTTransferItemQry();
			inParam2.recordBegin = 0;
			inParam2.recordNum = MAX_SEND_ITEMS_NUM_DEFAULT;
			inParam2.PPCID = ppcid;
			inParam2.SendStatus = "0";// 0:未发送 1:发送进行中 2:发送成功 4:发送失败
			inParam2.ItemType = itemType;

			JSONArray itemParams = new JSONArray();

			RowSet rset = PTWebClientAdapter.doBusiness(inParam2);
			while (RowSetUtils.rowsetNext(rset)) {
				int waterID = RowSetUtils.getIntValue(rset, "WaterID");
				// 取数据信息
				JSONObject itemJson = new JSONObject();
				itemJson.put("ItemID", waterID);// ItemFlag
				itemJson.put("ItemCode",
						RowSetUtils.getStringValue(rset, "PackageID"));
				itemJson.put("ItemType",
						RowSetUtils.getIntValue(rset, "ItemType"));
				itemJson.put("AZCID",
						RowSetUtils.getStringValue(rset, "ZoneID"));
				itemParams.add(itemJson);

				// 更新发送状态
				InParamPTTransferItemMod inParamMod = new InParamPTTransferItemMod();
				inParamMod.WaterID = waterID;
				inParamMod.SendStatus = "1";// 0:未发送 1:发送进行中 2:发送成功 4:发送失败
				PTWebClientAdapter.doBusiness(inParamMod);

				count++;
			}
			// #end

			// #start 提交线程执行发送
			SendInfo sendInfo = new SendInfo();
			sendInfo.apiString = apiString;
			sendInfo.userName = username;
			sendInfo.userKey = userkey;
			sendItemsToPPC(sendInfo);
			// #end
		}
		// #end
	}

	/**
	 * 将订单状态变化发送到PPC server
	 * 
	 * @param ppcid
	 * @param apiString
	 * @param userkey
	 * @param username
	 * @throws EduException
	 */
	@Deprecated
	public int sendUpdateItems(String ppcid, String apiString, String userkey,
			String username) throws EduException {
		int count = 0;
		// 过滤订单类型
		String itemType = SysDict.TRANSFER_TYPE_ANNOUNCING;

		// #start 删除已发送订单更新状态的数据
		PTTransferUpdateItemDel updateItemDel = new PTTransferUpdateItemDel();
		PTWebClientAdapter.doBusiness(updateItemDel);
		// #end

		// #start 发订单状态更新数据到PPC
		while (true) {
			// #start 查询同一PPC的待发送订单数量
			InParamPTTransferItemQryCount inParam1 = new InParamPTTransferItemQryCount();
			inParam1.PPCID = ppcid;
			inParam1.ItemType = itemType;
			inParam1.SendStatus = "0";// 0:未发送 1:发送进行中 2:发送成功 4:发送失败
			int itemsTotal = PTWebClientAdapter.doBusiness(inParam1);
			if (itemsTotal <= 0) {
				return count;
			}
			// #end

			// #start 查询同一PPC待发送订单数据
			InParamPTTransferItemQry inParam2 = new InParamPTTransferItemQry();
			inParam2.recordBegin = 0;
			inParam2.recordNum = MAX_SEND_ITEMS_NUM_DEFAULT;
			inParam2.PPCID = ppcid;
			inParam2.SendStatus = "0";// 0:未发送 1:发送进行中 2:发送成功 4:发送失败
			inParam2.ItemType = itemType;

			JSONArray itemParams = new JSONArray();

			RowSet rset = PTWebClientAdapter.doBusiness(inParam2);
			while (RowSetUtils.rowsetNext(rset)) {
				int waterID = RowSetUtils.getIntValue(rset, "WaterID");
				// 取数据信息
				JSONObject itemJson = new JSONObject();
				itemJson.put("ItemID", waterID);// ItemFlag
				itemJson.put("ItemCode",
						RowSetUtils.getStringValue(rset, "PackageID"));
				itemJson.put("ItemStatus",
						RowSetUtils.getIntValue(rset, "ItemStatus"));
				itemJson.put("AZCID",
						RowSetUtils.getStringValue(rset, "ZoneID"));
				itemParams.add(itemJson);

				// 更新发送状态
				InParamPTTransferItemMod inParamMod = new InParamPTTransferItemMod();
				inParamMod.WaterID = waterID;
				inParamMod.SendStatus = "1";// 0:未发送 1:发送进行中 2:发送成功 4:发送失败
				PTWebClientAdapter.doBusiness(inParamMod);
				count++;
			}
			// #end

			// #start 提交线程执行发送
			SendInfo sendInfo = new SendInfo();
			sendInfo.apiString = apiString;
			sendInfo.userKey = userkey;
			sendInfo.userName = username;
			sendItemsToPPC(sendInfo);
			// #end
		}
		// #end

	}
}
