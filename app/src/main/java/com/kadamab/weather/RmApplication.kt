package com.kadamab.weather

import android.app.Application
import com.kadamab.weather.Common.SharedPreference.init
import io.realm.Realm
import io.realm.RealmConfiguration


class RmApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        val config: RealmConfiguration = RealmConfiguration.Builder(this)
            .name(Realm.DEFAULT_REALM_NAME)
            .schemaVersion(4)
            .build()
        Realm.getInstance(config)
        Realm.setDefaultConfiguration(config);
    }
}