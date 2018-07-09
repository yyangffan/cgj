package com.lhkj.cgj.ui.mine;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityShareBinding;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.ui.login.LoginActivity;
import com.lhkj.cgj.utils.PixelUtil;
import com.lhkj.cgj.utils.PopManager;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

/**
 * Created by 浩琦 on 2017/6/22.
 */

public class ShareActivity extends BaseActivity {
    private ActivityShareBinding shareBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareBinding = DataBindingUtil.setContentView(this, R.layout.activity_share);
        shareBinding.include6.setAppBarLock(new AppBarLock(this, R.string.myshare, R.mipmap.icon_back, R.string.myfen).setRight(AppBarLock.MY_SHARE));
        paprCode();

    }

    private void paprCode() {
        createCode(RunTime.operation.getMine().SHARE_USER_LIST+"?user_id="+ User.getUser().userId);
    }

    private void createCode(String url) {
        if (url.equals("")) {
            Toast.makeText(ShareActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
        } else {
            // 位图
            try {
                /**
                 * 参数：1.文本 2 3.二维码的宽高 4.二维码中间的那个logo
                 */
                Bitmap bitmap = EncodingUtils.createQRCode(url, PixelUtil.dpToPx(this, 220), PixelUtil.dpToPx(this, 220), null);
                // 设置图片
                shareBinding.qrcode.setImageBitmap(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    public void toShare(View view) {
        if(User.getUser().isLogin()) {
            new PopManager(this).showShare("Mobile/Onepage/index?user_id=",null,null);
        }else {
            startActivity(LoginActivity.class);
        }
    }
}
