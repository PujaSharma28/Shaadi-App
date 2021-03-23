package com.example.test.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.model.*
import com.example.test.repository.UserRepository

/*
 * User view model class which interacts with repository and sends data to the UI
 */
class UserViewModel : ViewModel(), LifecycleObserver {

    var userLiveData:MutableLiveData<ArrayList<UsersTableModel>>? = null
    var userRealmDetails:MutableLiveData<ArrayList<UserRealmModel>> = MutableLiveData<ArrayList<UserRealmModel>>()

    fun getUsersResult() : LiveData<ArrayList<UserRealmModel>> {
        return userRealmDetails
    }


    fun getUser() : LiveData<ArrayList<UsersTableModel>>? {
        userLiveData = UserRepository.getServicesApiCall()
        return userLiveData
    }

    fun insertUsers(users: ArrayList<UsersTableModel>?) {
        UserRepository.insertData(users)
    }

    fun fetchUsers() {

        val users = UserRepository.fetchAllUsers()
        userRealmDetails.postValue(users)

    }
}