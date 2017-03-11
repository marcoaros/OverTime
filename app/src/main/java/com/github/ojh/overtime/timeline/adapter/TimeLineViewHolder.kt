package com.github.ojh.overtime.timeline.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.ojh.overtime.data.model.TimeLine
import kotlinx.android.synthetic.main.view_timeline.view.*

class TimeLineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: TimeLine) {
        itemView.tv_timeline_id.text = item.id.toString()
        itemView.tv_timeline_content.text = item.content
        itemView.tv_timeline_date.text = item.date.toString()
    }
}