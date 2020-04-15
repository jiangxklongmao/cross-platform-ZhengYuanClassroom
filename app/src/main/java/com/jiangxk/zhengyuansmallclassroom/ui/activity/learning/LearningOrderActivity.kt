package com.jiangxk.zhengyuansmallclassroom.ui.activity.learning

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
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerLearningOrderComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.LearningOrderModule
import com.jiangxk.zhengyuansmallclassroom.model.LearningOrderModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.learning.LearningOrderContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.learning.LearningOrderPresenter
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import com.jiangxk.zhengyuansmallclassroom.repository.user.local.UserLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.user.remote.UserRemoteApi
import com.jiangxk.zhengyuansmallclassroom.ui.adapter.LearningOrderAdapter
import kotlinx.android.synthetic.main.activity_learning_order.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.activity.learning
 * @author jiangxk
 * @time 2020-04-15  16:44
 */
class LearningOrderActivity : BaseMvpActivity<LearningOrderContract.View, LearningOrderPresenter>(),
    LearningOrderContract.View {

    private lateinit var learningOrderAdapter: LearningOrderAdapter
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
            val intent = Intent(context, LearningOrderActivity::class.java)
            context?.startActivity(intent)
        }
    }

    override fun injectComponent() {
        DaggerLearningOrderComponent.builder().activityComponent(mActivityComponent)
            .learningOrderModule(
                LearningOrderModule(
                    this,
                    UserRepository.getInstance(UserLocalApi(DatabaseOpenHelper), UserRemoteApi())
                )
            ).build().inject(this)
    }

    override fun getLayoutId() = R.layout.activity_learning_order

    override fun initView() {
        tv_title.text = "学习时长排名列表"
        iv_back.visibility = View.VISIBLE

        arrowRefreshHeader = ArrowRefreshHeader(this)
        loadingFooter = LoadingFooter(this)
        recyclerView.setRefreshHeader(arrowRefreshHeader)
        recyclerView.setLoadMoreFooter(loadingFooter, false)

        learningOrderAdapter = LearningOrderAdapter(this)
        lRecyclerViewAdapter = LRecyclerViewAdapter(learningOrderAdapter)

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
            val data = learningOrderAdapter.getData()[position]

            showMessage(data.userName)
        }

        recyclerView.setOnRefreshListener {
            page = 0
            recyclerView.setLoadMoreEnabled(true)
            mPresenter.getLearningOrderList(page, pageSize)
        }
        recyclerView.setOnLoadMoreListener {
            mPresenter.getLearningOrderList(++page, pageSize)
        }
    }

    override fun initData() {
        mPresenter.getLearningOrderList(page, pageSize)
    }

    override fun showLearningOrderList(array: List<LearningOrderModel>) {
        if (page == 0) {
            learningOrderAdapter.updateData(array)
        } else {
            learningOrderAdapter.addAll(array)
        }
        if (array.size < pageSize) {
            recyclerView.setLoadMoreEnabled(false)
        }
        lRecyclerViewAdapter.notifyDataSetChanged()
        recyclerView.refreshComplete(pageSize)
    }

}
