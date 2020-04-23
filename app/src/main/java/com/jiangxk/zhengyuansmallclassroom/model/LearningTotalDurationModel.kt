package com.jiangxk.zhengyuansmallclassroom.model

/**
 * @description com.jiangxk.zhengyuansmallclassroom.model
 * @author jiangxk
 * @time 2020-04-22  22:59
 */
data class LearningTotalDurationModel(
    val _id: String,
    val modifyTime: String,
    val openId: String,
    val status: Int,
    val totalLearningDuration: Long,
    val userId: Int,
    val userName: String
)