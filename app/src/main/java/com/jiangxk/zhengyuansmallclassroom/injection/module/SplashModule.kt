package com.jiangxk.zhengyuansmallclassroom.injection.module

import com.jiangxk.zhengyuansmallclassroom.mvp.contract.splash.SplashContract
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import dagger.Module
import dagger.Provides

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.module
 * @author jiangxk
 * @time 2020-04-15  14:41
 */
@Module
class SplashModule(
    private val view: SplashContract.View,
    private val userRepository: UserRepository
) {
    @Provides
    fun provideView() = view

    @Provides
    fun provideUserRepository() = userRepository

}