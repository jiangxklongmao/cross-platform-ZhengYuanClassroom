package com.jiangxk.common.rxjava

import com.orhanobut.logger.Logger
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @description com.jiangxk.common.rxjava
 * @author jiangxk
 * @time 2020-04-13  20:15
 */
abstract class OnceObserver<T> : Observer<T> {

    override fun onSubscribe(d: Disposable) {
        onDispose(d)
    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        e.message?.let {
            Logger.e(it)
        }
    }

    override fun onComplete() {
    }

    abstract fun onDispose(disposable: Disposable)

    abstract fun onSuccess(t: T)
}