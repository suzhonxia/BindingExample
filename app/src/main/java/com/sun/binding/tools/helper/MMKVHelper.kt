package com.sun.binding.tools.helper

import com.sun.binding.tools.ext.toEntity
import com.sun.binding.tools.ext.toJson
import com.tencent.mmkv.MMKV

/**
 * MMKV 使用封装
 */
object MMKVHelper {

    fun getInt(key: String, default: Int = 0): Int = MMKV.defaultMMKV().getInt(key, default)

    fun saveInt(key: String, value: Int) = MMKV.defaultMMKV().putInt(key, value).commit()

    fun getString(key: String, default: String = ""): String = MMKV.defaultMMKV().getString(key, default).toString()

    fun saveString(key: String, value: String) = MMKV.defaultMMKV().putString(key, value).commit()

    fun getBoolean(key: String, default: Boolean = false): Boolean = MMKV.defaultMMKV().getBoolean(key, default)

    fun saveBoolean(key: String, value: Boolean) = MMKV.defaultMMKV().putBoolean(key, value).commit()

    fun <T> getObject(key: String, clz: Class<T>): T? = MMKV.defaultMMKV().decodeString(key, "").toEntity(clz)

    fun saveObject(key: String, any: Any) = MMKV.defaultMMKV().encode(key, any.toJson())
}