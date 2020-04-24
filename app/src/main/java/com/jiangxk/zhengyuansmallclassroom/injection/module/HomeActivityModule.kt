package com.jiangxk.zhengyuansmallclassroom.injection.module

import com.jiangxk.zhengyuansmallclassroom.mvp.contract.home.HomeActivityContract
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import dagger.Module
import dagger.Provides

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.module
 * @author jiangxk
 * @time 2020-04-24  16:45
 */
@Module
class HomeActivityModule(
    private val view: HomeActivityContract.View,
    private val userRespository: UserRepository
) {

    @Provides
    fun provideView() = view

    @Provides
    fun provideUserRepository() = userRespository
}