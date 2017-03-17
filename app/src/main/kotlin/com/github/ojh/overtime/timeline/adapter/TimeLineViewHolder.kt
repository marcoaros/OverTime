package com.github.ojh.overtime.timeline.adapter

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.ojh.overtime.data.model.TimeLine
import com.github.ojh.overtime.util.load
import kotlinx.android.synthetic.main.view_timeline.view.*

class TimeLineViewHolder(
        itemView: View,
        clickListener: ((position: Int) -> Unit)?,
        longClickListener: ((position: Int) -> Unit)?

) : RecyclerView.ViewHolder(itemView) {

    private var timeLine: TimeLine? = null

    init {
        itemView.setOnClickListener {
            clickListener?.invoke(adapterPosition)
        }

        itemView.setOnLongClickListener {
            longClickListener?.invoke(adapterPosition)
            false
        }
    }

    fun bind(item: TimeLine) {
        this.timeLine = item
        with(item) {
            mId?.let {
                itemView.tv_timeline_id.text = it.toString()
            }

            mContent?.let {
                itemView.tv_timeline_content.text = it
            }

            mDate?.let {
                itemView.tv_timeline_date.text = it.toString()
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