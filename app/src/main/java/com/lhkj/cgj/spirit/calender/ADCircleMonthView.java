package com.lhkj.cgj.spirit.calender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.lhkj.cgj.R;


/**
 * Created by Administrator on 2016/8/9.
 */
public class ADCircleMonthView extends MonthView {

    public ADCircleMonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void drawLines(Canvas canvas, int rowsCount) {
        int rightX = getWidth();
        Path path;
        float startX = 0;
        float endX = rightX;
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(theme.colorLine());
        for (int row = 1; row <= rowsCount; row++) {
            float startY = row * rowSize;
            path = new Path();
            path.moveTo(startX, startY);
            path.lineTo(endX, startY);
            canvas.drawPath(path, paint);
        }
    }

    @Override
    protected void drawBG(Canvas canvas, int column, int row, int year, int month, int day) {
        float startRecX = columnSize * column + 1;
        float startRecY = rowSize * row + 1;
        float endRecX = startRecX + columnSize - 2 * 1;
        float endRecY = startRecY + rowSize - 2 * 1;
        float cx = (startRecX + endRecX) / 2;
        float cy = (startRecY + endRecY) / 2;
        float radius = columnSize < (rowSize * 0.6) ? columnSize / 2 : (float) (rowSize * 0.6) / 2;
        paint.setColor(theme.colorSelectBG());
        if (theme.selectOut() || (day >= currDay && day == selDay) || (month > currMonth && day == selDay)) { //绘制背景色圆形
            if (tonchMonth == month && tonchYear == year) {
                paint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(cx, cy, radius, paint);
            }
        }
        if (theme.toDay() && day == currDay && currMonth == selMonth) {//今日绘制圆环
            paint.setColor(getResources().getColor(R.color.main_color));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(cx, cy, radius, paint);
        }
    }

    @Override
    protected void drawDecor(Canvas canvas, int column, int row, int year, int month, int day) {
        if (calendarInfos != null && calendarInfos.size() > 0) {
            if (TextUtils.isEmpty(iscalendarInfo(year, month, day))) return;
            paint.setColor(theme.colorDecor());
            paint.setStyle(Paint.Style.FILL);
//            float circleX = (float) (columnSize * column + columnSize * 0.5);
            float circleX = (float) (columnSize * column + columnSize * 0.05);
//            float circleY = (float) (rowSize * row + rowSize * 0.25);
            float circleY = (float) (rowSize * row - rowSize * 0.20);
            if (day == selDay) {//选中日期无事务
                circleY = (float) (rowSize * row + rowSize * 0.1);
            }
//            des上画点
//            canvas.drawCircle(circleX, circleY, theme.sizeDecor(), paint);
//            Bitmap bitmap=getcalendarBit(year, month, day);
//            Log.i("bitmap","year"+year+"month"+month+"day"+day);
//            if (bitmap!=null){
//                canvas.drawBitmap(bitmap,circleX,circleY,paint);
//            }
        }
    }

    @Override
    protected void drawRest(Canvas canvas, int column, int row, int year, int month, int day) {
        if (calendarInfos != null && calendarInfos.size() > 0) {
            float radius = columnSize < (rowSize * 0.6) ? columnSize / 2 : (float) (rowSize * 0.6) / 2;
            for (CalendarInfo calendarInfo : calendarInfos) {
                if (calendarInfo.day == day && calendarInfo.year == year && calendarInfo.month == month + 1) {
                    float restX = columnSize * column + (columnSize + paint.measureText(day + "")) / 2;
                    float restY = rowSize * row + rowSize / 2 - (paint.ascent() + paint.descent()) / 2;
                    if (day == selDay) {
                        restX = columnSize * column + columnSize / 2 + radius;
                    }
                    paint.setStyle(Paint.Style.FILL);
                    if (calendarInfo.rest == 2) {//班
                        paint.setColor(theme.colorWork());
//                        paint.setTextSize(theme.sizeDesc());
                        paint.measureText("班");
                        canvas.drawText("班", restX, restY, paint);
                    } else if (calendarInfo.rest == 1) {//休息
                        paint.setColor(theme.colorRest());
//                        paint.setTextSize(theme.sizeDesc());
                        canvas.drawText("休", restX, restY, paint);
                    }
                }
            }
        }
    }

    @Override
    protected void drawText(Canvas canvas, int column, int row, int year, int month, int day) {
        paint.setTextSize(theme.sizeDay());
        float startX = columnSize * column + (columnSize - paint.measureText(day + "")) / 2;
        float startY = rowSize * row + rowSize / 2 - (paint.ascent() + paint.descent()) / 2;
        paint.setStyle(Paint.Style.STROKE);
        String des = iscalendarInfo(year, month, day);
        if (day == currDay && currDay != selDay && currMonth == selMonth) {//今日的颜色，不是选中的时候
            //正常月，选中其他日期，则今日为红色
            paint.setColor(theme.colorToday());
//            canvas.drawText(day + "", startX, startY, paint);//屏蔽递减事件
        } else {
            if (!TextUtils.isEmpty(des)) {//没选中，但是desc不为空
                int dateY = (int) startY;
                if (getcalendarBit(year, month, day) == null) {
                    paint.setColor(getResources().getColor(R.color.main_color));
                    canvas.drawText(day + "", startX, dateY, paint);
                }
            } else {//des为空
                paint.setColor(theme.colorWeekday());
                canvas.drawText(day + "", startX, startY, paint);
            }
        }
        if (!theme.selectOut() && day < currDay && month == currMonth || month < currMonth) {//小于当前日期变灰
            boolean isDraw = false;
            if (calendarInfos != null && calendarInfos.size() > 0) {
                for (CalendarInfo calendarInfo : calendarInfos) {
                    if (calendarInfo.day == day && calendarInfo.year == year && calendarInfo.month == month + 1) {
                        paint.setColor(getResources().getColor(R.color.main_color));
                        if (getcalendarBit(year, month + 1, day) == null) {
                            canvas.drawText(day + "", startX, startY, paint);
                            isDraw = true;
                        }
                    } else {
                        if (!isDraw) {
                            paint.setColor(getResources().getColor(R.color.gray));
                            canvas.drawText(day + "", startX, startY, paint);
                        }
                    }
                }
            } else {
                paint.setColor(getResources().getColor(R.color.gray));
                canvas.drawText(day + "", startX, startY, paint);
            }
        }
        if (theme.toDay() && day == currDay && month == currMonth && year == currYear) {//当前日期字色
            paint.setColor(getResources().getColor(R.color.white));
            canvas.drawText(day + "", startX, startY, paint);
        }
        //图片
        float circleX = (float) (columnSize * column + columnSize * 0.06);
        float circleY = (float) (rowSize * row - rowSize * 0.19);
        Bitmap bitmap = getcalendarBit(year, month, day);
//        if (bitmap != null) {//杨帆修改如下注释为原来
//            canvas.drawBitmap(bitmap, circleX, circleY, paint);
//        }
        if (bitmap != null) {
            float startX_bit =  (float) (columnSize * column + columnSize * 0.09);
            float startY_bit =  (float) (rowSize * row - rowSize * 0.04);
            canvas.drawBitmap(bitmap, startX_bit, startY_bit, paint);
        }
    }

    @Override
    protected void createTheme() {
        theme = new ADCircleDayTheme();
    }

    @Override
    protected void drawDayArr(Canvas canvas, int column, int row, int year, int month, int day, ADCircleCalendarView.DayData firstDay, ADCircleCalendarView.DayData afterDay) {

        if (firstDay != null || afterDay != null) {
            if (firstDay.year != 0 && afterDay.year != 0) {
                if ((firstDay.year <= year && year <= afterDay.year) && (firstDay.month <= month && month <= afterDay.month) && ((firstDay.month == month && firstDay.day <= day) || (afterDay.month == month && day <= afterDay.day))) {
                    if (firstDay.month == afterDay.month) {
                        if (firstDay.day <= day && day <= afterDay.day) {
                            monthDistinguish(canvas, column, row, year, month, day, firstDay, afterDay);
                        }
                    } else {
                        monthDistinguish(canvas, column, row, year, month, day, firstDay, afterDay);
                    }
                }
            }
        }
    }

    private void monthDistinguish(Canvas canvas, int column, int row, int year, int month, int day, ADCircleCalendarView.DayData firstDay, ADCircleCalendarView.DayData afterDay) {
        float startRecX = columnSize * column;
        float startRecY = rowSize * row + 1;
        float endRecX = startRecX + columnSize;
        float endRecY = startRecY + rowSize - 2 * 1;
        float cx = (startRecX + endRecX) / 2;
        float cy = (startRecY + endRecY) / 2;
        float radius = columnSize < (rowSize * 0.6) ? columnSize / 2 : (float) (rowSize * 0.6) / 2;
        if (theme.selectOut() || (!firstDay.selectOut(currYear, currMonth, currDay) && !afterDay.selectOut(currYear, currMonth, currDay))) {
            paint.setStyle(Paint.Style.FILL);
            if (firstDay.year == year && firstDay.month == month && firstDay.day == day) {
                paint.setColor(theme.colorSelectBG());
                canvas.drawCircle(cx, cy, radius, paint);
                if (!firstDay.equls(afterDay)) {
                    paint.setColor(theme.rectColor());
                    canvas.drawRect(cx, cy - radius, endRecX, cy + radius, paint);
                }
                return;
            }
            if (afterDay.year == year && afterDay.month == month && afterDay.day == day) {
                paint.setColor(theme.colorSelectBG());
                canvas.drawCircle(cx, cy, radius, paint);
                paint.setColor(theme.rectColor());
                canvas.drawRect(startRecX, cy - radius, cx, cy + radius, paint);
                return;
            }
            if (!firstDay.equls(afterDay)) {
                paint.setColor(theme.rectColor());
                canvas.drawRect(startRecX, cy - radius, endRecX, cy + radius, paint);
            }
            if (firstDay.month != afterDay.month) {
                canvas.drawRect(startRecX, cy - radius, endRecX, cy + radius, paint);
            }
        }
    }
}
