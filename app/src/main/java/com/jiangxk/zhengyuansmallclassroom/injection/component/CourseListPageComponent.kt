package com.jiangxk.zhengyuansmallclassroom.injection.component

import com.jiangxk.common.injection.component.ActivityComponent
import com.jiangxk.common.injection.scope.PerComponentScope
import com.jiangxk.zhengyuansmallclassroom.injection.module.CourseListPageModule
import com.jiangxk.zhengyuansmallclassroom.ui.activity.course.CourseListPageActivity
import dagger.Component

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.component
 * @author jiangxk
 * @time 2020-04-12  19:12
 */
@PerComponentScope
@Component(modules = [CourseListPageModule::class], dependencies = [ActivityComponent::class])
interface CourseListPageComponent {
    fun inject(activity: CourseListPageActivity)
}