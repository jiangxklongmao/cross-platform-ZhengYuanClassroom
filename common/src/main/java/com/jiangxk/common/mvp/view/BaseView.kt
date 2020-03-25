package com.jiangxk.common.mvp.view

/**
 * @desc View 基类
 * @auth jiangxk
 * @time 2019-10-16  17:37
 */
interface BaseView {
    /**
     * 显示Loading
     */
    fun showLoading()

    /**
     * 显示Content
     */
    fun showContent()

    /**
     * 显示Error
     */
    fun showError()

    /**
     * 显示Empty
     */
    fun showEmpty()
}