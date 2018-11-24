
/*箱使用情况统计视图*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_QYBoxUsage')
DROP VIEW V_QYBoxUsage;
GO
CREATE VIEW V_QYBoxUsage AS
SELECT
  a.TerminalNo,
  a.ZoneID,
  b.TerminalName,
  b.Location,
  c.BoxType,
  d.BoxTypeName,
  YEAR(a.StoredDate) AS OccurYear,
  DATEDIFF(QUARTER,DATEADD(YEAR,DATEDIFF(YEAR,-0,a.StoredDate),-1),a.StoredDate) AS OccurQuarter,
  MONTH(a.StoredDate) AS OccurMonth,
  DATEDIFF(WEEK,DATEADD(YEAR,DATEDIFF(YEAR,-0,a.StoredDate),-1),a.StoredDate) AS OccurWeek,
  a.StoredDate AS OccurDate,
  CASE WHEN  c.BoxType = '0' THEN 1 ELSE 0
  END AS SmallNum,
  CASE WHEN  c.BoxType = '1' THEN 1 ELSE 0
  END AS MediumNum,
  CASE WHEN  c.BoxType = '2' THEN 1 ELSE 0
  END AS LargeNum
FROM PTDeliverHistory a, 
  TBTerminal b,
  TBServerBox c,
  TBBoxType d
WHERE  a.StoredDate IS NOT NULL  
  AND b.TerminalNo=a.TerminalNo
  AND c.TerminalNo=a.TerminalNo
  AND c.BoxNo=a.BoxNo
  AND d.BoxType=c.BoxType;
 GO
  
/*
SELECT
 a.TerminalNo,
 a.TerminalName,
 a.Location,
 '' AS UserInfo,
 a.OccurYear AS OccurYear,
 a.OccurQuarter AS OccurQuarter,
 a.OccurMonth AS OccurMonth,
 a.OccurWeek AS OccurWeek,
 a.OccurDate AS OccurDate,
 SUM(SmallNum) AS SmallCnt,
 SUM(MediumNum) AS MediumCnt,
 SUM(LargeNum) AS LargeCnt 
FROM V_QYBoxUsage a 
WHERE 1=1 
GROUP BY TerminalNo,OccurQuarter, OccurMonth;
*/  
  
/*包裹在箱时间统计视图*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_QYItemDurationHours')
DROP VIEW V_QYItemDurationHours;
GO
CREATE VIEW V_QYItemDurationHours AS
SELECT
  YEAR(a.StoredDate) AS OccurYear,
  DATEDIFF(QUARTER,DATEADD(YEAR,DATEDIFF(YEAR,-0,a.StoredDate),-1),a.StoredDate) AS OccurQuarter,
  MONTH(a.StoredDate) AS OccurMonth,
  DATEDIFF(WEEK,DATEADD(YEAR,DATEDIFF(YEAR,-0,a.StoredDate),-1),a.StoredDate) AS OccurWeek,
  a.StoredDate AS OccurDate,
  /*a.StoredTime,*/
  /*a.TakedTime,*/
  a.DADFlag,
  DATEDIFF(HOUR, a.StoredTime, a.TakedTime)+1 AS DurationHours
FROM PTDeliverHistory a 
WHERE  a.StoredTime IS NOT NULL
  AND a.TakedTime IS NOT NULL;
GO

/*Service owner箱使用时间统计视图*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_QYServiceOwnerBoxUsage')
DROP VIEW V_QYServiceOwnerBoxUsage;
GO
CREATE VIEW V_QYServiceOwnerBoxUsage AS
SELECT
  a.PackageID,
  a.CreateTime,
  a.TerminalNo,
  a.BoxNo,
  g.BoxType,
  h.BoxTypeName,
  a.ZoneID,
  f.ZoneName,
  f.CompanyID,
  e.CompanyName,
  a.DropNum,
  a.DADFlag,
  a.StoredTime,
  a.StoredDate,
  a.TakedTime,
  DATEDIFF(DAY, a.StoredTime, a.TakedTime) AS Days,
  a.ItemStatus,
  b.DictName AS ItemStatusName,
  a.RunStatus AS RunStatus,
  c.DictName  AS RunStatusName
FROM PTDeliverHistory a,
  TBServerBox g,
  TBBoxType h,
  IMCompany e,
  IMZone f,
  PADictionary b,
  PADictionary c
WHERE  a.StoredTime IS NOT NULL
  AND a.TakedTime IS NOT NULL
  AND g.TerminalNo=a.TerminalNo
  AND g.BoxNo=a.BoxNo
  AND h.BoxType = g.BoxType
  AND f.ZoneID=a.ZoneID
  AND e.CompanyID  = f.CompanyID
  AND b.DictTypeID = 33003
  AND b.DictID = a.ItemStatus
  AND c.DictTypeID = 33071
  AND c.DictID = a.RunStatus
GO


/*===================================================================
*投递量统计  每日每台投递量统计   
*
=====================================================================*/
-- 投递统计视图
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'_V_OrderStat0')
DROP VIEW _V_OrderStat0;
GO
CREATE VIEW _V_OrderStat0 AS
SELECT 
 a.PackageID, 
 a.DropAgentID AS PostmanID,
 a.CompanyID,
 a.TerminalNo, 
 a.ZoneID,
 a.StoredDate AS OccurDate, 
 1 AS DropNum
FROM PTDeliverHistory a 
WHERE  a.StoredDate IS NOT NULL
UNION ALL
SELECT
 a.PackageID,
 a.DropAgentID AS PostmanID,
 a.CompanyID,
 a.TerminalNo, 
 a.ZoneID,
 a.StoredDate AS OccurDate, 
 1 AS DropNum
FROM PTInBoxPackage a;
GO
 -- 在箱统计视图
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'_V_OrderStat1')
DROP VIEW _V_OrderStat1;
GO
CREATE VIEW _V_OrderStat1 AS
SELECT 
 a.PackageID, 
 a.DropAgentID AS PostmanID,
 a.CompanyID,
 a.TerminalNo, 
 a.ZoneID,
 a.StoredDate AS OccurDate, 
 1 AS InBoxNum,
  CASE WHEN a.ItemStatus in('8','9') THEN 1 ELSE 0
  END AS  ExpiredNum
FROM PTInBoxPackage a;
GO
/*SELECT 
 a.PackageID, 
 a.DropAgentID AS PostmanID,
 a.CompanyID,
 a.TerminalNo, 
 a.ZoneID,
 a.TakedTime, 
 CONVERT(varchar(10),a.TakedTime ,20) AS OccurDate, 
 CASE WHEN a.ItemStatus in('7') THEN 1 ELSE 0
  END AS  TakeOutNum,
  CASE WHEN a.ItemStatus not in('7') THEN 1 ELSE 0
  END AS  TakeBackNum
FROM PTDeliverHistory a 
WHERE  a.StoredDate IS NOT NULL AND a.TakedTime>DATEADD(day,-600,GETDATE()); 
*/

/*同一天、同一柜体、同一投递员的投递统计视图*/
DROP TABLE _QYDropOrderStat;
GO
CREATE TABLE  _QYDropOrderStat
(
   TerminalNo           VARCHAR(20) NOT NULL ,
   OccurDate            DATE NOT NULL ,
   PostmanID            VARCHAR(32) NOT NULL  ,
   CompanyID            varchar(8) ,
   ZoneID               varchar(10),
   DropNum              INT DEFAULT 0,
   TakeOutNum            INT DEFAULT 0,
   TakeBackNum          INT DEFAULT 0,
   ExpiredNum           INT DEFAULT 0,
   ManagerOutNum        INT DEFAULT 0,
   ReturnNum            INT DEFAULT 0,
   InBoxNum             INT DEFAULT 0,
   CreateTime           DATETIME ,
   LastModifyTime       DATETIME ,
   InBoxNumLast         INT DEFAULT 0,
   ExpiredNumLast       INT DEFAULT 0,
   PRIMARY KEY (TerminalNo,OccurDate,CompanyID,PostmanID,ZoneID)
);
/*
ALTER TABLE _QYDropOrderStat ADD  InBoxNumLast INT DEFAULT 0;
ALTER TABLE _QYDropOrderStat ADD  ExpiredNumLast INT DEFAULT 0;
update _QYDropOrderStat set InBoxNumLast =0,ExpiredNumLast =0;
select * from _QYDropOrderStat;
*/

IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_QYDropOrderStat0')
DROP VIEW V_QYDropOrderStat0;
GO
CREATE VIEW V_QYDropOrderStat0 AS
SELECT
  a.TerminalNo,
  a.OccurDate,
  YEAR(a.OccurDate) AS OccurYear,
  DATEPART(QUARTER, a.OccurDate) AS OccurQuarter,
  DATEPART(MONTH, a.OccurDate) AS OccurMonth,
  DATEPART(WEEK, a.OccurDate) AS OccurWeek,
  CONVERT(varchar(10), DATEADD(QUARTER,DATEPART(QUARTER,  a.OccurDate)-1, DATEADD(DAY,(-DATEPART(DAYOFYEAR,  a.OccurDate)+1),  a.OccurDate)) , 120)+'~'+CONVERT(varchar(10), DATEADD(QUARTER,DATEPART(QUARTER,  a.OccurDate), DATEADD(DAY,(-DATEPART(DAYOFYEAR,  a.OccurDate)),  a.OccurDate)) , 120)  AS QuarterPeriod,
  CONVERT(varchar(10), DATEADD(DAY,(-DATEPART(WEEKDAY, a.OccurDate)+1), a.OccurDate), 120)+'~'+CONVERT(varchar(10),  DATEADD(DAY,(-DATEPART(WEEKDAY, a.OccurDate)+7), a.OccurDate), 120) AS WeekPeriod,
  DATENAME (YEAR ,a.OccurDate)+DATENAME (MONTH ,a.OccurDate) AS MonthPeriod,
  a.PostmanID,
  a.CompanyID,
  a.ZoneID,
  a.DropNum,
  a.InBoxNumLast AS InBoxNum,
  a.ExpiredNumLast AS ExpiredNum,
  a.TakeOutNum AS  PickUpNum,
  a.TakeBackNum AS ReturnNum,
  '9' AS InBoxFlag
FROM _QYDropOrderStat a 
WHERE  a.TerminalNo IS NOT NULL AND a.TerminalNo <> '';

/*SELECT
  a.PackageID,
  a.TerminalNo,
  a.StoredDate AS OccurDate,
  YEAR(a.StoredDate) AS OccurYear,
  DATEPART(QUARTER, a.StoredDate) AS OccurQuarter,
  DATEPART(MONTH, a.StoredDate) AS OccurMonth,
  DATEPART(WEEK, a.StoredDate) AS OccurWeek,
  CONVERT(varchar(10), DATEADD(QUARTER,DATEPART(QUARTER,  a.StoredDate)-1, DATEADD(DAY,(-DATEPART(DAYOFYEAR,  a.StoredDate)+1),  a.StoredDate)) , 120)+'~'+CONVERT(varchar(10), DATEADD(QUARTER,DATEPART(QUARTER,  a.StoredDate), DATEADD(DAY,(-DATEPART(DAYOFYEAR,  a.StoredDate)),  a.StoredDate)) , 120)  AS QuarterPeriod,
  CONVERT(varchar(10), DATEADD(DAY,(-DATEPART(WEEKDAY, a.StoredDate)+1), a.StoredDate), 120)+'~'+CONVERT(varchar(10),  DATEADD(DAY,(-DATEPART(WEEKDAY, a.StoredDate)+7), a.StoredDate), 120) AS WeekPeriod,
  DATENAME (YEAR ,a.StoredDate)+DATENAME (MONTH ,a.StoredDate) AS MonthPeriod,
  a.DropAgentID AS PostmanID,
  a.CompanyID,
  a.ZoneID,
  a.ItemStatus,
  1 AS DropNum,
  0 AS InBoxNum,
  0 AS ExpiredNum,
  CASE WHEN a.ItemStatus in('7') THEN 1 ELSE 0
  END AS  PickUpNum,
  CASE WHEN a.ItemStatus not in('7') THEN 1 ELSE 0
  END AS  ReturnNum,
  '0' AS InBoxFlag
FROM PTDeliverHistory a 
WHERE  a.StoredDate IS NOT NULL AND a.TerminalNo IS NOT NULL AND a.TerminalNo <> ''
UNION ALL
SELECT
  a.PackageID,
  a.TerminalNo,
  a.StoredDate AS OccurDate,
  YEAR(a.StoredDate) AS OccurYear,
  DATEPART(QUARTER, a.StoredDate) AS OccurQuarter,
  DATEPART(MONTH, a.StoredDate) AS OccurMonth,
  DATEPART(WEEK, a.StoredDate) AS OccurWeek,
  CONVERT(varchar(10), DATEADD(QUARTER,DATEPART(QUARTER,  a.StoredDate)-1, DATEADD(DAY,(-DATEPART(DAYOFYEAR,  a.StoredDate)+1),  a.StoredDate)) , 120)+'~'+CONVERT(varchar(10), DATEADD(QUARTER,DATEPART(QUARTER,  a.StoredDate), DATEADD(DAY,(-DATEPART(DAYOFYEAR,  a.StoredDate)),  a.StoredDate)) , 120)  AS QuarterPeriod,
  CONVERT(varchar(10), DATEADD(DAY,(-DATEPART(WEEKDAY, a.StoredDate)+1), a.StoredDate), 120)+'~'+CONVERT(varchar(10),  DATEADD(DAY,(-DATEPART(WEEKDAY, a.StoredDate)+7), a.StoredDate), 120) AS WeekPeriod,
  DATENAME (YEAR ,a.StoredDate)+DATENAME (MONTH ,a.StoredDate) AS MonthPeriod,
  a.DropAgentID AS PostmanID,
  a.CompanyID,
  a.ZoneID,
  a.ItemStatus,
  1 AS DropNum,
  1 AS InBoxNum,
  CASE WHEN a.ItemStatus in('8','9') THEN 1 ELSE 0
  END AS  ExpiredNum,
  0 AS PickUpNum,
  0 AS ReturnNum,
  '1' AS InBoxFlag
FROM PTInBoxPackage a 
;
*/
GO

/* 运营报表统计视图2017-12-07*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_QYOperationReportTimeout')
DROP VIEW V_QYOperationReportTimeout;
GO

CREATE VIEW V_QYOperationReportTimeout AS 
SELECT 
  a.WaterID,
  a.PackageID,
  a.OperatorID AS OperID,
  e.OperName,
  a.OperatorType AS OperType,
  e.OperTypeName,
  a.ZoneID,
  d.ZoneName,
  a.LastStatus AS ItemStatus,
  b.DictName AS ItemStatusName,
  a.StatusTime
  ,CASE WHEN a.StatusTime-c.TimeLimit>0 THEN a.StatusTime-c.TimeLimit ELSE 0 END AS StatusTimeOut
  ,CASE WHEN a.StatusTime-c.TimeLimit>0 THEN 1 ELSE 0 END AS StatusTimeOutNum
  ,a.LastModifyTime
FROM  PTItemLifeCycle a, 
  MBTimeLimit c,
  IMZone d,
  V_Staff e,
  PADictionary b
WHERE a.LastStatus = c.StatusID
AND a.ZoneID = d.ZoneID 
AND a.OperatorID=e.OperID and a.OperatorType = e.OperType
AND a.LastStatus = b.DictID AND  b.DictTypeID = '33003';
GO

/* 显示包裹个数 2017-12-13*/
/* PTReadyPackage*/
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
/*20180730更新pane7监控视图*/
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

