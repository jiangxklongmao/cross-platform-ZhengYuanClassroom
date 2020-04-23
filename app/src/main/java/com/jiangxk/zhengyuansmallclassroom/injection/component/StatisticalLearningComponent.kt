package com.jiangxk.zhengyuansmallclassroom.injection.component

import com.jiangxk.common.injection.component.ActivityComponent
import com.jiangxk.common.injection.scope.PerComponentScope
import com.jiangxk.zhengyuansmallclassroom.injection.module.StatisticalLearningModule
import com.jiangxk.zhengyuansmallclassroom.ui.activity.learning.StatisticalLearningActivity
import dagger.Component

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.component
 * @author jiangxk
 * @time 2020-04-20  17:11
 */
@PerComponentScope
@Component(modules = [StatisticalLearningModule::class], dependencies = [ActivityComponent::class])
interface StatisticalLearningComponent {
    fun inject(activity: StatisticalLearningActivity)
}