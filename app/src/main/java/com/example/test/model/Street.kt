package com.example.test.model

import com.google.gson.annotations.SerializedName

data class Street (

	@SerializedName("number") val number : Int?,
	@SerializedName("name") val name : String?
)