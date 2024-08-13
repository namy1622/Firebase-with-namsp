package com.example.realtimedb_firebase

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.database

class Activity_List_KT : AppCompatActivity(), UserAdapter.ICkickListener {

    private lateinit var btn_list_push: Button
    private lateinit var edt_list_id: EditText
    private lateinit var edt_list_name: EditText
    private lateinit var recyclerView: RecyclerView

    private lateinit var mUserAdapter: UserAdapter
    private lateinit var mListUser: MutableList<User_KL>

    //--------------------------------------------------------------
    //--------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_kt)

        initUI()
        get_ListUser_From_Realtime_batabase()
        btn_list_push.setOnClickListener {
//            var id: Int = (edt_list_id.text.toString().trim()).toInt()
//            var name: String = edt_list_name.text.toString().trim()
//            var user = User_KL(id, name)
//            onClickAddUser(user)
            clickAdd_All_User()
        }
    }

    private fun clickAdd_All_User() {
        val database = Firebase.database
        val myRef = database.getReference("list_user")

        val list: MutableList<User_KL> = mutableListOf()
        list.add(User_KL(1, "User_1"))
        list.add(User_KL(2, "User_2"))
        list.add(User_KL(3, "User_3"))
        list.add(User_KL(4, "User_4"))

        myRef.setValue(list)

    }

    private fun onClickAddUser(user: User_KL) {


        val database = Firebase.database
        val myRef = database.getReference("list_user")

        val pathObject: String = user.id.toString()
        myRef.child(pathObject).setValue(user).addOnCompleteListener { taskId ->
            if (taskId.isSuccessful) {
                Toast.makeText(this, "Add success", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Failed to add user", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun get_ListUser_From_Realtime_batabase() {
        val database = Firebase.database
        val myRef = database.getReference("list_user")

        // - --------- cach 1---------
//        myRef.addValueEventListener(object  : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                // Xóa danh sách hiện tại trước khi thêm dữ liệu mới
//                mListUser.clear()
//
//                for (userSnapshot in snapshot.children) {
//                    val user: User_KL? = userSnapshot.getValue(User_KL::class.java)
//                    if (user != null) {
//                        mListUser.add(user)
//                    }
//                }
//
//                // Thông báo cho Adapter rằng dữ liệu đã thay đổi
//                mUserAdapter.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Getting Post failed, log a message
//                Log.w("error in get_ListUser_From_Realtime_batabase", "loadPost:onCancelled", error.toException())
//                // ...
//                Toast.makeText(this@Activity_List_KT, "Failed to add user", Toast.LENGTH_LONG).show()
//            }
//        })

        val childEvenListener = object : ChildEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                try {
                    val user: User_KL? = snapshot.getValue(User_KL::class.java)
                    if (user != null) {
                        mListUser.add(user)
                        mUserAdapter.notifyDataSetChanged()
                    }
                } catch (e: DatabaseException) {
                    Log.e(this@Activity_List_KT.toString(), e.printStackTrace().toString())
                    // Xử lý lỗi ở đây, có thể thông báo cho người dùng hoặc ghi log
                }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val user: User_KL? = snapshot.getValue(User_KL::class.java)

                if (user == null) return

                for (i in mListUser.indices) {
                    if (user.id == mListUser.get(i).id) {
                        mListUser[i] = user
                        mUserAdapter.notifyDataSetChanged()
                        break
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User_KL::class.java)

                if(user == null || mListUser == null) return

                for(i in mListUser.indices){
                    if(user.id == mListUser.get(i).id){
                        mListUser.remove(mListUser.get(i))
                        break
                    }
                }
                mUserAdapter.notifyDataSetChanged()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        myRef.addChildEventListener(childEvenListener)
    }

    private fun initUI() {
        // Khởi tạo các view sau khi setContentView
        btn_list_push = findViewById(R.id.btn_list_push)
        edt_list_id = findViewById(R.id.edt_id_list)
        edt_list_name = findViewById(R.id.edt_name_list)
        recyclerView = findViewById(R.id.rv_users)

        val linearlayoutmanager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearlayoutmanager

        mListUser = mutableListOf()

        mUserAdapter = UserAdapter(mListUser, this)
        recyclerView.adapter = mUserAdapter
    }

    //
    override fun onClickUpdateItem(user: User_KL) {
        openDialogUpdateItem(user)
    }

    override fun onClickDeleteItem(user: User_KL) {
        onClickDeleteData(user)
    }

    private fun onClickDeleteData(user: User_KL) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.app_name))
            .setMessage("Ban muon xoa ban ghi nay khong ?")
            .setPositiveButton("Ok", DialogInterface.OnClickListener {
                                                                     dialog, which ->
                val database = Firebase.database
                val myRef = database.getReference("list_user")
                myRef.child((user.id - 1).toString()).removeValue()

            })
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun openDialogUpdateItem(user: User_KL) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.layout_dialog)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.setCancelable(false)

        val edtUpdateName: EditText = dialog.findViewById(R.id.edt_update_name)
        val btnCancelItem: Button = dialog.findViewById(R.id.btn_cancel_item)
        val btnUpdateItem: Button = dialog.findViewById(R.id.btn_update_item)

        edtUpdateName.setText(user.name)

        btnCancelItem.setOnClickListener {
            dialog.dismiss()
        }
        btnUpdateItem.setOnClickListener {
            val new_name = edtUpdateName.text.toString().trim()
            user.name = new_name


            val database = Firebase.database
            val myRef = database.getReference("list_user")
            myRef.child((user.id - 1).toString()).updateChildren(mapOf("name" to new_name))

//            val myRef_chil = myRef.child(user.id.toString())
//            myRef_chil.child("id").updateChildren(mapOf("name" to new_name))

            dialog.dismiss()
        }

        dialog.show()

    }
}



