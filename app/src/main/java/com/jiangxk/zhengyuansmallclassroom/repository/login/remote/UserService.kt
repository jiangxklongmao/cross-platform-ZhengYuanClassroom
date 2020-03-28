package com.jiangxk.zhengyuansmallclassroom.repository.login.remote

import com.jiangxk.common.model.BaseModel
import com.jiangxk.zhengyuansmallclassroom.constant.Constant.PARAMETER_APPID
import com.jiangxk.zhengyuansmallclassroom.constant.Constant.PARAMETER_GRANT_TYPE
import com.jiangxk.zhengyuansmallclassroom.constant.Constant.PARAMETER_SECRET
import com.jiangxk.zhengyuansmallclassroom.constant.Constant.PATH_GET_TOKEN
import com.jiangxk.zhengyuansmallclassroom.model.TokenModel
import com.jiangxk.zhengyuansmallclassroom.model.UserModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @description com.jiangxk.zhengyuansmallclassroom.repository.login.remote
 * @author jiangxk
 * @time 2020-03-26  15:03
 */
interface UserService {
    @GET(PATH_GET_TOKEN)
    fun getToken(
        @Query(PARAMETER_GRANT_TYPE) grant_type: String,
        @Query(PARAMETER_APPID) appid: String,
        @Query(PARAMETER_SECRET) secret: String
    ): Observable<TokenModel>

    fun login(@Query("grant_type") phoneNumber: String, password: String): Observable<BaseModel<UserModel>>
}