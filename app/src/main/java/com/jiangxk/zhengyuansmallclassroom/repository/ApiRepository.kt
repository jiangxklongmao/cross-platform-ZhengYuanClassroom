package com.jiangxk.zhengyuansmallclassroom.repository

import com.jiangxk.common.common.model.BaseMiniProgramModel
import com.jiangxk.common.common.model.BaseModel
import com.jiangxk.common.http.RetrofitFactory
import com.jiangxk.common.repository.BaseRepository
import com.jiangxk.common.repository.FiledHashMap
import com.jiangxk.common.repository.QueryHashMap
import com.jiangxk.common.rxjava.ClassroomThrowable
import com.jiangxk.common.utils.AppPrefsUtils
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.model.TokenModel
import com.jiangxk.zhengyuansmallclassroom.repository.course.remote.CourseService
import com.jiangxk.zhengyuansmallclassroom.repository.user.remote.UserService
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import java.util.*

/**
 * @description com.jiangxk.zhengyuansmallclassroom.repository
 * @author jiangxk
 * @time 2020-03-26  14:22
 */
abstract class ApiRepository : BaseRepository() {
    private val retrofitFactory by lazy {
        RetrofitFactory.getInstance(Constant.BASE_URL)
    }

    private val authenticationService: AuthenticationService by lazy {
        retrofitFactory.create(AuthenticationService::class.java)
    }

    protected val apiService: ApiService by lazy {
        retrofitFactory.create(ApiService::class.java)
    }

    protected val userService: UserService by lazy {
        retrofitFactory.create(UserService::class.java)
    }

    protected val courseService: CourseService by lazy {
        retrofitFactory.create(CourseService::class.java)
    }

    protected fun authentication(): Observable<String> {
        val token = AppPrefsUtils.getString(Constant.SP_MINI_PROGRAM_CLASSROOM_TOKEN_KEY)
        val timestamp =
            AppPrefsUtils.getLong(Constant.SP_MINI_PROGRAM_CLASSROOM_TOKEN_EXPIRE_TIMESTAMP_KEY)

        val timeZone = TimeZone.getTimeZone("GMT+8")
        val calendar = Calendar.getInstance(timeZone)

        Logger.i("calendar = " + calendar.time.toString())
        Logger.i("calendar = " + calendar.time.time)

        calendar.time.time

        if (token.isNullOrEmpty() || timestamp == 0L || timestamp < calendar.time.time) {
            return getNewAccessTokenFromRemote()
        }


        return Observable.just(token)
    }

    /**
     * 获取服务器最新的Token
     */
    private fun getNewAccessTokenFromRemote(): Observable<String> {
        return authenticationService.getToken(
            Constant.PARAMETER_GRANT_TYPE_VALUE,
            Constant.MINI_PROGRAM_TRAINING_CENTRE_APP_ID,
            Constant.MINI_PROGRAM_TRAINING_CENTRE_APP_SECRET
        ).concatMap {

            val fileHashMap = FiledHashMap().apply {
                put(Constant.PARAMETER_ACCESS_TOKEN, it.access_token)
                put(Constant.PARAMETER_ENV, Constant.MINI_PROGRAM_TRAINING_CENTRE_ENV)
                put(Constant.PARAMETER_NAME, "getClassroomToken")
            }
            //调用云函数 获取 正元小课堂 token
            authenticationService.getClassroomToken(fileHashMap)
        }.map {
            Logger.i("Token = $it")

//            val type = object : TypeToken<BaseModel<TokenModel>>() {}.type
//            val baseModel: BaseModel<TokenModel> = Gson().fromJson(it.resp_data, type)

//            val type: Type = object : TypeToken<BaseModel<TokenModel>>() {}.type
//            val data: BaseModel<TokenModel> = Gson().fromJson(it.resp_data, type)

            val data: BaseModel<TokenModel> = it.getData()

            data.data
        }.map {
            AppPrefsUtils.putString(
                Constant.SP_MINI_PROGRAM_CLASSROOM_TOKEN_KEY,
                it.access_token
            )
            AppPrefsUtils.putLong(
                Constant.SP_MINI_PROGRAM_CLASSROOM_TOKEN_EXPIRE_TIMESTAMP_KEY,
                it.expires_timestamp + it.expires_in * 1000
            )
            it.access_token
        }
    }


    /**
     * 小程序云返回 过滤错误码
     * @param base BaseMiniProgramModel<T>
     * @return Boolean
     */
    protected fun <T> miniProgramResponseFilter(base: BaseMiniProgramModel<T>): Boolean {
        if (base == null) {
            throw ClassroomThrowable(
                com.jiangxk.common.constant.Constant.NETWORK_ERROR_CODE_10007,
                "云服务返回空",
                Throwable()
            )
        }
        if (base.errcode == 0) {
            return true
        }

        var message =
            when (base.errcode) {
                -1 -> {
                    "云服务系统错误"
                }
                -1000 -> {
                    "云服务系统错误"
                }
                42001 -> {
                    AppPrefsUtils.putString(Constant.SP_MINI_PROGRAM_CLASSROOM_TOKEN_KEY, "")
                    "AccessToken过期，请重试"
                }
                else -> {
                    "云服务未知错误"
                }
            }
        throw ClassroomThrowable(
            base.errcode,
            message,
            Throwable()
        )
    }
}