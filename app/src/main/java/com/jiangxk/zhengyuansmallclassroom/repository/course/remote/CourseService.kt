package com.jiangxk.zhengyuansmallclassroom.repository.course.remote

import com.jiangxk.common.common.model.BaseMiniProgramModel
import com.jiangxk.common.repository.QueryHashMap
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.model.GradeModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap

/**
 * @description com.jiangxk.zhengyuansmallclassroom.repository.user.remote
 * @author jiangxk
 * @time 2020-04-09  21:44
 */
interface CourseService {
    /**
     * 用户登录
     */
    @Headers("Content-type:application/json")
    @GET(Constant.METHOD_POST_INVOKE_CLOUD_FUNCTION)
    fun getGradeList(@QueryMap queryHashMap: QueryHashMap): Observable<BaseMiniProgramModel<List<GradeModel>>>
}