package com.jiangxk.zhengyuansmallclassroom.model

/**
 * @description com.jiangxk.zhengyuansmallclassroom.model
 * @author jiangxk
 * @time 2020-04-11  17:00
 */
data class NodeModel(
    val _id: String,
    val nodeId: Int,
    val nodeName: String,
    val status: Int,
    val subjectId: Int
)