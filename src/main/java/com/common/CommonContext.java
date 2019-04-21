package com.common;
/**
  * 
   * 共通数据
 * @author A-T
 *
 */
public class CommonContext {
	
	
	/**
	 * 请求成功状态
	 */
	public static String HTTP_SUCCESS = "200";
	
	/**
	 * 请求失败状态
	 */
	public static String HTTP_ERROR = "500";
	
	
	/**
	 *  未登录状态码
	 */
	public static String NO_LOGIN = "401";
	
	
	/**
	 * 已付款
	 */
	public static String IS_ORDER = "0";
	
	/**
	 * 未付款
	 */
	public static String NO_ORDER = "1";
	
	/**
	 * 已评价
	 */
	public static String IS_EVA = "0";
	
	/**
	 * 未评价
	 */
	public static String NO_EVA = "1";
	
	
	/**
	 * 已登录
	 */
	public static String IS_LOGIN = "0";
	
	/**
	 * 身份--游客
	 */
	public static String USER_TYPE_TR = "0";
	
	/**
	 * 身份--管理员
	 */
	public static String USER_TYPE_ADMIN = "1";
	
	/**
	 * 身份--超级管理员
	 */
	public static String USER_TYPE_SUPER_ADMIN = "2";

	
}
