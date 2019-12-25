package com.sun.binding.model.course

import android.text.SpannableStringBuilder
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.SpanUtils
import com.sun.binding.R
import com.sun.binding.model.base.BaseViewModel
import com.sun.binding.mvvm.binding.BindingField
import com.sun.binding.tools.tool.getColor
import com.sun.binding.ui.course.SubmitPhotoAdapter

class TaskSubmitViewModel : BaseViewModel() {

    /** 提交配置 */
    val taskSubmitConfig: TaskSubmitConfig = TaskSubmitConfig()
}

/**
 * 作业提交配置项
 */
data class TaskSubmitConfig(
    val introMaxSize: Int = 1000,
    val maxPhotoSize: Int = 9,
    val introCounterLabel: BindingField<SpannableStringBuilder> = BindingField(SpannableStringBuilder("0/$introMaxSize")),
    val hasSelectedVideo: BindingField<Boolean> = BindingField(false),
    val videoPath: BindingField<String> = BindingField(""),
    val videoCover: BindingField<String> = BindingField(""),
    private val introSize: BindingField<Int> = BindingField(0)
) {

    fun updateIntroSize(value: Int) {
        introSize.set(value)
        introCounterLabel.set(
            SpanUtils()
                .append(value.toString())
                .setForegroundColor((if (value == 0) R.color.app_text_color_gray_light else R.color.app_text_color_black_light).getColor())
                .append("/${introMaxSize}")
                .create()
        )
    }

    fun updateSelectVideo(path: String, cover: String) {
        videoPath.set(path)
        videoCover.set(cover)
        hasSelectedVideo.set(path.isNotBlank())
    }
}