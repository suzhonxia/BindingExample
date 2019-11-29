@file:Suppress("unused")

package com.sun.binding.tools.tool

import com.sun.binding.R
import com.sun.binding.widget.state.*

/**
 * 通用模式的状态切换
 */
fun StatefulLayout.setCommonMode(errorAction: (() -> Unit)? = null) {
    (getViewForState(StateEnum.EMPTY) as? StateEmptyView)?.updateEmptyOption(R.drawable.state_icon_empty, StateConstants.EMPTY_COMMON)
    (getViewForState(StateEnum.ERROR) as? StateErrorView)?.updateErrorOption(R.drawable.state_icon_error_net, StateConstants.ERROR_NET, "重试", errorAction)
}

/**
 * 通用模式的状态切换
 */
fun StatefulLayout.setLocationMode(errorAction: (() -> Unit)? = null) {
    (getViewForState(StateEnum.EMPTY) as? StateEmptyView)?.updateEmptyOption(R.drawable.state_icon_empty, StateConstants.EMPTY_COMMON)
    (getViewForState(StateEnum.ERROR) as? StateErrorView)?.updateErrorOption(R.drawable.state_icon_error_location, StateConstants.ERROR_LOCATION, "去开启", errorAction)
}