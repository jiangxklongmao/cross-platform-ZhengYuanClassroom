package com.jiangxk.common.ui.adapter

import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.jiangxk.common.common.BaseApplication.Companion.context
import com.jiangxk.common.ext.loadImage

/**
 * @desc RecyclerView-ViewHolder 基类
 * @auth jiangxk
 * @time 2019-10-17  17:24
 */
open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val views: SparseArray<View> = SparseArray()

    fun <T : View> getView(@IdRes viewId: Int): T {
        var view = views.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            views.put(viewId, view)
        }
        return view as T
    }

    /**
     * TextVIew 设置文本
     * @param viewId Int
     * @param text String
     * @return BaseViewHolder
     */
    fun setText(@IdRes viewId: Int, text: String): BaseViewHolder {
        getView<TextView>(viewId).text = text
        return this
    }

    /**
     * ImageView 设置本地图片
     * @param viewId Int
     * @param resId Int
     * @return BaseViewHolder
     */
    fun setImageReource(@IdRes viewId: Int, @DrawableRes resId: Int): BaseViewHolder {
        getView<ImageView>(viewId).setImageResource(resId)
        return this
    }

    /**
     * ImageView 设置本地背景图片
     * @param viewId Int
     * @param resId Int
     * @return BaseViewHolder
     */
    fun setBackgroundResource(@IdRes viewId: Int, @DrawableRes resId: Int): BaseViewHolder {
        getView<ImageView>(viewId).setBackgroundResource(resId)
        return this
    }


    /**
     * ImageView 加载网络图片
     * @param viewId Int
     * @param imageUrl String
     * @return BaseViewHolder
     */
    fun setImageUrl(@IdRes viewId: Int, imageUrl: String): BaseViewHolder {
        getView<ImageView>(viewId).loadImage(context, imageUrl)
        return this
    }

    /**
     * ImageView 加载网络图片
     * @param viewId Int
     * @param imageUrl String
     * @return BaseViewHolder
     */
    fun setImageUrl(@IdRes viewId: Int, imageUrl: String, @DrawableRes error: Int): BaseViewHolder {
        getView<ImageView>(viewId).loadImage(context, imageUrl, error)
        return this
    }

    /**
     * 设置 VIew 可见性
     * @param viewId Int
     * @param isVisibility Boolean
     * @return BaseViewHolder
     */
    fun setVisibility(@IdRes viewId: Int, isVisibility: Boolean): BaseViewHolder {
        getView<View>(viewId).visibility = if (isVisibility) View.VISIBLE else View.GONE
        return this
    }

}