package com.jiangxk.zhengyuansmallclassroom.ui.adapter

import android.content.Context
import com.jiangxk.common.ui.adapter.BaseAdapter
import com.jiangxk.common.ui.adapter.BaseViewHolder
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.model.NodeModel
import com.jiangxk.zhengyuansmallclassroom.utils.ResourceUtils
import kotlin.random.Random

/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.adapter
 * @author jiangxk
 * @time 2020-04-09  18:00
 */
class NodePageAdapter(context: Context) : BaseAdapter<NodeModel>(context) {
    override fun getItemLayoutId(): Int {
        return R.layout.item_home_grade
    }

    override fun onBindView(holder: BaseViewHolder, position: Int) {
        val data = mData[position]
        holder.apply {
            setText(R.id.tv_gradeName, data.nodeName)
            setImageReource(R.id.iv_image, getRandomImageResource())
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }


    private fun getRandomImageResource(): Int {
        val value = Random.nextInt(1, 24)
        return ResourceUtils.getImageResource(value)
    }

}