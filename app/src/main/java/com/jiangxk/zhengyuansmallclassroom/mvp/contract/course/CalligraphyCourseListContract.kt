package com.jiangxk.zhengyuansmallclassroom.mvp.contract.course

import com.jiangxk.common.mvp.presenter.BasePresenter
import com.jiangxk.common.mvp.view.BaseView
import com.jiangxk.zhengyuansmallclassroom.model.CourseModel

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.contract
 * @author jiangxk
 * @time 2020-04-14  11:48
 */
interface CalligraphyCourseListContract {
    interface View : BaseView {
        /**
         * 获取 课程列表
         * @param courseList List<CourseModel>
         */
        fun showCourseList(courseList: List<CourseModel>)
    }

    interface Presenter : BasePresenter<View> {
        /**
         * 获取 课程列表
         * @param subjectId Int
         * @param nodeId Int
         * @param page Int
         * @param pageSize Int
         */
        fun getCourseList(nodeId: Int, page: Int, pageSize: Int)
    }
}