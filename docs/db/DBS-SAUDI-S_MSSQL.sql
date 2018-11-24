/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     2016/2/4 13:39:07                            */
/*==============================================================*/

use dbs_server_saudi
go

if exists (select 1
            from  sysobjects
           where  id = object_id('APUserKeyMap')
            and   type = 'U')
   drop table APUserKeyMap
go

if exists (select 1
            from  sysobjects
           where  id = object_id('DMCollectionParcel')
            and   type = 'U')
   drop table DMCollectionParcel
go

if exists (select 1
            from  sysobjects
           where  id = object_id('DMHistoryItem')
            and   type = 'U')
   drop table DMHistoryItem
go

if exists (select 1
            from  sysobjects
           where  id = object_id('DMItemLifeCycle')
            and   type = 'U')
   drop table DMItemLifeCycle
go

if exists (select 1
            from  sysobjects
           where  id = object_id('IMBPartnerServiceRight')
            and   type = 'U')
   drop table IMBPartnerServiceRight
go

if exists (select 1
            from  sysobjects
           where  id = object_id('IMBusinessPartner')
            and   type = 'U')
   drop table IMBusinessPartner
go

if exists (select 1
            from  sysobjects
           where  id = object_id('IMCollectZone')
            and   type = 'U')
   drop table IMCollectZone
go

if exists (select 1
            from  sysobjects
           where  id = object_id('IMCompany')
            and   type = 'U')
   drop table IMCompany
go

if exists (select 1
            from  sysobjects
           where  id = object_id('IMCompanyBoxRight')
            and   type = 'U')
   drop table IMCompanyBoxRight
go

if exists (select 1
            from  sysobjects
           where  id = object_id('IMCustomer')
            and   type = 'U')
   drop table IMCustomer
go

if exists (select 1
            from  sysobjects
           where  id = object_id('IMEcommercePartner')
            and   type = 'U')
   drop table IMEcommercePartner
go

if exists (select 1
            from  sysobjects
           where  id = object_id('IMGateway')
            and   type = 'U')
   drop table IMGateway
go

if exists (select 1
            from  sysobjects
           where  id = object_id('IMPostalProcessingCenter')
            and   type = 'U')
   drop table IMPostalProcessingCenter
go

if exists (select 1
            from  sysobjects
           where  id = object_id('IMServiceCounter')
            and   type = 'U')
   drop table IMServiceCounter
go

if exists (select 1
            from  sysobjects
           where  id = object_id('IMServiceRate')
            and   type = 'U')
   drop table IMServiceRate
go

if exists (select 1
            from  sysobjects
           where  id = object_id('IMZone')
            and   type = 'U')
   drop table IMZone
go

if exists (select 1
            from  sysobjects
           where  id = object_id('IMZoneCounter')
            and   type = 'U')
   drop table IMZoneCounter
go

if exists (select 1
            from  sysobjects
           where  id = object_id('IMZoneLockerRight')
            and   type = 'U')
   drop table IMZoneLockerRight
go

if exists (select 1
            from  sysobjects
           where  id = object_id('MBBindMobileWater')
            and   type = 'U')
   drop table MBBindMobileWater
go

if exists (select 1
            from  sysobjects
           where  id = object_id('MBBoxStatusLog')
            and   type = 'U')
   drop table MBBoxStatusLog
go

if exists (select 1
            from  sysobjects
           where  id = object_id('MBLockUserTime')
            and   type = 'U')
   drop table MBLockUserTime
go

if exists (select 1
            from  sysobjects
           where  id = object_id('MBMobileBlackList')
            and   type = 'U')
   drop table MBMobileBlackList
go

if exists (select 1
            from  sysobjects
           where  id = object_id('MBMonitorPictureInfo')
            and   type = 'U')
   drop table MBMonitorPictureInfo
go

if exists (select 1
            from  sysobjects
           where  id = object_id('MBPwdShortMsg')
            and   type = 'U')
   drop table MBPwdShortMsg
go

if exists (select 1
            from  sysobjects
           where  id = object_id('MBReminderWater')
            and   type = 'U')
   drop table MBReminderWater
go

if exists (select 1
            from  sysobjects
           where  id = object_id('MBSignInfo')
            and   type = 'U')
   drop table MBSignInfo
go

if exists (select 1
            from  sysobjects
           where  id = object_id('MBSmsStat')
            and   type = 'U')
   drop table MBSmsStat
go

if exists (select 1
            from  sysobjects
           where  id = object_id('MBTerminalAlertLog')
            and   type = 'U')
   drop table MBTerminalAlertLog
go

if exists (select 1
            from  sysobjects
           where  id = object_id('MBTerminalStatusLog')
            and   type = 'U')
   drop table MBTerminalStatusLog
go

if exists (select 1
            from  sysobjects
           where  id = object_id('MBWrongPwdWater')
            and   type = 'U')
   drop table MBWrongPwdWater
go

if exists (select 1
            from  sysobjects
           where  id = object_id('OPFunction')
            and   type = 'U')
   drop table OPFunction
go

if exists (select 1
            from  sysobjects
           where  id = object_id('OPMenu')
            and   type = 'U')
   drop table OPMenu
go

if exists (select 1
            from  sysobjects
           where  id = object_id('OPOperAllLimit')
            and   type = 'U')
   drop table OPOperAllLimit
go

if exists (select 1
            from  sysobjects
           where  id = object_id('OPOperOnline')
            and   type = 'U')
   drop table OPOperOnline
go

if exists (select 1
            from  sysobjects
           where  id = object_id('OPOperRole')
            and   type = 'U')
   drop table OPOperRole
go

if exists (select 1
            from  sysobjects
           where  id = object_id('OPOperSpeRight')
            and   type = 'U')
   drop table OPOperSpeRight
go

if exists (select 1
            from  sysobjects
           where  id = object_id('OPOperToMenu')
            and   type = 'U')
   drop table OPOperToMenu
go

if exists (select 1
            from  sysobjects
           where  id = object_id('OPOperator')
            and   type = 'U')
   drop table OPOperator
go

if exists (select 1
            from  sysobjects
           where  id = object_id('OPOperatorLog')
            and   type = 'U')
   drop table OPOperatorLog
go

if exists (select 1
            from  sysobjects
           where  id = object_id('OPRole')
            and   type = 'U')
   drop table OPRole
go

if exists (select 1
            from  sysobjects
           where  id = object_id('OPRoleAllLimit')
            and   type = 'U')
   drop table OPRoleAllLimit
go

if exists (select 1
            from  sysobjects
           where  id = object_id('OPRoleSpeRight')
            and   type = 'U')
   drop table OPRoleSpeRight
go

if exists (select 1
            from  sysobjects
           where  id = object_id('OPRoleToMenu')
            and   type = 'U')
   drop table OPRoleToMenu
go

if exists (select 1
            from  sysobjects
           where  id = object_id('OPSpecialPriv')
            and   type = 'U')
   drop table OPSpecialPriv
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PAControlParam')
            and   type = 'U')
   drop table PAControlParam
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PADictionary')
            and   type = 'U')
   drop table PADictionary
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PASequence')
            and   type = 'U')
   drop table PASequence
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PATerminalCtrlParam')
            and   type = 'U')
   drop table PATerminalCtrlParam
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PMPostman')
            and   type = 'U')
   drop table PMPostman
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PMPostmanLockerRight')
            and   type = 'U')
   drop table PMPostmanLockerRight
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PTDeliverHistory')
            and   type = 'U')
   drop table PTDeliverHistory
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PTDeliverItemTransfer')
            and   type = 'U')
   drop table PTDeliverItemTransfer
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PTInBoxPackage')
            and   type = 'U')
   drop table PTInBoxPackage
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PTItemLifeCycle')
            and   type = 'U')
   drop table PTItemLifeCycle
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PTReadyPackage')
            and   type = 'U')
   drop table PTReadyPackage
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PTTransferItemWater')
            and   type = 'U')
   drop table PTTransferItemWater
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PYServiceBillWater')
            and   type = 'U')
   drop table PYServiceBillWater
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PYTopUpReq')
            and   type = 'U')
   drop table PYTopUpReq
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RMAppealLog')
            and   type = 'U')
   drop table RMAppealLog
go

if exists (select 1
            from  sysobjects
           where  id = object_id('RMOpenBoxLog')
            and   type = 'U')
   drop table RMOpenBoxLog
go

if exists (select 1
            from  sysobjects
           where  id = object_id('SCFtpLog')
            and   type = 'U')
   drop table SCFtpLog
go

if exists (select 1
            from  sysobjects
           where  id = object_id('SCPushDataQueue')
            and   type = 'U')
   drop table SCPushDataQueue
go

if exists (select 1
            from  sysobjects
           where  id = object_id('SCSyncFailWater')
            and   type = 'U')
   drop table SCSyncFailWater
go

if exists (select 1
            from  sysobjects
           where  id = object_id('SCTimestamp')
            and   type = 'U')
   drop table SCTimestamp
go

if exists (select 1
            from  sysobjects
           where  id = object_id('SCUploadDataQueue')
            and   type = 'U')
   drop table SCUploadDataQueue
go

if exists (select 1
            from  sysobjects
           where  id = object_id('SMAdVideo')
            and   type = 'U')
   drop table SMAdVideo
go

if exists (select 1
            from  sysobjects
           where  id = object_id('SMSystemInfo')
            and   type = 'U')
   drop table SMSystemInfo
go

if exists (select 1
            from  sysobjects
           where  id = object_id('TBBoxType')
            and   type = 'U')
   drop table TBBoxType
go

if exists (select 1
            from  sysobjects
           where  id = object_id('TBServerBox')
            and   type = 'U')
   drop table TBServerBox
go

if exists (select 1
            from  sysobjects
           where  id = object_id('TBServerDesk')
            and   type = 'U')
   drop table TBServerDesk
go

if exists (select 1
            from  sysobjects
           where  id = object_id('TBTerminal')
            and   type = 'U')
   drop table TBTerminal
go

if exists (select 1
            from  sysobjects
           where  id = object_id('TBTerminalCounter')
            and   type = 'U')
   drop table TBTerminalCounter
go

if exists (select 1
            from  sysobjects
           where  id = object_id('OPEmail')
            and   type = 'U')
   drop table OPEmail
go

/*==============================================================*/
/* Table: APUserKeyMap                                          */
/*==============================================================*/
create table APUserKeyMap (
   KeyID                varchar(32)          not null,
   UserID               varchar(32)          not null,
   UserType             varchar(4)           not null,
   UserKey              varchar(60)          not null,
   LastModifyTime       datetime             null,
   CreateTime           datetime             null,
   Remark               varchar(256)         null,
   constraint PK_APUSERKEYMAP primary key nonclustered (KeyID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户密码映射表',
   'user', @CurrentUser, 'table', 'APUserKeyMap'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '密码编号',
   'user', @CurrentUser, 'table', 'APUserKeyMap', 'column', 'KeyID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户编号',
   'user', @CurrentUser, 'table', 'APUserKeyMap', 'column', 'UserID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户类型',
   'user', @CurrentUser, 'table', 'APUserKeyMap', 'column', 'UserType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户密码',
   'user', @CurrentUser, 'table', 'APUserKeyMap', 'column', 'UserKey'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'APUserKeyMap', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'APUserKeyMap', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'APUserKeyMap', 'column', 'Remark'
go

/*==============================================================*/
/* Table: DMCollectionParcel                                    */
/*==============================================================*/
create table DMCollectionParcel (
   PackageID            varchar(32)          not null,
   CreateTime           datetime             not null,
   BPartnerID           varchar(20)          not null,
   ItemStatus           varchar(4)           not null,
   ServiceID            varchar(20)          not null,
   ServiceAmt           numeric(16,3)        null,
   CollectionFlag       varchar(2)           null,
   CollectionAmt        numeric(16,3)        null,
   ReturnFlag           varchar(2)           null,
   ZoneID               varchar(10)          null,
   CompanyID            varchar(8)           null,
   TradeWaterNo         varchar(32)          null,
   CustomerAddress      varchar(256)         null,
   CustomerName         varchar(60)          null,
   CustomerMobile       varchar(30)          null,
   CustomerID           varchar(20)          null,
   TerminalNo           varchar(20)          null,
   BoxNo                varchar(20)          null,
   ParcelSize           varchar(20)          null,
   CollectZoneID        varchar(30)          null,
   CollectionType       varchar(4)           null,
   CollectionAgentID    varchar(64)          null,
   CollectionTime       datetime             null,
   PrintedFlag          int                  null,
   ReportOrderID        varchar(32)          null,
   PPCID                varchar(10)          null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null,
   constraint PK_DMCOLLECTIONPARCEL primary key nonclustered (PackageID, CreateTime)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '寄件订单信息',
   'user', @CurrentUser, 'table', 'DMCollectionParcel'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单号',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'PackageID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建订单时间',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '商业伙伴编号',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'BPartnerID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单状态',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'ItemStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务编号',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'ServiceID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务费用',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'ServiceAmt'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '揽件服务标志',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'CollectionFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '揽件费用',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'CollectionAmt'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '退件服务标志',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'ReturnFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分拣区域编号',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'ZoneID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹服务商编号',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'CompanyID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '交易流水号',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'TradeWaterNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人地址',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'CustomerAddress'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人姓名',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'CustomerName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人手机',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'CustomerMobile'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人编号',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'CustomerID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱门号',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'BoxNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹尺寸',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'ParcelSize'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '揽件区域编号',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'CollectZoneID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '揽件方式',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'CollectionType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '揽件员编号',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'CollectionAgentID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '揽件时间',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'CollectionTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '打印标识（按位表示：R1~R8，0表示未打印，1表示已打印）',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'PrintedFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报表流水号',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'ReportOrderID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹处理中心编号：发往PPC的编号',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'PPCID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'DMCollectionParcel', 'column', 'Remark'
go

/*==============================================================*/
/* Table: DMHistoryItem                                         */
/*==============================================================*/
create table DMHistoryItem (
   PackageID            varchar(32)          not null,
   CreateTime           datetime             not null,
   BPartnerID           varchar(20)          not null,
   PPCID                varchar(10)          not null,
   ItemStatus           varchar(4)           not null,
   ServiceID            varchar(20)          null,
   ServiceAmt           numeric(16,3)        null,
   CollectionFlag       varchar(2)           null,
   CollectionAmt        numeric(16,3)        null,
   ReturnFlag           varchar(2)           null,
   ZoneID               varchar(10)          null,
   CompanyID            varchar(8)           null,
   TradeWaterNo         varchar(32)          null,
   CustomerAddress      varchar(256)         null,
   CustomerName         varchar(60)          null,
   CustomerMobile       varchar(30)          null,
   CustomerID           varchar(20)          null,
   TerminalNo           varchar(20)          null,
   BoxNo                varchar(20)          null,
   ParcelSize           varchar(20)          null,
   CollectZoneID        varchar(30)          null,
   CollectionType       varchar(4)           null,
   CollectionAgentID    varchar(64)          null,
   CollectionTime       datetime             null,
   ReportOrderID        varchar(32)          null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null,
   PrintedFlag          int                  null,
   constraint PK_DMHISTORYITEM primary key nonclustered (PackageID, CreateTime)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '寄件历史信息',
   'user', @CurrentUser, 'table', 'DMHistoryItem'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单号',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'PackageID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建订单时间',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '商业伙伴编号',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'BPartnerID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹处理中心编号：发往PPC的编号',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'PPCID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单状态',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'ItemStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务编号',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'ServiceID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务费用',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'ServiceAmt'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '揽件服务标志',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'CollectionFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '揽件费用',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'CollectionAmt'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '退件服务标志',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'ReturnFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分拣区域编号',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'ZoneID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹服务商编号',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'CompanyID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '交易流水号',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'TradeWaterNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人地址',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'CustomerAddress'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人姓名',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'CustomerName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人手机',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'CustomerMobile'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人编号',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'CustomerID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱门号',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'BoxNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹尺寸',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'ParcelSize'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '揽件区域编号',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'CollectZoneID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '揽件方式',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'CollectionType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '揽件员编号',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'CollectionAgentID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '揽件时间',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'CollectionTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报表流水号',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'ReportOrderID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'Remark'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '打印标识（按位表示：R1~R8，0表示未打印，1表示已打印）',
   'user', @CurrentUser, 'table', 'DMHistoryItem', 'column', 'PrintedFlag'
go

/*==============================================================*/
/* Table: DMItemLifeCycle                                       */
/*==============================================================*/
create table DMItemLifeCycle (
   WaterID              bigint               not null,
   PackageID            varchar(32)          not null,
   ItemStatus           varchar(4)           not null,
   OperatorID           varchar(64)          not null,
   OperatorType         varchar(4)           null,
   RecordLevel          int                  null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null,
   constraint PK_DMITEMLIFECYCLE primary key nonclustered (WaterID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '寄件生命周期记录',
   'user', @CurrentUser, 'table', 'DMItemLifeCycle'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流水号',
   'user', @CurrentUser, 'table', 'DMItemLifeCycle', 'column', 'WaterID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单号',
   'user', @CurrentUser, 'table', 'DMItemLifeCycle', 'column', 'PackageID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单状态',
   'user', @CurrentUser, 'table', 'DMItemLifeCycle', 'column', 'ItemStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作员编号',
   'user', @CurrentUser, 'table', 'DMItemLifeCycle', 'column', 'OperatorID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作员类型',
   'user', @CurrentUser, 'table', 'DMItemLifeCycle', 'column', 'OperatorType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '记录级别',
   'user', @CurrentUser, 'table', 'DMItemLifeCycle', 'column', 'RecordLevel'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'DMItemLifeCycle', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'DMItemLifeCycle', 'column', 'Remark'
go

/*==============================================================*/
/* Table: IMBPartnerServiceRight                                */
/*==============================================================*/
create table IMBPartnerServiceRight (
   BPartnerID           varchar(20)          not null,
   ServiceID            varchar(8)           not null,
   Remark               varchar(256)         null,
   constraint PK_IMBPARTNERSERVICERIGHT primary key (BPartnerID, ServiceID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '商业伙伴服务权限',
   'user', @CurrentUser, 'table', 'IMBPartnerServiceRight'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '商业伙伴编号',
   'user', @CurrentUser, 'table', 'IMBPartnerServiceRight', 'column', 'BPartnerID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务类型编号',
   'user', @CurrentUser, 'table', 'IMBPartnerServiceRight', 'column', 'ServiceID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'IMBPartnerServiceRight', 'column', 'Remark'
go

/*==============================================================*/
/* Table: IMBusinessPartner                                     */
/*==============================================================*/
create table IMBusinessPartner (
   BPartnerID           varchar(20)          not null,
   CollectZoneID        varchar(30)          not null,
   BPartnerName         varchar(64)          not null,
   Address              varchar(256)         null,
   Email                varchar(64)          null,
   Mobile               varchar(30)          null,
   Username             varchar(32)          not null,
   Password             varchar(32)          not null,
   Balance              numeric(16,3)        null,
   CreditFlag           varchar(2)           null,
   CreditLimit          numeric(16,3)        null,
   Discount             int                  null,
   CollectionServiceFlag varchar(2)           null,
   ReturnServiceFlag    varchar(2)           null,
   Longitude            numeric(16,12)       null,
   Latitude             numeric(16,12)       null,
   CreateTime           datetime             null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null,
   constraint PK_IMBUSINESSPARTNER primary key nonclustered (BPartnerID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '商业合作伙伴',
   'user', @CurrentUser, 'table', 'IMBusinessPartner'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '商业伙伴编号',
   'user', @CurrentUser, 'table', 'IMBusinessPartner', 'column', 'BPartnerID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '揽件区域编号',
   'user', @CurrentUser, 'table', 'IMBusinessPartner', 'column', 'CollectZoneID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '商业伙伴名称',
   'user', @CurrentUser, 'table', 'IMBusinessPartner', 'column', 'BPartnerName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '商业伙伴地址',
   'user', @CurrentUser, 'table', 'IMBusinessPartner', 'column', 'Address'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '商业伙伴邮箱',
   'user', @CurrentUser, 'table', 'IMBusinessPartner', 'column', 'Email'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '商业伙伴手机',
   'user', @CurrentUser, 'table', 'IMBusinessPartner', 'column', 'Mobile'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '商业伙伴用户',
   'user', @CurrentUser, 'table', 'IMBusinessPartner', 'column', 'Username'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '商业伙伴密码',
   'user', @CurrentUser, 'table', 'IMBusinessPartner', 'column', 'Password'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '账户余额',
   'user', @CurrentUser, 'table', 'IMBusinessPartner', 'column', 'Balance'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '授信',
   'user', @CurrentUser, 'table', 'IMBusinessPartner', 'column', 'CreditFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '商业伙伴信用额度',
   'user', @CurrentUser, 'table', 'IMBusinessPartner', 'column', 'CreditLimit'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '折扣（%）',
   'user', @CurrentUser, 'table', 'IMBusinessPartner', 'column', 'Discount'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '揽件服务',
   'user', @CurrentUser, 'table', 'IMBusinessPartner', 'column', 'CollectionServiceFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '商业伙伴退件服务标识',
   'user', @CurrentUser, 'table', 'IMBusinessPartner', 'column', 'ReturnServiceFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '经度',
   'user', @CurrentUser, 'table', 'IMBusinessPartner', 'column', 'Longitude'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '纬度',
   'user', @CurrentUser, 'table', 'IMBusinessPartner', 'column', 'Latitude'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'IMBusinessPartner', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'IMBusinessPartner', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'IMBusinessPartner', 'column', 'Remark'
go

/*==============================================================*/
/* Table: IMCollectZone                                         */
/*==============================================================*/
create table IMCollectZone (
   CollectZoneID        varchar(30)          not null,
   ZoneID               varchar(20)          not null,
   CollectZoneName      varchar(64)          not null,
   CollectZoneDesc      varchar(256)         null,
   Remark               varchar(256)         null,
   constraint PK_IMCOLLECTZONE primary key nonclustered (CollectZoneID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '揽件区域',
   'user', @CurrentUser, 'table', 'IMCollectZone'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '揽件区域编号',
   'user', @CurrentUser, 'table', 'IMCollectZone', 'column', 'CollectZoneID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分拣区域编号',
   'user', @CurrentUser, 'table', 'IMCollectZone', 'column', 'ZoneID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '揽件区域名称',
   'user', @CurrentUser, 'table', 'IMCollectZone', 'column', 'CollectZoneName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '揽件区域描述',
   'user', @CurrentUser, 'table', 'IMCollectZone', 'column', 'CollectZoneDesc'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'IMCollectZone', 'column', 'Remark'
go

/*==============================================================*/
/* Table: IMCompany                                             */
/*==============================================================*/
create table IMCompany (
   CompanyID            varchar(16)          not null,
   CompanyName          varchar(64)          not null,
   CompanyType          varchar(4)           not null,
   MasterFlag           varchar(2)           not null,
   Address              varchar(256)         null,
   Email                varchar(64)          null,
   Mobile               varchar(30)          null,
   Feedback             varchar(2)           null,
   LogoUrl              varchar(512)         null,
   Slogan               varchar(64)          null,
   SMS_Notification     varchar(256)         null,
   ExpiredDays          int                  null,
   ReminderDays         int                  null,
   ReminderTime         varchar(32)          null,
   CreateTime           datetime             null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null,
   constraint PK_IMCOMPANY primary key nonclustered (CompanyID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包括主运营方(沙特邮政)，合作的快递公司，其他政府部门，银行等包裹服务提供商',
   'user', @CurrentUser, 'table', 'IMCompany'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务商编号',
   'user', @CurrentUser, 'table', 'IMCompany', 'column', 'CompanyID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务商名称',
   'user', @CurrentUser, 'table', 'IMCompany', 'column', 'CompanyName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务商类型：1-Logistics Company，2-Service Owner（政府部门，银行等）',
   'user', @CurrentUser, 'table', 'IMCompany', 'column', 'CompanyType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '主运营商标识',
   'user', @CurrentUser, 'table', 'IMCompany', 'column', 'MasterFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务商地址',
   'user', @CurrentUser, 'table', 'IMCompany', 'column', 'Address'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务商邮箱',
   'user', @CurrentUser, 'table', 'IMCompany', 'column', 'Email'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务商联系手机',
   'user', @CurrentUser, 'table', 'IMCompany', 'column', 'Mobile'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务商反馈标识，是否发送用户已取件信息',
   'user', @CurrentUser, 'table', 'IMCompany', 'column', 'Feedback'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务商LOGO,图片地址',
   'user', @CurrentUser, 'table', 'IMCompany', 'column', 'LogoUrl'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务商标语',
   'user', @CurrentUser, 'table', 'IMCompany', 'column', 'Slogan'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务商通告',
   'user', @CurrentUser, 'table', 'IMCompany', 'column', 'SMS_Notification'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '逾期天数',
   'user', @CurrentUser, 'table', 'IMCompany', 'column', 'ExpiredDays'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '催领间隔',
   'user', @CurrentUser, 'table', 'IMCompany', 'column', 'ReminderDays'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '催领时间',
   'user', @CurrentUser, 'table', 'IMCompany', 'column', 'ReminderTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'IMCompany', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'IMCompany', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'IMCompany', 'column', 'Remark'
go

/*==============================================================*/
/* Table: IMCompanyBoxRight                                     */
/*==============================================================*/
create table IMCompanyBoxRight (
   CompanyID            varchar(16)          not null,
   TerminalNo           varchar(20)          not null,
   BoxNo                varchar(20)          not null,
   Remark               varchar(256)         null,
   constraint PK_IMCOMPANYBOXRIGHT primary key (CompanyID, TerminalNo, BoxNo)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '公司使用格口权限',
   'user', @CurrentUser, 'table', 'IMCompanyBoxRight'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务商编号',
   'user', @CurrentUser, 'table', 'IMCompanyBoxRight', 'column', 'CompanyID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'IMCompanyBoxRight', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱门号',
   'user', @CurrentUser, 'table', 'IMCompanyBoxRight', 'column', 'BoxNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'IMCompanyBoxRight', 'column', 'Remark'
go

/*==============================================================*/
/* Table: IMCustomer                                            */
/*==============================================================*/
create table IMCustomer (
   CustomerID           varchar(32)          not null,
   TerminalNo           varchar(20)          not null,
   CustomerName         varchar(60)          not null,
   IDCard               varchar(32)          null,
   Address              varchar(256)         null,
   Email                varchar(64)          null,
   Mobile               varchar(30)          not null,
   Active               varchar(2)           not null,
   Status               varchar(4)           null,
   ActiveCode           varchar(16)          null,
   Validity             datetime             not null,
   keepMonths           int                  null,
   CreateTime           datetime             null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null,
   constraint PK_IMCUSTOMER primary key nonclustered (CustomerID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '个人客户信息表',
   'user', @CurrentUser, 'table', 'IMCustomer'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '客户编号',
   'user', @CurrentUser, 'table', 'IMCustomer', 'column', 'CustomerID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'IMCustomer', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '客户姓名',
   'user', @CurrentUser, 'table', 'IMCustomer', 'column', 'CustomerName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '身份证号',
   'user', @CurrentUser, 'table', 'IMCustomer', 'column', 'IDCard'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '客户地址',
   'user', @CurrentUser, 'table', 'IMCustomer', 'column', 'Address'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '客户邮箱',
   'user', @CurrentUser, 'table', 'IMCustomer', 'column', 'Email'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '客户手机',
   'user', @CurrentUser, 'table', 'IMCustomer', 'column', 'Mobile'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '客户激活标识',
   'user', @CurrentUser, 'table', 'IMCustomer', 'column', 'Active'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '客户状态',
   'user', @CurrentUser, 'table', 'IMCustomer', 'column', 'Status'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '激活码',
   'user', @CurrentUser, 'table', 'IMCustomer', 'column', 'ActiveCode'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '有效期',
   'user', @CurrentUser, 'table', 'IMCustomer', 'column', 'Validity'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '保留期',
   'user', @CurrentUser, 'table', 'IMCustomer', 'column', 'keepMonths'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'IMCustomer', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'IMCustomer', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'IMCustomer', 'column', 'Remark'
go

/*==============================================================*/
/* Table: IMEcommercePartner                                    */
/*==============================================================*/
create table IMEcommercePartner (
   EPartnerID           varchar(20)          not null,
   EPartnerName         varchar(64)          null,
   Address              varchar(256)         null,
   Email                varchar(64)          null,
   Mobile               varchar(30)          null,
   Username             varchar(32)          null,
   Password             varchar(32)          null,
   Active               varchar(2)           not null,
   CreateTime           datetime             null,
   Remark               varchar(256)         null,
   constraint PK_IMECOMMERCEPARTNER primary key nonclustered (EPartnerID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '电子商务伙伴',
   'user', @CurrentUser, 'table', 'IMEcommercePartner'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '电商伙伴编号',
   'user', @CurrentUser, 'table', 'IMEcommercePartner', 'column', 'EPartnerID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '电商伙伴名称',
   'user', @CurrentUser, 'table', 'IMEcommercePartner', 'column', 'EPartnerName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '电商伙伴地址',
   'user', @CurrentUser, 'table', 'IMEcommercePartner', 'column', 'Address'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '电商伙伴邮箱',
   'user', @CurrentUser, 'table', 'IMEcommercePartner', 'column', 'Email'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '电商伙伴手机',
   'user', @CurrentUser, 'table', 'IMEcommercePartner', 'column', 'Mobile'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '电商伙伴用户',
   'user', @CurrentUser, 'table', 'IMEcommercePartner', 'column', 'Username'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '电商伙伴密码',
   'user', @CurrentUser, 'table', 'IMEcommercePartner', 'column', 'Password'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '接口是否激活',
   'user', @CurrentUser, 'table', 'IMEcommercePartner', 'column', 'Active'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'IMEcommercePartner', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'IMEcommercePartner', 'column', 'Remark'
go

/*==============================================================*/
/* Table: IMGateway                                             */
/*==============================================================*/
create table IMGateway (
   GatewayID            varchar(8)           not null,
   GatewayName          varchar(64)          not null,
   SMSUsername          varchar(64)          not null,
   SMSPassword          varchar(32)          not null,
   SMSUsercode          varchar(64)          not null,
   SMSField1            varchar(64)          null,
   SMSField2            varchar(64)          null,
   SMSField3            varchar(64)          null,
   SMSTemplate          varchar(256)         null,
   GatewayURL           varchar(256)         not null,
   EmailParam1          varchar(64)          null,
   EmailParam2          varchar(64)          null,
   EmailParam3          varchar(64)          null,
   EmailParam4          varchar(64)          null,
   EmailTemplate        varchar(256)         null,
   TemplateType         varchar(4)           not null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null,
   constraint PK_IMGATEWAY primary key nonclustered (GatewayID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '短信入口信息表',
   'user', @CurrentUser, 'table', 'IMGateway'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '短信邮件接口编号',
   'user', @CurrentUser, 'table', 'IMGateway', 'column', 'GatewayID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '短信邮件接口名称',
   'user', @CurrentUser, 'table', 'IMGateway', 'column', 'GatewayName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '短信接入用户名',
   'user', @CurrentUser, 'table', 'IMGateway', 'column', 'SMSUsername'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '短信接入密码',
   'user', @CurrentUser, 'table', 'IMGateway', 'column', 'SMSPassword'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '短信用户编码',
   'user', @CurrentUser, 'table', 'IMGateway', 'column', 'SMSUsercode'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '短信接口字段1',
   'user', @CurrentUser, 'table', 'IMGateway', 'column', 'SMSField1'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '接口字段2',
   'user', @CurrentUser, 'table', 'IMGateway', 'column', 'SMSField2'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '接口字段3',
   'user', @CurrentUser, 'table', 'IMGateway', 'column', 'SMSField3'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '短信模板',
   'user', @CurrentUser, 'table', 'IMGateway', 'column', 'SMSTemplate'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '短信接口地址',
   'user', @CurrentUser, 'table', 'IMGateway', 'column', 'GatewayURL'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮件接口参数1',
   'user', @CurrentUser, 'table', 'IMGateway', 'column', 'EmailParam1'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮件接口参数2',
   'user', @CurrentUser, 'table', 'IMGateway', 'column', 'EmailParam2'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮件接口参数3',
   'user', @CurrentUser, 'table', 'IMGateway', 'column', 'EmailParam3'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮件接口参数4',
   'user', @CurrentUser, 'table', 'IMGateway', 'column', 'EmailParam4'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮件模板',
   'user', @CurrentUser, 'table', 'IMGateway', 'column', 'EmailTemplate'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '模板类型',
   'user', @CurrentUser, 'table', 'IMGateway', 'column', 'TemplateType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'IMGateway', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'IMGateway', 'column', 'Remark'
go

/*==============================================================*/
/* Table: IMPostalProcessingCenter                              */
/*==============================================================*/
create table IMPostalProcessingCenter (
   PPCID                varchar(10)          not null,
   CompanyID            varchar(16)          not null,
   PPCName              varchar(64)          not null,
   Username             varchar(32)          null,
   Password             varchar(32)          null,
   PPCServerIP          varchar(32)          null,
   PPCServerURL         varchar(512)         null,
   PPCServerUsername    varchar(32)          null,
   PPCServerPassword    varchar(32)          null,
   CreateTime           datetime             null,
   Remark               varchar(256)         null,
   constraint PK_IMPOSTALPROCESSINGCENTER primary key nonclustered (PPCID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹处理中心（PPC）',
   'user', @CurrentUser, 'table', 'IMPostalProcessingCenter'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹处理中心编号',
   'user', @CurrentUser, 'table', 'IMPostalProcessingCenter', 'column', 'PPCID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务商编号',
   'user', @CurrentUser, 'table', 'IMPostalProcessingCenter', 'column', 'CompanyID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹处理中心名称',
   'user', @CurrentUser, 'table', 'IMPostalProcessingCenter', 'column', 'PPCName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '接入用户名',
   'user', @CurrentUser, 'table', 'IMPostalProcessingCenter', 'column', 'Username'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '接入用户密码',
   'user', @CurrentUser, 'table', 'IMPostalProcessingCenter', 'column', 'Password'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'PPC服务器IP',
   'user', @CurrentUser, 'table', 'IMPostalProcessingCenter', 'column', 'PPCServerIP'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'PPC服务器URL',
   'user', @CurrentUser, 'table', 'IMPostalProcessingCenter', 'column', 'PPCServerURL'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'PPC服务器接入用户名',
   'user', @CurrentUser, 'table', 'IMPostalProcessingCenter', 'column', 'PPCServerUsername'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'PPC服务器接入用户密码',
   'user', @CurrentUser, 'table', 'IMPostalProcessingCenter', 'column', 'PPCServerPassword'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'IMPostalProcessingCenter', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'IMPostalProcessingCenter', 'column', 'Remark'
go

/*==============================================================*/
/* Table: IMServiceCounter                                      */
/*==============================================================*/
create table IMServiceCounter (
   ServiceID            varchar(8)           not null,
   CounterType          varchar(4)           not null,
   CounterName          varchar(64)          null,
   CntValue             int                  not null,
   CntMaxValue          int                  null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null,
   constraint PK_IMSERVICECOUNTER primary key nonclustered (ServiceID, CounterType)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务计数器',
   'user', @CurrentUser, 'table', 'IMServiceCounter'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务类型编号',
   'user', @CurrentUser, 'table', 'IMServiceCounter', 'column', 'ServiceID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '计数器类型',
   'user', @CurrentUser, 'table', 'IMServiceCounter', 'column', 'CounterType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '计数器名称',
   'user', @CurrentUser, 'table', 'IMServiceCounter', 'column', 'CounterName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '计数器数值',
   'user', @CurrentUser, 'table', 'IMServiceCounter', 'column', 'CntValue'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '计数器最大值',
   'user', @CurrentUser, 'table', 'IMServiceCounter', 'column', 'CntMaxValue'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'IMServiceCounter', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'IMServiceCounter', 'column', 'Remark'
go

/*==============================================================*/
/* Table: IMServiceRate                                         */
/*==============================================================*/
create table IMServiceRate (
   ServiceID            varchar(8)           not null,
   ServiceName          varchar(64)          not null,
   ServiceAmt           numeric(16,3)        not null,
   ServiceAmtSmall      numeric(16,3)        null,
   ServiceAmtBig        numeric(16,3)        null,
   ServiceAmtMed        numeric(16,3)        null,
   ServicePrefix        varchar(32)          not null,
   ExtraServiceID       varchar(8)           null,
   Active               varchar(2)           not null,
   ReturnAmt            numeric(16,3)        not null,
   CreateTime           datetime             null,
   Remark               varchar(256)         null,
   constraint PK_IMSERVICERATE primary key nonclustered (ServiceID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务项目',
   'user', @CurrentUser, 'table', 'IMServiceRate'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务类型编号',
   'user', @CurrentUser, 'table', 'IMServiceRate', 'column', 'ServiceID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务名称',
   'user', @CurrentUser, 'table', 'IMServiceRate', 'column', 'ServiceName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务价格',
   'user', @CurrentUser, 'table', 'IMServiceRate', 'column', 'ServiceAmt'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '小箱价格',
   'user', @CurrentUser, 'table', 'IMServiceRate', 'column', 'ServiceAmtSmall'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '大箱价格',
   'user', @CurrentUser, 'table', 'IMServiceRate', 'column', 'ServiceAmtBig'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '中箱价格',
   'user', @CurrentUser, 'table', 'IMServiceRate', 'column', 'ServiceAmtMed'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务名称前缀（缩写）',
   'user', @CurrentUser, 'table', 'IMServiceRate', 'column', 'ServicePrefix'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '额外服务编号',
   'user', @CurrentUser, 'table', 'IMServiceRate', 'column', 'ExtraServiceID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务激活标识',
   'user', @CurrentUser, 'table', 'IMServiceRate', 'column', 'Active'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '退件服务价格',
   'user', @CurrentUser, 'table', 'IMServiceRate', 'column', 'ReturnAmt'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'IMServiceRate', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'IMServiceRate', 'column', 'Remark'
go

/*==============================================================*/
/* Table: IMZone                                                */
/*==============================================================*/
create table IMZone (
   ZoneID               varchar(20)          not null,
   CompanyID            varchar(16)          not null,
   ZoneName             varchar(64)          not null,
   Description          varchar(256)         null,
   CollectCharge        numeric(16,3)        null,
   MandatoryFlag        int                  null,
   CreateTime           datetime             null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null,
   constraint PK_IMZONE primary key nonclustered (ZoneID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分拣区域中心（AZC:Annoucing Zone Centers）',
   'user', @CurrentUser, 'table', 'IMZone'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分拣区域编号',
   'user', @CurrentUser, 'table', 'IMZone', 'column', 'ZoneID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务商编号',
   'user', @CurrentUser, 'table', 'IMZone', 'column', 'CompanyID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分拣区域名称',
   'user', @CurrentUser, 'table', 'IMZone', 'column', 'ZoneName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分拣区域描述',
   'user', @CurrentUser, 'table', 'IMZone', 'column', 'Description'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分拣中心揽件费用',
   'user', @CurrentUser, 'table', 'IMZone', 'column', 'CollectCharge'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '强制打印开关（按位表示，R1~R8，0表示不强制打印，1-表示强制打印）',
   'user', @CurrentUser, 'table', 'IMZone', 'column', 'MandatoryFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'IMZone', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'IMZone', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'IMZone', 'column', 'Remark'
go

/*==============================================================*/
/* Table: IMZoneCounter                                         */
/*==============================================================*/
create table IMZoneCounter (
   ZoneID               varchar(20)          not null,
   CounterType          varchar(4)           not null,
   CounterName          varchar(64)          null,
   CntValue             int                  not null,
   CntMaxValue          int                  null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null,
   constraint PK_IMZONECOUNTER primary key nonclustered (ZoneID, CounterType)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分拣中心计数器',
   'user', @CurrentUser, 'table', 'IMZoneCounter'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分拣区域编号',
   'user', @CurrentUser, 'table', 'IMZoneCounter', 'column', 'ZoneID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '计数器类型',
   'user', @CurrentUser, 'table', 'IMZoneCounter', 'column', 'CounterType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '计数器名称',
   'user', @CurrentUser, 'table', 'IMZoneCounter', 'column', 'CounterName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '计数器数值',
   'user', @CurrentUser, 'table', 'IMZoneCounter', 'column', 'CntValue'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '计数器最大值',
   'user', @CurrentUser, 'table', 'IMZoneCounter', 'column', 'CntMaxValue'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'IMZoneCounter', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'IMZoneCounter', 'column', 'Remark'
go

/*==============================================================*/
/* Table: IMZoneLockerRight                                     */
/*==============================================================*/
create table IMZoneLockerRight (
   ZoneID               varchar(20)          not null,
   TerminalNo           varchar(20)          not null,
   Remark               varchar(256)         null,
   constraint PK_IMZONELOCKERRIGHT primary key (ZoneID, TerminalNo)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分拣中心柜体权限',
   'user', @CurrentUser, 'table', 'IMZoneLockerRight'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分拣区域编号',
   'user', @CurrentUser, 'table', 'IMZoneLockerRight', 'column', 'ZoneID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'IMZoneLockerRight', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'IMZoneLockerRight', 'column', 'Remark'
go

/*==============================================================*/
/* Table: MBBindMobileWater                                     */
/*==============================================================*/
create table MBBindMobileWater (
   VerifyKey            varchar(60)          not null,
   ExpiredTime          datetime             not null,
   BindMobile           varchar(30)          not null,
   PostmanID            varchar(32)          null,
   LastModifyTime       datetime             not null,
   Remark               varchar(256)         null default '',
   constraint PK_MBBINDMOBILEWATER primary key nonclustered (VerifyKey)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮递员手机绑定流水',
   'user', @CurrentUser, 'table', 'MBBindMobileWater'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '验证码',
   'user', @CurrentUser, 'table', 'MBBindMobileWater', 'column', 'VerifyKey'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '激活有效期',
   'user', @CurrentUser, 'table', 'MBBindMobileWater', 'column', 'ExpiredTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '绑定手机',
   'user', @CurrentUser, 'table', 'MBBindMobileWater', 'column', 'BindMobile'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮递员编号',
   'user', @CurrentUser, 'table', 'MBBindMobileWater', 'column', 'PostmanID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'MBBindMobileWater', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'MBBindMobileWater', 'column', 'Remark'
go

/*==============================================================*/
/* Table: MBBoxStatusLog                                        */
/*==============================================================*/
create table MBBoxStatusLog (
   TerminalNo           varchar(20)          not null,
   BoxNo                varchar(20)          not null,
   BoxStatus            varchar(4)           not null,
   BoxUsedStatus        varchar(4)           not null,
   BoxArticleStatus     varchar(4)           not null,
   BoxDoorStatus        varchar(4)           not null,
   LogDate              date                 not null,
   LogTime              datetime             not null,
   Remark               varchar(256)         null default '',
   constraint PK_MBBOXSTATUSLOG primary key nonclustered (TerminalNo, BoxNo)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱状态报告',
   'user', @CurrentUser, 'table', 'MBBoxStatusLog'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'MBBoxStatusLog', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱门号',
   'user', @CurrentUser, 'table', 'MBBoxStatusLog', 'column', 'BoxNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱状态 0:正常  1:锁定 2:故障 4:占用 9:未知',
   'user', @CurrentUser, 'table', 'MBBoxStatusLog', 'column', 'BoxStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱使用状态',
   'user', @CurrentUser, 'table', 'MBBoxStatusLog', 'column', 'BoxUsedStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱物品状态 0:无 1:有 9:未知',
   'user', @CurrentUser, 'table', 'MBBoxStatusLog', 'column', 'BoxArticleStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱门状态 0:关 1:开 9:未知',
   'user', @CurrentUser, 'table', 'MBBoxStatusLog', 'column', 'BoxDoorStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报告日期',
   'user', @CurrentUser, 'table', 'MBBoxStatusLog', 'column', 'LogDate'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报告时间',
   'user', @CurrentUser, 'table', 'MBBoxStatusLog', 'column', 'LogTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'MBBoxStatusLog', 'column', 'Remark'
go

/*==============================================================*/
/* Table: MBLockUserTime                                        */
/*==============================================================*/
create table MBLockUserTime (
   PackageID            varchar(32)          not null,
   LockedDate           date                 not null,
   WrongCount           int                  not null,
   LockStatus           varchar(2)           null,
   LockTimeRange        int                  null,
   LastModifyTime       datetime             not null,
   constraint PK_MBLOCKUSERTIME primary key nonclustered (PackageID, LockedDate)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '客户取件密码错误锁定时间表',
   'user', @CurrentUser, 'table', 'MBLockUserTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单号',
   'user', @CurrentUser, 'table', 'MBLockUserTime', 'column', 'PackageID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '锁定日期',
   'user', @CurrentUser, 'table', 'MBLockUserTime', 'column', 'LockedDate'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '输错次数',
   'user', @CurrentUser, 'table', 'MBLockUserTime', 'column', 'WrongCount'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '锁定状态',
   'user', @CurrentUser, 'table', 'MBLockUserTime', 'column', 'LockStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '锁定时间段',
   'user', @CurrentUser, 'table', 'MBLockUserTime', 'column', 'LockTimeRange'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'MBLockUserTime', 'column', 'LastModifyTime'
go

/*==============================================================*/
/* Table: MBMobileBlackList                                     */
/*==============================================================*/
create table MBMobileBlackList (
   CustomerMobile       varchar(30)          not null,
   LastModifyTime       datetime             not null,
   Remark               varchar(256)         null default '',
   constraint PK_MBMOBILEBLACKLIST primary key nonclustered (CustomerMobile)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '手机黑名单',
   'user', @CurrentUser, 'table', 'MBMobileBlackList'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人手机',
   'user', @CurrentUser, 'table', 'MBMobileBlackList', 'column', 'CustomerMobile'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'MBMobileBlackList', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'MBMobileBlackList', 'column', 'Remark'
go

/*==============================================================*/
/* Table: MBMonitorPictureInfo                                  */
/*==============================================================*/
create table MBMonitorPictureInfo (
   TerminalNo           varchar(20)          not null,
   WaterID              bigint               not null,
   PictureName          varchar(64)          not null,
   PicturePath          varchar(512)         not null,
   PictureType          varchar(4)           not null,
   PackageID            varchar(32)          null,
   CreateTime           datetime             null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null,
   constraint PK_MBMONITORPICTUREINFO primary key nonclustered (TerminalNo, WaterID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '监控图片信息',
   'user', @CurrentUser, 'table', 'MBMonitorPictureInfo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'MBMonitorPictureInfo', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流水号',
   'user', @CurrentUser, 'table', 'MBMonitorPictureInfo', 'column', 'WaterID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '图片名称',
   'user', @CurrentUser, 'table', 'MBMonitorPictureInfo', 'column', 'PictureName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '图片保存路径',
   'user', @CurrentUser, 'table', 'MBMonitorPictureInfo', 'column', 'PicturePath'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '图片类型',
   'user', @CurrentUser, 'table', 'MBMonitorPictureInfo', 'column', 'PictureType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单号',
   'user', @CurrentUser, 'table', 'MBMonitorPictureInfo', 'column', 'PackageID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建订单时间',
   'user', @CurrentUser, 'table', 'MBMonitorPictureInfo', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'MBMonitorPictureInfo', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'MBMonitorPictureInfo', 'column', 'Remark'
go

/*==============================================================*/
/* Table: MBPwdShortMsg                                         */
/*==============================================================*/
create table MBPwdShortMsg (
   WaterID              bigint               not null,
   TerminalNo           varchar(20)          not null,
   PackageID            varchar(32)          not null,
   StoredDate           date                 not null,
   StoredTime           datetime             not null,
   CustomerMobile       varchar(30)          not null,
   CustomerName         varchar(60)          null,
   OpenBoxKey           varchar(32)          null,
   MsgContent           varchar(4000)        null,
   SendStatus           varchar(4)           not null,
   ScheduleDateTime     datetime             null,
   UMID                 varchar(64)          null,
   MsgType              int                  null,
   ReSendNum            int                  null,
   LastModifyTime       datetime             null,
   PackageStatus        varchar(2)           not null,
   Remark               varchar(256)         null default '',
   constraint PK_MBPWDSHORTMSG primary key nonclustered (WaterID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '客户取件密码短消息',
   'user', @CurrentUser, 'table', 'MBPwdShortMsg'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流水号',
   'user', @CurrentUser, 'table', 'MBPwdShortMsg', 'column', 'WaterID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'MBPwdShortMsg', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单号',
   'user', @CurrentUser, 'table', 'MBPwdShortMsg', 'column', 'PackageID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '存物日期',
   'user', @CurrentUser, 'table', 'MBPwdShortMsg', 'column', 'StoredDate'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '存物时间',
   'user', @CurrentUser, 'table', 'MBPwdShortMsg', 'column', 'StoredTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人手机',
   'user', @CurrentUser, 'table', 'MBPwdShortMsg', 'column', 'CustomerMobile'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人姓名',
   'user', @CurrentUser, 'table', 'MBPwdShortMsg', 'column', 'CustomerName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '开箱密码',
   'user', @CurrentUser, 'table', 'MBPwdShortMsg', 'column', 'OpenBoxKey'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '消息内容',
   'user', @CurrentUser, 'table', 'MBPwdShortMsg', 'column', 'MsgContent'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '发送状态',
   'user', @CurrentUser, 'table', 'MBPwdShortMsg', 'column', 'SendStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '预定发送时间',
   'user', @CurrentUser, 'table', 'MBPwdShortMsg', 'column', 'ScheduleDateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '消息ID',
   'user', @CurrentUser, 'table', 'MBPwdShortMsg', 'column', 'UMID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '消息类型',
   'user', @CurrentUser, 'table', 'MBPwdShortMsg', 'column', 'MsgType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '重发次数',
   'user', @CurrentUser, 'table', 'MBPwdShortMsg', 'column', 'ReSendNum'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'MBPwdShortMsg', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹状态 ',
   'user', @CurrentUser, 'table', 'MBPwdShortMsg', 'column', 'PackageStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'MBPwdShortMsg', 'column', 'Remark'
go

/*==============================================================*/
/* Table: MBReminderWater                                       */
/*==============================================================*/
create table MBReminderWater (
   WaterID              bigint               not null,
   TerminalNo           varchar(20)          not null,
   CustomerMobile       varchar(30)          not null,
   PackageID            varchar(32)          not null,
   PostmanID            varchar(32)          not null,
   StoredDate           date                 not null,
   StoredTime           datetime             not null,
   ReminderNum          int                  not null,
   LastModifyTime       datetime             not null,
   Remark               varchar(256)         null default '',
   constraint PK_MBREMINDERWATER primary key nonclustered (WaterID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹催领流水',
   'user', @CurrentUser, 'table', 'MBReminderWater'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流水号',
   'user', @CurrentUser, 'table', 'MBReminderWater', 'column', 'WaterID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'MBReminderWater', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人手机',
   'user', @CurrentUser, 'table', 'MBReminderWater', 'column', 'CustomerMobile'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单号',
   'user', @CurrentUser, 'table', 'MBReminderWater', 'column', 'PackageID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮递员编号',
   'user', @CurrentUser, 'table', 'MBReminderWater', 'column', 'PostmanID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '存物日期',
   'user', @CurrentUser, 'table', 'MBReminderWater', 'column', 'StoredDate'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '存物时间',
   'user', @CurrentUser, 'table', 'MBReminderWater', 'column', 'StoredTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '催领短信',
   'user', @CurrentUser, 'table', 'MBReminderWater', 'column', 'ReminderNum'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'MBReminderWater', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'MBReminderWater', 'column', 'Remark'
go

/*==============================================================*/
/* Table: MBSignInfo                                            */
/*==============================================================*/
create table MBSignInfo (
   TerminalNo           varchar(20)          not null,
   SignTime             datetime             not null,
   SignIP               varchar(32)          null,
   SignMac              varchar(32)          null,
   SignKey              varchar(256)         null,
   SoftwareVersion      varchar(32)          null,
   InitPasswd           varchar(32)          null,
   OnlineStatus         varchar(2)           null,
   LastModifyTime       datetime             not null,
   Remark               varchar(256)         null default '',
   constraint PK_MBSIGNINFO primary key (TerminalNo)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备签到信息',
   'user', @CurrentUser, 'table', 'MBSignInfo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'MBSignInfo', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '签到时间',
   'user', @CurrentUser, 'table', 'MBSignInfo', 'column', 'SignTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '签到终端IP',
   'user', @CurrentUser, 'table', 'MBSignInfo', 'column', 'SignIP'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '签到终端MAC',
   'user', @CurrentUser, 'table', 'MBSignInfo', 'column', 'SignMac'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '签到生成密钥',
   'user', @CurrentUser, 'table', 'MBSignInfo', 'column', 'SignKey'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '软件版本号',
   'user', @CurrentUser, 'table', 'MBSignInfo', 'column', 'SoftwareVersion'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '安装密码',
   'user', @CurrentUser, 'table', 'MBSignInfo', 'column', 'InitPasswd'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '在线状态',
   'user', @CurrentUser, 'table', 'MBSignInfo', 'column', 'OnlineStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'MBSignInfo', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'MBSignInfo', 'column', 'Remark'
go

/*==============================================================*/
/* Table: MBSmsStat                                             */
/*==============================================================*/
create table MBSmsStat (
   TerminalNo           varchar(20)          not null,
   OccurYear            int                  not null,
   OccurMonth           int                  not null,
   TotalNum             int                  null,
   PwdNum               int                  null,
   ExpireNum            int                  null,
   ReminderNum          int                  null,
   DynamicNum           int                  null,
   PickupNum            int                  null,
   OtherNum             int                  null,
   constraint PK_MBSMSSTAT primary key nonclustered (TerminalNo, OccurYear, OccurMonth)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '短信统计表',
   'user', @CurrentUser, 'table', 'MBSmsStat'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'MBSmsStat', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '发生年份',
   'user', @CurrentUser, 'table', 'MBSmsStat', 'column', 'OccurYear'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '发生月份',
   'user', @CurrentUser, 'table', 'MBSmsStat', 'column', 'OccurMonth'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '短信总数量',
   'user', @CurrentUser, 'table', 'MBSmsStat', 'column', 'TotalNum'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件密码短信',
   'user', @CurrentUser, 'table', 'MBSmsStat', 'column', 'PwdNum'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '逾期取回短信',
   'user', @CurrentUser, 'table', 'MBSmsStat', 'column', 'ExpireNum'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '催领短信',
   'user', @CurrentUser, 'table', 'MBSmsStat', 'column', 'ReminderNum'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '动态密码短信',
   'user', @CurrentUser, 'table', 'MBSmsStat', 'column', 'DynamicNum'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '已取件短信',
   'user', @CurrentUser, 'table', 'MBSmsStat', 'column', 'PickupNum'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '其它短信',
   'user', @CurrentUser, 'table', 'MBSmsStat', 'column', 'OtherNum'
go

/*==============================================================*/
/* Table: MBTerminalAlertLog                                    */
/*==============================================================*/
create table MBTerminalAlertLog (
   TerminalNo           varchar(20)          not null,
   ReportWaterID        bigint               not null,
   AlertType            varchar(4)           not null,
   AlertLevel           varchar(4)           null,
   AlertContent         varchar(256)         null,
   BoxNo                varchar(20)          null,
   LogDate              date                 not null,
   LogTime              datetime             not null,
   Remark               varchar(256)         null default '',
   constraint PK_MBTERMINALALERTLOG primary key nonclustered (TerminalNo, ReportWaterID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备终端报警日志',
   'user', @CurrentUser, 'table', 'MBTerminalAlertLog'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'MBTerminalAlertLog', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报告流水号',
   'user', @CurrentUser, 'table', 'MBTerminalAlertLog', 'column', 'ReportWaterID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报警种类',
   'user', @CurrentUser, 'table', 'MBTerminalAlertLog', 'column', 'AlertType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报警等级',
   'user', @CurrentUser, 'table', 'MBTerminalAlertLog', 'column', 'AlertLevel'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报警内容',
   'user', @CurrentUser, 'table', 'MBTerminalAlertLog', 'column', 'AlertContent'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱门号',
   'user', @CurrentUser, 'table', 'MBTerminalAlertLog', 'column', 'BoxNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报告日期',
   'user', @CurrentUser, 'table', 'MBTerminalAlertLog', 'column', 'LogDate'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报告时间',
   'user', @CurrentUser, 'table', 'MBTerminalAlertLog', 'column', 'LogTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'MBTerminalAlertLog', 'column', 'Remark'
go

/*==============================================================*/
/* Table: MBTerminalStatusLog                                   */
/*==============================================================*/
create table MBTerminalStatusLog (
   TerminalNo           varchar(20)          not null,
   TerminalStatus       varchar(2)           null,
   CardReaderStatus     varchar(2)           null,
   ICStatus             varchar(2)           null,
   IDCardStatus         varchar(2)           null,
   BarcodeStatus        varchar(2)           null,
   PowerStatus          varchar(2)           null,
   PasskeyBoardStatus   varchar(2)           null,
   PrinterStatus        varchar(2)           null,
   Camera1Status        varchar(2)           null,
   Camera2Status        varchar(2)           null,
   Camera3Status        varchar(2)           null,
   TerminalTime         datetime             null,
   SoftwareVersion      varchar(32)          null,
   LogDate              date                 not null,
   LogTime              datetime             not null,
   Remark               varchar(256)         null default '',
   constraint PK_MBTERMINALSTATUSLOG primary key nonclustered (TerminalNo)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '柜状态报告',
   'user', @CurrentUser, 'table', 'MBTerminalStatusLog'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'MBTerminalStatusLog', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '柜体状态： 0:正常  1:锁定 2:故障  9:未知',
   'user', @CurrentUser, 'table', 'MBTerminalStatusLog', 'column', 'TerminalStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '读卡器状态',
   'user', @CurrentUser, 'table', 'MBTerminalStatusLog', 'column', 'CardReaderStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'IC设备状态',
   'user', @CurrentUser, 'table', 'MBTerminalStatusLog', 'column', 'ICStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'IDCard设备状态',
   'user', @CurrentUser, 'table', 'MBTerminalStatusLog', 'column', 'IDCardStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '条形码状态',
   'user', @CurrentUser, 'table', 'MBTerminalStatusLog', 'column', 'BarcodeStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '电源状态',
   'user', @CurrentUser, 'table', 'MBTerminalStatusLog', 'column', 'PowerStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '密码键盘状态',
   'user', @CurrentUser, 'table', 'MBTerminalStatusLog', 'column', 'PasskeyBoardStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '打印机状态',
   'user', @CurrentUser, 'table', 'MBTerminalStatusLog', 'column', 'PrinterStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '摄像1状态',
   'user', @CurrentUser, 'table', 'MBTerminalStatusLog', 'column', 'Camera1Status'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '摄像2状态',
   'user', @CurrentUser, 'table', 'MBTerminalStatusLog', 'column', 'Camera2Status'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '摄像3状态',
   'user', @CurrentUser, 'table', 'MBTerminalStatusLog', 'column', 'Camera3Status'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '本地时间',
   'user', @CurrentUser, 'table', 'MBTerminalStatusLog', 'column', 'TerminalTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '软件版本号',
   'user', @CurrentUser, 'table', 'MBTerminalStatusLog', 'column', 'SoftwareVersion'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报告日期',
   'user', @CurrentUser, 'table', 'MBTerminalStatusLog', 'column', 'LogDate'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报告时间',
   'user', @CurrentUser, 'table', 'MBTerminalStatusLog', 'column', 'LogTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'MBTerminalStatusLog', 'column', 'Remark'
go

/*==============================================================*/
/* Table: MBWrongPwdWater                                       */
/*==============================================================*/
create table MBWrongPwdWater (
   WaterID              bigint               not null,
   CustomerID           varchar(20)          not null,
   CustomerName         varchar(60)          null,
   WrongPwd             varchar(32)          null,
   PackageID            varchar(32)          null,
   TakedTime            datetime             not null,
   UploadFlag           varchar(2)           not null,
   Remark               varchar(256)         null default '',
   constraint PK_MBWRONGPWDWATER primary key nonclustered (WaterID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '客户取件密码错误流水',
   'user', @CurrentUser, 'table', 'MBWrongPwdWater'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流水号',
   'user', @CurrentUser, 'table', 'MBWrongPwdWater', 'column', 'WaterID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人编号',
   'user', @CurrentUser, 'table', 'MBWrongPwdWater', 'column', 'CustomerID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人姓名',
   'user', @CurrentUser, 'table', 'MBWrongPwdWater', 'column', 'CustomerName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '错误密码',
   'user', @CurrentUser, 'table', 'MBWrongPwdWater', 'column', 'WrongPwd'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单号',
   'user', @CurrentUser, 'table', 'MBWrongPwdWater', 'column', 'PackageID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件时间',
   'user', @CurrentUser, 'table', 'MBWrongPwdWater', 'column', 'TakedTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '上传标志 0:未上传 1:已上传',
   'user', @CurrentUser, 'table', 'MBWrongPwdWater', 'column', 'UploadFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'MBWrongPwdWater', 'column', 'Remark'
go

/*==============================================================*/
/* Table: OPFunction                                            */
/*==============================================================*/
create table OPFunction (
   FunctionID           varchar(8)           not null,
   FunctionName         varchar(64)          not null,
   FunctionServiceName  varchar(60)          null,
   LogFlag              varchar(2)           not null,
   OpenFlag             varchar(2)           null,
   constraint PK_OPFUNCTION primary key nonclustered (FunctionID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '功能表',
   'user', @CurrentUser, 'table', 'OPFunction'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '功能编号',
   'user', @CurrentUser, 'table', 'OPFunction', 'column', 'FunctionID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '功能编号名称',
   'user', @CurrentUser, 'table', 'OPFunction', 'column', 'FunctionName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '功能对应服务名称',
   'user', @CurrentUser, 'table', 'OPFunction', 'column', 'FunctionServiceName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '记录日志标志',
   'user', @CurrentUser, 'table', 'OPFunction', 'column', 'LogFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '开通标志',
   'user', @CurrentUser, 'table', 'OPFunction', 'column', 'OpenFlag'
go

/*==============================================================*/
/* Table: OPMenu                                                */
/*==============================================================*/
create table OPMenu (
   MenuID               varchar(8)           not null,
   MenuName             varchar(64)          not null default '',
   MenuLevel            int                  not null default 0,
   MenuEngName          varchar(60)          null,
   CompShortName        varchar(60)          null,
   Description          varchar(256)         null default '',
   Action               varchar(256)         null default '',
   HotKey               int                  null,
   Icon                 varchar(256)         null default '',
   HelpContext          varchar(256)         null default '',
   MenuType             int                  not null default 0,
   LeafFlag             int                  not null,
   Remark               varchar(4000)        null,
   constraint PK_OPMENU primary key nonclustered (MenuID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '菜单表',
   'user', @CurrentUser, 'table', 'OPMenu'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '菜单号',
   'user', @CurrentUser, 'table', 'OPMenu', 'column', 'MenuID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '菜单中文名称',
   'user', @CurrentUser, 'table', 'OPMenu', 'column', 'MenuName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '菜单级别',
   'user', @CurrentUser, 'table', 'OPMenu', 'column', 'MenuLevel'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '菜单英文文名称',
   'user', @CurrentUser, 'table', 'OPMenu', 'column', 'MenuEngName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹服务商简称',
   'user', @CurrentUser, 'table', 'OPMenu', 'column', 'CompShortName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '菜单说明',
   'user', @CurrentUser, 'table', 'OPMenu', 'column', 'Description'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '相应的操作',
   'user', @CurrentUser, 'table', 'OPMenu', 'column', 'Action'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '热键',
   'user', @CurrentUser, 'table', 'OPMenu', 'column', 'HotKey'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '图标',
   'user', @CurrentUser, 'table', 'OPMenu', 'column', 'Icon'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '帮助文件',
   'user', @CurrentUser, 'table', 'OPMenu', 'column', 'HelpContext'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '菜单类别 0:菜单 1:功能按钮',
   'user', @CurrentUser, 'table', 'OPMenu', 'column', 'MenuType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '叶结点标志 0:非叶子结点1:叶子结点',
   'user', @CurrentUser, 'table', 'OPMenu', 'column', 'LeafFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'OPMenu', 'column', 'Remark'
go

/*==============================================================*/
/* Table: OPOperAllLimit                                        */
/*==============================================================*/
create table OPOperAllLimit (
   OperID               varchar(64)          not null,
   LimitTypeID          int                  not null,
   LimitObject          varchar(20)          not null default '',
   LimitObjectName      varchar(64)          null default '',
   constraint PK_OPOPERALLLIMIT primary key nonclustered (OperID, LimitTypeID, LimitObject)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作员总体限制表',
   'user', @CurrentUser, 'table', 'OPOperAllLimit'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作员编号',
   'user', @CurrentUser, 'table', 'OPOperAllLimit', 'column', 'OperID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '限制类别编号',
   'user', @CurrentUser, 'table', 'OPOperAllLimit', 'column', 'LimitTypeID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '限制的对象',
   'user', @CurrentUser, 'table', 'OPOperAllLimit', 'column', 'LimitObject'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '限制的对象名称',
   'user', @CurrentUser, 'table', 'OPOperAllLimit', 'column', 'LimitObjectName'
go

/*==============================================================*/
/* Table: OPOperOnline                                          */
/*==============================================================*/
create table OPOperOnline (
   OperID               varchar(64)          not null,
   OperType             int                  not null default 0,
   ZoneID               varchar(20)          null default '',
   LoginInTime          datetime             not null,
   LoginOutTime         datetime             null,
   NetCardAddr          varchar(64)          null default '',
   LoginIPAddr          varchar(30)          null default '',
   LastLoginTime        datetime             null,
   LastLoginIP          varchar(30)          null,
   PreVersion           varchar(20)          null,
   LastQueryTime        datetime             null,
   CurrentVersion       varchar(20)          null,
   LoginTerminal        varchar(4)           null,
   PreClientVession     varchar(20)          null,
   CurrentClientVersion varchar(20)          null,
   OnlineStatus         varchar(2)           not null default '0',
   constraint PK_OPOPERONLINE primary key nonclustered (OperID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作员在线信息',
   'user', @CurrentUser, 'table', 'OPOperOnline'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作员编号',
   'user', @CurrentUser, 'table', 'OPOperOnline', 'column', 'OperID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户类别',
   'user', @CurrentUser, 'table', 'OPOperOnline', 'column', 'OperType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分拣区域编号',
   'user', @CurrentUser, 'table', 'OPOperOnline', 'column', 'ZoneID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '登入时间',
   'user', @CurrentUser, 'table', 'OPOperOnline', 'column', 'LoginInTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '登出时间',
   'user', @CurrentUser, 'table', 'OPOperOnline', 'column', 'LoginOutTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '网卡地址',
   'user', @CurrentUser, 'table', 'OPOperOnline', 'column', 'NetCardAddr'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '登陆IP地址',
   'user', @CurrentUser, 'table', 'OPOperOnline', 'column', 'LoginIPAddr'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '上一次登入时间',
   'user', @CurrentUser, 'table', 'OPOperOnline', 'column', 'LastLoginTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '上一次登录IP',
   'user', @CurrentUser, 'table', 'OPOperOnline', 'column', 'LastLoginIP'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '上次登录版本号',
   'user', @CurrentUser, 'table', 'OPOperOnline', 'column', 'PreVersion'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '上一次查询时间',
   'user', @CurrentUser, 'table', 'OPOperOnline', 'column', 'LastQueryTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '当前版本号',
   'user', @CurrentUser, 'table', 'OPOperOnline', 'column', 'CurrentVersion'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '登录终端 10:网页 11：客户端 ',
   'user', @CurrentUser, 'table', 'OPOperOnline', 'column', 'LoginTerminal'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '上次登录客户端版本号',
   'user', @CurrentUser, 'table', 'OPOperOnline', 'column', 'PreClientVession'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '当前客户端版本号',
   'user', @CurrentUser, 'table', 'OPOperOnline', 'column', 'CurrentClientVersion'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '在线状态',
   'user', @CurrentUser, 'table', 'OPOperOnline', 'column', 'OnlineStatus'
go

/*==============================================================*/
/* Table: OPOperRole                                            */
/*==============================================================*/
create table OPOperRole (
   OperID               varchar(64)          not null,
   RoleID               int                  not null,
   constraint PK_OPOPERROLE primary key (OperID, RoleID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作员角色',
   'user', @CurrentUser, 'table', 'OPOperRole'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作员编号',
   'user', @CurrentUser, 'table', 'OPOperRole', 'column', 'OperID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色编号',
   'user', @CurrentUser, 'table', 'OPOperRole', 'column', 'RoleID'
go

/*==============================================================*/
/* Table: OPOperSpeRight                                        */
/*==============================================================*/
create table OPOperSpeRight (
   OperID               varchar(64)          not null,
   SpePrivID            int                  not null,
   constraint PK_OPOPERSPERIGHT primary key (OperID, SpePrivID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作员的特殊权限表',
   'user', @CurrentUser, 'table', 'OPOperSpeRight'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作员编号',
   'user', @CurrentUser, 'table', 'OPOperSpeRight', 'column', 'OperID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '特殊权限号',
   'user', @CurrentUser, 'table', 'OPOperSpeRight', 'column', 'SpePrivID'
go

/*==============================================================*/
/* Table: OPOperToMenu                                          */
/*==============================================================*/
create table OPOperToMenu (
   OperID               varchar(64)          not null,
   MenuID               varchar(8)           not null,
   constraint PK_OPOPERTOMENU primary key (OperID, MenuID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作员菜单表',
   'user', @CurrentUser, 'table', 'OPOperToMenu'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作员编号',
   'user', @CurrentUser, 'table', 'OPOperToMenu', 'column', 'OperID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '菜单号',
   'user', @CurrentUser, 'table', 'OPOperToMenu', 'column', 'MenuID'
go

/*==============================================================*/
/* Table: OPOperator                                            */
/*==============================================================*/
create table OPOperator (
   OperID               varchar(64)          not null,
   ZoneID               varchar(20)          not null,
   UserID               varchar(64)          null,
   OperType             int                  not null,
   OperName             varchar(64)          not null,
   OperPassword         varchar(32)          not null,
   PlainPassword        varchar(32)          null,
   BindCardID           varchar(32)          null,
   BindMobile           varchar(30)          null,
   OfficeTel            varchar(30)          null,
   Mobile               varchar(30)          null,
   Email                varchar(64)          null,
   UpperUserID          varchar(64)          not null,
   CreateDate           date                 not null,
   CreateTime           datetime             not null,
   OperStatus           varchar(2)           not null,
   LastModifyTime       datetime             null,
   Remark               varchar(4000)        null,
   constraint PK_OPOPERATOR primary key nonclustered (OperID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作员信息',
   'user', @CurrentUser, 'table', 'OPOperator'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作员编号',
   'user', @CurrentUser, 'table', 'OPOperator', 'column', 'OperID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分拣区域编号',
   'user', @CurrentUser, 'table', 'OPOperator', 'column', 'ZoneID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户编号',
   'user', @CurrentUser, 'table', 'OPOperator', 'column', 'UserID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户类别',
   'user', @CurrentUser, 'table', 'OPOperator', 'column', 'OperType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户名称',
   'user', @CurrentUser, 'table', 'OPOperator', 'column', 'OperName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户密码',
   'user', @CurrentUser, 'table', 'OPOperator', 'column', 'OperPassword'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Plain密码',
   'user', @CurrentUser, 'table', 'OPOperator', 'column', 'PlainPassword'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '绑定卡号',
   'user', @CurrentUser, 'table', 'OPOperator', 'column', 'BindCardID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '绑定手机',
   'user', @CurrentUser, 'table', 'OPOperator', 'column', 'BindMobile'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '办公电话',
   'user', @CurrentUser, 'table', 'OPOperator', 'column', 'OfficeTel'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作员手机',
   'user', @CurrentUser, 'table', 'OPOperator', 'column', 'Mobile'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '电子邮箱',
   'user', @CurrentUser, 'table', 'OPOperator', 'column', 'Email'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '大写员工编号',
   'user', @CurrentUser, 'table', 'OPOperator', 'column', 'UpperUserID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建日期',
   'user', @CurrentUser, 'table', 'OPOperator', 'column', 'CreateDate'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'OPOperator', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户状态 ',
   'user', @CurrentUser, 'table', 'OPOperator', 'column', 'OperStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'OPOperator', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'OPOperator', 'column', 'Remark'
go

/*==============================================================*/
/* Table: OPOperatorLog                                         */
/*==============================================================*/
create table OPOperatorLog (
   OperLogID            bigint               not null,
   OperID               varchar(64)          null,
   FunctionID           varchar(8)           not null,
   OperType             int                  null default 0,
   TerminalNo           varchar(20)          null,
   OccurDate            date                 not null,
   OccurTime            datetime             not null,
   StationAddr          varchar(30)          null default '',
   Remark               varchar(4000)        null,
   constraint PK_OPOPERATORLOG primary key nonclustered (OperLogID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作日志',
   'user', @CurrentUser, 'table', 'OPOperatorLog'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户日志序号',
   'user', @CurrentUser, 'table', 'OPOperatorLog', 'column', 'OperLogID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作员编号',
   'user', @CurrentUser, 'table', 'OPOperatorLog', 'column', 'OperID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '功能编号',
   'user', @CurrentUser, 'table', 'OPOperatorLog', 'column', 'FunctionID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户类别',
   'user', @CurrentUser, 'table', 'OPOperatorLog', 'column', 'OperType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'OPOperatorLog', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '发生日期',
   'user', @CurrentUser, 'table', 'OPOperatorLog', 'column', 'OccurDate'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '发生时间',
   'user', @CurrentUser, 'table', 'OPOperatorLog', 'column', 'OccurTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '站点地址',
   'user', @CurrentUser, 'table', 'OPOperatorLog', 'column', 'StationAddr'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'OPOperatorLog', 'column', 'Remark'
go

/*==============================================================*/
/* Table: OPRole                                                */
/*==============================================================*/
create table OPRole (
   RoleID               int                  not null,
   RoleName             varchar(60)          not null,
   OperID               varchar(64)          null,
   constraint PK_OPROLE primary key nonclustered (RoleID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色信息',
   'user', @CurrentUser, 'table', 'OPRole'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色编号',
   'user', @CurrentUser, 'table', 'OPRole', 'column', 'RoleID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色名称',
   'user', @CurrentUser, 'table', 'OPRole', 'column', 'RoleName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '用户内部编号',
   'user', @CurrentUser, 'table', 'OPRole', 'column', 'OperID'
go

/*==============================================================*/
/* Table: OPRoleAllLimit                                        */
/*==============================================================*/
create table OPRoleAllLimit (
   RoleID               int                  not null,
   LimitTypeID          int                  not null,
   LimitObject          varchar(20)          not null default '',
   LimitObjectName      varchar(64)          null default '',
   constraint PK_OPROLEALLLIMIT primary key nonclustered (RoleID, LimitTypeID, LimitObject)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色总体限制表',
   'user', @CurrentUser, 'table', 'OPRoleAllLimit'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色编号',
   'user', @CurrentUser, 'table', 'OPRoleAllLimit', 'column', 'RoleID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '限制类别编号',
   'user', @CurrentUser, 'table', 'OPRoleAllLimit', 'column', 'LimitTypeID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '限制的对象',
   'user', @CurrentUser, 'table', 'OPRoleAllLimit', 'column', 'LimitObject'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '限制的对象名称',
   'user', @CurrentUser, 'table', 'OPRoleAllLimit', 'column', 'LimitObjectName'
go

/*==============================================================*/
/* Table: OPRoleSpeRight                                        */
/*==============================================================*/
create table OPRoleSpeRight (
   SpePrivID            int                  not null,
   RoleID               int                  not null,
   constraint PK_OPROLESPERIGHT primary key (SpePrivID, RoleID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色特殊权限表',
   'user', @CurrentUser, 'table', 'OPRoleSpeRight'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '特殊权限号',
   'user', @CurrentUser, 'table', 'OPRoleSpeRight', 'column', 'SpePrivID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色编号',
   'user', @CurrentUser, 'table', 'OPRoleSpeRight', 'column', 'RoleID'
go

/*==============================================================*/
/* Table: OPRoleToMenu                                          */
/*==============================================================*/
create table OPRoleToMenu (
   MenuID               varchar(8)           not null,
   RoleID               int                  not null,
   constraint PK_OPROLETOMENU primary key (MenuID, RoleID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色菜单表',
   'user', @CurrentUser, 'table', 'OPRoleToMenu'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '菜单号',
   'user', @CurrentUser, 'table', 'OPRoleToMenu', 'column', 'MenuID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '角色编号',
   'user', @CurrentUser, 'table', 'OPRoleToMenu', 'column', 'RoleID'
go

/*==============================================================*/
/* Table: OPSpecialPriv                                         */
/*==============================================================*/
create table OPSpecialPriv (
   SpePrivID            int                  not null,
   SpePrivName          varchar(64)          not null default '',
   constraint PK_OPSPECIALPRIV primary key nonclustered (SpePrivID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '特殊权限信息表',
   'user', @CurrentUser, 'table', 'OPSpecialPriv'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '特殊权限号',
   'user', @CurrentUser, 'table', 'OPSpecialPriv', 'column', 'SpePrivID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '特殊权限名称',
   'user', @CurrentUser, 'table', 'OPSpecialPriv', 'column', 'SpePrivName'
go

/*==============================================================*/
/* Table: PAControlParam                                        */
/*==============================================================*/
create table PAControlParam (
   CtrlTypeID           int                  not null,
   CtrlTypeName         varchar(64)          not null default '',
   KeyString            varchar(60)          not null,
   DefaultValue         varchar(1000)        null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null default '',
   constraint PK_PACONTROLPARAM primary key nonclustered (CtrlTypeID, KeyString)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '系统控制参数',
   'user', @CurrentUser, 'table', 'PAControlParam'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '参数类型',
   'user', @CurrentUser, 'table', 'PAControlParam', 'column', 'CtrlTypeID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '参数类型名称',
   'user', @CurrentUser, 'table', 'PAControlParam', 'column', 'CtrlTypeName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '关键词串',
   'user', @CurrentUser, 'table', 'PAControlParam', 'column', 'KeyString'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '默认值',
   'user', @CurrentUser, 'table', 'PAControlParam', 'column', 'DefaultValue'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'PAControlParam', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'PAControlParam', 'column', 'Remark'
go

/*==============================================================*/
/* Table: PADictionary                                          */
/*==============================================================*/
create table PADictionary (
   DictTypeID           int                  not null default 0,
   DictTypeName         varchar(64)          not null default '',
   DictID               varchar(4)           not null,
   DictName             varchar(64)          not null default '',
   OtherDictName        varchar(64)          null,
   Remark               varchar(256)         null default '',
   constraint PK_PADICTIONARY primary key nonclustered (DictID, DictTypeID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '系统字典表',
   'user', @CurrentUser, 'table', 'PADictionary'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字典类型编号',
   'user', @CurrentUser, 'table', 'PADictionary', 'column', 'DictTypeID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字典类型名称',
   'user', @CurrentUser, 'table', 'PADictionary', 'column', 'DictTypeName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字典编号',
   'user', @CurrentUser, 'table', 'PADictionary', 'column', 'DictID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '字典名称',
   'user', @CurrentUser, 'table', 'PADictionary', 'column', 'DictName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '对照字典名称',
   'user', @CurrentUser, 'table', 'PADictionary', 'column', 'OtherDictName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'PADictionary', 'column', 'Remark'
go

/*==============================================================*/
/* Table: PASequence                                            */
/*==============================================================*/
create table PASequence (
   SeqName              varchar(64)          not null default '',
   SeqValue             bigint               not null,
   CacheSize            int                  not null default 0,
   SeqMaxValue          int                  null,
   constraint PK_PASEQUENCE primary key nonclustered (SeqName)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '序列号',
   'user', @CurrentUser, 'table', 'PASequence'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '序列名称',
   'user', @CurrentUser, 'table', 'PASequence', 'column', 'SeqName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '当前序列号值',
   'user', @CurrentUser, 'table', 'PASequence', 'column', 'SeqValue'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '缓存大小',
   'user', @CurrentUser, 'table', 'PASequence', 'column', 'CacheSize'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最大序号值',
   'user', @CurrentUser, 'table', 'PASequence', 'column', 'SeqMaxValue'
go

/*==============================================================*/
/* Table: PATerminalCtrlParam                                   */
/*==============================================================*/
create table PATerminalCtrlParam (
   CtrlTypeID           int                  not null,
   CtrlTypeName         varchar(64)          not null default '',
   KeyString            varchar(60)          not null,
   DefaultValue         varchar(1000)        not null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null default '',
   constraint PK_PATERMINALCTRLPARAM primary key nonclustered (CtrlTypeID, KeyString)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备端控制参数',
   'user', @CurrentUser, 'table', 'PATerminalCtrlParam'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '参数类型',
   'user', @CurrentUser, 'table', 'PATerminalCtrlParam', 'column', 'CtrlTypeID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '参数类型名称',
   'user', @CurrentUser, 'table', 'PATerminalCtrlParam', 'column', 'CtrlTypeName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '关键词串',
   'user', @CurrentUser, 'table', 'PATerminalCtrlParam', 'column', 'KeyString'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '默认值',
   'user', @CurrentUser, 'table', 'PATerminalCtrlParam', 'column', 'DefaultValue'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'PATerminalCtrlParam', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'PATerminalCtrlParam', 'column', 'Remark'
go

/*==============================================================*/
/* Table: PMPostman                                             */
/*==============================================================*/
create table PMPostman (
   PostmanID            varchar(32)          not null,
   ZoneID               varchar(20)          not null,
   CollectZoneID        varchar(30)          null,
   PostmanType          varchar(4)           not null,
   PostmanName          varchar(60)          not null,
   Password             varchar(32)          not null,
   PlainPassword        varchar(32)          null,
   BindCardID           varchar(32)          null,
   BindMobile           varchar(30)          null,
   IDCard               varchar(32)          null,
   OfficeTel            varchar(30)          null,
   Mobile               varchar(30)          null,
   Email                varchar(64)          null,
   ContactAddress       varchar(256)         null,
   InputMobileFlag      varchar(2)           null,
   PostmanStatus        varchar(2)           null,
   DropRight            varchar(2)           not null,
   ReturnRight          varchar(2)           not null,
   CollectRight         varchar(2)           not null default '0',
   DADRight             varchar(2)           not null default '0',
   CreateManID          varchar(64)          null,
   CreateDate           date                 null,
   CreateTime           datetime             null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null default '',
   constraint PK_PMPOSTMAN primary key nonclustered (PostmanID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮递员信息',
   'user', @CurrentUser, 'table', 'PMPostman'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮递员编号',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'PostmanID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分拣区域编号',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'ZoneID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '揽件区域编号',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'CollectZoneID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮递员类型',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'PostmanType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮递员姓名',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'PostmanName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮递员密码',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'Password'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'Plain密码',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'PlainPassword'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '绑定卡号',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'BindCardID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '绑定手机',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'BindMobile'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '身份证号',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'IDCard'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '办公电话',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'OfficeTel'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮递员手机',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'Mobile'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '电子邮箱',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'Email'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '联系地址',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'ContactAddress'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '投递输入客户手机标志 0:不需要输入 1:需要输入',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'InputMobileFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮递员状态',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'PostmanStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '投递权限',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'DropRight'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '逾期取件权限',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'ReturnRight'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '揽件权限',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'CollectRight'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '直投权限',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'DADRight'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建人员编号',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'CreateManID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建日期',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'CreateDate'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'PMPostman', 'column', 'Remark'
go

/*==============================================================*/
/* Table: PMPostmanLockerRight                                  */
/*==============================================================*/
create table PMPostmanLockerRight (
   TerminalNo           varchar(20)          not null,
   PostmanID            varchar(32)          not null,
   Remark               varchar(256)         null default '',
   constraint PK_PMPOSTMANLOCKERRIGHT primary key (TerminalNo, PostmanID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮递员柜体权限',
   'user', @CurrentUser, 'table', 'PMPostmanLockerRight'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'PMPostmanLockerRight', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮递员编号',
   'user', @CurrentUser, 'table', 'PMPostmanLockerRight', 'column', 'PostmanID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'PMPostmanLockerRight', 'column', 'Remark'
go

/*==============================================================*/
/* Table: PTDeliverHistory                                      */
/*==============================================================*/
create table PTDeliverHistory (
   PackageID            varchar(32)          not null,
   CreateTime           datetime             not null,
   ItemStatus           varchar(4)           not null,
   RunStatus            varchar(4)           null,
   PPCID                varchar(10)          null,
   ZoneID               varchar(10)          null,
   CompanyID            varchar(8)           null,
   RefNo                varchar(20)          null,
   CustomerID           varchar(20)          null,
   ParcelSize           varchar(20)          null,
   CustomerName         varchar(60)          null,
   CustomerAddress      varchar(256)         null,
   CustomerMobile       varchar(30)          null,
   TerminalNo           varchar(20)          null,
   BoxNo                varchar(20)          null,
   DropAgentID          varchar(32)          null,
   DynamicCode          varchar(32)          null,
   StoredDate           date                 null,
   StoredTime           datetime             null,
   ExpiredTime          datetime             null,
   OpenBoxKey           varchar(32)          null,
   TakedWay             varchar(4)           null,
   TakedTime            datetime             null,
   ReturnAgentID        varchar(64)          null,
   DADFlag              varchar(2)           null default '0',
   TradeWaterNo         varchar(32)          null,
   PosPayFlag           varchar(2)           null,
   UploadFlag           varchar(2)           null,
   DropNum              int                  null,
   PrintedFlag          int                  null,
   ReportOrderID        varchar(32)          null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null,
   constraint PK_PTDELIVERHISTORY primary key nonclustered (PackageID, CreateTime)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '投递历史信息',
   'user', @CurrentUser, 'table', 'PTDeliverHistory'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单号',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'PackageID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建订单时间',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单状态',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'ItemStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单执行状态',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'RunStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹处理中心编号',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'PPCID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分拣区域编号',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'ZoneID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹服务商编号',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'CompanyID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '参考编号',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'RefNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人编号',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'CustomerID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹尺寸',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'ParcelSize'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人姓名',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'CustomerName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人地址',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'CustomerAddress'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人手机',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'CustomerMobile'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱门号',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'BoxNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '投递员编号',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'DropAgentID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '投递动态码',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'DynamicCode'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '存物日期',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'StoredDate'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '存物时间',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'StoredTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '逾期时间',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'ExpiredTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '开箱密码',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'OpenBoxKey'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件方式',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'TakedWay'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件时间',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'TakedTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '回收员编号',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'ReturnAgentID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '直投标志',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'DADFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '交易流水序号',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'TradeWaterNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '支付标志',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'PosPayFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '上传标志 0:未上传 1:已上传',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'UploadFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单投递次数',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'DropNum'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '打印标识（按位表示：R1~R8，0表示未打印，1表示已打印）',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'PrintedFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报表流水号',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'ReportOrderID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'PTDeliverHistory', 'column', 'Remark'
go

/*==============================================================*/
/* Table: PTDeliverItemTransfer                                 */
/*==============================================================*/
create table PTDeliverItemTransfer (
   WaterID              bigint               not null,
   PackageID            varchar(32)          null,
   ItemStatus           varchar(4)           null,
   CreateTime           datetime             null,
   ZoneID               varchar(10)          null,
   PPCID                varchar(10)          null,
   TransferType         varchar(4)           null,
   TransferStatus       varchar(4)           null,
   TransferOrderID      varchar(32)          null,
   ReSendNum            int                  null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null,
   constraint PK_PTDELIVERITEMTRANSFER primary key nonclustered (WaterID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '投递订单转移信息',
   'user', @CurrentUser, 'table', 'PTDeliverItemTransfer'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流水号',
   'user', @CurrentUser, 'table', 'PTDeliverItemTransfer', 'column', 'WaterID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单号',
   'user', @CurrentUser, 'table', 'PTDeliverItemTransfer', 'column', 'PackageID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单状态',
   'user', @CurrentUser, 'table', 'PTDeliverItemTransfer', 'column', 'ItemStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建订单时间',
   'user', @CurrentUser, 'table', 'PTDeliverItemTransfer', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分拣区域编号',
   'user', @CurrentUser, 'table', 'PTDeliverItemTransfer', 'column', 'ZoneID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹处理中心编号',
   'user', @CurrentUser, 'table', 'PTDeliverItemTransfer', 'column', 'PPCID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '转移类型',
   'user', @CurrentUser, 'table', 'PTDeliverItemTransfer', 'column', 'TransferType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '转移状态',
   'user', @CurrentUser, 'table', 'PTDeliverItemTransfer', 'column', 'TransferStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '转移清单号',
   'user', @CurrentUser, 'table', 'PTDeliverItemTransfer', 'column', 'TransferOrderID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '重发次数',
   'user', @CurrentUser, 'table', 'PTDeliverItemTransfer', 'column', 'ReSendNum'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'PTDeliverItemTransfer', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'PTDeliverItemTransfer', 'column', 'Remark'
go

/*==============================================================*/
/* Table: PTInBoxPackage                                        */
/*==============================================================*/
create table PTInBoxPackage (
   PackageID            varchar(32)          not null,
   TerminalNo           varchar(20)          not null,
   BoxNo                varchar(20)          not null,
   ItemStatus           varchar(4)           not null,
   CreateTime           datetime             not null,
   PPCID                varchar(10)          null,
   ZoneID               varchar(10)          null,
   CompanyID            varchar(8)           null,
   RefNo                varchar(20)          null,
   ParcelSize           varchar(20)          null,
   CustomerID           varchar(20)          null,
   CustomerName         varchar(60)          null,
   CustomerAddress      varchar(256)         null,
   CustomerMobile       varchar(30)          null,
   DropAgentID          varchar(32)          null,
   DynamicCode          varchar(32)          null,
   DropNum              int                  null,
   StoredDate           date                 null,
   StoredTime           datetime             null,
   ExpiredTime          datetime             null,
   ReminderDateTime     datetime             null,
   OpenBoxKey           varchar(32)          null,
   DADFlag              varchar(2)           null default '0',
   TradeWaterNo         varchar(32)          null,
   PosPayFlag           varchar(2)           null,
   UploadFlag           varchar(2)           null,
   ParcelStatus         varchar(4)           null,
   PrintedFlag          int                  null,
   ReportOrderID        varchar(32)          null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null,
   constraint PK_PTINBOXPACKAGE primary key nonclustered (PackageID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '投递在箱信息',
   'user', @CurrentUser, 'table', 'PTInBoxPackage'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单号',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'PackageID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱门号',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'BoxNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单状态',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'ItemStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建订单时间',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹处理中心编号',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'PPCID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分拣区域编号',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'ZoneID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹服务商编号',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'CompanyID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '参考编号',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'RefNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹尺寸',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'ParcelSize'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人编号',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'CustomerID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人姓名',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'CustomerName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人地址',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'CustomerAddress'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人手机',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'CustomerMobile'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '投递员编号',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'DropAgentID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '投递动态码',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'DynamicCode'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单投递次数',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'DropNum'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '存物日期',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'StoredDate'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '存物时间',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'StoredTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '逾期时间',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'ExpiredTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '催领时间',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'ReminderDateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '开箱密码',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'OpenBoxKey'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '直投标志',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'DADFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '交易流水序号',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'TradeWaterNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '支付标志',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'PosPayFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '上传标志 0:未上传 1:已上传',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'UploadFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹在箱状态',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'ParcelStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '打印标识（按位表示：R1~R8，0表示未打印，1表示已打印）',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'PrintedFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报表流水号',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'ReportOrderID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'PTInBoxPackage', 'column', 'Remark'
go

/*==============================================================*/
/* Table: PTItemLifeCycle                                       */
/*==============================================================*/
create table PTItemLifeCycle (
   WaterID              bigint               not null,
   PackageID            varchar(32)          not null,
   ItemStatus           varchar(4)           not null,
   OperatorID           varchar(64)          not null,
   OperatorType         varchar(4)           null,
   RecordLevel          int                  null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null,
   constraint PK_PTITEMLIFECYCLE primary key nonclustered (WaterID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '投递生命周期记录',
   'user', @CurrentUser, 'table', 'PTItemLifeCycle'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流水号',
   'user', @CurrentUser, 'table', 'PTItemLifeCycle', 'column', 'WaterID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单号',
   'user', @CurrentUser, 'table', 'PTItemLifeCycle', 'column', 'PackageID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单状态',
   'user', @CurrentUser, 'table', 'PTItemLifeCycle', 'column', 'ItemStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作员编号',
   'user', @CurrentUser, 'table', 'PTItemLifeCycle', 'column', 'OperatorID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '操作员类型',
   'user', @CurrentUser, 'table', 'PTItemLifeCycle', 'column', 'OperatorType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '记录级别',
   'user', @CurrentUser, 'table', 'PTItemLifeCycle', 'column', 'RecordLevel'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'PTItemLifeCycle', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'PTItemLifeCycle', 'column', 'Remark'
go

/*==============================================================*/
/* Table: PTReadyPackage                                        */
/*==============================================================*/
create table PTReadyPackage (
   PackageID            varchar(32)          not null,
   CreateTime           datetime             not null,
   ItemStatus           varchar(4)           not null,
   PPCID                varchar(10)          not null,
   ZoneID               varchar(10)          not null,
   CompanyID            varchar(8)           null,
   RefNo                varchar(20)          null,
   ParcelSize           varchar(20)          null,
   CustomerID           varchar(20)          null,
   CustomerName         varchar(60)          null,
   CustomerAddress      varchar(256)         null,
   CustomerMobile       varchar(30)          null,
   TerminalNo           varchar(20)          null,
   BoxType              varchar(20)          null,
   BoxNo                varchar(20)          null,
   PrintedFlag          int                  null,
   ReportOrderID        varchar(32)          null,
   DropAgentID          varchar(32)          null,
   ExpiredTime          datetime             null,
   DropNum              int                  null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null,
   constraint PK_PTREADYPACKAGE primary key nonclustered (PackageID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '待投递信息',
   'user', @CurrentUser, 'table', 'PTReadyPackage'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单号',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'PackageID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建订单时间',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单状态',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'ItemStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹处理中心编号',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'PPCID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分拣区域编号',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'ZoneID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹服务商编号',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'CompanyID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '参考编号',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'RefNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹尺寸',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'ParcelSize'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人编号',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'CustomerID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人姓名',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'CustomerName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人地址',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'CustomerAddress'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人手机',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'CustomerMobile'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱门类型',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'BoxType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱门号',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'BoxNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '打印标识（按位表示：R1~R8，0表示未打印，1表示已打印）',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'PrintedFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '报表流水号',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'ReportOrderID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '投递员编号',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'DropAgentID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '逾期时间',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'ExpiredTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单投递次数',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'DropNum'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'PTReadyPackage', 'column', 'Remark'
go

/*==============================================================*/
/* Table: PTTransferItemWater                                   */
/*==============================================================*/
create table PTTransferItemWater (
   WaterID              bigint               not null,
   PackageID            varchar(32)          null,
   ItemStatus           varchar(4)           null,
   CreateTime           datetime             null,
   ZoneID               varchar(10)          null,
   PPCID                varchar(10)          null,
   TransferType         varchar(4)           null,
   TransferStatus       varchar(4)           null,
   TransferID           varchar(32)          null,
   ResendNum            int                  null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null,
   constraint PK_PTTRANSFERITEMWATER primary key nonclustered (WaterID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单转移流水',
   'user', @CurrentUser, 'table', 'PTTransferItemWater'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单号',
   'user', @CurrentUser, 'table', 'PTTransferItemWater', 'column', 'PackageID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单状态',
   'user', @CurrentUser, 'table', 'PTTransferItemWater', 'column', 'ItemStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'PTTransferItemWater', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '分拣区域编号',
   'user', @CurrentUser, 'table', 'PTTransferItemWater', 'column', 'ZoneID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '包裹处理中心编号',
   'user', @CurrentUser, 'table', 'PTTransferItemWater', 'column', 'PPCID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '转移类型',
   'user', @CurrentUser, 'table', 'PTTransferItemWater', 'column', 'TransferType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '转移状态',
   'user', @CurrentUser, 'table', 'PTTransferItemWater', 'column', 'TransferStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '转移编号',
   'user', @CurrentUser, 'table', 'PTTransferItemWater', 'column', 'TransferID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '重发次数',
   'user', @CurrentUser, 'table', 'PTTransferItemWater', 'column', 'ResendNum'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'PTTransferItemWater', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'PTTransferItemWater', 'column', 'Remark'
go

/*==============================================================*/
/* Table: PYServiceBillWater                                    */
/*==============================================================*/
create table PYServiceBillWater (
   BPartnerID           varchar(20)          not null,
   TradeWaterID         varchar(32)          not null,
   BillTime             datetime             not null,
   BillType             varchar(2)           not null,
   BillDetails          varchar(512)         null,
   BillAmt              numeric(16,3)        null,
   Balance              numeric(16,3)        null,
   PackageID            varchar(32)          null,
   ServiceID            varchar(20)          null,
   Remark               varchar(256)         null default '',
   constraint PK_PYSERVICEBILLWATER primary key nonclustered (BPartnerID, TradeWaterID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务账单流水',
   'user', @CurrentUser, 'table', 'PYServiceBillWater'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '商业伙伴编号',
   'user', @CurrentUser, 'table', 'PYServiceBillWater', 'column', 'BPartnerID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流水号',
   'user', @CurrentUser, 'table', 'PYServiceBillWater', 'column', 'TradeWaterID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '账单日期',
   'user', @CurrentUser, 'table', 'PYServiceBillWater', 'column', 'BillTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '账单类型：1-充值；2-消费',
   'user', @CurrentUser, 'table', 'PYServiceBillWater', 'column', 'BillType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '账单明细',
   'user', @CurrentUser, 'table', 'PYServiceBillWater', 'column', 'BillDetails'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '账单金额',
   'user', @CurrentUser, 'table', 'PYServiceBillWater', 'column', 'BillAmt'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '账户余额',
   'user', @CurrentUser, 'table', 'PYServiceBillWater', 'column', 'Balance'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单号',
   'user', @CurrentUser, 'table', 'PYServiceBillWater', 'column', 'PackageID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务编号',
   'user', @CurrentUser, 'table', 'PYServiceBillWater', 'column', 'ServiceID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'PYServiceBillWater', 'column', 'Remark'
go

/*==============================================================*/
/* Table: PYTopUpReq                                            */
/*==============================================================*/
create table PYTopUpReq (
   TradeWaterID         varchar(40)          not null,
   Amount               numeric(16,3)        null,
   UserID               varchar(32)          not null,
   UserCode             varchar(60)          null,
   Status               varchar(2)           not null,
   CreateTime           datetime             not null,
   TimeoutIns           int                  not null,
   Field1               varchar(60)          null,
   Field2               varchar(60)          null,
   Field3               varchar(60)          null,
   Field4               varchar(60)          null,
   Field5               varchar(60)          null,
   Field6               varchar(60)          null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null,
   constraint PK_PYTOPUPREQ primary key nonclustered (TradeWaterID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '充值请求表',
   'user', @CurrentUser, 'table', 'PYTopUpReq'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '交易流水号',
   'user', @CurrentUser, 'table', 'PYTopUpReq', 'column', 'TradeWaterID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '充值金额',
   'user', @CurrentUser, 'table', 'PYTopUpReq', 'column', 'Amount'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '充值账户编号',
   'user', @CurrentUser, 'table', 'PYTopUpReq', 'column', 'UserID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '充值账户编码',
   'user', @CurrentUser, 'table', 'PYTopUpReq', 'column', 'UserCode'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '请求状态',
   'user', @CurrentUser, 'table', 'PYTopUpReq', 'column', 'Status'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '请求创建时间',
   'user', @CurrentUser, 'table', 'PYTopUpReq', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '请求超时时间（S）',
   'user', @CurrentUser, 'table', 'PYTopUpReq', 'column', 'TimeoutIns'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '预留字段1',
   'user', @CurrentUser, 'table', 'PYTopUpReq', 'column', 'Field1'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '预留字段2',
   'user', @CurrentUser, 'table', 'PYTopUpReq', 'column', 'Field2'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '预留字段3',
   'user', @CurrentUser, 'table', 'PYTopUpReq', 'column', 'Field3'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '预留字段4',
   'user', @CurrentUser, 'table', 'PYTopUpReq', 'column', 'Field4'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '预留字段5',
   'user', @CurrentUser, 'table', 'PYTopUpReq', 'column', 'Field5'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '预留字段6',
   'user', @CurrentUser, 'table', 'PYTopUpReq', 'column', 'Field6'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'PYTopUpReq', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'PYTopUpReq', 'column', 'Remark'
go

/*==============================================================*/
/* Table: RMAppealLog                                           */
/*==============================================================*/
create table RMAppealLog (
   AppealNo             varchar(32)          not null,
   AppealUser           varchar(32)          not null,
   AppealType           varchar(4)           not null,
   TerminalNo           varchar(20)          not null,
   BoxNo                varchar(20)          not null,
   PackageID            varchar(32)          null,
   Mobile               varchar(30)          null,
   OpenBoxKey           varchar(32)          null,
   AppealContent        varchar(1000)        null,
   AppealTime           datetime             not null,
   AppealStatus         varchar(4)           not null,
   FaultReason          varchar(1000)        null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null,
   constraint PK_RMAPPEALLOG primary key nonclustered (AppealNo)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '远程求助日志',
   'user', @CurrentUser, 'table', 'RMAppealLog'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '求助编号',
   'user', @CurrentUser, 'table', 'RMAppealLog', 'column', 'AppealNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '求助用户',
   'user', @CurrentUser, 'table', 'RMAppealLog', 'column', 'AppealUser'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '求助类型',
   'user', @CurrentUser, 'table', 'RMAppealLog', 'column', 'AppealType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'RMAppealLog', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱门号',
   'user', @CurrentUser, 'table', 'RMAppealLog', 'column', 'BoxNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单号',
   'user', @CurrentUser, 'table', 'RMAppealLog', 'column', 'PackageID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人手机',
   'user', @CurrentUser, 'table', 'RMAppealLog', 'column', 'Mobile'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '开箱密码',
   'user', @CurrentUser, 'table', 'RMAppealLog', 'column', 'OpenBoxKey'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '求助内容',
   'user', @CurrentUser, 'table', 'RMAppealLog', 'column', 'AppealContent'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '求助时间',
   'user', @CurrentUser, 'table', 'RMAppealLog', 'column', 'AppealTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '求助状态',
   'user', @CurrentUser, 'table', 'RMAppealLog', 'column', 'AppealStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '故障原因分析',
   'user', @CurrentUser, 'table', 'RMAppealLog', 'column', 'FaultReason'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'RMAppealLog', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'RMAppealLog', 'column', 'Remark'
go

/*==============================================================*/
/* Table: RMOpenBoxLog                                          */
/*==============================================================*/
create table RMOpenBoxLog (
   WaterID              bigint               not null,
   OpenBoxUser          varchar(32)          not null,
   OpenBoxType          varchar(4)           not null,
   BoxNo                varchar(20)          not null,
   TerminalNo           varchar(20)          not null,
   PackageID            varchar(32)          null,
   Mobile               varchar(30)          null,
   LastModifyTime       datetime             not null,
   Remark               varchar(256)         null,
   constraint PK_RMOPENBOXLOG primary key nonclustered (WaterID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '开箱记录',
   'user', @CurrentUser, 'table', 'RMOpenBoxLog'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流水号',
   'user', @CurrentUser, 'table', 'RMOpenBoxLog', 'column', 'WaterID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '开箱用户',
   'user', @CurrentUser, 'table', 'RMOpenBoxLog', 'column', 'OpenBoxUser'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '开箱类型',
   'user', @CurrentUser, 'table', 'RMOpenBoxLog', 'column', 'OpenBoxType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱门号',
   'user', @CurrentUser, 'table', 'RMOpenBoxLog', 'column', 'BoxNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'RMOpenBoxLog', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单号',
   'user', @CurrentUser, 'table', 'RMOpenBoxLog', 'column', 'PackageID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件人手机',
   'user', @CurrentUser, 'table', 'RMOpenBoxLog', 'column', 'Mobile'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'RMOpenBoxLog', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'RMOpenBoxLog', 'column', 'Remark'
go

/*==============================================================*/
/* Table: SCFtpLog                                              */
/*==============================================================*/
create table SCFtpLog (
   TerminalNo           varchar(20)          not null,
   StoredDate           date                 not null,
   LastModifyTime       datetime             not null,
   Remark               varchar(256)         null default '',
   constraint PK_SCFTPLOG primary key nonclustered (TerminalNo, StoredDate)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   'FTP同步日志',
   'user', @CurrentUser, 'table', 'SCFtpLog'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'SCFtpLog', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '存物日期',
   'user', @CurrentUser, 'table', 'SCFtpLog', 'column', 'StoredDate'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'SCFtpLog', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'SCFtpLog', 'column', 'Remark'
go

/*==============================================================*/
/* Table: SCPushDataQueue                                       */
/*==============================================================*/
create table SCPushDataQueue (
   MsgUid               varchar(32)          not null,
   TerminalNo           varchar(20)          not null,
   ServiceName          varchar(60)          null,
   MsgContent           varchar(4000)        null,
   FailureCount         int                  null,
   LastModifyTime       datetime             not null,
   Remark               varchar(256)         null default '',
   constraint PK_SCPUSHDATAQUEUE primary key nonclustered (MsgUid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '推送数据队列',
   'user', @CurrentUser, 'table', 'SCPushDataQueue'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '消息唯一编号',
   'user', @CurrentUser, 'table', 'SCPushDataQueue', 'column', 'MsgUid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'SCPushDataQueue', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '业务类型',
   'user', @CurrentUser, 'table', 'SCPushDataQueue', 'column', 'ServiceName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '消息内容',
   'user', @CurrentUser, 'table', 'SCPushDataQueue', 'column', 'MsgContent'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '同步失败次数',
   'user', @CurrentUser, 'table', 'SCPushDataQueue', 'column', 'FailureCount'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'SCPushDataQueue', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'SCPushDataQueue', 'column', 'Remark'
go

/*==============================================================*/
/* Table: SCSyncFailWater                                       */
/*==============================================================*/
create table SCSyncFailWater (
   WaterID              bigint               not null,
   TerminalNo           varchar(20)          not null,
   ServiceName          varchar(60)          null,
   PackageID            varchar(32)          null,
   MsgContent           varchar(4000)        null,
   FailReason           varchar(4000)        null,
   LastModifyTime       datetime             not null,
   Remark               varchar(256)         null default '',
   constraint PK_SCSYNCFAILWATER primary key nonclustered (WaterID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '同步数据失败流水',
   'user', @CurrentUser, 'table', 'SCSyncFailWater'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '流水号',
   'user', @CurrentUser, 'table', 'SCSyncFailWater', 'column', 'WaterID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'SCSyncFailWater', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '业务类型',
   'user', @CurrentUser, 'table', 'SCSyncFailWater', 'column', 'ServiceName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单号',
   'user', @CurrentUser, 'table', 'SCSyncFailWater', 'column', 'PackageID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '消息内容',
   'user', @CurrentUser, 'table', 'SCSyncFailWater', 'column', 'MsgContent'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '失败原因',
   'user', @CurrentUser, 'table', 'SCSyncFailWater', 'column', 'FailReason'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'SCSyncFailWater', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'SCSyncFailWater', 'column', 'Remark'
go

/*==============================================================*/
/* Table: SCTimestamp                                           */
/*==============================================================*/
create table SCTimestamp (
   TimestampID          bigint               not null,
   TerminalTimestamp    datetime             null,
   BoxTimestamp         datetime             null,
   LogTimestamp         datetime             null,
   WrongpwdTimestamp    datetime             null,
   constraint PK_SCTIMESTAMP primary key nonclustered (TimestampID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '数据同步时间戳',
   'user', @CurrentUser, 'table', 'SCTimestamp'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '时间戳序号',
   'user', @CurrentUser, 'table', 'SCTimestamp', 'column', 'TimestampID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '柜体信息同步时间戳',
   'user', @CurrentUser, 'table', 'SCTimestamp', 'column', 'TerminalTimestamp'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱体信息同步时间戳',
   'user', @CurrentUser, 'table', 'SCTimestamp', 'column', 'BoxTimestamp'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '管理员日志同步时间戳',
   'user', @CurrentUser, 'table', 'SCTimestamp', 'column', 'LogTimestamp'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '取件错误密码日志同步时间戳',
   'user', @CurrentUser, 'table', 'SCTimestamp', 'column', 'WrongpwdTimestamp'
go

/*==============================================================*/
/* Table: SCUploadDataQueue                                     */
/*==============================================================*/
create table SCUploadDataQueue (
   MsgUid               varchar(32)          not null,
   TerminalNo           varchar(20)          not null,
   ServiceName          varchar(60)          null,
   PackageID            varchar(32)          null,
   StoredTime           datetime             null,
   MsgContent           varchar(4000)        null,
   FailureCount         int                  null,
   LastModifyTime       datetime             not null,
   Remark               varchar(256)         null default '',
   constraint PK_SCUPLOADDATAQUEUE primary key nonclustered (MsgUid)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '上传数据队列',
   'user', @CurrentUser, 'table', 'SCUploadDataQueue'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '消息唯一编号',
   'user', @CurrentUser, 'table', 'SCUploadDataQueue', 'column', 'MsgUid'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'SCUploadDataQueue', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '业务类型',
   'user', @CurrentUser, 'table', 'SCUploadDataQueue', 'column', 'ServiceName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '订单号',
   'user', @CurrentUser, 'table', 'SCUploadDataQueue', 'column', 'PackageID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '存物时间',
   'user', @CurrentUser, 'table', 'SCUploadDataQueue', 'column', 'StoredTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '消息内容',
   'user', @CurrentUser, 'table', 'SCUploadDataQueue', 'column', 'MsgContent'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '同步失败次数',
   'user', @CurrentUser, 'table', 'SCUploadDataQueue', 'column', 'FailureCount'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'SCUploadDataQueue', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'SCUploadDataQueue', 'column', 'Remark'
go

/*==============================================================*/
/* Table: SMAdVideo                                             */
/*==============================================================*/
create table SMAdVideo (
   VideoID              bigint               not null,
   VideoName            varchar(64)          not null,
   VideoPath            varchar(256)         not null,
   VideoSort            int                  not null,
   LastModifyTime       datetime             not null,
   Remark               varchar(256)         null default '',
   constraint PK_SMADVIDEO primary key nonclustered (VideoID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '广告视频表',
   'user', @CurrentUser, 'table', 'SMAdVideo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '视频编号',
   'user', @CurrentUser, 'table', 'SMAdVideo', 'column', 'VideoID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '视频名称',
   'user', @CurrentUser, 'table', 'SMAdVideo', 'column', 'VideoName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '视频路径',
   'user', @CurrentUser, 'table', 'SMAdVideo', 'column', 'VideoPath'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '视频排序',
   'user', @CurrentUser, 'table', 'SMAdVideo', 'column', 'VideoSort'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'SMAdVideo', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'SMAdVideo', 'column', 'Remark'
go

/*==============================================================*/
/* Table: SMSystemInfo                                          */
/*==============================================================*/
create table SMSystemInfo (
   SystemID             varchar(10)          not null,
   SoftwareVersion      varchar(32)          not null,
   UpdateContent        varchar(1000)        null,
   InitPasswd           varchar(32)          null,
   LastInitPasswd       varchar(32)          null,
   TerminalPasswd       varchar(32)          null,
   ServerIP             varchar(32)          null,
   ServerPort           int                  null,
   ServerSSL            varchar(2)           null,
   MonServerIP          varchar(32)          null,
   MonServerPort        int                  null,
   LastModifyTime       datetime             not null,
   Remark               varchar(256)         null default '',
   constraint PK_SMSYSTEMINFO primary key nonclustered (SystemID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '系统信息表',
   'user', @CurrentUser, 'table', 'SMSystemInfo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '系统编号',
   'user', @CurrentUser, 'table', 'SMSystemInfo', 'column', 'SystemID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '软件版本号',
   'user', @CurrentUser, 'table', 'SMSystemInfo', 'column', 'SoftwareVersion'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '更新内容',
   'user', @CurrentUser, 'table', 'SMSystemInfo', 'column', 'UpdateContent'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '安装密码',
   'user', @CurrentUser, 'table', 'SMSystemInfo', 'column', 'InitPasswd'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '上一次安装密码',
   'user', @CurrentUser, 'table', 'SMSystemInfo', 'column', 'LastInitPasswd'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '终端密码',
   'user', @CurrentUser, 'table', 'SMSystemInfo', 'column', 'TerminalPasswd'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务器IP地址',
   'user', @CurrentUser, 'table', 'SMSystemInfo', 'column', 'ServerIP'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务器端口号',
   'user', @CurrentUser, 'table', 'SMSystemInfo', 'column', 'ServerPort'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '服务器端口号',
   'user', @CurrentUser, 'table', 'SMSystemInfo', 'column', 'ServerSSL'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '监控服务器IP地址',
   'user', @CurrentUser, 'table', 'SMSystemInfo', 'column', 'MonServerIP'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '监控服务器端口号',
   'user', @CurrentUser, 'table', 'SMSystemInfo', 'column', 'MonServerPort'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'SMSystemInfo', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'SMSystemInfo', 'column', 'Remark'
go

/*==============================================================*/
/* Table: TBBoxType                                             */
/*==============================================================*/
create table TBBoxType (
   BoxType              varchar(8)           not null,
   BoxTypeName          varchar(64)          not null,
   MBBoxType            varchar(8)           null,
   BoxHeight            numeric(16,3)        null,
   BoxWidth             numeric(16,3)        null,
   BoxDepth             numeric(16,3)        null,
   BoxInterval          numeric(16,3)        null,
   ChargeAmt            numeric(16,3)        null,
   Remark               varchar(256)         null,
   constraint PK_TBBOXTYPE primary key nonclustered (BoxType)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱类型信息',
   'user', @CurrentUser, 'table', 'TBBoxType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱类型编号 0:小, 1:中, 2:大, 3:超大',
   'user', @CurrentUser, 'table', 'TBBoxType', 'column', 'BoxType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱类型名称',
   'user', @CurrentUser, 'table', 'TBBoxType', 'column', 'BoxTypeName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '运营商箱类型编号',
   'user', @CurrentUser, 'table', 'TBBoxType', 'column', 'MBBoxType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱长度',
   'user', @CurrentUser, 'table', 'TBBoxType', 'column', 'BoxHeight'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '宽度',
   'user', @CurrentUser, 'table', 'TBBoxType', 'column', 'BoxWidth'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱深度',
   'user', @CurrentUser, 'table', 'TBBoxType', 'column', 'BoxDepth'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱间隙',
   'user', @CurrentUser, 'table', 'TBBoxType', 'column', 'BoxInterval'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '收费金额',
   'user', @CurrentUser, 'table', 'TBBoxType', 'column', 'ChargeAmt'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'TBBoxType', 'column', 'Remark'
go

/*==============================================================*/
/* Table: TBServerBox                                           */
/*==============================================================*/
create table TBServerBox (
   TerminalNo           varchar(20)          not null,
   BoxNo                varchar(20)          not null,
   BoxType              varchar(8)           not null,
   BoxName              varchar(64)          not null,
   DeskNo               int                  null,
   DeskBoxNo            int                  null,
   BoxStatus            varchar(4)           not null,
   BoxUsedStatus        varchar(4)           not null,
   constraint PK_TBSERVERBOX primary key nonclustered (TerminalNo, BoxNo)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '运营端箱信息',
   'user', @CurrentUser, 'table', 'TBServerBox'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'TBServerBox', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱门号',
   'user', @CurrentUser, 'table', 'TBServerBox', 'column', 'BoxNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱类型编号 0:小, 1:中, 2:大, 3:超大',
   'user', @CurrentUser, 'table', 'TBServerBox', 'column', 'BoxType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱门名称',
   'user', @CurrentUser, 'table', 'TBServerBox', 'column', 'BoxName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '副柜编号',
   'user', @CurrentUser, 'table', 'TBServerBox', 'column', 'DeskNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱门副柜编号',
   'user', @CurrentUser, 'table', 'TBServerBox', 'column', 'DeskBoxNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱状态 0:正常  1:锁定 2:故障 4:锁定故障 8:占用 9:未知',
   'user', @CurrentUser, 'table', 'TBServerBox', 'column', 'BoxStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '箱使用状态',
   'user', @CurrentUser, 'table', 'TBServerBox', 'column', 'BoxUsedStatus'
go

/*==============================================================*/
/* Table: TBServerDesk                                          */
/*==============================================================*/
create table TBServerDesk (
   TerminalNo           varchar(20)          not null,
   DeskNo               int                  not null,
   DialupID             int                  not null,
   BoxNum               int                  null,
   DeskHeight           numeric(16,3)        null,
   DeskWidth            numeric(16,3)        null,
   Remark               varchar(256)         null,
   constraint PK_TBSERVERDESK primary key nonclustered (TerminalNo, DeskNo)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '运营端扩展柜信息',
   'user', @CurrentUser, 'table', 'TBServerDesk'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'TBServerDesk', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '副柜编号',
   'user', @CurrentUser, 'table', 'TBServerDesk', 'column', 'DeskNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '副柜寻址地址',
   'user', @CurrentUser, 'table', 'TBServerDesk', 'column', 'DialupID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '柜体箱总数量',
   'user', @CurrentUser, 'table', 'TBServerDesk', 'column', 'BoxNum'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '副柜高度',
   'user', @CurrentUser, 'table', 'TBServerDesk', 'column', 'DeskHeight'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '副柜宽度',
   'user', @CurrentUser, 'table', 'TBServerDesk', 'column', 'DeskWidth'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'TBServerDesk', 'column', 'Remark'
go

/*==============================================================*/
/* Table: TBTerminal                                            */
/*==============================================================*/
create table TBTerminal (
   TerminalNo           varchar(20)          not null,
   TerminalName         varchar(64)          not null,
   TerminalStatus       varchar(2)           not null,
   Address              varchar(256)         null,
   Location             varchar(256)         null,
   Longitude            numeric(16,12)       null,
   Latitude             numeric(16,12)       null,
   ZoneID               varchar(20)          null,
   City                 varchar(64)          null,
   Zipcode              varchar(16)          null,
   RegisterFlag         varchar(2)           null,
   AppRegisterLimit     int                  null,
   AppRegisterFlag      varchar(2)           null,
   MaintMobile          varchar(30)          null,
   MaintEmail           varchar(64)          null,
   OfBureau             varchar(256)         null,
   OfSegment            varchar(256)         null,
   DepartmentID         varchar(20)          null,
   MBDeviceNo           varchar(20)          null,
   DeskNum              int                  null,
   BoxNum               int                  null,
   MacAddr              varchar(64)          null,
   Brand                varchar(64)          null,
   Model                varchar(64)          null,
   MachineSize          varchar(64)          null,
   MainDeskAddress      int                  null,
   CreateTime           datetime             null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null,
   constraint PK_TBTERMINAL primary key nonclustered (TerminalNo)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '柜体信息',
   'user', @CurrentUser, 'table', 'TBTerminal'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备名称',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'TerminalName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '柜体状态： 0:正常  1:锁定 2:故障  9:未知',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'TerminalStatus'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '柜体安装地址',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'Address'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '柜体地理位置',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'Location'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '柜体经度',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'Longitude'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '柜体纬度',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'Latitude'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '所属分拣区域',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'ZoneID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '所在城市',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'City'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '所在城市邮编',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'Zipcode'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '柜体注册标志',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'RegisterFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '应用注册期限',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'AppRegisterLimit'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '应用注册标记',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'AppRegisterFlag'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '维修人员手机',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'MaintMobile'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '维修人员邮箱',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'MaintEmail'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '所属投递局',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'OfBureau'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '所属投递段',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'OfSegment'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '运营网点编号',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'DepartmentID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '运营商柜体编号',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'MBDeviceNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '扩展柜数量',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'DeskNum'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '柜体箱总数量',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'BoxNum'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '柜体Mac地址',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'MacAddr'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '柜体品牌',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'Brand'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '柜体型号',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'Model'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '柜体占地尺寸',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'MachineSize'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '柜体主柜位置',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'MainDeskAddress'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'TBTerminal', 'column', 'Remark'
go

/*==============================================================*/
/* Table: TBTerminalCounter                                     */
/*==============================================================*/
create table TBTerminalCounter (
   TerminalNo           varchar(20)          not null,
   CounterType          varchar(4)           not null,
   CounterName          varchar(64)          null,
   CntValue             int                  not null,
   CntMaxValue          int                  null,
   LastModifyTime       datetime             null,
   Remark               varchar(256)         null,
   constraint PK_TBTERMINALCOUNTER primary key nonclustered (TerminalNo, CounterType)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '柜体计数器',
   'user', @CurrentUser, 'table', 'TBTerminalCounter'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '设备号',
   'user', @CurrentUser, 'table', 'TBTerminalCounter', 'column', 'TerminalNo'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '计数器类型：1-寄件计数器（LockerPOSCODECOUNTER）；2-客户计数器（LockerCOCounter）；3-投递计数器（LockerDOCounter）；4-直投计数器（LockerDDCounter）；5-回收计数器（LockerROCounter）',
   'user', @CurrentUser, 'table', 'TBTerminalCounter', 'column', 'CounterType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '计数器名称',
   'user', @CurrentUser, 'table', 'TBTerminalCounter', 'column', 'CounterName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '计数器数值',
   'user', @CurrentUser, 'table', 'TBTerminalCounter', 'column', 'CntValue'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '计数器最大值',
   'user', @CurrentUser, 'table', 'TBTerminalCounter', 'column', 'CntMaxValue'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'TBTerminalCounter', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'TBTerminalCounter', 'column', 'Remark'
go
/*==============================================================*/
/* Table: OPEmail                                       */
/*==============================================================*/
create table OPEmail (
   OperID               varchar(32)    not null,
   OperName             varchar(60)    not null,
   EmailType            varchar(4)     not null,
   SendAsRights         varchar(4)     not null,
   Email                varchar(64)    not null,
   CreateTime           datetime,
   LastModifyTime       datetime,
   Remark               varchar(255),
   constraint PK_OPMail primary key nonclustered (OperID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '管理员编号',
   'user', @CurrentUser, 'table', 'OPEmail', 'column', 'OperID'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '管理员姓名',
   'user', @CurrentUser, 'table', 'OPEmail', 'column', 'OperName'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '邮件类型:1:异常报警邮件 2:格口使用情况邮件',
   'user', @CurrentUser, 'table', 'OPEmail', 'column', 'EmailType'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '发送权限0:不发送 1:发送管理员网点下的设备信息（默认值）2:发送根网点下的设备信息',
   'user', @CurrentUser, 'table', 'OPEmail', 'column', 'sendAsRights'
go


declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '管理员邮箱',
   'user', @CurrentUser, 'table', 'OPEmail', 'column', 'Email'
go


declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'user', @CurrentUser, 'table', 'OPEmail', 'column', 'CreateTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '最后修改时间',
   'user', @CurrentUser, 'table', 'OPEmail', 'column', 'LastModifyTime'
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'user', @CurrentUser, 'table', 'OPEmail', 'column', 'Remark'
go
