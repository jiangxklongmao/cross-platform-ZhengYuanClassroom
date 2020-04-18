package com.jiangxk.zhengyuansmallclassroom.injection.module

import com.jiangxk.zhengyuansmallclassroom.mvp.contract.manager.LimitCoursePageContract
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import dagger.Module
import dagger.Provides

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.module
 * @author jiangxk
 * @time 2020-04-18  11:48
 */
@Module
class LimitCoursePageModule(
    private val view: LimitCoursePageContract.View,
    private val userRepository: UserRepository,
    private val courseRepository: CourseRepository
) {
    @Provides
    fun provideView() = view

    @Provides
    fun provideUserRepository() = userRepository

    @Provides
    fun provideCourseRepository() = courseRepository
}