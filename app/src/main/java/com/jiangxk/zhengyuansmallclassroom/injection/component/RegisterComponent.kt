package com.jiangxk.zhengyuansmallclassroom.injection.component

import com.jiangxk.common.injection.component.ActivityComponent
import com.jiangxk.common.injection.scope.PerComponentScope
import com.jiangxk.zhengyuansmallclassroom.injection.module.RegisterModule
import com.jiangxk.zhengyuansmallclassroom.ui.activity.login.RegisterActivity
import dagger.Component

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.component
 * @author jiangxk
 * @time 2020-04-26  17:32
 */
@PerComponentScope
@Component(modules = [RegisterModule::class], dependencies = [ActivityComponent::class])
interface RegisterComponent {
    fun inject(activity: RegisterActivity)
}