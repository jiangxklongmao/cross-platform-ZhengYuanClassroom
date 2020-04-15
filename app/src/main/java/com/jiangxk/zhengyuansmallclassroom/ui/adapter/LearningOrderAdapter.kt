package com.jiangxk.zhengyuansmallclassroom.ui.adapter

import android.content.Context
import com.jiangxk.common.ui.adapter.BaseAdapter
import com.jiangxk.common.ui.adapter.BaseViewHolder
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.model.LearningOrderModel
import com.jiangxk.zhengyuansmallclassroom.utils.ResourceUtils
import kotlin.random.Random

/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.adapter
 * @author jiangxk
 * @time 2020-04-09  18:00
 */
class LearningOrderAdapter(context: Context) : BaseAdapter<LearningOrderModel>(context) {
    override fun getItemLayoutId(): Int {
        return R.layout.item_learning_order
    }

    override fun onBindView(holder: BaseViewHolder, position: Int) {
        val data = mData[position]
        holder.apply {
            setText(R.id.tv_userName, "${data.userName} 同学")
            setImageReource(R.id.iv_image, getRandomImageResource())
            setText(
                R.id.tv_learning_duration,
                String.format("%.1f", data.totalLearningDuration.div(1000f * 60))
//                data.totalLearningDuration.div(1000 * 60).toString()
            )
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