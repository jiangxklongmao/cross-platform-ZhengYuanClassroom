package com.jiangxk.zhengyuansmallclassroom.injection.module

import com.jiangxk.zhengyuansmallclassroom.mvp.contract.course.ChapterPageContract
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import dagger.Module
import dagger.Provides

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.module
 * @author jiangxk
 * @time 2020-04-11  18:41
 */
@Module
class ChapterPageModule(
    private val view: ChapterPageContract.View,
    private val courseRepository: CourseRepository
) {
    @Provides
    fun provideView(): ChapterPageContract.View {
        return this.view
    }

    @Provides
    fun provideCourseRepository(): CourseRepository {
        return this.courseRepository
    }
}