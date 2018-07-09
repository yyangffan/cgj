package com.lhkj.cgj.base.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lizc on 2017/4/20.
 * 类描述：
 * 创建人：lizc
 * 创建时间：2017/4/20 17:08
 * 修改人：lizc
 * 修改时间：2017/4/20 17:08
 * 修改备注：
 */

public class BaseSingleTextAdapter extends BaseAdapter {
    private Context context;
    private ArrayList textList;
    private float textSize = 14f;
    private int textColor = Color.BLACK;
    private int textGravity = Gravity.NO_GRAVITY;
    private int patingTop, patingBottom, patingLeft, patingRight;
    private int hei;
    private TextLisenter lisenter;
    private boolean isFrist;

    public BaseSingleTextAdapter(Context context, ArrayList textList) {
        this.context = context;
        this.textList = textList;
    }

    @Override
    public int getCount() {
        if (textList == null) {
            return 0;
        }
        return textList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv;
        if (convertView == null) {
            tv = new TextView(context);
        } else {
            tv = (TextView) convertView;
        }
        attribute(tv, position);

        return tv;
    }

    private void attribute(TextView tv, int position) {
        tv.setText((String) textList.get(position));
        tv.setTextSize(textSize);
        if (position!=0){
            tv.setTextColor(textColor);
        }
        tv.setGravity(textGravity);
        tv.setPadding(patingLeft, patingTop, patingRight, patingBottom);
        if (hei != 0) {
            tv.setHeight(hei);
        }
        if (lisenter != null) {
            lisenter.subView(tv, position);
        }
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextGravity(int textGravity) {
        this.textGravity = textGravity;
    }

    public void setPating(int patingTop, int patingBottom, int patingLeft, int patingRight) {
        this.patingTop = patingTop;
        this.patingBottom = patingBottom;
        this.patingLeft = patingLeft;
        this.patingRight = patingBottom;
    }

    public void setHei(int hei) {
        this.hei = hei;
    }

    public void setLisenter(TextLisenter lisenter) {
        this.lisenter = lisenter;
    }

    public interface TextLisenter {
        void subView(TextView tv, int position);
    }

}
