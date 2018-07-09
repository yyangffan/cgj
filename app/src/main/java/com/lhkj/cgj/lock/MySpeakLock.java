package com.lhkj.cgj.lock;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.AdapterView;

import com.lhkj.cgj.BR;
import com.lhkj.cgj.R;
import com.lhkj.cgj.databinding.ActivityMyspeakBinding;
import com.lhkj.cgj.databinding.MyspeakItemBinding;
import com.lhkj.cgj.entity.News;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.network.response.CommentResponse;
import com.lhkj.cgj.ui.bbs.NewsDetailsActivity;
import com.lhkj.cgj.ui.mine.MySpeakActivity;
import com.lhkj.cgj.ui.mine.MySpeakAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/22.
 * 我的评论
 */

public class MySpeakLock {
    public MySpeakAdapter mySpeakAdapter;
    private ArrayList speakData;

    public MySpeakLock(final Context context, ActivityMyspeakBinding myspeakBinding) {
        speakData = new ArrayList();
        mySpeakAdapter = new MySpeakAdapter(context, speakData, R.layout.myspeak_item, BR.mySpeakItem);
        myspeakBinding.speakList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MySpeakItem mySpeakItem = ((MyspeakItemBinding) DataBindingUtil.getBinding(view)).getMySpeakItem();
                News news = new News(mySpeakItem.speakId, mySpeakItem.speakUrl, mySpeakItem.speakTitle,
                        mySpeakItem.speakText, mySpeakItem.speakReturn, mySpeakItem.zan);
                RunTime.setData(RunTime.NEWID, news);
                ((MySpeakActivity) context).startActivity(NewsDetailsActivity.class);
            }
        });
        getData();
    }

    private void getData() {
        speakData.clear();
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(CommentResponse.class, RunTime.operation.getMine().SPEAK_LIST, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                CommentResponse commentResponse = (CommentResponse) data;
                for (CommentResponse.Info info : commentResponse.info) {
//                    content
                    if (info.is_read.equals("0")) {
                        speakData.add(new MySpeakItem(info.article_id, info.img_url, info.title,
                                info.zhaiyao, info.content, true, info.zan));
                    } else {
                        speakData.add(new MySpeakItem(info.article_id, info.img_url, info.title,
                                info.zhaiyao, info.content, false, info.zan));
                    }
                }
                mySpeakAdapter.notifyDataSetChanged();
            }
        });
    }

    public void refresh(){
        getData();
    }

    public class MySpeakItem {
        public MySpeakItem(String speakId, String speakUrl, String speakTitle,
                           String speakN, String speakText, boolean speakReturn, String zan) {
            this.speakId = speakId;
            this.speakUrl = speakUrl;
            this.speakTitle = speakTitle;
            this.speakText = speakText;
            this.speakReturn = speakReturn;
            this.speakN = speakN;
            this.zan = zan;
        }

        public String speakId;
        public String speakUrl;
        public String speakTitle;
        public String speakText;
        public String speakN;
        public boolean speakReturn;
        public String zan;
    }
}
