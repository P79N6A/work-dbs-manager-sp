
/*===================================================================
*  包裹服务商视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_IMCompany')
DROP VIEW V_IMCompany;
GO
CREATE VIEW V_IMCompany AS
SELECT
	a.CompanyID,
	a.CompanyName,
	a.CompanyType,
	c.DictName AS CompanyTypeName,
	a.MasterFlag,
	d.DictName AS MasterFlagName,
	a.Address,
	a.Email,
	a.Mobile,
	a.Feedback,
	e.DictName AS FeedbackName,
	a.LogoUrl,
	a.Slogan,
	a.SMS_Notification,
	a.ExpiredDays,
	a.ReminderDays,
	a.ReminderTime,
	a.CreateTime,
	a.Remark
FROM IMCompany a,
     PADictionary c,
     PADictionary d,
     PADictionary e
WHERE c.DictID = a.CompanyType
AND c.DictTypeID = 25001
AND d.DictID = a.MasterFlag
AND d.DictTypeID = 25011
AND e.DictID = a.Feedback
AND e.DictTypeID = 25012
;
GO
/*===================================================================
*  包裹处理中心视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_IMPPC')
DROP VIEW V_IMPPC;
GO
CREATE VIEW V_IMPPC AS
SELECT
	a.PPCID,
	a.CompanyID,
	b.CompanyName,
	a.PPCName,
	a.Password,
	a.Username,
	a.PPCServerURL,
	a.PPCServerIP,
	a.PPCServerPassword,
	a.PPCServerUsername,
	a.Remark
FROM IMPostalProcessingCenter a,
	IMCompany b
WHERE b.CompanyID = a.CompanyID;
GO

/*===================================================================
*  个人用户视图
*
=====================================================================*/
/**
* Customer.Address生成规则：
* "AZC " +AZC.Code +"-"+Locker.Code+", "+Customer.Code
* (Locker.City+","+Locker.Zipcode
* "Saudi Arabia")
*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_IMCustomer')
DROP VIEW V_IMCustomer;
GO
CREATE VIEW V_IMCustomer AS
SELECT
	a.CustomerID,
	a.CustomerName,
	a.TerminalNo,
	b.TerminalName,
	ISNULL(b.ZoneID,'') AS ZoneID,
	a.IDCard,
	/*a.Address,*/
	'AZC '+ISNULL(b.ZoneID,'NA')+'-'+b.TerminalNo+', '+a.CustomerID+'\n'+ISNULL(b.City,'CITY')+', ZIP '+ISNULL(b.Zipcode,'NA')+'\n'+'Saudi Arabia' AS Address,
	a.Email,
	a.Mobile,
	a.Active,
	c.DictName AS ActiveName,
	a.Status,
	d.DictName AS StatusName,
	CONVERT(DATE,a.Validity,23) AS Validity,
	0 AS Months,/*a.keepMonths*/
	a.Remark
FROM IMCustomer a 
	LEFT OUTER JOIN TBTerminal b ON(b.TerminalNo = a.TerminalNo),
	PADictionary c,
	PADictionary d
WHERE  c.DictID = a.Active
AND c.DictTypeID = 25013
AND d.DictID = a.Status
AND d.DictTypeID = 25015
;
GO

/*===================================================================
*  电商伙伴信息视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_IMEcomPartner')
DROP VIEW V_IMEcomPartner;
GO
CREATE VIEW V_IMEcomPartner AS
SELECT
	a.EPartnerID,
	a.EPartnerName,
	a.Address,
	a.Email,
	a.Mobile,
	a.Username,
	a.Password,
	a.Active,
	c.DictName AS ActiveName,
	a.Remark
FROM IMEcommercePartner a ,
     PADictionary c
WHERE c.DictID = a.Active
AND c.DictTypeID = 25013
;
GO
/*===================================================================
*  商业伙伴信息视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_IMBusiPartner')
DROP VIEW V_IMBusiPartner;
GO
CREATE VIEW V_IMBusiPartner AS
SELECT
	a.BPartnerID,
	a.BPartnerName,
	a.CollectZoneID,
	b.CollectZoneName,
	a.Address,
	a.Longitude,
	a.Latitude,
	a.Email,
	a.Mobile,
	a.Username,
	a.Balance,
	a.CreditFlag,
	c.DictName AS CreditFlagName,
	a.CreditLimit,
	a.Discount,
	a.CollectionServiceFlag,
	d.DictName AS CollectServFlagName,
	a.ReturnServiceFlag,
	e.DictName AS ReturnServFlagName,
	a.Remark
FROM IMBusinessPartner a,
	IMCollectZone b,
	PADictionary e,
	PADictionary d,
	PADictionary c
WHERE c.DictID = a.CreditFlag
	AND c.DictTypeID = 25022
	AND d.DictID = a.CollectionServiceFlag
	AND d.DictTypeID = 25021
	AND e.DictID = a.ReturnServiceFlag
	AND e.DictTypeID = 25021
	AND b.CollectZoneID = a.CollectZoneID
;
GO
/*===================================================================
*  服务类型视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_IMServiceType')
DROP VIEW V_IMServiceType;
GO
CREATE VIEW V_IMServiceType AS
SELECT
	a.ServiceID,
	a.ServiceName,
	a.ServiceAmt,
	a.ServiceAmtSmall,
	a.ServiceAmtMed,
	a.ServiceAmtBig,
	a.ServicePrefix,
	a.ExtraServiceID,
	a.ReturnAmt,
	a.Active,
	c.DictName AS ActiveName,
	ISNULL(b.CntValue,0) AS Counter,
	a.Remark
FROM IMServiceRate a LEFT OUTER JOIN IMServiceCounter b ON(b.ServiceID=a.ServiceID),
	PADictionary c
WHERE c.DictID = a.Active
AND c.DictTypeID = 25013 
;
GO
/*===================================================================
*  分拣区域视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_IMZone')
DROP VIEW V_IMZone;
GO
CREATE VIEW V_IMZone AS
SELECT
	a.ZoneID,
	a.CompanyID,
	b.CompanyName,
	b.MasterFlag,
	b.MasterFlagName,
	a.ZoneName,
	a.Description,
	a.CollectCharge,
	CASE WHEN (a.MandatoryFlag&1)=1 THEN '1' ELSE '0'
	END AS R1Mandatory,
	CASE WHEN (a.MandatoryFlag&2)=2 THEN '1' ELSE '0'
	END AS R2Mandatory,
	CASE WHEN (a.MandatoryFlag&4)=4 THEN '1' ELSE '0'
	END AS R3Mandatory,
	CASE WHEN (a.MandatoryFlag&8)=8 THEN '1' ELSE '0'
	END AS R4Mandatory,
	CASE WHEN (a.MandatoryFlag&16)=16 THEN '1' ELSE '0'
	END AS R5Mandatory,
	CASE WHEN (a.MandatoryFlag&32)=32 THEN '1' ELSE '0'
	END AS R6Mandatory,
	CASE WHEN (a.MandatoryFlag&64)=64 THEN '1' ELSE '0'
	END AS R7Mandatory,
	CASE WHEN (a.MandatoryFlag&128)=128 THEN '1' ELSE '0'
	END AS R8Mandatory,
	a.CreateTime,
	a.Remark
FROM IMZone a,
     V_IMCompany b
WHERE b.CompanyID = a.CompanyID;
GO

/*===================================================================
* 揽件区域视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_IMCollectZone')
DROP VIEW V_IMCollectZone;
GO
CREATE VIEW V_IMCollectZone AS
SELECT
	a.CollectZoneID,
	a.ZoneID,
	b.ZoneName,
	a.CollectZoneName,
	a.CollectZoneDesc,
	a.Remark
FROM IMCollectZone a,
   IMZone b
WHERE b.ZoneID = a.ZoneID;
GO

/*===================================================================
* 分拣区域柜体权限视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_IMZoneLockerRight')
DROP VIEW V_IMZoneLockerRight;
GO
CREATE VIEW V_IMZoneLockerRight AS
SELECT
	a.ZoneID,
	a.TerminalNo,
	b.CompanyID,
	b.CompanyName,
	b.MasterFlag,
	b.MasterFlagName,
	b.ZoneName,
	c.TerminalName,
	c.TerminalStatus
FROM IMZoneLockerRight a,
	V_IMZone b,
	TBTerminal c
WHERE b.ZoneID = a.ZoneID
  AND c.TerminalNo = a.TerminalNo;
GO

/*===================================================================
* 包裹服务商箱体权限视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_IMCompBoxRight')
DROP VIEW V_IMCompBoxRight;
GO
CREATE VIEW V_IMCompBoxRight AS
SELECT
	a.CompanyID,
	a.TerminalNo,
	a.BoxNo,
	b.CompanyName,
	b.MasterFlag,
	b.MasterFlagName,
	c.BoxType,
	c.BoxTypeName,
	c.BoxName,
	c.BoxStatus,
	c.BoxUsedStatus
FROM IMCompanyBoxRight a,
	V_IMCompany b,
	V_TBBox c
WHERE a.CompanyID = b.CompanyID
	AND a.TerminalNo=c.TerminalNo 
	AND a.BoxNo = c.BoxNo;
GO

/*===================================================================
*  包裹服务商可用空箱统计信息
* V_IMCompBoxUsedStat  TB
* V_IMCompFreeBoxStat  TB
=====================================================================*/



/*===================================================================
* 商业伙伴服务权限视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_IMBPartnerServiceRight')
DROP VIEW V_IMBPartnerServiceRight;
GO
CREATE VIEW V_IMBPartnerServiceRight AS
SELECT
	a.BPartnerID,
	c.BPartnerName,
	a.ServiceID,
	b.ServiceName,
	b.ServiceAmtSmall,
	b.ServiceAmtBig,
	b.ServiceAmtMed
FROM IMBPartnerServiceRight a,
	IMServiceRate b,
	IMBusinessPartner c
WHERE b.ServiceID = a.ServiceID
  AND b.Active = '1'/*1-激活*/
  AND c.BPartnerID = a.BPartnerID;
  GO

/*===================================================================
* 消息模板，短信邮件网关视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_IMGatewayMsg')
DROP VIEW V_IMGatewayMsg;
GO
CREATE VIEW V_IMGatewayMsg AS
SELECT
	a.GatewayID,
	a.GatewayName,
	a.SMSUsername,
	a.SMSPassword,
	a.SMSUsercode,
	a.SMSField1,
	a.SMSField2,
	a.SMSField3,
	a.SMSTemplate,
	a.GatewayURL,
	a.EmailParam1,
	a.EmailParam2,
	a.EmailParam3,
	a.EmailParam4,
	a.EmailTemplate,
	a.TemplateType AS Type,
	c.DictName AS TypeName,
	a.Remark
FROM IMGateway a,
		PADictionary c
WHERE c.DictID = a.TemplateType
AND c.DictTypeID = 25050;
GO

IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_IMSMSGateway')
DROP VIEW V_IMSMSGateway;
GO
CREATE VIEW V_IMSMSGateway AS
SELECT
	a.GatewayID,
	a.GatewayName,
	a.SMSUsername,
	a.SMSPassword,
	a.SMSUsercode,
	a.SMSField1,
	a.SMSField2,
	a.SMSField3,
	a.GatewayURL,
	a.Remark
FROM IMGateway a
WHERE a.TemplateType='51';
GO

IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_IMEmailAccount')
DROP VIEW V_IMEmailAccount;
GO
CREATE VIEW V_IMEmailAccount AS
SELECT
	a.GatewayID AS EmailID,
	a.GatewayName AS EmailName,
	a.GatewayURL AS Username,
	a.SMSPassword AS Password,
	a.EmailParam1,
	a.EmailParam2,
	a.EmailParam3,
	a.EmailParam4,
	a.Remark
FROM IMGateway a
WHERE a.TemplateType='52';
GO

IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_IMMsgTemplate')
DROP VIEW V_IMMsgTemplate;
GO
CREATE VIEW V_IMMsgTemplate AS
SELECT
	a.GatewayID AS TemplateID,
	a.GatewayName AS TemplateName,
	a.SMSTemplate AS SMSTemplate,
	a.EmailTemplate AS EmailTemplate,
	a.TemplateType,/*1~*/
	c.DictName AS TemplateTypeName,
	a.Remark
FROM IMGateway a,
		PADictionary c
WHERE c.DictID = a.TemplateType
AND c.DictTypeID = 25052;
GO

