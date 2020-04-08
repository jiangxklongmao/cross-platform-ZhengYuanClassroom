package com.jiangxk.zhengyuansmallclassroom.mvp.presenter

import com.jiangxk.common.mvp.presenter.BaseMvpPresenter
import com.jiangxk.common.rxjava.LoadingObserver
import com.jiangxk.zhengyuansmallclassroom.model.UserModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.LoginContract
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.presenter
 * @author jiangxk
 * @time 2020-03-26  17:31
 */
class LoginPresenter @Inject constructor(private val userRepository: UserRepository) :
    BaseMvpPresenter<LoginContract.View>(), LoginContract.Presenter {


    override fun login(phoneNumber: String, password: String) {

        if (phoneNumber.isNullOrEmpty()) {
            mView.showMessage("请输入手机号")
            return
        }

        if (phoneNumber.length != 11) {
            mView.showMessage("请输入正确的手机号")
            return
        }

        if (password.isNullOrEmpty()) {
            mView.showMessage("请输入密码")
            return
        }

        if (password.length < 6) {
            mView.showMessage("请输入6位以上有效密码")
            return
        }

        userRepository.userLogin(phoneNumber, password)
            .concatMap {
                Observable.fromArray(*it)
            }
            .doOnNext {
                userRepository.saveUser(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : LoadingObserver<UserModel>(mView) {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(user: UserModel) {
                    mView.showMessage("onSuccess")
                    mView.showMessage(user.userName)
                }

            })
    }

    override fun queryUserFromDatabase() {
        userRepository.queryUserById(1000)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : LoadingObserver<UserModel>(mView) {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(user: UserModel) {
                    mView.showMessage("onSuccess")
                    mView.showMessage(user.userName)

                    Logger.i(user.toString())
                }

            })
    }


    companion object {
        const val TAG = "LoginPresenter"
    }


}