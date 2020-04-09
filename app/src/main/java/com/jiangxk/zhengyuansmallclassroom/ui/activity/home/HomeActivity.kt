package com.jiangxk.zhengyuansmallclassroom.ui.activity.home

import android.content.Context
import android.content.Intent
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jiangxk.common.common.activity.BaseActivity
import com.jiangxk.common.utils.AppPrefsUtils
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.orhanobut.logger.Logger


/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.activity.home
 * @author jiangxk
 * @time 2020-04-08  23:02
 */
class HomeActivity : BaseActivity() {
    private var clickTime: Long = 0

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, HomeActivity::class.java))
        }
    }

    override fun getLayoutId() = R.layout.activity_home

    override fun initView() {

        Logger.i("supportActionBar = " + supportActionBar.toString())

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.itemIconTintList = null
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_my
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun initData() {
        if (AppPrefsUtils.getString(Constant.SP_PERSONAL_INFORMATION_OPEN_ID_KEY).isNullOrEmpty() ||
            AppPrefsUtils.getInt(Constant.SP_PERSONAL_INFORMATION_USER_ID_KEY) == 0
        ) {
            showMessage("用户信息获取失败，请重新登录")
            finish()
        }
    }

    override fun onBackPressed() {
        exit()
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