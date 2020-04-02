package com.jiangxk.common.mvp.presenter

import com.jiangxk.common.mvp.view.BaseView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @description com.jiangxk.common.mvp.presenter
 * @author jiangxk
 * @time 2020-03-26  19:02
 */
abstract class BaseMvpPresenter<T : BaseView> : BasePresenter<T> {
    @Inject
    lateinit var mView: T

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    private fun dispose() {
        mCompositeDisposable.clear()
    }

    protected fun addDisposable(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }

    @Inject
    override fun attachView(view: T) {
        mView = view
    }

    override fun detachView() {
        dispose()
    }
}