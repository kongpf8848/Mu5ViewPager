package com.github.kongpf8848.Mu5ViewPager

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import java.util.*


class Mu5ViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs) {

    private val TAG = this.javaClass.simpleName

    protected var fixedSpeed: Int = 0//重设滚动的速度
    protected var sourceHeights: Array<Int>? = null//每张图片的高度
    private var defaultHeight: Float = 0f
    private var maxHeight=0
    protected var mu5Interface: Mu5Interface? = null
    protected var mu5PagerAdapter: Mu5PagerAdapter? = null


    protected var pageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            if (position == sourceHeights!!.size - 1 || defaultHeight == 0f) {//用于defaultHeight初始化之前的拦截,即等到获取到第一张图片再做操作
                return
            }
            //计算ViewPager即将变化到的高度
            var height: Float = (if (sourceHeights!![position] == 0) defaultHeight else sourceHeights!![position].toFloat()) * (1 - positionOffset) + (if (sourceHeights!![position + 1] == 0) defaultHeight else sourceHeights!![position + 1].toFloat()) * positionOffset

            //为ViewPager设置高度
            val params = this@Mu5ViewPager.layoutParams
            params.height = height.toInt()
            this@Mu5ViewPager.layoutParams = params

        }

        override fun onPageSelected(position: Int) {
            if (mu5Interface != null) {
                mu5Interface!!.onPageSelected(position)
            }
        }

        override fun onPageScrollStateChanged(state: Int) {

        }
    }


    init {
        if (fixedSpeed > 0) {
            setScrollerSpeed(fixedSpeed)
        }
        addOnPageChangeListener(pageChangeListener)

    }

    fun setMaxHeight(height:Int){
        maxHeight=height
    }
    /**
     * 设置图片的网络链接，回调接口（注意：这个必须实现的）
     *
     * @param urls
     * @param mu5Interface
     */
    fun setData(urls: List<String>?, mu5Interface: Mu5Interface) {
        if (urls == null || urls.size == 0) {
            throw RuntimeException("error:don't give a empty source to me!")
        }
        this.mu5Interface = mu5Interface
        sourceHeights = Array(urls.size) { 0 }
        mu5PagerAdapter = Mu5PagerAdapter(context, urls, mu5Interface)
        this.adapter = mu5PagerAdapter
    }

    fun setData(urls: Array<String>, mu5Interface: Mu5Interface) {
        setData(Arrays.asList(*urls), mu5Interface)
    }


    fun bindSource(loadedImage: Bitmap?, position: Int, imageView: ImageView) {
        if (loadedImage != null) {
            val scale = loadedImage.height.toFloat() / loadedImage.width
            var height = (scale * Utils.getDisplayWidth(context)).toInt()
            if(maxHeight>0 && height>maxHeight){
                height=maxHeight
            }
            setSourceHeights(height, position)
            imageView.setImageBitmap(loadedImage)
        } else {
            Toast.makeText(context, "error:picture ${position} is empty", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * 设置加载出的图片的高度参数
     * 设计目的：主要便于用户使用自己的加载框架
     *
     * @param height
     * @param position
     */
    private fun setSourceHeights(height: Int, position: Int) {

        if (height < 0) {
            throw RuntimeException("error:i got a wrong height:$height")
        }
        if (sourceHeights == null || sourceHeights!!.size == 0 || sourceHeights!!.size <= position) {
            throw RuntimeException("error:i don't have so much more index")
        }
        sourceHeights!![position] = height
        if (position == 0 && defaultHeight == 0f) {//初始化默认高度
            defaultHeight = height.toFloat()
            val params = this@Mu5ViewPager.layoutParams
            params.height = height
            this@Mu5ViewPager.layoutParams = params
        }
    }


    fun setScrollerSpeed(speed: Int) {
        try {
            val mScroller = ViewPager::class.java.getDeclaredField("mScroller")
            mScroller.isAccessible = true
            val scroller = FixedSpeedScroller(context, null!!, speed)
            mScroller.set(this, scroller)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
