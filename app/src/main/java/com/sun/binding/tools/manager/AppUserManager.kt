package com.sun.binding.tools.manager

import com.sun.binding.constants.SP_KEY_TOKEN
import com.sun.binding.constants.SP_KEY_USER_INFO
import com.sun.binding.entity.UserInfoEntity
import com.sun.binding.tools.ext.toEntity
import com.sun.binding.tools.ext.toJson
import com.sun.binding.tools.helper.MMKVHelper

/**
 * 应用程序用户相关管理类
 */
@Suppress("unused")
object AppUserManager {

    @JvmStatic
    fun saveUserData(entity: UserInfoEntity?) {
        saveUserInfo(entity)
        saveToken(entity?.token)
    }

    @JvmStatic
    fun getUserInfo(): UserInfoEntity? = MMKVHelper.getObject(SP_KEY_USER_INFO, UserInfoEntity::class.java)

    @JvmStatic
    fun saveUserInfo(entity: UserInfoEntity?) = MMKVHelper.saveObject(SP_KEY_USER_INFO, entity ?: "")

    @JvmStatic
    fun getToken(): String? = MMKVHelper.getString(SP_KEY_TOKEN, "")

    @JvmStatic
    fun saveToken(token: String?) = MMKVHelper.saveString(SP_KEY_TOKEN, token ?: "")

    @JvmStatic
    fun hasLogin(): Boolean = getUserInfo() != null && !getToken().isNullOrEmpty()
}