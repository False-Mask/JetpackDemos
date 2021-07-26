package com.example.roomdemo.db

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomdemo.db.dao.UserDao
import com.example.roomdemo.model.User

/**
 *@author ZhiQiang Tu
 *@time 2021/7/24  17:25
 *@signature 我们不明前路，却已在路上
 */
private const val APP_DATABASE_NAME = "AppDataBaseName"
@SuppressLint("RestrictedApi")
@Database(entities = [User::class]
    , version = 1
    /*,autoMigrations =
        [@AutoMigration(1,2)]*/
    ,exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object{
        var instance:AppDatabase? = null
        @Synchronized
        fun getInstance(applicationContext: Context):AppDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(applicationContext,AppDatabase::class.java,
                APP_DATABASE_NAME)
                .fallbackToDestructiveMigrationFrom()
                //.createFromAsset("database/myapp.db")
                .build()
                .apply {
                    instance = this
            }
        }

    }
}