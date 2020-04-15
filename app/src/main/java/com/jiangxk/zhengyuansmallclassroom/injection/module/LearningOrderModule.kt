package com.jiangxk.zhengyuansmallclassroom.injection.module

import com.jiangxk.zhengyuansmallclassroom.mvp.contract.learning.LearningOrderContract
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import dagger.Module
import dagger.Provides

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.module
 * @author jiangxk
 * @time 2020-04-15  20:52
 */
@Module
class LearningOrderModule(
    private val view: LearningOrderContract.View,
    private val userRepository: UserRepository
) {
    @Provides
    fun provideView() = view

    @Provides
    fun provideUserRepository() = userRepository
}