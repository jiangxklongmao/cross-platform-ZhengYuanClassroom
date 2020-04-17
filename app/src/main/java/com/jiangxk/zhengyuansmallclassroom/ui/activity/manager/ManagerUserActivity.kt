package com.jiangxk.zhengyuansmallclassroom.ui.activity.manager

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.github.jdsjlzx.view.ArrowRefreshHeader
import com.github.jdsjlzx.view.LoadingFooter
import com.jiangxk.common.common.activity.BaseMvpActivity
import com.jiangxk.common.database.DatabaseOpenHelper
import com.jiangxk.common.ui.dialog.CommonDialogFragment
import com.jiangxk.common.ui.dialog.CommonListDialogFragment
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

            showOperatorDialog(data._id, data.userName, data.manager, data.status)
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


    /**
     * 操作弹窗
     * @param docId String
     * @param userName String
     * @param manager Int
     * @param status Int
     */
    private fun showOperatorDialog(docId: String, userName: String, manager: Int, status: Int) {

        val list = listOf("修改权限", "修改状态", "限制次数")
        val adapter = CommonListDialogFragment.DefaultItemAdapter(this)
        adapter.addAll(list)
        CommonListDialogFragment.Builder()
            .title("选择下列操作")
            .onItemClickListener(object : CommonListDialogFragment.OnItemClickListener() {
                override fun onItemClick(position: Int) {
                    when (position) {
                        0 -> modifyPermissions(docId, userName, manager)
                        1 -> modifyStatus(docId, userName, status)
                        2 -> limitCount()
                    }
                }
            })
            .adapter(adapter)
            .build().show(supportFragmentManager, "")
    }

    /**
     *
     * @param docId String
     * @param userName String
     * @param manager Int
     */
    private fun modifyPermissions(docId: String, userName: String, manager: Int) {
        CommonDialogFragment.Builder()
            .title("权限修改")
            .content("确定将 ( $userName ) 权限修改成\n${if (manager == 1) "普通用户" else "管理员"} ?")
            .cancel("取消")
            .confirm("确定")
            .confirmColor(R.color.colorPrimary)
            .onItemClickListener(object : CommonDialogFragment.OnItemClickListener() {
                override fun onConfirm() {
                    mPresenter.modifyPermissions(docId, if (manager == 1) 0 else 1)
                }

                override fun onCancel() {
                    showMessage("取消修改")
                }
            })
            .build()
            .show(supportFragmentManager, "")
    }

    /**
     *
     * @param docId String
     * @param userName String
     * @param status Int
     */
    private fun modifyStatus(docId: String, userName: String, status: Int) {
        CommonDialogFragment.Builder()
            .title("状态修改")
            .content("确定将 ( $userName ) 状态修改成\n${if (status == 1) "未审核" else "正常"} ?")
            .cancel("取消")
            .confirm("确定")
            .confirmColor(R.color.colorPrimary)
            .onItemClickListener(object : CommonDialogFragment.OnItemClickListener() {
                override fun onConfirm() {
                    mPresenter.modifyStatus(docId, if (status == 1) 0 else 1)
                }

                override fun onCancel() {
                    showMessage("取消修改")
                }
            })
            .build()
            .show(supportFragmentManager, "")
    }

    private fun limitCount() {
        showMessage("限制次数")
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

    override fun modifyPermissionsSuccessful() {
        showMessage("修改成功")
        page = 0
        mPresenter.getManagerUserList(page, pageSize)
    }

    override fun modifyStatusSuccessful() {
        showMessage("修改成功")
        page = 0
        mPresenter.getManagerUserList(page, pageSize)
    }

}