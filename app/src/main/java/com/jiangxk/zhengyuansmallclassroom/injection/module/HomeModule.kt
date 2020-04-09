package com.jiangxk.zhengyuansmallclassroom.injection.module

import com.jiangxk.zhengyuansmallclassroom.mvp.contract.home.HomeContract
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import dagger.Module
import dagger.Provides

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.module Dagger -- HomeModule
 * @author jiangxk
 * @time 2020-03-24  18:10
 */
@Module
class HomeModule(
    private val view: HomeContract.View,
    private val courseRepository: CourseRepository
) {

    @Provides
    fun provideView(): HomeContract.View {
        return this.view
    }

    @Provides
    fun provideCourseRepository(): CourseRepository {
        return this.courseRepository
    }

}