package com.sun.binding.ui.circle

import android.Manifest
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ctetin.expandabletextviewlibrary.ExpandableTextView
import com.sun.binding.R
import com.sun.binding.constants.KeyConstant
import com.sun.binding.databinding.CircleProductFragmentBinding
import com.sun.binding.entity.ProductEntity
import com.sun.binding.entity.validLocation
import com.sun.binding.model.circle.CircleProductViewModel
import com.sun.binding.tools.helper.ComputeHelper
import com.sun.binding.tools.helper.GlideHelper
import com.sun.binding.tools.helper.PermissionHelper
import com.sun.binding.tools.manager.AppUserManager
import com.sun.binding.tools.tool.getDrawable
import com.sun.binding.tools.util.event.EventObserver
import com.sun.binding.ui.base.BaseFragment
import com.sun.binding.widget.decoration.CircleProductItemDecoration
import com.sun.binding.widget.state.StateEnum
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * 同学拳 - Tab
 */
class CircleProductFragment private constructor() : BaseFragment<CircleProductViewModel, CircleProductFragmentBinding>() {
    companion object {
        /**
         * 创建 Fragment
         *
         * @return 首页 Fragment
         */
        fun newInstance(type: Int): CircleProductFragment {
            return CircleProductFragment().apply { arguments = bundleOf(KeyConstant.KEY_TYPE to type) }
        }
    }

    override val viewModel: CircleProductViewModel by viewModel()

    override val layoutResId: Int = R.layout.circle_product_fragment

    /** 课程列表适配器 */
    private val circleProductAdapter: CircleProductAdapter =
        CircleProductAdapter(listOf())

    /** 列表 item 装饰 */
    private val circleProductItemDecoration = CircleProductItemDecoration()

    override fun initView() {
        mBinding.run {
            adapter = circleProductAdapter
            itemDecoration = circleProductItemDecoration
        }

        // 刷新请求
        refresh()
    }

    override fun initObserve() {
        viewModel.run {
            circleProductList.observe(this@CircleProductFragment, Observer {
                if (isRefreshFlag()) {
                    circleProductAdapter.setNewData(it)
                } else {
                    circleProductAdapter.addData(it)
                }
            })
            retryTarget.observe(this@CircleProductFragment, EventObserver {
                refresh()
            })
        }
    }

    private fun refresh() {
        if (!viewModel.needLocation()) {
            viewModel.refreshConfig.refreshing.set(true)
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        val action = {
            if (AppUserManager.getLocation().validLocation()) {
                viewModel.refreshConfig.refreshing.set(true)
            } else {
                viewModel.startLocation(mContext) {
                    viewModel.refreshConfig.refreshing.set(true)
                }
            }
        }
        if (!PermissionHelper.isGranted(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            viewModel.viewState.set(StateEnum.ERROR)
            PermissionHelper.requestPermission(mContext, PermissionConstants.LOCATION) { action() }
        } else {
            viewModel.viewState.set(StateEnum.CONTENT)
            action()
        }
    }
}

internal class CircleProductAdapter(data: List<ProductEntity>?) : BaseQuickAdapter<ProductEntity, BaseViewHolder>(R.layout.circle_product_item, data) {
    override fun convert(helper: BaseViewHolder, item: ProductEntity?) {
        GlideHelper.loadImage(helper.getView(R.id.ivAuthorAvatar), item?.avatar, R.drawable.app_user_default_avatar.getDrawable())
        helper.setText(R.id.tvAuthorName, item?.nickname)
        helper.setText(R.id.tvCreateTime, item?.createtime)
        helper.setText(R.id.tvFocusAction, if (item?.focus == 0) "+ 关注" else "已关注")
        helper.getView<View>(R.id.tvFocusAction).isSelected = item?.focus == 1

        helper.getView<ExpandableTextView>(R.id.tvProductContent).setContent(item?.intro ?: "")
        if (item != null) applyResourceLayout(helper, item) else {
            helper.setGone(R.id.videoLayout, false)
            helper.setGone(R.id.photoLayout, false)
        }
        helper.setText(R.id.tvProductDistance, item?.distance)
        helper.setGone(R.id.tvProductDistance, !item?.distance.isNullOrEmpty())
        helper.setText(R.id.tvProductFavour, item?.favour)
        helper.getView<TextView>(R.id.tvProductFavour).setCompoundDrawablesWithIntrinsicBounds(
            (if (item?.favourType == 0) R.drawable.app_icon_favour else R.drawable.app_icon_favour_sel).getDrawable(), null, null, null
        )
    }

    private fun applyResourceLayout(helper: BaseViewHolder, item: ProductEntity) {
        when {
            item.video_img.isNotEmpty() -> {
                helper.setGone(R.id.videoLayout, true)
                helper.setGone(R.id.photoLayout, false)
                applyVideo(helper, item)
            }
            item.images.list.isNotEmpty() -> {
                helper.setGone(R.id.videoLayout, false)
                helper.setGone(R.id.photoLayout, true)
                applyPhoto(helper, item)
            }
            else -> {
                helper.setGone(R.id.videoLayout, false)
                helper.setGone(R.id.photoLayout, false)
            }
        }
    }

    private fun applyVideo(helper: BaseViewHolder, item: ProductEntity) {
        val maxSize = ScreenUtils.getScreenWidth() - SizeUtils.dp2px(60F)

        val coverSize = ComputeHelper.computeVideoCoverSize(maxSize, item.video_img_info)
        val videoLayout = helper.getView<View>(R.id.videoLayout)
        val ivVideoCover = helper.getView<ImageView>(R.id.ivVideoCover)
        videoLayout.layoutParams.width = coverSize[0]
        videoLayout.layoutParams.height = coverSize[1]
        GlideHelper.loadImage(ivVideoCover, item.video_img, R.color.app_window_bg.getDrawable())
    }

    private fun applyPhoto(helper: BaseViewHolder, item: ProductEntity) {
        val imageBean = item.images
        val gridSpace = SizeUtils.dp2px(6F)
        val maxSize = ScreenUtils.getScreenWidth() - SizeUtils.dp2px(60F)
        val imgMaxSize = ((ScreenUtils.getScreenWidth() - SizeUtils.dp2px(60F)) * 0.8F).toInt()

        val ivPhotoCover = helper.getView<ImageView>(R.id.ivPhotoCover)
        val gvPhoto = helper.getView<GridView>(R.id.gvPhoto)

        when {
            imageBean.list.size == 1 -> {
                ivPhotoCover.visibility = View.VISIBLE
                gvPhoto.visibility = View.GONE

                val coverSize = ComputeHelper.computeImageCoverSize(imgMaxSize, imageBean.imageSize)
                ivPhotoCover.layoutParams.width = coverSize[0]
                ivPhotoCover.layoutParams.height = coverSize[1]
                GlideHelper.loadImage(ivPhotoCover, imageBean.list[0], R.color.app_window_bg.getDrawable())
            }
            imageBean.list.size == 4 -> {
                ivPhotoCover.visibility = View.GONE
                gvPhoto.visibility = View.VISIBLE

                gvPhoto.numColumns = 2
                gvPhoto.verticalSpacing = gridSpace
                gvPhoto.horizontalSpacing = gridSpace
                gvPhoto.layoutParams.width = maxSize * 2 / 3
                gvPhoto.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                gvPhoto.adapter = CircleProductPhotoAdapter(mContext, (maxSize * 2 / 3 - gridSpace) / 2, imageBean.list)
            }
            else -> {
                ivPhotoCover.visibility = View.GONE
                gvPhoto.visibility = View.VISIBLE

                gvPhoto.numColumns = 3
                gvPhoto.verticalSpacing = gridSpace
                gvPhoto.horizontalSpacing = gridSpace
                gvPhoto.layoutParams.width = maxSize
                gvPhoto.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                gvPhoto.adapter = CircleProductPhotoAdapter(mContext, (maxSize - 2 * gridSpace) / 3, imageBean.list)
            }
        }
    }
}