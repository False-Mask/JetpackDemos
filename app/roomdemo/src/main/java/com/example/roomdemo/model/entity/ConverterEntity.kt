package com.example.roomdemo.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 *@author ZhiQiang Tu
 *@time 2021/7/26  23:20
 *@signature 我们不明前路，却已在路上
 */
@Entity
data class ConverterEntity(
    val data:Date
){
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0
}