/*===================================================================
*pane1 listed   received
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_PTPane1')
DROP VIEW V_PTPane1;
GO
CREATE VIEW V_PTPane1 AS
SELECT
  a.PackageID,
  a.CreateTime,
  a.ItemStatus,
  b.DictName AS ItemStatusName,
  a.PPCID,
  g.PPCName,
  a.ZoneID,
  a.ZoneIDDes,
  f.ZoneName,
  f.CompanyID,
  e.CompanyName,
  a.RefNo,
  a.CustomerID,
  a.CustomerName,
  a.CustomerAddress,
  a.CustomerMobile,
  ISNULL(a.TerminalNo,'') AS TerminalNo,
  ISNULL(a.BoxType,'')  AS BoxType,
  ISNULL(d.BoxTypeName,'')  AS BoxTypeName,
  ISNULL(a.BoxNo,'0') AS BoxNo,
  ISNULL(a.DropAgentID,'') AS DropAgentID,
  '' AS ReturnAgentID,
  ISNULL(a.DropAgentID,'') AS PostmanID,
  a.DropNum,
  a.ReportOrderID,/*DropOrderID: DOxxxxxx, ReturnOrderID:ROxxxxxx,TransferOrderID:TRxxxxxxx,ReceiveOrderID:RCxxxxx*/
  a.LastModifyTime,
  a.Remark
FROM PTReadyPackage a 
    LEFT OUTER JOIN TBBoxType d ON(d.BoxType = a.BoxType),
    PADictionary b,
    IMCompany e,
    IMZone f,
    IMPostalProcessingCenter g
WHERE a.ItemStatus in('0','1','15','16')
AND  a.ZoneID=f.ZoneID
AND  f.CompanyID  = e.CompanyID
AND  g.PPCID = a.PPCID
AND  b.DictTypeID = 33003
AND  b.DictID = a.ItemStatus;
GO

/*===================================================================
*pane2   2 - Assigned, 3 - Scheduled
*
/* 将箱类型的检测修改为，显示没有分配箱类型的订单 */
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_PTPane2')
DROP VIEW V_PTPane2;
GO
CREATE VIEW V_PTPane2 AS
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
  c.TerminalName,
  a.BoxType,
  d.BoxTypeName,
  ISNULL(a.BoxNo,'0') AS BoxNo,
  ISNULL(a.DropAgentID,'') AS DropAgentID,
  '' AS ReturnAgentID,
  ISNULL(a.DropAgentID,'') AS PostmanID,
  ISNULL(h.PostmanName,'') AS PostmanName,
  a.DropNum,
  a.ReportOrderID,/*DropOrderID: DOxxxxxx, ReturnOrderID:ROxxxxxx,TransferOrderID:TRxxxxxxx,ReceiveOrderID:RCxxxxx*/
  CASE WHEN a.ReportOrderID LIKE 'DO%' THEN a.ReportOrderID ELSE ''
  END AS DropOrderID,
  a.LastModifyTime,
  a.Remark
FROM TBBoxType d RIGHT OUTER JOIN PTReadyPackage a ON(d.BoxType = a.BoxType)
  LEFT OUTER JOIN PMPostman h ON(h.PostmanID = a.DropAgentID),
  TBTerminal c,
  PADictionary b,
  IMCompany e,
  IMZone f/*,
  IMPostalProcessingCenter g*/
WHERE a.ItemStatus in('2','3')
AND  a.ZoneID=f.ZoneID
AND  f.CompanyID  = e.CompanyID
/*AND  g.PPCID = a.PPCID*/
AND  c.TerminalNo = a.TerminalNo
AND  b.DictTypeID = 33003
AND  b.DictID = a.ItemStatus;
GO

/*===================================================================
*pane3 4 - Intransit-Out, 10 - Intransit-Back
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_PTPane3')
DROP VIEW V_PTPane3;
GO
CREATE VIEW V_PTPane3 AS
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
  '0' AS DADFlag,
  a.DropNum,
  a.ReportOrderID,/*DropOrderID: DOxxxxxx, ReturnOrderID:ROxxxxxx,TransferOrderID:TRxxxxxxx,ReceiveOrderID:RCxxxxx*/
  CASE WHEN a.ReportOrderID LIKE 'DO%' THEN a.ReportOrderID ELSE ''
  END AS DropOrderID,
  '' AS ReturnOrderID,
  a.LastModifyTime,
  a.Remark 
FROM PTReadyPackage a Left join TBTerminal c on a.TerminalNo = c.TerminalNo,
  PMPostman h,
  PADictionary b,
  IMCompany e,
  IMZone f /*,
  IMPostalProcessingCenter g*/
WHERE (a.ItemStatus = '16' OR a.ItemStatus='4') 
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
  c.TerminalName,
  a.BoxNo,
  ISNULL(a.DropAgentID,'') AS DropAgentID,
  ISNULL(a.ReturnAgentID,'') AS ReturnAgentID,
  a.ReturnAgentID AS PostmanID,/*history： Return Agent*/
  h.PostmanName,
  a.StoredTime,
  a.StoredDate,
  ISNULL(a.ExpiredTime,CONVERT(DATETIME,'1900-01-01',20)) AS ExpiredTime,
  a.DADFlag,
  a.DropNum,
  a.ReportOrderID,/*DropOrderID: DOxxxxxx, ReturnOrderID:ROxxxxxx,TransferOrderID:TRxxxxxxx,ReceiveOrderID:RCxxxxx*/
  '' AS DropOrderID,
  CASE WHEN a.ReportOrderID LIKE 'RO%' THEN a.ReportOrderID ELSE ''
  END AS ReturnOrderID,
  a.LastModifyTime,
  a.Remark
FROM PTDeliverHistory a, 
  TBTerminal c,
  PMPostman h ,
  PADictionary b,
  IMCompany e,
  IMZone f/*,
  IMPostalProcessingCenter g*/
WHERE  a.ItemStatus ='10'
AND  c.TerminalNo = a.TerminalNo
AND  f.ZoneID=a.ZoneID
AND  e.CompanyID  = f.CompanyID
/*AND  g.PPCID = a.PPCID*/
AND  h.PostmanID = a.ReturnAgentID
AND  b.DictTypeID = 33003
AND  b.DictID = a.ItemStatus
GO


/*===================================================================
*pane4 5 - Dropped, 6 - D-Dropped, 8 - Expired, 9 - M-Expired
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_PTPane4')
DROP VIEW V_PTPane4;
GO
CREATE VIEW V_PTPane4 AS
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
  a.ReportOrderID,/*DropOrderID: DOxxxxxx, ReturnOrderID:ROxxxxxx,TransferOrderID:TRxxxxxxx,ReceiveOrderID:RCxxxxx*/
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
WHERE d.TerminalNo = a.TerminalNo
AND   f.ZoneID=a.ZoneID
AND   e.CompanyID  = f.CompanyID
AND   h.PostmanID = a.DropAgentID
/*AND   g.PPCID = a.PPCID*/
AND   b.DictTypeID = 33003
AND   b.DictID = a.ItemStatus;
GO

/*===================================================================
*pane7 4 - Intransit-Out
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
WHERE (a.TerminalNo = c.TerminalNo and c.TerminalType='1')/* */
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
WHERE (d.TerminalNo = a.TerminalNo and d.TerminalType='1')/**/
AND   f.ZoneID=a.ZoneID
AND   e.CompanyID  = f.CompanyID
AND   h.PostmanID = a.DropAgentID
/*AND   g.PPCID = a.PPCID*/
AND   b.DictTypeID = 33003
AND   b.DictID = a.ItemStatus;
GO


/*===================================================================
*pane5 12 - Missing,11 - Return
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_PTPane5')
DROP VIEW V_PTPane5;
GO
CREATE VIEW V_PTPane5 AS
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
  ISNULL(d.TerminalName,'') AS TerminalName,
  a.BoxNo,
  ISNULL(a.DropAgentID,'') AS DropAgentID,
  ISNULL(a.ReturnAgentID,'') AS ReturnAgentID,
  ISNULL(a.ReturnAgentID,'') AS PostmanID,/*history： Return Agent*/
  ISNULL(h.PostmanName,'') AS PostmanName,
  ISNULL(a.StoredTime,CONVERT(DATETIME,'1900-01-01',20)) AS StoredTime,
  ISNULL(a.StoredDate,CONVERT(DATE,'1900-01-01',23)) AS StoredDate,
  ISNULL(a.ExpiredTime,CONVERT(DATETIME,'1900-01-01',20)) AS ExpiredTime,
  CONVERT(DATETIME,'1900-01-01',20) AS ReminderDateTime,
  ISNULL(a.TakedTime,CONVERT(DATETIME,'1900-01-01',20)) AS TakedTime,
  a.DADFlag,
  a.DropNum,
  a.ReportOrderID,/*DropOrderID: DOxxxxxx, ReturnOrderID:ROxxxxxx,TransferOrderID:TRxxxxxxx,ReceiveOrderID:RCxxxxx*/
  CASE WHEN a.ReportOrderID LIKE 'DO%' THEN a.ReportOrderID ELSE ''
  END AS DropOrderID,
  CASE WHEN a.ReportOrderID LIKE 'RO%' THEN a.ReportOrderID ELSE ''
  END AS ReturnOrderID,
  a.LastModifyTime,
  a.Remark
FROM PTDeliverHistory a 
  LEFT OUTER JOIN TBTerminal d ON(d.TerminalNo = a.TerminalNo)
  LEFT OUTER JOIN PMPostman h ON(h.PostmanID = a.ReturnAgentID),
  PADictionary b,
  IMCompany e,
  IMZone f/*,
  IMPostalProcessingCenter g*/
WHERE  a.ItemStatus in('11','12')
AND a.RunStatus='0'
AND  f.ZoneID=a.ZoneID
AND  e.CompanyID  = f.CompanyID
/*AND  g.PPCID = a.PPCID*/
AND  b.DictTypeID = 33003
AND  b.DictID = a.ItemStatus
go

/*===================================================================
*V_PTDeliveryRecord 
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_PTDeliveryRecord')
DROP VIEW V_PTDeliveryRecord;
GO
CREATE VIEW V_PTDeliveryRecord AS
SELECT
  a.PackageID,
  a.CreateTime,
  a.ItemStatus,
  b.DictName AS ItemStatusName,
  '0' AS RunStatus,
  'Running' AS RunStatusName,
  a.PPCID,
  g.PPCName,
  a.ZoneID,
  f.ZoneName,
  f.CompanyID,
  e.CompanyName,
  a.RefNo,
  a.CustomerID,
  a.CustomerName,
  a.CustomerAddress,
  a.CustomerMobile,
  ISNULL(a.TerminalNo,'') AS TerminalNo,
  ISNULL(c.TerminalName,'')  AS TerminalName,
  ISNULL(a.BoxType,'')  AS BoxType,
  ISNULL(d.BoxTypeName,'')  AS BoxTypeName,
  ISNULL(a.BoxNo,'0') AS BoxNo,
  ISNULL(a.DropAgentID,'') AS DropAgentID,
  '' AS ReturnAgentID,
  ISNULL(a.DropAgentID,'') AS PostmanID,
  ISNULL(h.PostmanName,'') AS PostmanName,
  CONVERT(DATETIME,'1900-01-01',20) AS StoredTime,
  CONVERT(DATE,'1900-01-01',23) AS StoredDate,
  CONVERT(DATETIME,'1900-01-01',20) AS ExpiredTime,
  CONVERT(DATETIME,'1900-01-01',20) AS ReminderDateTime,
  CONVERT(DATETIME,'1900-01-01',20) AS TakedTime,
  '' AS TradeWaterNo,
  '' AS PosPayFlag,
  '' AS UploadFlag,
  '' AS UploadFlagName,
  '' AS DADFlag,
  a.DropNum,
  a.ReportOrderID,/*DropOrderID: DOxxxxxx, ReturnOrderID:ROxxxxxx,TransferOrderID:TRxxxxxxx,ReceiveOrderID:RCxxxxx*/
  CASE WHEN a.ReportOrderID LIKE 'DO%' THEN a.ReportOrderID ELSE ''
  END AS DropOrderID,
  '' AS ReturnOrderID,
  a.LastModifyTime,
  a.Remark
FROM PTReadyPackage a 
    LEFT OUTER JOIN TBTerminal c ON(c.TerminalNo = a.TerminalNo)
    LEFT OUTER JOIN TBBoxType d ON(d.BoxType = a.BoxType)
    LEFT OUTER JOIN PMPostman h ON(h.PostmanID = a.DropAgentID),
    PADictionary b,
    IMCompany e,
    IMZone f,
    IMPostalProcessingCenter g
WHERE a.ZoneID=f.ZoneID
AND    f.CompanyID  = e.CompanyID
AND    g.PPCID = a.PPCID
AND    b.DictTypeID = 33003
AND    b.DictID = a.ItemStatus
UNION ALL
SELECT
  a.PackageID,
  a.CreateTime,
  a.ItemStatus,
  b.DictName AS ItemStatusName,
  '0' AS RunStatus,
  'Running' AS RunStatusName,
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
  '' AS BoxType,
  '' AS BoxTypeName,
  a.BoxNo,
  ISNULL(a.DropAgentID,'') AS DropAgentID,
  '' AS ReturnAgentID,
  ISNULL(a.DropAgentID,'') AS PostmanID,
  ISNULL(h.PostmanName,'') AS PostmanName,
  ISNULL(a.StoredTime,CONVERT(DATETIME,'1900-01-01',20)) AS StoredTime,
  ISNULL(a.StoredDate,CONVERT(DATE,'1900-01-01',23)) AS StoredDate,
  ISNULL(a.ExpiredTime,CONVERT(DATETIME,'1900-01-01',20)) AS ExpiredTime,
  ISNULL(a.ReminderDateTime,CONVERT(DATETIME,'1900-01-01',20)) AS ReminderDateTime,
  CONVERT(DATETIME,'1900-01-01',20) AS TakedTime,
  a.TradeWaterNo,
  a.PosPayFlag,
  a.UploadFlag,
  '' AS UploadFlagName,
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
    IMZone f
WHERE d.TerminalNo = a.TerminalNo
AND   f.ZoneID=a.ZoneID
AND   e.CompanyID  = f.CompanyID
AND   h.PostmanID = a.DropAgentID
AND   b.DictTypeID = 33003
AND   b.DictID = a.ItemStatus
UNION ALL
SELECT
  a.PackageID,
  a.CreateTime,
  a.ItemStatus,
  b.DictName AS ItemStatusName,
  a.RunStatus AS RunStatus,
  c.DictName  AS RunStatusName,
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
  ISNULL(d.TerminalName,'') AS TerminalName,
  '' AS BoxType,
  '' AS BoxTypeName,
  a.BoxNo,
  ISNULL(a.DropAgentID,'') AS DropAgentID,
  ISNULL(a.ReturnAgentID,'') AS ReturnAgentID,
  ISNULL(a.ReturnAgentID,'') AS PostmanID,/*history： Return Agent*/
  ISNULL(h.PostmanName,'') AS PostmanName,
  ISNULL(a.StoredTime,CONVERT(DATETIME,'1900-01-01',20)) AS StoredTime,
  ISNULL(a.StoredDate,CONVERT(DATE,'1900-01-01',23)) AS StoredDate,
  ISNULL(a.ExpiredTime,CONVERT(DATETIME,'1900-01-01',20)) AS ExpiredTime,
  CONVERT(DATETIME,'1900-01-01',20) AS ReminderDateTime,
  ISNULL(a.TakedTime,CONVERT(DATETIME,'1900-01-01',20)) AS TakedTime,
  a.TradeWaterNo,
  a.PosPayFlag,
  a.UploadFlag,
  '' AS UploadFlagName,
  a.DADFlag,
  a.DropNum,
  a.ReportOrderID,
  CASE WHEN a.ReportOrderID LIKE 'DO%' THEN a.ReportOrderID ELSE ''
  END AS DropOrderID,
  CASE WHEN a.ReportOrderID LIKE 'RO%' THEN a.ReportOrderID ELSE ''
  END AS ReturnOrderID,
  a.LastModifyTime,
  a.Remark
FROM PTDeliverHistory a 
		LEFT OUTER JOIN TBTerminal d ON(d.TerminalNo = a.TerminalNo)
		LEFT OUTER JOIN PMPostman h ON(h.PostmanID = a.ReturnAgentID),
    PADictionary b,
    PADictionary c,
    IMCompany e,
    IMZone f
WHERE  f.ZoneID=a.ZoneID
AND   e.CompanyID  = f.CompanyID
AND    b.DictTypeID = 33003
AND    b.DictID = a.ItemStatus
AND    c.DictTypeID = 33071
AND    c.DictID = a.RunStatus
;
GO
/*SELECT
  a.PackageID,
  a.CreateTime,
  a.ItemStatus,
  b.DictName AS ItemStatusName,
  '0' AS RunStatus,
  a.PPCID,
  '' AS PPCName,
  a.ZoneID,
  f.ZoneName,
  f.CompanyID,
  e.CompanyName,
  a.RefNo,
  a.CustomerID,
  a.CustomerMobile,
  a.TerminalNo,
  d.TerminalName,
  a.BoxNo,
  ISNULL(a.DropAgentID,'') AS PostmanID,
  ISNULL(h.PostmanName,'') AS PostmanName,
  ISNULL(a.StoredTime,CONVERT(DATETIME,'1900-01-01',20)) AS StoredTime,
  ISNULL(a.StoredDate,CONVERT(DATE,'1900-01-01',23)) AS StoredDate,
  ISNULL(a.ExpiredTime,CONVERT(DATETIME,'1900-01-01',20)) AS ExpiredTime,
  ISNULL(a.ReminderDateTime,CONVERT(DATETIME,'1900-01-01',20)) AS ReminderDateTime,
  CONVERT(DATETIME,'1900-01-01',20) AS TakedTime,
  a.DADFlag,
  a.DropNum,
  a.LastModifyTime,
  a.Remark
FROM PTInBoxPackage a,
    TBTerminal d,
    PMPostman h,
    PADictionary b,
    IMCompany e,
    IMZone f
WHERE d.TerminalNo = a.TerminalNo
AND   f.ZoneID=a.ZoneID
AND   e.CompanyID  = f.CompanyID
AND   h.PostmanID = a.DropAgentID
AND   b.DictTypeID = 33003
AND   b.DictID = a.ItemStatus
UNION ALL
SELECT
  a.PackageID,
  a.CreateTime,
  a.ItemStatus,
  b.DictName AS ItemStatusName,
  a.RunStatus,
  a.PPCID,
  '' AS PPCName,
  a.ZoneID,
  f.ZoneName,
  f.CompanyID,
  e.CompanyName,
  a.RefNo,
  a.CustomerID,
  a.CustomerMobile,
  a.TerminalNo,
  ISNULL(d.TerminalName,'') AS TerminalName,
  a.BoxNo,
  ISNULL(a.ReturnAgentID,'') AS PostmanID,
  ISNULL(h.PostmanName,'') AS PostmanName,
  ISNULL(a.StoredTime,CONVERT(DATETIME,'1900-01-01',20)) AS StoredTime,
  ISNULL(a.StoredDate,CONVERT(DATE,'1900-01-01',23)) AS StoredDate,
  ISNULL(a.ExpiredTime,CONVERT(DATETIME,'1900-01-01',20)) AS ExpiredTime,
  CONVERT(DATETIME,'1900-01-01',20) AS ReminderDateTime,
  ISNULL(a.TakedTime,CONVERT(DATETIME,'1900-01-01',20)) AS TakedTime,
  a.DADFlag,
  a.DropNum,
  a.LastModifyTime,
  a.Remark
FROM PTDeliverHistory a 
  LEFT OUTER JOIN TBTerminal d ON(d.TerminalNo = a.TerminalNo)
  LEFT OUTER JOIN PMPostman h ON(h.PostmanID = a.ReturnAgentID),
  PADictionary b,
  IMCompany e,
  IMZone f
WHERE  f.ZoneID=a.ZoneID
AND   e.CompanyID  = f.CompanyID
AND    b.DictTypeID = 33003
AND    b.DictID = a.ItemStatus
*/

GO



/*===================================================================
*投待投递订单记录
*
=====================================================================*/

IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_PTReadyItems')
DROP VIEW V_PTReadyItems;
GO
CREATE VIEW V_PTReadyItems AS
SELECT
  a.PackageID,
  a.CreateTime,
  a.ItemStatus,
  b.DictName AS ItemStatusName,
  '0' AS RunStatus,
  'Running' AS RunStatusName,
  a.PPCID,
  g.PPCName,
  a.ZoneID,
  f.ZoneName,
  f.CompanyID,
  e.CompanyName,
  a.RefNo,
  a.CustomerID,
  a.CustomerName,
  a.CustomerAddress,
  a.CustomerMobile,
  ISNULL(a.TerminalNo,'') AS TerminalNo,
  ISNULL(c.TerminalName,'')  AS TerminalName,
  ISNULL(a.BoxType,'')  AS BoxType,
  ISNULL(d.BoxTypeName,'')  AS BoxTypeName,
  ISNULL(a.BoxNo,'0') AS BoxNo,
  ISNULL(a.DropAgentID,'') AS DropAgentID,
  '' AS ReturnAgentID,
  ISNULL(a.DropAgentID,'') AS PostmanID,
  ISNULL(h.PostmanName,'') AS PostmanName,
  CONVERT(DATETIME,'1900-01-01',20) AS StoredTime,
  CONVERT(DATE,'1900-01-01',23) AS StoredDate,
  CONVERT(DATETIME,'1900-01-01',20) AS ExpiredTime,
  CONVERT(DATETIME,'1900-01-01',20) AS ReminderDateTime,
  CONVERT(DATETIME,'1900-01-01',20) AS TakedTime,
  '' AS TradeWaterNo,
  '' AS PosPayFlag,
  '' AS UploadFlag,
  '' AS UploadFlagName,
  '' AS DADFlag,
  a.DropNum,
  a.ReportOrderID,/*DropOrderID: DOxxxxxx, ReturnOrderID:ROxxxxxx,TransferOrderID:TRxxxxxxx,ReceiveOrderID:RCxxxxx*/
  CASE WHEN a.ReportOrderID LIKE 'DO%' THEN a.ReportOrderID ELSE ''
  END AS DropOrderID,
  '' AS ReturnOrderID,
  a.LastModifyTime,
  a.Remark
FROM PTReadyPackage a 
    LEFT OUTER JOIN TBTerminal c ON(c.TerminalNo = a.TerminalNo)
    LEFT OUTER JOIN TBBoxType d ON(d.BoxType = a.BoxType)
    LEFT OUTER JOIN PMPostman h ON(h.PostmanID = a.DropAgentID),
    PADictionary b,
    IMCompany e,
    IMZone f,
    IMPostalProcessingCenter g
WHERE a.ZoneID=f.ZoneID
AND    f.CompanyID  = e.CompanyID
AND    g.PPCID = a.PPCID
AND    b.DictTypeID = 33003
AND    b.DictID = a.ItemStatus;
GO



/*===================================================================
*投递在箱订单记录
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_PTInBoxItems')
DROP VIEW V_PTInBoxItems;
GO
CREATE VIEW V_PTInBoxItems AS
SELECT
  a.PackageID,
  a.CreateTime,
  a.ItemStatus,
  b.DictName AS ItemStatusName,
  '0' AS RunStatus,
  'Running' AS RunStatusName,
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
  '' AS BoxType,
  '' AS BoxTypeName,
  a.BoxNo,
  ISNULL(a.DropAgentID,'') AS DropAgentID,
  '' AS ReturnAgentID,
  ISNULL(a.DropAgentID,'') AS PostmanID,
  ISNULL(h.PostmanName,'') AS PostmanName,
  ISNULL(a.StoredTime,CONVERT(DATETIME,'1900-01-01',20)) AS StoredTime,
  ISNULL(a.StoredDate,CONVERT(DATE,'1900-01-01',23)) AS StoredDate,
  ISNULL(a.ExpiredTime,CONVERT(DATETIME,'1900-01-01',20)) AS ExpiredTime,
  ISNULL(a.ReminderDateTime,CONVERT(DATETIME,'1900-01-01',20)) AS ReminderDateTime,
  CONVERT(DATETIME,'1900-01-01',20) AS TakedTime,
  a.TradeWaterNo,
  a.PosPayFlag,
  a.UploadFlag,
  '' AS UploadFlagName,
  a.DADFlag,
  a.DropNum,
  a.ReportOrderID,/*DropOrderID: DOxxxxxx, ReturnOrderID:ROxxxxxx,TransferOrderID:TRxxxxxxx,ReceiveOrderID:RCxxxxx*/
  CASE WHEN a.ReportOrderID LIKE 'DO%' THEN a.ReportOrderID ELSE ''
  END AS DropOrderID,
  '' AS ReturnOrderID,
  a.LastModifyTime,
  a.Remark
FROM PTInBoxPackage a,/*LEFT OUTER JOIN PMPostman h ON(h.PostmanID = a.DropAgentID)*/
    TBTerminal d,
    PMPostman h,
    PADictionary b,
    IMCompany e,
    IMZone f
    /*,IMPostalProcessingCenter g*/
WHERE d.TerminalNo = a.TerminalNo
AND   f.ZoneID=a.ZoneID
AND   e.CompanyID  = f.CompanyID
AND   h.PostmanID = a.DropAgentID
/*AND   g.PPCID = a.PPCID*/
AND   b.DictTypeID = 33003
AND   b.DictID = a.ItemStatus;
GO


/*===================================================================
*投递历史订单记录
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_PTHistoryItems')
DROP VIEW V_PTHistoryItems;
GO
CREATE VIEW V_PTHistoryItems AS
SELECT
  a.PackageID,
  a.CreateTime,
  a.ItemStatus,
  b.DictName AS ItemStatusName,
  a.RunStatus AS RunStatus,
  c.DictName  AS RunStatusName,
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
  ISNULL(d.TerminalName,'') AS TerminalName,
  '' AS BoxType,
  '' AS BoxTypeName,
  a.BoxNo,
  ISNULL(a.DropAgentID,'') AS DropAgentID,
  ISNULL(a.ReturnAgentID,'') AS ReturnAgentID,
  ISNULL(a.ReturnAgentID,'') AS PostmanID,/*history： Return Agent*/
  ISNULL(h.PostmanName,'') AS PostmanName,
  ISNULL(a.StoredTime,CONVERT(DATETIME,'1900-01-01',20)) AS StoredTime,
  ISNULL(a.StoredDate,CONVERT(DATE,'1900-01-01',23)) AS StoredDate,
  ISNULL(a.ExpiredTime,CONVERT(DATETIME,'1900-01-01',20)) AS ExpiredTime,
  CONVERT(DATETIME,'1900-01-01',20) AS ReminderDateTime,
  ISNULL(a.TakedTime,CONVERT(DATETIME,'1900-01-01',20)) AS TakedTime,
  a.TradeWaterNo,
  a.PosPayFlag,
  a.UploadFlag,
  '' AS UploadFlagName,
  a.DADFlag,
  a.DropNum,
  a.ReportOrderID,/*DropOrderID: DOxxxxxx, ReturnOrderID:ROxxxxxx,TransferOrderID:TRxxxxxxx,ReceiveOrderID:RCxxxxx*/
  CASE WHEN a.ReportOrderID LIKE 'DO%' THEN a.ReportOrderID ELSE ''
  END AS DropOrderID,
  CASE WHEN a.ReportOrderID LIKE 'RO%' THEN a.ReportOrderID ELSE ''
  END AS ReturnOrderID,
  a.LastModifyTime,
  a.Remark
FROM PTDeliverHistory a 
		LEFT OUTER JOIN TBTerminal d ON(d.TerminalNo = a.TerminalNo)
		LEFT OUTER JOIN PMPostman h ON(h.PostmanID = a.ReturnAgentID),
    PADictionary b,
    PADictionary c,
    IMCompany e,
    IMZone f
    /*,IMPostalProcessingCenter g*/
WHERE  f.ZoneID=a.ZoneID
AND   e.CompanyID  = f.CompanyID
/*AND   g.PPCID = a.PPCID*/
AND    b.DictTypeID = 33003
AND    b.DictID = a.ItemStatus
AND    c.DictTypeID = 33071
AND    c.DictID = a.RunStatus
;
GO

IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_SimpleHistoryItems')
DROP VIEW V_SimpleHistoryItems;
GO
CREATE VIEW V_SimpleHistoryItems AS
SELECT
  a.PackageID,
  a.CreateTime,
  a.ItemStatus,
 	a.RunStatus AS RunStatus,
  a.PPCID,
  a.ZoneID,
  a.CustomerID,
  a.CustomerName,
  a.CustomerAddress,
  a.CustomerMobile,
  a.TerminalNo,
 	a.BoxNo,
  c.BoxTypeName
FROM PTDeliverHistory a,
    TBServerBox b,
    TBBoxType c
WHERE b.TerminalNo = a.TerminalNo
AND   b.BoxNo = a.BoxNo
AND   c.BoxType = b.BoxType
;
GO

/*===================================================================
*所有的投递记录
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_PTAllItems')
DROP VIEW V_PTAllItems;
GO
CREATE VIEW V_PTAllItems AS
SELECT
  a.PackageID,
  a.CreateTime,
  a.ItemStatus,
  a.ItemStatusName,
  a.RunStatus,
  a.RunStatusName,
  a.PPCID,
  a.PPCName,
  a.ZoneID,
  a.ZoneName,
  a.CompanyID,
  a.CompanyName,
  a.RefNo,
  a.CustomerID,
  a.CustomerName,
  a.CustomerAddress,
  a.CustomerMobile,
  a.TerminalNo,
  a.TerminalName,
  a.BoxType,
  a.BoxTypeName,
  a.BoxNo,
  a.DropAgentID,
  a.ReturnAgentID,
  a.PostmanID,
  a.PostmanName,
  a.StoredTime,
  a.StoredDate,
  a.ExpiredTime,
  a.ReminderDateTime,
  a.TakedTime,
  a.TradeWaterNo,
  a.PosPayFlag,
  a.UploadFlag,
  a.UploadFlagName,
  a.DADFlag,
  a.DropNum,
  a.ReportOrderID,
  a.DropOrderID,
  a.ReturnOrderID,
  a.LastModifyTime,
  a.Remark
FROM V_PTReadyItems a
UNION ALL
SELECT
  a.PackageID,
  a.CreateTime,
  a.ItemStatus,
  a.ItemStatusName,
  a.RunStatus,
  a.RunStatusName,
  a.PPCID,
  a.PPCName,
  a.ZoneID,
  a.ZoneName,
  a.CompanyID,
  a.CompanyName,
  a.RefNo,
  a.CustomerID,
  a.CustomerName,
  a.CustomerAddress,
  a.CustomerMobile,
  a.TerminalNo,
  a.TerminalName,
  a.BoxType,
  a.BoxTypeName,
  a.BoxNo,
  a.DropAgentID,
  a.ReturnAgentID,
  a.PostmanID,
  a.PostmanName,
  a.StoredTime,
  a.StoredDate,
  a.ExpiredTime,
  a.ReminderDateTime,
  a.TakedTime,
  a.TradeWaterNo,
  a.PosPayFlag,
  a.UploadFlag,
  a.UploadFlagName,
  a.DADFlag,
  a.DropNum,
  a.ReportOrderID,
  a.DropOrderID,
  a.ReturnOrderID,
  a.LastModifyTime,
  a.Remark
FROM V_PTInboxItems a
UNION ALL
SELECT
  a.PackageID,
  a.CreateTime,
  a.ItemStatus,
  a.ItemStatusName,
  a.RunStatus,
  a.RunStatusName,
  a.PPCID,
  a.PPCName,
  a.ZoneID,
  a.ZoneName,
  a.CompanyID,
  a.CompanyName,
  a.RefNo,
  a.CustomerID,
  a.CustomerName,
  a.CustomerAddress,
  a.CustomerMobile,
  a.TerminalNo,
  a.TerminalName,
  a.BoxType,
  a.BoxTypeName,
  a.BoxNo,
  a.DropAgentID,
  a.ReturnAgentID,
  a.PostmanID,
  a.PostmanName,
  a.StoredTime,
  a.StoredDate,
  a.ExpiredTime,
  a.ReminderDateTime,
  a.TakedTime,
  a.TradeWaterNo,
  a.PosPayFlag,
  a.UploadFlag,
  a.UploadFlagName,
  a.DADFlag,
  a.DropNum,
  a.ReportOrderID,
  a.DropOrderID,
  a.ReturnOrderID,
  a.LastModifyTime,
  a.Remark
FROM V_PTHistoryItems a;
GO


/*===================================================================
*订单报表打印视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_PTItemPrintReport')
DROP VIEW V_PTItemPrintReport;
GO
CREATE VIEW V_PTItemPrintReport AS
SELECT
  a.PackageID,
  a.CreateTime,
  a.ItemStatus,
  a.PrintedFlag,
  a.ReportOrderID
FROM PTReadyPackage a 
UNION ALL
SELECT
  a.PackageID,
  a.CreateTime,
  a.ItemStatus,
  a.PrintedFlag,
  a.ReportOrderID
FROM PTInBoxPackage a 
UNION ALL
SELECT
  a.PackageID,
  a.CreateTime,
  a.ItemStatus,
  a.PrintedFlag,
  a.ReportOrderID
FROM PTDeliverHistory a 
;
GO

/*===================================================================
*转移订单信息视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_DeliverItemTransfer')
DROP VIEW V_DeliverItemTransfer;
GO
CREATE VIEW V_DeliverItemTransfer AS
SELECT
	a.WaterID,
  a.PackageID,
  a.CreateTime,
  a.ZoneID,
  b.ZoneName,
  a.PPCID,
  a.ItemStatus,
  c.PPCName,
  a.TransferType AS ItemType,
  d.DictName AS ItemTypeName,
  a.TransferStatus AS SendStatus,
 	f.DictName  AS SendStatusName,
  a.TransferOrderID,
  a.ReSendNum,
  a.Remark
FROM PTDeliverItemTransfer a,
    IMZone b,
    IMPostalProcessingCenter c,
    PADictionary d,
    PADictionary f
WHERE b.ZoneID = a.ZoneID
AND   c.PPCID = a.PPCID
AND d.DictTypeID = 33074
AND d.DictID = a.TransferType
AND f.DictTypeID = 33076
AND f.DictID = a.TransferStatus
;
GO

/*===================================================================
*订单发送流水视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_PTTransferItemWater')
DROP VIEW V_PTTransferItemWater;
GO
CREATE VIEW V_PTTransferItemWater AS
SELECT
	a.WaterID,
  a.PackageID,
  a.CreateTime,
  a.ZoneID,
  b.ZoneName,
  a.PPCID,
  a.ItemStatus,
  c.PPCName,
  a.TransferType AS ItemType,
  d.DictName AS ItemTypeName,
  a.TransferStatus AS SendStatus,
 	f.DictName  AS SendStatusName,
  a.TransferID AS TransferOrderID,
  a.ReSendNum,
  a.Remark
FROM PTTransferItemWater a,
    IMZone b,
    IMPostalProcessingCenter c,
    PADictionary d,
    PADictionary f
WHERE b.ZoneID = a.ZoneID
AND   c.PPCID = a.PPCID
AND d.DictTypeID = 33074
AND d.DictID = a.TransferType
AND f.DictTypeID = 33076
AND f.DictID = a.TransferStatus
;
GO

/*===================================================================
*投递订单状态视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_PTItemStatus')
DROP VIEW V_PTItemStatus;
GO
CREATE VIEW V_PTItemStatus AS
SELECT
  a.PackageID,
  a.CreateTime,
  CAST(a.ItemStatus AS INT) AS ItemStatus,
  b.DictName AS ItemStatusName,
  '0' AS RunStatus,
  'Running' AS RunStatusName,
  a.CompanyID,
  a.PPCID,
  a.ZoneID,
  CONVERT(DATETIME,'1900-01-01',20) AS StoredTime,
  a.TerminalNo,
  '0' AS DADFlag,
  ISNULL(a.LastModifyTime, a.CreateTime) AS LastModifyTime
FROM PTReadyPackage a,
  PADictionary b
WHERE  b.DictTypeID = 33003
AND    b.DictID = a.ItemStatus 
UNION ALL
SELECT
  a.PackageID,
  a.CreateTime,
  CAST(a.ItemStatus AS INT) AS ItemStatus,
  b.DictName AS ItemStatusName,
  '0' AS RunStatus,
  'Running' AS RunStatusName,
  a.CompanyID,
  a.PPCID,
  a.ZoneID,
  a.StoredTime,
  a.TerminalNo,
  CASE WHEN a.DADFlag<>'1' THEN '0' ELSE '1' END AS DADFlag,
  a.LastModifyTime
FROM PTInBoxPackage a,
  PADictionary b
WHERE  b.DictTypeID = 33003
AND    b.DictID = a.ItemStatus 
UNION ALL
SELECT
  a.PackageID,
  a.CreateTime,
  CAST(a.ItemStatus AS INT) AS ItemStatus,
  b.DictName AS ItemStatusName,
  a.RunStatus AS RunStatus,
  c.DictName  AS RunStatusName,
  a.CompanyID,
  a.PPCID,
  a.ZoneID,
  ISNULL(a.StoredTime,CONVERT(DATETIME,'1900-01-01',20)) AS StoredTime,
  a.TerminalNo,
  CASE WHEN a.DADFlag<>'1' THEN '0' ELSE '1' END AS DADFlag,
  a.LastModifyTime
FROM PTDeliverHistory a,
  PADictionary b,
  PADictionary c
WHERE  b.DictTypeID = 33003
AND    b.DictID = a.ItemStatus
AND    c.DictTypeID = 33071
AND    c.DictID = a.RunStatus;
GO

