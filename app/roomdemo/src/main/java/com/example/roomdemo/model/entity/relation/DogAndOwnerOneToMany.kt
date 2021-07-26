package com.example.roomdemo.model.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.roomdemo.model.entity.Dog
import com.example.roomdemo.model.entity.Owner

/**
 *@author ZhiQiang Tu
 *@time 2021/7/26  17:42
 *@signature 我们不明前路，却已在路上
 */
data class DogAndOwnerOneToMany(
    @Embedded
    val owner:Owner,

    @Relation(
        parentColumn = "ownerId",
        entityColumn = "dogOwnerId"
    )
    val dogs:List<Dog>
)