package com.lhkj.cgj.ui.station;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityStationInfoBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.lock.StationLock;
import com.lhkj.cgj.network.response.StationInfoReponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

/**
 * 创建日期:2017/9/28 on 9:26
 * 描述:加油站详情
 * 作者:郭士超
 * QQ:1169380200
 */

public class StationInfoActivity extends BaseActivity {

    private ActivityStationInfoBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_station_info);
        binding.setStationLock(new StationLock(this, binding));
        binding.include2.setAppBarLock(new AppBarLock(this, R.string.station_info, R.mipmap.icon_back, 0, true, false));

        getContent();
    }

    public void getContent() {
        HashMap hashMap = new HashMap();
        String adminId = (String) RunTime.getRunTime(RunTime.STATION_ID);
        hashMap.put("admin_id", adminId);
        RunTime.operation.tryPostRefresh(StationInfoReponse.class, RunTime.operation.getHome().STATION_INFO, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                StationInfoReponse result = (StationInfoReponse) data;
                if (result.getResultcode().equals("200")) {
                    setWebView(result.info.content);
                } else {
                    Toast.makeText(StationInfoActivity.this, "查询失败，请返回重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setWebView(String content) {
        //添加webView
        if (content != null) {
            binding.webView.setVisibility(View.VISIBLE);
            WebSettings webSettings = binding.webView.getSettings();//获取webview设置属性
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webview等宽的一列中
            webSettings.setJavaScriptEnabled(true);//支持js
            webSettings.setBuiltInZoomControls(true); // 显示放大缩小
            webSettings.setSupportZoom(true); // 可以缩放
            binding.webView.loadDataWithBaseURL(null, getNewContent(content), "text/html", "utf-8", null);
        }
    }

    /**
     * 将html文本内容中包含img标签的图片，宽度变为屏幕宽度，高度根据宽度比例自适应
     **/
    public static String getNewContent(String htmltext) {
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


    private void setWebView2(String content) {
        binding.webView.loadDataWithBaseURL("about:blank", content, "text/html", "utf-8", null);  // 加载Html语言

        WebSettings wSet = binding.webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        binding.webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
    }
}
