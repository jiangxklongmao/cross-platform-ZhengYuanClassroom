package com.jiangxk.zhengyuansmallclassroom.mvp.presenter.home

import com.jiangxk.common.mvp.presenter.BaseMvpPresenter
import com.jiangxk.common.rxjava.LoadingObserver
import com.jiangxk.zhengyuansmallclassroom.model.UpdateModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.home.HomeActivityContract
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.presenter.home
 * @author jiangxk
 * @time 2020-04-24  16:44
 */
class HomeActivityPresenter @Inject constructor(private val userRepository: UserRepository) :
    BaseMvpPresenter<HomeActivityContract.View>(),
    HomeActivityContract.Presenter {

    override fun checkForUpdates(versionCode: Int) {
        userRepository.checkForUpdates(versionCode)
            .flatMap {
                Observable.just(it.first())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : LoadingObserver<UpdateModel>(mView) {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(t: UpdateModel) {
                    if (t.versionCode > versionCode) {
                        mView.showUpdateDialog(
                            t.downloadUrl,
                            t.description,
                            "force" == t.updateType
                        )
                    }
                }
            })
    }

}