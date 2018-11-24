
/*===================================================================
*  投递员视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_Postman')
DROP VIEW V_Postman;
GO
CREATE VIEW V_Postman AS
SELECT
	a.PostmanID,
	a.PostmanName,
	a.ZoneID,
	b.ZoneName,
	b.CompanyID,
	f.CompanyName,
	f.CompanyType,
	f.MasterFlag,
	a.CollectZoneID,
	d.CollectZoneName,
	a.PostmanType,
	c.DictName AS PostmanTypeName,
	a.OfficeTel,
	a.Mobile,
	a.Email,
	a.BindCardID,
	a.BindMobile,
	a.ContactAddress,
	a.PostmanStatus,
	e.DictName AS PostmanStatusName,
	a.DropRight,
	a.ReturnRight,
	a.CollectRight,
	a.DADRight,
	a.Remark
FROM PMPostman a LEFT JOIN IMCollectZone d ON(d.CollectZoneID = a.CollectZoneID),
    IMZone b,
    IMCompany f,
    PADictionary c,
    PADictionary e
WHERE b.ZoneID = a.ZoneID
AND b.CompanyID = f.CompanyID
AND c.DictTypeID=31001
AND c.DictID=a.PostmanType
AND e.DictTypeID=31021
AND e.DictID=a.PostmanStatus;
GO

