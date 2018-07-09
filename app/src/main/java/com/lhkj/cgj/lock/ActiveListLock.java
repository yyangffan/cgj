package com.lhkj.cgj.lock;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.lhkj.cgj.BR;
import com.lhkj.cgj.R;
import com.lhkj.cgj.base.network.HttpAbstractTask;
import com.lhkj.cgj.databinding.ActivityActiveListBinding;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.network.request.HttpPostTask;
import com.lhkj.cgj.network.response.ActiveListResponse;
import com.lhkj.cgj.network.response.HttpResponse;
import com.lhkj.cgj.ui.bbs.ActiveDetailActivity;
import com.lhkj.cgj.ui.bbs.ActiveListActivity;
import com.lhkj.cgj.ui.bbs.NewsListAdapter;
import com.lhkj.cgj.utils.HttpTaskSubmit;
import com.lhkj.cgj.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2018/4/13.
 */

public class ActiveListLock {
    private ActivityActiveListBinding mBinding;
    private Context context;
    private ArrayList<NewsLock.NewsItem> newsData;
    public NewsListAdapter newsListAdapter;

    public ActiveListLock(final Context context, ActivityActiveListBinding binding) {
        mBinding = binding;
        this.context = context;
        newsData = new ArrayList();
        newsListAdapter = new NewsListAdapter(context, newsData, R.layout.news_item, BR.newsItem);
        getData();
        mBinding.newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                News news = new News(newsData.get(position).id, "", newsData.get(position).imsTitle,
//                        newsData.get(position).imsNote, true, newsData.get(position).zan);
//                RunTime.setData(RunTime.NEWID, news);
//                ((ActiveListActivity) context).startActivity(NewsDetailsActivity.class);

                Intent intent = new Intent(context, ActiveDetailActivity.class);
                intent.putExtra("title", newsData.get(position).imsTitle);
                intent.putExtra("ac_id", newsData.get(position).id);
                ((ActiveListActivity) context).startActivity(intent);

            }
        });

    }


    private void getData() {
        newsData.clear();
        HashMap hashMap = new HashMap();
        String user_id = SharedPreferencesUtil.getStringData(context, "user_id", "");
        Log.e("活动时的User_id", user_id);
        hashMap.put("user_id", user_id);
        togetData(ActiveListResponse.class, RunTime.operation.getNewsList().NEWW_ACTIVE, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                ActiveListResponse newsListResponse = (ActiveListResponse) data;
                if (newsListResponse.getResultcode().equals("200")) {
                    for (ActiveListResponse.InfoBean info : newsListResponse.getInfo()) {
                        newsData.add(new NewsLock.NewsItem(info.getId(), info.getImg_url(), "", info.getTitle(), "", "", ""));
                    }
                    newsListAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(context, newsListResponse.getSuccess(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void togetData(Class clazz, String listName, HashMap data, final Operation.Listener Listener) {
        HttpPostTask httpPostTask = new HttpPostTask(listName, data, listName.hashCode(), clazz);
        HttpTaskSubmit.executeTask(httpPostTask, new HttpAbstractTask.OnResponseCallback() {
            @Override
            public void onResponse(int identifier, Object response) {

                if (((HttpResponse) response).getResultcode().equals("200")) {
                    Listener.tryReturn(200, response);
                } else {
                    Listener.tryReturn(100, response);
                    if (((HttpResponse) response).getResultmsg() != null && !((HttpResponse) response).getResultmsg().equals("")) {
                        Toast.makeText(RunTime.appContext, ((HttpResponse) response).getResultmsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


}
