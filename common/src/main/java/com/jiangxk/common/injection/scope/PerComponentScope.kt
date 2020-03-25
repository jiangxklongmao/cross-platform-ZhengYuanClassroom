package com.jiangxk.common.injection.scope

import javax.inject.Scope

/**
 * @desc 组件级别作用域
 * @auth jiangxk
 * @time 2019-10-16  17:59
 */
@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class PerComponentScope