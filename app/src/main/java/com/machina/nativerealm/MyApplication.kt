package com.machina.nativerealm

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val config = RealmConfiguration.
                Builder().
                deleteRealmIfMigrationNeeded().
                schemaVersion(0).
                name("myRealm.realm").build()
        Realm.setDefaultConfiguration(config)
    }

}