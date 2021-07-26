package com.example.roomdemo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.roomdemo.R
import com.example.roomdemo.db.ConverterDatabase
import com.example.roomdemo.db.dao.ConverterDao
import com.example.roomdemo.model.entity.ConverterEntity
import kotlinx.android.synthetic.main.activity_room_converter.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
private const val TAG = "RoomConverter"
class RoomConverter : AppCompatActivity() {
    lateinit var dao:ConverterDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_converter)

        dao  = ConverterDatabase.getInstance(applicationContext).getConverterDao()

        setListeners()
    }

    private fun setListeners() {
        button_insert.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO){
                val date =  Date(System.currentTimeMillis())
                val insertEntity = dao.insertEntity(ConverterEntity(date))
                insertEntity.forEach{
                    Log.e(TAG, "insertEntity $it" )
                }
            }
        }

        button_get.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO){
                val data = dao.getAll()
                data.forEach{
                    Log.e(TAG, "insertAll $it" )
                }
            }
        }
    }
}