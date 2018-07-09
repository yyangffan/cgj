package com.lhkj.cgj.ui.bbs;

import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.BaseActivity;
import com.lhkj.cgj.databinding.ActivityNewsdetailsBinding;
import com.lhkj.cgj.entity.News;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.lock.AppBarLock;
import com.lhkj.cgj.lock.NewsDetailsLock;
import com.lhkj.cgj.network.response.NewsDetailsContextReponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;


/**
 * Created by 浩琦 on 2017/6/23.
 */

public class NewsDetailsActivity extends BaseActivity {

    private ActivityNewsdetailsBinding newsdetailsBinding;
    public NewsDetailsLock newsDetailsLock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsdetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_newsdetails);
        newsDetailsLock = new NewsDetailsLock(this, newsdetailsBinding);
        newsdetailsBinding.setNewsDeatailsLock(newsDetailsLock);
        init();

        //在Activity的onCreate里添加如下方法
        addLayoutListener(newsdetailsBinding.llyt, newsdetailsBinding.llyt2);
    }

    private void init() {
        newsdetailsBinding.newsDetails.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        newsdetailsBinding.newsDetails.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        newsdetailsBinding.newsDetails.getSettings().setJavaScriptEnabled(true);
        newsdetailsBinding.newsDetails.getSettings().setDomStorageEnabled(true);
        newsdetailsBinding.newsDetails.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        newsdetailsBinding.newsDetails.getSettings().setAppCachePath(appCachePath);
        newsdetailsBinding.newsDetails.getSettings().setAppCacheEnabled(true);
        newsdetailsBinding.newsDetails.getSettings().setAllowFileAccess(true);
//        newsdetailsBinding.newsDetails.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        newsdetailsBinding.newsDetails.getSettings().setLoadWithOverviewMode(true);
        newsdetailsBinding.newsDetails.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        swUrl();
    }

    private void swUrl() {
        News news = (News) RunTime.getRunTime(RunTime.NEWID);
        String title="";
        if(news.speakTitle.length()>9){
            title=news.speakTitle.substring(0,9)+"...";
        }else {
            title=news.speakTitle;
        }
        newsdetailsBinding.include9.setAppBarLock(
                new AppBarLock(this,
                        title, R.mipmap.icon_back,
                        R.mipmap.icon_share)
                        .setRight(AppBarLock.SHARE));

//        news.speakText = news.speakText.replace("<img", "<img height=\"250px\"; width=\"100%\"");

        getContext();
    }

    public void editF(View view) {
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

    private void getContext() {
        HashMap hashMap = new HashMap();
        hashMap.put("article_id", ((News) RunTime.getRunTime(RunTime.NEWID)).speakId);
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(NewsDetailsContextReponse.class, RunTime.operation.getNewsList().NEWS_DEAT_CONTEXT, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                NewsDetailsContextReponse newsDetailsContextReponse = (NewsDetailsContextReponse) data;
                String context = exImg(newsDetailsContextReponse.getInfo().getContent());
                newsdetailsBinding.newsDetails.loadDataWithBaseURL(null, context, "text/html", "utf-8", null);
            }
        });
    }


    /**
     * addLayoutListener方法如下
     * @param main 根布局
     * @param scroll 需要显示的最下方View
     */
    public void addLayoutListener(final View main, final View scroll) {
        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //1、获取main在窗体的可视区域
                main.getWindowVisibleDisplayFrame(rect);
                //2、获取main在窗体的不可视区域高度，在键盘没有弹起时，main.getRootView().getHeight()调节度应该和rect.bottom高度一样
                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
                int screenHeight = main.getRootView().getHeight();//屏幕高度
                //3、不可见区域大于屏幕本身高度的1/4：说明键盘弹起了
                if (mainInvisibleHeight > screenHeight / 4) {
                    int[] location = new int[2];
                    scroll.getLocationInWindow(location);
                    // 4､获取Scroll的窗体坐标，算出main需要滚动的高度
                    int srollHeight = (location[1] + scroll.getHeight()) - rect.bottom;
                    //5､让界面整体上移键盘的高度
                    main.scrollTo(0, srollHeight);
                } else {
                    //3、不可见区域小于屏幕高度1/4时,说明键盘隐藏了，把界面下移，移回到原有高度
                    main.scrollTo(0, 0);
                }
            }
        });
    }
}
