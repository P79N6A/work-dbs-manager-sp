/***************************************************************************
  *	数据库初始化
  *
  *	创建日期：2014-2-6
  *	创建人：郑晓勇
  *
  *
  *	************************************************************************/

BEGIN TRANSACTION;
/************************************************
  * 1.系统字典信息
  *
  * 字典类别编号规则
  *    1-2：功能模块编号
  *    3-5：顺序号
  *
  * 11：系统配置管理
  * 12：基础信息管理
  * 13：管理账户管理
  * 15：运营信息管理
  * 21：设备信息管理
  * 31：投递员管理
  * 33: 投递业务管理
  * 34: 寄件业务管理
  * 41: 支付信息管理
  * 51: 数据同步管理
  *

  *	************************************************/
TRUNCATE TABLE PADictionary;

/*=======================系统管理(11001-110999)=========================*/
INSERT INTO	PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(11001 , '模块编号'	, '11' , '系统服务管理');
INSERT INTO	PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(11001 , '模块编号'	, '12' , '基础信息管理');
INSERT INTO	PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(11001 , '模块编号'	, '13' , '管理账户管理');
INSERT INTO	PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(11001 , '模块编号'	, '15' , '运营信息管理');
INSERT INTO	PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(11001 , '模块编号'	, '21' , '设备信息管理');
INSERT INTO	PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(11001 , '模块编号'	, '31' , '投递员管理');
INSERT INTO	PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(11001 , '模块编号'	, '33' , '投递业务管理');
INSERT INTO	PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(11001 , '模块编号'	, '34' , '寄件业务管理');
INSERT INTO	PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(11001 , '模块编号'	, '41' , '支付信息管理');
INSERT INTO	PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(11001 , '模块编号'	, '51' , '数据同步管理');


INSERT INTO	PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(11011 , '外设接口'	, '1' , 'COM1');
INSERT INTO	PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(11011 , '外设接口'	, '2' , 'COM2');
INSERT INTO	PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(11011 , '外设接口'	, '3' , 'COM3');
INSERT INTO	PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(11011 , '外设接口'	, '4' , 'COM4');
INSERT INTO	PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(11011 , '外设接口'	, '5' , 'COM5');
INSERT INTO	PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(11011 , '外设接口'	, '6' , 'COM6');
INSERT INTO	PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(11011 , '外设接口'	, '7' , 'COM7');
INSERT INTO	PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(11011 , '外设接口'	, '8' , 'COM8');
INSERT INTO	PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(11011 , '外设接口'	, '9' , 'COM9');

/*=======================系统参数管理(12001-12999)=========================*/



/*=======================操作账户管理(13001-13999);=========================*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13001,'管理员类型','1','System Admin');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13001,'管理员类型','2','Master Admin');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13001,'管理员类型','3','Local Admin');/*现场管理员*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13001,'管理员类型','4','Operator');
/*INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13001,'管理员类型','11','Master Admin');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13001,'管理员类型','12','Company Admin');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13001,'管理员类型','13','Zone Admin');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13001,'管理员类型','14','Zone Operator');*/


INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13011,'用户状态','0','Normal');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13011,'用户状态','1','Invalid');/*失效Logout注销*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13011,'用户状态','2','Inactive');/*注册未激活*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(13021,'用户在线状态 ','0','Off-line');/*离线*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(13021,'用户在线状态 ','1','On-line');/*在线*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13050,'记录日志标志','0','不记录');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13050,'记录日志标志','1','手动记录');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13050,'记录日志标志','5','自动记录');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13050,'记录日志标志','6','暂停记录');

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13051,'功能开通标志','1','开通');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13051,'功能开通标志','2','升级中');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13051,'功能开通标志','3','暂停');


/*
  *  操作人员类型：
  
      13001 '管理员类别'    1~30   系统运营维护管理员
      31001,'邮递员类型'  81~90
*/
/**/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13005,'操作人员类型','0','System');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13005,'操作人员类型','1','Super Admin');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13005,'操作人员类型','2','Master Admin');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13005,'操作人员类型','3','Locker Admin');/*现场管理员*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13005,'操作人员类型','4','Operator');
/*INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13005,'操作人员类型','11','Master Admin');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13005,'操作人员类型','12','Company Admin');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13005,'操作人员类型','13','Zone Admin');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13005,'操作人员类型','14','Zone Operator');*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13005,'操作人员类型','70','individual customer');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13005,'操作人员类型','71','business partner');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13005,'操作人员类型','72','eCommerce partner');

/*邮递员类型*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13005,'操作人员类型','81','Postman');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13005,'操作人员类型','82','DAD');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13005,'操作人员类型','83','DAD-B');/*BusinessPartner DAD*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13005,'操作人员类型','84','Return');/*Return agent*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13005,'操作人员类型','85','Transport');/*操作人员类型-转运*/

/*
  *  登录用户类型：
  
*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13006,'登录用户类型','1','Super Admin');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13006,'登录用户类型','2','Admin');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13006,'登录用户类型','3','Service Owner');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13006,'登录用户类型','4','Business Partner');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13006,'登录用户类型','5','eCommerce Partner');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13006,'登录用户类型','6','Postman');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(13006,'登录用户类型','7','Individual Customer');






/*=======================运营信息管理(15001-15999)=========================*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15001,'短消息发送状态','0','Unsent');/*未发送*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15001,'短消息发送状态','1','Sending');/*发送进行中*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15001,'短消息发送状态','2','Success');/*发送成功*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15001,'短消息发送状态','4','Failure');/*发送失败*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15011,'报警种类 ','11','Vibration');/*震动*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15011,'报警种类 ','12','Pry');/*防撬*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15011,'报警种类 ','13','Power-fail');/*电源不足*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15011,'报警种类 ','14','Power-recovery');/*电源恢复*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15011,'报警种类 ','16','Drive plate anomaly');/*驱动板异常*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15011,'报警种类 ','21','High temperature');/*高温异常*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15011,'报警种类 ','23','Low temperature');/*低温异常*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15011,'报警种类 ','31','Locker fault');/*柜体故障*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15011,'报警种类 ','33','Card reader fault');/*读卡器故障*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15011,'报警种类 ','34','Scanner fault');/*条码扫描仪故障*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15011,'报警种类 ','35','Printer fault');/*打印机故障*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15011,'报警种类 ','41','Box fault');/*箱体故障*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15011,'报警种类 ','43','Abnormal opening'); /*异常开箱---主页面检测*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15011,'报警种类 ','45','Re opening');/*取件重新开箱*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15011,'报警种类 ','91','Off-Line');/*网络异常*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15011,'报警种类 ','92','Too many handles');/*句柄过多*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15011,'报警种类 ','95','Open the box failure(out)');/*大物卡箱*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15011,'报警种类 ','96','Open the box failure(in)');/*投递开箱失败*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15013,'报警级别 ','A','Slight');/*轻微-slight*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15013,'报警级别 ','B','Common');/*一般-common*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15013,'报警级别 ','C','Urgency');/*紧急-urgency*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15013,'报警级别 ','D','Critical');/*严重-critical*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15021,'在线状态 ','0','Off-line');/*离线*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15021,'在线状态 ','1','On-line');/*在线*/


INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15042,'监控照片类型(投递取件)','1','DropByStaff');/*投递员*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15042,'监控照片类型(投递取件)','2','TakeoutByCustomer');/*用户取件*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15042,'监控照片类型(投递取件)','3','TakeoutByPostman');/*投递员逾期取件*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(15042,'监控照片类型(投递取件)','4','TakeoutByManager');/*管理员取件*/



/*=======================自提柜管理(21001-21999)=========================*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21001,'柜详细状态','0','Normal');/*正常*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21001,'柜详细状态','2','Fault');/*故障*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21001,'柜详细状态','3','Off-line');/*故障-离线*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21001,'柜详细状态','4','Power-fail');/*故障-掉电*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21001,'柜详细状态','5','BoxFault');/*故障-存在箱门故障*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21001,'柜详细状态','9','Unregistered');/*未启用,未注册*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21002,'柜故障状态','0','Normal');/*正常*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21002,'柜故障状态','2','Fault');/*故障*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(21003,'柜体类型','0','elocker包裹柜');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(21003,'柜体类型','1','counter投递柜台');

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21005,'柜注册标志','0','Unregistered');/*未注册*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21005,'柜注册标志','1','Successful');/*注册成功*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21005,'柜注册标志','2','Failure');/*注册失败*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21008,'箱锁定状态','0','Normal');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21008,'箱锁定状态','1','Locked');/*故障*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21009,'箱故障状态','0','Normal');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21009,'箱故障状态','1','Fault');/*故障*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21011,'箱状态','0','Normal');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21011,'箱状态','1','Locked');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21011,'箱状态','2','Fault');/*故障*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21011,'箱状态','3','Fault&Locked');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21011,'箱状态','8','Inuse');/*使用*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21013,'箱使用状态','0','Free');/*空闲*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21013,'箱使用状态','1','Schedule');/*预定*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21013,'箱使用状态','2','Occupy');/*占用*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21013,'箱使用状态','5','Occupy-Err');/*异常占用*/
/*INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21013,'箱使用状态','3','订单取消');*/
/*INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21013,'箱使用状态','4','订单超时');*/
/*INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21013,'箱使用状态','5','Occupy');异常占用*/
/*insert into PADictionary(DictTypeID,DictTypeName,DictID,DictName)   values(21013,'箱使用状态','9','未知');*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21015,'箱物品状态','0','Nothing');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21015,'箱物品状态','1','Something');
/*insert into PADictionary(DictTypeID,DictTypeName,DictID,DictName)   values(21015,'箱物品状态','9','未知');*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21017,'箱门状态','0','Off');/*关*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21017,'箱门状态','1','On');/*开*/
/*insert into PADictionary(DictTypeID,DictTypeName,DictID,DictName)   values(21017,'箱门状态','9','未知');*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21018,'PoBox标识','0','No');/**/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21018,'PoBox标识','1','Yes');/**/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21031,'箱类型','0','Small');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21031,'箱类型','1','Medium');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21031,'箱类型','2','Large');
/*INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21031,'箱类型','3','超大');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21031,'箱类型','4','超小');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21031,'箱类型','6','快餐');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21031,'箱类型','7','生鲜');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21031,'箱类型','8','广告箱');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21031,'箱类型','9','主控箱');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21031,'箱类型','10','寄件箱');*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21032,'沙特邮政箱类型','0','Small');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21032,'沙特邮政箱类型','1','Medium');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(21032,'沙特邮政箱类型','2','Large');

/*=======================基础信息管理(25001-25999)=========================*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25001,'包裹服务商类型','0','Master');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25001,'包裹服务商类型','1','Logistics Company');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25001,'包裹服务商类型','2','Government Dept');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25001,'包裹服务商类型','3','Business Partner');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25001,'包裹服务商类型','4','Collection Dept');
/*INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25001,'包裹服务商类型','99','未分类');*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25002,'服务商类型','1','Logistics Company');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25002,'服务商类型','2','Government Dept');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25002,'服务商类型','3','Business Partner');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25002,'服务商类型','4','Collection Dept');

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25011,'主运营标识','0','No');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25011,'主运营标识','1','Yes');

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25012,'反馈标识','0','No');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25012,'反馈标识','1','Yes');

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25013,'激活标识','0','No');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25013,'激活标识','1','Yes');



INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25014,'强制打印开关','0','No');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25014,'强制打印开关','1','Yes');


INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25015,'个人客户状态','0','Inactive');/*注册未激活*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25015,'个人客户状态','1','Normal');/*正常*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25015,'个人客户状态','2','Invalid');/*失效*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25021,'服务标识','0','No');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25021,'服务标识','1','Yes');

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25022,'授信标识','0','No');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25022,'授信标识','1','Yes');

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25041,'计数器类型','1','RECounter');/*RECEIVED*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25041,'计数器类型','2','TRCounter');/*TRANSFER*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25041,'计数器类型','3','RDCounter');/*REDISTRIBUTE*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25041,'计数器类型','4','COCounter');/*collect*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25041,'计数器类型','5','MICounter');/*missing*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25041,'计数器类型','6','LOCounter');/*lose*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25041,'计数器类型','21','DOCounter');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25041,'计数器类型','22','DODCounter');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25041,'计数器类型','23','ROCounter');/*RETURNED*/      
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25041,'计数器类型','24','CUCounter');/*customer*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25041,'计数器类型','41','SMCounter'); 



INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25050,'网关&模板类型','1','TakeoutPwdMsg');               /*取件密码消息*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25050,'网关&模板类型','2','ReturnedMsg');                 /*逾期取回消息*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25050,'网关&模板类型','3','ReminderMsg');                 /*催领消息*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25050,'网关&模板类型','4','PickupMsg');                   /*取件消息*/
/*INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25050,'网关&模板类型','5','重发投递消息');*/            /*重发投递消息*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25050,'网关&模板类型','8','PostmanPwdMsg');               /*投递员密码消息*/
/*INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25050,'网关&模板类型','9','UrgentPwdMsg');*/            /*紧急取件短信*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25050,'网关&模板类型','10','RegisterPwdMsg');             /*注册验证码消息*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25050,'网关&模板类型','51','SMSGateway');                 /*短信网关*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25050,'网关&模板类型','52','SupportEmail');               /*运维邮箱账户*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25051,'网关类型','51','SMSGateway');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25051,'网关类型','52','SupportEmail');/**/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25052,'消息模板类型','1','TakeoutPwdMsg');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25052,'消息模板类型','2','ReturnedMsg');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25052,'消息模板类型','3','ReminderMsg');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25052,'消息模板类型','4','PickupMsg');
/*INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25052,'消息模板类型','5','重发投递短信');*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25052,'消息模板类型','8','PostmanPwdMsg');
/*INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25052,'消息模板类型','9','紧急取件短信');*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25052,'消息模板类型','10','RegisterPwdMsg');

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25052,'消息模板类型','21','NewPOBoxAddressMsg');/*新的POBox地址短信*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25052,'消息模板类型','22','ReminderActiveMsg');/*提醒用户激活短信*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25052,'消息模板类型','23','ReminderExtendMsg');/*提醒用户延期短信*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25052,'消息模板类型','24','VerificationCodeMsg');/*用户更新的身份验证短信*/


INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25055,'API类型','1','Api for ppc server');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25055,'API类型','2','Api for ecommerce partner website');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25055,'API类型','3','Api for POS system');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25055,'API类型','4','Api for collection app');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25055,'API类型','5','Api for business partner website/app');

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25056,'API用户类型','0','External system');
/*INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25056,'API用户类型','1','ppc server');*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25056,'API用户类型','2','Ecommerce partner website');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25056,'API用户类型','3','POS system');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25056,'API用户类型','4','Collection app');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(25056,'API用户类型','5','Business partner website/app');

/*=======================邮递员管理(31001-31999)=========================*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(31001,'邮递员类型','81','Postman');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(31001,'邮递员类型','82','DAD');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(31001,'邮递员类型','83','DAD-B');/*BusinessPartner*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(31001,'邮递员类型','84','Return');/*Return agent*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(31001,'邮递员类型','85','Transport');/*邮递员类型-转运*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(31001,'邮递员类型','86','DAD-SP');/*SP corporate customer*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(31021,'邮递员状态','0','Normal');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(31021,'邮递员状态','1','Invalid');/*Logout注销，失效*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(31021,'邮递员状态','2','Inactive');/*注册未激活*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(31023,'邮递员权限标识','0','No');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(31023,'邮递员权限标识','1','Yes');

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(31025,'邮递员权限','1','Drop');/*投递权限*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(31025,'邮递员权限','2','Return');/*回收权限*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(31025,'邮递员权限','3','Collect');/*揽件权限*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(31025,'邮递员权限','4','DAD');/*直投权限*/

/***********************控制邮递员输入手机标志  0:寄件(需手工输入手机) 1:转入(不需要输入手机,回显就可以) 2:联网(回显手机号，可修改)*/
/*INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(31031,'转入标志','0','寄件');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(31031,'转入标志','1','转入');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(31031,'转入标志','2','联网');*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(31041,'认证标志','0','获取动态密码');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(31041,'认证标志','1','认证');

/*
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(31051,'证件类型','1','身份证');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(31051,'证件类型','2','学生证');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(31051,'证件类型','3','驾驶证');
*/

/*=======================投递信息管理(33001-33999)=========================*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33001,'包裹状态','0','Normal');/*在箱正常*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33001,'包裹状态','1','Locked');/*在箱锁定*/
/*insert into PADictionary(DictTypeID,DictTypeName,DictID,DictName)   values(33001,'包裹状态','2','取消');*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33001,'包裹状态','3','Expired');/*超时*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33001,'包裹状态','5','TakeoutByPostman');/*投递员取回*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33001,'包裹状态','6','Expired&Return');/*逾期回收*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33001,'包裹状态','7','Abnormal&Return');/*异常回收*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33001,'包裹状态','8','Takeout');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33001,'包裹状态','9','TakeoutByManager');/*管理员取回*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','0','Listed');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','1','Received');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','2','Assigned');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','3','Scheduled');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','4','Intransit-out');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','5','Dropped');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','6','D-Dropped');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','7','TakeOut');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','8','Expired');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','9','M-Expired');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','10','Intransit-back');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','11','Returned');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','12','Missing');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','13','Transfer');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','14','Parcel Lose');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','15','TransferList');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','16','Package-listed');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','17','Package-out');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','20','D-Create');/*直投订单创建*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'寄件订单状态','54','Inward-Received');

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33004,'投递订单状态04-Pane1','0','Listed');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33004,'投递订单状态04-Pane1','1','Received');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33004,'投递订单状态04-Pane1','15','TransferList');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33004,'投递订单状态04-Pane1','16','Package-listed');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33005,'投递订单状态05-Pane2','2','Assigned');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33005,'投递订单状态05-Pane2','3','Scheduled');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33006,'投递订单状态06-Pane3','4','Intransit-out');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33006,'投递订单状态06-Pane3','10','Intransit-back');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33006,'投递订单状态06-Pane3','17','Package-out');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33007,'投递订单状态07-Pane4','8','Expired');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33007,'投递订单状态07-Pane4','9','M-Expired');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33007,'投递订单状态07-Pane4','5','Dropped');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33007,'投递订单状态07-Pane4','6','D-Dropped');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33008,'投递订单状态08-Pane5','11','Returned');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33008,'投递订单状态08-Pane5','12','Missing');

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33009,'投递订单状态09-Pane7','4','Intransit-out');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33009,'投递订单状态09-Pane7','17','Package-out');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33009,'投递订单状态09-Pane7','8','Expired');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33009,'投递订单状态09-Pane7','9','M-Expired');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33009,'投递订单状态09-Pane7','5','Dropped');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33009,'投递订单状态09-Pane7','6','D-Dropped');

/*INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','7','TakeOut');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','13','Transfer');
*/


INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33011,'在箱包裹状态','0','Normal');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33011,'在箱包裹状态','1','Locked');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33011,'在箱包裹状态','3','Expired');

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(33021,'取件方式','0','密码');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(33021,'取件方式','1','二维码');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(33021,'取件方式','2','刷卡');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)  VALUES(33021,'取件方式','9','未知');

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33031,'支付标志','0','不需要支付');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33031,'支付标志','1','需要支付');

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33041,'寄存标志','0','投递包裹');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33041,'寄存标志','1','寄存包裹');

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33051,'开箱类型','1','投递开箱');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33051,'开箱类型','2','取消投递开箱');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33051,'开箱类型','3','设置箱门故障开箱');


INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33063,'报告打印类型','1','Report1');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33063,'报告打印类型','2','Report2');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33063,'报告打印类型','3','Report3');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33063,'报告打印类型','4','Report4');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33063,'报告打印类型','5','Report5');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33063,'报告打印类型','6','Report6');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33063,'报告打印类型','7','Report7');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33063,'报告打印类型','8','Report8');

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33071,'订单执行状态','0','Announcing');/*订单处理中*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33071,'订单执行状态','1','Pickup by Customer');/*用户正常取件*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33071,'订单执行状态','2','Pickup by DAD postman');/*直投包裹回收*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33071,'订单执行状态','3','ErrorInfo');/*异常信息退单*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33071,'订单执行状态','4','Expired');/*逾期未取退单*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33071,'订单执行状态','5','ReDisttibuted');/*逾期未取重投递*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33071,'订单执行状态','6','Missing');/*包裹丢失退单*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33071,'订单执行状态','9','Pickup by manager');/*管理员取件*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33073,'订单编号类型','1','ReceiveOrderID');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33073,'订单编号类型','2','TransferOrderID');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33073,'订单编号类型','3','ReDisttibuteOrderID');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33073,'订单编号类型','4','ReturnOrderID');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33073,'订单编号类型','5','DropOrderID');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33073,'订单编号类型','6','DirectDropOrderID');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33073,'订单编号类型','7','CollectionOrderID');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33073,'订单编号类型','8','MissingOrderID');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33073,'订单编号类型','9','LoseOrderID');

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33074,'转移订单类型','0','Announcing');/*分拣投递中*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33074,'转移订单类型','1','Takeout by customer');/*用户正常取件*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33074,'转移订单类型','2','Did not receive a physical parcel');/*未收到物理包裹*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33074,'转移订单类型','3','ErrorInfo');/*异常信息退单*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33074,'转移订单类型','4','Expired');/*逾期未取退单*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33074,'转移订单类型','7','M-Expired');/*逾期未取退单*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33074,'转移订单类型','6','Missing');/*包裹丢失退单*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33074,'转移订单类型','8','SendMail');/*寄件订单*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33074,'转移订单类型','9','ReturnMail');/*退件订单*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33074,'转移订单类型','10','Dropped');/**/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33074,'转移订单类型','11','D-Dropped');/**/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33074,'转移订单类型','12','Takeout by DAD');/**/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33074,'转移订单类型','13','AddItem');/**/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33074,'转移订单类型','51','SubmitItem');/**/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33076,'转移订单发送状态','0','Unsent');/*未发送*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33076,'转移订单发送状态','1','Sending');/*发送中*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33076,'转移订单发送状态','2','Success');/*发送成功*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33076,'转移订单发送状态','4','Failure');/*发送失败*/



INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33081,'直投标志','0','Drop');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33081,'直投标志','1','DAD');



/*=======================寄件信息管理(34001-34999)=========================*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(34003,'寄件订单状态','50','Created');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(34003,'寄件订单状态','51','To Be Collected');
/*INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(34003,'寄件订单状态','52','Scheduled');*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(34003,'寄件订单状态','53','Intransit-Collected');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(34003,'寄件订单状态','54','Inward-Received');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(34003,'寄件订单状态','55','Transfer');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(34003,'寄件订单状态','56','To Be Dropped');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(34003,'寄件订单状态','57','To Be D-Dropped');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(34003,'寄件订单状态','58','Canceled');
/*INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(34003,'寄件订单状态','59','Return');*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(34003,'寄件订单状态','60','Delivered for Collection');

/*
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(34011,'揽件方式','1','揽件员上门取件');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(34011,'揽件方式','2','揽件员DropBox取件');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(34011,'揽件方式','9','其他方式取件');
*/

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(34021,'揽件服务标识','0','NO');/*非上门服务*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(34021,'揽件服务标识','1','YES');/*上门揽件*/



/*=======================支付信息管理(41001-41999)=========================*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(41001,'账单类型','1','TopUp');/*充值账单*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(41001,'账单类型','2','Consumed');/*消费账单*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(41001,'账单类型','3','Ordered');/*预付账单*/

/*=======================同步数据管理(51001-51999)=========================*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(51001,'上传标志','0','No');/*未上传*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(51001,'上传标志','1','Yes');/*已上传*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(51001,'上传标志','2','Failed');/*上传失败*/

/*insert into PADictionary(DictTypeID,DictTypeName,DictID,DictName)   values(51031,'数据同步业务类型','0','未上传');
insert into PADictionary(DictTypeID,DictTypeName,DictID,DictName)   values(51031,'数据同步业务类型','1','已上传');*/


INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(55001,'求助类型','11','取消投递后误关箱门');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(55001,'求助类型','12','确认回收后误关箱门');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(55001,'求助类型','13','确认回收后箱门未弹开');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(55001,'求助类型','21','取件后误关箱门');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(55001,'求助类型','22','取件后箱门未弹开');

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(55011,'求助状态','1','申请开箱密码');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(55011,'求助状态','3','准备开箱');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(55011,'求助状态','5','开箱成功');

INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(56011,'邮件类型','0','柜体使用统计邮件');
/*INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(56011,'柜体类型','1','离线报警邮件');*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(57011,'发送权限','0','管理员AZC区域');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(57011,'发送权限','1','所有AZC区域');
/************************************************
  *	2.控制参数信息
  *
  *		系统参数（小于10000为系统参数）
  *	************************************************/
TRUNCATE TABLE PAControlParam;

/*=======================系统管理(11001-11999)=========================*/
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11001,'公司信息','companyName','Hangzhou Dongcheng Electronic Co.','name of company');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11001,'公司信息','companyTel','0571-86468639','tel');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11001,'公司信息','companyAddress','#1291 Hanghai Rd. Jiubao Town, Hangzhou, ZheJiang, China,310019','address of company');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11001,'公司信息','companyWWW','www.hzdongcheng.com','web');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11001,'公司信息','companyEmail','zhengxiaoyong@hzdongcheng.com','Email');
/*INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11001,'公司信息','serviceTel','0571-86468639','tel');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11001,'公司信息','msgTel','0571-86468639','tel');
*/

INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11003,'Master','elockerPortal','http://60.191.125.50:8090/DBSPortal/','web');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11003,'Master','elockerServiceTel','0571-86468639','tel');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11003,'Master','customerAddressChange','yes','Customer Address Change:yes or no');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11003,'Master','poBoxMonthlyRate','0','POBox Monthly Rate');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11003,'Master','poBoxApplicationGuide','','POBox Application Guide');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11003,'Master','e1ValidityPeriodDays','7','validity period days later, the E1 was canceled.');

/*INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	values(11005,'业务处理模式','buinessModel','STDBusinessProxy','业务处理模式(STD 标准版 XIANGYOU 湘邮 JINGDONG 京东');*/
/*INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11006,'网络通讯模式','networkModel','0','网络通讯模式(0 长连接 SJJK4HzyouzhengProxy ');*/

/*
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11011,'第二服务器设置','serverIP','60.191.125.50','服务器IP地址');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11011,'第二服务器设置','serverPort','8090','服务器端口');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	values(11011,'通讯服务器设置','serverSSL','false','通讯是否采用SSL加密 false:不采用 true:采用');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	values(11011,'通讯服务器设置','serverConnectTimeout','60','超时时间(秒)');*/

INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11013,'应用服务器设置','masterServerID','elocker_server','主应用服务器ID');

/*
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11031,'屏幕设置','screenProtectTime','7','屏保启用时间（单位：分钟）');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11031,'屏幕设置','screensoundFlag','1','语音提示（0关闭；1开启）');
*/

INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','locale','en','locale:en, ar');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','countryCode','SA','countryCode:SA-Saudi, CH-China');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','sysRunModel','2','sysRunModel:2-master');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','interfacePackage','ProxyDcdzsoft','interfacePackage ProxyDcdzsoft');
/*INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','mbHost','splocker.cloudapp.net','mbHost');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','mbPort','8080','mbPort');*/
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','mbUri','http://splocker.cloudapp.net:8080/','mbUri');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','imgServerUri','http://splocker.cloudapp.net:8080/','imgServerUri');
/*INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','ftpHost','','ftpHost');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','ftpPort','','ftpPort');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','ftpUser','','ftpUser');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','ftpPasswd','','ftpPasswd');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','dbsAipsIp','','dbsAipsIp');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','dbsAipsPort','','ftpPort');
*/
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','emailServerHost','smtpout.secureserver.net','elocker system email: emailServerHost');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','emailServerPort','25','elocker system email:emailServerPort');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','emailUser','shamnad@elaammal.com','elocker system email:emailUser');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','emailPwd','123456','elocker system email:emailPwd');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','emailAddress','shamnad@elaammal.com','elocker system email:emailAddress');
/*INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','maintenanceEmail','','elocker system email:maintenanceEmail');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','legalDepartmentEmail','','elocker system email:legalDepartmentEmail');
*/

INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','sendShortMsg','MsgProxyWaveCell','SMS Gateway Setting:sendShortMsg');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','smsServerIp','','SMS Gateway Setting:smsServerIp');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','smsServerPort','','SMS Gateway Setting:smsServerPort');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','gatewayUser','elaammal1','SMS Gateway Setting:MsgProxyWaveCellAccountId');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','gatewayPwd','parcel13579','SMS Gateway Setting:MsgProxyWaveCell.gatewayPwd');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','gatewayField1','elaammal1_std','SMS Gateway Setting:MsgProxyWaveCell.SubAccountId');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','gatewayField2','SaudiPost','SMS Gateway Setting:MsgProxyWaveCell.Source');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','gatewayField3','','SMS Gateway Setting:');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','gatewayField4','','SMS Gateway Setting:');

INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','allowClientIPs','127.0.0.1','webservice setting:allowClientIPs');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11041,'ApplicationConfig','appKeyElockerWebProtal','dcdzSoft','webservice setting:appKeyElockerWebProtal');


/*
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','remoteAppeal','0','远程求助 0:未开通 1:开通');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','swipingCardLogin','0','投递员刷卡登陆 0:未开通 1:开通');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','postmanRegister','0','投递员注册 0:未开通 1:开通');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','postmanBindingCard','0','投递员一卡通绑定 0:未开通 1:开通');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','terminalRegister','0','柜体注册 0:未开通 1:开通');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','spotManager','0','现场管理功能 0:未开通 1:开通');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','takePicture','0','拍照 0:未开通 1:开通');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','ofBureauEmpty','1','投递局号是否允许为空 0:不允许 1:允许');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','longConnMonitor','1','长连接监控 0:不开通 1:开通');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','sendToPartner','0','发送反馈给服务商 0:不开通 1:开通');
*/
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','mobileBlackList','0','手机黑名单 0:未开通 1:开通');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','mobileFormatCheck','0','手机格式检查 0:未开通 1:开通');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','sendReminderSMS','1','发送催领短信 0:不发送 1:发送');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','sendReSendSMS','1','重发短信开关：0:未开通 1:开通');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','sendReturnSMS','0','发送回收通知短信开关：0:未开通 1:开通');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','messageTempletLoadMode','1','消息模板加载方式 0:vm文件加载 1:数据库加载');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','sendBPPwdSMS','1','发送BP密码短信 0:未开通 1:开通');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','sendOperatorPwdSMS','1','发送操作员密码短信 0:未开通 1:开通');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','sendTopupMsgSMS','1','发送充值账单短信：0:未开通 1:开通');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','sendBalanceSMS','1','发送余额提醒短信：0:未开通 1:开通');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','sendLockerAlarmSMS','1','发送Locker告警短信：0:未开通 1:开通');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','modifyMobileFromLocker','0','在终端修改取件手机：0:未开通 1:开通');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','renewBoxAndLockedOldBox','1','重新分箱时锁定原先分配的箱子：0:未开通 1:开通');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','sendSMSTimeGapInMs','300000','短信发送时间间隔(ms)：');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11081,'功能开通控制','printfSMSContent','0','打印短信内容：0:未开通 1:开通');


INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11082,'定期同步控制','loadIntervalInMs','3600000','定期加载时间间隔(ms), 1 minute=60000;5 minute=300000 ;1 hour=3600000');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11082,'定期同步控制','loadFunctionToMemory','1','定期加载日志标志到内存：0:未开通 1:开通');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11082,'定期同步控制','loadCtrlParamToMemory','1','定期加载控制参数到内存：0:未开通 1:开通');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11082,'定期同步控制','loadApplicationConfigToMemory','1','定期加载应用配置到内存：0:未开通 1:开通');

/*
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11091,'接口认证','appclientId','dcdzsoft','客户端Id');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11091,'接口认证','appKey','1QAZ2WSX','应用的app_key');

INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(11095,'推送业务控制','pushserviceCtrl','','推送业务控制');
*/


/*=======================系统参数管理(12001-12999);=========================*/
/*--------------------- 1:事件通知(通过事件获取数据)  2:轮询(应答数据需不断轮询)*/
/*INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12001,'驱动板端口','deviceBoardPort','COM1','驱动板端口');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12001,'驱动板端口','deviceBoardModel','2','控制模式(1:事件通知 2:轮询)');


INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12002,'读卡器端口','cardReaderPort','COM2','读卡器端口');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12002,'读卡器端口','cardReaderModel','1','控制模式(1:事件通知 2:轮询)');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12002,'读卡器端口','cardReaderNeedFlag','1','是否需要读卡器(0:不需要 1:需要)');


INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12003,'打印端口','printPort','COM3','打印端口');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12003,'打印端口','printModel','3','控制模式(1:事件通知 2:轮询 3:按需同步)');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12003,'打印端口','printNeedFlag','0','是否需要打印机(0:不需要 1:需要)');

INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12004,'条码端口','barCodePort','COM4','条码端口');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12004,'条码端口','barCodeModel','1','控制模式(1:事件通知 2:轮询)');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12004,'条码端口','barNeedFlag','1','是否需要条码扫描(0:不需要 1:需要)');

INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12005,'短信端口','shortMsgPort','COM5','短信端口');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12005,'短信端口','shortMsgModel','3','控制模式(1:事件通知 2:轮询 3:按需同步)');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12005,'短信端口','shortMsgNeedFlag','0','是否需要短信猫(0:不需要 1:需要)');

INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12006,'LED条屏端口','ledScreenPort','COM6','LED条屏端口');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12006,'LED条屏端口','ledSreenModel','3','控制模式(1:事件通知 2:轮询 3:按需同步)');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12006,'LED条屏端口','ledSreenNeedFlag','0','是否需要LED条屏(0:不需要 1:需要)');

INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12007,'POS机端口','posPort','COM7','POS机端口');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12007,'POS机端口','posModel','4','控制模式(1:事件通知 2:轮询 4:按需异步)');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12007,'POS机端口','posNeedFlag','0','是否需要POS机(0:不需要 1:需要)');

INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12008,'投币机端口','coinPort','COM8','投币机端口');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12008,'投币机端口','coinModel','1','控制模式(1:事件通知 2:轮询)');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12008,'投币机端口','coinNeedFlag','0','是否需要投币机(0:不需要 1:需要)');

INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12009,'银行卡端口','bankCardPort','COM9','银行卡端口');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12009,'银行卡端口','bankCardModel','1','控制模式(1:事件通知 2:轮询)');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12009,'银行卡端口','bankCardNeedFlag','0','是否需要银行卡(0:不需要 1:需要)');


INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12010,'密码键盘端口','keyboardPort','COM10','密码键盘端口');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12010,'密码键盘端口','keyboardModel','1','控制模式(1:事件通知 2:轮询)');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12010,'密码键盘端口','keyboardNeedFlag','0','是否需要密码键盘(0:不需要 1:需要)');

INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12013,'DVR端口','dvrPort','COM11','DVR端口');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12013,'DVR端口','dvrModel','1','控制模式(1:事件通知 2:轮询)');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12013,'DVR端口','dvrNeedFlag','0','是否需要DVR(0:不需要 1:需要)');

INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12021,'摄像端口','cameraPort','USB1','摄像端口');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12021,'摄像端口','cameraModel','4','控制模式(1:事件通知 2:轮询 4:按需异步)');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12021,'摄像端口','cameraNeedFlag','0','是否需要摄像头(0:不需要 1:需要)');
*/

/*---------------------基础信息---------------------------------*/

INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12135,'个人客户管理','customerIDMode','0','客户号模式，0-固定，1-动态更新（随注册的柜号变化而变化）');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12135,'个人客户管理','customerIDFormat','1','1-Locker.LockerID+"-"+Locker.Counter；2-Locker.LockerName+"-"+Locker.Counter. defualt=1');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12135,'个人客户管理','customerExtendLimitDays','30','设置延期期限（天），customer需要在期限内完成延期');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12135,'个人客户管理','customerExpiredKeepDays','15','设置系统保留已失效的customer账户的天数');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12135,'个人客户管理','customerPOBoxBandBode','1','POBox地址绑定模式:1-一个手机一个地址（默认）;2-一个身份证号一个POBox地址');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12135,'个人客户管理','customerPOBoxRateFree','1','Customer使用POBox是否免费：1-free,2-Not free');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(12135,'个人客户管理','poBoxAddressFirstLineFormat','1','1-AZC.Code+","+Customer.Code；2-AZC.Name+","+Customer.Code. defualt=1');

/*=======================管理账户管理(13001-13999);=========================*/
/*INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(13001,'现场后台安全管理','systemHTPWD','0000','前台转后台密码');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(13001,'现场后台安全管理','spotAdminID','165629','现场管理员编号');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(13001,'现场后台安全管理','spotAdminPWD','52c69e3a57331081823331c4e69d3f2e','现场管理员密码');
*/

/*=======================运营信息管理(15001-15999)=========================*/
/*INSERT INTO PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark) VALUES(15001,'运营网点根节点','headerDepartmentID','100000','运营网点根节点');*/

INSERT INTO PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark) VALUES(15101,'APIMonitorSetting','monitorEmail','zhengxiaoyong@hzdongcheng.com','API Monitor Stuff  Email');
INSERT INTO PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark) VALUES(15101,'APIMonitorSetting','sendSMSFailReportCnt','1','The number of sending failed');
INSERT INTO PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark) VALUES(15101,'APIMonitorSetting','sendPPCFailReportCnt','1','The number of sending failed');

/*=======================箱体信息管理(21001-21999)=========================*/
/*INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(21001,'柜体检测标志','articleInspectFlag','1','物品检测标志(0:关闭 1:打开)');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(21001,'柜体检测标志','doorInspectFlag','1','箱门检测标志(0:关闭 1:打开)');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(21001,'柜体检测标志','powerInspectFlag','0','电源检测标志(0:关闭 1:打开)');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(21001,'柜体检测标志','shockInspectFlag','0','震动防撬检测标志(0:关闭 1:打开)');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(21001,'柜体检测标志','boxWarnTime','0','箱门异常报警时间参数(0:关闭 30秒)');

INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(21002,'副柜默认高度','deskDefaultHeight','1900','副柜默认高度');

INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(21011,'箱体类型控制','existSuperLargeBox','0','存在超大箱体 0:不存在 1:存在');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(21011,'箱体类型控制','existSameTypeBox','0','存在生鲜配送箱体 0:不存在 1:存在');
*/

INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(21021,'柜体应用开关','lockerStationMapQry','1','柜体位置查询：0-提供所有，1-提供已注册柜体，2-提供正常在线柜体');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(21021,'柜体应用开关','lockerListQry','1','柜体列表查询：0-提供所有，1-提供已注册柜体，2-提供正常在线柜体');

/*=======================投递员管理(31001-31999)=========================*/
/*INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(31001,'投递员身份验证模式','postmanCheckSource','1','验证源 0:本地 1:网络');*/
/*INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	values(31001,'投递员身份验证模式','postmanCheckModel','1','认证方式 0:获取动态码 1:身份认证');*/
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(31005,'投递员柜体权限验证模式','manTerminalRightCheck','3','0:不需要 1:验证公司 2:验证本人 3:验证AZC');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(31005,'投递员柜体权限验证模式','manDADTerminalRightCheck','0','0:不需要 1:使用manTerminalRightCheck验证');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(31006,'投递员格口权限验证模式','manBoxRightCheck','1','0:不需要 1:验证公司 2:验证本人');

/*
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(31011,'密码相关','postmanMD5Flag','1','投递员密码使用MD5加密(0:不使用 1:使用)' );
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(31011,'密码相关','managerMD5Flag','1','管理员使用MD5加密(0:不使用 1:使用)' );
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(31011,'密码相关','passwdCheckModel','0','密码验证方式(0:普通MD5 1:京东MD5 2:杭州邮政)' );
*/

/*INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(31021,'投递员注册','registerModel','1','投递员注册模式(0:输入验证码 1:短信获取验证码)' );*/
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(31021,'投递员注册','regSentPwdSms','1','注册发送短信密码(0:不发送 1:发送)');

/*INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(31031,'投递公司查询','companyQryModel','1','投递公司查询模式(1:同级别或以下 2:同级别或以下或上级)' );
*/

/*=======================投递信息管理(33001-33999)=========================*/
/*
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33001,'投递设置','orderDeliverySource','0','投递订单来源 0:无 1:网络 2:本地');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33001,'投递设置','ordersNotInList','1','列表外订单处理方式 0:不允许投递 1:允许投递');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33001,'投递设置','orderNeedNetCheck','0','订单需要网络验证 0:不需要 1:需要');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33001,'投递设置','orderFormatHandleWay','0','订单号格式处理方式 0:无 1:京东 2:杭州邮政');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33001,'投递设置','cancelDeliveryOpenDoor','0','取消投递开门 0:不弹开箱门 1:弹开箱门');
*/
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33001,'投递设置','dadDropE1Item','0','DAD投递E1订单 0:不允许 1:允许投递');


INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33003,'投递业务设置','assignLockerMode','0','分配柜体的模式：0-不允许修改已分配的柜体，1-允许修改已分配的柜体');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33003,'投递业务设置','assignBoxSizeMode','1','分配箱大小的模式：0-不允许修改已分配的箱大小；1-允许修改已分配的箱大小');

INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33011,'回收设置','recoveryTimeout','7','回收超时时间（单位：天数）');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33011,'回收设置','reminderDays','2','催领天数（单位：天数）');

INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33011,'回收设置','recoverySource','1','回收来源 1从服务器下载；2从本地库验证(终端开关)');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33011,'回收设置','recoveryScope','2','回收范围 1投递员；2 AZC (后台开关)');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33011,'回收设置','recoveryPower','1','回收力度 1强制回收(需要先回收后才能投递)，2非强制回收，沙特版本未提供该功能(终端开关)');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33011,'回收设置','recoveryInputPwd','1','回收输入密码 0 不输入，1 输入(终端开关)');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33011,'回收设置','recoveryMode','1','回收模式 0 可以回收柜子中的所有包裹 1只能回收同类型的包裹(普通包裹或DAD包裹)(后台开关)');


/*INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33021,'取件密码设置','takeOutPWDSource','0','取件密码生成来源 0:本地不生成 1:本地生成');*/
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33021,'取件密码设置','takeOutPwdLen','6','取件密码长度');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33021,'取件密码设置','takeOutPwdFormat','0','取件密码组成格式 0:数字 1:字母 2:字母数字组合');


INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33031,'取件设置','autoLockOrder','1','过期自动锁定订单 0:不锁定 1:锁定');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33031,'取件设置','takeOutCheckType','3','取件验证组合方式 1.单号+密码，2.手机号+密码，3.手机号后4位+密码 4.单号或手机号+密码，5.会员卡+提货码 8.单号后4位+密码 9.单号后4位或手机号+密码');

/*INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33031,'取件设置','takeOutCheckModel','1','取件验证模式 1.本地验证，2.网络验证');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33031,'取件设置','takeOutPwdMD5Type','0','取件密码MD5加密类型 0:标准 1:京东' );
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33031,'取件设置','refuseCloseDoor','3','拒关箱门（单位：次数）');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33031,'取件设置','displayTerm','0','显示取件条款 0:不显示 1:显示');
*/
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33031,'取件设置','takeOutExpiredItemLimit','1','客户取逾期包裹的限制: 1-不允许取人工逾期的包裹 2-不允许取自动逾期的包裹 3-不允许取逾期包裹，其他-无限制');


/*
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33041,'取件密码出错设置','firstWrongPwdCount','3','初次取件密码出错次数上限');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33041,'取件密码出错设置','secondWrongPwdCount','3','再次取件密码出错次数上限');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33041,'取件密码出错设置','temporaryLockTime ','1','暂时锁定时间限制(分钟)');
*/

INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33051,'TransferToPPCSetting','transferToPPCFlag','0','开关：0-off，1-on');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33051,'TransferToPPCSetting','transferResendTimes','3','自动发送次数');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(33051,'TransferToPPCSetting','transferItemRetainDays ','7','发送队列中订单保留天数');


/*=======================支付信息管理(41001-41999)=========================*/

/*=======================同步数据管理(51001-51999)=========================*/
/*--异常箱体信息比对(定时发送箱体状态，出现异常应该由现场管理员处理掉)
---异常在箱信息比对(数据恢复，数据重新上传)*/
/*INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(51001,'异常信息处理','inBoxInfoCompare','1','异常在箱信息比对，1需要比对，0不需要');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(51001,'异常信息处理','boxInfoCompare','1','异常箱体信息比对，1需要比对，0不需要');
*/
/*=======================远程求助管理(55001-55999)=========================*/
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(55001,'求助联系人电话','contactTel','18658178571','');
INSERT INTO	PAControlParam(CtrlTypeID,CtrlTypeName,KeyString,DefaultValue,Remark)	VALUES(55005,'RemoteAppeal','appealTimeout','30','minute');

/************************************************
  *	5.系统信息
  *
  *	************************************************/
TRUNCATE TABLE SMSystemInfo;
INSERT INTO SMSystemInfo(SystemID,SoftwareVersion,InitPasswd,ServerIP,ServerPort,ServerSSL,MonServerIP,MonServerPort,LastModifyTime,UpdateContent) 
VALUES('10001','1.0.00','c08d8b8450ecb9d15bc4ca00e20c7109','60.191.125.50',8080,'0','60.191.125.50',8080,GETDATE(),'Welcome to use Saudi Post eLocker System');


/************************************************
  *	6.数据同步时间戳
  *
  *	************************************************/
  TRUNCATE TABLE SCTimestamp;
INSERT INTO SCTimestamp(TimestampID,TerminalTimestamp,BoxTimestamp,LogTimestamp,WrongpwdTimestamp) 
VALUES(1,GETDATE(),GETDATE(),GETDATE(),GETDATE());

COMMIT TRANSACTION;

