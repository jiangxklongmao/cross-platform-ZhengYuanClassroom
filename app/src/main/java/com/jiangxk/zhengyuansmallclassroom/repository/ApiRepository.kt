package com.jiangxk.zhengyuansmallclassroom.repository

import com.jiangxk.common.http.RetrofitFactory
import com.jiangxk.common.repository.BaseRepository
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.repository.login.remote.UserService

/**
 * @description com.jiangxk.zhengyuansmallclassroom.repository
 * @author jiangxk
 * @time 2020-03-26  14:22
 */
abstract class ApiRepository : BaseRepository() {
    private val retrofitFactory by lazy {
        RetrofitFactory.getInstance(Constant.BASE_URL)
    }

    protected val apiService: ApiService by lazy {
        retrofitFactory.create(ApiService::class.java)
    }

    protected val userService: UserService by lazy {
        retrofitFactory.create(UserService::class.java)
    }
}