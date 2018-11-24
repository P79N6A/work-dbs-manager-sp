package com.dcdzsoft.dao.common;

import javax.sql.RowSet;

import com.dcdzsoft.EduException;
import com.dcdzsoft.config.ApplicationConfig;
import com.dcdzsoft.dto.business.OutParamItemDetail;
import com.dcdzsoft.dto.function.DMHistoryItem;
import com.dcdzsoft.dto.function.DMItemLifeCycle;
import com.dcdzsoft.dto.function.IMBusinessPartner;
import com.dcdzsoft.dto.function.IMCustomer;
import com.dcdzsoft.dto.function.IMServiceRate;
import com.dcdzsoft.dto.function.MBPwdShortMsg;
import com.dcdzsoft.dto.function.OPOperOnline;
import com.dcdzsoft.dto.function.OPOperatorLog;
import com.dcdzsoft.dto.function.PMPostman;
import com.dcdzsoft.dto.function.PTDeliverHistory;
import com.dcdzsoft.dto.function.PTDeliverItemTransfer;
import com.dcdzsoft.dto.function.PTInBoxPackage;
import com.dcdzsoft.dto.function.PTItemLifeCycle;
import com.dcdzsoft.dto.function.PTReadyPackage;
import com.dcdzsoft.dto.function.PYServiceBillWater;
import com.dcdzsoft.sms.SMSInfo;
import com.dcdzsoft.util.LockUtil.MyLock;

public interface CommonDAO {
    /**
     * 判断操作员是否在线
     * @param OperID String
     * @return OPOperOnline
     * @throws EduException
     */
    public OPOperOnline isOnline(String OperID) throws EduException;

    /**
     * 记录操作员日志
     * @param log OPOperatorLog
     * @return long
     * @throws EduException
     */
    public long addOperatorLog(OPOperatorLog log) throws EduException;


    /**
     * 修改操作员在线信息
     * @param operOnline OPOperOnline
     * @return boolean
     * @throws EduException
     */
    public boolean modifyOperOnline(OPOperOnline operOnline) throws EduException;

    /**
     * 生成管理员编号
     * @return String
     * @throws EduException
     */
    public String getInnerUserID() throws EduException;

    /**
     * 操作员菜单信息查询
     * @param OperID String
     * @param ModuleID String
     * @return RowSet
     * @throws EduException
     */
    public RowSet operMenuQry(String OperID, String ModuleID) throws EduException;

    /**
      * 检查投递员的柜体权限
      * @param postman PMPostman
      * @param terminalno String
      * @throws EduException
      */
     public void checkManTerminalRight(PMPostman postman,String terminalno) throws EduException;
     
     /**
      * 警报消息入库
      * @param TerminalNo
      * @param AlertType
      * @param AlertLevel
      * @param BoxNo
      * @param Remark
      * @return ReportWaterID
      * @throws EduException
      */
     public long insertAlert(String TerminalNo,String AlertType,String AlertLevel,String BoxNo,String Remark) throws EduException;
     
     /**
      * 添加投递订单生命周期记录
      * @param itemLifeCycle PTItemLifeCycle
      * @throws EduException
      */
     public void addItemLifeCycle(PTItemLifeCycle itemLifeCycle) throws EduException;
     public void addItemLifeCycle(int action, PTItemLifeCycle itemLifeCycle,String[] desc) throws EduException;
     /**
      * 添加寄件订单生命周期记录
      * @param itemLifeCycle DMItemLifeCycle
      * @throws EduException
      */
     public void addItemLifeCycle(DMItemLifeCycle itemLifeCycle) throws EduException;
     /**
      * 批量包裹单号解析
      * @param packageIDs “<ItemCode>#<Createtime>~<ItemCode>#<Createtime>”
      * @return {{<ItemCode>,<Createtime>},{<ItemCode>,<Createtime>},...}
      * @throws EduException
      */
     public String[][] decodeBatchPackageID(String packageIDs) throws EduException;
     
     /** 获取ID
      * @param type   ID类型
      * @param from    
      * @param to     
      * @return nextOrderID
      * @throws EduException
      */
     public String getNextOrderID(String type, String from, String to) throws EduException;
     /** 获取ID
      * @param type     String ID类型
      * @param from     String
      * @param to       String
      * @param isUpdate boolean
      * @return OrderID
      * @throws EduException
       */
      public String getNextOrderID(String type, String from, String to, boolean isUpdate) throws EduException;
     /**
      * 检查订单打印标识
      * @param itemsCodeAndTime String[][] {{PackageID，createTime},{PackageID，createTime}}
      * @param itemStatus       String
      * @param flagMandatory    int
      * @param codeMandatory    int
      * @param printErrorCode   String
      * @return isPrint : true需要打印，false 不需要打印
      * */
     public boolean checkPrintFlag(String[][] itemsCodeAndTime, String itemStatus, int flagMandatory, int codeMandatory,String printErrorCode) throws EduException;
     
     /**
      * 更新分拣区域计数器
      * @param zoneID 
      * @param counterType
      * @throws EduException
      */
     public int updateZoneCounter(String zoneID, String counterType) throws EduException;
     /**
      * 更新自提柜计数器
      * @param LockerID 
      * @param counterType
      * @throws EduException
      */
     public int updateLockerCounter(String LockerID, String counterType) throws EduException;

     /**
      * 更新计数器：serviceCounter
      * @param ServiceID
      * @param ExtraServiceID
      * @return CntValue
      * @throws EduException
      */
     public int updateServiceCounter(String ServiceID, String ExtraServiceID) throws EduException;
     
     /**
      * 更新柜体计数器:CustomerCounter
      * @param lockerID
      * @return CntValue
      */
     public int updateLockerCustomerCounter(String lockerID) throws EduException;
    /**
     * 获取公司可用空箱
     * @param CompanyID 
     * @param LockerID
     * @param BoxType
     * @param boxNum boxNum >0 查询指定数量；<=0
     * @return boxNoSet
     * @throws EduException
     */
    public java.util.Set<String> getCompFreeBoxSet(String CompanyID, String TerminalNo, String BoxType, int boxNum) throws EduException;
    /**
     * 获取一个公司可用空箱
     * @param CompanyID 
     * @param TerminalNo
     * @param BoxType
     * @return boxNo
     * @throws EduException
     */
    public String getOneCompFreeBox(String CompanyID, String TerminalNo, String BoxType) throws EduException;

     /**
      * 申请柜体锁（对象）
      * @param terminalNo  String
      * @return termianlLock MyLock
      * @throws EduException
      */
     public MyLock allocTermianlLock(String terminalNo) throws EduException;
     /**
      * 释放占用的箱
      * @param terminalNo
      * @throws EduException
      */
     public void releaseTermianlLocks(String terminalNo) throws EduException;
     
     /**
      * 获取查询限制条件语句 (主运营方权限) 
      * @param OperID
      * @param ZoneID
      * @throws EduException
      */
     public String getQueryMasterLimitSQL(String OperID,String ZoneID) throws EduException;
     /**
      * 获取查询限制条件语句(包裹服务商权限)  
      * @param OperID
      * @param ZoneID
      * @throws EduException
      */
     public String getQueryCompanyLimitSQL(String OperID,String ZoneID) throws EduException;
     /**
      * 获取查询限制条件语句  (分拣区域权限)
      * @param OperID
      * @param ZoneID
      * @throws EduException
      */
     public String getQueryZoneLimitSQL(String OperID,String ZoneID) throws EduException;
     
     /**
      * 获取查询Locker限制条件语句  
      * @param OperID
      * @param ZoneID
      * @return
      * @throws EduException
      */
     public String getQueryLockerLimitSQL(String OperID,String ZoneID) throws EduException;
     /**
      * 获取查询Locker-Box限制条件语句  
      * @param OperID
      * @param ZoneID
      * @return
      * @throws EduException
      */
     public String getQueryLockerBoxLimitSQL(String OperID,String ZoneID) throws EduException;
     /**
      * 获取逾期时间  
      * @param CompanyID
      * @return
      * @throws EduException
      */
     public java.sql.Timestamp getExpiredTime(String CompanyID) throws EduException;
     /**
      * 获取催领时间  
      * @param CompanyID
      * @param isFirst 第一次催领间隔+1天
      * @return
      * @throws EduException
      */
     public java.sql.Timestamp getReminderTime(String CompanyID, boolean isFirst) throws EduException;
     /**
      * 获取逾期天数  
      * @param CompanyID
      * @return
      * @throws EduException
      */
     public int getExpiredDays(String CompanyID) throws EduException;
     /**
      * 获取逾期天数  
      * @param StoredTime
      * @param ExpiredTime
      * @return
      * @throws EduException
      */
     public int getExpiredDays(java.sql.Timestamp StoredTime, java.sql.Timestamp ExpiredTime) throws EduException;
     /**
      * 获取催领间隔天数  
      * @param CompanyID
      * @return
      * @throws EduException
      */
     public int getReminderDays(String CompanyID) throws EduException;
     /**
      * 获取投递在箱的天数(不包括投递当天，当天返回：0)  
      * @param StoredTime
      * @return
      * @throws EduException
      */
     public int getDroppedDays(java.sql.Timestamp StoredTime) throws EduException;
     /**
      * 是否为有效手机号  
      * @param input
      * @return
      */
     public boolean isPhoneNumber(String input);
     /**
      * 验证邮箱  
      * @param input
      * @return
      */
     public  boolean isEmail(String input);
     /**
      * 修改在箱订单的逾期时间  
      * @param itemsCodeAndTime
      * @param TerminalNo
      * @param expiredTime
      * @return
      * @throws EduException
      */
     public int modExpireTime(String OperID, String[][] itemsCodeAndTime,String TerminalNo,java.sql.Timestamp expiredTime) throws EduException;
     /**
      * 修改在箱订单的催领时间  
      * @param itemsCodeAndTime
      * @param TerminalNo
      * @param reminderDateNew
      * @return
      * @throws EduException
      */
     public int modReminderTime	(String OperID, String[][] itemsCodeAndTime,String TerminalNo,java.sql.Date reminderDateNew) throws EduException;
     
     /**
      * 寄件订单校验码  
      * @param serialNumberStr  8位数字
      * @return
      */
     public int getChecksumE1(String serialNumberStr);
     /**
      * 获取E1单号
      * @param service
      * @return
      * @throws EduException
      */
     public String getE1Code(IMServiceRate service) throws EduException;
     
     /**
      * 获取投递员权限
      * 投递员权限1~32位（低）
      * 1：投递权限（0 or 1）；
      * 2：回收权限（0 or 1）；
      * 3：揽件权限（0 or 1）；
      * 4：直投权限（0 or 1）
      * @param postman
      * @return
      */
     public int getPostmanRight(PMPostman postman);
     /** 
      * 字符串转换成日期 
      * @param str 
      * @return date 
      */ 
      public  java.sql.Date strToDate(String str) ;
      
     
      /**
       * 在寄件订单创建时预付费
       * @param partner
       * @param amount
       * @param itemcode
       * @param serverid
       * @param detail
       * @return tradeWaterID
       * @throws EduException
       */
      public String doBusinessPartnerPrePay(IMBusinessPartner partner, double amount, String itemcode, String serverid, String detail) throws EduException;
      
      /**
       * 生成支付账单：1）AZC收件，2）直投，3）揽件员取件（？？？？？）
       * @param tradeWaterID
       * @param bPartnerID
       * @param itemcode
       * @return 
       * @throws EduException
       */
      public void doBusinessPartnerPay(String tradeWaterID, String bPartnerID, String itemcode) throws EduException;
      /**
       * 取消订单时预付金额退回
       * @param businessPartner
       * @param tradeWaterID
       * @param itemcode
       * @throws EduException
       */
      public void doBusinessPartnerRefund(IMBusinessPartner businessPartner, String tradeWaterID, String itemcode) throws EduException;
      /**
       * Business Partner充值
       * @param partner
       * @param amount
       * @param tradeWaterID
       * @param detail : detail for the top up reason
       * @return tradeWaterID
       * @throws EduException
       */
      public String doBusinessPartnerTopUp(IMBusinessPartner partner, double amount, String tradeWaterID, String detail) throws EduException;
      
      /**
       * 订单数据发回PPC
       * @param itemTransfer
       * @return
       * @throws EduException
       */
      //public int toDoTransferSend(PTDeliverItemTransfer itemTransfer) throws EduException;
      /**
       * 添加订单状态更新信息到发送队列
       * @param itemTransfer
       * @return
       * @throws EduException
       */
      //public int addItemStatusUpdate(PTDeliverItemTransfer itemTransfer) throws EduException;
      
      public int addItemStatusUpdate(String ppcId,String nextStatus, PTReadyPackage readyPack, String transferType) throws EduException;
      public int addItemStatusUpdate(String ppcId,String nextStatus, PTInBoxPackage inboxPack, String transferType) throws EduException;
      public int addItemStatusUpdate(String ppcId,String nextStatus, PTDeliverHistory historyPack, String transferType) throws EduException;
      public int addItemStatusUpdate(String ppcId,String nextStatus, DMHistoryItem historyItem, String transferType) throws EduException;
      /**
       * 地址信息换行由html的'<br>'改为'\n'
       * @param addressHtml 
       * @return
       */
      public String getAddressHtmlToText(String addressHtml);
      /**
       * 检查用户名是否已存在
       * @param username
       * @return  true-表示可用，false-表示已存在
       * @throws EduException
       */
      public boolean checkUserName(String username) throws EduException;
      
      /**
       * 获取订单记录详情
       * @param ItemCode 订单编号
       * @param QryType  默认通过订单号查询 
       * @param BeginDate 开始日期
       * @param EndDate   结束日期
       * @param level
       * @return
       * @throws EduException
       */
      public OutParamItemDetail getItemRecordDetail(String ItemCode, String QryType, java.sql.Date BeginDate, java.sql.Date EndDate, int level) throws EduException;
      /**
       * 通过邮件发送告警信息
       * @param reportWaterID 告警流水号(0表示未知流水号，发送当前柜体状态)
       * @param terminalNo      
       * @param mode   :1-只发送邮件；2-只发送短信；其他-发送短信和邮件         
       * @return 0 发送成功 -1 柜体不存在；-2 手机和邮箱都不存在；-3邮箱不存在；-4手机不存在;-5 邮件发送失败；-6 短信发送失败
       * @throws EduException
       */
      public int doSendAlarmInfo(long reportWaterID, String lockerid, int mode) throws EduException;
      /**
       * 过滤含html标签的字符串
       * @param inputString
       * @return
       */
      public String Html2Text(String inputString);
      /**
       *  激活或更新POBox地址
       * @param customer
       * @param lockerid
       * @param months
       * @return
       * @throws EduException
       */
      public IMCustomer activeOrUpdatePOBoxAddress(IMCustomer customer, String lockerid, int months) throws EduException;
      /**
       * 获取Logo的本地文件路径
       * @param filename
       * @return
       * @throws EduException
       */
      public String getCompanyLogoLocalPath(String filename)throws EduException;
      /**
       * 订单号不分大小写，统一保存为大写
       * @param code
       * @return
       */
      public  String normalizeItemCode(String code) throws EduException;
      /**
       * 取件短信、催领短信预定发送，要求如果多条短信发送到同一手机，每条短信间隔发送
       * @param smsInfo
       * @param shortMsg
       * @param sysDateTime
       * @throws EduException
       */
      public  void scheduleSendSMS (SMSInfo smsInfo,MBPwdShortMsg shortMsg, java.sql.Timestamp sysDateTime) throws EduException;
      /**
       * 包裹取走以后，更新短消息状态：包括订单状态和短信发送状态
       * @param PackageID
       * @param TerminalNo
       * @param StoredTime
       * @param PackageStatus
       * @param sysDateTime
       * @throws EduException
       */
      public  void updatePwdSMSStauts (String PackageID,String TerminalNo, java.sql.Timestamp StoredTime, 
              String PackageStatus,
              java.sql.Timestamp sysDateTime) throws EduException;
      /**
       * 包裹取走以后，更新短消息状态：包括订单状态和短信发送状态
       * @param shortMsg
       * @param PackageStatus
       * @param sysDateTime
       * @throws EduException
       */
      public  void updatePwdSMSStauts (MBPwdShortMsg shortMsg, String PackageStatus,
              java.sql.Timestamp sysDateTime) throws EduException;
}


