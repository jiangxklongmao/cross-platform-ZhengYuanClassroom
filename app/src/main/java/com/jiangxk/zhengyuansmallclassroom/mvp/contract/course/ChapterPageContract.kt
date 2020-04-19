package com.jiangxk.zhengyuansmallclassroom.mvp.contract.course

import com.jiangxk.common.mvp.presenter.BasePresenter
import com.jiangxk.common.mvp.view.BaseView
import com.jiangxk.zhengyuansmallclassroom.model.ChapterModel

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.contract.course
 * @author jiangxk
 * @time 2020-04-11  18:35
 */
interface ChapterPageContract {
    interface View : BaseView {
        /**
         * 显示 chapter列表
         * @param nodeList List<ChapterModel>
         */
        fun showChapterList(nodeList: List<ChapterModel>)

        /**
         * 显示 限制次数
         * @param limitCount Int
         */
        fun showLimitCount(limitCount: Int)
    }

    interface Presenter : BasePresenter<View> {
        /**
         * 获取 Chapter列表
         * @param nodeId Int
         * @param page Int
         * @param pageSize Int
         */
        fun getChapterList(nodeId: Int, page: Int, pageSize: Int)

        /**
         * 查询限制次数
         * @param userId Int
         * @param subjectId Int
         * @param nodeId Int
         */
        fun getLimitCountByUser(userId: Int, subjectId: Int, nodeId: Int)
    }
}