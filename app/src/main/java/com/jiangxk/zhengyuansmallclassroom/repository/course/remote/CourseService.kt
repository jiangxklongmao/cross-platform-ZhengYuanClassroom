package com.jiangxk.zhengyuansmallclassroom.repository.course.remote

import com.jiangxk.common.common.model.BaseMiniProgramModel
import com.jiangxk.common.repository.QueryHashMap
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.model.GradeModel
import com.jiangxk.zhengyuansmallclassroom.model.SubjectModel
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
}