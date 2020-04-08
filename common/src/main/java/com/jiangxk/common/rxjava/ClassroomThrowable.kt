package com.jiangxk.common.rxjava

/**
 * @description com.jiangxk.common.rxjava
 * @author jiangxk
 * @time 2020-04-06  10:01
 */
class ClassroomThrowable(
    val errorCode: Int,
    override val message: String?,
    override val cause: Throwable?
) :
    Throwable() {

    override fun toString(): String {
        return "ClassroomThrowable(errorCode=$errorCode, message=$message, cause=$cause)"
    }
}