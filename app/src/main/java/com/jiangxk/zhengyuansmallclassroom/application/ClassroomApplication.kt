package com.jiangxk.zhengyuansmallclassroom.application

import com.jiangxk.common.common.BaseApplication
import com.jiangxk.zhengyuansmallclassroom.BuildConfig
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
}