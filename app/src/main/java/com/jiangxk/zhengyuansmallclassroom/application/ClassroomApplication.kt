package com.jiangxk.zhengyuansmallclassroom.application

import com.bun.miitmdid.core.JLibrary
import com.jiangxk.common.common.BaseApplication

/**
 * @description com.jiangxk.zhengyuansmallclassroom.application
 * @author jiangxk
 * @time 2020-04-19  22:52
 */
class ClassroomApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()

        initDomob()
    }

    fun initDomob() {
        //初始化移动安全联盟MSA
        JLibrary.InitEntry(this)
    }

}