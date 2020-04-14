package com.jiangxk.zhengyuansmallclassroom.ui.activity.course

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jiangxk.common.common.activity.BaseMvpActivity
import com.jiangxk.common.ui.adapter.BaseAdapter
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.constant.Constant.EXTRA_PARAMETER
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerNodePageComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.NodePageModule
import com.jiangxk.zhengyuansmallclassroom.model.NodeModel
import com.jiangxk.zhengyuansmallclassroom.model.ParameterModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.course.NodePageContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.course.NodePagePresenter
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import com.jiangxk.zhengyuansmallclassroom.repository.course.local.CourseLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.course.remote.CourseRemoteApi
import com.jiangxk.zhengyuansmallclassroom.ui.adapter.NodePageAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.activity.course
 * @author jiangxk
 * @time 2020-04-11  15:39
 */
class NodePageActivity : BaseMvpActivity<NodePageContract.View, NodePagePresenter>(),
    NodePageContract.View {


    private lateinit var nodeAdapter: NodePageAdapter
    private lateinit var parameterModel: ParameterModel

    companion object {
        /**
         *
         * @param context Context?
         * @param parameterModel ParameterModel
         */
        fun start(context: Context?, parameterModel: ParameterModel) {
            val intent = Intent(context, NodePageActivity::class.java)
            intent.putExtra(EXTRA_PARAMETER, parameterModel)
            context?.startActivity(intent)
        }
    }

    override fun injectComponent() {
        DaggerNodePageComponent.builder().activityComponent(mActivityComponent)
            .nodePageModule(
                NodePageModule(
                    this,
                    CourseRepository.getInstance(CourseLocalApi, CourseRemoteApi)
                )
            ).build().inject(this)
    }

    override fun getLayoutId() = R.layout.activity_node_page

    override fun initOperate() {
        super.initOperate()
        parameterModel = intent.getParcelableExtra(EXTRA_PARAMETER)


        if (parameterModel.subjectId == 0) {
            showMessage("获取课程ID失败")
            finish()
        }
    }

    override fun initView() {
        tv_title.text = parameterModel.subjectName
        iv_back.visibility = View.VISIBLE

        context?.let {
            nodeAdapter = NodePageAdapter(it)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = nodeAdapter
        }

        nodeAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val data = nodeAdapter.getData()[position]
                parameterModel.nodeId = data.nodeId
                parameterModel.nodeName = data.nodeName

                when (data.subjectId) {
                    1 -> {
                        CalligraphyCourseListActivity.start(context, parameterModel)
                    }
                    else -> {
                        ChapterPageActivity.start(context, parameterModel)
                    }
                }


            }
        })
    }

    override fun initData() {
        mPresenter.getNodeList(parameterModel.subjectId)
    }

    override fun setListener() {
        super.setListener()
        iv_back.setOnClickListener {
            finish()
        }
    }

    override fun showNodeList(nodeList: List<NodeModel>) {
        nodeAdapter.addAll(nodeList)
    }

}