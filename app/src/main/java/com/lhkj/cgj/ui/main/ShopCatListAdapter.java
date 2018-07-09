package com.lhkj.cgj.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lhkj.cgj.R;
import com.lhkj.cgj.entity.RunTime;
import com.lhkj.cgj.network.response.ShopCatResponse;

import java.util.List;

/**
 * Created by user on 2018/3/28.
 */

public class ShopCatListAdapter extends RecyclerView.Adapter<ShopCatListAdapter.ViewHolder> {
    private Context mContext;
    private List<ShopCatResponse.Info> mInfos;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public ShopCatListAdapter(Context context, List<ShopCatResponse.Info> infos) {
        mContext = context;
        mInfos = infos;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.layout_cat_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ShopCatResponse.Info info = mInfos.get(position);
        String name = info.name;
        holder.mtv_title.setText(name);
        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
        Glide.with(mContext).load(RunTime.BASEURL+info.img_url).into(holder.mimgv);
    }

    @Override
    public int getItemCount() {
        return mInfos == null ? 0 : mInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mRelativeLayout;
        private TextView mtv_title;
        private ImageView mimgv;

        public ViewHolder(View itemView) {
            super(itemView);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.layout_rela);
            mtv_title = (TextView) itemView.findViewById(R.id.layout_title);
            mimgv = (ImageView) itemView.findViewById(R.id.layout_imgv);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
