package com.lhkj.cgj.ui.mine;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.lhkj.cgj.base.ui.adapter.BaseTopAdapter;
import com.lhkj.cgj.databinding.CardItemBinding;

import java.util.List;

/**
 * Created by user on 2018/4/16.
 */

public class CardListAdapter extends BaseTopAdapter {
    private  OnItemClickLongListener mOnItemClickLongListener;
    private OnItemClickListener mOnItemClickListener;
    public CardListAdapter(Context context, List list, int layoutId, int variableId) {
        super(context, list, layoutId, variableId);
    }

    public void setOnItemClickLongListener(OnItemClickLongListener onItemClickLongListener) {
        mOnItemClickLongListener = onItemClickLongListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    protected void subClassTask(ViewDataBinding binding, final int position) {
        super.subClassTask(binding, position);
        CardItemBinding cardItemBinding= (CardItemBinding) binding;
        cardItemBinding.cardLlC.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOnItemClickLongListener!=null){
                    mOnItemClickLongListener.OnItemClickLongListener(position);
                }
                return true;
            }
        });
        cardItemBinding.cardLlC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.OnItemClickListener(position);
                }
            }
        });

    }

    public interface OnItemClickLongListener{
        void OnItemClickLongListener(int position);
    }
    public interface OnItemClickListener{
        void OnItemClickListener(int position);
    }


}