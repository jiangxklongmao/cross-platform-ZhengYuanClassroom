package com.jiangxk.zhengyuansmallclassroom.repository.course.remote

import com.google.gson.JsonObject
import com.jiangxk.common.common.model.BaseModel
import com.jiangxk.common.repository.FiledHashMap
import com.jiangxk.common.repository.QueryHashMap
import com.jiangxk.common.rxjava.Mapper
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.constant.Constant.EXTRA_PLATFORM
import com.jiangxk.zhengyuansmallclassroom.model.*
import com.jiangxk.zhengyuansmallclassroom.repository.ApiRepository
import com.jiangxk.zhengyuansmallclassroom.utils.ResourceUtils.Companion.getRightMiniProgramFunctionName
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

                val filedHashMap = FiledHashMap().apply {
                    put(Constant.PARAMETER_ACCESS_TOKEN, it)
                    put(Constant.PARAMETER_ENV, Constant.MINI_PROGRAM_CLASSROOM_ENV)
                    put(Constant.PARAMETER_NAME, "appGetGradeList")
                }

                //调用云函数 用户登录
                courseService.getGradeList(filedHashMap)
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
        subjectId: Int,
        chapterId: Int,
        page: Int,
        pageSize: Int
    ): Observable<List<CourseModel>> {

        return authentication()
            .flatMap {
                val queryHashMap = QueryHashMap().apply {
                    put(Constant.PARAMETER_ACCESS_TOKEN, it)
                    put(Constant.PARAMETER_ENV, Constant.MINI_PROGRAM_CLASSROOM_ENV)
                    put(Constant.PARAMETER_NAME, getRightMiniProgramFunctionName(subjectId))
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

    override fun uploadLearningLog(parameterModel: ParameterModel): Observable<String> {
        return authentication()
            .flatMap {
                val queryHashMap = QueryHashMap().apply {
                    put(Constant.PARAMETER_ACCESS_TOKEN, it)
                    put(Constant.PARAMETER_ENV, Constant.MINI_PROGRAM_CLASSROOM_ENV)
                    put(Constant.PARAMETER_NAME, "appAddLearningLog")
                }

                val jsonObject = JsonObject()
                jsonObject.addProperty("userId", parameterModel.userId)
                jsonObject.addProperty("userName", parameterModel.userName)
                jsonObject.addProperty("phoneNumber", parameterModel.phoneNumber)
                jsonObject.addProperty("gradeId", parameterModel.gradeId)
                jsonObject.addProperty("subjectId", parameterModel.subjectId)
                jsonObject.addProperty("nodeId", parameterModel.nodeId)
                jsonObject.addProperty("chapterId", parameterModel.chapterId)
                jsonObject.addProperty("courseId", parameterModel.courseId)
                jsonObject.addProperty("courseName", parameterModel.courseName)
                jsonObject.addProperty("platform", EXTRA_PLATFORM)
                jsonObject.addProperty("videoUrl", parameterModel.videoUrl)

                val requestBody = jsonObject.toString()
                    .toRequestBody("application/json;charset=UTF-8".toMediaTypeOrNull())

                //调用云函数
                courseService.uploadLearningLog(queryHashMap, requestBody)
            }
            .filter { miniProgramResponseFilter(it) }
            .concatMap {
                Logger.i("it.resp_data = " + it.resp_data)
                Observable.just(it.getData() as BaseModel<String>)
            }
            .map(Mapper())
            .subscribeOn(Schedulers.io())
    }

    override fun updateLearningLogDuration(
        logId: String,
        parameterModel: ParameterModel,
        learningDuration: Long
    ): Observable<String> {
        return authentication()
            .flatMap {
                val queryHashMap = QueryHashMap().apply {
                    put(Constant.PARAMETER_ACCESS_TOKEN, it)
                    put(Constant.PARAMETER_ENV, Constant.MINI_PROGRAM_CLASSROOM_ENV)
                    put(Constant.PARAMETER_NAME, "appUpdateLearningLogDuration")
                }

                val jsonObject = JsonObject()
                jsonObject.addProperty("id", logId)
                jsonObject.addProperty("userId", parameterModel.userId)
                jsonObject.addProperty("userName", parameterModel.userName)
                jsonObject.addProperty("courseId", parameterModel.courseId)
                jsonObject.addProperty("courseName", parameterModel.courseName)
                jsonObject.addProperty("learningDuration", learningDuration)
                jsonObject.addProperty("platform", EXTRA_PLATFORM)

                val requestBody = jsonObject.toString()
                    .toRequestBody("application/json;charset=UTF-8".toMediaTypeOrNull())

                //调用云函数
                courseService.updateLearningLogDuration(queryHashMap, requestBody)
            }
            .filter { miniProgramResponseFilter(it) }
            .concatMap {
                Logger.i("it.resp_data = " + it.resp_data)
                Observable.just(it.getData() as BaseModel<String>)
            }
            .map(Mapper())
            .subscribeOn(Schedulers.io())
    }

    override fun getCalligraphyCourseList(
        nodeId: Int,
        page: Int,
        pageSize: Int
    ): Observable<List<CourseModel>> {

        return authentication()
            .flatMap {
                val queryHashMap = QueryHashMap().apply {
                    put(Constant.PARAMETER_ACCESS_TOKEN, it)
                    put(Constant.PARAMETER_ENV, Constant.MINI_PROGRAM_CLASSROOM_ENV)
                    put(Constant.PARAMETER_NAME, "appGetCalligraphyCourseList")
                }

                val jsonObject = JsonObject()
                jsonObject.addProperty("nodeId", nodeId)
                jsonObject.addProperty("page", page)
                jsonObject.addProperty("pageSize", pageSize)

                val requestBody = jsonObject.toString()
                    .toRequestBody("application/json;charset=UTF-8".toMediaTypeOrNull())

                //调用云函数
                courseService.getCalligraphyCourseList(queryHashMap, requestBody)
            }
            .filter { miniProgramResponseFilter(it) }
            .concatMap {
                Logger.i("it.resp_data = " + it.resp_data)
                Observable.just(it.getData() as BaseModel<List<CourseModel>>)
            }
            .map(Mapper())
            .subscribeOn(Schedulers.io())

    }

    override fun getLimitCourseList(openId: String, userId: Int): Observable<List<NodeLimitModel>> {
        return authentication()
            .flatMap {
                val queryHashMap = QueryHashMap().apply {
                    put(Constant.PARAMETER_ACCESS_TOKEN, it)
                    put(Constant.PARAMETER_ENV, Constant.MINI_PROGRAM_CLASSROOM_ENV)
                    put(Constant.PARAMETER_NAME, "appGetNodeAndLimitCountList")
                }

                val jsonObject = JsonObject()
                jsonObject.addProperty("openId", openId)
                jsonObject.addProperty("userId", userId)

                val requestBody = jsonObject.toString()
                    .toRequestBody("application/json;charset=UTF-8".toMediaTypeOrNull())

                //调用云函数
                courseService.getLimitCourseList(queryHashMap, requestBody)
            }
            .filter { miniProgramResponseFilter(it) }
            .concatMap {
                Logger.i("it.resp_data = " + it.resp_data)
                Observable.just(it.getData() as BaseModel<List<NodeLimitModel>>)
            }
            .map(Mapper())
            .subscribeOn(Schedulers.io())
    }

    override fun modifyLimitByUser(
        openId: String,
        userId: Int,
        subjectId: Int,
        nodeId: Int,
        limitSize: Int?,
        totalCount: Int?
    ): Observable<Boolean> {
        return authentication()
            .flatMap {
                val queryHashMap = QueryHashMap().apply {
                    put(Constant.PARAMETER_ACCESS_TOKEN, it)
                    put(Constant.PARAMETER_ENV, Constant.MINI_PROGRAM_CLASSROOM_ENV)
                    put(Constant.PARAMETER_NAME, "appModifyNodeLimitByUser")
                }

                val jsonObject = JsonObject()
                jsonObject.addProperty("openId", openId)
                jsonObject.addProperty("userId", userId)
                jsonObject.addProperty("subjectId", subjectId)
                jsonObject.addProperty("nodeId", nodeId)
                jsonObject.addProperty("limitSize", limitSize)
                jsonObject.addProperty("totalCount", totalCount)

                val requestBody = jsonObject.toString()
                    .toRequestBody("application/json;charset=UTF-8".toMediaTypeOrNull())

                //调用云函数
                courseService.modifyLimitByUser(queryHashMap, requestBody)
            }
            .filter { miniProgramResponseFilter(it) }
            .concatMap {
                Logger.i("it.resp_data = " + it.resp_data)
                Observable.just(it.getData() as BaseModel<Boolean>)
            }
            .map(Mapper())
            .subscribeOn(Schedulers.io())
    }

    override fun getAndUpdateLimitCountByUser(
        userId: Int,
        subjectId: Int,
        nodeId: Int
    ): Observable<Int> {
        return authentication()
            .flatMap {
                val queryHashMap = QueryHashMap().apply {
                    put(Constant.PARAMETER_ACCESS_TOKEN, it)
                    put(Constant.PARAMETER_ENV, Constant.MINI_PROGRAM_CLASSROOM_ENV)
                    put(Constant.PARAMETER_NAME, "appGetAndUpdateLimitCountByUser")
                }

                val jsonObject = JsonObject()
                jsonObject.addProperty("userId", userId)
                jsonObject.addProperty("subjectId", subjectId)
                jsonObject.addProperty("nodeId", nodeId)

                val requestBody = jsonObject.toString()
                    .toRequestBody("application/json;charset=UTF-8".toMediaTypeOrNull())

                //调用云函数
                courseService.getAndUpdateLimitCountByUser(queryHashMap, requestBody)
            }
            .filter { miniProgramResponseFilter(it) }
            .concatMap {
                Logger.i("it.resp_data = " + it.resp_data)
                Observable.just(it.getData() as BaseModel<Int>)
            }
            .map(Mapper())
            .subscribeOn(Schedulers.io())
    }
}