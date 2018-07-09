package com.lhkj.cgj.ui.bbs;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.Toast;

import com.lhkj.cgj.R;
import com.lhkj.cgj.base.ui.adapter.BaseTopAdapter;
import com.lhkj.cgj.databinding.ItemAuthorReplyBinding;
import com.lhkj.cgj.entity.News;
import com.lhkj.cgj.entity.Operation;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.entity.User;
import com.lhkj.cgj.lock.NewsDetailsLock;
import com.lhkj.cgj.network.response.SuccessResponse;
import com.lhkj.cgj.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 创建日期：2017/10/21 on 13:21
 * 描述：
 * 作者：郭士超
 * QQ：1169380200
 */

public class AuthorReplyAdapter extends BaseTopAdapter {

    private ArrayList<NewsDetailsLock.AuthorReplyItem> list;
    private Context context;
    private ItemAuthorReplyBinding itemBinding;

    public AuthorReplyAdapter(Context context, List list, int layoutId, int variableId) {
        super(context, list, layoutId, variableId);
        this.list = (ArrayList<NewsDetailsLock.AuthorReplyItem>) list;
        this.context = context;
    }

    @Override
    protected void subClassTask(ViewDataBinding binding, final int position) {
        super.subClassTask(binding, position);
        itemBinding = (ItemAuthorReplyBinding) binding;

        itemBinding.ivAutherZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subZan(position);
            }
        });

        itemBinding.tvAutherNote.setText(list.get(position).autherNote);
        itemBinding.tvAutherZan.setText(list.get(position).autherZan);
        if (list.get(position).isAutherZan){
            itemBinding.ivAutherZan.setImageResource(R.mipmap.icon_smallzan);
        }else {
            itemBinding.ivAutherZan.setImageResource(R.mipmap.icon_smallzano);
        }

    }


    // 回复点赞
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
                NewsDetailsLock.AuthorReplyItem item = list.get(position);
                if (list.get(position).isAutherZan) {
                    item.autherZan = ((Integer.parseInt(item.autherZan) - 1) + "");
                } else {
                    item.autherZan = ((Integer.parseInt(item.autherZan) + 1) + "");
                }
                list.get(position).isAutherZan = !list.get(position).isAutherZan;
                list.get(position).notifyChange();

//                list.get(position).commntFlush();
            }
        });
    }
}