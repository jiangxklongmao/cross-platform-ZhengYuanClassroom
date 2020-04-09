package com.jiangxk.zhengyuansmallclassroom.mvp.presenter

import com.jiangxk.common.mvp.presenter.BaseMvpPresenter
import com.jiangxk.common.rxjava.LoadingObserver
import com.jiangxk.common.utils.AppPrefsUtils
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.model.UserModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.LoginContract
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import io.reactivex.Observable
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

                AppPrefsUtils.putString(
                    Constant.SP_PERSONAL_INFORMATION_OPEN_ID_KEY,
                    it.openId
                )
                AppPrefsUtils.putInt(
                    Constant.SP_PERSONAL_INFORMATION_USER_ID_KEY,
                    it.userId
                )
                AppPrefsUtils.putString(
                    Constant.SP_PERSONAL_INFORMATION_PHONE_NUMBER_KEY,
                    it.phoneNumber
                )
                AppPrefsUtils.putString(
                    Constant.SP_PERSONAL_INFORMATION_PASSWORD_KEY,
                    it.password
                )
                AppPrefsUtils.putString(
                    Constant.SP_PERSONAL_INFORMATION_OPEN_ID_KEY,
                    it.openId
                )
                AppPrefsUtils.putString(
                    Constant.SP_PERSONAL_INFORMATION_USER_NAME_KEY,
                    it.userName
                )
                AppPrefsUtils.putString(
                    Constant.SP_PERSONAL_INFORMATION_AVATAR_URL_KEY,
                    it.avatarUrl
                )

            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : LoadingObserver<UserModel>(mView) {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(user: UserModel) {
                    mView.loginSuccess()
                }

            })
    }


    companion object {
        const val TAG = "LoginPresenter"
    }


}