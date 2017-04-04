package com.github.ojh.overtime.main.timeline.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.BitmapRequestBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.ImageViewTarget
import com.github.ojh.overtime.R
import com.github.ojh.overtime.data.TimeLine
import com.github.ojh.overtime.util.ViewClickHandler
import com.github.ojh.overtime.util.extensions.toFormatString
import com.github.ojh.overtime.util.extensions.toWeekString
import com.github.ojh.overtime.util.palette.PaletteBitmap
import com.github.ojh.overtime.util.palette.PaletteBitmapTranscoder
import com.github.ojh.overtime.util.palette.PaletteUtil
import kotlinx.android.synthetic.main.view_timeline.view.*
import kotlin.LazyThreadSafetyMode.NONE

class TimeLineAdapter(
        val context: Context

) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
        TimeLineAdapterContract.Model, TimeLineAdapterContract.View {

    private val timeLines = mutableListOf<TimeLine>()

    private var itemClickListener: ViewClickHandler? = null
    private var settingClickListener: ViewClickHandler? = null

    private val glideRequest: BitmapRequestBuilder<String, PaletteBitmap> by lazy(NONE) {
        Glide.with(context)
                .fromString()
                .asBitmap()
                .transcode(PaletteBitmapTranscoder(context), PaletteBitmap::class.java)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.view_timeline, parent, false)
        val viewHolder = TimeLineViewHolder(view, itemClickListener, settingClickListener)

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as TimeLineViewHolder).bind(timeLines[position])
    }

    override fun getItemCount(): Int = timeLines.size

    override fun getSize() = itemCount

    override fun setTimeLines(timeLines: List<TimeLine>) {
        this.timeLines.clear()
        this.timeLines.addAll(timeLines)
    }

    override fun addTimeLine(timeLine: TimeLine, position: Int) {
        timeLines.add(position, timeLine)
        notifyItemInserted(position)
    }

    override fun updateTimeLine(timeLine: TimeLine) {
        val updatedPosition = timeLines.map { it.mId }.indexOf(timeLine.mId)
        if (updatedPosition != -1) {
            timeLines[updatedPosition] = timeLine
            notifyItemChanged(updatedPosition)
        }
    }

    override fun deleteTimeLine(timeLineId: Int) {
        val deletedPosition = timeLines.map { it.mId }.indexOf(timeLineId)
        if (deletedPosition != -1) {
            timeLines.removeAt(deletedPosition)
            notifyItemRemoved(deletedPosition)
        }
    }

    override fun findTimeLineId(position: Int): Int? = timeLines[position].mId

    override fun setOnClickViewHolder(listener: ViewClickHandler) {
        itemClickListener = listener
    }

    override fun setOnClickSetting(listener: ViewClickHandler) {
        settingClickListener = listener
    }

    override fun refreshAll() {
        notifyDataSetChanged()
    }


    inner class TimeLineViewHolder(
            itemView: View,
            itemClickListener: ViewClickHandler?,
            settingClickListener: ViewClickHandler?

    ) : RecyclerView.ViewHolder(itemView) {

        private var timeLine: TimeLine? = null

        init {
            itemView.setOnClickListener {
                itemClickListener?.invoke(it, adapterPosition)
            }

            itemView.iv_setting.setOnClickListener {
                settingClickListener?.invoke(it, adapterPosition)
            }
        }

        fun bind(timeLine: TimeLine) {
            this.timeLine = timeLine
            with(itemView) {
                timeLine.mContent?.let {
                    tv_timeline_content.text = it
                }

                timeLine.mDate?.let {
                    tv_timeline_date.text = it.toFormatString()
                    tv_timeline_day.text = it.toWeekString()
                }

                if (timeLine.mImgUri != null) {
                    glideRequest
                            .load(timeLine.mImgUri)
                            .into(object : ImageViewTarget<PaletteBitmap>(iv_timeline_image) {
                                override fun setResource(resource: PaletteBitmap?) {
                                    super.view.setImageBitmap(resource?.bitmap)

                                    PaletteUtil.getSwatch(resource?.palette)
                                            ?.let {
                                                itemView.setBackgroundColor(it.rgb)
                                                tv_timeline_content.setTextColor(it.bodyTextColor)
                                            }

                                }
                            })

                    iv_timeline_image.visibility = View.VISIBLE
                } else {
                    iv_timeline_image.visibility = View.GONE
                }
            }
        }
    }
}