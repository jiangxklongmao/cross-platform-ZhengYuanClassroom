package com.jiangxk.zhengyuansmallclassroom.repository

import com.jiangxk.common.common.model.BaseMiniProgramModel
import com.jiangxk.common.repository.QueryHashMap
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.model.TokenModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * @description com.jiangxk.zhengyuansmallclassroom.repository 鉴权服务
 * @author jiangxk
 * @time 2020-04-03  15:46
 */
interface AuthenticationService {
    /**
     * 鉴权接口
     */
    fun <T> authentication(): Observable<T>

    @GET(Constant.METHOD_GET_TOKEN)
    fun getToken(
        @Query(Constant.PARAMETER_GRANT_TYPE) grant_type: String,
        @Query(Constant.PARAMETER_APPID) appid: String,
        @Query(Constant.PARAMETER_SECRET) secret: String
    ): Observable<TokenModel>

    @GET(Constant.METHOD_POST_INVOKE_CLOUD_FUNCTION)
    fun getClassroomToken(@QueryMap queryHashMap: QueryHashMap): Observable<BaseMiniProgramModel<TokenModel>>

}