package com.jiangxk.zhengyuansmallclassroom.mvp.contract.course

import com.jiangxk.common.mvp.presenter.BasePresenter
import com.jiangxk.common.mvp.view.BaseView
import com.jiangxk.zhengyuansmallclassroom.model.SubjectModel

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.contract.course
 * @author jiangxk
 * @time 2020-04-10  18:27
 */
interface SubjectPageContract {
    interface View : BaseView {
        /**
         * 显示Subject 列表
         * @param subjectList List<SubjectModel>
         */
        fun showSubjectList(subjectList: List<SubjectModel>)
    }

    interface Presenter : BasePresenter<View> {
        /**
         * 获取 Subject列表
         * @param gradeId Int
         */
        fun getSubjectList(gradeId: Int)
    }
}