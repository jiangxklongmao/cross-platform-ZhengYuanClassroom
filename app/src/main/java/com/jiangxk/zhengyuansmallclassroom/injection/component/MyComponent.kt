package com.jiangxk.zhengyuansmallclassroom.injection.component

import com.jiangxk.common.injection.component.ActivityComponent
import com.jiangxk.common.injection.scope.PerComponentScope
import com.jiangxk.zhengyuansmallclassroom.injection.module.MyModule
import com.jiangxk.zhengyuansmallclassroom.ui.fragment.my.MyFragment
import dagger.Component

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.component
 * @author jiangxk
 * @time 2020-04-14  16:34
 */
@PerComponentScope
@Component(modules = [MyModule::class], dependencies = [ActivityComponent::class])
interface MyComponent {
    fun inject(fragment: MyFragment)
}