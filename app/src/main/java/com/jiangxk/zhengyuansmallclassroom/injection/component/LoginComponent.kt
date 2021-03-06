package com.jiangxk.zhengyuansmallclassroom.injection.component

import com.jiangxk.common.injection.component.ActivityComponent
import com.jiangxk.common.injection.scope.PerComponentScope
import com.jiangxk.zhengyuansmallclassroom.injection.module.LoginModule
import com.jiangxk.zhengyuansmallclassroom.ui.activity.login.LoginActivity
import dagger.Component

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.component
 * @author jiangxk
 * @time 2020-03-26  17:42
 */
@PerComponentScope
@Component(modules = [LoginModule::class], dependencies = [ActivityComponent::class])
interface LoginComponent {
    fun inject(activity: LoginActivity)
}