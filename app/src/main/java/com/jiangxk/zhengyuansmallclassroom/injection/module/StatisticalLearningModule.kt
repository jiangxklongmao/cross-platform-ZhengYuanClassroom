package com.jiangxk.zhengyuansmallclassroom.injection.module

import com.jiangxk.zhengyuansmallclassroom.mvp.contract.learning.StatisticalLearningContract
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import dagger.Module
import dagger.Provides

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.module
 * @author jiangxk
 * @time 2020-04-20  17:09
 */
@Module
class StatisticalLearningModule(
    private val view: StatisticalLearningContract.View,
    private val userRepository: UserRepository
) {

    @Provides
    fun provideView() = view

    @Provides
    fun provideUserRespository() = userRepository

}