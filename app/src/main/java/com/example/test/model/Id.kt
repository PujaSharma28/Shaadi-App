package com.example.test.model

import com.google.gson.annotations.SerializedName

data class Id (

	@SerializedName("name") val name : String?,
	@SerializedName("value") val value : String?
)