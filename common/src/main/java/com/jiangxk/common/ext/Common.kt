package com.jiangxk.common.ext

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.jiangxk.common.utils.GlideImageLoader
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import java.io.StringReader
import java.lang.reflect.Type

/**
 * @desc
 * @auth jiangxk
 * @time 2019-10-30  16:49
 */

var BANNER_TIME = 5 * 1000

/**
 * 设置Banner
 * @receiver Banner
 * @param titles List<String>?
 * @param bannerImages List<String>?
 */
fun Banner.player(titles: List<String>?, bannerImages: List<String>?) {
    val isNotEmptyImages = bannerImages?.isNotEmpty() ?: false
    if (isNotEmptyImages) {
        if (null != titles) {
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE)
            setBannerTitles(titles)
        } else {
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        }
        setImageLoader(GlideImageLoader())
        setImages(bannerImages)
        setDelayTime(BANNER_TIME)
        isAutoPlay(true)
        setIndicatorGravity(BannerConfig.CENTER)
        setBannerAnimation(Transformer.Default)
        start()
    }
}

@Throws(JsonSyntaxException::class)
inline fun <reified T> Gson.fromJson2(json: String, typeOfT: Type): T {
    val reader = StringReader(json)
    return fromJson(reader, typeOfT)
}

fun Context.dp2px(dip: Int): Int {
    val scale = this.resources.displayMetrics.density
    return (dip * scale + 0.5 * (if (dip >= 0) 1 else -1)).toInt()
}

fun Context.sp2px(dip: Int): Int {
    val scale = this.resources.displayMetrics.density
    return (dip * scale + 0.5 * (if (dip >= 0) 1 else -1)).toInt()
}
