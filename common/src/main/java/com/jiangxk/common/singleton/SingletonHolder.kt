package com.jiangxk.common.singleton

/**
 * @description com.jiangxk.common.singleton 单例Holder 可传入参数的单例
 * @author jiangxk
 * @time 2020-04-07  16:51
 */
open class SingletonHolder<out T, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A): T {
        val ins = instance
        if (ins != null) {
            return ins
        }

        return synchronized(this) {
            val ins2 = instance
            if (ins2 != null) {
                ins2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}

open class SingletonHolder2<out T, in A, in B>(creator: (A, B) -> T) {
    private var creator: ((A, B) -> T)? = creator
    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A, arg2: B): T {
        val ins = instance
        if (ins != null) {
            return ins
        }

        return synchronized(this) {
            val ins2 = instance
            if (ins2 != null) {
                ins2
            } else {
                val created = creator!!(arg, arg2)
                instance = created
                creator = null
                created
            }
        }
    }
}