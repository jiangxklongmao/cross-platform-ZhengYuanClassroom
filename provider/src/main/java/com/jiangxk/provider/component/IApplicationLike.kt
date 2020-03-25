package com.jiangxk.provider.component

/**
 * @desc 组件加载，卸载接口
 * @auth jiangxk
 * @time 2019-10-31  16:15
 */
interface IApplicationLike {
    /**
     * 组件注册
     */
    fun registered()

    /**
     * 组件卸载
     */
    fun unregistered()
}