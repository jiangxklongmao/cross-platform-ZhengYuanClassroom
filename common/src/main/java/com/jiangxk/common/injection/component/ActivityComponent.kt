package com.jiangxk.common.injection.component

import android.app.Activity
import android.content.Context
import com.jiangxk.common.injection.module.ActivityModule
import com.jiangxk.common.injection.scope.ActivityScope
import dagger.Component

/**
 * @desc
 * @auth jiangxk
 * @time 2019-10-16  17:56
 */
@ActivityScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {
    fun context(): Context
    fun activity(): Activity
}