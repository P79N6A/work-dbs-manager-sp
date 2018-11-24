package com.dcdzsoft.constant;

/**
 * <p>
 * Title: 智能自助包裹柜系统
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 *
 * <p>
 * Company: 杭州东城电子有限公司
 * </p>
 *
 * @author wangzl
 * @version 1.0
 */
public class ErrorCode {

    public final static String OK_SUCCESS = "0"; // 正确"
    public final static String OK_WARNING = "1"; // 警告"
    public final static String ERR_PARMERR = "-1"; // 参数错误"
    public final static String ERR_NORECORD = "-2"; // 数据库错误"
    public final static String ERR_SQLERR = "-3"; // SQL错误"
    public final static String ERR_ORACLERESOURCEBUSY = "-4"; // 数据库资源忙,请求用了NOWAIT选项"
    public final static String ERR_GETCURRENTDATEFAIL = "-5"; // 表示取当前时间出错"
    public final static String ERR_GETCURRENTTIMEFAIL = "-6"; // 表示取服务器时间出错"
    public final static String ERR_GETSEQUENCEERR = "-7"; // 计数器获取失败"
    public final static String ERR_OPERATEDBERROR = "-8"; // 操作数据库失败"
    public final static String ERR_EXECUTEPROCFAIL = "-9"; // 执行存储过程失败"
    public final static String ERR_GETCONNECTIONFAIL = "-10"; // 获取数据库连接失败"
    public final static String ERR_TRAVERSEROWSETFAIL = "-11"; // 遍历结果集失败"
    public final static String ERR_GETTEXTDATAFAIL = "-12"; // 获取文本数据失败"
    public final static String ERR_GETBINDATAFAIL = "-13"; // 获取二进制数据失败"
    public final static String ERR_BACKUPFAIL = "-20"; // 备份失败"
    public final static String ERR_RECOVERFAIL = "-21"; // 恢复失败"
    public final static String ERR_CONVERTXMLDATA = "-51"; // 转换XMLData数据包出错"
    public final static String ERR_NOTSUPPORTDATATYPE = "-52"; // 不支持的数据类型"
    public final static String ERR_WRONGPUSHMSGFORMAT = "-61"; // 错误的数据格式
    public final static String ERR_NETSERVICEEXCPTION = "-81"; // 网络服务异常，请稍候再试

    
    
    public static final String ERR_RECORDNOTEXIST = "-9001"; // 记录不存在
    public static final String ERR_RECORDEXISTS = "-9002"; // 记录已经存在
    
    public final static String ERR_FORBIDDELETE = "-9021"; // 禁止删除
    //////////////////////////




    //table:APUserKeyMap
    public final static String ERR_APUSERKEYMAPNODATA = "-1000";//记录不存在
    public final static String ERR_ADDAPUSERKEYMAPFAIL = "-1001";//插入失败
    public final static String ERR_MODAPUSERKEYMAPFAIL = "-1002";//修改失败
    public final static String ERR_DELAPUSERKEYMAPFAIL = "-1003";//删除失败
    public final static String ERR_QRYAPUSERKEYMAPFAIL = "-1004";//查询失败

    //table:DMCollectionParcel
    public final static String ERR_DMCOLLECTIONPARCELNODATA = "-1005";//记录不存在
    public final static String ERR_ADDDMCOLLECTIONPARCELFAIL = "-1006";//插入失败
    public final static String ERR_MODDMCOLLECTIONPARCELFAIL = "-1007";//修改失败
    public final static String ERR_DELDMCOLLECTIONPARCELFAIL = "-1008";//删除失败
    public final static String ERR_QRYDMCOLLECTIONPARCELFAIL = "-1009";//查询失败

    //table:DMHistoryItem
    public final static String ERR_DMHISTORYITEMNODATA = "-1010";//记录不存在
    public final static String ERR_ADDDMHISTORYITEMFAIL = "-1011";//插入失败
    public final static String ERR_MODDMHISTORYITEMFAIL = "-1012";//修改失败
    public final static String ERR_DELDMHISTORYITEMFAIL = "-1013";//删除失败
    public final static String ERR_QRYDMHISTORYITEMFAIL = "-1014";//查询失败

    //table:DMItemLifeCycle
    public final static String ERR_DMITEMLIFECYCLENODATA = "-1015";//记录不存在
    public final static String ERR_ADDDMITEMLIFECYCLEFAIL = "-1016";//插入失败
    public final static String ERR_MODDMITEMLIFECYCLEFAIL = "-1017";//修改失败
    public final static String ERR_DELDMITEMLIFECYCLEFAIL = "-1018";//删除失败
    public final static String ERR_QRYDMITEMLIFECYCLEFAIL = "-1019";//查询失败

    //table:IMBPartnerServiceRight
    public final static String ERR_IMBPARTNERSERVICERIGHTNODATA = "-1020";//记录不存在
    public final static String ERR_ADDIMBPARTNERSERVICERIGHTFAIL = "-1021";//插入失败
    public final static String ERR_MODIMBPARTNERSERVICERIGHTFAIL = "-1022";//修改失败
    public final static String ERR_DELIMBPARTNERSERVICERIGHTFAIL = "-1023";//删除失败
    public final static String ERR_QRYIMBPARTNERSERVICERIGHTFAIL = "-1024";//查询失败

    //table:IMBusinessPartner
    public final static String ERR_IMBUSINESSPARTNERNODATA = "-1025";//记录不存在
    public final static String ERR_ADDIMBUSINESSPARTNERFAIL = "-1026";//插入失败
    public final static String ERR_MODIMBUSINESSPARTNERFAIL = "-1027";//修改失败
    public final static String ERR_DELIMBUSINESSPARTNERFAIL = "-1028";//删除失败
    public final static String ERR_QRYIMBUSINESSPARTNERFAIL = "-1029";//查询失败

    //table:IMCollectZone
    public final static String ERR_IMCOLLECTZONENODATA = "-1030";//记录不存在
    public final static String ERR_ADDIMCOLLECTZONEFAIL = "-1031";//插入失败
    public final static String ERR_MODIMCOLLECTZONEFAIL = "-1032";//修改失败
    public final static String ERR_DELIMCOLLECTZONEFAIL = "-1033";//删除失败
    public final static String ERR_QRYIMCOLLECTZONEFAIL = "-1034";//查询失败

    //table:IMCompany
    public final static String ERR_IMCOMPANYNODATA = "-1035";//记录不存在
    public final static String ERR_ADDIMCOMPANYFAIL = "-1036";//插入失败
    public final static String ERR_MODIMCOMPANYFAIL = "-1037";//修改失败
    public final static String ERR_DELIMCOMPANYFAIL = "-1038";//删除失败
    public final static String ERR_QRYIMCOMPANYFAIL = "-1039";//查询失败

    //table:IMCompanyBoxRight
    public final static String ERR_IMCOMPANYBOXRIGHTNODATA = "-1040";//记录不存在
    public final static String ERR_ADDIMCOMPANYBOXRIGHTFAIL = "-1041";//插入失败
    public final static String ERR_MODIMCOMPANYBOXRIGHTFAIL = "-1042";//修改失败
    public final static String ERR_DELIMCOMPANYBOXRIGHTFAIL = "-1043";//删除失败
    public final static String ERR_QRYIMCOMPANYBOXRIGHTFAIL = "-1044";//查询失败

    //table:IMCustomer
    public final static String ERR_IMCUSTOMERNODATA = "-1045";//记录不存在
    public final static String ERR_ADDIMCUSTOMERFAIL = "-1046";//插入失败
    public final static String ERR_MODIMCUSTOMERFAIL = "-1047";//修改失败
    public final static String ERR_DELIMCUSTOMERFAIL = "-1048";//删除失败
    public final static String ERR_QRYIMCUSTOMERFAIL = "-1049";//查询失败

    //table:IMEcommercePartner
    public final static String ERR_IMECOMMERCEPARTNERNODATA = "-1050";//记录不存在
    public final static String ERR_ADDIMECOMMERCEPARTNERFAIL = "-1051";//插入失败
    public final static String ERR_MODIMECOMMERCEPARTNERFAIL = "-1052";//修改失败
    public final static String ERR_DELIMECOMMERCEPARTNERFAIL = "-1053";//删除失败
    public final static String ERR_QRYIMECOMMERCEPARTNERFAIL = "-1054";//查询失败

    //table:IMGateway
    public final static String ERR_IMGATEWAYNODATA = "-1055";//记录不存在
    public final static String ERR_ADDIMGATEWAYFAIL = "-1056";//插入失败
    public final static String ERR_MODIMGATEWAYFAIL = "-1057";//修改失败
    public final static String ERR_DELIMGATEWAYFAIL = "-1058";//删除失败
    public final static String ERR_QRYIMGATEWAYFAIL = "-1059";//查询失败

    //table:IMPostalProcessingCenter
    public final static String ERR_IMPOSTALPROCESSINGCENTERNODATA = "-1060";//记录不存在
    public final static String ERR_ADDIMPOSTALPROCESSINGCENTERFAIL = "-1061";//插入失败
    public final static String ERR_MODIMPOSTALPROCESSINGCENTERFAIL = "-1062";//修改失败
    public final static String ERR_DELIMPOSTALPROCESSINGCENTERFAIL = "-1063";//删除失败
    public final static String ERR_QRYIMPOSTALPROCESSINGCENTERFAIL = "-1064";//查询失败

    //table:IMServiceCounter
    public final static String ERR_IMSERVICECOUNTERNODATA = "-1065";//记录不存在
    public final static String ERR_ADDIMSERVICECOUNTERFAIL = "-1066";//插入失败
    public final static String ERR_MODIMSERVICECOUNTERFAIL = "-1067";//修改失败
    public final static String ERR_DELIMSERVICECOUNTERFAIL = "-1068";//删除失败
    public final static String ERR_QRYIMSERVICECOUNTERFAIL = "-1069";//查询失败

    //table:IMServiceRate
    public final static String ERR_IMSERVICERATENODATA = "-1070";//记录不存在
    public final static String ERR_ADDIMSERVICERATEFAIL = "-1071";//插入失败
    public final static String ERR_MODIMSERVICERATEFAIL = "-1072";//修改失败
    public final static String ERR_DELIMSERVICERATEFAIL = "-1073";//删除失败
    public final static String ERR_QRYIMSERVICERATEFAIL = "-1074";//查询失败

    //table:IMZone
    public final static String ERR_IMZONENODATA = "-1075";//记录不存在
    public final static String ERR_ADDIMZONEFAIL = "-1076";//插入失败
    public final static String ERR_MODIMZONEFAIL = "-1077";//修改失败
    public final static String ERR_DELIMZONEFAIL = "-1078";//删除失败
    public final static String ERR_QRYIMZONEFAIL = "-1079";//查询失败

    //table:IMZoneCounter
    public final static String ERR_IMZONECOUNTERNODATA = "-1080";//记录不存在
    public final static String ERR_ADDIMZONECOUNTERFAIL = "-1081";//插入失败
    public final static String ERR_MODIMZONECOUNTERFAIL = "-1082";//修改失败
    public final static String ERR_DELIMZONECOUNTERFAIL = "-1083";//删除失败
    public final static String ERR_QRYIMZONECOUNTERFAIL = "-1084";//查询失败

    //table:IMZoneLockerRight
    public final static String ERR_IMZONELOCKERRIGHTNODATA = "-1085";//记录不存在
    public final static String ERR_ADDIMZONELOCKERRIGHTFAIL = "-1086";//插入失败
    public final static String ERR_MODIMZONELOCKERRIGHTFAIL = "-1087";//修改失败
    public final static String ERR_DELIMZONELOCKERRIGHTFAIL = "-1088";//删除失败
    public final static String ERR_QRYIMZONELOCKERRIGHTFAIL = "-1089";//查询失败

    //table:MBBindMobileWater
    public final static String ERR_MBBINDMOBILEWATERNODATA = "-1090";//记录不存在
    public final static String ERR_ADDMBBINDMOBILEWATERFAIL = "-1091";//插入失败
    public final static String ERR_MODMBBINDMOBILEWATERFAIL = "-1092";//修改失败
    public final static String ERR_DELMBBINDMOBILEWATERFAIL = "-1093";//删除失败
    public final static String ERR_QRYMBBINDMOBILEWATERFAIL = "-1094";//查询失败

    //table:MBBoxStatusLog
    public final static String ERR_MBBOXSTATUSLOGNODATA = "-1095";//记录不存在
    public final static String ERR_ADDMBBOXSTATUSLOGFAIL = "-1096";//插入失败
    public final static String ERR_MODMBBOXSTATUSLOGFAIL = "-1097";//修改失败
    public final static String ERR_DELMBBOXSTATUSLOGFAIL = "-1098";//删除失败
    public final static String ERR_QRYMBBOXSTATUSLOGFAIL = "-1099";//查询失败

    //table:MBLockUserTime
    public final static String ERR_MBLOCKUSERTIMENODATA = "-1100";//记录不存在
    public final static String ERR_ADDMBLOCKUSERTIMEFAIL = "-1101";//插入失败
    public final static String ERR_MODMBLOCKUSERTIMEFAIL = "-1102";//修改失败
    public final static String ERR_DELMBLOCKUSERTIMEFAIL = "-1103";//删除失败
    public final static String ERR_QRYMBLOCKUSERTIMEFAIL = "-1104";//查询失败

    //table:MBMobileBlackList
    public final static String ERR_MBMOBILEBLACKLISTNODATA = "-1105";//记录不存在
    public final static String ERR_ADDMBMOBILEBLACKLISTFAIL = "-1106";//插入失败
    public final static String ERR_MODMBMOBILEBLACKLISTFAIL = "-1107";//修改失败
    public final static String ERR_DELMBMOBILEBLACKLISTFAIL = "-1108";//删除失败
    public final static String ERR_QRYMBMOBILEBLACKLISTFAIL = "-1109";//查询失败

    //table:MBMonitorPictureInfo
    public final static String ERR_MBMONITORPICTUREINFONODATA = "-1110";//记录不存在
    public final static String ERR_ADDMBMONITORPICTUREINFOFAIL = "-1111";//插入失败
    public final static String ERR_MODMBMONITORPICTUREINFOFAIL = "-1112";//修改失败
    public final static String ERR_DELMBMONITORPICTUREINFOFAIL = "-1113";//删除失败
    public final static String ERR_QRYMBMONITORPICTUREINFOFAIL = "-1114";//查询失败

    //table:MBPwdShortMsg
    public final static String ERR_MBPWDSHORTMSGNODATA = "-1115";//记录不存在
    public final static String ERR_ADDMBPWDSHORTMSGFAIL = "-1116";//插入失败
    public final static String ERR_MODMBPWDSHORTMSGFAIL = "-1117";//修改失败
    public final static String ERR_DELMBPWDSHORTMSGFAIL = "-1118";//删除失败
    public final static String ERR_QRYMBPWDSHORTMSGFAIL = "-1119";//查询失败

    //table:MBReminderWater
    public final static String ERR_MBREMINDERWATERNODATA = "-1120";//记录不存在
    public final static String ERR_ADDMBREMINDERWATERFAIL = "-1121";//插入失败
    public final static String ERR_MODMBREMINDERWATERFAIL = "-1122";//修改失败
    public final static String ERR_DELMBREMINDERWATERFAIL = "-1123";//删除失败
    public final static String ERR_QRYMBREMINDERWATERFAIL = "-1124";//查询失败

    //table:MBSignInfo
    public final static String ERR_MBSIGNINFONODATA = "-1125";//记录不存在
    public final static String ERR_ADDMBSIGNINFOFAIL = "-1126";//插入失败
    public final static String ERR_MODMBSIGNINFOFAIL = "-1127";//修改失败
    public final static String ERR_DELMBSIGNINFOFAIL = "-1128";//删除失败
    public final static String ERR_QRYMBSIGNINFOFAIL = "-1129";//查询失败

    //table:MBSmsStat
    public final static String ERR_MBSMSSTATNODATA = "-1130";//记录不存在
    public final static String ERR_ADDMBSMSSTATFAIL = "-1131";//插入失败
    public final static String ERR_MODMBSMSSTATFAIL = "-1132";//修改失败
    public final static String ERR_DELMBSMSSTATFAIL = "-1133";//删除失败
    public final static String ERR_QRYMBSMSSTATFAIL = "-1134";//查询失败

    //table:MBTerminalAlertLog
    public final static String ERR_MBTERMINALALERTLOGNODATA = "-1135";//记录不存在
    public final static String ERR_ADDMBTERMINALALERTLOGFAIL = "-1136";//插入失败
    public final static String ERR_MODMBTERMINALALERTLOGFAIL = "-1137";//修改失败
    public final static String ERR_DELMBTERMINALALERTLOGFAIL = "-1138";//删除失败
    public final static String ERR_QRYMBTERMINALALERTLOGFAIL = "-1139";//查询失败

    //table:MBTerminalStatusLog
    public final static String ERR_MBTERMINALSTATUSLOGNODATA = "-1140";//记录不存在
    public final static String ERR_ADDMBTERMINALSTATUSLOGFAIL = "-1141";//插入失败
    public final static String ERR_MODMBTERMINALSTATUSLOGFAIL = "-1142";//修改失败
    public final static String ERR_DELMBTERMINALSTATUSLOGFAIL = "-1143";//删除失败
    public final static String ERR_QRYMBTERMINALSTATUSLOGFAIL = "-1144";//查询失败

    //table:MBWrongPwdWater
    public final static String ERR_MBWRONGPWDWATERNODATA = "-1145";//记录不存在
    public final static String ERR_ADDMBWRONGPWDWATERFAIL = "-1146";//插入失败
    public final static String ERR_MODMBWRONGPWDWATERFAIL = "-1147";//修改失败
    public final static String ERR_DELMBWRONGPWDWATERFAIL = "-1148";//删除失败
    public final static String ERR_QRYMBWRONGPWDWATERFAIL = "-1149";//查询失败

    //table:OPFunction
    public final static String ERR_OPFUNCTIONNODATA = "-1150";//记录不存在
    public final static String ERR_ADDOPFUNCTIONFAIL = "-1151";//插入失败
    public final static String ERR_MODOPFUNCTIONFAIL = "-1152";//修改失败
    public final static String ERR_DELOPFUNCTIONFAIL = "-1153";//删除失败
    public final static String ERR_QRYOPFUNCTIONFAIL = "-1154";//查询失败

    //table:OPMenu
    public final static String ERR_OPMENUNODATA = "-1155";//记录不存在
    public final static String ERR_ADDOPMENUFAIL = "-1156";//插入失败
    public final static String ERR_MODOPMENUFAIL = "-1157";//修改失败
    public final static String ERR_DELOPMENUFAIL = "-1158";//删除失败
    public final static String ERR_QRYOPMENUFAIL = "-1159";//查询失败

    //table:OPOperAllLimit
    public final static String ERR_OPOPERALLLIMITNODATA = "-1160";//记录不存在
    public final static String ERR_ADDOPOPERALLLIMITFAIL = "-1161";//插入失败
    public final static String ERR_MODOPOPERALLLIMITFAIL = "-1162";//修改失败
    public final static String ERR_DELOPOPERALLLIMITFAIL = "-1163";//删除失败
    public final static String ERR_QRYOPOPERALLLIMITFAIL = "-1164";//查询失败

    //table:OPOperOnline
    public final static String ERR_OPOPERONLINENODATA = "-1165";//记录不存在
    public final static String ERR_ADDOPOPERONLINEFAIL = "-1166";//插入失败
    public final static String ERR_MODOPOPERONLINEFAIL = "-1167";//修改失败
    public final static String ERR_DELOPOPERONLINEFAIL = "-1168";//删除失败
    public final static String ERR_QRYOPOPERONLINEFAIL = "-1169";//查询失败

    //table:OPOperRole
    public final static String ERR_OPOPERROLENODATA = "-1170";//记录不存在
    public final static String ERR_ADDOPOPERROLEFAIL = "-1171";//插入失败
    public final static String ERR_MODOPOPERROLEFAIL = "-1172";//修改失败
    public final static String ERR_DELOPOPERROLEFAIL = "-1173";//删除失败
    public final static String ERR_QRYOPOPERROLEFAIL = "-1174";//查询失败

    //table:OPOperSpeRight
    public final static String ERR_OPOPERSPERIGHTNODATA = "-1175";//记录不存在
    public final static String ERR_ADDOPOPERSPERIGHTFAIL = "-1176";//插入失败
    public final static String ERR_MODOPOPERSPERIGHTFAIL = "-1177";//修改失败
    public final static String ERR_DELOPOPERSPERIGHTFAIL = "-1178";//删除失败
    public final static String ERR_QRYOPOPERSPERIGHTFAIL = "-1179";//查询失败

    //table:OPOperToMenu
    public final static String ERR_OPOPERTOMENUNODATA = "-1180";//记录不存在
    public final static String ERR_ADDOPOPERTOMENUFAIL = "-1181";//插入失败
    public final static String ERR_MODOPOPERTOMENUFAIL = "-1182";//修改失败
    public final static String ERR_DELOPOPERTOMENUFAIL = "-1183";//删除失败
    public final static String ERR_QRYOPOPERTOMENUFAIL = "-1184";//查询失败

    //table:OPOperator
    public final static String ERR_OPOPERATORNODATA = "-1185";//记录不存在
    public final static String ERR_ADDOPOPERATORFAIL = "-1186";//插入失败
    public final static String ERR_MODOPOPERATORFAIL = "-1187";//修改失败
    public final static String ERR_DELOPOPERATORFAIL = "-1188";//删除失败
    public final static String ERR_QRYOPOPERATORFAIL = "-1189";//查询失败

    //table:OPOperatorLog
    public final static String ERR_OPOPERATORLOGNODATA = "-1190";//记录不存在
    public final static String ERR_ADDOPOPERATORLOGFAIL = "-1191";//插入失败
    public final static String ERR_MODOPOPERATORLOGFAIL = "-1192";//修改失败
    public final static String ERR_DELOPOPERATORLOGFAIL = "-1193";//删除失败
    public final static String ERR_QRYOPOPERATORLOGFAIL = "-1194";//查询失败

    //table:OPRole
    public final static String ERR_OPROLENODATA = "-1195";//记录不存在
    public final static String ERR_ADDOPROLEFAIL = "-1196";//插入失败
    public final static String ERR_MODOPROLEFAIL = "-1197";//修改失败
    public final static String ERR_DELOPROLEFAIL = "-1198";//删除失败
    public final static String ERR_QRYOPROLEFAIL = "-1199";//查询失败

    //table:OPRoleAllLimit
    public final static String ERR_OPROLEALLLIMITNODATA = "-1200";//记录不存在
    public final static String ERR_ADDOPROLEALLLIMITFAIL = "-1201";//插入失败
    public final static String ERR_MODOPROLEALLLIMITFAIL = "-1202";//修改失败
    public final static String ERR_DELOPROLEALLLIMITFAIL = "-1203";//删除失败
    public final static String ERR_QRYOPROLEALLLIMITFAIL = "-1204";//查询失败

    //table:OPRoleSpeRight
    public final static String ERR_OPROLESPERIGHTNODATA = "-1205";//记录不存在
    public final static String ERR_ADDOPROLESPERIGHTFAIL = "-1206";//插入失败
    public final static String ERR_MODOPROLESPERIGHTFAIL = "-1207";//修改失败
    public final static String ERR_DELOPROLESPERIGHTFAIL = "-1208";//删除失败
    public final static String ERR_QRYOPROLESPERIGHTFAIL = "-1209";//查询失败

    //table:OPRoleToMenu
    public final static String ERR_OPROLETOMENUNODATA = "-1210";//记录不存在
    public final static String ERR_ADDOPROLETOMENUFAIL = "-1211";//插入失败
    public final static String ERR_MODOPROLETOMENUFAIL = "-1212";//修改失败
    public final static String ERR_DELOPROLETOMENUFAIL = "-1213";//删除失败
    public final static String ERR_QRYOPROLETOMENUFAIL = "-1214";//查询失败

    //table:OPSpecialPriv
    public final static String ERR_OPSPECIALPRIVNODATA = "-1215";//记录不存在
    public final static String ERR_ADDOPSPECIALPRIVFAIL = "-1216";//插入失败
    public final static String ERR_MODOPSPECIALPRIVFAIL = "-1217";//修改失败
    public final static String ERR_DELOPSPECIALPRIVFAIL = "-1218";//删除失败
    public final static String ERR_QRYOPSPECIALPRIVFAIL = "-1219";//查询失败

    //table:PAControlParam
    public final static String ERR_PACONTROLPARAMNODATA = "-1220";//记录不存在
    public final static String ERR_ADDPACONTROLPARAMFAIL = "-1221";//插入失败
    public final static String ERR_MODPACONTROLPARAMFAIL = "-1222";//修改失败
    public final static String ERR_DELPACONTROLPARAMFAIL = "-1223";//删除失败
    public final static String ERR_QRYPACONTROLPARAMFAIL = "-1224";//查询失败

    //table:PADictionary
    public final static String ERR_PADICTIONARYNODATA = "-1225";//记录不存在
    public final static String ERR_ADDPADICTIONARYFAIL = "-1226";//插入失败
    public final static String ERR_MODPADICTIONARYFAIL = "-1227";//修改失败
    public final static String ERR_DELPADICTIONARYFAIL = "-1228";//删除失败
    public final static String ERR_QRYPADICTIONARYFAIL = "-1229";//查询失败

    //table:PASequence
    public final static String ERR_PASEQUENCENODATA = "-1230";//记录不存在
    public final static String ERR_ADDPASEQUENCEFAIL = "-1231";//插入失败
    public final static String ERR_MODPASEQUENCEFAIL = "-1232";//修改失败
    public final static String ERR_DELPASEQUENCEFAIL = "-1233";//删除失败
    public final static String ERR_QRYPASEQUENCEFAIL = "-1234";//查询失败

    //table:PATerminalCtrlParam
    public final static String ERR_PATERMINALCTRLPARAMNODATA = "-1235";//记录不存在
    public final static String ERR_ADDPATERMINALCTRLPARAMFAIL = "-1236";//插入失败
    public final static String ERR_MODPATERMINALCTRLPARAMFAIL = "-1237";//修改失败
    public final static String ERR_DELPATERMINALCTRLPARAMFAIL = "-1238";//删除失败
    public final static String ERR_QRYPATERMINALCTRLPARAMFAIL = "-1239";//查询失败

    //table:PMPostman
    public final static String ERR_PMPOSTMANNODATA = "-1240";//记录不存在
    public final static String ERR_ADDPMPOSTMANFAIL = "-1241";//插入失败
    public final static String ERR_MODPMPOSTMANFAIL = "-1242";//修改失败
    public final static String ERR_DELPMPOSTMANFAIL = "-1243";//删除失败
    public final static String ERR_QRYPMPOSTMANFAIL = "-1244";//查询失败

    //table:PMPostmanLockerRight
    public final static String ERR_PMPOSTMANLOCKERRIGHTNODATA = "-1245";//记录不存在
    public final static String ERR_ADDPMPOSTMANLOCKERRIGHTFAIL = "-1246";//插入失败
    public final static String ERR_MODPMPOSTMANLOCKERRIGHTFAIL = "-1247";//修改失败
    public final static String ERR_DELPMPOSTMANLOCKERRIGHTFAIL = "-1248";//删除失败
    public final static String ERR_QRYPMPOSTMANLOCKERRIGHTFAIL = "-1249";//查询失败

    //table:PTDeliverHistory
    public final static String ERR_PTDELIVERHISTORYNODATA = "-1250";//记录不存在
    public final static String ERR_ADDPTDELIVERHISTORYFAIL = "-1251";//插入失败
    public final static String ERR_MODPTDELIVERHISTORYFAIL = "-1252";//修改失败
    public final static String ERR_DELPTDELIVERHISTORYFAIL = "-1253";//删除失败
    public final static String ERR_QRYPTDELIVERHISTORYFAIL = "-1254";//查询失败

    //table:PTDeliverItemTransfer
    public final static String ERR_PTDELIVERITEMTRANSFERNODATA = "-1255";//记录不存在
    public final static String ERR_ADDPTDELIVERITEMTRANSFERFAIL = "-1256";//插入失败
    public final static String ERR_MODPTDELIVERITEMTRANSFERFAIL = "-1257";//修改失败
    public final static String ERR_DELPTDELIVERITEMTRANSFERFAIL = "-1258";//删除失败
    public final static String ERR_QRYPTDELIVERITEMTRANSFERFAIL = "-1259";//查询失败

    //table:PTInBoxPackage
    public final static String ERR_PTINBOXPACKAGENODATA = "-1260";//记录不存在
    public final static String ERR_ADDPTINBOXPACKAGEFAIL = "-1261";//插入失败
    public final static String ERR_MODPTINBOXPACKAGEFAIL = "-1262";//修改失败
    public final static String ERR_DELPTINBOXPACKAGEFAIL = "-1263";//删除失败
    public final static String ERR_QRYPTINBOXPACKAGEFAIL = "-1264";//查询失败

    //table:PTItemLifeCycle
    public final static String ERR_PTITEMLIFECYCLENODATA = "-1265";//记录不存在
    public final static String ERR_ADDPTITEMLIFECYCLEFAIL = "-1266";//插入失败
    public final static String ERR_MODPTITEMLIFECYCLEFAIL = "-1267";//修改失败
    public final static String ERR_DELPTITEMLIFECYCLEFAIL = "-1268";//删除失败
    public final static String ERR_QRYPTITEMLIFECYCLEFAIL = "-1269";//查询失败

    //table:PTReadyPackage
    public final static String ERR_PTREADYPACKAGENODATA = "-1270";//记录不存在
    public final static String ERR_ADDPTREADYPACKAGEFAIL = "-1271";//插入失败
    public final static String ERR_MODPTREADYPACKAGEFAIL = "-1272";//修改失败
    public final static String ERR_DELPTREADYPACKAGEFAIL = "-1273";//删除失败
    public final static String ERR_QRYPTREADYPACKAGEFAIL = "-1274";//查询失败

    //table:PYServiceBillWater
    public final static String ERR_PYSERVICEBILLWATERNODATA = "-1275";//记录不存在
    public final static String ERR_ADDPYSERVICEBILLWATERFAIL = "-1276";//插入失败
    public final static String ERR_MODPYSERVICEBILLWATERFAIL = "-1277";//修改失败
    public final static String ERR_DELPYSERVICEBILLWATERFAIL = "-1278";//删除失败
    public final static String ERR_QRYPYSERVICEBILLWATERFAIL = "-1279";//查询失败

    //table:PYTopUpReq
    public final static String ERR_PYTOPUPREQNODATA = "-1280";//记录不存在
    public final static String ERR_ADDPYTOPUPREQFAIL = "-1281";//插入失败
    public final static String ERR_MODPYTOPUPREQFAIL = "-1282";//修改失败
    public final static String ERR_DELPYTOPUPREQFAIL = "-1283";//删除失败
    public final static String ERR_QRYPYTOPUPREQFAIL = "-1284";//查询失败

    //table:RMAppealLog
    public final static String ERR_RMAPPEALLOGNODATA = "-1285";//记录不存在
    public final static String ERR_ADDRMAPPEALLOGFAIL = "-1286";//插入失败
    public final static String ERR_MODRMAPPEALLOGFAIL = "-1287";//修改失败
    public final static String ERR_DELRMAPPEALLOGFAIL = "-1288";//删除失败
    public final static String ERR_QRYRMAPPEALLOGFAIL = "-1289";//查询失败

    //table:RMOpenBoxLog
    public final static String ERR_RMOPENBOXLOGNODATA = "-1290";//记录不存在
    public final static String ERR_ADDRMOPENBOXLOGFAIL = "-1291";//插入失败
    public final static String ERR_MODRMOPENBOXLOGFAIL = "-1292";//修改失败
    public final static String ERR_DELRMOPENBOXLOGFAIL = "-1293";//删除失败
    public final static String ERR_QRYRMOPENBOXLOGFAIL = "-1294";//查询失败

    //table:SCFtpLog
    public final static String ERR_SCFTPLOGNODATA = "-1295";//记录不存在
    public final static String ERR_ADDSCFTPLOGFAIL = "-1296";//插入失败
    public final static String ERR_MODSCFTPLOGFAIL = "-1297";//修改失败
    public final static String ERR_DELSCFTPLOGFAIL = "-1298";//删除失败
    public final static String ERR_QRYSCFTPLOGFAIL = "-1299";//查询失败

    //table:SCPushDataQueue
    public final static String ERR_SCPUSHDATAQUEUENODATA = "-1300";//记录不存在
    public final static String ERR_ADDSCPUSHDATAQUEUEFAIL = "-1301";//插入失败
    public final static String ERR_MODSCPUSHDATAQUEUEFAIL = "-1302";//修改失败
    public final static String ERR_DELSCPUSHDATAQUEUEFAIL = "-1303";//删除失败
    public final static String ERR_QRYSCPUSHDATAQUEUEFAIL = "-1304";//查询失败

    //table:SCSyncFailWater
    public final static String ERR_SCSYNCFAILWATERNODATA = "-1305";//记录不存在
    public final static String ERR_ADDSCSYNCFAILWATERFAIL = "-1306";//插入失败
    public final static String ERR_MODSCSYNCFAILWATERFAIL = "-1307";//修改失败
    public final static String ERR_DELSCSYNCFAILWATERFAIL = "-1308";//删除失败
    public final static String ERR_QRYSCSYNCFAILWATERFAIL = "-1309";//查询失败

    //table:SCTimestamp
    public final static String ERR_SCTIMESTAMPNODATA = "-1310";//记录不存在
    public final static String ERR_ADDSCTIMESTAMPFAIL = "-1311";//插入失败
    public final static String ERR_MODSCTIMESTAMPFAIL = "-1312";//修改失败
    public final static String ERR_DELSCTIMESTAMPFAIL = "-1313";//删除失败
    public final static String ERR_QRYSCTIMESTAMPFAIL = "-1314";//查询失败

    //table:SCUploadDataQueue
    public final static String ERR_SCUPLOADDATAQUEUENODATA = "-1315";//记录不存在
    public final static String ERR_ADDSCUPLOADDATAQUEUEFAIL = "-1316";//插入失败
    public final static String ERR_MODSCUPLOADDATAQUEUEFAIL = "-1317";//修改失败
    public final static String ERR_DELSCUPLOADDATAQUEUEFAIL = "-1318";//删除失败
    public final static String ERR_QRYSCUPLOADDATAQUEUEFAIL = "-1319";//查询失败

    //table:SMAdVideo
    public final static String ERR_SMADVIDEONODATA = "-1320";//记录不存在
    public final static String ERR_ADDSMADVIDEOFAIL = "-1321";//插入失败
    public final static String ERR_MODSMADVIDEOFAIL = "-1322";//修改失败
    public final static String ERR_DELSMADVIDEOFAIL = "-1323";//删除失败
    public final static String ERR_QRYSMADVIDEOFAIL = "-1324";//查询失败

    //table:SMSystemInfo
    public final static String ERR_SMSYSTEMINFONODATA = "-1325";//记录不存在
    public final static String ERR_ADDSMSYSTEMINFOFAIL = "-1326";//插入失败
    public final static String ERR_MODSMSYSTEMINFOFAIL = "-1327";//修改失败
    public final static String ERR_DELSMSYSTEMINFOFAIL = "-1328";//删除失败
    public final static String ERR_QRYSMSYSTEMINFOFAIL = "-1329";//查询失败

    //table:TBBoxType
    public final static String ERR_TBBOXTYPENODATA = "-1330";//记录不存在
    public final static String ERR_ADDTBBOXTYPEFAIL = "-1331";//插入失败
    public final static String ERR_MODTBBOXTYPEFAIL = "-1332";//修改失败
    public final static String ERR_DELTBBOXTYPEFAIL = "-1333";//删除失败
    public final static String ERR_QRYTBBOXTYPEFAIL = "-1334";//查询失败

    //table:TBServerBox
    public final static String ERR_TBSERVERBOXNODATA = "-1335";//记录不存在
    public final static String ERR_ADDTBSERVERBOXFAIL = "-1336";//插入失败
    public final static String ERR_MODTBSERVERBOXFAIL = "-1337";//修改失败
    public final static String ERR_DELTBSERVERBOXFAIL = "-1338";//删除失败
    public final static String ERR_QRYTBSERVERBOXFAIL = "-1339";//查询失败

    //table:TBServerDesk
    public final static String ERR_TBSERVERDESKNODATA = "-1340";//记录不存在
    public final static String ERR_ADDTBSERVERDESKFAIL = "-1341";//插入失败
    public final static String ERR_MODTBSERVERDESKFAIL = "-1342";//修改失败
    public final static String ERR_DELTBSERVERDESKFAIL = "-1343";//删除失败
    public final static String ERR_QRYTBSERVERDESKFAIL = "-1344";//查询失败

    //table:TBTerminal
    public final static String ERR_TBTERMINALNODATA = "-1345";//记录不存在
    public final static String ERR_ADDTBTERMINALFAIL = "-1346";//插入失败
    public final static String ERR_MODTBTERMINALFAIL = "-1347";//修改失败
    public final static String ERR_DELTBTERMINALFAIL = "-1348";//删除失败
    public final static String ERR_QRYTBTERMINALFAIL = "-1349";//查询失败

    //table:TBTerminalCounter
    public final static String ERR_TBTERMINALCOUNTERNODATA = "-1350";//记录不存在
    public final static String ERR_ADDTBTERMINALCOUNTERFAIL = "-1351";//插入失败
    public final static String ERR_MODTBTERMINALCOUNTERFAIL = "-1352";//修改失败
    public final static String ERR_DELTBTERMINALCOUNTERFAIL = "-1353";//删除失败
    public final static String ERR_QRYTBTERMINALCOUNTERFAIL = "-1354";//查询失败

    //table:PTTransferItemWater
    public final static String ERR_PTTRANSFERITEMWATERNODATA    = "-1355";//订单转移流水记录不存在
    public final static String ERR_ADDPTTRANSFERITEMWATERFAIL = "-1356";//插入订单转移流水失败
    public final static String ERR_MODPTTRANSFERITEMWATERFAIL = "-1357";//修改订单转移流水失败
    public final static String ERR_DELPTTRANSFERITEMWATERFAIL = "-1358";//删除订单转移流水失败
    public final static String ERR_QRYPTTRANSFERITEMWATERFAIL = "-1359";//查询订单转移流水失败
    
    //table:MBSendReportEmail
    public final static String ERR_MBSENDREPORTEMAILNODATA = "-1360"; //发送报告邮件管理记录不存在
    public final static String ERR_ADDMBSENDREPORTEMAILFAIL = "-1361"; //插入发送报告邮件管理失败
    public final static String ERR_MODMBSENDREPORTEMAILFAIL = "-1362"; //修改发送报告邮件管理失败
    public final static String ERR_DELMBSENDREPORTEMAILFAIL = "-1363"; //删除发送报告邮件管理失败
    public final static String ERR_QRYMBSENDREPORTEMAILFAIL = "-1364"; //查询发送报告邮件管理失败
    
    //table:MBTimeLimit
    public final static String ERR_MBTIMELIMITNODATA = "-1365"; //任务时间限制表记录不存在
    public final static String ERR_ADDMBTIMELIMITFAIL = "-1366"; //插入任务时间限制表失败
    public final static String ERR_MODMBTIMELIMITFAIL = "-1367"; //修改任务时间限制表失败
    public final static String ERR_DELMBTIMELIMITFAIL = "-1368"; //删除任务时间限制表失败
    public final static String ERR_QRYMBTIMELIMITFAIL = "-1369"; //查询任务时间限制表失败

    //table:PMTransporter
    public final static String ERR_PMTRANSPORTERNODATA = "-1370"; //包裹转运表记录不存在
    public final static String ERR_ADDPMTRANSPORTERFAIL = "-1371"; //插入包裹转运表失败
    public final static String ERR_MODPMTRANSPORTERFAIL = "-1372"; //修改包裹转运表失败
    public final static String ERR_DELPMTRANSPORTERFAIL = "-1373"; //删除包裹转运表失败
    public final static String ERR_QRYPMTRANSPORTERFAIL = "-1374"; //查询包裹转运表失败


    /////////////////////////////////////

    public final static String ERR_PADICTIONARYEXISTS = "-120005"; // 系统字典已经存在

    public final static String ERR_OPERNOLOGIN = "-130001"; // 系统超时，请重新登陆
    public final static String ERR_OPERHAVALOGIN = "-130002"; // 操作员已经登陆
    public final static String ERR_OPERNOOPENFUNC = "-130003"; // 此操作员没有开通此项功能
    public final static String ERR_OPERHAVEEXIST = "-130004"; // 用户已经存在
    public final static String ERR_OPERHAVECANCEL = "-130005"; // 操作员已注销
    public final static String ERR_OPERNOTREQOPEN = "-130006"; // 操作员状态正常不需要启用
    public final static String ERR_OPERROLEHAVEEXIST = "-130007"; // 操作员角色已存在
    public final static String ERR_OPERSTATUSABNORMAL = "-130008"; // 操作员状态不正常
    public final static String ERR_FORBIDMODSYSROLE = "-130009"; // 系统角色不能修改
    public final static String ERR_EMAILHAVEEXIST = "-130010"; // EMAIL已经存在
    public final static String ERR_OPERPWDWRONG = "-130011"; // 用户密码错误
    public final static String ERR_OVERCORPOPER = "-130012"; // 超过企业用户上限
    public final static String ERR_OVERDEPOPER = "-130013"; // 超过管理部门用户上限
    public final static String ERR_FORBIDADDSUPER = "-130014"; // 系统管理员已经存在不允许增加
    public final static String ERR_ROLENAMEEXIST = "-130015"; // 角色名称已存在
    public final static String ERR_OPERTYPEWRONG = "-130016"; // 操作员类别不正确
    public final static String ERR_FORBIDDELROLE = "-130017"; // 角色已经在使用不能删除
    public final static String ERR_OPOPERSPERIGHTEXISTS = "-130018"; // 操作员权限设置信息已经存在
    public final static String ERR_OPROLESPERIGHTEXISTS = "-130019"; // 角色权限设置信息已经存在
    public final static String ERR_FORBIDCORPEMPTY = "-130020"; // 用户所属的企业信息必须输入
    public final static String ERR_FORBIDDEPEMTPY = "-130021"; // 用户所属的部门信息必须输入
    public final static String ERR_FORBIDDELDEFAULTGROUP = "-130022"; // 默认组不允许删除
    public final static String ERR_FORBIDLOGIN = "-130023"; // 禁止登录
    public final static String ERR_INACTIVEFORBIDLOGIN = "-130024"; // 注册未激活不允许登录
    public final static String ERR_NOCHECKFORBIDLOGIN = "-130025"; // 注册未审核不允许登录
    public final static String ERR_CHECKNOTPASSFORBIDLOGIN = "-130026"; // 审核未通过不允许登录
    public final static String ERR_CONTACTHAVEEXIST = "-130027"; // 联系人信息已存在
    public final static String ERR_SYSGROUPNAMEFORBIDMOD = "-130028"; // 系统组名称不允许修改
    public final static String ERR_GROUPNAMEEXIST = "-130029"; // 组名称已经存在

    public final static String ERR_USERNOTEXIST = "-130030"; // 用户不存在
    public final static String ERR_EMAILNOTEXIST = "-130031"; // Email不存在
    public final static String ERR_USERNAMEEXISTS = "-130032"; // 用户名称已经存在

    public final static String ERR_WRONGPWDORUSERID = "-130033"; // 用户名或密码错误
    public final static String ERR_FORBIDDELOPERATOR = "-130034"; //用户已经在使用不能删除

    public final static String ERR_MODTERMINANO4REGISTER = "-150023"; // 注册未通过，请修改柜号
    public final static String ERR_NOTEXISTSBUREAU = "-150024"; // 投递局号不存在
    public final static String ERR_REGISTERGUOTONGFAILURE = "-150025"; // 向国通注册失败
    public final static String ERR_DEVICENETWORKABNORMAL = "-150033"; // 设备没在线
    
    //==21 柜体信息管理
    public final static String ERR_TERMINALREGISTERFAIL = "-210001"; // 柜体注册失败
    
    //==25 基础信息管理
    public final static String ERR_COMPANYEXISTS = "-250000"; // 服务商已经存在
    public final static String ERR_COMPANYNAMEEXISTS = "-250001"; // 服务商名称已经存在
    public final static String ERR_FORBIDDELCOMPANY = "-250002"; // 禁止删除服务商
    public final static String ERR_COMPANYNOTEXISTS = "-250005"; // 服务商不存在
    
    public final static String ERR_ZONEEXISTS = "-250010"; // 分拣区域中心已经存在
    public final static String ERR_ZONENAMEEXISTS = "-250011"; // 分拣区域中心名称已经存在
    public final static String ERR_FORBIDDELZONE_POSTMAN = "-250012"; // 禁止删除分拣区域中心
    public final static String ERR_FORBIDDELZONE_OPERATOR = "-250013"; // 禁止删除分拣区域中心
    public final static String ERR_ZONENOTEXISTS = "-250015"; // 分拣区域中心不存在

    public final static String ERR_PPCEXISTS = "-250020"; // 包裹处理中心已经存在
    public final static String ERR_PPCNAMEEXISTS = "-250021"; // 包裹处理中心名称已经存在
    public final static String ERR_FORBIDDELPPC = "-250022"; // 禁止删除包裹处理中心
    public final static String ERR_PPCNOTEXISTS = "-250025"; // 包裹处理中心不存在
    
    public final static String ERR_BUSINESSPARTNEREXISTS = "-250030"; // 商业合作伙伴已经存在
    public final static String ERR_BUSINESSPARTNERNAMEEXISTS = "-250031"; // 商业合作伙伴名称已经存在
    public final static String ERR_BUSINESSPARTNERNOTEXISTS  = "-250035"; // 商业合作伙伴不存在

    public final static String ERR_ECOMMERCEPARTNEREXISTS = "-250040"; // 电商合作伙伴已经存在
    public final static String ERR_ECOMMERCEPARTNERNAMEEXISTS = "-250041"; // 电商合作伙伴名称已经存在

    public final static String ERR_CUSTOMEREXISTS = "-250050"; // 个人客户已经存在
    public final static String ERR_CUSTOMERNAMEEXISTS = "-250051"; // 个人客户名称已经存在
    public final static String ERR_CUSTOMER_EXISTSBINDMOBILE = "-250052"; // 个人客户手机被绑定
    public final static String ERR_CUSTOMER_EXISTSBINDINGCARD = "-250053"; // 个人客户卡号被绑定
    public final static String ERR_POBOX_SERVICE_NOT_AVAILABLE = "-250054"; // 柜体POBox服务不可用
    public final static String ERR_CUSTOMERNOTEXISTS = "-250055"; // 个人客户不存在
    
    public final static String ERR_GATEWAYEXISTS = "-250060"; // 短信接口已经存在
    public final static String ERR_GATEWAYNAMEEXISTS = "-250061"; // 短信接口名称已经存在
    public final static String ERR_EMAILACCOUNTEXISTS = "-250062"; // 邮件账户已经存在
    public final static String ERR_EMAILACCOUNTNAMEEXISTS = "-250063"; // 邮件账户名称已经存在
    public final static String ERR_MSGTEMPLATEEXISTS = "-250064"; // 消息模板已经存在
    public final static String ERR_MSGTEMPLATENAMEEXISTS = "-250065"; // 消息模板名称已经存在
    
    public final static String ERR_SERVICEEXISTS = "-250070"; // 服务类型已经存在
    public final static String ERR_SERVICENAMEEXISTS = "-250071"; // 服务类型名称已经存在
    public final static String ERR_SERVICENOTEXISTS  = "-250075"; // 服务不存在
    public final static String ERR_SERVICENOTACTIVE  = "-250076"; // 服务未激活
    public final static String ERR_SERVICNOTALLOW    = "-250077"; // 不允许使用该服务
    
    public final static String ERR_COLLECTZONEEXISTS = "-250100"; // 揽件区域已经存在
    public final static String ERR_COLLECTZONENAMEEXISTS = "-250101"; // 揽件区域名称已经存在
    public final static String ERR_FORBIDDELCOLLECTZONE_POSTMAN = "-250102"; // 禁止删除揽件区域
    public final static String ERR_FORBIDDELCOLLECTZONE_PARTNER = "-250103"; // 禁止删除揽件区域
    public final static String ERR_COLLECTZONENOTEXISTS = "-250105"; // 揽件区域不存在
    
    public final static String ERR_COMPBOXRIGHTHAVEEXIST = "-251001"; // 服务商箱门权限已经存在
    public final static String ERR_COMPBOXRIGHTNOTEXIST = "-251002"; // 服务商无箱门权限
    
    public final static String ERR_ZONELOCKERRIGHTHAVEEXIST = "-251021"; // 分拣区域柜体权限已经存在
    public final static String ERR_ZONELOCKERRIGHTNOTEXIST = "-251022"; // 分拣区域无柜体权限

    public final static String ERR_MANDATORY_REPORT1_PRINT = "-252001";//R1强制报告打印
    public final static String ERR_MANDATORY_REPORT2_PRINT = "-252002";//R2强制报告打印
    public final static String ERR_MANDATORY_REPORT3_PRINT = "-252003";//R3强制报告打印
    public final static String ERR_MANDATORY_REPORT4_PRINT = "-252004";//R4强制报告打印
    public final static String ERR_MANDATORY_REPORT5_PRINT = "-252005";//R5强制报告打印
    public final static String ERR_MANDATORY_REPORT6_PRINT = "-252006";//R6强制报告打印
    public final static String ERR_MANDATORY_REPORT7_PRINT = "-252007";//R7强制报告打印
    public final static String ERR_MANDATORY_REPORT8_PRINT = "-252008";//R8强制报告打印
    
    //==31
    public final static String ERR_PACKAGEIDEXISTS = "-311001"; // 包裹单号已存在
    
    public final static String ERR_POSTMANEXISTS = "-310001"; // 投递员已存在
    public final static String ERR_FORBIDDELPOSTMAN = "-310003"; // 禁止删除投递员
    public final static String ERR_FORBIDOPERATORLOCK = "-310004"; // 无柜体操作权限
    public final static String ERR_POSTMANNOTEXISTS = "-310005"; // 投递员不存在
    public final static String ERR_POSTMANHAVECANCELED = "-310066"; // 投递员已经注销或未激活
    public final static String ERR_ZONEOFPOSTMANNOTEXISTS = "-310070"; // 投递员所属区域信息异常
    public final static String ERR_FORBIDPOSTMANDROP      = "-310071"; // 投递员无投递包裹权限
    public final static String ERR_FORBIDPOSTMANTAKEOUT   = "-310072"; // 投递员无取回包裹权限
    public final static String ERR_FORBIDPOSTMALOGIN      = "-310073"; // 投递员无柜体登录权限
    
    public final static String ERR_POSTMANLOCKERRIGHTHAVEEXIST = "-311011"; // 投递员柜体权限已经存在
    
    public final static String ERR_PACKHAVEDELIVERYD = "-310006"; // 包裹已经做过投递
    public final static String ERR_PACKAGENOTEXISTS = "-310008"; // 包裹不存在
    public final static String ERR_PACKAGENOTASSIGN = "-310009"; // 没有给包裹分配一个箱子
    public final static String ERR_NOFREEDBOX = "-310011"; // 未找到可用箱门
    public final static String ERR_BOXSIZEFAULT = "-310012"; //箱门尺寸错误
    public final static String ERR_PACKAGEHAVELOCKED = "-310013"; // 订单已经锁定
    public final static String ERR_PACKSTATUSABNORMAL = "-310015"; // 订单状态不正常

    public final static String ERR_BUSINESS_WRONGPACKAGID = "-310021"; //包裹单号或提货码错误
    public final static String ERR_BUSINESS_WRONGMOBILE = "-310022"; //手机号或提货码错误
    public final static String ERR_BUSINESS_WRONGCUSTOMERID = "-310023"; //用户卡号或提货码错误
    public final static String ERR_BUSINESS_WRONGPACKMOBILE = "-310024"; //包裹单号(手机号)或提货码错误

    public final static String ERR_BUSINESS_SENDSMSFAIL = "-310121"; //发送短消息失败
    public final static String ERR_BUSINESS_EXISTSBINDINGCARD = "-310151"; //卡号已经有人绑定
    public final static String ERR_BUSINESS_EXISTSBINDMOBILE  = "-310152"; //手机号已经有人绑定
    public final static String ERR_BUSINESS_MOBILEFORMATERROR = "-310153"; //手机格式错误
    public final static String ERR_BUSINESS_MOBILEBLACKLIST   = "-310154";//Mobile number in blacklist
    public final static String ERR_BUSINESS_INPUTMOBILEERROR  = "-310155"; //输入的手机格号与绑定的手机号不一致
    public final static String ERR_BUSINESS_BOUNDMOBILEERROR  = "-310156"; //绑定的手机号格式不正确，请修改绑定的手机号
    
    public final static String ERR_BUSINESS_SENDEMAILFAIL = "-310161"; // 发送邮件失败
    public final static String ERR_BUSINESS_EMAILFORMATERROR = "-310163"; // 邮件地址格式错误
    
    public final static String ERR_OPENBOXKEYEXISTS = "-310088"; //开箱密码重复
    
    //==33
    public final static String ERR_DELIVERY_FORBIDMODITEMSTATUS = "-330001";//不允许修改订单状态
    
    public final static String ERR_DELIVERY_DIRECTDROPPARAM     = "-330011";//直投订单参数异常
    public final static String ERR_DELIVERY_FORBIDDIRECTDROP    = "-330013";//不允许修改直投订单状态
    public final static String ERR_DELIVERY_BOXIDISUSERED = "-330021"; // 箱门编号已在用
    
    public final static String ERR_PARAM_PACKAGEIDCODE = "-330031"; // 批量订单编号格式错误
    
    public final static String ERR_OPERAT_EXISTSFORBIDITEM             = "-330101";//请重新选择订单
    public final static String ERR_OPERAT_FORBIDSUBMITMULTIITEMSTATUS  = "-330102";//请选择同一订单状态的订单
    public final static String ERR_OPERAT_FORBIDSUBMITMULTIZONE        = "-330103";//请选择所属同一AZC的订单
    public final static String ERR_OPERAT_FORBIDSUBMITMULTIPPC         = "-330104";//请选择所属同一PPC的订单
    public final static String ERR_OPERAT_FORBIDSUBMITMULTILOCKER      = "-330105";//请选择同一自提柜的订单
    public final static String ERR_OPERAT_SUBMITITEMNOTEXISTS          = "-330106";//提交了不存在的订单，请刷新
    public final static String ERR_OPERAT_SELECTPOSTMAN_BELONGZONE     = "-330107";//请选择所属分拣区域的投递员
    public final static String ERR_OPERAT_SELECTPOSTMAN_BELONGCOMPANY  = "-330108";//请选择所属服务商的投递员
    public final static String ERR_OPERAT_SELECTPOSTMAN_NO_DROPRIGHT   = "-330109";//请为投递员添加投递权限，或选择其他投递员
    public final static String ERR_OPERAT_FORBIDSUBMITMULTIPOSTMAN     = "-330110";//请选择同一投递员的订单
    
    public final static String ERR_OPERAT_FORBIDUSERLOCKER             = "-330121";//请选择其他自提柜
    
    public final static String ERR_OPERAT_ITEMBELONGOTDIFFERENTAZC     = "-330125";//包裹不属于当前AZC
    
    //==34
    public final static String ERR_FORBID_COLLECTION   = "-340001";//无寄件权限
    public final static String ERR_NOTRETURNSERVICE    = "-340005";//无退件服务
    public final static String ERR_BALANCE_NOT_ENOUGH  = "-340011";//账户余额不足
    public final static String ERR_BALANCE_UPDATE_FAIL = "-340012";//账户余额更新失败
    
    public final static String ERR_COLLECT_ITEM_NOT_EXISTS      = "-340021";//包裹单号不存在（未在系统下单）
    public final static String ERR_COLLECT_ITEMCODE_EXPIRED     = "-340022";//包裹单号已过期
    public final static String ERR_COLLECT_SERVICE_NOT_SELECTED = "-340023";//未选择上门取件服务
    public final static String ERR_COLLECT_NO_COLLECT_RIGHT     = "-340024";//无揽件权限
    public final static String ERR_COLLECT_FORBID_CANCEL        = "-340025";//不允许取消
    public final static String ERR_COLLECT_RETURNIEMTSONLYTRANSFER ="-340027";//退单只能发往PPC
    //==41
    public final static String ERR_UPTOP_TRADE_NOT_EXISTS = "-410001";//充值交易流水号不存在
    public final static String ERR_UPTOP_TRADE_TIMEOUT    = "-410002";//充值交易超时
    public final static String ERR_UPTOP_AMOUNT_UNEQUAL   = "-410003";//充值金额与请求充值金额不一致
    public final static String ERR_UPTOP_USERID_UNEQUAL   = "-410004";//充值用户与请求充值用户不一致
    public final static String ERR_UPTOP_TRADENO_INVALID  = "-410005";//交易流水号无效（重复充值）
    
    public final static String ERR_BUSINESS_REMOTEBOX_TIMEOUT = "-550001"; //远程求助已经超时
    public final static String ERR_BUSINESS_FORBIDREMOTEBOX = "-550002"; //订单不允许远程求助
    
    //手机注册
    public final static String ERR_MOBILE_SYSTME         = "-9000";//系统出错
    public final static String ERR_MOBILE_LOCKERNOTEXSISTS  = "-650001";//elocker不存在
    public final static String ERR_MOBILE_NOTALLOW       = "-650002";//不允许注册
    public final static String ERR_MOBILE_USERNAMEEMPTY  = "-650003";
    public final static String ERR_MOBILE_USERMOBILEEMPTY= "-650004";
    public final static String ERR_MOBILE_REGFAIL       = "-650005";
    public final static String ERR_MOBILE_RESCAN        = "-650006";//session 过期，请重新扫描二维码
    public final static String ERR_MOBILE_VCODE_EMPTY   = "-650007";//验证码不能为空
    public final static String ERR_MOBILE_MODFAIL       = "-650008";//更新失败
    public final static String ERR_MOBILE_SMSSUCCESS    = "-650009";//验证码发送成功，请查收
    public final static String ERR_MOBILE_SMSFAIL       = "-650010";//验证码发送失败
    public final static String ERR_MOBILE_VCODE_INVALID = "-650011";//验证码已失效
}
