package com.jiangxk.common.utils

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jiangxk.common.R

/**
 * @desc 图片加载工具类  -- Glide实现
 * @auth jiangxk
 * @time 2019-10-30  17:18
 */
object ImageLoader {

    /**
     * 加载网络图片
     * @param context Context
     * @param url String
     * @param imageView ImageView
     */
    fun loadUrlImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context).load(url).placeholder(R.drawable.icon_default)
            .error(R.drawable.icon_default).into(imageView)
    }

    /**
     *
     * @param activity Activity
     * @param url String
     * @param imageView ImageView
     */
    fun loadUrlImage(activity: Activity, url: String, imageView: ImageView) {
        Glide.with(activity).load(url).placeholder(R.drawable.icon_default)
            .error(R.drawable.icon_default).into(imageView)
    }
}