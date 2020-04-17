package com.jiangxk.zhengyuansmallclassroom.repository.user.local

import com.jiangxk.common.constant.Constant.DATABASE_TABLE_USER
import com.jiangxk.common.database.ColumnParser
import com.jiangxk.common.database.DatabaseOpenHelper
import com.jiangxk.zhengyuansmallclassroom.model.UserModel
import io.reactivex.Observable
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.replaceOrThrow
import org.jetbrains.anko.db.select

/**
 * @description com.jiangxk.zhengyuansmallclassroom.repository.login.local
 * @author jiangxk
 * @time 2020-04-06  22:57
 */
class UserLocalApi constructor(private val database: DatabaseOpenHelper) : IUserLocalApi {
    override fun saveUser(user: UserModel): Long {
        return database.use {
            replaceOrThrow(
                DATABASE_TABLE_USER,
                *ColumnParser.parserToPair(user)
            )
//            replaceOrThrow(
//                DATABASE_TABLE_USER,
//                DATABASE_TABLE_USER_COLUMN__ID to user._id,
//                DATABASE_TABLE_USER_COLUMN_USER_ID to user.userId,
//                DATABASE_TABLE_USER_COLUMN_OPEN_ID to user.openId,
//                DATABASE_TABLE_USER_COLUMN_PHONE_NUMBER to user.phoneNumber,
//                DATABASE_TABLE_USER_COLUMN_USER_NAME to user.userName,
//                DATABASE_TABLE_USER_COLUMN_AVATAR_URL to user.avatarUrl,
//                DATABASE_TABLE_USER_COLUMN_CITY to user.city,
//                DATABASE_TABLE_USER_COLUMN_CLOUD_ENVIRONMENT to user.cloudEnvironment,
//                DATABASE_TABLE_USER_COLUMN_CLOUD_SOURCE to user.cloudSource,
//                DATABASE_TABLE_USER_COLUMN_COUNTRY to user.country,
//                DATABASE_TABLE_USER_COLUMN_GENDER to user.gender,
//                DATABASE_TABLE_USER_COLUMN_LANGUAGE to user.language,
//                DATABASE_TABLE_USER_COLUMN_MANAGER to user.manager,
//                DATABASE_TABLE_USER_COLUMN_NICK_NAME to user.nickName,
//                DATABASE_TABLE_USER_COLUMN_PASSWORD to user.password,
//                DATABASE_TABLE_USER_COLUMN_PROVINCE to user.province,
//                DATABASE_TABLE_USER_COLUMN_REGISTER_DATE to user.registerDate,
//                DATABASE_TABLE_USER_COLUMN_STATUS to user.status
//            )
        }
    }

    override fun queryUserById(userId: Int): Observable<UserModel> {
        val user = database.use {

            select(
                DATABASE_TABLE_USER,
                *ColumnParser.parser(UserModel::class.java)
            ).whereArgs(
                "userId = {userId}",
                "userId" to userId
            ).exec {
                parseList(classParser<UserModel>())
                    .first()
            }

//            select(
//                DATABASE_TABLE_USER,
//                DATABASE_TABLE_USER_COLUMN__ID,
//                DATABASE_TABLE_USER_COLUMN_USER_ID,
//                DATABASE_TABLE_USER_COLUMN_OPEN_ID,
//                DATABASE_TABLE_USER_COLUMN_PHONE_NUMBER,
//                DATABASE_TABLE_USER_COLUMN_USER_NAME,
//                DATABASE_TABLE_USER_COLUMN_AVATAR_URL,
//                DATABASE_TABLE_USER_COLUMN_CITY,
//                DATABASE_TABLE_USER_COLUMN_CLOUD_ENVIRONMENT,
//                DATABASE_TABLE_USER_COLUMN_CLOUD_SOURCE,
//                DATABASE_TABLE_USER_COLUMN_COUNTRY,
//                DATABASE_TABLE_USER_COLUMN_GENDER,
//                DATABASE_TABLE_USER_COLUMN_LANGUAGE,
//                DATABASE_TABLE_USER_COLUMN_MANAGER,
//                DATABASE_TABLE_USER_COLUMN_NICK_NAME,
//                DATABASE_TABLE_USER_COLUMN_PASSWORD,
//                DATABASE_TABLE_USER_COLUMN_PROVINCE,
//                DATABASE_TABLE_USER_COLUMN_REGISTER_DATE,
//                DATABASE_TABLE_USER_COLUMN_STATUS
//            ).whereArgs(
//                "(userId = {userId})",
//                "userId" to userId
//            ).exec {
//                parseSingle(classParser<UserModel>())
//            }
        }

        return Observable.just(user)

    }

}

