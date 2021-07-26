package com.example.roomdemo.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *@author ZhiQiang Tu
 *@time 2021/7/26  15:29
 *@signature 我们不明前路，却已在路上
 */

@Entity
data class Dog(
    @PrimaryKey val dogId: Long,
    val dogOwnerId: Long,
    val name: String,
    val cuteness: Int,
    val barkVolume: Int,
    val breed: String
)