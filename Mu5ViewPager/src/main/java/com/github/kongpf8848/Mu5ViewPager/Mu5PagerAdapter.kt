package com.github.kongpf8848.Mu5ViewPager

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter


class Mu5PagerAdapter : PagerAdapter {

    private var mContext: Context? = null
    private var urls: List<String>? = null
    private var mu5Interface: Mu5Interface? = null

    constructor(mContext: Context, urls: List<String>) {
        this.mContext = mContext
        this.urls = urls
    }

    constructor(mContext: Context, urls: List<String>, mu5Interface: Mu5Interface) {
        this.mContext = mContext
        this.urls = urls
        this.mu5Interface = mu5Interface
    }

    fun setMu5Interface(mu5Interface: Mu5Interface) {
        this.mu5Interface = mu5Interface
    }

    fun setUrls(urls: List<String>) {
        this.urls = urls
    }

    override fun getCount(): Int {
        return urls?.size ?: 0
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(mContext)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        mu5Interface?.onLoadImage(imageView, urls!![position], position)

        container.addView(imageView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        imageView.setOnClickListener {
            mu5Interface?.onClick(urls!![position], position)
        }
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }


}
