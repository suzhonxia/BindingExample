package com.sun.binding.widget.dialog

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.impl.PartShadowPopupView
import com.sun.binding.R
import com.sun.binding.entity.OptionEntity
import com.sun.binding.tools.tool.getColor

/**
 * 依附于某个 View 的下拉列表选项弹窗
 */
class AttachOptionPopupWindow @JvmOverloads constructor(
    context: Context,
    val optionList: MutableList<OptionEntity>? = null,
    private val onShow: (() -> Unit)? = null,
    private val onDismiss: (() -> Unit)? = null
) : PartShadowPopupView(context) {

    /** 选中的数据 */
    var selectedOption = MutableLiveData<OptionEntity>()

    private lateinit var optionAdapter: BaseQuickAdapter<OptionEntity, BaseViewHolder>

    override fun getImplLayoutId(): Int = R.layout.app_dialog_attach_option

    override fun onCreate() {
        super.onCreate()
        optionAdapter = object : BaseQuickAdapter<OptionEntity, BaseViewHolder>(R.layout.app_dialog_attach_option_item, optionList) {
            override fun convert(helper: BaseViewHolder, item: OptionEntity?) {
                val isSelected = item == selectedOption.value

                helper.setText(R.id.tvOption, item?.name)
                helper.setVisible(R.id.ivSelect, isSelected)
                helper.setTextColor(R.id.tvOption, (if (isSelected) R.color.app_text_color_black_light else R.color.app_text_color_gray_light).getColor())
            }
        }.apply {
            setOnItemClickListener { _, _, position ->
                optionList?.get(position)?.let {
                    if (it != selectedOption.value) {
                        selectedOption.value = it
                        notifyDataSetChanged()
                        dismiss()
                    }
                }
            }
        }

        val rvOption = findViewById<RecyclerView>(R.id.rvOption)
        rvOption.layoutManager = LinearLayoutManager(context)
        rvOption.adapter = optionAdapter
    }

    fun updateSelectedOption(optionEntity: OptionEntity?) {
        selectedOption.value = optionEntity
        if (this::optionAdapter.isInitialized) {
            optionAdapter.notifyDataSetChanged()
        }
    }

    override fun show(): BasePopupView {
        onShow?.invoke()
        return super.show()
    }

    override fun dismiss() {
        onDismiss?.invoke()
        super.dismiss()
    }
}