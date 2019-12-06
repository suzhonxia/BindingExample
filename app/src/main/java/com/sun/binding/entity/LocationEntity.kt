package com.sun.binding.entity

data class LocationEntity(
    val latitude: Double,
    val longitude: Double
)

fun LocationEntity?.validLocation() = this != null && (latitude != 0.0 || longitude != 0.0)