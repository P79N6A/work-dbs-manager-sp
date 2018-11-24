package com.dcdzsoft.businessproxy;

import com.dcdzsoft.EduException;

/**
 *
 * <p>Title: 智能自助包裹柜系统</p>
 *
 * <p>Description: 监控业务 </p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: 杭州东城电子有限公司</p>
 *
 * @author wangzl
 * @version 1.0
 */
public class MonitorProxy {
	protected MonitorProxy(){}

	/**
	 * 广播异常消息
	 * @param TerminalNo
	 * @param AlertType
	 * @param AlertLevel
	 * @param BoxNo
	 * @param Remark
	 */
	public static void broadcastAlert(String TerminalNo,String AlertType,String AlertLevel,String BoxNo,String Remark)
	{
		
	}
}
