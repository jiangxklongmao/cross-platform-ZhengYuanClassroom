package com.jiangxk.zhengyuansmallclassroom.ui.activity.login

import com.jiangxk.common.common.activity.BaseMvpActivity
import com.jiangxk.common.ddatabase.DatabaseOpenHelper
import com.jiangxk.common.utils.AppPrefsUtils
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerLoginComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.LoginModule
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.login.LoginContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.login.LoginPresenter
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import com.jiangxk.zhengyuansmallclassroom.repository.user.local.UserLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.user.remote.UserRemoteApi
import com.jiangxk.zhengyuansmallclassroom.ui.activity.home.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.activity.login
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

    override fun getLayoutId() = R.layout.activity_login

    override fun isSetStateView() = true

    override fun initView() {
        btn_login.setOnClickListener {
            mPresenter.login(edt_phoneNumber.text.toString(), edt_password.text.toString())
        }
    }

    override fun initData() {

        AppPrefsUtils.getString(Constant.SP_PERSONAL_INFORMATION_PHONE_NUMBER_KEY)?.let {
            edt_phoneNumber.setText(it)
            edt_phoneNumber.setSelection(it.length)
        }
        AppPrefsUtils.getString(Constant.SP_PERSONAL_INFORMATION_PASSWORD_KEY)?.let {
            edt_password.setText(it)
            edt_password.setSelection(it.length)
        }

    }

    override fun loginSuccess() {
        HomeActivity.start(this)
        finish()
    }

}