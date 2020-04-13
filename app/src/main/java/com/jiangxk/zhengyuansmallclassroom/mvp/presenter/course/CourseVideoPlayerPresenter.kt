package com.jiangxk.zhengyuansmallclassroom.mvp.presenter.course

import com.jiangxk.common.mvp.presenter.BaseMvpPresenter
import com.jiangxk.common.rxjava.OnceObserver
import com.jiangxk.zhengyuansmallclassroom.model.ParameterModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.course.CourseVideoPlayerContract
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.presenter.course
 * @author jiangxk
 * @time 2020-04-13  15:35
 */
class CourseVideoPlayerPresenter @Inject constructor(private val courseRepository: CourseRepository) :
    BaseMvpPresenter<CourseVideoPlayerContract.View>(), CourseVideoPlayerContract.Presenter {


    override fun uploadLearningLog(parameterModel: ParameterModel) {
        courseRepository.uploadLearningLog(parameterModel)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : OnceObserver<String>() {
                override fun onDispose(disposable: Disposable) {
                }

                override fun onSuccess(t: String) {
                    mView?.uploadSuccess(t)
                }
            })
    }

    override fun updateLearningLogDuration(
        logId: String,
        parameterModel: ParameterModel,
        duration: Long
    ) {

        courseRepository.updateLearningLogDuration(logId, parameterModel, duration)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : OnceObserver<String>() {
                override fun onDispose(disposable: Disposable) {

                }

                override fun onSuccess(t: String) {
                    Logger.i("logId = $t")
                }
            })


    }

}