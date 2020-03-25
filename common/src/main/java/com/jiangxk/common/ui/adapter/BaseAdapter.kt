package com.jiangxk.common.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * @desc
 * @auth jiangxk
 * @time 2019-10-17  17:23
 */
abstract class BaseAdapter<T>(private var context: Context) : RecyclerView.Adapter<BaseViewHolder>() {
    protected val mData = ArrayList<T>()

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemView = LayoutInflater.from(context).inflate(getItemLayoutId(), parent, false)
        return BaseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener?.onItemClick(it, position)
        }
        onBindView(holder, position)
    }

    /**
     * 获取 Item 布局 Id
     * @return Int
     */
    @LayoutRes
    abstract fun getItemLayoutId(): Int

    /**
     * 回调 onBindViewHolder
     * @param holder BaseViewHolder
     * @param position Int
     */
    abstract fun onBindView(holder: BaseViewHolder, position: Int)


    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.listener = onItemClickListener
    }

    /**
     * 更新数据
     * @param data List<T>
     */
    fun addAll(data: List<T>) {
        val lastIndex = mData.size
        if (mData.addAll(data)) {
            notifyItemRangeInserted(lastIndex, mData.size)
        }
    }

    fun updateData(data: List<T>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    fun getData(): List<T> {
        return mData
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

}