package com.jiangxk.zhengyuansmallclassroom.repository.course.remote

import com.jiangxk.common.common.model.BaseMiniProgramModel
import com.jiangxk.common.repository.QueryHashMap
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.model.*
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * @description com.jiangxk.zhengyuansmallclassroom.repository.user.remote
 * @author jiangxk
 * @time 2020-04-09  21:44
 */
interface CourseService {
    /**
     * 获取年级课程
     * @param queryHashMap QueryHashMap
     * @return Observable<BaseMiniProgramModel<List<GradeModel>>>
     */
    @Headers("Content-type:application/json")
    @GET(Constant.METHOD_POST_INVOKE_CLOUD_FUNCTION)
    fun getGradeList(@QueryMap queryHashMap: QueryHashMap): Observable<BaseMiniProgramModel<List<GradeModel>>>

    /**
     * 获取 Subject 列表
     * @param queryHashMap QueryHashMap
     * @param filedHashMap FiledHashMap
     * @return Observable<BaseMiniProgramModel<List<SubjectModel>>>
     */
    @Headers("Content-type:application/json")
    @POST(Constant.METHOD_POST_INVOKE_CLOUD_FUNCTION)
    fun getSubjectList(@QueryMap queryHashMap: QueryHashMap, @Body body: RequestBody): Observable<BaseMiniProgramModel<List<SubjectModel>>>

    /**
     * 获取NodeList
     * @param queryHashMap QueryHashMap
     * @param body RequestBody
     * @return Observable<BaseMiniProgramModel<List<NodeModel>>>
     */
    @Headers("Content-type:application/json")
    @POST(Constant.METHOD_POST_INVOKE_CLOUD_FUNCTION)
    fun getNodeList(@QueryMap queryHashMap: QueryHashMap, @Body body: RequestBody): Observable<BaseMiniProgramModel<List<NodeModel>>>

    /**
     * 获取 ChapterList
     * @param queryHashMap QueryHashMap
     * @param body RequestBody
     * @return Observable<BaseMiniProgramModel<List<ChapterModel>>>
     */
    @Headers("Content-type:application/json")
    @POST(Constant.METHOD_POST_INVOKE_CLOUD_FUNCTION)
    fun getChapterList(@QueryMap queryHashMap: QueryHashMap, @Body body: RequestBody): Observable<BaseMiniProgramModel<List<ChapterModel>>>

    /**
     * 获取 courseList
     * @param queryHashMap QueryHashMap
     * @param body RequestBody
     * @return Observable<BaseMiniProgramModel<List<ChapterModel>>>
     */
    @Headers("Content-type:application/json")
    @POST(Constant.METHOD_POST_INVOKE_CLOUD_FUNCTION)
    fun getCourseList(@QueryMap queryHashMap: QueryHashMap, @Body body: RequestBody): Observable<BaseMiniProgramModel<List<CourseModel>>>

    /**
     * 上传学习日志
     * @param queryHashMap QueryHashMap
     * @param body RequestBody
     * @return Observable<BaseMiniProgramModel<String>>
     */
    @Headers("Content-type:application/json")
    @POST(Constant.METHOD_POST_INVOKE_CLOUD_FUNCTION)
    fun uploadLearningLog(@QueryMap queryHashMap: QueryHashMap, @Body body: RequestBody): Observable<BaseMiniProgramModel<String>>

    /**
     * 更新学习日志时长
     * @param queryHashMap QueryHashMap
     * @param body RequestBody
     * @return Observable<BaseMiniProgramModel<String>>
     */
    @Headers("Content-type:application/json")
    @POST(Constant.METHOD_POST_INVOKE_CLOUD_FUNCTION)
    fun updateLearningLogDuration(@QueryMap queryHashMap: QueryHashMap, @Body body: RequestBody): Observable<BaseMiniProgramModel<String>>

    /**
     * 获取 书法课程courseList
     * @param queryHashMap QueryHashMap
     * @param body RequestBody
     * @return Observable<BaseMiniProgramModel<List<ChapterModel>>>
     */
    @Headers("Content-type:application/json")
    @POST(Constant.METHOD_POST_INVOKE_CLOUD_FUNCTION)
    fun getCalligraphyCourseList(@QueryMap queryHashMap: QueryHashMap, @Body body: RequestBody): Observable<BaseMiniProgramModel<List<CourseModel>>>

    /**
     * 获取 Node 限制播放次数列表
     * @param queryHashMap QueryHashMap
     * @param body RequestBody
     * @return Observable<BaseMiniProgramModel<List<CourseModel>>>
     */
    @Headers("Content-type:application/json")
    @POST(Constant.METHOD_POST_INVOKE_CLOUD_FUNCTION)
    fun getLimitCourseList(@QueryMap queryHashMap: QueryHashMap, @Body body: RequestBody): Observable<BaseMiniProgramModel<List<CourseModel>>>

    /**
     * 修改 用户 观看课程限制
     * @param queryHashMap QueryHashMap
     * @param body RequestBody
     * @return Observable<BaseMiniProgramModel<List<CourseModel>>>
     */
    @Headers("Content-type:application/json")
    @POST(Constant.METHOD_POST_INVOKE_CLOUD_FUNCTION)
    fun modifyLimitByUser(@QueryMap queryHashMap: QueryHashMap, @Body body: RequestBody): Observable<BaseMiniProgramModel<Boolean>>

}