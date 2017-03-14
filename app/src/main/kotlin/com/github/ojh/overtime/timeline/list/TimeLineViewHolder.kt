package com.github.ojh.overtime.timeline.list

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.ojh.overtime.data.model.TimeLine
import com.github.ojh.overtime.timeline.list.TimeLineActivity
import com.github.ojh.overtime.timeline.dialog.TimeLineSettingDialog
import com.github.ojh.overtime.util.load
import kotlinx.android.synthetic.main.view_timeline.view.*

class TimeLineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var timeLine: TimeLine? = null

    init {
        val context = itemView.context

        itemView.setOnLongClickListener {
            val dialog = TimeLineSettingDialog.newInstance(timeLine?.id!!)
            dialog.show((context as TimeLineActivity).supportFragmentManager, "option")

            false
        }
    }

    fun bind(item: TimeLine) {
        this.timeLine = item
        with(item) {
            id?.let {
                itemView.tv_timeline_id.text = it.toString()
            }

            content?.let {
                itemView.tv_timeline_content.text = it
            }

            date?.let {
                itemView.tv_timeline_date.text = it.toString()
            }

            imgUri?.let {
                itemView.iv_timeline_image.load(Uri.parse(it))
            }
        }
    }
}