package com.jiangxk.zhengyuansmallclassroom.repository.course.remote

import com.jiangxk.zhengyuansmallclassroom.model.*
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

    /***
     * 获取SubjectList
     * @param gradeId Int
     * @return Observable<List<SubjectModel>>
     */
    fun getSubjectList(gradeId: Int): Observable<List<SubjectModel>>

    /**
     * 获取NodeList
     * @param subjectId Int
     * @return Observable<List<NodeModel>>
     */
    fun getNodeList(subjectId: Int): Observable<List<NodeModel>>

    /**
     * 获取 chapterList
     * @param nodeId Int
     * @return Observable<List<ChapterModel>>
     */
    fun getChapterList(nodeId: Int, page: Int, pageSize: Int): Observable<List<ChapterModel>>

    /**
     * 获取 courseList
     * @param chapterId Int
     * @return Observable<List<CourseModel>>
     */
    fun getCourseList(chapterId: Int, page: Int, pageSize: Int): Observable<List<CourseModel>>
}