package com.jiangxk.zhengyuansmallclassroom.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.jiangxk.common.ext.dp2px
import com.jiangxk.common.ext.sp2px
import com.jiangxk.zhengyuansmallclassroom.R
import org.jetbrains.anko.collections.forEachWithIndex
import java.text.DecimalFormat

/**
 * @desc
 * @auth jiangxk
 * @time 2018/5/31  10:19
 */
class BrokenLineGraphChartView : View {
    val DEFAULT_WIDTH = context.dp2px(100)
    val DEFAULT_HEIGHT = context.dp2px(100)

    //文本
    private lateinit var textPaint: Paint
    //坐标
    private lateinit var coordinatePaint: Paint
    //星期
    private lateinit var weekPaint: Paint
    //圆点
    private lateinit var dotPaint: Paint
    //连接虚线
    private lateinit var imaginaryLinePaint: Paint
    //渐变背景
    private lateinit var shaderPaint: Paint
    //浮动块
    private lateinit var floatPaint: Paint
    //浮动文本
    private lateinit var floatTextPaint: Paint

    private lateinit var pathEffect: PathEffect

    //坐标 边距
    var textMargin = context.dp2px(20)
    //圆点半径
    var radius = context.dp2px(2)


    //最大值
    var maxData = 1000f
    //最小值
    var minData = 0f

    //数据
    var dataList = arrayListOf<Float>()
    //横坐标值
    var weekList = arrayListOf<String>()
    //横坐标值
    var dateList = arrayListOf<String>()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        init()
    }

    private fun init() {
        dataList.clear()
        dataList.add(0f)
        dataList.add(0f)
        dataList.add(0f)
        dataList.add(0f)
        dataList.add(0f)
        dataList.add(0f)
        dataList.add(0f)

        dateList.add("")
        dateList.add("")
        dateList.add("")
        dateList.add("")
        dateList.add("")
        dateList.add("")
        dateList.add("")

        textPaint = Paint()
        textPaint.isAntiAlias = true
        textPaint.strokeWidth = 1f
        textPaint.style = Paint.Style.FILL_AND_STROKE
        textPaint.strokeCap = Paint.Cap.ROUND
        textPaint.color = context.resources.getColor(R.color.textSecondColor)
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.textSize = context.sp2px(11).toFloat()

        coordinatePaint = Paint()
        coordinatePaint.isAntiAlias = true
        coordinatePaint.strokeWidth = 1f
        coordinatePaint.style = Paint.Style.STROKE
        coordinatePaint.color = context.resources.getColor(R.color.textSecondColor)

        weekPaint = Paint()
        weekPaint.isAntiAlias = true
        weekPaint.strokeWidth = 1f
        weekPaint.style = Paint.Style.FILL_AND_STROKE
        weekPaint.strokeCap = Paint.Cap.ROUND
        weekPaint.color = context.resources.getColor(R.color.textSecondColor)
        weekPaint.textAlign = Paint.Align.CENTER
        weekPaint.textSize = context.sp2px(11).toFloat()

        floatPaint = Paint()
        floatPaint.isAntiAlias = true
        floatPaint.strokeWidth = 1f
        floatPaint.style = Paint.Style.FILL_AND_STROKE
        floatPaint.color = context.resources.getColor(R.color.colorAccent)

        dotPaint = Paint()
        dotPaint.isAntiAlias = true
        dotPaint.strokeWidth = 1f
        dotPaint.style = Paint.Style.FILL_AND_STROKE
        dotPaint.color = context.resources.getColor(R.color.colorPrimary)

        imaginaryLinePaint = Paint()
        imaginaryLinePaint.isAntiAlias = true
        imaginaryLinePaint.strokeWidth = 1f
        imaginaryLinePaint.style = Paint.Style.FILL_AND_STROKE
        imaginaryLinePaint.color = context.resources.getColor(R.color.colorPrimary)

        shaderPaint = Paint()
        shaderPaint.isAntiAlias = true
        shaderPaint.strokeWidth = 1f
        shaderPaint.style = Paint.Style.FILL_AND_STROKE
        shaderPaint.color = context.resources.getColor(R.color.colorPrimary)


        floatTextPaint = Paint()
        floatTextPaint.isAntiAlias = true
        floatTextPaint.strokeWidth = 1f
        floatTextPaint.style = Paint.Style.FILL_AND_STROKE
        floatTextPaint.strokeCap = Paint.Cap.ROUND
        floatTextPaint.color = context.resources.getColor(R.color.colorAccent)
        floatTextPaint.textAlign = Paint.Align.CENTER
        floatTextPaint.textSize = context.sp2px(11).toFloat()

        pathEffect = DashPathEffect(floatArrayOf(5f, 5f), 0f)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val minWidth = suggestedMinimumWidth
        val minHeight = suggestedMinimumHeight

        val width = measureWidth(minWidth, widthMeasureSpec)
        val height = measureHeight(minHeight, heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (dataList.isNotEmpty()) {
            maxData = dataList[0]
            minData = dataList[0]
        }

        for (data in dataList) {
            if (maxData < data) maxData = data
            if (minData > data) minData = data
        }


        canvas?.drawColor(context.resources.getColor(R.color.colorWhite))

        coordinatePaint.pathEffect = null
        //画纵坐标
        canvas?.drawLine(
            paddingLeft + textPaint.textSize + textMargin,
            paddingTop + textPaint.textSize + textMargin,
            paddingLeft + textPaint.textSize + textMargin,
            height - paddingBottom - paddingTop - weekPaint.textSize - textMargin,
            coordinatePaint
        )

        //画坐标虚线
        coordinatePaint.pathEffect = pathEffect
        val path = Path()
        path.reset()
        val verticalSpace =
            (height - paddingBottom - paddingTop - textPaint.textSize - weekPaint.textSize - 2 * textMargin) / 10
        for (i in 1..9) {
            val beginX = paddingLeft + textPaint.textSize + textMargin
            val beginY = textPaint.textSize + textMargin + verticalSpace * i
            val endX = width.toFloat() - textMargin
            val endY = textPaint.textSize + textMargin + verticalSpace * i

            path.moveTo(beginX, beginY)
            path.lineTo(endX, endY)
            canvas?.drawPath(path, coordinatePaint)
        }

        textPaint.textAlign = Paint.Align.CENTER
        //画时长
        canvas?.drawText(
            "时长(分)",
            paddingLeft + textPaint.textSize + textMargin,
            paddingTop + textPaint.textSize / 2 + textMargin,
            textPaint
        )

        textPaint.textAlign = Paint.Align.RIGHT

        //数值差值区间 (最大减最小)
        val differenceValue = maxData - minData

        for (i in 0..10) {
            canvas?.drawText(
                "" + ((minData + i * differenceValue / 10).toInt()),
                paddingLeft.toFloat() + textPaint.textSize,
                textPaint.textSize + textMargin + verticalSpace * (10 - i) + textPaint.textSize / 2,
                textPaint
            )
        }

        coordinatePaint.pathEffect = null
        //画横坐标
        canvas?.drawLine(
            paddingLeft + textPaint.textSize + textMargin,
            textPaint.textSize + textMargin + verticalSpace * (10),
            width.toFloat() - textMargin,
            textPaint.textSize + textMargin + verticalSpace * (10),
            coordinatePaint
        )

        //画圆点
        //画连接虚线
        imaginaryLinePaint.pathEffect = pathEffect
        val imaginaryPath = Path()
        imaginaryPath.reset()

        shaderPaint.shader = LinearGradient(
            paddingLeft + textPaint.textSize + textMargin,
            paddingTop + textPaint.textSize + textMargin,
            paddingLeft + textPaint.textSize + textMargin,
            paddingTop + textPaint.textSize + textMargin + verticalSpace * 10,
            intArrayOf(
                context.resources.getColor(R.color.transparent_70_colorPrimary),
                context.resources.getColor(R.color.transparent_10_colorPrimary)
            ),
            null,
            Shader.TileMode.MIRROR
        )

        val horizontalSpace = (width - textPaint.textSize - textMargin * 2) / (7)

        val shaderPath = Path()
        shaderPath.moveTo(
            horizontalSpace,
            paddingTop + textPaint.textSize + textMargin + verticalSpace * 10
        )


        dataList.forEachWithIndex { index, data ->
            var value = when {
                data > maxData -> maxData
                data < minData -> minData
                else -> data
            }

            val height =
                textPaint.textSize + textMargin + verticalSpace * 10 * (1 - (value - minData) / differenceValue)
            //画圆点
            canvas?.drawCircle(horizontalSpace * (index + 1), height, radius.toFloat(), dotPaint)

            //画数值
            canvas?.drawText(
                DecimalFormat("#0.0").format(data),
                horizontalSpace * (index + 1),
                height - textMargin,
                floatTextPaint
            )

            shaderPath.lineTo(horizontalSpace * (index + 1), height)
            if (index == dataList.size - 1) {
                shaderPath.lineTo(
                    horizontalSpace * (index + 1),
                    paddingTop + textPaint.textSize + textMargin + verticalSpace * 10
                )
                shaderPath.close()
            }

            val x1 = horizontalSpace * (index + 1)
            val y1 = height



            if (index + 1 < dataList.size) {

                val value = when {
                    dataList[index + 1] > maxData -> maxData
                    dataList[index + 1] < minData -> minData
                    else -> dataList[index + 1]
                }
                val x2 = horizontalSpace * (index + 2)
                val y2 =
                    textPaint.textSize + textMargin + verticalSpace * 10 * (1 - (value - minData) / differenceValue)

                imaginaryPath.moveTo(x1, y1)
                imaginaryPath.lineTo(x2, y2)
                canvas?.drawPath(imaginaryPath, imaginaryLinePaint)
            }
        }

        canvas?.drawPath(shaderPath, shaderPaint)

        //画星期
        weekList.forEachWithIndex { index, week ->
            val x = horizontalSpace * (index + 1)
            val y = textPaint.textSize + textMargin + verticalSpace * 10 + textMargin
            canvas?.drawText(week, x, y, weekPaint)
            canvas?.drawText(dateList[index], x, y + weekPaint.textSize, weekPaint)
        }
        //画日期
        dateList.forEachWithIndex { index, date ->
            val x = horizontalSpace * (index + 1)
            val y = textPaint.textSize + textMargin + verticalSpace * 10 + textMargin
            canvas?.drawText(date, x, y + weekPaint.textSize, weekPaint)
        }
    }

    /**
     * 测量宽度
     */
    private fun measureWidth(minWidth: Int, measureSpec: Int): Int {
        var defaultWidth = 0
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        when (specMode) {
            MeasureSpec.AT_MOST -> {
                defaultWidth = DEFAULT_WIDTH + paddingLeft + paddingRight
            }
            MeasureSpec.EXACTLY -> {
                defaultWidth = specSize
            }
            MeasureSpec.UNSPECIFIED -> {
                defaultWidth = Math.max(minWidth, specSize)
            }
        }
        return defaultWidth
    }


    /**
     * 测量高度
     */
    private fun measureHeight(minHeight: Int, measureSpec: Int): Int {
        var defaultHeight = 0
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        when (specMode) {
            MeasureSpec.AT_MOST -> {
                defaultHeight = DEFAULT_HEIGHT + paddingTop + paddingBottom
            }
            MeasureSpec.EXACTLY -> {
                defaultHeight = specSize
            }
            MeasureSpec.UNSPECIFIED -> {
                defaultHeight = Math.max(minHeight, specSize)
            }
        }
        return defaultHeight
    }

}