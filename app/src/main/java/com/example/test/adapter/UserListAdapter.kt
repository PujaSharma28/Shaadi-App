package com.example.test.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test.R
import com.example.test.model.UserRealmModel
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_user_details.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

/*
 * Recycler adapter class which display the list of users to UI and update the Users List after accept and decline button
 */
class UserListAdapter(
    val context: Context,
    val list: ArrayList<UserRealmModel>
) : RecyclerView.Adapter<UserListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return MyViewHolder(inflater.inflate(R.layout.activity_user_details, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val realm = Realm.getDefaultInstance()
        val user = list.get(position)
        if (user.name == null) {
            holder.tv_name.setText("Unknown")
        } else {
            holder.tv_name.setText(user.name!!.title + " " + user.name!!.first + " " + user.name!!.last)

        }
        holder.btn_accpet.setBackgroundColor(Color.GREEN)
        holder.btn_decline.setBackgroundColor(Color.RED)
        if (!user.userSelection.equals(0)) {
            holder.btn_layout.visibility = View.INVISIBLE
            if (user.userSelection.equals(1)) {
                holder.tv_accpet_or_declined.setText("User invitation is accpepted.")
            } else {
                holder.tv_accpet_or_declined.setText("User invitation is declined.")
            }
            holder.tv_accpet_or_declined.visibility = View.VISIBLE

        } else {
            holder.tv_accpet_or_declined.visibility = View.INVISIBLE
            holder.btn_layout.visibility = View.VISIBLE

        }
        holder.tv_email.setText(user.email)
        holder.tv_gender.setText(user.gender)
        holder.tv_age.setText(user.dob_age.toString())

        Glide.with(context).load(user.imgRealmModel?.large ?: "")
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.iv_user)



        holder.btn_accpet.setOnClickListener {

            realm.executeTransaction() {
                user.userSelection = 1
                GlobalScope.launch(Dispatchers.Main) {
                    notifyItemChanged(position)

                }

            }

        }

        holder.btn_decline.setOnClickListener {
            realm.executeTransaction() {
                user.userSelection = 2
                GlobalScope.launch(Dispatchers.Main) {
                    notifyItemChanged(position)

                }

            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_name = view.tvName
        var tv_email: TextView = view.tvEmail
        var tv_gender: TextView = view.tvGender
        var tv_age: TextView = view.tvAge
        var btn_accpet: Button = view.btnAccept
        var btn_decline: TextView = view.btnDecline
        var iv_user: ImageView = view.ivUser
        var tv_accpet_or_declined = view.tvAcceptedOrDeclined
        var btn_layout = view.btnLayout

    }
}