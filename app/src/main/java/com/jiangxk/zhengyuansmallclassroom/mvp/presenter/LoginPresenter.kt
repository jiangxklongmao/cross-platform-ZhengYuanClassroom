package com.jiangxk.zhengyuansmallclassroom.mvp.presenter

import com.jiangxk.common.mvp.presenter.BaseMvpPresenter
import com.jiangxk.common.rxjava.LoadingObserver
import com.jiangxk.zhengyuansmallclassroom.model.TokenModel
import com.jiangxk.zhengyuansmallclassroom.model.UserModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.LoginContract
import com.jiangxk.zhengyuansmallclassroom.repository.login.remote.UserRepository
import com.orhanobut.logger.Logger
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.presenter
 * @author jiangxk
 * @time 2020-03-26  17:31
 */
class LoginPresenter @Inject constructor(private val userRepository: UserRepository) :
    BaseMvpPresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun getToken(
        grant_type: String,
        appId: String,
        secret: String
    ) {
        userRepository.getToken(grant_type, appId, secret)
            .subscribe(object : Observer<TokenModel> {
                override fun onSubscribe(d: Disposable) {
                    addDisposable(d)
                    Logger.i("onSubscribe", TAG)
                }

                override fun onNext(t: TokenModel) {
                    Logger.i("onNext", TAG)
                    Logger.i("t = $t", TAG)

                    mView?.showMessage(t.access_token)

                }

                override fun onError(e: Throwable) {
                    Logger.i("onError", TAG)
                    Logger.i("e = ${e.message}", TAG)
                }

                override fun onComplete() {
                    Logger.i("onComplete", TAG)
                }

            })
    }

    override fun login(phoneNumber: String, password: String) {

        if (phoneNumber.isNullOrEmpty()) {
            mView?.showMessage("请输入手机号")
            return
        }


        userRepository.userLogin(phoneNumber, password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : LoadingObserver<UserModel>(mView, userRepository) {
                override fun onSuccess(data: UserModel) {
                    Logger.i(TAG, "onSuccess")
                }

                override fun onFailure(e: Throwable) {
                    Logger.i(TAG, "Failure " + e.message)
                }

            })


    }


    companion object {
        const val TAG = "LoginPresenter"
    }


}