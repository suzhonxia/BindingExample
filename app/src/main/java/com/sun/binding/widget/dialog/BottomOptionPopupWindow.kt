package com.sun.binding.widget.dialog

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lxj.xpopup.core.BottomPopupView
import com.sun.binding.R
import com.sun.binding.tools.tool.getColor

/**
 * 底部弹出的选项弹出层
 */
class BottomOptionPopupWindow @JvmOverloads constructor(
    context: Context,
    private val optionList: MutableList<String>
) : BottomPopupView(context) {

    /** 选中的数据 */
    var selectedOption = MutableLiveData<String>()

    override fun getImplLayoutId() = R.layout.app_dialog_bottom_option

    override fun onCreate() {
        super.onCreate()
        findViewById<TextView>(R.id.tvClose).setOnClickListener { dismiss() }
        findViewById<RecyclerView>(R.id.rvOption).run {
            this.addItemDecoration(object : RecyclerView.ItemDecoration() {
                private val paint = Paint().apply { color = R.color.app_dividing_bg.getColor() }

                override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                    super.onDrawOver(c, parent, state)
                    val spacing = SizeUtils.dp2px(15F).toFloat()
                    val childCount = parent.childCount
                    for (i in 0 until childCount) {
                        val childView = parent.getChildAt(i)
                        if (i != 0) {
                            c.drawLine(spacing, childView.top.toFloat(), ScreenUtils.getScreenWidth() - spacing, (childView.top + SizeUtils.dp2px(1F)).toFloat(), paint)
                        }
                    }
                }
            })
            this.adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.app_dialog_bottom_option_item, optionList) {
                override fun convert(helper: BaseViewHolder, item: String?) {
                    (helper.itemView as TextView).text = item
                }
            }.apply {
                setOnItemClickListener { _, _, position ->
                    selectedOption.value = optionList[position]
                    dismiss()
                }
            }
        }
    }
}