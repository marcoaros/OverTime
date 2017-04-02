package com.github.ojh.overtime.data.remote

import com.github.ojh.overtime.api.FirebaseAPI
import com.github.ojh.overtime.data.DataSource
import com.github.ojh.overtime.data.FilterType
import com.github.ojh.overtime.data.TimeLine
import io.reactivex.Flowable
import java.util.*
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
        private val firebaseAPI: FirebaseAPI
)  : DataSource {

    override fun getWrittenDates(): Flowable<List<Date>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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

    override fun backUpData(): Flowable<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun restoreData(internalFilePath: String, exportFilePath: String): Flowable<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}