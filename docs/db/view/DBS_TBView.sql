
/*===================================================================
*  柜体信息
*
=====================================================================*/

/*存在故障箱的柜体*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_TBTerminalBoxFault')
DROP VIEW V_TBTerminalBoxFault;
GO
CREATE VIEW  V_TBTerminalBoxFault AS
SELECT DISTINCT TerminalNo 
FROM TBServerBox 
WHERE BoxStatus IN('1','2','3');
GO

/*
*柜体状态：增加离线状态，和箱故障状态
*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_TBTerminalStatus')
DROP VIEW V_TBTerminalStatus;
GO
CREATE VIEW  V_TBTerminalStatus AS
SELECT
	a.TerminalNo,
	CASE WHEN (a.TerminalStatus IN('0')) AND (b.TerminalNo IS NULL OR b.OnlineStatus<>'1') THEN '3'/*'3' : Off-line*/
	WHEN (a.TerminalStatus IN('0')) AND (c.TerminalNo IS NOT NULL ) THEN '5'/*存在故障箱 '5':BoxFault*/
	ELSE a.TerminalStatus END AS TerminalStatus
FROM TBTerminal a 
	LEFT JOIN MBSignInfo b ON(b.TerminalNo = a.TerminalNo)
	LEFT JOIN V_TBTerminalBoxFault c ON(c.TerminalNo = a.TerminalNo)/*故障箱*/
;
GO

IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_TBTerminalStatusName')
DROP VIEW V_TBTerminalStatusName;
GO
CREATE VIEW  V_TBTerminalStatusName AS
SELECT
	a.TerminalNo,
	a.TerminalStatus,
	d.DictName AS TerminalStatusName
FROM V_TBTerminalStatus a, 
	PADictionary d
WHERE d.DictID = a.TerminalStatus
AND d.DictTypeID = 21001;
GO

/**
 * Locker.Address自动生成：
 * "AZC " +AZC.Code +", "+Locker.LockerID  //V_TBTerminal中实现
 * Locker.City+","+Locker.Zipcode
 * "Saudi Arabia"
*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_TBTerminal')
DROP VIEW V_TBTerminal;
GO
CREATE VIEW  V_TBTerminal AS
SELECT
	a.TerminalNo,
	a.TerminalName,
	a.TerminalType,
	g.DictName AS TerminalTypeName,
	f.TerminalStatus,
	d.DictName AS TerminalStatusName,
	/*a.Address,*/
	'AZC '+ISNULL(a.ZoneID,'NA')+', '+a.TerminalNo+'\n'+ISNULL(a.City,'CITY')+', ZIP '+ISNULL(a.Zipcode,'NA')+'\n'+'Saudi Arabia' AS Address,
	a.Location,
	ISNULL(a.Longitude,46.724767) AS Longitude,
	ISNULL(a.Latitude,24.711598) AS Latitude,
	a.ZoneID,
	b.ZoneName,
	ISNULL(a.City,'') AS City,
	ISNULL(a.Zipcode,'') AS Zipcode,
	a.RegisterFlag,
	c.DictName AS RegisterFlagName,
	a.AppRegisterLimit,
	a.AppRegisterFlag,
	e.DictName AS AppRegisterFlagName,
	a.MaintMobile,
	a.MaintEmail,
	a.OfBureau,
	a.OfSegment,
	/*a.DepartmentID,*/
	a.MBDeviceNo,
	a.BoxNum,
	a.DeskNum,
	a.MacAddr,
	a.Brand,
	a.Model,
	a.MachineSize,
	a.MainDeskAddress,
	/*a.CreateTime,*/
	a.LastModifyTime,
	a.Remark
FROM TBTerminal a LEFT JOIN IMZone b ON(b.ZoneID = a.ZoneID),
	V_TBTerminalStatus f,
	PADictionary c,
	PADictionary d,
	PADictionary e,
    PADictionary g 
WHERE f.TerminalNo= a.TerminalNo
AND c.DictID = a.RegisterFlag
AND c.DictTypeID = 21005
AND d.DictID = f.TerminalStatus
AND d.DictTypeID = 21001
AND e.DictID = a.AppRegisterFlag
AND e.DictTypeID = 21018 
AND g.DictID = a.TerminalType 
AND g.DictTypeID = 21003;
GO

/*===================================================================
*  箱体信息
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_TBBox')
DROP VIEW V_TBBox;
GO
CREATE VIEW  V_TBBox AS
SELECT
	a.BoxNo,
	a.TerminalNo,
	d.TerminalName,
	a.BoxName,
	a.DeskNo,
	a.DeskBoxNo,
	a.BoxType,
	b.BoxTypeName,
	a.BoxStatus,
	c.DictName AS BoxStatusName,
	a.BoxUsedStatus,
	e.DictName AS BoxUsedStatusName,
	CASE WHEN a.BoxStatus IN('1','3') THEN '1' ELSE '0' END AS LockStatus,
	CASE WHEN a.BoxStatus IN('2','3') THEN '1' ELSE '0' END AS FaultStatus
FROM TBServerBox a,
     TBBoxType b,
     PADictionary c,
     PADictionary e,
     TBTerminal d
WHERE b.BoxType = a.BoxType
AND c.DictID = a.BoxStatus
AND c.DictTypeID = 21011
AND e.DictID = a.BoxUsedStatus
AND e.DictTypeID = 21013
AND d.TerminalNo = a.TerminalNo
;
GO

/*===================================================================
*订单使用箱情况视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_PTItemsUsedBox')
DROP VIEW V_PTItemsUsedBox;
GO
CREATE VIEW V_PTItemsUsedBox AS
SELECT
	a.PackageID,
  a.ZoneID,
  a.CompanyID,
  a.TerminalNo,
  a.BoxNo,
  a.ParcelSize,
  b.BoxType,
  b.BoxStatus,
   '1' AS  BoxUsedStatus  /*0.空闲 1.预定 2.占用 9.未知*/
FROM PTReadyPackage a, 
		TBServerBox b
WHERE b.TerminalNo = a.TerminalNo AND b.BoxNo = a.BoxNo
UNION ALL
SELECT
  a.PackageID,
  a.ZoneID,
  a.CompanyID,
  a.TerminalNo,
  a.BoxNo,
  a.ParcelSize,
  b.BoxType,
  b.BoxStatus,
  '2' AS  BoxUsedStatus  /*0.空闲 1.预定 2.占用 9.未知*/
FROM PTInBoxPackage a,  TBServerBox b
WHERE  b.TerminalNo = a.TerminalNo
  AND b.BoxNo = a.BoxNo;
GO

/*箱可用状态*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_TBBoxUsedStatus')
DROP VIEW V_TBBoxUsedStatus;
GO
CREATE VIEW  V_TBBoxUsedStatus AS
SELECT
  a.BoxNo,
  a.BoxName,
  a.DeskNo,
  a.DeskBoxNo,
  a.TerminalNo,
  a.BoxType,
  CASE 
    WHEN (c.PackageID IS NOT NULL ) AND a.BoxStatus NOT IN('1','2','3')  THEN '8'  /*1.锁定 2.故障 3.锁定故障 8.占用*/
    ELSE a.BoxStatus
    END AS BoxStatus,
  CASE WHEN (c.PackageID IS NOT NULL ) THEN c.BoxUsedStatus  /*0.空闲 1.预定 2.占用 9.未知*/ 
    ELSE '0'
    END AS BoxUsedStatus,	
  c.ZoneID,
  c.CompanyID,
  c.PackageID
FROM TBServerBox a 
     LEFT OUTER JOIN V_PTItemsUsedBox c ON(c.TerminalNo = a.TerminalNo AND c.BoxNo = a.BoxNo),
     TBTerminal b
WHERE b.TerminalNo = a.TerminalNo;
GO

/**/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_IMCompBoxUsedStat')
DROP VIEW V_IMCompBoxUsedStat;
GO
CREATE VIEW  V_IMCompBoxUsedStat AS
SELECT
  a.CompanyID,
	a.TerminalNo,
	a.BoxNo,
	c.BoxType,
	c.BoxStatus,
	c.BoxUsedStatus
FROM IMCompanyBoxRight a
  LEFT JOIN V_TBBoxUsedStatus c ON(c.TerminalNo = a.TerminalNo AND c.BoxNo = a.BoxNo)
;
GO

IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_IMCompFreeBoxStat')
DROP VIEW V_IMCompFreeBoxStat;
GO
CREATE VIEW  V_IMCompFreeBoxStat AS
SELECT
  a.CompanyID,
	a.TerminalNo,
	a.BoxNo,
	a.BoxType,
	a.BoxStatus,
	a.BoxUsedStatus,
	CASE WHEN BoxType = '0' THEN 1 ELSE 0
	END AS FreeSmallNum,
	CASE WHEN BoxType = '1' THEN 1 ELSE 0
	END AS FreeMediumNum,
	CASE WHEN BoxType = '2' THEN 1 ELSE 0
	END AS FreeLargeNum,
	CASE WHEN BoxType = '3' THEN 1 ELSE 0
	END AS FreeSuperNum,
	CASE WHEN BoxType = '7' THEN 1 ELSE 0
	END AS FreeFreshNum
FROM V_IMCompBoxUsedStat a
WHERE (a.BoxStatus='0' AND a.BoxUsedStatus='0');
GO

/*箱状态信息视图*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_TBBoxStatusInfo')
DROP VIEW V_TBBoxStatusInfo;
GO
CREATE VIEW  V_TBBoxStatusInfo AS
SELECT
	a.BoxNo,
	a.BoxName,
	a.DeskNo,
	a.DeskBoxNo,
	a.TerminalNo,
	d.TerminalName,
	a.BoxType,
	b.BoxTypeName,
	a.BoxStatus,
	c.DictName AS BoxStatusName,
	a.BoxUsedStatus,
	e.DictName AS BoxUsedStatusName,
	a.PackageID,
	a.ZoneID,
	f.ZoneName,
  a.CompanyID,
  g.CompanyName
FROM V_TBBoxUsedStatus a 
     LEFT OUTER JOIN IMZone f ON(f.ZoneID = a.ZoneID)
     LEFT OUTER JOIN IMCompany g ON(g.CompanyID = a.CompanyID),
     TBBoxType b,
     PADictionary c,
     PADictionary e,
     TBTerminal d
WHERE b.BoxType = a.BoxType 
AND c.DictID = a.BoxStatus 
AND c.DictTypeID = 21011 
AND e.DictID = a.BoxUsedStatus 
AND e.DictTypeID = 21013 
AND d.TerminalNo = a.TerminalNo
;
GO

/*===================================================================
*  箱体统计信息
*
=====================================================================*/
/*20170730添加zoneid，companyid*/
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

