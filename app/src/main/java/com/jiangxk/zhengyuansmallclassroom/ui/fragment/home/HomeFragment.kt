package com.jiangxk.zhengyuansmallclassroom.ui.fragment.home

import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jiangxk.common.common.fragment.BaseMvpFragment
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerHomeComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.HomeModule
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.HomeContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.HomePresenter


class HomeFragment : BaseMvpFragment<HomePresenter>(), HomeContract.View {
    private lateinit var homeViewModel: HomeViewModel

    override fun injectComponent() {
        DaggerHomeComponent.builder().activityComponent(mActivityComponent)
            .homeModule(HomeModule(this))
            .build().inject(this)
    }

    override fun getLayoutId() = R.layout.fragment_home

    override fun initOperate() {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val textView: TextView = findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })
    }

    override fun initView() {


    }

    override fun initData() {

    }


}