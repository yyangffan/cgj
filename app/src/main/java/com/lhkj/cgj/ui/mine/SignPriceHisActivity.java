package com.lhkj.cgj.ui.mine;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.lhkj.cgj.BR;
import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivitySignpriceHisBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.network.response.SignPriceHisRepocse;
import com.lhkj.cgj.ui.other.MyQrCodeActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/8/10.
 * 领取记录
 */

public class SignPriceHisActivity extends BaseActivity {
    private ArrayList<SignPriceHisItem> signprices;
    private SignPriceHisAdapter signpriceAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignpriceHisBinding signpriceHisBinding = DataBindingUtil.setContentView(this, R.layout.activity_signprice_his);
        signprices = new ArrayList<>();
        signpriceHisBinding.appBar.setAppBarLock(new AppBarLock(this, R.string.sign_price_list));
        signpriceAdapter = new SignPriceHisAdapter(this, signprices, R.layout.item_signprice_his, BR.signPriceHisItem);
        signpriceHisBinding.signHisList.setAdapter(signpriceAdapter);
        signpriceHisBinding.signHisList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (signprices.get(position).priceState.equals("1")) {
                    getCalenderPrices(signprices.get(position).priceId, signprices.get(position).priceIcon);
                } else {
                    Toast.makeText(SignPriceHisActivity.this, "您已经领取此礼品", Toast.LENGTH_SHORT).show();
                }
            }
        });
        initUrl();
    }

    // 判断如果是商品跳转二维码
    protected void getCalenderPrices(String priceId, String url) {
        RunTime.setData(RunTime.CODE_TYPE, MyQrCodeActivity.CODE_SIGN);
        RunTime.setData(RunTime.SIGN_ID, priceId);
        RunTime.setData(RunTime.SIGN_URL, url);
        startActivity(MyQrCodeActivity.class);
    }

    private void initUrl() {
        signprices.clear();
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefreshF(SignPriceHisRepocse.class, RunTime.operation.getMine().MY_PRICE_LIST, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                if (id == 200) {
                    SignPriceHisRepocse signPriceHisRepocse = (SignPriceHisRepocse) data;
                    for (SignPriceHisRepocse.InfoBean info : signPriceHisRepocse.info) {
                        // 原来用的use_time
                        if (info.send_time != null) {
                            Date date = new Date();
                            date.setTime(Long.parseLong(info.send_time + "000"));
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            info.send_time = formatter.format(date);
                        }
                        signprices.add(new SignPriceHisItem(info.id, info.goods_img, info.goods_name,
                                info.jifen_num, info.send_time, info.state, info.type));
                    }
                    signpriceAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initUrl();
    }

    public class SignPriceHisItem {
        public SignPriceHisItem(String priceId, String priceIcon, String priceName, String priceLll,
                                String priceTime, String priceState, String priceTpye) {
            this.priceId = priceId;
            this.priceIcon = priceIcon;
            if (priceName == null) {
                this.priceName = "积分";
            } else {
                this.priceName = priceName;
            }
            if (priceLll == null) {
                priceLll = "0";
            }
            if (priceTime == null) {
                priceTime = "暂未领取";
                receive = false;
            }
            this.priceLll = "领取积分：" + priceLll;
            this.priceTime = "领取时间：" + priceTime;
            this.priceState = priceState;
            this.priceTpye = priceTpye;

            if (priceName == null) {
                this.receiveName = "领取积分：" + priceLll;
            } else {
                this.receiveName = priceName;
            }
            this.receiveTime = "领取时间：" + priceTime;

            if ("1".equals(priceState)) {
                state = "未兑换";
            } else {
                state = "已兑换";
            }
        }

        public String priceId;
        public String priceIcon;
        public String priceName;
        public String priceLll;
        public String priceTime;
        public String priceState;
        public String priceTpye;
        public boolean receive = true;

        public String receiveName;
        public String receiveTime;

        public String state;
    }


}
