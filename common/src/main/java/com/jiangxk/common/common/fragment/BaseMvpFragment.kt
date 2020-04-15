package com.jiangxk.common.common.fragment

import android.app.Activity
import com.jiangxk.common.common.BaseApplication
import com.jiangxk.common.injection.component.DaggerActivityComponent
import com.jiangxk.common.injection.module.ActivityModule
import com.jiangxk.common.mvp.presenter.BasePresenter
import com.jiangxk.common.mvp.view.BaseView
import javax.inject.Inject

/**
 * @desc
 * @auth jiangxk
 * @time 2019-10-16  17:45
 */
abstract class BaseMvpFragment<T : BasePresenter<*>> : BaseFragment(), BaseView {

    @Inject
    lateinit var mPresenter: T

    lateinit var mActivityComponent: DaggerActivityComponent

    override fun initOperate() {
        initActivityInjection()
        injectComponent()
    }

    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityComponent.builder()
            .appComponent((activity?.application as BaseApplication).appComponent)
            .activityModule(ActivityModule(activity as Activity, this))
            .build() as DaggerActivityComponent
    }

    /*注册依赖关系*/
    abstract fun injectComponent()

    override fun showEmpty() {

    }

    override fun showError() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }
}