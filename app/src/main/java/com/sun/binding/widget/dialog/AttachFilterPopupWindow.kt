package com.sun.binding.widget.dialog

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.impl.PartShadowPopupView
import com.sun.binding.R
import com.sun.binding.entity.FilterEntity
import com.sun.binding.entity.OptionEntity
import com.sun.binding.tools.util.event.Event

/**
 * 依附于某个 View 的下拉筛选弹窗
 */
class AttachFilterPopupWindow @JvmOverloads constructor(
    context: Context,
    private val onShow: (() -> Unit)? = null,
    private val onDismiss: (() -> Unit)? = null
) : PartShadowPopupView(context) {

    override fun getImplLayoutId(): Int = R.layout.app_dialog_attach_filter

    // 选中的选项数据(一次性)
    private val onceSelectOption: MutableList<OptionEntity> = mutableListOf()

    private lateinit var filterAdapter: BaseQuickAdapter<FilterEntity, BaseViewHolder>

    /** 提交事件(重置 and 确定按钮点击触发) */
    var submitTarget = MutableLiveData<Event<Boolean>>()

    var filterList: MutableList<FilterEntity>? = null
        set(value) {
            field = value
            if (this::filterAdapter.isInitialized) {
                filterAdapter.setNewData(value)
            }
        }

    fun getSelectData(): String {
        val data = mutableMapOf<String, List<String>>()
        filterList?.forEach { filter ->
            val key = filter.flag
            val value = mutableListOf<String>().also {
                filter.optionList.forEach { option ->
                    if (option.isSelected) {
                        it.add(option.id.toString())
                    }
                }
            }
            data[key] = value
        }
        return GsonUtils.toJson(data)
    }

    override fun onCreate() {
        super.onCreate()
        filterAdapter = object : BaseQuickAdapter<FilterEntity, BaseViewHolder>(R.layout.app_dialog_attach_filter_item, filterList) {
            override fun convert(helper: BaseViewHolder, item: FilterEntity?) {
                helper.setText(R.id.tvOptionTitle, item?.title)

                val optionContainer = helper.getView<ViewGroup>(R.id.optionContainer)
                if (item?.optionList.isNullOrEmpty()) {
                    optionContainer.visibility = View.GONE
                } else {
                    val optionList = item?.optionList!!
                    optionContainer.visibility = View.VISIBLE
                    optionContainer.removeAllViews()
                    optionList.forEach { optionBean ->
                        optionContainer.addView(View.inflate(context, R.layout.app_dialog_attach_filter_option_item, null).apply {
                            findViewById<TextView>(R.id.tvOption).run {
                                text = optionBean.name
                                setBackgroundResource(if (optionBean.isSelected) R.drawable.course_layer_list_option_selected else R.drawable.app_shape_negative_bg)
                                layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                                    leftMargin = SizeUtils.dp2px(15F)
                                    bottomMargin = SizeUtils.dp2px(10F)
                                }
                                setOnClickListener {
                                    notifySelectData(optionBean)
                                    optionBean.isSelected = !optionBean.isSelected
                                    setBackgroundResource(if (optionBean.isSelected) R.drawable.course_layer_list_option_selected else R.drawable.app_shape_negative_bg)
                                }
                            }
                        })
                    }
                }
            }
        }

        findViewById<RecyclerView>(R.id.rvFilter).run {
            layoutManager = LinearLayoutManager(context)
            adapter = filterAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    val position = parent.getChildAdapterPosition(view)
                    if (position == 0) {
                        outRect.top = SizeUtils.dp2px(15F)
                    }
                }
            })
        }

        findViewById<View>(R.id.tvReset).setOnClickListener { resetSelect() }
        findViewById<View>(R.id.tvConfirm).setOnClickListener { submitSelectData() }
    }

    private fun notifySelectData(optionBean: OptionEntity) {
        if (!onceSelectOption.contains(optionBean)) {
            onceSelectOption.add(optionBean)
        } else {
            onceSelectOption.remove(optionBean)
        }
    }

    private fun resetSelect() {
        filterList?.map { filter ->
            filter.optionList.map { option ->
                option.isSelected = false
            }
        }
        filterAdapter.notifyDataSetChanged()
        submitTarget.value = Event(false)
        dismissWithClear()
    }

    private fun submitSelectData() {
        submitTarget.value = Event(true)
        dismissWithClear()
    }

    private fun dismissWithClear() {
        onceSelectOption.clear()
        dismiss()
    }

    override fun show(): BasePopupView {
        onShow?.invoke()
        return super.show()
    }

    override fun dismiss() {
        onDismiss?.invoke()
        super.dismiss()
        if (onceSelectOption.isNotEmpty()) {
            onceSelectOption.forEach { option ->
                option.isSelected = !option.isSelected
            }

            onceSelectOption.clear()
            filterAdapter.notifyDataSetChanged()
        }
    }
}