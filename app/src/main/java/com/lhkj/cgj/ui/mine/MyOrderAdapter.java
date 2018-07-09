package com.lhkj.cgj.ui.mine;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.bumptech.glide.Glide;
import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.adapter.BaseTopAdapter;
import com.lhkj.cgj.databinding.MyorderItemBinding;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.lock.MyOrderLock;
import com.lhkj.cgj.ui.other.MyQrCodeActivity;
import com.lhkj.cgj.ui.shop.ShopDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 浩琦 on 2017/6/21.
 */

public class MyOrderAdapter extends BaseTopAdapter {
    private ArrayList<MyOrderLock.MyOrderItem> list;
    private Context context;

    public MyOrderAdapter(Context context, List list, int layoutId, int variableId) {
        super(context, list, layoutId, variableId);
        this.list = (ArrayList) list;
        this.context = context;
    }

    @Override
    protected void subClassTask(ViewDataBinding binding, final int position) {
        super.subClassTask(binding, position);
        MyorderItemBinding myorderItemBinding = (MyorderItemBinding) binding;
        Glide.with(context).load(list.get(position).orderUrl).into(myorderItemBinding.imageView);
        if (list.get(position).isPay) {
            myorderItemBinding.myorderEnter.setBackgroundColor(context.getResources().getColor(R.color.white));
            myorderItemBinding.myorderEnter.setTextColor(context.getResources().getColor(R.color.gray_text));
        } else {
            myorderItemBinding.myorderEnter.setBackground(context.getResources().getDrawable(R.drawable.maintext_radius));
            myorderItemBinding.myorderEnter.setTextColor(context.getResources().getColor(R.color.main_color));
        }

        myorderItemBinding.myorderEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).orderText.equals("去支付")) {
//                    PayUtils payUtils = new PayUtils();
//                    payUtils.tryPay(context, list.get(position).orderNum, list.get(position).type);
                    RunTime.setData(RunTime.SHOPID, list.get(position).goods_id);
                    RunTime.setData(RunTime.SHOP_TYPE, 1);
                    RunTime.setData(RunTime.ORDER_ID, list.get(position).orderId);
                    RunTime.setData(RunTime.ORDER_NUM, list.get(position).orderNum);
                    ((MyOrderActivity) context).startActivity(ShopDetailsActivity.class);
                    return;
                }
                if (!list.get(position).isPay) {
                    RunTime.setData(RunTime.CODE_TYPE, MyQrCodeActivity.CODE_PAY);
                    RunTime.setData(RunTime.ORDER_ID, list.get(position).orderId);
                    ((MyOrderActivity) context).startActivity(MyQrCodeActivity.class);
                }
            }
        });
    }
}
