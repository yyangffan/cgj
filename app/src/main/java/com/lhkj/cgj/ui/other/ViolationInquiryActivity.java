package com.lhkj.cgj.ui.other;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityViolationInquiryBinding;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.lock.ViolationLock;

/**
 * 创建日期:2017/9/26 on 14:59
 * 描述:
 * 作者:郭士超
 * QQ:1169380200
 */

public class ViolationInquiryActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityViolationInquiryBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_violation_inquiry);
        binding.setViolationLock(new ViolationLock(this, binding));
        binding.include2.setAppBarLock(new AppBarLock(this, R.string.lllegal, R.mipmap.icon_back, 0, true, false));
        binding.webView.loadUrl("http://m.hbgajg.com/");  // 违章查询网站

        WebSettings wSet = binding.webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        binding.webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });

    }

}
