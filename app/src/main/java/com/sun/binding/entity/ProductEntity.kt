package com.sun.binding.entity

data class ProductEntity(
    val id: String = "",
    val user_id: String = "",
    val newcourse_id: String = "",
    val videofile: String = "",
    val photos: String = "",
    val intro: String = "",
    val share: ShareEntity = ShareEntity(),
    val favour: String = "",
    val comment: Int = 0,
    val is_original: Int = 0,
    val createtime: String = "",
    val nickname: String = "",
    val avatar: String = "",
    val focusStatus: Int = 0,
    val focus: Int = 0,
    val favourType: Int = 0,
    val img_info: List<Int> = listOf(),
    val images: ProductImages = ProductImages(),
    val video_img: String = "",
    val distance: String = ""
) {

}

data class ProductImages(
    val count: Int = 0,
    val list: List<String> = listOf(),
    val imageSize: List<Int> = listOf()
)

