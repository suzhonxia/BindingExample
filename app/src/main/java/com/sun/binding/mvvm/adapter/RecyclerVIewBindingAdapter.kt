@file:Suppress("unused")

package com.sun.binding.mvvm.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * RecyclerView DataBinding 适配器
 */

/**
 * 设置适配器
 *
 * @param adapter 适配器
 */
@BindingAdapter("android:bind_rv_adapter")
fun setRecyclerViewAdapter(rv: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
    rv.adapter = adapter
}

/**
 * 添加 item 装饰
 */
@BindingAdapter("android:bind_rv_item_decoration")
fun addItemDecoration(rv: RecyclerView, itemDecoration: ItemDecoration?) {
    if (itemDecoration != null) {
        rv.addItemDecoration(itemDecoration)
    }
}