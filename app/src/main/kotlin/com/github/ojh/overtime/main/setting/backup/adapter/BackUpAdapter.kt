package com.github.ojh.overtime.main.setting.backup.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ojh.overtime.R
import com.github.ojh.overtime.util.ViewClickHandler
import kotlinx.android.synthetic.main.view_backup.view.*
import java.io.File

class BackUpAdapter(
        var onClickHandler: ViewClickHandler? = null

): RecyclerView.Adapter<BackUpViewHolder>() {

    val backUpFilePathList = mutableListOf<String>()

    override fun getItemCount(): Int {
        return backUpFilePathList.size
    }

    override fun onBindViewHolder(holder: BackUpViewHolder?, position: Int) {
        holder?.bind(backUpFilePathList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BackUpViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.view_backup, parent, false)
        val holder = BackUpViewHolder(view)

        view.setOnClickListener {
            onClickHandler?.invoke(it, holder.adapterPosition)
        }
        return holder
    }

    fun setBackupFilePath(backUpFilePathList: List<String>) {
        this.backUpFilePathList.addAll(backUpFilePathList)
        notifyDataSetChanged()
    }
}

class BackUpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(backUpFilePath: String) {
        itemView.tv_path.text = File(backUpFilePath).name
    }
}