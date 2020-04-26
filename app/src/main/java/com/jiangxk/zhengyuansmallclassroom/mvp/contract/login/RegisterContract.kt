package com.jiangxk.zhengyuansmallclassroom.mvp.contract.login

import com.jiangxk.common.mvp.presenter.BasePresenter
import com.jiangxk.common.mvp.view.BaseView

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.contract.login
 * @author jiangxk
 * @time 2020-04-26  17:21
 */
interface RegisterContract {
    interface View : BaseView {

        fun showRegisterMethod(method: String)

        fun showDefaultMethod()
    }

    interface Presenter : BasePresenter<View> {
        fun getRegisterMethod()
    }
}