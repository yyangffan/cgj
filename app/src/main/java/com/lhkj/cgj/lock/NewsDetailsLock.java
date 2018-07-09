package com.lhkj.cgj.lock;

import android.content.Context;
import android.databinding.BaseObservable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lhkj.cgj.BR;
import com.lhkj.cgj.R;
import com.lhkj.cgj.databinding.ActivityNewsdetailsBinding;
import com.lhkj.cgj.entity.News;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.network.response.NewsDetailsResponse;
import com.lhkj.cgj.network.response.NewsResponse;
import com.lhkj.cgj.network.response.SuccessResponse;
import com.lhkj.cgj.ui.bbs.AuthorReplyAdapter;
import com.lhkj.cgj.ui.bbs.NewsCommntAdapter;
import com.lhkj.cgj.ui.bbs.NewsDetailsActivity;
import com.lhkj.cgj.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/28.
 * 文章详情
 */

public class NewsDetailsLock {
    public NewsCommntAdapter newsDetailsAdapter;
    private ArrayList<NewsDetailsItem> newsDetailsList;
    public NewsDetailsData newsDetailsData;
    private Context context;
    private ActivityNewsdetailsBinding newsdetailsBinding;
    private Handler handler = new Handler();

    public NewsDetailsLock(Context context, final ActivityNewsdetailsBinding newsdetailsBinding) {
        this.context = context;
        this.newsdetailsBinding = newsdetailsBinding;
        newsDetailsData = new NewsDetailsData();
        //zan状态
        newsDetailsData.zanN = Integer.parseInt(((News) RunTime.getRunTime(RunTime.NEWID)).zan);
        newsDetailsData.zanNum = "+" + newsDetailsData.zanN;
//        newsDetailsData.isZan = ((News) RunTime.getRunTime(RunTime.NEWID)).isZan;
        newsDetailsData.isZan = false;
        newsDetailsData.zan = context.getResources().getDrawable(R.mipmap.icon_zano);
        getZan();
        newsDetailsList = new ArrayList();

        LinearLayoutManager ms = new LinearLayoutManager(context);
        newsdetailsBinding.commntList.setLayoutManager(ms);
        newsDetailsAdapter = new NewsCommntAdapter(context, newsDetailsList);
        newsdetailsBinding.commntList.setAdapter(newsDetailsAdapter);
        handler.post(new Runnable() {
            @Override
            public void run() {
                newsdetailsBinding.scroll.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        getCommntList();
    }

    private void getZan() {
        HashMap hashMap = new HashMap();
        if (User.getUser().userId != null) {
            hashMap.put("user_id", User.getUser().userId);
            hashMap.put("article_id", ((News) RunTime.getRunTime(RunTime.NEWID)).speakId);
        }
        RunTime.operation.tryPostRefresh(NewsResponse.class, RunTime.operation.getHome().GET_NEWS, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                NewsResponse news = (NewsResponse) data;

                if (news.info != null) {
                    if (news.info.is_click != null && news.info.is_click.equals("1")) {
                        newsDetailsData.isZan = true;
                        newsdetailsBinding.ivZan.setImageResource(R.mipmap.icon_zan);
                    } else {
                        newsDetailsData.isZan = false;
                        newsdetailsBinding.ivZan.setImageResource(R.mipmap.icon_zano);
                    }
                    newsDetailsData.zanN = Integer.parseInt(news.info.click);
                    newsDetailsData.zanNum = "+" + news.info.click;
                    newsdetailsBinding.tvZan.setText(newsDetailsData.zanNum);
                }
            }
        });
    }

    public class NewsDetailsData extends BaseObservable {
        public String subNote = "";
        public int zanN = 0;
        public String zanNum = "+0";
        public Drawable zan;
        public boolean isZan;
    }

    //提交评论
    public void subCommnt() {
        if (!User.getUser().isLogin()) {
            Toast.makeText(context, "请先登陆后再评论", Toast.LENGTH_SHORT).show();
            ((NewsDetailsActivity) context).startActivity(LoginActivity.class);
            return;
        }
        if (newsDetailsData.subNote.equals("")) {
            Toast.makeText(context, "评论内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("article_id", ((News) RunTime.getRunTime(RunTime.NEWID)).speakId);
        hashMap.put("user_id", User.getUser().userId);
        hashMap.put("contents", newsDetailsData.subNote);
        RunTime.operation.tryPostRefreshF(SuccessResponse.class, RunTime.operation.getNewsList().NEWS_COMMENT, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
//                flush();
                if (id == 200) {
                    Toast.makeText(context, "评论已提交,审核中", Toast.LENGTH_SHORT).show();
                    newsDetailsData.subNote = "";
                    newsDetailsData.notifyChange();
                } else {
                    Toast.makeText(context, "评论提交失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //获取评论列表
    private void getCommntList() {
        HashMap hashMap = new HashMap();
        hashMap.put("article_id", ((News) RunTime.getRunTime(RunTime.NEWID)).speakId);
        hashMap.put("user_id", User.getUser().userId);
        RunTime.operation.tryPostRefresh(NewsDetailsResponse.class, RunTime.operation.getNewsList().NEWS_DEAT, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                if (data instanceof NewsDetailsResponse) {
                    newsDetailsList.clear();
                    NewsDetailsResponse newsDetailsResponse = (NewsDetailsResponse) data;
                    for (NewsDetailsResponse.Info info : newsDetailsResponse.info) {
                        NewsDetailsItem newsDetailsItem = new NewsDetailsItem(
                                info.comment_id, info.nickname, info.contents, info.click, info.head_pic, info.is_click);
                        newsDetailsItem.authorReplyList.clear();
                        if (info.reply != null && info.reply.size() != 0) {
                            for (NewsDetailsResponse.Reply reply : info.reply) {
                                newsDetailsItem.authorReplyList.add(new AuthorReplyItem(
                                        reply.comment_id, reply.contents, reply.is_click, reply.click));
                            }
                            newsDetailsItem.isReply = true;
                        } else {
                            newsDetailsItem.authorReplyList.add(new AuthorReplyItem());
                        }
                        newsDetailsList.add(newsDetailsItem);
                        newsDetailsAdapter.notifyDataSetChanged();

                    }
                }
            }
        });
    }

    //文章点赞
    public void newsZan() {
        if (!User.getUser().isLogin()) {
            Toast.makeText(context, "请先登陆后再点赞", Toast.LENGTH_SHORT).show();
            ((NewsDetailsActivity) context).startActivity(LoginActivity.class);
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("article_id", ((News) RunTime.getRunTime(RunTime.NEWID)).speakId);
        hashMap.put("user_id", User.getUser().userId);
        hashMap.put("type", "1");
        RunTime.operation.tryPostRefreshF(SuccessResponse.class, RunTime.operation.getNewsList().NEWS_SUB_ZAN, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                //zan状态
                newsDetailsData.isZan = !newsDetailsData.isZan;
                if (newsDetailsData.isZan) {
                    newsDetailsData.zanN = newsDetailsData.zanN + 1;
                    newsdetailsBinding.ivZan.setImageResource(R.mipmap.icon_zan);
                } else {
                    newsDetailsData.zanN = newsDetailsData.zanN - 1;
                    newsdetailsBinding.ivZan.setImageResource(R.mipmap.icon_zano);
                }
                newsdetailsBinding.tvZan.setText("+" + newsDetailsData.zanN);
            }
        });
    }

    public void flush() {
        getCommntList();
    }


    public class NewsDetailsItem extends BaseObservable {
        public NewsDetailsItem(String commentId, String userName, String userNote,
                               String zanNum, String img_url, String isZan) {
            this.commentId = commentId;
            this.userName = userName;
            this.userNote = userNote;
            this.zanNum = zanNum;
            this.img_url = img_url;
            if (isZan != null && isZan.equals("1")) {
                this.isZan = true;
            }else {
                this.isZan = false;
            }
        }

        public void commntFlush() {
            flush();
        }

        public String userName;
        public String userNote;
        public String zanNum;
        public String img_url;
        public boolean isZan;
        public String commentId = "";
        public String commentUserId = "";
        public boolean isReply = false;

        public Drawable zan = context.getResources().getDrawable(R.mipmap.icon_smallzan);
        public Drawable zano = context.getResources().getDrawable(R.mipmap.icon_smallzano);


        public ArrayList<AuthorReplyItem> authorReplyList = new ArrayList<AuthorReplyItem>();

        public AuthorReplyAdapter authorReplyAdapter = new AuthorReplyAdapter(context, authorReplyList,
        R.layout.item_author_reply, BR.authorReplyItem);

    }

    public class AuthorReplyItem extends BaseObservable{

        public AuthorReplyItem() {
            this.commentId = "";
        }

        public void commntFlush() {
            flush();
        }

        public AuthorReplyItem(String commentId, String autherNote, String isAutherZan, String autherZan) {
            this.commentId = commentId;

            this.autherNote = autherNote;
            if (isAutherZan != null && isAutherZan.equals("1")) {
                this.isAutherZan = true;
            }else {
                this.isAutherZan = false;
            }
            this.autherZan = autherZan;
        }

        public String commentId;

        public String autherNote;
        public boolean isAutherZan;
        public String autherZan;

        public Drawable zan = context.getResources().getDrawable(R.mipmap.icon_smallzan);
        public Drawable zano = context.getResources().getDrawable(R.mipmap.icon_smallzano);
    }


}
