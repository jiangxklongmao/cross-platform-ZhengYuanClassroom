package com.jiangxk.zhengyuansmallclassroom.model

/**
 * @description com.jiangxk.zhengyuansmallclassroom.model
 * @author jiangxk
 * @time 2020-04-17  21:29
 */
data class UpdateResultModel(
    val errMsg: String,
    val stats: Stats
)

data class Stats(
    val updated: Int
)