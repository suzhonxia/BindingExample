package com.sun.binding.model.mine

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.sun.binding.R
import com.sun.binding.constants.SP_KEY_TOKEN
import com.sun.binding.entity.UserInfoEntity
import com.sun.binding.mvvm.BaseViewModel
import com.sun.binding.mvvm.binding.BindingField
import com.sun.binding.mvvm.model.SnackbarModel
import com.sun.binding.net.repository.UserRepository
import com.sun.binding.tools.ext.getStackTraceString
import com.sun.binding.tools.ext.toToastMsg
import com.sun.binding.tools.helper.MMKVHelper
import com.sun.binding.ui.base.tagableScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MineViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    /** 标记 - 是否正在刷新 */
    val refreshing: BindingField<Boolean> = BindingField(false)

    /** 刷新回调 */
    val onRefresh: () -> Unit = { getUserPrivateData() }

    /** 用户信息数据 */
    val userInfoData = MutableLiveData<UserInfoEntity>()

    /** 用户信息布局点击事件 */
    val onUserInfoClick: (View) -> Unit = { v ->
        when (v.id) {
            R.id.ivUserAvatar, R.id.tvUserInfoEdit -> toastData.postValue("个人资料编辑".toToastMsg())
            R.id.tvUserFocus -> toastData.postValue("关注".toToastMsg())
            R.id.tvUserFans -> toastData.postValue("粉丝".toToastMsg())
        }
    }

    /** 菜单栏点击事件 */
    val onMenuClick: (View) -> Unit = { v ->
        (v.tag as? String)?.let { toastData.postValue(it.toToastMsg()) }
    }

    /**
     * 获取用户隐私信息
     */
    private fun getUserPrivateData() {
        tagableScope.launch {
            try {
                val userInfo = userRepository.getUserInfo()
                if (userInfo.success()) {
                    MMKVHelper.saveString(SP_KEY_TOKEN, userInfo.data!!.token)
                }
                LogUtils.d("getUserPrivateData userInfo = $userInfo")
            } catch (throwable: Throwable) {
                snackbarData.postValue(SnackbarModel(throwable.getStackTraceString()))
            } finally {
                refreshing.set(false)
            }
        }
    }
}