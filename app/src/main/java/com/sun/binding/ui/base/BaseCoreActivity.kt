package com.sun.binding.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.sun.binding.tools.ext.hideSoftKeyboard
import com.sun.binding.tools.tool.shouldHideInput

/**
 * Activity 基类
 * - 维护 [mContext]：当前界面的 Context 对象
 * - 维护 [touchToHideInput]：是否在点击 [EditText] 以外的地方隐藏软键盘
 * - 添加 [startAnim]、[finishAnim] 方法，统一处理界面跳转动画效果
 */
abstract class BaseCoreActivity : AppCompatActivity() {

    /** 当前界面 Context 对象 */
    protected lateinit var mContext: AppCompatActivity

    /** 标记 - 触摸输入框以外范围是否隐藏软键盘 */
    protected var touchToHideInput = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 初始化 Context 对象
        mContext = this
    }

    override fun onPause() {
        super.onPause()
        // 移除当前获取焦点控件的焦点，防止下个界面软键盘顶起布局
        currentFocus?.clearFocus()
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle?) {
        super.startActivityForResult(intent, requestCode, options)
        // 设置打开动画
        startAnim()
    }

    override fun finish() {
        super.finish()
        // 设置退出动画
        finishAnim()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (touchToHideInput) {
            if (ev.action == MotionEvent.ACTION_DOWN) {
                val v = currentFocus
                if (shouldHideInput(v, ev)) {
                    // 需要隐藏软键盘
                    (v as? EditText)?.hideSoftKeyboard()
                }
                return super.dispatchTouchEvent(ev)
            }
            if (window.superDispatchTouchEvent(ev)) {
                return true
            }
            return onTouchEvent(ev)
        } else {
            return super.dispatchTouchEvent(ev)
        }
    }

    /**
     * 开始动画，可按需求重写
     */
    protected open fun startAnim() {
    }

    /**
     * 结束动画，可按需求重写
     */
    protected open fun finishAnim() {
    }
}