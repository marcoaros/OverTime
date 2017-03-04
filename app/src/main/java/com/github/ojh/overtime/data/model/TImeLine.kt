package com.github.ojh.overtime.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

/**
 * Created by ohjaehwan on 2017. 3. 2..
 */
@RealmClass
open class TimeLine : RealmObject() {

    @PrimaryKey
    open var id: Int = 0

    open var date: Date? = null
    open var content: String? = null
    open var pay: Int = 0
}