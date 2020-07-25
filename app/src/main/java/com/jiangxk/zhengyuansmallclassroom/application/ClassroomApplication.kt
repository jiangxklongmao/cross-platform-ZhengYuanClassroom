package com.jiangxk.zhengyuansmallclassroom.application

import com.bytedance.sdk.openadsdk.*
import com.jiangxk.common.common.BaseApplication
import com.jiangxk.common.utils.AppPrefsUtils
import com.jiangxk.zhengyuansmallclassroom.BuildConfig
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.constant.Constant.SP_PERSONAL_INFORMATION_USER_ID_KEY
import com.jiangxk.zhengyuansmallclassroom.utils.ad.TTAdManagerHolder
import com.tencent.bugly.crashreport.CrashReport


/**
 * @description com.jiangxk.zhengyuansmallclassroom.application
 * @author jiangxk
 * @time 2020-04-19  22:52
 */
class ClassroomApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()

        initBugly()
        initAd()
    }

    private fun initBugly() {
        val strategy = CrashReport.UserStrategy(applicationContext)
        val appChannel = "${BuildConfig.FLAVOR}-${BuildConfig.BUILD_TYPE}-"
        val appVersion = BuildConfig.VERSION_NAME
        //渠道
        strategy.appChannel = appChannel
        //版本号
        strategy.appVersion = appVersion
        //包名
        strategy.appPackageName = BuildConfig.APPLICATION_ID
        CrashReport.initCrashReport(this, "4d0ebef472", BuildConfig.DEBUG, strategy)
    }


    private fun initAd() {
        //强烈建议在应用对应的Application#onCreate()方法中调用，避免出现content为null的异常
        TTAdManagerHolder.init(this)
    }

}