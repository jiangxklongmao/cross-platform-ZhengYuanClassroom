package com.jiangxk.zhengyuansmallclassroom.component

import com.jiangxk.provider.component.IApplicationLike
import com.jiangxk.provider.component.Router
import com.jiangxk.provider.service.HomeService

/**
 * @description com.jiangxk.zhengyuansmallclassroom.component
 * @author jiangxk
 * @time 2020-03-24  17:40
 */

class HomeApplicationLike : IApplicationLike {
    override fun registered() {
        Router.addService(HomeService::class.java.simpleName, HomeServiceImpl())
    }

    override fun unregistered() {
        Router.removeService(HomeService::class.java.simpleName)
    }

}