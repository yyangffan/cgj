package com.lhkj.cgj.ui.mine;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.lhkj.cgj.BR;
import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.base.ui.adapter.BaseTopAdapter;
import com.lhkj.cgj.databinding.ActivityLllInfoBinding;
import com.lhkj.cgj.databinding.ActivityShareUserBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.network.response.LllInfoResponse;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 创建日期:2017/9/29 on 10:49
 * 描述:积分详情
 * 作者:郭士超
 * QQ:1169380200
 */

public class LllInfoActivity extends BaseActivity {
    private BaseTopAdapter LllInfoAdapter;
    private ArrayList<LllInfoItem> LllInfoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLllInfoBinding lllInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_lll_info);
        lllInfoBinding.inclub.setAppBarLock(new AppBarLock(this, R.string.lllinfo));
        LllInfoList = new ArrayList();
        LllInfoAdapter = new BaseTopAdapter(this, LllInfoList, R.layout.item_lll_info, BR.lllInfo);
        lllInfoBinding.setUserAdapter(LllInfoAdapter);
        initUrl();

        ((TextView)findViewById(R.id.lll_num)).setText(User.getUser().nowLll);
    }

    private void initUrl() {
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(LllInfoResponse.class, RunTime.operation.getMine().MY_LLL_INFO, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                LllInfoResponse lllinfo = (LllInfoResponse) data;
                switch (lllinfo.getResultcode()) {
                    case "200":
                        for (LllInfoResponse.Info info : lllinfo.info) {
                            LllInfoList.add(new LllInfoItem(info.add_time, info.money, info.pay_method));
                        }
                        LllInfoAdapter.notifyDataSetChanged();
                        break;
                    case "201":
                        Toast.makeText(LllInfoActivity.this, "无积分使用记录", Toast.LENGTH_SHORT).show();
                        break;
                    case "100":
                        Toast.makeText(LllInfoActivity.this, "无积分使用记录", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(LllInfoActivity.this, "查询失败，请重试", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    public class LllInfoItem {
        public LllInfoItem(String time, String lll, String type) {
            this.type = type;
            if (this.type.equals("1")) {
                this.lll = "+" + lll;
            } else {
                this.lll = lll;
            }
            this.time = time;
        }

        public String time;
        public String lll;
        public String type; // 1是加积分 2是减积分
    }
}
