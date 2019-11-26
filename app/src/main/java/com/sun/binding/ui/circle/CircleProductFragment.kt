package com.sun.binding.ui.circle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.sun.binding.R

/**
 * 同学拳 - Tab
 */
class CircleProductFragment private constructor() : Fragment() {
    companion object {
        /**
         * 创建 Fragment
         *
         * @return 首页 Fragment
         */
        fun newInstance(type: Int): CircleProductFragment {
            return CircleProductFragment().apply { arguments = bundleOf("type" to type) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.circle_product_fragment, container, false)
    }
}