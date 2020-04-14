package com.jiangxk.zhengyuansmallclassroom.utils

import com.jiangxk.common.common.BaseApplication
import com.jiangxk.zhengyuansmallclassroom.R

/**
 * @description com.jiangxk.zhengyuansmallclassroom.utils
 * @author jiangxk
 * @time 2020-04-14  15:20
 */

class DeviceUtils {

    companion object {
        //获取状态栏高度
        private var statusBarHeight = 0

        /**
         * 获取状态栏高度
         */
        fun getStatusBarHeight(): Int {
            if (statusBarHeight > 0) {
                return statusBarHeight
            }
            val context = BaseApplication.context
            statusBarHeight = 0
            try {
                val resourceId =
                    context.getResources().getIdentifier("status_bar_height", "dimen", "android")
                if (resourceId > 0) {
                    statusBarHeight = context.getResources().getDimensionPixelSize(resourceId)
                }
                if (statusBarHeight <= 0) {
                    statusBarHeight =
                        context.getResources().getDimension(R.dimen.padding_top).toInt()
                }
            } catch (t: Throwable) {
            }

            return statusBarHeight
        }

    }

}