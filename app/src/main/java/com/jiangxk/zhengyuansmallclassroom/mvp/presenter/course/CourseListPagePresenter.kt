package com.jiangxk.zhengyuansmallclassroom.mvp.presenter.course

import com.jiangxk.common.mvp.presenter.BaseMvpPresenter
import com.jiangxk.common.rxjava.LoadingObserver
import com.jiangxk.zhengyuansmallclassroom.model.CourseModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.course.CourseListPageContract
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.presenter.course
 * @author jiangxk
 * @time 2020-04-12  18:00
 */
class CourseListPagePresenter @Inject constructor(private val courseRepository: CourseRepository) :
    BaseMvpPresenter<CourseListPageContract.View>(), CourseListPageContract.Presenter {

    override fun getCourseList(subjecId: Int, chapterId: Int, page: Int, pageSize: Int) {
        courseRepository.getCourseList(subjecId, chapterId, page, pageSize)
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

}