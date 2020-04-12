package com.jiangxk.zhengyuansmallclassroom.utils

import com.jiangxk.zhengyuansmallclassroom.R

/**
 * @description com.jiangxk.zhengyuansmallclassroom.utils
 * @author jiangxk
 * @time 2020-04-09  21:15
 */
class ResourceUtils {
    companion object {

        fun getImageResource(id: Int): Int {
            return when (id) {
                1 -> R.drawable.image_1
                2 -> R.drawable.image_2
                3 -> R.drawable.image_3
                4 -> R.drawable.image_4
                5 -> R.drawable.image_5
                6 -> R.drawable.image_6
                7 -> R.drawable.image_7
                8 -> R.drawable.image_8
                9 -> R.drawable.image_9
                10 -> R.drawable.image_10
                11 -> R.drawable.image_11
                12 -> R.drawable.image_12
                13 -> R.drawable.image_13
                14 -> R.drawable.image_14
                15 -> R.drawable.image_15
                16 -> R.drawable.image_16
                17 -> R.drawable.image_17
                18 -> R.drawable.image_18
                19 -> R.drawable.image_19
                20 -> R.drawable.image_20
                21 -> R.drawable.image_21
                22 -> R.drawable.image_22
                23 -> R.drawable.image_23
                24 -> R.drawable.image_24
                else -> R.drawable.image_1
            }
        }

        /**
         * 获取 小程序函数名
         * @param subjectId Int
         * @return String
         */
        fun getRightMiniProgramFunctionName(subjectId: Int): String {
            return when (subjectId) {
                1 -> {  //少儿硬笔书法
                    ""
                }
                2 -> {  //逻辑思维训练
                    "appGetPrimarySchoolMathematicsOlympicsCourseList"
                }
                3 -> {  //小学数学
                    "appGetPrimarySchoolMathCourseList"
                }
                4 -> {  //小学国学语文
                    "appGetPrimarySchoolChineseCourseList"
                }
                5 -> {  //初中数学
                    "appGetJuniorMiddleSchoolMathList"
                }
                6 -> {  //初中物理
                    "appGetJuniorMiddleSchoolPhysicsList"
                }
                7 -> {  //初中化学
                    "appGetJuniorMiddleSchoolChemistryList"
                }
                else -> {
                    ""
                }
            }
        }

    }


}