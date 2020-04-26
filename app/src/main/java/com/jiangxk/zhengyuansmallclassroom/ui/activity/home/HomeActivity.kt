package com.jiangxk.zhengyuansmallclassroom.ui.activity.home

import android.Manifest
import android.content.Context
import android.content.Intent
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jiangxk.common.common.activity.BaseMvpActivity
import com.jiangxk.common.database.DatabaseOpenHelper
import com.jiangxk.common.utils.AppPrefsUtils
import com.jiangxk.zhengyuansmallclassroom.BuildConfig
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerHomeActivityComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.HomeActivityModule
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.home.HomeActivityContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.home.HomeActivityPresenter
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import com.jiangxk.zhengyuansmallclassroom.repository.user.local.UserLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.user.remote.UserRemoteApi
import com.jiangxk.zhengyuansmallclassroom.ui.activity.login.LoginActivity
import com.jiangxk.zhengyuansmallclassroom.ui.dialog.CheckForUpdatesDialog
import com.orhanobut.logger.Logger
import com.tencent.bugly.crashreport.CrashReport
import pub.devrel.easypermissions.EasyPermissions


/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.activity.home
 * @author jiangxk
 * @time 2020-04-08  23:02
 */
class HomeActivity : BaseMvpActivity<HomeActivityContract.View, HomeActivityPresenter>(),
    HomeActivityContract.View, EasyPermissions.PermissionCallbacks {

    private var clickTime: Long = 0

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, HomeActivity::class.java))
        }

        const val PERMISSION_STORAGE_MSG = "请授予存储权限，否则影响部分使用功能"
        const val PERMISSION_STORAGE_CODE = 10001
    }

    override fun injectComponent() {
        DaggerHomeActivityComponent.builder().activityComponent(mActivityComponent)
            .homeActivityModule(
                HomeActivityModule(
                    this,
                    UserRepository.getInstance(UserLocalApi(DatabaseOpenHelper), UserRemoteApi())
                )
            ).build().inject(this)
    }

    override fun getLayoutId() = com.jiangxk.zhengyuansmallclassroom.R.layout.activity_home


    override fun initView() {

        Logger.i("supportActionBar = " + supportActionBar.toString())

        val navView: BottomNavigationView =
            findViewById(com.jiangxk.zhengyuansmallclassroom.R.id.nav_view)
        navView.itemIconTintList = null
        navView.itemTextColor =
            resources.getColorStateList(com.jiangxk.zhengyuansmallclassroom.R.color.selector_tab_color)
        val navController =
            findNavController(com.jiangxk.zhengyuansmallclassroom.R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                com.jiangxk.zhengyuansmallclassroom.R.id.navigation_home,
                com.jiangxk.zhengyuansmallclassroom.R.id.navigation_my
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun initData() {
        if (AppPrefsUtils.getString(Constant.SP_PERSONAL_INFORMATION_OPEN_ID_KEY).isNullOrEmpty() ||
            AppPrefsUtils.getInt(Constant.SP_PERSONAL_INFORMATION_USER_ID_KEY) == 0
        ) {
            LoginActivity.start(this)
            finish()
        }

        CrashReport.putUserData(
            context,
            "userId",
            AppPrefsUtils.getInt(
                Constant.SP_PERSONAL_INFORMATION_USER_ID_KEY
            ).toString()
        )
        CrashReport.putUserData(
            context,
            "openId",
            AppPrefsUtils.getString(
                Constant.SP_PERSONAL_INFORMATION_OPEN_ID_KEY
            )
        )

        requestPermissions()
    }

    override fun onBackPressed() {
        exit()
    }

    private fun requestPermissions() {
        val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            // Already have permission, do the thing
            // ...

            mPresenter.checkForUpdates(BuildConfig.VERSION_CODE)

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

    //接受系统权限的处理，这里交给EasyPermissions来处理，回调到 EasyPermissions.PermissionCallbacks接口
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
            showMessage(PERMISSION_STORAGE_MSG)
        }
    }

    /**
     * 申请成功时调用
     * @param requestCode Int
     * @param perms MutableList<String>
     */
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == PERMISSION_STORAGE_CODE) {
            mPresenter.checkForUpdates(BuildConfig.VERSION_CODE)
        }
    }

    override fun showUpdateDialog(apkUrl: String, updateInfo: String, isForce: Boolean) {
        CheckForUpdatesDialog.Builder()
            .apkUrl(apkUrl)
            .updateInfo(updateInfo)
            .isForce(isForce)
            .build().show(supportFragmentManager, "CheckForUpdatesDialog")
    }


    /**
     * 连续按两下退出程序
     */
    private fun exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            showMessage("再按一次退出程序")
            clickTime = System.currentTimeMillis()
        } else {
            this.finish()
        }
    }

}