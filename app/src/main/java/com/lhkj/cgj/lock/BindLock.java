package com.lhkj.cgj.lock;

import android.content.Context;
import android.databinding.BaseObservable;

import com.lhkj.cgj.base.ui.adapter.BaseSingleTextAdapter;
import com.lhkj.cgj.databinding.ActivityBindBinding;

import java.util.ArrayList;

/**
 * Created by 浩琦 on 2017/6/21.
 * 绑定加油站
 */

public class BindLock {
    public BindData bindData;
    private Context context;
    private ActivityBindBinding bindBinding;
    private BaseSingleTextAdapter bindAdapter;
    private ArrayList bindList;
    public BindLock(Context context, ActivityBindBinding bindBinding){
        this.bindBinding=bindBinding;
        this.context=context;
        bindList=new ArrayList();
        bindData=new BindData();
        bindAdapter=new BaseSingleTextAdapter(context,bindList);
    }

    public void selectBind(){
//        final PopManager popManager=new PopManager(context);
//        final SmallListBinding smallListBinding= ((SmallListBinding)popManager.showPop(bindBinding.bindEnter,R.layout.small_list));
//        smallListBinding.setSmallAdapter(bindAdapter);
//        smallListBinding.smallList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                popManager.stop();
//                bindData.selectBind=(String) bindList.get(position);
//                RunTime.operation.getHome().bindOil(bindData.selectBind, new Operation.noListener() {
//                    @Override
//                    public void tryReturn(boolean isComm) {
//                        bindData.notifyChange();
//                    }
//                });
//            }
//        });
    }

    public class BindData extends BaseObservable{
        public String selectBind;
    }
}
