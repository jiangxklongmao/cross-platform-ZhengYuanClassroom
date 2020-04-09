package com.jiangxk.zhengyuansmallclassroom.repository.course

import com.jiangxk.common.singleton.SingletonHolder2
import com.jiangxk.zhengyuansmallclassroom.model.GradeModel
import com.jiangxk.zhengyuansmallclassroom.repository.course.local.ICourseLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.course.remote.ICourseRemoteApi
import io.reactivex.Observable

/**
 * @description com.jiangxk.zhengyuansmallclassroom.repository.course
 * @author jiangxk
 * @time 2020-04-09  21:34
 */
class CourseRepository private constructor(
    private val courseLocalApi: ICourseLocalApi,
    private val courseRemoteApi: ICourseRemoteApi
) : ICourseLocalApi, ICourseRemoteApi {

    companion object :
        SingletonHolder2<CourseRepository, ICourseLocalApi, ICourseRemoteApi>(::CourseRepository)


    override fun getGradeList(): Observable<List<GradeModel>> {
        return courseRemoteApi.getGradeList()
    }

}