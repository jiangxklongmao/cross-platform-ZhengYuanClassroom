package com.jiangxk.common.common.activity

import com.jiangxk.common.common.BaseApplication
import com.jiangxk.common.injection.component.DaggerActivityComponent
import com.jiangxk.common.injection.module.ActivityModule
import com.jiangxk.common.mvp.presenter.BaseMvpPresenter
import com.jiangxk.common.mvp.view.BaseView
import javax.inject.Inject

/**
 * @description com.jiangxk.common.ui.activity
 * @author jiangxk
 * @time 2020-03-26  17:25
 */
abstract class BaseMvpActivity<V : BaseView, T : BaseMvpPresenter<V>> : BaseActivity(), BaseView {
    @Inject
    lateinit var mPresenter: T

    lateinit var mActivityComponent: DaggerActivityComponent

    override fun initOperate() {
        initActivityInjection()
        injectComponent()
//        mPresenter.attachView(this as V)
    }

    /*注册依赖关系*/
    abstract fun injectComponent()

    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityComponent.builder()
            .appComponent((this.application as BaseApplication).appComponent)
            .activityModule(ActivityModule(this, this))
            .build() as DaggerActivityComponent
    }

    override fun showEmpty() {

    }

    override fun showError() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}