package com.lhkj.cgj.spirit.calender;

import android.graphics.Color;

/**
 * Created by Administrator on 2016/8/9.
 */
public class ADCircleDayTheme implements IDayTheme {
    @Override
    public int colorSelectBG() {
        return Color.parseColor("#F84764");
    }

    @Override
    public int colorSelectDay() {
        return Color.parseColor("#4F4F4F");
    }

    @Override
    public int colorToday() {
        return Color.parseColor("#FFFFFF");
    }

    @Override
    public int colorMonthView() {
        return Color.parseColor("#FFFFFF");
    }

    @Override
    public int colorWeekday() {
        return Color.parseColor("#4F4F4F");
    }

    @Override
    public int colorWeekend() {
        return Color.parseColor("#FFFFFF");
    }

    @Override
    public int colorDecor() {
        return Color.parseColor("#4AB9AE");
    }

    @Override
    public int colorRest() {
        return Color.parseColor("#2AC5C8");
    }

    @Override
    public int colorWork() {
        return Color.parseColor("#ffffff");
    }

    @Override
    public int colorDesc() {
        return Color.parseColor("#FFFFFF");
    }

    @Override
    public int sizeDay() {
        return 45;
    }

    @Override
    public int sizeDesc() {
        return 25;
    }

    @Override
    public int sizeDecor() {
        return 6;
    }

    @Override
    public int dateHeight() {
        return 100;
    }

    @Override
    public int colorLine() {
        return Color.parseColor("#ffffff");
    }

    @Override
    public int smoothMode() {
        return 1;
    }

    @Override
    public boolean selectOut() {
        return false;
    }

    @Override
    public boolean toDay() {
        return true;
    }

    @Override
    public boolean isSmooth() {
        return false;
    }

    @Override
    public int rectColor() {
        return  Color.parseColor("#503bc3c2");
    }

}
