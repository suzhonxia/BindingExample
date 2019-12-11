package com.sun.binding.entity

data class EducOptionEntity(
    val sort: MutableList<OptionEntity> = mutableListOf(),
    val mukuai: MutableList<OptionEntity> = mutableListOf(),
    val age: MutableList<OptionEntity> = mutableListOf()
)

data class OptionEntity(
    val id: Int = 0,
    val key: String = "",
    val name: String = ""
)