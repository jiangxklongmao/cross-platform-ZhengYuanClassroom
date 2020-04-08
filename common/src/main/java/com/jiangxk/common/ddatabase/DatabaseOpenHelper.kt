package com.jiangxk.common.ddatabase

import android.database.sqlite.SQLiteDatabase
import com.jiangxk.common.common.BaseApplication.Companion.context
import com.jiangxk.common.constant.Constant
import org.jetbrains.anko.db.*

/**
 * @description com.jiangxk.common.ddatabase
 * @author jiangxk
 * @time 2020-04-06  15:48
 */
object DatabaseOpenHelper : ManagedSQLiteOpenHelper(
    context,
    Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION
) {


    override fun onCreate(db: SQLiteDatabase?) {
        createUser(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) {
            db?.dropTable(Constant.DATABASE_TABLE_USER, true)
            createUser(db)
        }
    }

    /**
     * 创建 User表
     */
    private fun createUser(db: SQLiteDatabase?) {
        db?.createTable(
            Constant.DATABASE_TABLE_USER, true,
            Constant.DATABASE_TABLE_USER_COLUMN__ID to TEXT + PRIMARY_KEY + UNIQUE,
            Constant.DATABASE_TABLE_USER_COLUMN_USER_ID to INTEGER + UNIQUE,
            Constant.DATABASE_TABLE_USER_COLUMN_OPEN_ID to TEXT + UNIQUE,
            Constant.DATABASE_TABLE_USER_COLUMN_PHONE_NUMBER to TEXT + UNIQUE,
            Constant.DATABASE_TABLE_USER_COLUMN_USER_NAME to TEXT,
            Constant.DATABASE_TABLE_USER_COLUMN_AVATAR_URL to TEXT,
            Constant.DATABASE_TABLE_USER_COLUMN_CITY to TEXT,
            Constant.DATABASE_TABLE_USER_COLUMN_CLOUD_ENVIRONMENT to TEXT,
            Constant.DATABASE_TABLE_USER_COLUMN_CLOUD_SOURCE to TEXT,
            Constant.DATABASE_TABLE_USER_COLUMN_COUNTRY to TEXT,
            Constant.DATABASE_TABLE_USER_COLUMN_GENDER to INTEGER,
            Constant.DATABASE_TABLE_USER_COLUMN_LANGUAGE to TEXT,
            Constant.DATABASE_TABLE_USER_COLUMN_MANAGER to INTEGER,
            Constant.DATABASE_TABLE_USER_COLUMN_NICK_NAME to TEXT,
            Constant.DATABASE_TABLE_USER_COLUMN_PASSWORD to TEXT,
            Constant.DATABASE_TABLE_USER_COLUMN_PROVINCE to TEXT,
            Constant.DATABASE_TABLE_USER_COLUMN_REGISTER_DATE to TEXT,
            Constant.DATABASE_TABLE_USER_COLUMN_STATUS to INTEGER
        )
    }

}