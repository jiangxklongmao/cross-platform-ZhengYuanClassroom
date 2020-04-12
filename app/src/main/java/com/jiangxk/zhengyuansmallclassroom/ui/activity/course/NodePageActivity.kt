package com.jiangxk.zhengyuansmallclassroom.ui.activity.course

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jiangxk.common.common.activity.BaseMvpActivity
import com.jiangxk.common.ui.adapter.BaseAdapter
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerNodePageComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.NodePageModule
import com.jiangxk.zhengyuansmallclassroom.model.NodeModel
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
    private var subjectId: Int = 0
    private lateinit var subjectName: String

    companion object {
        const val EXTRA_SUBJECT_ID = "extra_subject_id"
        const val EXTRA_SUBJECT_NAME = "extra_subject_name"

        /**
         *
         * @param context Context
         * @param gradeId Int
         * @param gradeName String
         */
        fun start(context: Context?, subjectId: Int, subjectName: String) {
            val intent = Intent(context, NodePageActivity::class.java)
            intent.putExtra(EXTRA_SUBJECT_ID, subjectId)
            intent.putExtra(EXTRA_SUBJECT_NAME, subjectName)
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
        subjectId = intent.getIntExtra(EXTRA_SUBJECT_ID, 0)
        subjectName = intent.getStringExtra(EXTRA_SUBJECT_NAME)

        if (subjectId == 0) {
            showMessage("获取课程ID失败")
            finish()
        }
    }

    override fun initView() {
        tv_title.text = subjectName
        iv_back.visibility = View.VISIBLE

        context?.let {
            nodeAdapter = NodePageAdapter(it)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = nodeAdapter
        }

        nodeAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val data = nodeAdapter.getData()[position]

                ChapterPageActivity.start(context, data.nodeId, data.nodeName)
            }
        })
    }

    override fun initData() {
        mPresenter.getNodeList(subjectId)
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