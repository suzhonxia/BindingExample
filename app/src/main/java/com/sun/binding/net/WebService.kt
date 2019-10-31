package com.sun.binding.net

import com.sun.binding.entity.HomeEntity
import com.sun.binding.entity.UserInfoEntity
import kotlinx.coroutines.Deferred
import retrofit2.http.POST

/**
 * 网络请求接口配置
 */
interface WebService {
    /**
     * 获取用户信息
     */
    @POST(UrlDefinition.API_USER_INFO)
    suspend fun getUserInfo(): NetResult<UserInfoEntity>

    @POST(UrlDefinition.API_HOME_DATA)
    suspend fun getHomeData(): NetResult<HomeEntity>
}