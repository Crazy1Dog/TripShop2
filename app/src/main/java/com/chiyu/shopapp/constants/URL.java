package com.chiyu.shopapp.constants;

/**
 * Description: 项目中所有的URL
 */
public class URL {

	/** 请求超时，5秒 ***/
	public static final int REQUEST_TIMEOUT = 60 * 1000;
	/*** 响应超时，10秒 **/
	public static final int SO_TIMEOUT = 60 * 1000;
	/***
	 * 线上域名
	 */
	public static String DEBUG = "https://receive.service.tripb2b.com";// 生产
	public static String IMAGE_DEBUG = "http://img.happytoo.cn/";// 生产 图片域名

	public static final String pay_URL = "http://pay.service.tripb2b.com";// 支付参数获取   线上
	public static final String ALIpay_HUITIAOURL = "http://pay.service.tripb2b.com/callback?platform=alipaymobile";// 支付回调
	public static final String pay_C_HUITIAOURL = "http://receive.service.tripb2b.com/api/receive/order/savePayInfo";// c++回调java   线上
	public static String FENXIANG_DEBUG = "http://wei.tripb2c.com/";// 生产 分享

	/***
	 * pre 域名
	 */
//	 public static String IMAGE_DEBUG = "http://imgtest.happytoo.cn/";// pre测试    获取图片地址域名
//	 public static String DEBUG = "http://pre.receive.service.tripb2b.com";// pre测试
//	 public static final String pay_URL = "http://pre.pay.service.tripb2b.com";// 支付参数获取 pre
//	 public static final String ALIpay_HUITIAOURL="http://pre.pay.service.tripb2b.com/callback?platform=alipaymobile";//支付回调
//	 public static final String pay_C_HUITIAOURL = "http://pre.receive.service.tripb2b.com/api/receive/order/savePayInfo";//c++回调java
//	 public static String FENXIANG_DEBUG ="http://pre.shopweb.tripb2b.com/";// pre测试 分享域名

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
	 * 
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
	
	 */
	public static String COLLECT = "/api/receive/linedetail/collect";
	public static String COLLECT1 = "/api/receive/uc/center/collection";
	/**
	 * 收藏
	 */
	public static String GETMYCOLLECTLIST = "/api/receive/uc/collections";
	/**
	 
	 */
	public static String lIKE = "/api/receive/linedetail/praise";
	/**
	
	 */

	/*****
	 * 描述：根据线路id展示线路详细信息
	 * 
	 * 
	 * 
	 */

	/*****
	 * 描述：根据线路ID，取得线路详情团期信息
	 * 
	 * 
	 * 
	 */

	/*****
	 * 描述：预定订单 ---------------------------------------- ---------- 提交方式：POST
	 * 接口地址：/api/receive/order/saveOrder 参数列表：[ receiveGuestId:微店ID
	 * companyId:公司ID userId:计调ID b2cUserId:用户ID lineCategory:线路类型（0自发；1代销）
	 * lineId:线路ID dateId:团期ID adult:成人数 child:儿童数 baby:婴儿数 isPay:是否实时支付 0 否；1 是
	 * goTime:出团日期 backTime:返团时间 goTraffic:出发交通 backTraffic:回团交通 detail:说明
	 * prices:价格
	 * json格式:{"singleRoom":"单房差","adultPriceMarket":"成人市场价","adultPrice"
	 * :"成人结算价"
	 * ,"childPriceMarket":"儿童市场价","childPrice":"儿童结算价","babyPriceMarket"
	 * :"婴儿市场价","babyPrice":"婴儿结算价"} operations:预定人
	 * json格式:{"name":"名字","mobile":
	 * "手机","tel":"电话","email":"邮箱","detail":"备注","address":"地址"} ]
	 * 
	 */
	public static String POSTORDER_SAVEORDER = "/api/receive/order/saveOrder";

	/*****
	 * 描述：生成确认单 ---------------------------------------- ---------- 提交方式：GET
	 * 接口地址：/api/receive/order/confirmorder/{orderid} 参数列表：[ orderid:订单ID ]
	 * ---------------------------------------- ---------- 数据格式：JSON
	 * 展现形式：{message:'通知信息',result:数据,code：500|200} 返回参数列表：[ message ：'通知信息'
	 * result ：'数据' code ：状态码 500 失败 200 成功 ]
	 * 
	 * 
	 */
	public static String GETORDER_CONFIRMORDER = "/api/receive/order/confirmorder/";

	/*****
	 * 描述：获取订单详情 ---------------------------------------- ---------- 提交方式：GET
	 * 接口地址：/api/receive/order/getOrderInfo 参数列表：[ （日期格式：yyyy-MM-dd）
	 * orderId:订单ID ] ---------------------------------------- ----------
	 * 数据格式：JSON 展现形式：{message:'通知信息',result:数据,code：500|570|200} 返回参数列表：[
	 * message ：'通知信息' result ：'数据' code ：状态码 500 失败 570 订单不存在 200 成功 ]
	 * ----------------------------------------
	 * 
	 * 
	 * 
	 */
	public static String GETORDER_GETORDERINFO = "/api/receive/order/getOrderInfo/";

	/*****
	 * 描述：获取订单详情 ---------------------------------------- ----------
	 * 
	 * 描述：获取通讯录用户列表 ---------------------------------------- ----------
	 * 提交方式：POST 接口地址：/api/receive/contacts/getContactsUserList 参数列表：[
	 * userId:当前用户id 必填 pageSize: 每页显示数量 pageNumber: 要查询的目标页码 orderBy: 排序字段
	 * sort: 排序方式 asc/desc 数据格式：JSON
	 * 展现形式：{message:'通知信息',result:数据,code：500|200} 返回参数列表：[ message ：'通知信息'
	 * result ：'数据' code ：状态码 500 失败 200 成功 ]
	 * 
	 * ----------------------------------------
	 * 
	 * 
	 * 
	 */
	public static String GETCONTACTSUSERLIST = "/api/receive/contacts/getContactsUserList";

	/*****
	 * 描述：订单列表 ---------------------------------------- ---------- 提交方式：POST
	 * 接口地址：/api/receive/order/getOrderList/ 参数列表：[ （日期格式：yyyy-MM-dd至yyyy-MM-dd）
	 * params:查询条件
	 * json格式:{"receiveGuestId":"微店ID（必传）","b2cUserId":"C客Id","baseCategory"
	 * :"基础分类ID"
	 * ,"orderCode":"订单编号","lineCategory":"线路类型（0自发；1代销）","orderStatus":
	 * "1 名单不全；2未确认；3已确认；4退款；5取消；6已出票；7供应商未确认；8供应商已确认"
	 * ,"payStatus":"支付状态：0未支付；1已支付；2 支付失败"
	 * ,"createTime":"下单日期","goTime":"出团日期","confirmTime":"确认日期"} pageNumber:页数
	 * pageSize:每页数量 ] ---------------------------------------- ----------
	 * 数据格式：JSON 展现形式：{message:'通知信息',result:数据,code：500|200} 返回参数列表：[ message
	 * ：'通知信息' result ：'数据' code ：状态码 500 失败 200 成功 ]
	 * 
	 * ----------------------------------------
	 * 
	 */
	public static String POSTORDERLIST = "/api/receive/order/getOrderList";

	/*****
	 * 描述：修改订单状态(b2c订单取消调用本接口) ----------------------------------------
	 * ---------- 提交方式：POST 接口地址：/api/receive/order/updateOrderStatus 参数列表：[
	 * （日期格式：yyyy-MM-dd） orderId:订单ID mark:订单（0=b2c,1=b2b） status:状态标示
	 * (3已确认；4退款；5取消；6已出票；8供应商已确认) detail:备注 operationName:操作人 ]
	 * ---------------------------------------- ---------- 数据格式：JSON
	 * 展现形式：{message:'通知信息',result:数据,code：500|570|575|200} 返回参数列表：[ message
	 * ：'通知信息' result ：'数据' code ：状态码 500 失败 570 订单不存在 575 该订单已经被供应商确认，不能取消 200
	 * 成功 ] ----------------------------------------
	 * 
	 * 
	 */
	public static String ORDER_UPDATERSTATUS = "/api/receive/order/updateOrderStatus";

	/*****
	 * 描述：用于 C 客所有红包列表 ---------------------------------------- REQUEST:
	 * ---------- 提交方式：GET 接口地址：/api/receive/uc/redpackets/{token}/{userId}
	 * 参数列表：[ token :登录token--必填 userId :C客ID--必填 ]
	 * ---------------------------------------- RESPONSE： ---------- 数据格式：JSON
	 * 展现形式：{message:'通知信息',result:注册，添加后的内容,code：500|200} 参数列表：[ message
	 * ：'通知信息' result ：'数据' code ：状态码 500 失败 200 成功 ]
	 * 
	 * 
	 * ----------------------------------------
	 * 
	 * 
	 */
	public static String UC_REDPACKETS = "/api/receive/uc/redpackets";

	/*****
	 * 描述：申请退款 ---------------------------------------- ---------- 提交方式：POST
	 * 接口地址：/api/receive/refund/apply 参数列表：[ type: (1:支付宝;2:银行转账)-必填 alipay:
	 * 支付宝账号 bank:银行名称 bankcode:银行卡号 orderid:订单id-必填 memberid:计调id-必填
	 * receiveguestid:微店id-必填 ] ----------------------------------------
	 * ---------- 数据格式：JSON 展现形式：{message:'通知信息',result:数据,code：500|200}
	 * 返回参数列表：[ message ：'通知信息' result ：'数据' code ：状态码 500 失败 200 成功 ]
	 * ----------------------------------------
	 * 
	 * 
	 * 
	 */
	public static String REFUND_APPLY = "/api/receive/refund/apply";

	/*****
	 * 描述：查看退款信息 ---------------------------------------- ---------- 提交方式：GET
	 * 接口地址：/api/receive/refund/info/{orderid} 参数列表：[ orderid: 订单id-必填 ]
	 * ---------------------------------------- ---------- 数据格式：JSON
	 * 展现形式：{message:'通知信息',result:数据,code：500|200} 返回参数列表：[ message ：'通知信息'
	 * result ：'数据' code ：状态码 500 失败 200 成功 ]
	 * ----------------------------------------
	 * 
	 * 
	 */
	public static String REFUND_INFO = "/api/receive/refund/info";

	/*****
	 * 描述：修改游客信息 ---------------------------------------- ---------- 提交方式：POST
	 * 接口地址：/api/receive/order/addGuestInfo 参数列表：[ （日期格式：yyyy-MM-dd）
	 * orderId:订单ID guests:游客信息
	 * json格式：[{"id":"id","name":"游客名字","gender":"性别（0保密；1男；2女）
	 * ","category":"客户类型:0成人,1儿童,2婴儿","cardCategory":"证件类型:1身份证,2护照,3港澳通行证,
	 * 4台胞证,5海员证,6旅行证,7其他","idcard":"证件号","mobile":"电话","detail":"备注"},{....}]
	 * 
	 * 
	 */
	public static String ORDER_ADDGUES_INFO = "/api/receive/order/updateGuestInfo";
	/*****
	 * 描述：用于获取收客通红包配置信息 ---------------------------------------- REQUEST:
	 * ---------- 提交方式：GET 接口地址：/api/receive/content/redPacketSetting/{guestId}
	 * 参数列表：[ guestId ：微店id ] ----------------------------------------
	 * 
	 * 
	 */
	public static String CONTENT_REDPACKETSETTING = "/api/receive/content/redPacketSetting/";

	/*****
	 * 描述：支付时使用的红包 ---------------------------------------- ---------- 提交方式：POST
	 * 接口地址：/api/receive/order/useRedPackets 参数列表：[ orderId:订单ID
	 * redPacketIds:红包ID串（逗号分隔） ] ----------------------------------------
	 */
	public static String ORDER_USEREDPACKETS = "/api/receive/order/useRedPackets";

	/*****
	 * 描述：是否可以支付（支付前调用该接口是否能支付） ----------------------------------------
	 * ---------- 提交方式：GET 接口地址：/api/receive/order/getPayStatus/{orderId}
	 * orderId:订单ID ] ---------------------------------------- ----------
	 */
	public static String ORDER_GETPAYSTATUS = "/api/receive/order/getPayStatus/";

	/**
	 * 提交联系人
	 * **/
	public static String COMMIT_CONTACTS = "/api/receive/contacts/addContactsUser";
	/**
	 * 个人中心提交更改的个人信息
	 * */
	public static String COMMIT_PERSONAL = "/api/receive/uc/update";

	/**
	 * 上传C客头像图片
	 * **/
	public static String COMMIT_PHOTO_C = "/api/receive/uc/center/photo";
	/*****
	 * 描述：根据线路ID，取得线路详情团期信息
	 * 
	 * ---------------------------------------- ---------- 提交方式：GET
	 * 接口地址：/api/receive/linedetail/date/{lineId} 参数列表：[ lineId:线路ID
	 * 
	 */
	public static String GETLINEDETAIL_GOTIME = "/api/receive/linedetail/date/";
	/**
	 * 描述：判断线路是否已点赞 ---------------------------------------- ----------
	 * 提交方式：POST 接口地址：/api/receive/linedetail/praise/check/ 参数列表：[
	 * lineIds:多个线路ID userId:用户ID ]
	 */
	public static String CHECKLIKE = "/api/receive/linedetail/praise/check";
	/*****
	 * 描述：根据线路id展示线路详细信息
	 * 
	 * ---------------------------------------- ---------- 提交方式：GET
	 * 接口地址：/api/receive/linedetail/base/{lineId}/{dateId} 参数列表：[ lineId:线路ID
	 * 
	 */
	public static String GETLINE_BASE = "/api/receive/linedetail/base/";
	/**
	 * 描述：个人中心 GET /api/receive/uc/center/{token}/{userId}
	 * 
	 * @param token
	 *            登录token--必填
	 * @param userId
	 *            C客ID--必填
	 * @param successBlock
	 *            成功
	 * @param faildBlock
	 *            失败
	 */
	public static String DESCRIBE = "/api/receive/uc/center/";
	/**
	 * 版本更新接口
	 */
	public static String UPDATE_VERSION = "http://service.tripb2b.com/operation/index?type=getapp&mold=3&category=2";

	/**
	 * 更新通讯录用户信息
	 * **/
	public static String UPDATE_CONTACT = "/api/receive/contacts/updateContactsUser";
}
