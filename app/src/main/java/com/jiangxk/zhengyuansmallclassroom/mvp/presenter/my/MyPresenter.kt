package com.jiangxk.zhengyuansmallclassroom.mvp.presenter.my

import com.jiangxk.common.mvp.presenter.BaseMvpPresenter
import com.jiangxk.common.rxjava.LoadingObserver
import com.jiangxk.common.utils.AppPrefsUtils
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.model.UserModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.my.MyContract
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.presenter.my
 * @author jiangxk
 * @time 2020-04-14  16:28
 */
class MyPresenter @Inject constructor(private val userRepository: UserRepository) :
    BaseMvpPresenter<MyContract.View>(), MyContract.Presenter {

    override fun queryUserInfo() {
//        userRepository.queryUserById(AppPrefsUtils.getInt(Constant.SP_PERSONAL_INFORMATION_USER_ID_KEY))
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : LoadingObserver<UserModel>(mView) {
//                override fun onDispose(disposable: Disposable) {
//                    addDisposable(disposable)
//                }
//
//                override fun onSuccess(t: UserModel) {
//                    mView.showUser(t)
//                }
//
//            })

        val docId = AppPrefsUtils.getString(Constant.SP_PERSONAL_INFORMATION_DOCUMENT_ID_KEY)
            ?: return

        userRepository.getUserById(docId)
            .subscribeOn(Schedulers.io())
            .doOnNext {
                userRepository.saveUser(it)

                AppPrefsUtils.putString(
                    Constant.SP_PERSONAL_INFORMATION_DOCUMENT_ID_KEY,
                    it._id
                )
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
                    Constant.SP_PERSONAL_INFORMATION_USER_NAME_KEY,
                    it.userName
                )
                AppPrefsUtils.putString(
                    Constant.SP_PERSONAL_INFORMATION_AVATAR_URL_KEY,
                    it.avatarUrl
                )
                AppPrefsUtils.putInt(
                    Constant.SP_PERSONAL_INFORMATION_MANAGER_KEY,
                    it.manager
                )
                AppPrefsUtils.putInt(
                    Constant.SP_PERSONAL_INFORMATION_STATUS_KEY,
                    it.status
                )

            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : LoadingObserver<UserModel>(mView) {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(t: UserModel) {
                    mView.showUser(t)
                }
            })

    }
}