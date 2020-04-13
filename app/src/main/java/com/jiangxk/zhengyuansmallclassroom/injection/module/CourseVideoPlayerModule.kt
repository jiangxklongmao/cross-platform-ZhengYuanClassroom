package com.jiangxk.zhengyuansmallclassroom.injection.module

import com.jiangxk.zhengyuansmallclassroom.mvp.contract.course.CourseVideoPlayerContract
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import dagger.Module
import dagger.Provides

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.module
 * @author jiangxk
 * @time 2020-04-13  15:43
 */
@Module
class CourseVideoPlayerModule(
    private val view: CourseVideoPlayerContract.View,
    private val courseRepository: CourseRepository
) {
    @Provides
    fun provideView(): CourseVideoPlayerContract.View {
        return this.view
    }

    @Provides
    fun provideCourseRepository(): CourseRepository {
        return this.courseRepository
    }
}