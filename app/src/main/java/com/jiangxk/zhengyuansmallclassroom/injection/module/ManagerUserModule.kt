package com.jiangxk.zhengyuansmallclassroom.injection.module

import com.jiangxk.zhengyuansmallclassroom.mvp.contract.manager.ManagerUserContract
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import dagger.Module
import dagger.Provides

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.module
 * @author jiangxk
 * @time 2020-04-16  11:03
 */
@Module
class ManagerUserModule(
    private val view: ManagerUserContract.View,
    private val userRepository: UserRepository
) {
    @Provides
    fun provideView() = view

    @Provides
    fun provideUserRepository() = userRepository
}