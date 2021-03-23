package com.example.test.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("/api/?results=10")
    fun getServices() : Call<Any>

}