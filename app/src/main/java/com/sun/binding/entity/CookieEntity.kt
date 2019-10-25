package com.sun.binding.entity

import okhttp3.Cookie

/**
 * Cookie 数据实体类
 *
 * * 创建时间：2019/10/15
 *
 * @author 王杰
 */
data class CookieEntity
/**
 * @param cookies Cookie 列表
 */
constructor(
        var cookies: ArrayList<Cookie>? = arrayListOf()
)