package com.example.test.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/*
  * User model class to update the data to UI and update the data to realm
 */

open class UserRealmModel (

    @PrimaryKey var email: String = "",

    var gender: String = "",

    var name: NameRealmModel? = null,

    var lastName: String = "",

    var location: LocationRealmModel? = null,

    var login: String = "",

    var userName: String = "",

    var passwrd: String = "",

    var dob_date: String = "",

    var dob_age: Int = 0,

    var imgRealmModel: PicRealmModel? = null,

    /*
      * user selection varibale
      * userSelection = 0 .. user is neither selected nor declined
      * userSelection = 1 .. accepted
      * userSelection = 2 .. declined
     */
    var userSelection: Int = 0

) : RealmObject()
