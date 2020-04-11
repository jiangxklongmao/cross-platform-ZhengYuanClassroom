package com.jiangxk.zhengyuansmallclassroom.injection.component

import com.jiangxk.common.injection.component.ActivityComponent
import com.jiangxk.common.injection.scope.PerComponentScope
import com.jiangxk.zhengyuansmallclassroom.injection.module.SubjectPageModule
import com.jiangxk.zhengyuansmallclassroom.ui.activity.course.SubjectPageActivity
import dagger.Component

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.component
 * @author jiangxk
 * @time 2020-04-10  18:57
 */
@PerComponentScope
@Component(modules = [SubjectPageModule::class], dependencies = [ActivityComponent::class])
interface SubjectPageComponent {
    fun inject(activity: SubjectPageActivity)
}