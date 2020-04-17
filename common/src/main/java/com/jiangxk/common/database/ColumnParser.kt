package com.jiangxk.common.database

import com.orhanobut.logger.Logger

/**
 * @description com.jiangxk.common.ddatabase    解析model属性名称
 * @author jiangxk
 * @time 2020-04-07  14:37
 */
object ColumnParser {


    fun parser(clazz: Class<*>): Array<String> {
        var columns = arrayOf<String>()

        try {
            //获取声明的字段
            val declaredFields = clazz.declaredFields

            for (filed in declaredFields) {
                filed.isAccessible = true
                Logger.i("filed = " + filed.name)
                columns = columns.plus(filed.name)
            }
        } catch (e: Throwable) {
            Logger.i(e.message + "")
        }


        return columns
    }


    inline fun <reified T> parserToPair(t: T): Array<Pair<String, Any?>> {
        var pairArray = arrayOf<Pair<String, Any?>>()

        try {
            val clazz = T::class.java

            //获取声明的字段
            val declaredFields = clazz.declaredFields

            for (filed in declaredFields) {
                filed.isAccessible = true
                Logger.i("filed = " + filed.name + " value = " + filed.get(t))

                val value = if (filed.get(t) != null) filed.get(t) else "null"

                pairArray = pairArray.plus(filed.name to value)
            }

        } catch (e: Throwable) {
            Logger.i(e.message + "")
        }

        return pairArray

    }
}