package top.carljay.book.widget

import android.content.Context
import android.graphics.Canvas
import android.support.v7.widget.AppCompatTextView
import android.text.Layout
import android.text.StaticLayout
import android.util.AttributeSet
import com.carljay.cjlibrary.helper.UiHelper

/**
 * Created by carljay on 2018/2/9.
 */
open class BookContentView (context: Context, attrs: AttributeSet? = null) : AppCompatTextView(context,attrs){

    init {
        textSize = 16f
        setLineSpacing(UiHelper.spToPx(14),1f)
    }
    override fun onDraw(canvas: Canvas?) {
        val layout = StaticLayout(text, paint, canvas!!.width, Layout.Alignment.ALIGN_NORMAL, lineSpacingMultiplier, lineSpacingExtra, false)
        layout.draw(canvas)
    }
    fun getLineCount1() : Int{
        val ascent = paint.ascent()
        val descent = paint.descent()
        val textHeight = descent - ascent
        return ((height * 1.0f - lineSpacingExtra) / (textHeight + lineSpacingExtra)).toInt()
    }
    fun getLineArray(s : String) : ArrayList<String>{
        val mPaint = paint
        mPaint.isSubpixelText = true
        val tempLayout = StaticLayout(s, mPaint, width, Layout.Alignment.ALIGN_NORMAL, lineSpacingMultiplier, lineSpacingExtra, false)
        val linesdata = ArrayList<String>()
        (0 until tempLayout.lineCount).mapTo(linesdata) { s.substring(tempLayout.getLineStart(it), tempLayout.getLineEnd(it)) }
        return linesdata
    }
}