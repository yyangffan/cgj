package com.lhkj.cgj.ui.main;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.utils.UpdateManager;

/**
 * 创建日期：2017/10/19 on 10:29
 * 描述：
 * 作者：郭士超
 * QQ：1169380200
 */

public class StartPageActivity extends BaseActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        mImageView = (ImageView) findViewById(R.id.image);

        Glide.with(this)
                .load(R.drawable.start_pape)
                .into(mImageView);
        checkUpdate();
    }


    public void checkUpdate() {
                 /*如下添加更新*/
        UpdateManager updateManager = new UpdateManager(StartPageActivity.this, false);
        updateManager.checkUpdate();
        updateManager.setOnCancelClickListener(new UpdateManager.OnCancelClickListener() {
            @Override
            public void OnCancelClickListener() {
                StartPageActivity.this.finish();
            }
        });
        updateManager.setIsUpDateListener(new UpdateManager.IsUpDateListener() {
            @Override
            public void IsUpDateListener(boolean isUpdate) {
                if (!isUpdate) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(4 * 1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                startActivity(MainActivity.class);
                                finish();
                            }
                        }
                    }).start();
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkUpdate();
    }
}
