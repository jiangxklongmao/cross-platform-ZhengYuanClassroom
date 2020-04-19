package com.jiangxk.zhengyuansmallclassroom.mvp.presenter.course

import com.jiangxk.common.mvp.presenter.BaseMvpPresenter
import com.jiangxk.common.rxjava.LoadingObserver
import com.jiangxk.zhengyuansmallclassroom.model.CourseModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.course.CalligraphyCourseListContract
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.presenter.course
 * @author jiangxk
 * @time 2020-04-14  11:49
 */
class CalligraphyCourseListPresenter @Inject constructor(private val courseRepository: CourseRepository) :
    BaseMvpPresenter<CalligraphyCourseListContract.View>(),
    CalligraphyCourseListContract.Presenter {


    override fun getCourseList(nodeId: Int, page: Int, pageSize: Int) {
        courseRepository.getCalligraphyCourseList(nodeId, page, pageSize)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : LoadingObserver<List<CourseModel>>(mView) {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(t: List<CourseModel>) {
                    mView.showCourseList(t)
                }

            })
    }

    override fun getLimitCountByUser(userId: Int, subjectId: Int, nodeId: Int) {
        courseRepository.getAndUpdateLimitCountByUser(userId, subjectId, nodeId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : LoadingObserver<Int>(mView) {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(t: Int) {
                    mView.showLimitCount(t)
                }
            })

    }

}