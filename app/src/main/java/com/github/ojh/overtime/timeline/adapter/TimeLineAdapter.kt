package com.github.ojh.overtime.timeline.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.ojh.overtime.R
import com.github.ojh.overtime.data.model.TimeLine

/**
 * Created by ohjaehwan on 2017. 3. 2..
 */
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


    override fun setTimlines(timeLines: List<TimeLine>) {
        this.timeLines.addAll(timeLines)
    }

    override fun refresh() {
        notifyDataSetChanged()
    }
}