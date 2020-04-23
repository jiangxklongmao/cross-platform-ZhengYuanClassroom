package com.jiangxk.zhengyuansmallclassroom.mvp.presenter.learning

import com.jiangxk.common.mvp.presenter.BaseMvpPresenter
import com.jiangxk.common.rxjava.LoadingObserver
import com.jiangxk.common.rxjava.OnceObserver
import com.jiangxk.zhengyuansmallclassroom.model.LearningLogModel
import com.jiangxk.zhengyuansmallclassroom.model.LearningTotalDurationModel
import com.jiangxk.zhengyuansmallclassroom.model.UserModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.learning.StatisticalLearningContract
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.presenter.learning
 * @author jiangxk
 * @time 2020-04-20  17:00
 */
class StatisticalLearningPresenter @Inject constructor(private val userRepository: UserRepository) :
    BaseMvpPresenter<StatisticalLearningContract.View>(),
    StatisticalLearningContract.Presenter {

    override fun getUserByOpenIdAndUserId(openId: String, userId: Int) {
        userRepository.getUserByOpenIdAndUserId(openId, userId)
            .concatMap {
                Observable.just(it.first())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : OnceObserver<UserModel>() {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(t: UserModel) {
                    mView.showUser(t)
                }

            })
    }

    override fun getTotalDuration(openId: String, userId: Int) {
        userRepository.getLearningTotalDurationList(openId, userId)
            .concatMap {
                Observable.just(it.first())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : OnceObserver<LearningTotalDurationModel>() {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(t: LearningTotalDurationModel) {
                    mView.showTotalDuration(t.totalLearningDuration)
                }

            })
    }

    override fun getRecentLearningList(openId: String, userId: Int) {
        userRepository.getRecentLearningLogList(openId, userId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : LoadingObserver<List<LearningLogModel>>(mView) {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(t: List<LearningLogModel>) {
                    mView.showLearningList(t.reversed())

                }
            })
    }

}