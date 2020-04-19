package com.jiangxk.common.common

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.jiangxk.common.BuildConfig
import com.jiangxk.common.injection.component.DaggerAppComponent
import com.jiangxk.common.injection.module.AppModule
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

/**
 * @desc
 * @auth jiangxk
 * @time 2019-10-16  18:49
 */
open class BaseApplication : Application() {
    lateinit var appComponent: DaggerAppComponent
    override fun onCreate() {
        super.onCreate()

        initInjection()

        initLogger()

        context = this

        initBugly()
        initARouter()
        initDomob()
    }

    private fun initInjection() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build() as DaggerAppComponent
    }

    /**
     * 初始化 日志模块
     */
    private fun initLogger() {
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    private fun initBugly() {
    }

    /**
     * 初始化 ARouter
     */
    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog()
            //开启调试模式
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    private fun initDomob(){
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

}