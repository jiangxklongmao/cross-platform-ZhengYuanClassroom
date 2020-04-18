package com.jiangxk.zhengyuansmallclassroom.model

/**
 * @description com.jiangxk.zhengyuansmallclassroom.model
 * @author jiangxk
 * @time 2020-04-18  13:52
 */
data class NodeLimitModel(
    val _id: String,
    var limitSize: Int,
    val nodeId: Int,
    val nodeName: String,
    val status: Int,
    val subjectId: Int,
    var totalCount: Int
)