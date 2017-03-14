package com.github.ojh.overtime.timeline.list.adapter

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
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
        val updatedPosition = timeLines.map { it.id }.indexOf(timeLine.id)
        notifyItemChanged(updatedPosition)
    }

    override fun deleteTimeLine(timeLineId: Int) {
        val id = timeLines.map { it.id }.indexOf(timeLineId)
        Log.d("timelineadapter id = ", ""+id)
//        timeLines.removeIf { it.id == timeLineId }
//        notifyDataSetChanged()
//        val deletePosition = timeLines.map { it.id }.indexOf(timeLineId)
//        timeLines.removeAt(deletePosition)
//        notifyItemRemoved(deletePosition)

    }

    override fun refreshAll() {
        notifyDataSetChanged()
    }

    inner class TimeLineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var timeLine: TimeLine? = null

        init {
            val context = itemView.context

            itemView.setOnLongClickListener {
                timeLines[adapterPosition].id?.let {
                    Log.d("dialog id : ", "" + it)
                    val dialog = TimeLineSettingDialog.newInstance(it)
                    dialog.show((context as TimeLineActivity).supportFragmentManager, "option")
                }
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
}