package com.jiangxk.zhengyuansmallclassroom.mvp.presenter.home

import com.jiangxk.common.mvp.presenter.BaseMvpPresenter
import com.jiangxk.common.rxjava.LoadingObserver
import com.jiangxk.zhengyuansmallclassroom.model.GradeModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.home.HomeContract
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.presenter
 * @author jiangxk
 * @time 2020-03-24  17:44
 */
class HomePresenter @Inject constructor(private val courseRepository: CourseRepository) :
    BaseMvpPresenter<HomeContract.View>(), HomeContract.Presenter {

    override fun getGradeList() {
        courseRepository.getGradeList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : LoadingObserver<List<GradeModel>>(mView) {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(t: List<GradeModel>) {
                    mView.showGradeList(t)
                }
            })
    }
}