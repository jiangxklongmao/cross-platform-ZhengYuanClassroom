package com.jiangxk.zhengyuansmallclassroom.ui.activity.course

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jiangxk.common.common.activity.BaseMvpActivity
import com.jiangxk.common.ui.adapter.BaseAdapter
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerSubjectPageComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.SubjectPageModule
import com.jiangxk.zhengyuansmallclassroom.model.SubjectModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.course.SubjectPageContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.course.SubjectPagePresenter
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import com.jiangxk.zhengyuansmallclassroom.repository.course.local.CourseLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.course.remote.CourseRemoteApi
import com.jiangxk.zhengyuansmallclassroom.ui.adapter.SubjectPageAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.activity.course
 * @author jiangxk
 * @time 2020-04-10  18:26
 */
class SubjectPageActivity : BaseMvpActivity<SubjectPageContract.View, SubjectPagePresenter>(),
    SubjectPageContract.View {


    private lateinit var subjectAdapter: SubjectPageAdapter
    private var gradeId: Int = 0
    private lateinit var gradeName: String

    companion object {
        const val EXTRA_GRADE_ID = "extra_grade_id"
        const val EXTRA_GRADE_NAME = "extra_grade_name"

        /**
         *
         * @param context Context
         * @param gradeId Int
         * @param gradeName String
         */
        fun start(context: Context?, gradeId: Int, gradeName: String) {
            val intent = Intent(context, SubjectPageActivity::class.java)
            intent.putExtra(EXTRA_GRADE_ID, gradeId)
            intent.putExtra(EXTRA_GRADE_NAME, gradeName)
            context?.startActivity(intent)
        }
    }

    override fun injectComponent() {
        DaggerSubjectPageComponent.builder().activityComponent(mActivityComponent)
            .subjectPageModule(
                SubjectPageModule(
                    this,
                    CourseRepository.getInstance(CourseLocalApi, CourseRemoteApi)
                )
            ).build().inject(this)
    }

    override fun getLayoutId() = R.layout.activity_subject_page

    override fun initOperate() {
        super.initOperate()
        gradeId = intent.getIntExtra(EXTRA_GRADE_ID, 0)
        gradeName = intent.getStringExtra(EXTRA_GRADE_NAME)

        if (gradeId == 0) {
            showMessage("获取年级课程ID失败")
            finish()
        }
    }

    override fun initView() {
        tv_title.text = gradeName
        iv_back.visibility = View.VISIBLE

        context?.let {
            subjectAdapter = SubjectPageAdapter(it)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = subjectAdapter
        }

        subjectAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val data = subjectAdapter.getData()[position]
                if (!data.isOpen) {
                    showMessage(getString(R.string.app_tips_course_not_open))
                    return
                }
                showMessage(data.subjectName)
            }
        })
    }

    override fun initData() {
        mPresenter.getSubjectList(gradeId)
    }

    override fun setListener() {
        super.setListener()
        iv_back.setOnClickListener {
            finish()
        }
    }

    override fun showSubjectList(subjectList: List<SubjectModel>) {
        subjectAdapter.addAll(subjectList)
    }
}