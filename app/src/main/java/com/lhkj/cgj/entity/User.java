package com.lhkj.cgj.entity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lhkj.cgj.R;
import com.lhkj.cgj.network.response.UserResponse;
import com.lhkj.cgj.utils.BitmapUtils;
import com.lhkj.cgj.utils.PixelUtil;
import com.lhkj.cgj.utils.SharedPreferencesUtil;

/**
 * Created by 浩琦 on 2017/6/20.
 */

public class User{
    private static User user;
    public String userId = "";
    public String username = "";
    public String usernike = "";
    public String usersex = "";
    public Drawable userIcon = RunTime.appContext.getResources().getDrawable(R.mipmap.def_usericon);
    public Drawable userIconClone = RunTime.appContext.getResources().getDrawable(R.mipmap.def_usericon);
    public String userOilId = "";
    public String userOilName = "";
    public String nowLll = "0";
    public String plateNumber = "";
    public String carBrand = "";
    public String aftplateNumber = "";
    public String carAge = "";
    public String carMin = "";
    public String oilName = "";
    public String userLevel = "";
    public final String SAVE_NAME = "userName";
    public final String SAVE_PWD = "userPwd";
    private static boolean isLogin;
    public String oil_type="";
    public String oil_type_name="";

    private User() {

    }

    public static User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public static boolean isLogin() {
        return isLogin;
    }

    public void clearUser() {
        userId = "";
        usernike = "";
        username = "";
        userOilId = "";
        userOilName = "";
        plateNumber="";
        userIcon = RunTime.appContext.getResources().getDrawable(R.mipmap.def_usericon);
        userIconClone =  RunTime.appContext.getResources().getDrawable(R.mipmap.def_usericon);
        isLogin = false;
        SharedPreferencesUtil.saveSharePreString(RunTime.appContext, SAVE_NAME, "");
        SharedPreferencesUtil.saveSharePreString(RunTime.appContext, SAVE_PWD, "");
    }


    public void initUser(UserResponse userResponse) {
        usernike = userResponse.getInfo().getNickname();
        userId = userResponse.getInfo().getUser_id();
        username = userResponse.getInfo().getMobile();
        if (userResponse.getInfo().getSex().equals("1")) {
            usersex = "男";
        } else {
            usersex = "女";
        }
        userOilId = userResponse.getInfo().getBind_oil();
        userOilName = userResponse.getInfo().getOil_name();
        nowLll = userResponse.getInfo().getPoints();
        if (nowLll == null) {
            nowLll = "0.0";
        }
        carBrand = userResponse.getInfo().getCar_brand();
        plateNumber = userResponse.getInfo().getCar_guishu();
        aftplateNumber = userResponse.getInfo().getCar_number();
        carAge = userResponse.getInfo().getCar_date();
        carMin = userResponse.getInfo().getCar_km();
        userLevel = userResponse.getInfo().getLevel();
        oil_type=userResponse.getInfo().getOil_type();
        oil_type_name=userResponse.getInfo().getOil_type_name();
//        oilName = userResponse.getInfo().getOil_name();
        Glide.with(RunTime.appContext).asBitmap().load(userResponse.getInfo().getHead_pic()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                addIcon(resource);
            }
        });
        if (userIcon == null) {
            userIcon = RunTime.appContext.getResources().getDrawable(R.mipmap.def_usericon);
            userIconClone = RunTime.appContext.getResources().getDrawable(R.mipmap.def_usericon);
        }
        isLogin = true;
    }

    private void addIcon(Bitmap resource) {
        if (resource.getWidth() > PixelUtil.dpToPx(RunTime.appContext, 60)) {
            Log.i("aaaa", resource.getWidth() + "");
            resource = BitmapUtils.scaleBitmap(resource, PixelUtil.dpToPx(RunTime.appContext, 60), PixelUtil.dpToPx(RunTime.appContext, 60));
            resource = BitmapUtils.makeRoundCorner(resource);
        }
        userIcon = new BitmapDrawable(RunTime.appContext.getResources(), resource);
        userIconClone = new BitmapDrawable(RunTime.appContext.getResources(), resource);
    }

    public interface AuthorizationListener {
        void authorization(boolean isOk);
    }

}
