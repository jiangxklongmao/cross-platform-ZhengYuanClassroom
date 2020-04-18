package com.jiangxk.zhengyuansmallclassroom.mvp.presenter.manager

import com.jiangxk.common.mvp.presenter.BaseMvpPresenter
import com.jiangxk.common.rxjava.LoadingObserver
import com.jiangxk.zhengyuansmallclassroom.model.NodeLimitModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.manager.LimitCoursePageContract
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.presenter.manager
 * @author jiangxk
 * @time 2020-04-18  11:43
 */
class LimitCoursePagePresenter @Inject constructor(
    private val userRepository: UserRepository,
    private val courseRepository: CourseRepository
) : BaseMvpPresenter<LimitCoursePageContract.View>(),
    LimitCoursePageContract.Presenter {


    override fun getLimitCourseList(openId: String, userId: Int) {
        courseRepository.getLimitCourseList(openId, userId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : LoadingObserver<List<NodeLimitModel>>(mView) {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(t: List<NodeLimitModel>) {
                    mView.showLimitCourseList(t)
                    countSurplus(t)
                }
            })
    }

    private fun countSurplus(limitList: List<NodeLimitModel>) {
        var count = 0
        var isExist = false
        for (limit in limitList) {
            if (limit.totalCount != -1) {
                isExist = true
                count += limit.totalCount
            }
        }

        if (!isExist) {
            count = -1
        }

        mView.showSurplus(count)
    }

    override fun modifyLimitByUser(
        position: Int,
        openId: String,
        userId: Int,
        subjectId: Int,
        nodeId: Int,
        limitSize: Int,
        totalCount: Int,
        type: Int
    ) {
        courseRepository.modifyLimitByUser(
            openId,
            userId,
            subjectId,
            nodeId,
            if (type == 1) limitSize else null,
            if (type == 2) totalCount else null
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : LoadingObserver<Boolean>(mView) {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(t: Boolean) {
                    if (t) {

                        if (type == 1) {
                            mView.getLimitCourseList()[position].limitSize = limitSize
                            mView.getLimitCourseList()[position].totalCount = limitSize
                        }

                        if (type == 2) {
                            mView.getLimitCourseList()[position].totalCount = totalCount
                        }

                        countSurplus(mView.getLimitCourseList())

                        mView.showModifySuccessful(position)
                    } else {
                        mView.showMessage("修改失败")
                    }
                }
            })
    }

}