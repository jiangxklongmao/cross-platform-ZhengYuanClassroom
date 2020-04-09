package com.jiangxk.zhengyuansmallclassroom.injection.component

import com.jiangxk.common.injection.component.ActivityComponent
import com.jiangxk.common.injection.scope.PerComponentScope
import com.jiangxk.zhengyuansmallclassroom.injection.module.HomeModule
import com.jiangxk.zhengyuansmallclassroom.ui.fragment.home.HomeFragment
import dagger.Component

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.component
 * @author jiangxk
 * @time 2020-03-24  18:12
 */
@PerComponentScope
@Component(modules = [HomeModule::class], dependencies = [ActivityComponent::class])
interface HomeComponent {
    fun inject(fragment: HomeFragment)
}