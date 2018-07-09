package com.lhkj.cgj.ui.main;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityMainBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.PermissionsManager;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.lock.MainLock;
import com.lhkj.cgj.network.response.HttpResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import pub.devrel.easypermissions.EasyPermissions;

import static com.lhkj.cgj.lock.AppBarLock.BIND;
import static com.lhkj.cgj.lock.AppBarLock.LOGIN;

/*
* 分享:shareSDK
* 推送:极光推送
* 网络框架:okhttp
* 前端框架:databinding
*
* 主要逻辑在lock下对应类(其余部分在activity中)
* network:封装的网络请求及实体类
* push:极光推送接收器
* spirit:view控件
* ui:activity
* utils:各种工具
*
* */

public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private MainLock mainLock;
    public AppBarLock appBarLock;
    private ActivityMainBinding mMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainLock = new MainLock(this, mMainBinding);
        mMainBinding.setMainLock(mainLock);
        appBarLock = new AppBarLock(this, R.string.bind,R.string.nothing, R.string.loginor, true, !User.isLogin(), true).setRight(LOGIN).setLeft(BIND);
        appBarLock.barData.isBig = false;
        appBarLock.setTitleCanClick(true);

        mMainBinding.include.setAppBarLock(appBarLock);

        RunTime.setData(RunTime.LOGIN_ID, false);

        PermissionsManager.getPermissionsManager().signManager(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(getApplication());

        saveBitmap();

        setLayout();

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }


    public void flushBar() {
        mainLock.flushBar();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//          /*如下添加更新*/
//        UpdateManager updateManager = new UpdateManager(MainActivity.this, false);
//        updateManager.checkUpdate();
//        updateManager.setOnCancelClickListener(new UpdateManager.OnCancelClickListener() {
//            @Override
//            public void OnCancelClickListener() {
//                MainActivity.this.finish();
//            }
//        });
        mainLock.flushMine();
        mainLock.flushBar();
        mainLock.flushHome();
        mainLock.flushShop();
        if(mainLock.isWhat==0) {/*展示的是车管家*/
            if (User.getUser().userOilName == null || User.getUser().userOilName.equals("")) {
                appBarLock.barData.title = "绑定加油站";
            } else {
                appBarLock.barData.title = User.getUser().userOilName;
            }
            appBarLock.barData.notifyChange();
        }else if(mainLock.isWhat==1){/*展示的是积分商城*/

        }
        if (!User.isLogin()) {
            mainLock.homePage(mMainBinding.mainHome);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAlias();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    /*11.18添加主页扫描二维码返回结果*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String codeText = bundle.getString("result");
            HashMap h = new HashMap();
            h.put("user_id", User.getUser().userId);
            h.put("s_id", codeText);
            //boo
            RunTime.operation.tryPostRefresh(HttpResponse.class, RunTime.operation.getHome().USBANG, h, new Operation.Listener() {
                @Override
                public void tryReturn(int id, Object data) {
                    HttpResponse h = (HttpResponse) data;
                    if (id == 200) {
                        Toast.makeText(MainActivity.this, "扫描成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, h.getSuccess(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    private void setLayout() {
        int netType = -1;
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            Toast.makeText(this, "连接超时，请检查您的网络。", Toast.LENGTH_SHORT).show();
            return;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                netType = 3;
            } else {
                netType = 2;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1;
        }
    }

    private void setAlias() {
        if (!User.getUser().userId.equals("")) {
            JPushInterface.setAlias(this, Integer.parseInt(User.getUser().userId), User.getUser().userId);
            String use=User.getUser().userId;
            Log.e("油品惠推送ID",use);
        }
    }

    /**
     * 保存方法
     */
    public void saveBitmap() {
        Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.logo);

        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "yph");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "logo.png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);  // 多少秒退出
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }

}
