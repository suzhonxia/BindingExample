package com.sun.binding.net

/**
 * 网络接口地址
 */
object UrlDefinition {

    /** 服务器域名配置 */
    private var api_toggle = false

    /** 正式环境 */
    private const val API_HOST_RELEASE = "https://www.magfx-jbkk.com"

    /** 测试环境 */
    private const val API_HOST_TEST = "https://test.magfx-jbkk.com"

    /** 服务器 HOST */
    val BASE_URL = if (api_toggle) API_HOST_RELEASE else API_HOST_TEST

    /** 获取个人信息 */
    const val API_USER_INFO = "/api/user/index"

    /** 首页聚合数据 */
    const val API_HOME_INDEX = "/v2/index/index"

    /** 亲职教育分类 */
    const val API_COURSE_INDEX = "/v2/qinzhi/index"

    /** 同学圈列表 */
    const val API_CIRCLE_PRODUCT = "/v2/circle/t_list"
}