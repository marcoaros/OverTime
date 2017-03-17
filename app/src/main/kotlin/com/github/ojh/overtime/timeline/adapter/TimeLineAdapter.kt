package com.github.ojh.overtime.timeline.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.ojh.overtime.R
import com.github.ojh.overtime.data.model.TimeLine

class TimeLineAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
        TimeLineAdapterContract.Model, TimeLineAdapterContract.View {

    private val timeLines = mutableListOf<TimeLine>()

    var clickListener: ((timeLineId: Int) -> Unit)? = null
    var longClickListener: ((timeLineId: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.view_timeline, parent, false)
        val viewHolder = TimeLineViewHolder(view, clickListener, longClickListener)

        return viewHolder
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

    override fun findTimeLineId(position: Int): Int? = timeLines[position].mId

    override fun setOnClickViewHolder(listener: (timeLineId: Int) -> Unit) {
        clickListener = listener
    }

    override fun setOnLongClickViewHolder(listener: (timeLineId: Int) -> Unit) {
        longClickListener = listener
    }

    override fun refreshAll() {
        notifyDataSetChanged()
    }

    override fun refreshItemChanged(position: Int) {
        notifyItemChanged(position)
    }

    override fun refreshItemInserted(position: Int) {
        notifyItemInserted(position)
    }

    override fun refreshItemRemoved(position: Int) {
        notifyItemRemoved(position)
    }
}