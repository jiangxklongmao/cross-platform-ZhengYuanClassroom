package com.jiangxk.common.rxjava

import com.jiangxk.common.constant.Constant
import com.jiangxk.common.mvp.view.BaseView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * @description com.jiangxk.common.rxjava
 * @author jiangxk
 * @time 2020-03-26  15:30
 */
abstract class LoadingObserver<T>(
    private val mView: BaseView
) : Observer<T> {

    override fun onSubscribe(d: Disposable) {
        mView.showLoading()
        onDispose(d)
    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        mView.showError()

        val error = when (e) {
            is ClassroomThrowable -> {
                e
            }
            is IOException -> {
                ClassroomThrowable(Constant.NETWORK_ERROR_CODE_10003, "网络错误", e.cause)
            }
            is ConnectException, is SocketTimeoutException -> {
                ClassroomThrowable(Constant.NETWORK_ERROR_CODE_10004, "服务器错误", e.cause)
            }
            is ClassCastException -> {
                ClassroomThrowable(Constant.NETWORK_ERROR_CODE_10005, "数据转换错误", e.cause)
            }
            else -> {
                ClassroomThrowable(Constant.NETWORK_ERROR_CODE_10006, "未知错误", e.cause)
            }
        }
        mView.showMessage(error.toString())
        mView.showContent()
    }

    override fun onComplete() {
        mView.showContent()
    }

    abstract fun onDispose(disposable: Disposable)

    abstract fun onSuccess(t: T)


}