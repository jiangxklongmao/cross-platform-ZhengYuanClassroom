package com.jiangxk.zhengyuansmallclassroom.injection.module

import com.jiangxk.zhengyuansmallclassroom.mvp.contract.course.SubjectPageContract
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import dagger.Module
import dagger.Provides

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.module
 * @author jiangxk
 * @time 2020-04-10  18:49
 */
@Module
class SubjectPageModule(
    private val view: SubjectPageContract.View,
    private val courseRepository: CourseRepository
) {
    @Provides
    fun provideView(): SubjectPageContract.View {
        return this.view
    }

    @Provides
    fun provideCourseRepository(): CourseRepository {
        return this.courseRepository
    }
}