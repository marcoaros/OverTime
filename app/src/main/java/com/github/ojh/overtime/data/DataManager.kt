package com.github.ojh.overtime.data

import com.github.ojh.overtime.data.model.TimeLine
import io.reactivex.Observable

/**
 * Created by ohjaehwan on 2017. 3. 2..
 */
class DataManager {
    private val dataSource = LocalDataSource()

    fun getTimeLineData(): Observable<List<TimeLine>> {
        return dataSource.getTimeLineData()
    }
}