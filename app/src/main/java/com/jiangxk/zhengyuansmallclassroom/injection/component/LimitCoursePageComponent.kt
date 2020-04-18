package com.jiangxk.zhengyuansmallclassroom.injection.component

import com.jiangxk.common.injection.component.ActivityComponent
import com.jiangxk.common.injection.scope.PerComponentScope
import com.jiangxk.zhengyuansmallclassroom.injection.module.LimitCoursePageModule
import com.jiangxk.zhengyuansmallclassroom.ui.activity.manager.LimitCoursePageActivity
import dagger.Component

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.component
 * @author jiangxk
 * @time 2020-04-18  11:50
 */
@PerComponentScope
@Component(modules = [LimitCoursePageModule::class], dependencies = [ActivityComponent::class])
interface LimitCoursePageComponent {
    fun inject(activity: LimitCoursePageActivity)
}