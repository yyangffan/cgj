package com.lhkj.cgj.ui.mine;

import android.content.Context;
import android.databinding.ViewDataBinding;

import com.lhkj.cgj.base.ui.adapter.BaseTopAdapter;
import com.lhkj.cgj.databinding.DialogLvitemBinding;
import com.lhkj.cgj.lock.HomeLock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/11/21.
 */

public class RedBaoAdapter extends BaseTopAdapter {
    private ArrayList<HomeLock.CardItem> list;
    private Context context;
    public RedBaoAdapter(Context context, List list, int layoutId, int variableId) {
        super(context, list, layoutId, variableId);
        this.list= (ArrayList<HomeLock.CardItem>) list;
        this.context=context;
    }

    @Override
    protected void subClassTask(ViewDataBinding bindingg, int position) {
        super.subClassTask(bindingg, position);
        DialogLvitemBinding binding= (DialogLvitemBinding) bindingg;
//        binding.textView21.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                context.startActivity(new MyCardActivity().getCallIntent(context,2));
//            }
//        });


    }
}
