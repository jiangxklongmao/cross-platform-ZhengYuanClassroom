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
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerCourseListPageComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.CourseListPageModule
import com.jiangxk.zhengyuansmallclassroom.model.CourseModel
import com.jiangxk.zhengyuansmallclassroom.model.ParameterModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.course.CourseListPageContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.course.CourseListPagePresenter
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import com.jiangxk.zhengyuansmallclassroom.repository.course.local.CourseLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.course.remote.CourseRemoteApi
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
    private var page: Int = 0
    private val pageSize: Int = 20
    private lateinit var parameterModel: ParameterModel

    companion object {

        /**
         *
         * @param context Context?
         * @param parameterModel ParameterModel
         */
        fun start(context: Context?, parameterModel: ParameterModel) {
            val intent = Intent(context, CourseListPageActivity::class.java)
            intent.putExtra(Constant.EXTRA_PARAMETER, parameterModel)
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
        parameterModel = intent.getParcelableExtra(Constant.EXTRA_PARAMETER)

        if (parameterModel.chapterId == 0) {
            showMessage("获取课程ID失败")
            finish()
        }
    }

    override fun initView() {
        tv_title.text = parameterModel.chapterName
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
        mPresenter.getCourseList(parameterModel.subjectId, parameterModel.chapterId, page, pageSize)
    }

    override fun setListener() {
        super.setListener()
        iv_back.setOnClickListener {
            finish()
        }

        lRecyclerViewAdapter.setOnItemClickListener { _, position ->
            val data = coursePageAdapter.getData()[position]
            parameterModel.courseId = data.courseId
            parameterModel.courseName = data.courseName
            parameterModel.videoUrl = data.videoUrl
            CourseVideoPlayerActivity.start(
                this,
                parameterModel
            )
        }

        recyclerView.setOnRefreshListener {
            page = 0
            recyclerView.setLoadMoreEnabled(true)
            mPresenter.getCourseList(
                parameterModel.subjectId,
                parameterModel.chapterId,
                page,
                pageSize
            )
        }
        recyclerView.setOnLoadMoreListener {
            mPresenter.getCourseList(
                parameterModel.subjectId,
                parameterModel.chapterId,
                ++page,
                pageSize
            )
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