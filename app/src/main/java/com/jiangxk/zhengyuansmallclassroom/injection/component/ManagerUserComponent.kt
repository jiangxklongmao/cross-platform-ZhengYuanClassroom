package com.jiangxk.zhengyuansmallclassroom.injection.component

import com.jiangxk.common.injection.component.ActivityComponent
import com.jiangxk.common.injection.scope.PerComponentScope
import com.jiangxk.zhengyuansmallclassroom.injection.module.ManagerUserModule
import com.jiangxk.zhengyuansmallclassroom.ui.activity.manager.ManagerUserActivity
import dagger.Component

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.component
 * @author jiangxk
 * @time 2020-04-16  11:05
 */
@PerComponentScope
@Component(modules = [ManagerUserModule::class], dependencies = [ActivityComponent::class])
interface ManagerUserComponent {
    fun inject(activity: ManagerUserActivity)
}