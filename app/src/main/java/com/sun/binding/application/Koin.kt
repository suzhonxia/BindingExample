package com.sun.binding.application

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.sun.binding.BuildConfig
import com.sun.binding.constants.NET_CACHE_FILE_SIZE
import com.sun.binding.constants.NET_TIMEOUT_MS
import com.sun.binding.constants.SP_KEY_COOKIES
import com.sun.binding.constants.SP_KEY_TOKEN
import com.sun.binding.entity.CookieEntity
import com.sun.binding.model.home.HomeViewModel
import com.sun.binding.model.main.MainViewModel
import com.sun.binding.model.main.SplashViewModel
import com.sun.binding.model.mine.MineViewModel
import com.sun.binding.net.UrlDefinition
import com.sun.binding.net.WebService
import com.sun.binding.net.repository.UserRepository
import com.sun.binding.tools.ext.toJson
import com.sun.binding.tools.helper.MMKVHelper
import com.tencent.mmkv.MMKV
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 网络请求 Module
 */
val netModule: Module = module {
    single {
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .cookieJar(object : CookieJar {
                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    val cookieEntity = MMKVHelper.getObject(SP_KEY_COOKIES, CookieEntity::class.java)
                    return cookieEntity?.cookies.orEmpty()
                }

                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                    if (cookies.size > 1) {
                        val ls = arrayListOf<Cookie>()
                        ls.addAll(cookies)
                        val cookieEntity = CookieEntity(ls)
                        MMKVHelper.saveString(SP_KEY_COOKIES, cookieEntity.toJson())
                    }
                }
            })
            .addNetworkInterceptor(HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    if (BuildConfig.DEBUG) {
                        LogUtils.d("Net", message)
                    }
                }
            }))
            .addInterceptor(object : Interceptor {
                private val appKey: String = "dajlkdjakldajkdl"
                private val token: String = MMKVHelper.getString(SP_KEY_TOKEN, "")
                private val userAgent: String = "JBKK/2019(Android ${BuildConfig.VERSION_NAME})/${BuildConfig.VERSION_CODE}"

                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request().newBuilder()
                        .header("token", token)
                        .header("AppKey", appKey)
                        .header("User-Agent", userAgent)
                        .header("Content-Type", "application/json;charset=UTF-8")
                        .header("Authorization", "Bearer $token")
                        .build()
                    return chain.proceed(request)
                }
            })
            .readTimeout(NET_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .writeTimeout(NET_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .connectTimeout(NET_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .cache(Cache(Utils.getApp().cacheDir, NET_CACHE_FILE_SIZE))
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(UrlDefinition.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
    }

    single<WebService> {
        get<Retrofit>().create(WebService::class.java)
    }
}

/**
 * 数据仓库 Module
 */
val repositoryModule: Module = module {
    single { UserRepository() }
}

/**
 * 适配器 Module
 */
val adapterModule: Module = module { }

/**
 * ViewModel Module
 */
val viewModelModule: Module = module {
    viewModel { SplashViewModel() }
    viewModel { MainViewModel() }
    viewModel { HomeViewModel() }
    viewModel { MineViewModel(get()) }
}