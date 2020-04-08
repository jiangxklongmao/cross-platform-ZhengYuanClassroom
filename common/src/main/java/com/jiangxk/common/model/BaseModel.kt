package com.jiangxk.common.model

import com.jiangxk.common.common.BaseConstant

/**
 * @description com.jiangxk.common.model 数据基础类
 * @author jiangxk
 * @time 2020-03-25  17:37
 */
data class BaseModel<T>(var data: T, var message: String, var code: Int) {
    fun isSuccessfull(): Boolean {
        if (data == null) {
            return false
        }
        return code == BaseConstant.NETWORK_SUCCESS
    }
}