/***************************************************************************
  *	数据库初始化-初始化一次
  *
  *	创建日期：2014-2-6
  *	创建人：郑晓勇
  *
  *
  *	************************************************************************/

BEGIN TRANSACTION;
/************************************************
  * 1.SaudiPost
  * 公司及分拣区域中心信息 01-SaudiPost 01 000 为虚拟分拣中心，与公司名称相同
  *
  * ************************************************/

  TRUNCATE TABLE IMCompany;
INSERT INTO IMCompany(CompanyID,CompanyName,CompanyType,MasterFlag,Address,Email,Mobile,Feedback,LogoUrl,Slogan,SMS_Notification,CreateTime)
VALUES('01','SaudiPost','1','1','','','','0','','','',GETDATE());

  TRUNCATE TABLE IMZone;
INSERT INTO IMZone(ZoneID,CompanyID,ZoneName,Description,CollectCharge,MandatoryFlag,CreateTime)
VALUES('01000','01','SaudiPost','SaudiPost','0',255,GETDATE());

/************************************************
  * 3.角色信息
  *
  * ************************************************/
TRUNCATE TABLE OPRole;

INSERT INTO OPRole VALUES(1,'Super','181818');/*超级管理员角色*/
INSERT INTO OPRole VALUES(2,'Master','181818');/*运营管理角色*/
INSERT INTO OPRole VALUES(10,'Operator','181818');/*操作员角色*/

/*INSERT INTO OPRole VALUES(11,'Master Administrator','181818');
INSERT INTO OPRole VALUES(12,'Service Owner Administrator','181818');
INSERT INTO OPRole VALUES(13,'Zone Administrator','181818');
INSERT INTO OPRole VALUES(14,'Zone Operator','181818');*/

/************************************************
  * 4.管理员信息
  *
  * ************************************************/
TRUNCATE TABLE OPOperator;

/*超级管理员*/
INSERT INTO OPOperator (OperID,OperName,ZoneID,UserID,OperType,OperPassword,CreateDate,CreateTime,UpperUserID,OperStatus)
	VALUES('181818','Admin','01000','181818',1,'52c69e3a57331081823331c4e69d3f2e',GETDATE(),GETDATE(),'181818','0');

/*主运营方管理员*/
INSERT INTO OPOperator (OperID,OperName,ZoneID,UserID,OperType,OperPassword,CreateDate,CreateTime,UpperUserID,OperStatus)
	VALUES('Master','Master','01000','master',2,'52c69e3a57331081823331c4e69d3f2e',GETDATE(),GETDATE(),'MASTER','0');

/*现场管理员*/
INSERT INTO OPOperator (OperID,OperName,ZoneID,UserID,OperType,OperPassword,CreateDate,CreateTime,UpperUserID,OperStatus)
	VALUES('165629','165629','01000','165629',3,'52c69e3a57331081823331c4e69d3f2e',GETDATE(),GETDATE(),'165629','0');	
/************************************************
  * 5.管理员角色信息
  *
  * ************************************************/
TRUNCATE TABLE OPOperRole;
  
INSERT INTO OPOperRole(OperID,RoleID) VALUES('181818',1);

INSERT INTO OPOperRole(OperID,RoleID) VALUES('Master',2);

/************************************************
  * 6.特殊权限信息
  *
  * ************************************************/
TRUNCATE TABLE OPSpecialPriv;


INSERT INTO OPSpecialPriv(SpePrivID,SpePrivName) VALUES(1,'运营方数据访问权限');
INSERT INTO OPSpecialPriv(SpePrivID,SpePrivName) VALUES(2,'服务商数据访问权限');
INSERT into OPSpecialPriv(SpePrivID,SpePrivName) VALUES(3,'远程开箱');
INSERT into OPSpecialPriv(SpePrivID,SpePrivName) VALUES(4,'修改设备端欢迎词');

/*INSERT INTO OPSpecialPriv(SpePrivID,SpePrivName) VALUES(11,'运营方数据访问权限');
INSERT INTO OPSpecialPriv(SpePrivID,SpePrivName) VALUES(21,'服务商数据访问权限');
INSERT INTO OPSpecialPriv(SpePrivID,SpePrivName) VALUES(31,'分拣区域数据访问权限');*/

/************************************************
  * 管理员特殊权限信息
  *
  * ************************************************/
TRUNCATE TABLE OPOperSpeRight;

INSERT INTO OPOperSpeRight(OperID,SpePrivID) VALUES('181818',1);

INSERT INTO OPOperSpeRight(OperID,SpePrivID) VALUES('Master',1);


/************************************************
  * 7.角色菜单信息
  *
  * ************************************************/
TRUNCATE TABLE OPRoleToMenu;

INSERT INTO OPRoleToMenu(RoleID,MenuID)
SELECT 1,MenuID
FROM OPMenu  WHERE MenuID<'33000000' OR MenuID>'33999999';

INSERT INTO OPRoleToMenu(RoleID,MenuID)
SELECT 2,MenuID
FROM OPMenu WHERE MenuID>='12000000';
  
/************************************************
  * 8.管理员菜单信息
  *
  * ************************************************/
TRUNCATE TABLE OPOperToMenu;
  
INSERT INTO OPOperToMenu(OperID,MenuID)
SELECT '181818',MenuID
FROM OPMenu WHERE MenuID<'33000000' OR MenuID>'33999999'; /*无业务处理权限*/

INSERT INTO OPOperToMenu(OperID,MenuID)
SELECT 'Master',MenuID
FROM OPMenu WHERE MenuID>='12000000';

/************************************************
  * 9.角色总体限制信息
  *
  * ************************************************/
TRUNCATE TABLE OPRoleAllLimit;



/************************************************
  * 10.管理员总体限制信息
  *
  * ************************************************/
TRUNCATE TABLE OPOperAllLimit;

/**************************************************
 * 11.管理员在线信息
 *
 **************************************************/
TRUNCATE TABLE OPOperOnline;


/************************************************
  *	3.流水号计数器
  *
  *	************************************************/
TRUNCATE TABLE PASequence;

INSERT INTO	PASequence(SeqName,SeqValue,CacheSize,SeqMaxValue) VALUES('SeqOperLogID',0,100,0);           /*操作员日志序号*/
INSERT INTO	PASequence(SeqName,SeqValue,CacheSize,SeqMaxValue) VALUES('SeqReportWaterID',0,100,0);          /*报告流水号*/
INSERT INTO	PASequence(SeqName,SeqValue,CacheSize,SeqMaxValue) VALUES('SeqWaterID',0,100,0);                    /*流水号*/
INSERT INTO	PASequence(SeqName,SeqValue,CacheSize,SeqMaxValue) VALUES('SeqTradeWaterID',0,100,23587200);   /*交易流水号*/
INSERT INTO	PASequence(SeqName,SeqValue,CacheSize,SeqMaxValue) VALUES('SeqItemWaterID',0,10,23587200);   /*订单流水号*/
INSERT INTO	PASequence(SeqName,SeqValue,CacheSize,SeqMaxValue) VALUES('SeqItemTransferWaterID',0,10,23587200);   /*Transfer订单流水号*/
INSERT INTO	PASequence(SeqName,SeqValue,CacheSize,SeqMaxValue) VALUES('SeqTransferWaterID',0,50,23587200);   /*Transfer订单流水号*/
/************************************************
  *	4.箱类型信息
  *
  *	************************************************/
TRUNCATE TABLE TBBoxType;
INSERT INTO TBBoxType(BoxType,MBBoxType,BoxTypeName,BoxHeight,BoxWidth,BoxDepth)  VALUES('0','0','Small',120,403,550);/*小*/
INSERT INTO TBBoxType(BoxType,MBBoxType,BoxTypeName,BoxHeight,BoxWidth,BoxDepth)  VALUES('1','1','Medium',240,403,550);/*中*/
INSERT INTO TBBoxType(BoxType,MBBoxType,BoxTypeName,BoxHeight,BoxWidth,BoxDepth)  VALUES('2','2','Large',360,403,550);/*大*/
/*INSERT INTO TBBoxType(BoxType,MBBoxType,BoxTypeName,BoxHeight,BoxWidth,BoxDepth)  VALUES('3','3','超大',800,403,550);*/
/*INSERT INTO TBBoxType(BoxType,MBBoxType,BoxTypeName,BoxHeight,BoxWidth,BoxDepth)  VALUES('4','4','超小',120,200,550);*/
/*INSERT INTO TBBoxType(BoxType,MBBoxType,BoxTypeName,BoxHeight,BoxWidth,BoxDepth)  VALUES('6','6','快餐',180,403,550);*/
/*INSERT INTO TBBoxType(BoxType,MBBoxType,BoxTypeName,BoxHeight,BoxWidth,BoxDepth)  VALUES('7','7','生鲜',460,403,550);*/
/*INSERT INTO TBBoxType(BoxType,MBBoxType,BoxTypeName,BoxHeight,BoxWidth,BoxDepth)  VALUES('8','8','广告箱',360,403,550);*/
/*INSERT INTO TBBoxType(BoxType,MBBoxType,BoxTypeName,BoxHeight,BoxWidth,BoxDepth)  VALUES('9','9','主控箱',760,403,550);*/




COMMIT TRANSACTION;

