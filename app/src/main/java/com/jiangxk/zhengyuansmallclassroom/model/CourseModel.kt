package com.jiangxk.zhengyuansmallclassroom.model

/**
 * @description com.jiangxk.zhengyuansmallclassroom.model
 * @author jiangxk
 * @time 2020-04-12  19:24
 */
data class CourseModel(
    val _id: String,
    val chapterId: Int,
    val courseId: Int,
    val courseName: String,
    val status: Int,
    val videoUrl: String
)