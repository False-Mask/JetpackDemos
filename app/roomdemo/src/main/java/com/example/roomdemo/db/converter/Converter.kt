package com.example.roomdemo.db.converter

import androidx.room.TypeConverter
import java.util.*

/**
 *@author ZhiQiang Tu
 *@time 2021/7/26  23:24
 *@signature 我们不明前路，却已在路上
 */
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}