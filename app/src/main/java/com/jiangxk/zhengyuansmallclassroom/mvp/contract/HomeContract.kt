package com.jiangxk.zhengyuansmallclassroom.mvp.contract

import com.jiangxk.common.mvp.presenter.BasePresenter
import com.jiangxk.common.mvp.view.BaseView

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.contract
 * @author jiangxk
 * @time 2020-03-24  17:45
 */
interface HomeContract {
    interface View :BaseView{}
    interface Presenter:BasePresenter<View>{}
}