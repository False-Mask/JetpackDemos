package com.example.roomdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.roomdemo.db.AppDatabase
import com.example.roomdemo.db.dao.UserDao
import com.example.roomdemo.model.User
import com.example.roomdemo.ui.activity.RoomObjectRelation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    lateinit var userDao:UserDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userDao = AppDatabase.getInstance(applicationContext).userDao()
        setContentView(R.layout.activity_main)
        setListeners()
    }

    private fun setListeners() {
        button_get_all.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val all = userDao.getAll()
                all.forEach{
                    Log.e(TAG, "$it" )
                }
            }
        }

        button_find_by_name.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val result = userDao.findByName("a","a")
                Log.e(TAG, "$result" )
            }
        }

        button_load_all_by_ids.setOnClickListener {
            lifecycleScope.launch (Dispatchers.IO){
                val result = userDao.loadAllByIds(intArrayOf(1,2,3,4))
                result.forEach{
                    Log.e(TAG, "$it" )
                }
            }
        }

        button_delete.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val delete = userDao.delete(User("a", "a"))
                Log.e(TAG, "delete $delete")
            }
        }

        button_insert_all.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO){
                /*val insertAll = userDao.insertAll(
                    Dog("a", "a"),
                    Dog("b", "b"),
                    Dog("c", "c"),
                    Dog("d", "d")
                )*/
                val user = User("a","a")
                val user2 = User("a","b")
                val insertAll = userDao.insertAll(user,user2)
                insertAll.forEach{
                    Log.e(TAG, "insertAll $insertAll" )
                }
            }
        }

        jump_button.setOnClickListener {
            startActivity(Intent(this,RoomObjectRelation::class.java))
        }
    }
}