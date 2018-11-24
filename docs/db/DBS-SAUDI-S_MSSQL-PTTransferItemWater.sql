/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     2017/5/22 18:23:02                           */
/*==============================================================*/


if exists (select 1
            from  sysobjects
           where  id = object_id('PTDeliverItemTransfer')
            and   type = 'U')
   drop table PTDeliverItemTransfer
go

if exists (select 1
            from  sysobjects
           where  id = object_id('PTTransferItemWater')
            and   type = 'U')
   drop table PTTransferItemWater
go

if exists(select 1 from systypes where name='BBoolean')
   drop type BBoolean
go

if exists(select 1 from systypes where name='D4WDM')
   drop type D4WDM
go

if exists(select 1 from systypes where name='D8WDM')
   drop type D8WDM
go

if exists(select 1 from systypes where name='DAddress')
   drop type DAddress
go

if exists(select 1 from systypes where name='DCHAR1000')
   drop type DCHAR1000
go

if exists(select 1 from systypes where name='DCHAR256')
   drop type DCHAR256
go

if exists(select 1 from systypes where name='DCHAR512')
   drop type DCHAR512
go

if exists(select 1 from systypes where name='DChar16')
   drop type DChar16
go

if exists(select 1 from systypes where name='DChar20')
   drop type DChar20
go

if exists(select 1 from systypes where name='DChar30')
   drop type DChar30
go

if exists(select 1 from systypes where name='DChar32')
   drop type DChar32
go

if exists(select 1 from systypes where name='DChar40')
   drop type DChar40
go

if exists(select 1 from systypes where name='DChar5')
   drop type DChar5
go

if exists(select 1 from systypes where name='DChar60')
   drop type DChar60
go

if exists(select 1 from systypes where name='DChar61')
   drop type DChar61
go

if exists(select 1 from systypes where name='DChar8')
   drop type DChar8
go

if exists(select 1 from systypes where name='DCounter')
   drop type DCounter
go

if exists(select 1 from systypes where name='DDate')
   drop type DDate
go

if exists(select 1 from systypes where name='DDateTime')
   drop type DDateTime
go

if exists(select 1 from systypes where name='DDigit')
   drop type DDigit
go

if exists(select 1 from systypes where name='DDiscount')
   drop type DDiscount
go

if exists(select 1 from systypes where name='DFlag')
   drop type DFlag
go

if exists(select 1 from systypes where name='DLChar')
   drop type DLChar
go

if exists(select 1 from systypes where name='DMoney')
   drop type DMoney
go

if exists(select 1 from systypes where name='DN168')
   drop type DN168
go

if exists(select 1 from systypes where name='DName')
   drop type DName
go

if exists(select 1 from systypes where name='DName2')
   drop type DName2
go

if exists(select 1 from systypes where name='DNum2')
   drop type DNum2
go

if exists(select 1 from systypes where name='DNum4')
   drop type DNum4
go

if exists(select 1 from systypes where name='DNum6')
   drop type DNum6
go

if exists(select 1 from systypes where name='DNumID')
   drop type DNumID
go

if exists(select 1 from systypes where name='DPhone')
   drop type DPhone
go

if exists(select 1 from systypes where name='DRemark')
   drop type DRemark
go

if exists(select 1 from systypes where name='DTime')
   drop type DTime
go

if exists(select 1 from systypes where name='DVar10')
   drop type DVar10
go

if exists(select 1 from systypes where name='DVar32')
   drop type DVar32
go

if exists(select 1 from systypes where name='Dvar2')
   drop type Dvar2
go

if exists(select 1 from systypes where name='Email')
   drop type Email
go

if exists(select 1 from systypes where name='LVCHAR')
   drop type LVCHAR
go

if exists(select 1 from systypes where name='数字8')
   drop type 数字8
go

/*==============================================================*/
/* Domain: BBoolean                                             */
/*==============================================================*/
create type BBoolean
   from bit
go

/*==============================================================*/
/* Domain: D4WDM                                                */
/*==============================================================*/
create type D4WDM
   from varchar(4)
go

/*==============================================================*/
/* Domain: D8WDM                                                */
/*==============================================================*/
create type D8WDM
   from varchar(8)
go

/*==============================================================*/
/* Domain: DAddress                                             */
/*==============================================================*/
create type DAddress
   from varchar(256)
go

/*==============================================================*/
/* Domain: DCHAR1000                                            */
/*==============================================================*/
create type DCHAR1000
   from varchar(1000)
go

/*==============================================================*/
/* Domain: DCHAR256                                             */
/*==============================================================*/
create type DCHAR256
   from varchar(256)
go

/*==============================================================*/
/* Domain: DCHAR512                                             */
/*==============================================================*/
create type DCHAR512
   from varchar(512)
go

/*==============================================================*/
/* Domain: DChar16                                              */
/*==============================================================*/
create type DChar16
   from varchar(16)
go

/*==============================================================*/
/* Domain: DChar20                                              */
/*==============================================================*/
create type DChar20
   from varchar(20)
go

/*==============================================================*/
/* Domain: DChar30                                              */
/*==============================================================*/
create type DChar30
   from varchar(30)
go

/*==============================================================*/
/* Domain: DChar32                                              */
/*==============================================================*/
create type DChar32
   from varchar(32)
go

/*==============================================================*/
/* Domain: DChar40                                              */
/*==============================================================*/
create type DChar40
   from varchar(40)
go

/*==============================================================*/
/* Domain: DChar5                                               */
/*==============================================================*/
create type DChar5
   from varchar(5)
go

/*==============================================================*/
/* Domain: DChar60                                              */
/*==============================================================*/
create type DChar60
   from varchar(60)
go

/*==============================================================*/
/* Domain: DChar61                                              */
/*==============================================================*/
create type DChar61
   from varchar(64)
go

/*==============================================================*/
/* Domain: DChar8                                               */
/*==============================================================*/
create type DChar8
   from varchar(8)
go

/*==============================================================*/
/* Domain: DCounter                                             */
/*==============================================================*/
create type DCounter
   from varchar(10)
go

/*==============================================================*/
/* Domain: DDate                                                */
/*==============================================================*/
create type DDate
   from datetime
go

/*==============================================================*/
/* Domain: DDateTime                                            */
/*==============================================================*/
create type DDateTime
   from datetime
go

/*==============================================================*/
/* Domain: DDigit                                               */
/*==============================================================*/
create type DDigit
   from numeric(16,3)
go

/*==============================================================*/
/* Domain: DDiscount                                            */
/*==============================================================*/
create type DDiscount
   from int
go

/*==============================================================*/
/* Domain: DFlag                                                */
/*==============================================================*/
create type DFlag
   from varchar(2)
go

/*==============================================================*/
/* Domain: DLChar                                               */
/*==============================================================*/
create type DLChar
   from varchar(4000)
go

/*==============================================================*/
/* Domain: DMoney                                               */
/*==============================================================*/
create type DMoney
   from numeric(16,3)
go

/*==============================================================*/
/* Domain: DN168                                                */
/*==============================================================*/
create type DN168
   from numeric(16,12)
go

/*==============================================================*/
/* Domain: DName                                                */
/*==============================================================*/
create type DName
   from varchar(60)
go

/*==============================================================*/
/* Domain: DName2                                               */
/*==============================================================*/
create type DName2
   from varchar(64)
go

/*==============================================================*/
/* Domain: DNum2                                                */
/*==============================================================*/
create type DNum2
   from int
go

/*==============================================================*/
/* Domain: DNum4                                                */
/*==============================================================*/
create type DNum4
   from int
go

/*==============================================================*/
/* Domain: DNum6                                                */
/*==============================================================*/
create type DNum6
   from int
go

/*==============================================================*/
/* Domain: DNumID                                               */
/*==============================================================*/
create type DNumID
   from bigint
go

/*==============================================================*/
/* Domain: DPhone                                               */
/*==============================================================*/
create type DPhone
   from varchar(30)
go

/*==============================================================*/
/* Domain: DRemark                                              */
/*==============================================================*/
create type DRemark
   from varchar(256)
go

/*==============================================================*/
/* Domain: DTime                                                */
/*==============================================================*/
create type DTime
   from datetime
go

/*==============================================================*/
/* Domain: DVar10                                               */
/*==============================================================*/
create type DVar10
   from varchar(10)
go

/*==============================================================*/
/* Domain: DVar32                                               */
/*==============================================================*/
create type DVar32
   from varchar(32)
go

/*==============================================================*/
/* Domain: Dvar2                                                */
/*==============================================================*/
create type Dvar2
   from varchar(2)
go

/*==============================================================*/
/* Domain: Email                                                */
/*==============================================================*/
create type Email
   from varchar(64)
go

/*==============================================================*/
/* Domain: LVCHAR                                               */
/*==============================================================*/
create type LVCHAR
   from ntext
go

/*==============================================================*/
/* Domain: 数字8                                                  */
/*==============================================================*/
create type 数字8
   from int
go

/*==============================================================*/
/* Table: PTDeliverItemTransfer                                 */
/*==============================================================*/
create table PTDeliverItemTransfer (
   WaterID              bigint               not null,
   PackageID            varchar(32)          null,
   ItemStatus           varchar(4)           null,
   CreateTime           datetime             null,
   TerminalNo           varchar(20)          null,
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
   '设备号',
   'user', @CurrentUser, 'table', 'PTDeliverItemTransfer', 'column', 'TerminalNo'
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
/* Table: PTTransferItemWater                                   */
/*==============================================================*/
create table PTTransferItemWater (
   WaterID              bigint               not null,
   PackageID            varchar(32)          null,
   ItemStatus           varchar(4)           null,
   CreateTime           datetime             null,
   TerminalNo           varchar(20)          null,
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
   '设备号',
   'user', @CurrentUser, 'table', 'PTTransferItemWater', 'column', 'TerminalNo'
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

