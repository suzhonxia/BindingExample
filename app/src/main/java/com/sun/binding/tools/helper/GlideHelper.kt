package com.sun.binding.tools.helper

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sun.binding.tools.ext.orFalse

/**
 * Glide 封装使用
 */
object GlideHelper {

    fun loadImage(iv: ImageView, imgUrl: String? = null, placeholder: Drawable? = null, default: Drawable? = null, circle: Boolean? = null, corner: Int? = null) {
        if (imgUrl.isNullOrBlank()) {
            if (null != default) {
                iv.setImageDrawable(default)
            }
            return
        }
        val options = RequestOptions().apply {
            if (null != placeholder) {
                placeholder(placeholder)
            }
            if (null != default) {
                error(default)
            }
            if (circle.orFalse()) {
                circleCrop()
            }
            if (corner != null) {
                transform(RoundedCorners(SizeUtils.dp2px(corner.toFloat())))
            }
        }
        Glide.with(iv.context)
            .load(imgUrl)
            .apply(options)
            .into(iv)
    }
}