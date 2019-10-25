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
}