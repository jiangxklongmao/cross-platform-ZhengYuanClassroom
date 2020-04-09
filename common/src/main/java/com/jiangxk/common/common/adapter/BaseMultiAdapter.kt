package com.jiangxk.kotlinMediaCommon.ui.adapter

import android.content.Context
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jiangxk.common.ui.adapter.BaseViewHolder

/**
 * @desc
 * @auth jiangxk
 * @time 2019-10-18  10:54
 */
abstract class BaseMultiAdapter<T : IMultilItemEntity>(private val context: Context?) :
    RecyclerView.Adapter<BaseViewHolder>() {

    protected val mDatas = ArrayList<T>()

    /**
     * 存储不同类型的Item布局
     */
    private val mLayouts = SparseIntArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val itemView = LayoutInflater.from(context).inflate(getItemViewType(viewType), parent, false)
        return BaseViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBindItemHolder(holder, position)
    }

    override fun getItemViewType(position: Int): Int {
        return mDatas[position].getItemType()
    }

    /**
     * 根据Item类型绑定对应的数据，通过entity的getItemType来获取类型
     * @param holder BaseViewHolder
     * @param position Int
     */
    abstract fun onBindItemHolder(holder: BaseViewHolder, position: Int)

    /**
     * 设置item类型以及对应的布局
     * @param itemType Int
     * @param layoutId Int
     */
    protected fun addItemType(itemType: Int, layoutId: Int) {
        mLayouts.put(itemType, layoutId)
    }

    /**
     * 添加数据 局部刷新
     * @param datas List<T>
     */
    fun addAll(datas: List<T>) {
        val lastIndex = mDatas.size
        if (mDatas.addAll(datas)) {
            notifyItemRangeInserted(lastIndex, mDatas.size)
        }
    }

    /**
     * 更新数据
     * @param datas List<T>
     */
    fun updateData(datas: List<T>) {
        mDatas.clear()
        mDatas.addAll(datas)
        notifyDataSetChanged()
    }

    /**
     * 获取数据
     * @return List<T>
     */
    fun getDatas(): List<T> {
        return mDatas
    }

}