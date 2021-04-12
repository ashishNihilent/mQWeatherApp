package com.kadamab.weather.rm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class City : RealmObject() {
    @PrimaryKey
    var name: String? = null
}