package com.chiyu.shopapp.adapters;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by steven on 2015/11/17.
 */
public  abstract  class MyAbsAdapter<T> extends BaseAdapter {
    private Context context;
    private List<T> datas;
    private int[] resid;
    //因为是多布局的封装适配器，所以需要传入一个布局数组resid，所以适配器的参数要使用可变参数以便于传入一个数组。
    public MyAbsAdapter(Context context, int... resid){
        this.context = context;
        this.resid = resid;
        datas = new ArrayList<T>();
    }
    //给适配器设置数据。
    public void setDatas(List<T> datas){
        this.datas.clear();
        addDatas(datas);
    }
    //该方法应该是在分页加载的时候，页数page改变的时候调用该方法将后面的数据加到适配器中。
    public void addDatas(List<T> datas){
        this.datas.addAll(datas);
        this.notifyDataSetChanged();
    }
    //获得适配器里面的数据列表
    protected List<T> getDatas(){
        return datas;
    }
    public List<T> getData(){
    	return this.datas;
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    //getView方法的目的在于将外面传入的数据和传入的布局链接在一起。
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView != null){
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(resid[getItemViewType(position)], null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        //从数据源中获得数据，依次放入ViewHodler中的控件里，此处由于不确定传入的布局是哪一个布局，所以具体的绑定数据的方法让之类去实现。
        bindDatas(viewHolder, datas.get(position));
        myBindDatas(viewHolder, datas.get(position),position);
        return convertView;
    }
    public abstract void myBindDatas(ViewHolder viewHolder, T data , int position);
    public abstract void bindDatas(ViewHolder viewHolder, T data);

    /**
     * ViewHolder主要是用来缓存布局页面中的子控件，避免下一次使用时还需要findViewById
     */
    public class ViewHolder{
        View layoutView;
        Map<Integer, View> mapCache = new HashMap<Integer,View>();

        public ViewHolder(View layoutView) {
            this.layoutView = layoutView;
        }

        public View getView(int id){
            View view = null;
            if(mapCache.containsKey(id)){
                view = mapCache.get(id);
            } else {
                view = layoutView.findViewById(id);
                mapCache.put(id, view);
            }
            return view;
        }
    }


    /**
     * 强制要求使用这个封装适配器的数据源中的实体类里面必须有一个名字叫做“type”的属性
     * Class c = data.getClass();
     * Class c = T.class;
     * Class c = Class.forName("com.xxx.xxx.xxx");
     * @param position
     * @return 重写getItemViewType()方法的目的在于获得item布局的类型，该类型存在item对应的data对象中，
     * 使用反射的方式将每一个item对应的data对象type属性的反射出来返回。以便于在引入布局的时候进行判断。
     * 该方法的返回结果确定position对应的位置item应该具体加入哪一种布局。
     *
     */
    @Override
    public int getItemViewType(int position) {
        T data = datas.get(position);
        Class c = data.getClass();
        try {
            Field field = c.getDeclaredField("type");
            field.setAccessible(true);
            return field.getInt(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    //getViewTypeCount()方法是获得item布局累种类，那就是外面传入的布局数据的元素的个数，就是数据的长度。
    @Override
    public int getViewTypeCount() {
        return resid.length;
    }
}
