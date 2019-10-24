package com.sun.binding.ui.circle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.binding.R

/**
 * 主界面 - 同学圈 Tab
 */
class CircleFragment : Fragment() {
    companion object {
        /**
         * 创建 Fragment
         *
         * @return 首页 Fragment
         */
        fun newInstance(): CircleFragment {
            return CircleFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.circle_fragment, container, false)
    }
}