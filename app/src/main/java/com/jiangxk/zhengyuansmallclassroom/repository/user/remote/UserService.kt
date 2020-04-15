package com.jiangxk.zhengyuansmallclassroom.repository.user.remote

import com.jiangxk.common.common.model.BaseMiniProgramModel
import com.jiangxk.common.repository.QueryHashMap
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.constant.Constant.METHOD_GET_TOKEN
import com.jiangxk.zhengyuansmallclassroom.constant.Constant.PARAMETER_APPID
import com.jiangxk.zhengyuansmallclassroom.constant.Constant.PARAMETER_GRANT_TYPE
import com.jiangxk.zhengyuansmallclassroom.constant.Constant.PARAMETER_SECRET
import com.jiangxk.zhengyuansmallclassroom.model.LearningOrderModel
import com.jiangxk.zhengyuansmallclassroom.model.TokenModel
import com.jiangxk.zhengyuansmallclassroom.model.UserModel
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * @description com.jiangxk.zhengyuansmallclassroom.repository.login.remote
 * @author jiangxk
 * @time 2020-03-26  15:03
 */
interface UserService {

    @GET(METHOD_GET_TOKEN)
    fun getToken(
        @Query(PARAMETER_GRANT_TYPE) grant_type: String,
        @Query(PARAMETER_APPID) appid: String,
        @Query(PARAMETER_SECRET) secret: String
    ): Observable<TokenModel>


    /**
     * 用户登录
     */
    @Headers("Content-type:application/json")
    @POST(Constant.METHOD_POST_INVOKE_CLOUD_FUNCTION)
    fun userLogin(@QueryMap queryHashMap: QueryHashMap, @Body body: RequestBody): Observable<BaseMiniProgramModel<UserModel>>

    /**
     * 获取学生学习排名
     */
    @Headers("Content-type:application/json")
    @POST(Constant.METHOD_POST_INVOKE_CLOUD_FUNCTION)
    fun getUserLearningOrderList(@QueryMap queryHashMap: QueryHashMap, @Body body: RequestBody): Observable<BaseMiniProgramModel<LearningOrderModel>>
}