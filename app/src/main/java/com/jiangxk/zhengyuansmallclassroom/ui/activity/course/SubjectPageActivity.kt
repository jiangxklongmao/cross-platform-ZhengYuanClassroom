package com.jiangxk.zhengyuansmallclassroom.ui.activity.course

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jiangxk.common.common.activity.BaseMvpActivity
import com.jiangxk.common.ui.adapter.BaseAdapter
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.constant.Constant.EXTRA_PARAMETER
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerSubjectPageComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.SubjectPageModule
import com.jiangxk.zhengyuansmallclassroom.model.ParameterModel
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
    private lateinit var parameterModel: ParameterModel

    companion object {
        /**
         *
         * @param context Context?
         * @param parameterModel ParameterModel
         */
        fun start(context: Context?, parameterModel: ParameterModel) {
            val intent = Intent(context, SubjectPageActivity::class.java)
            intent.putExtra(EXTRA_PARAMETER, parameterModel)
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
        parameterModel = intent.getParcelableExtra(EXTRA_PARAMETER)

        if (parameterModel.gradeId == 0) {
            showMessage("获取年级课程ID失败")
            finish()
        }
    }

    override fun initView() {
        tv_title.text = parameterModel.gradeName
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
                parameterModel.subjectId = data.subjectId
                parameterModel.subjectName = data.subjectName

                NodePageActivity.start(context, parameterModel)
            }
        })
    }

    override fun initData() {
        mPresenter.getSubjectList(parameterModel.gradeId)
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