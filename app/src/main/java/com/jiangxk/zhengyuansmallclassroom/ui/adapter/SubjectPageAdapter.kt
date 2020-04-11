package com.jiangxk.zhengyuansmallclassroom.ui.adapter

import android.content.Context
import com.jiangxk.common.ui.adapter.BaseAdapter
import com.jiangxk.common.ui.adapter.BaseViewHolder
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.model.SubjectModel

/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.adapter
 * @author jiangxk
 * @time 2020-04-09  18:00
 */
class SubjectPageAdapter(context: Context) : BaseAdapter<SubjectModel>(context) {
    override fun getItemLayoutId(): Int {
        return R.layout.item_subject_page
    }

    override fun onBindView(holder: BaseViewHolder, position: Int) {
        val data = mData[position]
        holder.apply {
            setText(R.id.tv_subjectName, data.subjectName)
            setImageUrl(R.id.iv_image, data.imageUrl)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

}