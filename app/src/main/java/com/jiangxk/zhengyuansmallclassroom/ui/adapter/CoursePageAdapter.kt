package com.jiangxk.zhengyuansmallclassroom.ui.adapter

import android.content.Context
import com.jiangxk.common.ui.adapter.BaseAdapter
import com.jiangxk.common.ui.adapter.BaseViewHolder
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.model.CourseModel

/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.adapter
 * @author jiangxk
 * @time 2020-04-09  18:00
 */
class CoursePageAdapter(context: Context) : BaseAdapter<CourseModel>(context) {
    override fun getItemLayoutId(): Int {
        return R.layout.item_course_list
    }

    override fun onBindView(holder: BaseViewHolder, position: Int) {
        val data = mData[position]
        holder.apply {
            setText(R.id.tv_gradeName, data.courseName)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

}