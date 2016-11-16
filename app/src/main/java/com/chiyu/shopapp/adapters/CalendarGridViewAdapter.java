package com.chiyu.shopapp.adapters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.chiyu.shopapp.bean.Line_DateEntity;
import com.chiyu.shopapp.ui.R;
import com.chiyu.shopapp.utils.DateUtil;

public class CalendarGridViewAdapter extends BaseAdapter {

	private Calendar calStartDate = Calendar.getInstance();// 当前显示的日历
	private Calendar calSelected = Calendar.getInstance(); // 选择的日历

	private TextView txtToDay1, txt1;
	private ArrayList<Line_DateEntity> line_Calendars = new ArrayList<Line_DateEntity>();

	public ArrayList<Line_DateEntity> getLine_Calendars() {
		return line_Calendars;
	}

	public void setLine_Calendars(ArrayList<Line_DateEntity> line_Calendars) {
		this.line_Calendars = line_Calendars;
		notifyDataSetChanged();
	}

	public void setSelectedDate(Calendar cal) {
		calSelected = cal;
		notifyDataSetChanged();
	}

	private Calendar calToday = Calendar.getInstance(); // 今日
	private int iMonthViewCurrentMonth = 0; // 当前视图月

	// 根据改变的日期更新日历
	// 填充日历控件用
	private void UpdateStartDateForMonth() {
		calStartDate.set(Calendar.DATE, 1); // 设置成当月第一天
		iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);// 得到当前日历显示的月

		// 星期一是2 星期天是1 填充剩余天数
		int iDay = 0;
		int iFirstDayOfWeek = Calendar.MONDAY;
		int iStartDay = iFirstDayOfWeek;
		if (iStartDay == Calendar.MONDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
			if (iDay < 0)
				iDay = 6;
		}
		if (iStartDay == Calendar.SUNDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
			if (iDay < 0)
				iDay = 6;
		}
		calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);

		calStartDate.add(Calendar.DAY_OF_MONTH, -1);// 周日第一位

	}

	ArrayList<Date> titles;

	private ArrayList<Date> getDates() {

		UpdateStartDateForMonth();

		ArrayList<Date> alArrayList = new ArrayList<Date>();

		for (int i = 1; i <= 42; i++) {
			alArrayList.add(calStartDate.getTime());
			calStartDate.add(Calendar.DAY_OF_MONTH, 1);
		}

		return alArrayList;
	}

	private Activity activity;
	Resources resources;

	public CalendarGridViewAdapter(Activity a, Calendar cal) {
		calStartDate = cal;
		activity = a;
		resources = activity.getResources();
		titles = getDates();
	}

	public CalendarGridViewAdapter(Activity a) {
		activity = a;
		resources = activity.getResources();
	}

	@Override
	public int getCount() {
		if (titles == null || titles.size() <= 0) {
			return 0;
		}
		return titles.size();
	}

	@Override
	public Object getItem(int position) {
		if (titles == null || titles.size() <= 0 || position < 0
				|| position >= titles.size()) {
			return null;
		}
		return titles.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings({ "null" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout iv = new LinearLayout(activity);
		iv.setId(position + 5000);
		LinearLayout imageLayout = new LinearLayout(activity);
		imageLayout.setOrientation(LinearLayout.VERTICAL);
		iv.setGravity(Gravity.CENTER);
		iv.setOrientation(LinearLayout.HORIZONTAL);
		iv.setBackgroundColor(resources.getColor(R.color.white));

		Date myDate = (Date) getItem(position);
		Calendar calCalendar = Calendar.getInstance();
		calCalendar.setTime(myDate);

		final int iMonth = calCalendar.get(Calendar.MONTH);
		final int iDay = calCalendar.get(Calendar.DAY_OF_WEEK);

		// 判断周六周日
		iv.setBackgroundColor(resources.getColor(R.color.white));
		// if (iDay == 7) {
		// // 周六
		// iv.setBackgroundColor(resources.getColor(R.color.text_6));
		// } else if (iDay == 1) {
		// // 周日
		// iv.setBackgroundColor(resources.getColor(R.color.text_6));
		// } else {
		//
		// }
		// 判断周六周日结束
		TextView txtToDay = new TextView(activity);// 日本老黄历
		txtToDay.setGravity(Gravity.CENTER_HORIZONTAL);
		txtToDay.setTextSize(15);

		// CalendarUtil calendarUtil = new CalendarUtil(calCalendar);
		// if (equalsDate(calToday.getTime(), myDate)) {
		// // 当前日期
		// // iv.setBackgroundColor(resources.getColor(R.color.event_center));
		// txtToDay.setText(calendarUtil.toString());
		// } else {
		// txtToDay.setText(calendarUtil.toString());
		// }

		// 设置背景颜色
		if (equalsDate(calSelected.getTime(), myDate)) {
			// 选择的
			iv.setBackgroundColor(resources.getColor(R.color.white));
//			System.out.println("ssssssssssscalToday.getTime()"
//					+ calToday.getTime());
		}
		// 价格
		txt1 = new TextView(activity);// 日本老黄历
		txt1.setGravity(Gravity.CENTER);
		txt1.setTextColor(resources.getColor(R.color.juse));

		// 数量
		txtToDay1 = new TextView(activity);// 日本老黄历
		txtToDay1.setGravity(Gravity.CENTER);
		txtToDay1.setTextColor(resources.getColor(R.color.yuwei));
//		System.out.println("line_Calendars===-----适配器----------"
//				+ line_Calendars.size());
		if (line_Calendars != null && line_Calendars.size() > 0) {
			for (int i = 0; i < line_Calendars.size(); i++) {
				// String re_StrTime = null;
				// SimpleDateFormat sdf = new SimpleDateFormat(
				// "yyyy-MM-dd HH:mm:ss");
				// long lcc_time =
				// Long.valueOf(line_Calendars.get(i).getGoTime());
				// re_StrTime = sdf.format(new Date(lcc_time * 1000L));
//				System.out.println("出团日期--------------"
//						+ line_Calendars.get(i).getGoTime());
				Date date = DateUtil.getChooseDate(line_Calendars.get(i)
						.getGoTime());
				if (equalsDate(date, myDate)) {
					if (line_Calendars.get(i).getAdultPrice() > 9999) {
						txt1.setTextSize(10);
						txt1.setText("￥"
								+ line_Calendars.get(i).getAdultPrice());
					} else {
						txt1.setTextSize(13);
						txt1.setText("￥"
								+ line_Calendars.get(i).getAdultPrice());
					}

					// } else if (line_Calendars.get(i).getIstakechild() == 1) {
					// txt1.setText("￥" +
					// line_Calendars.get(i).getChildPrice());

					// } else if (line_Calendars.get(i).getIstakebaby() == 1) {
					// txt1.setText("￥" + line_Calendars.get(i).getBabyPrice());
					// }
					String dd = txt1.getText().toString();
					if (dd == null && dd.equals("")) {
						txt1.setTextSize(13);
						// if (line_Calendars.get(i).getIstakeadult() == 1) {
						txt1.setText("￥"
								+ line_Calendars.get(i).getAdultPrice());

						// } else if (line_Calendars.get(i).getIstakechild() ==
						// 1) {
						// txt1.setText("￥"
						// + line_Calendars.get(i).getChildPrice());

						// } else if (line_Calendars.get(i).getIstakebaby() ==
						// 1) {
						// txt1.setText("￥" +
						// line_Calendars.get(i).getBabyPrice());
						// }
					}

					// String rcount = line_Calendars.get(i).getPerson()+"";
					// if (rcount != null && !rcount.equals("")) {
					int num = line_Calendars.get(i).getPerson();
					if(line_Calendars.get(i).getIsReceive()==0){
						txtToDay1.setTextSize(13);
						txtToDay1.setText((num-line_Calendars.get(i).getPersonOrder())+"");
					}else{
						txtToDay1.setTextSize(13);
						txtToDay1.setText(line_Calendars.get(i).getLeaveseats()+"");
					}
//					txtToDay1.setTextSize(13);
//					txtToDay1.setText(num+"");
					
					
//					if (num > 9) {
//						
//						txtToDay1.setTextSize(13);
//						txtToDay1.setText(num+"");
////						txtToDay1.setText("充足");
//
//					} else if (num == 0) {
//						txtToDay1.setTextSize(13);
//						txtToDay1.setText("客满");
//					} else {
//						txtToDay1.setTextSize(13);
//						txtToDay1.setText(num + "");
//						// }
//					}
				}
			}

		}

		// 设置背景颜色结束

		// 日期开始
		TextView txtDay = new TextView(activity);// 日期
		txtDay.setGravity(Gravity.CENTER_HORIZONTAL);
		txtDay.setTextSize(15);
		// 判断是否是当前月
		if (iMonth == iMonthViewCurrentMonth) {
			txtToDay.setTextColor(resources.getColor(R.color.ToDayText));
			txtDay.setTextColor(resources.getColor(R.color.Text));
		} else {
			txtDay.setTextColor(resources.getColor(R.color.noMonth));
			txtToDay.setTextColor(resources.getColor(R.color.noMonth));
		}

		int day = myDate.getDate(); // 日期
		txtDay.setText(String.valueOf(day));
		txtDay.setId(position + 500);
		iv.setTag(myDate);

		LayoutParams lp = new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		iv.addView(txtDay, lp);

		// 2014-06-27
		LayoutParams lp1 = new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		iv.addView(txt1, lp1);

		LayoutParams lp2 = new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		iv.addView(txtToDay1, lp2);

		return iv;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	private Boolean equalsDate(Date date1, Date date2) {

		if (date1.getYear() == date2.getYear()
				&& date1.getMonth() == date2.getMonth()
				&& date1.getDate() == date2.getDate()) {
			return true;
		} else {
			return false;
		}
	}
}
