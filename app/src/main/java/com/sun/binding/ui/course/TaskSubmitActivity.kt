package com.sun.binding.ui.course

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lxj.xpopup.XPopup
import com.sun.binding.R
import com.sun.binding.constants.RequestCodeConstant.CHOOSE_PHOTO
import com.sun.binding.constants.RequestCodeConstant.CHOOSE_TAKE
import com.sun.binding.constants.RequestCodeConstant.CHOOSE_VIDEO
import com.sun.binding.databinding.TaskSubmitActivityBinding
import com.sun.binding.model.course.TaskSubmitViewModel
import com.sun.binding.tools.ext.condition
import com.sun.binding.tools.ext.setWhiteStatusBar
import com.sun.binding.tools.ext.showToast
import com.sun.binding.tools.helper.GlideHelper
import com.sun.binding.tools.helper.MatisseHelper
import com.sun.binding.tools.manager.AppFileManager
import com.sun.binding.tools.tool.getDrawable
import com.sun.binding.ui.base.BaseActivity
import com.sun.binding.widget.DisallowInterceptTouchListener
import com.sun.binding.widget.decoration.GridSpaceItemDecoration
import com.sun.binding.widget.dialog.BottomOptionPopupWindow
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.internal.utils.MediaStoreCompat
import org.koin.android.viewmodel.ext.android.viewModel

class TaskSubmitActivity : BaseActivity<TaskSubmitViewModel, TaskSubmitActivityBinding>() {

    companion object {
        const val SHOOT = "拍摄"
        const val ALBUM = "从手机相册中选择"
    }

    override val viewModel: TaskSubmitViewModel by viewModel()

    private val submitPhotoAdapter: SubmitPhotoAdapter = SubmitPhotoAdapter(mutableListOf(SubmitPhotoAdapter.UN_LOAD))

    // 底部弹出层
    private lateinit var photoSelectWindow: BottomOptionPopupWindow
    // 拍照兼容类
    private val mediaStoreCompat: MediaStoreCompat = MediaStoreCompat(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_submit_activity)
        setWhiteStatusBar()

        initView()
    }

    override fun rightClick() {
        super.rightClick()
        val data = mutableMapOf<String, Any?>().apply {
            put("intro", mBinding.etIntro.text.toString())
            put("photo", submitPhotoAdapter.data)
            put("videoPath", viewModel.taskSubmitConfig.videoPath.get())
            put("videoCover", viewModel.taskSubmitConfig.videoCover.get())
        }
        XPopup.Builder(this).asConfirm("上传", JsonUtils.formatJson(GsonUtils.toJson(data)), "取消", "确定", null, null, true).show()
    }

    private fun initView() {
        mBinding.run {
            adapter = submitPhotoAdapter.apply {
                setOnItemChildClickListener { _, view, position ->
                    val photoList = submitPhotoAdapter.data
                    val item = photoList[position]
                    when (view.id) {
                        R.id.ivChoose -> {
                            if (item == SubmitPhotoAdapter.UN_LOAD) {
                                if (!this@TaskSubmitActivity::photoSelectWindow.isInitialized) {
                                    photoSelectWindow =
                                        (XPopup.Builder(mContext).asCustom(
                                            BottomOptionPopupWindow(
                                                mContext,
                                                mutableListOf(SHOOT, ALBUM)
                                            )
                                        ) as BottomOptionPopupWindow).also { window ->
                                            window.selectedOption.observe(mContext, Observer { option ->
                                                if (option == SHOOT) {
                                                    MatisseHelper.takePhoto(mContext, mediaStoreCompat, CHOOSE_TAKE)
                                                } else if (option == ALBUM) {
                                                    MatisseHelper.choosePhoto(
                                                        mContext, this@TaskSubmitActivity.viewModel.taskSubmitConfig.maxPhotoSize - submitPhotoAdapter.data.size + 1, CHOOSE_PHOTO
                                                    )
                                                }
                                            })
                                        }
                                }
                                photoSelectWindow.show()
                            }
                        }
                        R.id.ivPhotoDel -> {
                            if (item != SubmitPhotoAdapter.UN_LOAD) {
                                submitPhotoAdapter.remove(position)
                                if (photoList.size < this@TaskSubmitActivity.viewModel.taskSubmitConfig.maxPhotoSize && !photoList.contains(SubmitPhotoAdapter.UN_LOAD)) {
                                    photoList.add(SubmitPhotoAdapter.UN_LOAD)
                                }
                                submitPhotoAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
            itemDecoration = GridSpaceItemDecoration(SizeUtils.dp2px(15F), false)
            videoPlayClick = View.OnClickListener {
                if (this@TaskSubmitActivity.viewModel.taskSubmitConfig.hasSelectedVideo.get().condition) {
                    this@TaskSubmitActivity.viewModel.taskSubmitConfig.videoPath.get().showToast()
                }
            }

            // 输入框初始化
            etIntro.run {
                addTextChangedListener { this@TaskSubmitActivity.viewModel.taskSubmitConfig.updateIntroSize(it?.length ?: 0) }
                setOnTouchListener(DisallowInterceptTouchListener())
            }

            // 动态设置视频容器大小
            val width = ScreenUtils.getScreenWidth() - SizeUtils.dp2px(30F)
            val height = (width / 3.45F).toInt()
            videoContainer.run {
                layoutParams.width = width
                layoutParams.height = height
                setOnClickListener {
                    if (!this@TaskSubmitActivity.viewModel.taskSubmitConfig.hasSelectedVideo.get().condition) {
                        MatisseHelper.chooseVideo(mContext, 1, CHOOSE_VIDEO)
                    }
                }
            }
            ivVideoCover.run {
                layoutParams.height = height - SizeUtils.dp2px(20F)
                layoutParams.width = ((height - SizeUtils.dp2px(20F)) * 1.7875F).toInt()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CHOOSE_TAKE && resultCode == RESULT_OK) {
            // 拍照
            insertPhoto(mutableListOf(mediaStoreCompat.currentPhotoPath))
        } else if (requestCode == CHOOSE_PHOTO && resultCode == RESULT_OK) {
            // 图片选择
            insertPhoto(Matisse.obtainPathResult(data))
        } else if (requestCode == CHOOSE_VIDEO && resultCode == RESULT_OK) {
            // 视频
            val result = Matisse.obtainPathResult(data)
            if (!result.isNullOrEmpty()) {
                generateVideoCover(result[0])
            }
        }
    }

    // 插入图片数据
    private fun insertPhoto(result: MutableList<String>) {
        if (!result.isNullOrEmpty()) {
            submitPhotoAdapter.data.remove(SubmitPhotoAdapter.UN_LOAD)// 先删除占位的item
            submitPhotoAdapter.data.addAll(result)// 再将选中的图片添加进数组
            if (submitPhotoAdapter.data.size < viewModel.taskSubmitConfig.maxPhotoSize) {
                submitPhotoAdapter.data.add(SubmitPhotoAdapter.UN_LOAD)// 如果已选图片数量小于最大可选数，则添加占位的item
            }
            submitPhotoAdapter.notifyDataSetChanged()// 刷新UI
        }
    }

    // 根据视频文件生产封面图文件
    private fun generateVideoCover(videoPath: String) {
        Glide.with(this).asBitmap().load(videoPath).into(object : BitmapImageViewTarget(mBinding.ivVideoCover) {
            override fun setResource(resource: Bitmap?) {
                super.setResource(resource)
                resource?.let {
                    val coverPath = AppFileManager.getCoverCachePath(videoPath)
                    val result = ImageUtils.save(it, coverPath, Bitmap.CompressFormat.JPEG)
                    viewModel.taskSubmitConfig.updateSelectVideo(videoPath, if (result) coverPath else "")
                }
            }
        })
    }
}

internal class SubmitPhotoAdapter(data: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.task_submit_photo_item, data) {
    companion object {
        const val UN_LOAD = "un_load"
    }

    override fun convert(helper: BaseViewHolder, item: String?) {
        if (item == UN_LOAD) {
            helper.setGone(R.id.ivChoose, true)
            helper.setGone(R.id.ivPhoto, false)
            helper.setGone(R.id.ivPhotoDel, false)
            if (helper.layoutPosition == 0) {
                helper.setImageResource(R.id.ivChoose, R.drawable.course_icon_submit_photo)
            } else {
                helper.setImageResource(R.id.ivChoose, R.drawable.course_icon_submit_photo_add)
            }
        } else {
            helper.setGone(R.id.ivChoose, false)
            helper.setGone(R.id.ivPhoto, true)
            helper.setGone(R.id.ivPhotoDel, true)
            GlideHelper.loadImage(helper.getView(R.id.ivPhoto), item, R.drawable.app_placeholder_course.getDrawable())
        }
        helper.addOnClickListener(R.id.ivChoose, R.id.ivPhotoDel)
    }
}