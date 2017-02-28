package com.github.ojh.overtime.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

/**
 * Created by OhJaeHwan on 2017-02-28.
 */
@RealmClass
open class Page : RealmObject() {

    @PrimaryKey
    open var id: Int = 0

    open var date: Date? = null
    open var content: String? = null
    open var pay: Int = 0
}