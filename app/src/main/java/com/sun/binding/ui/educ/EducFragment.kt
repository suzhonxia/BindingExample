package com.sun.binding.ui.educ

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.binding.R

/**
 * 主界面 - 亲职教育 Tab
 */
class EducFragment : Fragment() {
    companion object {
        /**
         * 创建 Fragment
         *
         * @return 首页 Fragment
         */
        fun newInstance(): EducFragment {
            return EducFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.educ_fragment, container, false)
    }
}