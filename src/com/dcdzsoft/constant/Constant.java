package com.dcdzsoft.constant;

import java.text.SimpleDateFormat;

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
public final class Constant {
	public static final SimpleDateFormat dateFromat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat dateFromatSa = new SimpleDateFormat("dd/MM/yyyy");
	public static final SimpleDateFormat dateFromat1 = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat dateFromat1Sa = new SimpleDateFormat("dd/MM/yyyy");
	public static final SimpleDateFormat timeFromat1 = new SimpleDateFormat("HH:mm:ss");
	/**
     * 长字符串最大长度
     */
    public static final int LONGSTR_LEN = 40960; //40K

    public static final int CONTENT_MAX_LEN = 3900;

    /**
     * Operator
     */
    public static final String DEFAULT_SUPEROPERID = "181818"; //超级管理员编号
    public static final String DEFAULT_MASTER = "Master"; //超级管理员编号
    public static final String DEFAULT_QUERYOPERID = "!@#4rfv*&^()"; //内部查询用

    public static final String DEFAULT_HEAD_DEPARTMENTID = "100000"; //

    /**
     * 系统运行模式
     */
    public static final int SYS_RUN_MODEL_FORWARD = 1; //转发
    public static final int SYS_RUN_MODEL_OPERATE = 2; //运营
    public static final int SYS_RUN_MODEL_MOBILE = 3; //运维


    /**
     * Operator type
     */
   public static final int OPER_TYPE_SYS = 1; //系统管理员

    /**
     * login Type
     */
    public static final String LOGIN_TYPE_INNER = "10";
    public static final String LOGIN_TYPE_HOTMAIL = "20";
    public static final String LOGIN_TYPE_FACEBOOK = "30";
    public static final String LOGIN_TYPE_GMAIL = "40";
    public static final String LOGIN_TYPE_YAHOO = "50";


    /**
     * ModuleID
     */
    public static final String MODULE_ID_OP = "13"; //操作员管理

    /**
     * 角色
     */
    public static final int ROLE_TYPE_COMMON = 1; //普通用户角色
    public static final int ROLE_TYPE_VIP = 2; //VIP用户角色
    public static final int ROLE_TYPE_SYS = 9; //管理部门角色



    /**
     * 用户状态
     */
    public static final String USER_STATUS_NORMAL = "0"; //正常
    public static final String USER_STATUS_LOGOUT = "1"; //注销(审核未通过)
    public static final String USER_STATUS_NOTACTIVATION = "2"; //注册未激活

    /**
     * 企业状态
     */
    public static final String CORP_STATUS_NORMAL = "0"; //正常
    public static final String CORP_STATUS_LOGOUT = "1"; //注销
    public static final String CORP_STATUS_NOTCHECED = "2"; //注册未审核
    public static final String CORP_STATUS_NOTPASSED = "3"; //审核未通过

    /**
     * 企业类型
     */
    public static final String CORP_TYPE_INNTERNET = "0"; //公网注册
    public static final String CORP_TYPE_INNER = "1"; //内部注册

    /**
     * 资料完善申请状态
     */
    public static final String IMPROVE_INFO_STATUS_FIRST = "1"; //初次申请
    public static final String IMPROVE_INFO_STATUS_SECOND = "2"; //二次申请

    /**
     * 审核状态
     */
    public static final String USER_CHECKFLAG_FAIL = "-1"; //审核未通过
    public static final String USER_CHECKFLAG_NOCHECK = "0"; //未审核
    public static final String USER_CHECKFLAG_PASS = "1"; //审核通过
    public static final String USER_CHECKFLAG_V = "2"; //加V标志




    /**
     * 显示标志
     */
    public static final String SHOW_FLAG_NO = "0"; //NO
    public static final String SHOW_FLAG_YES = "1"; //YES

    /**
     * 关注标志
     */
    public static final String CONCERN_FLAG_NO = "0"; //NO
    public static final String CONCERN_FLAG_YES = "1"; //YES

    /**
     * 好友组标识
     */
    public static final String FRIEND_GROUP_FLAG_COMMON = "0"; //common
    public static final String FRIEND_GROUP_FLAG_DEFAULT = "1"; //friend
    public static final String FRIEND_GROUP_FLAG_BLACK = "9"; //black


    public static final String TRANSFER_FLAG_UPLOAD = "1";//1-transfer item from azc to ppc
    public static final String TRANSFER_FLAG_UPDATE = "2";//2-update item status (from azc to ppc)
    /**
     * 记录日志标志
     */
    public static final String LOG_FLAG_NO = "0"; //不记录
    public static final String LOG_FLAG_MANUAL = "1"; //手动
    public static final String LOG_FLAG_AUTOMATIC = "5"; //自动
    public static final String LOG_FLAG_PAUSE = "6"; //暂停

    public static final String COMPANY_YOUZHENG_ID = "0001";
    
    public static final String COMPANY_SAUDIPOST_ID   = "01";//沙特邮政
    public static final String COMPANY_START_ID   = "02";
    public static final String COMPANY_MAX_ID     = "999";
    public static final String ZONE_VIRTUAL_ID    = "000";//用于构建虚拟分拣区域编号
    public static final String ZONE_START_ID      = "001";
    public static final String ZONE_MAX_ID        = "999";
    public static final String DEFAULT_PPC_ID     = "01001";//默认PPCID,Master PPC Server
    
    public static final int TRANSFER_TO_PPC_SEND_TIMES_MAX = 3;//最多自动发送的次数
    public static final int TRANSFER_TO_PPC_ITEM_RETAIN_DAYS = 7;//发送队列中（from azc to ppc）订单保留时间，超过时间订单从发送队列中删除
    //
    public static final String TERMINAL_UPLADETYPE_PICKUPPIC = "pickupPic";//取件人照片
    
    public static final int MAX_NUM_OF_CUSTOMER_PER_LOCKER = 999999;//LOCKER.Customer.Counter的最大值
    
    public static final int LOCKER_OFFLINE_DELAY_MINUTES  = 10;//柜体离线延迟通知时间（分钟）
    public static final long MIN_SMS_SEND_INTERVAL_MS=(1000*60*5);//多条短信发送到同一手机的最短时间间隔：5分钟
    
    //Action code
    public static final int ACTION_CODE_ADDITEM_API      = 0;//
    public static final int ACTION_CODE_ADDITEM          = 1;//
    public static final int ACTION_CODE_RECEIVE          = 2;//
    public static final int ACTION_CODE_ASSIGN           = 3;
    public static final int ACTION_CODE_UNASSIGN         = 4;
    public static final int ACTION_CODE_ADD2ORDER        = 5;
    public static final int ACTION_CODE_DEL4ORDER        = 6;
    public static final int ACTION_CODE_EXEORDER         = 7;
    public static final int ACTION_CODE_DROP             = 8;
    public static final int ACTION_CODE_D_DROP           = 9;
    public static final int ACTION_CODE_FORCEEXPIRE      = 10;
    public static final int ACTION_CODE_PICKUPBYCUSTOMER = 11;
    public static final int ACTION_CODE_PICKUPBYPOSTMAN  = 12;
    public static final int ACTION_CODE_PICKUPBYMANAGER  = 13;
    public static final int ACTION_CODE_MISSING          = 14;
    public static final int ACTION_CODE_RETRIEVE         = 15;
    public static final int ACTION_CODE_RETURN           = 16;
    public static final int ACTION_CODE_LOST             = 18;
    public static final int ACTION_CODE_REDISTRIBUTE     = 19;
    public static final int ACTION_CODE_TRANSFER         = 20;
    
    public static String getAction(int actionCode, String[] arg){
        
        String actionDesc = "";
        String actionName = "";
        switch(actionCode){
        case ACTION_CODE_ADDITEM_API:
        case ACTION_CODE_ADDITEM://"AddItem("+PPCName+"=>"+ZoneName+")"
            actionName = "AddItem";
            actionDesc = "XX0=>XX1";
            break;
        case ACTION_CODE_RECEIVE://"Receive("+PPCName+"=>"+ZoneName+")"
            actionName = "Receive";
            actionDesc = "XX0=>XX1";
            break;
        case ACTION_CODE_ASSIGN://"Assign("+TerminalNo+"-"+BoxType+"-"+"BoxNo)"
            actionName = "Assign";
            actionDesc = "XX0-XX1-XX2";
            break;
        case ACTION_CODE_UNASSIGN://"UnAssign("+TerminalNo+"-"+BoxType+"-"+"BoxNo)"
            actionName = "UnAssign";
            actionDesc = "XX0-XX1-XX2";
            break;
        case ACTION_CODE_ADD2ORDER://"AddDropOrder("+TerminalNo+"-"+BoxType+"-"+BoxNo+")"
            actionName = "AddDropOrder";
            actionDesc = "XX0-XX1-XX2";
            break;
        case ACTION_CODE_DEL4ORDER://"DelDropOrder("+TerminalNo+"-"+BoxType+"-"+BoxNo+")"
            actionName = "DelDropOrder";
            actionDesc = "XX0-XX1-XX2";
            break;
        case ACTION_CODE_EXEORDER://"ExeDropOrder("+PostmanID+"-"+PostmanName+"=>"+TerminalNo+")"
            actionName = "ExeDropOrder";
            actionDesc = "XX0-XX1=>XX2";
            break;
        case ACTION_CODE_DROP://"Dropped("+PostmanID+"-"+PostmanName+"=>"+TerminalNo+"-"+BoxNo+")"
        case ACTION_CODE_D_DROP:   
            actionName = "Dropped";
            actionDesc = "XX0-XX1=>XX2-XX3";
            break;
        case ACTION_CODE_FORCEEXPIRE://"Force-Expire("+ExpiredTime+")"
            actionName = "Force-Expire";
            actionDesc = "XX0";
            break;
        case ACTION_CODE_PICKUPBYCUSTOMER://"Takeout" TerminalNo->CoustomerMobile
            actionName = "Takeout";
            actionDesc = "XX0=>XX1";
            break;
        case ACTION_CODE_PICKUPBYPOSTMAN://"Return back("+inboxPack.TerminalNo+"-"+inboxPack.BoxNo+")"
            actionName = "Return back";
            actionDesc = "XX0-XX1";
            break;
        case ACTION_CODE_PICKUPBYMANAGER://"ManagerTakeout("+TerminalNo+"-"+BoxNo+")"
            actionName = "ManagerTakeout";
            actionDesc = "XX0-XX1";
            break;
        case ACTION_CODE_MISSING://"Missed("+PostmanID+"-"+PostmanName+")"
            actionName = "Missed";
            actionDesc = "XX0-XX1";
            break;
        case ACTION_CODE_RETRIEVE://"Retrieve("+postmanid+"-"+postmanName+"=>"+ZoneName+")"
            actionName = "Retrieve";
            actionDesc = "XX0-XX1=>XX2";
            break;
        case ACTION_CODE_RETURN://"Return("+postmanid+"-"+postmanName+"=>"+ZoneName+")"
            actionName = "Return";
            actionDesc = "XX0-XX1=>XX2";
            break;
        case ACTION_CODE_LOST://"Confirm Lose("ZoneName+")"
            actionName = "Confirm Lose";
            actionDesc = "XX0";
            break;
        case ACTION_CODE_REDISTRIBUTE://"Redistribute("+ZoneName+",No."+DropNum+")"
            actionName = "Redistribute";
            actionDesc = "XX0,No. XX1";
            break;
        case ACTION_CODE_TRANSFER://"Transfer("+zone.ZoneName+"=>"+ppc.PPCName+")"
            actionName = "Transfer";
            actionDesc = "XX0=>XX1";
            break;
        }
        //System.out.println(actionName+""+actionDesc+","+arg.length+","+arg.toString());
        if(arg!=null){
            for(int i = 0; i < arg.length; i++){
                actionDesc =  actionDesc.replaceAll("XX"+i, arg[i]);
                //System.out.println(i+":"+actionDesc);
            }
        }
        
        return actionName+"("+actionDesc+")";
    }
}
