package com.sun.binding.tools.helper

import com.sun.binding.tools.ext.toEntity
import com.tencent.mmkv.MMKV

/**
 * MMKV 使用封装
 */
object MMKVHelper {

    fun getInt(key: String, default: Int = 0): Int = MMKV.defaultMMKV().decodeInt(key, default)

    fun getString(key: String, default: String = ""): String = MMKV.defaultMMKV().decodeString(key, default).toString()

    fun getBoolean(key: String, default: Boolean = false): Boolean = MMKV.defaultMMKV().decodeBool(key, default)

    fun saveInt(key: String, value: Int) = MMKV.defaultMMKV().putInt(key, value).commit()

    fun saveString(key: String, value: String) = MMKV.defaultMMKV().putString(key, value).commit()

    fun saveBoolean(key: String, value: Boolean) = MMKV.defaultMMKV().putBoolean(key, value).commit()

    fun <T> getObject(key: String, clz: Class<T>): T? = MMKV.defaultMMKV().decodeString(key, "").toEntity(clz)
}