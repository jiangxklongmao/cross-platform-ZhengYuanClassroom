package com.jiangxk.zhengyuansmallclassroom.ui.activity.splash

import android.os.Handler
import com.jiangxk.common.common.activity.BaseMvpActivity
import com.jiangxk.common.ddatabase.DatabaseOpenHelper
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerSplashComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.SplashModule
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.splash.SplashContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.splash.SplashPresenter
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import com.jiangxk.zhengyuansmallclassroom.repository.user.local.UserLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.user.remote.UserRemoteApi
import com.jiangxk.zhengyuansmallclassroom.ui.activity.home.HomeActivity

/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.activity.splash
 * @author jiangxk
 * @time 2020-04-15  14:31
 */
class SplashActivity : BaseMvpActivity<SplashContract.View, SplashPresenter>(),
    SplashContract.View {


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
        Handler().postDelayed({
            HomeActivity.start(this)
            finish()
        }, 2000)
    }

}