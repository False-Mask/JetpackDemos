package com.example.roomdemo.db.dao

import androidx.room.*
import com.example.roomdemo.model.User

/**
 *@author ZhiQiang Tu
 *@time 2021/7/24  17:23
 *@signature 好在键盘没坏。ha
 */
@Dao
abstract class UserDao {
    @Query("SELECT * FROM user_table")
    abstract fun getAll(): List<User>

    @Query("SELECT * FROM user_table WHERE uid IN (:userIds)")
    abstract fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user_table WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    abstract fun findByName(first: String, last: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(vararg users: User):List<Long>

    @Delete
    abstract fun delete(user: User):Int
}