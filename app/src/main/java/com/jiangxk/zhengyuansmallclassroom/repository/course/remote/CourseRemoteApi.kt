package com.jiangxk.zhengyuansmallclassroom.repository.course.remote

import com.jiangxk.common.common.model.BaseModel
import com.jiangxk.common.repository.QueryHashMap
import com.jiangxk.common.rxjava.Mapper
import com.jiangxk.zhengyuansmallclassroom.constant.Constant
import com.jiangxk.zhengyuansmallclassroom.model.GradeModel
import com.jiangxk.zhengyuansmallclassroom.repository.ApiRepository
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * @description com.jiangxk.zhengyuansmallclassroom.repository.course.remote
 * @author jiangxk
 * @time 2020-04-09  21:36
 */
class CourseRemoteApi : ApiRepository(), ICourseRemoteApi {
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
}