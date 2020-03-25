package com.jiangxk.common.injection.component

import android.content.Context
import com.jiangxk.common.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 * @desc
 * @auth jiangxk
 * @time 2019-10-16  18:02
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun context(): Context
}