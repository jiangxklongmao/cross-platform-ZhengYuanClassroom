package com.jiangxk.zhengyuansmallclassroom.application

import com.jiangxk.common.common.BaseApplication
import com.jiangxk.zhengyuansmallclassroom.utils.ad.TTAdManagerHolder
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure


/**
 * @description com.jiangxk.zhengyuansmallclassroom.application
 * @author jiangxk
 * @time 2020-04-19  22:52
 */
class ClassroomApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()

        initUmeng()
        initAd()
    }

    private fun initUmeng() {
        /**
         * 注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调
         * 用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，
         * UMConfigure.init调用中appkey和channel参数请置为null）。
         */
        UMConfigure.init(
            this,
            "5f1f8b20d62dd10bc71c7e08",
            "Umeng",
            UMConfigure.DEVICE_TYPE_PHONE,
            ""
        )
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)
    }


    private fun initAd() {
        //强烈建议在应用对应的Application#onCreate()方法中调用，避免出现content为null的异常
        TTAdManagerHolder.init(this)
    }

}