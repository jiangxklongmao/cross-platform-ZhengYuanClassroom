package com.jiangxk.zhengyuansmallclassroom.injection.module

import com.jiangxk.provider.service.HomeService
import com.jiangxk.zhengyuansmallclassroom.component.HomeServiceImpl
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.HomeContract
import dagger.Module
import dagger.Provides

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.module Dagger -- HomeModule
 * @author jiangxk
 * @time 2020-03-24  18:10
 */
@Module
class HomeModule(private val view: HomeContract.View) {

    @Provides
    fun provideHomeService(homeService: HomeServiceImpl): HomeService {
        return homeService
    }

    @Provides
    fun provideView(): HomeContract.View {
        return this.view
    }

}