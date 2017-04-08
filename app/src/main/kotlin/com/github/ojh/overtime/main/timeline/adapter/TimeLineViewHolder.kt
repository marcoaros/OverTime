package com.github.ojh.overtime.main.timeline.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.github.ojh.overtime.R
import com.github.ojh.overtime.data.TimeLine
import com.github.ojh.overtime.util.ViewClickHandler
import com.github.ojh.overtime.util.extensions.loadFromPalette
import com.github.ojh.overtime.util.extensions.toFormatString
import com.github.ojh.overtime.util.extensions.toWeekString
import kotlinx.android.synthetic.main.view_timeline.view.*

class TimeLineViewHolder(
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

            //다른 포지션에 색깔요상하게 되는거 방지용
            itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            tv_timeline_content.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryText_brown))


            val imgUrl = timeLine.mImgUri

            if (imgUrl != null) {
                iv_timeline_image.loadFromPalette(imgUrl, { rgb, _, bodyColor ->
                    itemView.setBackgroundColor(rgb)
                    tv_timeline_content.setTextColor(bodyColor)
                    iv_timeline_image.visibility = View.VISIBLE
                })
            } else {
                Glide.clear(iv_timeline_image)
                iv_timeline_image.visibility = View.GONE
            }
        }
    }
}