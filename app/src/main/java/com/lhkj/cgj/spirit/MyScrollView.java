package com.lhkj.cgj.spirit;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by user on 2017/11/23.
 */

public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return 0;
    }
}
