alter table PTDeliverItemTransfer add TerminalNo           varchar(20)          null;
alter table PTTransferItemWater add TerminalNo           varchar(20)          null;

/*===================================================================
*  2017-10-27 增加管理员邮箱表
*
=====================================================================
drop table if exists OPEmail;

create table OPEmail
(
   OperID               varchar(32) not null comment '管理员编号',
   OperName             varchar(60) not null comment '管理员姓名',
   EmailType            varchar(4) not null comment '邮件类型:1:异常报警邮件 2:格口使用情况邮件',
   sendAsRights         varchar(4) not null comment '发送权限，0:不发送 1:发送管理员网点下的设备信息（默认值）2:发送根网点下的设备信息',
   Email                varchar(64) comment '管理员邮箱',
   CreateTime           datetime comment '创建时间',
   LastModifyTime       datetime comment '最后修改时间',
   Remark               varchar(255) comment '备注',
   primary key (OperID)
);
alter table OPEmail comment '邮箱管理';*/
  
/*sqlserver建表*/
use dbs_server_saudi 
go
if exists(select name from sys.tables where name='OPEmail')
drop table OPEmail
go
create table OPEmail (
   OperID               varchar(32)    not null 
   OperName             varchar(60)    not null
   EmailType            varchar(4)     not null 
   SendAsRights         varchar(4)     not null
   Email                varchar(64)    not null
   CreateTime           datetime 
   LastModifyTime       datetime 
   Remark               varchar(255) 
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

  
  
  
  
  