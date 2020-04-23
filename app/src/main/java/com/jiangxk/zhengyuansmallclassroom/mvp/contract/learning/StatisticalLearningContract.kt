package com.jiangxk.zhengyuansmallclassroom.mvp.contract.learning

import com.jiangxk.common.mvp.presenter.BasePresenter
import com.jiangxk.common.mvp.view.BaseView
import com.jiangxk.zhengyuansmallclassroom.model.LearningLogModel
import com.jiangxk.zhengyuansmallclassroom.model.UserModel

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.contract.learning
 * @author jiangxk
 * @time 2020-04-20  16:47
 */

interface StatisticalLearningContract {
    interface View : BaseView {
        /**
         * 显示总学习时长
         * @param duration Long
         */
        fun showTotalDuration(duration: Long)

        /**
         * 显示学习日志列表
         * @param logList List<LearningLogModel>
         */
        fun showLearningList(logList: List<LearningLogModel>)

        /**
         * 显示用户信息
         * @param userModel UserModel
         */
        fun showUser(userModel: UserModel)
    }

    interface Presenter : BasePresenter<View> {

        /**
         * 获取用户总学习时长
         * @param openId String
         * @param userId Int
         */
        fun getTotalDuration(openId: String, userId: Int)

        /**
         *  获取近期学习时长
         * @param openId String
         * @param userId Int
         */
        fun getRecentLearningList(openId: String, userId: Int)

        /**
         * 获取指定用户信息
         * @param openId String
         * @param userId Int
         */
        fun getUserByOpenIdAndUserId(openId: String, userId: Int)
    }
}