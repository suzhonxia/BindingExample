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

    /**
     * 首页 index
     */
    @POST(UrlDefinition.API_HOME_INDEX)
    suspend fun getHomeIndex(): NetResult<HomeEntity>

    /**
     * 亲职教育 index
     */
    @POST(UrlDefinition.API_COURSE_INDEX)
    suspend fun getEducIndex(): NetResult<List<EducEntity>>

    /**
     * 亲职教育筛选
     */
    @POST(UrlDefinition.API_EDUC_COURSE_OPTION)
    suspend fun getEducCourseOption(): NetResult<EducOptionEntity>

    /**
     * 亲职教育课程列表
     */
    @FormUrlEncoded
    @POST(UrlDefinition.API_EDUC_COURSE_DATA)
    suspend fun getEducCourseData(
        @Field("sort") sortId: Int = 0,
        @Field("mid") categoryId: Int = 0,
        @Field("age") ageId: Int = 0,
        @Field("page") page: Int
    ): NetResult<List<CourseEntity>>

    /**
     * 同学圈列表
     */
    @FormUrlEncoded
    @POST(UrlDefinition.API_CIRCLE_PRODUCT)
    suspend fun getCircleProductData(
        @Field("type") type: Int,
        @Field("page") page: Int,
        @Field("lat") lat: Double = 0.0,
        @Field("lng") lng: Double = 0.0
    ): NetResult<CircleEntity>

    /**
     * 磁力片套装课程列表
     */
    @POST(UrlDefinition.API_SUIT_COURSE)
    suspend fun getSuitCourseData(): NetResult<List<SuitCourseEntity>>

    /**
     * 课程分类筛选
     */
    @POST(UrlDefinition.API_CATEGORY_COURSE_OPTION)
    suspend fun getCategoryCourseOption(): NetResult<CategoryOptionEntity>

    /**
     * 课程分类课程列表
     */
    @FormUrlEncoded
    @POST(UrlDefinition.API_CATEGORY_COURSE_DATA)
    suspend fun getCategoryCourseData(
        @Field("data") data: String,
        @Field("page") page: Int
    ): NetResult<List<CourseEntity>>
}