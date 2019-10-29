package com.sun.binding.tools.util

object Utils {

    @JvmStatic
    fun isEmptyList(list: List<*>?): Boolean {
        if (list == null) return false
        return list.isEmpty()
    }
}