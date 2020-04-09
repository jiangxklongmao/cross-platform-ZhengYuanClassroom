package com.jiangxk.zhengyuansmallclassroom.model

/**
 * @description com.jiangxk.zhengyuansmallclassroom.model
 * @author jiangxk
 * @time 2020-04-09  18:05
 */
data class GradeModel(
    val _id: String,
    val gradeId: Int,
    val gradeName: String,
    val isOpen: Boolean,
    val status: Int
)