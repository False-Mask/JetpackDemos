package com.example.roomdemo.model

import androidx.room.*

/**
 *@author ZhiQiang Tu
 *@time 2021/7/24  17:09
 *@signature 我们不明前路，却已在路上
 */
@Entity(tableName = "user_table")
data class User(
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?
){
    @PrimaryKey(/*autoGenerate = true*/) var uid:Int = 2
    override fun toString(): String {
        return "$uid-$firstName-$lastName"
    }
}
