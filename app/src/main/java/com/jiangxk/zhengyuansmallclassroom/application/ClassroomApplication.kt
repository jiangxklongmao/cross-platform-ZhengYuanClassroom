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
        CrashReport.initCrashReport(this, "4d0ebef472", BuildConfig.DEBUG)
    }
}