package com.lhkj.cgj.lock;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lhkj.cgj.R;
import com.lhkj.cgj.databinding.FragmentMineBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.network.response.AdsenseReponse;
import com.lhkj.cgj.network.response.NowLllReponse;
import com.lhkj.cgj.network.response.PromptReponse;
import com.lhkj.cgj.network.response.UserLevelResponse;
import com.lhkj.cgj.ui.login.LoginActivity;
import com.lhkj.cgj.ui.main.MainActivity;
import com.lhkj.cgj.ui.mine.LllGameActivity;
import com.lhkj.cgj.ui.mine.LllInfoActivity;
import com.lhkj.cgj.ui.mine.MyCardActivity;
import com.lhkj.cgj.ui.mine.MyHisActivity;
import com.lhkj.cgj.ui.mine.MyOrderActivity;
import com.lhkj.cgj.ui.mine.MySpeakActivity;
import com.lhkj.cgj.ui.mine.PerfectActivity;
import com.lhkj.cgj.ui.mine.ShareActivity;
import com.lhkj.cgj.ui.mine.SignCalendarActivity;
import com.lhkj.cgj.ui.other.SetActivity;
import com.lhkj.cgj.ui.station.BindingActivity;

import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/19.
 * 个人中心
 */

public class MineLock {
    private Context context;
    private FragmentMineBinding mineBinding;
    public MineData mineData;

    public MineLock(Context context, FragmentMineBinding mineBinding) {
        this.mineBinding = mineBinding;
        this.context = context;
        mineData = new MineData();
        init();
    }

    private void init() {
        if (!User.isLogin()) {
            mineData.isLogin = false;
            mineData.userName = context.getResources().getString(R.string.loginor);
            mineData.nowLll = context.getResources().getString(R.string.nowlll);
        } else {
            mineData.isLogin = true;
            mineData.userName = User.getUser().usernike;
            mineData.mineUser = User.getUser().userIcon;
            getAdsen();
            getPrompt();
            getLevel();
            getLll();
        }

    }

    private void getAdsen() {
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        hashMap.put("cat_id", "1");
        RunTime.operation.tryPostRefresh(AdsenseReponse.class, RunTime.operation.getHome().ADSENSE, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                if (((AdsenseReponse) data).info != null) {
                    Glide.with(context).load(((AdsenseReponse) data).info.img_url).into(mineBinding.mineAd);
                    mineData.isAds = true;
                    mineData.notifyChange();
                }
            }
        });
    }

    private void getPrompt() {
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        hashMap.put("cat_id", "1");
        RunTime.operation.tryPostRefresh(PromptReponse.class, RunTime.operation.getMine().PROMPT, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                if (((PromptReponse) data).num != null && !((PromptReponse) data).num.equals("0")) {
//                        mineBinding.redCurr.setText(((PromptReponse) data).num);
                    mineBinding.redCurr.setVisibility(View.VISIBLE);
                } else {
                    mineBinding.redCurr.setVisibility(View.GONE);
                }
            }
        });
    }

    private void getLevel() {
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(UserLevelResponse.class, RunTime.operation.getMine().USER_LEVEL, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                mineData.oilName = "等级:" + ((UserLevelResponse) data).level_name;
                mineData.notifyChange();
            }
        });
    }

    private void getLll() {
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(NowLllReponse.class, RunTime.operation.getHome().NOWLLL, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                mineData.nowLll = "当前积分:" + ((NowLllReponse) data).info;
                User.getUser().nowLll = ((NowLllReponse) data).info;
                mineData.notifyChange();
            }
        });
    }

    public void toSet() {
        ((MainActivity) context).startActivity(SetActivity.class);
    }

    public void toLogin() {
        if (!User.isLogin()) {
            ((MainActivity) context).startActivityForResult(new Intent(context, LoginActivity.class), 200);
        }
    }

    public void toSign() {
        if (User.getUser().userOilId != null && !User.getUser().userOilId.equals("")) {
            ((MainActivity) context).startActivity(SignCalendarActivity.class);
        } else {
            Toast.makeText(context, "请先绑定加油站", Toast.LENGTH_SHORT).show();
            ((MainActivity) context).startActivityForResult(new Intent(context, BindingActivity.class), 200);
        }
    }

    public void myOrder() {
        to(MyOrderActivity.class);
    }

    public void myCard() {
        to(MyCardActivity.class);
    }

    public void perfect() {
        to(PerfectActivity.class);
    }

    public void mySpeak() {
        to(MySpeakActivity.class);
    }

    public void lllInfo() {
        to(LllInfoActivity.class);
    }

    public void lllGame() {
        to(LllGameActivity.class);
    }

    public void myShare() {
        to(ShareActivity.class);
    }

    public void myHis() {
        to(MyHisActivity.class);
    }

    public void ads() {

    }

    public void adsClose() {
        mineData.isAds = false;
        mineData.notifyChange();
    }

    private void to(Class clazz) {
        if (User.isLogin()) {
            ((MainActivity) context).startActivity(clazz);
        } else {
            Toast.makeText(context, "请先登陆", Toast.LENGTH_SHORT).show();
            ((MainActivity) context).startActivityForResult(new Intent(context, LoginActivity.class), 200);
        }
    }


    public void flushMine() {
        mineData.flush();
        mineData.notifyChange();
    }

    public class MineData extends BaseObservable {
        public Drawable mineUser = User.getUser().userIcon;
        public boolean isLogin = User.isLogin();
        public String userName = User.getUser().usernike;
        public String nowLll = context.getResources().getString(R.string.nowlll);
        public String oilName = User.getUser().userLevel;
        public boolean isAds;

        public void flush() {
            mineUser = User.getUser().userIcon;
            isLogin = User.isLogin();
            userName = User.getUser().usernike;
            init();
        }
    }

}
