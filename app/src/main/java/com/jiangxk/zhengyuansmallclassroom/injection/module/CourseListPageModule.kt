package com.jiangxk.zhengyuansmallclassroom.injection.module

import com.jiangxk.zhengyuansmallclassroom.mvp.contract.course.CourseListPageContract
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import dagger.Module
import dagger.Provides

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.module
 * @author jiangxk
 * @time 2020-04-12  19:09
 */
@Module
class CourseListPageModule(
    private val view: CourseListPageContract.View,
    private val courseRepository: CourseRepository
) {
    @Provides
    fun provideView(): CourseListPageContract.View {
        return this.view
    }

    @Provides
    fun provideCourseRepository(): CourseRepository {
        return this.courseRepository
    }
}