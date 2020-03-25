package com.jiangxk.provider.component

/**
 * @desc 组件注册
 * @auth jiangxk
 * @time 2019-10-31  16:39
 */
object Router {
    private val services = hashMapOf<String, Any>()

    /**
     * 添加服务
     * @param serviceName String?
     * @param serviceImpl Any?
     */
    @Synchronized
    fun addService(serviceName: String?, serviceImpl: Any?) {
        if (serviceName == null || serviceImpl == null) {
            return
        }
        services[serviceName] = serviceImpl
    }


    /**
     * 获取指定服务
     * @param serviceName String?
     * @return Any?
     */
    @Synchronized
    fun getService(serviceName: String?): Any? {
        return if (serviceName == null) null else services[serviceName]
    }

    /**
     * 移除指定服务
     * @param serviceName String?
     */
    @Synchronized
    fun removeService(serviceName: String?) {
        serviceName?.let { services.remove(serviceName) }
    }


}