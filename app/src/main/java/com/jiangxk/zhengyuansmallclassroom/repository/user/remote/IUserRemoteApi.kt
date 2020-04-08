package com.jiangxk.zhengyuansmallclassroom.repository.user.remote

import com.jiangxk.zhengyuansmallclassroom.model.UserModel
import io.reactivex.Observable

/**
 * @description com.jiangxk.zhengyuansmallclassroom.repository.login.remote
 * @author jiangxk
 * @time 2020-04-07  16:24
 */
interface IUserRemoteApi {
    /**
     * 用户登录接口
     * @param phoneNumber String
     * @param password String
     * @return Observable<BaseModel<Array<UserModel>>>
     */
    fun userLogin(phoneNumber: String, password: String): Observable<Array<UserModel>>
}