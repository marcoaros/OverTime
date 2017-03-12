package com.github.ojh.overtime.timeline.viewholder

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.ojh.overtime.R
import com.github.ojh.overtime.data.model.TimeLine
import com.github.ojh.overtime.data.model.TimeLine.Companion.KEY_TIMELINE
import com.github.ojh.overtime.util.load
import com.github.ojh.overtime.write.WriteActivity
import kotlinx.android.synthetic.main.view_timeline.view.*
import org.parceler.Parcels

class TimeLineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var timeLine: TimeLine? = null

    init {
        val context = itemView.context

        itemView.setOnLongClickListener {
            AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.timeline_modify_dialog_title))
                    .setMessage(context.getString(R.string.timeline_modify_dialog_message))
                    .setNegativeButton(context.getString(R.string.common_no), null)
                    .setPositiveButton(context.getString(R.string.common_yes), { _, _ ->
                        val intent = Intent(context, WriteActivity::class.java)
                        intent.putExtra(KEY_TIMELINE, Parcels.wrap(TimeLine::class.java, timeLine))
                        context.startActivity(intent)
                    }).show()
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