package com.jiangxk.zhengyuansmallclassroom.model

/**
 * @description com.jiangxk.zhengyuansmallclassroom.model
 * @author jiangxk
 * @time 2020-04-11  18:36
 */
data class ChapterModel(
    val _id: String,
    val chapterId: Int,
    val chapterName: String,
    val nodeId: Int,
    val status: Int,
    val subjectId: Int
)