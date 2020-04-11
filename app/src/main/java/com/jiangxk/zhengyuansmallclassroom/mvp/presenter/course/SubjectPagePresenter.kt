package com.jiangxk.zhengyuansmallclassroom.mvp.presenter.course

import com.jiangxk.common.mvp.presenter.BaseMvpPresenter
import com.jiangxk.common.rxjava.LoadingObserver
import com.jiangxk.zhengyuansmallclassroom.model.SubjectModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.course.SubjectPageContract
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.presenter.course
 * @author jiangxk
 * @time 2020-04-10  18:30
 */
class SubjectPagePresenter @Inject constructor(private val courseRepository: CourseRepository) :
    BaseMvpPresenter<SubjectPageContract.View>(),
    SubjectPageContract.Presenter {

    override fun getSubjectList(gradeId: Int) {
        courseRepository.getSubjectList(gradeId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : LoadingObserver<List<SubjectModel>>(mView) {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(t: List<SubjectModel>) {
                    mView.showSubjectList(t)
                }
            })
    }

}