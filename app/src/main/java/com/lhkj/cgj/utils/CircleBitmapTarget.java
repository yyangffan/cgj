package com.lhkj.cgj.utils;

/**
 * 创建日期：2017/10/21 on 9:59
 * 描述：
 * 作者：郭士超
 * QQ：1169380200
 */

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.request.target.ImageViewTarget;

/**
 *
 * 设置 默认图片，异常图片，指定路径的图片为圆角图片
 */

public class CircleBitmapTarget extends ImageViewTarget<Bitmap> {

    public CircleBitmapTarget(ImageView view) {
        super(view);
    }

    /**
     *  从指定路径加载的Bitmap
     * @param resource
     */
    @Override
    protected void setResource(@Nullable Bitmap resource) {
        bindCircleBitmapToImageView(resource);
    }
    /**
     *
     * onLoadFailed()和onLoadStarted调用该方法，用于设置默认的图片和异常图片
     * 设置默认图片
     * @param drawable
     */
    @Override
    public void setDrawable(Drawable drawable) {
        if (drawable instanceof BitmapDrawable){
            Bitmap bitmap1= ((BitmapDrawable) drawable).getBitmap();
            bindCircleBitmapToImageView(bitmap1);
        }else{
            view.setImageDrawable(drawable);
        }
    }

    /**
     * 通过RoundedBitmapDrawable绘制圆形Bitmap,且加载ImageView.
     * @param bitmap
     */
    private void bindCircleBitmapToImageView(Bitmap bitmap){
        RoundedBitmapDrawable bitmapDrawable=  RoundedBitmapDrawableFactory.create(view.getContext().getResources(),bitmap);
        bitmapDrawable.setCircular(true);
        view.setImageDrawable(bitmapDrawable);
    }
}