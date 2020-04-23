package com.jiangxk.zhengyuansmallclassroom.ui.fragment.my

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jiangxk.common.common.fragment.BaseMvpFragment
import com.jiangxk.common.database.DatabaseOpenHelper
import com.jiangxk.common.utils.GlideImageLoader
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerMyComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.MyModule
import com.jiangxk.zhengyuansmallclassroom.model.UserModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.my.MyContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.my.MyPresenter
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import com.jiangxk.zhengyuansmallclassroom.repository.user.local.UserLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.user.remote.UserRemoteApi
import com.jiangxk.zhengyuansmallclassroom.ui.activity.learning.LearningOrderActivity
import com.jiangxk.zhengyuansmallclassroom.ui.activity.learning.StatisticalLearningActivity
import com.jiangxk.zhengyuansmallclassroom.ui.activity.manager.ManagerUserActivity
import kotlinx.android.synthetic.main.fragment_my.*
import kotlinx.android.synthetic.main.include_toolbar.*

class MyFragment : BaseMvpFragment<MyPresenter>(), MyContract.View {

    private lateinit var myViewModel: MyViewModel

    private var manager: Int = 0
    private var status: Int = 0
    private var user: UserModel? = null

    override fun injectComponent() {
        DaggerMyComponent.builder().activityComponent(mActivityComponent)
            .myModule(
                MyModule(
                    this,
                    UserRepository.getInstance(UserLocalApi(DatabaseOpenHelper), UserRemoteApi())
                )
            ).build().inject(this)
    }

    override fun getLayoutId() = R.layout.fragment_my

    override fun initOperate() {
        super.initOperate()
        myViewModel =
            ViewModelProviders.of(this).get(MyViewModel::class.java)
        myViewModel.text.observe(this, Observer {
        })
    }

    override fun initView() {
        tv_title.text = "我的"
        iv_back.visibility = GONE
    }

    override fun setListener() {
        super.setListener()
        rl_learning.setOnClickListener {
            if (status != 1) {
                showMessage("账号未审核，请联系客服审核后再使用！")
                return@setOnClickListener
            }

            user?.let {
                StatisticalLearningActivity.start(context, it)
            }
        }

        rl_order.setOnClickListener {
            if (status != 1) {
                showMessage("账号未审核，请联系客服审核后再使用！")
                return@setOnClickListener
            }
            LearningOrderActivity.start(context)
        }

        rl_manager.setOnClickListener {
            if (status != 1) {
                showMessage("账号未审核，请联系客服审核后再使用！")
                return@setOnClickListener
            }
            ManagerUserActivity.start(context)
        }
    }

    override fun initData() {
        mPresenter.queryUserInfo()
    }

    override fun showUser(user: UserModel) {
        this.user = user
        tv_userName.text = user.userName
        tv_phoneNumber.text = user.phoneNumber
        GlideImageLoader().displayImage(context, user.avatarUrl, civ_avatar)
        manager = user.manager
        status = user.status
        if (user.manager == 1) {
            rl_manager.visibility = VISIBLE
        } else {
            rl_manager.visibility = GONE
        }
    }

}