package com.jiangxk.common.utils

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.ProgressCallback
import com.jiangxk.common.common.BaseApplication
import com.orhanobut.logger.Logger
import java.io.File

/**
 * @description com.jiangxk.common.utils
 * @author jiangxk
 * @time 2020-04-23  17:53
 */

object DownloadManager {


    fun load(url: String?, listener: DownloadListener? = null) {
        url?.let {
            Fuel.download(it)
                .fileDestination { response, url ->
                    val path = "/classroom"
                    val dir = BaseApplication.context.getExternalFilesDir(path)
//                    val dir = Environment.getExternalStoragePublicDirectory(path)
                    dir?.exists()?.let { exists ->
                        if (!exists) {
                            dir?.mkdirs()
                        }
                    }
                    val file = File(dir?.absolutePath, "temp.mp4")

                    file
                }
                .progress(object : ProgressCallback {
                    override fun invoke(readBytes: Long, totalBytes: Long) {
                        listener?.progress((readBytes.toFloat() / totalBytes * 100).toInt())
                    }
                })
                .response { request, response, result ->
                    when (result) {
                        is com.github.kittinunf.result.Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
                            ex.message?.let { message ->
                                listener?.error(message)
                            }

                        }
                        is com.github.kittinunf.result.Result.Success -> {
                            val data = result.get()
                            println(data)
                        }
                    }

                    Logger.i("request = $request")
                    Logger.i("response = $response")
                    Logger.i("result = $result")
                }

        }
    }

    fun load(url: String?, file: File, listener: DownloadListener? = null) {
        url?.let {
            Fuel.download(it)
                .fileDestination { response, url ->
                    file
                }
                .progress(object : ProgressCallback {
                    override fun invoke(readBytes: Long, totalBytes: Long) {
                        listener?.progress((readBytes.toFloat() / totalBytes * 100).toInt())
                    }
                })
                .response { request, response, result ->
                    when (result) {
                        is com.github.kittinunf.result.Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
                            ex.message?.let { message ->
                                listener?.error(message)
                            }

                        }
                        is com.github.kittinunf.result.Result.Success -> {
                            val data = result.get()
                            listener?.success(file)
                        }
                    }
                }

        }
    }

    interface DownloadListener {
        fun progress(progress: Int)
        fun success(file: File)
        fun error(error: String)
    }
}