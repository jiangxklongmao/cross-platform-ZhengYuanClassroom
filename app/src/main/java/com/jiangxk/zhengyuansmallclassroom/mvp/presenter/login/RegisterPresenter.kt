package com.jiangxk.zhengyuansmallclassroom.mvp.presenter.login

import com.jiangxk.common.mvp.presenter.BaseMvpPresenter
import com.jiangxk.common.rxjava.LoadingObserver
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.login.RegisterContract
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.presenter.login
 * @author jiangxk
 * @time 2020-04-26  17:23
 */
class RegisterPresenter @Inject constructor(private val userRepository: UserRepository) :
    BaseMvpPresenter<RegisterContract.View>(), RegisterContract.Presenter {
    override fun getRegisterMethod() {
        userRepository.getRegisterMethod()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : LoadingObserver<String>(mView) {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(t: String) {
                    mView.showRegisterMethod(t)
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    mView.showDefaultMethod()
                }
            })
    }
}