package com.jiangxk.zhengyuansmallclassroom.repository.user.local

import com.jiangxk.zhengyuansmallclassroom.model.UserModel
import io.reactivex.Observable

/**
 * @description com.jiangxk.zhengyuansmallclassroom.repository.login.local
 * @author jiangxk
 * @time 2020-04-06  22:47
 */
interface IUserLocalApi {

    /**
     * 插入或替换User
     * @param user UserModel
     * @return Long
     */
    fun saveUser(user: UserModel): Long

    /**
     * 查询 User By Id
     * @param userId Int
     * @return Observable<UserModel>
     */
    fun queryUserById(userId: Int): Observable<UserModel>
}