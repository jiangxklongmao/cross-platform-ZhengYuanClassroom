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

        /**
         * 显示 限制次数
         * @param limitCount Int
         */
        fun showLimitCount(limitCount: Int)
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

        /**
         * 查询限制次数
         * @param userId Int
         * @param subjectId Int
         * @param nodeId Int
         */
        fun getLimitCountByUser(userId: Int, subjectId: Int, nodeId: Int)
    }
}