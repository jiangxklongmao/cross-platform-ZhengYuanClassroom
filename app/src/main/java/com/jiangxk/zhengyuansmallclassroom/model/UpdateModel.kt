package com.jiangxk.zhengyuansmallclassroom.model

/**
 * @description com.jiangxk.zhengyuansmallclassroom.model
 * @author jiangxk
 * @time 2020-04-24  17:48
 */
data class UpdateModel(
    val _id: String,
    val client: String,
    val description: String,
    val downloadUrl: String,
    val fileMD5: String,
    val filesSize: Int,
    val status: Int,
    val updateType: String,
    val versionCode: Int,
    val versionName: String
)