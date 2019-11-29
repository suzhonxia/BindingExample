package com.sun.binding.tools.manager

import com.blankj.utilcode.util.GsonUtils
import com.sun.binding.constants.SP_KEY_LOCATION
import com.sun.binding.constants.SP_KEY_TOKEN
import com.sun.binding.db.BindingDatabase
import com.sun.binding.entity.LocationEntity
import com.sun.binding.entity.UserInfoEntity
import com.sun.binding.tools.helper.MMKVHelper

/**
 * 应用程序用户相关管理类
 */
@Suppress("unused")
object AppUserManager {

    @JvmStatic
    fun saveUserData(entity: UserInfoEntity) {
        saveUserInfo(entity)
        saveToken(entity.token)
    }

    @JvmStatic
    fun clearUserData() {
        saveToken("")
    }

    @JvmStatic
    fun hasLogin(): Boolean = getUserInfo() != null && !getToken().isNullOrEmpty()

    @JvmStatic
    fun getUserInfo(): UserInfoEntity? = BindingDatabase.getInstance().getUserInfoDao().getUserInfo()

    @JvmStatic
    fun saveUserInfo(entity: UserInfoEntity) = BindingDatabase.getInstance().getUserInfoDao().insertUserInfo(entity)

    @JvmStatic
    fun clearUserInfo() = BindingDatabase.getInstance().getUserInfoDao().deleteUserInfo()

    @JvmStatic
    fun getToken(): String? = MMKVHelper.getString(SP_KEY_TOKEN, "")

    @JvmStatic
    fun saveToken(token: String?) = MMKVHelper.saveString(SP_KEY_TOKEN, token ?: "")

    @JvmStatic
    fun getLocation(): LocationEntity? {
        return MMKVHelper.getString(SP_KEY_LOCATION, "").run {
            if (isNotEmpty()) {
                return GsonUtils.fromJson(this, LocationEntity::class.java)
            } else {
                null
            }
        }
    }

    @JvmStatic
    fun saveLocation(location: LocationEntity) = MMKVHelper.saveString(SP_KEY_LOCATION, GsonUtils.toJson(location))
}