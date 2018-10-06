package com.patent.util;

public class Constants {
	//-------------------变量------------------------//
	public static final String LAST_LOGIN_DATE = "last_login_date";
	public static final String LOGIN_TIMES = "login_times";
	public static final String LOGIN_USER_ID = "login_user_id";
	public static final String LOGIN_ACCOUNT = "login_account";
	public static final String LOGIN_REAL_NAME = "login_real_name";
	public static final String LOGIN_USER_ROLE_ID = "login_user_role_id";
	public static final String LOGIN_USER_ROLE_NAME = "login_user_role_name";
	public static final String LOGIN_TYPE = "login_type";
	
	public static final String MAIL_SERVER_HOST = "smtp.163.com";
	public static final String MAIL_SERVER_PORT = "25";
	public static final Boolean VALIDATE_FLAG = true;
	public static final String SYSTEM_EMAIL_ACCOUNT = "service_cus@163.com";// //邮箱账号
	public static final String SYSTEM_EMAIL_PASS = "32011823wmk";// 您的邮箱授权码 
	
	public static final Integer JF_SL_END_DATE_CPY = 45;//代理机构缴受理费期限
	public static final Integer JF_SC_END_DATE_GF = 1095;//实质审查费官方期限
	public static final Integer JF_SC_END_DATE_CPY = 1080;//实质审查费代理机构期限
	
	public static final String SYS_CONFIG_WJ = "e:\\sysConfig.json";//系统配置文件存放地
	public static final Integer TD_RECEIVE_DAYS = 15;//推定收到日
	
	public static final Integer SC_FEE = 2500;//实质审查费
	public static final Integer YELLOW_ALERT_DAYS = 10;//黄色警告截止天数
	public static final Integer GREEN_ALERT_DAYS = 20;//绿色警告截止天数
	//20天或者20天以上为绿色警告，10-20天内为黄色警告，少于10天为红色警告
	
	//免费会员不能增加子公司
	public static final Integer SUB_CPY_NUM_YP = 1;//银牌对应的子公司个数
	public static final Integer SUB_CPY_NUM_JP = 3;//金牌对应的子公司个数
	public static final Integer SUB_CPY_NUM_ZS = 5;//钻石对应的子公司个数
	
	//免费会员每月只能增加一个专利
	public static final Integer MONTH_MAX_ZL_NUM_TP = 1;//免费会员每月只能增加的专利个数
	public static final Integer ADD_ZL_NUM_YP = 200;//银牌可增加专利数
	public static final Integer ADD_ZL_NUM_JP = 2000;//金牌可增加专利数
	//钻石会员无限制
	
	public static final Integer freeDays = 15;//代理机构注册免费天数
	

	//-------------------WEB-------------------------//
	public static final String WEB_CPY_USER_INFO = "cpy_user_info_Web";
	public static final String WEB_CPY_ROLE_INFO = "cpy_role_info_Web";
	public static final String WEB_CPY_INFO = "cpy_info_Web";
	public static final String WEB_APPLY_INFO = "apply_info_Web";
	public static final String WEB_SUPER_USER_INFO = "super_user_info_Web";
	public static final String WEB_SEND_MAIL_CODE_INFO = "send_mail_code_info_Web";
	public static final String WEB_MAIL_INFO = "mail_info_Web";
	public static final String WEB_JS_FIELD_INFO = "js_field_info_Web";
	public static final String WEB_MODULE_INFO = "module_info_Web";
	public static final String WEB_MOD_ACT_INFO = "mod_act_info_Web";
	public static final String WEB_ACT_ROLE_INFO = "act_role_info_Web";
	public static final String WEB_PUB_ZL_INFO = "pub_zl_info_Web";
	public static final String WEB_CPY_JOIN_INFO = "cpy_join_info_Web";
	public static final String WEB_CUSTOMER_INFO = "customer_info_Web";
	public static final String WEB_ZLAJ_EWYQ_INFO = "zlaj_ewyq_info_Web";
	public static final String WEB_ZLAJ_MAIN_INFO = "zlaj_main_info_Web";
	public static final String WEB_ZLAJ_LC_INFO = "zlaj_lc_info_Web";
	public static final String WEB_ZLAJ_LC_MX_INFO = "zlaj_lc_mx_info_Web";
	public static final String WEB_ZLAJ_TZS_INFO = "zlaj_tzs_info_Web";
	public static final String WEB_ZLAJ_FJ_INFO = "zlaj_fj_info_Web";
	public static final String WEB_ZLAJ_FEE_INFO = "zlaj_fee_info_Web";
	
	//-------------------DAO-------------------------//
	public static final String DAO_CPY_USER_INFO = "cpy_user_info_Dao";
	public static final String DAO_CPY_ROLE_INFO = "cpy_role_info_Dao";
	public static final String DAO_CPY_ROLE_USER_INFO = "cpy_role_user_info_Dao";
	public static final String DAO_CPY_INFO = "cpy_info_Dao";
	public static final String DAO_APPLY_INFO = "apply_info_Dao";
	public static final String DAO_SUPER_USER_INFO = "super_user_info_Dao";
	public static final String DAO_SEND_MAIL_CODE_INFO = "send_mail_code_info_Dao";
	public static final String DAO_MAIL_INFO = "mail_info_Dao";
	public static final String DAO_JS_FIELD_INFO = "js_field_info_Dao";
	public static final String DAO_MODULE_INFO = "module_info_Dao";
	public static final String DAO_MOD_ACT_INFO = "mod_act_info_Dao";
	public static final String DAO_ACT_ROLE_INFO = "act_role_info_Dao";
	public static final String DAO_PUB_ZL_INFO = "pub_zl_info_Dao";
	public static final String DAO_CPY_JOIN_INFO = "cpy_join_info_Dao";
	public static final String DAO_CUSTOMER_INFO = "customer_info_Dao";
	public static final String DAO_CUSTOMER_LXR_INFO = "customer_lxr_info_Dao";
	public static final String DAO_CUSTOMER_FMR_INFO = "customer_fmr_info_Dao";
	public static final String DAO_ZLAJ_EWYQ_INFO = "zlaj_ewyq_info_Dao";
	public static final String DAO_ZLAJ_MAIN_INFO = "zlaj_main_info_Dao";
	public static final String DAO_ZLAJ_LC_INFO = "zlaj_lc_info_Dao";
	public static final String DAO_ZLAJ_LC_MX_INFO = "zlaj_lc_mx_info_Dao";
	public static final String DAO_ZLAJ_TZS_INFO = "zlaj_tzs_info_Dao";
	public static final String DAO_ZLAJ_FJ_INFO = "zlaj_fj_info_Dao";
	public static final String DAO_ZLAJ_FEE_INFO = "zlaj_fee_info_Dao";
	public static final String DAO_FEE_TYPE_INFO = "fee_type_info_Dao";
}
