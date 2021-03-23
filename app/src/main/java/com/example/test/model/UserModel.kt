package com.example.test.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/*
  * User model class to update the data to UI and update the data to realm
 */

open class UserModel (

    @PrimaryKey var email: String = "",

    var Gender: String = "",

    var name: String = "",

    var lastName: String = "",

    var country: String = "",

    var login: String = "",

    var userName: String = "",

    var passwrd: String = "",

    var dob_date: String = "",

    var dob_age: Int = 0,

    var img: String = "",

    //
    /*
      * user selection varibale
      * userSelection = 0 .. user is neither selected not declined
      * userSelection = 1 .. user is neither selected
      * userSelection = 2 .. user is  not selected
     */
    var userSelection: Int = 0,

    var manuallyRemoved: Boolean = false

) : RealmObject()
