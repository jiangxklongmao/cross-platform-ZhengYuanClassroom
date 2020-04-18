package com.jiangxk.zhengyuansmallclassroom.ui.adapter

import android.content.Context
import android.widget.TextView
import com.jiangxk.common.ui.adapter.BaseAdapter
import com.jiangxk.common.ui.adapter.BaseViewHolder
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.model.NodeLimitModel

/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.adapter
 * @author jiangxk
 * @time 2020-04-18  13:52
 */
class LimitCourseAdapter(private val context: Context) : BaseAdapter<NodeLimitModel>(context) {


    override fun getItemLayoutId() = R.layout.item_limit_course

    override fun onBindView(holder: BaseViewHolder, position: Int) {
        val data = mData[position]
        holder.apply {
            setText(R.id.tv_nodeName, "课程名称: ${data.nodeName}")

            if (data.limitSize == -1) {
                setText(R.id.tv_limit, "无限制")
                getView<TextView>(R.id.tv_limit).setTextColor(context.resources.getColor(R.color.colorPrimary))
            } else {
                setText(R.id.tv_limit, "${data.limitSize}")
                getView<TextView>(R.id.tv_limit).setTextColor(context.resources.getColor(R.color.colorAccent))
            }

            if (data.totalCount == -1) {
                setText(R.id.tv_surplus, "无限制")
                getView<TextView>(R.id.tv_surplus).setTextColor(context.resources.getColor(R.color.colorPrimary))
            } else {
                setText(R.id.tv_surplus, "${data.totalCount}")
                getView<TextView>(R.id.tv_surplus).setTextColor(context.resources.getColor(R.color.colorAccent))
            }

        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

}