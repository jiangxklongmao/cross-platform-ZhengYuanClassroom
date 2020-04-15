package com.jiangxk.zhengyuansmallclassroom.injection.component

import com.jiangxk.common.injection.component.ActivityComponent
import com.jiangxk.common.injection.scope.PerComponentScope
import com.jiangxk.zhengyuansmallclassroom.injection.module.LearningOrderModule
import com.jiangxk.zhengyuansmallclassroom.ui.activity.learning.LearningOrderActivity
import dagger.Component

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.component
 * @author jiangxk
 * @time 2020-04-15  20:58
 */
@PerComponentScope
@Component(modules = [LearningOrderModule::class], dependencies = [ActivityComponent::class])
interface LearningOrderComponent {
    fun inject(activity: LearningOrderActivity)
}