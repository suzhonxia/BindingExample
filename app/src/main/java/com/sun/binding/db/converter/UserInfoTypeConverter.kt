package com.sun.binding.db.converter

import androidx.room.TypeConverter
import com.blankj.utilcode.util.GsonUtils
import com.sun.binding.entity.UserAwardEntity
import com.sun.binding.entity.UserMenuEntity
import com.sun.binding.entity.UserShopEntity
import com.sun.binding.entity.UserShopNavEntity

class UserInfoTypeConverter {

    @TypeConverter
    fun shopToString(shop: UserShopEntity): String = GsonUtils.toJson(shop)

    @TypeConverter
    fun stringToShop(json: String): UserShopEntity = GsonUtils.fromJson(json, UserShopEntity::class.java)

    @TypeConverter
    fun shopNavToString(shopNav: List<UserShopNavEntity>): String = GsonUtils.toJson(shopNav)

    @TypeConverter
    fun stringToShopNav(json: String): List<UserShopNavEntity> = GsonUtils.fromJson(json, GsonUtils.getListType(UserShopNavEntity::class.java))

    @TypeConverter
    fun awardToString(award: UserAwardEntity): String = GsonUtils.toJson(award)

    @TypeConverter
    fun stringToAward(json: String): UserAwardEntity = GsonUtils.fromJson(json, UserAwardEntity::class.java)

    @TypeConverter
    fun menuToString(menu: List<UserMenuEntity>): String = GsonUtils.toJson(menu)

    @TypeConverter
    fun stringToMenu(json: String): List<UserMenuEntity> = GsonUtils.fromJson(json, GsonUtils.getListType(UserMenuEntity::class.java))
}