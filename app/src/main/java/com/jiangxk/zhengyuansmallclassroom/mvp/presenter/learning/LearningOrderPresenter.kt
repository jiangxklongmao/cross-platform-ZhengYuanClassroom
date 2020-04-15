package com.jiangxk.zhengyuansmallclassroom.mvp.presenter.learning

import com.jiangxk.common.mvp.presenter.BaseMvpPresenter
import com.jiangxk.common.rxjava.LoadingObserver
import com.jiangxk.zhengyuansmallclassroom.model.LearningOrderModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.learning.LearningOrderContract
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.presenter.learning
 * @author jiangxk
 * @time 2020-04-15  16:41
 */
class LearningOrderPresenter @Inject constructor(private val userRepository: UserRepository) :
    BaseMvpPresenter<LearningOrderContract.View>(), LearningOrderContract.Presenter {

    override fun getLearningOrderList(page: Int, pageSize: Int) {
        userRepository.getLearningOrderList(page, pageSize)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : LoadingObserver<List<LearningOrderModel>>(mView) {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(t: List<LearningOrderModel>) {
                    mView.showLearningOrderList(t)
                }
            })
    }
}