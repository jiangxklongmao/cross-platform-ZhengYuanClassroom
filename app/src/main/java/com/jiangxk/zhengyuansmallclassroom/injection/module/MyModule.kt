package com.jiangxk.zhengyuansmallclassroom.injection.module

import com.jiangxk.zhengyuansmallclassroom.mvp.contract.my.MyContract
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import dagger.Module
import dagger.Provides

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.module
 * @author jiangxk
 * @time 2020-04-14  16:27
 */
@Module
class MyModule(
    private val view: MyContract.View,
    private val userRepository: UserRepository
) {
    @Provides
    fun provideView(): MyContract.View {
        return view
    }

    @Provides
    fun provideUserRepository(): UserRepository {
        return userRepository
    }
}