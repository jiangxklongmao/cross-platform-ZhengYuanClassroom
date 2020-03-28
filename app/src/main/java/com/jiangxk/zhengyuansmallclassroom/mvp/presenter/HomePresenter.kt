package com.jiangxk.zhengyuansmallclassroom.mvp.presenter

import com.jiangxk.common.mvp.presenter.BaseMvpPresenter
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.HomeContract
import javax.inject.Inject

/**
 * @description com.jiangxk.zhengyuansmallclassroom.mvp.presenter
 * @author jiangxk
 * @time 2020-03-24  17:44
 */
class HomePresenter @Inject constructor() : BaseMvpPresenter<HomeContract.View>(),
    HomeContract.Presenter {

}