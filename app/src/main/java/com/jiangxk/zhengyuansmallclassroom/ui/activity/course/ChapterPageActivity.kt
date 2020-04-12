package com.jiangxk.zhengyuansmallclassroom.ui.activity.course

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.github.jdsjlzx.view.ArrowRefreshHeader
import com.github.jdsjlzx.view.LoadingFooter
import com.jiangxk.common.common.activity.BaseMvpActivity
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerChapterPageComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.ChapterPageModule
import com.jiangxk.zhengyuansmallclassroom.model.ChapterModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.course.ChapterPageContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.course.ChapterPagePresenter
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import com.jiangxk.zhengyuansmallclassroom.repository.course.local.CourseLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.course.remote.CourseRemoteApi
import com.jiangxk.zhengyuansmallclassroom.ui.activity.course.NodePageActivity.Companion.EXTRA_SUBJECT_ID
import com.jiangxk.zhengyuansmallclassroom.ui.adapter.ChapterPageAdapter
import kotlinx.android.synthetic.main.activity_chapter_page.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.activity.course
 * @author jiangxk
 * @time 2020-04-11  18:34
 */
class ChapterPageActivity : BaseMvpActivity<ChapterPageContract.View, ChapterPagePresenter>(),
    ChapterPageContract.View {

    private lateinit var chapterAdapter: ChapterPageAdapter
    private lateinit var lRecyclerViewAdapter: LRecyclerViewAdapter
    private lateinit var arrowRefreshHeader: ArrowRefreshHeader
    private lateinit var loadingFooter: LoadingFooter
    private var subjectId: Int = 0
    private var nodeId: Int = 0
    private lateinit var nodeName: String
    private var page: Int = 0
    private val pageSize: Int = 20

    companion object {
        const val EXTRA_NODE_ID = "extra_node_id"
        const val EXTRA_NODE_NAME = "extra_node_name"

        /**
         *
         * @param context Context?
         * @param nodeId Int
         * @param nodeName String
         */
        fun start(context: Context?, subjectId: Int, nodeId: Int, nodeName: String) {
            val intent = Intent(context, ChapterPageActivity::class.java)
            intent.putExtra(EXTRA_SUBJECT_ID, subjectId)
            intent.putExtra(EXTRA_NODE_ID, nodeId)
            intent.putExtra(EXTRA_NODE_NAME, nodeName)
            context?.startActivity(intent)
        }
    }

    override fun injectComponent() {
        DaggerChapterPageComponent.builder().activityComponent(mActivityComponent)
            .chapterPageModule(
                ChapterPageModule(
                    this,
                    CourseRepository.getInstance(CourseLocalApi, CourseRemoteApi)
                )
            ).build().inject(this)
    }

    override fun initOperate() {
        super.initOperate()
        subjectId = intent.getIntExtra(EXTRA_SUBJECT_ID, 0)
        nodeId = intent.getIntExtra(EXTRA_NODE_ID, 0)
        nodeName = intent.getStringExtra(EXTRA_NODE_NAME)

        if (nodeId == 0) {
            showMessage("获取课程ID失败")
            finish()
        }
    }

    override fun getLayoutId() = R.layout.activity_chapter_page

    override fun initView() {
        tv_title.text = nodeName
        iv_back.visibility = View.VISIBLE

        arrowRefreshHeader = ArrowRefreshHeader(this)
        loadingFooter = LoadingFooter(this)
        recyclerView.setRefreshHeader(arrowRefreshHeader)
        recyclerView.setLoadMoreFooter(loadingFooter, false)

        chapterAdapter = ChapterPageAdapter(this)
        lRecyclerViewAdapter = LRecyclerViewAdapter(chapterAdapter)

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = lRecyclerViewAdapter
        recyclerView.setLoadMoreEnabled(true)
        recyclerView.setPullRefreshEnabled(true)

        recyclerView.refreshComplete(pageSize)


    }

    override fun initData() {
        mPresenter.getChapterList(nodeId, page, pageSize)
    }

    override fun setListener() {
        super.setListener()
        iv_back.setOnClickListener {
            finish()
        }

        lRecyclerViewAdapter.setOnItemClickListener { _, position ->
            val data = chapterAdapter.getData()[position]
            CourseListPageActivity.start(this, subjectId, data.chapterId, data.chapterName)
        }

        recyclerView.setOnRefreshListener {
            page = 0
            recyclerView.setLoadMoreEnabled(true)
            mPresenter.getChapterList(nodeId, page, pageSize)
        }
        recyclerView.setOnLoadMoreListener {
            mPresenter.getChapterList(nodeId, ++page, pageSize)
        }
    }

    override fun showChapterList(nodeList: List<ChapterModel>) {
        if (page == 0) {
            chapterAdapter.updateData(nodeList)
        } else {
            chapterAdapter.addAll(nodeList)
        }
        if (nodeList.size < pageSize) {
            recyclerView.setLoadMoreEnabled(false)
        }
        lRecyclerViewAdapter.notifyDataSetChanged()
        recyclerView.refreshComplete(pageSize)
    }

}