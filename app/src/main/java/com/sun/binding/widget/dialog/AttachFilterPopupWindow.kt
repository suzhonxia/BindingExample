package com.sun.binding.widget.dialog

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.impl.PartShadowPopupView
import com.sun.binding.R
import com.sun.binding.entity.FilterEntity

/**
 * 依附于某个 View 的下拉筛选弹窗
 */
class AttachFilterPopupWindow @JvmOverloads constructor(
    context: Context,
    private val onShow: (() -> Unit)? = null,
    private val onDismiss: (() -> Unit)? = null
) : PartShadowPopupView(context) {

    override fun getImplLayoutId(): Int = R.layout.app_dialog_attach_filter

    private lateinit var filterAdapter: BaseQuickAdapter<FilterEntity, BaseViewHolder>

    var filterList: MutableList<FilterEntity>? = null
        set(value) {
            field = value
            if (this::filterAdapter.isInitialized) {
                filterAdapter.setNewData(value)
            }
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
                                setBackgroundResource(if (optionBean.isSelected) R.drawable.app_shape_positive_bg else R.drawable.app_shape_negative_bg)
                                layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                                    leftMargin = SizeUtils.dp2px(15F)
                                    bottomMargin = SizeUtils.dp2px(10F)
                                }
                                setOnClickListener {
                                    optionBean.isSelected = !optionBean.isSelected
                                    setBackgroundResource(if (optionBean.isSelected) R.drawable.app_shape_positive_bg else R.drawable.app_shape_negative_bg)
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

        findViewById<View>(R.id.tvReset).setOnClickListener { }
        findViewById<View>(R.id.tvConfirm).setOnClickListener { }
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