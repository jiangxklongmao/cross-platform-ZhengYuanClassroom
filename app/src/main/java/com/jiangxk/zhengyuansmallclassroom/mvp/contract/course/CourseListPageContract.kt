package com.jiangxk.zhengyuansmallclassroom.mvp.contract.course

import com.jiangxk.common.mvp.presenter.BasePresenter
import com.jiangxk.common.mvp.view.BaseView
import com.jiangxk.zhengyuansmallclassroom.model.CourseModel

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.contract.course
 * @author jiangxk
 * @time 2020-04-12  17:55
 */
interface CourseListPageContract {
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
         * @param chapterId Int
         * @param page Int
         * @param pageSize Int
         */
        fun getCourseList(subjectId: Int, chapterId: Int, page: Int, pageSize: Int)
    }
}