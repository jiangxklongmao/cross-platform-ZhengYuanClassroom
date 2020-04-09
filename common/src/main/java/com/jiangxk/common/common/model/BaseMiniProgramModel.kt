package com.jiangxk.common.common.model

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.Logger
import java.lang.reflect.Type


/**
 * @description com.jiangxk.common.model 小程序基础model
 * @author jiangxk
 * @time 2020-04-03  17:33
 */

class BaseMiniProgramModel<T>(val errcode: Int, val errmsg: String, val resp_data: String) :
    BaseMiniModel<T>() {

    override fun toString(): String {
        return "BaseMiniProgramModel(errcode=$errcode, errmsg='$errmsg', resp_data='$resp_data')"
    }

//    inline fun <reified K> getBaseModel(): K {
//        val json = JsonParser().parse(resp_data)
//        Logger.i(json.toString())
//
//        val type = BaseModel::class.java.genericSuperclass;
//        val type = javaClass.genericSuperclass
//        val types = (type as ParameterizedType).actualTypeArguments
//        val ty = ParameterizedTypeImpl(BaseModel::class.java, arrayOf(types[0]))
//        val data: BaseModel<K> = Gson().fromJson(resp_data, ty)
//        return data.data
//    }

    inline fun <K> getData(): K {
        val json = JsonParser().parse(resp_data)
        Logger.i(json.toString())

        val type: Type = object : TypeToken<K>() {}.type
        val data: K = Gson().fromJson(resp_data, type)
        return data
    }

}

