package com.github.ojh.overtime.timeline.list.adapter

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ojh.overtime.R
import com.github.ojh.overtime.data.model.TimeLine
import com.github.ojh.overtime.timeline.dialog.TimeLineSettingDialog
import com.github.ojh.overtime.timeline.list.TimeLineActivity
import com.github.ojh.overtime.util.load
import kotlinx.android.synthetic.main.view_timeline.view.*

class TimeLineAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
        TimeLineAdapterContract.Model, TimeLineAdapterContract.View {

    private val timeLines = mutableListOf<TimeLine>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.view_timeline, parent, false)
        return TimeLineViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as TimeLineViewHolder).bind(timeLines[position])
    }

    override fun getItemCount(): Int = timeLines.size


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
        timeLines[updatedPosition] = timeLine
        notifyItemChanged(updatedPosition)
    }

    override fun deleteTimeLine(timeLineId: Int) {
        val deletedPosition = timeLines.map { it.mId }.indexOf(timeLineId)
        timeLines.removeAt(deletedPosition)
        notifyItemRemoved(deletedPosition)

    }

    override fun refreshAll() {
        notifyDataSetChanged()
    }

    inner class TimeLineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var timeLine: TimeLine? = null

        init {
            val context = itemView.context

            itemView.setOnLongClickListener {
                timeLines[adapterPosition].mId?.let {
                    val dialog = TimeLineSettingDialog.newInstance(it)
                    dialog.show((context as TimeLineActivity).supportFragmentManager, TimeLineSettingDialog::class.java.simpleName)
                }
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

                mImgUri?.let {
                    itemView.iv_timeline_image.load(Uri.parse(it))
                }
            }
        }
    }
}