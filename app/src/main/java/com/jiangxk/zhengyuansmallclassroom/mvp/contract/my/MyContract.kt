package com.jiangxk.zhengyuansmallclassroom.mvp.contract.my

import com.jiangxk.common.mvp.presenter.BasePresenter
import com.jiangxk.common.mvp.view.BaseView
import com.jiangxk.zhengyuansmallclassroom.model.UserModel

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.contract.my
 * @author jiangxk
 * @time 2020-04-14  16:28
 */
interface MyContract {
    interface View : BaseView {
        /**
         * 显示自己的信息
         * @param user UserModel
         */
        fun showUser(user: UserModel)
    }

    interface Presenter : BasePresenter<View> {
        /**
         * 查询自己的信息
         */
        fun queryUserInfo()
    }
}