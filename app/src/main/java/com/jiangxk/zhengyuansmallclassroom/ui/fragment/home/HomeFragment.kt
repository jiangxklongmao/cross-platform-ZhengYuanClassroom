package com.jiangxk.zhengyuansmallclassroom.ui.fragment.home

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jiangxk.common.common.fragment.BaseMvpFragment
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerHomeComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.HomeModule
import com.jiangxk.zhengyuansmallclassroom.model.GradeModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.home.HomeContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.home.HomePresenter
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import com.jiangxk.zhengyuansmallclassroom.repository.course.local.CourseLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.course.remote.CourseRemoteApi
import com.jiangxk.zhengyuansmallclassroom.ui.adapter.HomeGradeAdapter
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseMvpFragment<HomePresenter>(), HomeContract.View {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var homeGradeAdapter: HomeGradeAdapter

    override fun injectComponent() {
        DaggerHomeComponent.builder().activityComponent(mActivityComponent)
            .homeModule(
                HomeModule(
                    this,
                    CourseRepository.getInstance(CourseLocalApi(), CourseRemoteApi())
                )
            )
            .build().inject(this)
    }

    override fun getLayoutId() = R.layout.fragment_home

    override fun initOperate() {
        super.initOperate()
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
//        val textView: TextView = findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
        })
    }

    override fun initView() {
        context?.let {
            homeGradeAdapter = HomeGradeAdapter(it)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = homeGradeAdapter
        }
    }

    override fun initData() {
        presenter.getGradeList()
    }

    override fun showGradeList(gradeList: List<GradeModel>) {
        homeGradeAdapter.addAll(gradeList)
    }


}