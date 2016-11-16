package com.chiyu.shopapp.view;

import com.chiyu.shopapp.ui.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
/**
 * 导航栏下面的小圆点  可以随pager滑动而滑动
 * Created by Administrator on 2015/11/21 0021.
 */
public class Dots_View extends FrameLayout {

    private Context context;
    private int checked,unchecked;
    private int left_margin,right_margin;
    private int count;
    private LinearLayout layout;
    private ImageView iv1;

    public Dots_View(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        parseAttrs(attrs);
        initView();
    }

    private void parseAttrs(AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_dots, this, true);

        layout = new LinearLayout(context);
        TypedArray array = getResources().obtainAttributes(attrs,R.styleable.dots);
        checked = array.getResourceId(R.styleable.dots_checked, 0);
        unchecked = array.getResourceId(R.styleable.dots_unchecked, 0);
        left_margin = array.getInteger(R.styleable.dots_left_margin,0);
        right_margin = array.getInteger(R.styleable.dots_right_margin,0);
    }

    private void initView() {
        this.removeAllViews();
        layout.removeAllViews();
        if(count > 0) {
            for(int i=0;i<count ; i ++) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                    ImageView iv = new ImageView(context);
                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    iv.setImageResource(unchecked);
                    params.leftMargin = left_margin;
                    params.weight = 1;
                    iv.setLayoutParams(params);
                    layout.addView(iv);
            }
            addView(layout);
            LayoutParams params = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            iv1 = new ImageView(context);
            iv1.setImageResource(checked);
            params.leftMargin = left_margin+iv1.getWidth();
            iv1.setLayoutParams(params);
            addView(iv1);
        }
    }

    public void setCount(int count) {
        this.count = count;
        initView();
    }

    /**
     *
     * @param offset  Viewpager监听方法中调用该方法传入position+offset即可实现联动
     */

    public void setOffset(float  offset) {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = (int)(((left_margin+iv1.getWidth())*offset)+left_margin);
        iv1.setLayoutParams(params);
    }
}
