package com.example.roomdemo.model.entity.relation

import androidx.room.Entity
import com.example.roomdemo.model.entity.Dog

/**
 *@author ZhiQiang Tu
 *@time 2021/7/26  19:51
 *@signature 我们不明前路，却已在路上
 */
@Entity(primaryKeys = ["dogId","ownerId"])
data class DogOwnerCrossRef(
    val dogId:Long,
    val ownerId:Long
)