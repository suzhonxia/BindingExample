package com.sun.binding.mvvm.adapter

import androidx.databinding.BindingAdapter
import com.sun.binding.widget.MarqueeView

@BindingAdapter("android:bind_marquee_showing")
fun set(marqueeView: MarqueeView, showing: Boolean?) {
    if (showing == null) return
    if (showing) {
        marqueeView.startFlipping()
    } else {
        marqueeView.stopFlipping()
    }
}