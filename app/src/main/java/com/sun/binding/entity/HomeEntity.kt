package com.sun.binding.entity

import android.graphics.Color
import android.text.SpannableStringBuilder
import com.blankj.utilcode.util.SpanUtils
import com.stx.xhb.xbanner.entity.SimpleBannerInfo

data class HomeEntity(
    val banner: List<BannerEntity>?,
    val category: List<CategoryEntity>?,
    val gao: List<GaoEntity>?,
    val newcourse: NewcourseEntity?,
    val news: List<NewEntity>?,
    val tao: TaoEntity?
)

data class BannerEntity(
    val image: String?,
    val link: String?,
    val title: String?
) : SimpleBannerInfo() {
    override fun getXBannerUrl(): Any = image!!
}

data class CategoryEntity(
    val id: Int?,
    val image: String?,
    val name: String?
)

data class GaoEntity(
    val id: Int?,
    val keyword: String?,
    val link: String?,
    val title: String?
) {
    fun getNoticeText(): SpannableStringBuilder {
        if (title.isNullOrBlank()) return SpanUtils().append("").create()
        if (keyword.isNullOrEmpty() || !title.contains(keyword)) return SpanUtils().append(title).create()

        val indexOf = title.indexOf(keyword)
        return SpanUtils()
            .append(title.substring(0, indexOf))
            .append(keyword).setForegroundColor(Color.parseColor("#FC650D"))
            .append(title.substring(indexOf + keyword.length))
            .create()
    }
}

data class NewcourseEntity(
    val type: Int,
    val cNum: Int,
    val okNum: Int,
    val allNum: Int
)

data class NewEntity(
    val createtime: String?,
    val id: Int?,
    val image: String?,
    val title: String?,
    val url: String?,
    val views: Int?
)

data class TaoEntity(
    val image: String?,
    val name: String?
)