package com.jiangxk.zhengyuansmallclassroom.ui.activity.splash

import android.os.Build
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import com.dm.sdk.ads.splash.SplashAD
import com.dm.sdk.ads.splash.SplashAdListener
import com.dm.sdk.common.util.AdError
import com.jiangxk.common.common.activity.BaseMvpActivity
import com.jiangxk.common.database.DatabaseOpenHelper
import com.jiangxk.common.utils.AppPrefsUtils
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerSplashComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.SplashModule
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.splash.SplashContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.splash.SplashPresenter
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import com.jiangxk.zhengyuansmallclassroom.repository.user.local.UserLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.user.remote.UserRemoteApi
import com.jiangxk.zhengyuansmallclassroom.ui.activity.home.HomeActivity
import com.jiangxk.zhengyuansmallclassroom.ui.activity.login.LoginActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_splash.*


/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.activity.splash
 * @author jiangxk
 * @time 2020-04-15  14:31
 */
class SplashActivity : BaseMvpActivity<SplashContract.View, SplashPresenter>(),
    SplashContract.View, SplashAdListener {


    private lateinit var splashAD: SplashAD

    companion object {
        /**
         * 登录超时时间 7天
         */
        const val TIMEOUT = 7 * 24 * 60 * 60 * 1000

        const val AD_TAG = "advertising"
    }

    override fun injectComponent() {
        DaggerSplashComponent.builder()
            .activityComponent(mActivityComponent).splashModule(
                SplashModule(
                    this,
                    UserRepository.getInstance(
                        UserLocalApi(DatabaseOpenHelper),
                        UserRemoteApi()
                    )
                )
            ).build().inject(this)
    }

    override fun getLayoutId() = R.layout.activity_splash

    override fun isSetPaddingTop() = false

    override fun initOperate() {
        super.initOperate()
        hideSystemNavigationBar()
    }

    override fun isSetStateBar() = false

    override fun initView() {
        splashAD = SplashAD(this, "96AgWMMw0XNk0oOBor", "A1303391216", this, 10 * 1000)
        splashAD.fetchAndShowIn(fl_ad_container)
    }

    override fun initData() {
        mPresenter.authenticationToken()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            true
        } else super.onKeyDown(keyCode, event)
    }


    private fun hideSystemNavigationBar() {
        if (Build.VERSION.SDK_INT in 12..18) {
            val view = this.window.decorView
            view.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            val decorView = window.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN)
            decorView.systemUiVisibility = uiOptions
        }
    }

    override fun authenticationComplete() {
//        val loginTimestamp =
//            AppPrefsUtils.getLong(Constant.SP_PERSONAL_INFORMATION_LOGIN_TIMESTAMP_KEY)
//
//        Handler().postDelayed({
//            if (loginTimestamp > 0 && (System.currentTimeMillis() - loginTimestamp) > TIMEOUT) {
//                LoginActivity.start(this)
//                showMessage("登录超时，请重新登录")
//            } else {
//                HomeActivity.start(this)
//            }
//            finish()
//        }, 2000)

    }

    override fun onAdClicked() {
        Logger.i("点击了广告", AD_TAG)
    }

    override fun onAdDismissed() {
        Logger.i("点击了跳转按钮", AD_TAG)

        val loginTimestamp =
            AppPrefsUtils.getLong(Constant.SP_PERSONAL_INFORMATION_LOGIN_TIMESTAMP_KEY)
        if (loginTimestamp > 0 && (System.currentTimeMillis() - loginTimestamp) > TIMEOUT) {
            LoginActivity.start(this)
            showMessage("登录超时，请重新登录")
        } else {
            HomeActivity.start(this)
        }

        finish()
    }

    override fun onAdPresent() {
        Logger.i("广告展现了", AD_TAG)
    }

    override fun onNoAd(p0: AdError?) {
        Logger.i("onNoAd  code = ${p0?.errorCode} message = ${p0?.errorMsg}", AD_TAG)

        val loginTimestamp =
            AppPrefsUtils.getLong(Constant.SP_PERSONAL_INFORMATION_LOGIN_TIMESTAMP_KEY)

        Handler().postDelayed({
            if (loginTimestamp > 0 && (System.currentTimeMillis() - loginTimestamp) > TIMEOUT) {
                LoginActivity.start(this)
                showMessage("登录超时，请重新登录")
            } else {
                HomeActivity.start(this)
            }
            finish()
        }, 2000)

    }

    override fun onAdFilled() {
        Logger.i("下发广告了", AD_TAG)
    }

}