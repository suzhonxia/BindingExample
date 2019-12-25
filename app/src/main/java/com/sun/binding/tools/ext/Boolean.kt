@file:JvmName("BooleanExt")

package com.sun.binding.tools.ext

/**
 * 如果为 null 则为 false
 */
fun Boolean?.orFalse(): Boolean {
    return this ?: false
}

/**
 * 如果为 null 则为 true
 */
fun Boolean?.orTrue(): Boolean {
    return this ?: true
}

/**
 * 判断条件节点
 */
val Boolean?.condition: Boolean
    get() = this == true

/**
 * 如果 true 返回 r1
 * 如果 false 返回 r2
 */
fun <T> Boolean?.estimate(r1: T, r2: T) = if (this.condition) r1 else r2