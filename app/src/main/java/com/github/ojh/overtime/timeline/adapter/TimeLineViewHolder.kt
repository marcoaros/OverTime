package com.github.ojh.overtime.timeline.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.ojh.overtime.data.model.TimeLine
import kotlinx.android.synthetic.main.view_timeline.view.*

/**
 * Created by ohjaehwan on 2017. 3. 2..
 */
class TimeLineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: TimeLine) {
        itemView.tv_timeline.text = item.content
    }
}