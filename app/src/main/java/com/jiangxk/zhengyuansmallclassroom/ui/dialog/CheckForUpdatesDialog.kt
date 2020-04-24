package com.jiangxk.zhengyuansmallclassroom.ui.dialog

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.FileProvider
import com.jiangxk.common.common.dialog.BaseDialogFragment
import com.jiangxk.common.utils.DownloadManager
import com.jiangxk.common.utils.FileUtils
import com.jiangxk.zhengyuansmallclassroom.BuildConfig
import com.jiangxk.zhengyuansmallclassroom.R
import kotlinx.android.synthetic.main.dialog_check_for_updates.*
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.dialog
 * @author jiangxk
 * @time 2020-04-24  10:43
 */
class CheckForUpdatesDialog(private val builder: Builder) : BaseDialogFragment(),
    EasyPermissions.PermissionCallbacks {

    private var apkUrl: String? = null
    private var isForce: Boolean? = true
    private var updateInfo: String? = null
    private var tempFile: File? = null

    companion object {
        const val PERMISSION_STORAGE_MSG = "请授予权限，否则无法自动安装新版APP"
        const val PERMISSION_STORAGE_CODE = 9999
    }

    init {
        apkUrl = builder.apkUrl
        isForce = builder.isForce
        updateInfo = builder.updateInfo
    }


    override fun getLayoutId(): Int {
        return R.layout.dialog_check_for_updates
    }

    override fun initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tv_content.text = Html.fromHtml(updateInfo, FROM_HTML_MODE_LEGACY)
        } else {
            tv_content.text = updateInfo
        }
        isForce?.let {
            if (it) {
                tv_cancel.visibility = GONE
            } else {
                tv_cancel.visibility = VISIBLE
            }
        }
    }

    override fun setListener() {
        super.setListener()
        tv_cancel.setOnClickListener {
            dismiss()
        }

        tv_update.setOnClickListener {
            //            tv_update.isClickable = false
//            progressBar.visibility = VISIBLE
//            progressBar.progress = 0
            showLongMessage("请从浏览器下载最新版本")
            downloadFromBrowser(apkUrl)
        }
    }

    override fun initData() {
    }

    /**
     * 从浏览器下载
     * @param apkUrl String
     */
    private fun downloadFromBrowser(apkUrl: String?) {
        val intent = Intent()
        intent.action = "android.intent.action.VIEW"
        val uri = Uri.parse(apkUrl)
        intent.data = uri
        startActivity(intent)
    }

    /**
     * 下载
     */
    private fun download() {
        val path = "/classroom/apk"
//        val dir = BaseApplication.context.getExternalFilesDir(path)
        val dir = FileUtils.getOutDirPublic(path)
        val file = File(dir?.absolutePath, "classroom_${BuildConfig.VERSION_CODE}.apk")
        if (!file.exists()) {
            file.createNewFile()
        }

        DownloadManager.load(apkUrl, file, object : DownloadManager.DownloadListener {
            override fun progress(progress: Int) {
                progressBar.progress = progress
            }

            override fun success(file: File) {
                progressBar.progress = 100
                tv_update.isClickable = true
                tempFile = file
                requestPermissions(file)
            }

            override fun error(error: String) {
                tv_update.isClickable = true
                showMessage("下载失败，请稍后重试")
                isForce?.let {
                    if (it) {
                        dismiss()
                    }
                }
            }

        })
    }

    private fun requestPermissions(file: File) {
        context?.let {
            val perms = arrayOf(Manifest.permission.REQUEST_INSTALL_PACKAGES)
            if (EasyPermissions.hasPermissions(it, *perms)) {
                // Already have permission, do the thing
                // ...

                autoInstall(file)
            } else {
                // Do not have permissions, request them now
                //这个方法是用户在拒绝权限之后，再次申请权限，才会弹出自定义的dialog，详情可以查看下源码 shouldShowRequestPermissionRationale()方法
                EasyPermissions.requestPermissions(
                    this,
                    PERMISSION_STORAGE_MSG,
                    PERMISSION_STORAGE_CODE,
                    *perms
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            this
        )//注意这个this，内部对实现该方法进行了查询，所以没有this的话，回调结果的方法不生效
    }

    /**
     * 申请拒绝时调用
     * @param requestCode Int
     * @param perms MutableList<String>
     */
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == PERMISSION_STORAGE_CODE) {
            showLongMessage("拒绝权限无法自动安装！请在存储根目录下文件夹</classroom/apk>中手动安装")
        }
    }

    /**
     * 申请成功时调用
     * @param requestCode Int
     * @param perms MutableList<String>
     */
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == PERMISSION_STORAGE_CODE) {
            tempFile?.let {
                autoInstall(it)
            }
        }
    }


    private fun autoInstall(file: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
        } else {
            context?.let {
                val uri = FileProvider.getUriForFile(
                    it,
                    it.packageName + ".updatefileprovider",
                    file
                )
                intent.setDataAndType(uri, "application/vnd.android.package-archive")
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

        }

        context?.let {
            val intent = Intent(Intent.ACTION_VIEW)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //适配7.0以上的手机
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                val contentUri =
                    FileProvider.getUriForFile(
                        it,
                        "${BuildConfig.APPLICATION_ID}.updatefileprovider",
                        file
                    )
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive")
            } else {
                intent.setDataAndType(
                    Uri.fromFile(file),
                    "application/vnd.android.package-archive"
                )
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            it.startActivity(intent)

        }
    }

    class Builder {
        internal var apkUrl: String? = null
        internal var isForce: Boolean? = true
        internal var updateInfo: String? = null

        fun apkUrl(apkUrl: String): Builder {
            this.apkUrl = apkUrl
            return this
        }

        fun updateInfo(updateInfo: String): Builder {
            this.updateInfo = updateInfo
            return this
        }

        fun isForce(isForce: Boolean): Builder {
            this.isForce = isForce
            return this
        }

        fun build(): CheckForUpdatesDialog {
            return CheckForUpdatesDialog(this)
        }
    }

}