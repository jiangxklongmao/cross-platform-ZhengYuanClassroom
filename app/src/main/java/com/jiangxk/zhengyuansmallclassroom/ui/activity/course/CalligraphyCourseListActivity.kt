package com.jiangxk.zhengyuansmallclassroom.ui.activity.course

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.github.jdsjlzx.view.ArrowRefreshHeader
import com.github.jdsjlzx.view.LoadingFooter
import com.jiangxk.common.common.activity.BaseMvpActivity
import com.jiangxk.common.utils.AppPrefsUtils
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerCalligraphyCourseListComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.CalligraphyCourseListModule
import com.jiangxk.zhengyuansmallclassroom.model.CourseModel
import com.jiangxk.zhengyuansmallclassroom.model.ParameterModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.course.CalligraphyCourseListContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.course.CalligraphyCourseListPresenter
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import com.jiangxk.zhengyuansmallclassroom.repository.course.local.CourseLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.course.remote.CourseRemoteApi
import com.jiangxk.zhengyuansmallclassroom.ui.adapter.CoursePageAdapter
import kotlinx.android.synthetic.main.activity_calligraphy_course_list.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.activity.course 书法课程列表
 * @author jiangxk
 * @time 2020-04-14  11:42
 */
class CalligraphyCourseListActivity :
    BaseMvpActivity<CalligraphyCourseListContract.View, CalligraphyCourseListPresenter>(),
    CalligraphyCourseListContract.View {


    private lateinit var coursePageAdapter: CoursePageAdapter
    private lateinit var lRecyclerViewAdapter: LRecyclerViewAdapter
    private lateinit var arrowRefreshHeader: ArrowRefreshHeader
    private lateinit var loadingFooter: LoadingFooter
    private var page: Int = 0
    private val pageSize: Int = 20
    private lateinit var parameterModel: ParameterModel

    private var status = 0
    private var userId = 0

    override fun injectComponent() {
        DaggerCalligraphyCourseListComponent.builder().activityComponent(mActivityComponent)
            .calligraphyCourseListModule(
                CalligraphyCourseListModule(
                    this,
                    CourseRepository.getInstance(CourseLocalApi, CourseRemoteApi)
                )
            ).build().inject(this)
    }

    companion object {

        /**
         *
         * @param context Context?
         * @param parameterModel ParameterModel
         */
        fun start(context: Context?, parameterModel: ParameterModel) {
            val intent = Intent(context, CalligraphyCourseListActivity::class.java)
            intent.putExtra(Constant.EXTRA_PARAMETER, parameterModel)
            context?.startActivity(intent)
        }
    }

    override fun getLayoutId() = R.layout.activity_calligraphy_course_list

    override fun initOperate() {
        super.initOperate()
        parameterModel = intent.getParcelableExtra(Constant.EXTRA_PARAMETER)

        if (parameterModel.nodeId == 0) {
            showMessage("获取课程ID失败")
            finish()
        }
    }

    override fun initView() {
        tv_title.text = parameterModel.nodeName
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

        status = AppPrefsUtils.getInt(Constant.SP_PERSONAL_INFORMATION_STATUS_KEY)
        userId = AppPrefsUtils.getInt(Constant.SP_PERSONAL_INFORMATION_USER_ID_KEY)

        mPresenter.getCourseList(parameterModel.nodeId, page, pageSize)
    }

    override fun setListener() {
        super.setListener()
        iv_back.setOnClickListener {
            finish()
        }

        lRecyclerViewAdapter.setOnItemClickListener { _, position ->

            if (status != 1) {
                showMessage("账号未审核，请联系客服审核后再使用！")
                return@setOnItemClickListener
            }

            val data = coursePageAdapter.getData()[position]
            parameterModel.courseId = data.courseId
            parameterModel.courseName = data.courseName
            parameterModel.videoUrl = data.videoUrl

            mPresenter.getLimitCountByUser(userId, parameterModel.subjectId, parameterModel.nodeId)
        }

        recyclerView.setOnRefreshListener {
            page = 0
            recyclerView.setLoadMoreEnabled(true)
            mPresenter.getCourseList(
                parameterModel.nodeId,
                page,
                pageSize
            )
        }
        recyclerView.setOnLoadMoreListener {
            mPresenter.getCourseList(
                parameterModel.nodeId,
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

    override fun showLimitCount(limitCount: Int) {
        if (limitCount == 0) {
            showMessage("今天学习太久啦，请明天再来学习吧")
        } else {
            CourseVideoPlayerActivity.start(
                this,
                parameterModel
            )
        }
    }
}