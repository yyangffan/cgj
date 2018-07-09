package com.lhkj.cgj.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.lhkj.cgj.R;
import com.lhkj.cgj.utils.PixelUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 浩琦 on 2017/8/10.
 */

public class ShopTypeAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;
    private ArrayList<String> types;
    private OnItemClickListener itemClickListener;
    private TextView oneText;
    private int width;
    private int height;

    public ShopTypeAdapter(Context context, ArrayList<String> types) {
        this.context = context;
        this.types = types;

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, viewGroup, false);
//        ViewHolder vh = new ViewHolder(view);
        //将创建的View注册点击事件
//        view.setOnClickListener(this);
        TextView textView = new TextView(context);
        textView.setLines(1);
        textView.setMaxEms(4);
        textView.setEms(4);
        textView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        textView.setOnClickListener(this);
        return new Type(textView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            oneText = (TextView) holder.itemView;
        }
        ((TextView) holder.itemView).setTextColor(context.getResources().getColor(R.color.gray));
        ((TextView) holder.itemView).setTextSize(16);
        ((TextView) holder.itemView).setGravity(Gravity.CENTER_HORIZONTAL);
        ((TextView) holder.itemView).setPadding(
                PixelUtil.dpToPx(context, 12), PixelUtil.dpToPx(context, 12),
                PixelUtil.dpToPx(context, 12), PixelUtil.dpToPx(context, 12));
        // 到底一条显示多少个呀
        ViewGroup.LayoutParams param = new ViewGroup.LayoutParams((int) (width / 3.8), ViewGroup.LayoutParams.WRAP_CONTENT);
        ((TextView) holder.itemView).setLayoutParams(param);
        ((TextView) holder.itemView).setText(types.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        if (types == null) {
            return 0;
        }
        return types.size();
    }

    public TextView getOneText() {
        return oneText;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            itemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class Type extends RecyclerView.ViewHolder {

        public Type(View itemView) {
            super(itemView);
        }
    }

}
