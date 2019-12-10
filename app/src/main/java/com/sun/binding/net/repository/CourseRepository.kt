package com.sun.binding.net.repository

import com.sun.binding.net.WebService
import com.sun.binding.tools.manager.AppUserManager
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

    suspend fun getEducCourseOption() = withContext(Dispatchers.IO) {
        mWebService.getEducCourseOption()
    }
    
    suspend fun getEducCourseData(sortId: Int, categoryId: Int, ageId: Int, page: Int) = withContext(Dispatchers.IO) {
        mWebService.getEducCourseData(sortId, categoryId, ageId, page)
    }

    suspend fun getCircleProductData(type: Int, page: Int) = withContext(Dispatchers.IO) {
        val location = AppUserManager.getLocation()
        mWebService.getCircleProductData(type, page, location?.latitude ?: 0.0, location?.longitude ?: 0.0)
    }

    suspend fun getSuitCourseData() = withContext(Dispatchers.IO) {
        mWebService.getSuitCourseData()
    }
}