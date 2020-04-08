package com.jiangxk.common.model

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @description com.jiangxk.common.model
 * @author jiangxk
 * @time 2020-04-05  00:16
 */
class ParameterizedTypeImpl(private val raw: Class<*>, private val args: Array<Type>) :
    ParameterizedType {


    override fun getRawType(): Type {
        return raw
    }

    override fun getOwnerType(): Type? {
        return null
    }

    override fun getActualTypeArguments(): Array<Type> {
        return args
    }

}