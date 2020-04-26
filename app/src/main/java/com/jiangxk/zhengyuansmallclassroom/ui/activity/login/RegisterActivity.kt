package com.jiangxk.zhengyuansmallclassroom.ui.activity.login

import android.content.Context
import android.content.Intent
import com.jiangxk.common.common.activity.BaseMvpActivity
import com.jiangxk.common.database.DatabaseOpenHelper
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerRegisterComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.RegisterModule
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.login.RegisterContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.login.RegisterPresenter
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import com.jiangxk.zhengyuansmallclassroom.repository.user.local.UserLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.user.remote.UserRemoteApi
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.activity.login
 * @author jiangxk
 * @time 2020-04-26  17:20
 */
class RegisterActivity : BaseMvpActivity<RegisterContract.View, RegisterPresenter>(),
    RegisterContract.View {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, RegisterActivity::class.java))
        }
    }

    override fun injectComponent() {
        DaggerRegisterComponent.builder().activityComponent(mActivityComponent)
            .registerModule(
                RegisterModule(
                    this,
                    UserRepository.getInstance(UserLocalApi(DatabaseOpenHelper), UserRemoteApi())
                )
            ).build().inject(this)
    }

    override fun getLayoutId() = R.layout.activity_register

    override fun initView() {
        tv_title.text = "用户注册"
        iv_back.setOnClickListener { finish() }
    }

    override fun initData() {
        mPresenter.getRegisterMethod()
    }

    override fun showRegisterMethod(method: String) {

        webView.loadData(method, "text/html", "UTF-8")

    }

    override fun showDefaultMethod() {
        val method = getString(R.string.app_register_method)

        webView.loadData(method, "text/html", "UTF-8")
    }

}