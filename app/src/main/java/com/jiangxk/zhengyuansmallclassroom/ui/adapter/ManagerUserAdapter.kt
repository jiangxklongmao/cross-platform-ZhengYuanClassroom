package com.jiangxk.zhengyuansmallclassroom.ui.adapter

import android.content.Context
import android.widget.TextView
import com.jiangxk.common.ui.adapter.BaseAdapter
import com.jiangxk.common.ui.adapter.BaseViewHolder
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.model.UserModel

/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.adapter
 * @author jiangxk
 * @time 2020-04-09  18:00
 */
class ManagerUserAdapter(val context: Context) : BaseAdapter<UserModel>(context) {
    override fun getItemLayoutId(): Int {
        return R.layout.item_manager_user
    }

    override fun onBindView(holder: BaseViewHolder, position: Int) {
        val data = mData[position]
        holder.apply {
            setText(R.id.tv_userName, "${data.userName} 同学")
            setText(R.id.tv_phoneNumber, "手机号: ${data.phoneNumber}")
            setImageUrl(R.id.civ_avatar, data.avatarUrl)

            setText(R.id.tv_jurisdiction, "${if (data.manager == 1) "管理员" else "普通用户"}")

            if (data.status == 1) {
                setText(R.id.tv_status, "正常")
                getView<TextView>(R.id.tv_status).setTextColor(context.resources.getColor(R.color.colorPrimary))
            } else {
                setText(R.id.tv_status, "未审核")
                getView<TextView>(R.id.tv_status).setTextColor(context.resources.getColor(R.color.colorAccent))
            }
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

}