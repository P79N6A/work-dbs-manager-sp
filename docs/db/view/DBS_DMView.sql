/*===================================================================
*寄件订单记录
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_DMDeliveryItems')
DROP VIEW V_DMDeliveryItems;
GO
CREATE VIEW V_DMDeliveryItems AS
SELECT
  a.PackageID,
  a.CreateTime,
  a.ItemStatus,
  b.DictName AS ItemStatusName,
  a.BPartnerID,
  d.BPartnerName,
  d.Address,
  d.Mobile,
  a.ServiceID,
  e.ServiceName,
  a.ServiceAmt,
  a.CollectionFlag,
  CASE WHEN a.CollectionFlag='1' THEN 'YES' ELSE 'NO'
  END AS CollectionFlagName,
  a.CollectionAmt,
  a.ReturnFlag,
  CASE WHEN a.ReturnFlag='1' THEN 'YES' ELSE 'NO'
  END AS ReturnFlagName,
  a.TradeWaterNo,
  a.CompanyID,
  a.ZoneID,
  c.ZoneName,
  a.PPCID,
  h.PPCName,
  a.CustomerID,
  a.CustomerName,
  a.CustomerAddress,
  a.CustomerMobile,
  a.ParcelSize,
  f.BoxTypeName AS ParcelSizeName,
  a.TerminalNo,
  l.TerminalName,
  l.OfBureau,
  a.BoxNo,
  a.CollectZoneID,
  i.CollectZoneName,
  ISNULL(a.CollectionAgentID,'') AS PostmanID,
  g.PostmanName AS PostmanName,
  ISNULL(a.CollectionTime,CONVERT(DATETIME,'1900-01-01',20)) AS CollectionTime,
  CASE WHEN a.ReportOrderID LIKE 'TR%' THEN a.ReportOrderID ELSE ''
  END AS TransferOrderID,
  a.LastModifyTime,
  /*CASE WHEN (a.PrintedFlag&8)=8 THEN '1' ELSE '0'
	END AS PrintedR4,
	CASE WHEN (a.PrintedFlag&16)=16 THEN '1' ELSE '0'
	END AS PrintedR5,*/
  a.Remark
FROM DMCollectionParcel a 
  LEFT JOIN IMPostalProcessingCenter  h ON(h.PPCID=a.PPCID)
  LEFT OUTER JOIN PMPostman g ON(g.PostmanID = a.CollectionAgentID)
  LEFT OUTER JOIN TBTerminal l ON(l.TerminalNo = a.TerminalNo),
  IMCollectZone i,
  IMZone c,
  IMBusinessPartner d,
  IMServiceRate e,
  TBBoxType f,
	PADictionary b
WHERE b.DictTypeID = 34003
	AND b.DictID = a.ItemStatus
	AND c.ZoneID = a.ZoneID
	AND d.BPartnerID = a.BPartnerID
	AND i.CollectZoneID = a.CollectZoneID
	AND e.ServiceID = a.ServiceID
	AND f.BoxType = a.ParcelSize
;
GO
/*==================================================================
*寄件历史订单记录
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_DMHistoryItems')
DROP VIEW V_DMHistoryItems;
GO
CREATE VIEW V_DMHistoryItems AS
SELECT
  a.PackageID,
  a.CreateTime,
  a.ItemStatus,
  b.DictName AS ItemStatusName,
  a.BPartnerID,
  d.BPartnerName,
  d.Address,
  d.Mobile,
  a.ServiceID,
  e.ServiceName,
  a.ServiceAmt,
  a.CollectionFlag,
  CASE WHEN a.CollectionFlag='1' THEN 'YES' ELSE 'NO'
  END AS CollectionFlagName,
  a.CollectionAmt,
  a.ReturnFlag,
  CASE WHEN a.ReturnFlag='1' THEN 'YES' ELSE 'NO'
  END AS ReturnFlagName,
  a.TradeWaterNo,
  a.CompanyID,
  a.ZoneID,
  c.ZoneName,
  a.PPCID,
  h.PPCName,
  a.CustomerID,
  a.CustomerName,
  a.CustomerAddress,
  a.CustomerMobile,
  a.ParcelSize,
  f.BoxTypeName AS ParcelSizeName,
  a.TerminalNo,
  l.TerminalName,
  l.OfBureau,
  a.BoxNo,
  a.CollectZoneID,
  i.CollectZoneName,
  ISNULL(a.CollectionAgentID,'') AS PostmanID,
  g.PostmanName AS PostmanName,
  ISNULL(a.CollectionTime,CONVERT(DATETIME,'1900-01-01',20)) AS CollectionTime,
  CASE WHEN a.ReportOrderID LIKE 'TR%' THEN a.ReportOrderID ELSE ''
  END AS TransferOrderID,
  a.LastModifyTime,
  /*CASE WHEN (a.PrintedFlag&8)=8 THEN '1' ELSE '0'
	END AS PrintedR4,
	CASE WHEN (a.PrintedFlag&16)=16 THEN '1' ELSE '0'
	END AS PrintedR5,*/
  a.Remark
FROM DMHistoryItem a 
  LEFT OUTER JOIN IMPostalProcessingCenter  h ON(h.PPCID=a.PPCID)
  LEFT OUTER JOIN PMPostman g ON(g.PostmanID = a.CollectionAgentID)
  LEFT OUTER JOIN TBTerminal l ON(l.TerminalNo = a.TerminalNo),
  IMCollectZone i,
  IMZone c,
  IMBusinessPartner d,
  IMServiceRate e,
  TBBoxType f,
	PADictionary b
WHERE b.DictTypeID = 34003
	AND b.DictID = a.ItemStatus
	AND c.ZoneID = a.ZoneID
	AND d.BPartnerID = a.BPartnerID
	AND i.CollectZoneID = a.CollectZoneID
	AND e.ServiceID = a.ServiceID
	AND f.BoxType = a.ParcelSize
;
GO


/*===================================================================
*所有的投递记录
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_DMAllDeliveryItems')
DROP VIEW V_DMAllDeliveryItems;
GO
CREATE VIEW V_DMAllDeliveryItems AS
SELECT
  a.PackageID,
  a.CreateTime,
  a.ItemStatus,
  a.ItemStatusName,
  a.BPartnerID,
  a.BPartnerName,
  a.Address,
  a.Mobile,
  a.ServiceID,
  a.ServiceName,
  a.ServiceAmt,
  a.CollectionFlag,
  a.CollectionFlagName,
  a.CollectionAmt,
  a.ReturnFlag,
  a.ReturnFlagName,
  a.TradeWaterNo,
  a.CompanyID,
  a.ZoneID,
  a.ZoneName,
  a.PPCID,
  a.PPCName,
  a.CustomerID,
  a.CustomerName,
  a.CustomerAddress,
  a.CustomerMobile,
  a.ParcelSize,
  a.ParcelSizeName,
  a.TerminalNo,
  a.TerminalName,
  a.OfBureau,
  a.BoxNo,
  a.CollectZoneID,
  a.CollectZoneName,
  a.PostmanID,
  a.PostmanName,
  a.CollectionTime,
  a.TransferOrderID,
  a.LastModifyTime,
  /*a.PrintedR4,
	a.PrintedR5,*/
  a.Remark
FROM V_DMDeliveryItems a
UNION ALL
SELECT
  a.PackageID,
  a.CreateTime,
  a.ItemStatus,
  a.ItemStatusName,
  a.BPartnerID,
  a.BPartnerName,
  a.Address,
  a.Mobile,
  a.ServiceID,
  a.ServiceName,
  a.ServiceAmt,
  a.CollectionFlag,
  a.CollectionFlagName,
  a.CollectionAmt,
  a.ReturnFlag,
  a.ReturnFlagName,
  a.TradeWaterNo,
  a.CompanyID,
  a.ZoneID,
  a.ZoneName,
  a.PPCID,
  a.PPCName,
  a.CustomerID,
  a.CustomerName,
  a.CustomerAddress,
  a.CustomerMobile,
  a.ParcelSize,
  a.ParcelSizeName,
  a.TerminalNo,
  a.TerminalName,
  a.OfBureau,
  a.BoxNo,
  a.CollectZoneID,
  a.CollectZoneName,
  a.PostmanID,
  a.PostmanName,
  a.CollectionTime,
  a.TransferOrderID,
  a.LastModifyTime,
  /*a.PrintedR4,
	a.PrintedR5,*/
  a.Remark
FROM V_DMHistoryItems a;
GO

/*===================================================================
*投递订单状态视图
*
=====================================================================*/

IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_DMItemStatus')
DROP VIEW V_DMItemStatus;
GO
CREATE VIEW V_DMItemStatus AS
SELECT
  a.PackageID,
  a.CreateTime,
  CAST(a.ItemStatus AS INT) AS ItemStatus,
  b.DictName AS ItemStatusName,
  CASE WHEN a.CollectionFlag<>'1' THEN '0' ELSE '1' END AS CollectionFlag,
  CASE WHEN a.ReturnFlag<>'1' THEN '0' ELSE '1' END AS ReturnFlag,
  a.CompanyID,
  a.ZoneID,
  a.PPCID,
  ISNULL(a.LastModifyTime, a.CreateTime) AS LastModifyTime
FROM DMCollectionParcel a, 
  PADictionary b
WHERE b.DictTypeID = 34003
  AND b.DictID = a.ItemStatus
UNION ALL
SELECT
  a.PackageID,
  a.CreateTime,
  CAST(a.ItemStatus AS INT) AS ItemStatus,
  b.DictName AS ItemStatusName,
  CASE WHEN a.CollectionFlag<>'1' THEN '0' ELSE '1' END AS CollectionFlag,
  CASE WHEN a.ReturnFlag<>'1' THEN '0' ELSE '1' END AS ReturnFlag,
  a.CompanyID,
  a.ZoneID,
  a.PPCID,
  a.LastModifyTime
FROM DMHistoryItem a, 
  PADictionary b
WHERE b.DictTypeID = 34003
  AND b.DictID = a.ItemStatus;
GO

