package com.jiangxk.zhengyuansmallclassroom.model

/**
 * @description com.jiangxk.zhengyuansmallclassroom.model
 * @author jiangxk
 * @time 2020-04-23  13:50
 */
data class LearningLogModel(
    val _id: String,
    val createTime: String,
    val dailyLog: List<DailyLog>,
    val learningDate: String,
    val modifyTime: String,
    val openId: String,
    val totalLearningDuration: Long,
    val userId: Int,
    val userName: String
)

data class DailyLog(
    val browseDate: String,
    val courseId: Int,
    val courseName: String,
    val duration: Long,
    val platform: String?
)