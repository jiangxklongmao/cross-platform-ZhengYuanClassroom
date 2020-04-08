package com.jiangxk.zhengyuansmallclassroom.model

/**
 * @description com.jiangxk.zhengyuansmallclassroom.model
 * @author jiangxk
 * @time 2020-03-27  17:07
 */
data class TokenStringModel(
    val access_token: String,
    val expires_in: String,
    val expires_timestamp: String,
    val errcode: String,
    val errmsg: String
)