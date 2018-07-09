package com.lhkj.cgj.lock;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.lhkj.cgj.R;
import com.lhkj.cgj.databinding.ActivityMainBinding;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.ui.login.LoginActivity;
import com.lhkj.cgj.ui.main.HomeFragment;
import com.lhkj.cgj.ui.main.MainActivity;
import com.lhkj.cgj.ui.main.MineFragment;
import com.lhkj.cgj.ui.main.ShopFragment;
import com.lhkj.cgj.ui.station.BindingActivity;

import java.util.ArrayList;

import static com.lhkj.cgj.lock.AppBarLock.DUIHUANJILU;
import static com.lhkj.cgj.lock.AppBarLock.LOGIN;


/**
 * Created by 浩琦 on 2017/6/16.
 * 首页Act
 */

public class MainLock {
    private Context context;
    private ActivityMainBinding mainBinding;
    private ArrayList fragmentList;
    private HomeFragment mainFragment;
    private ShopFragment shopFragment;
    private MineFragment mineFragment;
    private RadioButton radio;
    public int isWhat=0;


    public MainLock(Context context, ActivityMainBinding mainBinding) {
        this.context = context;
        this.mainBinding = mainBinding;
        init();
    }


    private void init() {
        radio = mainBinding.mainHome;
        fragmentList = new ArrayList();
        mainFragment = new HomeFragment();
        shopFragment = new ShopFragment();
        mineFragment = new MineFragment();
        fragmentList.add(mainFragment);
        fragmentList.add(shopFragment);
        fragmentList.add(mineFragment);
        ((MainActivity) context).displayFragment(fragmentList, mainFragment, ((MainActivity) context).getSupportFragmentManager(), R.id.main_ll);
    }


    public void homePage(View view) {
        rbDarwTopAllFalse(view);
        flushBar();
        mainBinding.include.getRoot().setVisibility(View.VISIBLE);
        ((MainActivity) context).displayFragment(fragmentList, mainFragment, ((MainActivity) context).getSupportFragmentManager(), R.id.main_ll);
    }

    public void shoppingPage(View view) {
        // 刷新
//        ShopLock.refresh = true;
        ShopLock.seeWhat = 0;
        ShopLock.selectView = null;
        shopFragment.flushShop();

        if (!User.isLogin()) {
            ((MainActivity) context).startActivity(LoginActivity.class);
            Toast.makeText(context, "请先登陆", Toast.LENGTH_SHORT).show();
            return;
        }
        if (User.isLogin() && (User.getUser().userOilId == null || User.getUser().userOilId.equals(""))) {
            ((MainActivity) context).startActivity(BindingActivity.class);
            Toast.makeText(context, "请先绑定加油站", Toast.LENGTH_SHORT).show();
            return;
        }
        rbDarwTopAllFalse(view);
        flushBar();
        mainBinding.include.getRoot().setVisibility(View.VISIBLE);
        ((MainActivity) context).displayFragment(fragmentList, shopFragment, ((MainActivity) context).getSupportFragmentManager(), R.id.main_ll);
    }

    public void minePage(View view) {
        if (!User.isLogin()) {
            ((MainActivity) context).startActivity(LoginActivity.class);
            Toast.makeText(context, "请先登陆", Toast.LENGTH_SHORT).show();
            return;
        }
        rbDarwTopAllFalse(view);
        mainBinding.include.getRoot().setVisibility(View.GONE);
        ((MainActivity) context).displayFragment(fragmentList, mineFragment, ((MainActivity) context).getSupportFragmentManager(), R.id.main_ll);
    }

    private void rbDarwTopAllFalse(View view) {
        Drawable drawable = null;
        switch (radio.getId()) {
            case R.id.main_home:
                drawable = context.getResources().getDrawable(R.mipmap.icon_home_false);
                break;
            case R.id.main_shop:
                drawable = context.getResources().getDrawable(R.mipmap.icon_shop_flase);
                break;
            case R.id.main_mine:
                drawable = context.getResources().getDrawable(R.mipmap.icon_mine_flase);
                break;
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        radio.setCompoundDrawables(null, drawable, null, null);
        switch (view.getId()) {
            case R.id.main_home:
                drawable = context.getResources().getDrawable(R.mipmap.icon_home_true);
                break;
            case R.id.main_shop:
                drawable = context.getResources().getDrawable(R.mipmap.icon_shop_true);
                break;
            case R.id.main_mine:
                drawable = context.getResources().getDrawable(R.mipmap.icon_mine_true);
                break;
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        ((RadioButton) view).setCompoundDrawables(null, drawable, null, null);
        radio = (RadioButton) view;
    }

    public void flushMine() {
        mineFragment.flush();
    }

    public void flushHome() {
        mainFragment.flushHome();
    }

    public void flushShop() {
        shopFragment.flushShop();
    }

    public void flushBar() {
        switch (radio.getId()) {
            case R.id.main_home:
                isWhat=0;
                mainBinding.include.getAppBarLock().barData.title = context.getResources().getString(R.string.app_name);
                mainBinding.include.getAppBarLock().barData.isLeft = true;
                mainBinding.include.getAppBarLock().setRight(LOGIN);

                mainBinding.include.getAppBarLock().barData.isBig = false;
                mainBinding.include.getAppBarLock().setTitleCanClick(true);
                if (User.isLogin()) {
                    // TODO: 2017/11/20 等接口可以使用的时候在放出来
                    mainBinding.include.titleRightImg.setVisibility(View.VISIBLE);
                } else {
                    mainBinding.include.titleRightImg.setVisibility(View.GONE);
                }
                otherAddSz();

                break;
            case R.id.main_shop:
                isWhat=1;
                mainBinding.include.getAppBarLock().barData.title = context.getResources().getString(R.string.lll_shop);
                mainBinding.include.getAppBarLock().barData.isLeft = false;

                mainBinding.include.titleRightImg.setVisibility(View.GONE);

                mainBinding.include.getAppBarLock().barData.isBig = true;
                mainBinding.include.getAppBarLock().setTitleCanClick(false);
                mainBinding.include.getAppBarLock().setRight(DUIHUANJILU);
                mainBinding.include.getAppBarLock().barData.isRight=true;
                mainBinding.include.getAppBarLock().barData.titleRight = context.getResources().getString(R.string.exchange);
                break;
        }
        // TODO: 2018/3/28 修改中间的为绑定加油站
//        if (User.getUser().userOilId != null && !User.getUser().userOilId.equals("")) {
//            mainBinding.include.getAppBarLock().barData.titleLeft = User.getUser().userOilName;
//
//            mainBinding.include.getAppBarLock().barData.title = User.getUser().userOilName;
//        } else {
//            mainBinding.include.getAppBarLock().barData.titleLeft = context.getResources().getString(R.string.bind);
//
//            mainBinding.include.getAppBarLock().barData.title = context.getResources().getString(R.string.bind);
//        }
//        mainBinding.include.getAppBarLock().barData.titleRight = context.getResources().getString(R.string.loginor);
//        mainBinding.include.getAppBarLock().barData.isRight = !User.isLogin();
        mainBinding.include.getAppBarLock().barData.notifyChange();
    }

    public void otherAddSz() {
        // TODO: 2018/3/28 修改中间的为绑定加油站
        if (User.getUser().userOilId != null && !User.getUser().userOilId.equals("")) {
            mainBinding.include.getAppBarLock().barData.titleLeft = User.getUser().aftplateNumber;
            mainBinding.include.getAppBarLock().barData.title = User.getUser().userOilName;
        } else {
//            mainBinding.include.getAppBarLock().barData.titleLeft = context.getResources().getString(R.string.bind);
            mainBinding.include.getAppBarLock().barData.titleLeft = User.getUser().aftplateNumber;
            mainBinding.include.getAppBarLock().barData.title = context.getResources().getString(R.string.bind);
        }
        if(!User.isLogin()){
            mainBinding.include.getAppBarLock().barData.titleLeft = "";
            mainBinding.include.getAppBarLock().barData.title = context.getResources().getString(R.string.bind);
        }
        mainBinding.include.getAppBarLock().barData.titleRight = context.getResources().getString(R.string.loginor);
        mainBinding.include.getAppBarLock().barData.isRight = !User.isLogin();
        mainBinding.include.getAppBarLock().barData.notifyChange();

    }

}
