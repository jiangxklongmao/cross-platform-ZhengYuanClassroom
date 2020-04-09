package com.jiangxk.zhengyuansmallclassroom.component

import com.jiangxk.common.common.fragment.BaseFragment
import com.jiangxk.provider.service.HomeService
import com.jiangxk.zhengyuansmallclassroom.ui.fragment.home.HomeFragment

/**
 * @description com.jiangxk.zhengyuansmallclassroom.component
 * @author jiangxk
 * @time 2020-03-24  17:42
 */
class HomeServiceImpl : HomeService {
    override fun getHomeFragment(): BaseFragment {
        return HomeFragment()
    }

}