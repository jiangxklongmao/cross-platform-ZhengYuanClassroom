package com.jiangxk.zhengyuansmallclassroom.injection.component

import com.jiangxk.common.injection.component.ActivityComponent
import com.jiangxk.common.injection.scope.PerComponentScope
import com.jiangxk.zhengyuansmallclassroom.injection.module.CalligraphyCourseListModule
import com.jiangxk.zhengyuansmallclassroom.ui.activity.course.CalligraphyCourseListActivity
import dagger.Component

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.component
 * @author jiangxk
 * @time 2020-04-14  14:14
 */
@PerComponentScope
@Component(
    modules = [CalligraphyCourseListModule::class],
    dependencies = [ActivityComponent::class]
)
interface CalligraphyCourseListComponent {
    fun inject(activity: CalligraphyCourseListActivity)
}