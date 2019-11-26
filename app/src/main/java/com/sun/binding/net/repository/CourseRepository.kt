package com.sun.binding.net.repository

import com.sun.binding.net.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.inject

/**
 * 课程相关数据仓库
 */
class CourseRepository : BaseRepository() {
    override val mWebService: WebService by inject()

    suspend fun getEducIndex() = withContext(Dispatchers.IO) {
        mWebService.getEducIndex()
    }
}