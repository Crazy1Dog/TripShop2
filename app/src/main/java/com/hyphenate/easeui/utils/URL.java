package com.hyphenate.easeui.utils;

/**
 * Description: 项目中所有的URL
 */
public class URL {
	/**
	 * 获取图片地址域名:http://img.etu6.org
	 */
	public static String IMAGE_DEBUG = "http://img.etu6.org/";
	/**
	 * 调试开关
	 */
	private static boolean ISDEBUG = true;
	/**
	 * 测试地址bb
	 */
	public static String DEBUG = "http://test.receive.service.tripb2b.com";// 生产
	/**
	 * 线上地址
	 */
	public static String ONLINE = "http://api.";
	/**
	 * 地址
	 */
	public static String ROOT = ISDEBUG ? DEBUG : ONLINE;
	/**
	 * 根据邀请码匹配计调微店信息
	 */
	public static String INVIT_CODE = "/api/receive/invitecode/receiveguest/";
	/**
	 * 提交方式：GET 接口地址：/api/receive/uc/sendvcode/{weid}/{mobile}/{action} 参数列表：[
	 * weid:微店id--必填 mobile:手机号码--必填 action:动作类型 0：注册， 1：登录，2：忘记密码，3:绑定手机--必填 ]
	 */
	public static String GET_SECURITY_CODE = "/api/receive/uc/sendvcode/";
	/**
	 * 描述：C客登录接口 ---------------------------------------- ---------- 提交方式：POST
	 * 接口地址：/api/receive/uc/login 参数列表：[ weid :微店id--必填 type :登录方式
	 * 0:手机号码登录，1:用户名密码登录--必填 userName :用户名--当 type=1时必填 password :密码--当
	 * type=1时必填 mobile:手机号码--当 type=0时必填 vcode :验证码--当 type=0时必填 loginsource
	 * :登录来源--当 loginsource=app时表明从app接入（app登录时必填） ]
	 */
	public static String lOGIN = "/api/receive/uc/login";
	/**
	 * 描述：根据计调id展示计调详细信息 ---------------------------------------- ----------
	 * 提交方式：GET 接口地址：/api/receive/invitecode/memberinfo/{memberid} 参数列表：[
	 * memberid:计调id ] ---------------------------------------- ----------
	 * 数据格式：JSON 展现形式：{message:'通知信息',result:数据,code：500|200} 返回参数列表：[ message
	 * ：'通知信息' result ：'数据' code ：状态码 500 失败 200 成功 ]
	 */
	public static String MEMBER_DTAIL = "/api/receive/invitecode/memberinfo/";
	/**
	 * 描述：获取微店的线路分类列表(取有效分类) ---------------------------------------- ----------
	 * 提交方式：GET 接口地址：/api/receive/category/listbyreceiveguest/{receiveguestid}
	 * 参数列表：[ receiveguestid:微店Id ]
	 */
	public static String CATEGORY_LIST = "/api/receive/category/listbyreceiveguest/";
	/**
	 * 描述：前台查询旅游产品线路列表 ---------------------------------------- ----------
	 * 提交方式：POST 接口地址：/api/receive/line/guestLinelist 参数列表：[ guestId:微店ID
	 * categoryId:线路类别ID departure:出发地 destination:目的地 days:行程天数 earlyDay:最早出发时间
	 * lateDay:最晚出发时间 minPrice:最小价格 maxPrice:最大价格 tabId:标签Id
	 * priceSort:价格排序(0:从低到高，1:从高到低) curpageNo:当前页 pageSize:一页多少条 keyWord:搜索关键字
	 * type:app(从收客通app发出的请求) ]
	 */
	public static String GUSTLINE_LIST = "/api/receive/line/guestLinelist";

	/**
	 * 描述：查询微店所有出发地 ---------------------------------------- ----------
	 * 提交方式：POST 接口地址：/api/receive/line/departureStr/{guestId} 参数列表：[
	 * guestId:微店ID categoryId:类别ID ----(后台不用传此参数，前台必传)
	 * type:收客通前台必传----(后台不用传此参数，前台必传type值是mobile) ]
	 */
	public static String DEPARTRUE = "/api/receive/line/departureStr";
	/**
	 * 描述：查询微店所有目的地 ---------------------------------------- ----------
	 * 提交方式：POST 接口地址：/api/receive/line/destinationStr 参数列表：[ guestId:微店ID
	 * categoryId:类别ID ----(后台不用传此参数，前台必传) type:mobile(前台目的地查询热门目的地必填,后台不用填) ]
	 */
	public static String DESTINATION = "/api/receive/line/destinationStr";

	/**
	 * 描述：获取通讯录用户列表 ---------------------------------------- ----------
	 * 提交方式：POST 接口地址：/api/receive/contacts/getContactsUserList 参数列表：[
	 * userId:当前用户id 必填 pageSize: 每页显示数量 pageNumber: 要查询的目标页码 orderBy: 排序字段
	 * sort: 排序方式 asc/desc 数据格式：JSON
	 * 展现形式：{message:'通知信息',result:数据,code：500|200} 返回参数列表：[ message ：'通知信息'
	 * result ：'数据' code ：状态码 500 失败 200 成功
	 * **/
	public static String CONTACTLIST = "/api/receive/contacts/getContactsUserList/";
	/**
	 *  描述：收藏线路 post   /api/receive/linedetail/collect/
	 *
	 *  @param lineId       线路id
	 *  @param userId       用户id
	 *  @param successBlock 成功回调
	 *  @param faildBlock   失败回调
	 */
	public static String COLLECT = "/api/receive/linedetail/collect";
	/**
	 * 描述：用于 C 客我的收藏列表
	 ----------------------------------------
	  REQUEST:
	 ----------
	  提交方式：POST
	  接口地址：/api/receive/uc/collections
	  参数列表：[
	   token :登录token--必填
	   userId :C客ID--必填
	   pageSize :每页记录数--必填
	   curPageNo :当前页码--必填
	  ]
	 ----------------------------------------
	  RESPONSE：
	 ----------
	  数据格式：JSON
	  展现形式：{message:'通知信息',result:注册，添加后的内容,code：500|200}
	  参数列表：[
	   message ：'通知信息'
	   result ：'数据'
	   code ：状态码
	   500 失败
	   200 成功
	   ]
	 */
	public static String GETMYCOLLECTLIST = "/api/receive/uc/collections";
	/**
	 * 描述：点赞
	 ----------------------------------------
	 ----------
	  提交方式：POST
	  接口地址：/api/receive/linedetail/praise/
	  参数列表：[
	   lineId:线路ID
	   userId:用户ID
	  ] 
	 */
	public static String lIKE = "/api/receive/linedetail/praise";
	/**
	 *  描述：判断线路是否已点赞
	 ----------------------------------------
	 ----------
	  提交方式：POST
	  接口地址：/api/receive/linedetail/praise/check/
	  参数列表：[
	   lineIds:多个线路ID
	   userId:用户ID
	  ] 
	 */
	public static String  CHECKLIKE = "/api/receive/linedetail/praise/check";
	
	
	public  static String GETLINE_BASE = "/api/receive/linedetail/base";
}
