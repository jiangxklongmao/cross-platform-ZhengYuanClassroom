package com.jiangxk.zhengyuansmallclassroom.repository.user

import com.jiangxk.common.singleton.SingletonHolder2
import com.jiangxk.zhengyuansmallclassroom.model.LearningOrderModel
import com.jiangxk.zhengyuansmallclassroom.model.UpdateResultModel
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

    override fun authenticationToken(): Observable<String> {
        return iUserRemoteApi.authenticationToken()
    }


    override fun saveUser(user: UserModel): Long {
        return iUserLocalApi.saveUser(user)
    }

    override fun getUserById(docId: String): Observable<UserModel> {
        return iUserRemoteApi.getUserById(docId)
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

    override fun getLearningOrderList(
        page: Int,
        pageSize: Int
    ): Observable<List<LearningOrderModel>> {
        return iUserRemoteApi.getLearningOrderList(page, pageSize)
    }

    override fun getManagerUserList(page: Int, pageSize: Int): Observable<List<UserModel>> {
        return iUserRemoteApi.getManagerUserList(page, pageSize)
    }

    override fun modifyPermissions(docId: String, manager: Int): Observable<UpdateResultModel> {
        return iUserRemoteApi.modifyPermissions(docId, manager)
    }

    override fun modifyStatus(docId: String, status: Int): Observable<UpdateResultModel> {
        return iUserRemoteApi.modifyStatus(docId, status)
    }

    override fun deleteUser(docId: String): Observable<Boolean> {
        return iUserRemoteApi.deleteUser(docId)
    }

}