package com.github.kongpf8848.Mu5ViewPager;

import android.widget.ImageView;


interface Mu5Interface {

    fun onPageSelected(position: Int)
    fun onClick(url: String, position: Int)
    fun onLoadImage(imageView: ImageView, url: String, position: Int)
}
