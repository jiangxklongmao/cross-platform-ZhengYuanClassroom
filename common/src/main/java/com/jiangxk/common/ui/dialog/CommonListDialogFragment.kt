package com.jiangxk.common.ui.dialog

import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.jiangxk.common.R
import com.jiangxk.common.common.dialog.BaseDialogFragment
import com.jiangxk.common.ui.adapter.BaseAdapter
import com.jiangxk.common.ui.adapter.BaseViewHolder
import kotlinx.android.synthetic.main.dialog_common.tv_cancel
import kotlinx.android.synthetic.main.dialog_common.tv_title
import kotlinx.android.synthetic.main.dialog_common_list.*

/**
 * @description com.jiangxk.common.ui.dialog
 * @author jiangxk
 * @time 2020-04-16  17:11
 */
class CommonListDialogFragment private constructor(builder: Builder) : BaseDialogFragment() {

    private var title: CharSequence? = ""
    @ColorRes
    private var titleColor: Int = 0

    private var cancel: CharSequence? = ""
    @ColorRes
    private var cancelColor: Int = 0
    private var listener: OnItemClickListener? = null

    private var adapter: BaseAdapter<*>? = null

    init {
        title = builder.title
        titleColor = builder.titleColor
        cancel = builder.cancel
        cancelColor = builder.cancelColor
        listener = builder.listener
        adapter = builder.adapter
    }

    override fun getLayoutId() = R.layout.dialog_common_list

    override fun initView() {
        if (title.isNullOrEmpty()) tv_title.text = "标题"
        else tv_title.text = title


        if (cancel.isNullOrEmpty()) {
            tv_cancel.text = "取消"
        } else {
            tv_cancel.text = cancel
        }

        if (titleColor == 0) {
            tv_title.setTextColor(resources.getColor(R.color.common_black))
        } else {
            tv_title.setTextColor(resources.getColor(titleColor))
        }


        if (cancelColor == 0) {
            tv_cancel.setTextColor(resources.getColor(R.color.common_gray))
        } else {
            tv_cancel.setTextColor(resources.getColor(cancelColor))
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        if (adapter != null) {
            recyclerView.adapter = adapter
        } else {
            context?.let {
                recyclerView.adapter = DefaultItemAdapter(it)
            }
        }

    }

    override fun initData() {
    }

    override fun setListener() {
        super.setListener()
        tv_cancel.setOnClickListener {
            dismiss()
        }

        adapter?.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                dismiss()
                listener?.onItemClick(position)
            }
        })

    }

    override fun cancelable() = true

    class Builder {
        internal var title: CharSequence? = null
        @ColorRes
        internal var titleColor: Int = 0
        internal var cancel: CharSequence? = null
        @ColorRes
        internal var cancelColor: Int = 0
        internal var listener: OnItemClickListener? = null

        internal var adapter: BaseAdapter<*>? = null

        fun title(title: CharSequence?): Builder {
            this.title = title
            return this
        }

        fun titleColor(@ColorRes titleColor: Int): Builder {
            this.titleColor = titleColor
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

        fun adapter(adapter: BaseAdapter<*>): Builder {
            this.adapter = adapter
            return this
        }

        fun onItemClickListener(listener: OnItemClickListener?): Builder {
            this.listener = listener
            return this
        }

        fun build(): CommonListDialogFragment {
            return CommonListDialogFragment(this)
        }

    }

    open class OnItemClickListener {
        open fun onItemClick(position: Int) {}
    }

    class DefaultItemAdapter(context: Context) : BaseAdapter<String>(context) {
        override fun getItemLayoutId() = R.layout.item_dialog_common_list

        override fun onBindView(holder: BaseViewHolder, position: Int) {
            holder.apply {
                setText(R.id.tv_option, getData()[position])
            }
        }

        override fun getItemCount(): Int {
            return mData.size
        }

    }
}