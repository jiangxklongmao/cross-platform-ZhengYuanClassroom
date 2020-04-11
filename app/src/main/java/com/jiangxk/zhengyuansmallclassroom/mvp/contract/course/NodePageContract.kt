package com.jiangxk.zhengyuansmallclassroom.mvp.contract.course

import com.jiangxk.common.mvp.presenter.BasePresenter
import com.jiangxk.common.mvp.view.BaseView
import com.jiangxk.zhengyuansmallclassroom.model.NodeModel

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.presenter.course
 * @author jiangxk
 * @time 2020-04-11  15:42
 */
interface NodePageContract {
    interface View : BaseView {
        /**
         * 显示 Node列表
         * @param nodeList List<NodeModel>
         */
        fun showNodeList(nodeList: List<NodeModel>)
    }

    interface Presenter : BasePresenter<View> {
        /**
         * 获取 Node列表
         * @param subjectId Int
         */
        fun getNodeList(subjectId: Int)
    }
}