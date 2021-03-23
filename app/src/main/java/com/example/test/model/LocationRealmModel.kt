package com.example.test.model

import com.google.gson.annotations.SerializedName
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.kotlin.createObject

/*
  * User model class to update the data to UI and update the data to realm
 */

open class LocationRealmModel(

    var streetNumber: Int = 0,
    var streetName: String = "",

    var city: String = "",
    var state: String = "",
    var country: String = "",
    var postcode: String = "",
    var latitude: String = "",
    var longitude: String = "",
    var timezoneOffset: String = "",
    var timezoneDesc: String = ""



) : RealmObject() {
    fun getModel(userData: Location?): LocationRealmModel {

        val realm = Realm.getDefaultInstance()
        val locationRealmModel = realm.createObject<LocationRealmModel>()
        locationRealmModel.streetNumber = userData?.street?.number?.toInt() ?: 0
        locationRealmModel.streetName = userData?.street?.name?.toString() ?: ""
        locationRealmModel.city = userData?.city?.toString() ?: ""
        locationRealmModel.state = userData?.state?.toString() ?: ""
        locationRealmModel.country = userData?.country?.toString() ?: ""
        locationRealmModel.postcode = userData?.postcode?.toString() ?: ""
        locationRealmModel.latitude = userData?.coordinates?.latitude?.toString() ?: ""
        locationRealmModel.longitude = userData?.coordinates?.longitude?.toString() ?: ""
        locationRealmModel.timezoneOffset = userData?.timezone?.offset?.toString() ?: ""
        locationRealmModel.timezoneDesc = userData?.timezone?.description?.toString() ?: ""
        return locationRealmModel

    }


}
