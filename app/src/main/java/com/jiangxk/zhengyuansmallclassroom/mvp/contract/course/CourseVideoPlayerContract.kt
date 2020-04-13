package com.jiangxk.zhengyuansmallclassroom.mvp.contract.course

import com.jiangxk.common.mvp.presenter.BasePresenter
import com.jiangxk.common.mvp.view.BaseView
import com.jiangxk.zhengyuansmallclassroom.model.ParameterModel

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.contract.course
 * @author jiangxk
 * @time 2020-04-13  15:35
 */
interface CourseVideoPlayerContract {
    interface View : BaseView {
        fun uploadSuccess(logId: String)
    }

    interface Presenter : BasePresenter<View> {

        fun uploadLearningLog(parameterModel: ParameterModel)

        fun updateLearningLogDuration(logId: String, parameterModel: ParameterModel, duration: Long)

    }
}