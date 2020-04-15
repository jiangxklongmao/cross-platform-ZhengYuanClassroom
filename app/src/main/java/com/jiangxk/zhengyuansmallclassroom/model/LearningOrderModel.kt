package com.jiangxk.zhengyuansmallclassroom.model

/**
 * @description com.jiangxk.zhengyuansmallclassroom.model
 * @author jiangxk
 * @time 2020-04-15  21:34
 */
data class LearningOrderModel(
    val _id: String,
    val modifyTime: String,
    val openId: String,
    val status: Int,
    val totalLearningDuration: Long,
    val userId: Int,
    val userName: String
)