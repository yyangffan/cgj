package com.lhkj.cgj.lock;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.lhkj.cgj.BR;
import com.lhkj.cgj.R;
import com.lhkj.cgj.databinding.ActivityNewsBinding;
import com.lhkj.cgj.entity.News;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.network.response.NewsListResponse;
import com.lhkj.cgj.ui.bbs.NewsActivity;
import com.lhkj.cgj.ui.bbs.NewsDetailsActivity;
import com.lhkj.cgj.ui.bbs.NewsListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/22.
 * 文章列表
 */

public class NewsLock {
    private ArrayList<NewsItem> newsData;
    public NewsListAdapter newsListAdapter;
    private ActivityNewsBinding newsBinding;
    private Context context;

    public NewsLock(final Context context, ActivityNewsBinding newsBinding) {
        this.context = context;
        newsData = new ArrayList();
        newsListAdapter = new NewsListAdapter(context, newsData, R.layout.news_item, BR.newsItem);
        getData();
        newsBinding.newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = new News(newsData.get(position).id, "", newsData.get(position).imsTitle,
                        newsData.get(position).imsNote, true, newsData.get(position).zan);
                RunTime.setData(RunTime.NEWID, news);

                ((NewsActivity) context).startActivity(NewsDetailsActivity.class);
            }
        });

    }

    private void getData() {
        newsData.clear();
        HashMap hashMap = new HashMap();
        hashMap.put("cat_id", RunTime.getRunTime(RunTime.CAT_ID));
//        hashMap.put("admin_id", User.getUser().userOilId);
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(NewsListResponse.class, RunTime.operation.getNewsList().NEWS_LIST, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                NewsListResponse newsListResponse = (NewsListResponse) data;
                for (NewsListResponse.Info info : newsListResponse.info) {
                    newsData.add(new NewsItem(info.article_id,info.img_url, info.zhaiyao, info.title, info.content, info.add_time, info.click));
                }
                newsListAdapter.notifyDataSetChanged();
            }
        });


    }

    public static class NewsItem {
        public NewsItem(String id,String imsUrl, String imsText, String imsTitle, String imsNote, String imsTime, String zan) {
            this.id = id;
            this.imsText = imsText;
            this.imsTitle = imsTitle;
            this.imsNote = imsNote;
            this.imsTime = imsTime;
            this.imsUrl=imsUrl;
            this.zan = zan;
        }

        public String imsText;
        public String id;
        public String imsTitle;
        public String imsTime;
        public String imsUrl;
        public String imsNote;
        public String zan;
    }
}
