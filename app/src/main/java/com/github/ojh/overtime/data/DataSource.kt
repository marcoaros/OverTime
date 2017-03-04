package com.github.ojh.overtime.data

import com.github.ojh.overtime.data.model.TimeLine
import io.reactivex.Observable

/**
 * Created by OhJaeHwan on 2017-02-28.
 */
interface DataSource {
    fun getTimeLineData(): Observable<List<TimeLine>>
}