package com.jiangxk.zhengyuansmallclassroom.ui.login

import android.widget.Toast
import com.jiangxk.common.ddatabase.DatabaseOpenHelper
import com.jiangxk.common.ui.activity.BaseMvpActivity
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerLoginComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.LoginModule
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.LoginContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.LoginPresenter
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import com.jiangxk.zhengyuansmallclassroom.repository.user.local.UserLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.user.remote.UserRemoteApi
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_login.*

/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.login
 * @author jiangxk
 * @time 2020-03-25  15:08
 */
class LoginActivity : BaseMvpActivity<LoginContract.View, LoginPresenter>(), LoginContract.View {

    override fun injectComponent() {
        DaggerLoginComponent.builder().activityComponent(mActivityComponent)
            .loginModule(
                LoginModule(
                    this,
                    UserRepository.getInstance(UserLocalApi(DatabaseOpenHelper), UserRemoteApi())
                )
            )
            .build().inject(this)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        Logger.i("showMessage = $message")
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        btn_login.setOnClickListener {
            mPresenter.login(edt_phoneNumber.text.toString(), edt_password.text.toString())
        }

        civ_logo.setOnClickListener {
            mPresenter.queryUserFromDatabase()
        }
    }

    override fun initData() {
    }

}