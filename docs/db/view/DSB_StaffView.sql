
/*===================================================================
*  员工视图 包括系统操作员，投递员，商业合作伙伴，个人用户
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_Staff')
DROP VIEW V_Staff;
GO
CREATE VIEW V_Staff AS
SELECT
  a.OperID AS OperID,
  a.OperName AS OperName,
  a.OperType AS OperType,
  c.DictName AS OperTypeName
FROM OPOperator a,
	PADictionary c
WHERE  c.DictTypeID=13005
AND   c.DictID=a.OperType
UNION ALL
SELECT
	a.PostmanID AS OperID,
	a.PostmanName AS OperName,
	a.PostmanType AS OperType,
	c.DictName AS OperTypeName
FROM PMPostman a,
	PADictionary c
WHERE c.DictTypeID=13005
AND c.DictID=a.PostmanType
UNION ALL
SELECT
	a.BPartnerID AS OperID,
	a.BPartnerName AS OperName,
	'71' AS OperType,
	c.DictName AS OperTypeName
FROM IMBusinessPartner a,
	PADictionary c
WHERE c.DictTypeID=13005
AND c.DictID='71'
;
GO

/*===================================================================
*  登录用户信息视图
*  1-Super Admin,
   2-Admin, 
   3-Service Owner,
   4-Business Partner,
   5-eCommerce partner,
   6-Postman,
   7-Individual customer
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_LoginUser')
DROP VIEW V_LoginUser;
GO
CREATE VIEW V_LoginUser AS
SELECT
  a.OperID AS UserID,
  a.UserID AS UserName,/*OPOperator的用户ID作为登录名*/
  a.OperPassword AS UserKey,
  CASE WHEN a.OperType IN(1) THEN '1' 
  WHEN a.OperType IN(2,3) THEN '2'
  ELSE '3'
  END AS UserType/*1-Super Admin,2-Admin,3-Service Owner*/
FROM OPOperator a
UNION ALL
SELECT
	a.BPartnerID AS UserID,
	a.Username   AS UserName,
	a.Password   AS UserKey,
	'4' AS UserType/*4-Business Partner*/
FROM IMBusinessPartner a
UNION ALL
SELECT
	a.EPartnerID AS UserID,
	a.EPartnerID AS UserName,
	a.Password   AS UserKey,
	'5' AS UserType/*"-5-eCommerce partner*/
FROM IMEcommercePartner a
;
GO

/*===================================================================
*  订单历史详情视图
*
=====================================================================*/
/* ==== 派件操作 ==== 
* 系统管理员；操作员；投递员；个人用户对订单进行操作
*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_ItemDetailDrop')
DROP VIEW V_ItemDetailDrop;
GO
CREATE VIEW V_ItemDetailDrop AS
SELECT
  a.WaterID,
  a.PackageID,
  a.ItemStatus,
  b.DictName AS ItemStatusName,
  a.OperatorID AS OperID,
  c.OperName AS OperName,
  a.OperatorType AS OperType,
  c.OperTypeName AS OperTypeName,
  a.LastModifyTime,
  a.Remark
FROM PTItemLifeCycle a,
	PADictionary b,
	V_Staff c
WHERE b.DictTypeID = 33003 
AND b.DictID = a.ItemStatus 
AND c.OperID=a.OperatorID
AND c.OperType=a.OperatorType
UNION ALL
SELECT
  a.WaterID,
  a.PackageID,
  a.ItemStatus,
  b.DictName AS ItemStatusName,
  a.OperatorID AS OperID,
  a.OperatorID AS OperName,
  a.OperatorType AS OperType,
  'Customer' AS OperTypeName,/*个人客户取件*/
  a.LastModifyTime,
  a.Remark
FROM PTItemLifeCycle a,
	PADictionary b
WHERE b.DictTypeID = 33003 
AND b.DictID = a.ItemStatus 
AND a.OperatorType = '70'
UNION ALL
SELECT
  a.WaterID,
  a.PackageID,
  a.ItemStatus,
  b.DictName AS ItemStatusName,
  a.OperatorID AS OperID,
  a.OperatorID AS OperName,
  a.OperatorType AS OperType,
  'System' AS OperTypeName,/*系统操作*/
  a.LastModifyTime,
  a.Remark
FROM PTItemLifeCycle a,
	PADictionary b
WHERE b.DictTypeID = 33003 
AND b.DictID = a.ItemStatus 
AND a.OperatorType = '0'
;
GO

/* ==== 揽件 ==== 
* 系统管理员；操作员；投递员;商业合作伙伴
*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_ItemDetailCollect')
DROP VIEW V_ItemDetailCollect;
GO
CREATE VIEW V_ItemDetailCollect AS
SELECT
  a.WaterID,
  a.PackageID,
  a.ItemStatus,
  b.DictName AS ItemStatusName,
  a.OperatorID AS OperID,
  c.OperName  AS OperName,
  a.OperatorType AS OperType,
  c.OperTypeName AS OperTypeName,
  a.LastModifyTime,
  a.Remark
FROM DMItemLifeCycle a,
	PADictionary b,
	V_Staff c
WHERE b.DictTypeID = 34003 
AND b.DictID = a.ItemStatus 
AND c.OperID=a.OperatorID
AND c.OperType=a.OperatorType
;
GO

/* ==== 订单历史详情 ==== 
*包括揽件订单和派件订单
*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_ItemHistoryDetail')
DROP VIEW V_ItemHistoryDetail;
GO
CREATE VIEW V_ItemHistoryDetail AS
SELECT
  a.WaterID,
  a.PackageID,
  a.ItemStatus,
  a.ItemStatusName,
  a.OperID,
  a.OperName,
  a.OperType,
  a.OperTypeName,
  a.LastModifyTime,
  a.Remark
FROM V_ItemDetailDrop a
UNION ALL
SELECT
  a.WaterID,
  a.PackageID,
  a.ItemStatus,
  a.ItemStatusName,
  a.OperID,
  a.OperName,
  a.OperType,
  a.OperTypeName,
  a.LastModifyTime,
  a.Remark
FROM V_ItemDetailCollect a
;
GO

