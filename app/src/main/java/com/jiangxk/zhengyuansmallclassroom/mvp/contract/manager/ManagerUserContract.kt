package com.jiangxk.zhengyuansmallclassroom.mvp.contract.manager

import com.jiangxk.common.mvp.presenter.BasePresenter
import com.jiangxk.common.mvp.view.BaseView
import com.jiangxk.zhengyuansmallclassroom.model.UserModel

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.contract.manager
 * @author jiangxk
 * @time 2020-04-16  10:50
 */
interface ManagerUserContract {
    interface View : BaseView {
        /**
         * 显示用户列表
         * @param userList List<UserModel>
         */
        fun showUserList(userList: List<UserModel>)
    }

    interface Presenter : BasePresenter<View> {
        /**
         * 获取管理用户列表
         * @param page Int
         * @param pageSize Int
         */
        fun getManagerUserList(page: Int, pageSize: Int)
    }
}