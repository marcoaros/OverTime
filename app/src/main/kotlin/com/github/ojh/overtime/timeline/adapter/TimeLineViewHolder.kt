package com.github.ojh.overtime.timeline.adapter

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.ojh.overtime.data.model.TimeLine
import com.github.ojh.overtime.util.load
import kotlinx.android.synthetic.main.view_timeline.view.*

class TimeLineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: TimeLine) {
        itemView.tv_timeline_id.text = item.id.toString()
        itemView.tv_timeline_content.text = item.content
        itemView.tv_timeline_date.text = item.date.toString()
        itemView.iv_timeline_image.load(Uri.parse(item.imgUri))
    }
}