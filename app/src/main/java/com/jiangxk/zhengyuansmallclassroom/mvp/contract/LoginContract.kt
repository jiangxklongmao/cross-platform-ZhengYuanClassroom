package com.jiangxk.zhengyuansmallclassroom.mvp.contract

import com.jiangxk.common.mvp.presenter.BasePresenter
import com.jiangxk.common.mvp.view.BaseView

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.contract
 * @author jiangxk
 * @time 2020-03-26  17:29
 */
interface LoginContract {
    interface View : BaseView {
        /**
         * 登录成功
         */
        fun loginSuccess()
    }

    interface Presenter : BasePresenter<View> {

        fun login(phoneNumber: String, password: String)

    }
}