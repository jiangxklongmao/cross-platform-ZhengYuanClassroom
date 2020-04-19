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
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerChapterPageComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.ChapterPageModule
import com.jiangxk.zhengyuansmallclassroom.model.ChapterModel
import com.jiangxk.zhengyuansmallclassroom.model.ParameterModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.course.ChapterPageContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.course.ChapterPagePresenter
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import com.jiangxk.zhengyuansmallclassroom.repository.course.local.CourseLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.course.remote.CourseRemoteApi
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
    private lateinit var parameterModel: ParameterModel

    private var page: Int = 0
    private val pageSize: Int = 20

    private var status = 0

    companion object {

        /**
         *
         * @param context Context?
         * @param parameterModel ParameterModel
         */
        fun start(context: Context?, parameterModel: ParameterModel) {
            val intent = Intent(context, ChapterPageActivity::class.java)
            intent.putExtra(Constant.EXTRA_PARAMETER, parameterModel)
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
        parameterModel = intent.getParcelableExtra(Constant.EXTRA_PARAMETER)

        if (parameterModel.nodeId == 0) {
            showMessage("获取课程ID失败")
            finish()
        }
    }

    override fun getLayoutId() = R.layout.activity_chapter_page

    override fun initView() {
        tv_title.text = parameterModel.nodeName
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
        mPresenter.getChapterList(parameterModel.nodeId, page, pageSize)

        status = AppPrefsUtils.getInt(Constant.SP_PERSONAL_INFORMATION_STATUS_KEY)
    }

    override fun setListener() {
        super.setListener()
        iv_back.setOnClickListener {
            finish()
        }

        lRecyclerViewAdapter.setOnItemClickListener { _, position ->
            val data = chapterAdapter.getData()[position]

            if (status != 1) {
                showMessage("账号未审核，请联系客服审核后再使用！")
                return@setOnItemClickListener
            }

            parameterModel.chapterId = data.chapterId
            parameterModel.chapterName = data.chapterName
            CourseListPageActivity.start(
                this, parameterModel
            )
        }

        recyclerView.setOnRefreshListener {
            page = 0
            recyclerView.setLoadMoreEnabled(true)
            mPresenter.getChapterList(parameterModel.nodeId, page, pageSize)
        }
        recyclerView.setOnLoadMoreListener {
            mPresenter.getChapterList(parameterModel.nodeId, ++page, pageSize)
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