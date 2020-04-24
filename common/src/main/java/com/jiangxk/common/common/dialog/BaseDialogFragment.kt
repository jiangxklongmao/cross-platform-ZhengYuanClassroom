package com.jiangxk.common.common.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment

/**
 * @description com.jiangxk.common.common.dialog
 * @author jiangxk
 * @time 2020-04-16  15:48
 */
abstract class BaseDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initLayout()

        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (initExtra()) {
            initView()
            initData()
            setListener()
        } else {
            dismiss()
        }
    }

    private fun initLayout() {
        val window = dialog?.window
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            if (isHideStatusBar()) {
                setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            }

            setGravity(dialogGravity())
            requestFeature(Window.FEATURE_NO_TITLE)
            setWindowAnimations(windowAnimations())
            dialog?.setOnKeyListener { dialog, keyCode, event -> keyCode == KeyEvent.KEYCODE_BACK && !cancelable() }
            isCancelable = cancelable()
            dialog?.setCanceledOnTouchOutside(canCanceledOnTouchOutSide())
            val wlp = window?.attributes
            if (isBackgroundTransparent()) {
                wlp?.dimAmount = 0f
            } else {
                val dimAmount = getBackgroundDimAmount()

                if (dimAmount in 0f..1f) {
                    wlp?.dimAmount = dimAmount
                }
            }
            window?.attributes = wlp

        }
    }

    /**
     * 弹窗是否隐藏状态栏
     * @return Boolean
     */
    open fun isHideStatusBar() = true

    /**
     * 布局位置
     * @return Int
     */
    open fun dialogGravity() = Gravity.CENTER

    /**
     * dialog 动画
     * @return Int
     */
    open fun windowAnimations() = 0

    /**
     * 返回键dialog 是否关闭 默认不可以
     * @return Boolean
     */
    open fun cancelable() = false

    /**
     * 点击弹窗外部关闭 默认不可以
     * @return Boolean
     */
    open fun canCanceledOnTouchOutSide() = false

    /**
     * 弹窗 背景 是否透明
     * @return Boolean
     */
    open fun isBackgroundTransparent() = false

    /**
     * 弹窗 弹窗遮罩黑色深度
     * @return Float
     */
    open fun getBackgroundDimAmount() = -1f

    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * 初始化 参数
     * @return Boolean
     */
    open fun initExtra() = true

    abstract fun initView()

    abstract fun initData()

    open fun setListener() {

    }

    /**
     * 显示Toast
     * @param message String
     */
    open fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    open fun showLongMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }


}