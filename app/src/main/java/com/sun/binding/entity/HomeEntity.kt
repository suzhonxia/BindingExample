package com.sun.binding.entity

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
)

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
)

data class NewcourseEntity(
    val type: Int?
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