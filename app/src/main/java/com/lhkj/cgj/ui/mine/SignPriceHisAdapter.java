package com.lhkj.cgj.ui.mine;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.adapter.BaseTopAdapter;
import com.lhkj.cgj.databinding.ItemSignpriceHisBinding;

import java.util.List;

/**
 * Created by 浩琦 on 2017/8/10.
 */

public class SignPriceHisAdapter extends BaseTopAdapter {

    public SignPriceHisAdapter(Context context, List list, int layoutId, int variableId) {
        super(context, list, layoutId, variableId);
    }

    @Override
    protected void subClassTask(ViewDataBinding binding, int position) {
        super.subClassTask(binding, position);
        ItemSignpriceHisBinding signpriceHisBinding = (ItemSignpriceHisBinding) binding;
        if (((SignPriceHisActivity.SignPriceHisItem) list.get(position)).priceIcon == null) {
            signpriceHisBinding.hisIms.setImageResource(R.mipmap.def_lll_text);
        } else {
            Glide.with(context).load(((SignPriceHisActivity.SignPriceHisItem) list.get(position)).priceIcon).into(signpriceHisBinding.hisIms);
        }
        if (!((SignPriceHisActivity.SignPriceHisItem) list.get(position)).receive) {
            signpriceHisBinding.tvReceive.setTextColor(ContextCompat.getColor(context, R.color.main_color));
        }else {
            signpriceHisBinding.tvReceive.setTextColor(ContextCompat.getColor(context, R.color.text_gray));
        }

        this.notifyDataSetChanged();
    }
}
