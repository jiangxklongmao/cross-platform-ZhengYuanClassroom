package com.jiangxk.common.mvp.presenter

import com.jiangxk.common.mvp.view.BaseView

/**
 * @desc Presenter 基类
 * @auth jiangxk
 * @time 2019-10-16  17:34
 */
interface BasePresenter<in T : BaseView> {
    /**
     * 绑定View
     */
    fun attachView(view: T)

    /**
     * 解除绑定
     */
    fun detachView()
}