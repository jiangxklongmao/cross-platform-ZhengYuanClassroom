package com.jiangxk.zhengyuansmallclassroom.injection.component

import com.jiangxk.common.injection.component.ActivityComponent
import com.jiangxk.common.injection.scope.PerComponentScope
import com.jiangxk.zhengyuansmallclassroom.injection.module.ChapterPageModule
import com.jiangxk.zhengyuansmallclassroom.ui.activity.course.ChapterPageActivity
import dagger.Component

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.component
 * @author jiangxk
 * @time 2020-04-11  18:44
 */
@PerComponentScope
@Component(modules = [ChapterPageModule::class], dependencies = [ActivityComponent::class])
interface ChapterPageComponent {
    fun inject(activity: ChapterPageActivity)
}