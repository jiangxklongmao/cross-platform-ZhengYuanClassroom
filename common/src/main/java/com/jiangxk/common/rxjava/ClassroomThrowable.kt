package com.jiangxk.common.rxjava

import com.orhanobut.logger.Logger

/**
 * @description com.jiangxk.common.rxjava
 * @author jiangxk
 * @time 2020-04-06  10:01
 */
class ClassroomThrowable(
    private val errorCode: Int,
    override val message: String?,
    override val cause: Throwable?
) :
    Throwable() {

    override fun toString(): String {
        Logger.e("ClassroomThrowable(errorCode=$errorCode, message=$message, cause=$cause)")
        return "Error(errorCode=$errorCode, message=$message)"
    }
}