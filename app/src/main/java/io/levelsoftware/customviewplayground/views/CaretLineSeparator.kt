package io.levelsoftware.customviewplayground.views

import android.content.Context
import android.graphics.*
import android.support.v4.math.MathUtils
import android.util.AttributeSet
import android.view.View
import io.levelsoftware.customviewplayground.R

class CaretLineSeparator @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()

    private var lineBounds = RectF()
    private var peakOffset = 0

    var peakPercentage = 0.5f
        set(value) {
            field = MathUtils.clamp(value, 0f, 1f)
            invalidate()
        }
    var caretAngle = 45
        set(value) {
            field = MathUtils.clamp(value, 30, 60)
            invalidate()
        }

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CaretLineSeparator)
        peakPercentage = ta.getFloat(R.styleable.CaretLineSeparator_clsPeakPercentage, peakPercentage)
        caretAngle = ta.getInt(R.styleable.CaretLineSeparator_clsCaretAngle, caretAngle)
        peakOffset = ta.getDimensionPixelSize(R.styleable.CaretLineSeparator_clsPeakOffset, peakOffset)
        paint.strokeWidth = ta.getDimension(R.styleable.CaretLineSeparator_clsLineThickness, 1f)
        paint.color = ta.getColor(R.styleable.CaretLineSeparator_clsLineColor, Color.BLACK)
        ta.recycle()

        paint.style = Paint.Style.STROKE
        lineBounds.top = paint.strokeWidth
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if (changed) {
            lineBounds.bottom = height.toFloat() - paint.strokeWidth / 2
            lineBounds.right = width.toFloat()
        }
    }

    override fun onDraw(canvas: Canvas) {
        val (left, top, right, bottom) = lineBounds
        val peakX = (width - peakOffset * 2) * peakPercentage + peakOffset
        val peakHalfWidth = ((bottom - top) / Math.tan(Math.toRadians(caretAngle.toDouble()))).toFloat()

        path.reset()
        path.moveTo(left, bottom)
        path.lineTo(peakX - peakHalfWidth, bottom)
        path.lineTo(peakX, top)
        path.lineTo(peakX + peakHalfWidth, bottom)
        path.lineTo(right, bottom)
        canvas.drawPath(path, paint)
    }

    private operator fun RectF.component1() = left
    private operator fun RectF.component2() = top
    private operator fun RectF.component3() = right
    private operator fun RectF.component4() = bottom
}