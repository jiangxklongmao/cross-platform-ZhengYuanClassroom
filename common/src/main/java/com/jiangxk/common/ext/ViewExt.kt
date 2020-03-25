package com.jiangxk.common.ext

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.github.jdsjlzx.ItemDecoration.SpacesItemDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerView
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.jiangxk.common.R

/**
 * @desc View 相关扩展
 * @auth jiangxk
 * @time 2019-10-17  17:37
 */


/**
 * 加载网络图片
 * @receiver ImageView
 * @param context Context
 * @param url String
 * @param error Int
 */
fun ImageView.loadImage(context: Context, url: String) {
    Glide.with(context).load(url).into(this)
}

/**
 * 加载网络图片
 * @receiver ImageView
 * @param context Context
 * @param url String
 * @param error Int
 */
fun ImageView.loadImage(context: Context, url: String, @DrawableRes error: Int) {
    Glide.with(context).load(url).error(error).into(this)
}

fun LRecyclerView.gridInit(context: Context, span: Int = 3, adapter: LRecyclerViewAdapter) {
    val layoutManager = GridLayoutManager(context, span)
    this.layoutManager = layoutManager
    this.adapter = adapter
    this.setLoadMoreEnabled(false)
    this.setPullRefreshEnabled(false)
    val spacing = resources.getDimensionPixelSize(R.dimen.dp_4)
    this.addItemDecoration(
        SpacesItemDecoration.newInstance(
            spacing,
            spacing,
            span,
            resources.getColor(R.color.colorItemDecoration,null)
        )
    )

}