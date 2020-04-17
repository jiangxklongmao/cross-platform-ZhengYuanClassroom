package com.jiangxk.zhengyuansmallclassroom.repository.user.remote

import com.jiangxk.zhengyuansmallclassroom.model.LearningOrderModel
import com.jiangxk.zhengyuansmallclassroom.model.UpdateResultModel
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

    /**
     * 鉴权
     */
    fun authenticationToken(): Observable<String>

    /**
     * 获取学习排名
     * @param page Int
     * @param pageSize Int
     */
    fun getLearningOrderList(page: Int, pageSize: Int): Observable<List<LearningOrderModel>>

    /**
     * 管理用户列表
     * @param page Int
     * @param pageSize Int
     */
    fun getManagerUserList(page: Int, pageSize: Int): Observable<List<UserModel>>

    /**
     * 修改权限
     * @param docId String
     * @param manager Int
     */
    fun modifyPermissions(docId: String, manager: Int): Observable<UpdateResultModel>

    /**
     * 修改状态
     * @param docId String
     * @param status Int
     */
    fun modifyStatus(docId: String, status: Int): Observable<UpdateResultModel>

}