package com.jiangxk.zhengyuansmallclassroom.mvp.presenter.course

import com.jiangxk.common.mvp.presenter.BaseMvpPresenter
import com.jiangxk.common.rxjava.LoadingObserver
import com.jiangxk.zhengyuansmallclassroom.model.ChapterModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.course.ChapterPageContract
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.presenter.course
 * @author jiangxk
 * @time 2020-04-11  18:39
 */
class ChapterPagePresenter @Inject constructor(private val courseRepository: CourseRepository) :
    BaseMvpPresenter<ChapterPageContract.View>(),
    ChapterPageContract.Presenter {


    override fun getChapterList(nodeId: Int, page: Int, pageSize: Int) {
        courseRepository.getChapterList(nodeId, page, pageSize)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : LoadingObserver<List<ChapterModel>>(mView) {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(t: List<ChapterModel>) {
                    mView.showChapterList(t)
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