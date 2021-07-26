package com.example.roomdemo.model.entity.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.roomdemo.model.entity.Dog
import com.example.roomdemo.model.entity.Owner

/**
 *@author ZhiQiang Tu
 *@time 2021/7/26  19:45
 *@signature 我们不明前路，却已在路上
 */

data class DogWithOwners(
    @Embedded
    val dog:Dog,
    @Relation(
        parentColumn = "dogId",
        entityColumn = "ownerId",
        associateBy = Junction(DogOwnerCrossRef::class)
    )
    val owner:List<Owner>
)