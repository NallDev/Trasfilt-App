package com.example.trafilt.utility

import android.view.Window
import androidx.core.view.WindowInsetsControllerCompat

fun lightStatusBar(window: Window, isLight : Boolean = true){
    val wic = WindowInsetsControllerCompat(window, window.decorView)
    wic.isAppearanceLightStatusBars = isLight
}