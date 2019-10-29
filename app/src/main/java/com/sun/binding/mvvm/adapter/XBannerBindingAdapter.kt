package com.sun.binding.mvvm.adapter

import androidx.databinding.BindingAdapter
import com.stx.xhb.xbanner.XBanner

@BindingAdapter("android:bind_banner_showing")
fun setAutoPlay(banner: XBanner, showing: Boolean?) {
    if (showing == null) return
    if (showing) {
        banner.startAutoPlay()
    } else {
        banner.stopAutoPlay()
    }
}