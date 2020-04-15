package com.jiangxk.zhengyuansmallclassroom.ui.fragment.home

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jiangxk.common.common.fragment.BaseMvpFragment
import com.jiangxk.common.ui.adapter.BaseAdapter
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerHomeComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.HomeModule
import com.jiangxk.zhengyuansmallclassroom.model.GradeModel
import com.jiangxk.zhengyuansmallclassroom.model.ParameterModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.home.HomeContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.home.HomePresenter
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import com.jiangxk.zhengyuansmallclassroom.repository.course.local.CourseLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.course.remote.CourseRemoteApi
import com.jiangxk.zhengyuansmallclassroom.ui.activity.course.SubjectPageActivity
import com.jiangxk.zhengyuansmallclassroom.ui.adapter.HomeGradeAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_toolbar.*


class HomeFragment : BaseMvpFragment<HomePresenter>(), HomeContract.View {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var homeGradeAdapter: HomeGradeAdapter

    override fun injectComponent() {
        DaggerHomeComponent.builder().activityComponent(mActivityComponent)
            .homeModule(
                HomeModule(
                    this,
                    CourseRepository.getInstance(CourseLocalApi, CourseRemoteApi)
                )
            )
            .build().inject(this)
    }

    override fun getLayoutId() = R.layout.fragment_home

    override fun initOperate() {
        super.initOperate()
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel.text.observe(this, Observer {
        })
    }

    override fun initView() {
        tv_title.text = "课程列表"
        iv_back.visibility = View.GONE

        context?.let {
            homeGradeAdapter = HomeGradeAdapter(it)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = homeGradeAdapter
        }

        homeGradeAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val data = homeGradeAdapter.getData()[position]
                if (!data.isOpen) {
                    showMessage(getString(R.string.app_tips_course_not_open))
                    return
                }
                SubjectPageActivity.start(
                    context, ParameterModel.getGradeParameter(data.gradeId, data.gradeName)
                )
            }
        })
    }

    override fun initData() {
        mPresenter.getGradeList()
    }

    override fun showGradeList(gradeList: List<GradeModel>) {
        homeGradeAdapter.addAll(gradeList)
    }


}