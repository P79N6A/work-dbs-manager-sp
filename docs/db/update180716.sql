INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(31001,'邮递员类型','86','DAD-SP');/*SP corporate customer*/


INSERT INTO OPMenu VALUES('33080000','Pane7',                   2,'','','','{"url":"appjs/pt/PTPane7.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('33080001','Query',                   3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33080011','Counter Receive',                 3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33080012','Counter Return',                3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33080013','Counter Pickup',                3,'','','','','','','',1,1,'');


INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33009,'投递订单状态09-Pane7','4','Intransit-out');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33009,'投递订单状态09-Pane7','17','Package-out');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33009,'投递订单状态09-Pane7','8','Expired');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33009,'投递订单状态09-Pane7','9','M-Expired');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33009,'投递订单状态09-Pane7','5','Dropped');
/*INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33009,'投递订单状态09-Pane7','6','D-Dropped');*/

/*===================================================================
*pane7 4 - Intransit-Out  5 - Dropped, 6 - D-Dropped, 8 - Expired, 9 - M-Expired
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_PTPane7')
DROP VIEW V_PTPane7;
GO
CREATE VIEW V_PTPane7 AS
SELECT
  a.PackageID,
  a.CreateTime,
  case when a.ItemStatus='16' THEN '17' ElSE a.ItemStatus END AS ItemStatus,
  case when a.ItemStatus='16' THEN 'Package-out'  ELSE b.DictName END AS ItemStatusName,
  a.PPCID,
  '' AS PPCName,
  a.ZoneID,
  f.ZoneName,
  f.CompanyID,
  e.CompanyName,
  a.RefNo,
  a.CustomerID,
  a.CustomerName,
  a.CustomerAddress,
  a.CustomerMobile,
  a.TerminalNo,
  c.TerminalName,
  a.BoxNo,
  ISNULL(a.DropAgentID,'') AS DropAgentID,
  '' AS ReturnAgentID,
  a.DropAgentID AS PostmanID,
  h.PostmanName AS PostmanName,
  CONVERT(DATETIME,'1900-01-01',20) AS StoredTime,
  CONVERT(DATE,'1900-01-01',23) AS StoredDate,
  CONVERT(DATETIME,'1900-01-01',20) AS ExpiredTime,
  CONVERT(DATETIME,'1900-01-01',20) AS ReminderDateTime,
  '0' AS DADFlag,
  a.DropNum,
  a.ReportOrderID,/*DropOrderID: DOxxxxxx, ReturnOrderID:ROxxxxxx,TransferOrderID:TRxxxxxxx,ReceiveOrderID:RCxxxxx*/
  CASE WHEN a.ReportOrderID LIKE 'DO%' THEN a.ReportOrderID ELSE ''
  END AS DropOrderID,
  '' AS ReturnOrderID,
  a.LastModifyTime,
  a.Remark 
FROM PTReadyPackage a ,
  TBTerminal c,
  PMPostman h,
  PADictionary b,
  IMCompany e,
  IMZone f /*,
  IMPostalProcessingCenter g*/
WHERE (a.TerminalNo = c.TerminalNo) and c.TerminalType='1'
AND (a.ItemStatus = '16' OR a.ItemStatus='4') 
AND  a.ZoneID=f.ZoneID
AND  f.CompanyID  = e.CompanyID
/*AND  g.PPCID = a.PPCID*/
AND  h.PostmanID = a.DropAgentID
AND  b.DictTypeID = 33003 
AND  b.DictID = a.ItemStatus
UNION ALL
SELECT
  a.PackageID,
  a.CreateTime,
  a.ItemStatus,
  b.DictName AS ItemStatusName,
  a.PPCID,
  '' AS PPCName,
  a.ZoneID,
  f.ZoneName,
  f.CompanyID,
  e.CompanyName,
  a.RefNo,
  a.CustomerID,
  a.CustomerName,
  a.CustomerAddress,
  a.CustomerMobile,
  a.TerminalNo,
  d.TerminalName,
  a.BoxNo,
  ISNULL(a.DropAgentID,'') AS DropAgentID,
  '' AS ReturnAgentID,
  ISNULL(a.DropAgentID,'') AS PostmanID,
  ISNULL(h.PostmanName,'') AS PostmanName,
  a.StoredTime,
  a.StoredDate,
  ISNULL(a.ExpiredTime,CONVERT(DATETIME,'1900-01-01',20)) AS ExpiredTime,
  ISNULL(a.ReminderDateTime,CONVERT(DATETIME,'1900-01-01',20)) AS ReminderDateTime,
  a.DADFlag,
  a.DropNum,
  a.ReportOrderID,
  CASE WHEN a.ReportOrderID LIKE 'DO%' THEN a.ReportOrderID ELSE ''
  END AS DropOrderID,
  '' AS ReturnOrderID,
  a.LastModifyTime,
  a.Remark
FROM PTInBoxPackage a,
  TBTerminal d,
  PMPostman h,
  PADictionary b,
  IMCompany e,
  IMZone f/*,
  IMPostalProcessingCenter g*/
WHERE (d.TerminalNo = a.TerminalNo ) and d.TerminalType='1'
AND   f.ZoneID=a.ZoneID
AND   e.CompanyID  = f.CompanyID
AND   h.PostmanID = a.DropAgentID
/*AND   g.PPCID = a.PPCID*/
AND   b.DictTypeID = 33003
AND   b.DictID = a.ItemStatus;
GO

/*20180728更新*/
/* 将intransit-out，package-out从V_QYOperReportPackNumReady移到V_QYOperReportPackNumHistory*/
/* PTReadyPackage*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_QYOperReportPackNumReady')
DROP VIEW V_QYOperReportPackNumReady;
GO
CREATE VIEW V_QYOperReportPackNumReady AS 
SELECT a.ZoneID,a.TerminalNo
      ,CASE WHEN a.ItemStatus = 0 THEN 1 ELSE 0 END AS ListedPack
      ,CASE WHEN a.ItemStatus = 0 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS ListedPackTimeOut 
      
      ,CASE WHEN a.ItemStatus = 1 THEN 1 ELSE 0 END AS ReceivedPack
      ,CASE WHEN a.ItemStatus = 1 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS ReceivedPackTimeOut
      
      ,CASE WHEN a.ItemStatus = 15 THEN 1 ELSE 0 END AS TransferListPack
      ,CASE WHEN a.ItemStatus = 15 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS TransferListPackTimeOut
      
      ,CASE WHEN a.ItemStatus = 16 THEN 1 ELSE 0 END AS PackageListedPack
      ,CASE WHEN a.ItemStatus = 16 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS PackageListedPackTimeOut
       
      ,CASE WHEN a.ItemStatus = 2 THEN 1 ELSE 0 END AS AssignedPack
      ,CASE WHEN a.ItemStatus = 2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS AssignedPackTimeOut
      
      ,CASE WHEN a.ItemStatus = 3 THEN 1 ELSE 0 END AS ScheduledPack
      ,CASE WHEN a.ItemStatus = 3 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS ScheduledPackTimeOut
      
      ,CASE WHEN a.ItemStatus = 4 THEN 1 ELSE 0 END AS IntransitOutPack
      ,CASE WHEN a.ItemStatus = 4 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS IntransitOutPackTimeOut 
      
       ,CASE WHEN a.ItemStatus = 17 THEN 1 ELSE 0 END AS PackageOutPack
      ,CASE WHEN a.ItemStatus = 17 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS PackageOutPackTimeOut
FROM PTReadyPackage a,MBTimeLimit c 
where a.ItemStatus = c.StatusID AND c.StatusID in (0,1,2,3,4,15,16,17)
go
/* PTDeliverHistory*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_QYOperReportPackNumHistory')
DROP VIEW V_QYOperReportPackNumHistory;
GO
CREATE VIEW V_QYOperReportPackNumHistory AS 
SELECT a.ZoneID,a.TerminalNo
      ,CASE WHEN a.ItemStatus = 11 THEN 1 ELSE 0 END AS ReturnedPack
      ,CASE WHEN a.ItemStatus = 11 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS ReturnedPackTimeOut 
      
      ,CASE WHEN a.ItemStatus = 12 THEN 1 ELSE 0 END AS MissingPack
      ,CASE WHEN a.ItemStatus = 12 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS MissingPackTimeOut

       ,CASE WHEN a.ItemStatus = 10 THEN 1 ELSE 0 END AS IntransitBackPack
      ,CASE WHEN a.ItemStatus = 10 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS IntransitBackPackTimeOut
       
FROM PTDeliverHistory a,MBTimeLimit c 
where a.ItemStatus = c.StatusID AND c.StatusID in (11,12,10)
go
/*20180730监控视图更新*/
/* V_TBBoxStat添加zoneid，companyid*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_TBBoxStat0')
DROP VIEW V_TBBoxStat0;
GO
CREATE VIEW  V_TBBoxStat0 AS
SELECT
	a.BoxNo,
	a.TerminalNo,
	b.TerminalName,
	b.Location,
	b.BoxNum,
	a.BoxType,
	c.ZoneID,
	c.CompanyID,
	CASE WHEN c.PackageID IS NOT NULL AND a.BoxStatus NOT IN('1','2','3')   THEN '8' /*1.锁定 2.故障 3.锁定故障 8.占用*/
	         ELSE a.BoxStatus
	END AS BoxStatus
FROM TBServerBox a LEFT OUTER JOIN V_PTItemsUsedBox c ON(c.TerminalNo = a.TerminalNo AND c.BoxNo = a.BoxNo),
     TBTerminal b
WHERE b.TerminalNo = a.TerminalNo;
GO

IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_TBBoxStat')
DROP VIEW V_TBBoxStat;
GO
CREATE VIEW  V_TBBoxStat AS
SELECT
	a.BoxNo,
	a.TerminalNo,
	a.TerminalName,
	a.Location,
	a.BoxType,
	a.BoxStatus,
	a.ZoneID,
	a.CompanyID,
	CASE WHEN BoxStatus = '8' THEN 1 ELSE 0
	END AS UsedNum,
	CASE WHEN BoxStatus = '0' AND BoxType = '0' THEN 1 ELSE 0
	END AS FreeSmallNum,
	CASE WHEN BoxStatus = '0' AND BoxType = '1' THEN 1 ELSE 0
	END AS FreeMediumNum,
	CASE WHEN BoxStatus = '0' AND BoxType = '2' THEN 1 ELSE 0
	END AS FreeLargeNum,
	CASE WHEN BoxStatus = '0' AND BoxType = '3' THEN 1 ELSE 0
	END AS FreeSuperNum,
	CASE WHEN BoxStatus = '0' AND BoxType = '7' THEN 1 ELSE 0
	END AS FreeFreshNum,
	CASE WHEN BoxStatus IN('2','3') THEN 1 ELSE 0
	END AS FaultNum
FROM V_TBBoxStat0 a;
GO
/*V_QYOperReportPackNumReady,V_QYOperReportPackNumInBox,V_QYOperReportPackNumHistory,V_QYOperReportPackCounterNum添加CompanyID*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_QYOperReportPackNumReady')
DROP VIEW V_QYOperReportPackNumReady;
GO
CREATE VIEW V_QYOperReportPackNumReady AS 
SELECT a.ZoneID,a.TerminalNo,a.CompanyID
      ,CASE WHEN a.ItemStatus = 0 THEN 1 ELSE 0 END AS ListedPack
      ,CASE WHEN a.ItemStatus = 0 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS ListedPackTimeOut 
      
      ,CASE WHEN a.ItemStatus = 1 THEN 1 ELSE 0 END AS ReceivedPack
      ,CASE WHEN a.ItemStatus = 1 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS ReceivedPackTimeOut
      
      ,CASE WHEN a.ItemStatus = 15 THEN 1 ELSE 0 END AS TransferListPack
      ,CASE WHEN a.ItemStatus = 15 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS TransferListPackTimeOut
      
      ,CASE WHEN a.ItemStatus = 16 THEN 1 ELSE 0 END AS PackageListedPack
      ,CASE WHEN a.ItemStatus = 16 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS PackageListedPackTimeOut
       
      ,CASE WHEN a.ItemStatus = 2 THEN 1 ELSE 0 END AS AssignedPack
      ,CASE WHEN a.ItemStatus = 2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS AssignedPackTimeOut
      
      ,CASE WHEN a.ItemStatus = 3 THEN 1 ELSE 0 END AS ScheduledPack
      ,CASE WHEN a.ItemStatus = 3 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS ScheduledPackTimeOut
      
      ,CASE WHEN a.ItemStatus = 4 THEN 1 ELSE 0 END AS IntransitOutPack
      ,CASE WHEN a.ItemStatus = 4 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS IntransitOutPackTimeOut 
      
       ,CASE WHEN a.ItemStatus = 17 THEN 1 ELSE 0 END AS PackageOutPack
      ,CASE WHEN a.ItemStatus = 17 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS PackageOutPackTimeOut
FROM PTReadyPackage a,MBTimeLimit c 
where a.ItemStatus = c.StatusID AND c.StatusID in (0,1,2,3,4,15,16,17)
go
/* PTInBoxPackage*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_QYOperReportPackNumInBox')
DROP VIEW V_QYOperReportPackNumInBox;
GO
CREATE VIEW V_QYOperReportPackNumInBox AS 
SELECT a.ZoneID,a.TerminalNo,a.CompanyID
      ,CASE WHEN a.ItemStatus = 5 THEN 1 ELSE 0 END AS DroppedPack
      ,CASE WHEN a.ItemStatus = 5 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS DroppedPackTimeOut 
      
      ,CASE WHEN a.ItemStatus = 6 THEN 1 ELSE 0 END AS DDroppedPack
      ,CASE WHEN a.ItemStatus = 6 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS DDroppedPackTimeOut

      ,CASE WHEN a.ItemStatus = 8 THEN 1 ELSE 0 END AS ExpiredPack
      ,CASE WHEN a.ItemStatus = 8 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS ExpiredPackTimeOut 
      
      ,CASE WHEN a.ItemStatus = 9 THEN 1 ELSE 0 END AS MExpiredPack
      ,CASE WHEN a.ItemStatus = 9 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS MExpiredPackTimeOut


FROM PTInBoxPackage a,MBTimeLimit c 
where a.ItemStatus = c.StatusID AND c.StatusID in (5,6,8,9)
go

/* PTDeliverHistory*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_QYOperReportPackNumHistory')
DROP VIEW V_QYOperReportPackNumHistory;
GO
CREATE VIEW V_QYOperReportPackNumHistory AS 
SELECT a.ZoneID,a.TerminalNo,a.CompanyID
      ,CASE WHEN a.ItemStatus = 11 THEN 1 ELSE 0 END AS ReturnedPack
      ,CASE WHEN a.ItemStatus = 11 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS ReturnedPackTimeOut 
      
      ,CASE WHEN a.ItemStatus = 12 THEN 1 ELSE 0 END AS MissingPack
      ,CASE WHEN a.ItemStatus = 12 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS MissingPackTimeOut

       ,CASE WHEN a.ItemStatus = 10 THEN 1 ELSE 0 END AS IntransitBackPack
      ,CASE WHEN a.ItemStatus = 10 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS IntransitBackPackTimeOut


           
FROM PTDeliverHistory a,MBTimeLimit c 
where a.ItemStatus = c.StatusID AND c.StatusID in (11,12,10)
go

IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_QYOperReportPackCounterNum')
DROP VIEW V_QYOperReportPackCounterNum;
GO
CREATE VIEW V_QYOperReportPackCounterNum AS 
SELECT a.ZoneID,a.TerminalNo,a.CompanyID
      ,CASE WHEN a.ItemStatus = 5 THEN 1 ELSE 0 END AS CDroppedPack
      ,CASE WHEN a.ItemStatus = 5 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS CDroppedPackTimeOut 
      
      ,CASE WHEN a.ItemStatus = 4 THEN 1 ELSE 0 END AS CIntransitOutPack
      ,CASE WHEN a.ItemStatus = 4 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS CIntransitOutPackTimeOut 

      ,CASE WHEN a.ItemStatus = 8 THEN 1 ELSE 0 END AS CExpiredPack
      ,CASE WHEN a.ItemStatus = 8 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS CExpiredPackTimeOut 
      
      ,CASE WHEN a.ItemStatus = 9 THEN 1 ELSE 0 END AS CMExpiredPack
      ,CASE WHEN a.ItemStatus = 9 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS CMExpiredPackTimeOut

	  ,CASE WHEN a.ItemStatus = 16 THEN 1 ELSE 0 END AS CPackageListedPack
      ,CASE WHEN a.ItemStatus = 16 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS CPackageListedPackTimeOut
FROM V_PTPane7 a,MBTimeLimit c 
where a.ItemStatus = c.StatusID AND c.StatusID in (5,4,8,9,16)
go
/*20180807监控面板各状态列表更新*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_QYOperReportPackNumReady')
DROP VIEW V_QYOperReportPackNumReady;
GO
CREATE VIEW V_QYOperReportPackNumReady AS 
SELECT a.ZoneID,a.TerminalNo,a.CompanyID
      ,CASE WHEN a.ItemStatus = 0 THEN 1 ELSE 0 END AS ListedPack
      ,CASE WHEN a.ItemStatus = 0 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit THEN 1 ELSE 0 END AS ListedPackTimeOut 
      ,CASE WHEN a.ItemStatus = 0 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*2 THEN 1 ELSE 0 END AS ListedPackTimeOut1 
      ,CASE WHEN a.ItemStatus = 0 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*10 THEN 1 ELSE 0 END AS ListedPackTimeOut2 
      ,CASE WHEN a.ItemStatus = 0 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*10 THEN 1 ELSE 0 END AS ListedPackTimeOut3 
 	  
      ,CASE WHEN a.ItemStatus = 1 THEN 1 ELSE 0 END AS ReceivedPack
      ,CASE WHEN a.ItemStatus = 1 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit THEN 1 ELSE 0 END AS ReceivedPackTimeOut
	  ,CASE WHEN a.ItemStatus = 1 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*2 THEN 1 ELSE 0 END AS ReceivedPackTimeOut1 
      ,CASE WHEN a.ItemStatus = 1 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*10 THEN 1 ELSE 0 END AS ReceivedPackTimeOut2 
      ,CASE WHEN a.ItemStatus = 1 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*10 THEN 1 ELSE 0 END AS ReceivedPackTimeOut3 
      
      ,CASE WHEN a.ItemStatus = 15 THEN 1 ELSE 0 END AS TransferListPack
      ,CASE WHEN a.ItemStatus = 15 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit THEN 1 ELSE 0 END AS TransferListPackTimeOut
	  ,CASE WHEN a.ItemStatus = 15 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*2 THEN 1 ELSE 0 END AS TransferListPackTimeOut1 
      ,CASE WHEN a.ItemStatus = 15 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*10 THEN 1 ELSE 0 END AS TransferListPackTimeOut2 
      ,CASE WHEN a.ItemStatus = 15 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*10 THEN 1 ELSE 0 END AS TransferListPackTimeOut3 
      
      ,CASE WHEN a.ItemStatus = 16 THEN 1 ELSE 0 END AS PackageListedPack
      ,CASE WHEN a.ItemStatus = 16 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit THEN 1 ELSE 0 END AS PackageListedPackTimeOut
	  ,CASE WHEN a.ItemStatus = 16 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*2 THEN 1 ELSE 0 END AS PackageListedPackTimeOut1 
      ,CASE WHEN a.ItemStatus = 16 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*10 THEN 1 ELSE 0 END AS PackageListedPackTimeOut2 
      ,CASE WHEN a.ItemStatus = 16 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*10 THEN 1 ELSE 0 END AS PackageListedPackTimeOut3
       
      ,CASE WHEN a.ItemStatus = 2 THEN 1 ELSE 0 END AS AssignedPack
      ,CASE WHEN a.ItemStatus = 2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit THEN 1 ELSE 0 END AS AssignedPackTimeOut
	  ,CASE WHEN a.ItemStatus = 2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*2 THEN 1 ELSE 0 END AS AssignedPackTimeOut1 
      ,CASE WHEN a.ItemStatus = 2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*10 THEN 1 ELSE 0 END AS AssignedPackTimeOut2 
      ,CASE WHEN a.ItemStatus = 2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*10 THEN 1 ELSE 0 END AS AssignedPackTimeOut3
      
      ,CASE WHEN a.ItemStatus = 3 THEN 1 ELSE 0 END AS ScheduledPack
      ,CASE WHEN a.ItemStatus = 3 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit THEN 1 ELSE 0 END AS ScheduledPackTimeOut
	  ,CASE WHEN a.ItemStatus = 3 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*2 THEN 1 ELSE 0 END AS ScheduledPackTimeOut1 
      ,CASE WHEN a.ItemStatus = 3 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*10 THEN 1 ELSE 0 END AS ScheduledPackTimeOut2 
      ,CASE WHEN a.ItemStatus = 3 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*10 THEN 1 ELSE 0 END AS ScheduledPackTimeOut3
      
      ,CASE WHEN a.ItemStatus = 4 THEN 1 ELSE 0 END AS IntransitOutPack
      ,CASE WHEN a.ItemStatus = 4 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit THEN 1 ELSE 0 END AS IntransitOutPackTimeOut 
	  ,CASE WHEN a.ItemStatus = 4 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*2 THEN 1 ELSE 0 END AS IntransitOutPackTimeOut1 
      ,CASE WHEN a.ItemStatus = 4 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*10 THEN 1 ELSE 0 END AS IntransitOutPackTimeOut2 
      ,CASE WHEN a.ItemStatus = 4 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*10 THEN 1 ELSE 0 END AS IntransitOutPackTimeOut3
      
      ,CASE WHEN a.ItemStatus = 17 THEN 1 ELSE 0 END AS PackageOutPack
      ,CASE WHEN a.ItemStatus = 17 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit THEN 1 ELSE 0 END AS PackageOutPackTimeOut
	  ,CASE WHEN a.ItemStatus = 17 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*2 THEN 1 ELSE 0 END AS PackageOutPackTimeOut1 
      ,CASE WHEN a.ItemStatus = 17 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*10 THEN 1 ELSE 0 END AS PackageOutPackTimeOut2 
      ,CASE WHEN a.ItemStatus = 17 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*10 THEN 1 ELSE 0 END AS PackageOutPackTimeOut3
FROM PTReadyPackage a,MBTimeLimit c 
where a.ItemStatus = c.StatusID AND c.StatusID in (0,1,2,3,4,15,16,17)
go
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_QYOperReportPackNumInBox')
DROP VIEW V_QYOperReportPackNumInBox;
GO
CREATE VIEW V_QYOperReportPackNumInBox AS 
SELECT a.ZoneID,a.TerminalNo,a.CompanyID
      ,CASE WHEN a.ItemStatus = 5 THEN 1 ELSE 0 END AS DroppedPack
      ,CASE WHEN a.ItemStatus = 5 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit THEN 1 ELSE 0 END AS DroppedPackTimeOut 
      ,CASE WHEN a.ItemStatus = 5 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*2 THEN 1 ELSE 0 END AS DroppedPackTimeOut1 
      ,CASE WHEN a.ItemStatus = 5 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*10 THEN 1 ELSE 0 END AS DroppedPackTimeOut2 
      ,CASE WHEN a.ItemStatus = 5 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*10 THEN 1 ELSE 0 END AS DroppedPackTimeOut3
	  
      ,CASE WHEN a.ItemStatus = 6 THEN 1 ELSE 0 END AS DDroppedPack
      ,CASE WHEN a.ItemStatus = 6 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit THEN 1 ELSE 0 END AS DDroppedPackTimeOut
	  ,CASE WHEN a.ItemStatus = 6 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*2 THEN 1 ELSE 0 END AS DDroppedPackTimeOut1 
      ,CASE WHEN a.ItemStatus = 6 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*10 THEN 1 ELSE 0 END AS DDroppedPackTimeOut2 
      ,CASE WHEN a.ItemStatus = 6 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*10 THEN 1 ELSE 0 END AS DDroppedPackTimeOut3
		
      ,CASE WHEN a.ItemStatus = 8 THEN 1 ELSE 0 END AS ExpiredPack
      ,CASE WHEN a.ItemStatus = 8 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit THEN 1 ELSE 0 END AS ExpiredPackTimeOut 
	  ,CASE WHEN a.ItemStatus = 8 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*2 THEN 1 ELSE 0 END AS ExpiredPackTimeOut1 
      ,CASE WHEN a.ItemStatus = 8 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*10 THEN 1 ELSE 0 END AS ExpiredPackTimeOut2 
      ,CASE WHEN a.ItemStatus = 8 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*10 THEN 1 ELSE 0 END AS ExpiredPackTimeOut3
      
      ,CASE WHEN a.ItemStatus = 9 THEN 1 ELSE 0 END AS MExpiredPack
      ,CASE WHEN a.ItemStatus = 9 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit THEN 1 ELSE 0 END AS MExpiredPackTimeOut
	  ,CASE WHEN a.ItemStatus = 9 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*2 THEN 1 ELSE 0 END AS MExpiredPackTimeOut1 
      ,CASE WHEN a.ItemStatus = 9 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*10 THEN 1 ELSE 0 END AS MExpiredPackTimeOut2 
      ,CASE WHEN a.ItemStatus = 9 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*10 THEN 1 ELSE 0 END AS MExpiredPackTimeOut3


FROM PTInBoxPackage a,MBTimeLimit c 
where a.ItemStatus = c.StatusID AND c.StatusID in (5,6,8,9)
go

/* PTDeliverHistory*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_QYOperReportPackNumHistory')
DROP VIEW V_QYOperReportPackNumHistory;
GO
CREATE VIEW V_QYOperReportPackNumHistory AS 
SELECT a.ZoneID,a.TerminalNo,a.CompanyID
      ,CASE WHEN a.ItemStatus = 11 THEN 1 ELSE 0 END AS ReturnedPack
      ,CASE WHEN a.ItemStatus = 11 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit THEN 1 ELSE 0 END AS ReturnedPackTimeOut 
	  ,CASE WHEN a.ItemStatus = 11 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*2 THEN 1 ELSE 0 END AS ReturnedPackTimeOut1 
      ,CASE WHEN a.ItemStatus = 11 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*10 THEN 1 ELSE 0 END AS ReturnedPackTimeOut2 
      ,CASE WHEN a.ItemStatus = 11 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*10 THEN 1 ELSE 0 END AS ReturnedPackTimeOut3
      
      ,CASE WHEN a.ItemStatus = 12 THEN 1 ELSE 0 END AS MissingPack
      ,CASE WHEN a.ItemStatus = 12 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit THEN 1 ELSE 0 END AS MissingPackTimeOut
	  ,CASE WHEN a.ItemStatus = 12 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*2 THEN 1 ELSE 0 END AS MissingPackTimeOut1 
      ,CASE WHEN a.ItemStatus = 12 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*10 THEN 1 ELSE 0 END AS MissingPackTimeOut2 
      ,CASE WHEN a.ItemStatus = 12 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*10 THEN 1 ELSE 0 END AS MissingPackTimeOut3

      ,CASE WHEN a.ItemStatus = 10 THEN 1 ELSE 0 END AS IntransitBackPack
      ,CASE WHEN a.ItemStatus = 10 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit THEN 1 ELSE 0 END AS IntransitBackPackTimeOut
	  ,CASE WHEN a.ItemStatus = 10 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*2 THEN 1 ELSE 0 END AS IntransitBackPackTimeOut1 
      ,CASE WHEN a.ItemStatus = 10 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*10 THEN 1 ELSE 0 END AS IntransitBackPackTimeOut2 
      ,CASE WHEN a.ItemStatus = 10 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*10 THEN 1 ELSE 0 END AS IntransitBackPackTimeOut3


           
FROM PTDeliverHistory a,MBTimeLimit c 
where a.ItemStatus = c.StatusID AND c.StatusID in (11,12,10)
go

IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_QYOperReportPackCounterNum')
DROP VIEW V_QYOperReportPackCounterNum;
GO
CREATE VIEW V_QYOperReportPackCounterNum AS 
SELECT a.ZoneID,a.TerminalNo,a.CompanyID
      ,CASE WHEN a.ItemStatus = 5 THEN 1 ELSE 0 END AS CDroppedPack
      ,CASE WHEN a.ItemStatus = 5 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit THEN 1 ELSE 0 END AS CDroppedPackTimeOut 
	  ,CASE WHEN a.ItemStatus = 5 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*2 THEN 1 ELSE 0 END AS CDroppedPackTimeOut1 
      ,CASE WHEN a.ItemStatus = 5 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*10 THEN 1 ELSE 0 END AS CDroppedPackTimeOut2 
      ,CASE WHEN a.ItemStatus = 5 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*10 THEN 1 ELSE 0 END AS CDroppedPackTimeOut3
      
      ,CASE WHEN a.ItemStatus = 4 THEN 1 ELSE 0 END AS CIntransitOutPack
      ,CASE WHEN a.ItemStatus = 4 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit THEN 1 ELSE 0 END AS CIntransitOutPackTimeOut 
	  ,CASE WHEN a.ItemStatus = 4 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*2 THEN 1 ELSE 0 END AS CIntransitOutPackTimeOut1 
      ,CASE WHEN a.ItemStatus = 4 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*10 THEN 1 ELSE 0 END AS CIntransitOutPackTimeOut2 
      ,CASE WHEN a.ItemStatus = 4 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*10 THEN 1 ELSE 0 END AS CIntransitOutPackTimeOut3

      ,CASE WHEN a.ItemStatus = 8 THEN 1 ELSE 0 END AS CExpiredPack
      ,CASE WHEN a.ItemStatus = 8 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit THEN 1 ELSE 0 END AS CExpiredPackTimeOut 
	  ,CASE WHEN a.ItemStatus = 8 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*2 THEN 1 ELSE 0 END AS CExpiredPackTimeOut1 
      ,CASE WHEN a.ItemStatus = 8 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*10 THEN 1 ELSE 0 END AS CExpiredPackTimeOut2 
      ,CASE WHEN a.ItemStatus = 8 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*10 THEN 1 ELSE 0 END AS CExpiredPackTimeOut3
      
      ,CASE WHEN a.ItemStatus = 9 THEN 1 ELSE 0 END AS CMExpiredPack
      ,CASE WHEN a.ItemStatus = 9 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit THEN 1 ELSE 0 END AS CMExpiredPackTimeOut
	  ,CASE WHEN a.ItemStatus = 9 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*2 THEN 1 ELSE 0 END AS CMExpiredPackTimeOut1 
      ,CASE WHEN a.ItemStatus = 9 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*10 THEN 1 ELSE 0 END AS CMExpiredPackTimeOut2 
      ,CASE WHEN a.ItemStatus = 9 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*10 THEN 1 ELSE 0 END AS CMExpiredPackTimeOut3

	  ,CASE WHEN a.ItemStatus = 16 THEN 1 ELSE 0 END AS CPackageListedPack
      ,CASE WHEN a.ItemStatus = 16 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit THEN 1 ELSE 0 END AS CPackageListedPackTimeOut
	  ,CASE WHEN a.ItemStatus = 16 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*2 THEN 1 ELSE 0 END AS CPackageListedPackTimeOut1 
      ,CASE WHEN a.ItemStatus = 16 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*2 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) <c.TimeLimit*10 THEN 1 ELSE 0 END AS CPackageListedPackTimeOut2 
      ,CASE WHEN a.ItemStatus = 16 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit*10 THEN 1 ELSE 0 END AS CPackageListedPackTimeOut3
FROM V_PTPane7 a,MBTimeLimit c 
where a.ItemStatus = c.StatusID AND c.StatusID in (5,4,8,9,16)
go

