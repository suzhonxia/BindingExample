package com.sun.binding.model.home

import androidx.lifecycle.MutableLiveData
import com.sun.binding.R
import com.sun.binding.entity.HomeEntity
import com.sun.binding.mvvm.BaseViewModel
import com.sun.binding.mvvm.binding.BindingField
import com.sun.binding.mvvm.model.SnackbarModel
import com.sun.binding.net.repository.HomeRepository
import com.sun.binding.tools.ext.getStackTraceString
import java.lang.RuntimeException

class HomeViewModel(private val homeRepository: HomeRepository) : BaseViewModel() {

    /** title */
    val title = "MagFX 机变酷卡"

    /** logo */
    val logoRes = R.drawable.app_logo

    /** 标记 页面是否在前台显示 */
    val showing = BindingField(false)

    /** 标记 - 是否正在刷新 */
    val refreshing = BindingField(false)

    /** 刷新回调 */
    val onRefresh: () -> Unit = { requestHomeData() }

    /** 首页数据 */
    val homeData = MutableLiveData<HomeEntity>()

    fun onResume() {
        showing.set(true)
    }

    fun onPause() {
        showing.set(false)
    }

    /**
     * 获取首页聚合数据
     */
    private fun requestHomeData() {
        launchOnIO {
            tryBlock {
                val result = homeRepository.getHomeData()
                if (result.checkResponseCode()) {
                    homeData.postValue(result.data)
                }
            }
            catchBlock { e ->
                snackbarData.postValue(SnackbarModel(e.getStackTraceString()))
            }
            finallyBlock {
                refreshing.set(false)
            }
        }
    }
}