package com.jiangxk.common.injection.module

import android.app.Activity
import com.jiangxk.common.injection.scope.ActivityScope
import com.jiangxk.common.mvp.view.BaseView
import dagger.Module
import dagger.Provides

/**
 * @desc
 * @auth jiangxk
 * @time 2019-10-16  18:59
 */
@Module
class ActivityModule(private val activity: Activity, private val baseView: BaseView) {
    @ActivityScope
    @Provides
    fun provideActivity(): Activity {
        return this.activity
    }

    @Provides
    fun provideView(): BaseView {
        return this.baseView
    }
}