package com.jiangxk.zhengyuansmallclassroom.model

import android.os.Parcel
import android.os.Parcelable
import com.jiangxk.common.utils.AppPrefsUtils
import com.jiangxk.zhengyuansmallclassroom.constant.Constant

/**
 * @description com.jiangxk.zhengyuansmallclassroom.model
 * @author jiangxk
 * @time 2020-04-13  17:14
 */
data class ParameterModel(
    var userId: Int,
    var openId: String?,
    var userName: String?,
    var phoneNumber: String?,
    var gradeId: Int,
    var gradeName: String?,
    var subjectId: Int,
    var subjectName: String?,
    var nodeId: Int,
    var nodeName: String?,
    var chapterId: Int,
    var chapterName: String?,
    var courseId: Int,
    var courseName: String?,
    var videoUrl: String?,
    var learningDuration: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(userId)
        parcel.writeString(openId)
        parcel.writeString(userName)
        parcel.writeString(phoneNumber)
        parcel.writeInt(gradeId)
        parcel.writeString(gradeName)
        parcel.writeInt(subjectId)
        parcel.writeString(subjectName)
        parcel.writeInt(nodeId)
        parcel.writeString(nodeName)
        parcel.writeInt(chapterId)
        parcel.writeString(chapterName)
        parcel.writeInt(courseId)
        parcel.writeString(courseName)
        parcel.writeString(videoUrl)
        parcel.writeLong(learningDuration)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParameterModel> {
        override fun createFromParcel(parcel: Parcel): ParameterModel {
            return ParameterModel(parcel)
        }

        override fun newArray(size: Int): Array<ParameterModel?> {
            return arrayOfNulls(size)
        }

        fun getGradeParameter(gradeId: Int, gradeName: String?) =
            ParameterModel(
                AppPrefsUtils.getInt(Constant.SP_PERSONAL_INFORMATION_USER_ID_KEY),
                AppPrefsUtils.getString(Constant.SP_PERSONAL_INFORMATION_OPEN_ID_KEY),
                AppPrefsUtils.getString(Constant.SP_PERSONAL_INFORMATION_USER_NAME_KEY),
                AppPrefsUtils.getString(Constant.SP_PERSONAL_INFORMATION_PHONE_NUMBER_KEY),
                gradeId,
                gradeName,
                0,
                null,
                0,
                null,
                0,
                null,
                0,
                null,
                null,
                0
            )
    }

}