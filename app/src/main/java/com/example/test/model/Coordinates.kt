package com.example.test.model

import com.google.gson.annotations.SerializedName

data class Coordinates (

	@SerializedName("latitude") val latitude : String?,
	@SerializedName("longitude") val longitude : String?
)