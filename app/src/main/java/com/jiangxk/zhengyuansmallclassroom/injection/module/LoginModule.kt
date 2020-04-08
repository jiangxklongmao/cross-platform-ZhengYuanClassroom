package com.jiangxk.zhengyuansmallclassroom.injection.module

import com.jiangxk.zhengyuansmallclassroom.mvp.contract.LoginContract
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import dagger.Module
import dagger.Provides

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.module
 * @author jiangxk
 * @time 2020-03-26  17:39
 */
@Module
class LoginModule(
    private val view: LoginContract.View,
    private val userRepository: UserRepository
) {

    @Provides
    fun provideView(): LoginContract.View {
        return this.view
    }

    @Provides
    fun provideUserRepository(): UserRepository {
        return this.userRepository
    }
}