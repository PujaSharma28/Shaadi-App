package com.example.test.util

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/*
 * Main Application class
 */
class ShaadiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Init realm code
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("userdb.realm")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()
        Realm.setDefaultConfiguration(config)
    }

    companion object {
        lateinit var instance : ShaadiApplication
        private set
    }
}