package com.sun.binding.ui.circle

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.sun.binding.R
import com.sun.binding.tools.helper.GlideHelper
import com.sun.binding.tools.tool.getDrawable

class CircleProductPhotoAdapter(private val context: Context, private val itemSize: Int, private val data: List<String>?) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var itemView = convertView
        val holder: ViewHolder = if (itemView == null) {
            itemView = View.inflate(context, R.layout.circle_product_photo_item, null)
            ViewHolder(itemView.findViewById(R.id.ivPhoto)).also { itemView.tag = it }
        } else {
            itemView.tag as ViewHolder
        }

        holder.iv.layoutParams?.width = itemSize
        holder.iv.layoutParams?.height = itemSize
        GlideHelper.loadImage(holder.iv, data!![position], R.color.app_window_bg.getDrawable())
        return itemView
    }

    override fun getItem(position: Int): Any? = data?.get(position)

    override fun getItemId(position: Int): Long = 0

    override fun getCount(): Int = data?.size ?: 0

    private data class ViewHolder(val iv: ImageView)
}
