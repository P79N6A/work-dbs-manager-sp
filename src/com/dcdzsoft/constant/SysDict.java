package com.dcdzsoft.constant;

/**
 * <p>Title: 智能自助包裹柜系统</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: 杭州东城电子有限公司</p>
 *
 * @author wangzl
 * @version 1.0
 */
public final class SysDict {
    public static final int CNST_DICT_MODULE = 11001; //系统模块

    public static final int CNST_DICT_OPERTYPE = 13001; //操作员类别
    public static final int CNST_DICT_OPERSTATUS = 13002; //操作员状态

    //管理员类型 13001,'用户类别','1','Super Admin'
    public final static String OPER_TYPE_SUPER = "1"; //超级管理员
    public final static String OPER_TYPE_MASTER = "2"; //运营方管理员
    public final static String OPER_TYPE_SPOT = "3"; //现场管理人员
    public final static String OPER_TYPE_OPERATOR = "4";//普通操作员
    //public final static String OPER_TYPE_POSTMAN = "99"; //投递员
    
    public final static String OPER_TYPE_CUSTOMER = "70";//individual customer');
    public final static String OPER_TYPE_BPARTNER = "71";//business partner');
    public final static String OPER_TYPE_EPATRNER = "72";//eCommerce partner');
    
    //31001,'邮递员类型','81','Postman'
    public final static String POSTMAN_TYPE_POST = "81"; //投递员
    public final static String POSTMAN_TYPE_DAD  = "82"; //直投员
    public final static String POSTMAN_TYPE_DAD_BUSINESSPARTNER  = "83"; //直投员 Businesspartner
    public final static String POSTMAN_TYPE_RETURN  = "84"; //回收专员
    public final static String POSTMAN_TYPE_TRANSFER  = "85"; //AZC之间转运邮递员
    public final static String POSTMAN_TYPE_DAD_SP  = "86"; //SP corporate customer
    
    //public final static String POSTMAN_TYPE_LEFT = "2"; //寄存客户
    //public final static String POSTMAN_TYPE_CLEAR = "3"; //生鲜配送员

    //13006 '登录用户类型'
    public final static String USER_TYPE_SUPER        = "1";//Super Admin
    public final static String USER_TYPE_ADMIN        = "2";//Admin 
    public final static String USER_TYPE_SERVER_OWNER = "3";//Service Owner
    public final static String USER_TYPE_BPARTNER = "4";//Business partner
    public final static String USER_TYPE_EPARTNER = "5";//eCommerce partner
    public final static String USER_TYPE_POSTMAN  = "6";//Postman
    public final static String USER_TYPE_CUSTOMER  = "7";//Individual customer
    
    //投递员身份地点
    public final static String POSTMAN_CHECKSOURCE_LOCAL = "0"; //本地
    public final static String POSTMAN_CHECKSOURCE_NETWORK = "1"; //网络

    //投递员身份验证方式
    public final static String POSTMAN_CHECKMODEL_DYNAMIC = "0"; //动态密码认证
    public final static String POSTMAN_CHECKMODEL_STATIC = "1"; //静态密码认证

    //投递员登陆认证标志
    public final static String POSTMAN_VERIFY_CODE = "0"; //动态验证码
    public final static String POSTMAN_VERIFY_PWD = "1"; //密码认证

    //投递员密码存储是否使用MD5加密
    public final static String POSTMAN_PWD_MD5_NO = "0"; //不使用
    public final static String POSTMAN_PWD_MD5_YES = "1"; //使用

    public final static String PASSWD_CHECKMODEL_COMMON = "0"; //普通MD5
    public final static String PASSWD_CHECKMODEL_JINGDONG = "1"; //京东MD5
    public final static String PASSWD_CHECKMODEL_HZYOUCHENG = "2"; //杭州邮政


    //投递员柜体权限验证模式
    public final static String POSTMAN_LOCKRIGHT_JUDGE_NO = "0"; //不需要
    public final static String POSTMAN_LOCKRIGHT_JUDGE_COMPANY = "1"; //验证公司
    public final static String POSTMAN_LOCKRIGHT_JUDGE_PERSON = "2"; //验证本人
    public final static String POSTMAN_LOCKRIGHT_JUDGE_AZC = "3"; //验证AZC

    //投递员格口权限验证模式
    public final static String POSTMAN_BOXRIGHT_JUDGE_NO = "0"; //不需要
    public final static String POSTMAN_BOXRIGHT_JUDGE_COMPANY = "1"; //验证公司
    public final static String POSTMAN_BOXRIGHT_JUDGE_PERSON = "2"; //验证本人

    //投递订单来源
    public final static String ORDER_DELIVERY_SOURCE_NO = "0"; //无
    public final static String ORDER_DELIVERY_SOURCE_NETWORK = "1"; //网络
    public final static String ORDER_DELIVERY_SOURCE_LOCAL = "2"; //本地

    //列表外订单处理方式
    public final static String ORDERS_NOTINLIST_NO = "0"; //不允许投递
    public final static String ORDERS_NOTINLIST_YES = "1"; //允许投递

    //订单需要网络验证
    public final static String ORDER_NEED_NETCHCK_NO = "0"; //不需要
    public final static String ORDER_NEED_NETCHCK_YES = "1"; //需要


    //订单号格式处理方式(扫描出特殊字符后,提示重刷(26个字符加数字加-,存储时候替换-为空)
    public final static String ORDER_FORMATWAY_COMMON = "0"; //普通
    public final static String ORDER_FORMATWAY_JINGDONG = "1"; //京东

    //回收订单来源
    public final static String RECOVERY_DELIVERY_SOURCE_NETWORK = "1"; //从服务器下载
    public final static String RECOVERY_DELIVERY_SOURCE_LOCAL = "2"; //从本地库验证

    //回收范围
    public final static String RECOVERY_DELIVERY_SCOPE_PERSON = "1"; //投递员
    public final static String RECOVERY_DELIVERY_SCOPE_COMPANY = "2"; //投递公司

    //寄存标志
    public final static String PACKAGE_LEFT_FLAG_N0 = "0"; //投递包裹
    public final static String PACKAGE_LEFT_FLAG_YES = "1"; //寄存包裹

    //支付标志
    public final static String PAY_FLAG_NO = "0"; //不需要支付
    public final static String PAY_FLAG_YES = "1"; //需要支付

    //取件密码生成来源
    public final static String TAKEOUTPWD_SOURCE_LOCAL_NO = "0"; //本地不生成
    public final static String TAKEOUTPWD_SOURCE_LOCAL_YES = "1"; //本地生成

    //取件密码组成格式
    public final static String TAKEOUTPWD_FORM_NUMBER = "0"; //数字
    public final static String TAKEOUTPWD_FORM_CHAR = "1"; //字母
    public final static String TAKEOUTPWD_FORM_NUMBERCHAR = "2"; //字母数字组合

    //取件验证模式
    public final static String TAKEOUTCHECK_MODEL_LOCAL = "1"; //本地网络
    public final static String TAKEOUTCHECK_MODEL_NETWORK = "2"; //网络验证

    //取件密码MD5加密类型
    public final static String TAKEOUTPWD_MD5TYPE_COMMON = "0"; //标准
    public final static String TAKEOUTPWD_MD5TYPE_JINGDONG = "1"; //京东


    //取件验证组合方式
    public final static String TAKEOUTCHECK_TYPE_PACKAGEID = "1"; //单号+密码
    public final static String TAKEOUTCHECK_TYPE_MOBILE = "2"; //手机号+密码
    public final static String TAKEOUTCHECK_TYPE_MOBILEAFTER4 = "3"; //手机号后4位+密码
    public final static String TAKEOUTCHECK_TYPE_PACKORMOBILE = "4"; //单号或手机号+密码
    public final static String TAKEOUTCHECK_TYPE_CARDID = "5"; //会员卡+提货码
    public final static String TAKEOUTCHECK_TYPE_4PACKID = "8"; //单号后4位+密码
    public final static String TAKEOUTCHECK_TYPE_4PACKORMOBILE = "9"; //单号后4位或手机号+密码


    //包裹状态
    public final static String PACKAGE_STATUS_NORMAL = "0"; //正常状态
    public final static String PACKAGE_STATUS_LOCKED = "1"; //锁定状态
    public final static String PACKAGE_STATUS_CANCEL = "2"; //取消状态(删除) 历史记录 ???
    public final static String PACKAGE_STATUS_TIMEOUT = "3"; //超时状态
    public final static String PACKAGE_STATUS_OUT4POSTMAN = "5"; //投递员取回
    public final static String PACKAGE_STATUS_OUTEXPIRED = "6"; //逾期回收
    public final static String PACKAGE_STATUS_OUTEXCEPTION = "7"; //异常回收
    public final static String PACKAGE_STATUS_OUTNORMAL = "8"; //正常取件
    public final static String PACKAGE_STATUS_OUT4MANAGER = "9"; //管理员取件
    
    //上传标志
    public final static String UPLOAD_FLAG_NO = "0"; //未上传
    public final static String UPLOAD_FLAG_YES = "1"; //已上传
    public final static String UPLOAD_FLAG_FAILURE = "2"; //上传失败


    //柜体状态
    public final static String TERMINAL_STATUS_NORMAL = "0"; //正常
    public final static String TERMINAL_STATUS_LOCKED = "1"; //锁定
    public final static String TERMINAL_STATUS_FAULT  = "2"; //故障 
    public final static String TERMINAL_STATUS_FAULT_OFFLINE   = "3"; //离线故障Off-line
    public final static String TERMINAL_STATUS_FAULT_POWERFAIL = "4"; //掉电故障Power-fail
    public final static String TERMINAL_STATUS_FAULT_BOX       = "5"; //存在箱门故障
    public final static String TERMINAL_STATUS_UNKNOWN = "9"; //未知，未注册

    //柜体注册标志
    public final static String TERMINAL_REGISTERFLAG_NO = "0"; //未注册
    public final static String TERMINAL_REGISTERFLAG_YES = "1"; //已注册
    public final static String TERMINAL_REGISTERFLAG_FAILURE = "2"; //注册失败

    //柜体类型
    public final static String TERMINAL_TYPE_LOCKER = "0";//0-elocker包裹柜子
    public final static String TERMINAL_TYPE_COUNTER = "1";//1-counter包裹柜台
    //箱体状态
    public final static String BOX_STATUS_NORMAL = "0"; //正常
    public final static String BOX_STATUS_LOCKED = "1"; //锁定
    public final static String BOX_STATUS_FAULT = "2"; //故障
    public final static String BOX_STATUS_FAULTLOCKED = "3"; //故障锁定
    public final static String BOX_STATUS_USED = "8"; //占用
    public final static String BOX_STATUS_UNKNOWN = "9"; //未知

    //箱锁定状态
    public final static String BOX_LOCKEDSTATUS_NO = "0"; //正常
    public final static String BOX_LOCKEDSTATUS_YES = "1"; //已锁定

    //箱故障状态
    public final static String BOX_FAULTSTATUS_NO = "0"; //正常
    public final static String BOX_FAULTSTATUS_YES = "1"; //故障

    //箱体使用状态
    public final static String BOX_USED_STATUS_FREE = "0"; //空闲
    public final static String BOX_USED_STATUS_SCHEDULE = "1"; //预定，Schedule
    public final static String BOX_USED_STATUS_OCCUPY = "2"; //占用 Occupy
    public final static String BOX_USED_STATUS_UNNORMAL_OCCUPY = "5"; //异常占用 Occupy
    //public final static String BOX_USED_STATUS_NORMAL = "1"; //订单正常
    //public final static String BOX_USED_STATUS_LOCKED = "2"; //订单锁定
    //public final static String BOX_USED_STATUS_LOCKED = "2"; //订单锁定
    //public final static String BOX_USED_STATUS_CANCEL = "3"; //订单取消
    //public final static String BOX_USED_STATUS_EXPIRED = "4"; //订单超时
    public final static String BOX_USED_STATUS_UNKNOWN = "9"; //未知

    //箱物品状态
    public final static String BOX_ARTICLE_STATUS_NON = "0"; //无
    public final static String BOX_ARTICLE_STATUS_EXIST = "1"; //有
    public final static String BOX_ARTICLE_STATUS_UNKNOWN = "9"; //未知

    //箱门状态
    public final static String BOX_DOOR_STATUS_CLOSE = "0"; //关
    public final static String BOX_DOOR_STATUS_OPEN = "1"; //开
    public final static String BOX_DOOR_STATUS_UNKNOWN = "9"; //未知

    //箱类型
    public final static String BOX_TYPE_SMALL = "0"; //小
    public final static String BOX_TYPE_MEDIAL = "1"; //中
    public final static String BOX_TYPE_BIG = "2"; //大
    public final static String BOX_TYPE_HUGE = "3"; //超大
    public final static String BOX_TYPE_FRESH = "7"; //生鲜
    public final static String BOX_TYPE_UNKNOWN = "9"; //未知

    public final static int STATUS_ABNORMAL = 0; //不正常
    public final static int STATUS_NORMAL = 1; //正常

    //外设需要标志
    public final static String DEVICE_NEED_FLAG_NO = "0"; //不需要
    public final static String DEVICE_NEED_FLAG_YES = "1"; //需要

    //需要同步数据标志
    public final static String SYNC_DATA_NEED_FLAG_NO = "0"; //不需要
    public final static String SYNC_DATA_NEED_FLAG_YES = "1"; //需要

    //本地远程操作标志
    public final static String OPER_FLAG_LOCAL = "LOCAL"; //本地操作
    public final static String OPER_FLAG_REMOTE = "REMOTE"; //远程操作

    public final static String ALERT_TYPE_SHOCK = "11"; //震动
    public final static String ALERT_TYPE_RESISTANT = "12"; //防撬
    public final static String ALERT_TYPE_POWERLOW = "13"; //电源不足
    public final static String ALERT_TYPE_POWERRECOVERY = "14"; //电源恢复Power recovery
    public final static String ALERT_TYPE_DRIVERERROR = "16"; //驱动板异常
    public final static String ALERT_TYPE_HIGHTEMP = "21"; //高温异常
    public final static String ALERT_TYPE_LOWTERMP = "23"; //低温异常
    public final static String ALERT_TYPE_DEVICEFAULT = "31"; //柜体故障
    public final static String ALERT_TYPE_CARDFAULT = "33"; //读卡器故障
    public final static String ALERT_TYPE_BARCODEFAULT = "34"; //条码扫描仪故障
    public final static String ALERT_TYPE_PRINTFAULT = "35"; //打印机故障
    public final static String ALERT_TYPE_BOXFAULT = "41"; //箱体故障
    public final static String ALERT_TYPE_BOXNOTCLOSED = "43"; //箱门长时间未关
    public final static String ALERT_TYPE_BOXREOPENING = "45"; //取件重新开箱
    public final static String ALERT_TYPE_NETWORKCLOSED = "91"; //网络异常
    public final static String ALERT_TYPE_TOOMANYHANDLES = "92"; //句柄过多
    public final static String ALERT_TYPE_OPENBOXFAIL = "95"; //大物卡箱 Open the box failure
    public final static String ALERT_TYPE_DROPOPENBOXFAIL = "96"; //投递开箱失败 Open the box failure

    public final static String ALERT_LEVEL_SLIGHT = "A"; //轻微
    public final static String ALERT_LEVEL_COMMON = "B"; //一般
    public final static String ALERT_LEVEL_URGENCY = "C"; //紧急
    public final static String ALERT_LEVEL_CRITICAL = "D"; //严重
    
    public final static String UPLOAD_LOG_TYPE_INBOX = "inbox";            //在箱信息
    public final static String UPLOAD_LOG_TYPE_INBOX2DB = "inbox2db";      //入库在箱信息
    public final static String UPLOAD_LOG_TYPE_HISTORY = "history";        //投递记录
    public final static String UPLOAD_LOG_TYPE_HISTORY2DB = "history2db";   //入库投递记录
    public final static String UPLOAD_LOG_TYPE_NOTMATCH = "detectresult";  //不一致结果
    public final static String UPLOAD_LOG_TYPE_SYSLOG = "syslog";          //系统日志
    public final static String UPLOAD_LOG_TYPE_OPERLOG = "operlog";         //管理员日志
    public final static String UPLOAD_LOG_TYPE_CTRLPARAM = "ctrlparam";     //控制参数
    public final static String UPLOAD_LOG_TYPE_FAILDATA = "faildata";       //失败上传数据
    public final static String UPLOAD_LOG_TYPE_CHECKACCOUNT = "checkacc";   //上传对账文件
    
    public final static String APPEAL_STATUS_REQUESTKEY = "1"; //申请开箱密码
    public final static String APPEAL_STATUS_READYOPEN = "3"; //准备开箱
    public final static String APPEAL_STATUS_OPENED = "5"; //开箱成功
     
    //订单转入标志
    public final static String INPUTMOBILE_FLAG_LOCAL = "0"; //寄件，需要输入手机
    public final static String INPUTMOBILE_FLAG_TRANSFER = "1"; //转入，手机回显不能修改
    public final static String INPUTMOBILE_FLAG_NETWORK = "2"; //网络获取，手机回显可以修改

    //主运营方标识
    public final static String MASTER_FLAG_NO  = "0";//Master标识 NO
    public final static String MASTER_FLAG_YES  = "1";//Master标识 YES
    
    //投递订单状态
    public final static String ITEM_STATUS_DROP_LISTED = "0";//Listed
    public final static String ITEM_STATUS_DROP_RECEIVED = "1";//Received
    public final static String ITEM_STATUS_DROP_ASSIGNED = "2";//Assigned
    public final static String ITEM_STATUS_DROP_SCHEDULED = "3";//Scheduled
    public final static String ITEM_STATUS_DROP_INTRANSIT_OUT = "4";//Intransit-out
    public final static String ITEM_STATUS_DROP_DROPPED = "5";//Dropped
    public final static String ITEM_STATUS_DROP_D_DROPPED = "6";//D-Dropped
    public final static String ITEM_STATUS_DROP_TAKEOUT = "7";//TakeOut
    public final static String ITEM_STATUS_DROP_EXPIRED = "8";//Expired
    public final static String ITEM_STATUS_DROP_M_EXPIRED = "9";//M-Expired
    public final static String ITEM_STATUS_DROP_INTRANSIT_BACK = "10";//Intransit-back
    public final static String ITEM_STATUS_DROP_RETURNED = "11";//Returned
    public final static String ITEM_STATUS_DROP_MISSING = "12";//Missing
    public final static String ITEM_STATUS_DROP_TRANSFER = "13";//Transfer;
    public final static String ITEM_STATUS_DROP_LOSE     = "14";//Parcel Lose
    public final static String ITEM_STATUS_DROP_TRANSFERLIST = "15";//TransferList
    public final static String ITEM_STATUS_DROP_PACKAGE_LISTED = "16";//Package-listed
    public final static String ITEM_STATUS_DROP_PACKAGE_OUT = "17";//Package-out
    
    public final static String ITEM_STATUS_DROP_D_CREATE = "20";//DAD create
    
    //寄件订单状态
    public final static String ITEM_STATUS_COLLECT_CREATED = "50"; //Created
    public final static String ITEM_STATUS_COLLECT_TOBECOLLECTED = "51"; //To Be Collected
    //public final static String ITEM_STATUS_COLLECT_SCHEDULED = "52"; //Scheduled
    public final static String ITEM_STATUS_COLLECT_INTRANSIT_COLLECTED = "53"; //Intransit-Collected
    public final static String ITEM_STATUS_COLLECT_INWARD_RECEIVED = "54"; //Inward-Received
    public final static String ITEM_STATUS_COLLECT_TRANSFER = "55"; //Transfer
    public final static String ITEM_STATUS_COLLECT_TOBE_DEOPPED = "56";//To Be Dropped
    public final static String ITEM_STATUS_COLLECT_TOBE_D_DEOPPED = "57";//To Be D-Dropped
    public final static String ITEM_STATUS_COLLECT_CANCELLED = "58";//Cancelled
    //public final static String ITEM_STATUS_COLLECT_RETURNED  = "59";//Returned
    public final static String ITEM_STATUS_COLLECT_DELIVERED   = "60";//Delivered for Collection 揽件部门回收
    
    //订单执行状态
    public final static String ITEM_RUN_STATUS_RUNNING = "0";//正在执行
    public final static String ITEM_RUN_STATUS_TAKEOUT_CUSOTMER = "1";//用户正常取件
    public final static String ITEM_RUN_STATUS_TAKEOUT_DADBACK  = "2";//直投包裹回收
    public final static String ITEM_RUN_STATUS_TRANSFER_ERR_INFO = "3";//异常信息退单
    public final static String ITEM_RUN_STATUS_TRANSFER_EXPIRED  = "4";//逾期未取退单
    public final static String ITEM_RUN_STATUS_RE_DISTRIBUTE     = "5";//逾期未取重投递
    public final static String ITEM_RUN_STATUS_PARCEL_MISSING    = "6";//包裹丢失
    public final static String ITEM_RUN_STATUS_TAKEOUT_MANAGER = "9"; //管理员取件
    //强制打印开关
    public final static String MANDATORY_REPORT_PRINT_NO  = "0";//不需打印
    public final static String MANDATORY_REPORT_PRINT_YES = "1";//强制打印
    
    public final static int MANDATORY_REPORT1_CODE        = 1;
    public final static int MANDATORY_REPORT2_CODE        = 2;
    public final static int MANDATORY_REPORT3_CODE        = 4;
    public final static int MANDATORY_REPORT4_CODE        = 8;
    public final static int MANDATORY_REPORT5_CODE        = 16;
    public final static int MANDATORY_REPORT6_CODE        = 32;
    public final static int MANDATORY_REPORT7_CODE        = 64;
    public final static int MANDATORY_REPORT8_CODE        = 128;
    
    //报告打印类型                                                
    public final static String PRINT_REPORT_TYPE_1 = "1";//Report1  OrderID     
    public final static String PRINT_REPORT_TYPE_2 = "2";//Report2  OrderID
    public final static String PRINT_REPORT_TYPE_3 = "3";//Report3  OrderID     
    public final static String PRINT_REPORT_TYPE_4 = "4";//Report4  OrderID     
    public final static String PRINT_REPORT_TYPE_5 = "5";//Report5  OrderID     
    public final static String PRINT_REPORT_TYPE_6 = "6";//Report6  OrderID     
    public final static String PRINT_REPORT_TYPE_7 = "7";//Report7  OrderID     
    public final static String PRINT_REPORT_TYPE_8 = "8";//Report8  OrderID
    //
    //订单编号类型                                                
    public final static String ORDER_ID_TYPE_RECEIVED     = "1";//ReceiveOrderID     
    public final static String ORDER_ID_TYPE_TRANSFER     = "2";//TransferOrderID
    public final static String ORDER_ID_TYPE_REDISTRIBUTE = "3";//ReDisttibuteOrderID     
    public final static String ORDER_ID_TYPE_RETURN       = "4";//ReturnOrderID     
    public final static String ORDER_ID_TYPE_DROP         = "5";//DropOrderID   
    public final static String ORDER_ID_TYPE_D_DROP       = "6";//DirectDropOrderID      
    public final static String ORDER_ID_TYPE_COLLECTION   = "7";//CollectionOrderID     
    public final static String ORDER_ID_TYPE_MISSING      = "8";//MissingOrderID 
    public final static String ORDER_ID_TYPE_LOSE         = "9";//LoseOrderID 
    //AZC Counter
    public final static String COUNTER_TYPE_AZC_RECEIVED     = "1";//收件计数器,RECounter
    public final static String COUNTER_TYPE_AZC_TRANSFER     = "2";//运单计数器,TRCounter
    public final static String COUNTER_TYPE_AZC_REDISTRIBUTE = "3";//重投计数器,RDCounter
    public final static String COUNTER_TYPE_AZC_COLLECTION   = "4";//揽件回收计数器,COCounter
    public final static String COUNTER_TYPE_AZC_MISSING      = "5";//丢失计数器,MICounter
    public final static String COUNTER_TYPE_AZC_LOSE         = "6";//确认丢失计数器,LOCounter
    
    //Locker Counter
    public final static String COUNTER_TYPE_LOCKER_DROP      = "21";//投递计数器,DOCounter
    public final static String COUNTER_TYPE_LOCKER_D_DROP    = "22";//直投计数器,DODCounter
    public final static String COUNTER_TYPE_LOCKER_RETURNED  = "23";//回收计数器,ROCounter
    public final static String COUNTER_TYPE_LOCKER_CUSTOMER  = "24";//customer数器,CUCounter
    //public final static String COUNTER_TYPE_LOCKER_POSCODECOUNTER = "23";//POSCODECOUNTER
    
    //ServiceMaster Counter
    public final static String COUNTER_TYPE_SERVICE_MASTER   = "41";//ServiceMaster Counter
    
    //邮递员权限标识
    public final static String POSTMAN_RIGHT_NO  = "0";//未分配权限
    public final static String POSTMAN_RIGHT_YES = "1";//已分配权限
    
    //直投标识
    public final static String DIRECT_DROP_FLAG_NO  = "0";//非直投
    public final static String DIRECT_DROP_FLAG_YES = "1";//直投订单
    
    //揽件服务标识
    public final static String SERVICE_COLLECTION_FLAG_NO  = "0";//NO
    public final static String SERVICE_COLLECTION_FLAG_YES = "1";//YES
    
    //商业伙伴授信标识
    public final static String BUSINESS_CREDIT_FLAG_NO  = "0";//NO
    public final static String BUSINESS_CREDIT_FLAG_YES = "1";//YES

    //账单类型
    public final static String BILL_TYPE_TOPUP    = "1";//充值账单
    public final static String BILL_TYPE_CONSUME  = "2";//消费账单
    public final static String BILL_TYPE_PREPAY   = "3";//预付账单
    
    //充值请求状态
    public final static String TOPUP_STATUS_DOING   = "1";//充值中
    public final static String TOPUP_STATUS_SUCCESS = "2";//充值成功
    public final static String TOPUP_STATUS_TIMEOUT = "3";//超时
    
    //激活标识
    public final static String ACTIVE_FLAG_NO    = "0";//未激活
    public final static String ACTIVE_FLAG_YES  = "1";//已激活
    
    //特殊权限Special Access
    public final static int SPECIAL_ACCESS_DATA_QUERY_MASTER  = 1;//主运营方数据访问权限
    public final static int SPECIAL_ACCESS_DATA_QUERY_COMPANY = 2;//服务商数据访问权限
    public final static int SPEPRIV_OPENBOX = 3; //远程开箱
    public final static int SPEPRIV_MODWELCOMEINFO = 4; //修改设备端欢迎词
    
    //15042,'监控照片类型(投递取件)'
    public final static String MONITOR_PICTURE_TYPE_DROP     = "1";/*投递员投递*/
    public final static String MONITOR_PICTURE_TYPE_CUSTOMER = "2";/*用户取件*/
    public final static String MONITOR_PICTURE_TYPE_POSTMAN  = "3";/*投递员逾期取件*/
    public final static String MONITOR_PICTURE_TYPE_MANANER  = "4";/*管理员取件*/

    //33074,'转移订单类型'
    public final static String TRANSFER_TYPE_ANNOUNCING          = "0";/*分拣投递中,上传更新订单状态*/
    public final static String TRANSFER_TYPE_TAKEOUT_BY_CUSTOMER = "1";/*用户正常取件*/
    public final static String TRANSFER_TYPE_PARCEL_NOT_RECEIVED = "2";/*未收到包裹*/
    public final static String TRANSFER_TYPE_ERROR_INFO          = "3";/*异常信息退单*/
    public final static String TRANSFER_TYPE_EXPIRED             = "4";/*逾期未取退单*/
    public final static String TRANSFER_TYPE_M_EXPIRED           = "7";/*逾期未取退单(人工)*/
    public final static String TRANSFER_TYPE_TAKEOUT_BY_MANAGER  = "5";/*manager取件*/
    public final static String TRANSFER_TYPE_MISSING             = "6";/*包裹丢失退单*/
    public final static String TRANSFER_TYPE_SEND_MAIL           = "8";/*寄件订单*/
    public final static String TRANSFER_TYPE_MAIL_RETURNED       = "9";/*寄件退单*/
    public final static String TRANSFER_TYPE_DROPPED             = "10";/*投递到箱*/
    public final static String TRANSFER_TYPE_D_DROPPED           = "11";/*投递到箱(直投)*/
    public final static String TRANSFER_TYPE_TAKEOUT_BY_DAD      = "12";/*直投取件*/
    public final static String TRANSFER_TYPE_ADD_ITEM            = "13";/*AddItem*/
    
    public final static String TRANSFER_TYPE_SUBMIT_ITEM         = "51";/*寄件数据上传*/
    
    //33076,'转移订单发送状态'
    public final static String TRANSFER_STATUS_UNSENT  = "0";/*未发送*/
    public final static String TRANSFER_STATUS_SENTING = "1";/*发送中*/
    public final static String TRANSFER_STATUS_SUCCESS = "2";/*发送成功*/
    public final static String TRANSFER_STATUS_FAILURE = "4";/*发送失败*/
    
    //25015 个人客户状态
    public final static String CUSTOMER_STATUS_INACTIVE  = "0";/*注册未激活:Inactive*/
    public final static String CUSTOMER_STATUS_NORMAL    = "1";/*正常:Normal*/
    public final static String CUSTOMER_STATUS_INVALID   = "2";/*失效:Invalid*/
    
    
    //25051,'网关类型'
    public final static String GATEWAY_TYPE_SMS   = "51";/*SMSGateway*/
    public final static String GATEWAY_TYPE_EMAIL = "52";/*SupportEmail*/
    
    //25052,消息模板类型
    public final static String MSG_TEMPLATE_TAKEOUTPWD  = "1";//TakeoutPwdMsg
    public final static String MSG_TEMPLATE_RETURNED    = "2";//ReturnedMsg
    public final static String MSG_TEMPLATE_REMINDER    = "3";//ReminderMsg
    public final static String MSG_TEMPLATE_PICKOUT     = "4";//PickupMsg
    public final static String MSG_TEMPLATE_RESENT      = "5";//重发投递短信
    public final static String MSG_TEMPLATE_POSTMANPWD  = "8";//PostmanPwdMsg
    public final static String MSG_TEMPLATE_SENDURGENT  = "9";//紧急取件消息
    public final static String MSG_TEMPLATE_REGISTERPWD = "10";//RegisterPwdMsg
    
    //API接入的用户类型
    public final static String APP_USER_TYPE_EXT_SYSTEM       = "0";//elocker system 之外允许更新数据的外部系统external 
    public final static String APP_USER_TYPE_PPC_SERVER       = "1";//PPC服务器
    public final static String APP_USER_TYPE_ECOMMERCE_WEBSIT = "2";//电商伙伴网站，查询柜体信息
    public final static String APP_USER_TYPE_POS_SYSTEM       = "3";//POS系统网站，BP充值
    public final static String APP_USER_TYPE_APP_POSTMAN      = "4";//APP for POSTMAN 揽件
    public final static String APP_USER_TYPE_BUSINESS_WEBSIT  = "5";//商业伙伴网站，创建寄件订单
    
    //API接入的用户类型名称，不允许相同
    public final static String APP_USER_TYPE_EXT_SYSTEMNAME        = "UserKey for external system";//elocker system 之外允许更新数据的外部系统
    public final static String APP_USER_TYPE_PPC_SERVER_NAME       = "UserKey for ppc server";//PPC服务器
    public final static String APP_USER_TYPE_ECOMMERCE_WEBSIT_NAME = "UserKey for ecommerce partner website";//电商伙伴网站，查询柜体信息
    public final static String APP_USER_TYPE_POS_SYSTEM_NAME       = "UserKey for POS system";//POS系统网站，BP充值
    public final static String APP_USER_TYPE_APP_POSTMAN_NAME      = "UserKey for collection app";//APP for POSTMAN 揽件
    public final static String APP_USER_TYPE_BUSINESS_WEBSIT_NAME  = "UserKey for business partner website/app";//商业伙伴网站，创建寄件订单
    //发送邮件相关
    public final static String EMAIL_SENDASRIGHTS_OWNERAZC = "0";//发送权限：管理员所在 AZC
    public final static String EMAIL_SENDASRIGHTS_ALLAZC   = "1";//所有AZC
    public final static String EMAIL_TYPE_BOXUSED          = "0";//邮件类型：柜体使用统计邮件
    public final static String EMAIL_TYPE_OFFLINEALARM     = "1";//离线报警邮件
    //柜体类型
    public final static String TERMINALTYPE_PACKAGE = "0";  //包裹柜
    public final static String TERMINALTYPE_COUNTER = "1";  //投递柜台
    public final static String TERMINALTYPE_ONETOMANY = "2";//一箱多单柜
    
}
