package com.jiangxk.zhengyuansmallclassroom.mvp.presenter.splash

import com.jiangxk.common.mvp.presenter.BaseMvpPresenter
import com.jiangxk.common.rxjava.OnceObserver
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.splash.SplashContract
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.presenter.splash
 * @author jiangxk
 * @time 2020-04-15  14:17
 */
class SplashPresenter @Inject constructor(private val userRepository: UserRepository) :
    BaseMvpPresenter<SplashContract.View>(), SplashContract.Presenter {

    override fun authenticationToken() {

        userRepository.authenticationToken()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : OnceObserver<String>() {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(t: String) {
                    mView.authenticationComplete()
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    mView.authenticationComplete()
                }
            })
    }
}