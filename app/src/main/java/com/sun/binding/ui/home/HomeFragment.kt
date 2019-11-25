package com.sun.binding.ui.home

import android.graphics.Rect
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sun.binding.R
import com.sun.binding.databinding.HomeFragmentBinding
import com.sun.binding.entity.*
import com.sun.binding.model.home.HomeViewModel
import com.sun.binding.mvvm.model.ProgressModel
import com.sun.binding.tools.ext.showToast
import com.sun.binding.tools.helper.GlideHelper
import com.sun.binding.tools.manager.AppUserManager
import com.sun.binding.tools.tool.getDrawable
import com.sun.binding.ui.base.BaseFragment
import com.sun.binding.widget.decoration.GridSpaceItemDecoration
import kotlinx.android.synthetic.main.home_layout_asset_percent.view.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * 主界面 - 首页 Tab
 */
class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>() {
    companion object {
        /**
         * 创建 Fragment
         */
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override val viewModel: HomeViewModel by viewModel()

    override val layoutResId: Int = R.layout.home_fragment

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun initView() {
        viewModel.progressData.postValue(ProgressModel(true))
        viewModel.onRefresh.invoke()
    }

    override fun initObserve() {
        viewModel.homeData.observe(this, Observer { homeEntity ->
            viewModel.progressData.postValue(ProgressModel(false))
            homeEntity?.let { appViews(homeEntity) } ?: showCache()
        })
    }

    private fun showCache() {

    }

    private fun appViews(homeEntity: HomeEntity) {
        mBinding.viewModel = viewModel
        applyBanner(homeEntity.banner!!)
        applyNotice(homeEntity.gao)
        applyAsset(homeEntity.newcourse)
        applyCategory(homeEntity.category)
        applySuit(homeEntity.tao)
        applyNews(homeEntity.news)
    }

    private fun applyBanner(bannerList: List<BannerEntity>) {
        mBinding.bannerHome.setIsClipChildrenMode(true)
        mBinding.bannerHome.setBannerData(R.layout.home_banner_item, bannerList)
        mBinding.bannerHome.loadImage { _, _, view, position ->
            val bannerBean = bannerList[position]
            GlideHelper.loadImage(view as ImageView, bannerBean.image, R.drawable.app_placeholder_course_169.getDrawable())
        }
        mBinding.bannerHome.setOnItemClickListener { _, _, _, position ->
            bannerList[position].link?.showToast()
        }
    }

    private fun applyNotice(noticeList: List<GaoEntity>?) {
        if (!noticeList.isNullOrEmpty()) {
            val noticeMessageList = noticeList.map { it.getNoticeText() }.toList()
            mBinding.mvNotice.startWithList(noticeMessageList)
            mBinding.mvNotice.setOnItemClickListener { position, _ ->
                noticeList[position].link?.showToast()
            }
        }
    }

    private fun applyAsset(asset: NewcourseEntity?) {
        if (asset == null) return
        if (asset.type == 0) {
            mBinding.assetUploadLayout.setOnClickListener {
                if (AppUserManager.hasLogin()) {
                    "跳转到上传资产页面".showToast()
                } else {
                    "跳转到登录页面".showToast()
                }
            }
        } else {
            mBinding.assetPercentInclude.tvAssetComplete.text = String.format("已完成（${asset.okNum}个课程）")
            mBinding.assetPercentInclude.percentComplete.setPercent(asset.allNum, asset.okNum)
            mBinding.assetPercentInclude.tvAssetOpen.text = String.format("可完成（${asset.cNum}个课程）")
            mBinding.assetPercentInclude.percentOpen.setPercent(asset.allNum, asset.cNum)

            mBinding.assetPercentInclude.assetCompleteLayout.setOnClickListener { "已完成".showToast() }
            mBinding.assetPercentInclude.assetOpenLayout.setOnClickListener { "可完成".showToast() }
        }
    }

    private fun applyCategory(categoryList: List<CategoryEntity>?) {
        if (categoryList.isNullOrEmpty()) {
            mBinding.tvCategoryLabel.visibility = View.GONE
            mBinding.rvCourseCategory.visibility = View.GONE
        } else {
            mBinding.tvCategoryLabel.visibility = View.VISIBLE
            mBinding.rvCourseCategory.visibility = View.VISIBLE

            mBinding.tvCategoryLabel.text = "课程分类"
            mBinding.rvCourseCategory.layoutManager = GridLayoutManager(mContext, 2)
            if (mBinding.rvCourseCategory.itemDecorationCount == 0) {
                mBinding.rvCourseCategory.addItemDecoration(GridSpaceItemDecoration(SizeUtils.dp2px(8F)))
            }
            mBinding.rvCourseCategory.adapter = object : BaseQuickAdapter<CategoryEntity, BaseViewHolder>(R.layout.home_category_item, categoryList) {
                override fun convert(helper: BaseViewHolder, item: CategoryEntity?) {
                    GlideHelper.loadImage(helper.getView(R.id.ivCategory), item?.image, R.drawable.app_placeholder_course_169.getDrawable())
                    helper.itemView.setOnClickListener { "课程分类 : ${helper.layoutPosition}".showToast() }
                }
            }
        }
    }

    private fun applySuit(tao: TaoEntity?) {
        if (tao == null) {
            mBinding.tvSuitLabel.visibility = View.GONE
            mBinding.ivSuitCover.visibility = View.GONE
        } else {
            mBinding.tvSuitLabel.visibility = View.VISIBLE
            mBinding.ivSuitCover.visibility = View.VISIBLE

            mBinding.tvSuitLabel.text = String.format("MagFx磁力片套装")
            GlideHelper.loadImage(mBinding.ivSuitCover, tao.image, R.drawable.app_placeholder_course_169.getDrawable())
        }
    }

    private fun applyNews(newsList: List<NewEntity>?) {
        mBinding.tvNewsLabel.visibility = View.VISIBLE
        mBinding.tvNewsMore.visibility = View.VISIBLE
        mBinding.tvNewsLabel.text = "学院活动"
        mBinding.tvNewsMore.text = "更多"
        mBinding.tvNewsMore.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.app_right_arrow_gray, 0)
        mBinding.tvNewsMore.setOnClickListener { "学院活动more".showToast() }
        if (newsList.isNullOrEmpty()) {
            mBinding.rvNews.visibility = View.VISIBLE
        } else {
            mBinding.rvNews.visibility = View.GONE

            mBinding.rvNews.layoutManager = LinearLayoutManager(mContext)
            if (mBinding.rvNews.itemDecorationCount == 0) {
                mBinding.rvNews.addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        val position = parent.getChildAdapterPosition(view)
                        if (position != 0) {
                            outRect.top = SizeUtils.dp2px(6F)
                        }
                    }
                })
            }
            mBinding.rvNews.adapter = object : BaseQuickAdapter<NewEntity, BaseViewHolder>(R.layout.home_news_item, newsList) {
                override fun convert(helper: BaseViewHolder, item: NewEntity?) {
                    GlideHelper.loadImage(helper.getView(R.id.ivNewsCover), item?.image, R.drawable.app_placeholder_course.getDrawable())
                    helper.setText(R.id.tvNewsTitle, item?.title)
                    helper.setText(R.id.tvNewsDate, item?.createtime)
                    helper.setText(R.id.tvNewsNum, item?.views.toString())
                    helper.itemView.setOnClickListener { "学院活动 : ${helper.layoutPosition}".showToast() }
                }
            }
        }
    }
}