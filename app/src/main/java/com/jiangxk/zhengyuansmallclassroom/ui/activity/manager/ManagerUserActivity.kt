package com.jiangxk.zhengyuansmallclassroom.ui.activity.manager

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.github.jdsjlzx.view.ArrowRefreshHeader
import com.github.jdsjlzx.view.LoadingFooter
import com.jiangxk.common.common.activity.BaseMvpActivity
import com.jiangxk.common.ddatabase.DatabaseOpenHelper
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerManagerUserComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.ManagerUserModule
import com.jiangxk.zhengyuansmallclassroom.model.UserModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.manager.ManagerUserContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.manager.ManagerUserPresenter
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import com.jiangxk.zhengyuansmallclassroom.repository.user.local.UserLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.user.remote.UserRemoteApi
import com.jiangxk.zhengyuansmallclassroom.ui.adapter.ManagerUserAdapter
import kotlinx.android.synthetic.main.activity_manager_user.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.activity.manager
 * @author jiangxk
 * @time 2020-04-16  11:00
 */
class ManagerUserActivity : BaseMvpActivity<ManagerUserContract.View, ManagerUserPresenter>(),
    ManagerUserContract.View {

    private lateinit var userAdapter: ManagerUserAdapter
    private lateinit var lRecyclerViewAdapter: LRecyclerViewAdapter
    private lateinit var arrowRefreshHeader: ArrowRefreshHeader
    private lateinit var loadingFooter: LoadingFooter
    private var page: Int = 0
    private val pageSize: Int = 20

    companion object {

        /**
         *
         * @param context Context?
         */
        fun start(context: Context?) {
            val intent = Intent(context, ManagerUserActivity::class.java)
            context?.startActivity(intent)
        }
    }

    override fun injectComponent() {
        DaggerManagerUserComponent.builder().activityComponent(mActivityComponent)
            .managerUserModule(
                ManagerUserModule(
                    this,
                    UserRepository.getInstance(UserLocalApi(DatabaseOpenHelper), UserRemoteApi())
                )
            ).build().inject(this)
    }

    override fun getLayoutId() = R.layout.activity_manager_user

    override fun initView() {
        tv_title.text = "学生列表"
        iv_back.visibility = View.VISIBLE

        arrowRefreshHeader = ArrowRefreshHeader(this)
        loadingFooter = LoadingFooter(this)
        recyclerView.setRefreshHeader(arrowRefreshHeader)
        recyclerView.setLoadMoreFooter(loadingFooter, false)

        userAdapter = ManagerUserAdapter(this)
        lRecyclerViewAdapter = LRecyclerViewAdapter(userAdapter)

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = lRecyclerViewAdapter
        recyclerView.setLoadMoreEnabled(true)
        recyclerView.setPullRefreshEnabled(true)

        recyclerView.refreshComplete(pageSize)
    }

    override fun setListener() {
        super.setListener()
        iv_back.setOnClickListener {
            finish()
        }

        lRecyclerViewAdapter.setOnItemClickListener { _, position ->
            val data = userAdapter.getData()[position]

            showMessage(data.userName)
        }

        recyclerView.setOnRefreshListener {
            page = 0
            recyclerView.setLoadMoreEnabled(true)
            mPresenter.getManagerUserList(page, pageSize)
        }
        recyclerView.setOnLoadMoreListener {
            mPresenter.getManagerUserList(++page, pageSize)
        }
    }

    override fun initData() {
        mPresenter.getManagerUserList(page, pageSize)
    }

    override fun showUserList(userList: List<UserModel>) {
        if (page == 0) {
            userAdapter.updateData(userList)
        } else {
            userAdapter.addAll(userList)
        }
        if (userList.size < pageSize) {
            recyclerView.setLoadMoreEnabled(false)
        }
        lRecyclerViewAdapter.notifyDataSetChanged()
        recyclerView.refreshComplete(pageSize)
    }
}