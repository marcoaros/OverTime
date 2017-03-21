package com.github.ojh.overtime.timeline.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.ojh.overtime.R
import com.github.ojh.overtime.data.TimeLine
import com.github.ojh.overtime.util.ViewClickHandler

class TimeLineAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
        TimeLineAdapterContract.Model, TimeLineAdapterContract.View {

    private val timeLines = mutableListOf<TimeLine>()

    private var itemClickListener: ViewClickHandler? = null
    private var settingClickListener: ViewClickHandler? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.view_timeline, parent, false)
        val viewHolder = TimeLineViewHolder(view, itemClickListener, settingClickListener)

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as TimeLineViewHolder).bind(timeLines[position])
    }

    override fun getItemCount(): Int = timeLines.size

    override fun getSize() = itemCount

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
        if (updatedPosition != -1) {
            timeLines[updatedPosition] = timeLine
            notifyItemChanged(updatedPosition)
        }
    }

    override fun deleteTimeLine(timeLineId: Int) {
        val deletedPosition = timeLines.map { it.mId }.indexOf(timeLineId)
        if(deletedPosition != -1) {
            timeLines.removeAt(deletedPosition)
            notifyItemRemoved(deletedPosition)
        }
    }

    override fun findTimeLineId(position: Int): Int? = timeLines[position].mId

    override fun setOnClickViewHolder(listener: ViewClickHandler) {
        itemClickListener = listener
    }

    override fun setOnClickSetting(listener: ViewClickHandler) {
        settingClickListener = listener
    }

    override fun refreshAll() {
        notifyDataSetChanged()
    }
}