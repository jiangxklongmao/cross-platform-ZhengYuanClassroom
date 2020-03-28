package com.jiangxk.common.rxjava

import com.jiangxk.common.common.BaseConstant
import com.jiangxk.common.model.BaseModel
import com.jiangxk.common.mvp.view.BaseView
import com.jiangxk.common.repository.BaseRepository
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @description com.jiangxk.common.rxjava
 * @author jiangxk
 * @time 2020-03-26  15:30
 */
abstract class LoadingObserver<T>(
    private val mView: BaseView?,
    private val mRepository: BaseRepository
) : Observer<BaseModel<T>> {

    override fun onSubscribe(d: Disposable) {
        mRepository.addSubsribe(d)
        mView?.showLoading()
    }

    override fun onNext(baseModel: BaseModel<T>) {
        when (baseModel.code) {
            BaseConstant.NETWORK_SUCCESS -> {
                onSuccess(baseModel.data)
            }
        }
    }

    override fun onError(e: Throwable) {
        mView?.showError()
        e.message?.let {
            mView?.showMessage(it)
        }
        onFailure(e)
    }

    override fun onComplete() {
        mView?.showContent()
    }

    abstract fun onSuccess(data: T)

    abstract fun onFailure(e: Throwable)

}