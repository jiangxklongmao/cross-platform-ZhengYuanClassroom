package com.jiangxk.provider

/**
 * @desc 页面路由路径
 * @auth jiangxk
 * @time 2019-10-30  11:25
 */
object RouterPath {

    /*播放模块*/
    class Player {
        companion object {
            /*视频播放页面*/
            const val PATH_PLAYER = "/player/playActivity"
            const val PATH_PLAYER_WEB = "/playerWeb/playwebActivity"
            const val KEY_PLAYER = "videoPlayer"
            const val KEY_IMAGE = "videoImage"
            const val KEY_NAME = "videoName"
        }
    }

    /*首页模块*/
    class Home {
        companion object {
            /*视频搜索页面*/
            const val PATH_SEARCH = "/home/searchActivity"
        }
    }

}