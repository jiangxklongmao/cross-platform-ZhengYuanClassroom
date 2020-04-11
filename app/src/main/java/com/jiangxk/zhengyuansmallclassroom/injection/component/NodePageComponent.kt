package com.jiangxk.zhengyuansmallclassroom.injection.component

import com.jiangxk.common.injection.component.ActivityComponent
import com.jiangxk.common.injection.scope.PerComponentScope
import com.jiangxk.zhengyuansmallclassroom.injection.module.NodePageModule
import com.jiangxk.zhengyuansmallclassroom.ui.activity.course.NodePageActivity
import dagger.Component

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.component
 * @author jiangxk
 * @time 2020-04-11  16:42
 */
@PerComponentScope
@Component(modules = [NodePageModule::class], dependencies = [ActivityComponent::class])
interface NodePageComponent {
    fun inject(activity: NodePageActivity)
}