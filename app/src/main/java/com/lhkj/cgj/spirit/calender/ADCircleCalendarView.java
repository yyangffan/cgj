package com.lhkj.cgj.spirit.calender;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lhkj.cgj.R;

import java.util.List;

/**
 * Created by Administrator on 2016/8/7.
 */
public class ADCircleCalendarView extends LinearLayout {

    private WeekView weekView;
    private ADCircleMonthView circleMonthView;
    private TextView textViewYear, textViewMonth;
    private DayData firstDay, moveDay, afterDay;
    private Context context;
    private LayoutParams llParams;

    public ADCircleCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(LinearLayout.VERTICAL);
        llParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(context).inflate(R.layout.display_grid_date, null);
        weekView = new WeekView(context, null);
        circleMonthView = new ADCircleMonthView(context, null);
        addView(view, llParams);
//        addView(weekView, llParams);
        addView(circleMonthView, llParams);
        //  textViewYear = (TextView) view.findViewById(R.id.year);
        textViewMonth = (TextView) view.findViewById(R.id.month);


    }

    private String toShowString(int i) {
        String ShowMonth[] = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
        return ShowMonth[i];
    }

    public void LeftMonth(){
        circleMonthView.onLeftClick();
    }
    public void RightMonth(){
        circleMonthView.onRightClick();
    }

    //多选
    public void setDayData(DayData firstDay, DayData afterDay) {

        circleMonthView.setDayArr(firstDay, afterDay);
    }

    public void prpa(){
        textViewMonth.setText((circleMonthView.getSelMonth() + 1) + "月签到表");
    }
    /**
     * 设置日历点击事件
     *
     * @param dateClick
     */
    public void setDateClick(MonthView.IDateClick dateClick) {
        circleMonthView.setDateClick(dateClick);
    }

    public void setWeekStart(int dayIndex) {
        circleMonthView.setStartDay(dayIndex);
    }

    /**
     * 设置日历是否有下按钮
     *
     * @param
     */
    public void setBottomView() {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setHorizontalGravity(LinearLayout.HORIZONTAL);
        LayoutParams ls = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        final TextView firstMonth = new TextView(context, null);
        firstMonth.setGravity(Gravity.LEFT);
        firstMonth.setPadding(10, 0, 0, 0);
        firstMonth.setLayoutParams(ls);
        final TextView afterMonth = new TextView(context, null);
        afterMonth.setGravity(Gravity.RIGHT);
        afterMonth.setPadding(0, 0, 0, 10);
        afterMonth.setLayoutParams(ls);
        linearLayout.addView(firstMonth);
        linearLayout.addView(afterMonth);
        addView(linearLayout, llParams);
        circleMonthView.setMonthLisener(new MonthView.IMonthLisener() {
            @Override
            public void setTextMonth() {
                //         textViewYear.setText(circleMonthView.getSelYear()+"年");
                textViewMonth.setText(circleMonthView.getSelYear() + "-" + (circleMonthView.getSelMonth() + 1));
                firstMonth.setText("<<" + circleMonthView.getSelMonth() + "月");
                afterMonth.setText((circleMonthView.getSelMonth() + 2) + "月>>");
            }
        });
        afterMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                circleMonthView.onRightClick();
            }
        });
        firstMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                circleMonthView.onLeftClick();
            }
        });
    }

    /**
     * 设置星期的形式
     *
     * @param weekString 默认值	"日","一","二","三","四","五","六"
     */
    public void setWeekString(String[] weekString) {
        weekView.setWeekString(weekString);
    }

    public void setCalendarInfos(List<CalendarInfo> calendarInfos) {
        circleMonthView.setCalendarInfos(calendarInfos);
//        textViewYear.setText(circleMonthView.getSelYear()+"年");
        textViewMonth.setText((circleMonthView.getSelMonth() + 1) + "月签到表");
    }

    public void setDayTheme(IDayTheme theme) {
        circleMonthView.setTheme(theme);
    }

    public void setWeekTheme(IWeekTheme weekTheme) {
        weekView.setWeekTheme(weekTheme);
    }

    public class DayData {
        public int year;
        public int month;
        public int day;

        public void setData(int year, int month, int day) {
            this.year = year;
            this.month = month - 1;
            this.day = day;
        }

        public void exchange(DayData dayData) {
            if (year > dayData.year || (year == dayData.year && month > dayData.month) || (year == dayData.year && month == dayData.month && day > dayData.day)) {
                moveDay = firstDay;
                firstDay = dayData;
                afterDay = moveDay;
            }
        }

        public void remove() {
            year = 0;
            month = 0;
            day = 0;
        }

        public boolean equls(DayData dayData) {
            if (this.year == dayData.year && this.month == dayData.month && this.day == dayData.day) {
                return true;
            } else {
                return false;
            }
        }

        public boolean selectOut(int year, int month, int day) {
            if (this.year < year) {
                return true;
            } else if (year > this.year) {
                return false;
            } else if (this.month < month) {
                return true;
            } else if (this.month > month) {
                return false;
            } else if (this.day < day) {
                return true;
            } else if (this.day > day) {
                return false;
            } else {
                return false;
            }
        }
    }
}
