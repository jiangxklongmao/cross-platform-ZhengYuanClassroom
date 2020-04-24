package com.jiangxk.zhengyuansmallclassroom.injection.component

import com.jiangxk.common.injection.component.ActivityComponent
import com.jiangxk.common.injection.scope.PerComponentScope
import com.jiangxk.zhengyuansmallclassroom.injection.module.HomeActivityModule
import com.jiangxk.zhengyuansmallclassroom.ui.activity.home.HomeActivity
import dagger.Component

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.component
 * @author jiangxk
 * @time 2020-04-24  16:49
 */
@PerComponentScope
@Component(modules = [HomeActivityModule::class], dependencies = [ActivityComponent::class])
interface HomeActivityComponent {
    fun inject(activity: HomeActivity)
}