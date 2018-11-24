
  
/*sqlserver建表*/
use dbs_server_saudi 
go


/* 柜体类型插入字典PADictionary*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(21003,'柜体类型','0','elocker包裹柜');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(21003,'柜体类型','1','counter投递柜台');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(21003,'柜体类型','2','elocker一箱多单');

/*PADictionary 转运列表状态的订单*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','15','TransferList');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33004,'投递订单状态04-Pane1','15','TransferList');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','16','Package-listed');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33004,'投递订单状态04-Pane1','16','Package-listed');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33003,'投递订单状态','17','Package-out');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(33006,'投递订单状态06-Pane3','17','Package-out');
/*投递员类型增加转运类型*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(31001,'邮递员类型','85','Transport');/*邮递员类型-转运*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName)   VALUES(13005,'操作人员类型','85','Transport');/*操作人员类型-转运*/
/* 邮件类型，发送权限*/
/*INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(56011,'邮件类型','0','柜体使用统计邮件');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(56011,'邮件类型','1','离线报警邮件');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(57011,'发送权限','0','管理员AZC区域');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(57011,'发送权限','1','所有AZC区域');*/
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(56011,'邮件类型','0','BoxUsed statistical mail');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(57011,'发送权限','0','Administrator AZC');
INSERT INTO PADictionary(DictTypeID,DictTypeName,DictID,DictName) VALUES(57011,'发送权限','1','All AZC');
/* 发送报告邮件*/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150181','接收邮件管理员添加','MBReportEmailRecrAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150182','接收邮件管理员修改','MBReportEmailRecrMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150183','接收邮件管理员删除','MBReportEmailRecrDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150184','接收邮件管理员查询','MBReportEmailRecrQry','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150185','接收邮件管理员查询数量','MBReportEmailRecrQryCount','1');

/*OPFunction中对MBTimeLimit业务的功能参数的添加*/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150161','订单状态时间限制添加','MBTimeLimitAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150162','订单状态时间限制修改','MBTimeLimitMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150163','订单状态时间限制删除','MBTimeLimitDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150164','订单状态时间限制查询','MBTimeLimitQry','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150165','订单状态时间限制查询数量','MBTimeLimitQryCount','1');


INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('350074','运营报表统计','QYOperationReport','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('350075','运营报表统计数量','QYOperationReportCount','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('350076','运营报表按订单号统计','QYOperationReportByPack','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('350077','运营报表按订单号统计数量','QYOperationReportByPackCount','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('350078','运营报表包裹数量统计','QYOperationReportPackNum','1');

/*OPFunction表增加参数FuntionID   中文界面授权时显示不出来*/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331016','添加转运包裹到清单','PTTransportPackAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331017','从转运清单中删除订单','PTTransportPackDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331018','指定邮递员派送','PTTransportPackExe','1');

/*OPFunction中对PTCounter业务的功能参数的添加*/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331035','投递柜台接收包裹','PTCounterReceive','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331036','从投递柜台回收包裹','PTCounterReturn','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331037','从投递柜台取件','PTCounterPickup','1');
/* 从PPC发送到AZC的订单，在pane1已经显示，但是ppc已经作别的处理，不用再在e-Locker系统中处理的订单，增加按钮来让管理员从pane1中移除。*/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331075','错误订单删除','PTErrPackDel','1');
INSERT INTO OPMenu VALUES('33010022','错误订单删除',                 3,'','','','','','','',1,1,'');

/*Panel 15160000 状态时间限制管理*/
INSERT INTO OPMenu VALUES('15160000','状态时间限制管理',      2,'','','','{"url":"appjs/mb/MBTimeLimit.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('15160001','添加',      3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('15160002','修改',      3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('15160003','删除',      3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('15160004','查询',      3,'','','','','','','',1,1,'');
/*Panel 15170000 接收报告邮件管理*/
INSERT INTO OPMenu VALUES('15170000','接收报告邮件管理',      2,'','','','{"url":"appjs/mb/MBSendReportEmail.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('15170001','添加',      3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('15170002','修改',      3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('15170003','删除',      3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('15170004','查询',      3,'','','','','','','',1,1,'');

/* 统计报表菜单*/
INSERT INTO OPMenu VALUES('35270000','运营报表按订单号统计',      2,'','','','{"url":"appjs/qy/QYOperationReportByPack.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('35270004','查询',      3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('35270005','导出',      3,'','','','','','','',1,1,'');
/*INSERT INTO OPMenu VALUES('35280000','运营报表按分拣中心统计',      2,'','','','{"url":"appjs/qy/QYOperationReportByAZC.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('35280004','查询',      3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('35280005','导出',      3,'','','','','','','',1,1,'');*/

INSERT INTO OPMenu VALUES('35290000','运营报表统计',      2,'','','','{"url":"appjs/qy/QYOperationReport.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('35290004','查询',      3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('35290005','导出',      3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('35290020','监控',      3,'','','','','','','',1,1,'');
/*，在OPMenu表中增加菜单ID ，  后面js页面中第二个字段可以换掉*/
INSERT INTO OPMenu VALUES('33010009','添加转运包裹到清单',                 3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33010010','从转运清单中删除订单',                 3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33010011','指定邮递员派送',                 3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('33030035','投递柜台接收包裹',                 3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33040036','从投递柜台回收包裹',                3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33040037','从投递柜台取件',                3,'','','','','','','',1,1,'');

/*更新表结构 TBTerminal */
alter table TBTerminal add TerminalType varchar(4) not null default '0';
EXEC sp_addextendedproperty 'MS_Description', '柜体类型：0：包裹柜1：投递柜台2:一箱多单柜', 'user', dbo, 'table', TBTerminal, 'column', TerminalType;
/*PTReadyPackgae*/
alter table PTReadyPackage add ZoneIDDes varchar(10) default null;
EXEC sp_addextendedproperty 'MS_Description', '目的分拣区域编号', 'user', dbo, 'table', PTReadyPackage, 'column', ZoneIDDes;
/*数据库表PTTransferItemWater和PTDeliverItemTransfer中加入TerminalNo字段*/
alter table PTTransferItemWater add TerminalNo varchar(20) default null;
EXEC sp_addextendedproperty 'MS_Description', '设备号', 'user', dbo, 'table', PTTransferItemWater, 'column', TerminalNo;
alter table PTDeliverItemTransfer add TerminalNo varchar(20) default null;
EXEC sp_addextendedproperty 'MS_Description', '设备号', 'user', dbo, 'table', PTDeliverItemTransfer, 'column', TerminalNo;

/* PTItemLifeCycle，每个状态时间统计*/
alter table PTItemLifeCycle add StatusTime int default null;
EXEC sp_addextendedproperty 'MS_Description', '状态耗时', 'user', dbo, 'table', PTItemLifeCycle, 'column', StatusTime;
alter table PTItemLifeCycle add LastStatus nvarchar(4) default null;
EXEC sp_addextendedproperty 'MS_Description', '上个状态', 'user', dbo, 'table', PTItemLifeCycle, 'column', LastStatus;
alter table PTItemLifeCycle add ZoneID nvarchar(10) default null;
EXEC sp_addextendedproperty 'MS_Description', '分拣区域编号', 'user', dbo, 'table', PTItemLifeCycle, 'column', ZoneID;

/* DMItemLifeCycle，每个状态时间统计*/
alter table DMItemLifeCycle add StatusTime int default null;
EXEC sp_addextendedproperty 'MS_Description', '状态耗时', 'user', dbo, 'table', DMItemLifeCycle, 'column', StatusTime;
alter table DMItemLifeCycle add LastStatus nvarchar(4) default null;
EXEC sp_addextendedproperty 'MS_Description', '上个状态', 'user', dbo, 'table', DMItemLifeCycle, 'column', LastStatus;

/*==============================================================*/
/* Table: MBTimeLimit                                           */
/*==============================================================*/
alter table MBTimeLimit
   drop constraint PK_MBTIMELIMIT
go

if exists (select 1
            from  sysobjects
            where  id = object_id('MBTimeLimit')
            and   type = 'U')
   drop table MBTimeLimit
go

alter table MBWrongPwdWater
   drop constraint PK_MBWRONGPWDWATER
go

create table MBTimeLimit (
   StatusID             varchar(2)                not null,   -- 自定义类型，服务器数据库中没有（DFlag）,
   StatusName           varchar(60)               not null,   -- DChar60
   TimeLimit            int                       null        -- 数字8
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '任务时间限制表',
   'user', @CurrentUser, 'table', 'MBTimeLimit'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '状态ID',
   'user', @CurrentUser, 'table', 'MBTimeLimit', 'column', 'StatusID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '状态名称',
   'user', @CurrentUser, 'table', 'MBTimeLimit', 'column', 'StatusName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '限制时间',
   'user', @CurrentUser, 'table', 'MBTimeLimit', 'column', 'TimeLimit'
go

alter table MBTimeLimit
   add constraint PK_MBTIMELIMIT primary key nonclustered (StatusID)
go
/*==============================================================*/
/* Table: MBSendReportEmail                                           */
/*==============================================================*/
create table MBSendReportEmail (
   OperID               varchar(64)              not null,
   EmailType            varchar(2)                 not null,
   SendAsRights         varchar(2)                 not null,
   CreateTime           datetime2           null,
   LastModifyTime       datetime2            null,
   Remark               varchar(256)              null
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '发送报告邮件管理',
   'user', @CurrentUser, 'table', 'MBSendReportEmail'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作员编号',
   'user', @CurrentUser, 'table', 'MBSendReportEmail', 'column', 'OperID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮件类型',
   'user', @CurrentUser, 'table', 'MBSendReportEmail', 'column', 'EmailType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '发送权限',
   'user', @CurrentUser, 'table', 'MBSendReportEmail', 'column', 'SendAsRights'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'MBSendReportEmail', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'MBSendReportEmail', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'MBSendReportEmail', 'column', 'Remark'
go

alter table MBSendReportEmail
   add constraint PK_MBSENDREPORTEMAIL primary key nonclustered (OperID)
go
/*插入初始数据*/
INSERT INTO MBTimeLimit(StatusID,StatusName,TimeLimit) VALUES('0','Listed',9999),('1','Received',9999),('15','TransferList',9999),('2','Assigned',9999)
            ,('3','Scheduled',9999),('4','Intransit-out',9999),('10','Intransit-back',9999),('5','Dropped',9999)
            ,('6','D-Dropped',9999),('8','Expired',9999),('9','M-Expired',9999),('11','Returned',9999)
            ,('12','Missing',9999),('16','Package-listed',9999),('17','Package-out',9999);

/*V_PTPane1中将15-TransferList状态显示，并且将目的AZC的ZoneName显示*/
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
/* 将箱类型的检测修改为，显示没有分配箱类型的订单 */
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
/* V_PTPane3中将有ZoneIDDes的订单Listed状态显示为Intransit-out*/
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


/*  超时时长统计 */
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
/* 初始化PTItemLifeCycle表中新加入的字段*/
update b set b.ZoneID = a.ZoneID 
FROM PTReadyPackage a,PTItemLifeCycle b where a.PackageID = b.PackageID;
GO
update b set b.ZoneID = a.ZoneID 
FROM PTInBoxPackage a,PTItemLifeCycle b where a.PackageID = b.PackageID;
GO
update b set b.ZoneID = a.ZoneID 
FROM PTDeliverHistory a,PTItemLifeCycle b where a.PackageID = b.PackageID;
/* 初始化PTItemLifeCycle表中新加入的字段*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'vv_PTItemLifeCycle')
DROP VIEW vv_PTItemLifeCycle;
GO
CREATE VIEW vv_PTItemLifeCycle AS 
select a.PackageID,a.ItemStatus,a.LastModifyTime,MAX(b.LastModifyTime) maxLastModifyTime
from PTItemLifeCycle a,
PTItemLifeCycle b 
where a.PackageID = b.PackageID AND a.LastModifyTime>b.LastModifyTime
group by a.PackageID,a.ItemStatus,a.LastModifyTime
;
GO

IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'vv_PTItemLifeCycle1')
DROP VIEW vv_PTItemLifeCycle1;
GO
CREATE VIEW vv_PTItemLifeCycle1 AS 
select a.PackageID,a.ItemStatus,a.LastModifyTime,b.ItemStatus AS LastStatus,a.maxLastModifyTime
from vv_PTItemLifeCycle a,
PTItemLifeCycle b 
where a.PackageID = b.PackageID AND a.maxLastModifyTime=b.LastModifyTime
;
GO
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'vv_PTItemLifeCycle2')
DROP VIEW vv_PTItemLifeCycle2;
GO
CREATE VIEW vv_PTItemLifeCycle2 AS 
select a.ItemStatus,b.LastStatus,datediff(minute,b.maxLastModifyTime,a.LastModifyTime) AS StatusTime, a.LastModifyTime,b.maxLastModifyTime
from 
PTItemLifeCycle a,
vv_PTItemLifeCycle1 b
where a.PackageID = b.PackageID AND a.ItemStatus=b.ItemStatus and b.LastModifyTime=a.LastModifyTime;
go
/*更新*/
update a set a.LastStatus =b.LastStatus,a.StatusTime = b.StatusTime 
from PTItemLifeCycle a,vv_PTItemLifeCycle2 b 
where a.LastModifyTime = b.LastModifyTime;

/* 发送邮件视图*/
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
go
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

/* 显示包裹个数 */
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
      
      ,CASE WHEN a.ItemStatus = 10 THEN 1 ELSE 0 END AS IntransitBackPack
      ,CASE WHEN a.ItemStatus = 10 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS IntransitBackPackTimeOut
      
      ,CASE WHEN a.ItemStatus = 17 THEN 1 ELSE 0 END AS PackageOutPack
      ,CASE WHEN a.ItemStatus = 17 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS PackageOutPackTimeOut
      
FROM PTReadyPackage a,MBTimeLimit c 
where a.ItemStatus = c.StatusID AND c.StatusID in (0,1,2,3,4,10,15,16,17)
go
/* PTInBoxPackage*/
IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME = N'V_QYOperReportPackNumInBox')
DROP VIEW V_QYOperReportPackNumInBox;
GO
CREATE VIEW V_QYOperReportPackNumInBox AS 
SELECT a.ZoneID,a.TerminalNo
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
SELECT a.ZoneID,a.TerminalNo
      ,CASE WHEN a.ItemStatus = 11 THEN 1 ELSE 0 END AS ReturnedPack
      ,CASE WHEN a.ItemStatus = 11 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS ReturnedPackTimeOut 
      
      ,CASE WHEN a.ItemStatus = 12 THEN 1 ELSE 0 END AS MissingPack
      ,CASE WHEN a.ItemStatus = 12 AND DATEDIFF(mi,a.LastModifyTime,CONVERT(datetime,GETDATE(),20)) >c.TimeLimit THEN 1 ELSE 0 END AS MissingPackTimeOut
       
FROM PTDeliverHistory a,MBTimeLimit c 
where a.ItemStatus = c.StatusID AND c.StatusID in (11,12)
go

/* 增加柜体类型，在柜体信息维护中可以修改类型*/
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



