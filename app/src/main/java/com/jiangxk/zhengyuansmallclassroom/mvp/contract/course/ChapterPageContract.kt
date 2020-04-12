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
    }

    interface Presenter : BasePresenter<View> {
        /**
         * 获取 Chapter列表
         * @param nodeId Int
         * @param page Int
         * @param pageSize Int
         */
        fun getChapterList(nodeId: Int, page: Int, pageSize: Int)
    }
}