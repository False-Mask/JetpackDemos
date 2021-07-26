package com.example.roomdemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomdemo.db.dao.DogAndOwnerDao
import com.example.roomdemo.model.entity.Dog
import com.example.roomdemo.model.entity.Owner
import com.example.roomdemo.model.entity.relation.DogOwnerCrossRef

/**
 *@author ZhiQiang Tu
 *@time 2021/7/26  15:33
 *@signature 我们不明前路，却已在路上
 */
private const val USER_AND_LIBRARY_DATABASE_NAME = "UserAndLibraryDataBase"
@Database(entities = [Dog::class,Owner::class,DogOwnerCrossRef::class],version = 1)
abstract class DogAndOwnerDataBase : RoomDatabase() {

    abstract fun getDogAndOwnerDao(): DogAndOwnerDao

    companion object{
        var instance:DogAndOwnerDataBase? = null
        @Synchronized
        fun getInstance(applicationContext: Context): DogAndOwnerDataBase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(applicationContext,DogAndOwnerDataBase::class.java,
                USER_AND_LIBRARY_DATABASE_NAME)
                .fallbackToDestructiveMigrationFrom()
                //.createFromAsset("database/myapp.db")
                .build()
                .apply {
                    instance = this
                }
        }

    }
}