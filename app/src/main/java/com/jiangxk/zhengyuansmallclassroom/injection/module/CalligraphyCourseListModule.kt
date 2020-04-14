package com.jiangxk.zhengyuansmallclassroom.injection.module

import com.jiangxk.zhengyuansmallclassroom.mvp.contract.course.CalligraphyCourseListContract
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import dagger.Module
import dagger.Provides

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.module
 * @author jiangxk
 * @time 2020-04-14  14:01
 */
@Module
class CalligraphyCourseListModule(
    private val view: CalligraphyCourseListContract.View,
    private val courseRepository: CourseRepository
) {
    @Provides
    fun provideView(): CalligraphyCourseListContract.View {
        return view
    }

    @Provides
    fun provideCourseRepository(): CourseRepository {
        return courseRepository
    }
}