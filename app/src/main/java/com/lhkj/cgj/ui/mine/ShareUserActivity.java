package com.lhkj.cgj.ui.mine;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lhkj.cgj.BR;
import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.base.ui.adapter.BaseTopAdapter;
import com.lhkj.cgj.databinding.ActivityShareUserBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.network.response.ShareUserListReponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/7/12.
 */

public class ShareUserActivity extends BaseActivity {
    private BaseTopAdapter userAdapter;
    private ArrayList<UserItem> userList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityShareUserBinding shareUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_share_user);
        shareUserBinding.inclub.setAppBarLock(new AppBarLock(this, R.string.myfen));
        userList = new ArrayList();
        userAdapter = new BaseTopAdapter(this, userList, R.layout.share_user_item, BR.userItem);
        shareUserBinding.setUserAdapter(userAdapter);
        initUrl();
    }

    private void initUrl() {
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(ShareUserListReponse.class, RunTime.operation.getMine().MY_SHARE_LIST, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                ShareUserListReponse shareUserListReponse = (ShareUserListReponse) data;
                for (ShareUserListReponse.Info info : shareUserListReponse.info) {
                    Date date = new Date();
                    date.setTime(Long.parseLong(info.time + "000"));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    info.time = formatter.format(date);
                    userList.add(new UserItem(null, info.nickname, info.time, info.jifen));
                }
                userAdapter.notifyDataSetChanged();
            }
        });
    }

    public class UserItem {
        public UserItem(Drawable icon, String name, String time, String lll) {
            if (name != null && name.length() >= 6) {
                this.name = name.substring(0, 6) + "...";
            }else {
                this.name=name;
            }
            this.time = time;
            this.lll = "注册油品惠成功  " + "奖励" + lll + "积分";
            if (icon == null) {
                this.icon = getResources().getDrawable(R.mipmap.def_usericon);
            } else {
                this.icon = icon;
            }
        }

        public Drawable icon;
        public String name;
        public String time;
        public String lll;
    }
}
