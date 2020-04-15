package com.jiangxk.zhengyuansmallclassroom.injection.component

import com.jiangxk.common.injection.component.ActivityComponent
import com.jiangxk.common.injection.scope.PerComponentScope
import com.jiangxk.zhengyuansmallclassroom.injection.module.SplashModule
import com.jiangxk.zhengyuansmallclassroom.ui.activity.splash.SplashActivity
import dagger.Component

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.component
 * @author jiangxk
 * @time 2020-04-15  14:44
 */
@PerComponentScope
@Component(modules = [SplashModule::class], dependencies = [ActivityComponent::class])
interface SplashComponent {
    fun inject(activity: SplashActivity)
}