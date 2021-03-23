package com.example.test.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.util.InternetUtil
import com.example.test.R
import com.example.test.adapter.UserListAdapter
import com.example.test.model.UserRealmModel
import com.example.test.model.UsersTableModel
import com.example.test.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_user.*

lateinit var context: Context

class UserActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        context = this@UserActivity

        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        InternetUtil.observe(this, Observer { status ->
            if (status) {

                userViewModel.getUser()?.observe(this, object : Observer<ArrayList<UsersTableModel>> {

                    override fun onChanged(users: ArrayList<UsersTableModel>?) {

                        Log.i("msg",users?.size.toString() +"=============="+ users?.get(0)?.gender)

                        if(!users.isNullOrEmpty()) {
                            userViewModel.insertUsers(users)
                            userViewModel.fetchUsers()

                        }
                    }

                })
            } else {
                userViewModel.fetchUsers()
                Toast.makeText(this, getString(R.string.network_not_available), Toast.LENGTH_SHORT).show()
            }
        })

        userViewModel.getUsersResult().observe(this, object : Observer<ArrayList<UserRealmModel>> {

            override fun onChanged(userRealms: ArrayList<UserRealmModel>?) {
                if(!userRealms.isNullOrEmpty()) {

                    recyclerView.adapter = UserListAdapter(context, userRealms)
                }
            }
        })
    }
}