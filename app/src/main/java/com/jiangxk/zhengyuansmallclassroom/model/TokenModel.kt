package com.jiangxk.zhengyuansmallclassroom.model

/**
 * @description com.jiangxk.zhengyuansmallclassroom.model
 * @author jiangxk
 * @time 2020-03-27  17:07
 */
data class TokenModel(
    val access_token: String,
    val expires_in: Int,
    val errcode: Int,
    val errmsg: String
)