package com.jiangxk.zhengyuansmallclassroom.model

/**
 * @description com.jiangxk.zhengyuansmallclassroom.model
 * @author jiangxk
 * @time 2020-03-26  14:54
 */
data class UserModel(
    val _id: String,
    val avatarUrl: String,
    val city: String,
    val cloudEnvironment: String,
    val cloudSource: String,
    val country: String,
    val gender: Int,
    val language: String,
    val manager: Int,
    val nickName: String,
    val openId: String,
    val password: String,
    val phoneNumber: String,
    val province: String,
    val registerDate: String,
    val status: Int,
    val userId: Int,
    val userName: String
) {
    override fun toString(): String {
        return "UserModel(_id='$_id', avatarUrl='$avatarUrl', city='$city', cloudEnvironment='$cloudEnvironment', cloudSource='$cloudSource', country='$country', gender=$gender, language='$language', manager=$manager, nickName='$nickName', openId='$openId', password='$password', phoneNumber='$phoneNumber', province='$province', registerDate='$registerDate', status=$status, userId=$userId, userName='$userName')"
    }

    fun toParameter(): UserParameter {
        return UserParameter(
            _id,
            avatarUrl,
            city,
            cloudEnvironment,
            cloudSource,
            country,
            gender,
            language,
            manager,
            nickName,
            openId,
            password,
            phoneNumber,
            province,
            registerDate,
            status,
            userId,
            userName
        )
    }

}

