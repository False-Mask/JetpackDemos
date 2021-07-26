package com.example.roomdemo.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomdemo.model.entity.ConverterEntity

/**
 *@author ZhiQiang Tu
 *@time 2021/7/26  23:15
 *@signature 好在键盘没坏。ha
 */
@Dao
interface ConverterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEntity(vararg converterEntity: ConverterEntity):List<Long>

    @Query("select * from ConverterEntity")
    fun getAll():List<ConverterEntity>
}