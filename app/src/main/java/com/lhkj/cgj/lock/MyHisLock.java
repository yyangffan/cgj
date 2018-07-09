package com.lhkj.cgj.lock;

import android.content.Context;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.lhkj.cgj.BR;
import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.adapter.BaseTopAdapter;
import com.lhkj.cgj.databinding.ActivityMyhisBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.network.response.BannerResponse;
import com.lhkj.cgj.network.response.HisResponse;
import com.lhkj.cgj.utils.NetworkImageHolderView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/22.
 * 加油记录
 */

public class MyHisLock {
    public BaseTopAdapter myHisAdapter;
    private ArrayList<MyHisItem> myHisData;
    private ActivityMyhisBinding myhisBinding;
    private ArrayList networkImages;

    public MyHisLock(Context context, ActivityMyhisBinding myhisBinding) {
        myHisData = new ArrayList();
        myHisAdapter = new BaseTopAdapter(context, myHisData, R.layout.myhis_item, BR.myHisItem);
        this.myhisBinding=myhisBinding;
        networkImages=new ArrayList();
        getData();
    }
    private void getData(){
        myHisData.clear();
        HashMap hashMap=new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(HisResponse.class, RunTime.operation.getMine().HIS_LIST, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                HisResponse hisResponse=(HisResponse)data;
                for (HisResponse.Info info: hisResponse.info) {
                    Date date=new Date();
                    date.setTime(Long.parseLong(info.add_time+"000"));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    info.add_time = formatter.format(date);
                    info.money="￥"+info.money;
                    myHisData.add(new MyHisItem(User.getUser().usernike,info.jifen,info.money,info.add_time));
                }
                myHisAdapter.notifyDataSetChanged();
            }
        });
        RunTime.operation.tryPostRefresh(BannerResponse.class, RunTime.operation.getMine().OIL_BANNER, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                if (((BannerResponse) data).info != null) {
                    for (BannerResponse.Info info : ((BannerResponse) data).info) {
                        networkImages.add( info.img_url);
                    }
                    myhisBinding.myhisIms.startTurning(2500);
                    myhisBinding.myhisIms.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                        @Override
                        public NetworkImageHolderView createHolder() {
                            return new NetworkImageHolderView();
                        }
                    }, networkImages);
//                .setPageIndicator(new int[]{com.bigkoo.convenientbanner.R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
                }
            }
        });
    }

    public class MyHisItem {
        public MyHisItem(String userName, String getlll, String payNum, String payTime) {
            this.userName = userName;
            this.getlll = getlll;
            this.payNum = payNum;
            this.payTime = payTime;
        }

        public String userName;
        public String getlll;
        public String payNum;
        public String payTime;
    }
}
