package com.github.ojh.overtime.main.timeline.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.BitmapRequestBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.ImageViewTarget
import com.github.ojh.overtime.data.TimeLine
import com.github.ojh.overtime.util.ViewClickHandler
import com.github.ojh.overtime.util.extensions.toFormatString
import com.github.ojh.overtime.util.extensions.toWeekString
import com.github.ojh.overtime.util.palette.PaletteBitmap
import com.github.ojh.overtime.util.palette.PaletteBitmapTranscoder
import com.github.ojh.overtime.util.palette.PaletteUtil
import kotlinx.android.synthetic.main.view_timeline.view.*
