package com.xinxin.aicare;

public class Constant {
    public static final Boolean DEBUG = true;
    public static final String RID = "TEST_RID";
    public static boolean isShowIntroduce = false;
    public static boolean isDeviceBind = false;
    public static boolean isBindConnect = false;
    public static boolean isNetworkReceipt = true;

    public static final int STATUS_CODE_PERMISSION_REQUEST = 100000;

    //微信支付AppID
    public static final int REQUEST_PERMISSION_CODE = 101;


    public static final String BASE_URL = "http://wechatshare.xinxinchoice.com/api/";
    public static final int PAY_FOR_ORDER = 10001;

    public static final String WX_APPID = "wxfeda27e75bee03f3";
    public static final String DEVICE_NAME = "LN-200c";

    //user部分
    public static final String URL_GET_CODE = "user/getcode";
    public static final String URL_REGISTER = "user/register";
    public static final String URL_LOGINWX = "user/loginwx";
    public static final String URL_LOGIN = "user/login";
    public static final String URL_LOGINBYPSW = "user/loginByPsw";
    public static final String URL_EDITPSW = "user/editPsw";
    public static final String URL_LOGOUT = "user/loginout";
    public static final String URL_PERSONINFO = "user/personalInfo";
    public static final String URL_CHECKMESSAGEREAD = "user/checkMessageRead";
    public static final String URL_MESSAGELIST = "user/messageList";
    public static final String URL_MESSAGEINFO = "user/messageInfo";
    public static final String URL_COUPONLIST = "user/couponList";
    public static final String URL_GROWTHRANKING = "user/gowthRanking";
    public static final String URL_USERGROWTHRANKING = "user/userGowthRanking";
    public static final String URL_ADDADVISE = "user/addAdvise";
    public static final String URL_SIGNININFO = "user/signinInfo";
    public static final String URL_SIGNIN = "user/signin";
    public static final String URL_GROWTHPOINTLIST = "user/growthPointList";
    public static final String URL_MYGROWTH = "user/myGrowth";
    public static final String URL_BINDEMAIL = "user/bindEmail";
    public static final String URL_BINDPHONE = "user/bindphone";
    public static final String URL_EDITIMG = "user/editImg";
    public static final String URL_EDITUSER = "user/editUser";
    public static final String URL_SENDEMAIL = "user/sendEmail";
    public static final String URL_CHECKEMAIL = "user/checkEmail";
    public static final String URL_INFORMATIONSTAR = "user/informationStar";
    public static final String URL_INFORMATIONCOLLECT = "user/informationCollect";
    public static final String URL_PERSONALACTLIST = "user/personalActList";
    public static final String URL_DELINFOREAD = "user/delInfoRead";
    public static final String URL_ADDADDRESS = "user/addAddress";
    public static final String URL_EDITADDRESS = "user/editAddress";
    public static final String URL_DELADDRESS = "user/delAddress";
    public static final String URL_SETDEFAULTADDRESS = "user/setDefaultAddress";
    public static final String URL_ADDRESSLIST = "user/addressList";
    public static final String URL_ADDRESSINFO = "user/addressInfo";
    public static final String URL_SHARESUCCESS = "user/shareSuccess";
    public static final String URL_MESSAGEREADALLDO = "user/messageReadAll.do";

    //通用类
    public static final String URL_GETCOURSE = "index/getcourse";
    public static final String URL_VERSION = "index/version";
    public static final String URL_USERAGREEMENT = "index/useragreement";
    public static final String URL_ABOUTUS = "index/aboutUs";

    public static final String URL_INFORMATIONTYPE = "index/informationType";
    public static final String URL_INFORMATIONLIST = "index/informationList";
    public static final String URL_INFORMATION = "index/information";

    public static final String URL_GETBRANDSIZE = "index/getBrandSize";

    //成员类
    public static final String URL_ADDMEMBER = "member/addMember";
    public static final String URL_DELETEMEMBER = "member/deleteMember";
    public static final String URL_EDITMEMBERIMG = "member/editMemberImg";
    public static final String URL_EDITMEMBERSIZE = "member/editMemberSize";
    public static final String URL_EDIT_MEMBERINFO = "member/editMemberInfo";
    public static final String URL_MEMBERLIST = "member/memberList";
    public static final String URL_MEMBERINFO = "member/memberInfo";
    public static final String URL_MEMBERDATACAL = "member/memberDataCal";
    public static final String URL_MEMBERCHANGEINFO = "member/memberChangeInfo";
    public static final String URL_MEMBERDATASHARE = "member/memberDataShare";
    public static final String URL_DEVICEPARAMINFO = "member/deviceParamInfo";
    public static final String URL_UNBINDDEVICE = "member/unBindDevice";
    public static final String URL_BINDDEVICE = "member/bindDevice";
    public static final String URL_EDITDEVICEPARAM = "member/editDeviceParam";
    public static final String URL_UPLOADDEVICEDATA = "member/uploadDeviceData";
    public static final String URL_MEMBERDEVICEPARAMLIST = "member/memberDeviceParamList";

    //商城类
    public static final String URL_HOMECAROUSEL = "mall/homecarousel";
    public static final String URL_GOODSLIST = "mall/goodsList";
    public static final String URL_GOODSINFO = "mall/goodsInfo";

    public static final String URL_LISTMYYUYUE = "mall/listMyYuyue";
    public static final String URL_SHOWMYYUYUE = "mall/showMyYuyue";
    public static final String URL_CANCELMYYUYUE = "mall/cancelMyYuyue";
    public static final String URL_PREYUYUE = "mall/preYuyue";
    public static final String URL_PREYUYUECOUPON = "mall/preYuyueCoupon";
    public static final String URL_SUBMITYUYUEORDER = "mall/submitYuyueOrder";
    public static final String URL_PAYORDER = "mall/payOrder";
    public static final String URL_SHOWMYYUYUEREFUND = "mall/showMyYuyueRefund";
    public static final String URL_SUBMITREFUNDNO = "mall/submitRefundNo";
    public static final String URL_SHOWMYYUYUELOGISTICS = "mall/showMyYuyueLogistics";
    public static final String URL_SHOWSIMPLEMYYUYUE = "mall/showSimpleMyYuyue";
    public static final String URL_RECEIVEMYYUYUE = "mall/receiveMyYuyue";
    public static final String URL_PREREFUNDMYYUYUE = "mall/preRefundMyYuyue";
    public static final String URL_PREREFUNDNUM = "mall/preRefundNum";
    public static final String URL_REFUNDMYYUYUE = "mall/refundMyYuyue";

    //提醒文案
    public static final String[] WARNING_TIPS = {"您的宝宝[NICKNAME]已达到预设的尿湿门槛，请注意更换尿片", "您的宝宝[NICKNAME]已达到预设的尿湿峰值，请尽快更换尿片，避免引起宝宝不适", "您的宝宝[NICKNAME]所处环境温度过低，请检查宝宝是否踢被", "您的宝宝[NICKNAME]所处环境温度过高，为了宝宝安全，请检查宝宝情况！", "您的宝宝[NICKNAME]现在处于趴睡状态，请尽快调整睡姿，以免影响宝宝健康！"};
    public static final String[] WARNING_TITLES = {"尿湿提醒", "尿满提醒", "过冷提醒", "过热提醒", "趴睡提醒"};
    public static final String[] SLEEP_POS_TIPS = {"宝宝仰睡", "宝宝左侧睡", "宝宝右侧睡", "宝宝趴睡，注意风险"};
}
