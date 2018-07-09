package com.lhkj.cgj.ui.shop;

import android.content.Context;
import android.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.adapter.BaseTopAdapter;
import com.lhkj.cgj.databinding.ItemShopExchangeBinding;

import java.util.List;

/**
 * Created by 浩琦 on 2017/8/11.
 */

public class ShopExchangeAdapter extends BaseTopAdapter {

    public ShopExchangeAdapter(Context context, List list, int layoutId, int variableId) {
        super(context, list, layoutId, variableId);
    }

    @Override
    protected void subClassTask(ViewDataBinding binding, int position) {
        super.subClassTask(binding, position);
        ItemShopExchangeBinding shopExchangeBinding = (ItemShopExchangeBinding) binding;
        if (((ShopExchangeActivity.ShopExchangeItem) list.get(position)).exchangeIcon == null) {
            shopExchangeBinding.exchaneIms.setImageResource(R.mipmap.def_lll_text);
            this.notifyDataSetChanged();
        } else {
            Glide.with(context).load(((ShopExchangeActivity.ShopExchangeItem) list.get(position)).exchangeIcon).into(shopExchangeBinding.exchaneIms);
        }
    }
}
