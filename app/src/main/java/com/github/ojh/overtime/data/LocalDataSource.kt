package com.github.ojh.overtime.data

import com.github.ojh.overtime.data.model.TimeLine
import io.reactivex.Observable

/**
 * Created by OhJaeHwan on 2017-02-28.
 */
class LocalDataSource : DataSource {
    override fun getTimeLineData(): Observable<List<TimeLine>> {
        return Observable.just(listOf())
    }
}