package com.jiangxk.common.ui.dialog

import android.view.View
import androidx.annotation.ColorRes
import com.jiangxk.common.R
import com.jiangxk.common.common.dialog.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_common.*

/**
 * @description com.jiangxk.common.ui.dialog
 * @author jiangxk
 * @time 2020-04-16  17:11
 */
class CommonDialogFragment private constructor(builder: Builder) : BaseDialogFragment() {

    private var title: CharSequence? = ""
    @ColorRes
    private var titleColor: Int = 0
    private var content: CharSequence? = ""
    @ColorRes
    private var contentColor: Int = 0
    private var cancel: CharSequence? = ""
    @ColorRes
    private var cancelColor: Int = 0
    private var confirm: CharSequence? = ""
    @ColorRes
    private var confirmColor: Int = 0
    private var listener: OnItemClickListener? = null

    init {
        title = builder.title
        titleColor = builder.titleColor
        content = builder.content
        contentColor = builder.contentColor
        cancel = builder.cancel
        cancelColor = builder.cancelColor
        confirm = builder.confirm
        confirmColor = builder.confirmColor
        listener = builder.listener
    }

    override fun getLayoutId() = R.layout.dialog_common

    override fun initView() {
        if (title.isNullOrEmpty()) tv_title.text = "标题"
        else tv_title.text = title

        tv_content.text = content

        if (cancel.isNullOrEmpty() && confirm.isNullOrEmpty()) {
            tv_cancel.text = "取消"
            tv_confirm.text = "确定"
        } else if (cancel.isNullOrEmpty()) {
            tv_cancel.visibility = View.GONE
            iv_vertical_line.visibility = View.GONE
        } else if (confirm.isNullOrEmpty()) {
            iv_vertical_line.visibility = View.GONE
            tv_confirm.visibility = View.GONE
        } else {
            tv_cancel.text = cancel
            tv_confirm.text = confirm
        }

        if (titleColor == 0) {
            tv_title.setTextColor(resources.getColor(R.color.common_black))
        } else {
            tv_title.setTextColor(resources.getColor(titleColor))
        }

        if (contentColor == 0) {
            tv_content.setTextColor(resources.getColor(R.color.common_text_color))
        } else {
            tv_content.setTextColor(resources.getColor(contentColor))
        }

        if (cancelColor == 0) {
            tv_cancel.setTextColor(resources.getColor(R.color.common_gray))
        } else {
            tv_cancel.setTextColor(resources.getColor(cancelColor))
        }

        if (confirmColor == 0) {
            tv_confirm.setTextColor(resources.getColor(R.color.colorPrimary))
        } else {
            tv_confirm.setTextColor(resources.getColor(confirmColor))
        }

    }

    override fun initData() {
    }

    override fun setListener() {
        super.setListener()
        tv_cancel.setOnClickListener {
            dismiss()
            listener?.onCancel()
        }
        tv_confirm.setOnClickListener {
            dismiss()
            listener?.onConfirm()
        }
    }

    override fun cancelable() = true

    class Builder {
        internal var title: CharSequence? = null
        @ColorRes
        internal var titleColor: Int = 0
        internal var content: CharSequence? = null
        @ColorRes
        internal var contentColor: Int = 0
        internal var cancel: CharSequence? = null
        @ColorRes
        internal var cancelColor: Int = 0
        internal var confirm: CharSequence? = null
        @ColorRes
        internal var confirmColor: Int = 0
        internal var listener: OnItemClickListener? = null

        fun title(title: CharSequence?): Builder {
            this.title = title
            return this
        }

        fun titleColor(@ColorRes titleColor: Int): Builder {
            this.titleColor = titleColor
            return this
        }

        fun content(content: CharSequence?): Builder {
            this.content = content
            return this
        }

        fun contentColor(@ColorRes contentColor: Int): Builder {
            this.contentColor = contentColor
            return this
        }

        fun cancel(cancel: CharSequence?): Builder {
            this.cancel = cancel
            return this
        }

        fun cancelColor(@ColorRes cancelColor: Int): Builder {
            this.cancelColor = cancelColor
            return this
        }

        fun confirm(confirm: CharSequence?): Builder {
            this.confirm = confirm
            return this
        }

        fun confirmColor(@ColorRes confirmColor: Int): Builder {
            this.confirmColor = confirmColor
            return this
        }

        fun onItemClickListener(listener: OnItemClickListener?): Builder {
            this.listener = listener
            return this
        }

        fun build(): CommonDialogFragment {
            return CommonDialogFragment(this)
        }

    }

    open class OnItemClickListener {
        open fun onCancel() {}
        open fun onConfirm() {}
    }
}