package com.lhkj.cgj.ui.bbs;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityActiveDetailBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.lock.ActivieDetailLock;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.network.response.ActiveDetailResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

public class ActiveDetailActivity extends BaseActivity {

    private ActivityActiveDetailBinding mBinding;
    private ActivieDetailLock mLock;
    private String mAc_id;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_active_detail);
        mLock = new ActivieDetailLock(this, mBinding);
        mBinding.setLock(mLock);
        init();
    }

    public void init() {
        Intent intent = this.getIntent();
        if(intent!=null) {
            mTitle = intent.getStringExtra("title");
            mAc_id = intent.getStringExtra("ac_id");
        }
        if(mTitle.length()>9){
            mTitle=mTitle.substring(0,9)+"...";
        }
        Log.e("优惠活动传过来的标题",mTitle);
        mBinding.include9.setAppBarLock(new AppBarLock(this,mTitle));
        mBinding.newsDetails.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mBinding.newsDetails.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mBinding.newsDetails.getSettings().setJavaScriptEnabled(true);
        mBinding.newsDetails.getSettings().setDomStorageEnabled(true);
        mBinding.newsDetails.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        mBinding.newsDetails.getSettings().setAppCachePath(appCachePath);
        mBinding.newsDetails.getSettings().setAppCacheEnabled(true);
        mBinding.newsDetails.getSettings().setAllowFileAccess(true);
//        mBinding.newsDetails.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        mBinding.newsDetails.getSettings().setLoadWithOverviewMode(true);
        mBinding.newsDetails.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        getData();
    }

    public void getData() {
        HashMap hashMap = new HashMap();
        hashMap.put("ac_id",mAc_id);
        RunTime.operation.tryPostRefresh(ActiveDetailResponse.class, RunTime.operation.getNewsList().NEWW_ACTIVE_CONTENT, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                ActiveDetailResponse newsDetailsContextReponse = (ActiveDetailResponse) data;
//                String context = exImg(newsDetailsContextReponse.getInfo().getContent());
                String context = newsDetailsContextReponse.getInfo().getContent();
                mBinding.newsDetails.loadDataWithBaseURL(null, context, "text/html", "utf-8", null);
            }
        });
    }
    //图片自适应
    private String exImg(String htmltext) {
        try {
            Document doc = Jsoup.parse(htmltext);
            Elements elements = doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width", "100%").attr("height", "auto");
            }
            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }

}
