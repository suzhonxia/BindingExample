@file:Suppress("unused")

package com.sun.binding.mvvm.adapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sun.binding.constants.IMG_DRAWABLE_MARK
import com.sun.binding.constants.IMG_MIPMAP_MARK
import com.sun.binding.constants.IMG_RESOURCE_MARK
import com.sun.binding.tools.ext.orFalse
import com.sun.binding.tools.tool.getIdentifier
import java.io.File

/**
 * ImageView DataBinding 适配器
 *
 * @author 王杰
 */

/**
 * 加载图片
 *
 * @param iv     [ImageView] 对象
 * @param imgUrl 图片 Url
 * @param placeholder 占位图片
 * @param default 默认图片
 * @param circle 圆形图片
 * @param corner 圆角大小(DP)
 */
@BindingAdapter(
    "android:bind_iv_img_url",
    "android:bind_iv_img_placeholder",
    "android:bind_iv_img_default",
    "android:bind_iv_img_circle",
    "android:bind_iv_img_corner",
    requireAll = false
)
fun setImageViewUrl(
    iv: ImageView,
    imgUrl: String?,
    placeholder: Drawable?,
    default: Drawable?,
    circle: Boolean?,
    corner: Int?
) {
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

/**
 * 加载图片
 *
 * @param iv     [ImageView] 对象
 * @param path 图片地址
 * @param placeholder 占位图片
 * @param default 默认图片
 * @param circle 圆形图片
 */
@BindingAdapter(
    "android:bind_iv_img_path",
    "android:bind_iv_img_placeholder",
    "android:bind_iv_img_default",
    "android:bind_iv_img_circle",
    "android:bind_iv_img_corner",
    requireAll = false
)
fun setImageViewPath(
    iv: ImageView,
    path: String?,
    placeholder: Drawable?,
    default: Drawable?,
    circle: Boolean?,
    corner: Int?
) {
    if (path.isNullOrBlank()) {
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
    if (path.isNullOrBlank()) {
        Glide.with(iv.context)
            .load(default)
    } else {
        Glide.with(iv.context)
            .load(File(path))
    }
        .apply(options)
        .into(iv)
}

/**
 * 加载图片
 *
 * @param iv     [ImageView] 对象
 * @param img 图片路径
 * @param placeholder 占位图片
 * @param default 默认图片
 * @param circle 圆形图片
 */
@BindingAdapter(
    "android:bind_iv_img",
    "android:bind_iv_img_placeholder",
    "android:bind_iv_img_default",
    "android:bind_iv_img_circle",
    "android:bind_iv_img_corner",
    requireAll = false
)
fun setImageViewImg(iv: ImageView, img: String?, placeholder: Drawable?, default: Drawable?, circle: Boolean?, corner: Int?) {
    if (img.isNullOrBlank()) {
        if (null != default) {
            iv.setImageDrawable(default)
        }
        return
    }
    if (img.contains("http:") || img.contains("https:")) {
        // url
        setImageViewUrl(iv, img, placeholder, default, circle, corner)
    } else if (img.contains(IMG_RESOURCE_MARK)) {
        // Resource
        setImageResource(iv, img)
    } else {
        // path
        setImageViewPath(iv, img, placeholder, default, circle, corner)
    }
}

/**
 * 根据资源 id 加载图片
 *
 * @param iv    [ImageView] 对象
 * @param resId 图片资源 id
 */
@BindingAdapter("android:bind_iv_src")
fun src(iv: ImageView, @DrawableRes resId: Int?) {
    if (null == resId) {
        return
    }
    if (0 != resId) {
        iv.setImageResource(resId)
    }
}

/**
 * 根据 id 字符串加载图片
 *
 * @param iv [ImageView] 对象
 * @param res 资源字符串 drawable-resource:resId 或 mipmap-resource:resId
 */
@BindingAdapter("android:bind_iv_src")
fun setImageResource(iv: ImageView, res: String?) {
    if (res.isNullOrBlank()) {
        return
    }
    if (res.startsWith(IMG_DRAWABLE_MARK)) {
        val realRes = res.split(":")[1]
        iv.setImageResource(realRes.getIdentifier(iv.context, "drawable"))
    } else if (res.startsWith(IMG_MIPMAP_MARK)) {
        val realRes = res.split(":")[1]
        iv.setImageResource(realRes.getIdentifier(iv.context, "mipmap"))
    }
}