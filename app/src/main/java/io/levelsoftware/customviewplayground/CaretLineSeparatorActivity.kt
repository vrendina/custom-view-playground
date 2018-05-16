package io.levelsoftware.customviewplayground

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import io.levelsoftware.customviewplayground.R.id.caretLine
import io.levelsoftware.customviewplayground.R.id.positionValue
import kotlinx.android.synthetic.main.activity_caret_line_separator.*

class CaretLineSeparatorActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caret_line_separator)

        positionSeekBar.setOnSeekBarChangeListener(this)
        updatePosition(caretLine.peakPercentage)

        angleSeekBar.setOnSeekBarChangeListener(this)
        angleSeekBar.progress = (100 * (caretLine.caretAngle - 30) / 30)
        angleValue.text = caretLine.caretAngle.toString()

        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            when (seekBar.id) {
                R.id.positionSeekBar -> updatePosition(progress / seekBar.max.toFloat())
                R.id.angleSeekBar -> {
                    caretLine.caretAngle = (30 * progress / seekBar.max.toFloat() + 30).toInt()
                    angleValue.text = caretLine.caretAngle.toString()
                }
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button1 -> updatePosition(0.125f)
            R.id.button2 -> updatePosition(0.375f)
            R.id.button3 -> updatePosition(0.625f)
            R.id.button4 -> updatePosition(0.875f)
        }
    }

    private fun updatePosition(percentage: Float) {
        caretLine.peakPercentage = percentage
        positionValue.text = caretLine.peakPercentage.toString()
        positionSeekBar.progress = (100 * caretLine.peakPercentage).toInt()
    }
}
