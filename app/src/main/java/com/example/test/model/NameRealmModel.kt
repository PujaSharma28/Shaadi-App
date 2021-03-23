package com.example.test.model

import io.realm.Realm
import io.realm.RealmObject
import io.realm.kotlin.createObject

/*
  * User model class to update the data to UI and update the data to realm
 */

open class NameRealmModel (

    var title : String = "",
    var first : String = "",
    var last : String = ""

) : RealmObject() {
    fun getModel(userData : Name?) : NameRealmModel {
        val realm = Realm.getDefaultInstance()
        val nameRealmModel = realm.createObject<NameRealmModel>()
        nameRealmModel.title = userData?.title.toString()
        nameRealmModel.first = userData?.first.toString()
        nameRealmModel.last = userData?.last.toString()
        return nameRealmModel

    }


}
