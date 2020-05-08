package com.jiangxk.common.http

import com.jiangxk.common.BuildConfig
import com.orhanobut.logger.Logger
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset

/**
 * @description com.jiangxk.common.model
 * @author jiangxk
 * @time 2020-03-25  17:54
 */
class RetrofitFactory private constructor(baseUrl: String) {

    companion object {

        private var instance: RetrofitFactory? = null

        fun getInstance(baseUrl: String): RetrofitFactory {
            if (instance == null) {
                instance = RetrofitFactory(baseUrl)
            }
            return instance!!
        }
    }

    fun <T> create(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }

    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(initOkHttpClient())
            .build()
    }

    /**
     * 初始化OKHttp
     */
    private fun initOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(initTokenInterceptor())
            .addInterceptor(initHeaderInterceptor())
            .addInterceptor(HttpLoggingInterceptor())
            .addInterceptor(initJsonLoggingInterceptor())
            .build()
    }

    /**
     * 存储token
     */
    private fun initTokenInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)

            response
        }
    }

    /**
     * 请求头拦截器
     */
    private fun initHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
//                .addHeader("Content-Type", "application/json")
//                .addHeader("charset", "UTF-8")
                .build()

            chain.proceed(request)
        }
    }

    private fun initJsonLoggingInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            print(request.url)

            if (BuildConfig.DEBUG) {
                val json = response.body?.string()

                val requestBody = request.body
                val buffer = Buffer()
                var requestBodyString = ""
                requestBody?.let {
                    it.writeTo(buffer)
                    requestBodyString = buffer.readString(Charset.forName("UTF-8"))
                    buffer.close()
                }

                json?.let {
                    Logger.i("$request\nrequestBody = ${if (!requestBodyString.isNullOrEmpty()) requestBodyString else "无body"}\nResponse = $it")
                }

                response.newBuilder().body(json?.toResponseBody()).build()
            } else {
                response
            }
        }
    }
}