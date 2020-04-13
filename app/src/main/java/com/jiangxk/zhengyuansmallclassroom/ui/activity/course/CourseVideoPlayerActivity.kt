package com.jiangxk.zhengyuansmallclassroom.ui.activity.course

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.view.View.VISIBLE
import com.jiangxk.common.common.activity.BaseMvpActivity
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerCourseVideoPlayerComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.CourseVideoPlayerModule
import com.jiangxk.zhengyuansmallclassroom.model.ParameterModel
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.course.CourseVideoPlayerContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.course.CourseVideoPlayerPresenter
import com.jiangxk.zhengyuansmallclassroom.repository.course.CourseRepository
import com.jiangxk.zhengyuansmallclassroom.repository.course.local.CourseLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.course.remote.CourseRemoteApi
import com.orhanobut.logger.Logger
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.android.synthetic.main.activity_course_player_video.*


/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.activity.course
 * @author jiangxk
 * @time 2020-04-13  15:33
 */
class CourseVideoPlayerActivity :
    BaseMvpActivity<CourseVideoPlayerContract.View, CourseVideoPlayerPresenter>(),
    CourseVideoPlayerContract.View {

    private lateinit var parameterModel: ParameterModel

    private var logId: String? = null
    private var learningStart: Long = 0
    private var learningDurationArray: Array<Long> = arrayOf()

    var orientationUtils: OrientationUtils? = null

    companion object {

        /**
         *
         * @param context Context?
         * @param parameterModel ParameterModel
         */
        fun start(context: Context?, parameterModel: ParameterModel) {
            val intent = Intent(context, CourseVideoPlayerActivity::class.java)
            intent.putExtra(Constant.EXTRA_PARAMETER, parameterModel)
            context?.startActivity(intent)
        }
    }

    override fun injectComponent() {
        DaggerCourseVideoPlayerComponent.builder().activityComponent(mActivityComponent)
            .courseVideoPlayerModule(
                CourseVideoPlayerModule(
                    this,
                    CourseRepository.getInstance(CourseLocalApi, CourseRemoteApi)
                )
            ).build().inject(this)
    }

    override fun initOperate() {
        super.initOperate()
        parameterModel = intent.getParcelableExtra(Constant.EXTRA_PARAMETER)

        if (parameterModel.courseId == 0) {
            showMessage("获取课程ID失败")
            finish()
        }
        if (parameterModel.videoUrl.isNullOrEmpty()) {
            showMessage("获取课程视频地址失败")
            finish()
        }
    }

    override fun isSetPaddingTop() = false

    override fun getLayoutId() =
        R.layout.activity_course_player_video

    override fun initView() {

        videoPlayer.setUp(parameterModel.videoUrl, true, parameterModel.courseName)

        //增加封面
//        val imageView = ImageView(this)
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP)
//        imageView.setImageResource(com.jiangxk.zhengyuansmallclassroom.R.mipmap.xxx1)
//        videoPlayer.setThumbImageView(imageView)
        //增加title
        videoPlayer.titleTextView.visibility = VISIBLE
        //设置返回键
        videoPlayer.backButton.visibility = VISIBLE
        //设置旋转
        orientationUtils = OrientationUtils(this, videoPlayer)
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.fullscreenButton.setOnClickListener {
            orientationUtils?.resolveByClick()
        }
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true)
        //设置返回按键功能
        videoPlayer.backButton.setOnClickListener {
            onBackPressed()
        }
        videoPlayer.startPlayLogic()
    }

    override fun initData() {
        mPresenter.uploadLearningLog(parameterModel)

        learningStart = System.currentTimeMillis()
    }

    override fun setListener() {
        super.setListener()
        videoPlayer.setVideoAllCallBack(object : GSYSampleCallBack() {
            override fun onStartPrepared(url: String?, vararg objects: Any?) {
                super.onStartPrepared(url, *objects)
                Logger.i("onStartPrepared")
            }

            override fun onPrepared(url: String?, vararg objects: Any?) {
                super.onPrepared(url, *objects)
                learningStart = System.currentTimeMillis()
                Logger.i("onPrepared")
            }

            override fun onClickStartIcon(url: String?, vararg objects: Any?) {
                super.onClickStartIcon(url, *objects)
                learningStart = System.currentTimeMillis()
                Logger.i("onClickStartIcon")
            }

            override fun onClickStop(url: String?, vararg objects: Any?) {
                super.onClickStop(url, *objects)
                val duration = System.currentTimeMillis() - learningStart
                learningDurationArray = learningDurationArray.plus(duration)
                Logger.i("onClickStop")
                Logger.i("onClickStop = $duration")
            }

            override fun onClickStopFullscreen(url: String?, vararg objects: Any?) {
                super.onClickStopFullscreen(url, *objects)
                val duration = System.currentTimeMillis() - learningStart
                learningDurationArray = learningDurationArray.plus(duration)
                Logger.i("onClickStopFullscreen")
                Logger.i("onClickStopFullscreen = $duration")
            }

            override fun onClickResume(url: String?, vararg objects: Any?) {
                super.onClickResume(url, *objects)
                learningStart = System.currentTimeMillis()
                Logger.i("onClickResume")
            }

            override fun onClickResumeFullscreen(url: String?, vararg objects: Any?) {
                super.onClickResumeFullscreen(url, *objects)
                learningStart = System.currentTimeMillis()
                Logger.i("onClickResumeFullscreen")
            }

            override fun onAutoComplete(url: String?, vararg objects: Any?) {
                super.onAutoComplete(url, *objects)
                val duration = System.currentTimeMillis() - learningStart
                learningDurationArray = learningDurationArray.plus(duration)

                Logger.i("onAutoComplete")
                Logger.i("onAutoComplete = $duration")
            }

            override fun onPlayError(url: String?, vararg objects: Any?) {
                super.onPlayError(url, *objects)
                val duration = System.currentTimeMillis() - learningStart
                learningDurationArray = learningDurationArray.plus(duration)

                Logger.i("onPlayError")
                Logger.i("onPlayError = $duration")
            }

            override fun onClickStartThumb(url: String?, vararg objects: Any?) {
                super.onClickStartThumb(url, *objects)
                learningStart = System.currentTimeMillis()
                Logger.i("onClickStartThumb")
            }

        })
    }

    override fun onResume() {
        super.onResume()
        videoPlayer.onVideoResume()
    }

    override fun onPause() {
        super.onPause()
        videoPlayer.onVideoPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
        orientationUtils?.releaseListener()

        var duration = 0L

        Logger.i(" duration =  $duration")
        learningDurationArray.forEach {
            Logger.i(" learningDurationArray =  $it")
            duration += it
        }
        Logger.i(" duration =  $duration")

        duration = 0

        Logger.i(" learningDurationArray =  ${learningDurationArray.size}")
        for (l in learningDurationArray) {
            duration += l
            Logger.i(" learningDurationArray =  $l")
        }



        logId?.let {
            mPresenter.updateLearningLogDuration(it, parameterModel, duration)
        }
    }

    override fun onBackPressed() {
        //先返回正常状态
        if (orientationUtils?.screenType == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.fullscreenButton.performClick()
            return
        }

        if (videoPlayer.gsyVideoManager.isPlaying) {
            val duration = System.currentTimeMillis() - learningStart
            learningDurationArray = learningDurationArray.plus(duration)
        }

        //释放所有
        videoPlayer.setVideoAllCallBack(null)
        super.onBackPressed()
    }


    override fun uploadSuccess(logId: String) {
        this.logId = logId
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Logger.i("onConfigurationChanged")
    }

}