package com.jiangxk.zhengyuansmallclassroom.constant

/**
 * @description com.jiangxk.zhengyuansmallclassroom.constant
 * @author jiangxk
 * @time 2020-03-25  17:58
 */
object Constant {
    const val BASE_URL = "https://api.weixin.qq.com/"

    //region 小程序ID以及秘钥
    //正元小课堂小程序
    const val MINI_PROGRAM_CLASSROOM_APP_ID = "wx5950e05f747a9d13"
    const val MINI_PROGRAM_CLASSROOM_APP_SECRET = "5e4099f3e0a87d45256e3215ac39df1e"
    const val MINI_PROGRAM_CLASSROOM_ENV = "online-classroom"

    //正元培训小程序
    const val MINI_PROGRAM_TRAINING_CENTRE_APP_ID = "wx5d3d6479a6a4bbc8"
    const val MINI_PROGRAM_TRAINING_CENTRE_APP_SECRET = "3342bc26e2f17c6a729d651b9e6fa196"
    const val MINI_PROGRAM_TRAINING_CENTRE_ENV = "online-zhengyuan-education"

    //endregion

    //region 获取token
    const val METHOD_GET_TOKEN = "cgi-bin/token"
    const val PARAMETER_GRANT_TYPE = "grant_type"
    const val PARAMETER_GRANT_TYPE_VALUE = "client_credential"
    const val PARAMETER_APPID = "appid"
    const val PARAMETER_SECRET = "secret"
    //endregion

    //region 调用云函数 invokeCloudFunction

    const val METHOD_POST_INVOKE_CLOUD_FUNCTION = "tcb/invokecloudfunction"
    const val PARAMETER_ACCESS_TOKEN = "access_token"
    const val PARAMETER_ENV = "env"
    const val PARAMETER_NAME = "name"
    const val PARAMETER_POST_BODY = "POSTBODY"

    //endregion

    //region SP存储

    //region Token 存储相关
    const val SP_MINI_PROGRAM_CLASSROOM_TOKEN_KEY = "sp_mini_program_classroom_token_key"
    const val SP_MINI_PROGRAM_CLASSROOM_TOKEN_EXPIRE_TIMESTAMP_KEY =
        "sp_mini_program_classroom_token_expire_timestamp_key"
    //endregion

    //region 个人信息相关

    const val SP_PERSONAL_INFORMATION_USER_ID_KEY = "sp_personal_information_user_id_key"
    const val SP_PERSONAL_INFORMATION_OPEN_ID_KEY = "sp_personal_information_open_id_key"
    const val SP_PERSONAL_INFORMATION_PHONE_NUMBER_KEY = "sp_personal_information_phone_number_key"
    const val SP_PERSONAL_INFORMATION_PASSWORD_KEY = "sp_personal_information_password_key"
    const val SP_PERSONAL_INFORMATION_USER_NAME_KEY = "sp_personal_information_user_name_key"
    const val SP_PERSONAL_INFORMATION_AVATAR_URL_KEY = "sp_personal_information_avatar_url_key"
    const val SP_PERSONAL_INFORMATION_LOGIN_TIMESTAMP_KEY =
        "sp_personal_information_login_timestamp_key"

    //endregion

    //region 传参 extra arguments

    const val EXTRA_PARAMETER = "extra_parameter"
    const val EXTRA_OPEN_ID = "extra_open_id"
    const val EXTRA_USER_ID = "extra_user_id"
    const val EXTRA_USER = "extra_user"

    const val EXTRA_PLATFORM = "Android"
    //endreigon

}