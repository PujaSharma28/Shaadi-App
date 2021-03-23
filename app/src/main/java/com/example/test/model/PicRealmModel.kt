package com.example.test.model

import com.google.gson.annotations.SerializedName
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.kotlin.createObject

/*
  * User model class to update the data to UI and update the data to realm
 */

open class PicRealmModel (

    var large : String = "",
    var medium : String = "",
    var thumnail : String = ""

) : RealmObject() {
    fun getModel(large: String, medium: String, thumbnail: String) : PicRealmModel {
        val realm = Realm.getDefaultInstance()
        val picRealmModel = realm.createObject<PicRealmModel>()
        picRealmModel.large = large
        picRealmModel.medium = medium
        picRealmModel.thumnail = thumbnail
        return picRealmModel

    }


}
