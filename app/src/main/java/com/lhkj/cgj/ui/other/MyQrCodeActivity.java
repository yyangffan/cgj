package com.lhkj.cgj.ui.other;

import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityMyqrcodeBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.network.response.AdsenseReponse;
import com.lhkj.cgj.network.response.HttpResponse;
import com.lhkj.cgj.utils.PixelUtil;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 浩琦 on 2017/6/22.
 * 我的二维码页面
 */

public class MyQrCodeActivity extends BaseActivity {
    private ActivityMyqrcodeBinding myqrcodeBinding;
    private MyQrCodeLock myQrCodeLock;
    //首页跳转
    public static final int CODE_MAIN = 0;
    // 商品跳转
    public static final int CODE_PAY = 1;
    //优惠券跳转
    public static final int CODE_COUPON = 2;
    //签到奖品跳转
    public static final int CODE_SIGN = 3;
    private Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myqrcodeBinding = DataBindingUtil.setContentView(this, R.layout.activity_myqrcode);
        myqrcodeBinding.include7.setAppBarLock(new AppBarLock(this, R.string.qrcode, R.mipmap.icon_back, 0, true, false));
        myQrCodeLock = new MyQrCodeLock();
        myqrcodeBinding.setMyQrCordLock(myQrCodeLock);
        paprCode();
        getAdsen();
    }

    // 轮询(是否被扫,推送未完成)
    public void readOver() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if ((boolean) RunTime.getRunTime(RunTime.IS_CODE_READ)) {
                    MyQrCodeActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            myqrcodeBinding.serviceQrcode.setVisibility(View.GONE);
                            myqrcodeBinding.serviceScore.setVisibility(View.VISIBLE);
                        }
                    });
                    RunTime.setData(RunTime.IS_CODE_READ, false);
                    this.cancel();
                }
            }
        }, 1000, 600);
    }

    private void paprCode() {
//        生成对应二维码数据
        gson = new Gson();
        switch ((int) RunTime.getRunTime(RunTime.CODE_TYPE)) {
            case CODE_MAIN:
                codeMain();
                break;
            case CODE_PAY:
                codePay();
                break;
            case CODE_COUPON:
                codeCoupon();
                break;
            case CODE_SIGN:
                codeSign();
            default:
                break;
        }
    }

    private void getAdsen() {
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        hashMap.put("cat_id", "3");
        RunTime.operation.tryPostRefresh(AdsenseReponse.class, RunTime.operation.getHome().ADSENSE, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                if (((AdsenseReponse) data).info != null && ((AdsenseReponse) data).info.img_url != null) {
                    Glide.with(MyQrCodeActivity.this).load(((AdsenseReponse) data).info.img_url).into(myqrcodeBinding.adsen);
                    myQrCodeLock.isAds = true;
                    myQrCodeLock.notifyChange();
                }
            }
        });
    }

    private void codeMain() {
        if (!User.getUser().userId.equals("")) {
            generateCode(gson.toJson(new QrCodeJson(User.getUser().userId, CODE_MAIN)));
        } else {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void codePay() {
        generateCode(gson.toJson(new QrCodeJson(User.getUser().userId, (String) RunTime.getRunTime(RunTime.ORDER_ID), CODE_PAY)));
    }

    private void codeCoupon() {
        generateCode(gson.toJson(new QrCodeJson(User.getUser().userId, (String) RunTime.getRunTime(RunTime.STATE_ID), (String) RunTime.getRunTime(RunTime.COUPON_ID), CODE_COUPON)));
    }

    private void codeSign() {
        generateCode(gson.toJson(new QrCodeJson(User.getUser().userId, (String) RunTime.getRunTime(RunTime.SIGN_ID), CODE_SIGN, (String) RunTime.getRunTime(RunTime.SIGN_URL))));
    }

    private void generateCode(String url) {
        String str = url;
        if (str.equals("")) {
            Log.e("qrcode", "null");
        } else {
            // 位图
            try {
                /**
                 * 参数：1.文本 2 3.二维码的宽高 4.二维码中间的那个logo
                 */
                Bitmap bitmap = EncodingUtils.createQRCode(str, PixelUtil.dpToPx(this, 220), PixelUtil.dpToPx(this, 220), null);
                // 设置图片
                myqrcodeBinding.qrcode.setImageBitmap(bitmap);

                RunTime.setData(RunTime.IS_CODE_READ, false);
                readOver();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    public class MyQrCodeLock extends BaseObservable {
        public String reSpeak = "";
        public float star1 = 0;
        public float star2 = 0;
        public float star3 = 0;
        public boolean isAds;

        public void subReSpeak() {
            star1 = myqrcodeBinding.ratingBar1.getRating();
            star2 = myqrcodeBinding.ratingBar2.getRating();
            star3 = myqrcodeBinding.ratingBar3.getRating();

            if (0 == star1 || 0 == star2 || 0 == star3) {
                Toast.makeText(MyQrCodeActivity.this, "请打星对其进行评价", Toast.LENGTH_SHORT).show();
                return;
            }

            HashMap hashMap = new HashMap();
            hashMap.put("user_id", User.getUser().userId);
            hashMap.put("admin_id", User.getUser().userOilId);
            hashMap.put("content", reSpeak);
            hashMap.put("fuwu", star1 + "");
            hashMap.put("xiaolv", star2 + "");
            hashMap.put("xing", star3 + "");
            RunTime.operation.tryPostRefresh(HttpResponse.class, RunTime.operation.getHome().SCORE, hashMap, new Operation.Listener() {
                @Override
                public void tryReturn(int id, Object data) {
                    if(200 == id) {
                        Toast.makeText(MyQrCodeActivity.this, "评价成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        }

        public void ads() {

        }

        public void adsClose() {
            myQrCodeLock.isAds = false;
            myQrCodeLock.notifyChange();
        }
    }

    public class QrCodeJson {
        public QrCodeJson(String userId, String orderSn, int type) {
            this.userId = userId;
            this.orderSn = orderSn;
            this.type = type;
        }

        public QrCodeJson(String userId, String stateId, String couponId, int type) {
            this.userId = userId;
            this.type = type;
            this.stateId = stateId;
            this.couponId = couponId;
        }

        public QrCodeJson(String userId, String signId, int type, String imgUrl) {
            this.userId = userId;
            this.signId = signId;
            this.type = type;
            this.imgUrl = imgUrl;
        }

        public QrCodeJson(String userId, int type) {
            this.userId = userId;
            this.type = type;
        }

        public String userId;//用户ID
        public String orderSn;//订单号
        public String couponId;//优惠卷ID
        public String stateId;//优惠卷ID
        public String signId;//签到的商品id
        public int type;/*类型 0：正常加油积分
        1：商品领取
        2：优惠卷使用
        3: 签到领取*/
        public String imgUrl;

    }
}
