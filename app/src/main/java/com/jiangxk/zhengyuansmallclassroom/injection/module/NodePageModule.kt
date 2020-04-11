package com.jiangxk.zhengyuansmallclassroom.injection.module

import com.jiangxk.zhengyuansmallclassroom.mvp.contract.course.NodePageContract
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import dagger.Module
import dagger.Provides

/**
 * @description com.jiangxk.zhengyuansmallclassroom.injection.module
 * @author jiangxk
 * @time 2020-04-11  16:27
 */
@Module
class NodePageModule(
    private val view: NodePageContract.View,
    private val courseRepository: CourseRepository
) {
    @Provides
    fun provideView(): NodePageContract.View {
        return this.view
    }

    @Provides
    fun provideCourseRepository(): CourseRepository {
        return this.courseRepository
    }
}