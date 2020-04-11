package com.jiangxk.zhengyuansmallclassroom.mvp.presenter.course

import com.jiangxk.common.mvp.presenter.BaseMvpPresenter
import com.jiangxk.common.rxjava.LoadingObserver
import com.jiangxk.zhengyuansmallclassroom.model.NodeModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.course.NodePageContract
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.presenter.course
 * @author jiangxk
 * @time 2020-04-11  15:46
 */
class NodePagePresenter @Inject constructor(private val courseRepository: CourseRepository) :
    BaseMvpPresenter<NodePageContract.View>(), NodePageContract.Presenter {

    override fun getNodeList(subjectId: Int) {
        courseRepository.getNodeList(subjectId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : LoadingObserver<List<NodeModel>>(mView) {
                override fun onDispose(disposable: Disposable) {
                    addDisposable(disposable)
                }

                override fun onSuccess(t: List<NodeModel>) {
                    mView.showNodeList(t)
                }
            })
    }

}