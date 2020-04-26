package com.jiangxk.zhengyuansmallclassroom.injection.module

import com.jiangxk.zhengyuansmallclassroom.mvp.contract.login.RegisterContract
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import dagger.Module
import dagger.Provides

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.module
 * @author jiangxk
 * @time 2020-04-26  17:24
 */
@Module
class RegisterModule(
    private val view: RegisterContract.View,
    private val userRepository: UserRepository
) {
    @Provides
    fun provideView() = view

    @Provides
    fun provideUserRepository() = userRepository
}