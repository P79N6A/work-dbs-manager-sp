/***************************************************************************
  * 管理员初始化
  *
  * 创建日期：2015-4-7
  *	创建人：郑晓勇  
  *
  *
  * ************************************************************************/

BEGIN TRANSACTION;
/************************************************
  * 1.菜单信息
  *
  * ************************************************/
TRUNCATE TABLE OPMenu;

/************************************************
  *系统配置管理
  * ************************************************/
  
INSERT INTO OPMenu VALUES('11000000','系统配置管理',        1,'','','','','','icon-sm','',0,0,'');

INSERT INTO OPMenu VALUES('11010000','全局配置设置',      2,'','','','{"url":"appjs/sm/SMGlobalCfgSet.js","type":"win"}','','','',0,1,'');
INSERT INTO OPMenu VALUES('11020000','数据库配置设置',      2,'','','','{"url":"appjs/sm/SMDbCfgSet.js","type":"win"}','','','',0,1,'');


INSERT INTO OPMenu VALUES('11110000','内存数据动态加载',      2,'','','','{"url":"appjs/sm/SMMemoryData.js","type":"win"}','','','',0,1,'');

INSERT INTO OPMenu VALUES('11210000','修改软件版本号',      2,'','','','{"url":"appjs/sm/SMModSysVersion.js","type":"win"}','','','',0,1,'');
INSERT INTO OPMenu VALUES('11250000','修改签到密码',      2,'','','','{"url":"appjs/sm/SMModSignPwd.js","type":"win"}','','','',0,1,'');
INSERT INTO OPMenu VALUES('11270000','修改设备端欢迎词',      2,'','','','{"url":"appjs/sm/SMModWelcomeInfo.js","type":"win"}','','','',0,1,'');

INSERT INTO OPMenu VALUES('11280000','修改服务器连接设置',      2,'','','','{"url":"appjs/sm/SMModServerCfg.js","type":"win"}','','','',0,1,'');



INSERT INTO OPMenu VALUES('11410000','系统字典管理',    2,'','','','{"url":"appjs/pa/PADictionary.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('11410002','修改',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('11410004','查询',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('11420000','控制参数管理',    2,'','','','{"url":"appjs/pa/PAControlParam.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('11420002','修改',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('11420004','查询',            3,'','','','','','','',1,1,'');

/************************************************
  *基础信息管理
  * ************************************************/
INSERT INTO OPMenu VALUES('12000000','基础信息管理',    1,'','','','','','icon-pa','',0,0,'');

/******移到系统配置管理
INSERT INTO OPMenu VALUES('12010000','系统字典管理',    2,'','','','{"url":"appjs/pa/PADictionary.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('12010002','修改',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('12020000','控制参数管理',    2,'','','','{"url":"appjs/pa/PAControlParam.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('12020002','修改',            3,'','','','','','','',1,1,'');
******/

/******
insert into OPMenu values('12080000','物流公司管理',    2,'','','','{"url":"appjs/pa/PALogistics.js","type":"panel"}','','','',0,0,'');
insert into OPMenu values('12080001','增加',            3,'','','','','','','',1,1,'');
insert into OPMenu values('12080002','修改',            3,'','','','','','','',1,1,'');
insert into OPMenu values('12080003','删除',            3,'','','','','','','',1,1,'');

insert into OPMenu values('12150000','运营网点管理',    2,'','','','{"url":"appjs/pa/PADepartment.js","type":"win"}','','','',0,0,'');
insert into OPMenu values('12150001','增加',            3,'','','','','','','',1,1,'');
insert into OPMenu values('12150002','修改',            3,'','','','','','','',1,1,'');
insert into OPMenu values('12150003','删除',            3,'','','','','','','',1,1,'');

insert into OPMenu values('12170000','运营网点维护',    2,'','','','{"url":"appjs/pa/PADepartmentList.js","type":"panel"}','','','',0,0,'');
insert into OPMenu values('12170001','增加',            3,'','','','','','','',1,1,'');
insert into OPMenu values('12170002','修改',            3,'','','','','','','',1,1,'');
insert into OPMenu values('12170003','删除',            3,'','','','','','','',1,1,'');
******/


INSERT INTO OPMenu VALUES('12050000','服务类型管理',    2,'','','','{"url":"appjs/im/IMServiceRate.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('12050001','增加',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12050002','修改',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12050003','删除',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12050004','查询',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('12100000','短信接口管理',    2,'','','','{"url":"appjs/im/IMGateway.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('12100001','增加',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12100002','修改',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12100003','删除',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12100004','查询',            3,'','','','','','','',1,1,'');

/*
INSERT INTO OPMenu VALUES('12110000','客服邮箱维护',    2,'','','','{"url":"appjs/im/IMEmail.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('12110002','修改',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('12120000','消息模板管理',    2,'','','','{"url":"appjs/im/IMMsgTemplate.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('12120002','修改',            3,'','','','','','','',1,1,'');
*/

/*
INSERT INTO OPMenu VALUES('12130000','短信模板管理',    2,'','','','{"url":"appjs/im/IMSMSTemplate.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('12130002','修改',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12140000','邮件模板管理',    2,'','','','{"url":"appjs/im/IMEmailTemplate.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('12140002','修改',            3,'','','','','','','',1,1,'');
*/

INSERT INTO OPMenu VALUES('12210000','包裹服务商管理',    2,'','','','{"url":"appjs/im/IMCompany.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('12210001','增加',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12210002','修改',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12210003','删除',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12210004','查询',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12210012','授箱体权限',      3,'','','','','','','',1,1,'');


INSERT INTO OPMenu VALUES('12250000','分拣区域管理',    2,'','','','{"url":"appjs/im/IMZone.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('12250001','增加',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12250002','修改',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12250003','删除',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12250004','查询',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12250010','授柜体权限',      3,'','','','','','','',1,1,'');


INSERT INTO OPMenu VALUES('12270000','包裹处理中心管理',    2,'','','','{"url":"appjs/im/IMPostalProcessingCenter.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('12270001','增加',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12270002','修改',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12270003','删除',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12270004','查询',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('12290000','揽件区域管理',    2,'','','','{"url":"appjs/im/IMCollectZone.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('12290001','增加',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12290002','修改',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12290003','删除',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12290004','查询',            3,'','','','','','','',1,1,'');


INSERT INTO OPMenu VALUES('12310000','商业伙伴管理',    2,'','','','{"url":"appjs/im/IMBusinessPartner.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('12310001','增加',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12310002','修改',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12310003','删除',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12310004','查询',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12310005','选择服务',        3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12310006','账户充值',        3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12310007','忘记密码',        3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('12330000','电商伙伴管理',    2,'','','','{"url":"appjs/im/IMEcommercePartner.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('12330001','增加',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12330002','修改',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12330003','删除',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('12330004','查询',            3,'','','','','','','',1,1,'');
                                                   

INSERT INTO OPMenu VALUES('12350000','个人用户管理',    2,'','','','{"url":"appjs/im/IMCustomer.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('12350001','增加',            3,'','','','','','','',1,1,'');      
INSERT INTO OPMenu VALUES('12350002','修改',            3,'','','','','','','',1,1,'');      
INSERT INTO OPMenu VALUES('12350003','删除',            3,'','','','','','','',1,1,'');    
INSERT INTO OPMenu VALUES('12350004','查询',            3,'','','','','','','',1,1,'');

/************************************************
  *管理账户管理
  * ************************************************/
INSERT INTO OPMenu VALUES('13000000','管理账户管理',      1,'','','','','','icon-op','',0,0,'');

INSERT INTO OPMenu VALUES('13010000','角色设置',        2,'','','','{"url":"appjs/op/OPRole.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('13010001','增加',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('13010002','修改',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('13010003','删除',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('13010004','查询',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('13010010','授功能权限',      3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('13010012','授特殊权限',      3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('13020000','管理员设置',      2,'','','','{"url":"appjs/op/OPOperator.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('13020001','增加',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('13020002','修改',            3,'','','','','','','',1,1,'');
/*insert into OPMenu values('13020003','注销/启用',            3,'','','','','','','',1,1,'');*/
INSERT INTO OPMenu VALUES('13020004','删除',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('13020005','查询',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('13020006','修改角色',        3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('13020010','授功能权限',      3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('13020012','授特殊权限',      3,'','','','','','','',1,1,'');



INSERT INTO OPMenu VALUES('13030000','管理员清密',      2,'','','','{"url":"appjs/op/OPOperClearPwd.js","type":"win"}','','','',0,1,'');
INSERT INTO OPMenu VALUES('13040000','管理员日志查询',  2,'','','','{"url":"appjs/op/OPOperLogQry.js","type":"panel"}','','','',0,1,'');
INSERT INTO OPMenu VALUES('13050000','管理员在线查询',  2,'','','','{"url":"appjs/op/OPOperOnlineQry.js","type":"panel"}','','','',0,1,'');



/*--运营信息管理--*/
INSERT INTO OPMenu VALUES('15000000','运营信息管理',      1,'','','','','','icon-mb','',0,0,'');

INSERT INTO OPMenu VALUES('15030000','取件密码重发',    2,'','','','{"url":"appjs/mb/MBShortMsg.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('15030001','重新发送',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('15030004','查询',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('15050000','订单数据重发',    2,'','','','{"url":"appjs/mb/MBSendItemToPPC.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('15050001','重新发送',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('15050004','查询',            3,'','','','','','','',1,1,'');
/*INSERT INTO OPMenu VALUES('15130000','催领信息查询',    2,'','','','{"url":"appjs/mb/MBReminderWater.js","type":"panel"}','','','',0,1,'');*/

/******暂不开放
INSERT INTO OPMenu VALUES('15150000','手机号码黑名单管理',    2,'','','','{"url":"appjs/mb/MBMobileBlacklist.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('15150001','增加',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('15150003','删除',            3,'','','','','','','',1,1,'');


INSERT INTO OPMenu VALUES('15200000','推送现场管理员',      2,'','','','{"url":"appjs/mb/MBSpotAdminAdd.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('15200002','推送',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('15260000','修改现场管理员密码',      2,'','','','{"url":"appjs/mb/MBSpotAdminMod.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('15260002','修改密码',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('15280000','删除现场管理员',      2,'','','','{"url":"appjs/mb/MBSpotAdminDel.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('15280002','删除',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('15500000','设备端控制参数修改',    2,'','','','{"url":"appjs/mb/MBTerminalCtrlParam.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('15500002','同步参数',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('15560000','推送LED消息',    2,'','','','{"url":"appjs/mb/MBPushLedMsg.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('15560002','推送',            3,'','','','','','','',1,1,'');
*********/

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

/************************************************
  *设备信息管理
  * ************************************************/

INSERT INTO OPMenu VALUES('21000000','设备信息管理',    1,'','','','','','icon-tb','',0,0,'');

INSERT INTO OPMenu VALUES('21010000','柜体信息维护',    2,'','','','{"url":"appjs/tb/TBTerminal.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('21010002','编辑',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('21010004','查询',            3,'','','','','','','',1,1,'');

/*INSERT INTO OPMenu VALUES('65110000','设备签到查询',      2,'','','','{"url":"appjs/mn/MNTerminalSign.js","type":"panel"}','','','',0,1,'');*/
INSERT INTO OPMenu VALUES('21020000','柜体签到查询',      2,'','','','{"url":"appjs/tb/TBTerminalSign.js","type":"panel"}','','','',0,1,'');
INSERT INTO OPMenu VALUES('21020004','查询',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('21020015','导出',            3,'','','','','','','',1,1,'');

/******暂不开放
INSERT INTO OPMenu VALUES('21030000','柜体信息同步',    2,'','','','{"url":"appjs/tb/TBSyncTerminal.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('21030002','同步',            3,'','','','','','','',1,1,'');
******/


INSERT INTO OPMenu VALUES('21050000','柜体状态维护',    2,'','','','{"url":"appjs/tb/TBTerminalStatus.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('21050002','修改状态',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('21050004','查询',            3,'','','','','','','',1,1,'');

/******暂不开放
INSERT INTO OPMenu VALUES('21150000','箱体锁定状态维护',    2,'','','','{"url":"appjs/tb/TBBoxLock.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('21150002','修改状态',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('21180000','箱体故障状态维护',    2,'','','','{"url":"appjs/tb/TBBoxFault.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('21180002','修改状态',            3,'','','','','','','',1,1,'');
******/

INSERT INTO OPMenu VALUES('21400000','箱体状态维护',    2,'','','','{"url":"appjs/tb/TBBoxStatus.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('21400002','修改锁定状态',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('21400004','修改故障状态',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('21400005','查询',            3,'','','','','','','',1,1,'');

/*INSERT INTO OPMenu VALUES('65280000','设备报警信息查询',      2,'','','','{"url":"appjs/mn/MNDeviceAlert.js","type":"panel"}','','','',0,1,'');*/
INSERT INTO OPMenu VALUES('21500000','设备报警信息查询',      2,'','','','{"url":"appjs/tb/TBDeviceAlert.js","type":"panel"}','','','',0,1,'');
INSERT INTO OPMenu VALUES('21500004','查询',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('21500015','导出',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('21800000','柜体状态监控',      2,'','','','{"url":"appjs/tb/TBTerminalStatusMap.js","type":"panel"}','','','',0,1,'');
INSERT INTO OPMenu VALUES('21800004','查询',            3,'','','','','','','',1,1,'');

/*--投递员管理--*/
INSERT INTO OPMenu VALUES('31000000','投递员管理',    1,'','','','','','icon-pm','',0,0,'');

/*****移除
insert into OPMenu values('31010000','投递公司维护',    2,'','','','{"url":"appjs/pm/PMCompany.js","type":"panel"}','','','',0,0,'');
insert into OPMenu values('31010001','增加',            3,'','','','','','','',1,1,'');
insert into OPMenu values('31010002','修改',            3,'','','','','','','',1,1,'');
insert into OPMenu values('31010003','删除',            3,'','','','','','','',1,1,'');


insert into OPMenu values('31010010','授柜体权限',      3,'','','','','','','',1,1,'');
insert into OPMenu values('31010012','授箱体权限',      3,'','','','','','','',1,1,'');

insert into OPMenu values('31030000','投递公司所属网点',    2,'','','','{"url":"appjs/pm/PMCompanyDep.js","type":"panel"}','','','',0,0,'');
insert into OPMenu values('31030002','修改',            3,'','','','','','','',1,1,'');
*****/

INSERT INTO OPMenu VALUES('31050000','投递员信息维护',    2,'','','','{"url":"appjs/pm/PMPostman.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('31050001','增加',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('31050002','修改',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('31050003','删除',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('31050004','查询',            3,'','','','','','','',1,1,'');
/*INSERT INTO OPMenu VALUES('31050004','注销/启用',            3,'','','','','','','',1,1,'');*/
/*INSERT INTO OPMenu VALUES('31050015','导出',            3,'','','','','','','',1,1,'');*/

/******暂不开放
INSERT INTO OPMenu VALUES('31050010','授柜体权限',      3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('31050012','授箱体权限',      3,'','','','','','','',1,1,'');
*****/

INSERT INTO OPMenu VALUES('31090000','投递员密码维护',    2,'','','','{"url":"appjs/pm/PMModPostmanPwd.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('31090002','修改',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('31090004','查询',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('31110000','投递员权限维护',    2,'','','','{"url":"appjs/pm/PMModPostmanRight.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('31110002','修改',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('31110004','查询',            3,'','','','','','','',1,1,'');

/**移除
INSERT INTO OPMenu VALUES('31150000','投递员所属网点维护',    2,'','','','{"url":"appjs/pm/PMModPostmanDep.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('31150002','修改',            3,'','','','','','','',1,1,'');
**/
INSERT INTO OPMenu VALUES('31160000','投递员所属区域维护',    2,'','','','{"url":"appjs/pm/PMModPostmanZone.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('31160002','修改',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('31160004','查询',            3,'','','','','','','',1,1,'');
/**移除
INSERT INTO OPMenu VALUES('31190000','投递员所属公司维护',    2,'','','','{"url":"appjs/pm/PMModPostmanComp.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('31190002','修改',            3,'','','','','','','',1,1,'');
**/





/************************************************
  *投递业务管理
  * ************************************************/
INSERT INTO OPMenu VALUES('33000000','投递业务管理',            1,'','','','','','icon-pt','',0,0,'');


INSERT INTO OPMenu VALUES('33010000','Pane1',                   2,'','','','{"url":"appjs/pt/PTPane1.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('33010001','Query',                   3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33010002','ConfirmReceive',          3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33010004','AssignLocker',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33010005','Transfer',                3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33010006','AddItem',                 3,'','','','','','','',1,1,'');
/*INSERT INTO OPMenu VALUES('33010007','PrintTransfer',           3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33010008','PrintConfirm',            3,'','','','','','','',1,1,'');*/


INSERT INTO OPMenu VALUES('33020000','Pane2',                   2,'','','','{"url":"appjs/pt/PTPane2.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('33020001','Query',                   3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33020013','UnassignLocker',          3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33020014','AddToDropOrder',          3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33020015','DeleteFromDropOrder',     3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33020016','ExecuteDropOrder',        3,'','','','','','','',1,1,'');
/*INSERT INTO OPMenu VALUES('33020019','PrintDropOrder',          3,'','','','','','','',1,1,'');*/
/*INSERT INTO OPMenu VALUES('33020020','NextDropOrder',           3,'','','','','','','',1,1,'');*/
/*INSERT INTO OPMenu VALUES('33020021','EmptyBox',                3,'','','','','','','',1,1,'');*/

INSERT INTO OPMenu VALUES('33030000','Pane3',                   2,'','','','{"url":"appjs/pt/PTPane3.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('33030001','Query',                   3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33030023','Return',                  3,'','','','','','','',1,1,'');
/*INSERT INTO OPMenu VALUES('33030024','PrintReturnOrder',        3,'','','','','','','',1,1,'');*/
INSERT INTO OPMenu VALUES('33030025','Missing',                 3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('33040000','Pane4',                   2,'','','','{"url":"appjs/pt/PTPane4.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('33040001','Query',                   3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33040027','M-Expire',                3,'','','','','','','',1,1,'');
/*INSERT INTO OPMenu VALUES('33040028','SendSMS',                     3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33040029','SendEmail',                   3,'','','','','','','',1,1,'');
*/
INSERT INTO OPMenu VALUES('33040030','AbnormalReturn',          3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('33040031','ChangeExpireDate',         3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33040032','ChangeReminderDate',       3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('33050000','Pane5',                   2,'','','','{"url":"appjs/pt/PTPane5.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('33050001','Query',                   3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33050025','Retrieve',                3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33050026','ConfirmLose',             3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33050005','Transfer',                3,'','','','','','','',1,1,'');
/*INSERT INTO OPMenu VALUES('33050007','PrintTransfer',           3,'','','','','','','',1,1,'');*/
INSERT INTO OPMenu VALUES('33050030','Redistribute',            3,'','','','','','','',1,1,'');
/*INSERT INTO OPMenu VALUES('33050031','PrintRedistribute',       3,'','','','','','','',1,1,'');*/

INSERT INTO OPMenu VALUES('33060000','Pane6',                   2,'','','','{"url":"appjs/pt/PTPane6.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('33060001','Query',                   3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33060075','Transfer',                3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33060076','Receive',                 3,'','','','','','','',1,1,'');
/*INSERT INTO OPMenu VALUES('33060077','PrintReceive',          3,'','','','','','','',1,1,'');*/
/*INSERT INTO OPMenu VALUES('33060085','PrintTransfer',           3,'','','','','','','',1,1,'');*/
/*INSERT INTO OPMenu VALUES('33060086','NextTransferOrder',       3,'','','','','','','',1,1,'');*/
INSERT INTO OPMenu VALUES('33060087','Redistribute',             3,'','','','','','','',1,1,'');
/*INSERT INTO OPMenu VALUES('33060088','Cancel',                  3,'','','','','','','',1,1,'');*/
INSERT INTO OPMenu VALUES('33060060','ExportTransaction',        3,'','','','','','','',1,1,'');

/******移除
INSERT INTO OPMenu VALUES('33010000','订单状态修改',    2,'','','','{"url":"appjs/pt/PTModPackStatus.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('33010002','修改',            3,'','','','','','','',1,1,'');


INSERT INTO OPMenu VALUES('33030000','删除在箱订单',    2,'','','','{"url":"appjs/pt/PTDelInboxPack.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('33030003','删除',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('33050000','逾期时间修改',    2,'','','','{"url":"appjs/pt/PTModExpiredTime.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('33050002','修改',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('33070000','重置提货码',    2,'','','','{"url":"appjs/pt/PTResetOpenKey.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('33070002','重置',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('33110000','同步投递反馈信息',    2,'','','','{"url":"appjs/pt/PTSyncDelivery.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('33110002','同步',            3,'','','','','','','',1,1,'');
******/




/*--统计报表管理--*/

INSERT INTO OPMenu VALUES('35000000','统计报表管理',    1,'','','','','','icon-qy','',0,0,'');

INSERT INTO OPMenu VALUES('35110000','投递记录查询',    2,'','','','{"url":"appjs/qy/QYHistoryQry.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('35110010','AbnormalReturn',          3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('35110015','导出',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('35210000','格口使用统计',    2,'','','','{"url":"appjs/qy/QYBoxUsedStat.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('35210015','导出',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('35220000','格口类型使用频率统计',    2,'','','','{"url":"appjs/qy/QYBoxUsageStat.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('35220015','导出',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('35240000','ServiceOwner格口使用统计',    2,'','','','{"url":"appjs/qy/QYCompanyBoxUsage.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('35240015','导出',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('35260000','BP账单查询',    2,'','','','{"url":"appjs/qy/QYBPBillsQry.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('35260015','导出',            3,'','','','','','','',1,1,'');

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

/* */
INSERT INTO OPMenu VALUES('33010009','PTTransportPackAdd',                 3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33010010','PTTransportPackDel',                 3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33010011','PTTransportPackExe',                 3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('33030035','PTCounterReceive',                 3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33040036','PTCounterReturn',                3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('33040037','PTCounterPickup',                3,'','','','','','','',1,1,'');

/*INSERT INTO OPMenu VALUES('35310000','按柜体统计',    2,'','','','{"url":"appjs/qy/QYStat4Terminal.js","type":"panel"}','','','',0,0,'');

INSERT INTO OPMenu VALUES('35410000','按投递公司统计',    2,'','','','{"url":"appjs/qy/QYStat4Company.js","type":"panel"}','','','',0,0,'');

INSERT INTO OPMenu VALUES('35510000','短信发送数量统计',    2,'','','','{"url":"appjs/qy/QYStat4SMS.js","type":"panel"}','','','',0,0,'');*/
INSERT INTO OPMenu VALUES('35910000','投递量统计',  2,'','','','{"url":"appjs/qy/QYDeliverOrderStat.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('35910005','查询',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('35910007','导出',            3,'','','','','','','',1,1,'');


/*--远程求助管理--*/
INSERT INTO OPMenu VALUES('55000000','远程求助管理',    1,'','','','','','icon-rm','',0,0,'');
INSERT INTO OPMenu VALUES('55100000','获取开箱密码',      2,'','','','{"url":"appjs/rm/RMAppeal.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('55100001','远程开箱',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('55100002','故障原因',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('55100004','查询',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('55100015','导出',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('55600000','远程求助查询',      2,'','','','{"url":"appjs/rm/RMAppealQry.js","type":"panel"}','','','',0,0,'');



/*--设备监控管理--*/
/******暂未实现 移到柜体信息管理
INSERT INTO OPMenu VALUES('65000000','设备监控管理',    1,'','','','','','icon-mn','',0,0,'');
INSERT INTO OPMenu VALUES('65110000','设备签到查询',      2,'','','','{"url":"appjs/mn/MNTerminalSign.js","type":"panel"}','','','',0,1,'');

INSERT INTO OPMenu VALUES('65210000','设备状态信息查询',      2,'','','','{"url":"appjs/mn/MNTerminalStatus.js","type":"panel"}','','','',0,1,'');
INSERT INTO OPMenu VALUES('65230000','箱体状态信息查询',      2,'','','','{"url":"appjs/mn/MNBoxStatus.js","type":"panel"}','','','',0,1,'');
INSERT INTO OPMenu VALUES('65280000','设备报警信息查询',      2,'','','','{"url":"appjs/mn/MNDeviceAlert.js","type":"panel"}','','','',0,1,'');
******/

/*--设备诊断更新--*/

INSERT INTO OPMenu VALUES('85000000','设备诊断更新',    1,'','','','','','icon-da','',0,0,'');
INSERT INTO OPMenu VALUES('85150000','获取设备在箱包裹信息',      2,'','','','{"url":"appjs/da/DAGetInboxPackage.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('85150002','获取',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('85150004','查询',            3,'','','','','','','',1,1,'');

/******暂未实现
INSERT INTO OPMenu VALUES('85250000','获取设备历史投递记录信息',      2,'','','','{"url":"appjs/da/DAGetHistoryPackage.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('85250002','获取',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('85350000','获取箱体一致性比对信息',      2,'','','','{"url":"appjs/da/DAGetBoxDectionResult.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('85350002','获取',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('85580000','重新发送对账文件',      2,'','','','{"url":"appjs/da/DAUploadInbox.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('85580002','上传',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('85580004','查询',            3,'','','','','','','',1,1,'');

******/

INSERT INTO OPMenu VALUES('85380000','获取设备端控制参数',      2,'','','','{"url":"appjs/da/DAGetControlParam.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('85380002','获取',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('85380004','查询',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('85400000','获取上传失败数据',      2,'','','','{"url":"appjs/da/DAGetUpFailureData.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('85400002','获取',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('85400004','查询',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('85450000','获取管理员日志',      2,'','','','{"url":"appjs/da/DAGetManagerLog.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('85450002','获取',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('85450004','查询',            3,'','','','','','','',1,1,'');

INSERT INTO OPMenu VALUES('85550000','获取设备系统日志',      2,'','','','{"url":"appjs/da/DAGetDeviceSysLog.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('85550002','获取',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('85550004','查询',            3,'','','','','','','',1,1,'');


INSERT INTO OPMenu VALUES('85610000','设备端程序升级',      2,'','','','{"url":"appjs/da/DAUpgradeProgram.js","type":"panel"}','','','',0,0,'');
INSERT INTO OPMenu VALUES('85610002','升级',            3,'','','','','','','',1,1,'');
INSERT INTO OPMenu VALUES('85610004','查询',            3,'','','','','','','',1,1,'');


/************************************************
  * 2.更新角色菜单信息
  *
  * ************************************************/
/*TRUNCATE TABLE OPRoleToMenu;*/

INSERT INTO OPRoleToMenu(RoleID,MenuID)
SELECT 1,MenuID
FROM OPMenu  WHERE MenuID<'33000000' OR MenuID>'33999999';

INSERT INTO OPRoleToMenu(RoleID,MenuID)
SELECT 2,MenuID
FROM OPMenu WHERE MenuID>='12000000';
  
/************************************************
  * 3.更新管理员菜单信息
  *
  * ************************************************/
/*TRUNCATE TABLE OPOperToMenu;*/
  
INSERT INTO OPOperToMenu(OperID,MenuID)
SELECT '181818',MenuID
FROM OPMenu WHERE MenuID<'33000000' OR MenuID>'33999999';  /*无业务处理权限*/

INSERT INTO OPOperToMenu(OperID,MenuID)
SELECT 'Master',MenuID
FROM OPMenu WHERE MenuID>='12000000';

COMMIT TRANSACTION;

