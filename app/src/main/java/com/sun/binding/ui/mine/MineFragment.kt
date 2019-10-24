package com.sun.binding.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.binding.R

/**
 * 主界面 - 我的 Tab
 */
class MineFragment : Fragment() {
    companion object {
        /**
         * 创建 Fragment
         *
         * @return 首页 Fragment
         */
        fun newInstance(): MineFragment {
            return MineFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.mine_fragment, container, false)
    }
}