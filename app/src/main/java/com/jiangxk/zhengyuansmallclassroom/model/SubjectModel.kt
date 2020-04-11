package com.jiangxk.zhengyuansmallclassroom.model

/**
 * @description com.jiangxk.zhengyuansmallclassroom.model
 * @author jiangxk
 * @time 2020-04-11  11:54
 */
data class SubjectModel(
    val _id: String,
    val gradeId: Int,
    val imageUrl: String,
    val isOpen: Boolean,
    val status: Int,
    val subjectId: Int,
    val subjectName: String
)