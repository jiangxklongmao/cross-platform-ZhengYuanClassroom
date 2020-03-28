package com.jiangxk.common.model

/**
 * @description com.jiangxk.common.model 数据基础类
 * @author jiangxk
 * @time 2020-03-25  17:37
 */
data class BaseModel<T>(var data: T, var message: String, var code: Int)