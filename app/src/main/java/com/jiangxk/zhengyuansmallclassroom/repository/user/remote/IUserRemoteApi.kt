package com.jiangxk.zhengyuansmallclassroom.repository.user.remote

import com.jiangxk.zhengyuansmallclassroom.model.*
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

    fun getUserById(docId: String): Observable<UserModel>

    /**
     * 获取用户信息
     * @param openId String
     * @param userId Int
     * @return Observable<UserModel>
     */
    fun getUserByOpenIdAndUserId(openId: String, userId: Int): Observable<List<UserModel>>

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

    /**
     * 删除用户
     * @param docId String
     * @return Observable<Boolean>
     */
    fun deleteUser(docId: String): Observable<Boolean>

    /**
     * 获取指定用户的 学习总时长
     * @param openId String
     * @param userId Int
     * @return Observable<List<LearningTotalDurationModel>>
     */
    fun getLearningTotalDurationList(
        openId: String,
        userId: Int
    ): Observable<List<LearningTotalDurationModel>>

    /**
     * 获取近期 学习日志列表
     * @param openId String
     * @param userId Int
     * @return Observable<List<LearningLogModel>>
     */
    fun getRecentLearningLogList(
        openId: String,
        userId: Int
    ): Observable<List<LearningLogModel>>

    /**
     * 检查更新
     * @param versionCode Int
     * @return Observable<List<LearningLogModel>>
     */
    fun checkForUpdates(
        versionCode: Int
    ): Observable<List<UpdateModel>>

    /**
     * 获取注册方式
     */
    fun getRegisterMethod(): Observable<String>
}