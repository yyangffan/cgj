package com.lhkj.cgj.spirit.calender;

import android.graphics.Color;

/**
 * Created by Administrator on 2016/7/31.
 */
public class DefaultWeekTheme implements IWeekTheme {
    @Override
    public int colorTopLinen() {
        return Color.parseColor("#ffffff");
    }

    @Override
    public int colorBottomLine() {
        return Color.parseColor("#ffffff");
    }

    @Override
    public int colorWeekday() {
        return Color.parseColor("#646464");
    }

    @Override
    public int colorWeekend() {
        return Color.parseColor("#646464");
    }

    @Override
    public int colorWeekView() {
        return Color.parseColor("#FFFFFF");
    }

    @Override
    public int sizeLine() {
        return 4;
    }

    @Override
    public int sizeText() {
        return 14;
    }
}
