package com.kadamab.weather

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration




class RealmApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        val config: RealmConfiguration = RealmConfiguration.Builder(this)
            .name("myRealmExaple.realm").build()
        Realm.setDefaultConfiguration(config)
    }
}