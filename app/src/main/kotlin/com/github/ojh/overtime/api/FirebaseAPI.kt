package com.github.ojh.overtime.api

import com.github.ojh.overtime.data.TimeLine
import io.reactivex.Flowable
import retrofit2.http.GET

interface FirebaseAPI {

    @GET("/timeLine.json")
    fun getTimeLines(): Flowable<List<TimeLine>>
}