package com.example.test.model

import com.google.gson.annotations.SerializedName

data class UsersTableModel(
    @SerializedName("gender") val gender : String?,
    @SerializedName("name") val name : Name?,
    @SerializedName("location") val location : Location?,
    @SerializedName("email") val email : String,
    @SerializedName("login") val login : Login?,
    @SerializedName("date") val dob_date : String?,
    @SerializedName("age") val dob_age : Int?,
    @SerializedName("date") val registered_date : String?,
    @SerializedName("age") val registered_age : Int?,
    @SerializedName("phone") val phone : String?,
    @SerializedName("cell") val cell : String?,
    @SerializedName("id") val id : Id?,
    @SerializedName("large") val picture_large : String?,
    @SerializedName("medium") val picture_medium : String?,
    @SerializedName("thumbnail") val picture_thumbnail : String?,
    @SerializedName("nat") val nat : String?,
    @SerializedName("userSelection") val userSelection : Int?
)