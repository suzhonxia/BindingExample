package com.sun.binding.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 用户信息数据
 */
@Entity(tableName = "userInfo")
data class UserInfoEntity
/**
 * 构造方法
 *
 * @param user_id 用户id
 * @param token Token
 * @param nickname 昵称
 * @param mobile 手机号
 * @param avatar 头像
 * @param gender 性别：1：男、2：女
 * @param focus_num 关注数
 * @param fans_num 粉丝数
 * @param shop 用户商城数据
 * @param award 用户奖励金数据
 * @param menu_list 扩展菜单列表
 */
constructor(
    @PrimaryKey
    @ColumnInfo(name = "userId")
    var user_id: String = "",
    var token: String = "",
    var nickname: String = "",
    var mobile: String = "",
    var avatar: String = "",
    var gender: Int = 0,
    var focus_num: String = "",
    var fans_num: String = "",
    var shop: UserShopEntity,
    var award: UserAwardEntity,
    var menu_list: List<UserMenuEntity> = arrayListOf()
)

/**
 * 用户商城数据
 */
data class UserShopEntity
/**
 * 构造方法
 *
 * @param isShow 是否需要显示 0：不显示、1：显示
 * @param url 跳转链接
 * @param nav 子类菜单项
 */
constructor(
    var isShow: Int = 0,
    var url: String = "",
    var nav: List<UserShopNavEntity> = arrayListOf()
)

/**
 * 用户商城数据 子类菜单项
 */
data class UserShopNavEntity
constructor(
    var txt: String = "",
    var num: String = "",
    var unit: String = "",
    var url: String = ""
)

/**
 * 用户奖励金数据
 */
data class UserAwardEntity
constructor(
    var amt: String = "",
    var url: String = "",
    var wxappid: String = ""
)

/**
 * 扩展菜单列表
 */
data class UserMenuEntity
constructor(
    var is_show: Int = 0,
    var title: String = "",
    var url: String = "",
    var icon: String = "",
    var tips: String = ""
) {
    fun shouldShow() = is_show == 1
}