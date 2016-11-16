package com.chiyu.shopapp.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chiyu.shopapp.constants.MyApp;
import com.chiyu.shopapp.utils.NetworkUtils;
import com.hyphenate.easeui.controller.EaseUI;

/**
 * Created by xmtang on 2016/2/29.
 * 基类，封装标题栏
 */
public class BaseActivity extends FragmentActivity {
    private RelativeLayout rltTitle;
    public ImageView ivBack;
    private TextView tvTitle;
    private TextView tvSubtitle;
    public ImageView ivTitleRight;
    public TextView tvTitleRight;
    public TextView tvTitleRightSecond;
    private View viewTopPlaceHolder;
    /**
     * 网络连接状态
     */
    public boolean ISCONNECTED;
    public static ArrayList<Activity> activities = new ArrayList<Activity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ISCONNECTED = NetworkUtils.isNetworkAvailable(this);
        if (!ISCONNECTED) {
            Toast.makeText(this, "当前网络连接不可用", Toast.LENGTH_SHORT).show();
        }
        activities.add(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.guangyu);
        MyApp.getInstance().addActivity(this);
        rltTitle = (RelativeLayout) findViewById(R.id.layout_title);
        ivBack = (ImageView) findViewById(R.id.iv_title_left);
        tvTitle = (TextView) findViewById(R.id.tv_title_center);
        tvSubtitle = (TextView) findViewById(R.id.tv_title_center_sub);
        ivTitleRight = (ImageView) findViewById(R.id.iv_title_right);
        tvTitleRight = (TextView) findViewById(R.id.tv_title_right);
        tvTitleRightSecond = (TextView) findViewById(R.id.tv_title_right_left);
        viewTopPlaceHolder = findViewById(R.id.view_title_place_holder);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        LinearLayout lltContainer = (LinearLayout) findViewById(R.id.llt_container);
        View view = View.inflate(this, layoutResID, null);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        lltContainer.addView(view, lp);
    }
    /**
     * 设置PageTitle 的可见性
     *
     * @param visibility
     */
    public void setPageTitleVisibility(int visibility) {
        rltTitle.setVisibility(visibility);
    }

    public void setPageTitle(String title) {
        tvTitle.setText(title);
    }

    public void setPageTitleColor(int color) {
        tvTitle.setTextColor(color);
    }

    public void setPageTitle(int resid) {
        tvTitle.setText(resid);
    }

    public void setPageSubTitle(String title) {
        tvSubtitle.setVisibility(View.VISIBLE);
        tvSubtitle.setText(title);
    }

    public void setPageSubTitle(int resid) {
        tvSubtitle.setVisibility(View.VISIBLE);
        tvSubtitle.setText(resid);
    }

    public void setPageBackNavigatorVisibility(int visibility) {
        ivBack.setVisibility(visibility);
    }

    public void setPageBackNavigatorResource(int resid) {
        ivBack.setImageResource(resid);
    }

    public void setPageBackNavigatorImage(Drawable drawable) {
        ivBack.setImageDrawable(drawable);
    }

    public void setPageRightNavigatorResource(int resid) {
        ivTitleRight.setVisibility(View.VISIBLE);
        ivTitleRight.setImageResource(resid);
    }

    public void setPageRightNavigatorImage(Drawable drawable) {
        ivTitleRight.setVisibility(View.VISIBLE);
        ivTitleRight.setImageDrawable(drawable);
    }

    public void setPageRightNavigatorText(int resid) {
        ivTitleRight.setVisibility(View.GONE);
        tvTitleRight.setVisibility(View.VISIBLE);
        tvTitleRight.setText(resid);
    }

    public void setPageRightNavigatorText(String txt) {
        ivTitleRight.setVisibility(View.GONE);
        tvTitleRight.setVisibility(View.VISIBLE);
        tvTitleRight.setText(txt);
    }

    public void setPageRightNavigatorTextVisibility(int visibility) {
        tvTitleRight.setVisibility(visibility);
    }

    public void setPageTitleBackgroundColor(int color) {
        rltTitle.setBackgroundColor(color);
    }

    public void setPageTitleHeight(int heightPx) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) rltTitle
                .getLayoutParams();
        lp.height = heightPx;
    }

    public void setTopPlaceHolderViewVisibility(int visibility) {
        viewTopPlaceHolder.setVisibility(visibility);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        activities.remove(this);
    }
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	EaseUI.getInstance().getNotifier().reset();
    }
}
