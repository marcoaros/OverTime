package com.github.ojh.overtime.data.remote

import com.github.ojh.overtime.api.FirebaseAPI
import com.github.ojh.overtime.data.DataSource
import com.github.ojh.overtime.data.TimeLine
import com.github.ojh.overtime.util.FilterType
import io.reactivex.Flowable
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
        private val firebaseAPI: FirebaseAPI
)  : DataSource {
    override fun getTimeLine(timeLineId: Int): Flowable<TimeLine> {
        return Flowable.just(null)
    }

    override fun getTimeLines(filter: FilterType): Flowable<List<TimeLine>> {
        return firebaseAPI.getTimeLines()
    }

    override fun saveTimeLine(timeLine: TimeLine) {

    }

    override fun updateTimeLine(timeLine: TimeLine) {

    }

    override fun deleteTimeLine(timeLineId: Int) {

    }
}