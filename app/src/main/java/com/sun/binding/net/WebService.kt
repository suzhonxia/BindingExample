package com.sun.binding.net

import com.sun.binding.entity.*
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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

    @POST(UrlDefinition.API_HOME_INDEX)
    suspend fun getHomeIndex(): NetResult<HomeEntity>

    @POST(UrlDefinition.API_COURSE_INDEX)
    suspend fun getEducIndex(): NetResult<List<EducEntity>>

    @FormUrlEncoded
    @POST(UrlDefinition.API_CIRCLE_PRODUCT)
    suspend fun getCircleProductData(
        @Field("type") type: Int,
        @Field("page") page: Int,
        @Field("lat") lat: Double = 0.0,
        @Field("lng") lng: Double = 0.0
    ): NetResult<CircleEntity>

    @POST(UrlDefinition.API_SUIT_COURSE)
    suspend fun getSuitCourseData(): NetResult<List<SuitCourseEntity>>
}