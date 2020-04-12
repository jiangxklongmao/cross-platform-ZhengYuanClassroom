package com.jiangxk.zhengyuansmallclassroom.repository.course

import com.jiangxk.common.singleton.SingletonHolder2
import com.jiangxk.zhengyuansmallclassroom.model.*
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

    override fun getSubjectList(gradeId: Int): Observable<List<SubjectModel>> {
        return courseRemoteApi.getSubjectList(gradeId)
    }

    override fun getNodeList(subjectId: Int): Observable<List<NodeModel>> {
        return courseRemoteApi.getNodeList(subjectId)
    }

    override fun getChapterList(
        nodeId: Int,
        page: Int,
        pageSize: Int
    ): Observable<List<ChapterModel>> {
        return courseRemoteApi.getChapterList(nodeId, page, pageSize)
    }

    override fun getCourseList(
        chapterId: Int,
        page: Int,
        pageSize: Int
    ): Observable<List<CourseModel>> {
        return courseRemoteApi.getCourseList(chapterId, page, pageSize)
    }
}