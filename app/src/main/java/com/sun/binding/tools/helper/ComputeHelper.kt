package com.sun.binding.tools.helper

import com.sun.binding.tools.util.Utils
import java.math.BigDecimal
import kotlin.math.min

object ComputeHelper {
    fun computeImageCoverSize(maxSize: Int, imageSize: List<Long>?): Array<Int> {
        val minSize = maxSize / 3

        // 当前图片比例值
        val imageRatio = getCoverRatio(imageSize)
        // 当前图片宽高
        val imageInfo = getCoverInfo(imageSize, minSize)

        val width: Int
        val height: Int

        when {
            imageInfo[0] > imageInfo[1] -> {// 宽比高大
                width = computeSize(minSize, maxSize, imageInfo[0])
                height = min(maxSize, (width * imageRatio).toInt())
            }
            imageInfo[0] < imageInfo[1] -> {// 高比宽大
                height = computeSize(minSize, maxSize, imageInfo[1])
                width = min(maxSize, (height / imageRatio).toInt())
            }
            else -> {
                width = computeSize(minSize, maxSize, imageInfo[0])
                height = computeSize(minSize, maxSize, imageInfo[1])
            }
        }

        return arrayOf(width, height)
    }

    fun computeVideoCoverSize(maxSize: Int, videoSize: List<Long>?): Array<Int> {
        val maxWidth = maxSize * 1
        val maxHeight = (maxWidth * 0.8).toInt()
        val minSize = maxWidth / 3

        // 视频比例值
        val videoRatio = getCoverRatio(videoSize)
        // 视频宽高
        val videoInfo = getCoverInfo(videoSize, minSize)

        val width: Int
        val height: Int

        when {
            videoInfo[0] > videoInfo[1] -> {// 宽比高大
                width = computeSize(minSize, maxWidth, videoInfo[0])
                height = min(maxHeight, (width * videoRatio).toInt())
            }
            videoInfo[0] < videoInfo[1] -> {// 高比宽大
                height = computeSize(minSize, maxHeight, videoInfo[1])
                width = min(maxWidth, (height / videoRatio).toInt())
            }
            else -> {
                height = computeSize(minSize, maxHeight, videoInfo[1])
                width = height
            }
        }

        return arrayOf(width, height)
    }

    private fun getCoverRatio(coverSize: List<Long>?) = if (coverSize.isNullOrEmpty() || coverSize.size < 2) {
        1F
    } else {
        Utils.div(coverSize[1].toDouble(), coverSize[0].toDouble(), 2).toFloat()
    }

    private fun getCoverInfo(coverSize: List<Long>?, minSize: Int) = if (coverSize.isNullOrEmpty() || coverSize.size < 2) {
        arrayOf(minSize, minSize)
    } else {
        arrayOf(coverSize[0].toInt(), coverSize[1].toInt())
    }

    private fun computeSize(minSize: Int, maxSize: Int, targetSize: Int): Int = when {
        targetSize < minSize -> minSize
        targetSize > maxSize -> maxSize
        else -> targetSize
    }
}