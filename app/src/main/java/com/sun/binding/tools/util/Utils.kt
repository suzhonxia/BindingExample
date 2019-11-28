package com.sun.binding.tools.util

import java.math.BigDecimal

object Utils {

    @JvmStatic
    fun isEmptyList(list: List<*>?): Boolean {
        if (list == null) return false
        return list.isEmpty()
    }

    @JvmStatic
    fun div(v1: Double, v2: Double, scale: Int): Double {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }
}