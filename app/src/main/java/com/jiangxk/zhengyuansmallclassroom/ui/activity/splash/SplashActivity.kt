package com.jiangxk.zhengyuansmallclassroom.ui.activity.splash

import android.os.Handler
import android.view.View
import androidx.annotation.MainThread
import com.bytedance.sdk.openadsdk.*
import com.jiangxk.common.common.activity.BaseMvpActivity
import com.jiangxk.common.database.DatabaseOpenHelper
import com.jiangxk.common.utils.AppPrefsUtils
import com.jiangxk.zhengyuansmallclassroom.R
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.injection.component.DaggerSplashComponent
import com.jiangxk.zhengyuansmallclassroom.injection.module.SplashModule
import com.jiangxk.zhengyuansmallclassroom.mvp.contract.splash.SplashContract
import com.jiangxk.zhengyuansmallclassroom.mvp.presenter.splash.SplashPresenter
import com.jiangxk.zhengyuansmallclassroom.repository.user.UserRepository
import com.jiangxk.zhengyuansmallclassroom.repository.user.local.UserLocalApi
import com.jiangxk.zhengyuansmallclassroom.repository.user.remote.UserRemoteApi
import com.jiangxk.zhengyuansmallclassroom.ui.activity.home.HomeActivity
import com.jiangxk.zhengyuansmallclassroom.ui.activity.login.LoginActivity
import com.jiangxk.zhengyuansmallclassroom.utils.UIUtils
import com.jiangxk.zhengyuansmallclassroom.utils.ad.TTAdManagerHolder
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_splash.*


/**
 * @description com.jiangxk.zhengyuansmallclassroom.ui.activity.splash
 * @author jiangxk
 * @time 2020-04-15  14:31
 */
class SplashActivity : BaseMvpActivity<SplashContract.View, SplashPresenter>(),
    SplashContract.View {

    companion object {
        /**
         * 登录超时时间 7天
         */
        const val TIMEOUT = 7 * 24 * 60 * 60 * 1000

        //开屏广告加载超时时间,建议大于3000,这里为了冷启动第一次加载到广告并且展示,示例设置了3000ms
        private const val AD_TIME_OUT = 5000
        private const val TAG = "SplashActivity"
    }

    private val codeId = "887355736"

    //是否请求模板广告
    private val isExpress = false

    //是否强制跳转到主页面
    private var mForceGoMain = false

    private lateinit var ttAdNative: TTAdNative
    override fun injectComponent() {
        DaggerSplashComponent.builder()
            .activityComponent(mActivityComponent).splashModule(
                SplashModule(
                    this,
                    UserRepository.getInstance(
                        UserLocalApi(DatabaseOpenHelper),
                        UserRemoteApi()
                    )
                )
            ).build().inject(this)
    }

    override fun getLayoutId() = R.layout.activity_splash

    override fun isSetPaddingTop() = false

    override fun isSetStateBar() = false

    override fun onResume() {
        //判断是否该跳转到主页面
        if (mForceGoMain) {
            goToMainActivity()
        }
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        mForceGoMain = true
    }

    override fun initView() {
        ttAdNative = TTAdManagerHolder.get().createAdNative(this)
    }

    override fun initData() {
        loadSplashAd()
    }


    override fun authenticationComplete() {

        val loginTimestamp =
            AppPrefsUtils.getLong(Constant.SP_PERSONAL_INFORMATION_LOGIN_TIMESTAMP_KEY)

        Handler().postDelayed({
            if (loginTimestamp > 0 && (System.currentTimeMillis() - loginTimestamp) > TIMEOUT) {
                LoginActivity.start(this)
                showMessage("登录超时，请重新登录")
            } else {
                HomeActivity.start(this)
            }
            finish()
        }, 0)

    }

    private fun goToMainActivity() {
        splash_container.removeAllViews()
        mPresenter.authenticationToken()
    }

    /**
     * 加载开屏广告
     */
    private fun loadSplashAd() {
        //step3:创建开屏广告请求参数AdSlot,具体参数含义参考文档
        val adSlot = if (isExpress) {
            //个性化模板广告需要传入期望广告view的宽、高，单位dp，请传入实际需要的大小，
            //比如：广告下方拼接logo、适配刘海屏等，需要考虑实际广告大小
            val expressViewWidth: Float = UIUtils.getScreenWidthDp(this)
            val expressViewHeight: Float = UIUtils.getHeight(this)
            AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(
                    1080,
                    1920
                ) //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
                .setExpressViewAcceptedSize(expressViewWidth, expressViewHeight)
                .build()
        } else {
            AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920)
                .build()
        }

//        val adSlot = AdSlot.Builder()
//            .setCodeId(codeId)
//            .setSupportDeepLink(true)
//            .setImageAcceptedSize(1080, 1920)
//            .build()

        //step4:请求广告，调用开屏广告异步请求接口，对请求回调的广告作渲染处理
        ttAdNative.loadSplashAd(adSlot, object : TTAdNative.SplashAdListener {
            @MainThread
            override fun onError(code: Int, message: String) {
                Logger.i("$TAG - $code $message")
                goToMainActivity()
            }

            @MainThread
            override fun onTimeout() {
                Logger.i("$TAG - 开屏广告加载超时")
                goToMainActivity()
            }

            @MainThread
            override fun onSplashAdLoad(ad: TTSplashAd) {
                Logger.i("$TAG - 开屏广告请求成功")
                if (ad == null) {
                    return
                }
                //获取SplashView
                val view: View = ad.splashView
                if (view != null && splash_container != null && !this@SplashActivity.isFinishing) {
                    splash_container.removeAllViews()
                    //把SplashView 添加到ViewGroup中,注意开屏广告view：width >=70%屏幕宽；height >=50%屏幕高
                    splash_container.addView(view)
                    //设置不开启开屏广告倒计时功能以及不显示跳过按钮,如果这么设置，您需要自定义倒计时逻辑
                    //ad.setNotAllowSdkCountdown();
                } else {
                    goToMainActivity()
                }

                //设置SplashView的交互监听器
                ad.setSplashInteractionListener(object : TTSplashAd.AdInteractionListener {
                    override fun onAdClicked(view: View?, type: Int) {
                        Logger.i("$TAG onAdClicked")
                    }

                    override fun onAdShow(view: View?, type: Int) {
                        Logger.i("$TAG onAdShow")
                    }

                    override fun onAdSkip() {
                        Logger.i("$TAG onAdSkip")
                        goToMainActivity()
                    }

                    override fun onAdTimeOver() {
                        Logger.i("$TAG onAdTimeOver")
                        goToMainActivity()
                    }
                })
                if (ad.interactionType == TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
                    ad.setDownloadListener(object : TTAppDownloadListener {
                        var hasShow = false
                        override fun onIdle() {}
                        override fun onDownloadActive(
                            totalBytes: Long,
                            currBytes: Long,
                            fileName: String,
                            appName: String
                        ) {
                            if (!hasShow) {
                                Logger.i("$TAG 下载中...")
                                hasShow = true
                            }
                        }

                        override fun onDownloadPaused(
                            totalBytes: Long,
                            currBytes: Long,
                            fileName: String,
                            appName: String
                        ) {
                            Logger.i("$TAG 下载暂停...")
                        }

                        override fun onDownloadFailed(
                            totalBytes: Long,
                            currBytes: Long,
                            fileName: String,
                            appName: String
                        ) {
                            Logger.i("$TAG 下载失败...")
                        }

                        override fun onDownloadFinished(
                            totalBytes: Long,
                            fileName: String,
                            appName: String
                        ) {
                            Logger.i("$TAG 下载完成...")
                        }

                        override fun onInstalled(
                            fileName: String,
                            appName: String
                        ) {
                            Logger.i("$TAG 安装完成...")
                        }
                    })
                }
            }
        }, AD_TIME_OUT)
    }

}