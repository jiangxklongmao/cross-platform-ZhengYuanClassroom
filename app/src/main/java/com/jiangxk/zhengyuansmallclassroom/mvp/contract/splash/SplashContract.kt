package com.jiangxk.zhengyuansmallclassroom.mvp.contract.splash

import com.jiangxk.common.mvp.presenter.BasePresenter
import com.jiangxk.common.mvp.view.BaseView

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.contract.splash
 * @author jiangxk
 * @time 2020-04-15  14:16
 */
interface SplashContract {
    interface View : BaseView {

        fun authenticationComplete()

    }

    interface Presenter : BasePresenter<View> {
        /**
         * 鉴权
         */
        fun authenticationToken()
    }
}