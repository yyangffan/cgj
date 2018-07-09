package com.lhkj.cgj.ui.main;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.network.response.ZixunDetailResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

/********************************************************************
 @version: 1.0.0
 @description: 这个界面并没有使用DataBinding框架因为太简单了
 @author: user  点击资讯服务每一条的详情显示
 @time: 2018/4/12 11:34
 @变更历史:
 ********************************************************************/

public class ZixunDetailActivity extends BaseActivity {
    private ImageView mimg_back;
    private TextView mtv_title;
    private TextView mtv_content;
    private String mZx_id;
    private WebView mWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zixun_detail);
        initViews();
    }

    public void initViews() {
        mimg_back = (ImageView) findViewById(R.id.zixun_ims_left);
        mtv_title = (TextView) findViewById(R.id.zixun_app_title);
        mtv_content = (TextView) findViewById(R.id.zixun_detail_tv);
        mWebView= (WebView) findViewById(R.id.news_details);

        mZx_id = this.getIntent().getStringExtra("zx_id");
        mimg_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZixunDetailActivity.this.finish();
            }
        });
        initWeb();
    }

    public void initWeb() {
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        mWebView.getSettings().setAppCachePath(appCachePath);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setAllowFileAccess(true);
//        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        if (mZx_id != null && !mZx_id.equals("")) {
            getdata();
        }
    }

    public void getdata() {
        HashMap hashMap = new HashMap();
        hashMap.put("zx_id", mZx_id);
        RunTime.operation.tryPostRefresh(ZixunDetailResponse.class, RunTime.operation.getHome().ZIXUNCONTENT, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                ZixunDetailResponse response = (ZixunDetailResponse) data;
                if (response.getResultcode().equals("200")) {
                    mtv_title.setText(response.getInfo().getZx_title());
//                    mtv_content.setText(response.getInfo().getZx_content());
                    String context = exImg(response.getInfo().getZx_content());
                   mWebView.loadDataWithBaseURL(null, context, "text/html", "utf-8", null);
                }
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
