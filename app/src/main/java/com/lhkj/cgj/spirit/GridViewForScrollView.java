package com.lhkj.cgj.spirit;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by lizc on 2017/4/18.
 * 类描述：
 * 创建人：lizc
 * 创建时间：2017/4/18 16:28
 * 修改人：lizc
 * 修改时间：2017/4/18 16:28
 * 修改备注：
 */

public class GridViewForScrollView extends GridView{
    public GridViewForScrollView(Context context) {
        super(context);
    }

    public GridViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewForScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
