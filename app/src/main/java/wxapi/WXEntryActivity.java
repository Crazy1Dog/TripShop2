package wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.chiyu.shopapp.ui.R;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


/**
 * 如果要使用微信分享就要创建改类
 *
 * @author tangxian
 *
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler  {

	private IWXAPI api;
	private final String WXAppId = "wxf5a649f0f190b79a";
	@SuppressWarnings("unused")
	private final String WXAppSecret = "3f3978a3cac4c704a5855f69c3788f39";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		api = WXAPIFactory.createWXAPI(this, WXAppId, false);
		api.registerApp(WXAppId);
		api.handleIntent(getIntent(), this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		int result = 0;

		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result = R.string.errcode_success;
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = R.string.errcode_cancel;
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = R.string.errcode_deny;
			break;
		default:
			result = R.string.errcode_unknown;
			break;
		}

		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		finish();
	}
	/**
	 17      * 处理微信发出的向第三方应用请求app message
	 18      * <p>
	 19      * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
	 20      * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
	 21      * 做点其他的事情，包括根本不打开任何页面
	 22      */
	public void onGetMessageFromWXReq(WXMediaMessage msg) {
		Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
		startActivity(iLaunchMyself);
	}
	/**
	 28      * 处理微信向第三方应用发起的消息
	 29      * <p>
	 30      * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
	 31      * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
	 32      * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
	 33      * 回调。
	 34      * <p>
	 35      * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
	 36      */
	public void onShowMessageFromWXReq(WXMediaMessage msg) {
		if (msg != null && msg.mediaObject != null
				&& (msg.mediaObject instanceof WXAppExtendObject)) {
			WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
			Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
		}
	}
}