
/*===================================================================
*  服务账单视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_PYServiceBillWater')
DROP VIEW V_PYServiceBillWater;
GO
CREATE VIEW V_PYServiceBillWater AS
SELECT
	a.BPartnerID,
	b.BPartnerName,
	a.TradeWaterID,
	a.BillTime,
	a.BillType,
	c.DictName AS BillTypeName,
	a.BillAmt,
	a.Balance,
	a.PackageID,
	a.ServiceID,
	d.ServiceName AS ServiceName,
	a.BillDetails
FROM PYServiceBillWater a LEFT JOIN IMServiceRate d ON(d.ServiceID = a.ServiceID),
    IMBusinessPartner b,
    PADictionary c
WHERE b.BPartnerID = a.BPartnerID
AND c.DictTypeID=41001
AND c.DictID=a.BillType;
GO

