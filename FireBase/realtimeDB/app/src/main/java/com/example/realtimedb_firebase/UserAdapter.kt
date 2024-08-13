package com.example.realtimedb_firebase

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

 class UserAdapter(
     private var userList: MutableList<User_KL>,
     private var mIClickListener: ICkickListener
 ) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

   //  private var userList: MutableList<User_KL> = mutableListOf()
  //   private lateinit  var  mIClickListener : ICkickListener

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_User_id: TextView = itemView.findViewById(R.id.tv_id_item)
        val tv_User_name: TextView = itemView.findViewById(R.id.tv_name_item)
        val btn_Update_item: Button = itemView.findViewById(R.id.btn_update_item)
        val btn_Delete_item: Button = itemView.findViewById(R.id.btn_delete_item)
    }

  //   val mListUser : List<User_KL>

interface  ICkickListener{
    fun onClickUpdateItem(user : User_KL)

    fun onClickDeleteItem(user: User_KL)
}

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_user, parent, false)

         return UserViewHolder(view)
     }

     override fun getItemCount(): Int {
         return userList.size
         //return mListUser.size
     }

     @SuppressLint("SetTextI18n")
     override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
         val user : User_KL = userList[position]

         if(user == null) return

         holder.tv_User_id.setText("ID: " + user.id)
         holder.tv_User_name.setText("Name: " + user.name)
         holder.btn_Update_item.setOnClickListener {
             mIClickListener.onClickUpdateItem(user)
         }

         holder.btn_Delete_item.setOnClickListener {
             mIClickListener.onClickDeleteItem(user)
         }
     }
 }