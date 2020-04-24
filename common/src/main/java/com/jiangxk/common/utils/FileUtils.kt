package com.jiangxk.common.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import com.jiangxk.common.common.BaseApplication
import java.io.File

/**
 * @description com.jiangxk.common.utils
 * @author jiangxk
 * @time 2020-04-24  22:08
 */
class FileUtils {
    companion object {


        fun getOutDirPublic(path: String): File? {
            return getOutDirPublic(BaseApplication.context, path)
        }

        /**
         * getFilesDir()获取你app的内部存储空间，相当于你的应用在内部存储上的根目录。<br>
         * 存储级别与SharedPreferences一致<br>
         * 一般不使用
         * path 为"img/cache"时，结果：/data/data/PackageName/files/img/cache
         * @param context Context
         * @param path String
         * @return File
         */
        fun getOutDirPublic(context: Context, path: String): File? {
            if (isExternalPathExist(context)) {
                val file = Environment.getExternalStoragePublicDirectory(path)
                if (!file.exists()) {
                    file.mkdirs()
                }
                return file
            }
            return null
        }

        /**
         * 判断是否可以操作外部存储器
         * 包括权限、判断是否已挂载外部存储器
         * @param context Context
         * @return Boolean
         */
        fun isExternalPathExist(context: Context): Boolean {
            //Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);判断外部存储器是否挂载
            return haveMountedPermission(context) && Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        }

        /**
         * 是否有权限操作存储卡
         * @param context Context
         * @return Boolean
         */
        private fun haveMountedPermission(context: Context): Boolean {
            try {
                val array = context.packageManager.getPackageInfo(
                    context.packageName,
                    PackageManager.GET_PERMISSIONS
                ).requestedPermissions
                for (permi in array) {
                    if (permi == Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        return true
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return false
        }

    }
}