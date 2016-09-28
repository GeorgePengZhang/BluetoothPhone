package com.aura.bluetoothphone.configs;

/**
 * 服务端配置类
 * 
 * @Description 此处定义服务器的链接地址配置和接口请求的方法，
 * @author LiGang
 * @version 1.0
 * @date 2014年4月4日
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc.
 *             All rights reserved.
 * 
 */
public class ServerConfig {

	/**
	 * 内网切换，取消注释：“服务器地址_内网”、“API接口方法模块_内网”、“SERVER_TYPE_VALUE”
	 */
	// *****************************网请求消息状态码 ******************************//
	/** 请求接口数据成功状态码 */
	public static final String RESPONSE_STATUS_SUCCESS = "0";
	/** 本地上传失败 */
	public static final String EXCEPTION_UPLOAD_ERROR_STATUS = "805";

	// **************************失败状态码*****************************//
	/** 注册需要手机号码 */
	public static final String STATUS_REGISTER_NEED_PHONE = "60001";
	/** 登录需要手机号码 */
	public static final String STATUS_LOGIN_NEED_PHONE = "60003";
	/** Tickey 过期失效 */
	public static final String STATUS_TICKEY_UNVALID = "60004";

	// ***************************接口请求配置 ****************************//
	/** 服务器连接方法key */
	public static final String SERVER_METHOD_KEY = "method";
	/** 服务器连接类型key */
	public static final String SERVER_TYPE_KEY = "app_key";
	/** 服务器连接类型数据 */
	public static final String SERVER_TYPE_VALUE = "10";
	/** 服务器连接版本key */
	public static final String SERVER_VESRTION_KEY = "v";
	/** 服务器连接版本 数据 */
	public static final String SERVER_VESRTION_VAULE = "1.0";

	/** 服务器升级版本key */
	public static final String SERVER_UPDATE_KEY = "app_v";
	/** 服务器升级版本 数据 */
	public static final String SERVER_UPDATE_VAULE = "2.0";
	/** 服务器超时时间 */
	public static final int SERVER_CONNECT_TIMEOUT = 20000;
	/** 请求数据条数 */
	public static final String PAGE_COUNT = "10";
	/** 管理后台分配给此系统的连接ID的key */
	public static final String SERVER_CAS_KEY = "res_key";
	/** 管理后台分配给此系统的连接ID */
	public static final String SERVER_CAS_VALUE = "100";




	/*************************** 服务器地址_内网 ****************************/
	/**应用服务器IP */
	public static String SERVER_API_URL = "http://192.168.1.30:8080" ;



//	/*************************** 服务器地址_外网 ****************************/
//	/** Api服务器地址 */
//	public final static String SERVER_API_URL = "http://192.168.1.100:45231";




	/*************************** URL ****************************/

	/** 注册 url */
	public static final String SERVER_REGISTER_URL = SERVER_API_URL + "/iotserver/client/registerUser";

	/** 登录 url */
	public static final String SERVER_LONGIN_URL = SERVER_API_URL + "/iotserver/client/userLogin";

	/** 修改密码 url */
	public static final String SERVER_CHANGEUSERPASSWORD_URL = SERVER_API_URL + "/iotserver/client/changeUserPassword";





}
