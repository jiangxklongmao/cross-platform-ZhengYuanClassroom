package com.jiangxk.zhengyuansmallclassroom.repository.course.remote

import com.jiangxk.zhengyuansmallclassroom.model.GradeModel
import io.reactivex.Observable

/**
 * @description com.jiangxk.zhengyuansmallclassroom.repository.course.remote
 * @author jiangxk
 * @time 2020-04-09  21:36
 */
interface ICourseRemoteApi {
    /**
     * 获取年级课程列表
     * @return Observable<List<GradeModel>>
     */
    fun getGradeList(): Observable<List<GradeModel>>
}