package com.jiangxk.zhengyuansmallclassroom.mvp.presenter.manager

import com.jiangxk.common.mvp.presenter.BaseMvpPresenter
import com.jiangxk.common.rxjava.LoadingObserver
import com.jiangxk.zhengyuansmallclassroom.model.UpdateResultModel
import com.jiangxk.zhengyuansmallclassroom.model.UserModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.manager.ManagerUserContract
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.presenter.manager
 * @author jiangxk
 * @time 2020-04-16  10:53
 */
class ManagerUserPresenter @Inject constructor(private val userRepository: UserRepository) :
    BaseMvpPresenter<ManagerUserContract.View>(), ManagerUserContract.Presenter {

    override fun getManagerUserList(page: Int, pageSize: Int) {
        userRepository.getManagerUserList(page, pageSize)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : LoadingObserver<List<UserModel>>(mView) {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(t: List<UserModel>) {
                    mView.showUserList(t)
                }
            })
    }

    override fun modifyPermissions(docId: String, manager: Int) {
        userRepository.modifyPermissions(docId, manager)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : LoadingObserver<UpdateResultModel>(mView) {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(t: UpdateResultModel) {
                    if (t.stats.updated == 1) {
                        mView.modifyPermissionsSuccessful()
                    } else {
                        mView.showMessage("修改失败")
                    }
                }

            })
    }

    override fun modifyStatus(docId: String, status: Int) {
        userRepository.modifyStatus(docId, status)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : LoadingObserver<UpdateResultModel>(mView) {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(t: UpdateResultModel) {
                    if (t.stats.updated == 1) {
                        mView.modifyPermissionsSuccessful()
                    } else {
                        mView.showMessage("修改失败")
                    }
                }
            })
    }
}