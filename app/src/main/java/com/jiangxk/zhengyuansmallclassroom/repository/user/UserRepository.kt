package com.jiangxk.zhengyuansmallclassroom.repository.user

import com.jiangxk.common.singleton.SingletonHolder2
import com.jiangxk.zhengyuansmallclassroom.model.UserModel
import com.jiangxk.zhengyuansmallclassroom.repository.user.local.IUserLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.user.remote.IUserRemoteApi
import io.reactivex.Observable


/**
 * @description com.jiangxk.zhengyuansmallclassroom.repository.login.remote
 * @author jiangxk
 * @time 2020-03-26  14:50
 */
class UserRepository private constructor(
    private val iUserLocalApi: IUserLocalApi,
    private val iUserRemoteApi: IUserRemoteApi
) : IUserLocalApi, IUserRemoteApi {

    companion object :
        SingletonHolder2<UserRepository, IUserLocalApi, IUserRemoteApi>(::UserRepository)

    override fun saveUser(user: UserModel): Long {
        return iUserLocalApi.saveUser(user)
    }

    override fun queryUserById(userId: Int): Observable<UserModel> {
        return iUserLocalApi.queryUserById(userId)
    }

    override fun userLogin(
        phoneNumber: String,
        password: String
    ): Observable<Array<UserModel>> {
        return iUserRemoteApi.userLogin(phoneNumber, password)
    }


}