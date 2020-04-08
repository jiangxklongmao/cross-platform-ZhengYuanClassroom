package com.jiangxk.common.constant

/**
 * @description com.jiangxk.common.constant
 * @author jiangxk
 * @time 2020-04-06  10:39
 */
object Constant {

    const val NETWORK_ERROR_CODE_10000 = 10000 //RxJava 内部报错
    const val NETWORK_ERROR_CODE_10001 = 10001 //RxJava 数据解析失败
    const val NETWORK_ERROR_CODE_10002 = 10002 //RxJava 数据返回码错误
    const val NETWORK_ERROR_CODE_10003 = 10003 //RxJava 网络错误
    const val NETWORK_ERROR_CODE_10004 = 10004 //RxJava 服务器错误
    const val NETWORK_ERROR_CODE_10005 = 10005 //RxJava 数据转换错误
    const val NETWORK_ERROR_CODE_10006 = 10005 //RxJava 未知错误


    //region 数据库

    /**
     * 数据库名称
     */
    const val DATABASE_NAME = "database_name_classroom"
    /**
     * 数据库版本
     */
    const val DATABASE_VERSION = 1


    const val DATABASE_TABLE_USER = "User"
    const val DATABASE_TABLE_USER_COLUMN__ID = "_id"
    const val DATABASE_TABLE_USER_COLUMN_USER_ID = "userId"
    const val DATABASE_TABLE_USER_COLUMN_OPEN_ID = "openId"
    const val DATABASE_TABLE_USER_COLUMN_PHONE_NUMBER = "phoneNumber"
    const val DATABASE_TABLE_USER_COLUMN_USER_NAME = "userName"
    const val DATABASE_TABLE_USER_COLUMN_AVATAR_URL = "avatarUrl"
    const val DATABASE_TABLE_USER_COLUMN_CITY = "city"
    const val DATABASE_TABLE_USER_COLUMN_CLOUD_ENVIRONMENT = "cloudEnvironment"
    const val DATABASE_TABLE_USER_COLUMN_CLOUD_SOURCE = "cloudSource"
    const val DATABASE_TABLE_USER_COLUMN_COUNTRY = "country"
    const val DATABASE_TABLE_USER_COLUMN_GENDER = "gender"
    const val DATABASE_TABLE_USER_COLUMN_LANGUAGE = "language"
    const val DATABASE_TABLE_USER_COLUMN_MANAGER = "manager"
    const val DATABASE_TABLE_USER_COLUMN_NICK_NAME = "nickName"
    const val DATABASE_TABLE_USER_COLUMN_PASSWORD = "password"
    const val DATABASE_TABLE_USER_COLUMN_PROVINCE = "province"
    const val DATABASE_TABLE_USER_COLUMN_REGISTER_DATE = "registerDate"
    const val DATABASE_TABLE_USER_COLUMN_STATUS = "status"


    //endregion


}