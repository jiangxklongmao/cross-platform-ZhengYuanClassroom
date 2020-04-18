package com.jiangxk.zhengyuansmallclassroom.model

import android.os.Parcel
import android.os.Parcelable

/**
 * @description com.jiangxk.zhengyuansmallclassroom.model
 * @author jiangxk
 * @time 2020-04-18  16:35
 */
data class UserParameter(
    val _id: String?,
    val avatarUrl: String?,
    val city: String?,
    val cloudEnvironment: String?,
    val cloudSource: String?,
    val country: String?,
    val gender: Int,
    val language: String?,
    val manager: Int,
    val nickName: String?,
    val openId: String?,
    val password: String?,
    val phoneNumber: String?,
    val province: String?,
    val registerDate: String?,
    val status: Int,
    val userId: Int,
    val userName: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readString(),
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readInt(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(_id)
        writeString(avatarUrl)
        writeString(city)
        writeString(cloudEnvironment)
        writeString(cloudSource)
        writeString(country)
        writeInt(gender)
        writeString(language)
        writeInt(manager)
        writeString(nickName)
        writeString(openId)
        writeString(password)
        writeString(phoneNumber)
        writeString(province)
        writeString(registerDate)
        writeInt(status)
        writeInt(userId)
        writeString(userName)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<UserParameter> =
            object : Parcelable.Creator<UserParameter> {
                override fun createFromParcel(source: Parcel): UserParameter = UserParameter(source)
                override fun newArray(size: Int): Array<UserParameter?> = arrayOfNulls(size)
            }
    }
}