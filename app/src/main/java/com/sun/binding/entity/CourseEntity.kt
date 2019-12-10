package com.sun.binding.entity

data class CourseEntity(
    val id: Int,
    val image: String,
    val name: String,
    val part_num: Int,
    val readnum: String,
    val createtime: String
)

data class CourseConfigEntity(
    val hasPart: Boolean = true,
    val singleLine: Boolean = true
)