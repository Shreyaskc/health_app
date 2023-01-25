package com.app.utils;

/**
 * URL list
 */
public class URL {
//	http://192.168.1.33:93/api/HSTAdmin
//	private static final String ROOT_URL = "http://hst.bigscal.com/api/HSTAdmin";
//	private static final String ROOT_URL = "http://mobileclient1.hstechnology.com/api/HSTAdmin"; 
	private static final String ROOT_URL = "http://mobileclient1.hstechnology.com/api/HSTAdmin";


	public static final String REGISTER_USER_URL = ROOT_URL
			+ "/addEndUser";
	public static final String GET_AGREEMENT_URL = ROOT_URL + "/getagreement/";
	public static final String GET_APP_DETAIL_URL = ROOT_URL + "/GetAppDetail/";
	public static final String LOGIN_USER_URL = ROOT_URL + "/Login";
	public static final String GET_DEMO_GRAPHIC_INFO_URL = ROOT_URL
			+ "/GetDemoGraphicInfo";
	public static final String FORGET_PASSWORD_URL = ROOT_URL
			+ "/ForgetPassword";
	public static final String RESET_PASSWORD_URL = ROOT_URL + "/ResetPassword";
	public static final String MEMBER_INSURANCE_INFO_URL = ROOT_URL
			+ "/GetMemberInsuranceInfo";
	public static final String MEMBER_PCP_INFO_URL = ROOT_URL + "/GetPCPInfo";
	public static final String PROVIDER_LIST_URL = ROOT_URL+ "/GetAuthorizedProviders";
	public static final String PROVIDER_CATEGORY_URL = ROOT_URL+ "/GetProviderCategory";
	public static final String BLUE_SHIELD_PLANS_URL = ROOT_URL+ "/GetBlueShieldPlans";

}
