package com.sun.binding.widget.state

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import com.sun.binding.R

/**
 * 多状态切换 Layout
 */
class StatefulLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(context, attrs, defStyle) {

    /** 内容 */
    private var contentView: View? = null

    /** 加载中 */
    private var loadingView: View? = null

    /** 错误 */
    private var errorView: View? = null

    /** 空数据 */
    private var emptyView: View? = null

    /** 状态切换监听器 */
    var listener: StateListener? = null

    /** 状态切换是否使用动画 */
    private var animateLayoutChanges: Boolean = true

    /** 是否使用默认视图 */
    private var applyNormalView: Boolean = true

    /** 当前状态 */
    var viewState: StateEnum = StateEnum.CONTENT
        set(value) {
            val previousField = field
            if (value != previousField) {
                field = value
                showViewForState(previousField)
                listener?.onStateChanged(value)
            }
        }

    init {
        val inflater = LayoutInflater.from(context)
        val a = context.obtainStyledAttributes(attrs, R.styleable.StatefulLayout)
        animateLayoutChanges = a.getBoolean(R.styleable.StatefulLayout_sfl_animateViewChanges, animateLayoutChanges)
        applyNormalView = a.getBoolean(R.styleable.StatefulLayout_sfl_applyNormalView, applyNormalView)

        val loadingViewResId = a.getResourceId(R.styleable.StatefulLayout_sfl_loadingView, -1)
        if (loadingViewResId > -1) {
            val inflatedLoadingView = inflater.inflate(loadingViewResId, this, false)
            loadingView = inflatedLoadingView
            addView(inflatedLoadingView, inflatedLoadingView.layoutParams)
        } else if (applyNormalView) {
            val stateLoadingView = StateLoadingView(context)
            loadingView = stateLoadingView
            addView(stateLoadingView)
        }

        val emptyViewResId = a.getResourceId(R.styleable.StatefulLayout_sfl_emptyView, -1)
        if (emptyViewResId > -1) {
            val inflatedEmptyView = inflater.inflate(emptyViewResId, this, false)
            emptyView = inflatedEmptyView
            addView(inflatedEmptyView, inflatedEmptyView.layoutParams)
        } else if (applyNormalView) {
            val stateEmptyView = StateEmptyView(context)
            emptyView = stateEmptyView
            addView(stateEmptyView)
        }

        val errorViewResId = a.getResourceId(R.styleable.StatefulLayout_sfl_errorView, -1)
        if (errorViewResId > -1) {
            val inflatedErrorView = inflater.inflate(errorViewResId, this, false)
            errorView = inflatedErrorView
            addView(inflatedErrorView, inflatedErrorView.layoutParams)
        } else if (applyNormalView) {
            val stateErrorView = StateErrorView(context)
            errorView = stateErrorView
            addView(stateErrorView)
        }

        viewState = when (a.getInt(R.styleable.StatefulLayout_sfl_viewState, StateEnum.CONTENT.code)) {
            StateEnum.ERROR.code -> StateEnum.ERROR
            StateEnum.EMPTY.code -> StateEnum.EMPTY
            StateEnum.LOADING.code -> StateEnum.LOADING
            else -> StateEnum.CONTENT
        }
        a.recycle()
    }

    /**
     * 返回和 State 相关联的 View
     */
    fun getViewForState(viewState: StateEnum): View? {
        return when (viewState) {
            StateEnum.LOADING -> loadingView
            StateEnum.CONTENT -> contentView
            StateEnum.EMPTY -> emptyView
            StateEnum.ERROR -> errorView
        }
    }

    /**
     * 设置对应状态的 View
     */
    fun setViewForState(@LayoutRes layoutRes: Int, viewState: StateEnum, switchToState: Boolean = false) {
        val view = LayoutInflater.from(context).inflate(layoutRes, this, false)
        setViewForState(view, viewState, switchToState)
    }

    /**
     * 设置对应状态的 View
     */
    fun setViewForState(view: View, viewState: StateEnum, switchToState: Boolean = false) {
        when (viewState) {
            StateEnum.LOADING -> {
                if (loadingView != null) removeView(loadingView)
                loadingView = view
                addView(view)
            }

            StateEnum.EMPTY -> {
                if (emptyView != null) removeView(emptyView)
                emptyView = view
                addView(view)
            }

            StateEnum.ERROR -> {
                if (errorView != null) removeView(errorView)
                errorView = view
                addView(view)
            }

            StateEnum.CONTENT -> {
                if (contentView != null) removeView(contentView)
                contentView = view
                addView(view)
            }
        }

        if (switchToState) this.viewState = viewState
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (contentView == null) throw IllegalArgumentException("Content view is not defined")

        when (viewState) {
            StateEnum.CONTENT -> showViewForState(StateEnum.CONTENT)
            else -> contentView?.visibility = View.GONE
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        return when (val superState = super.onSaveInstanceState()) {
            null -> superState
            else -> SavedState(superState, viewState)
        }
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            viewState = state.state
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    /*
    重写了所有的 addView 方法
    因为 StatefulLayout 可以通过 xml 获取内容视图，因此不建议通过 addView 方法将视图添加进去
    建议使用 setViewForState 方法来设置对应状态的 View
     */
    override fun addView(child: View) {
        if (isValidContentView(child)) contentView = child
        super.addView(child)
    }

    override fun addView(child: View, index: Int) {
        if (isValidContentView(child)) contentView = child
        super.addView(child, index)
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (isValidContentView(child)) contentView = child
        super.addView(child, index, params)
    }

    override fun addView(child: View, params: ViewGroup.LayoutParams) {
        if (isValidContentView(child)) contentView = child
        super.addView(child, params)
    }

    override fun addView(child: View, width: Int, height: Int) {
        if (isValidContentView(child)) contentView = child
        super.addView(child, width, height)
    }

    override fun addViewInLayout(child: View, index: Int, params: ViewGroup.LayoutParams): Boolean {
        if (isValidContentView(child)) contentView = child
        return super.addViewInLayout(child, index, params)
    }

    override fun addViewInLayout(child: View, index: Int, params: ViewGroup.LayoutParams, preventRequestLayout: Boolean): Boolean {
        if (isValidContentView(child)) contentView = child
        return super.addViewInLayout(child, index, params, preventRequestLayout)
    }

    /**
     * 检查是否拥有有效的 contentView
     */
    private fun isValidContentView(view: View): Boolean {
        return if (contentView != null && contentView !== view) {
            false
        } else view != loadingView && view != errorView && view != emptyView
    }

    /**
     * 根据 StateEnum 显示 View
     */
    private fun showViewForState(previousState: StateEnum) {
        when (viewState) {
            StateEnum.LOADING -> {
                requireNotNull(loadingView).apply {
                    contentView?.visibility = View.GONE
                    errorView?.visibility = View.GONE
                    emptyView?.visibility = View.GONE

                    if (animateLayoutChanges) {
                        animateLayoutChange(getViewForState(previousState))
                    } else {
                        visibility = View.VISIBLE
                    }
                }
            }

            StateEnum.EMPTY -> {
                requireNotNull(emptyView).apply {
                    contentView?.visibility = View.GONE
                    errorView?.visibility = View.GONE
                    loadingView?.visibility = View.GONE

                    if (animateLayoutChanges) {
                        animateLayoutChange(getViewForState(previousState))
                    } else {
                        visibility = View.VISIBLE
                    }
                }
            }

            StateEnum.ERROR -> {
                requireNotNull(errorView).apply {
                    contentView?.visibility = View.GONE
                    loadingView?.visibility = View.GONE
                    emptyView?.visibility = View.GONE

                    if (animateLayoutChanges) {
                        animateLayoutChange(getViewForState(previousState))
                    } else {
                        visibility = View.VISIBLE
                    }
                }
            }

            StateEnum.CONTENT -> {
                requireNotNull(contentView).apply {
                    loadingView?.visibility = View.GONE
                    errorView?.visibility = View.GONE
                    emptyView?.visibility = View.GONE

                    if (animateLayoutChanges) {
                        animateLayoutChange(getViewForState(previousState))
                    } else {
                        visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    /**
     * 状态布局切换动画
     */
    private fun animateLayoutChange(@Nullable previousView: View?) {
        if (previousView == null) {
            requireNotNull(getViewForState(viewState)).visibility = View.VISIBLE
            return
        }

        ObjectAnimator.ofFloat(previousView, "alpha", 1.0f, 0.0f).apply {
            duration = 250L
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    previousView.visibility = View.VISIBLE
                }

                override fun onAnimationEnd(animation: Animator) {
                    previousView.visibility = View.GONE
                    val currentView = requireNotNull(getViewForState(viewState))
                    currentView.visibility = View.VISIBLE
                    ObjectAnimator.ofFloat(currentView, "alpha", 0.0f, 1.0f).setDuration(250L).start()
                }
            })
        }.start()
    }

    private class SavedState : BaseSavedState {
        internal val state: StateEnum

        constructor(superState: Parcelable, state: StateEnum) : super(superState) {
            this.state = state
        }

        constructor(parcel: Parcel) : super(parcel) {
            state = parcel.readSerializable() as StateEnum
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeSerializable(state)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(`in`: Parcel): SavedState {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}

/**
 * 状态枚举
 */
enum class StateEnum(val code: Int) {
    CONTENT(0), LOADING(1), EMPTY(2), ERROR(3);
}

/**
 * 状态切换监听器
 */
interface StateListener {
    /**
     * Callback for when the [StateEnum] has changed
     *
     * @param viewState The [StateEnum] that was switched to
     */
    fun onStateChanged(viewState: StateEnum)
}