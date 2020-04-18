package com.jiangxk.zhengyuansmallclassroom.ui.activity.manager

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jiangxk.common.common.activity.BaseMvpActivity
import com.jiangxk.common.database.DatabaseOpenHelper
import com.jiangxk.common.ui.adapter.BaseAdapter
import com.jiangxk.common.ui.dialog.CommonListDialogFragment
import com.jiangxk.common.utils.GlideImageLoader
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerLimitCoursePageComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.LimitCoursePageModule
import com.jiangxk.zhengyuansmallclassroom.model.NodeLimitModel
import com.jiangxk.zhengyuansmallclassroom.model.UserModel
import com.jiangxk.zhengyuansmallclassroom.model.UserParameter
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.manager.LimitCoursePageContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.manager.LimitCoursePagePresenter
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import com.jiangxk.zhengyuansmallclassroom.repository.course.local.CourseLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.course.remote.CourseRemoteApi
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import com.jiangxk.zhengyuansmallclassroom.repository.user.local.UserLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.user.remote.UserRemoteApi
import com.jiangxk.zhengyuansmallclassroom.ui.adapter.LimitCourseAdapter
import kotlinx.android.synthetic.main.activity_limit_course_page.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.activity.manager
 * @author jiangxk
 * @time 2020-04-18  11:44
 */
class LimitCoursePageActivity :
    BaseMvpActivity<LimitCoursePageContract.View, LimitCoursePagePresenter>(),
    LimitCoursePageContract.View {


    private lateinit var nodeAdapter: LimitCourseAdapter

    private var user: UserParameter? = null

    companion object {
        /**
         *
         * @param context Context?
         */
        fun start(context: Context?, user: UserModel) {
            val intent = Intent(context, LimitCoursePageActivity::class.java)
            intent.putExtra(Constant.EXTRA_USER, user.toParameter())
            context?.startActivity(intent)
        }
    }

    override fun injectComponent() {
        DaggerLimitCoursePageComponent.builder().activityComponent(mActivityComponent)
            .limitCoursePageModule(
                LimitCoursePageModule(
                    this,
                    UserRepository.getInstance(UserLocalApi(DatabaseOpenHelper), UserRemoteApi()),
                    CourseRepository.getInstance(CourseLocalApi, CourseRemoteApi)
                )
            )
            .build().inject(this)
    }

    override fun getLayoutId() = R.layout.activity_limit_course_page

    override fun initOperate() {
        super.initOperate()

        user = intent.getParcelableExtra(Constant.EXTRA_USER)

        if (user == null) {
            showMessage("获取用户失败")
            finish()
        }
    }

    override fun initView() {
        tv_title.text = "限制课程观看次数"
        iv_back.visibility = View.VISIBLE

        context?.let {
            nodeAdapter = LimitCourseAdapter(it)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = nodeAdapter
        }

        user?.apply {
            tv_userName.text = "$userName 同学"
            GlideImageLoader().displayImage(this@LimitCoursePageActivity, avatarUrl, civ_avatar)
        }

    }

    override fun setListener() {
        super.setListener()
        iv_back.setOnClickListener {
            finish()
        }

        nodeAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val data = nodeAdapter.getData()[position]
                showOperatorDialog(position, data)
            }
        })
    }

    override fun initData() {
        user?.let { it.openId?.let { it1 -> mPresenter.getLimitCourseList(it1, it.userId) } }
    }


    fun showOperatorDialog(index: Int, nodeLimit: NodeLimitModel) {
        val list = listOf("修改限制次数", "修改剩余次数")
        val adapter = CommonListDialogFragment.DefaultItemAdapter(this)
        adapter.addAll(list)
        CommonListDialogFragment.Builder()
            .title("选择下列操作")
            .onItemClickListener(object : CommonListDialogFragment.OnItemClickListener() {
                override fun onItemClick(position: Int) {
                    when (position) {
                        0 -> showModifyLimitCount(index, nodeLimit)
                        1 -> showModifySurplusCount(index, nodeLimit)
                    }
                }
            })
            .adapter(adapter)
            .build().show(supportFragmentManager, "")
    }

    fun showModifyLimitCount(index: Int, nodeLimit: NodeLimitModel) {
        val list = listOf("无限制", "限制最大次数30", "限制最大次数20", "限制最大次数10", "限制最大次数5", "拒绝观看")
        val adapter = CommonListDialogFragment.DefaultItemAdapter(this)
        adapter.addAll(list)
        CommonListDialogFragment.Builder()
            .title("选择下列操作")
            .onItemClickListener(object : CommonListDialogFragment.OnItemClickListener() {
                override fun onItemClick(position: Int) {
                    when (position) {
                        0 -> {
                            if (nodeLimit.limitSize == -1) {
                                showMessage("当前限制大小与选择限制大小相同")
                                return
                            }
                            modifyLimitCount(index, -1, nodeLimit)
                        }
                        1 -> {
                            if (nodeLimit.limitSize == 30) {
                                showMessage("当前限制大小与选择限制大小相同")
                                return
                            }
                            modifyLimitCount(index, 30, nodeLimit)
                        }
                        2 -> {
                            if (nodeLimit.limitSize == 20) {
                                showMessage("当前限制大小与选择限制大小相同")
                                return
                            }
                            modifyLimitCount(index, 20, nodeLimit)
                        }
                        3 -> {
                            if (nodeLimit.limitSize == 10) {
                                showMessage("当前限制大小与选择限制大小相同")
                                return
                            }
                            modifyLimitCount(index, 10, nodeLimit)
                        }
                        4 -> {
                            if (nodeLimit.limitSize == 5) {
                                showMessage("当前限制大小与选择限制大小相同")
                                return
                            }
                            modifyLimitCount(index, 5, nodeLimit)
                        }
                        5 -> {
                            if (nodeLimit.limitSize == 0) {
                                showMessage("当前限制大小与选择限制大小相同")
                                return
                            }
                            modifyLimitCount(index, 0, nodeLimit)
                        }
                    }
                }
            })
            .adapter(adapter.itemColor(R.color.colorAccent))
            .build().show(supportFragmentManager, "")
    }

    fun modifyLimitCount(position: Int, limit: Int, nodeLimit: NodeLimitModel) {
        user?.let {
            it.openId?.let { it1 ->
                mPresenter.modifyLimitByUser(
                    position,
                    it1,
                    it.userId,
                    nodeLimit.subjectId,
                    nodeLimit.nodeId,
                    limit,
                    nodeLimit.totalCount,
                    1
                )
            }
        }
    }

    fun showModifySurplusCount(index: Int, nodeLimit: NodeLimitModel) {
        val list = listOf("今日无限制", "今日剩余次数30", "今日剩余次数20", "今日剩余次数10", "今日剩余次数5", "今日拒绝观看")
        val adapter = CommonListDialogFragment.DefaultItemAdapter(this)
        adapter.addAll(list)
        CommonListDialogFragment.Builder()
            .title("选择下列操作")
            .onItemClickListener(object : CommonListDialogFragment.OnItemClickListener() {
                override fun onItemClick(position: Int) {
                    when (position) {
                        0 -> {
                            if (nodeLimit.totalCount == -1) {
                                showMessage("当前剩余大小与选择剩余大小相同")
                                return
                            }
                            modifySurplusCount(index, -1, nodeLimit)
                        }
                        1 -> {
                            if (nodeLimit.totalCount == 30) {
                                showMessage("当前剩余大小与选择剩余大小相同")
                                return
                            }
                            modifySurplusCount(index, 30, nodeLimit)
                        }
                        2 -> {
                            if (nodeLimit.totalCount == 20) {
                                showMessage("当前剩余大小与选择剩余大小相同")
                                return
                            }
                            modifySurplusCount(index, 20, nodeLimit)
                        }
                        3 -> {
                            if (nodeLimit.totalCount == 10) {
                                showMessage("当前剩余大小与选择剩余大小相同")
                                return
                            }
                            modifySurplusCount(index, 10, nodeLimit)
                        }
                        4 -> {
                            if (nodeLimit.totalCount == 5) {
                                showMessage("当前剩余大小与选择剩余大小相同")
                                return
                            }
                            modifySurplusCount(index, 5, nodeLimit)
                        }
                        5 -> {
                            if (nodeLimit.totalCount == 0) {
                                showMessage("当前剩余大小与选择剩余大小相同")
                                return
                            }
                            modifySurplusCount(index, 0, nodeLimit)
                        }
                    }
                }
            })
            .adapter(adapter.itemColor(R.color.colorPrimary))
            .build().show(supportFragmentManager, "")
    }

    fun modifySurplusCount(position: Int, surplus: Int, nodeLimit: NodeLimitModel) {
        user?.let {
            it.openId?.let { it1 ->
                mPresenter.modifyLimitByUser(
                    position,
                    it1,
                    it.userId,
                    nodeLimit.subjectId,
                    nodeLimit.nodeId,
                    nodeLimit.limitSize,
                    surplus,
                    2
                )
            }
        }
    }


    override fun showLimitCourseList(limitList: List<NodeLimitModel>) {
        nodeAdapter.addAll(limitList)
    }

    override fun showSurplus(surplus: Int) {
        if (surplus == -1) {
            tv_surplus.text = "无限制"
            tv_surplus.setTextColor(resources.getColor(R.color.colorPrimary))
        } else {
            tv_surplus.text = surplus.toString()
            tv_surplus.setTextColor(resources.getColor(R.color.colorAccent))
        }
    }

    override fun getLimitCourseList(): List<NodeLimitModel> {
        return nodeAdapter.getData()
    }

    override fun showModifySuccessful(position: Int) {
        nodeAdapter.notifyItemChanged(position)
        showMessage("修改成功")
    }

}