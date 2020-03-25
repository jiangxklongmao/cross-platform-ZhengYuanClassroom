package com.jiangxk.common.utils

import android.content.Context
import android.widget.ImageView
import com.jiangxk.common.utils.ImageLoader.loadUrlImage
import com.youth.banner.loader.ImageLoader

/**
 * @desc
 * @auth jiangxk
 * @time 2019-10-30  17:11
 */
class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        imageView?.let {
            context?.let { it1 ->
                loadUrlImage(
                    it1,
                    path as String,
                    it
                )
            }
        }
    }
}