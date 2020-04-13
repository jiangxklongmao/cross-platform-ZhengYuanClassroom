package com.jiangxk.zhengyuansmallclassroom.injection.component

import com.jiangxk.common.injection.component.ActivityComponent
import com.jiangxk.common.injection.scope.PerComponentScope
import com.jiangxk.zhengyuansmallclassroom.injection.module.CourseVideoPlayerModule
import com.jiangxk.zhengyuansmallclassroom.ui.activity.course.CourseVideoPlayerActivity
import dagger.Component

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.component
 * @author jiangxk
 * @time 2020-04-13  15:44
 */
@PerComponentScope
@Component(modules = [CourseVideoPlayerModule::class], dependencies = [ActivityComponent::class])
interface CourseVideoPlayerComponent {
    fun inject(activity: CourseVideoPlayerActivity)
}