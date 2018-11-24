
/*===================================================================
*  操作员角色视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_OperRole')
DROP VIEW V_OperRole;
GO
CREATE VIEW V_OperRole AS
SELECT
  a.OperID,
  b.UserID,
  b.OperName,
  a.RoleID,
  c.RoleName
FROM OPOperRole a,
     OPOperator b,
     OPRole c
WHERE b.OperID=a.OperID
AND   c.RoleID=a.RoleID;
GO
/*===================================================================
*  操作员视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_Operator')
DROP VIEW V_Operator;
GO
CREATE VIEW V_Operator AS
SELECT
  a.OperID,
  a.UserID,
  a.OperName,
  a.OperType,
  b.DictName AS OperTypeName,
  a.ZoneID,
  d.ZoneName,
  d.CompanyID,
  a.OfficeTel,
  a.Mobile,
  a.Email,
  a.CreateDate,
  a.CreateTime,
  a.UpperUserID,
  a.OperStatus,
  CASE
    WHEN a.OperStatus='0' THEN 'Normal'
    WHEN a.OperStatus='1' THEN 'Cancel'
  END AS OperStatusName,
  a.Remark,
  ISNULL(e.RoleID,0) AS RoleID,
  ISNULL(e.RoleName,'') AS RoleName
FROM OPOperator a LEFT OUTER JOIN IMZone d ON (d.ZoneID=a.ZoneID)
          LEFT OUTER JOIN V_OperRole e ON(e.OperID = a.OperID),
   PADictionary b
WHERE b.DictTypeID=13001
AND   b.DictID=a.OperType;
GO

/*===================================================================
*  操作员菜单视图，包括角色菜单
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_OPOperToMenu')
DROP VIEW V_OPOperToMenu;
GO
CREATE VIEW V_OPOperToMenu AS
SELECT
  a.OperID,
  b.MenuID
FROM OPOperRole a,
     OPRoleToMenu b
WHERE  b.RoleID=a.RoleID
UNION
SELECT
  a.OperID,
  a.MenuID
FROM OPOperToMenu a
GO


/*===================================================================
*  操作员日志视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_OperatorLog')
DROP VIEW V_OperatorLog;
GO
CREATE VIEW V_OperatorLog AS
SELECT
  a.OperLogID,
  b.UserID AS OperID,
  b.OperName,
  a.FunctionID,
  e.FunctionServiceName AS FunctionName,
  a.OccurDate,
  a.OccurTime,
  a.StationAddr,
  a.TerminalNo,
  a.Remark
FROM OPOperatorLog a, 
   OPOperator b,
   OPFunction e
WHERE e.FunctionID = a.FunctionID
  AND b.OperID=a.OperID;
GO
/*SELECT
  a.OperLogID,
  a.OperID,
  a.OperType,
  CASE WHEN a.OperType = 99 THEN c.PostmanName
           ELSE b.OperName
  END AS OperName,
  a.FunctionID,
  e.FunctionServiceName AS FunctionName,
  a.OccurDate,
  a.OccurTime,
  a.StationAddr,
  a.TerminalNo,
  a.Remark
FROM OPOperatorLog a  LEFT OUTER JOIN OPOperator b ON (b.OperID=a.OperID)
          LEFT OUTER JOIN PMPostman c ON(c.PostmanID = a.OperID),
   OPFunction e
WHERE e.FunctionID = a.FunctionID;*/

/*===================================================================
*  操作员在线视图
*
=====================================================================*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_OperOnline')
DROP VIEW V_OperOnline;
GO
CREATE VIEW V_OperOnline AS
SELECT
  a.OperID,
  e.UserID,
  e.OperName,
  a.OperType,
  b.DictName as OperTypeName,
  a.ZoneID,
  d.ZoneName,
  a.LastLoginIP,
  a.LastLoginTime,
  a.PreVersion,
  a.CurrentVersion,
  a.LoginInTime,
  a.LoginOutTime,
  a.NetCardAddr,
  a.LoginIPAddr,
  a.OnlineStatus,
  CASE
    WHEN a.OnlineStatus='0' THEN '不在线'
    WHEN a.OnlineStatus='1' THEN '在线'
  END AS OnlineStatusName
FROM OPOperOnline a  LEFT OUTER JOIN IMZone d ON (d.ZoneID=a.ZoneID),
   PADictionary b,
   OPOperator e
WHERE b.DictTypeID=13001
AND   b.DictID=a.OperType
AND   e.OperID = a.OperID;
GO

