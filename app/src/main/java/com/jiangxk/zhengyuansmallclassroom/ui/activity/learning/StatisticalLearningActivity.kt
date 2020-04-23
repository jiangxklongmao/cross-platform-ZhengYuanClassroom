package com.jiangxk.zhengyuansmallclassroom.ui.activity.learning

import android.content.Context
import android.content.Intent
import com.jiangxk.common.common.activity.BaseMvpActivity
import com.jiangxk.common.database.DatabaseOpenHelper
import com.jiangxk.common.utils.GlideImageLoader
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerStatisticalLearningComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.StatisticalLearningModule
import com.jiangxk.zhengyuansmallclassroom.model.LearningLogModel
import com.jiangxk.zhengyuansmallclassroom.model.UserModel
import com.jiangxk.zhengyuansmallclassroom.model.UserParameter
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.learning.StatisticalLearningContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.learning.StatisticalLearningPresenter
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import com.jiangxk.zhengyuansmallclassroom.repository.user.local.UserLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.user.remote.UserRemoteApi
import kotlinx.android.synthetic.main.activity_statistical_learning.*
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.activity.learning
 * @author jiangxk
 * @time 2020-04-20  17:02
 */
class StatisticalLearningActivity :
    BaseMvpActivity<StatisticalLearningContract.View, StatisticalLearningPresenter>(),
    StatisticalLearningContract.View {


    //数据
    private var mDataList = arrayListOf<Float>()
    //    //星期
    private var mWeekList = arrayListOf<String>()
    private var mDateList = arrayListOf<String>()

    private var openId: String? = null
    private var userId: Int = 0
    private var user: UserParameter? = null

    companion object {

        /**
         *
         * @param context Context?
         */
        fun start(context: Context?, user: UserModel) {
            val intent = Intent(context, StatisticalLearningActivity::class.java)
            intent.putExtra(Constant.EXTRA_USER, user.toParameter())
            context?.startActivity(intent)
        }

        fun start(context: Context?, openId: String, userId: Int) {
            val intent = Intent(context, StatisticalLearningActivity::class.java)
            intent.putExtra(Constant.EXTRA_OPEN_ID, openId)
            intent.putExtra(Constant.EXTRA_USER_ID, userId)
            context?.startActivity(intent)
        }
    }

    override fun injectComponent() {
        DaggerStatisticalLearningComponent.builder().activityComponent(mActivityComponent)
            .statisticalLearningModule(
                StatisticalLearningModule(
                    this,
                    UserRepository.getInstance(UserLocalApi(DatabaseOpenHelper), UserRemoteApi())
                )
            ).build().inject(this)
    }

    override fun getLayoutId() = R.layout.activity_statistical_learning

    override fun initOperate() {
        super.initOperate()
        user = intent.getParcelableExtra(Constant.EXTRA_USER)
        openId = intent.getStringExtra(Constant.EXTRA_OPEN_ID)
        userId = intent.getIntExtra(Constant.EXTRA_USER_ID, 0)
        if (user == null && openId.isNullOrEmpty() && userId == 0) {
            showMessage("获取用户失败")
            finish()
        }
    }

    override fun initView() {
        tv_title.text = "学习统计"


        user?.apply {
            tv_userName.text = userName
            GlideImageLoader().displayImage(this@StatisticalLearningActivity, avatarUrl, civ_avatar)
        }
    }

    override fun initData() {
        if (user != null) {
            user!!.openId?.let {
                mPresenter.getTotalDuration(it, user!!.userId)
                mPresenter.getRecentLearningList(it, user!!.userId)
            }
        } else {
            openId?.let {
                mPresenter.getUserByOpenIdAndUserId(it, userId)
                mPresenter.getTotalDuration(it, userId)
                mPresenter.getRecentLearningList(it, userId)
            }
        }
    }

    override fun showUser(userModel: UserModel) {
        tv_userName.text = userModel.userName
        GlideImageLoader().displayImage(this, userModel.avatarUrl, civ_avatar)
    }

    override fun showTotalDuration(duration: Long) {
        val minute = String.format("%.1f", duration.div(1000f * 60))
        tv_total_duration.text = minute
    }

    override fun showLearningList(logList: List<LearningLogModel>) {
        mDataList.clear()
        mDateList.clear()
        for (i in 0..6) {
            if (i < logList.size) {
                mDataList.add(logList[i].totalLearningDuration.div(60 * 1000f))
                val dateList = logList[i].learningDate.split("-")
                val mouth = if (dateList[1].toInt() > 9) dateList[1] else "0${dateList[1]}"
                val day = if (dateList[2].toInt() > 9) dateList[2] else "0${dateList[2]}"

                mDateList.add("$mouth/$day")
            } else {
                mDataList.add(0f)
                mDateList.add("00/00")
            }
        }
        brokenLineGraph?.apply {
            dataList.clear()
            dateList.clear()
            weekList.clear()
            dataList.addAll(mDataList)
//            weekList.addAll(mWeekList)
            dateList.addAll(mDateList)
            invalidate()
        }
    }
}