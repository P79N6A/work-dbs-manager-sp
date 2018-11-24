TRUNCATE TABLE OPFunction;

/*------------------------------------------------------------------------*/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('110102','全局配置设置','SMGlobalCfgSet','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('110104','全局配置查询','SMGlobalCfgQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('110712','数据库配置设置','SMDbCfgSet','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('110714','数据库配置查询','SMDbCfgQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('110725','加载邮件模板','SMLoadMailVm','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('110746','重新加载记录日志标志数据','SMReloadFuncData','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('110747','重新加载日志文件配置','SMReloadLogCfg','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('110748','重新加载控制参数','SMReloadCtrlParam','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('110112','修改软件版本号','SMModSysVersion','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('110113','修改签到密码','SMModSignPwd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('110114','修改服务器连接设置','SMModServerCfg','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('110115','修改欢迎词','SMModWelcomeInfo','1');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('110119','系统配置查询','SMSysInfoQry','0');

/*------------------------------------------------------------------------*/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('120002','修改控制参数信息','PAControlParamMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('120004','查询控制参数信息','PAControlParamQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('120007','查询控制参数类别','PAControlTypeQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('120012','同步到设备端控制参数','PATerminalCtrlParamMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('120036','修改系统字典','PADictionaryMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('120037','删除系统字典','PADictionaryDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('120038','查询系统字典','PADictionaryQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('120039','查询系统字典类别','PADictTypeQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('120040','查询所有的数据字典','PAAllDictQry','0');

/****************************************************************************/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('130001','角色增加','OPRoleAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('130002','角色修改','OPRoleMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('130003','角色删除','OPRoleDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('130004','角色查询','OPRoleQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('130011','角色菜单信息增加','OPRoleToMenuAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('130014','角色菜单信息查询','OPRoleToMenuQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('130016','角色总体限制权限增加','OPRoleAllLimitAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('130018','角色总体限制权限删除','OPRoleAllLimitDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('130019','角色总体限制权限查询','OPRoleAllLimitQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('133011','角色特殊权限设置增加','OPRoleSpeRightAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('133013','角色特殊权限设置删除','OPRoleSpeRightDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('133014','角色特殊权限设置查询','OPRoleSpeRightQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132001','管理员增加','OPOperatorAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132002','管理员修改','OPOperatorMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132003','管理员删除','OPOperatorDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132004','查询管理员信息','OPOperatorQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132005','管理员个数查询','OPOperatorQryCount','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132008','管理员注销(启用)','OPOperatorModStatus','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132011','管理员菜单信息增加','OPOperToMenuAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132014','管理员菜单信息查询','OPOperToMenuQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132016','管理员总体限制权限增加','OPOperAllLimitAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132018','管理员总体限制权限删除','OPOperAllLimitDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132019','管理员总体限制权限查询','OPOperAllLimitQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132031','管理员角色增加','OPOperRoleAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132033','管理员角色删除','OPOperRoleDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132034','管理员角色查询','OPOperRoleQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132061','管理员日志查询','OPOperLogQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132062','查询管理员日志数量','OPOperLogQryCount','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132065','管理员在线信息查询','OPOperOnlineQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132066','查询管理员在线信息数量','OPOperOnlineQryCount','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('133001','管理员特殊权限设置增加','OPOperSpeRightAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('133003','管理员特殊权限设置删除','OPOperSpeRightDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('133004','管理员特殊权限设置查询','OPOperSpeRightQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132051','管理员登陆','OPOperLogin','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132052','操作员清密','OPOperClearPwd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132053','修改个人密码','OPOperModPwd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('132054','操作员登出','OPOperLogout','0');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('133051','推送现场管理员','OPPushSpotOperator','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('133052','修改现场管理员密码','OPModSpotOperPwd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('133053','删除现场管理员','OPDelSpotOperator','1');


/*--------------------------------------------------------------------------*/
/***********
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150001','增加运营网点信息','MBDepartmentAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150002','修改运营网点信息','MBDepartmentMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150003','删除运营网点信息','MBDepartmentDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150004','查询运营网点信息','MBDepartmentQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150005','查询运营网点信息(以树形结构返回)','MBDepartTreeQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150006','查询运营网点信息（列表结构返回）','MBDepartListQry','0');
*********/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150111','修改短消息发送状态','MBModSMSSentStatus','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150112','取件密码短消息重新发送','MBPwdShortMsgReSend','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150114','取件密码短消息查询','MBPwdShortMsgQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150115','取件密码短消息查询数量','MBPwdShortMsgQryCount','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150134','包裹催领流水查询','MBReminderWaterQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150135','包裹催领流水查询数量','MBReminderWaterQryCount','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150201','黑名单增加','MBMobileBlackListAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150203','黑名单删除','MBMobileBlackListDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150204','黑名单查询','MBMobileBlackListQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150205','黑名单查询数量','MBMobileBlackListQryCount','0');


INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150301','设备签到','MBDeviceSign','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150302','修改设备注册注册标志','MBModRegisterFlag','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150303','设备离线','MBDeviceOffline','1');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150304','设备签到信息查询','MBDeviceSignQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150306','设备签到信息查询数量','MBDeviceSignQryCount','0');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150314','设备警报信息查询','MBDeviceAlertQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150315','设备警报信息查询数量','MBDeviceAlertQryCount','0');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150331','获取设备在箱包裹信息','MBGetInboxPackage','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150332','获取设备历史投递记录信息','MBGetHistoryPackage','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150333','获取箱体一致性比对信息','MBGetBoxDectionResult','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150335','获取管理员日志','MBGetManagerLog','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150336','获取设备系统日志','MBGetDeviceSysLog','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150337','获取设备端控制参数','MBGetControlParam','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150338','获取上传失败数据','MBGetUpFailureData','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150345','发送设备升级通知','MBSendUpgradeInform','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150362','上传设备在箱包裹信息(对账信息)','MBUploadInboxPack','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150444','现场管理员登录','MBSpotAdminLogin','1');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150452','推送LED消息','MBPushTerminalLedMsg','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150454','LED消息查询','MBTerminalLedMsgQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('150455','LED消息查询数量','MBTerminalLedMsgQryCount','0');



/*-------------------------------------------------------------------------*/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210001','柜体信息增加','TBTerminalAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210002','柜体信息修改','TBTerminalMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210003','修改设备号','TBTerminalNoMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210004','单个柜体信息查询','TBTerminalDetail','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210005','柜体监控参数修改','TBTerminalParamMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210006','柜体监控参数查询','TBTerminalParamQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210007','柜体网络参数修改','TBNetworkParamMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210008','柜体网络参数查询','TBNetworkParamQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210010','柜体名称修改','TBTerminalModName','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210011','柜体状态修改','TBTerminalModStatus','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210012','修改柜体所属运营网点编号','TBTerminalModDepartID','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210014','柜体信息查询','TBTerminalQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210015','柜体信息查询数量','TBTerminalQryCount','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210020','柜体地址信息查询（范围查询）','LockerStationAddressQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210021','柜体地址信息查询（ID查询）','TBLockerAddressQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210101','扩展柜增加','TBDeskAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210102','扩展柜修改','TBDeskMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210103','扩展柜删除','TBDeskDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210104','扩展柜查询','TBDeskQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210106','打开副柜维修条','TBOpenMaintainKey','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210202','箱体显示名称修改','TBBoxNameMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210204','箱体类型修改','TBBoxTypeMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210208','箱体状态修改','TBBoxStatusMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210209','箱体故障状态维护','TBFaultStatusMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210210','箱体锁定状态维护','TBLockStatusMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210214','箱体信息查询','TBBoxQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210215','箱体信息查询数量','TBBoxQryCount','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210224','箱体状态信息查询','TBBoxStatusInfoQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210225','箱体状态信息查询数量','TBBoxStatusInfoQryCount','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210261','管理开箱箱体查询','TBBoxQry4Open','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210262','设备检测箱体查询','TBBoxQry4Detect','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210263','可用箱体信息统计','TBFreeBoxStat','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210273','复制箱体类型','TBCopyBoxType','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210302','柜体外设配置修改','TBPeripheralsMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210304','柜体外设配置查询','TBPeripheralsQry','0');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210401','管理员开箱','TBOpenBox4Manager','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210402','投递开箱','TBOpenBox4Delivery','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210403','寄存开箱','TBOpenBox4Deposit','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210404','回收开箱','TBOpenBox4Recovery','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210405','取件开箱','TBOpenBox4Pickup','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210406','投递员再次开箱','TBReOpenBox4Postman','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210407','投递员取消投递再次开箱','TBReOpenBox4Cancel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('210408','取件重新开箱','TBReOpenBox4Pickup','1');


/****************************************************************************************/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250001','服务商信息增加','IMCompanyAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250002','服务商信息修改','IMCompanyMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250003','服务商信息删除','IMCompanyDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250004','服务商信息查询','IMCompanyQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250005','服务商数量查询','IMCompanyQryCount','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250006','服务商列表查询','IMCompanyListQry','0');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250021','分拣区域中心增加','IMZoneAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250022','分拣区域中心修改','IMZoneMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250023','分拣区域中心删除','IMZoneDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250024','分拣区域中心查询','IMZoneQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250025','分拣区域中心数量查询','IMZoneQryCount','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250026','分拣区域中心列表查询','IMZoneListQry','0');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250031','分拣区域计数器增加','IMZoneCounterAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250032','分拣区域计数器修改','IMZoneCounterMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250034','分拣区域计数器查询','IMZoneCounterQry','0');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250041','包裹处理中心信息增加','IMPPCAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250042','包裹处理中心信息修改','IMPPCMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250043','包裹处理中心信息删除','IMPPCDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250044','包裹处理中心信息查询','IMPPCQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250045','包裹处理中心查询数量','IMPPCQryCount','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250046','包裹处理中心列表查询','IMPPCListQry','0');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250051','商业伙伴信息增加','IMBusiPartnerAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250052','商业伙伴信息修改','IMBusiPartnerMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250053','商业伙伴信息删除','IMBusiPartnerDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250054','商业伙伴信息查询','IMBusiPartnerQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250055','商业伙伴查询数量','IMBusiPartnerQryCount','0');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250061','电商伙伴信息增加','IMEcomPartnerAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250062','电商伙伴信息修改','IMEcomPartnerMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250063','电商伙伴信息删除','IMEcomPartnerDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250064','电商伙伴信息查询','IMEcomPartnerQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250065','电商伙伴查询数量','IMEcomPartnerQryCount','0');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250071','个人客户信息增加','IMCustomerAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250072','个人客户信息修改','IMCustomerMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250073','个人客户信息删除','IMCustomerDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250074','个人客户信息查询','IMCustomerQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250075','个人客户查询数量','IMCustomerQryCount','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250076','个人客户单条信息查询','IMCustomerInfoQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250078','自动修改个人客户状态','IMCustomerModStatusAuto','1');


INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250081','短信入口信息增加','IMGatewayAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250082','短信入口信息修改','IMGatewayMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250083','短信入口信息删除','IMGatewayDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250084','短信入口信息查询','IMGatewayQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250085','短信入口查询数量','IMGatewayQryCount','0');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250091','服务类型信息增加','IMServiceRateAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250092','服务类型信息修改','IMServiceRateMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250093','服务类型信息删除','IMServiceRateDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250094','服务类型信息查询','IMServiceRateQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250095','服务类型查询数量','IMServiceRateQryCount','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250096','服务类型列表查询','IMServiceRateListQry','0');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250101','揽件区域增加','IMCollectZoneAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250102','揽件区域修改','IMCollectZoneMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250103','揽件区域删除','IMCollectZoneDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250104','揽件区域信息查询','IMCollectZoneQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250105','揽件区域查询数量','IMCollectZoneQryCount','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250106','揽件区域列表查询','IMCollectZoneListQry','0');


INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250111','邮件账户信息增加','IMEmailAccountAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250112','邮件账户信息修改','IMEmailAccountMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250113','邮件账户信息删除','IMEmailAccountDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250114','邮件账户信息查询','IMEmailAccountQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250115','邮件账户数量数量查询','IMEmailAccountQryCount','0');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250121','消息模板信息增加','IMMsgTemplateAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250122','消息模板信息修改','IMMsgTemplateMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250123','消息模板信息删除','IMMsgTemplateDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250124','消息模板信息查询','IMMsgTemplateQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('250125','消息模板数量查询','IMMsgTemplateQryCount','0');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('251001','服务商箱门权限增加','IMCompBoxRightAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('251002','服务商箱门权限修改','IMCompBoxRightMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('251003','服务商箱门权限删除','IMCompBoxRightDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('251004','服务商箱门权限查询','IMCompBoxRightQry','0');
 
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('251021','分拣区域柜体权限增加','IMZoneLockerRightAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('251022','分拣区域柜体权限修改','IMZoneLockerRightMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('251023','分拣区域柜体权限删除','IMZoneLockerRightDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('251024','分拣区域柜体权限查询','IMZoneLockerRightQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('251025','分拣区域柜体数量查询','IMZoneLockerRightQryCount','0'); 

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('251041','商业伙伴服务权限增加','IMBPartnerServiceRightAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('251042','商业伙伴服务权限修改','IMBPartnerServiceRightMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('251043','商业伙伴服务权限删除','IMBPartnerServiceRightDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('251044','商业伙伴服务权限查询','IMBPartnerServiceRightQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('251045','商业伙伴服务数量查询','IMBPartnerServiceRightQryCount','0'); 
                                                                             
/****************************************************************************************/
/*INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('310001','投递公司增加','PMCompanyAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('310002','投递公司修改','PMCompanyMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('310003','投递公司删除','PMCompanyDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('310004','投递公司查询','PMCompanyQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('310006','投递公司列表查询','PMCompanyListQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('310012','修改投递公司所属运营网点','PMCompanyModDep','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('310101','投递公司柜体权限增加','PMCorpTerminalRightAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('310104','投递公司柜体权限查询','PMCorpTerminalRightQry','0');
*/

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('311001','投递员增加','PMPostmanAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('311002','投递员修改','PMPostmanMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('311003','投递员删除','PMPostmanDel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('311004','投递员查询','PMPostmanQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('311005','投递员查询数量','PMPostmanQryCount','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('311021','投递员修改密码','PMPostmanModPwd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('311031','投递员绑定卡','PMPostmanBindCard','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('311032','投递员解绑定卡','PMPostmanUnBindCard','1');
/*INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('311042','修改投递员所属运营网点','PMPostmanModDep','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('311052','修改投递员所属投递公司','PMPostmanModComp','1');
*/

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('311101','投递员柜体权限增加','PMManTerminalRightAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('311104','投递员柜体权限查询','PMManTerminalRightQry','0');

/*
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('311201','推送投递员增加','PMPushPostmanAdd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('311202','推送投递员修改','PMPushPostmanMod','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('311203','推送投递员删除','PMPushPostmanDel','1');
*/

/*****************************************************************************************/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330001','投递员登陆验证','PTPostmanLogin','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330021','下载待投递订单列表','PTReadPackageQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330022','单个包裹查询','PTPackageDetail','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330023','本地验证包裹是否已经投递','PTPackIsDelivery','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330026','投递包裹','PTDeliveryPackage','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330031','下载逾期包裹单列表','PTExpiredPackQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330033','取回逾期包裹','PTWithdrawExpiredPack','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330035','本地验证逾期件是否允许取回','PTCheckWithdrawPack','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330037','投递员设置箱门故障','PTSetBoxFault','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330091','同步投递反馈信息','PTSyncDelivery','1');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330201','用户取件身份认证','PTVerfiyUser','1');
/*INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330203','用户取件','PTPickupPackage','1');*/
/*INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330204','用户取件','PTPickupPackage','1');*/

/*INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330207','管理员取件','PTManagerPickupPack','1');*/


INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330301','修改订单状态','PTModPackageStatus','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330302','删除订单','PTDelPackage','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330303','重置提货码','PTResetOpenBoxKey','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330304','修改订单超时时间','PTModExpiredTime','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330311','推送预约订单','PTPushSubscribeOrder','1');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330501','在箱信息入库','PTInboxPackage2DB','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330502','投递记录入库','PTDeliverPackage2DB','1');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331003','查询投递订单周期记录','PTItemLifeCycleQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331006','订单状态详情查询','PTDeliveryItemDetail','0');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331010','添加来自PPC的订单','PTAddItemsFromPPC','0');/**/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331011','添加待投递订单','PTDeliveryItemAdd','0');/**/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331014','投递订单信息查询','PTDeliveryItemQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331015','投递订单查询数量','PTDeliveryItemQryCount','0');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331021','确认接收包裹','PTParcelsReceive','0');/**/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331022','为包裹分配自提柜','PTParcelsAssign','0');/**/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331023','取消包裹分配的自提柜','PTParcelsUnAssign','0');/**/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331031','添加订单到投递清单','PTDropOrderItemsAdd','0');/**/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331032','从投递清单中删除订单','PTDropOrderItemsDel','0');/**/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331033','指定投递员派送','PTDropOrderItemsExe','0');/**/

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331040','包裹人工超期','PTParcelsManExpire','0');/**/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331041','未收到超期未取包裹','PTExpParcelsMissing','0');/**/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331042','超期未取包裹找回','PTExpParcelsRetrieve','0');/**/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331043','确认收到超期未取包裹','PTExpParcelsReturn','0');/**/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331044','超期包裹重新投递','PTExpParcesRedistribute','0');/**/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331045','确认包裹丢失','PTConfirmParcelsMissing','0');/**/

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331051','订单数据转运，等待发送','PTParcelsTransfer','0');/**/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331053','修改订单数据发送状态','PTTransferItemMod','0');/**/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331054','待发送订单查询','PTTransferItemQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331055','待发送订单数量查询','PTTransferItemQryCount','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331058','发送订单数量到PPC服务器','PTTransferItemSend','0');/**/


INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331060','获取下一报告单号','PTNextReportOrderID','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331070','查询投递订单生命周期','PTQryParcelItemLifeCycle','0');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331086','R6报告打印','PTPrintConfirmation','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331084','R4报告打印','PTPrintTransfer','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331082','R2报告打印','PTPrintDropOrder','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331083','R3报告打印','PTPrintReturn','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331087','R7报告打印','PTPrintRedistribute','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331085','R5报告打印','PTPrintInwardReceive','1');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('331093','定期消息发送（人工设定时间）','PTManSendMessage','1');


INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('330605','公司可用空箱类型数量查询','PTCompFreeBoxCountQry','0');

/****************************************************************************************/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('340001','商业合作伙伴登录','DMBusinessPartnerLogin','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('340011','商业伙伴修改密码','DMBusinessPartnerModPwd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('340012','商业伙伴忘记密码','DMBusinessPartnerForgetPwd','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('340100','商业伙伴账户充值请求','DMBusinessPartnerTopUpReq','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('340101','商业合作伙伴账户充值','DMBusinessPartnerTopUP','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('340102','商业伙伴账户余额更新','DMBPBalanceUpdate','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('340104','商业伙伴账户余额查询','DMPartnerBalanceQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('340106','商业伙伴服务查询','DMPartnerServicesQry','0');


INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('341011','创建寄件订单','DMDeliveryCreate','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('341013','取消寄件订单','DMDeliveryCancel','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('341014','寄件订单查询','DMDeliveryQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('341015','寄件订单数量查询','DMDeliveryQryCount','0');

/*INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('341021','分配揽件员','DMDeliveryAssign','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('341022','取消分配的揽件员','DMDeliveryUnAssign','1');*/

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('341030','揽件员查询包裹订单信息','DMCollectAgentItemQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('341031','寄件包裹由揽件员收件','DMDeliveryCollected','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('341032','分拣中心确认收到包裹','DMDeliveryReceived','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('341033','订单数据转运到PPC','DMDeliveryTransfer','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('341034','包裹就近派件投递','DMDeliveryTobeDropped','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('341041','导出交易账单','DMExportTransactions','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('341044','DMBPBillsQry','DMBPBillsQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('341045','DMBPBillsQryCount','DMBPBillsQryCount','0');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('341114','寄件订单查询（Partner）','DMDeliveryItemsQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('341115','寄件订单数量查询','寄件订单数量查询（Partner)','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('341116','寄件订单数量查询','寄件订单详情查询（Partner）','0');

/****************************************************************************************/
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('510001','用户开箱密码入库','SCWriteOpenBoxKey','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('510011','插入数据上传队列','SCInsertUploadDataQueue','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('510012','清除数据上传队列','SCDelUploadDataQueue','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('510013','修改数据上传队列','SCModUploadDataQueue','1');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('510101','同步服务器时间','SCSyncServerTime','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('510103','同步本地时间','SCSyncLocalTime','0');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('510201','人工同步柜体信息','SCSyncTerminalInfo','1');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('550028','远程开箱','RMRemoteOpenBox','1');


INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('650038','投递员APP取回','APPostmanAppTakeout','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('650138','用户APP取件','APCustomerAppOpenBox','1');

INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('650150','柜体PoBox信息查询','APLockerPoBoxQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('650151','用户注册，获取虚拟PoBox地址','APCustomerRegister','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('650152','注册用户更新虚拟PoBox地址','APCustomerUpdate','1');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('650153','用户信息查询','APCustomerInfoQry','0');
INSERT INTO OPFunction(FunctionID,FunctionName,FunctionServiceName,LogFlag) VALUES('650154','用户获取验证码','APCustomerGetKeyMsg','0');