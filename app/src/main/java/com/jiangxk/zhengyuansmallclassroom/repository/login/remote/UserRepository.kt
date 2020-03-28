package com.jiangxk.zhengyuansmallclassroom.repository.login.remote

import com.jiangxk.common.model.BaseModel
import com.jiangxk.zhengyuansmallclassroom.model.TokenModel
import com.jiangxk.zhengyuansmallclassroom.model.UserModel
import com.jiangxk.zhengyuansmallclassroom.repository.ApiRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @description com.jiangxk.zhengyuansmallclassroom.repository.login.remote
 * @author jiangxk
 * @time 2020-03-26  14:50
 */
class UserRepository() : ApiRepository() {

    fun getToken(
        grant_type: String,
        appId: String,
        secret: String
    ): Observable<TokenModel> {
        return userService.getToken(grant_type, appId, secret)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun userLogin(phoneNumber: String, password: String): Observable<BaseModel<UserModel>> {
        return userService.login(phoneNumber, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}