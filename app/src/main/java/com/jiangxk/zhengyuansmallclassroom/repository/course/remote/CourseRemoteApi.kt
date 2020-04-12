package com.jiangxk.zhengyuansmallclassroom.repository.course.remote

import com.google.gson.JsonObject
import com.jiangxk.common.common.model.BaseModel
import com.jiangxk.common.repository.QueryHashMap
import com.jiangxk.common.rxjava.Mapper
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.model.*
import com.jiangxk.zhengyuansmallclassroom.repository.ApiRepository
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * @description com.jiangxk.zhengyuansmallclassroom.repository.course.remote
 * @author jiangxk
 * @time 2020-04-09  21:36
 */
object CourseRemoteApi : ApiRepository(), ICourseRemoteApi {


    override fun getGradeList(): Observable<List<GradeModel>> {
        return authentication()
            .flatMap {

                val queryHashMap = QueryHashMap().apply {
                    put(Constant.PARAMETER_ACCESS_TOKEN, it)
                    put(Constant.PARAMETER_ENV, Constant.MINI_PROGRAM_CLASSROOM_ENV)
                    put(Constant.PARAMETER_NAME, "appGetGradeList")
                }

                //调用云函数 用户登录
                courseService.getGradeList(queryHashMap)
            }
            .filter { miniProgramResponseFilter(it) }
            .concatMap {
                Logger.i("it.resp_data = " + it.resp_data)
                Observable.just(it.getData() as BaseModel<List<GradeModel>>)
            }
            .map(Mapper())
            .subscribeOn(Schedulers.io())
    }


    /**
     * 获取SubjectList
     * @return Observable<List<SubjectModel>>
     */
    override fun getSubjectList(gradeId: Int): Observable<List<SubjectModel>> {
        return authentication()
            .flatMap {
                val queryHashMap = QueryHashMap().apply {
                    put(Constant.PARAMETER_ACCESS_TOKEN, it)
                    put(Constant.PARAMETER_ENV, Constant.MINI_PROGRAM_CLASSROOM_ENV)
                    put(Constant.PARAMETER_NAME, "appGetSubjectList")
                }

                val jsonObject = JsonObject()
                jsonObject.addProperty("gradeId", gradeId)

                val requestBody = jsonObject.toString()
                    .toRequestBody("application/json;charset=UTF-8".toMediaTypeOrNull())

                //调用云函数 获取 subjectId
                courseService.getSubjectList(queryHashMap, requestBody)
            }
            .filter { miniProgramResponseFilter(it) }
            .concatMap {
                Logger.i("it.resp_data = " + it.resp_data)
                Observable.just(it.getData() as BaseModel<List<SubjectModel>>)
            }
            .map(Mapper())
            .subscribeOn(Schedulers.io())
    }

    override fun getNodeList(subjectId: Int): Observable<List<NodeModel>> {
        return authentication()
            .flatMap {
                val queryHashMap = QueryHashMap().apply {
                    put(Constant.PARAMETER_ACCESS_TOKEN, it)
                    put(Constant.PARAMETER_ENV, Constant.MINI_PROGRAM_CLASSROOM_ENV)
                    put(Constant.PARAMETER_NAME, "appGetNodeList")
                }

                val jsonObject = JsonObject()
                jsonObject.addProperty("subjectId", subjectId)

                val requestBody = jsonObject.toString()
                    .toRequestBody("application/json;charset=UTF-8".toMediaTypeOrNull())

                //调用云函数
                courseService.getNodeList(queryHashMap, requestBody)
            }
            .filter { miniProgramResponseFilter(it) }
            .concatMap {
                Logger.i("it.resp_data = " + it.resp_data)
                Observable.just(it.getData() as BaseModel<List<NodeModel>>)
            }
            .map(Mapper())
            .subscribeOn(Schedulers.io())
    }

    override fun getChapterList(
        nodeId: Int,
        page: Int,
        pageSize: Int
    ): Observable<List<ChapterModel>> {
        return authentication()
            .flatMap {
                val queryHashMap = QueryHashMap().apply {
                    put(Constant.PARAMETER_ACCESS_TOKEN, it)
                    put(Constant.PARAMETER_ENV, Constant.MINI_PROGRAM_CLASSROOM_ENV)
                    put(Constant.PARAMETER_NAME, "appGetChapterList")
                }

                val jsonObject = JsonObject()
                jsonObject.addProperty("nodeId", nodeId)
                jsonObject.addProperty("page", page)
                jsonObject.addProperty("pageSize", pageSize)

                val requestBody = jsonObject.toString()
                    .toRequestBody("application/json;charset=UTF-8".toMediaTypeOrNull())

                //调用云函数
                courseService.getChapterList(queryHashMap, requestBody)
            }
            .filter { miniProgramResponseFilter(it) }
            .concatMap {
                Logger.i("it.resp_data = " + it.resp_data)
                Observable.just(it.getData() as BaseModel<List<ChapterModel>>)
            }
            .map(Mapper())
            .subscribeOn(Schedulers.io())
    }

    override fun getCourseList(
        chapterId: Int,
        page: Int,
        pageSize: Int
    ): Observable<List<CourseModel>> {

        return authentication()
            .flatMap {
                val queryHashMap = QueryHashMap().apply {
                    put(Constant.PARAMETER_ACCESS_TOKEN, it)
                    put(Constant.PARAMETER_ENV, Constant.MINI_PROGRAM_CLASSROOM_ENV)
                    put(Constant.PARAMETER_NAME, "appGetCourseList")
                }

                val jsonObject = JsonObject()
                jsonObject.addProperty("chapterId", chapterId)
                jsonObject.addProperty("page", page)
                jsonObject.addProperty("pageSize", pageSize)

                val requestBody = jsonObject.toString()
                    .toRequestBody("application/json;charset=UTF-8".toMediaTypeOrNull())

                //调用云函数
                courseService.getCourseList(queryHashMap, requestBody)
            }
            .filter { miniProgramResponseFilter(it) }
            .concatMap {
                Logger.i("it.resp_data = " + it.resp_data)
                Observable.just(it.getData() as BaseModel<List<CourseModel>>)
            }
            .map(Mapper())
            .subscribeOn(Schedulers.io())

    }
}