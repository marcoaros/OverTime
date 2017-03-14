package com.github.ojh.overtime.timeline.list.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.ojh.overtime.R
import com.github.ojh.overtime.data.model.TimeLine
import com.github.ojh.overtime.timeline.list.TimeLineViewHolder

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
        val deletePosition = timeLines.map { it.id }.indexOf(timeLineId)
        try {
            timeLines.removeAt(deletePosition)
            notifyItemRemoved(deletePosition)
        } catch (e: ArrayIndexOutOfBoundsException) {

        }
    }

    override fun refreshAll() {
        notifyDataSetChanged()
    }
}