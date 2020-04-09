package com.jiangxk.common.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingParent
import androidx.core.view.ScrollingView
import com.jiangxk.common.R

/**
 * @desc
 * @auth jiangxk
 * @time 2019-10-15  18:49
 */
class StateView constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    /* Empty 布局 ID*/
    private var mEmptyResource: Int = R.layout.base_empty
    /* Retry 布局ID */
    private var mRetryResource: Int = R.layout.base_empty
    /* Loading 布局ID*/
    private var mLoadingResource: Int = R.layout.base_loading

    /** empty布局解析成的View*/
    private var mEmptyView: View? = null
    /** retry布局解析成的View*/
    private var mRetryView: View? = null
    /** loading布局解析成的View*/
    private var mLoadingView: View? = null

    private var inflater: LayoutInflater? = null

    private var mRetryClickListener: (() -> Unit)? = null

    private var currentState = 0

    /* ConstraintLayout 布局参数 */
    private var mLayoutParamsConstrain: ConstraintLayout.LayoutParams? = null

    init {
        mLayoutParamsConstrain =
            ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

        visibility = GONE
        // 不做绘制  允许优化
        setWillNotDraw(true)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    @SuppressLint("MissingSuperCall")
    override fun draw(canvas: Canvas?) {
    }

    override fun dispatchDraw(canvas: Canvas?) {
    }

    /**
     * @param visibility Int
     */
    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
    }

    /*
    * 设置View的可见性
    * */
    private fun setVisibility(view: View?, visibility: Int) {
        if (visibility != view?.visibility) {
            view?.visibility = visibility
        }
    }

    private fun inflate(@LayoutRes layoutResource: Int): View {
        val viewParent = parent ?: throw  IllegalStateException("StateView 必须有一个父布局")

        val factory = inflater ?: LayoutInflater.from(context)
        val view = factory.inflate(layoutResource, viewParent as ViewGroup, false)
        val index = viewParent.indexOfChild(this)

        //防止点击穿透
        view.isClickable = true
        view.visibility = GONE
        val layoutParams = layoutParams
        if (layoutParams != null) {
            when (viewParent) {
                is ConstraintLayout -> viewParent.addView(view, index, mLayoutParamsConstrain)
                else -> viewParent.addView(view, index, layoutParams)
            }
        } else {
            viewParent.addView(view, index)
        }

        if (mLoadingView != null && mRetryView != null && mEmptyView != null) {
            viewParent.removeViewInLayout(this)
        }

        return view
    }

    /**
     * 隐藏 非传入的 其他View
     * @param showView View?
     */
    private fun hideViews(showView: View?) {
        when {
            mEmptyView === showView -> {
                setVisibility(mLoadingView, GONE)
                setVisibility(mRetryView, GONE)
            }
            mLoadingView == showView -> {
                setVisibility(mEmptyView, GONE)
                setVisibility(mRetryView, GONE)
            }
            else -> {
                setVisibility(mEmptyView, GONE)
                setVisibility(mLoadingView, GONE)
            }
        }
    }

    /**
     * 显示状态View
     * @param view View
     */
    private fun showView(view: View) {
        setVisibility(view, VISIBLE)
        hideViews(view)
    }

    /**
     * 显示内容
     */
    fun showContent() {
        currentState = STATE_CONTENT
        //隐藏状态View，调用 setVisibility(visibility: Int) 方法
        visibility = GONE
        hideViews(mLoadingView)
        mLoadingView?.visibility = GONE
    }

    /**
     * 显示空页面
     */
    fun showEmpty(): View {
        currentState = STATE_EMPTY
        if (mEmptyView == null) {
            mEmptyView = inflate(mEmptyResource)
        }
        showView(mEmptyView!!)
        return mEmptyView!!
    }

    /**
     * 显示Retry布局
     */
    fun showRetry(): View {
        currentState = STATE_ERROR
        if (mRetryView == null) {
            mRetryView = inflate(mRetryResource)
            mRetryView!!.setOnClickListener {
                mRetryClickListener?.invoke()
            }
        }
        showView(mRetryView!!)
        return mRetryView!!
    }

    /**
     * 显示Loading 布局
     * @return View
     */
    fun showLoading(): View {
        currentState = STATE_LOADING
        if (mLoadingView == null) {
            mLoadingView = inflate(mLoadingResource)
        }
        showView(mLoadingView!!)
        return mLoadingView!!
    }

    /**
     * 设置 自定义 EmptyView
     * @param emptyResource Int
     */
    fun setEmptyResource(@LayoutRes emptyResource: Int) {
        this.mEmptyResource = emptyResource
    }

    /**
     * 设置 自定义 RetryView
     * @param retryResource Int
     */
    fun setRetryResource(@LayoutRes retryResource: Int) {
        this.mRetryResource = retryResource
    }

    /**
     * 设置 自定义 LoadingView
     * @param loadingResource Int
     */
    fun setLoadingResource(@LayoutRes loadingResource: Int) {
        mLoadingResource = loadingResource
    }

    /**
     * 设置 重试点击监听
     * @param listener Function0<Unit>
     */
    fun setOnRetryClickListener(listener: () -> Unit) {
        this.mRetryClickListener = listener
    }

    /**
     * 从错误恢复到内容
     */
    fun restoreContent() {
        if (currentState == STATE_ERROR) {
            showContent()
        }
    }


    companion object {
        const val STATE_ERROR = 0x00
        const val STATE_LOADING = 0x01
        const val STATE_CONTENT = 0x02
        const val STATE_EMPTY = 0x03

        fun inject(parentView: ViewGroup): StateView {
            var parent = parentView
            //LinearLayout  / ScrollView / AdapterView  再嵌套一层

            if (parent is LinearLayout || parent is ScrollView || parent is AdapterView<*> ||
                parent is ScrollingView && parent is NestedScrollingChild ||
                parent is NestedScrollingParent && parent is NestedScrollingChild
            ) {
                val viewParent = parent.parent

                if (viewParent == null) {
                    throw IllegalStateException("注入的ViewGroup必须有一个父布局")
                } else {
                    var root = FrameLayout(parent.context)
                    root.layoutParams = parent.layoutParams
                    if (viewParent is ViewGroup) {
                        //把parent 从父容器中移除
                        viewParent.removeView(parent)
                        //替换成新的
                        viewParent.addView(root)
                    }
                    val layoutParams =
                        ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    parent.layoutParams = layoutParams
                    root.addView(parent)
                    parent = root
                }
            }

            val statView = StateView(parent.context)
            parent.addView(statView)
            statView.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            statView.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            return statView
        }
    }
}