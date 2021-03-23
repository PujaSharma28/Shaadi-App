package com.example.test.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.test.model.*

import com.example.test.retrofit.RetrofitClient
import com.google.gson.Gson
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
 * User Repository class will interacts with UserViewModel class and return data to view model after execution of functionality
 */
object UserRepository {

    val data = MutableLiveData<ArrayList<UsersTableModel>>()

    fun getServicesApiCall(): MutableLiveData<ArrayList<UsersTableModel>> {

        val call = RetrofitClient.apiInterface.getServices()
        val userList :  ArrayList<UsersTableModel> =  ArrayList<UsersTableModel> ()

        call.enqueue(object: Callback<Any> {

            override fun onFailure(call: Call<Any>, t: Throwable) {
                // TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<Any>,
                response: Response<Any>
            ) {

                if(response.isSuccessful) {

                    val jsonObject = JSONObject(Gson().toJson(response.body()))
                    val jsonArray : JSONArray
                    jsonArray = jsonObject.getJSONArray("results")

                    (0 until jsonArray.length()).forEach {
                        val item = jsonArray.getJSONObject(it)
                        val name : Name = Name(item.getJSONObject("name").getString("title"),
                            item.getJSONObject("name").getString("first"),
                            item.getJSONObject("name").getString("last") )
                        val jsonLocation = item.getJSONObject("location")
                        val street = Street(jsonLocation.getJSONObject("street").getInt("number"),
                            jsonLocation.getJSONObject("street").getString("name"))
                        val coordinates = Coordinates(jsonLocation.getJSONObject("coordinates").getString("latitude"),
                            jsonLocation.getJSONObject("coordinates").getString("longitude"))

                        val timeZone = Timezone(jsonLocation.getJSONObject("timezone").getString("offset"),
                            jsonLocation.getJSONObject("timezone").getString("description"))
                        val location = Location(street, item.getJSONObject("location").getString("city"),
                            item.getJSONObject("location").getString("state"),
                            item.getJSONObject("location").getString("country"),
                            item.getJSONObject("location").getString("postcode"), coordinates, timeZone)

                        val login = Login(item.getJSONObject("login").getString("uuid"),
                            item.getJSONObject("login").getString("username"),
                            item.getJSONObject("login").getString("password"),
                            item.getJSONObject("login").getString("salt"),
                            item.getJSONObject("login").getString("md5"),
                            item.getJSONObject("login").getString("sha1"),
                            item.getJSONObject("login").getString("sha256"))
                        val id = Id(item.getJSONObject("id").getString("name"),
                            item.getJSONObject("id").optString("value"))

                        val userInfo = UsersTableModel(item.getString("gender"),
                            name, location,item.getString("email"), login,
                            item.getJSONObject("dob").getString("date"),
                            item.getJSONObject("dob").getInt("age"),
                            item.getJSONObject("registered").getString("date"),
                            item.getJSONObject("registered").getInt("age"),
                            item.getString("phone"), jsonObject.optString("cell"), id,
                            item.getJSONObject("picture").getString("large"),
                            item.getJSONObject("picture").getString("medium"),
                            item.getJSONObject("picture").getString("thumbnail"),
                            item.getString("nat"), 0

                        )

                        userList.add(userInfo)
                        data.postValue(userList)
                    }

                }

            }
        })
        return data
    }


    /*
     *fetch all users
     */
    fun fetchAllUsers() : ArrayList<UserRealmModel>{
        val realm = Realm.getDefaultInstance()

        val user = realm.where<UserRealmModel>().findAll()
        val dataList : ArrayList<UserRealmModel> = ArrayList()
        (0 until user.size).forEach {
            val data = user.get(it)
            if (data != null) {
                dataList.add(data)
            }
        }
        return dataList
    }

    /*
     Saves unique data based on primary key 'email'
     **/
    fun insertData(users: ArrayList<UsersTableModel>?) {
        if(users.isNullOrEmpty()) {
            return
        }

        val realm = Realm.getDefaultInstance()
        val existingUsers = realm.where<UserRealmModel>().findAll()

        realm.executeTransaction() {

            (0 until users.size).forEach {
                val userData = users.get(it)
                var isDuplicate = true
                (0 until existingUsers.size).forEach {
                    val existingUser = existingUsers.get(it)

                    Log.i("DEBUG : ", existingUser?.email!!)

                    if(existingUser.email.equals(userData.email)) {
                        isDuplicate = false
                    }
                }

                if(isDuplicate) {

                    val user = realm.createObject<UserRealmModel>(userData.email)
                    user.name = NameRealmModel().getModel(userData.name)
                    user.gender = userData.gender.toString()
                    user.dob_age = userData.dob_age!!
                    user.lastName = userData.name?.last!!
                    user.location = LocationRealmModel().getModel(userData.location)
                    user.login = userData.login?.uuid.toString()
                    user.userName = userData.login?.username.toString()
                    user.passwrd = userData.login?.password.toString()
                    user.dob_date = userData.dob_date.toString()
                    user.userSelection = userData.userSelection!!
                    user.imgRealmModel = PicRealmModel().getModel(userData.picture_large ?: "",
                        userData.picture_medium ?: "", userData.picture_thumbnail ?: "")
                }
            }
        }
    }
}