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
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerCourseListPageComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.CourseListPageModule
import com.jiangxk.zhengyuansmallclassroom.model.CourseModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.course.CourseListPageContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.course.CourseListPagePresenter
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import com.jiangxk.zhengyuansmallclassroom.repository.course.local.CourseLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.course.remote.CourseRemoteApi
import com.jiangxk.zhengyuansmallclassroom.ui.activity.course.NodePageActivity.Companion.EXTRA_SUBJECT_ID
import com.jiangxk.zhengyuansmallclassroom.ui.adapter.CoursePageAdapter
import kotlinx.android.synthetic.main.activity_chapter_page.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.activity.course
 * @author jiangxk
 * @time 2020-04-12  18:43
 */
class CourseListPageActivity :
    BaseMvpActivity<CourseListPageContract.View, CourseListPagePresenter>(),
    CourseListPageContract.View {


    private lateinit var coursePageAdapter: CoursePageAdapter
    private lateinit var lRecyclerViewAdapter: LRecyclerViewAdapter
    private lateinit var arrowRefreshHeader: ArrowRefreshHeader
    private lateinit var loadingFooter: LoadingFooter
    private var subjectId: Int = 0
    private var chapterId: Int = 0
    private lateinit var chapterName: String
    private var page: Int = 0
    private val pageSize: Int = 20

    companion object {
        const val EXTRA_CHAPTER_ID = "extra_chapter_id"
        const val EXTRA_CHAPTER_NAME = "extra_chapter_name"

        /**
         *
         * @param context Context?
         * @param chapterId Int
         * @param chapterName String
         */
        fun start(context: Context?, subjectId: Int, chapterId: Int, chapterName: String) {
            val intent = Intent(context, CourseListPageActivity::class.java)
            intent.putExtra(EXTRA_SUBJECT_ID, subjectId)
            intent.putExtra(EXTRA_CHAPTER_ID, chapterId)
            intent.putExtra(EXTRA_CHAPTER_NAME, chapterName)
            context?.startActivity(intent)
        }
    }

    override fun injectComponent() {
        DaggerCourseListPageComponent.builder().activityComponent(mActivityComponent)
            .courseListPageModule(
                CourseListPageModule(
                    this,
                    CourseRepository.getInstance(CourseLocalApi, CourseRemoteApi)
                )
            ).build().inject(this)
    }

    override fun getLayoutId() = R.layout.activity_course_list_page

    override fun initOperate() {
        super.initOperate()
        subjectId = intent.getIntExtra(EXTRA_SUBJECT_ID, 0)
        chapterId = intent.getIntExtra(EXTRA_CHAPTER_ID, 0)
        chapterName = intent.getStringExtra(EXTRA_CHAPTER_NAME)

        if (chapterId == 0) {
            showMessage("获取课程ID失败")
            finish()
        }
    }

    override fun initView() {
        tv_title.text = chapterName
        iv_back.visibility = View.VISIBLE

        arrowRefreshHeader = ArrowRefreshHeader(this)
        loadingFooter = LoadingFooter(this)
        recyclerView.setRefreshHeader(arrowRefreshHeader)
        recyclerView.setLoadMoreFooter(loadingFooter, false)

        coursePageAdapter = CoursePageAdapter(this)
        lRecyclerViewAdapter = LRecyclerViewAdapter(coursePageAdapter)

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = lRecyclerViewAdapter
        recyclerView.setLoadMoreEnabled(true)
        recyclerView.setPullRefreshEnabled(true)

        recyclerView.refreshComplete(pageSize)
    }

    override fun initData() {
        mPresenter.getCourseList(subjectId, chapterId, page, pageSize)
    }

    override fun setListener() {
        super.setListener()
        iv_back.setOnClickListener {
            finish()
        }

        lRecyclerViewAdapter.setOnItemClickListener { _, position ->
            val data = coursePageAdapter.getData()[position]
            showMessage(data.courseName)
        }

        recyclerView.setOnRefreshListener {
            page = 0
            recyclerView.setLoadMoreEnabled(true)
            mPresenter.getCourseList(subjectId, chapterId, page, pageSize)
        }
        recyclerView.setOnLoadMoreListener {
            mPresenter.getCourseList(subjectId, chapterId, ++page, pageSize)
        }
    }

    override fun showCourseList(courseList: List<CourseModel>) {
        if (page == 0) {
            coursePageAdapter.updateData(courseList)
        } else {
            coursePageAdapter.addAll(courseList)
        }
        lRecyclerViewAdapter.notifyDataSetChanged()
        if (courseList.size < pageSize) {
            recyclerView.setLoadMoreEnabled(false)
        }
        recyclerView.refreshComplete(pageSize)
    }

}