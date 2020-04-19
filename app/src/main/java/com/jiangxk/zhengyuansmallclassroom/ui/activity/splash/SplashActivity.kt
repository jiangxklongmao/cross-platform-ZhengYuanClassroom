package com.jiangxk.zhengyuansmallclassroom.ui.activity.splash

import android.os.Handler
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

/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.activity.splash
 * @author jiangxk
 * @time 2020-04-15  14:31
 */
class SplashActivity : BaseMvpActivity<SplashContract.View, SplashPresenter>(),
    SplashContract.View {

    companion object {
        /**
         * 登录超时时间 7天
         */
        const val TIMEOUT = 7 * 24 * 60 * 60 * 1000
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

    override fun initView() {
    }

    override fun initData() {
        mPresenter.authenticationToken()
    }

    override fun authenticationComplete() {
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

}