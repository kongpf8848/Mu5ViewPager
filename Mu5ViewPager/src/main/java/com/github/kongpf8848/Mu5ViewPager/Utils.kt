package com.github.kongpf8848.Mu5ViewPager

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager



object Utils {

    fun getDisplayWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    fun getDisplayHeight(context: Context): Int {
       return context.resources.displayMetrics.heightPixels
    }

    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}
