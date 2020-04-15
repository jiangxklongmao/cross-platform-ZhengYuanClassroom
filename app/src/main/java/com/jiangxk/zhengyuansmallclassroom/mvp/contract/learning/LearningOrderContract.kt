package com.jiangxk.zhengyuansmallclassroom.mvp.contract.learning

import com.jiangxk.common.mvp.presenter.BasePresenter
import com.jiangxk.common.mvp.view.BaseView
import com.jiangxk.zhengyuansmallclassroom.model.LearningOrderModel

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.contract.learning
 * @author jiangxk
 * @time 2020-04-15  16:39
 */
interface LearningOrderContract {
    interface View : BaseView {
        /**
         * 显示用户排名
         * @param array List<LearningOrderModel>
         */
        fun showLearningOrderList(array: List<LearningOrderModel>)
    }

    interface Presenter : BasePresenter<View> {

        /**
         * 获取学生排名
         */
        fun getLearningOrderList(page: Int, pageSize: Int)

    }
}