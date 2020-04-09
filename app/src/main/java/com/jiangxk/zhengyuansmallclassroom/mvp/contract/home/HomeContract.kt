package com.jiangxk.zhengyuansmallclassroom.mvp.contract.home

import com.jiangxk.common.mvp.presenter.BasePresenter
import com.jiangxk.common.mvp.view.BaseView
import com.jiangxk.zhengyuansmallclassroom.model.GradeModel

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.contract
 * @author jiangxk
 * @time 2020-03-24  17:45
 */
interface HomeContract {
    interface View : BaseView {
        /**
         * 显示年级列表
         * @param gradeList List<GradeModel>
         */
        fun showGradeList(gradeList: List<GradeModel>)
    }

    interface Presenter : BasePresenter<View> {

        /**
         * 获取年级课程列表
         */
        fun getGradeList()

    }
}