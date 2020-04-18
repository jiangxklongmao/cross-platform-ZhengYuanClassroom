package com.jiangxk.zhengyuansmallclassroom.mvp.contract.manager

import com.jiangxk.common.mvp.presenter.BasePresenter
import com.jiangxk.common.mvp.view.BaseView
import com.jiangxk.zhengyuansmallclassroom.model.NodeLimitModel

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.contract.manager
 * @author jiangxk
 * @time 2020-04-18  11:42
 */
interface LimitCoursePageContract {
    interface View : BaseView {

        /**
         * 限制Node 课程
         * @param limitList List<NodeLimitModel>
         */
        fun showLimitCourseList(limitList: List<NodeLimitModel>)

        /**
         * 显示剩余次数
         * @param surplus Int
         */
        fun showSurplus(surplus: Int)

        fun getLimitCourseList(): List<NodeLimitModel>

        /**
         * 显示修改成功
         * @param position Int
         */
        fun showModifySuccessful(position: Int)


    }

    interface Presenter : BasePresenter<View> {

        /**
         *  获取指定用户的 限制课程
         * @param openId String
         * @param userId Int
         */
        fun getLimitCourseList(openId: String, userId: Int)


        /**
         * 修改用户 对应node课程观看限制
         * @param position Int
         * @param openId String
         * @param userId Int
         * @param subjectId Int
         * @param nodeId Int
         * @param limitSize Int
         * @param totalCount Int
         * @param type Int
         */
        fun modifyLimitByUser(
            position: Int,
            openId: String,
            userId: Int,
            subjectId: Int,
            nodeId: Int,
            limitSize: Int,
            totalCount: Int,
            type: Int
        )
    }
}