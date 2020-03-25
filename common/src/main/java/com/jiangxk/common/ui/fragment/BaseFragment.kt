package com.jiangxk.common.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.jiangxk.common.R
import com.jiangxk.common.widget.StateView
import org.jetbrains.anko.find

/**
 * @desc Fragment 基类
 * @auth jiangxk
 * @time 2019-10-15  15:36
 */

abstract class BaseFragment : Fragment() {
    private var isFirstVisible: Boolean = true
    private var isFirstInvisible: Boolean = true
    //第一次onResume中的调用onUserVisible避免操作与onFirstUserVisible操作重复
    private var isFirstResume: Boolean = true

    private lateinit var rootView: View
    private var statusView: StateView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(getLayoutId(), null)
        initOperate()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isSetStateView()) {
            setStatusLayout()
        }
        initView()
        initData()
        setListener()
    }

    override fun onResume() {
        super.onResume()
        applyPermission()

        if (isFirstResume) {
            isFirstResume = false
            return
        }
        if (userVisibleHint) {
            onUserVisible()
        }
    }

    override fun onPause() {
        super.onPause()
        if (userVisibleHint) {
            onFirstUserInvisible()
        }
    }

    /**
     * 判断用户是否可见，在第一次onResume时是不会调用该方法的
     * @param isVisibleToUser Boolean
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) { //用户可见
            if (isFirstVisible) {
                isFirstVisible = false
                onFirstUserVisible()
            } else {
                onUserVisible()
            }
        } else { //用户不可见
            if (isFirstInvisible) {
                isFirstInvisible = false
                onFirstUserInvisible()
            } else {
                onUserInvisible()
            }
        }
    }

    private fun setStatusLayout() {
        val contentView: ViewGroup? = rootView?.find(R.id.content_id)
        contentView?.let {
            statusView = StateView.inject(contentView)
        }
    }

    /*处理权限模块*/
    private fun applyPermission() {}

    /**
     * 是否设置多状态View 默认True
     * @return Boolean
     */
    open fun isSetStateView() = true

    /**
     * 进行初始化操作，在 {@link onCreateView} 中调用
     */
    open fun initOperate() {}

    fun <T : View> findViewById(@IdRes id: Int): T {
        return rootView.findViewById(id)
    }

    /**
     * 设置布局ID
     * @return Int
     */
    abstract fun getLayoutId(): Int

    /**
     * 初始化视图
     */
    abstract fun initView()

    /*初始化数据*/
    abstract fun initData()

    /*初始化监听*/
    open fun setListener() {}

    /*显示内容View*/
    fun showContent() {
        statusView?.showContent()
    }

    /*显示loading*/
    fun showLoading() {
        statusView?.showLoading()
    }

    /*第一次用户可见*/
    open fun onFirstUserVisible() {}

    /*第一次不可见*/
    open fun onFirstUserInvisible() {}

    /*用户可见时调用*/
    open fun onUserVisible() {}

    /*用户不可见时调用*/
    open fun onUserInvisible() {}

    /*返回键处理*/
    open fun onKeyBackPressed() = false

    /**
     * 显示Toast
     * @param message String
     */
    open fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}