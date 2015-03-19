package com.dormvote.net;


import com.dormvote.bean.StatusAndExplain;

public class Macros {

	public static final int REQUEST_SUCCESS = 300;
	public static final int REQUEST_FAILED = 400;

	public static final int SUCCESS = 200;
	public static final String ERROR = "500";
	public static final int FAILED_MSG = 999;
	public static final String LOGIN_EXPIRED = "invalid_access_token";// 登录过期，即返回不可用的access_token

	public static final int PAGE_SIZE = 6;

	public static final int MSG_LOGOUT = 100;
	
	
	public static final StatusAndExplain NET_STATUS_10001 = new StatusAndExplain(10001,"系统错误");
	public static final StatusAndExplain NET_STATUS_10002 = new StatusAndExplain(10002,"服务暂停");
	public static final StatusAndExplain NET_STATUS_10003 = new StatusAndExplain(10003,"远程服务错误");
	public static final StatusAndExplain NET_STATUS_10013 = new StatusAndExplain(10013,"不合法的用户");
	public static final StatusAndExplain NET_STATUS_10014 = new StatusAndExplain(10014,"应用的接口访问权限受限");
	public static final StatusAndExplain NET_STATUS_10016 = new StatusAndExplain(10016,"缺少参数");
	public static final StatusAndExplain NET_STATUS_10017 = new StatusAndExplain(10017,"参数错误");
	public static final StatusAndExplain NET_STATUS_10020 = new StatusAndExplain(10020,"接口不存在");
	public static final StatusAndExplain NET_STATUS_10021 = new StatusAndExplain(10021,"请求方式错误");
	
	public static final StatusAndExplain NET_STATUS_20003 = new StatusAndExplain(20003,"用户不存在");
	public static final StatusAndExplain NET_STATUS_20005= new StatusAndExplain(20005,"不支持的图片类型，仅仅支持JPG、GIF、PNG");
	public static final StatusAndExplain NET_STATUS_20006 = new StatusAndExplain(20006,"图片太大");
	public static final StatusAndExplain NET_STATUS_21301 = new StatusAndExplain(21301,"认证失败");
	public static final StatusAndExplain NET_STATUS_21302 = new StatusAndExplain(21302,"用户名或密码不正确");
	public static final StatusAndExplain NET_STATUS_21303 = new StatusAndExplain(21303,"用户没有权限");
	public static final StatusAndExplain NET_STATUS_21304 = new StatusAndExplain(21304,"accessToken错误");

	public static final StatusAndExplain NET_STATUS_22000 = new StatusAndExplain(22000,"待检查");
	public static final StatusAndExplain NET_STATUS_22001 = new StatusAndExplain(22001,"正在检查");
	public static final StatusAndExplain NET_STATUS_22002 = new StatusAndExplain(22002,"等待上传图片");
	public static final StatusAndExplain NET_STATUS_22003 = new StatusAndExplain(22003,"第一次检查无人");
	public static final StatusAndExplain NET_STATUS_22004 = new StatusAndExplain(22004,"检查合格");
	public static final StatusAndExplain NET_STATUS_22005 = new StatusAndExplain(22005,"检查优秀");
	public static final StatusAndExplain NET_STATUS_22006 = new StatusAndExplain(22006,"检查不合格");
	public static final StatusAndExplain NET_STATUS_22007 = new StatusAndExplain(22007,"检查拒绝拍照");
	public static final StatusAndExplain NET_STATUS_22008 = new StatusAndExplain(22008,"检查无人");
	public static final StatusAndExplain NET_STATUS_22009 = new StatusAndExplain(22009,"拒绝检查");
	public static final StatusAndExplain NET_STATUS_22010 = new StatusAndExplain(22010,"一票否决");
	
	
	public static final StatusAndExplain NET_STATUS_23001 = new StatusAndExplain(23001,"普通检查项");
	public static final StatusAndExplain NET_STATUS_23002 = new StatusAndExplain(23002,"一票否决");
	public static final StatusAndExplain NET_STATUS_24001 = new StatusAndExplain(24001,"已被一票否决");
	public static final StatusAndExplain NET_STATUS_25001 = new StatusAndExplain(25001,"用户已被移除出本检查小组");

    public static final StatusAndExplain NET_STATUS_0 = new StatusAndExplain(0,"普通成员");
    public static final StatusAndExplain NET_STATUS_1 = new StatusAndExplain(1,"组长");

	
//	20003	User does not exists	用户不存在
//	20005	Unsupported image type, only suport JPG, GIF, PNG	不支持的图片类型，仅仅支持JPG、GIF、PNG
//	20006	Image size too large	图片太大
//	-	-	-
//	21301	Auth faild	认证失败
//	21302	Username or password error	用户名或密码不正确
//	-	-	-
//	22001	unchecked	本组检查的宿舍没有开始检查
//	22002	checking	本组检查的宿舍正在检查
//	22003	waiting for image	本组检查的宿舍等待上传图片
//	22003	qualified	本组检查的宿舍合格
//	22004	perfect	本组检查的宿舍优秀
//	22005	unqualified	本组检查的宿舍不合格
//	22006	Refused to take pictures	本组检查的宿舍拒绝拍照
//	22006	nobody in	本组检查的宿舍检查无人
//	22007	nobody in on first turn	本组检查的宿舍一轮检查无人
//	22008	Refused to check	本组检查的宿舍拒检
//	22009	one vote veto	本组检查的宿舍一票否决
//	-	-	-
//	23001	common check item	普通检查项
//	23002	one vote veto item	一票否决项
//	-	-	-
//	24001	leader one vote veto	组长已经对该宿舍进行了“一票否决”
//	-	-	-
//	25001	user was removed off	用户已经被移除出本检查小组
	//用户状态
	public static final StatusAndExplain USER_STATUS_OK = new StatusAndExplain(100,"用户正常激活状态");
	public static final StatusAndExplain USER_STATUS_JOINED_OTHER_TEAM = new StatusAndExplain(200,"用户已加入到其他组");
	public static final StatusAndExplain USER_STATUS_FORBIDED_JOIN_A_TEAM = new StatusAndExplain(300,"用户被禁止加入到检查小组");
	//用户角色
	public static final StatusAndExplain USER_TEAM_ROLE_STATUS_MEMBER = new StatusAndExplain(0,"小组普通成员");
	public static final StatusAndExplain USER_TEAM_ROLE_STATUS_LEADER = new StatusAndExplain(1,"小组组长");
	//宿舍检查状态（小分类）
	public static final StatusAndExplain ROOM_STATUS_UNCHECKED = new StatusAndExplain(22000,"没有开始检查");
	public static final StatusAndExplain ROOM_STATUS_CHECKING = new StatusAndExplain(22001,"正在检查");
	public static final StatusAndExplain ROOM_STATUS_WAITING_FOR_IMAGE = new StatusAndExplain(22002,"等待上传图片");
    public static final StatusAndExplain ROOM_STATUS_NOBODY_IN = new StatusAndExplain(22003,"检查无人");


	public static final StatusAndExplain ROOM_STATUS_QUALIFIED = new StatusAndExplain(22004,"合格");
	public static final StatusAndExplain ROOM_STATUS_PERFECT= new StatusAndExplain(22005,"优秀");
	public static final StatusAndExplain ROOM_STATUS_UNQUALIFIED = new StatusAndExplain(22006,"不合格");

	public static final StatusAndExplain ROOM_STATUS_REFUSED_TO_TAKE_PICTURES = new StatusAndExplain(22007,"拒绝拍照");
    public static final StatusAndExplain ROOM_STATUS_NOBODY_IN_ON_FIRST_TURN = new StatusAndExplain(22008,"一轮检查无人");
	public static final StatusAndExplain ROOM_STATUS_REFUSED_TO_CHECK = new StatusAndExplain(22009,"拒检");
	public static final StatusAndExplain ROOM_STATUS_ONE_VOTE_VETO = new StatusAndExplain(22010,"一票否决");
    public static final String[] ROOM_STATUS =  {ROOM_STATUS_NOBODY_IN.getStatus()+"",ROOM_STATUS_REFUSED_TO_TAKE_PICTURES.getStatus()+"",ROOM_STATUS_NOBODY_IN_ON_FIRST_TURN.getStatus()+"",ROOM_STATUS_REFUSED_TO_CHECK.getStatus()+""};
    public static final String[] ROOM_STATUS_EXPLAIN =  {ROOM_STATUS_NOBODY_IN.getExplain(),ROOM_STATUS_REFUSED_TO_TAKE_PICTURES.getExplain(),ROOM_STATUS_NOBODY_IN_ON_FIRST_TURN.getExplain(),ROOM_STATUS_REFUSED_TO_CHECK.getExplain()};

	//宿舍检查状态（大分类）
	public static final StatusAndExplain CHECK_ITEM_STATUS_COMMON = new StatusAndExplain(10,"普通检查项");
	public static final StatusAndExplain CHECK_ITEM_STATUS_ONE_VOTE_VETO = new StatusAndExplain(20,"一票否决项");
	//小组成员角色
	public static final StatusAndExplain MEMBER_ROLE_IN_TEAM_STATUS_NORMAL= new StatusAndExplain(10,"普通成员");
	public static final StatusAndExplain MEMBER_ROLE_IN_TEAM_STATUS_CHARGER = new StatusAndExplain(20,"小组长");
	
	public static final String ERROR_CODE_SUCCESS = "200";
	public static final String ERROR_CODE_FAIL = "400";
	public static final String ERROR_CODE_DATA_DOSE_NOT_EXIST = "401";

	// api接口qxu1142020209.my3w.com/index.php/api
	public static final String DOMIN_NAME = "http://qxu1142020209.my3w.com/index.php/";
	public static final String INTERFACE_API = DOMIN_NAME + "api/";
	public final static String INTERFACE_TYPE_PUBLIC = INTERFACE_API+ "public/";
	public final static String INTERFACE_TYPE_MEMBER = INTERFACE_API+ "member/";
	public final static String INTERFACE_TYPE_TEAM = INTERFACE_API + "team/";
	public final static String INTERFACE_TYPE_TASK = INTERFACE_API + "task/";
	public final static String INTERFACE_TYPE_FILE = INTERFACE_API + "file/";

	public static final String GET_PUBLIC_DATA = INTERFACE_TYPE_PUBLIC+ "publicdata";
	public static final String LOGIN = INTERFACE_TYPE_PUBLIC + "login";
	public static final String GET_LOGOUT = INTERFACE_TYPE_PUBLIC + "logout";
	public static final String GET_USERINFO = INTERFACE_TYPE_MEMBER+ "userinfo";
	public static final String GET_TEAM_MEMBER = INTERFACE_TYPE_TEAM+ "teammember";
	public static final String GET_ROOMLIST = INTERFACE_TYPE_TASK + "roomlist";
	public static final String VOTEROOM = INTERFACE_TYPE_TASK + "voteroom";
	public static final String GET_CHECKPROGRESS = INTERFACE_TYPE_TASK+ "checkprogress";
	public static final String GET_VOTERESULT = INTERFACE_TYPE_TASK+ "voteresult";
	public static final String UPDATE_CLIENT_ID = INTERFACE_TYPE_PUBLIC+ "updateclientid";
	public static final String GET_UP_TOKEN = INTERFACE_TYPE_FILE+ "getUptoken";
	public static final String GET_ROOM_INFO = INTERFACE_TYPE_TASK+ "roominfo";
	public static final String CHANGE_ROOM_STATUS = INTERFACE_TYPE_TASK+ "changeroomstatus";
    public static final String UPLOAD_PHOTO_TO_QINIU = "http://upload.qiniu.com";
    public static final String UPLOAD_MINE_PHOTO = INTERFACE_TYPE_FILE+"room_pic_info";

	public static final int MSG_GET_PUBLIC_DATA = 1000;
	public static final int MSG_LOGIN = 2000;
	public static final int MSG_GET_LOGOUT = 3000;
	public static final int MSG_GET_USERINFO = 4000;
	public static final int MSG_GET_TEAM_MEMBER = 5000;
	public static final int MSG_GET_ROOMLIST_DONE = 6000;
    public static final int MSG_GET_ROOMLIST_UNDONE = 6001;
	public static final int MSG_VOTEROOM = 7000;
	public static final int MSG_GET_CHECKPROGRESS = 8000;
	public static final int MSG_GET_VOTERESULT = 9000;
	public static final int MSG_UPDATE_CLIENT_ID = 10000;
	public static final int MSG_GET_UP_TOKEN = 11000;
	public static final int MSG_GET_ROOM_INFO = 12000;
	public static final int MSG_CHANGE_ROOM_STATUS = 13000;
    public static final int MSG_UPLOAD_PHOTO_TO_QINIU = 14000;
    public static final int MSG_UPLOAD_MINE_PHOTO = 15000;
	
	
	
	
	
	
	
	
	
	
	
	
	

}
