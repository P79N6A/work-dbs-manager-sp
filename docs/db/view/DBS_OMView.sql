/* =========================================
* 发送邮件视图
=============================================*/

IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_MBSendReportEmail')
DROP VIEW V_MBSendReportEmail;
GO
CREATE VIEW V_MBSendReportEmail AS 
SELECT  a.TerminalNo,a.TerminalName,a.Location,b.ZoneID
	,COUNT(*) AS BoxNum
	,SUM(a.UsedNum) AS UsedNum
	,SUM(a.FreeSmallNum) AS FreeSmallNum
	,SUM(a.FreeMediumNum) AS FreeMediumNum
	,SUM(a.FreeLargeNum) AS FreeLargeNum
	,SUM(a.FaultNum) AS FaultNum 
FROM V_TBBoxStat a,TBTerminal b
WHERE a.TerminalNo = b.TerminalNo 
GROUP BY b.ZoneID,a.TerminalNo,a.TerminalName,a.Location;
GO
/* 接收邮件管理员视图*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_MBSendReportEmailJS')
DROP VIEW V_MBSendReportEmailJS;
GO
CREATE VIEW V_MBSendReportEmailJS AS 
select a.OperID, a.OperID AS UserID ,a.EmailType,b.DictName AS EmailTypeName,a.SendAsRights,c.DictName AS SendAsRightsName,a.CreateTime,a.LastModifyTime,a.Remark 
FROM MBSendReportEmail a,PADictionary b,PADictionary c 
where a.EmailType = b.DictID AND b.DictTypeID = '56011' 
     AND a.SendAsRights = c.DictID AND c.DictTypeID = '57011';
go
/*===================================================================
*  设备签到视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_MBSignInfo0')
DROP VIEW V_MBSignInfo0;
GO
CREATE VIEW  V_MBSignInfo0 AS
SELECT
  a.TerminalNo,
  b.TerminalName,
  b.Location,
  a.SignIP,
  a.SignMac,
  a.SoftwareVersion AS TerminalVersion,
  a.InitPasswd,
  a.SignTime,
  a.OnlineStatus,
  d.DictName AS OnlineStatusName,
  b.TerminalStatus,
  e.DictName AS TerminalStatusName,
  a.LastModifyTime,
  '10001' AS SystemID,
  a.Remark
FROM MBSignInfo a,TBTerminal b,PADictionary d,PADictionary e
WHERE b.TerminalNo = a.TerminalNo
AND d.DictID = a.OnlineStatus
AND d.DictTypeID = 15021
AND e.DictID = b.TerminalStatus
AND e.DictTypeID = 21001;
GO

IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_SMSystemInfo')
DROP VIEW V_SMSystemInfo;
GO
CREATE VIEW  V_SMSystemInfo AS
SELECT
  a.SystemID,
  a.SoftwareVersion AS ServerVersion
FROM SMSystemInfo a;
GO

IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_MBSignInfo')
DROP VIEW V_MBSignInfo;
GO
CREATE VIEW  V_MBSignInfo AS
SELECT
  a.TerminalNo,
  a.TerminalName,
  a.Location,
  a.SignIP,
  a.SignMac,
  a.TerminalVersion AS SoftwareVersion,
  f.ServerVersion,
  a.InitPasswd,
  a.SignTime,
  a.OnlineStatus,
  a.OnlineStatusName,
  a.TerminalStatus,
  a.TerminalStatusName,
  a.LastModifyTime,
  a.Remark
FROM V_MBSignInfo0 a,V_SMSystemInfo f
WHERE f.SystemID = a.SystemID;
GO


/*===================================================================
*  设备警报日志
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_TerminalAlertLog')
DROP VIEW V_TerminalAlertLog;
GO
CREATE VIEW  V_TerminalAlertLog AS
SELECT
  a.TerminalNo,
  b.TerminalName,
  b.Location,
  a.ReportWaterID,
  a.AlertType,
  d.DictName AS AlertTypeName,
  a.AlertLevel,
  e.DictName AS AlertLevelName,
  a.AlertContent,
  a.BoxNo,
  a.LogDate,
  a.LogTime,
  a.Remark
FROM MBTerminalAlertLog a,
          TBTerminal b,
           PADictionary d,
           PADictionary e
WHERE b.TerminalNo = a.TerminalNo
AND d.DictID = a.AlertType
AND d.DictTypeID = 15011
AND e.DictID = a.AlertLevel
AND e.DictTypeID = 15013;
GO


/*===================================================================
*  取件密码短消息
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_PwdShortMsg')
DROP VIEW V_PwdShortMsg;
GO
CREATE VIEW  V_PwdShortMsg AS
SELECT
  a.WaterID,
  a.PackageID,
  a.StoredDate,
  a.StoredTime,
  a.TerminalNo,
  b.TerminalName,      
  a.CustomerMobile,
  a.CustomerName,
  '' as OpenBoxKey,
  a.MsgContent,
  a.SendStatus,
  d.DictName AS SendStatusName,
  a.ReSendNum,
  a.LastModifyTime,
  a.PackageStatus,
  f.DictName AS PackageStatusName,
  /*f.ItemStatus AS PackageStatus,
  f.ItemStatusName AS PackageStatusName,*/
  a.Remark
FROM MBPwdShortMsg a,
     TBTerminal b,
     PADictionary d,
     PADictionary f
	 /*V_PTItemStatus f*/
WHERE b.TerminalNo = a.TerminalNo
AND d.DictTypeID = 15001
AND d.DictID = a.SendStatus
AND a.PackageStatus in('5','6','8','9')
AND f.DictTypeID = 33003
AND f.DictID = a.PackageStatus;
/*AND f.PackageID =a.PackageID
AND f.TerminalNo = a.TerminalNo
AND f.StoredTime = a.StoredTime*/
GO

/*===================================================================
*  催领短消息
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_ReminderWater')
DROP VIEW V_ReminderWater;
GO
CREATE VIEW  V_ReminderWater AS
SELECT
  a.WaterID,
  a.PackageID,
  a.TerminalNo,
  b.TerminalName,        
  a.CustomerMobile,
  a.PostmanID,
  a.StoredDate,
  a.StoredTime,
  a.LastModifyTime,
  a.Remark
FROM MBReminderWater a,
     TBTerminal b
WHERE b.TerminalNo = a.TerminalNo;
GO

/*===================================================================
*  短消息统计视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_Stat4SMS')
DROP VIEW V_Stat4SMS;
GO
CREATE VIEW  V_Stat4SMS AS
SELECT
   a.TerminalNo,
   a.OccurYear,
   a.OccurMonth,
   a.TotalNum,
   a.PwdNum,
   a.ExpireNum,
   a.ReminderNum,
   a.DynamicNum,
   a.PickupNum,
   a.OtherNum
FROM MBSmsStat a,
     TBTerminal b
WHERE b.TerminalNo = a.TerminalNo;
GO

/*===================================================================
*  监控图片信息视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_MonitorPictureInfo')
DROP VIEW V_MonitorPictureInfo;
GO
CREATE VIEW  V_MonitorPictureInfo AS
SELECT
   a.TerminalNo,
   b.TerminalName,
   a.WaterID,
   a.PictureName,
   a.PicturePath,
   a.PictureType,
   d.DictName AS PictureTypeName,
   a.PackageID,
   a.CreateTime
FROM MBMonitorPictureInfo a,
     TBTerminal b,
     PADictionary d
WHERE b.TerminalNo = a.TerminalNo
AND d.DictTypeID = 15042
AND d.DictID = a.PictureType;
GO

