package com.lhkj.cgj.ui.bbs;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lhkj.cgj.R;
import com.lhkj.cgj.databinding.CommentItemBinding;
import com.lhkj.cgj.entity.News;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.lock.NewsDetailsLock;
import com.lhkj.cgj.network.response.SuccessResponse;
import com.lhkj.cgj.ui.login.LoginActivity;
import com.lhkj.cgj.utils.CircleBitmapTarget;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/29.
 */

public class NewsCommntAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<NewsDetailsLock.NewsDetailsItem> list;
    private CommentItemBinding mBinding;


    public NewsCommntAdapter(Context context, ArrayList<NewsDetailsLock.NewsDetailsItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommentItemBinding mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.comment_item,
                parent,
                false);
        return new NewsCommntAdapter.Type(mBinding.getRoot(), mBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        mBinding = DataBindingUtil.findBinding(holder.itemView);
        mBinding.setNewsDetailsItem(list.get(position));

        Glide.with(context).asBitmap()
                .load(list.get(position).img_url)
                .apply(new RequestOptions().placeholder(R.mipmap.def_usericon))
                .into(new CircleBitmapTarget(mBinding.headIms))
                .onLoadFailed(context.getResources().getDrawable(R.mipmap.def_usericon));

        mBinding.zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subZan(position);
            }
        });

        if (list.get(position).authorReplyList.size() == 0) {
            mBinding.lvReply.setVisibility(View.GONE);
        } else {
//            list.get(position).authorReplyAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Type extends RecyclerView.ViewHolder {

        public Type(View itemView, final CommentItemBinding binding) {
            super(itemView);

        }
    }

    //评论点赞
    private void subZan(final int position) {
        if (!User.isLogin()) {
            Toast.makeText(context, "请先登陆再点赞", Toast.LENGTH_SHORT).show();
            ((NewsDetailsActivity) context).startActivity(LoginActivity.class);
            return;
        }
        if (list == null || list.size() == 0) return;
        HashMap hashMap = new HashMap();
        hashMap.put("article_id", ((News) RunTime.getRunTime(RunTime.NEWID)).speakId);
        hashMap.put("user_id", User.getUser().userId);
        hashMap.put("type", "2");
        hashMap.put("comment_id", list.get(position).commentId);
        RunTime.operation.tryPostRefresh(SuccessResponse.class, RunTime.operation.getNewsList().NEWS_SUB_ZAN, hashMap, new Operation.Listener() {
            @Override
            public void tryReturn(int id, Object data) {
                if (list == null || list.size() == 0) return;
                NewsDetailsLock.NewsDetailsItem item = list.get(position);
                if (list.get(position).isZan) {
                    item.zanNum = ((Integer.parseInt(item.zanNum) - 1) + "");
                } else {
                    item.zanNum = ((Integer.parseInt(item.zanNum) + 1) + "");
                }
                list.get(position).isZan = !list.get(position).isZan;
                list.get(position).notifyChange();

//                list.get(position).commntFlush();

            }
        });
    }

}
