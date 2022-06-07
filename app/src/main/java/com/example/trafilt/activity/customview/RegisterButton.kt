package com.example.trafilt.activity.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.trafilt.R

class RegisterButton : AppCompatButton {
    private lateinit var enabledBackground: Drawable
    private lateinit var disabledBackground: Drawable

    private var txtColor: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = if(isEnabled) enabledBackground else disabledBackground

        setTextColor(txtColor)
    }

    private fun init(){
        txtColor = ContextCompat.getColor(context, R.color.white)
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.btn_register_enable) as Drawable
        disabledBackground = ContextCompat.getDrawable(context, R.drawable.btn_register_disable) as Drawable
    }
}