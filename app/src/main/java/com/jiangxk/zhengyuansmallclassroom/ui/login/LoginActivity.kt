package com.jiangxk.zhengyuansmallclassroom.ui.login

import android.widget.Toast
import com.jiangxk.common.ui.activity.BaseMvpActivity
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerLoginComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.LoginModule
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.LoginContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.LoginPresenter
import com.jiangxk.zhengyuansmallclassroom.repository.login.remote.UserRepository
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
            .loginModule(LoginModule(this, UserRepository()))
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
            //            presenter.login("jiangxk", "123456")
            mPresenter
                .getToken(
                    "client_credential",
                    "wx5950e05f747a9d13",
                    "5e4099f3e0a87d45256e3215ac39df1e"
                )
        }
    }

    override fun initData() {
    }

}