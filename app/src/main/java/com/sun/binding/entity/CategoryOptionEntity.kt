package com.sun.binding.entity

data class CategoryOptionEntity(
    val order: MutableList<CategoryOrderEntity> = mutableListOf(),
    val select: MutableList<CategorySelectEntity> = mutableListOf()
) {

    fun getOrderList() = order.map { it.toOptionEntity() }.toMutableList()

    fun getCategoryList() = select.map { it.toOptionEntity() }.toMutableList()

    fun getFilterList(normalCategory: OptionEntity?) =
        (select.firstOrNull { normalCategory?.id == it.cid } ?: select[0]).select.map { it.toFilterEntity() }.toMutableList()
}

data class CategoryOrderEntity(
    val k: String = "",
    val v: String = ""
) {
    fun toOptionEntity(): OptionEntity = OptionEntity(0, v, k)
}

data class CategorySelectEntity(
    val cid: Int = 0,
    val title: String = "",
    val select: MutableList<SelectEntity> = mutableListOf()
) {
    fun toOptionEntity(): OptionEntity = OptionEntity(cid, "", title)
}

data class SelectEntity(
    val flag: String = "",
    val title: String = "",
    val list: MutableList<SelectListEntity> = mutableListOf()
) {
    fun toFilterEntity(): FilterEntity = FilterEntity(flag, title, list.map { it.toOptionEntity() }.toMutableList())
}

data class SelectListEntity(
    val id: Int = 0,
    val title: String = ""
) {
    fun toOptionEntity(): OptionEntity = OptionEntity(id, "", title)
}

data class FilterEntity(
    val flag: String = "",
    val title: String = "",
    val optionList: MutableList<OptionEntity> = mutableListOf()
)