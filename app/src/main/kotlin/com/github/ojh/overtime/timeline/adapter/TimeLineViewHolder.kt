package com.github.ojh.overtime.timeline.adapter

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.ojh.overtime.data.TimeLine
import com.github.ojh.overtime.util.ViewClickHandler
import com.github.ojh.overtime.util.load
import com.github.ojh.overtime.util.toFormatString
import com.github.ojh.overtime.util.toWeekString
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

    fun bind(item: TimeLine) {
        this.timeLine = item
        with(item) {
            mContent?.let {
                itemView.tv_timeline_content.text = it
            }

            mDate?.let {
                itemView.tv_timeline_date.text = it.toFormatString()
                itemView.tv_timeline_day.text = it.toWeekString()
            }

            if (mImgUri != null) {
                itemView.iv_timeline_image.load(Uri.parse(mImgUri))
                itemView.iv_timeline_image.visibility = View.VISIBLE
            } else {
                itemView.iv_timeline_image.visibility = View.GONE
            }
        }
    }
}