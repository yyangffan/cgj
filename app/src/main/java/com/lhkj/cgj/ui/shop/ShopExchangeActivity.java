package com.lhkj.cgj.ui.shop;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lhkj.cgj.BR;
import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityShopExchangeBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.network.response.SignCalenderReponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/8/10.
 * 商品兑换列表
 * 兑换记录
 */

public class ShopExchangeActivity extends BaseActivity {
    private ArrayList<ShopExchangeItem> exchanes;
    private ShopExchangeAdapter exchaneAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityShopExchangeBinding shopExchangeBinding = DataBindingUtil.setContentView(this, R.layout.activity_shop_exchange);
        shopExchangeBinding.appBar.setAppBarLock(new AppBarLock(this, R.string.exchange));
        exchanes = new ArrayList();
        exchaneAdapter = new ShopExchangeAdapter(this, exchanes, R.layout.item_shop_exchange, BR.shopExchangeItem);
        shopExchangeBinding.exchaneList.setAdapter(exchaneAdapter);
        init();
    }

    private void init() {
        exchanes.clear();
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefreshF(SignCalenderReponse.class, RunTime.operation.getShop().MY_EXCHANGE, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                if (id == 200) {
                    SignCalenderReponse signCalenderReponse = (SignCalenderReponse) data;
                    for (SignCalenderReponse.InfoBean info : signCalenderReponse.info) {
                        Date date = new Date();
                        date.setTime(Long.parseLong(info.time + "000"));
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        info.time = formatter.format(date);
                        if (info.type.equals("1")) {
                            exchanes.add(new ShopExchangeItem(info.goods_info.img_url, info.goods_info.goods_name,
                                    info.goods_info.string, info.time, info.type, info.goods_info.money));
                        } else {
                            exchanes.add(new ShopExchangeItem(null, info.goods_info.goods_name,
                                    "领取积分 " + info.jifen, info.time, info.type, info.goods_info.money));
                        }
                    }
                    exchaneAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public class ShopExchangeItem {
        public ShopExchangeItem(String exchangeIcon, String exchangeName, String exchangeLll,
                                String exchangeTime, String exchangeType, String exchangeMoney) {
            if (exchangeType.equals("2")) {
                this.exchangeIcon = null;
            } else {
                this.exchangeIcon = exchangeIcon;
            }
            if (exchangeName == null) {
                this.exchangeName = "积分";
            } else {
                this.exchangeName = exchangeName;
            }
            this.exchangeLll = exchangeLll;
            this.exchangeTime = "兑换时间 " + exchangeTime;
            this.exchangeType = exchangeType;
            if (exchangeMoney != null) {
                this.exchangeMoney = exchangeMoney;
            }
        }

        public String exchangeIcon;
        public String exchangeName;
        public String exchangeLll;
        public String exchangeTime;
        public String exchangeType;
        public String exchangeMoney;

    }
}
