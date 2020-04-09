package com.jiangxk.common.rxjava

import com.jiangxk.common.common.model.BaseModel
import com.jiangxk.common.constant.Constant
import io.reactivex.functions.Function

/**
 * @description com.jiangxk.common.rxjava
 * @author jiangxk
 * @time 2020-04-07  17:58
 */
class Mapper<T> : Function<BaseModel<T>, T> {

    override fun apply(t: BaseModel<T>): T {
        if (t == null) {
            throw ClassroomThrowable(
                Constant.NETWORK_ERROR_CODE_10001,
                "数据解析失败",
                cause = Throwable()
            )
        }

        if (!t.isSuccessfull()) {
            throw ClassroomThrowable(
                Constant.NETWORK_ERROR_CODE_10002,
                t.message,
                cause = Throwable()
            )
        }

        return t.data
    }

}