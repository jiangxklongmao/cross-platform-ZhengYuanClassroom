package com.jiangxk.zhengyuansmallclassroom.mvp.contract.home

import com.jiangxk.common.mvp.presenter.BasePresenter
import com.jiangxk.common.mvp.view.BaseView

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.contract.home
 * @author jiangxk
 * @time 2020-04-24  16:43
 */
interface HomeActivityContract {
    interface View : BaseView {
        fun showUpdateDialog(apkUrl: String, updateInfo: String, isForce: Boolean)
    }

    interface Presenter : BasePresenter<View> {

        /**
         * 检查更新
         * @param versionCode Int
         */
        fun checkForUpdates(versionCode: Int)
    }
}