package com.jiangxk.zhengyuansmallclassroom.repository.user.remote

import com.google.gson.JsonObject
import com.jiangxk.common.common.model.BaseModel
import com.jiangxk.common.repository.QueryHashMap
import com.jiangxk.common.rxjava.Mapper
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.model.TokenModel
import com.jiangxk.zhengyuansmallclassroom.model.UserModel
import com.jiangxk.zhengyuansmallclassroom.repository.ApiRepository
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * @description com.jiangxk.zhengyuansmallclassroom.repository.login.remote
 * @author jiangxk
 * @time 2020-04-07  16:26
 */
class UserRemoteApi : ApiRepository(), IUserRemoteApi {

    fun getToken(
        grant_type: String,
        appId: String,
        secret: String
    ): Observable<TokenModel> {
        return userService.getToken(grant_type, appId, secret)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun userLogin(
        phoneNumber: String,
        password: String
    ): Observable<Array<UserModel>> {
        return authentication()
            .concatMap {
                val queryHashMap = QueryHashMap().apply {
                    put(Constant.PARAMETER_ACCESS_TOKEN, it)
                    put(Constant.PARAMETER_ENV, Constant.MINI_PROGRAM_CLASSROOM_ENV)
                    put(Constant.PARAMETER_NAME, "appUserLogin")
                }

                val jsonObject = JsonObject()
                jsonObject.addProperty("phoneNumber", phoneNumber)
                jsonObject.addProperty("password", password)

                val requestBody = jsonObject.toString()
                    .toRequestBody("application/json;charset=UTF-8".toMediaTypeOrNull())

                //调用云函数 用户登录
                userService.userLogin(queryHashMap, requestBody)
            }.concatMap {

                Logger.i("it.resp_data = " + it.resp_data)


                Observable.just(it.getData() as BaseModel<Array<UserModel>>)
            }
            .map(Mapper())
            .subscribeOn(Schedulers.io())
    }

}