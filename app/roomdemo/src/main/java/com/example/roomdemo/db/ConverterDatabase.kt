package com.example.roomdemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.roomdemo.db.converter.Converters
import com.example.roomdemo.db.dao.ConverterDao
import com.example.roomdemo.model.entity.ConverterEntity

/**
 *@author ZhiQiang Tu
 *@time 2021/7/26  23:16
 *@signature 我们不明前路，却已在路上
 */
private const val CONVERTER_DATA_BASE_NAME = "ConverterDatabase"
@Database(version = 1,entities = [ConverterEntity::class])
@TypeConverters(Converters::class)
abstract class ConverterDatabase : RoomDatabase() {
    abstract fun getConverterDao():ConverterDao

    companion object{
        var instance:ConverterDatabase? = null
        @Synchronized
        fun getInstance(applicationContext:Context): ConverterDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(applicationContext,ConverterDatabase::class.java,
                CONVERTER_DATA_BASE_NAME)
                .build()
        }
    }
}